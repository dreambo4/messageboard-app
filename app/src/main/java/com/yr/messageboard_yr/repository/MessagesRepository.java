package com.yr.messageboard_yr.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static androidx.core.util.Preconditions.checkNotNull;

import com.yr.messageboard_yr.data.db.entity.Message;
import com.yr.messageboard_yr.data.db.MessageBoardRoomDatabase;
import com.yr.messageboard_yr.data.db.dao.MessageDao;

public class MessagesRepository {
    private MessageDao mMessageDao;
    private LiveData<List<Message>> mAllMessages;
    private static String TAG = "MessagesRepository";

    public MessagesRepository(Application application) {
        MessageBoardRoomDatabase db = MessageBoardRoomDatabase.getDatabase(application);
        mMessageDao = db.messageDao();
        mAllMessages = mMessageDao.getAll();
    }

    public LiveData<Message> get(String id) {
        LiveData<Message> message = mMessageDao.get(id);
        return message;
    }

    public LiveData<List<Message>> getAll() {
        return mAllMessages;
    }

    public long insert(Message message) throws ExecutionException, InterruptedException {
        long messageId = new InsertAsyncTask(mMessageDao).execute(message).get();
        return messageId;
    }

    public int update(Message message) throws ExecutionException, InterruptedException {
        int affectRow = new UpdateAsyncTask(mMessageDao).execute(message).get();
        return affectRow;
    }

    public int delete(Message message) throws ExecutionException, InterruptedException {
        int affectRow = new DeleteAsyncTask(mMessageDao).execute(message).get();
        return affectRow;
    }

    private static class InsertAsyncTask extends AsyncTask<Message, Void, Long> {

        private MessageDao mAsyncTaskDao;

        InsertAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(Message... messages) {
            long messageId = mAsyncTaskDao.insert(messages[0]);
            return messageId;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Message, Void, Integer> {

        private MessageDao mAsyncTaskDao;

        UpdateAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(Message... messages) {
            int affectRows = mAsyncTaskDao.update(messages[0]);
            return affectRows;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Message, Void, Integer> {

        private MessageDao mAsyncTaskDao;

        DeleteAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(Message... messages) {
            int affectRows = mAsyncTaskDao.delete(messages[0]);
            return affectRows;
        }
    }
}
