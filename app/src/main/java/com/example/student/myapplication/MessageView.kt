package com.example.student.myapplication

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.message_view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

class MessageView : AppCompatActivity() {
    var message: String = ""
    var password: String = ""
    var fail: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_view)

        val bundle = intent.extras
        if (bundle != null) {
            password = bundle.getString("password")
            message = bundle.getString("message")
            fail = bundle.getBoolean("fail")
        }

        init(password, message)

        change_message_from_view_button.setOnClickListener {
            goToChangeMessageActivity(password, message)
        }

        back_from_message_button.setOnClickListener {
            back()
        }
    }

    fun init(pass: String, mess: String) {
        if (mess != "") message_textview.text = mess.toString()
        else {
            alert("Please enter your new message.","Empty message") {
                positiveButton("OK") {
                    goToChangeMessageActivity(pass, mess)
                }
            }.show()
        }
    }

    fun goToChangeMessageActivity(pass: String, mess: String) {
        val intent = Intent(this, ChangeMessage::class.java)
        intent.putExtra("password", pass)
        intent.putExtra("message", mess)
        intent.putExtra("fail", fail)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivityForResult(intent, 1);
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                message = data.getStringExtra("message")
                message_textview.text = message
            }
        }
    }

    override fun onBackPressed() {
        back()
    }

    private fun back() {
        val intent = Intent()
        intent.putExtra("message", message)
        intent.putExtra("password", password)
        intent.putExtra("fail", fail)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
