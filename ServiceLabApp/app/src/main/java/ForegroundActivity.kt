package com.example.servicelabapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ForegroundActivity : AppCompatActivity() {

    private lateinit var musicServiceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreground)

        musicServiceIntent = Intent(this, MyMusicService::class.java)
    }

    fun startMusic(view: View) {
        ContextCompat.startForegroundService(this, musicServiceIntent)
    }

    fun stopMusic(view: View) {
        stopService(musicServiceIntent)
    }

    fun back(view: View) {
        finish()
    }
}