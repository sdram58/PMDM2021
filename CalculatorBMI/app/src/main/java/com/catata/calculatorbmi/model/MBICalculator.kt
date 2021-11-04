package com.catata.calculatorbmi.model

import kotlin.math.pow
typealias OnSucess = (result:Double) -> Unit
typealias OnWrongWeight = (error:String) -> Unit
typealias OnWrongHeight = (error:String) -> Unit


class MBICalculator {

    data class Request(
        val weight:Double,
        val height:Double
    )

    //Long function
    fun calculateMBI(request:Request):Double{
        println("Thread -> ${Thread.currentThread().name}")
        Thread.sleep(5000)
        println("Thread End -> ${Thread.currentThread().name}")
        return calculate(request)

    }

    fun  calculateFunctions(request: Request,
                            onSucess: OnSucess,
                            onWrongWeight: OnWrongWeight,
                            onWrongHeight: OnWrongHeight?){
        val minHeight = 50.0
        val miWeight = 20.0

        var error = false


        Thread.sleep(5000)

        if(request.weight<miWeight){
            onWrongWeight("Weight should be bigger")
            error = true
        }
        if(request.height<minHeight){
            onWrongHeight?.let {
                onWrongHeight("Height should be bigger")
                error = true
            }
        }
        if(!error)
            onSucess( calculate(request) )
    }

    private fun calculate(r: Request) = r.weight/(r.height/100).pow(2)
}