package com.sma.liveler.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
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
import java.util.*
import kotlin.collections.ArrayList


class AllUsersAdapter(private var onClickAllUsersListener: OnAllUsersListener) :
    RecyclerView.Adapter<AllUsersAdapter.AllUsersViewHolder>(), Filterable {

    private lateinit var layoutFriendItemBinding: LayoutAllUserItemBinding
    private lateinit var context: Context

    private var allUsers: ArrayList<AllUsers> = ArrayList<AllUsers>()
    private var allUsersFiltered: ArrayList<AllUsers> = ArrayList<AllUsers>()


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

        Picasso.get().load(allUsersFiltered[position].profile_picture)
            .placeholder(R.drawable.ic_user_avtar)
            .into(holder.ivUser!!)

        holder.tvUserName?.text = allUsersFiltered[position].name
        holder.tvDetails?.text = allUsersFiltered[position].email
        if (allUsersFiltered[position].type == TYPE_FRIENDS) {
            holder.btnAddFriend?.visibility = View.GONE
        } else if (allUsersFiltered[position].type == TYPE_PENDING) {
            holder.btnAddFriend?.visibility = View.VISIBLE
            holder.btnAddFriend?.text = "Pending"
            holder.btnAddFriend?.isEnabled = false
        } else {
            holder.btnAddFriend?.visibility = View.VISIBLE
        }

        holder.btnAddFriend?.setOnClickListener {
            onClickAllUsersListener.onClickAddFriend(allUsersFiltered[position].id)
        }

        holder.itemView.setOnClickListener {
            onClickAllUsersListener.onClickItem(allUsersFiltered[position])
        }
    }

    override fun getItemCount(): Int {
        return allUsersFiltered.size;
    }

    class AllUsersViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        var ivUser: CircleImageView?
        var tvUserName: TextView?
        var tvDetails: TextView?
        var btnAddFriend: Button?

        init {
            ivUser = view?.findViewById(R.id.ivUser)
            tvUserName = view?.findViewById(R.id.tvUserName)
            tvDetails = view?.findViewById(R.id.tvUserDetails)
            btnAddFriend = view?.findViewById(R.id.btnAddFriend)
        }
    }

    fun updateAllUsers(allUsers: List<AllUsers>) {
        this.allUsers = ArrayList<AllUsers>(allUsers)
        this.allUsersFiltered = ArrayList<AllUsers>(allUsers)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString: String = charSequence.toString()
                if (charString.isEmpty()) {
                    allUsersFiltered = allUsers
                } else {
                    val filteredList: MutableList<AllUsers> = ArrayList()
                    for (row in allUsers) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name.toLowerCase(Locale.getDefault())
                                .contains(charString.toLowerCase(Locale.getDefault()))
                        ) {
                            filteredList.add(row)
                        }
                    }
                    allUsersFiltered = filteredList as ArrayList<AllUsers>
                }

                val filterResults = FilterResults()
                filterResults.values = allUsersFiltered
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                allUsersFiltered = filterResults?.values as ArrayList<AllUsers>
                notifyDataSetChanged()
            }
        }
    }
}