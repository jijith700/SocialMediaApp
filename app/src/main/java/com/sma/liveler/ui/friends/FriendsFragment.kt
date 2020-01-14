package com.sma.liveler.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sma.liveler.R
import com.sma.liveler.databinding.FragmentFriendsBinding
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.VideoTabAdapter
import com.sma.liveler.ui.followers.FollowersFragment
import com.sma.liveler.ui.following.FollowingFragment
import kotlinx.android.synthetic.main.fragment_friends.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 *
 */
class FriendsFragment : Fragment() {

    private lateinit var binding: FragmentFriendsBinding

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: FriendsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return FriendsViewModel(
                    activity!!, PostRepository(activity!!)
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friends, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTabs()

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

    }

    /**
     * Method to add the tab to the view pager.
     */
    private fun addTabs() {
        val categoryPagerAdapter = VideoTabAdapter(childFragmentManager)
        categoryPagerAdapter.addFragment(FollowersFragment(), "Followers")
        categoryPagerAdapter.addFragment(FollowingFragment(), "Following")

        vpFriends.adapter = categoryPagerAdapter
        tlFriends.setupWithViewPager(vpFriends)
    }
}
