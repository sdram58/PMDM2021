package com.catata.coroutineexamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.catata.coroutineexamples.databinding.ActivityMainBinding
import kotlinx.coroutines.*

const val TAG = "COROUTINES"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityMainBinding.inflate(layoutInflater).also{
                binding = it
            }.root
        )

        binding.btnLaunch.setOnClickListener {
            log("${Thread.currentThread().name} Start")
            //mLaunch()
            //main()
            val job:Job = CoroutineScope(Dispatchers.Main).launch {
                //mAsync()
                //testWithContext()
                //log("after Launch")
                writeInMainThread("Starting")

                delay(2000)

                writeInMainThread("End")

            }
            log("outside Launch")
        }
    }

    suspend fun mAsync(){

        log( "Before")
        val resultOne = CoroutineScope(Dispatchers.Main).async { function1() }
        val resultTwo = CoroutineScope(Dispatchers.Main).async { function2() }
        log( "After")
        val resultText = resultOne.await() + resultTwo.await()
        log("Async ${resultText}")
    }

    suspend fun testWithContext() {
        var resultOne = "Android"
        var resultTwo = "Kotlin"
        log("withContext -- Before")
        resultOne = withContext(Dispatchers.IO) { function1() }
        resultTwo = withContext(Dispatchers.IO) { function2() }
        log("withContext -- After")
        val resultText = resultOne + resultTwo
        log("withContext $resultText")


    }

    suspend fun writeInMainThread(texto:String) = withContext(Dispatchers.Main){
        binding.tvText.text = texto
    }

    fun mLaunch()
    {
        var resultOne = "Android"
        var resultTwo = "Kotlin"
        log( "Before")
        CoroutineScope(Dispatchers.Main).launch { resultOne = function1() }
        CoroutineScope(Dispatchers.Main).launch { resultTwo = function2() }
        log( "After")
        val resultText = resultOne + resultTwo
        log(" resultText: ${resultText}")

    }

    suspend fun function1(): String
    {
        delay(1000L)
        val message = "function1"
        log( message)
        return message
    }

    suspend fun function2(): String
    {
        delay(100L)
        val message = "function2"
        log(message)
        return message
    }

    fun log(text:String){
        Log.d(TAG,text)
    }

    fun log(character: Char) {
       log("$character")
    }

    fun main() {
        log("Start")

        runBlocking {
            val job = launch {
                repeat(3) {
                    log("Launch rep #$it : I'm active")
                    delay(1000)
                }
                log("Launch : I'm finishing.")
            }

            delay(100)
            while(job.isActive) {
                log("RunBlocking : Job is active")
                delay(1000)
            }

            log("RunBlocking : Job is not active")
        }

        log("End")
    }


}