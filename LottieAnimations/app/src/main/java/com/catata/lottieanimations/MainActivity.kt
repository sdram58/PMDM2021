package com.catata.lottieanimations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.catata.lottieanimations.databinding.ActivityMainBinding
const val ANIMATION_TAG = "ANIMATION"
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)

        //variable that stores if we have pushed on first like
        var like = false

        //First like handler
        binding.ivLike1.setOnClickListener {
            like = likeAnimation(binding.ivLike1, R.raw.bandai_dokkan, like)
        }

        var like2 = false

        binding.ivLike2.setOnClickListener {
            like2 = likeAnimation(binding.ivLike2, R.raw.apple_event, like2)
        }

        var like3 = false

        binding.ivLike3.setOnClickListener {
            like3 = likeAnimation(binding.ivLike3, R.raw.black_joy, like3)
        }

        var like4 = false

        binding.ivLike4.setOnClickListener {
            like4 = likeAnimation(binding.ivLike4, R.raw.always_proud, like4)
        }

        var like5 = false

        binding.ivLike5.setOnClickListener {
            like5 = likeAnimation(it as LottieAnimationView, R.raw.hmm, like5)
        }

        //Additionally we can know when animation starts or ends
        binding.ivLike4.addAnimatorListener(object:AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                Log.i(ANIMATION_TAG, "Animation begins ${animation!!.duration}")
            }
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationStart(animation)
                Log.i(ANIMATION_TAG, "Animation ends")
            }

            //.....more events listener
        })
    }

    //Private function that we pass the imageview and the animation associated and the like status
    private fun likeAnimation(
        imageView: LottieAnimationView,
        animation: Int,
        like: Boolean,
    ) : Boolean {

        if (!like) { //If like is not pushed starts lottie animation
            imageView.setAnimation(animation)
            imageView.playAnimation()
        } else { //If like was pushed the uncheck it with an animation
                 //applying an alpha
            imageView.animate()
                .alpha(0f) //hide the image view in 200ms
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() { //Adding some listener

                    override fun onAnimationEnd(animator: Animator) {//when this animation finishes

                        imageView.setImageResource(R.drawable.twitter_like) //reset original image
                        imageView.alpha = 1f //reset alpha
                    }


                })

        }

        return !like //returns if like is checked or not
    }
}