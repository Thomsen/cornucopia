package com.cornucopia.kotlin.anko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AnkoAddFragment : Fragment() {

    companion object {
        fun newInstance() : Fragment {
            val fragment = AnkoAddFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return ankoUI()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (tv_post_body as TextView).text = "anko post body"
    }

//    private fun ankoUI(): View {
//        return UI {
//            verticalLayout {
//                val name = editText() {
//
//                }.lparams(width = matchParent)
//                button("say hello") {
//                    onClick { toast("Hello ${name.text}")}
//                }
//
//                include<View>(R.layout.layout_post_item) {
//                    backgroundColor = Color.RED
//                }.lparams(width = matchParent) { margin = dip(12) }
//            }
//        }.view
//    }
}