package com.example.datafromtheapi

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    lateinit var temptv:TextView
    lateinit var button: Button
    lateinit var pressuretv:TextView
    lateinit var windySpeedtv:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        temptv = findViewById<TextView>(R.id.temp)
        pressuretv = findViewById<TextView>(R.id.pressure)
        windySpeedtv = findViewById<TextView>(R.id.windySpeed)

        var city:TextView = findViewById<TextView>(R.id.city)
        button.setOnClickListener{
            if(city.text.isNotEmpty())
            {
                getResult(city.text.toString())
            }
            else
            {
                var sn = Snackbar.make(findViewById<View>(android.R.id.content), "Пустые поля", Snackbar.LENGTH_LONG)
                sn.setActionTextColor(Color.RED)
                sn.show()
            }
        }

    }
    fun getResult(city: String) {
        var key = "6b16682943627c3c6300a1f2e0797feb"
        var url = "https://api.openweathermap.org/data/2.5/weather?q=" +city+"&appid=6b16682943627c3c6300a1f2e0797feb&units=metric&lang=ru"
        val queue = Volley.newRequestQueue(this)
        val stringR = StringRequest(
            Request.Method.GET,
            url,
            {
                    response ->
                val obj = JSONObject(response)
                val temp = obj.getJSONObject("main")
                val wind = obj.getJSONObject("wind")

                var t = getString(R.string.temp)
                var e = temp.getString("temp").toString()
                temptv.text = "$t $e °C"

                t = getString(R.string.pressure)
                e = temp.getString("pressure").toString()
                pressuretv.text = "$t $e"

                t = getString(R.string.windySpeed)
                e = wind.getString("speed").toString()
                windySpeedtv.text = "$t $e"

                Log.d("MyLog", "Response:${temp.getString("temp")}")
            },
            {
                Log.d("MyLog", "Volley error:$it")
            }
        )
        queue.add(stringR)
    }
}