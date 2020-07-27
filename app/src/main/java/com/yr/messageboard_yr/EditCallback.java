package com.yr.messageboard_yr;

public interface EditCallback {
    void onSuccess(String msg);

    void onFail(String msg);

    void onCancel();
}
