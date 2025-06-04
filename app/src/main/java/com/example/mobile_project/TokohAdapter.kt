package psti.unram.tubesapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TokohAdapter(private val daftarTokoh: List<Tokoh>) :
    RecyclerView.Adapter<TokohAdapter.TokohViewHolder>() {

    class TokohViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgTokoh: ImageView = itemView.findViewById(R.id.imgTokoh)
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskripsi)
        val tvJudul: TextView = itemView.findViewById(R.id.tvJudul)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TokohViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tokoh, parent, false)
        return TokohViewHolder(view)
    }

    override fun onBindViewHolder(holder: TokohViewHolder, position: Int) {
        val tokoh = daftarTokoh[position]
        holder.imgTokoh.setImageResource(tokoh.gambarResId)
        holder.tvNama.text = tokoh.nama
        holder.tvDeskripsi.text = tokoh.deskripsi
        holder.tvJudul.text = "Pahlawan Indonesia"

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("nama", tokoh.nama)
            intent.putExtra("deskripsi", tokoh.deskripsi)
            intent.putExtra("gambar", tokoh.gambarResId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = daftarTokoh.size
}
