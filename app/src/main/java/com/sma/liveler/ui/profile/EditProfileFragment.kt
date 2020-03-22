package com.sma.liveler.ui.profile


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.sma.liveler.R
import com.sma.liveler.utils.PROFILE
import com.sma.liveler.vo.PersonalInfo
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class EditProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val personalInfo = arguments?.getParcelable(PROFILE) as PersonalInfo

        tipFirstName.setText(personalInfo.name)
        tipLastName.setText(personalInfo?.profile?.last_name)
        tipDateOfBirth.setText(personalInfo?.profile?.date_of_birth)
        tipPlaceOfBirth.setText(personalInfo?.profile?.birth_place)
        tipAbout.setText(personalInfo?.profile?.about)
        tipLivesIn.setText(personalInfo?.profile?.city)
        tipOccupation.setText(personalInfo?.profile.occupation)
        tipPhone.setText(personalInfo?.profile.phone_number)
        tipWebsite.setText(personalInfo?.profile.website)

        var genderAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.gender,
            android.R.layout.simple_list_item_1
        )
        spGender.adapter = genderAdapter

        if (!TextUtils.isEmpty(personalInfo?.profile.gender)) {
            if (personalInfo?.profile.gender.toLowerCase(Locale.getDefault()) == "MA".toLowerCase(
                    Locale.getDefault()
                )
            ) {
                spGender.setSelection(1)
            } else {
                spGender.setSelection(2)
            }
        } else {
            spGender.setSelection(0)
        }

        var statusAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.status,
            android.R.layout.simple_list_item_1
        )
        spStatus.adapter = statusAdapter

        if (!TextUtils.isEmpty(personalInfo?.profile.marital_status)) {
            if (personalInfo?.profile.marital_status.toLowerCase(Locale.getDefault()) == "NM".toLowerCase(
                    Locale.getDefault()
                )
            ) {
                spStatus.setSelection(1)
            } else {
                spStatus.setSelection(2)
            }
        } else {
            spStatus.setSelection(0)
        }

    }
}
