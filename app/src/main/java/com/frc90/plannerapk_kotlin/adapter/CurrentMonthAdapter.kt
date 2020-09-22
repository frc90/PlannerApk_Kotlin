package com.frc90.plannerapk_kotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frc90.plannerapk_kotlin.model.Result
import kotlinx.android.synthetic.main.card_activities.view.*

class CurrentMonthAdapter : RecyclerView.Adapter<CurrentMonthAdapter.CurrentMonthViewHolder>() {

    class CurrentMonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullName = itemView.tv_full_name
        var activityName = itemView.tv_activity_name
        var acapiteName = itemView.tv_acapite_name
        var goalName = itemView.tv_goal_name
        var state = itemView.tv_state
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
    ): CurrentMonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.frc90.plannerapk_kotlin.R.layout.card_activities, parent, false)
        return CurrentMonthViewHolder(view)

//        return CurrentMonthAdapter.CurrentMonthViewHolder(LayoutInflater.from(parent.context)
//            .inflate(R.layout.card_activities, parent, false))
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: CurrentMonthViewHolder, position: Int) {
        holder.fullName.text = result[position].owner.toString()
        holder.acapiteName.text = result[position].acapite.toString()
        holder.activityName.text = result[position].name
        holder.goalName.text = result[position].goal.toString()
        holder.state.text = result[position].status
        holder.date.text = result[position].startDate
    }
}