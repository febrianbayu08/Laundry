package com.example.laundry.Pelanggan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.R
import com.example.laundry.adapter.AdapterDataPelanggan
import com.example.laundry.ModelData.ModelPelanggan
import com.example.laundry.Pelanggan.TambahPelanggan
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DataPelanggan : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("pelanggan")

    private lateinit var rvdatapelanggan: RecyclerView
    private lateinit var fabTambahPelanggan: FloatingActionButton
    private lateinit var adapter: AdapterDataPelanggan
    private var listPelanggan = arrayListOf<ModelPelanggan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_pelanggan_activity)

        enableEdgeToEdge()

        initViews()
        setupRecyclerView()
        setupListeners()
        getData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.data_pelanggan)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        rvdatapelanggan = findViewById(R.id.rvDATA_PELANGGAN)
        fabTambahPelanggan = findViewById(R.id.fabDATA_PENGGUNA_Tambah)
    }

    private fun setupRecyclerView() {
        adapter = AdapterDataPelanggan(listPelanggan)
        rvdatapelanggan.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        rvdatapelanggan.setHasFixedSize(true)
        rvdatapelanggan.adapter = adapter
    }

    private fun setupListeners() {
        fabTambahPelanggan.setOnClickListener {
            startActivity(Intent(this, TambahPelanggan::class.java))
        }
    }

    private fun getData() {
        val query = myRef.orderByChild("terdaftar").limitToLast(100) // Urutkan berdasarkan waktu pendaftaran
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listPelanggan.clear()
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val pelanggan = dataSnapshot.getValue(ModelPelanggan::class.java)
                        pelanggan?.let { listPelanggan.add(it) }
                    }
                    listPelanggan.reverse() // Pastikan data terbaru muncul di atas
                    adapter.notifyDataSetChanged() // Perbarui data tanpa membuat adapter baru
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DataPelanggan, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
