package com.cartatar.sharedactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cartatar.sharedactivities.databinding.ActivitySharedBinding

class SharedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySharedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared)
        binding = ActivitySharedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val data = intent
        var text:String? = null

        data?.let{
            if(data.hasExtra(Intent.EXTRA_TEXT))
                text = data.getStringExtra(Intent.EXTRA_TEXT).toString().uppercase()
            binding.tvShared.text = text
        }


        binding.btnSendBack.setOnClickListener {
            val intent = Intent().apply {
                putExtra(Intent.EXTRA_RETURN_RESULT, text?:"No text")
            }

            setResult(RESULT_OK,intent)
            finish()
        }


    }
}