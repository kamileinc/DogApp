package com.example.dogapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogapp.R
import com.example.dogapp.ui.DogsListFragmentDirections

class RecyclerAdapter(
    private val context: Context,
    private val urls: Array<String>,
    private val view: View,
) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recycler, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemView.setOnClickListener {
            onItemClick(i)
        }

        Glide.with(context)
            .load(urls[i])
            .placeholder(R.drawable.ic_baseline_no_photography_24)
            .override(1000,1000)
            .dontAnimate()
            .skipMemoryCache(true)
            .into(viewHolder.imageView)
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView

        init {
            imageView = view.findViewById<View>(R.id.iv_glide) as ImageView
        }
    }

    private fun onItemClick(position: Int) {
        view.let {
            val action =
                DogsListFragmentDirections.actionDogsListFragmentToDogPhotoFragment(
                    urls[position]
                )
            view.findNavController().navigate(action)
        }
    }
}
