package com.example.watercheckbdg.presentation.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.watercheckbdg.R
import com.example.watercheckbdg.data.api.ApiClient
import com.example.watercheckbdg.presentation.ui.form.FormLaporanActivity
import com.example.watercheckbdg.presentation.ui.report.DaftarLaporanActivity
import com.example.watercheckbdg.presentation.components.CustomInfoWindow
import com.example.watercheckbdg.presentation.ui.auth.LoginActivity
import com.example.watercheckbdg.utils.AssetUtils
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var spinnerTahun: Spinner
    private lateinit var btnFormLaporan: Button
    private lateinit var btnDaftarLaporan: Button
    private lateinit var btnLogout: Button

    private var lastOpenedPolygon: Polygon? = null
    private var selectedTahun: String = "2015"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        Configuration.getInstance().load(applicationContext, getSharedPreferences("osm_pref", MODE_PRIVATE))
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        spinnerTahun = findViewById(R.id.spinnerTahun)
        btnFormLaporan = findViewById(R.id.btnFormLaporan)
        btnDaftarLaporan = findViewById(R.id.btnDaftarLaporan)
        btnLogout = findViewById(R.id.btnLogout)

        mapView.setMultiTouchControls(true)
        mapView.setBuiltInZoomControls(true)

        setupSpinner()
        setupButtonActions()
        centerToBandung()
        checkLocationPermission()

        mapView.setOnTouchListener { _, _ ->
            lastOpenedPolygon?.closeInfoWindow()
            lastOpenedPolygon = null
            false
        }
    }

    private fun setupSpinner() {
        val tahunList = listOf("2015", "2016", "2017", "2018", "2019")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tahunList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTahun.adapter = adapter

        spinnerTahun.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedTahun = tahunList[position]
                loadAndDisplayPolygons()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupButtonActions() {
        btnFormLaporan.setOnClickListener {
            startActivity(Intent(this, FormLaporanActivity::class.java))
        }

        btnDaftarLaporan.setOnClickListener {
            startActivity(Intent(this, DaftarLaporanActivity::class.java))
        }
        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya") { dialog, _ ->
                    FirebaseAuth.getInstance().signOut()
                    Toast.makeText(this, "Berhasil logout", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun centerToBandung() {
        val startPoint = GeoPoint(-6.9175, 107.6191)
        mapView.controller.setZoom(12.0)
        mapView.controller.setCenter(startPoint)

        val marker = Marker(mapView).apply {
            position = startPoint
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            title = "Bandung"
        }
        mapView.overlays.add(marker)
    }

    private fun loadAndDisplayPolygons() {
        lifecycleScope.launch {
            try {
                mapView.overlays.removeAll { it is Polygon }

                val response = ApiClient.apiService.getSaranaData()
                val filteredData = response.data.filter { it.tahun == selectedTahun }
                val dataMap = filteredData.associateBy { it.kemendagri_nama_kecamatan.uppercase() }

                val geoJsonString = AssetUtils.loadGeoJsonFromAssets(this@MainActivity)
                val geoJson = JSONObject(geoJsonString)
                val features = geoJson.getJSONArray("features")

                for (i in 0 until features.length()) {
                    val feature = features.getJSONObject(i)
                    val geometry = feature.getJSONObject("geometry")
                    val type = geometry.getString("type")

                    if (type == "Polygon") {
                        val coordsArray = geometry.getJSONArray("coordinates").getJSONArray(0)
                        val points = mutableListOf<GeoPoint>()
                        for (j in 0 until coordsArray.length()) {
                            val coord = coordsArray.getJSONArray(j)
                            val lon = coord.getDouble(0)
                            val lat = coord.getDouble(1)
                            points.add(GeoPoint(lat, lon))
                        }

                        val properties = feature.getJSONObject("properties")
                        val namaKecamatan = properties.optString("nama_kecamatan", "").uppercase()
                        val kabupatenKota = properties.optString("bps_nama_kabupaten_kota", "-")
                        val apiData = dataMap[namaKecamatan]
                        val jumlah = apiData?.jumlah_sarana ?: 0
                        val tahun = apiData?.tahun ?: "-"

                        val fillColor = when (jumlah) {
                            in 0..522 -> Color.parseColor("#ffffff")
                            in 523..3251 -> Color.parseColor("#ccffcc")
                            in 3252..5722 -> Color.parseColor("#99ff99")
                            in 5723..8346 -> Color.parseColor("#66cc66")
                            in 8347..10899 -> Color.parseColor("#33cc33")
                            else -> Color.parseColor("#009900")
                        }

                        val polygon = Polygon().apply {
                            this.points = points
                            this.fillColor = fillColor
                            this.strokeColor = Color.BLACK
                            this.strokeWidth = 2f
                            this.title = "$kabupatenKota||$namaKecamatan||$tahun"
                            this.subDescription = jumlah.toString()
                            this.infoWindow = CustomInfoWindow(mapView)

                            setOnClickListener { p, _, _ ->
                                if (p is Polygon) {
                                    if (lastOpenedPolygon == p && p.infoWindow?.isOpen == true) {
                                        p.closeInfoWindow()
                                        lastOpenedPolygon = null
                                    } else {
                                        mapView.overlays.filterIsInstance<Polygon>().forEach { it.closeInfoWindow() }
                                        p.showInfoWindow()
                                        lastOpenedPolygon = p
                                    }
                                }
                                true
                            }
                        }

                        mapView.overlays.add(polygon)
                    }
                }

                mapView.invalidate()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun checkLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}