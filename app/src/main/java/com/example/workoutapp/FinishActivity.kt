package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.example.workoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

        val hDao=(application as WorkOutApp).db.historyDao()
        addDateToDatabase(hDao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao){

        val cal=Calendar.getInstance()
        val dateTime=cal.time
        Log.e("Date:",""+dateTime)

        val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date=sdf.format(dateTime)
        Log.e("Date:",""+date)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e("Date","Added")
        }
    }
}