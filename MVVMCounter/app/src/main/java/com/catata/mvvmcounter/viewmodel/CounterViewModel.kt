package com.catata.mvvmcounter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catata.mvvmcounter.model.Counter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CounterViewModel:ViewModel() {

    val counterLiveData:MutableLiveData<Int> = MutableLiveData<Int>()
    val counter:Counter = Counter()

    var job: Job? = null

    fun start(){
        if(job!=null && job?.isActive!!) stop()
        else
        job= CoroutineScope(Dispatchers.IO).launch {
            counter.count {
                counterLiveData.postValue(it)
            }
        }
    }

    private fun stop(){
        job?.cancel()
    }
}