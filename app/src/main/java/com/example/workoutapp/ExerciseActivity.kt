package com.example.workoutapp

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityExerciseBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var bindingExercise : ActivityExerciseBinding? = null

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currExercisePosition = -1

    private var restTimer : CountDownTimer? = null
    private val restDuration : Long = 1
    private var restProgress : Int = 0

    private var exerciseTimer : CountDownTimer? = null
    private val exerciseDuration : Long = 1
    private var exerciseProgress : Int = 0

    private var tts : TextToSpeech? = null
    private var player : MediaPlayer? = null
    private var soundUri : Uri? = null

    private var exerciseAdapter : ExerciseStatusAdapter? = null

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

        exerciseList = Constants.defaultExerciseList()

        tts= TextToSpeech(this,this)
        try {
            soundUri = Uri.parse("android.resource://com.example.workoutapp/"
                    + R.raw.press_start)
            player = MediaPlayer.create(applicationContext,soundUri)
            player?.isLooping = false
        }catch (e:Exception){
            e.printStackTrace()
        }
        setupRestView()
        /** As in this we are using exerciseList so make sure that we calling the function after
         * assigning the exerciseList.
         * Alternative would be checking if our list is empty and if is not then only call function.*/
        setupExerciseStatusRecyclerView()
    }

    private fun setupExerciseStatusRecyclerView(){
        bindingExercise?.rvExerciseStatus?.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        bindingExercise?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView(){
        player?.start()
        bindingExercise?.flRestView?.visibility = View.VISIBLE
        bindingExercise?.tvTitle?.visibility = View.VISIBLE
        bindingExercise?.tvUpcomingLabel?.visibility = View.VISIBLE
        bindingExercise?.tvUpcomingExerciseName?.visibility = View.VISIBLE
        bindingExercise?.ivImage?.visibility = View.INVISIBLE
        bindingExercise?.tvExerciseName?.visibility = View.INVISIBLE
        bindingExercise?.flExerciseView?.visibility = View.INVISIBLE
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }
        bindingExercise?.tvUpcomingExerciseName?.setText(
            exerciseList!![currExercisePosition+1].getName())
        setRestProgressBar()
    }

    private fun setRestProgressBar() {
        bindingExercise?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(restDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                bindingExercise?.progressBar?.progress = ((restDuration - restProgress).toInt())
                bindingExercise?.tvTimer?.text = (restDuration- restProgress).toString()
            }

            override fun onFinish() {
                currExercisePosition++
                exerciseList!![currExercisePosition].setIsSelected(true)
                /** It will notify the recycler that data has changed and load the view again
                 * based on changed values
                 * We are notifying the view as it is data driven,we are not changing it manually */
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView(){
        /** INVISIBLE - will just make the view invisible but it will still stay inside of the view
         * GONE - will make view outside of the layout..its like deleting the view for that moment.*/
        bindingExercise?.flRestView?.visibility = View.INVISIBLE
        bindingExercise?.tvTitle?.visibility = View.INVISIBLE
        bindingExercise?.ivImage?.visibility = View.VISIBLE
        bindingExercise?.tvUpcomingLabel?.visibility = View.INVISIBLE
        bindingExercise?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        bindingExercise?.tvExerciseName?.visibility = View.VISIBLE
        bindingExercise?.flExerciseView?.visibility = View.VISIBLE
        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }

        speakOut(exerciseList!![currExercisePosition].getName() )

        bindingExercise?.ivImage?.setImageResource(exerciseList!![currExercisePosition].getImage())
        bindingExercise?.tvExerciseName?.setText(exerciseList!![currExercisePosition].getName())
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        bindingExercise?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++

                bindingExercise?.progressBarExercise?.progress =
                    ((exerciseDuration - exerciseProgress).toInt())

                bindingExercise?.tvTimerExercise?.text =
                    (exerciseDuration - exerciseProgress).toString()
            }

            override fun onFinish() {

                if(currExercisePosition < (exerciseList?.size!!-1)){
                    exerciseList!![currExercisePosition].setIsSelected(false)
                    exerciseList!![currExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                }
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

        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player!=null){
            player?.stop()
        }

        bindingExercise = null
    }

    override fun onInit(status: Int) {

        if(status==TextToSpeech.SUCCESS){
            tts!!.setSpeechRate(1.0F)
            val result = tts!!.setLanguage(Locale.ENGLISH)
            if(result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The Language Specified is not spported")
            }
        }
        else{
            Log.e("TTS","Initialization Failed!")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
}