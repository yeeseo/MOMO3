package com.example.momo;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

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
    }
}
