package com.example.basicfirebase

import android.app.Application
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Offline
        Firebase.database.setPersistenceEnabled(true)
    }
}
