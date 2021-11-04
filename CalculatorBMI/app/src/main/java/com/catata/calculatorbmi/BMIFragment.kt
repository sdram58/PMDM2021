package com.catata.calculatorbmi

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import com.catata.calculatorbmi.databinding.FragmentBMIBinding
import com.catata.calculatorbmi.model.MBICalculator
import com.catata.calculatorbmi.viewmodel.BMICalcultaroViewModel
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class BMIFragment : Fragment() {

    private lateinit var binding:FragmentBMIBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentBMIBinding.inflate(inflater,container,false).also {
            binding = it
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bmiCalculatorViewModel:BMICalcultaroViewModel = ViewModelProvider(this)[BMICalcultaroViewModel::class.java]

        binding.btnCalculate.setOnClickListener {

            closeKeyBoard(it)


            val mHeight = binding.etHeight.text.toString().toDouble()
            val mWeight = binding.etWeight.text.toString().toDouble()

            bmiCalculatorViewModel.calculteBMI(mWeight,mHeight)

        }

        bmiCalculatorViewModel.bmi.observe(viewLifecycleOwner){
            result ->
            val dec = DecimalFormat("#,###.00")

            binding.tvBMI.text = dec.format(result).toString()
        }

        bmiCalculatorViewModel.errorWeight.observe(viewLifecycleOwner){
            error ->
            if(error != "")
                binding.etWeight.error = error
            else
                binding.etWeight.error = null
        }

        bmiCalculatorViewModel.errorHeight.observe(viewLifecycleOwner){
                error ->
            if(error != "")
                binding.etHeight.error = error
            else
                binding.etHeight.error = null
        }
    }

    private fun closeKeyBoard(view:View){
        (activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}