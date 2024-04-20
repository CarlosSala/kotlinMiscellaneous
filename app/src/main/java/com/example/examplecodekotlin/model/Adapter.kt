package com.example.examplecodekotlin.model

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examplecodekotlin.databinding.ItemRecyclerviewBinding

class Adapter(
    private var fruitArrayList: ArrayList<Fruit>,
    private var clickListener: ClickListener,
    private var longClickListener: LongClickListener
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var multiSelection = false
    private var selectedItems: ArrayList<Int>? = null

    init {
        selectedItems = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, clickListener, longClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val fruit = fruitArrayList[position]

        holder.bind(fruit)

        if (selectedItems?.contains(position)!!)
            holder.binding.root.setBackgroundColor(Color.LTGRAY)
        else {
            holder.binding.root.setBackgroundColor(Color.WHITE)
        }
    }

    class ViewHolder(
        var binding: ItemRecyclerviewBinding,
        listener: ClickListener,
        longClickListener: LongClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        // from interface
        private var lister: ClickListener? = null
        private var longClickListener: LongClickListener? = null

        init {
            this.lister = listener
            this.longClickListener = longClickListener
            this.binding.root.setOnClickListener(this)
            this.binding.root.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {

            this.lister?.onclick(binding.root, adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            this.longClickListener?.longClick(binding.root, adapterPosition)
            return true
        }

        fun bind(fruit: Fruit) {

            binding.ivFruit.setImageResource(fruit.image)
            binding.tvName.text = fruit.name
            binding.tvPrice.text = "$ " + fruit.price.toString()
            binding.ratingBar.rating = fruit.rating
        }
    }


    override fun getItemCount(): Int {
        return fruitArrayList.count()
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
                itemsDeleted.add(fruitArrayList[index])
            }

            fruitArrayList.removeAll(itemsDeleted.toSet())
            selectedItems?.clear()
        }
    }
}