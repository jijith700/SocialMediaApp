package com.sma.liveler.ui.videochannel


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
import androidx.recyclerview.widget.LinearLayoutManager
import com.sma.liveler.R
import com.sma.liveler.databinding.FragmentVideoChannelBinding
import com.sma.liveler.interfaces.OnClickPostListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.VideoChannelAdapter
import com.sma.liveler.utils.USER_ID
import com.sma.liveler.utils.Utils
import kotlinx.android.synthetic.main.fragment_video_channel.*


/**
 * A simple [Fragment] subclass.
 *
 */
class VideoChannelFragment : Fragment(), OnClickPostListener {

    private lateinit var binding: FragmentVideoChannelBinding
    private lateinit var videoChannelAdapter: VideoChannelAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: VideoChannelViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return VideoChannelViewModel(activity!!, PostRepository(activity!!)) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_channel, container, false)
        videoChannelAdapter = VideoChannelAdapter(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvVideoChannel.layoutManager = LinearLayoutManager(context)
        /*binding.rvVideoChannel.addItemDecoration(VerticalDividerItemDecoration(20, false))*/
        binding.rvVideoChannel.adapter = videoChannelAdapter

        viewModel.videoPosts.observe(this, Observer {
            if (it.isEmpty()) {
                pbLoading.visibility = View.GONE
                tvNoVideo.visibility = View.VISIBLE
            }
            videoChannelAdapter.updatePosts(it)
        })

        viewModel.getVideoPost()
        pbLoading.visibility = View.VISIBLE

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onClickLike(postId: Int) {
        viewModel.likePost(postId, Utils.loadPreferenceInt(activity!!, USER_ID));
    }
}
