package com.sma.liveler.ui.account


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
import com.sma.liveler.databinding.FragmentBankAccountBinding
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.BankAccountAdapter
import com.sma.liveler.ui.addaccount.AddBankAccountFragment
import com.sma.liveler.ui.home.HomeActivity
import com.sma.liveler.vo.BankDetails
import kotlinx.android.synthetic.main.fragment_bank_account.*


/**
 * A simple [Fragment] subclass.
 *
 */
class BankAccountFragment : Fragment() {

    private lateinit var binding: FragmentBankAccountBinding
    private lateinit var bankAccountAdapter: BankAccountAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: BankAccountViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return BankAccountViewModel(activity!!, PostRepository(activity!!)) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bank_account, container, false)
        bankAccountAdapter = BankAccountAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvBankAccount.visibility = View.GONE
        linearLayoutBankInfo.visibility = View.GONE
        fabAddAccount.visibility = View.GONE

        viewModel.bankDetails.observe(this, Observer {
            updateBankDetails(it)
        })

        viewModel.getBankDetails()
        pbLoading.visibility = View.VISIBLE

        fabAddAccount.setOnClickListener {
            (activity!! as HomeActivity).switchPage(AddBankAccountFragment(), false)
        }
    }

    private fun updateBankDetails(bankDetails: BankDetails?) {
        if (bankDetails != null) {
            rvBankAccount.visibility = View.VISIBLE
            linearLayoutBankInfo.visibility = View.VISIBLE
            fabAddAccount.visibility = View.VISIBLE
            tvNoVideo.visibility = View.GONE
            pbLoading.visibility = View.GONE

            tipAccountHolderName.setText(bankDetails.account_holder_name)
            tipAccountNumber.setText(bankDetails.account_number)
            tipBankName.setText(bankDetails.name_of_bank)
            tipBranch.setText(bankDetails.branch)
            tipIfsc.setText(bankDetails.ifsc_number)
        } else {
            linearLayoutBankInfo.visibility = View.GONE
            tvNoVideo.visibility = View.VISIBLE
            pbLoading.visibility = View.GONE
        }
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }
}
