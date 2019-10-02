package com.mufeez.taskzero.base

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

open class BasePresenter<V : BaseView> : PresenterHelper<V> {

    private var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }

    override fun getView(): V? {
        return mView
    }
}
