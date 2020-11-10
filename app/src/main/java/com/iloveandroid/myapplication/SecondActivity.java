package com.iloveandroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView mReceive;
    EditText mReply;
    Button btnReply;
    public static final String EXTRA_REPLY = "REPLY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_second );
        mReceive = findViewById(R.id.receive);
        mReply = findViewById(R.id.txtReply);
        btnReply = findViewById( R.id.btnReply );


        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        mReceive.setText(message);




    }

    public void returnReply(View view) {

        String reply = mReply.getText().toString();
        Intent replyIntent  = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply );
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}