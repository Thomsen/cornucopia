package com.cornucopia.kotlin.nav

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.os.bundleOf
import androidx.util.rangeTo
import com.cornucopia.kotlin.R
import com.cornucopia.kotlin.R.id.btn_to_act
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_first.*


class NavigationFirstFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val convertView = inflater.inflate(R.layout.fragment_first, container, false);
        return convertView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOperator(view)
    }

    private fun initOperator(convertView: View?): Boolean {

        val btnNavFrag = convertView!!.findViewById(R.id.btn_nav_frag) as Button;

        RxView.clicks(btnNavFrag)
                .subscribe(Consumer<Any>() {
                    NavHostFragment.findNavController(this)
//                            .navigate(R.id.navigationSecondFragment)
                            .navigate(R.id.action_navigationFirstFragment_to_navigationSecondFragment)
                })

        var bundle = bundleOf()
        bundle.putString("message", "from first fragment")
        (btn_to_act as Button).setOnClickListener(Navigation
                .createNavigateOnClickListener(R.id.navigationSecondActivity, bundle))


        return true;
    }
}