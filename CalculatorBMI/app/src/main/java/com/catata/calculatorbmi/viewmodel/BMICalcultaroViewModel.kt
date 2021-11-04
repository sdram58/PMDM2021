package com.catata.calculatorbmi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catata.calculatorbmi.model.MBICalculator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BMICalcultaroViewModel: ViewModel() {

    private val mbiCalculator = MBICalculator()

    val bmi:MutableLiveData<Double> = MutableLiveData()
    val errorHeight:MutableLiveData<String> = MutableLiveData()
    val errorWeight:MutableLiveData<String> = MutableLiveData()

    fun calculteBMI(weight:Double,height:Double){
        calculateFunctions(weight,height)
    }

    fun calculateFunctions(weight:Double,height:Double){

        CoroutineScope(Dispatchers.IO).launch {
            mbiCalculator.calculateFunctions(MBICalculator.Request(weight,height),
            onSucess = {
                result -> bmi.postValue(result)
                errorWeight.postValue("")
                errorHeight.postValue("")
            },
            onWrongWeight = {
                error -> errorWeight.postValue(error)
            },null)


        }
    }

}