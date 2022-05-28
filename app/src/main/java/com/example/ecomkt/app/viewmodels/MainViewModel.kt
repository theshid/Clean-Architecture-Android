package com.example.ecomkt.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecomkt.app.base.BaseViewModel
import com.example.ecomkt.app.mappers.toPresentation
import com.example.ecomkt.app.models.state.Error
import com.example.ecomkt.app.models.state.InsertViewState
import com.example.ecomkt.app.models.state.PostResponseViewState
import com.example.ecomkt.domain.models.CommunityInformation
import com.example.ecomkt.domain.models.Result
import com.example.ecomkt.domain.usecases.InsertCommunityInfoUseCase
import com.example.ecomkt.domain.usecases.SendCommunityInfoUseCase
import com.example.ecomkt.app.util.ExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val sendCommunityInfoUseCase: SendCommunityInfoUseCase,
    val insertCommunityInfoUseCase: InsertCommunityInfoUseCase
) : BaseViewModel() {

    private var postCommunityInformationJob: Job? = null
    private var insertCommunityInfoJob: Job? = null
    val postResponseViewState: LiveData<PostResponseViewState>
        get() = _postResponseViewState

    private var _postResponseViewState = MutableLiveData<PostResponseViewState>()

    val insertViewState: LiveData<InsertViewState>
        get() = _insertViewState

    private var _insertViewState = MutableLiveData<InsertViewState>()

    init {
        _postResponseViewState.value =
            PostResponseViewState(
                isComplete = false,
                error = null,
                response = null
            )

        _insertViewState.value =
            InsertViewState(
                isInserted = false,
                error = null
            )
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        _postResponseViewState.value = _postResponseViewState.value?.copy(error = Error(message))
        _insertViewState.value = _insertViewState.value?.copy(error = Error(message))
    }

    override fun onCleared() {
        super.onCleared()
        postCommunityInformationJob?.cancel()
        insertCommunityInfoJob?.cancel()
    }

    fun postInfoToServer(communityInfo: CommunityInformation) {
        postCommunityInformationJob = launchCoroutine {
            async { saveCommunityInfoToServer(communityInfo) }.await()
            _postResponseViewState.value = _postResponseViewState.value?.copy(isComplete = true)
        }

    }

    private fun saveCommunityToLocalDb(communityInformation: CommunityInformation) {
        insertCommunityInfoJob = launchCoroutine {
            insertCommunityInfoUseCase(communityInformation).collect { result ->
                if (result == Result.SUCCESS) {
                    _insertViewState.value = _insertViewState.value?.copy(isInserted = true)
                } else {
                    Timber.i("Saving Favorites Failed")
                }
            }
        }
    }

    fun displayError(message: Int) {
        _postResponseViewState.value = _postResponseViewState.value?.copy(error = Error(message))
    }

    private suspend fun saveCommunityInfoToServer(communityInformation: CommunityInformation) {
        sendCommunityInfoUseCase(communityInformation).collect { response ->
            Timber.d("value of response:${response.status}")
            if (response.status != -1) {
                val infoToSaveInDb = CommunityInformation(
                    communityInformation.communityName, communityInformation.geographicalDistrict,
                    communityInformation.accessibility, communityInformation.distanceToECOM,
                    communityInformation.connectedToECG, communityInformation.licenseDate,
                    communityInformation.latitude, communityInformation.longitude,
                    communityInformation.image, communityInformation.createdBy,
                    communityInformation.createdDate, communityInformation.updateBy,
                    communityInformation.updateDate, true
                )
                saveCommunityToLocalDb(infoToSaveInDb)
            } else {
                saveCommunityToLocalDb(communityInformation)
            }
            val postResponsePresentation = response.toPresentation()
            _postResponseViewState.value =
                _postResponseViewState.value?.copy(response = postResponsePresentation)
        }
    }


}