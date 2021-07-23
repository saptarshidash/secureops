package com.saptarshidas.secureops.base

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter.ViewHolder>() {

    private  val TAG = "BaseAdapter"
    protected var dataList: ArrayList<T> = ArrayList()

    protected var layoutId: Int = 0

    protected lateinit var listener: OnRecyclerClickListener

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onViewHolderBind(holder, position, dataList[position])
        holder.view.setOnClickListener {
            listener.onClick(dataList[position])
        }

    }

    override fun getItemCount() = dataList.size

    open fun updateList(list: ArrayList<T>){
        dataList = list
        notifyDataSetChanged()
    }

    abstract fun onViewHolderBind(holder: ViewHolder, position: Int, data: Any?)

    interface OnRecyclerClickListener{
        fun onClick(data: Any?)
    }


}