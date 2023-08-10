package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var bindingExercise : ActivityExerciseBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingExercise=ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(bindingExercise?.root)
        setSupportActionBar(bindingExercise?.toolbarExercise)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        bindingExercise?.toolbarExercise?.setNavigationOnClickListener(){
            onBackPressed()
        }
    }
}