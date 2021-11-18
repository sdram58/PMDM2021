package com.catata.test1ev

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.catata.test1ev.databinding.FragmentLoginBinding
import com.catata.test1ev.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentLoginBinding.inflate(
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

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val pass = binding.etPassword.text.toString()

            viewModel.checkUser(username,pass)
        }

        viewModel.isLoadingLD.observe(viewLifecycleOwner){isLoading->
            if(isLoading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.userLD.observe(viewLifecycleOwner){user->
            user?.let {
                val i : Intent = Intent(requireActivity(),Home2Activity::class.java)
                i.putExtra(HomeActivity.ID_PARAM, user.id)
                requireActivity().startActivity(i)
            } ?: run{
                Snackbar.make(binding.root,"User doesn't exist", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etPassword.setText("")
        binding.etUsername.setText("")
    }

}