package com.sma.socialmediaapp.ui.timeline


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
import com.sma.socialmediaapp.databinding.FragmentTimeLineBinding
import com.sma.socialmediaapp.ui.adapter.TimelineAdapter
import com.sma.socialmediaapp.utils.VerticalDividerItemDecoration


/**
 * A simple [Fragment] subclass.
 *
 */
class TimelineFragment : Fragment() {

    private lateinit var binding: FragmentTimeLineBinding
    private lateinit var viewModel: TimelineViewModel
    private lateinit var timelineAdapter: TimelineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_line, container, false)
        timelineAdapter = TimelineAdapter()
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTimeline.layoutManager = LinearLayoutManager(context)
        binding.rvTimeline.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvTimeline.adapter = timelineAdapter

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, null).get(TimelineViewModel::class.java)
        binding.viewModel = viewModel

    }
}
