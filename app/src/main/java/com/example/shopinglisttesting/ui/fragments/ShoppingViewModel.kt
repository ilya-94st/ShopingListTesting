package com.example.shopinglisttesting.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ResponseImage
import com.example.shopinglisttesting.data.local.ShoppingItems
import com.example.shopinglisttesting.other.Event
import com.example.shopinglisttesting.other.Resources
import com.example.shopinglisttesting.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(val shoppingRepository: ShoppingRepository): ViewModel() {

    private val _imagesPix: MutableLiveData<Event<Resources<ResponseImage>>> = MutableLiveData()

    fun imagesPix() : LiveData<Event<Resources<ResponseImage>>> {
        return _imagesPix
    }

    private val _imagesUrl: MutableLiveData<String> = MutableLiveData()

    fun imagesUrl(): LiveData<String> {
        return _imagesUrl
    }

    private val _insertShoppingStatus: MutableLiveData<Event<Resources<ShoppingItems>>> = MutableLiveData()

    fun insertShoppingStatus(): LiveData<Event<Resources<ShoppingItems>>> {
        return _insertShoppingStatus
    }

    fun setImagesUrl(url: String) {
        _imagesUrl.postValue(url)
    }

    fun deleteShoppingItems(shoppingItems: ShoppingItems) = viewModelScope.launch {
        shoppingRepository.deleteShoppingItems(shoppingItems)
    }

    fun insertShoppingItemsDB(shoppingItems: ShoppingItems) = viewModelScope.launch {
        shoppingRepository.insert(shoppingItems)
    }

    fun readShoppingItems() = shoppingRepository.readAll()

    fun totalPrise() = shoppingRepository.readTotalPrise()

    fun insertShoppingItems(name: String, amountString: String, price: String) {

    }

    fun searchImagesPix(imageQuery: String) {

    }
}