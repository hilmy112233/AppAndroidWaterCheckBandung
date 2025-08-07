package com.example.watercheckbdg.presentation.ui.report

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watercheckbdg.R
import com.example.watercheckbdg.data.model.Laporan
import com.example.watercheckbdg.adapter.LaporanAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DaftarLaporanActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private lateinit var btnBack: Button
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_laporan)

        recyclerView = findViewById(R.id.recyclerViewLaporan)
        recyclerView.layoutManager = LinearLayoutManager(this)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        val uid = auth.currentUser?.uid

        if (uid == null) {
            Toast.makeText(this, "User belum login", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        db.collection("users")
            .document(uid)
            .collection("laporan")
            .get()
            .addOnSuccessListener { result ->
                val laporanList = result.map {
                    val laporan = it.toObject(Laporan::class.java)
                    laporan.id = it.id
                    laporan
                }.toMutableList()

                recyclerView.adapter = LaporanAdapter(laporanList) { laporan ->
                    db.collection("users")
                        .document(uid)
                        .collection("laporan")
                        .document(laporan.id ?: return@LaporanAdapter)
                        .delete()
                        .addOnSuccessListener {
                            laporanList.remove(laporan)
                            recyclerView.adapter?.notifyDataSetChanged()
                            Toast.makeText(this, "Laporan dihapus", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Gagal menghapus laporan", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal memuat laporan", Toast.LENGTH_SHORT).show()
            }
    }
}
