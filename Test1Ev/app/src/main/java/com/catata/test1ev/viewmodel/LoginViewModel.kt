package com.catata.test1ev.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.catata.test1ev.model.Login
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var login : Login = Login()

    var userLD:MutableLiveData<Login.User?> = MutableLiveData()
    var isLoadingLD:MutableLiveData<Boolean> = MutableLiveData()

    fun loadFromJSON(){
        CoroutineScope(Dispatchers.IO).launch {
            login.loadFromJSON(getApplication())
        }
    }

    fun checkUser(username:String, pass:String){
        CoroutineScope(Dispatchers.IO).launch {

            login.checkUser(Login.User(-1,username,pass),
            onLogin = {
                userLD.postValue(it)
            },
            onLoading = {
                isLoadingLD.postValue(it)
            })


        }
    }

    fun getUserById(id:Int){
        CoroutineScope(Dispatchers.IO).launch {

            login.getUserById(id,
                onLogin = {
                    userLD.postValue(it)
                },
                onLoading = {
                    isLoadingLD.postValue(it)
                })


        }
    }

    fun loadData(){
        login.loadFromJSON(getApplication())
    }
}