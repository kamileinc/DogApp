package com.example.dogapp.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DogPhotoViewModelTest {

    private lateinit var viewModel: DogPhotoViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = DogPhotoViewModel()
    }

    @Test
    fun getSum_whenStringOfOnlyNumbers_returnSum() {
        viewModel.getSum("02088094")
        assertEquals(31, viewModel.urlNumber.value?.data)
    }

    @Test
    fun getSum_whenStringWithTwoNumbersAfterUnderscore_returnSum() {
        viewModel.getSum("https://images.dog.ceo/breeds/hound-english/n02089973_32.jpg")
        assertEquals(38, viewModel.urlNumber.value?.data)
    }

    @Test
    fun getSum_whenStringWithThreeNumbersAfterUnderscore_returnSum() {
        viewModel.getSum("https://images.dog.ceo/breeds/hound-blood/n02088466_654.jpg")
        assertEquals(34, viewModel.urlNumber.value?.data)
    }

    @Test
    fun getSum_whenStringWithFourNumbersAfterUnderscore_returnSum() {
        viewModel.getSum("https://images.dog.ceo/breeds/hound-blood/n02088466_9334.jpg")
        assertEquals(34, viewModel.urlNumber.value?.data)
    }

    @Test
    fun getSum_whenStringWithFiveNumbersAfterUnderscore_returnSum() {
        viewModel.getSum("https://images.dog.ceo/breeds/hound-blood/n02088466_12388.jpg")
        assertEquals(34, viewModel.urlNumber.value?.data)
    }
    @Test
    fun getSum_whenStringWithoutAnyNumbers_returnZero() {
        viewModel.getSum("https://images.dog.ceo/breeds/hound-basset/n.jpg")
        assertEquals(0, viewModel.urlNumber.value?.data)
    }
    @Test
    fun getSum_whenEmptyString_returnZero() {
        viewModel.getSum("")
        assertEquals(0, viewModel.urlNumber.value?.data)
    }
}
