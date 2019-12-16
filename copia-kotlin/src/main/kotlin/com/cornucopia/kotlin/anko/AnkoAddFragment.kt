package com.cornucopia.kotlin.anko

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cornucopia.kotlin.R
import kotlinx.android.synthetic.main.layout_post_item.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class AnkoAddFragment : Fragment() {

    companion object {
        fun newInstance() : Fragment {
            val fragment = AnkoAddFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ankoUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (tv_post_body as TextView).text = "anko post body"
    }

    private fun ankoUI(): View {
        return UI {
            verticalLayout {
                val name = editText() {

                }.lparams(width = matchParent)
                button("say hello") {
                    onClick { toast("Hello ${name.text}")}
                }

                include<View>(R.layout.layout_post_item) {
                    backgroundColor = Color.RED
                }.lparams(width = matchParent) { margin = dip(12) }
            }
        }.view
    }
}