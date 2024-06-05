package com.exzi.coincase.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.exzi.coincase.Base
import com.exzi.coincase.data.defaultTicker.Data
import com.exzi.coincase.repository.CoinCaseRepository
import com.exzi.coincase.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SpotViewModel
@Inject constructor(
    application: Application,
    private val repository: CoinCaseRepository,
) : AndroidViewModel(application) {
    private var _response: MutableLiveData<Resource<Response<Base<List<Data>>>>?> = MutableLiveData()
    val response :LiveData<Resource<Response<Base<List<Data>>>>?> get()=_response

    fun getSpot() =viewModelScope.launch {
        getSpotSafeCall()
    }

    private suspend fun getSpotSafeCall() {
        _response.postValue(Resource.Loading())
        _response.postValue(Resource.Success(repository.getSpot()))
    }

    fun clearResponse(){
        _response.postValue(null)
    }


}