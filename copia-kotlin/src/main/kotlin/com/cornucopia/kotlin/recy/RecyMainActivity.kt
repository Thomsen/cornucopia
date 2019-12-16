package com.cornucopia.kotlin.recy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornucopia.kotlin.R
import kotlinx.android.synthetic.main.activity_recy_main.*

class RecyMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recy_main)

        recyList.layoutManager = LinearLayoutManager(this);

//        var items = listOf<String>("a", "b")

        val items: MutableList<String> = mutableListOf("a", "b", "c")
        var x = 1;
        while (x < 100) {
//            println(x.toString())
            items.add(x.toString())
            x++;
        }

        recyList.adapter = MainAdapter(items);
    }

    /**
     * support library version 26.1.0 -> 27.1.1
     *  public abstract void onBindViewHolder(VH holder, int position);
     *  ->
     *  public abstract void onBindViewHolder(@NonNull VH holder, int position);
     *  not need holder? and use holder!!
     *
     *  @NonNull from support library version 19.1
     */

    class MainAdapter(val items: List<String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

        var isTheMiddle = false;

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = items[position]
            Log.d("thom", "onBindViewHolder: reuse " + holder.textView.tag);
            Log.d("thom", "onBindViewHolder: put " + items.get(position));

            holder.textView.setTag(items.get(position));

            if (position == (items.size / 2)) {
                holder.setInTheMiddle(true);
                holder.textView.text = "item in the middle";
            } else {
                holder.setInTheMiddle(false);
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            Log.d("thom", "onCreateViewHolder");
            return ViewHolder(TextView(parent.context));
        }

        override fun getItemCount(): Int = items.size

        override fun onViewRecycled(holder: ViewHolder) {
            Log.d("thom", "onViewRecycled: " + holder.textView.text
                    + ", position: " + holder.adapterPosition );
        }

        inner class ViewHolder(val textView : TextView) : RecyclerView.ViewHolder(textView) {

            fun isInTheMiddle(): Boolean {
                return isTheMiddle;  // inner can visit outter variable
            }

            fun setInTheMiddle(isMiddle: Boolean) {
                isTheMiddle = isMiddle;
            }
        }
    }
}
