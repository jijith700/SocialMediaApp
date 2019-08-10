package com.sma.socialmediaapp.ui.groups


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sma.socialmediaapp.R
import com.sma.socialmediaapp.ui.adapter.GroupAdapter

/**
 * A simple [Fragment] subclass.
 *
 */
class GroupsFragment : Fragment() {

    private lateinit var binding: com.sma.socialmediaapp.databinding.FragmentGroupsBinding
    private lateinit var viewModel: GroupsViewModel
    private lateinit var groupAdapter: GroupAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_groups, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        groupAdapter = GroupAdapter()
        binding.rvGroups.layoutManager = LinearLayoutManager(context)
        val itemDecorator = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.bg_divider)!!)
        binding.rvGroups.addItemDecoration(itemDecorator)
        binding.rvGroups.adapter = groupAdapter

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, null).get(GroupsViewModel::class.java)
        binding.viewModel = viewModel
    }
}
