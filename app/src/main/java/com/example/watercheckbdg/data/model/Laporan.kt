package com.example.watercheckbdg.data.model

data class Laporan(
    var id: String? = null,
    var nama: String = "",
    var kategori: String = "",
    var lokasi: String = "",
    var deskripsi: String = "",
    var waktu: String = "",
    var timestamp: Long = 0L
)