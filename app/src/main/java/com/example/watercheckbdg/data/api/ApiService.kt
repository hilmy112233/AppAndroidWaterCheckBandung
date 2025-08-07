package com.example.watercheckbdg.data.api

import com.example.watercheckbdg.data.model.SaranaResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/bigdata/dinas_kesehatan/jmlh_srn_smbr_r_mnm_brklts_srt_mmnh_syrt_d_kt_bndng?page=1&per_page=999")
    suspend fun getSaranaData(): SaranaResponse
}
