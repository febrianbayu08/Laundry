package com.example.laundry.pelanggan

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

// Penamaan kelas sebaiknya menggunakan PascalCase
class DataPelanggan : AppCompatActivity() {

    // Inisialisasi Firebase
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("pelanggan")

    private lateinit var rvdatapelanggan: RecyclerView
    private lateinit var fab_tambah_pelanggan: FloatingActionButton
    private var listPelanggan = arrayListOf<ModelPelanggan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_pelanggan_activity)

        // Enable edge-to-edge untuk tampilan modern
        enableEdgeToEdge()

        initViews()
        setupRecyclerView()
        setupListeners()
        getData()

        // Atur padding agar tidak terhalang oleh system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.data_pelanggan)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        rvdatapelanggan = findViewById(R.id.rvdatapelanggan)
        fab_tambah_pelanggan = findViewById(R.id.fab_tambah_pelanggan)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        rvdatapelanggan.layoutManager = layoutManager
        rvdatapelanggan.setHasFixedSize(true)
    }

    private fun setupListeners() {
        fab_tambah_pelanggan.setOnClickListener {
            // Pastikan kelas activity untuk tambah pelanggan sudah terdaftar di AndroidManifest.xml
            val intent = Intent(this, TambahPelanggan::class.java)
            startActivity(intent)
        }
    }

    private fun getData() {
        // Ambil data berdasarkan idPelanggan dan batasi 100 data terakhir
        val query = myRef.orderByChild("idPelanggan").limitToLast(100)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listPelanggan.clear()
                if (snapshot.exists()){
                    for (dataSnapshot in snapshot.children) {
                        val pelanggan = dataSnapshot.getValue(ModelPelanggan::class.java)
                        pelanggan?.let { listPelanggan.add(it) }
                    }
                    // Perbarui adapter RecyclerView
                    val adapter = AdapterDataPelanggan(listPelanggan)
                    rvdatapelanggan.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DataPelanggan, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
