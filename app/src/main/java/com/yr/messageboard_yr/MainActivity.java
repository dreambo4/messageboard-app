package com.yr.messageboard_yr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ActionCallback, StatusMessageCallback {

    private MainViewModel mMainViewModel;

    private Button mSubmit;
    private EditText mName, mContent;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.setCallback(this);

        mSubmit = findViewById(R.id.submit);
        mName = findViewById(R.id.name);
        mContent = findViewById(R.id.content);
        mRecyclerView = findViewById(R.id.recycler_view);

        final MessageListAdapter adapter = new MessageListAdapter(this, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMainViewModel.getAllMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable final List<Message> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setMessages(words);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message(mName.getText().toString(), mContent.getText().toString());
                mMainViewModel.insert(message);
                Toast.makeText(MainActivity.this, "送出", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onEdit(String messageId) {
        Intent intent = new Intent(this, EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("message_id", messageId);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    @Override
    public void onDelete(Message message) {
        mMainViewModel.delete(message);
    }

    @Override
    public void successMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failMessage(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("失敗!")
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}