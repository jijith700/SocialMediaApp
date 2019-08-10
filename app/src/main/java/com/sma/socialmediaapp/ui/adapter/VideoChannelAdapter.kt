package com.sma.socialmediaapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sma.socialmediaapp.R


class VideoChannelAdapter : RecyclerView.Adapter<VideoChannelAdapter.VideoItemViewHolder>() {

    private lateinit var layoutVideoItemBinding: LayoutVideoItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {

        layoutVideoItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_video_item, parent, false);

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