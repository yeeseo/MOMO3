package com.example.momo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity {
    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID = "userID";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_DATE = "create_at";
    ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
    ListView mListViewList;

    TextView dateNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        final String userID = getIntent().getStringExtra("userID");
        final String userName = getIntent().getStringExtra("userName");
        final TextView title_text = findViewById(R.id.post_title);
        final TextView date_text = findViewById(R.id.post_date);
        final TextView content_text = findViewById(R.id.post_content);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response@@@@@@", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("webnautes");

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            Log.d("response@@@@@@", item.toString());
                            String title = item.getString("title");
                            String content = item.getString("content");
                            String date = item.getString("start_date") + "~" + item.getString("end_date");
                            Log.d("~~~~~", title + date);
                            title_text.setText(title);
                            date_text.setText(date);
                            content_text.setText(content);
                        }
                    } else {
                    }
                } catch (Exception e) {
                    Log.d("하아...", "showResult : ", e);
                }
            }
        };
        final String bid = getIntent().getStringExtra("BID");
        GetPostSaleRequest getPostSaleRequest = new GetPostSaleRequest(bid, responseListener);
        RequestQueue queue = Volley.newRequestQueue(PostActivity.this);
        queue.add(getPostSaleRequest);


        mListViewList = findViewById(R.id.reply_list);
        mListViewList.setAdapter(null);
        Response.Listener<String> replyListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reply---@@@@@@", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray(TAG_JSON);
                    mArrayList.clear();
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            Log.d("response@@@@@@", item.toString());
                            String content = item.getString(TAG_CONTENT);
                            String id = item.getString(TAG_ID);
                            String date = item.getString(TAG_DATE);
                            Log.d("~~~~~", content + date);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(TAG_ID, id);
                            hashMap.put(TAG_CONTENT, content);
                            hashMap.put(TAG_DATE, date);

                            mArrayList.add(hashMap);
                        }
                        if (mArrayList.size() > 0) {
                            Log.d("length---", " > 0");
                        }

                        ListAdapter adapter = new SimpleAdapter(
                                PostActivity.this, mArrayList, R.layout.reply_list,
                                new String[]{TAG_ID, TAG_CONTENT, TAG_DATE},
                                new int[]{R.id.reply_id, R.id.reply_content, R.id.reply_date}
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

        GetReplyRequest getReplyRequest = new GetReplyRequest(bid, replyListener);
        RequestQueue newQueue = Volley.newRequestQueue(PostActivity.this);
        queue.add(getReplyRequest);

        TextView replyid = findViewById(R.id.reply_id);
        replyid.setText(userID);
        dateNow= findViewById(R.id.reply_date);
        ShowTimeMethod();

        final TextView id = findViewById(R.id.reply_id);
        final TextView replydate =  findViewById(R.id.reply_date);
        final EditText content =  findViewById(R.id.reply_content);

        Button replybtn = findViewById(R.id.ReplyBtn);
        replybtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String userID = id.getText().toString();
                final String replyDate = replydate.getText().toString();
                final String contenttext = content.getText().toString();
                if (contenttext.equals("")) {
                    Toast.makeText(getApplicationContext(), "댓글을 입력하세요", Toast.LENGTH_LONG).show();
                } else {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("@@@@@@@@@ohyeah", contenttext + '\n' + response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    content.setText(null);
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put(TAG_ID, userID);
                                    hashMap.put(TAG_CONTENT, contenttext);
                                    hashMap.put(TAG_DATE, replyDate);
                                    mArrayList.add(hashMap);
                                    ListAdapter adapter = new SimpleAdapter(
                                            PostActivity.this, mArrayList, R.layout.reply_list,
                                            new String[]{TAG_ID, TAG_CONTENT, TAG_DATE},
                                            new int[]{R.id.reply_id, R.id.reply_content, R.id.reply_date}
                                    );
                                    ((BaseAdapter) adapter).notifyDataSetChanged();
                                    mListViewList.setAdapter(adapter);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    Log.d("###########", bid + "/" + userID + '/' + contenttext + '/' + replyDate);
                    ReplyRequest replyRequest = new ReplyRequest(bid, userID, contenttext, replyDate, responseListener);//댓글번호,게시판번호추가 댓글번호필요한가?
                    RequestQueue queue = Volley.newRequestQueue(PostActivity.this);
                    queue.add(replyRequest);
                    Toast.makeText(getApplicationContext(), "등록 성공", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    public void ShowTimeMethod() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat timer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateFormat = timer.format(date);
                dateNow.setText(dateFormat);

            }
        };
        Runnable task = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    handler.sendEmptyMessage(1);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}


