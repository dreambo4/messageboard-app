package com.yr.messageboard_yr;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.UUID;

@Entity(tableName = "messages")
public final class Message {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String mId;

    @NonNull
    @ColumnInfo(name = "user")
    private String mUser;

    @NonNull
    @ColumnInfo(name = "content")
    private String mContent;

    @NonNull
    @ColumnInfo(name = "time")
    private long mTime;

    public Message(@NonNull String user, @NonNull String content) {
        this(UUID.randomUUID().toString(), user, content, System.currentTimeMillis());
    }

    @Ignore
    public Message(@NonNull String id, @NonNull String user,
                   @NonNull String content, @NonNull long time) {
        mId = id;
        mUser = user;
        mContent = content;
        mTime = time;
    }

    @NonNull
    @Override
    public String toString() {
        HashMap<String, String> message = new HashMap<>();
        message.put("id", mId);
        message.put("user", mUser);
        message.put("content", mContent);
        message.put("time", Long.toString(mTime));

        return message.toString();
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getUser() {
        return mUser;
    }

    @NonNull
    public String getContent() {
        return mContent;
    }

    @NonNull
    public long getTime() {
        return mTime;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setUser(String user) {
        mUser = user;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public void setTime(long time) {
        mTime = time;
    }
}
