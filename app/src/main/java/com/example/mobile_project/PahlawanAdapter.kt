package com.example.mobile_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.R
import com.example.mobile_project.model.Pahlawan

class PahlawanAdapter(
    private val pahlawanList: List<Pahlawan>,
    private val onItemClick: (Pahlawan) -> Unit
) : RecyclerView.Adapter<PahlawanAdapter.PahlawanViewHolder>() {

    inner class PahlawanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgTokoh: ImageView = itemView.findViewById(R.id.imgTokoh)
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvJulukan: TextView = itemView.findViewById(R.id.tvJulukan)
        val rootLayout: View = itemView // klik di seluruh card
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PahlawanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tokoh, parent, false)
        return PahlawanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PahlawanViewHolder, position: Int) {
        val pahlawan = pahlawanList[position]
        val context = holder.itemView.context

        holder.tvNama.text = pahlawan.namaPahlawan
        holder.tvJulukan.text = pahlawan.julukan

        val imgResId = context.resources.getIdentifier(
            pahlawan.foto, "drawable", context.packageName
        )
        holder.imgTokoh.setImageResource(if (imgResId != 0) imgResId else R.drawable.soekarno)

        holder.rootLayout.setOnClickListener {
            onItemClick(pahlawan)
        }
    }

    override fun getItemCount(): Int = pahlawanList.size
}
