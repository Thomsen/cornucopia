package com.cornucopia.kotlin.account

import android.content.Context
import com.cornucopia.component.data.model.User
import com.cornucopia.kotlin.account.AccountManager.Companion.APP_ID
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.reactivex.Observable

class AccountManagerImpl(context: Context) : AccountManager {

    var wxApi = WXAPIFactory.createWXAPI(context, APP_ID, true);

    init {
        wxApi.registerApp(APP_ID)
    }

    override fun requestWxOpenId(): Observable<String> {

        val sendAuthReq = SendAuth.Req()

        sendAuthReq.scope = "snsapi_user_info"
        sendAuthReq.state = "wechat_sdk_copia"

        wxApi.sendReq(sendAuthReq)

        return Observable.fromArray("")
    }

    override fun loginWithWx(wxOpenId: String): Observable<User> {

        return Observable.create(null)
    }

}