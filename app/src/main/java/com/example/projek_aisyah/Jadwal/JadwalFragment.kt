package com.example.projek_aisyah.Jadwal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projek_aisyah.R
import com.example.projek_aisyah.databinding.FragmentJadwalBinding

class JadwalFragment : Fragment() {

    private var _binding: FragmentJadwalBinding? = null
    private val binding get() = _binding!!

    private val jadwalList = listOf(
        JadwalModel(
            namaObat = "Metformin 500 mg",
            dosis = "500 mg",
            jadwal = "07:00 dan 19:00",
            stok = "18 tablet",
            gambarObat = R.drawable.obat
        ),
        JadwalModel(
            namaObat = "Amlodipine 5 mg",
            dosis = "5 mg",
            jadwal = "13:00",
            stok = "4 tablet",
            gambarObat = R.drawable.obat2
        ),
        JadwalModel(
            namaObat = "Vitamin C 500 mg",
            dosis = "500 mg",
            jadwal = "09:00",
            stok = "14 tablet",
            gambarObat = R.drawable.obat3
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJadwalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Halaman Jadwal"

        val adapter = JadwalAdapter(requireContext(), jadwalList)
        binding.listViewObat.adapter = adapter
    }
}