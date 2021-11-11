package com.catata.mvvmcounter.model

import android.util.Log
import kotlinx.coroutines.delay

typealias OnInc=(num:Int)->Unit

class Counter {
    suspend fun count(onInc: OnInc){
        var num = 0
        while(true){
            log("Thread --> ${Thread.currentThread().name}")
            onInc(num)
            num++
            delay(1000)
        }
    }

    private fun log(text:String){
        Log.d("CHECK", text)
    }

}