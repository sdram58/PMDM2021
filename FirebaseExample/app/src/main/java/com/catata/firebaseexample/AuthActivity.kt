package com.catata.firebaseexample

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.catata.firebaseexample.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

const val TAG = "AuthActivity"
class AuthActivity : AppCompatActivity() {
    private val GOOGLE_SIGN_IN = 100
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //If we want to launch our own events to Google Analytics we can do it as follows
        //According to Firebase, it can take up to 24 hours from integration for the first events to start appearing.
        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle=Bundle().apply {
            putString("message", "Firebase integration completed")
        }
        analytics.logEvent("InitScreen",bundle)


        //Remote config
        val fireRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(60)
            .build()

        fireRemoteConfig.setConfigSettingsAsync(configSettings)

        //We're setting our defaults settings with the same key that firebase console.
        fireRemoteConfig.setDefaultsAsync(mapOf("show_error_button" to false, "error_button_text" to "Force Error"))

        //Notifications Push
        notification()

        //Authentication
        setup()

        session()

    }

    private fun notification() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "${getString(R.string.msg_token_fmt)} $token"
            Log.d(TAG, msg)
            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        //Topics to subscribe an App to a new Topic (messaging group)
        FirebaseMessaging.getInstance().subscribeToTopic("DAM2122")


        //Receive info from notification
        val url = intent.getStringExtra("url")
        url?.let{
            println("We've received this url $it in the notification")
        }


    }

    override fun onStart() {
        super.onStart()
        binding.llAuth.visibility = View.VISIBLE
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString(EMAIL, null)
        val provider = prefs.getString(PROVIDER, null)

        if(email!= null && provider != null){
            binding.llAuth.visibility = View.GONE
            showHome(email, ProviderType.valueOf(provider))
        }
    }

    private fun setup() {
        title = "Authentication"

        binding.btnRegister.setOnClickListener {
            //We should check with our restrictions, in our case just check if fields are not empty
            if(binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()){
                //Firebase auth
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?: "",ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }

            }
        }

        binding.btnLogin.setOnClickListener {
            //We should check with our restrictions, in our case just check if fields are not empty
            if(binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()){
                //Firebase auth
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?: "",ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }

            }
        }

        binding.btnGoogle.setOnClickListener {
            //Config

            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)

            googleClient.signOut()

            val signInIntent = googleClient.signInIntent
            launcher.launch(signInIntent)

            //startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle("Error")
            setMessage("An error occurred authenticating the user")
            setPositiveButton("Accept", null)
        }
        builder.create().apply { show() }

    }

    private fun showHome(email:String, provider: ProviderType){
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra(EMAIL, email)
            putExtra(PROVIDER, provider.name)
        }

        startActivity(intent)

    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try{

                    val account = task.getResult(ApiException::class.java)
                    if(account!=null){
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                            if(it.isSuccessful){
                                showHome(account.email ?: "",ProviderType.GOOGLE)
                            }else{
                                showAlert()
                            }
                        }

                    }
                }catch (apiException:ApiException){
                    showAlert()
                }
            }

    }
}