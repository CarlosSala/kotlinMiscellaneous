package com.example.examplecodekotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.examplecodekotlin.R
import com.example.examplecodekotlin.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG: String = "com.example.examplecodekotlin.MainActivity.DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        initComponent()
        initListener()
    }

    private fun initComponent() {

        setSupportActionBar(findViewById(R.id.toolbar))
    }

    private fun initListener() {

        binding.fab.setOnClickListener { view ->

            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra(TAG, "from MainActivity SnackBar")

            Snackbar.make(view, "Go to MainActivity2", Snackbar.LENGTH_LONG)
                .setAction("MainActivity2") { startActivity(intent) }.show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Handle action bar item clicks here. The action bar will
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {

            R.id.action_settings -> {

                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra(TAG, "from MainActivity menu")
                startActivity(intent)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    // lifecycle of activity
    override fun onStart() {
        super.onStart()
        Log.i("Test", "onStart happened")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Test", "onResume happened")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Test", "onPause happened")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Test", "onStop happened")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Test", "onDestroy happened")
    }
}