package com.yr.messageboard_yr.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yr.messageboard_yr.view.EditCallback;
import com.yr.messageboard_yr.data.db.entity.Message;
import com.yr.messageboard_yr.repository.MessagesRepository;

import java.util.concurrent.ExecutionException;

public class EditViewModel extends AndroidViewModel {
    private MessagesRepository mMessagesRepository;
    private EditCallback mEditCallback;
    private String TAG = getClass().getSimpleName();

    public EditViewModel(@NonNull Application application) {
        super(application);
        mMessagesRepository = new MessagesRepository(application);
    }

    public void setCallback(EditCallback editCallback) {
        mEditCallback = editCallback;
    }

    public LiveData<Message> getMessage(String id) {
        LiveData<Message> message = mMessagesRepository.get(id);
        return message;
    }

    public void update(Message message) {
        int affectRow = 0;
        try {
            affectRow = mMessagesRepository.update(message);
        } catch (ExecutionException e) {
            mEditCallback.onFail("修改發生錯誤!");
        } catch (InterruptedException e) {
            mEditCallback.onFail("修改中斷!");
        }
        Log.d(TAG, "update: " + affectRow);
        if (affectRow == 1) {
            mEditCallback.onSuccess("修改成功");
        } else {
            mEditCallback.onFail("修改失敗");
        }
    }

    public void cancel() {
        mEditCallback.onCancel();
    }
}
