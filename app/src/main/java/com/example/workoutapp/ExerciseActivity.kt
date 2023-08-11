package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var bindingExercise : ActivityExerciseBinding? = null
    private var restTimer : CountDownTimer? = null
    private var restProgress : Int = 0
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
        setupRestView()
    }

    private fun setupRestView(){
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }
    private fun setRestProgressBar() {
        bindingExercise?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                bindingExercise?.progressBar?.progress = 10 - restProgress
                bindingExercise?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,
                    "Here we will Start Exercise", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }
        bindingExercise = null
    }
}