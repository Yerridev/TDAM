package com.example.yerripc1.Interface;

import com.example.yerripc1.Models.RptaObtenerUsuarios;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIYerri {
    @GET("api_obtenerUsuarios")
    Call<RptaObtenerUsuarios> obtenerUsuarios();
}
