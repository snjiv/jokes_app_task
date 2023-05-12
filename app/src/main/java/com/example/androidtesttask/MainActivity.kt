package com.example.androidtesttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.health.TimerStat
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidtesttask.adapters.AdapterJokesRV
import com.example.androidtesttask.databinding.ActivityMainBinding
import com.example.androidtesttask.model.JokeResponseModel
import com.example.androidtesttask.utils.UtilityClass
import com.example.androidtesttask.viewModel.ViewModelClass
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class MainActivity : AppCompatActivity() {
    private val TAG ="MAIN"
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelClass: ViewModelClass
    private var jokesList: ArrayList<String>? = ArrayList<String>()
    private var latestJoke = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelClass = ViewModelProvider(this)[ViewModelClass::class.java]
        fetchLocalData()

       if (UtilityClass.isNetworkConnected(applicationContext)){
           callForNewJoke()
       }
        else{
           binding.timerTxt.text = "No internet connection!"
           Toast.makeText(applicationContext,"Please Turn on Internet connection!",Toast.LENGTH_SHORT).show()
       }
    }

    private fun fetchLocalData() {
        try {
            jokesList = UtilityClass.getArrayList(applicationContext)
            binding.jokesRV.adapter = AdapterJokesRV(jokesList!!)
        } catch (e: java.lang.Exception) {
            jokesList = ArrayList<String>()
            Log.e(TAG, e.message.toString())
        }
    }

    private fun callForNewJoke() {
        if (latestJoke.isNotEmpty()) {
            jokesList?.add(latestJoke)
        }

        val params = LinkedHashMap<String, String>()
        params["format"] = "json"
        MainScope().launch {
            UtilityClass.showProgress(this@MainActivity, "Bringing-in a new joke!")
            viewModelClass.jokeFetch(params).observe(this@MainActivity, Observer {
                UtilityClass.dismissProgress()
                if (jokesList!!.size > 10) {
                    jokesList!!.removeAt(0)
                }
                latestJoke = it.joke
                binding.latestJokeTxt.text = latestJoke
                binding.jokesRV.adapter = AdapterJokesRV(jokesList!!)

                val timer = object : CountDownTimer(60000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.timerTxt.text = "new joke in 00:${millisUntilFinished / 1000}"
                    }

                    override fun onFinish() {
                        callForNewJoke()
                    }
                }
                timer.start()
            })
        }
    }

    override fun onStop() {
        super.onStop()
        UtilityClass.saveArrayList(applicationContext, jokesList!!)
    }


}