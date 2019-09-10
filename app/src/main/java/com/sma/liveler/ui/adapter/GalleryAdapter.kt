package com.sma.liveler.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sma.liveler.R
import com.sma.liveler.databinding.LayoutGalleryItemBinding


class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.GalleryItemViewHolder>() {

    private lateinit var layoutVideoItemBinding: LayoutGalleryItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemViewHolder {

        layoutVideoItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_gallery_item, parent, false);

        return GalleryItemViewHolder(layoutVideoItemBinding.root)

    }

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }

    class GalleryItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        init {

        }

    }
}