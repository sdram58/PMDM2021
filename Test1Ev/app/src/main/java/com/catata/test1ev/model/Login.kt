package com.catata.test1ev.model

import android.content.Context
import com.catata.test1ev.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type

typealias OnLogin = (user: Login.User?)->Unit
typealias OnLoading = (loading:Boolean)->Unit

const val waitTime = 1000L
class Login {

    data class User(
        val id:Int,
        val username:String,
        val pass:String
    )

    companion object{
        var listUser: List<User> = listOf()
    }


    fun loadFromJSON(context: Context):Boolean{
        var userList:List<User>?
        val raw = context.resources.openRawResource(R.raw.users)
        val rd = BufferedReader(InputStreamReader(raw))

        val listType: Type = object : TypeToken<MutableList<User?>?>() {}.type

        val gson = Gson()
        userList = gson.fromJson(rd, listType)

        Thread.sleep(waitTime)

        userList?.let {
            listUser = it
        }

        return userList!!.isNotEmpty()
    }

    suspend fun checkUser(user:User, onLogin: OnLogin, onLoading: OnLoading){
        onLoading(true)
        delay(waitTime)
        val list = listUser.filter {
            it.username == user.username && it.pass == user.pass
        }

        if(list.isEmpty()) onLogin(null)
        else
            onLogin(list[0])

        onLoading(false)
    }

    suspend fun getUserById(id:Int,onLogin: OnLogin, onLoading: OnLoading){
        onLoading(true)
        delay(waitTime)
        val list = listUser.filter { user->
            user.id == id
        }

        if(list.isEmpty()) onLogin(null)
        else
            onLogin(list[0])

        onLoading(false)
    }

    fun logOut(){
        listUser = listOf()
    }

}