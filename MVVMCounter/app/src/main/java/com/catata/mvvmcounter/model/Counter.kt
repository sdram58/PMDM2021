package com.catata.mvvmcounter.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

typealias OnInc=(num:Int)->Unit

class Counter {
    val num = 0

    suspend fun count(onInc: OnInc){
        while(true){
            onInc(num)
            delay(1000)
        }
    }

}