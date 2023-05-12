package com.example.androidtesttask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtesttask.R
import java.util.Collections

class AdapterJokesRV(private val jokesList:ArrayList<String>) : RecyclerView.Adapter<AdapterJokesRV.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jokeText = itemView.findViewById<TextView>(R.id.jokeTxt)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.joke_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return jokesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.jokeText.text = jokesList[position]
        }

}