package com.sma.liveler.ui.timeline


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
import com.sma.liveler.databinding.FragmentTimeLineBinding
import com.sma.liveler.interfaces.OnClickTimelineListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.TimelineAdapter
import com.sma.liveler.utils.VerticalDividerItemDecoration


/**
 * A simple [Fragment] subclass.
 *
 */
class TimelineFragment : Fragment(), OnClickTimelineListener {

    private lateinit var binding: FragmentTimeLineBinding
    private lateinit var timelineAdapter: TimelineAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: TimelineViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TimelineViewModel(
                    activity!!,
                    PostRepository(activity!!)
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_line, container, false)
        timelineAdapter = TimelineAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTimeline.layoutManager = LinearLayoutManager(context)
        binding.rvTimeline.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvTimeline.adapter = timelineAdapter

        viewModel.posts.observe(this, Observer {
            timelineAdapter.updatePosts(it)
        })

        viewModel.getPosts()
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onClickLike(postId: Int) {
        viewModel.likePosts(postId)
    }
}
