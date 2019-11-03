package com.sma.liveler.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sma.liveler.R
import com.sma.liveler.vo.Group
import java.util.*
import kotlin.collections.ArrayList


class GroupAdapter : RecyclerView.Adapter<GroupAdapter.GroupItemViewHolder>(), Filterable {


    private lateinit var layoutGroupItemBinding: com.sma.liveler.databinding.LayoutGroupItemBinding

    private lateinit var context: Context
    private var groups: List<Group> = ArrayList<Group>();
    private var groupsFilter: List<Group> = ArrayList<Group>();


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupItemViewHolder {

        layoutGroupItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_group_item,
                parent,
                false
            );
        context = parent.context
        return GroupItemViewHolder(layoutGroupItemBinding.root)

    }

    override fun onBindViewHolder(holder: GroupItemViewHolder, position: Int) {
        Glide.with(context).load(groupsFilter[position].created_at).into(holder.ivGroup)
        holder.tvGroupName.text = groupsFilter[position].name
        holder.tvMembers.text =
            String.format(
                context.getString(R.string.tv_members),
                groupsFilter[position].membersCount
            )
    }

    override fun getItemCount(): Int {
        return groupsFilter.size
    }

    fun updateGroups(groups: List<Group>) {
        this.groups = groups
        this.groupsFilter = groups
        notifyDataSetChanged()
    }

    class GroupItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivGroup: ImageView = view.findViewById(R.id.ivGroup)
        var tvGroupName: TextView = view.findViewById(R.id.tvGroupName)
        var tvMembers: TextView = view.findViewById(R.id.tvMembers)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                if (charString.isEmpty()) {
                    groupsFilter = groups
                } else {
                    val filteredList = ArrayList<Group>()
                    for (group in groups) {

                        if (group.name.toUpperCase(Locale.getDefault()).contains(
                                charString.toUpperCase(
                                    Locale.getDefault()
                                )
                            )
                        ) {
                            filteredList.add(group)
                        }
                    }
                    groupsFilter = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = groupsFilter
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                groupsFilter = results?.values as ArrayList<Group>
                notifyDataSetChanged()
            }
        }
    }
}