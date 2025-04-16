package com.example.laundry

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.content.Intent
import com.example.laundry.Pegawai.DataPegawai
import com.example.laundry.Pelanggan.DataPelanggan


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Menghubungkan tampilan dengan ID yang sesuai di XML
        val pelangganMenu = findViewById<LinearLayout>(R.id.llpelanggan)
        val pegawaiMenu = findViewById<LinearLayout>(R.id.llpegawai)
        val greetingTextView: TextView = findViewById(R.id.tv_greeting)
        val dateTextView: TextView = findViewById(R.id.tv_date)

        // Mengatur klik event untuk membuka activity lain
        pelangganMenu.setOnClickListener {
            startActivity(Intent(this, DataPelanggan::class.java))
        }

        pegawaiMenu.setOnClickListener {
            startActivity(Intent(this, DataPegawai::class.java))
        }

        // Menyesuaikan padding sistem agar UI tetap terlihat baik
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mendapatkan waktu saat ini
        val currentTime = LocalDateTime.now()
        val hour = currentTime.hour

        // Menentukan salam berdasarkan waktu
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

        // Menampilkan salam dan tanggal di UI
        greetingTextView.text = "$greeting, $userName"
        dateTextView.text = formattedDate
    }
}