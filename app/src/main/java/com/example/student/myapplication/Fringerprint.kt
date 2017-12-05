package com.example.student.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.pro100svitlo.fingerprintAuthHelper.FahListener
import com.pro100svitlo.fingerprintAuthHelper.FingerprintAuthHelper
import org.jetbrains.anko.toast

class Fringerprint : AppCompatActivity(), FahListener {

    private var mFAH: FingerprintAuthHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fringerprint)

        mFAH = FingerprintAuthHelper.Builder(this, this)
                .setLoggingEnable(true)
                .build()
    }

    override fun onResume() {
        super.onResume()
        mFAH!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        mFAH!!.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        mFAH!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mFAH!!.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mFAH!!.onRestoreInstanceState(savedInstanceState)
    }

    override fun onFingerprintStatus(authSuccessful: Boolean, errorType: Int, errorMess: CharSequence?) {
        if (authSuccessful) {
            toast("Fringerprint success.")
            goToMainActivity()
        } else if (mFAH != null) {
            toast(errorMess.toString())
        }
    }

    override fun onFingerprintListening(listening: Boolean, milliseconds: Long) {
        if (listening) {
            toast("Fingerprint listening.")
        } else {
            toast("Fingerprint not listening.")
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this@Fringerprint, MainActivity::class.java))
    }
}