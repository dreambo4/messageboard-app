package com.yr.messageboard_yr;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {
    private MessagesRepository mMessagesRepository;
    private LiveData<List<Message>> mAllMessages;
    private StatusMessageCallback mCallback;
    private String TAG = getClass().getSimpleName();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mMessagesRepository = new MessagesRepository(application);
        mAllMessages = mMessagesRepository.getAll();
    }

    public void setCallback(StatusMessageCallback callback) {
        mCallback = callback;
    }

    LiveData<List<Message>> getAllMessages() {
        return mAllMessages;
    }

    public void insert(Message message) {
        long messageId = 0;
        try {
            messageId = mMessagesRepository.insert(message);
        } catch (ExecutionException e) {
            mCallback.failMessage("新增發生錯誤!");
        } catch (InterruptedException e) {
            mCallback.failMessage("新增中斷!");
        }
        Log.d(TAG, "insert: " + messageId);
        if (messageId >= 1) {
            mCallback.successMessage("新增成功");
        } else {
            mCallback.failMessage("新增失敗");
        }
    }

    public void delete(Message message) {
        int affectRow = 0;
        try {
            affectRow = mMessagesRepository.delete(message);
        } catch (ExecutionException e) {
            mCallback.failMessage("刪除發生錯誤!");
        } catch (InterruptedException e) {
            mCallback.failMessage("刪除中斷!");
        }
        Log.d(TAG, "delete: " + affectRow);
        if (affectRow == 1) {
            mCallback.successMessage("刪除成功");
        } else {
            mCallback.failMessage("刪除失敗");
        }
    }
}
