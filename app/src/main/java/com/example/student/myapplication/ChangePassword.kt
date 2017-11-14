package com.example.student.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.change_password.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast

class ChangePassword : Activity() {
    var password: String? = null
    var message: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)

        val bundle = intent.extras
        if (bundle != null) {
            password = bundle.getString("password")
            message = bundle.getString("message")
        }

        toast(password.toString())

        change_password_submit_button.setOnClickListener {
            changePassword()
        }

        back_from_change_button.setOnClickListener {
            back()
        }

    }

    override fun onBackPressed() {
        back()
    }

    private fun back() {
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    private fun changePassword() {
        val old = old_password_change_edittext.text.toString()
        val new = new_password_change_edittext.text.toString()

        if (old == "" || new == "") toast("Fields cannot be empty.")
        else if (old == password.toString()) {
            password = new

            database.use {
                update("Valentina", "password" to password, "message" to "", "id" to 1).whereSimple("id = 1")
            }
            database.close()

            val intent = Intent()
            intent.putExtra("password", new)
            intent.putExtra("message", message)
            setResult(RESULT_OK, intent);
            toast("Success.")
            finish()
        } else toast("Wrong password. Fail ")

        old_password_change_edittext.text = null
        new_password_change_edittext.text = null
    }
}
