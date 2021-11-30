package com.catata.directoriesfilesshareddao.dao

import com.catata.directoriesfilesshareddao.model.People
typealias OnError = (error:String)->Unit
typealias OnSaved = (isSaved:Boolean)->Unit
typealias OnLoaded = (people:People)->Unit

interface IMyDAO {
    suspend fun save(people: People, onSaved: OnSaved, onError: OnError?):Unit
    suspend fun load(onLoaded: OnLoaded,onError: OnError?): Unit
}