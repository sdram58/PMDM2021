package com.cartatar.masterdetailrecyclerview.adapters

import android.content.Context
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.cartatar.masterdetailrecyclerview.OnItemClick
import com.cartatar.masterdetailrecyclerview.R

import com.cartatar.masterdetailrecyclerview.databinding.FragmentItemBinding
import com.cartatar.masterdetailrecyclerview.model.Superhero

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<Superhero>,
    private val listener:OnItemClick?
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val superhero = values[position]
        holder.tvSuperhero.text = superhero.superhero
        holder.tvRealName.text = superhero.realName
        holder.tvPublisher.text = superhero.publisher
        val imageName = superhero.photo.split(".")[0]

        val context: Context = holder.ivPoster.context
        val id = context.resources.getIdentifier(
            imageName,
            "raw",
            context.packageName
        )

        val inputStream =context.resources.openRawResource(id)
        val image = BitmapFactory.decodeStream(inputStream)
        holder.ivPoster.setImageBitmap(image)
        holder.itemView.tag = superhero
        holder.itemView.setOnClickListener(holder)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        val tvSuperhero: TextView = binding.tvSuperhero
        val tvRealName: TextView = binding.tvRealName
        val tvPublisher: TextView = binding.tvPublisher
        val ivPoster: ImageView = binding.ivPoster

        override fun toString(): String {
            return super.toString() + " ${tvRealName.text} is ${tvSuperhero.text} from ${tvPublisher.text}"
        }

        override fun onClick(v: View?) {
            val superHero = v?.tag as Superhero
            //Toast.makeText(v?.context,"You have clicked on ${superHero.superhero}",Toast.LENGTH_SHORT).show()

            listener?.onItemClick(superHero)
        }
    }

}