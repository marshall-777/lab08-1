package com.example.servicelabapp

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MyMusicService : Service() {

    private lateinit var player: MediaPlayer
    private val CHANNEL_ID = "MusicChannel"

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.song)
        player.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Музыка играет")
            .setContentText("Фоновый сервис активен")
            .setSmallIcon(R.drawable.ic_music)
            .build()

        startForeground(1, notification)
        player.start()

        return START_STICKY
    }

    override fun onDestroy() {
        player.stop()
        player.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Music Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}