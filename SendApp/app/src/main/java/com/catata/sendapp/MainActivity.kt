package com.catata.sendapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import com.catata.sendapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnFocusChangeListener {

    private lateinit var binding:ActivityMainBinding

    private var myResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK) {

            val intent: Intent? = result.data

            //When data go back we put it in the text view
            binding.tvResult.text = "${intent?.getIntExtra(Intent.EXTRA_RETURN_RESULT,-1)?.toString()}"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etNum1.onFocusChangeListener = this
        binding.etNum2.onFocusChangeListener = this

        cleanErrors()


        binding.btnSend.setOnClickListener {
            cleanErrors()

            if(checkError(binding.etNum1)) return@setOnClickListener
            if(checkError(binding.etNum2)) return@setOnClickListener

            val myData = IntArray(2)
             myData[0] = (binding.etNum1.text.toString().toInt())
             myData[1] = (binding.etNum2.text.toString().toInt())
            val myIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, myData)
            }

            val shareIntent = Intent.createChooser(//Selector that allow us choose destination app according the action and intent-filter
                myIntent,
                "Hey choose an app to uppercase") //Title of selector window

            myResultLauncher.launch(shareIntent)
        }
    }

    private fun checkError(et:EditText):Boolean{
        with(et){
            if(text.isEmpty()){
                error = getString(R.string.empty_field)
                return true
            }
            return false
        }
    }

    private fun cleanErrors() {
        binding.etNum1.error =null
        binding.etNum2.error =null
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if(p0?.hasFocus()== false){
            checkError(p0 as EditText)
        }
    }
}