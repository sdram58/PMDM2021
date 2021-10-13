package com.cartatar.navgraph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.cartatar.navgraph.databinding.FragmentLoginBinding
import android.R
import androidx.navigation.NavController
import androidx.navigation.Navigation


class LoginFragment : Fragment() {

    lateinit var binding:FragmentLoginBinding

    var navController:NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.btnLoginNext.setOnClickListener{
            navController.navigate(R.id.action_loginFragment_to_infoFragment,null)
        }
    }
}