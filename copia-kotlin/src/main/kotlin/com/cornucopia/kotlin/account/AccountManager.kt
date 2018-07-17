package com.cornucopia.kotlin.account

import com.cornucopia.component.data.model.User
import io.reactivex.Observable

interface AccountManager {

    companion object {
        val APP_ID = "wxd930ea5d5a258f4f"
    }

    fun requestWxOpenId(): Observable<String>

    fun loginWithWx(wxOpenId: String): Observable<User>

}