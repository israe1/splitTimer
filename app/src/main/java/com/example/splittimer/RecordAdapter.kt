package com.example.splittimer

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordAdapter(private var _records: ArrayList<Int>):
    RecyclerView.Adapter<RecordAdapter.RecordViewHolder>(){
    class RecordViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var itemRecordText:TextView = view.findViewById(R.id.itemRecordText)
        private var indexText:TextView = view.findViewById(R.id.indexText)

        fun bind(index: Int, timeMillis: Int, ){
            indexText.text = index.toString()
            val millis = timeMillis % 100
            var valueSecond: Int = timeMillis / 100
            var valueMinute: Int =  valueSecond / 60
            val valueHour: Int = valueMinute / 24

            valueSecond %= 60
            valueMinute %= 60

            itemRecordText.text = String.format("%02d:%02d:%02d:%02d", valueHour, valueMinute, valueSecond, millis)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRecord(record: Int){
        _records.add(record)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearRecords(){
        _records.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder =
        RecordViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false))

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(position + 1, _records[position])
    }

    override fun getItemCount() = _records.size
}