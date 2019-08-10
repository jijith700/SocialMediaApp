package com.sma.socialmediaapp.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sma.socialmediaapp.R
import com.sma.socialmediaapp.ui.adapter.FriendsAdapter

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 *
 */
class FriendsFragment : Fragment() {

    private lateinit var binding: FragmentFriendsBinding
    private lateinit var viewModel: FriendsViewModel
    private lateinit var friendsAdapter: FriendsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friends, container, false)
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendsAdapter = FriendsAdapter()
        binding.rvFriends.layoutManager = GridLayoutManager(context, 4)
        /*val itemDecorator = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.bg_divider)!!)
        binding.rvFriends.addItemDecoration(itemDecorator)*/
        binding.rvFriends.adapter = friendsAdapter

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, null).get(FriendsViewModel::class.java)
        binding.viewModel = viewModel

    }
}
