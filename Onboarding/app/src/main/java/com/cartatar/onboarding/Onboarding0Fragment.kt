package com.cartatar.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Onboarding0Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Onboarding0Fragment : Fragment() {
    var btnNext: Button? = null
    var tvSkip: TextView? = null
    var navController: NavController? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding0, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btnNext = view.findViewById(R.id.btnNext)
        tvSkip = view.findViewById(R.id.tvSkip)
        btnNext?.setOnClickListener{
            navController!!.navigate(R.id.action_onboarding0Fragment_to_onboarding1Fragment)
        }

        tvSkip?.setOnClickListener {
            navController!!.navigate(R.id.action_onboarding0Fragment_to_homeFragment)
        }
    }
}