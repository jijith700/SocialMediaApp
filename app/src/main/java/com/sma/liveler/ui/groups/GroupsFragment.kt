package com.sma.liveler.ui.groups


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sma.liveler.R
import com.sma.liveler.repository.NavigationRepository
import com.sma.liveler.ui.adapter.GroupAdapter
import com.sma.liveler.utils.VerticalDividerItemDecoration

/**
 * A simple [Fragment] subclass.
 *
 */
class GroupsFragment : Fragment() {

    private lateinit var binding: com.sma.liveler.databinding.FragmentGroupsBinding
    private lateinit var groupAdapter: GroupAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: GroupsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return GroupsViewModel(
                    activity!!,
                    NavigationRepository(activity!!)
                ) as T
            }
        }
    }

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
        binding.rvGroups.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvGroups.adapter = groupAdapter

        viewModel.groups.observe(this, Observer {
            groupAdapter.updateGroups(it)
        })

        viewModel.getGroups()

        binding.edtSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*Timber.d("String %s start %d before %d count %d", s, start, before, count)*/
            }

            override fun afterTextChanged(s: Editable?) {
                groupAdapter.filter.filter(s.toString())
            }
        })
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }
}
