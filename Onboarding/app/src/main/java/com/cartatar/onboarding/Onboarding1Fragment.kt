package com.cartatar.onboarding

import androidx.navigation.NavController
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

import androidx.navigation.Navigation

class Onboarding1Fragment : Fragment() {
    var btnNext: Button? = null
    var tvSkip: TextView? = null
    var navController: NavController? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btnNext = view.findViewById(R.id.btnNext)
        tvSkip = view.findViewById(R.id.tvSkip)
        btnNext?.setOnClickListener{
            navController!!.navigate(R.id.action_onboarding1Fragment_to_onboarding2Fragment)
        }

        tvSkip?.setOnClickListener {
            navController!!.navigate(R.id.action_onboarding1Fragment_to_homeFragment)
        }
    }
}

/*
// Con ViewBinding

class Onboarding1Fragment : Fragment() {
    private var binding: FragmentOnboarding1Binding? = null
    var navController: NavController? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboarding1Binding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.btnNext.setOnClickListener(View.OnClickListener { navController!!.navigate(R.id.action_onboarding1Fragment_to_onboarding2Fragment) })
    }
}
*/
