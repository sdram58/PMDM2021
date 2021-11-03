package com.cartatar.body_mass_index.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartatar.body_mass_index.model.BMICalculator
import com.cartatar.body_mass_index.model.BMICalculator.CalculatingResult
import kotlinx.coroutines.*

class BMICalculatorViewModel: ViewModel() {

    private val bmiCalculator: BMICalculator = BMICalculator()


    val bmi : MutableLiveData<Double> = MutableLiveData()
    val heightError : MutableLiveData<String> = MutableLiveData()
    val weightError : MutableLiveData<String> = MutableLiveData()
    val error : MutableLiveData<String> = MutableLiveData()
    val loading : MutableLiveData<Boolean> = MutableLiveData()



    fun calculateBMI(weight:Double,height:Double){

        calculateBMICallBack(weight,height)
        //calculateBMISealed(weight,height)

    }

    private fun calculateBMISealed(weight: Double, height: Double) {

        //viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
            loading.postValue(true)
            bmiCalculator.calculateSealed(BMICalculator.Request(weight, height),null).also{ res ->
                when(res){
                    is BMICalculator.Response.OKResult -> {
                        bmi.postValue(res.result)
                        heightError.postValue("")
                        weightError.postValue("")
                        error.postValue("") }
                    is BMICalculator.Response.WrongHeight ->{
                        heightError.postValue(res.error)
                    }
                    is BMICalculator.Response.WrongWeight ->{
                        weightError.postValue(res.error)
                    }
                }

            }
            loading.postValue(false)
        }
    }

    private fun calculateBMICallBack(weight: Double, height: Double) {
        //Using coroutine
        CoroutineScope(Dispatchers.IO).launch {
            //Using callbacks
            bmiCalculator.calculateCallbacks(BMICalculator.Request(weight, height),
                onSuccess = { mBMI->
                    bmi.postValue(mBMI)
                    heightError.postValue("")
                    weightError.postValue("")
                    error.postValue("")
                },
                onError = {
                        e -> error.postValue(e)
                },
                onLoading = {
                        isLoading -> loading.postValue(isLoading)
                },
                onWrongWeight = {
                        e -> weightError.postValue(e)
                }, null)

        }
    }


}