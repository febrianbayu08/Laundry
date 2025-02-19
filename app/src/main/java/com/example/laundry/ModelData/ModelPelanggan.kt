package com.example.laundry.ModelData

data class ModelPelanggan(
    val idPelanggan: String? = null,
    val nama: String? = null,
    val alamat: String? = null,
    val noHP: String? = null,
    val cabang: String? = null,
    val terdaftar: String? = null,
    val timestamp: Long? = null // Tambahkan field timestamp
)