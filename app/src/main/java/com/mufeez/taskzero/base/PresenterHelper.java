package com.mufeez.taskzero.base;

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

public interface PresenterHelper<V extends  BaseView> {
    void attachView(V view);
    void detachView();
    V getView();
}
