package com.cornucopia.kotlin.service

interface ILocalService {

    fun getName() : String

    fun isBound() : Boolean

    fun sendMessage(msg: String)

}