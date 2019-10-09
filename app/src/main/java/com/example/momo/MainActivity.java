package com.example.momo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = getIntent();
        final String userID = getIntent().getStringExtra("userID");
        final Button info_Btn = findViewById(R.id.info_Btn);
        Button manage_Btn = findViewById(R.id.manage_Btn);
        if(!userID.equals("admin"))
        {
            manage_Btn.setVisibility(View.INVISIBLE);
        }
        info_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPassword = getIntent().getStringExtra("userPassword");
                String userEmail = getIntent().getStringExtra("userEmail");
                String userName = getIntent().getStringExtra("userName");
                Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userPassword",userPassword);
                intent.putExtra("userName",userName);
                intent.putExtra("userEmail",userEmail);
                MainActivity.this.startActivity(intent);
            }
        });
        //String userPassword = getIntent().getStringExtra("userPassword");

    }
}
