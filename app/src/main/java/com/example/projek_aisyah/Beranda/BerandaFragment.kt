package com.example.projek_aisyah.Beranda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projek_aisyah.DetailActivity
import com.example.projek_aisyah.R
import com.example.projek_aisyah.databinding.FragmentBerandaBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    // Data contoh. Nanti ganti dengan data dari database.
    private var dosisDiminum = 2
    private val totalDosis = 4
    private var amlodipineSudahDiminum = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nama = requireActivity().intent.getStringExtra("nama")
        val umur = requireActivity().intent.getIntExtra("umur", 0)
        Log.w("Hasil", "Nama: $nama Umur: $umur")

        // Sapaan dan tanggal hari ini
        binding.nama.text = "Halo, ${nama ?: "Pengguna"}"
        binding.tvTanggal.text =
            SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID")).format(Date())

        // SNACKBAR: tombol "Tandai sudah minum"
        binding.btnTandaiMinum.setOnClickListener {
            if (amlodipineSudahDiminum) {
                Snackbar.make(binding.root, "Dosis ini sudah ditandai", Snackbar.LENGTH_SHORT).show()
            } else {
                amlodipineSudahDiminum = true
                dosisDiminum++

                // Update progres
                binding.progressDosis.setProgressCompat(dosisDiminum * 100 / totalDosis, true)
                binding.tvProgresDosis.text = "$dosisDiminum/$totalDosis"
                binding.tvKeteranganProgres.text = "$dosisDiminum dari $totalDosis dosis sudah diminum"

                // Update baris 13:00 menjadi "Diminum"
                binding.rowAmlodipine.strokeWidth = 0
                binding.pillStatus13.setCardBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.mr_green_soft)
                )
                binding.tvStatus13.text = "Diminum"
                binding.tvStatus13.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.mr_green_text)
                )

                Snackbar.make(binding.root, "Amlodipine 5 mg ditandai sudah diminum", Snackbar.LENGTH_SHORT)
                    .setAction("Tutup") {
                        Log.e("Info Snackbar", "Snackbar ditutup")
                    }
                    .show()
            }
        }

        // LIHAT DETAIL
        binding.btnLihatDetail.setOnClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}