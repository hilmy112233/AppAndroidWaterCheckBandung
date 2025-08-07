package com.example.watercheckbdg.presentation.components

import android.view.View
import android.widget.TextView
import com.example.watercheckbdg.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.infowindow.InfoWindow

class CustomInfoWindow(mapView: MapView) :
    InfoWindow(R.layout.infowindow_kecamatan, mapView) {

    override fun onOpen(item: Any?) {
        val polygon = item as? Polygon ?: return
        val view: View = mView

        val tvKecamatan = view.findViewById<TextView>(R.id.tvKecamatan)
        val tvJumlah = view.findViewById<TextView>(R.id.tvJumlah)
        val tvTahun = view.findViewById<TextView>(R.id.tvTahun)
        val tvKota = view.findViewById<TextView>(R.id.tvKota)

        val parts = polygon.title?.split("||") ?: listOf("-", "-", "-")
        val kabupatenKota = parts.getOrNull(0) ?: "-"
        val namaKecamatan = parts.getOrNull(1) ?: "-"
        val tahun = parts.getOrNull(2) ?: "-"
        val jumlah = polygon.subDescription ?: "-"

        tvKota.text = "Kabupaten/Kota : Kota Bandung"
        tvKecamatan.text = "Kecamatan : $namaKecamatan"
        tvTahun.text = "Tahun : $tahun"
        tvJumlah.text = "Jumlah Sarana : $jumlah"
    }


    override fun onClose() {
    }
}