package com.example.student.myapplication

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    var message: String = ""
    var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        see_message_button.setOnClickListener {
            val pass = password_edittext.text.toString()
            val mess = message.toString()

            if (pass != "") showMessage(pass, mess)
            else  toast("Password cannot be empty.")
        }

        new_password_button.setOnClickListener {
            val pass = new_password_edittext.text.toString()
            if (pass != "") saveNewPassword(pass)
            else toast("New password cannot be empty.")
        }

        change_password_button.setOnClickListener {
           goToChangePasswordActivity(password)
        }

        reset_button.setOnClickListener {
            reset()
        }
    }

    private fun init() {
        database.use {
            password = select("Valentina").column("password").whereSimple("id = 1").limit(1).parseOpt(StringParser).toString()
            message = select("Valentina").column("message").whereSimple("id = 1").limit(1).parseOpt(StringParser).toString()
        }
        database.close()

        toast(password)
        toast(message)

        if ( password != null.toString() &&  password != "") {
            hello_layout.visibility = View.VISIBLE
            new_password_layout.visibility = View.GONE
        } else {
            hello_layout.visibility = View.GONE
            new_password_layout.visibility = View.VISIBLE
        }
    }

    private fun goToChangePasswordActivity(password: String) {
        val intent = Intent(this, ChangePassword::class.java)
        intent.putExtra("password", password)
        intent.putExtra("message", message)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivityForResult(intent, 1);
    }

    private fun reset() {
        alert("Are you sure you want to delete message and password?", "Reset") {
            positiveButton("YES") {
                database.use {
                    dropTable("Valentina", true)
                    createTable("Valentina", true, "id" to INTEGER, "password" to TEXT, "message" to TEXT)
                }
                database.close()

                message = ""
                password = ""

                toast("Data has been reset successfully.")

                hello_layout.visibility = View.GONE
                new_password_layout.visibility = View.VISIBLE
            }

            negativeButton("NO") {
                toast("Okay. Nothing happened.")
            }
        }.show()
    }

    private fun saveNewPassword(newPassword: String) {
        database.use {
            dropTable("Valentina", true)
            createTable("Valentina", true, "id" to INTEGER, "password" to TEXT, "message" to TEXT)
            insert("Valentina", "password" to newPassword, "message" to "", "id" to 1)
            password = select("Valentina").column("password").whereSimple("id = 1").limit(1).parseOpt(StringParser).toString()
            message = select("Valentina").column("message").whereSimple("id = 1").limit(1).parseOpt(StringParser).toString()
        }
        database.close()

        toast("Password has been saved.")

        hello_layout.visibility = View.VISIBLE
        new_password_layout.visibility = View.GONE
        new_password_edittext.text = null
    }

    private fun showMessage(pass: String, mess: String) {
        if (pass == "") toast("Please enter your password.")
        else if (password == pass) goToMessageViewActivity(pass, mess)
        else toast("Wrong password.")
        password_edittext.text = null
    }

    fun goToMessageViewActivity(pass: String, mess: String) {
        toast("Success.")
        val intent = Intent(this, MessageView::class.java)
        intent.putExtra("password", pass)
        intent.putExtra("message", mess)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivityForResult(intent, 1);
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                password = data.getStringExtra("password")
                message = data.getStringExtra("message")
            }
        }
    }
}
