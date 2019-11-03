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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

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
                        AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
                        builder.setMessage("실패!!").setNegativeButton("다시 시도", null).create().show();
                    }
                } catch (Exception e) {
                    Log.d("하아...", "showResult : ", e);
                }
            }
        };
        String bid = getIntent().getStringExtra("BID");
        GetPostSaleRequest getPostSaleRequest = new GetPostSaleRequest(bid, responseListener);
        RequestQueue queue = Volley.newRequestQueue(PostActivity.this);
        queue.add(getPostSaleRequest);


        TextView replyid = findViewById(R.id.replyid);
        replyid.setText(userID);
        dateNow= findViewById(R.id.replydate);
        ShowTimeMethod();

        final TextView id = findViewById(R.id.replyid);
        final TextView replydate =  findViewById(R.id.replydate);
        final EditText content =  findViewById(R.id.content);

        Button replybtn = findViewById(R.id.ReplyBtn);
        replybtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String userID = id.getText().toString();
                String replyDate = replydate.getText().toString();
                String contenttext = content.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                content.setText(null);
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                //ReplyRequest replyRequest = new ReplyRequest(userID,contenttext,replyDate,responseListener);//댓글번호,게시판번호추가 댓글번호필요한가?
                RequestQueue queue = Volley.newRequestQueue(PostActivity.this);
                //queue.add(replyRequest);

            }
        });
    }
    public void ShowTimeMethod() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dateNow.setText(DateFormat.getDateTimeInstance().format(new Date()));
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


