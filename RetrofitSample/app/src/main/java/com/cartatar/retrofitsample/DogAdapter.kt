package com.cartatar.retrofitsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.*

class DogAdapter(val images:List<String>): Adapter<DogViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)

        return DogViewHolder(layoutInflate.inflate(R.layout.item_dog,parent, false))

    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item:String = images[position]

        holder.bind(item)
    }

}