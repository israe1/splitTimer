package com.example.splittimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var timerText: TextView
    private lateinit var splitButton: Button
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var recordList: RecyclerView
    private val handler = Handler(Looper.getMainLooper())
    private var valueMillis = 0
    private var recordAdapter = RecordAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        timerText = findViewById(R.id.timerText)
        startButton = findViewById(R.id.startButton)
        splitButton = findViewById(R.id.splitButton)
        stopButton = findViewById(R.id.stopButton)
        recordList = findViewById(R.id.recordList)

        startButton.setOnClickListener(this)
        stopButton.setOnClickListener(this)
        splitButton.setOnClickListener(this)

        recordList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recordAdapter
        }
    }

    private fun runTimer(){
        valueMillis += 1
        val millis = valueMillis % 100
        var valueSecond: Int = valueMillis / 100
        var valueMinute: Int =  valueSecond / 60
        val valueHour: Int = valueMinute / 24

        valueSecond %= 60
        valueMinute %= 60

        timerText.text = String.format("%02d:%02d:%02d:%02d", valueHour, valueMinute, valueSecond, millis)

    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                startButton.id -> {
                    valueMillis = 0
                    recordAdapter.clearRecords()
                    val runnable = object:Runnable {
                        override fun run() {
                            runTimer()
                            handler.postDelayed(this, 1)
                        }
                    }
                    handler.post(runnable)
                }
                splitButton.id -> {
                    recordAdapter.addRecord(valueMillis)
                }
                stopButton.id -> {
                    handler.removeMessages(0)
                }
                else -> {

                }
            }
        }
    }
}