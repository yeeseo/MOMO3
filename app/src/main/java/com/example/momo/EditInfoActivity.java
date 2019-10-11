package com.example.momo;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class EditInfoActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinfo);
        final String userID = getIntent().getStringExtra("userID");
        final String userEmail = getIntent().getStringExtra("userEmail");
        final String userName = getIntent().getStringExtra("userName");

        final EditText edit_id_text = findViewById(R.id.edit_id_text);
        final EditText edit_pw2_text = findViewById(R.id.edit_pw2_test);
        final EditText edit_pw_text = findViewById(R.id.edit_pw_test);
        final EditText edit_email_text = findViewById(R.id.edit_email_text);
        final EditText edit_name_text = findViewById(R.id.edit_name_text);
        edit_id_text.setText(userID);
        edit_email_text.setText(userEmail);
        edit_name_text.setText(userName);

        Button edit_confirm_Btn = findViewById(R.id.edit_confirm_Btn);
        edit_confirm_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = edit_email_text.getText().toString();
                String userName = edit_name_text.getText().toString();
                String userPassword = edit_pw_text.getText().toString();
                String userPassword2 = edit_pw2_text.getText().toString();
                if(!userPassword.equals(userPassword2) )
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditInfoActivity.this);
                    AlertDialog dialog=builder.setMessage("비밀번호가 일치하지 않습니다.").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }
                else if(userPassword.equals("") && userPassword2.equals(""))
                {
                    userPassword = getIntent().getStringExtra("userPassword");
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.d("jsonresponse", jsonResponse.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                EditInfoRequest editInfoRequest = new EditInfoRequest(userID, userEmail, userName, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditInfoActivity.this);
                queue.add(editInfoRequest);
                Toast.makeText(getApplicationContext(),"수정 완료",Toast.LENGTH_SHORT).show();
                finish();
//                Intent intent = new Intent(EditInfoActivity.this, MypageActivity.class);
//                intent.putExtra("userID",userID);
//                intent.putExtra("userPassword",userPassword);
//                intent.putExtra("userName",userName);
//                intent.putExtra("userEmail",userEmail);
//                EditInfoActivity.this.startActivity(intent);
            }
        });
    }
}
