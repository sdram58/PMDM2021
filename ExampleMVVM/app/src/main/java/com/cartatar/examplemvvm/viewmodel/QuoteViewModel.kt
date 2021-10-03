package com.cartatar.examplemvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cartatar.examplemvvm.model.QuoteModel
import com.cartatar.examplemvvm.model.QuoteProvider

class QuoteViewModel:ViewModel() {
    val quoteModel = MutableLiveData<QuoteModel>()

    //Será llamado desde la vista para que le dé un dato
    fun randomQuote(){
        val currentQuote = QuoteProvider.radom()
        quoteModel.postValue(currentQuote)
    }
}