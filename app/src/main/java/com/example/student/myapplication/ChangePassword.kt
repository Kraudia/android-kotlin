package com.example.student.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.change_password.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast

class ChangePassword : Activity() {
    var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)

        val bundle = intent.extras
        if (bundle != null) {
            password = bundle.getString("password")
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
        if (old_password_change_edittext.text.toString() != ""
                && old_password_change_edittext.text.toString() == password.toString()
                && new_password_change_edittext.text.toString() != "") {
            database.use {
                dropTable("ValentinaPassword", true)
                createTable("ValentinaPassword", true, "id" to INTEGER, "password" to TEXT)
                password = new_password_change_edittext.text.toString()
                insert("ValentinaPassword", "password" to password, "id" to 1)
            }
            database.close()

            val intent = Intent()
            intent.putExtra("password", password)
            setResult(RESULT_OK, intent);
            toast("Success.")
            finish()

            old_password_change_edittext.text = null
            new_password_change_edittext.text = null

        } else {
            toast("Wrong password.")
        }
    }
}
