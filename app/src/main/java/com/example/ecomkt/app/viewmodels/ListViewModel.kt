package com.example.ecomkt.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecomkt.app.base.BaseViewModel
import com.example.ecomkt.app.mappers.toPresentation
import com.example.ecomkt.app.models.CommunityInfoPresentation
import com.example.ecomkt.app.models.state.CommunityInfoListViewState
import com.example.ecomkt.app.models.state.Error
import com.example.ecomkt.domain.usecases.DeleteCommunityInfoByIdUseCase
import com.example.ecomkt.domain.usecases.GetAllCommunityInfoUseCase
import com.example.ecomkt.app.util.ExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val deleteCommunityInfoByIdUseCase: DeleteCommunityInfoByIdUseCase,
    private val getAllCommunityInfoUseCase: GetAllCommunityInfoUseCase
): BaseViewModel() {

    private var deleteCommunityInfoJob: Job? = null
    private var getAllCommunityJob: Job? = null

    val communityInfoListViewState: LiveData<CommunityInfoListViewState>
        get() = _communityInfoListViewState

    private var _communityInfoListViewState = MutableLiveData<CommunityInfoListViewState>()

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        _communityInfoListViewState.value =
            _communityInfoListViewState.value?.copy(isLoading = false, error = Error(message))
    }

    init {
        _communityInfoListViewState.value =
            CommunityInfoListViewState(isLoading = true, error = null, communityInfoList = null)
        getAllCommunityInfos()
    }

     fun getAllCommunityInfos() {
        getAllCommunityJob = launchCoroutine {
            onCommunityInfoListLoading()
            loadListCommunityInfo()
        }
    }

    private fun onCommunityInfoListLoading() {
        _communityInfoListViewState.value = _communityInfoListViewState.value?.copy(isLoading = true)
    }

    private fun onCommunityInfoListLoadingComplete(list: List<CommunityInfoPresentation>) {
        _communityInfoListViewState.value =
            _communityInfoListViewState.value?.copy(isLoading = false, communityInfoList = list)
    }

    private suspend fun loadListCommunityInfo() {
        getAllCommunityInfoUseCase(Unit).collect { listCommunityInformation ->
            val listCommunityInfoPresentation = listCommunityInformation.map { it.toPresentation() }
            onCommunityInfoListLoadingComplete(listCommunityInfoPresentation)
        }
    }

    fun deleteCommunityInfo(id: Int) {
        deleteCommunityInfoJob = launchCoroutine {
            deleteCommunityInfoByIdUseCase(id).collect { row ->
                if (row > 0) {
                    loadListCommunityInfo()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        deleteCommunityInfoJob?.cancel()
        getAllCommunityJob?.cancel()
    }


}