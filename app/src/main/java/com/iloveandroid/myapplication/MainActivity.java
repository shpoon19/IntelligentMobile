package com.iloveandroid.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "MESSAGE";
    private static final  int TEXT_REQUEST = 1;

    EditText mMessage;
    TextView mTextHeader, mTextReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mMessage = findViewById( R.id.editText_main );
        mTextHeader = findViewById( R.id.text_header_reply );
        mTextReply = findViewById( R.id.text_message_reply );

    }

    public void launchSecondActivity(View view) {

        String message = mMessage.getText().toString();

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);
        startActivityForResult(intent, TEXT_REQUEST);
        Log.d(LOG_TAG, "Button Clicked!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == TEXT_REQUEST){
            if (resultCode == RESULT_OK){
                String reply = data.getStringExtra( SecondActivity.EXTRA_REPLY );
                mTextHeader.setVisibility(View.VISIBLE );
                mTextReply.setText( reply );
                mTextReply.setVisibility( View.VISIBLE );

            }
        }
    }
}