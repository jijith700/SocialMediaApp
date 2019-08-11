package com.sma.socialmediaapp.ui.videochannel


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sma.socialmediaapp.R
import com.sma.socialmediaapp.databinding.FragmentVideoChannelBinding
import com.sma.socialmediaapp.ui.adapter.VideoChannelAdapter


/**
 * A simple [Fragment] subclass.
 *
 */
class VideoChannelFragment : Fragment() {

    private lateinit var binding: FragmentVideoChannelBinding
    private lateinit var viewModel: VideoChannelViewModel
    private lateinit var videoChannelAdapter: VideoChannelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_channel, container, false)
        videoChannelAdapter = VideoChannelAdapter()
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvVideoChannel.layoutManager = LinearLayoutManager(context)
        /*binding.rvVideoChannel.addItemDecoration(VerticalDividerItemDecoration(20, false))*/
        binding.rvVideoChannel.adapter = videoChannelAdapter

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, null).get(VideoChannelViewModel::class.java)
        binding.viewModel = viewModel

    }

}
