package com.example.voto.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voto.databinding.ItemCameraBinding
import com.example.voto.model.Camera
import com.example.voto.util.helper
import com.example.voto.view.ui.DetailCameraScreen

class CameraAdapter(private var cameraList: List<Camera>): RecyclerView.Adapter<CameraAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemCameraBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = ItemCameraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var camera = cameraList[position]
        holder.binding.tvName.text = camera.name
        holder.binding.tvPrice.text = helper.formatRupiah(camera.price!!)
        holder.binding.tvResolution.text = camera.resolution
        helper.showImage(holder.binding.imgImgae).execute(helper.urlImage + camera.photo)

        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(Intent(holder.itemView.context, DetailCameraScreen::class.java).apply {
                putExtra("id", camera.id)
            })
        }
    }

    override fun getItemCount(): Int {
        return cameraList.size
    }
}