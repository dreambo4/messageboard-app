package com.yr.messageboard_yr;

public interface ActionCallback {
    void onEdit(String messageId);

    void onDelete(Message message);
}
