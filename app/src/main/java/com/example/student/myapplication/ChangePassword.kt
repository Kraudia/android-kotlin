package com.example.student.myapplication

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.change_password.*
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

        back_from_change_button.setOnClickListener {
            finish()
        }

    }
}
