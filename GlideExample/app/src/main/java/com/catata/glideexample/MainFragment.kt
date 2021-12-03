package com.catata.glideexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.bumptech.glide.Glide
import com.catata.glideexample.databinding.FragmentMainBinding
import kotlin.random.Random
const val ERROR_IMAGE = "https://bigseoagency.com/wp-content/uploads/2018/03/error-404-foxplay.png"
class MainFragment : Fragment() {
    private lateinit var binding:FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        return FragmentMainBinding.inflate(
            inflater,
            container,
            false
        ).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChangeImage.setOnClickListener {
            changeImage()
        }
    }

    private fun changeImage() {
        val url = "https://picsum.photos/200?rand=" + Random.nextInt()
        Glide.with(requireContext())
            .load(url) //Image that we want to show
            .placeholder(getDrawable(requireContext(),R.drawable.loading)) //Image that will be displayed while loading the image to be displayed
            .error(ERROR_IMAGE) //Image that we will show if something goes wrong. it is typically use a drawable image stored
            .into(binding.ivImage) //ImageView that will contain the image
    }


}