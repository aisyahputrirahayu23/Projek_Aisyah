package com.example.projek_aisyah.Riwayat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.projek_aisyah.R
import com.example.projek_aisyah.databinding.FragmentJadwalBinding
import com.example.projek_aisyah.databinding.FragmentRiwayatBinding
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.Toast

class RiwayatFragment : Fragment() {

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!

    val dataRiwayat = listOf(
        "Metformin 500 mg - Diminum pukul 07:00",
        "Vitamin C 500 mg - Diminum pukul 09:00",
        "Amlodipine 5 mg - Ditunda pukul 13:00",
        "Metformin 500 mg - Terlewat pukul 19:00"
    )

    val dataListWithDesc = listOf(
        mapOf(
            "title" to "Metformin 500 mg",
            "desc" to "07:00 - Diminum"
        ),
        mapOf(
            "title" to "Vitamin C 500 mg",
            "desc" to "09:00 - Diminum"
        ),
        mapOf(
            "title" to "Amlodipine 5 mg",
            "desc" to "13:00 - Ditunda"
        ),
        mapOf(
            "title" to "Metformin 500 mg",
            "desc" to "19:00 - Terlewat"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Halaman Riwayat"

        // Adapter: penghubung dataList dengan layout simple_list_item_1
//        val adapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_list_item_1,
//            dataRiwayat
//        )
//// Hubungkan listViewItems dengan adapter
//        binding.listViewItems.adapter = adapter
//// Aksi saat item diklik
//        binding.listViewItems.setOnItemClickListener { _, _, position, _ ->
//            val selectedItem = dataList[position]
//            Toast.makeText(
//                requireContext(), "Kamu memilih: $selectedItem",
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        val adapter = SimpleAdapter(
            requireContext(),
            dataListWithDesc,
            android.R.layout.simple_list_item_2,
            arrayOf("title", "desc"), // key yang diambil dari data
            intArrayOf(android.R.id.text1, android.R.id.text2) // tempat tampilnya
        )
        binding.listViewRiwayat.adapter = adapter
        binding.listViewRiwayat.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = dataListWithDesc[position]
            val title = selectedItem["title"]
            val desc = selectedItem["desc"]
            Toast.makeText(requireContext(), "Kamu memilih: $title ($desc)",
                Toast.LENGTH_SHORT).show()
        }
    }
}