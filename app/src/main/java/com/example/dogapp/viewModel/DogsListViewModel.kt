package com.example.dogapp.viewModel

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogapp.R
import com.example.dogapp.UiText
import com.example.dogapp.data.Data
import com.example.dogapp.utils.Resource
import com.google.gson.GsonBuilder

class DogsListViewModel(private val assets: AssetManager) : ViewModel() {

    private val _urlList = MutableLiveData<Resource<Array<String>>>(Resource.Empty())
    val urlList: LiveData<Resource<Array<String>>>
        get() = _urlList

    fun fetchData() {
        _urlList.value = Resource.Loading()

        val jsonFileString = assets.open("dogs/dog_urls.json").bufferedReader().use { it.readText() }
        val gson = GsonBuilder().create()
        val data = gson.fromJson(jsonFileString, Data::class.java)

        if (data.urls.isEmpty()) _urlList.value =
            Resource.Failure(UiText.StringResource(resId = R.string.no_urls))
        else _urlList.value = Resource.Success(data.urls)
    }
}
