package com.example.watercheckbdg.presentation.ui.form

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.watercheckbdg.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormLaporanActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etLokasi: EditText
    private lateinit var spKategori: Spinner
    private lateinit var etDeskripsi: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnBack: Button

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance() // Tambahkan ini

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_laporan)

        etNama = findViewById(R.id.etNama)
        etLokasi = findViewById(R.id.etLokasi)
        spKategori = findViewById(R.id.spKategori)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        val kategoriList = arrayOf("Air Bau", "Air Berwarna", "Air Keruh", "Lainnya")
        spKategori.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, kategoriList)

        btnSubmit.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val lokasi = etLokasi.text.toString().trim()
            val deskripsi = etDeskripsi.text.toString().trim()
            val kategori = spKategori.selectedItem.toString()

            if (nama.isEmpty() || lokasi.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentTime = System.currentTimeMillis()
            val currentFormatted = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                .format(Date(currentTime))

            val user = auth.currentUser
            val uid = user?.uid ?: "unknown"
            val email = user?.email ?: "Tidak diketahui"

            val laporan = hashMapOf(
                "nama" to nama,
                "lokasi" to lokasi,
                "kategori" to kategori,
                "deskripsi" to deskripsi,
                "timestamp" to currentTime,
                "waktu" to currentFormatted,
                "uid" to uid,
                "email" to email
            )

            firestore.collection("laporan")
                .add(laporan)

            firestore.collection("users")
                .document(uid)
                .collection("laporan")
                .add(laporan)

            Toast.makeText(this, "Laporan berhasil dikirim", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
