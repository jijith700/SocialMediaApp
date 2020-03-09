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
import com.sma.liveler.databinding.LayoutAllUserItemBinding
import com.sma.liveler.interfaces.OnAllUsersListener
import com.sma.liveler.utils.TYPE_FRIENDS
import com.sma.liveler.utils.TYPE_PENDING
import com.sma.liveler.vo.AllUsers
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class AllUsersAdapter(private var onClickAllUsersListener: OnAllUsersListener) :
    RecyclerView.Adapter<AllUsersAdapter.AllUsersViewHolder>() {

    private lateinit var layoutFriendItemBinding: LayoutAllUserItemBinding

    private var allUsers: List<AllUsers> = ArrayList<AllUsers>()
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllUsersViewHolder {

        layoutFriendItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_all_user_item,
                parent,
                false
            )

        context = layoutFriendItemBinding.root.context

        return AllUsersViewHolder(layoutFriendItemBinding.root)

    }

    override fun onBindViewHolder(holder: AllUsersViewHolder, position: Int) {

        Picasso.get().load(allUsers[position].profile_picture)
            .placeholder(R.drawable.ic_user_avtar)
            .into(holder.ivUser!!)

        holder.tvUserName?.text = allUsers[position].name
        holder.tvDetails?.text = allUsers[position].email
        if (allUsers[position].type == TYPE_FRIENDS) {
            holder.btnAddFriend?.visibility = View.GONE
        } else if (allUsers[position].type == TYPE_PENDING) {
            holder.btnAddFriend?.visibility = View.VISIBLE
            holder.btnAddFriend?.text = "Pending"
            holder.btnAddFriend?.isEnabled = false
        } else {
            holder.btnAddFriend?.visibility = View.VISIBLE
        }

        holder.btnAddFriend?.setOnClickListener {
            onClickAllUsersListener.onClickAddFriend(allUsers[position].id)
        }

        holder.itemView.setOnClickListener {
            onClickAllUsersListener.onClickItem(allUsers[position])
        }
    }

    override fun getItemCount(): Int {
        return allUsers.size;
    }

    class AllUsersViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        //        var ivCover : ImageView?
        var ivUser: CircleImageView?
        var tvUserName: TextView?
        var tvDetails: TextView?
        var btnAddFriend: Button?

        init {
//            ivCover = view?.findViewById(R.id.ivCover)
            ivUser = view?.findViewById(R.id.ivUser)
            tvUserName = view?.findViewById(R.id.tvUserName)
            tvDetails = view?.findViewById(R.id.tvUserDetails)
            btnAddFriend = view?.findViewById(R.id.btnAddFriend)
        }
    }

    fun updateAllUsers(allUsers: List<AllUsers>) {
        this.allUsers = allUsers
        notifyDataSetChanged()
    }
}