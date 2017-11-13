package com.example.student.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        new_password_textview.setVisibility(View.GONE)
        new_password_edittext.setVisibility(View.GONE)
        new_password_button.setVisibility(View.GONE)

        see_message_button.setOnClickListener {
            showMessage()
        }
    }

    private fun showMessage() {
        alert(R.string.fail_password_message, R.string.fail_password_title) {
            positiveButton(R.string.close) { }
        }.show()
    }
}
