package com.sma.socialmediaapp.ui.gallery


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
import com.sma.socialmediaapp.databinding.FragmentGalleryBinding
import com.sma.socialmediaapp.ui.adapter.GalleryAdapter
import com.sma.socialmediaapp.utils.VerticalDividerItemDecoration

/**
 * A simple [Fragment] subclass.
 *
 */
class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var viewModel: GalleryViewModel
    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
        galleryAdapter = GalleryAdapter()
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvGallery.layoutManager = LinearLayoutManager(context)
        binding.rvGallery.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvGallery.adapter = galleryAdapter

    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, null).get(GalleryViewModel::class.java)
        binding.viewModel = viewModel

    }

}
