package com.example.projek_aisyah.Riwayat

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projek_aisyah.R
import com.example.projek_aisyah.databinding.FragmentRiwayatBinding
import com.example.projek_aisyah.databinding.HeaderRiwayatBinding
import com.example.projek_aisyah.databinding.ItemBarRiwayatBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

class RiwayatFragment : Fragment() {

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!

    // Data contoh. Nanti ganti dengan data dari database.
    // Jumlah dosis yang diminum per hari, urut dari 6 hari lalu sampai hari ini.
    private val dosisPerHari = listOf(4, 4, 3, 4, 3, 4, 2)
    private val targetPerHari = 4

    private val riwayatKemarin = listOf(
        RiwayatModel("Metformin 500 mg", "07:00", "1 tablet", "Diminum"),
        RiwayatModel("Vitamin C 500 mg", "09:00", "1 tablet", "Diminum"),
        RiwayatModel("Amlodipine 5 mg", "13:00", "1 tablet", "Tertunda"),
        RiwayatModel("Metformin 500 mg", "19:00", "1 tablet", "Terlewat")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Header (judul, kartu kepatuhan, grafik) harus dipasang SEBELUM setAdapter
        val header = HeaderRiwayatBinding.inflate(
            LayoutInflater.from(requireContext()), binding.listViewRiwayat, false
        )
        isiHeader(header)
        binding.listViewRiwayat.addHeaderView(header.root, null, false)

        binding.listViewRiwayat.adapter = RiwayatAdapter(requireContext(), riwayatKemarin)

        // Aksi saat item diklik
        binding.listViewRiwayat.setOnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position) as? RiwayatModel
                ?: return@setOnItemClickListener
            Toast.makeText(
                requireContext(),
                "Kamu memilih: ${item.namaObat} (${item.jam} - ${item.status})",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun isiHeader(header: HeaderRiwayatBinding) {
        val localeId = Locale("id", "ID")

        // Persentase kepatuhan
        val totalDiminum = dosisPerHari.sum()
        val totalTarget = dosisPerHari.size * targetPerHari
        val persen = (totalDiminum * 100f / totalTarget).roundToInt()
        header.tvPersen.text = "$persen%"
        header.tvKeteranganPersen.text = "$totalDiminum dari $totalTarget dosis diminum"

        // Grafik batang 7 hari, hari terakhir (hari ini) berwarna koral
        val formatHari = SimpleDateFormat("EEE", localeId)
        val kalender = Calendar.getInstance()
        kalender.add(Calendar.DAY_OF_MONTH, -(dosisPerHari.size - 1))

        dosisPerHari.forEachIndexed { index, diminum ->
            val bar = ItemBarRiwayatBinding.inflate(
                LayoutInflater.from(requireContext()), header.layoutBar, false
            )
            val hariIni = index == dosisPerHari.lastIndex

            bar.tvHariBar.text = formatHari.format(kalender.time)

            val tinggiDp = (diminum * 80f / targetPerHari).roundToInt().coerceAtLeast(8)
            val params = bar.cardBar.layoutParams
            params.height = dp(tinggiDp)
            bar.cardBar.layoutParams = params

            bar.cardBar.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (hariIni) R.color.mr_coral else R.color.mr_teal
                )
            )
            if (hariIni) {
                bar.tvHariBar.setTextColor(ContextCompat.getColor(requireContext(), R.color.mr_coral_text))
                bar.tvHariBar.setTypeface(null, Typeface.BOLD)
            }

            header.layoutBar.addView(bar.root)
            kalender.add(Calendar.DAY_OF_MONTH, 1)
        }

        // Judul "Kemarin, <tanggal>"
        val kemarin = Calendar.getInstance()
        kemarin.add(Calendar.DAY_OF_MONTH, -1)
        header.tvJudulKemarin.text =
            "Kemarin, ${SimpleDateFormat("d MMMM", localeId).format(kemarin.time)}"
    }

    private fun dp(nilai: Int): Int = (nilai * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}