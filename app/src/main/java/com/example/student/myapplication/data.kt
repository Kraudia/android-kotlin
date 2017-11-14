package com.example.student.myapplication

import android.app.Application

class MyData : Application() {
    public var password: String = ""
        get() = this.toString()
        set

    public var message: String = ""
        get() = this.toString()
        set
}