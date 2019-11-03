package com.example.momo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MypageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        //Intent intent = getIntent();
        final String userID = getIntent().getStringExtra("userID");
        final String userPassword = getIntent().getStringExtra("userPassword");
        final String userEmail = getIntent().getStringExtra("userEmail");
        final String userName = getIntent().getStringExtra("userName");

        Button editInfo_Btn = findViewById(R.id.edit_myinfo);
        Button myPost_Btn = findViewById(R.id.mypost);
        myPost_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MypostActivity.class);
                intent.putExtra("userID",userID);
                MypageActivity.this.startActivity(intent);
            }
        });

        editInfo_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, EditInfoActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userPassword",userPassword);
                intent.putExtra("userName",userName);
                intent.putExtra("userEmail",userEmail);
                MypageActivity.this.startActivity(intent);
            }
        });
    }
}