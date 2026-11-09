package com.example.a3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvReturnedMessage: TextView

    private val startSecActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val reply = result.data?.getStringExtra("REPLY") ?: "Hello I have received your message"
            tvReturnedMessage.text = "從第二頁得到的回收文字:\n$reply"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvInitialMessage = findViewById<TextView>(R.id.tvInitialMessage)
        val btnSwitch = findViewById<Button>(R.id.btnSwitch)
        tvReturnedMessage = findViewById<TextView>(R.id.tvReturnedMessage)

        btnSwitch.setOnClickListener {
            val message = tvInitialMessage.text.toString()
            val intent = Intent(this, SecActivity::class.java).apply {
                putExtra("MESSAGE", message)
            }
            startSecActivityLauncher.launch(intent)
        }
    }
}
