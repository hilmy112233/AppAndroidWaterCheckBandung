package com.example.watercheckbdg.utils

import android.content.Context

object AssetUtils {
    fun loadGeoJsonFromAssets(context: Context): String {
        return context.assets.open("kecamatan.geojson").bufferedReader().use { it.readText() }
    }
}
