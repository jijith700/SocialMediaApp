package com.sma.liveler.ui.pages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sma.liveler.R
import com.sma.liveler.ui.adapter.PageAdapter
import com.sma.liveler.utils.VerticalDividerItemDecoration

/**
 * A simple [Fragment] subclass.
 *
 */
class PagesFragment : Fragment() {

    private lateinit var binding: com.sma.liveler.databinding.FragmentPagesBinding
    private lateinit var viewModel: PagesViewModel
    private lateinit var pageAdapter: PageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pages, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageAdapter = PageAdapter()
        binding.rvPages.layoutManager = LinearLayoutManager(context)
        binding.rvPages.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvPages.adapter = pageAdapter

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, null).get(PagesViewModel::class.java)
        binding.viewModel = viewModel
    }

}
