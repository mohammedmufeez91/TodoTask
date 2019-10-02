package com.mufeez.taskzero.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

abstract class BaseActivity : AppCompatActivity(), BaseView {
    override fun showMessage(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Oops! something went wrong.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showMessage(resId: Int) {
        showMessage(getString(resId))
    }

    override fun getContext(): Context {
        return this
    }
}
