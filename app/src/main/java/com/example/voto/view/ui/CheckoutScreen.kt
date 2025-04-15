package com.example.voto.view.ui

import android.os.AsyncTask
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.voto.R
import com.example.voto.databinding.ActivityCheckoutScreenBinding
import com.example.voto.network.HttpHandler
import com.example.voto.util.cart
import com.example.voto.util.helper
import com.example.voto.util.mySharedPrefrence
import org.json.JSONArray
import org.json.JSONObject

class CheckoutScreen : AppCompatActivity() {
    var total = 0
    var names = ""
    var phoneNumber = ""
    var address = ""

    lateinit var binding: ActivityCheckoutScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        me(this).execute()

        total = intent.getIntExtra("total", 0)
        binding.tvSubtotal.text = "Subtotal:          ${helper.formatRupiah(total)}"
        binding.tvFee.text = "Delivery Fee:         ${helper.formatRupiah(helper.fee)}"
        binding.tvTotal.text = "Total: ${helper.formatRupiah(total + helper.fee)}"

        binding.btn.setOnClickListener {
            sendData(this).execute()
        }

    }

    class me(private var activity: CheckoutScreen): AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {
            return HttpHandler().request(
                "me",
                token = mySharedPrefrence.getToken(activity))
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result!!.isNotEmpty()) {
                var code = JSONObject(result).getInt("code")
                var body = JSONObject(result).getString("body")

                if (code == 200) {
                    activity.names = JSONObject(body).getString("name")
                    activity.phoneNumber = JSONObject(body).getString("phoneNumber")
                    activity.address = JSONObject(body).getString("address")
                }
            }
        }
    }

    fun json() : String {
        var jo = JSONObject()
        try {
            if (binding.cbUse.isChecked) {
                jo.put("recipientName", names)
                jo.put("recipientPhoneNumber", phoneNumber)
                jo.put("shippingAddress", address)
                jo.put("totalPrice", total)
            } else {
                jo.put("recipientName", binding.etName)
                jo.put("recipientPhoneNumber", binding.etPhoneNumber)
                jo.put("shippingAddress", binding.etAddress)
                jo.put("totalPrice", total)
            }

            var ja = JSONArray()
            for (i in cart.cartList) {
                var joCart = JSONObject()
                joCart.put("cameraID", i.camera.id)
                joCart.put("qty", i.qty)
                joCart.put("subtotal", (i.subtotal!! * i.qty))
                ja.put(joCart)
            }
            jo.put("items", ja)

        } catch (e: Exception) {
            helper.log(e.message.toString())
        }
        return jo.toString()
    }

    class sendData(private var activity: CheckoutScreen): AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {
            return HttpHandler().request(
                endpoint = "transaction",
                method = "POST",
                token = mySharedPrefrence.getToken(activity),
                requestBody = activity.json()
            )
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result!!.isNotEmpty()) {
                var code = JSONObject(result).getInt("code")
                var body = JSONObject(result).getString("body")

                if (code == 200) {
                    activity.finish()
                }
            }
        }
    }
}