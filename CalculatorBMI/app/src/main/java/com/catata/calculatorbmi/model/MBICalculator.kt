package com.catata.calculatorbmi.model

import kotlin.math.pow
typealias OnSucess = (result:Double) -> Unit
typealias OnWrongWeight = (error:String) -> Unit
typealias OnWrongHeight = (error:String) -> Unit
typealias OnLoading = (isLoading:Boolean) -> Unit


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

    interface OnResponse{
        fun onSuccess(result:Double)
        fun onHeightError(error:String)
        fun onWeightError(error:String)
        fun onError(error:String)
        fun onLoading(loading: Boolean)
    }

    fun calculateCallBacks(request: Request, onResponse: OnResponse){
        val minHeight = 50.0
        val minWeight = 20.0

        var error = false

        onResponse.onLoading(true)


        Thread.sleep(5000)

        if(request.weight<minWeight){
            onResponse.onWeightError("Weight should be bigger")
            error = true
        }
        if(request.height<minHeight){
            onResponse.onHeightError("Height should be bigger")

            error = true
        }
        if(!error)
            onResponse.onSuccess( calculate(request) )

        onResponse.onLoading(false)
    }



    /*****Functions*****/

    fun  calculateFunctions(request: Request,
                            onSucess: OnSucess,
                            onLoading: OnLoading,
                            onWrongWeight: OnWrongWeight,
                            onWrongHeight: OnWrongHeight?){
        val minHeight = 50.0
        val minWeight = 20.0

        var error = false

        onLoading(true)


        Thread.sleep(5000)

        if(request.weight<minWeight){
            onWrongWeight("Weight should be bigger")
            error = true
        }
        if(request.height<minHeight){
            onWrongHeight?.let {
                onWrongHeight("Height should be bigger")
            }
            error = true
        }
        if(!error)
            onSucess( calculate(request) )

        onLoading(false)

    }


    /********SEALED CLASS****************/
    sealed class Response{
        class OKResult(val result:Double):Response()
        class WrongWeight(val error:String):Response()
        class WrongHeight(val error:String):Response()
    }

    fun calculateSealedClass(request: Request):Response{
        val minHeight = 50.0
        val minWeight = 20.0



        Thread.sleep(5000)

        if(minWeight > request.weight) return ( Response.WrongWeight("Weight should be bigger"))
        if(minHeight > request.height) return ( Response.WrongHeight("Height should be bigger"))


        return Response.OKResult(calculate(request))


    }

    private fun calculate(r: Request):Double = r.weight/(r.height/100).pow(2)
}