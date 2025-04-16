package com.example.laundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundry.ModelData.ModelPegawai
import com.example.laundry.R
import java.text.SimpleDateFormat
import java.util.Locale

class AdapterDataPegawai(private val listPegawai: ArrayList<ModelPegawai>) :
    RecyclerView.Adapter<AdapterDataPegawai.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_data_pegawai, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listPegawai[position]
        holder.tvID.text = item.idPegawai
        holder.tvNama.text = item.nama
        holder.tvAlamat.text = item.alamat
        holder.tvNoHP.text = item.noHP
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

    override fun getItemCount(): Int {
        return listPegawai.size
    }

    fun convertTimestampToDate(timestamp: Long?): String {
        return timestamp?.let {
            val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            sdf.format(it)
        } ?: "-"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvCARD: CardView = itemView.findViewById(R.id.card_datapegawai)
        val tvID: TextView = itemView.findViewById(R.id.tvCARD_PEGAWAI_ID)
        val tvNama: TextView = itemView.findViewById(R.id.tvCARD_PEGAWAI_nama)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvCARD_PEGAWAI_alamat)
        val tvNoHP: TextView = itemView.findViewById(R.id.tvCARD_PEGAWAI_nohp)
        val tvCabang: TextView = itemView.findViewById(R.id.tvCARD_PEGAWAI_terdaftar)
        val btHubungi: Button = itemView.findViewById(R.id.btn_hubungi)
        val btLihat: Button = itemView.findViewById(R.id.btn_lihat)
    }
}
