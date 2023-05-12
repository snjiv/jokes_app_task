package com.example.androidtesttask.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object UtilityClass {

    private var progressDialog: ProgressDialog? = null

    fun saveArrayList(context: Context, listArray: ArrayList<String>) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(listArray)
        editor.putString("TAG_LIST", json) ///"TAG_LIST" is a key must same for getting data
        editor.apply()
    }

    fun getArrayList(context: Context): ArrayList<String>? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString("TAG_LIST", null)
        val listType: Type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson<ArrayList<String>>(json, listType)
    }

    fun showProgress(activity: Activity?, message: String?) {
      progressDialog = ProgressDialog(activity)
      progressDialog!!.setMessage(message)
      progressDialog!!.setCanceledOnTouchOutside(false)
      progressDialog!!.show()
    }

    fun dismissProgress() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

}