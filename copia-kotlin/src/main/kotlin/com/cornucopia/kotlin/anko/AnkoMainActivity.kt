package com.cornucopia.kotlin.anko

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.cornucopia.kotlin.R
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class AnkoMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(ankoUI())

        AnkoMainComponent().setContentView(this)

        val fragment = AnkoAddFragment.Companion.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.R_id_anko_fragment, fragment).commit()
    }

    private fun ankoUI(): View {
        return frameLayout() {
            textView {
                gravity = Gravity.CENTER
                text = "anko text"
            } .lparams(width = matchParent, height = wrapContent)
        }

    }
}

