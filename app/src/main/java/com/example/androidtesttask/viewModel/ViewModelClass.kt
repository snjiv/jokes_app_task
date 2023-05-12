package com.example.androidtesttask.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtesttask.model.JokeResponseModel
import com.example.androidtesttask.retrofit.ApiInterface
import com.example.androidtesttask.retrofit.RetrofitClient

class ViewModelClass : ViewModel() {
    private val TAG ="RESPONSE"
    private var apiInterface = RetrofitClient.getClient()?.create(ApiInterface::class.java)!!

    suspend fun jokeFetch(params: HashMap<String,String>):LiveData<JokeResponseModel>{
        val mutableLiveData = MutableLiveData<JokeResponseModel>()
        val response = apiInterface.jokeFetch(params)
        if (response.body() != null){
            mutableLiveData.postValue(response.body())
        } else{
            Log.e(TAG,response.errorBody().toString())
        }
        return mutableLiveData
    }

}