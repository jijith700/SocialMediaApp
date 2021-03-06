package com.sma.liveler.ui.followers

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
import com.sma.liveler.databinding.FragmentFollowersBinding
import com.sma.liveler.interfaces.OnClickFriendListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.FriendsAdapter
import com.sma.liveler.utils.VerticalDividerItemDecoration

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 *
 */
class FollowersFragment : Fragment(), OnClickFriendListener {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var friendsAdapter: FriendsAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: FollowersViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return FollowersViewModel(
                    activity!!, PostRepository(activity!!)
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_followers, container, false)
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendsAdapter = FriendsAdapter(this)
        binding.rvFollowers.layoutManager = GridLayoutManager(context, 1)
        binding.rvFollowers.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvFollowers.adapter = friendsAdapter

        viewModel.friends.observe(this, Observer { friendsAdapter.updateFriends(it) })

        viewModel.getFriends();

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

    }

    override fun onRemove(userId: Int) {
        viewModel.unFriend(userId)
    }
}
