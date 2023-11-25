package com.example.cineteca2.Service;

import com.example.cineteca2.Model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmeService {
    @GET("/")
    Call<ApiResponse> RecuperaFilmePorTitulo(@Query("q") String titulo);
}
