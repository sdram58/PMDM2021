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
    val loading:MutableLiveData<Boolean> = MutableLiveData()

    fun calculteBMI(weight:Double,height:Double){
        //calculateFunctions(weight,height)
        //calculateCallbacks(weight,height)
        calculateSealed(weight,height)
    }

    private fun calculateSealed(weight: Double, height: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            loading.postValue(true)
            mbiCalculator.calculateSealedClass(MBICalculator.Request(weight, height)).also { res ->
                when (res) {
                    is MBICalculator.Response.OKResult -> {
                        bmi.postValue(res.result)
                        errorWeight.postValue("")
                        errorHeight.postValue("")
                    }
                    is MBICalculator.Response.WrongHeight -> {
                        errorHeight.postValue(res.error)
                    }
                    is MBICalculator.Response.WrongWeight -> {
                        errorWeight.postValue(res.error)
                    }
                }

            }
            loading.postValue(false)
        }
    }

    private fun calculateCallbacks(weight: Double, height: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            mbiCalculator.calculateCallBacks(
                MBICalculator.Request(weight,height),
                object:MBICalculator.OnResponse{
                    override fun onSuccess(result: Double) {
                        bmi.postValue(result)
                        errorWeight.postValue("")
                        errorHeight.postValue("")
                    }

                    override fun onHeightError(error: String) {
                        errorHeight.postValue(error)
                    }

                    override fun onWeightError(error: String) {
                        errorWeight.postValue(error)
                    }

                    override fun onError(error: String) {
                        TODO("Not yet implemented")
                    }

                    override fun onLoading(mLoading: Boolean) {
                        loading.postValue(mLoading)
                    }

                }
            )
        }
    }

    fun calculateFunctions(weight:Double,height:Double){

        CoroutineScope(Dispatchers.IO).launch {
            mbiCalculator.calculateFunctions(MBICalculator.Request(weight,height),
            onSucess = {
                result -> bmi.postValue(result)
                errorWeight.postValue("")
                errorHeight.postValue("")
            },
            onLoading = {
                isLoading -> loading.postValue(isLoading)
            },
            onWrongWeight = {
                error -> errorWeight.postValue(error)
            },null)


        }
    }

}