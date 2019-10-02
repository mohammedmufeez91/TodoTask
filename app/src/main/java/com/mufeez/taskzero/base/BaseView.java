package com.mufeez.taskzero.base;

import android.content.Context;

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

public interface BaseView {
    void showMessage(String message);
    void showMessage(int resId);
    Context getContext();
}
