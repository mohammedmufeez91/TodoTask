package com.mufeez.taskzero

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.mufeez.taskzero.activity.main.MainActivity

class RedirectActivity : AppCompatActivity() {

    internal var loginSession: LoginSession? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        loginSession = LoginSession(this)
        val isloggedin = loginSession!!.isLoggedIn

        if (isloggedin) {
            startActivity(Intent(this@RedirectActivity, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@RedirectActivity, GLoginActivity::class.java))
            finish()
        }
    }
}
