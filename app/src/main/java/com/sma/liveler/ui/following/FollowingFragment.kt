package com.sma.liveler.ui.following

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
import com.sma.liveler.databinding.FragmentFollowingBinding
import com.sma.liveler.interfaces.OnClickFriendListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.FollowingAdapter
import com.sma.liveler.utils.VerticalDividerItemDecoration

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 *
 */
class FollowingFragment : Fragment(), OnClickFriendListener {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var friendsAdapter: FollowingAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: FollowingViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return FollowingViewModel(
                    activity!!, PostRepository(activity!!)
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false)
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendsAdapter = FollowingAdapter(this)
        binding.rvFollowing.layoutManager = GridLayoutManager(context, 1)
        binding.rvFollowing.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvFollowing.adapter = friendsAdapter

        viewModel.friendsRequest.observe(this, Observer { friendsAdapter.updateFollowing(it) })

        viewModel.getFollowing()

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

    }

    override fun onRemove(userId: Int) {
        viewModel.acceptFriendRequest(userId)
    }

    override fun onAdd(userId: Int) {
        viewModel.cancelFriendRequest(userId)
    }
}
