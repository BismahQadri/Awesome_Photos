package com.example.awesomephotos.datasource.remote.images

import com.example.awesomephotos.datasource.models.Images
import com.example.awesomephotos.datasource.remote.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

class ImageService {
    companion object{
         val instance: ImageService by lazy { ImageService() }
    }

     fun images(callback: Callback<List<Images>>) {
        val service = ApiClient.retrofit.create(ImageApiInterface::class.java)
         val call = service.images()
         call.enqueue(callback)
    }
}

interface ImageApiInterface{
    @GET("/photos")
     fun images(
        @Query("per_page") perPages: Long = 30
    ): Call<List<Images>>

}