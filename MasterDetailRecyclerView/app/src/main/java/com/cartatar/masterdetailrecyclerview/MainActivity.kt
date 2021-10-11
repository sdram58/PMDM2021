package com.cartatar.masterdetailrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.cartatar.masterdetailrecyclerview.model.Superhero
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(),OnItemClick {
   private val layoutList:FrameLayout by lazy { findViewById(R.id.containerList) }
   private val layoutDetail:FrameLayout? by lazy { findViewById(R.id.containerDetail) }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadRecyclerView()

        if (isLandScape()){
            loadDetailFragment(true, Superhero.getFirstID())
        }



    }

    private fun loadRecyclerView() {
        supportFragmentManager.beginTransaction()
            .replace(layoutList.id, ListFragment.newInstance(1))
            .addToBackStack(null)
            .commit()
    }

    private fun isLandScape():Boolean {
        return layoutDetail != null
    }

    private fun loadDetailFragment(twoPane:Boolean, heroID:Int){
         val id = layoutDetail?.id?:layoutList.id

        supportFragmentManager.beginTransaction()
            .replace(id, DetailFragment.newInstance(heroID))
            .addToBackStack(null)
            .commit()
    }

    override fun onItemClick(hero: Superhero) {
        Snackbar.make(layoutList, "You have clicked on ${hero.superhero}", Snackbar.LENGTH_LONG)
            .setAction("Open"){

                loadDetailFragment(isLandScape(), hero.id)

            }
            .show()
    }
}