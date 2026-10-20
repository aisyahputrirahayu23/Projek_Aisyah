package com.example.projek_aisyah.Jadwal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.projek_aisyah.databinding.ItemJadwalObatBinding
import com.google.android.material.snackbar.Snackbar

class JadwalAdapter (
    context: Context,
    private val daftarJadwal: List<JadwalModel>
) : ArrayAdapter<JadwalModel>(context, 0, daftarJadwal) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemJadwalObatBinding.inflate(LayoutInflater.from(context), parent, false)
        val view = binding.root
        val data = daftarJadwal[position]
// Muat gambar dari URL ke ImageView
        Glide.with(context)
            .load(data.gambarObat)

            .into(binding.avatarImg)
        binding.textNamaObat.text = data.namaObat
        binding.textDosis.text = "Dosis: ${data.dosis}"
        binding.textJadwal.text = "Jadwal: ${data.jadwal}"
        binding.textStok.text = "Stok: ${data.stok}"

        view.setOnClickListener {
            Snackbar.make(
                parent,
                "Jadwal ${data.namaObat} - ${data.jadwal}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        return view
    }
}