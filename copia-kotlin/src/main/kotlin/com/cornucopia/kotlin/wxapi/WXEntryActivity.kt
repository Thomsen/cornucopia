package com.cornucopia.kotlin.wxapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cornucopia.kotlin.account.AccountManager
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

class WXEntryActivity : AppCompatActivity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val wxApi = WXAPIFactory.createWXAPI(this, AccountManager.APP_ID, true)
        wxApi.handleIntent(getIntent(), this)
    }

    override fun onResp(p0: BaseResp?) {
        when (p0!!.errCode) {
            BaseResp.ErrCode.ERR_OK -> {

            }

            BaseResp.ErrCode.ERR_AUTH_DENIED,
            BaseResp.ErrCode.ERR_USER_CANCEL -> {

            }

            BaseResp.ErrCode.ERR_UNSUPPORT -> {

            }

            ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM -> {

            }
        }
    }

    override fun onReq(p0: BaseReq?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}