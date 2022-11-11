package com.melyseev.factnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

class MainActivity : AppCompatActivity(), EnterNumberFragment.ShowDetails{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(savedInstanceState!=null){

            val pa= savedInstanceState.get("PARAM1")
            println("param1 = $pa")
        }

        if (savedInstanceState == null) {
            val enterNumberFragment = EnterNumberFragment.newInstance("1")
            supportFragmentManager.beginTransaction()
                .add(R.id.container, enterNumberFragment)
                .commit()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun showDetails() {
        val enterNumberFragment = DetailsNumberFragment.newInstance("DETAILS")
        supportFragmentManager.beginTransaction()
            .add(R.id.container, enterNumberFragment)
            .addToBackStack(DetailsNumberFragment::class.simpleName)
            .commit()
    }
}