package com.example.laundry.Pelanggan

import android.os.Bundle
import android.util.Log
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
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("pelanggan")
    lateinit var tvJudul: TextView
    lateinit var etNama: EditText
    lateinit var etAlamat: EditText
    lateinit var etNoHP: EditText
    lateinit var etCabang: EditText
    lateinit var btSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tambahpelanggan)
        init()

        btSimpan.setOnClickListener {
            cekValidasi()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Pelanggan)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun init() {
        Log.d("DEBUG", "init() dijalankan")
        tvJudul = findViewById(R.id.tvtambahkan_pelanggan)
        etNama = findViewById(R.id.etnama_lengkap)
        etAlamat = findViewById(R.id.etalamat)
        etNoHP = findViewById(R.id.etNoHp)
        etCabang = findViewById(R.id.etCabang)
        btSimpan = findViewById(R.id.btn_simpan)
    }

    fun cekValidasi() {
        Toast.makeText(this, "Tombol diklik", Toast.LENGTH_SHORT).show()

        val nama = etNama.text.toString()
        val alamat = etAlamat.text.toString()
        val noHp = etNoHP.text.toString()
        val cabang = etCabang.text.toString()

        // Validasi Nama
        if (nama.isEmpty()) {
            etNama.error = this.getString(R.string.validasi_nama_pelanggan)
            Toast.makeText(this, this.getString(R.string.validasi_nama_pelanggan), Toast.LENGTH_SHORT).show()
            etNama.requestFocus()
            return
        }

        // Validasi Alamat
        if (alamat.isEmpty()) {
            etAlamat.error = this.getString(R.string.validasi_alamat_pelanggan)
            Toast.makeText(this, this.getString(R.string.validasi_alamat_pelanggan), Toast.LENGTH_SHORT).show()
            etAlamat.requestFocus()
            return
        }

        // Validasi No HP
        if (noHp.isEmpty()) {
            etNoHP.error = this.getString(R.string.validasi_noHP_pelanggan)
            Toast.makeText(this, this.getString(R.string.validasi_noHP_pelanggan), Toast.LENGTH_SHORT).show()
            etNoHP.requestFocus()
            return
        }

        // Validasi Cabang
        if (cabang.isEmpty()) {
            etCabang.error = this.getString(R.string.validasi_cabang)
            Toast.makeText(this, this.getString(R.string.validasi_cabang), Toast.LENGTH_SHORT).show()
            etCabang.requestFocus()
            return
        }

        // Jika semua validasi berhasil, simpan data
        Log.d("DEBUG", "Validasi berhasil, memanggil simpan()")
        simpan()
    }

    fun simpan() {
        Log.d("DEBUG", "Fungsi simpan() dipanggil")

        val pelangganBaru = myRef.push()
        val pelangganid = pelangganBaru.key
        val data = ModelPelanggan(
            pelangganid.toString(),
            etNama.text.toString(),
            etAlamat.text.toString(),
            etNoHP.text.toString(),
            etCabang.text.toString(),
            System.currentTimeMillis() // Ini dia timestamp-nya
        )

        // Simpan data ke Firebase
        pelangganBaru.setValue(data)
            .addOnSuccessListener {
                Log.d("DEBUG", "Data berhasil disimpan ke Firebase")
                // Berhasil simpan, tampilkan Toast dan kembali
                Toast.makeText(
                    this,
                    this.getString(R.string.sukses_simpan_pelanggan),
                    Toast.LENGTH_SHORT
                ).show()
                finish() // Kembali ke activity sebelumnya
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseUpload", "Gagal menyimpan data: ${e.message}")
                Toast.makeText(this, getString(R.string.tambah_pelanggan_gagal), Toast.LENGTH_SHORT).show()
            }
    }}