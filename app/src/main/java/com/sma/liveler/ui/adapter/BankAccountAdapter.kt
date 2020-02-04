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
import com.sma.liveler.databinding.LayoutFriendItemBinding
import com.sma.liveler.vo.Friend
import de.hdodenhof.circleimageview.CircleImageView


class BankAccountAdapter() :
    RecyclerView.Adapter<BankAccountAdapter.BankAccountItemViewHolder>() {

    private lateinit var layoutFriendItemBinding: LayoutFriendItemBinding

    private var friends: List<Friend> = ArrayList<Friend>()
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankAccountItemViewHolder {

        layoutFriendItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_bank_account_item,
                parent,
                false
            )

        context = layoutFriendItemBinding.root.context

        return BankAccountItemViewHolder(layoutFriendItemBinding.root)

    }

    override fun onBindViewHolder(holder: BankAccountItemViewHolder, position: Int) {

        /*Picasso.get().load(friends[position].profile_picture)
            .placeholder(R.drawable.ic_user_avtar)
            .into(holder.ivUser!!)*/

        holder.tvBankName?.text = friends[position].name
        holder.tvAccountNumber?.text = friends[position].email
        holder.tvName?.text = friends[position].email
        holder.tvIfsc?.text = friends[position].email

        /*holder.btnRemove?.setOnClickListener({
            onClickFriendListener.onRemove(friends[position].user_id)
        })*/
    }

    override fun getItemCount(): Int {
        return friends.size;
    }

    class BankAccountItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        var ivUser: CircleImageView?
        var btnRemove: Button?
        var tvBankName: TextView?
        var tvAccountNumber: TextView?
        var tvName: TextView?
        var tvIfsc: TextView?

        init {
            ivUser = view?.findViewById(R.id.ivUser)
            btnRemove = view?.findViewById(R.id.btnRemove)
            tvBankName = view?.findViewById(R.id.tvBankName)
            tvAccountNumber = view?.findViewById(R.id.tvAccountNumber)
            tvName = view?.findViewById(R.id.tvName)
            tvIfsc = view?.findViewById(R.id.tvIfsc)
        }
    }

    fun updateBankDetails(friends: List<Friend>) {
        this.friends = friends
        notifyDataSetChanged()
    }
}