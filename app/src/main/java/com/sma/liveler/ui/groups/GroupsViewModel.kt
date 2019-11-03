package com.sma.liveler.ui.groups

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.repository.NavigationRepository
import com.sma.liveler.vo.Group

class GroupsViewModel() : ViewModel() {

    private lateinit var context: Context
    private lateinit var navigationRepository: NavigationRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var groups = MutableLiveData<List<Group>>()

    constructor(context: Context, navigationRepository: NavigationRepository) : this() {
        this.context = context
        this.navigationRepository = navigationRepository
        groups = navigationRepository.groups
        loadingVisibility = navigationRepository.loading
        errorMessage = navigationRepository.errrorMessage
        success = navigationRepository.success
    }

    fun getGroups() {
        navigationRepository.getGroups()
    }

    fun createGroup(groupName: String) {
        navigationRepository.createGroup(groupName)
    }
}