package com.example.lovecalculator.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface LoveApi {

    @GET("getPercentage")
    fun getPercentage(
        @Query("fname") firstName: String,
        @Query("sname") secondName: String,
        @Header("X-RapidAPI-Key") key:String = "3d200025ecmsh39f9a0e7eddb700p1d8b34jsnde980f3df1a8",
        @Header("X-RapidAPI-Host") host:String = "love-calculator.p.rapidapi.com",
    ) : Call<LoveModel>

}