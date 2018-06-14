package com.cornucopia.kotlin.anko

import android.view.View
import com.cornucopia.kotlin.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.linearLayout

class AnkoMainComponent : AnkoComponent<AnkoMainActivity> {

    override fun createView(ui: AnkoContext<AnkoMainActivity>): View = ui.apply {

        linearLayout() {
            id = R.id.R_id_anko_fragment
        }

    }.view

}