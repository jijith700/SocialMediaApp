package com.sma.socialmediaapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sma.socialmediaapp.R


class PageAdapter : RecyclerView.Adapter<PageAdapter.PageItemViewHolder>() {

    private lateinit var layoutPageItemBinding: com.sma.socialmediaapp.databinding.LayoutPageItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageItemViewHolder {

        layoutPageItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_page_item, parent, false);

        return PageItemViewHolder(layoutPageItemBinding.root)

    }

    override fun onBindViewHolder(holder: PageItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    class PageItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        init {

        }

    }
}