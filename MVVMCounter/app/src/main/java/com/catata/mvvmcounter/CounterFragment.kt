package com.catata.mvvmcounter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.catata.mvvmcounter.databinding.FragmentCounterBinding
import com.catata.mvvmcounter.viewmodel.CounterViewModel


class CounterFragment : Fragment() {

    private lateinit var binding:FragmentCounterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentCounterBinding.inflate(inflater,container,false)
            .also {
                binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calculatorVM = ViewModelProvider(this)[CounterViewModel::class.java]
        binding.btnStartStop.setOnClickListener {
            calculatorVM.start()
        }

        calculatorVM.counterLiveData.observe(viewLifecycleOwner){
            binding.tvCounter.text = it.toString()
        }

    }

}