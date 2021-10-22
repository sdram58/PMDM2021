package com.cartatar.intentactivitysameapp

import android.app.Activity
import android.app.Notification
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.cartatar.intentactivitysameapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    private val userName:String = "Carlos"
    private val surName:String = "Tarazona"

    companion object{
        val KEY_EXTRA_NAME:String ="MY_KEY_EXTRA_NAME"
        val KEY_EXTRA_SURNAME:String ="MY_KEY_EXTRA_SURNAME"
        val KEY_EXTRA_RESULT:String ="MY_KEY_EXTRA_RESULT"
        val KEY_EXTRA_RESULT_PARCELABLE:String ="MY_KEY_EXTRA_PARCELABLE"
    }

    var myResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            //When data go back we put it in the text view
            binding.tvGreeting.text = "${data?.getStringExtra(Intent.EXTRA_RETURN_RESULT)}"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            sendParcelablePerson()
            //sendShared()

        }
    }



    private fun sendShared() {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=39.4295152,-0.4660814?z=14(Treasure)")
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }

        /*val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hi there! pass that to uppercase and send back.")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(//Selector that allow us choose destination app according the action and intent-filter
            sendIntent,
            "Hey choose an app to uppercase") //Title of selector window
        myResultLauncher.launch(shareIntent)*/
    }

    private fun sendParcelablePerson() {
        val person = Person("Carlos", "Tarazona")
        val intent = Intent(this, Activity2::class.java).apply {
            putExtra(KEY_EXTRA_RESULT_PARCELABLE, person)
        }

        startActivity(intent)
    }

    private fun openActivity2() {
        val intent = Intent(this, Activity2::class.java).apply {
            putExtra(KEY_EXTRA_NAME, userName)
            putExtra(KEY_EXTRA_SURNAME, surName)
        }

/*        val bundle = Bundle()
        bundle.putString(KEY_EXTRA_NAME,userName)
        intent.putExtras(bundle)

        //or directly
        intent.putExtra(KEY_EXTRA_SURNAME, surName)*/

        startActivity(intent)
    }


    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            binding.tvGreeting.text = "Hello ${data?.getStringExtra(KEY_EXTRA_RESULT)?:"No return"}"
        }
    }



    fun openSomeActivityForResult() {
        val intent = Intent(this, Activity2::class.java)
        resultLauncher.launch(intent)
    }
}