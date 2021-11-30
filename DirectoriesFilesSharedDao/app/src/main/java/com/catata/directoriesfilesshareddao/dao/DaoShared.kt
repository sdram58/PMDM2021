package com.catata.directoriesfilesshareddao.dao

import android.content.Context
import com.catata.directoriesfilesshareddao.R
import com.catata.directoriesfilesshareddao.model.People

class DaoShared(val context:Context): IMyDAO {

    companion object{
        var daoShared:DaoShared? = null

        fun getInstance(c:Context):DaoShared{
            daoShared?.let {} ?: run {
                daoShared = DaoShared(c)
            }

            return daoShared!!
        }
    }


    override suspend fun save(people: People, onSaved: OnSaved, onError: OnError?) {
        val sharedPref2 = context.getSharedPreferences(
            context.getString(R.string.my_shared_file),
            Context.MODE_PRIVATE
        )
        with(sharedPref2.edit()){
            putString(context.getString(R.string.key_preference_name),
                people.name)
            putString(context.getString(R.string.key_preference_surname),
                people.surname)
            if(commit())
                onSaved(true)
            else {
                onError?.let {
                    it("Data can't be saved on Shared Preferences")
                }
            }
        }
    }

    override suspend fun load(onLoaded: OnLoaded, onError: OnError?) {
        val sharedPref2 = context.getSharedPreferences(
            context.getString(R.string.my_shared_file),
            Context.MODE_PRIVATE
        )
        val name = sharedPref2.getString(context.getString(R.string.key_preference_name), "")
        val surname = sharedPref2.getString(context.getString(R.string.key_preference_surname), "")
        if(name == "" || surname==""){
            onError?.let {
                onError("Data from shared can't be loaded")
            }
        }else{
            onLoaded(People(name, surname))
        }
    }
}