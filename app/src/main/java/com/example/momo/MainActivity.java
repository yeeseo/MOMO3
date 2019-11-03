package com.example.momo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID = "ID";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TEXT = "text";
    ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
    ListView mListViewList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String userID = getIntent().getStringExtra("userID");
        final Button info_Btn = findViewById(R.id.info_Btn);
        Button manage_Btn = findViewById(R.id.manage_Btn);
        if(!userID.equals("admin"))
        {
            manage_Btn.setVisibility(View.INVISIBLE);
        }
        mListViewList = findViewById(R.id.main_sale_list);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                Log.d("response@@@@@@", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray(TAG_JSON);

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            Log.d("response@@@@@@", item.toString());
                            String title = item.getString(TAG_TITLE);
                            String id = item.getString(TAG_ID);
                            String date = item.getString("start_date") + "~" + item.getString("end_date");
                            Log.d("~~~~~", title + date);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(TAG_ID, id);
                            hashMap.put(TAG_TITLE, title);
                            hashMap.put(TAG_TEXT, date);

                            mArrayList.add(hashMap);
                        }
                        if (mArrayList.size() > 0) {
                            Log.d("length---", " > 0");
                        }

                        ListAdapter adapter = new SimpleAdapter(
                                MainActivity.this, mArrayList, R.layout.post_list,
                                new String[]{TAG_ID, TAG_TITLE, TAG_TEXT},
                                new int[]{R.id.textView_list_id, R.id.textView_list_title, R.id.textView_list_date}
                        );
                        ((BaseAdapter) adapter).notifyDataSetChanged();
                        mListViewList.setAdapter(adapter);



                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("실패!!").setNegativeButton("다시 시도", null).create().show();
                    }
                } catch (Exception e) {
                    Log.d("하아...", "showResult : ", e);
                }
            }
        };

        BoardForSaleRequest boardForSaleRequest = new BoardForSaleRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(boardForSaleRequest);
        mListViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("클릭클릭클릭클릭...", "showResult : ");
                Intent go = new Intent(MainActivity.this, PostActivity.class);
                String userName = getIntent().getStringExtra("userName");
                go.putExtra("userID",userID);
                go.putExtra("userName",userName);
                go.putExtra("BID", mArrayList.get(position).get(TAG_ID));
                MainActivity.this.startActivity(go);
            }
        });


        info_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userID.equals("admin"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    AlertDialog dialog=builder.setMessage("관리자 계정").setPositiveButton("확인",null).create();
                    dialog.show();
                }
                else {
                    String userPassword = getIntent().getStringExtra("userPassword");
                    String userEmail = getIntent().getStringExtra("userEmail");
                    String userName = getIntent().getStringExtra("userName");
                    Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                    intent.putExtra("userID", userID);
                    intent.putExtra("userPassword", userPassword);
                    intent.putExtra("userName", userName);
                    intent.putExtra("userEmail", userEmail);
                    MainActivity.this.startActivity(intent);
                }
            }
        });
        //String userPassword = getIntent().getStringExtra("userPassword");

        manage_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                new BackgroundTask().execute();
            }
        });
        //삭제부분==========================================================================
//        Button postlist = findViewById(R.id.postlist);
////        postlist.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick (View view){
////                Intent go = new Intent(MainActivity.this, PostActivity.class);
////                MainActivity.this.startActivity(go);
////            }
////        });
        //삭제부분===========================================================================
        Button write_post_btn = findViewById(R.id.write_post);
        write_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(MainActivity.this, UploadPostActivity.class);
                intent.putExtra("userID", userID);
                MainActivity.this.startActivity(intent);
            }
        });
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent logout = new Intent(MainActivity.this, StartActivity.class);
                MainActivity.this.startActivity(logout);
                finish();
                LoginActivity LA = (LoginActivity) LoginActivity.loginactivity;
                LA.finish();
                StartActivity SA = (StartActivity) StartActivity.startactivity;
                SA.finish();
            }
        });
    }
    @Override
    protected  void onResume(){
        super.onResume();
        mListViewList = findViewById(R.id.main_sale_list);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                Log.d("response@@@@@@", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray(TAG_JSON);

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            Log.d("response@@@@@@", item.toString());
                            String title = item.getString(TAG_TITLE);
                            String id = item.getString(TAG_ID);
                            String date = item.getString("start_date") + "~" + item.getString("end_date");
                            Log.d("~~~~~", title + date);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(TAG_ID, id);
                            hashMap.put(TAG_TITLE, title);
                            hashMap.put(TAG_TEXT, date);

                            mArrayList.add(hashMap);
                        }
                        if (mArrayList.size() > 0) {
                            Log.d("length---", " > 0");
                        }

                        ListAdapter adapter = new SimpleAdapter(
                                MainActivity.this, mArrayList, R.layout.post_list,
                                new String[]{TAG_ID, TAG_TITLE, TAG_TEXT},
                                new int[]{R.id.textView_list_id, R.id.textView_list_title, R.id.textView_list_date}
                        );
                        ((BaseAdapter) adapter).notifyDataSetChanged();
                        mListViewList.setAdapter(adapter);



                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("실패!!").setNegativeButton("다시 시도", null).create().show();
                    }
                } catch (Exception e) {
                    Log.d("하아...", "showResult : ", e);
                }
            }
        };

        BoardForSaleRequest boardForSaleRequest = new BoardForSaleRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(boardForSaleRequest);
    }
    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String target;
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp=bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute(){
            target = "http://khsung0.dothome.co.kr/UserList.php";
        }
        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result){
            Intent intent = new Intent(MainActivity.this, ManageActivity.class);
            intent.putExtra("userList",result);
            MainActivity.this.startActivity(intent);
        }
    }
}
