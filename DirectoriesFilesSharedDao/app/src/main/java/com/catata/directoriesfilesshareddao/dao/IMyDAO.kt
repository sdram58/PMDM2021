package com.catata.directoriesfilesshareddao.dao

import com.catata.directoriesfilesshareddao.model.People


interface IMyDAO {
    fun save(people: People):Unit
    fun load(): People
}