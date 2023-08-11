package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var bindingExercise : ActivityExerciseBinding? = null
    private var restTimer : CountDownTimer? = null
    private val restDuration : Long = 10000
    private var restProgress : Int = 0

    private var exerciseTimer : CountDownTimer? = null
    private val exerciseDuration : Long = 30000
    private var exerciseProgress : Int = 0
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

    private fun setupExerciseView(){

        /** INVISIBLE - will just make the view invisible but it will still stay inside of the view
         * GONE - will make view outside of the layout..its like deleting the view for that moment.*/
        bindingExercise?.flRestView?.visibility = View.INVISIBLE
        bindingExercise?.tvTitle?.text = "Exercise Name"
        bindingExercise?.flExerciseView?.visibility = View.VISIBLE
        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }
        setExerciseProgressBar()
    }
    private fun setRestProgressBar() {
        bindingExercise?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(restDuration,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                bindingExercise?.progressBar?.progress = ((restDuration/1000 - restProgress).toInt())
                bindingExercise?.tvTimer?.text = (restDuration/1000 - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,
                    "Here we will Start Exercise", Toast.LENGTH_SHORT).show()
                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar() {
        bindingExercise?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseDuration,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++

                bindingExercise?.progressBarExercise?.progress =
                    ((exerciseDuration/1000 - exerciseProgress).toInt())

                bindingExercise?.tvTimerExercise?.text =
                    (exerciseDuration/1000 - exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,
                    "30 seconds over, lets go to rest view", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }

        bindingExercise = null
    }
}