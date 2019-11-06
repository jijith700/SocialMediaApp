package com.sma.liveler.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sma.liveler.R
import com.sma.liveler.databinding.LayoutMyVideoItemBinding


class MyVideoAdapter : RecyclerView.Adapter<MyVideoAdapter.VideoItemViewHolder>() {

    private lateinit var layoutVideoItemBinding: LayoutMyVideoItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {

        layoutVideoItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_my_video_item,
                parent,
                false
            );

        return VideoItemViewHolder(layoutVideoItemBinding.root)

    }

    override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }

    class VideoItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        init {

        }

    }
}