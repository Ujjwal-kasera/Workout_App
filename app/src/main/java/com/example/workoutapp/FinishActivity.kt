package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.workoutapp.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var bindingFinish : ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingFinish = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(bindingFinish?.root)

        setSupportActionBar(bindingFinish?.toolbarFinishActivity)

        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        bindingFinish?.toolbarFinishActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        bindingFinish?.btnFinish?.setOnClickListener{
            finish()
            /** Here we will not create the intent that will move us to home page
             * because it will create useless memory in the stack of existing states.*/
        }

    }
}