package com.example.projek_aisyah.obat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projek_aisyah.R
import com.example.projek_aisyah.databinding.FragmentTabObatBinding

class TabObatFragment : Fragment() {

    private var _binding: FragmentTabObatBinding? = null
    private val binding get() = _binding!!

    // Data 10 obat
    private val obatList = listOf(
        ObatModel("Amlodipine", "5 mg", "Tablet", "💊", 12, "tablet", "1x sehari · setelah makan"),
        ObatModel("Metformin", "500 mg", "Tablet", "💊", 4, "tablet", "2x sehari · setelah makan"),
        ObatModel("Vitamin C", "500 mg", "Tablet", "💊", 30, "tablet", "1x sehari · pagi hari"),
        ObatModel("Omeprazole", "20 mg", "Kapsul", "🧴", 3, "kapsul", "1x sehari · sebelum makan"),
        ObatModel("Amoxicillin", "500 mg", "Kapsul", "🧴", 14, "kapsul", "3x sehari · setelah makan"),
        ObatModel("OBH Combi", "100 ml", "Sirup", "🧪", 1, "botol", "3x sehari · 1 sendok makan", batas = 1),
        ObatModel("Hidrokortison", "1%", "Salep", "🩹", 3, "tube", "2x sehari · oleskan tipis", batas = 1),
        ObatModel("Tetes Mata", "10 ml", "Tetes", "💧", 1, "botol", "3x sehari · 1-2 tetes", batas = 1),
        ObatModel("Insulin", "100 IU/ml", "Suntik", "💉", 6, "pen", "Sebelum makan · sesuai dosis", batas = 2),
        ObatModel("Paracetamol", "500 mg", "Tablet", "💊", 20, "tablet", "Bila perlu · maksimal 3x sehari")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabObatBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val obatAdapter = ObatAdapter(obatList) { selectedItem ->
            Toast.makeText(
                requireContext(),
                "${selectedItem.nama} · ${selectedItem.aturan}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvObat.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = obatAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}