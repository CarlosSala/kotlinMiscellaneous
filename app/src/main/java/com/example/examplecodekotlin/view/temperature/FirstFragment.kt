package com.example.examplecodekotlin.view.temperature

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.examplecodekotlin.R
import androidx.navigation.fragment.findNavController
import com.example.examplecodekotlin.databinding.FragmentFirstBinding
import com.example.examplecodekotlin.model.People
import com.google.gson.Gson
import org.json.JSONObject
import com.example.examplecodekotlin.model.Person


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    private var peopleList: ArrayList<Person>? = null

    private val response = "{ \"people\" : [ " +
            "{" +
            " \"name\" : \"Marcos\" ," +
            " \"country\" : \"Mexico\" ," +
            " \"state\" : \"single\" ," +
            " \"experience\" : 5}," +

            "{" +
            " \"name\" : \"Agustin\" ," +
            " \"country\" : \"Spain\" ," +
            " \"state\" : \"married\" ," +
            " \"experience\" : 16}" +
            " ]" +
            " }"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()

        jsonParseGson()
        nativeParseGson()
    }

    private fun initListener() {

        binding.btnNext.setOnClickListener {

            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }


    private fun jsonParseGson() {

        val gson = Gson()
        val res = gson.fromJson(response, People::class.java)

        res.people?.forEach { it ->

            Log.i(
                "People Gson: ",
                it.name + " " + it.country + " " + it.state + " " + it.experience
            )
        }
    }

    private fun nativeParseGson() {

        val json = JSONObject(response)
        val people = json.getJSONArray("people")
        peopleList = ArrayList()

        for (i in 0 until people.length()) {

            val name = people.getJSONObject(i).getString("name")
            val country = people.getJSONObject(i).getString("country")
            val state = people.getJSONObject(i).getString("state")
            val experience = people.getJSONObject(i).getInt("experience")

            val person = Person(name, country, state, experience)
            peopleList?.add(person)
            Log.i(
                "People native: ",
                person.name + " " + person.country + " " + person.state + " " + person.experience
            )
        }
        Log.i("List of people: ", peopleList?.count().toString())
    }
}