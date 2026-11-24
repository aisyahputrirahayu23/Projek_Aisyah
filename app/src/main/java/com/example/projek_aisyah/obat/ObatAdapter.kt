package com.example.projek_aisyah.obat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.projek_aisyah.R
import com.example.projek_aisyah.data.entity.ObatEntity
import com.example.projek_aisyah.databinding.ItemObatBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ObatAdapter(
    private val obatList: List<ObatEntity>,
    private val onItemClick: (ObatEntity) -> Unit,
    private val onDelete: (ObatEntity) -> Unit
) : RecyclerView.Adapter<ObatAdapter.ObatViewHolder>() {

    inner class ObatViewHolder(val binding: ItemObatBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ObatViewHolder {
        val binding = ItemObatBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ObatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ObatViewHolder, position: Int) {
        val item = obatList[position]
        val context = holder.itemView.context

        with(holder.binding) {
            tvEmoji.text = when (item.bentuk.lowercase()) {
                "kapsul" -> "🧴"
                "sirup" -> "🧪"
                "salep" -> "🩹"
                "tetes" -> "💧"
                "suntik" -> "💉"
                else -> "💊"
            }
            tvNamaObat.text = item.nama
            tvDosisObat.text = "${item.dosis} · ${item.bentuk}"

            // Isi SEMUA kondisi (if dan else), karena baris didaur ulang
            if (item.stok <= 3) {
                tvStok.text = "Menipis: ${item.stok}"
                tvStok.setTextColor(ContextCompat.getColor(context, R.color.mr_coral_text))
                pillStok.setCardBackgroundColor(ContextCompat.getColor(context, R.color.mr_coral_soft))
            } else {
                tvStok.text = "Stok: ${item.stok}"
                tvStok.setTextColor(ContextCompat.getColor(context, R.color.mr_green_text))
                pillStok.setCardBackgroundColor(ContextCompat.getColor(context, R.color.mr_green_soft))
            }

            root.setOnClickListener { onItemClick(item) }

            btnHapus.setOnClickListener {
                MaterialAlertDialogBuilder(context)
                    .setTitle("Hapus Obat")
                    .setMessage("Yakin ingin menghapus ${item.nama}?")
                    .setPositiveButton("Ya") { _, _ -> onDelete(item) }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        }
        Log.d("ObatAdapter", "onBindViewHolder posisi $position: ${item.nama}")
    }

    override fun getItemCount(): Int = obatList.size
}