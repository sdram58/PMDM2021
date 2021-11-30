package com.catata.directoriesfilesshareddao.dao

import android.content.Context
import com.catata.directoriesfilesshareddao.R
import com.catata.directoriesfilesshareddao.model.People

class DaoShared(val context:Context): IMyDAO {

    companion object{
        var daoShared:DaoShared? = null

        fun getInstance(c:Context):DaoShared{
            return daoShared ?: DaoShared(c)
        }
    }

    override fun save(people: People) {
        val sharedPref2 = context.getSharedPreferences(
            context.getString(R.string.my_shared_file),
            Context.MODE_PRIVATE
        )
        with(sharedPref2.edit()){
            putString(context.getString(R.string.key_preference_name),
                people.name)
            putString(context.getString(R.string.key_preference_surname),
                people.surname)
            apply()
        }
    }

    override fun load(): People {
        val sharedPref2 = context.getSharedPreferences(
            context.getString(R.string.my_shared_file),
            Context.MODE_PRIVATE
        )
        val name = sharedPref2.getString(context.getString(R.string.key_preference_name), "No name")
        val surname = sharedPref2.getString(context.getString(R.string.key_preference_surname), "No name")
        return People(name, surname)
    }
}