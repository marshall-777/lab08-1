package com.example.servicelabapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class RandomCharacterService : Service() {

    private var isRunning = false
    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRunning = true
        Log.d("RandomCharService", "Service started")

        Thread {
            while (isRunning) {
                try {
                    Thread.sleep(1000)
                    val randomChar = alphabet.random()
                    Log.d("RandomCharService", "Generated: $randomChar")

                    val broadcast = Intent("my.custom.action.tag.lab6")
                    broadcast.setPackage(packageName) // 🔒 делаем Broadcast безопасным
                    broadcast.putExtra("randomCharacter", randomChar)
                    sendBroadcast(broadcast)

                } catch (e: Exception) {
                    Log.e("RandomCharService", "Error in thread", e)
                }
            }
        }.start()

        return START_STICKY
    }

    override fun onDestroy() {
        isRunning = false
        Log.d("RandomCharService", "Service stopped")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
