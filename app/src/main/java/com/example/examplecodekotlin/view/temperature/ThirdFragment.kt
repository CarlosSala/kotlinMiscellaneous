package com.example.examplecodekotlin.view.temperature

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.examplecodekotlin.R
import com.example.examplecodekotlin.model.City
import com.example.examplecodekotlin.model.NetworkStatus
import com.google.gson.Gson


class ThirdFragment : Fragment() {

    private lateinit var context: Context

    private lateinit var tvTemperature: TextView
    private lateinit var tvCity: TextView
    private lateinit var tvWeather: TextView
    private lateinit var secondBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent(view)
        initListeners()
        getTemperature()
    }

    private fun getTemperature() {

        val idCity = arguments?.getString("idCity")

        if (NetworkStatus.checkForInternet(requireContext())) {

            volleyRequest(
                "https://api.openweathermap.org/data/2.5/weather?id=${idCity}&appid=dabb2d5bc8c2a104fb325acef1607cfe&units=metric&lang=es"
            )
        } else {
            Toast.makeText(context, "There is not network", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListeners() {

        secondBtn.setOnClickListener {
            findNavController().navigate(R.id.action_thirdFragment_to_secondFragment)
        }
    }

    private fun initComponent(view: View) {

        tvTemperature = view.findViewById(R.id.tv_temperature)
        tvCity = view.findViewById(R.id.tv_city)
        tvWeather = view.findViewById(R.id.tv_weather)
        secondBtn = view.findViewById(R.id.button_second)
    }


    private fun volleyRequest(url: String) {

        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET, url, { response ->

            Log.i("GSON", response.toString())

            try {
                val gson = Gson()
                val res = gson.fromJson(response, City::class.java)

                tvCity.text = res.name
                tvTemperature.text = getString(R.string.temperature, res.main?.temp.toString())
                tvWeather.text = res.weather?.get(0)?.description

            } catch (e: Exception) {
                Log.i("Error request", e.toString())
            }
        }, {})

        queue.add(request)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.context = context
    }
}