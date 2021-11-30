package com.catata.directoriesfilesshareddao.videmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.catata.directoriesfilesshareddao.dao.DaoFiles
import com.catata.directoriesfilesshareddao.dao.DaoShared
import com.catata.directoriesfilesshareddao.dao.IMyDAO
import com.catata.directoriesfilesshareddao.model.People
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DaoViewModel(application:Application): AndroidViewModel(application) {

    var nameLD:MutableLiveData<People> = MutableLiveData<People>()
    var errorLD:MutableLiveData<String> = MutableLiveData<String>()
    var switchText:MutableLiveData<String> = MutableLiveData()

    val context: Context = application

    //By default we work with Files
    var iMyDAO:IMyDAO = DaoFiles.getInstance(context)

    fun load(){
        CoroutineScope(Dispatchers.IO).launch {
            iMyDAO.load(
                onLoaded = { p ->
                    nameLD.postValue(p)
                },
                onError = { error ->
                    errorLD.postValue(error)
                }
            )
        }
    }

    fun save(p: People){
        CoroutineScope(Dispatchers.IO).launch {
            iMyDAO.save(
                p,
                onSaved = {
                          if(it){
                              //......
                          }
                },
                onError = { error ->
                    errorLD.postValue(error)
                }
            )
        }
    }

    fun changeDao(type: Boolean){
        if(type){ //SharedPreferences
            iMyDAO = DaoShared(context)
            switchText.postValue("Shared")
        }else{ //Files
            iMyDAO = DaoFiles(context)
            switchText.postValue("Files")
        }
    }
}