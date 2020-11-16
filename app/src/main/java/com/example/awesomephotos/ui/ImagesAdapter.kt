package com.example.awesomephotos.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.awesomephotos.R
import com.example.awesomephotos.databinding.ItemImageBinding
import com.example.awesomephotos.datasource.models.Images
import com.example.awesomephotos.utilities.GlideApp

class ImagesAdapter(
    private val items: List<Images>, private val listener: (Int) -> Unit,
    private val longListener: (Int) -> Boolean
) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemBinding =
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(items[position], { listener(position) }, { longListener(position) })
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val view: ItemImageBinding) :
        RecyclerView.ViewHolder(view.root) {
        @SuppressLint("SetTextI18n")
        fun bindTo(
            item: Images,
            listener: View.OnClickListener,
            longListener: View.OnLongClickListener
        ) {
            view.root.setOnClickListener(listener)
            view.root.setOnLongClickListener(longListener)
            view.progressBar.visibility = View.VISIBLE
            if (item.flag) {
                view.tvTooltip.visibility = View.VISIBLE

            } else view.tvTooltip.visibility = View.GONE
            view.tvTooltip.text = item.alt_description + " ; Author: " + item.user.username
            GlideApp.with(itemView.context)
                .load(item.urls.small)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .fitCenter()
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        view.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        view.progressBar.visibility = View.GONE
                        return false
                    }
                })
                .placeholder(R.drawable.ic_dummy)
                .into(view.imageView)
        }
    }

    fun updateList(position: Int) {
        if (position < 0) {
            for (i in items.indices)
                items[i].flag = false
            notifyDataSetChanged()
            return
        }
        items[position].flag = true
        for (i in items.indices) {
            if (position != i)
                items[i].flag = false
        }
        notifyDataSetChanged()
    }

}