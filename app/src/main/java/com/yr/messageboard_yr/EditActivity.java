package com.yr.messageboard_yr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class EditActivity extends AppCompatActivity implements EditCallback {

    private EditViewModel mEditViewModel;

    private Button mSubmit, mCancel;
    private EditText mName, mContent;
    private Message mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mEditViewModel = ViewModelProviders.of(this).get(EditViewModel.class);
        mEditViewModel.setCallback(this);

        mName = findViewById(R.id.name);
        mContent = findViewById(R.id.content);
        mSubmit = findViewById(R.id.submit);
        mCancel = findViewById(R.id.cancel);

        Bundle bundle = getIntent().getExtras();
        final String messageId = bundle.getString("message_id", String.valueOf(0));

        mEditViewModel.getMessage(messageId).observe(this, new Observer<Message>() {
            @Override
            public void onChanged(Message message) {
                mName.setText(message.getUser());
                mContent.setText(message.getContent());
                mMessage = message;
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMessage.setUser(mName.getText().toString());
                mMessage.setContent(mContent.getText().toString());
                mEditViewModel.update(mMessage);
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditViewModel.cancel();
            }
        });
    }

    @Override
    public void onSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        back();
    }

    @Override
    public void onFail(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("失敗!")
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public void onCancel() {
        back();
    }

    private void back() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
