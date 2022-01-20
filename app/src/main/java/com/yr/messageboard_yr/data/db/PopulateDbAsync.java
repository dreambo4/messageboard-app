package com.yr.messageboard_yr.data.db;

import android.os.AsyncTask;

import com.yr.messageboard_yr.data.db.MessageBoardRoomDatabase;
import com.yr.messageboard_yr.data.db.dao.MessageDao;

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
    private final MessageDao mDao;

    public PopulateDbAsync(MessageBoardRoomDatabase db) {
        mDao = db.messageDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        return null;
    }
}
