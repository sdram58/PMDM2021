package com.catata.firebaseexample.models

import java.io.Serializable
import java.util.HashMap

data class Users(
    var id:String?,
    val email: String,
    val provider: String,
    val address: String,
    val phone: String,
){

    //We need an default constructor to retrieve data from
    constructor():this(null,"","","","")

    override fun equals(other: Any?): Boolean {
        return (other is Users && other.id == this.id)
    }
}
