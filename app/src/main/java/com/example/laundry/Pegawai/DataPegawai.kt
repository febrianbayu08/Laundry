package com.example.laundry.Pegawai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.laundry.R

class DataPegawai : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_data_pegawai) // Pastikan layout sudah dipanggil

        // Cek apakah View dengan ID "main" ada sebelum digunakan
        val mainView = findViewById<android.view.View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        } else {
            // Log error jika "main" tidak ditemukan
            android.util.Log.e("DataPegawai", "View dengan ID 'main' tidak ditemukan!")
        }
    }
}