package com.example.voto.network

import android.util.Log
import com.example.voto.util.helper
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class HttpHandler {
    fun request(
        endpoint: String? = null,
        method: String? = "GET",
        token: String? = null,
        requestBody: String? = null
    ) : String {
        var response = JSONObject()

        try {
            var url = URL(helper.url + endpoint)
            var conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = method
            conn.setRequestProperty("Content-Type", "application/json")

            if (token != null) {
                conn.doOutput = true
                conn.setRequestProperty("Authorization", "Bearer $token")
            }

            if (requestBody != null) {
                conn.outputStream.use { it.write(requestBody.toByteArray()) }
            }

            var code = conn.responseCode
            var body = try {
                conn.inputStream.bufferedReader().use { it.readText() }
            } catch (e: IOException) {
                conn.errorStream.bufferedReader().use { it.readText() }
            }

            response.put("code", code)
            response.put("body", body)

        } catch (e: Exception) {
            Log.d("HttpHandler", "Eror ${e.message}")
        }
        return response.toString()

    }
}