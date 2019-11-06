package com.sma.liveler.ui.myvideos


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
import androidx.recyclerview.widget.LinearLayoutManager
import com.sma.liveler.R
import com.sma.liveler.databinding.FragmentMyVideoBinding
import com.sma.liveler.ui.adapter.MyVideoAdapter


/**
 * A simple [Fragment] subclass.
 *
 */
class MyVideoFragment : Fragment() {

    private lateinit var binding: FragmentMyVideoBinding
    private lateinit var myVideoAdapter: MyVideoAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: MyVideoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MyVideoViewModel() as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_video, container, false)
        myVideoAdapter = MyVideoAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvVideoChannel.layoutManager = LinearLayoutManager(context)
        /*binding.rvVideoChannel.addItemDecoration(VerticalDividerItemDecoration(20, false))*/
        binding.rvVideoChannel.adapter = myVideoAdapter

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

    }
}
