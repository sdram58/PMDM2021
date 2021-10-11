package com.cartatar.masterdetailrecyclerview

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.cartatar.masterdetailrecyclerview.model.Superhero

private const val ARG_HERO = "arg_HERO"


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private var superHeroId: Int? = null
    private var superHero: Superhero? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            superHeroId = it.getInt(ARG_HERO,-1)
            superHero = Superhero.getSuperHeroById(superHeroId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tvSuperhero).text = superHero?.superhero
        view.findViewById<TextView>(R.id.tvRealName).text  = superHero?.realName
        view.findViewById<TextView>(R.id.tvDescription).text  = superHero?.description

        val imageName = superHero?.photo?.split(".")?.get(0)
        val id = context?.resources?.getIdentifier(
            imageName,
            "raw",
            context?.packageName
        )

        val inputStream =context?.resources?.openRawResource(id?:0)
        val image = BitmapFactory.decodeStream(inputStream)
        view.findViewById<ImageView>(R.id.ivPoster).setImageBitmap(image)

    }




    companion object {
        @JvmStatic
        fun newInstance(heroId: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_HERO, heroId)
                }
            }
    }
}