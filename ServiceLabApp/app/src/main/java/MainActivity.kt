package com.example.servicelabapp

import android.content.*
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var characterEditText: EditText
    private lateinit var serviceIntent: Intent

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val char = intent?.getCharExtra("randomCharacter", '?') ?: '?'
            Log.d("MainActivity", "Broadcast получен: $char")
            runOnUiThread {
                characterEditText.setText(char.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterEditText = findViewById(R.id.editText_random)
        serviceIntent = Intent(this, RandomCharacterService::class.java)
    }

    fun startService(view: View) {
        Log.d("MainActivity", "Кнопка СТАРТ нажата")
        startService(serviceIntent)
    }

    fun stopService(view: View) {
        stopService(serviceIntent)
        characterEditText.setText("")
    }

    fun goToForeground(view: View) {
        startActivity(Intent(this, ForegroundActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter("my.custom.action.tag.lab6")
        if (Build.VERSION.SDK_INT >= 33) {
            registerReceiver(receiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("DEPRECATION")
            registerReceiver(receiver, filter)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}
