package com.example.examplecodekotlin.view.temperature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.examplecodekotlin.R

class SecondFragment : Fragment() {

    private lateinit var btnChile: Button
    private lateinit var btnUsa: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent(view)
        initListeners()
    }

    private fun initListeners() {

        btnChile.setOnClickListener {

            val idSantiago = "3871336"
            val bundle = bundleOf("idCity" to idSantiago)

            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment, bundle)
        }

        btnUsa.setOnClickListener {

            val idNewYork = "5128581"
            val bundle = bundleOf("idCity" to idNewYork)

            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment, bundle)
        }
    }

    private fun initComponent(view: View) {

        btnChile = view.findViewById(R.id.btn_chile)
        btnUsa = view.findViewById(R.id.btn_usa)
    }
}