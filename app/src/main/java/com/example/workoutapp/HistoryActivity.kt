package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.workoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var historyBinding : ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(historyBinding?.root)

        /** We are setting supportActionBar to toolbarHistoryActivity
         * so we can access toolbar with supportActionBar name*/
        setSupportActionBar(historyBinding?.toolbarHistoryActivity)
        if(supportActionBar!=null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        historyBinding?.toolbarHistoryActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        val dao=(application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch {
            historyDao.fetchALlDates().collect(){ allCompletedDatesList ->
                if(allCompletedDatesList.isNotEmpty()){
                    historyBinding?.tvHistory?.visibility = View.VISIBLE
                    historyBinding?.rvHistory?.visibility = View.VISIBLE
                    historyBinding?.tvNoDataAvailable?.visibility = View.INVISIBLE
                    historyBinding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val dates = ArrayList<String>()
                    for(date in allCompletedDatesList){
                        dates.add(date.date)
                    }
                    val historyAdapter = HistoryAdapter(dates)
                    historyBinding?.rvHistory?.adapter = historyAdapter
                }else{
                    historyBinding?.tvHistory?.visibility = View.GONE
                    historyBinding?.rvHistory?.visibility = View.GONE
                    historyBinding?.tvNoDataAvailable?.visibility = View.VISIBLE
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        historyBinding=null
    }
}