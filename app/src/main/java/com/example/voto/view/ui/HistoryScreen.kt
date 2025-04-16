package com.example.voto.view.ui

import android.os.AsyncTask
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voto.R
import com.example.voto.databinding.ActivityHistoryScreenBinding
import com.example.voto.model.Camera
import com.example.voto.model.Cart
import com.example.voto.model.History
import com.example.voto.network.HttpHandler
import com.example.voto.util.helper
import com.example.voto.util.mySharedPrefrence
import com.example.voto.view.adapter.HistoryAdapter
import org.json.JSONArray
import org.json.JSONObject

class HistoryScreen : AppCompatActivity() {
    lateinit var binding: ActivityHistoryScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        showData(this).execute()
    }

    class showData(private var activity: HistoryScreen): AsyncTask<Void, Void, Void>() {
        var historyList: MutableList<History> = arrayListOf()

        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var jsonUrl = HttpHandler().request(
                    endpoint = "me/transaction",
                    method = "GET",
                    token =  mySharedPrefrence.getToken(activity)
                )

                var body = JSONObject(jsonUrl).getString("body")
                var code = JSONObject(jsonUrl).getInt("code")

                if (code == 200) {
                    var jsonArray = JSONArray(body)

                    for (i in 0 until jsonArray.length()) {
                        var history = jsonArray.getJSONObject(i)

                        var id = history.getString("id")
                        var status = history.getString("status")
                        var totalPrice = history.getInt("totalPrice")

                        var transaction = history.getJSONArray("transactions")
                        var cartList: MutableList<Cart> = arrayListOf()

                        for (ii in 0 until transaction.length()) {
                            var cart = transaction.getJSONObject(ii)
                            var name = cart.getString("name")
                            var qty = cart.getInt("qty")
                            var subtotal = cart.getInt("subtotal")

                            cartList.add(Cart(
                                qty = qty,
                                subtotal = subtotal,
                                camera = Camera(name = name)
                            ))
                        }

                        historyList.add(History(
                            id = id,
                            status = status,
                            totalPrice = totalPrice,
                            cart = cartList
                        ))
                    }

                } else {
                    helper.log(body)
                }


            } catch (e: Exception) {
                helper.log(e.message.toString())
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            if (result.toString().isNotEmpty()) {
                activity.binding.rv.adapter = HistoryAdapter(historyList)
                activity.binding.rv.layoutManager = LinearLayoutManager(activity)
            }

        }
    }
}