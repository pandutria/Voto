package com.example.voto.view.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.voto.databinding.ActivityLoginScreenBinding
import com.example.voto.network.HttpHandler
import com.example.voto.util.helper
import com.example.voto.util.mySharedPrefrence
import org.json.JSONObject

class LoginScreen : AppCompatActivity() {
    lateinit var binding: ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etEmail.setText("mahdi@gmail.com")
        binding.etPassword.setText("1234")

        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.toString() == "" || binding.etPassword.text.toString() == "") {
                helper.toast(this, "All fields must be filled")
                return@setOnClickListener
            }

            login(this).execute()
        }
    }

    fun json() : String {
        var jo = JSONObject()
        jo.put("email", binding.etEmail.text)
        jo.put("password", binding.etPassword.text)
        return jo.toString()
    }

    class login(private val activity: LoginScreen): AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {
            return HttpHandler().request(
                "auth",
                "POST",
                requestBody = activity.json()
            )
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var body = JSONObject(result).getString("body")
            var code = JSONObject(result).getInt("code")

            if (code == 200) {
                mySharedPrefrence.saveToken(activity, body)
//                helper.toast(activity, mySharedPrefrence.getToken(activity)!!)
                activity.startActivity(Intent(activity, HomeScreen::class.java))

            } else {
                helper.toast(activity, "User not found")
            }
        }
    }
}