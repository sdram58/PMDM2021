package com.cartatar.navgraph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cartatar.navgraph.databinding.FragmentLoginBinding

import android.app.Activity
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController


class LoginFragment : Fragment() {

    lateinit var binding:FragmentLoginBinding

    var navController:NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val activity = context as AppCompatActivity

        val actionBar: ActionBar? = activity.supportActionBar
        actionBar?.hide()

        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    //In this function view is R.layout.fragment_login
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLoginNext.setOnClickListener{
            //Navigates to InfoFragment

            val login = Login()
            login.usr = binding.etUser.text.toString()
            login.pass = binding.etPass.text.toString()

            val navController = findNavController()
            val action = LoginFragmentDirections
                .actionLoginFragmentToInfoFragment(login)
            navController.navigate(action)
        }
    }
}