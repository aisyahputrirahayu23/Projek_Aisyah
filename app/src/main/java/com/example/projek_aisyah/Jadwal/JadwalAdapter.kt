package com.example.projek_aisyah.Jadwal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.projek_aisyah.R
import com.example.projek_aisyah.databinding.ItemJadwalObatBinding
import com.google.android.material.snackbar.Snackbar

class JadwalAdapter(
    context: Context,
    private val daftarJadwal: List<JadwalModel>
) : ArrayAdapter<JadwalModel>(context, 0, daftarJadwal) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView != null) {
            ItemJadwalObatBinding.bind(convertView)
        } else {
            ItemJadwalObatBinding.inflate(LayoutInflater.from(context), parent, false)
        }
        val data = daftarJadwal[position]

        // Muat gambar obat
        Glide.with(context)
            .load(data.gambarObat)
            .centerCrop()
            .into(binding.avatarImg)

        binding.textNamaObat.text = data.namaObat
        binding.textJadwal.text = "${data.frekuensi} kali sehari · ${data.jadwal}"

        // Stok hampir habis (5 atau kurang): teks, bar, dan border jadi koral
        val hampirHabis = data.stok <= 5
        binding.textStok.text =
            if (hampirHabis) "Stok ${data.stok} · hampir habis" else "Stok ${data.stok} tablet"
        binding.textStok.setTextColor(
            ContextCompat.getColor(
                context,
                if (hampirHabis) R.color.mr_coral_text else R.color.mr_ink
            )
        )

        val warnaBar = ContextCompat.getColor(
            context,
            if (hampirHabis) R.color.mr_coral else R.color.mr_teal
        )
        binding.progressStok.setIndicatorColor(warnaBar)
        binding.progressStok.setProgressCompat(
            (data.stok * 100 / data.stokAwal.coerceAtLeast(1)).coerceIn(0, 100),
            false
        )

        binding.cardItem.strokeColor = ContextCompat.getColor(context, R.color.mr_coral)
        binding.cardItem.strokeWidth =
            if (hampirHabis) (2 * context.resources.displayMetrics.density).toInt() else 0

        // "sampai 20 Okt" hanya tampil kalau ada tanggal selesai
        if (data.sampai != null) {
            binding.textSampai.visibility = View.VISIBLE
            binding.textSampai.text = "sampai ${data.sampai}"
        } else {
            binding.textSampai.visibility = View.GONE
        }

        binding.root.setOnClickListener {
            Snackbar.make(
                parent,
                "Jadwal ${data.namaObat} - ${data.jadwal}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        return binding.root
    }
}