package com.sma.liveler.ui.addaccount

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.R
import com.sma.liveler.repository.PostRepository

class AddBankAccountViewModel() : ViewModel() {

    var TAG = AddBankAccountViewModel::class.java.simpleName

    private lateinit var context: Context
    private lateinit var postRepository: PostRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var holderName: MutableLiveData<String> = MutableLiveData()
    var bankName: MutableLiveData<String> = MutableLiveData()
    var accountNumber: MutableLiveData<String> = MutableLiveData()
    var branch: MutableLiveData<String> = MutableLiveData()
    var ifsc: MutableLiveData<String> = MutableLiveData()

    constructor(context: Context, postRepository: PostRepository) : this() {
        this.context = context
        this.postRepository = postRepository
        loadingVisibility = postRepository.loading
        errorMessage = postRepository.errrorMessage
        success = postRepository.success
    }

    fun getFriends() {
        postRepository.getFriends(0)
    }

    fun onClickAddBankAccount(
        holderName: String,
        bankName: String,
        accountNumber: String,
        branch: String,
        ifsc: String
    ) {
        if (TextUtils.isEmpty(holderName)) {
            this.holderName.value = context.getString(R.string.error_holder_name)
        } else if (TextUtils.isEmpty(bankName)) {
            this.holderName.value = context.getString(R.string.error_bank_name)
        } else if (TextUtils.isEmpty(accountNumber)) {
            this.holderName.value = context.getString(R.string.error_account_number)
        } else if (TextUtils.isEmpty(branch)) {
            this.holderName.value = context.getString(R.string.error_branch)
        } else if (TextUtils.isEmpty(ifsc)) {
            this.holderName.value = context.getString(R.string.error_ifsc)
        } else {
            postRepository.addBankAccount(holderName, bankName, accountNumber, branch, ifsc)
        }
    }
}