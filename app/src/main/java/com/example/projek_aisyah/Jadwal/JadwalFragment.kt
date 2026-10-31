package com.example.projek_aisyah.Jadwal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projek_aisyah.R
import com.example.projek_aisyah.TambahObatActivity
import com.example.projek_aisyah.databinding.FragmentJadwalBinding
import com.example.projek_aisyah.databinding.ItemHariBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class JadwalFragment : Fragment() {

    private var _binding: FragmentJadwalBinding? = null
    private val binding get() = _binding!!

    // Data contoh. Nanti ganti dengan data dari database.
    private val jadwalList = listOf(
        JadwalModel(
            namaObat = "Metformin 500 mg",
            dosis = "500 mg",
            frekuensi = 2,
            jadwal = "07:00 & 19:00",
            stok = 18,
            stokAwal = 30,
            gambarObat = R.drawable.obat
        ),
        JadwalModel(
            namaObat = "Amlodipine 5 mg",
            dosis = "5 mg",
            frekuensi = 1,
            jadwal = "13:00",
            stok = 4,
            stokAwal = 30,
            gambarObat = R.drawable.obat2
        ),
        JadwalModel(
            namaObat = "Vitamin C 500 mg",
            dosis = "500 mg",
            frekuensi = 1,
            jadwal = "09:00",
            stok = 14,
            stokAwal = 20,
            gambarObat = R.drawable.obat3,
            sampai = "20 Okt"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJadwalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Daftar obat
        binding.listViewObat.adapter = JadwalAdapter(requireContext(), jadwalList)
        binding.tvJumlahObat.text = "Obat aktif (${jadwalList.size})"

        // Strip tanggal 5 hari
        tampilkanStripHari()

        // Tombol +: buka halaman Tambah Obat
        binding.fabTambahJadwal.setOnClickListener {
            startActivity(Intent(requireContext(), TambahObatActivity::class.java))
        }
    }

    private fun tampilkanStripHari() {
        val localeId = Locale("id", "ID")
        val formatHari = SimpleDateFormat("EEE", localeId)
        val formatTanggal = SimpleDateFormat("d", localeId)
        val kalender = Calendar.getInstance()
        val daftarHari = mutableListOf<ItemHariBinding>()

        for (i in 0 until 5) {
            val item = ItemHariBinding.inflate(
                LayoutInflater.from(requireContext()), binding.layoutHari, false
            )
            item.tvNamaHari.text = formatHari.format(kalender.time)
            item.tvTanggalHari.text = formatTanggal.format(kalender.time)

            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            if (i < 4) params.marginEnd = dp(8)
            item.root.layoutParams = params

            binding.layoutHari.addView(item.root)
            daftarHari.add(item)
            kalender.add(Calendar.DAY_OF_MONTH, 1)
        }

        // Ubah tampilan kotak yang dipilih (teal) dan yang tidak (putih)
        fun pilihHari(terpilih: Int) {
            daftarHari.forEachIndexed { index, item ->
                val aktif = index == terpilih
                item.root.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        if (aktif) R.color.mr_teal else android.R.color.white
                    )
                )
                item.tvNamaHari.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        if (aktif) R.color.mr_mint else R.color.mr_ink_soft
                    )
                )
                item.tvTanggalHari.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        if (aktif) android.R.color.white else R.color.mr_ink
                    )
                )
            }
        }

        daftarHari.forEachIndexed { index, item ->
            item.root.setOnClickListener { pilihHari(index) }
        }
        pilihHari(0) // hari ini terpilih di awal
    }

    private fun dp(nilai: Int): Int = (nilai * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}