package com.example.watercheckbdg.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.watercheckbdg.R
import com.example.watercheckbdg.data.model.Laporan
import com.example.watercheckbdg.presentation.ui.detail.DetailLaporanActivity

class LaporanAdapter(
    private val list: MutableList<Laporan>,
    private val onDeleteClick: (Laporan) -> Unit
) : RecyclerView.Adapter<LaporanAdapter.LaporanViewHolder>() {

    inner class LaporanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvKategori: TextView = itemView.findViewById(R.id.tvKategori)
        val tvLokasi: TextView = itemView.findViewById(R.id.tvLokasi)
        val tvWaktu: TextView = itemView.findViewById(R.id.tvWaktu)
        val btnDelete: TextView = itemView.findViewById(R.id.btnDelete)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val context = itemView.context
                    val laporan = list[position]

                    val intent = Intent(context, DetailLaporanActivity::class.java).apply {
                        putExtra("nama", laporan.nama)
                        putExtra("kategori", laporan.kategori)
                        putExtra("lokasi", laporan.lokasi)
                        putExtra("deskripsi", laporan.deskripsi)
                        putExtra("waktu", laporan.waktu)
                    }
                    context.startActivity(intent)
                }
            }

            btnDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val laporan = list[position]
                    val context = itemView.context

                    androidx.appcompat.app.AlertDialog.Builder(context)
                        .setTitle("Konfirmasi Hapus")
                        .setMessage("Apakah Anda yakin ingin menghapus laporan ini?")
                        .setPositiveButton("Hapus") { _, _ ->
                            onDeleteClick(laporan)
                        }
                        .setNegativeButton("Batal", null)
                        .show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_laporan, parent, false)
        return LaporanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaporanViewHolder, position: Int) {
        val laporan = list[position]
        holder.tvNama.text = laporan.nama
        holder.tvKategori.text = "Kategori: ${laporan.kategori}"
        holder.tvLokasi.text = "Lokasi: ${laporan.lokasi}"
        holder.tvWaktu.text = laporan.waktu
    }

    override fun getItemCount() = list.size
}