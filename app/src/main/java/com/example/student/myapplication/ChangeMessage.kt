package com.example.student.myapplication

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.change_message.*
import kotlinx.android.synthetic.main.change_password.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast

class ChangeMessage : AppCompatActivity() {
    var message: String = ""
    var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_message)

        init()

        change_message_button.setOnClickListener {
            changeMessage()
        }

        back_button_from_change_message.setOnClickListener {
            back(message)
        }
    }

    fun init() {
        val bundle = intent.extras
        if (bundle != null) {
            password = bundle.getString("password")
            message = bundle.getString("message")
        }

        if (message != "") {
            change_message_header.text = getString(R.string.change_message)
            change_message_textview.text = getString(R.string.change_message_label)
            change_message_button.text = getString(R.string.change_message)
        } else {
            change_message_header.text = getString(R.string.save_message)
            change_message_textview.text = getString(R.string.save_message_label)
            change_message_button.text = getString(R.string.save_message)
        }
    }
    private fun changeMessage() {
        val new = change_message_edittext.text.toString()

        if (new == "") toast("Field cannot be empty.")
        else {
            database.use {
                update("Valentina", "password" to password, "message" to new).whereSimple("id = 1").exec()
            }
            database.close()
            toast("Success. Message has been edited.")
            back(new)
        }
        change_message_edittext.text = null
    }

    override fun onBackPressed() {
        back(message)
    }

    private fun back(mess: String) {
        val intent = Intent()
        intent.putExtra("message", mess)
        intent.putExtra("password", password)
        setResult(Activity.RESULT_OK, intent);
        finish()
    }
}
