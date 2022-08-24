package com.example.dogapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DogPhotoViewModel : ViewModel() {

    private val _urlNumber = MutableLiveData<Resource<Int>>(Resource.Empty())
    val urlNumber: LiveData<Resource<Int>>
        get() = _urlNumber

    fun getSum(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val numbers = filterNumbers(url)
            val sum = addNumbers(numbers)
            _urlNumber.postValue(Resource.Success(sum))
        }
    }

    private fun filterNumbers(url: String): List<Int> {
        val numbers = mutableListOf<Int>()
        url.substringBefore("_")
            .filter { it.isDigit() }
            .forEach { numbers.add(it.digitToInt())}
        return numbers
    }

    private fun addNumbers(numbers: List<Int>): Int {
        var sum = 0
            numbers.forEach { sum += it }
        return sum
    }
}
