package com.cartatar.navgraph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import androidx.appcompat.app.ActionBar
import androidx.navigation.fragment.navArgs
import com.cartatar.navgraph.databinding.FragmentHomeBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private val args: HomeFragmentArgs by navArgs()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val activity = context as AppCompatActivity

        val actionBar: ActionBar? = activity.supportActionBar
        actionBar?.show()
        activity.title = "My Nav Graph App"


        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvInfo.text = "Welcome:\n\nUser: ${args.loginInfo.usr}\nPass: ${args.loginInfo.pass}\nEmail: ${args.loginInfo.email}\nBirth date: ${args.loginInfo.birthDate}\n"

    }





}