package com.example.watercheckbdg.presentation.ui.detail

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.watercheckbdg.R

class DetailLaporanActivity : AppCompatActivity() {

    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_laporan)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        val tvNama = findViewById<TextView>(R.id.tvDetailNama)
        val tvKategori = findViewById<TextView>(R.id.tvDetailKategori)
        val tvLokasi = findViewById<TextView>(R.id.tvDetailLokasi)
        val tvDeskripsi = findViewById<TextView>(R.id.tvDetailDeskripsi)
        val tvWaktu = findViewById<TextView>(R.id.tvDetailWaktu)

        val nama = intent.getStringExtra("nama")
        val kategori = intent.getStringExtra("kategori")
        val lokasi = intent.getStringExtra("lokasi")
        val deskripsi = intent.getStringExtra("deskripsi")
        val waktu = intent.getStringExtra("waktu")

        tvNama.text = "Nama : $nama"
        tvKategori.text = "Kategori : $kategori"
        tvLokasi.text = "Lokasi : $lokasi"
        tvDeskripsi.text = "Deskripsi : $deskripsi"
        tvWaktu.text = waktu
    }
}