package com.catata.livedatatrainer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.catata.livedatatrainer.R
import com.catata.livedatatrainer.model.Trainer

class TrainerViewModel(application: Application) : AndroidViewModel(application) {
    private var trainer: Trainer = Trainer()


    var exerciseLiveData : LiveData<Int> //Images
    var repetitionLiveData :LiveData<String>

    private var previousExercise:String =""


    init {

        exerciseLiveData = Transformations.switchMap(trainer.orderLiveData){
                exercise ->
            val mExercise = exercise.split(":")[0]
            if(mExercise != previousExercise){
                previousExercise = mExercise

                var imageID:Int = when(mExercise){
                    "EXERCISE1" -> R.drawable.e1
                    "EXERCISE2" -> R.drawable.e2
                    "EXERCISE3" -> R.drawable.e3
                    "EXERCISE4" -> R.drawable.e4
                    else -> R.drawable.e1

                }
                return@switchMap MutableLiveData<Int>(imageID)
            }

            return@switchMap null
        }

        repetitionLiveData = Transformations.switchMap(trainer.orderLiveData) { exercise ->
            return@switchMap MutableLiveData<String>(exercise.split(":")[1])
        }



    }
}
