package com.example.laundry.Pelanggan

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.laundry.ModelData.ModelPelanggan
import com.example.laundry.R
import com.google.firebase.database.FirebaseDatabase

class TambahPelanggan : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("pelanggan")

    private lateinit var tvJudul: TextView
    private lateinit var etNama: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etNoHP: EditText
    private lateinit var etCabang: EditText
    private lateinit var btSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tambahpelanggan)

        // Terapkan window insets (pastikan root layout memiliki id "main")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi view
        init()

        // Atur aksi pada tombol simpan
        btSimpan.setOnClickListener {
            cekValidasi()
        }
    }

    private fun init() {
        tvJudul = findViewById(R.id.tvtambahkan_pelanggan)
        etNama = findViewById(R.id.etnama_lengkap)
        etAlamat = findViewById(R.id.etalamat)
        etNoHP = findViewById(R.id.etNoHp)
        etCabang = findViewById(R.id.etCabang)
        btSimpan = findViewById(R.id.btn_simpan)
    }

    private fun cekValidasi() {
        val nama = etNama.text.toString().trim()
        val alamat = etAlamat.text.toString().trim()
        val noHP = etNoHP.text.toString().trim()
        val cabang = etCabang.text.toString().trim()

        // Validasi data
        if (nama.isEmpty()) {
            etNama.error = getString(R.string.validasi_nama_pelanggan)
            Toast.makeText(this, getString(R.string.validasi_nama_pelanggan), Toast.LENGTH_SHORT).show()
            etNama.requestFocus()
            return
        }
        if (alamat.isEmpty()) {
            etAlamat.error = getString(R.string.validasi_alamat_pelanggan)
            Toast.makeText(this, getString(R.string.validasi_alamat_pelanggan), Toast.LENGTH_SHORT).show()
            etAlamat.requestFocus()
            return
        }
        // Validasi tambahan jika diperlukan (noHP dan cabang)
        if (noHP.isEmpty()) {
            etNoHP.error = getString(R.string.validasi_noHP_pelanggan)
            Toast.makeText(this, getString(R.string.validasi_noHP_pelanggan), Toast.LENGTH_SHORT).show()
            etNoHP.requestFocus()
            return
        }
        if (cabang.isEmpty()) {
            etCabang.error = getString(R.string.validasi_cabang)
            Toast.makeText(this, getString(R.string.validasi_cabang), Toast.LENGTH_SHORT).show()
            etCabang.requestFocus()
            return
        }

        // Jika semua validasi terpenuhi, simpan data
        simpanData(nama, alamat, noHP, cabang)
    }

    private fun simpanData(nama: String, alamat: String, noHP: String, cabang: String) {
        // Membuat key unik untuk data baru
        val key = myRef.push().key
        if (key != null) {
            val pelanggan = hashMapOf(
                "idPelanggan" to key,
                "nama" to nama,
                "alamat" to alamat,
                "noHP" to noHP,
                "cabang" to cabang
            )
            myRef.child(key).setValue(pelanggan)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data pelanggan berhasil disimpan", Toast.LENGTH_SHORT).show()
                    finish() // Tutup activity setelah penyimpanan berhasil
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Gagal mendapatkan key untuk data", Toast.LENGTH_SHORT).show()
        }
    }

    fun simpan() {
        val pelangganBaru = myRef.push()
        val pelangganId = pelangganBaru.key
        // Gunakan current time sebagai timestamp, misalnya dalam milidetik
        val timestamp = System.currentTimeMillis()

        // Pastikan ModelPelanggan memiliki konstruktor yang menerima parameter sesuai urutan ini
        val data = ModelPelanggan(
            pelangganId.toString(),
            etNama.text.toString(),
            etAlamat.text.toString(),
            etNoHP.text.toString(),
            etCabang.text.toString(),

        )

        pelangganBaru.setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this, getString(R.string.sukses_simpan_pelanggan), Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
            }
    }

}