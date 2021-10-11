package com.cartatar.fragmentsprogrammatically

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cartatar.fragmentsprogrammatically.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnFragmentActionsListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        binding.btnRed.setOnClickListener { loadFragment(RedFragment()) }
        binding.btnBlue.setOnClickListener { loadFragment(BlueFragment()) }
        */


        binding.btnRed.setOnClickListener { replaceFragment(RedFragment()) }
        binding.btnBlue.setOnClickListener { replaceFragment(BlueFragment()) }
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onClickFragmentButton() {
        Toast.makeText(this, "Button has been pushed", Toast.LENGTH_SHORT).show()
    }

}