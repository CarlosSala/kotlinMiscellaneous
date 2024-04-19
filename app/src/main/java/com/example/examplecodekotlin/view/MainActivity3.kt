package com.example.examplecodekotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.examplecodekotlin.R
import com.example.examplecodekotlin.model.Adapter
import com.example.examplecodekotlin.model.ClickListener
import com.example.examplecodekotlin.model.Fruit
import com.example.examplecodekotlin.model.LongClickListener

class MainActivity3 : AppCompatActivity() {

    private lateinit var rvFruit: RecyclerView
    private lateinit var rvAdapter: Adapter
    private var fruitArrayList = ArrayList<Fruit>()

    var isActionMode = false
    private var actionMode: android.view.ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        initComponent()
        initUI()

        // for contextual menu
        val callback = object : android.view.ActionMode.Callback {

            override fun onActionItemClicked(
                mode: android.view.ActionMode?,
                item: MenuItem?
            ): Boolean {

                when (item?.itemId) {
                    R.id.btn_delete -> {
                        rvAdapter.deleteSelected()
                    }

                    else -> {
                        return true
                    }
                }

                rvAdapter.finishActionMode()
                mode?.finish()
                isActionMode = false

                return true
            }

            override fun onCreateActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {

                // init actionMode
                rvAdapter.initActionMode()
                actionMode = mode

                // inflate menu
                menuInflater.inflate(R.menu.menu_contextual, menu)
                return true
            }

            override fun onPrepareActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {

                mode?.title = "0 selected"
                return false
            }

            override fun onDestroyActionMode(mode: android.view.ActionMode?) {

                rvAdapter.destroyActionMode()
                isActionMode = false
            }
        }

        rvAdapter = Adapter(fruitArrayList, object : ClickListener {

            override fun onclick(view: View, index: Int) {
                Toast.makeText(applicationContext, fruitArrayList[index].name, Toast.LENGTH_SHORT)
                    .show()
            }
        }, object : LongClickListener {

            override fun longClick(view: View, index: Int) {

                // Log.i("long", "test long click")
                if (!isActionMode) {

                    startActionMode(callback)
                    isActionMode = true
                    rvAdapter.selectItem(index)
                } else {
                    // selections or not
                    rvAdapter.selectItem(index)
                }

                actionMode?.title = rvAdapter.getElementNumber().toString() + " selected"
            }

        })
        rvFruit.adapter = rvAdapter


        val swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.str)
        swipeToRefresh.setOnRefreshListener {

            for (i in 1..1000000) {

            }
            swipeToRefresh.isRefreshing = false
            Log.i("refresh", "data updated")
            fruitArrayList.add(Fruit("Kiwis", 35.0, 3.5F, R.drawable.kiwis))
            rvAdapter.notifyDataSetChanged()
        }

    }

    private fun initUI() {

        rvFruit.setHasFixedSize(true)
        rvFruit.layoutManager = LinearLayoutManager(this)

        fruitArrayList.add(Fruit("Orange", 45.0, 3.6F, R.drawable.naranjas))
        fruitArrayList.add(Fruit("Apple", 55.0, 5.0F, R.drawable.manzanas))
        fruitArrayList.add(Fruit("Grapes", 24.0, 1.0F, R.drawable.uvas))
        fruitArrayList.add(Fruit("Pineapple", 75.0, 2.5F, R.drawable.pina))
        fruitArrayList.add(Fruit("Cherry", 35.0, 3.5F, R.drawable.cerezas))
        fruitArrayList.add(Fruit("Strawberry", 45.0, 3.6F, R.drawable.fresas))
        fruitArrayList.add(Fruit("Raspberry", 55.0, 5.0F, R.drawable.frambuesas))
        fruitArrayList.add(Fruit("Melon", 24.0, 1.0f, R.drawable.melon))
        fruitArrayList.add(Fruit("Watermelon", 75.0, 2.5F, R.drawable.sandia))
    }

    private fun initComponent() {

        rvFruit = findViewById(R.id.recycler_view)
    }
}