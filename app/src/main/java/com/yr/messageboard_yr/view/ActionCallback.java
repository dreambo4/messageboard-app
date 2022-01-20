package com.yr.messageboard_yr.view;

import com.yr.messageboard_yr.data.db.entity.Message;

public interface ActionCallback {
    void onEdit(String messageId);

    void onDelete(Message message);
}
