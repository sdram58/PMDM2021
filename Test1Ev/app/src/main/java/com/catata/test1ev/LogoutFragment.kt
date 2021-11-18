package com.catata.test1ev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.catata.test1ev.databinding.FragmentLogoutBinding
import com.catata.test1ev.viewmodel.LoginViewModel


class LogoutFragment : Fragment() {

    private lateinit var binding:FragmentLogoutBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentLogoutBinding.inflate(

            inflater,
            container,
            false
        ).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnLogout.setOnClickListener {
            requireActivity().finish()
        }
    }


}