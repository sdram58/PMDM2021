package com.cartatar.body_mass_index.model


import java.lang.Exception
import kotlin.math.pow


typealias OnWrongHeight = (error:String)->Unit
typealias OnWrongWeight = (error:String)->Unit
typealias onError = (error:String)->Unit
typealias OnSuccess = (bmi:Double)->Unit
typealias OnLoading = (loading:Boolean)->Unit


class BMICalculator{

    data class Request(
        val weight:Double,
        val height:Double
    )

    //Long calculating function
    fun calculateCallbacks(request:Request,onSuccess: OnSuccess, onError: onError, onLoading: OnLoading, onWrongWeight: OnWrongWeight?, onWrongHeight: OnWrongHeight?){

        onLoading(true)
        val minHeight= 50
        val minWeight = 10

        var error = false





        Thread.sleep(5000)
        println(Thread.currentThread().name)

        //If height is lower than minHeight call ourCallback
        if(minHeight > request.height){
            onWrongHeight?.let {
                it.invoke("Height should be bigger")
                error = true
            }
        }
        if(minWeight > request.weight){
            onWrongHeight?.let {
                it.invoke("Weight should be bigger")
                error = true
            }
        }

        if(!error){
            //All works fine
            try{
                val bmi = calcMBI(request.weight, request.height)
                onSuccess(bmi)
            }catch (e:Exception){
                onError(e.toString())
            }
        }
         onLoading(false)



    }

    //Long calculating function with Sealed class
    fun calculateSealed(request:Request,onLoading: OnLoading?):Response{
        onLoading?.invoke(true)
        val minHeight= 50
        val minWeight = 10

        if(minWeight > request.weight) return ( Response.WrongWeight("Weight should be bigger"))
        if(minHeight > request.height) return ( Response.WrongHeight("Height should be bigger"))




        Thread.sleep(5000)
        println("${Thread.currentThread().name}")

        //All works fine
        onLoading?.invoke(false)
        return Response.OKResult(calcMBI(request.weight, request.height))

    }

    private fun calcMBI(weight: Double, height: Double):Double = weight/ (height/100).pow(2)

    interface CalculatingResult{
        fun onResultReady(result:Double)
    }

    sealed class Response{
        class OKResult(val result:Double):Response()
        class WrongWeight(val error:String):Response()
        class WrongHeight(val error:String):Response()

    }


}