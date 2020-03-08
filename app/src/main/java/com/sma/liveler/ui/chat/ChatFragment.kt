package com.sma.liveler.ui.chat

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
import com.sma.liveler.databinding.FragmentChatBinding
import com.sma.liveler.interfaces.OnClickChatListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.ChatAdapter
import com.sma.liveler.ui.home.HomeActivity
import com.sma.liveler.utils.FRIEND
import com.sma.liveler.utils.Utils
import com.sma.liveler.utils.VerticalDividerItemDecoration
import com.sma.liveler.vo.ChatMessage
import com.sma.liveler.vo.Friend
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_chat.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 *
 */
class ChatFragment : Fragment(), OnClickChatListener {

    private val TAG = ChatFragment::class.java.simpleName

    private lateinit var binding: FragmentChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private var toUserId: Int? = null

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: ChatViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ChatViewModel(
                    activity!!, PostRepository(activity!!)
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val friend: Friend? = arguments?.getParcelable(FRIEND)

        toUserId = friend?.user_id

        chatAdapter = ChatAdapter(context!!, this)
        binding.rvMessage.layoutManager = GridLayoutManager(context, 1)
        binding.rvMessage.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvMessage.adapter = chatAdapter

        viewModel.getMessage(toUserId!!)

        btnSend.setOnClickListener {
            val fromUser = (activity as HomeActivity).userId
            val msg = edtMessage.text.toString()

            Timber.d("ChatFragment %d, %s", fromUser, msg)

            if (msg.isNotEmpty() && fromUser != null)
                viewModel.sendMessage(fromUser, toUserId!!, msg)
        }

        viewModel.sendUser.observe(this, Observer {

            val chatMessage = ChatMessage(
                edtMessage.text.toString(), "",
                it.role_id!!, it.id!!, "", "",
                it.firstName, it.avatar, friend?.user_id!!, ""
            )
            edtMessage.setText("")
            chatAdapter.updateChatMessage(chatMessage)
            Utils.hideKeyboard(activity!!, edtMessage)
            Timber.e(TAG, chatMessage)
        })

        viewModel.receiverUser.observe(this, Observer {
            chatAdapter.updateUserId(it?.role_id!!)
        })

        viewModel.chatMessages.observe(this, Observer {
            if (it != null) {
                chatAdapter.updateChatMessage(ArrayList<ChatMessage>(it))
            } else {
                Timber.e(TAG, "empty message")
            }
        })

        Picasso.get().load(friend?.profile_picture)
            .placeholder(R.drawable.ic_user_avtar)
            .into(ivUser)
        tvUserName.text = friend?.name
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onClickSend(userId: Int) {

    }
}
