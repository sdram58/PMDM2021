package com.catata.coroutines

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catata.coroutines.databinding.FragmentCorutineBinding
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
const val TAG = "COROUTINE_TAG"

class CorutineFragment : Fragment() {

    private lateinit var binding: FragmentCorutineBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentCorutineBinding.inflate(
            inflater,
            container,
            false
        ).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvResult.text  = ""
        binding.btnLaunch.setOnClickListener {
            runBlocking {
                log("***********************withContext****************")
                testWithContext()
                delay(3000)
                log("***********************Launch****************")
                testLaunch()
                log("***********************Async****************")
                testAsync()
                //funRunBlocking()
            }

        }
    }

    private suspend fun funRunBlocking() {
        log("Before")

        GlobalScope.launch(Dispatchers.IO) {
            delay(5000)
            log("Child")
        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            log("CoroutineScope")
        }



        withContext(Dispatchers.Default){
            delay(1000)
            log("With Context")
        }

        delay(2000)

        log("Later")
    }

    private suspend fun testWithContext(){
        var resultOne = "Android"
        var resultTwo = "Kotlin"
        log("withContext Before")
        resultOne = withContext(Dispatchers.IO) { function1() }
        resultTwo = withContext(Dispatchers.IO) { function2() }
        log("withContext After")
        val resultText = "$resultOne $resultTwo"
        log("withContext resultText -> $resultText")
    }
    private suspend fun function1(): String {
        delay(1000L)
        val message = "function1"
        log("withContext -> $message")
        return message
    }
    private suspend fun function2(): String {
        delay(100L)
        val message = "function2"
        log("withContext -> $message")
        return message
    }

    private  fun testLaunch() {
        runBlocking {
            var resultOne = "Android"
            var resultTwo = "Kotlin"
            log("Launch Before")
            launch(Dispatchers.IO) {resultOne = function1Launch() }
            launch(Dispatchers.IO) {resultTwo = function2Launch() }
            log("Launch After")
            val resultText = resultOne + resultTwo
            log("Launch resultText $resultText")
        }


    }
    private suspend fun function1Launch(): String {
        delay(1000L)
        val message = "function1Launch"
        log("Launch $message")
        return message
    }
    private suspend fun function2Launch(): String {
        delay(100L)
        val message = "function2Launch"
        log("Launch $message")
        return message
    }


    private fun testAsync() {
        runBlocking {
            log("Async Before")
            val resultOne = async(Dispatchers.IO) { function1Async() }
            val resultTwo = async(Dispatchers.IO) { function2Async() }
            log("Async After")
            val resultText = resultOne.await() + " " +  resultTwo.await()
            log("Async $resultText")
        }
    }
    private suspend fun function1Async(): String {
        delay(1000L)
        val message = "function1Async"
        log("Async $message")
        return message
    }
    private suspend fun function2Async(): String {
        delay(100L)
        val message = "function2Async"
        log("Async $message")
        return message
    }


    private fun log(text:String) = Log.d(TAG,"${Thread.currentThread().name} --> $text")


}