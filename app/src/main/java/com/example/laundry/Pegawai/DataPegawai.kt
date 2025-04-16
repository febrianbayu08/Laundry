package com.example.laundry.Pegawai

import android.annotation.SuppressLint
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
import com.example.laundry.adapter.AdapterDataPegawai
import com.example.laundry.ModelData.ModelPegawai
import com.example.laundry.Pegawai.TambahPegawai
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// Penamaan kelas sebaiknya menggunakan PascalCase
class DataPegawai : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("pegawai")

    private lateinit var rvdatapegawai: RecyclerView
    private lateinit var fabTambahPegawai: FloatingActionButton
    private lateinit var adapter: AdapterDataPegawai
    private var listPegawai = arrayListOf<ModelPegawai>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_pegawai_activity)

        // Enable edge-to-edge untuk tampilan modern
        enableEdgeToEdge()

        initViews()
        setupRecyclerView()
        setupListeners()
        getData()

        // Atur padding agar tidak terhalang oleh system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.data_pegawai)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        rvdatapegawai = findViewById(R.id.rvDATA_PEGAWAI)
        fabTambahPegawai = findViewById(R.id.fabDATA_PEGAWAI_Tambah)
    }

    private fun setupRecyclerView() {
        adapter = AdapterDataPegawai(listPegawai)
        rvdatapegawai.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        rvdatapegawai.setHasFixedSize(true)
        rvdatapegawai.adapter = adapter
    }

    private fun setupListeners() {
        fabTambahPegawai.setOnClickListener {
            startActivity(Intent(this, TambahPegawai::class.java))
        }
    }

    private fun getData() {
        val query = myRef.orderByChild("terdaftar").limitToLast(100) // Urutkan berdasarkan waktu pendaftaran
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listPegawai.clear()
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val pegawai = dataSnapshot.getValue(ModelPegawai::class.java)
                        pegawai?.let { listPegawai.add(it) }
                    }
                    listPegawai.reverse() // Pastikan data terbaru muncul di atas
                    adapter.notifyDataSetChanged() // Perbarui data tanpa membuat adapter baru
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DataPegawai, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
