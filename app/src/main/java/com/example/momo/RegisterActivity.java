package com.example.momo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = findViewById(R.id.editText);
        final EditText passwordText =  findViewById(R.id.editText2);
        final EditText nameText =  findViewById(R.id.editText7);
        final EditText emailText =  findViewById(R.id.editText3);

        final Button check_Btn = findViewById(R.id.check_Btn);
        check_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID= idText.getText().toString();
                if(check){
                    return;
                }
                if(userID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    AlertDialog dialog=builder.setMessage("아이디 입력").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                AlertDialog dialog=builder.setMessage("사용 가능").setPositiveButton("확인",null).create();
                                dialog.show();
                                idText.setEnabled(false);
                                check = true;
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                AlertDialog dialog=builder.setMessage("사용 불가").setPositiveButton("확인",null).create();
                                dialog.show();
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                DuplicatedRequest duplicatedRequest = new DuplicatedRequest(userID,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(duplicatedRequest);
            }
        });
        Button registerButton = findViewById(R.id.button3);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                String userEmail = emailText.getText().toString();
                if(!check)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    AlertDialog dialog=builder.setMessage("중복확인 필수").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }
                if(userID.equals("") )
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    AlertDialog dialog=builder.setMessage("ID입력 필수").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }else if(userPassword.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    AlertDialog dialog=builder.setMessage("PW입력 필수").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }else if(userName.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    AlertDialog dialog=builder.setMessage("NAME입력 필수").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }else if(userEmail.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    AlertDialog dialog=builder.setMessage("EMAIL입력 필수").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"등록 성공!!",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                RegisterRequest registerRequest = new RegisterRequest(userID,userPassword,userName,userEmail,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
