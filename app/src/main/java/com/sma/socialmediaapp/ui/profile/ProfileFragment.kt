package com.sma.socialmediaapp.ui.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sma.socialmediaapp.R
import com.sma.socialmediaapp.ui.about.AboutFragment
import com.sma.socialmediaapp.ui.adapter.TabAdapter
import com.sma.socialmediaapp.ui.friends.FriendsFragment
import com.sma.socialmediaapp.ui.timeline.TimelineFragment

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var tabAdapter: TabAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabAdapter = TabAdapter(fragmentManager!!)
        tabAdapter.addFragment(TimelineFragment(), getString(R.string.tab_timeline))
        tabAdapter.addFragment(AboutFragment(), getString(R.string.tab_about))
        tabAdapter.addFragment(FriendsFragment(), getString(R.string.tab_friends))

        binding.vpProfile.adapter = tabAdapter
        binding.tbProfile.setupWithViewPager(binding.vpProfile)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, null).get(ProfileViewModel::class.java)
        binding.viewModel = viewModel

    }

}
