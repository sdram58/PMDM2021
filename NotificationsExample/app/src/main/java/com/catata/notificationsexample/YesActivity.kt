package com.catata.notificationsexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.catata.notificationsexample.databinding.ActivityYesBinding

class YesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityYesBinding.inflate(layoutInflater).also { binding = it }.root)
        title = "Yes activity"

        intent.getStringExtra(YES_EXTRA_KEY)?.let {
            binding.tvYes.text = it
        }
    }
}