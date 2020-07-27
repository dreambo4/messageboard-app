package com.yr.messageboard_yr;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Message.class}, version = 1, exportSchema = false)
public abstract class MessageBoardRoomDatabase extends RoomDatabase {
    private static MessageBoardRoomDatabase INSTANCE;

    public abstract MessageDao messageDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    public static MessageBoardRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MessageBoardRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MessageBoardRoomDatabase.class, "messageboard_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
