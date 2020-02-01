package com.sma.liveler.ui.addaccount

import android.content.DialogInterface
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
import com.sma.liveler.databinding.FragmentAddBankAccountBinding
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.account.BankAccountFragment
import com.sma.liveler.ui.home.HomeActivity
import com.sma.liveler.utils.Utils
import kotlinx.android.synthetic.main.fragment_add_bank_account.*


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 *
 */
class AddBankAccountFragment : Fragment() {

    private lateinit var binding: FragmentAddBankAccountBinding

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: AddBankAccountViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AddBankAccountViewModel(
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
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_bank_account, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.holderName.observe(this, Observer {
            pbLoading.visibility = View.GONE
            tilAccountHolderName.error = it
        })

        viewModel.accountNumber.observe(this, Observer {
            pbLoading.visibility = View.GONE
            tilAccountNumber.error = it
        })

        viewModel.bankName.observe(this, Observer {
            pbLoading.visibility = View.GONE
            tilBankName.error = it
        })

        viewModel.branch.observe(this, Observer {
            pbLoading.visibility = View.GONE
            tilBankName.error = it
        })

        viewModel.ifsc.observe(this, Observer {
            pbLoading.visibility = View.GONE
            tilIfsc.error = it
        })

        viewModel.errorMessage.observe(this, Observer {
            Utils.alert(activity!!, it)
        })

        viewModel.success.observe(this, Observer {
            pbLoading.visibility = View.GONE
            if (it) {
                Utils.alert(activity!!, "Successfully added",
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.cancel()
                        dialog.dismiss()
                        (activity!! as HomeActivity).switchPage(BankAccountFragment(), false)
                    })
            }
        })

        btnAddAccount.setOnClickListener {
            viewModel.onClickAddBankAccount(
                tipAccountHolderName.text.toString(),
                tipBankName.text.toString(),
                tipAccountNumber.text.toString(),
                tipBranch.text.toString(),
                tipIfsc.text.toString()
            )
            pbLoading.visibility = View.VISIBLE
        }
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }
}
