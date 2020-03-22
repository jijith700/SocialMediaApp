package com.sma.liveler.ui.about


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
import com.sma.liveler.R
import com.sma.liveler.databinding.FragmentAboutBinding
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.home.HomeActivity
import com.sma.liveler.ui.profile.EditProfileFragment
import com.sma.liveler.utils.PROFILE
import com.sma.liveler.vo.PersonalInfo
import kotlinx.android.synthetic.main.fragment_about.*


/**
 * A simple [Fragment] subclass.
 *
 */
class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    private lateinit var personalInfo: PersonalInfo

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: AboutViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AboutViewModel(
                    activity!!, PostRepository(activity!!)
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        // setting values to model
        /* val user = DataBindingKotlinModel("Imtiyaz", "Khalani")
         binding.model = user*/
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutLoading.visibility = View.VISIBLE
        viewModel.getPersonalIfo()

        viewModel.userInfo.observe(this, Observer {
            layoutLoading.visibility = View.GONE
            personalInfo = it.personalInfo
            if (it != null) {
                tvUserName.text = it.personalInfo.name
                tvUserDetails.text = it.personalInfo.profile.city
                tv_about.text = it.personalInfo.profile.about
                tv_birthday.text = it.personalInfo.profile.date_of_birth
                tv_birth_place.text = it.personalInfo.profile.birth_place
                tv_lives_in.text = it.personalInfo.profile.city
                tv_occupation.text = it.personalInfo.profile.occupation
                tv_website.text = it.personalInfo.profile.website
                tv_gender.text = it.personalInfo.profile.gender
                tv_status.text = it.personalInfo.profile.marital_status
                tv_email.text = it.personalInfo.email
            } else {
                tvUserName.text = ""
                tvUserDetails.text = ""
                tv_about.text = ""
                tv_birthday.text = ""
                tv_birth_place.text = ""
                tv_lives_in.text = ""
                tv_occupation.text = ""
                tv_website.text = ""
                tv_gender.text = ""
                tv_status.text = ""
                tv_email.text = ""
            }
        })

        tv_edit.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(PROFILE, personalInfo)
            val editProfileFragment = EditProfileFragment()
            editProfileFragment.arguments = bundle
            (context as HomeActivity).switchPage(editProfileFragment, true)
        }
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }
}
