package com.sma.liveler.ui.notification

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
import com.sma.liveler.databinding.FragmentNotificationBinding
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.NotificationAdapter
import com.sma.liveler.utils.VerticalDividerItemDecoration

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 *
 */
class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private lateinit var notificationAdapter: NotificationAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: NotificationViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return NotificationViewModel(
                    activity!!, PostRepository(activity!!)
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationAdapter = NotificationAdapter()
        binding.rvNotification.layoutManager = GridLayoutManager(context, 1)
        binding.rvNotification.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvNotification.adapter = notificationAdapter

        viewModel.friends.observe(this, Observer { notificationAdapter.updateFriends(it) })

        viewModel.getNotifications();

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }
}
