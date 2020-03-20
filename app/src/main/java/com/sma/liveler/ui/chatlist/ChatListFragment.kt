package com.sma.liveler.ui.chatlist

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
import com.sma.liveler.databinding.FragmentChatListBinding
import com.sma.liveler.interfaces.OnClickFriendListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.ChatListAdapter
import com.sma.liveler.ui.chat.ChatFragment
import com.sma.liveler.ui.home.HomeActivity
import com.sma.liveler.utils.FRIEND
import com.sma.liveler.utils.VerticalDividerItemDecoration
import com.sma.liveler.vo.Friend
import kotlinx.android.synthetic.main.fragment_chat_list.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 *
 */
class ChatListFragment : Fragment(), OnClickFriendListener {

    private lateinit var binding: FragmentChatListBinding
    private lateinit var chatListAdapter: ChatListAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: ChatListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ChatListViewModel(
                    activity!!, PostRepository(activity!!)
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_list, container, false)
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatListAdapter = ChatListAdapter(this)
        binding.rvFollowers.layoutManager = GridLayoutManager(context, 1)
        binding.rvFollowers.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvFollowers.adapter = chatListAdapter

        viewModel.friends.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                tvNoChat.visibility = View.VISIBLE
            } else {
                tvNoChat.visibility = View.GONE
            }
            layoutLoading.visibility = View.GONE
            chatListAdapter.updateFriends(it)
        })

        layoutLoading.visibility = View.VISIBLE
        tvNoChat.visibility = View.GONE
        viewModel.getFriends();
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onRemove(userId: Int) {
    }

    override fun onClick(friend: Friend) {
        val bundle = Bundle()
        bundle.putParcelable(FRIEND, friend)
        val chatFragment = ChatFragment()
        chatFragment.arguments = bundle
        (activity as HomeActivity).switchPage(chatFragment, true)
    }
}
