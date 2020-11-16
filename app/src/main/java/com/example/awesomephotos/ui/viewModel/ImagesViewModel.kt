package com.example.awesomephotos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.awesomephotos.datasource.models.Images
import com.example.awesomephotos.datasource.remote.NetworkState
import com.example.awesomephotos.datasource.remote.images.ImageService
import com.example.awesomephotos.utilities.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImagesViewModel : ViewModel() {
    private val _imageState = MutableLiveData<Event<NetworkState<List<Images>>>>()
    val imageState: LiveData<Event<NetworkState<List<Images>>>> = _imageState


    fun images() {
        _imageState.value = Event(NetworkState.Loading())
            ImageService.instance.images(object : Callback<List<Images>>{
                override fun onResponse(
                    call: Call<List<Images>>,
                    response: Response<List<Images>>
                ) {
                    _imageState.value =
                        Event(NetworkState.Success(response.body()))
                }

                override fun onFailure(call: Call<List<Images>>, t: Throwable) {
                    _imageState.value = Event(
                        NetworkState.Failure(
                            t.toString()
                        )
                    )
                }
            })
    }
}