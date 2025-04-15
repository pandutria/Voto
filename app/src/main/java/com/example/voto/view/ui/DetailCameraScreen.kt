package com.example.voto.view.ui

import android.os.AsyncTask
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.voto.R
import com.example.voto.databinding.ActivityDetailCameraScreenBinding
import com.example.voto.model.Camera
import com.example.voto.model.Cart
import com.example.voto.network.HttpHandler
import com.example.voto.util.cart
import com.example.voto.util.helper
import org.json.JSONObject

class DetailCameraScreen : AppCompatActivity() {
    lateinit var binding: ActivityDetailCameraScreenBinding
    var camera_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCameraScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        camera_id = intent.getIntExtra("id", 0)
        showData(this).execute()

        binding.btn.setOnClickListener {
            cart.cartList.add(Cart(
                Camera()
            ))
        }
    }

    class showData(private var activity: DetailCameraScreen): AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {
            return HttpHandler().request("camera/${activity.camera_id}")
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result!!.isNotEmpty()) {
                try {
                    var code = JSONObject(result).getInt("code")
                    var body = JSONObject(result).getString("body")

                    if (code == 200) {
                        var c = JSONObject(body)
                        var binding = activity.binding
                        binding.tvName.text = c.getString("name")
                        binding.tvSellerShop.text = c.getString("sellerShop")
                        binding.tvSensor.text = c.getString("sensor")
                        binding.tvResolution.text = c.getString("resolution")
                        binding.tvAutofocus.text = c.getString("autoFocusSystem")
                        binding.tvIso.text = c.getString("isoRange")
                        binding.tvSpeed.text = c.getString("shuterSpeedRange")
                        binding.tvDimensions.text = c.getString("dimensions")
                        binding.tvPrice.text = helper.formatRupiah(c.getInt("price"))
                        helper.showImage(binding.imgImage).execute(helper.urlImage + c.getString("photo"))

                        if (c.getBoolean("wiFi")) {
                            binding.tvWifi.text = "Yes"
                        } else {
                            binding.tvWifi.text = "No"
                        }

                        if (c.getBoolean("flash")) {
                            binding.tvFlash.text = "Yes"
                        } else {
                            binding.tvFlash.text = "No"
                        }

                        if (c.getBoolean("touchScreen")) {
                            binding.tvTouchScreen.text = "Yes"
                        } else {
                            binding.tvTouchScreen.text = "No"
                        }

                        if (c.getBoolean("bluetooth")) {
                            binding.tvBluetooth.text = "Yes"
                        } else {
                            binding.tvBluetooth.text = "No"
                        }
                    }

                } catch (e: Exception) {
                    helper.log(e.message.toString())
                }

            }

        }
    }
}