package com.example.voto.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import java.net.URL
import java.text.NumberFormat
import java.util.Locale

object helper {
    var url = "http://10.0.2.2:5000/api/"
    var urlImage = url.replace("api/", "images/")
    var fee = 30000

    fun formatRupiah(num: Int): String {
        var format = NumberFormat.getNumberInstance(Locale("in", "ID"))
        var formated = format.format(num)
        return "Rp$formated"
    }

    class showImage(private var imageView: ImageView): AsyncTask<String, Void, Bitmap>() {
        override fun doInBackground(vararg p0: String?): Bitmap? {
            return try {
                var url = URL(p0[0]).openStream()
                BitmapFactory.decodeStream(url)
            } catch (e: Exception) {
                log(e.message.toString())
                null
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            imageView.setImageBitmap(result)
        }
    }

    fun log(string: String) {
        Log.d("DataApi", "Eror : $string")
    }

    fun toast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }
}