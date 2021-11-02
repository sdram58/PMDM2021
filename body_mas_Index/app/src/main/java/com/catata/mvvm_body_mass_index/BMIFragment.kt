package com.catata.mvvm_body_mass_index

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catata.mvvm_body_mass_index.databinding.FragmentBMIBinding


class BMIFragment : Fragment() {

    private lateinit var binding:FragmentBMIBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentBMIBinding
            .inflate(inflater, container, false)
                .also { binding = it }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}