package com.example.examplecodekotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.ShareActionProvider
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.examplecodekotlin.R
import com.example.examplecodekotlin.databinding.ActivityMain2Binding
import com.example.examplecodekotlin.model.CompleteListener
import com.example.examplecodekotlin.model.DownloadUrl
import com.example.examplecodekotlin.model.NetworkStatus
import com.google.android.material.snackbar.Snackbar
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.IOException

class MainActivity2 : AppCompatActivity(), CompleteListener {

    private lateinit var binding: ActivityMain2Binding

    private val url: String = "https://my-json-server.typicode.com/CarlosSala/response-json/posts"
    private var saved = "carlos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)

        setContentView(binding.root)

        initUI()
        initListeners()
    }

    private fun initUI() {

        binding.toolbar2.setTitle(R.string.app_name)
        setSupportActionBar(binding.toolbar2)

        // back button
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tvData.text = intent.getStringExtra(MainActivity.TAG)
    }

    private fun initListeners() {

        binding.btnValidate.setOnClickListener {

            if (NetworkStatus.checkForInternet(this)) {

                Toast.makeText(this, "Network is working", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "There is not network", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnNativeHttp.setOnClickListener {

            if (NetworkStatus.checkForInternet(this)) {

                DownloadUrl(this).makeNetworkCall(url)
            } else {
                Toast.makeText(this, "There is not network", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnVolley.setOnClickListener {

            if (NetworkStatus.checkForInternet(this)) {

                volleyRequest()
            } else {
                Toast.makeText(this, "There is not network", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnOkHttp.setOnClickListener {

            if (NetworkStatus.checkForInternet(this)) {

                requestOkHttp()
            } else {
                Toast.makeText(this, "There is not network", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnMainActivity3.setOnClickListener {

            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        Toast.makeText(this, saved, Toast.LENGTH_SHORT).show()

        binding.btnTestState.setOnClickListener {
            saved = "mario"
            Toast.makeText(this, saved, Toast.LENGTH_SHORT).show()
        }
    }

    // request result nativeHttp
    override fun downloadComplete(result: String) {
        // super.DownloadComplete(result)

        Log.i("NativeRequest, download completed", result)
    }

    private fun volleyRequest() {

        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(Request.Method.GET, url, { response ->

            try {
                Log.i("RequestVolley, download complete", response)

            } catch (e: Exception) {
                Log.i("RequestVolley, error", e.toString())
            }
        }, {})

        queue.add(request)
    }

    private fun requestOkHttp() {

        val client = OkHttpClient()
        val request = okhttp3.Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {

            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

                val result = response.body.string()
                Log.i("RequestOkHttp, download complete", result)

                // everything join to the main thread
                /*   this@MainActivity2.runOnUiThread {
                       try {
                           Log.i("RequestOkHttp", result)
                       } catch (e: Exception) {

                       }
                   }*/
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_toolbar_activity2, menu)

        val itemShare = menu?.findItem(R.id.share)
        val shareActionProvider =
            itemShare?.let { MenuItemCompat.getActionProvider(it) } as ShareActionProvider
        shareIntent(shareActionProvider)

        val itemSearch = menu.findItem(R.id.search)
        val viewSearch = itemSearch?.actionView as android.widget.SearchView

        viewSearch.queryHint = getString(R.string.write_your_name)

        viewSearch.setOnQueryTextFocusChangeListener { _, hasFocus ->
            Log.i("ListenerFocus", hasFocus.toString())
        }

        viewSearch.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    Log.i("TextChange", newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    Log.i("TextSubmit", query)
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    private fun shareIntent(shareActionProvider: ShareActionProvider) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "This message is shared")
        shareActionProvider.setShareIntent(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.favorite -> {
                Toast.makeText(this, "Add with favorite", Toast.LENGTH_SHORT).show()
                true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        saved = savedInstanceState.getString("saved").toString()
        Toast.makeText(this, saved, Toast.LENGTH_SHORT).show()
    }

    // Save state of variables
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("saved", saved)
    }

    // lifecycle of activity
    override fun onStart() {
        super.onStart()
        Log.i("point", "onStart happened")
    }

    override fun onResume() {
        super.onResume()
        Log.i("point", "onResume happened")
    }

    override fun onPause() {
        super.onPause()
        Log.i("point", "onPause happened")
    }

    override fun onStop() {
        super.onStop()
        Log.i("point", "onStop happened")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("point", "onDestroy happened")
    }

}
