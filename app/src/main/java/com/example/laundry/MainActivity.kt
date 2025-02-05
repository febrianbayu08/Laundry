package com.example.laundry

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Menyesuaikan padding sistem agar UI tetap terlihat baik
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil referensi TextView untuk salam dan tanggal
        val greetingTextView: TextView = findViewById(R.id.tv_greeting)
        val dateTextView: TextView = findViewById(R.id.tv_date)

        // Ambil waktu saat ini
        val currentTime = LocalDateTime.now()
        val hour = currentTime.hour

        // Tentukan salam berdasarkan waktu
        val greeting = when (hour) {
            in 5..11 -> "Selamat Pagi"
            in 12..15 -> "Selamat Siang"
            in 16..18 -> "Selamat Sore"
            else -> "Selamat Malam"
        }

        // Format tanggal dengan pola "dd MMMM yyyy"
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val formattedDate = currentTime.format(formatter)

        // Nama pengguna (dapat diubah sesuai kebutuhan)
        val userName = "Febrian"

        // Atur teks pada TextView
        greetingTextView.text = "$greeting, $userName"
        dateTextView.text = formattedDate
    }
}
