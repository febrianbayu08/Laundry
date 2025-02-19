package com.example.laundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.R
import com.example.laundry.ModelData.ModelPelanggan

class AdapterDataPelanggan(private val listPelanggan: ArrayList<ModelPelanggan>) :
    RecyclerView.Adapter<AdapterDataPelanggan.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_datapelanggan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listPelanggan[position]
        holder.tvID.text = item.idPelanggan
        holder.tvNama.text = item.nama
        holder.tvAlamat.text = item.alamat
        holder.tvNoHP.text = item.noHP
        holder.tvTerdaftar.text = item.cabang
        holder.tvCabang.text = item.cabang

        holder.cvCARD.setOnClickListener {
            // Tambahkan aksi saat CardView diklik
        }

        holder.btHubungi.setOnClickListener {
            // Tambahkan aksi saat tombol "Hubungi" diklik
        }

        holder.btLihat.setOnClickListener {
            // Tambahkan aksi saat tombol "Lihat" diklik
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvCARD: CardView = itemView.findViewById(R.id.card_datapelanggan)
        val tvID: TextView = itemView.findViewById(R.id.tvid_pelanggan)
        val tvNama: TextView = itemView.findViewById(R.id.tvnama_pelanggan)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvalamat_pelanggan)
        val tvNoHP: TextView = itemView.findViewById(R.id.tvnohp_pelanggan)
        val tvTerdaftar: TextView = itemView.findViewById(R.id.tvterdaftar_pelanggan)
        val tvCabang: TextView = itemView.findViewById(R.id.tvterdaftar_cabang) // ID sudah dicocokkan
        val btHubungi: Button = itemView.findViewById(R.id.btn_hubungi)
        val btLihat: Button = itemView.findViewById(R.id.btn_lihat)
    }

    override fun getItemCount(): Int {
        return listPelanggan.size
    }
}