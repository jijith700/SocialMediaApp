package com.sma.liveler.ui.videos


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
import com.sma.liveler.databinding.FragmentVideoBinding
import com.sma.liveler.ui.adapter.VideoTabAdapter
import com.sma.liveler.ui.myvideos.MyVideoFragment
import com.sma.liveler.ui.videochannel.VideoChannelFragment
import kotlinx.android.synthetic.main.fragment_video.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 *
 */
class VideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoBinding

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: VideoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return VideoViewModel(
                    activity!!
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false)
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
        Timber.i("Fragment_Name: %s", VideoFragment::class.java.simpleName)
        val categoryPagerAdapter = VideoTabAdapter(childFragmentManager)
        categoryPagerAdapter.addFragment(VideoChannelFragment(), "Videos")
        categoryPagerAdapter.addFragment(MyVideoFragment(), "My Videos")

        vpVideos.adapter = categoryPagerAdapter
        tlVideo.setupWithViewPager(vpVideos)
    }
}
