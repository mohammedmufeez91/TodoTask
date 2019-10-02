package com.mufeez.taskzero

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.mufeez.taskzero.activity.main.MainActivity

class GLoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private var signInButton: SignInButton? = null
    private var gso: GoogleSignInOptions? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    internal lateinit var loginSession: LoginSession
    private val SIGN_IN = 30
    private val tv: TextView? = null
    private val iv: ImageView? = null
    private var skipBtn:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.g_login_layout)

        loginSession = LoginSession(this)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        signInButton = findViewById<View>(R.id.sign_in_button) as SignInButton
        skipBtn = findViewById<View>(R.id.skip_btn) as Button

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso!!)
                .build()

        signInButton!!.setOnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, SIGN_IN)
        }

        /* tv = (TextView) findViewById(R.id.text);
        iv = (ImageView) findViewById(R.id.iv);*/
        /* btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                Toast.makeText(MainActivity.this, "Logout Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });*/

        skipBtn!!.setOnClickListener()
        {
            loginSession.createLoginSession("na", "na","na")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //If signin
        if (requestCode == SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            //Calling a new function to handle signin
            handleSignInResult(result)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess)
        Log.d(TAG, "handleSignInResult:status" + result.status)

        var photourl = "empty"
        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //Similarly you can get the email and photourl using acct.getEmail() and  acct.getPhotoUrl()

            Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show()


            loginSession.createLoginSession(acct!!.displayName, acct.email, acct.photoUrl!!.toString())
            startActivity(Intent(this, MainActivity::class.java))
            finish()

            if (acct.photoUrl != null) {
                photourl = acct.photoUrl!!.toString()
            }

            //calling api for storing data in  backend
            makePostRequest(acct.displayName, acct.email, photourl)

        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "login failed signedout", Toast.LENGTH_SHORT).show()
            //updateUI(false);
        }
    }


    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Toast.makeText(this, "onconnection failed ", Toast.LENGTH_SHORT).show()
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {
        Toast.makeText(this, "pointer changed", Toast.LENGTH_SHORT).show()
    }


    /**
     * Background Async task to load user profile picture from url
     */
    private inner class LoadProfileImage(internal var bmImage: ImageView) : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg uri: String): Bitmap? {
            val url = uri[0]
            var mIcon11: Bitmap? = null
            try {
                val `in` = java.net.URL(url).openStream()
                mIcon11 = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error", e.message)
                e.printStackTrace()
            }

            return mIcon11
        }

        override fun onPostExecute(result: Bitmap?) {

            if (result != null) {

                val resized = Bitmap.createScaledBitmap(result, 200, 200, true)
                bmImage.setImageBitmap(ImageHelper.getRoundedCornerBitmap(this@GLoginActivity, resized, 250, 200, 200, false, false, false, false))

            }
        }
    }


    internal fun makePostRequest(username: String?, email: String?, photourl: String) {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
                .baseUrl(AllConstants.MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val service = retrofit.create(RetrofitInterface::class.java)

        val call = service.login(AllConstants.MODULE_TODO, AllConstants.MODULE_ACTION_REG, username!!, email!!, photourl)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                Log.i("api-status", "success")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("api-status", "failed")
            }
        })
    }

    companion object {
        //private Button btn;
        private val TAG = "GLoginActivity"
    }


}
