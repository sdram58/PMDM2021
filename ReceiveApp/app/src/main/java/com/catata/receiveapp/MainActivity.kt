package com.catata.receiveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.catata.receiveapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Creating view Bindings
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Id there is an IntArray as extra with the key <Intent.EXTRA_TEXT> "android.intent.extra.TEXT"
        val num1:Int? = intent.getIntArrayExtra(Intent.EXTRA_TEXT)?.get(0)
        val num2:Int? = intent.getIntArrayExtra(Intent.EXTRA_TEXT)?.get(1)

        //
        binding.tvIncomingExtras.text = "${num1?:getString(R.string.no_number)}\n+\n${num2?:getString(R.string.no_number)}"
        binding.btnSendBack.setOnClickListener {
            val intent = Intent().apply {
                //Value that we return is num1 + num2, but if some of them is null then its value is 0
                //extra with      KEY                           VALUE
                putExtra(Intent.EXTRA_RETURN_RESULT,(num1?:0)+(num2?:0))
            }
            //       Result code, Intent
            setResult(RESULT_OK,intent)

            //In addition if something has gone wrong, for instance incoming extras were null
            //we can return another intent with another result code
            //setResult(RESULT_CANCELED, anotherIntent o same Intent with another extra)

            finish()
        }
    }
}