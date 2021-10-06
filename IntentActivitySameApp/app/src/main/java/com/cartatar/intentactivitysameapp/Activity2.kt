package com.cartatar.intentactivitysameapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cartatar.intentactivitysameapp.MainActivity.Companion.KEY_EXTRA_NAME
import com.cartatar.intentactivitysameapp.MainActivity.Companion.KEY_EXTRA_SURNAME
import com.cartatar.intentactivitysameapp.databinding.Activity2Binding
import com.cartatar.intentactivitysameapp.MainActivity.Companion.KEY_EXTRA_RESULT
import com.cartatar.intentactivitysameapp.MainActivity.Companion.KEY_EXTRA_RESULT_PARCELABLE


class Activity2 : AppCompatActivity() {
    private lateinit var binding:Activity2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var myText:String? = null

        if(intent.hasExtra(KEY_EXTRA_NAME))
            myText = "${intent.getStringExtra(KEY_EXTRA_NAME).toString()}"

        if(intent.hasExtra(KEY_EXTRA_SURNAME))
            myText = "${myText?:""} ${intent.getStringExtra(KEY_EXTRA_SURNAME).toString()}"

        if(intent.hasExtra(KEY_EXTRA_RESULT_PARCELABLE)){
            val person = intent.getParcelableExtra<Person>(KEY_EXTRA_RESULT_PARCELABLE)
            person.let{person ->
                myText = "${person?.name} ${person?.surname}"
            }
        }

        binding.tvTop.text = myText?:"No user"



/*        binding.btnBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }*/

        binding.btnBack.setOnClickListener{

            val text = binding.etName.text.toString()
            val returnIntent = Intent().apply {
                putExtra(KEY_EXTRA_RESULT, text)
            }   //Creates a new Intent with editText content as extra

            if(text != "")
                setResult(RESULT_OK, returnIntent) //The action went ok.
            else
                setResult(RESULT_CANCELED, returnIntent)

            finish() //Finish and close this activity

        }
    }
}