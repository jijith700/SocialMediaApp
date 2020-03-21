package com.sma.liveler.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.sma.liveler.R
import com.sma.liveler.databinding.LayoutFriendRequestItemBinding
import com.sma.liveler.interfaces.OnClickFriendListener
import com.sma.liveler.vo.BirthDay
import com.sma.liveler.vo.Friend
import com.squareup.picasso.Picasso
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import de.hdodenhof.circleimageview.CircleImageView


class BirthDayAdapter(
    private var context: Context,
    private var onClickFriendListener: OnClickFriendListener,
    groups: ArrayList<BirthDay>
) :
    ExpandableRecyclerViewAdapter<BirthDayAdapter.MonthViewHolder, BirthDayAdapter.FriendItemViewHolder>(
        groups
    ) {

    private lateinit var layoutFriendItemBinding: LayoutFriendRequestItemBinding

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): MonthViewHolder {
        val view: View = View.inflate(context, R.layout.layout_month_item, null)
        return MonthViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): FriendItemViewHolder {
        val view: View = View.inflate(context, R.layout.layout_birthday_item, null)
        return FriendItemViewHolder(view)
    }

    override fun onBindChildViewHolder(
        holder: FriendItemViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?,
        childIndex: Int
    ) {

        val friend = group!!.items[childIndex] as Friend

        Picasso.get().load(friend.profile_picture)
            .placeholder(R.drawable.ic_user_avtar)
            .into(holder?.ivUser!!)

        holder.tvUserName?.text = friend.name
        holder.tvDetails?.text = friend.date_of_birth

        holder.btnAccept?.setOnClickListener {
            onClickFriendListener.onAdd(friend.user_first_id)
        }

        holder.btnDecline?.setOnClickListener {
            onClickFriendListener.onRemove(friend.user_first_id)
        }
    }

    override fun onBindGroupViewHolder(
        holder: MonthViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {
        holder?.tvMonth?.text = group?.title
    }

    class MonthViewHolder(itemView: View?) : GroupViewHolder(itemView) {
        var tvMonth: TextView?

        init {
            tvMonth = itemView?.findViewById(R.id.tvMonth)
        }
    }

    class FriendItemViewHolder(view: View?) : ChildViewHolder(view) {
        var ivUser: CircleImageView?
        var tvUserName: TextView?
        var tvDetails: TextView?
        var btnAccept: Button?
        var btnDecline: Button?

        init {
            ivUser = view?.findViewById(R.id.ivUser)
            tvUserName = view?.findViewById(R.id.tvUserName)
            tvDetails = view?.findViewById(R.id.tvUserDetails)
            btnAccept = view?.findViewById(R.id.btnAccept)
            btnDecline = view?.findViewById(R.id.btnDecline)
        }
    }
}