package com.example.laundry.ModelData

data class ModelPelanggan(
    var idPelanggan: String? = null,
    var nama: String? = null,
    var alamat: String? = null,
    var noHP: String? = null,
    var cabang: String? = null,
    var terdaftar: Long? = null // Tambahkan ini
)
