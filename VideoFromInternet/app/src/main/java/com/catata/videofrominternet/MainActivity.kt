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

    //val urlVideo = "http://techslides.com/demos/sample-videos/small.mp4"
    val urlVideo = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
    //val urlVideo = "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"
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

        binding.btnGo.setOnClickListener{
            Intent(this, ShowVideoActivity::class.java).also { startActivity(it) }
        }

    }
}