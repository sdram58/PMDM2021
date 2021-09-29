package com.cartatar.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)


        //Here our app is ready
        setTheme(R.style.Theme_SplashScreen)

        setContentView(R.layout.activity_main)


    }
}