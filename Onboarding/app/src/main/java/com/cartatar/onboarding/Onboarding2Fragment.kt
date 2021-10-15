package com.cartatar.onboarding

import androidx.navigation.NavController
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class Onboarding2Fragment : Fragment() {
    var btnEnd: Button? = null
    var navController: NavController? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btnEnd = view.findViewById(R.id.btnEnd)
        btnEnd?.setOnClickListener{
            navController!!.navigate(R.id.action_onboarding2Fragment_to_homeFragment)
        }
    }
}

/*
// Con ViewBinding

class Onboarding2Fragment : Fragment() {
    private var binding: FragmentOnboarding2Binding? = null
    var navController: NavController? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboarding2Binding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.btnEnd.setOnClickListener(View.OnClickListener { navController!!.navigate(R.id.action_onboarding2Fragment_to_homeFragment) })
    }
}
*/