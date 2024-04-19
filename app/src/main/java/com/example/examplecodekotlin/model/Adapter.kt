package com.example.examplecodekotlin.model

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examplecodekotlin.R

class Adapter(
    private var items: ArrayList<Fruit>, private var listener: ClickListener,
    private var longClickListener: LongClickListener
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var multiSelection = false
    private var viewHolder: ViewHolder? = null
    private var selectedItems: ArrayList<Int>? = null

    init {
        selectedItems = ArrayList()
    }

    class ViewHolder(
        var view: View,
        listener: ClickListener,
        longClickListener: LongClickListener
    ) :
        RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        var iv: ImageView? = null
        var tvName: TextView? = null
        var tvPrice: TextView? = null
        var ratingBar: RatingBar? = null

        // from interface
        private var lister: ClickListener? = null
        private var longClickListener: LongClickListener? = null

        init {
            iv = this.view.findViewById(R.id.iv_fruit)
            tvName = this.view.findViewById(R.id.tv_name)
            tvPrice = this.view.findViewById(R.id.tv_price)
            ratingBar = this.view.findViewById(R.id.ratingBar)

            this.lister = listener
            this.longClickListener = longClickListener
            this.view.setOnClickListener(this)
            this.view.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {

            this.lister?.onclick(view, adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            this.longClickListener?.longClick(view, adapterPosition)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
        viewHolder = ViewHolder(view, listener, longClickListener)
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.iv?.setImageResource(item.image)
        holder.tvName?.text = item.name
        holder.tvPrice?.text = "$ " + item.price.toString()
        holder.ratingBar?.rating = item.rating

        if (selectedItems?.contains(position)!!)
            holder.view.setBackgroundColor(Color.LTGRAY)
        else {
            holder.view.setBackgroundColor(Color.WHITE)
        }
    }

    fun initActionMode() {
        multiSelection = true
    }

    fun destroyActionMode() {
        multiSelection = false
        selectedItems?.clear()
        notifyDataSetChanged()
    }

    fun finishActionMode() {
        // delete selected element
        for (item in selectedItems!!) {
            selectedItems?.remove(item)
        }
        multiSelection = false
    }

    fun selectItem(index: Int) {
        if (multiSelection) {
            if (selectedItems?.contains(index)!!) {
                selectedItems?.remove(index)
            } else {
                selectedItems?.add(index)
            }
            notifyDataSetChanged()
        }
    }

    fun getElementNumber(): Int {
        return selectedItems?.count()!!
    }

    fun deleteSelected() {

        if (selectedItems?.count()!! > 0) {
            var itemsDeleted = ArrayList<Fruit>()
            for (index in selectedItems!!) {
                itemsDeleted.add(items[index])
            }

            items.removeAll(itemsDeleted.toSet())
            selectedItems?.clear()
        }
    }
}