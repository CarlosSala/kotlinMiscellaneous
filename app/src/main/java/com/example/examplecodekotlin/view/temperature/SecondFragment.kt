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
import com.example.examplecodekotlin.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {

        binding.btnChile.setOnClickListener {

            val idSantiago = "3871336"
            val bundle = bundleOf("idCity" to idSantiago)

            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment, bundle)
        }

        binding.btnUsa.setOnClickListener {

            val idNewYork = "5128581"
            val bundle = bundleOf("idCity" to idNewYork)

            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment, bundle)
        }
    }

}