package com.sma.liveler.ui.adrequest


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
import com.sma.liveler.databinding.FragmentAdRequestBinding
import com.sma.liveler.ui.adapter.VideoTabAdapter
import com.sma.liveler.ui.myads.MyAdFragment
import com.sma.liveler.ui.request.RequestFragment
import kotlinx.android.synthetic.main.fragment_video.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 *
 */
class AdRequestFragment : Fragment() {

    private lateinit var binding: FragmentAdRequestBinding

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: AdRequestViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AdRequestViewModel(
                    activity!!
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ad_request, container, false)
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
        Timber.i("Fragment_Name: %s", AdRequestFragment::class.java.simpleName)
        val categoryPagerAdapter = VideoTabAdapter(childFragmentManager)
        categoryPagerAdapter.addFragment(RequestFragment(), "Request")
        categoryPagerAdapter.addFragment(MyAdFragment(), "My Ads")

        vpVideos.adapter = categoryPagerAdapter
        tlVideo.setupWithViewPager(vpVideos)
    }
}
