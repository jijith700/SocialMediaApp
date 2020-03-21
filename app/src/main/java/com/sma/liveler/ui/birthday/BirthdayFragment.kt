package com.sma.liveler.ui.birthday

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
import com.sma.liveler.databinding.FragmentBirthdayBinding
import com.sma.liveler.interfaces.OnClickFriendListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.BirthDayAdapter
import com.sma.liveler.utils.VerticalDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_following.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 *
 */
class BirthdayFragment : Fragment(), OnClickFriendListener {

    private lateinit var binding: FragmentBirthdayBinding
    private lateinit var bithDayAdapter: BirthDayAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: BirthdayViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return BirthdayViewModel(
                    activity!!, PostRepository(activity!!)
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_birthday, container, false)
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val birthDayList = viewModel.getBirthDay()
        bithDayAdapter = BirthDayAdapter(activity!!, this, birthDayList)
        binding.rvFollowing.layoutManager = GridLayoutManager(context, 1)
        binding.rvFollowing.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvFollowing.adapter = bithDayAdapter

        viewModel.friends.observe(this, Observer {
            layoutLoading.visibility = View.GONE
            if (!it.isNullOrEmpty()) {
                viewModel.getBirthDay(birthDayList, it)
                bithDayAdapter.notifyDataSetChanged()
            }
        })

        layoutLoading.visibility = View.VISIBLE
        tvNoFollowing.visibility = View.GONE
        viewModel.getFriends()
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onRemove(userId: Int) {
        viewModel.acceptFriendRequest(userId)
    }

    override fun onAdd(userId: Int) {
        viewModel.cancelFriendRequest(userId)
    }
}
