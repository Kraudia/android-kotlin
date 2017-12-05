package com.example.student.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.change_password.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast
import se.simbio.encryption.Encryption

class ChangePassword : Activity() {
    var password: String? = null
    var message: String = ""
    var fail: Boolean = false

    val key = "YourKey"
    val salt = "YourSalt"
    val iv = ByteArray(16)
    val encryption = Encryption.getDefault(key, salt, iv)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)

        val bundle = intent.extras
        if (bundle != null) {
            password = bundle.getString("password")
            message = bundle.getString("message")
            fail = bundle.getBoolean("fail")
        }

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
        else if (encryption.encryptOrNull(old) == password.toString()) {
            password = encryption.encryptOrNull(new)

            database.use {
                update("Valentina", "password" to password, "message" to message).whereSimple("id = 1").exec()
            }
            database.close()

            val intent = Intent()
            intent.putExtra("password", password)
            intent.putExtra("message", message)
            intent.putExtra("fail", false)
            setResult(RESULT_OK, intent);
            toast("Success.")
            finish()
        } else {
            val intent = Intent()
            intent.putExtra("password", password)
            intent.putExtra("message", message)
            intent.putExtra("fail", true)
            setResult(RESULT_OK, intent);
            toast("Wrong password. Fail.")
            finish()
        }

        old_password_change_edittext.text = null
        new_password_change_edittext.text = null
    }
}
