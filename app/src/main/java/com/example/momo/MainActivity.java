package com.example.momo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent inent = getIntent();
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
                AlertDialog.Builder dial=new AlertDialog.Builder(MainActivity.this);
                dial.setTitle("내정보");
                dial.setMessage("ID : "+userID);
//                dial.setNeutralButton("쓴글 확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
                dial.setNeutralButton("정보수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dial.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dial.show();
            }
        });
        //String userPassword = getIntent().getStringExtra("userPassword");
    }
}
