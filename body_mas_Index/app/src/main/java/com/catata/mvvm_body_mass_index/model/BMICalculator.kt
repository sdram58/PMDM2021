package com.catata.mvvm_body_mass_index.model

import kotlinx.coroutines.*

class BMICalculator{

    data class Request(
        val weight:Double,
        val height:Double
    )

    fun calculate(request:Request){
        GlobalScope.launch {
            println("${Thread.currentThread().name}")
            delay(10000)
            request

        }

    }

}