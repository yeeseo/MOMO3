package com.example.momo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;



public class MypostActivity extends AppCompatActivity {
    private static final String TAG_JSON="webnautes";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TEXT = "text";
    private static final String TAG_ID = "ID";
    ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
    ListView mListViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);
        mListViewList = findViewById(R.id.listView_main_list);
        Button my_post_sale = findViewById(R.id.my_post_sale);
        final String userID = getIntent().getStringExtra("userID");
        my_post_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AlertDialog.Builder b = new AlertDialog.Builder(MypostActivity.this);
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
                                        MypostActivity.this, mArrayList, R.layout.post_list,
                                        new String[]{TAG_ID, TAG_TITLE, TAG_TEXT},
                                        new int[]{R.id.textView_list_id, R.id.textView_list_title, R.id.textView_list_date}
                                );
                                ((BaseAdapter) adapter).notifyDataSetChanged();
                                mListViewList.setAdapter(adapter);


                            } else {
                            }
                        } catch (Exception e) {
                            Log.d("하아...", "showResult : ", e);
                        }
                    }
                };

                MypostRequest mypostRequest = new MypostRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MypostActivity.this);
                queue.add(mypostRequest);
            }
        });
    }
}