package com.example.student.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    var password: String? = null
    var message: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        see_message_button.setOnClickListener {
            showMessage()
        }

        new_password_button.setOnClickListener {
            saveNewPassword()
        }

        reset_button.setOnClickListener {
            reset()
        }
    }

    private fun init() {
        database.use {
            password = select("ValentinaPassword").column("password").whereSimple("id = 1").limit(1).parseOpt(StringParser).toString()
            message = select("ValentinaMessage").column("message").whereSimple("id = 1").limit(1).parseOpt(StringParser).toString()
        }
        database.close()

//        toast(password.toString())
//        toast(message.toString())

        if (password.toString() != null.toString() && password.toString() != "") {
            hello_layout.visibility = View.VISIBLE
            new_password_layout.visibility = View.GONE
        } else {
            hello_layout.visibility = View.GONE
            new_password_layout.visibility = View.VISIBLE
        }
    }

    private fun reset() {
        alert("Are you sure you want to delete message and password?", "Reset") {
            positiveButton("YES") {
                database.use {
                    dropTable("ValentinaPassword", true)
                    createTable("ValentinaPassword", true, "id" to INTEGER, "password" to TEXT)

                    dropTable("ValentinaMessage", true)
                    createTable("ValentinaMessage", true, "id" to INTEGER, "message" to TEXT)
                }
                database.close()

                message = ""
                password = ""

                toast("Data has been reset successfully.")

                hello_layout.visibility = View.GONE
                new_password_edittext.text = null
                new_password_layout.visibility = View.VISIBLE
            }
            negativeButton("NO") {
                toast("Okay. Nothing happened.")
            }
        }.show()
    }

    private fun saveNewPassword() {
        database.use {
            dropTable("ValentinaPassword", true)
            createTable("ValentinaPassword", true, "id" to INTEGER, "password" to TEXT)
            password = new_password_edittext.text.toString()
            insert("ValentinaPassword", "password" to password, "id" to 1)
            toast("Zapisano nowe hasło.")
        }
        database.close()

        hello_layout.visibility = View.VISIBLE
        new_password_layout.visibility = View.GONE
        new_password_edittext.text = null
    }

    private fun showMessage() {
        if (password_edittext.text.toString() != "" && password_edittext.text.toString() == password.toString()) {
            toast("Success.")
        } else {
            toast("Wrong password.")
        }
        password_edittext.text = null
    }
}
