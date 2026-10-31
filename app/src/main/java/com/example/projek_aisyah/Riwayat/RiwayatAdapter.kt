package com.example.projek_aisyah.Riwayat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.projek_aisyah.R
import com.example.projek_aisyah.databinding.ItemRiwayatBinding

class RiwayatAdapter(
    context: Context,
    private val daftarRiwayat: List<RiwayatModel>
) : ArrayAdapter<RiwayatModel>(context, 0, daftarRiwayat) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView != null) {
            ItemRiwayatBinding.bind(convertView)
        } else {
            ItemRiwayatBinding.inflate(LayoutInflater.from(context), parent, false)
        }
        val data = daftarRiwayat[position]

        binding.textNamaObat.text = data.namaObat
        binding.textDetail.text = "${data.jam} · ${data.jumlah}"
        binding.textStatus.text = data.status

        // Warna titik, latar label, dan teks label mengikuti status
        val (warnaDot, warnaLabel, warnaTeks) = when (data.status) {
            "Diminum" -> Triple(R.color.mr_green, R.color.mr_green_soft, R.color.mr_green_text)
            "Tertunda" -> Triple(R.color.mr_yellow, R.color.mr_yellow_soft, R.color.mr_yellow_text)
            else -> Triple(R.color.mr_red, R.color.mr_red_soft, R.color.mr_red_text)
        }
        binding.dotStatus.setCardBackgroundColor(ContextCompat.getColor(context, warnaDot))
        binding.pillStatus.setCardBackgroundColor(ContextCompat.getColor(context, warnaLabel))
        binding.textStatus.setTextColor(ContextCompat.getColor(context, warnaTeks))

        return binding.root
    }
}