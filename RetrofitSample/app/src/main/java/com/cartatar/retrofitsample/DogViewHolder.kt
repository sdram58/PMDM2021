package com.cartatar.retrofitsample

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cartatar.retrofitsample.databinding.ItemDogBinding

import fromUrl

class DogViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemDogBinding.bind(view)


    fun bind(image:String){
        //Extension function
        binding.ivDog.fromUrl(image)
    }
}