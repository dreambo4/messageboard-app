package com.yr.messageboard_yr;

import android.os.AsyncTask;

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
