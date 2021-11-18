package com.catata.test1ev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.catata.test1ev.databinding.FragmentInfoBinding
import com.catata.test1ev.viewmodel.LoginViewModel


class InfoFragment : Fragment() {

        private lateinit var binding:FragmentInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentInfoBinding.inflate(
            inflater,
            container,
            false
        ).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val id = requireActivity().intent.getIntExtra(
            HomeActivity.ID_PARAM,-1
        )

        viewModel.getUserById(id)

        viewModel.userLD.observe(viewLifecycleOwner){
            user ->
            user?.let {
                binding.tvInfo.text = "User: ${it.username}\nWith password: ${it.pass}"
            }
        }

        viewModel.isLoadingLD.observe(viewLifecycleOwner){
                isLoading ->
            if(isLoading)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        }
    }

}