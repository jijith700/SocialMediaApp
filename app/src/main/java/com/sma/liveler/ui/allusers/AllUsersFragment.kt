package com.sma.liveler.ui.allusers

import android.os.Bundle
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
import androidx.recyclerview.widget.GridLayoutManager
import com.sma.liveler.R
import com.sma.liveler.databinding.FragmentAllUsersBinding
import com.sma.liveler.interfaces.OnAllUsersListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.AllUsersAdapter
import com.sma.liveler.utils.VerticalDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_all_users.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 *
 */
class AllUsersFragment : Fragment(), OnAllUsersListener {

    private lateinit var binding: FragmentAllUsersBinding
    private var allUsersAdapter: AllUsersAdapter? = null

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: AllUsersViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AllUsersViewModel(
                    activity!!, PostRepository(activity!!)
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_users, container, false)
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allUsersAdapter = AllUsersAdapter(this)
        binding.rvFollowers.layoutManager = GridLayoutManager(context, 1)
        binding.rvFollowers.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvFollowers.adapter = allUsersAdapter

        viewModel.allUsers.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                tvNoUsers.visibility = View.VISIBLE
            } else {
                tvNoUsers.visibility = View.GONE
            }
            layoutLoading.visibility = View.GONE
            allUsersAdapter?.updateAllUsers(it)
        })

        layoutLoading.visibility = View.VISIBLE
        tvNoUsers.visibility = View.GONE
        viewModel.getAllUsers();
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onClickAddFriend(userId: Int) {
        viewModel.addFriend(userId)
    }

    fun search(keyword: String) {
        allUsersAdapter?.filter?.filter(keyword)
    }
}
