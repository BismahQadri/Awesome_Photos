package com.example.awesomephotos.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.awesomephotos.R
import com.example.awesomephotos.databinding.ActivityImagesBinding
import com.example.awesomephotos.datasource.models.Images
import com.example.awesomephotos.datasource.remote.NetworkState
import com.example.awesomephotos.preferences.ImagePreferences
import com.example.awesomephotos.ui.viewModel.ImagesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ImagesActivity : AppCompatActivity() {

    private val binding: ActivityImagesBinding by lazy {
        ActivityImagesBinding.inflate(layoutInflater)
    }
    private val viewModel: ImagesViewModel by lazy {
        ViewModelProvider(this).get(ImagesViewModel::class.java)
    }
    private lateinit var adapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpRecyclerView(ArrayList())
        init()
    }

    private fun init() {
        addObserver()
        viewModel.images()
        binding.swipeRefresh.setOnRefreshListener {
            addObserver()
            viewModel.images()
        }
    }

    private fun addObserver() {
        viewModel.imageState.observe(this, Observer {
            it ?: return@Observer
            val state = it.getContentIfNotHandled() ?: return@Observer

            if (state is NetworkState.Loading) {
                return@Observer
            }
            binding.swipeRefresh.isRefreshing = false
            when (state) {
                is NetworkState.Success -> {
                    val data = state.data ?: return@Observer
                    ImagePreferences.instance.saveImages(this, data)
                    setUpRecyclerView(data)

                }
                is NetworkState.Failure -> {
                    onFailure(state.error, binding.root)
                    if (ImagePreferences.instance.getImages(this) != null)
                        setUpRecyclerView(
                            ImagePreferences.instance.getImages(this) ?: return@Observer
                        )

                }
                else -> {
                    onFailure("The internet connection appears to be offline.", binding.root)
                    if (ImagePreferences.instance.getImages(this) != null)
                        setUpRecyclerView(
                            ImagePreferences.instance.getImages(this) ?: return@Observer
                        )
                }
            }
        })
    }

    private fun setUpRecyclerView(data: List<Images>) {
        adapter = ImagesAdapter(data, { index ->
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse(data[index].links.download)
            )
            startActivity(viewIntent)
        }, { index ->
            adapter.updateList(index)
            MainScope().launch {
                delay(2000)
                adapter.updateList(-1)
            }

            return@ImagesAdapter true
        })
        binding.recyclerView.adapter = adapter
    }

    private fun onFailure(message: String, view: View) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorError))
        val textView =
            snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorOnSecondary))
        snackBar.show()
    }
}