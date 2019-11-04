package com.example.momo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class UploadPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpost);

        final EditText _title = findViewById(R.id.up_title);
        final EditText _start_date =  findViewById(R.id.up_startdate);
        final EditText _end_date =  findViewById(R.id.up_enddate);
        final EditText _content =  findViewById(R.id.up_content);
        final String userID = getIntent().getStringExtra("userID");
        final RequestQueue queue = Volley.newRequestQueue(UploadPostActivity.this);
        Button postButton = findViewById(R.id.up_btn);
        postButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String title = _title.getText().toString();
                String start_date = _start_date.getText().toString();
                String end_date = _end_date.getText().toString();
                String content = _content.getText().toString();
                if(title.equals("") )
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadPostActivity.this);
                    AlertDialog dialog=builder.setMessage("제목 입력 필수").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }else if(start_date.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadPostActivity.this);
                    AlertDialog dialog=builder.setMessage("시작날짜 입력 필수").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }else if(end_date.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadPostActivity.this);
                    AlertDialog dialog=builder.setMessage("종료날짜 입력 필수").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }else if(content.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadPostActivity.this);
                    AlertDialog dialog=builder.setMessage("내용 입력 필수").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.d("RESPONSE@@@@@@@@@@@@", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"등록 성공!!",Toast.LENGTH_SHORT).show();
                                queue.stop();
                                finish();
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UploadPostActivity.this);
                                AlertDialog dialog=builder.setMessage("등록 실패!!").setPositiveButton("확인",null).create();
                                dialog.show();
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                UploadPostRequest uploadPostRequest = new UploadPostRequest(userID, title, start_date, end_date,content,responseListener);
                queue.add(uploadPostRequest);
            }
        });
    }
}
