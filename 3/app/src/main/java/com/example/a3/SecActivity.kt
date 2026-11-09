package com.example.a3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sec)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvReceivedMessage = findViewById<TextView>(R.id.tvReceivedMessage)
        val etReplyMessage = findViewById<EditText>(R.id.etReplyMessage)
        val btnReturn = findViewById<Button>(R.id.btnReturn)

        // Get text passed from MainActivity
        val receivedText = intent.getStringExtra("MESSAGE") ?: "This is the text string from the page one"
        tvReceivedMessage.text = "從第一頁收到的文字:\n$receivedText"

        btnReturn.setOnClickListener {
            val replyText = etReplyMessage.text.toString().ifEmpty { "Hello I have received your message" }
            val resultIntent = Intent().apply {
                putExtra("REPLY", replyText)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
