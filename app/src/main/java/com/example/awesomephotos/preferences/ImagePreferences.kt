package com.example.awesomephotos.preferences

import android.content.Context
import android.util.Log
import com.example.awesomephotos.datasource.models.Images
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImagePreferences {
    companion object {
        val instance: ImagePreferences by lazy { ImagePreferences() }
        const val PREFERENCE_NAME = "user.preferences"
        const val IMAGES = "images"
    }

    fun saveImages(context: Context, images: List<Images>): Boolean {
        val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val imageString = Gson().toJson(images)
        editor.putString( IMAGES, imageString)
        editor.apply()
        return editor.commit()
    }

    fun getImages(context: Context): List<Images>? {
        val imageString = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            .getString(IMAGES, null)
        return if (imageString != null) {
            Gson().fromJson<List<Images>>(imageString, object : TypeToken<List<Images>>() {}.type)
        } else null
    }

    fun clearPreferences(context: Context) {
        val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear().apply()
    }

}