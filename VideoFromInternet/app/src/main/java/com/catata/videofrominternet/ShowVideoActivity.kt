package com.catata.videofrominternet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import android.media.MediaPlayer

import android.widget.VideoView

import android.os.Build

import android.net.Uri
import android.webkit.URLUtil
import android.widget.MediaController

import android.widget.TextView




class ShowVideoActivity : AppCompatActivity() {
    private val VIDEO_SAMPLE =
        "https://www.youtube.com/watch?v=HexFqifusOk&list=RDHexFqifusOk&start_radio=1"
    private var mVideoView: VideoView? = null
    //private var mBufferingTextView: TextView? = null

    // Current playback position (in milliseconds).
    private var mCurrentPosition = 0

    // Tag for the instance state bundle.
    private val PLAYBACK_TIME = "play_time"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_video)
        mVideoView = findViewById(R.id.vvVideo2)


        // Set up the media controller widget and attach it to the video view.
        val controller = MediaController(this)
        controller.setMediaPlayer(mVideoView)
        mVideoView?.setMediaController(controller)

        //mBufferingTextView = findViewById(R.id.buffering_textview);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
    }


    override fun onStart() {
        super.onStart()

        // Load the media each time onStart() is called.
        initializePlayer()
    }

    override fun onPause() {
        super.onPause()

        // In Android versions less than N (7.0, API 24), onPause() is the
        // end of the visual lifecycle of the app.  Pausing the video here
        // prevents the sound from continuing to play even after the app
        // disappears.
        //
        // This is not a problem for more recent versions of Android because
        // onStop() is now the end of the visual lifecycle, and that is where
        // most of the app teardown should take place.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView!!.pause()
        }
    }

    override fun onStop() {
        super.onStop()

        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        releasePlayer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, mVideoView!!.currentPosition)
    }

    private fun initializePlayer() {
        // Show the "Buffering..." message while the video loads.
        //mBufferingTextView!!.visibility = VideoView.VISIBLE

        // Buffer and decode the video sample.
        val videoUri: Uri = getMedia(VIDEO_SAMPLE)
        mVideoView!!.setVideoURI(videoUri)

        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView!!.setOnPreparedListener { // Hide buffering message.
            //mBufferingTextView!!.visibility = VideoView.INVISIBLE

            // Restore saved position, if available.
            if (mCurrentPosition > 0) {
                mVideoView!!.seekTo(mCurrentPosition)
            } else {
                // Skipping to 1 shows the first frame of the video.
                mVideoView!!.seekTo(1)
            }

            // Start playing!
            mVideoView!!.start()
        }

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        mVideoView!!.setOnCompletionListener {
            Toast.makeText(this@ShowVideoActivity,
                "Completed",
                Toast.LENGTH_SHORT).show()

            // Return the video position to the start.
            mVideoView!!.seekTo(0)
        }
    }


    // Release all media-related resources. In a more complicated app this
    // might involve unregistering listeners or releasing audio focus.
    private fun releasePlayer() {
        mVideoView!!.stopPlayback()
    }

    // Get a Uri for the media sample regardless of whether that sample is
    // embedded in the app resources or available on the internet.
    private fun getMedia(mediaName: String): Uri {
        return if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            Uri.parse(mediaName)
        } else {

            // you can also put a video file in raw package and get file from there as shown below
            Uri.parse("android.resource://" + packageName +
                    "/raw/" + mediaName)
        }
    }
}