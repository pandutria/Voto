package com.example.voto.view.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.voto.R
import com.example.voto.databinding.ActivityHomeScreenBinding
import com.example.voto.model.Camera
import com.example.voto.network.HttpHandler
import com.example.voto.util.helper
import com.example.voto.view.adapter.CameraAdapter
import org.json.JSONArray
import org.json.JSONObject

class HomeScreen : AppCompatActivity() {
    lateinit var binding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cart.setOnClickListener {
            startActivity(Intent(this, CartScreen::class.java))
        }

        binding.walet.setOnClickListener {
            startActivity(Intent(this, HistoryScreen::class.java))
        }

        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                showData(this@HomeScreen).execute()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        showData(this).execute()
    }

    class showData(private var activity: HomeScreen): AsyncTask<Void, Void, Void>() {
        var cameraList: MutableList<Camera> = arrayListOf()
        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var jsonUrl = HttpHandler().request("camera")

                if (activity.binding.etSearch.text != null) {
                    jsonUrl = HttpHandler().request("camera?search=${activity.binding.etSearch.text}")
                }

                var code = JSONObject(jsonUrl).getInt("code")
                var body = JSONObject(jsonUrl).getString("body")

                if (code == 200) {
                    var jsonArray = JSONArray(body)

                    for (i in 0 until jsonArray.length()) {
                        var c = jsonArray.getJSONObject(i)
                        var id = c.getInt("id")
                        var name = c.getString("name")
                        var resolution = c.getString("resolution")
                        var price = c.getInt("price")
                        var photo = c.getString("photo")

                        cameraList.add(Camera(
                            id = id,
                            name = name,
                            resolution = resolution,
                            price = price,
                            photo = photo
                        ))
                    }

                }

            } catch (e: Exception) {
                helper.log(e.message.toString())
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            activity.binding.rv.adapter = CameraAdapter(cameraList)
            activity.binding.rv.layoutManager = GridLayoutManager(activity, 2)
        }
    }
}