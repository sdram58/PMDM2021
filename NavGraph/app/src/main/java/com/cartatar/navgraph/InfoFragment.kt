package com.cartatar.navgraph

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.cartatar.navgraph.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private val args:InfoFragmentArgs by navArgs()

    lateinit var binding: FragmentInfoBinding

    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val activity = context as AppCompatActivity

        val actionBar: ActionBar? = activity.supportActionBar
        actionBar?.hide()

        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val login = args.loginInfo
        login.email = binding.etEmail.text.toString()
        login.birthDate = binding.etBirthDate.text.toString()


        binding.btnInfoNext.setOnClickListener{
            navController = Navigation.findNavController(it)
            val action = InfoFragmentDirections
                .actionInfoFragmentToHomeFragment(login)
            navController?.navigate(action)
            //navController?.navigate(R.id.action_infoFragment_to_homeFragment)
        }
    }
}