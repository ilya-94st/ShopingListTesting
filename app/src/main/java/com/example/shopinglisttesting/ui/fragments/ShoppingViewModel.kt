package com.example.shopinglisttesting.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ResponseImage
import com.example.shopinglisttesting.data.local.ShoppingItems
import com.example.shopinglisttesting.other.Constants
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
        // проверка на пустое поле
       if(name.isEmpty() || amountString.isEmpty() || price.isEmpty()) {
           _insertShoppingStatus.postValue(Event(Resources.error("the field must not be empty", null)))
       return
       }
        // проверка на максимальную длинну имени
       if(name.length > Constants.MAX_LENGTH_NAME) {
           _insertShoppingStatus.postValue(Event(Resources.error("the field contains more" + "${Constants.MAX_LENGTH_NAME} symbols", null)))
           return
       }
        // проверка на максимальную длину цены
        if(price.length > Constants.MAX_LENGTH_PRICE) {
            _insertShoppingStatus.postValue(Event(Resources.error("the field contains more" + "${Constants.MAX_LENGTH_PRICE} symbols", null)))
            return
        }
        // проверка на корректно введенное число
        val amount = try {
         amountString.toInt()
        } catch (e: Exception){
            _insertShoppingStatus.postValue(Event(Resources.error("please enter the required number" + "amount", null)))
         return
        }
        val shoppingItems = ShoppingItems(name, amount, price.toFloat(), _imagesUrl.value?: "")
        insertShoppingItemsDB(shoppingItems)
        setImagesUrl("")
        _insertShoppingStatus.postValue(Event(Resources.successes(shoppingItems)))
    }

    fun searchImagesPix(imageQuery: String) {
      if (imageQuery.isEmpty()){
          return
      }
        _imagesPix.value = Event(Resources.landing(null))
        viewModelScope.launch {
            val response = shoppingRepository.searchPixImage(imageQuery)
            _imagesPix.value = Event(response)
        }
    }
}