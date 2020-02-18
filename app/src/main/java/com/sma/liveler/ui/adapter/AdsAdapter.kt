package com.sma.liveler.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sma.liveler.R
import com.sma.liveler.databinding.LayoutAdItemBinding
import com.sma.liveler.vo.Ad
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class AdsAdapter() : RecyclerView.Adapter<AdsAdapter.AdItemViewHolder>() {

    private lateinit var layoutAdItemBinding: LayoutAdItemBinding

    private lateinit var context: Context
    private var ads: List<Ad> = ArrayList<Ad>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdItemViewHolder {

        layoutAdItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_ad_item,
                parent,
                false
            )

        return AdItemViewHolder(layoutAdItemBinding.root)

    }

    override fun onBindViewHolder(holder: AdItemViewHolder, position: Int) {

        Picasso.get().load(ads[position].thumbnail).into(holder.ivAd)
        holder.tvAdTitle?.text = "" + ads[position].name
        holder.tvTime?.text = "" + ads[position].created_at
        holder.tvStatus?.text = "" + ads[position].status

        holder.btnMakePayment?.setOnClickListener { }

    }

    override fun getItemCount(): Int {
        return ads.size
    }

    class AdItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var ivAd: CircleImageView?
        var tvAdTitle: TextView?
        var tvTime: TextView?
        var tvStatus: TextView?
        var btnMakePayment: Button?

        init {
            ivAd = view?.findViewById(R.id.ivAd)
            tvAdTitle = view?.findViewById(R.id.tvAdTitle)
            tvTime = view?.findViewById(R.id.tvTime)
            tvStatus = view?.findViewById(R.id.tvStatus)
            btnMakePayment = view?.findViewById(R.id.btnMakePayment)
        }
    }

    fun updateAds(ads: List<Ad>) {
        this.ads = ads
        notifyDataSetChanged()
    }
}