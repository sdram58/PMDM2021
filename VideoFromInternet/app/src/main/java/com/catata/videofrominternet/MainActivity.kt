package com.catata.videofrominternet

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.catata.videofrominternet.databinding.ActivityMainBinding
import kotlin.coroutines.CoroutineContext
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val urlVideo = "https://sdram58.github.io/apuntesPMDM/unidades/UD8/assets/bola_drac.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)


        val uri: Uri = Uri.parse(urlVideo)
        with(binding.vvVideo){
            setMediaController(MediaController(this@MainActivity))
            setVideoURI(uri)
            requestFocus()
            setOnPreparedListener { mp ->
                mp.isLooping = true
                binding.vvVideo.start()
            }
        }



    }
}