package com.cartatar.navgraph

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.cartatar.navgraph.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    lateinit var binding: FragmentInfoBinding

    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = (context as Activity)
        activity.title = "Info"
        if(activity is ChangeToolbar){
            activity.changeToolbar(true)
        }


        binding.btnInfoNext.setOnClickListener{
            val navController = Navigation.findNavController(it)
            val action = InfoFragmentDirections
                .actionInfoFragmentToHomeFragment(Login())
            navController?.navigate(action)
        }
    }
}