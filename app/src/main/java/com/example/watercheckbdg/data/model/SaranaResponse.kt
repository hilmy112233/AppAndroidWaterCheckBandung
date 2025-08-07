package com.example.watercheckbdg.data.model

data class SaranaResponse(
    val code: Int,
    val message: String,
    val data: List<SaranaData>
)

data class SaranaData(
    val kemendagri_nama_kecamatan: String,
    val jumlah_sarana: Int,
    val tahun: String,
    val bps_nama_kabupaten_kota: String
)
