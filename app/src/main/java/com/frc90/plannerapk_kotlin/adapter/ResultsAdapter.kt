package com.frc90.plannerapk_kotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frc90.plannerapk_kotlin.model.Result
import kotlinx.android.synthetic.main.card_result.view.*

class ResultsAdapter : RecyclerView.Adapter<ResultsAdapter.ResultViewHolder>() {

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullName = itemView.tv_full_name
        var activityName = itemView.tv_activity_name
        var date = itemView.tv_start_date
    }

    private var result: List<Result> = emptyList()

    fun setListOfResult(list: List<Result>) {
        this.result = list
        notifyDataSetChanged() // super importante
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.frc90.plannerapk_kotlin.R.layout.card_result, parent, false)
        return ResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.fullName.text = result[position].owner.toString()
        holder.activityName.text = result[position].name
        holder.date.text = result[position].startDate
    }
}