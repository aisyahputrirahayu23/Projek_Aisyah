package com.example.projek_aisyah.obat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projek_aisyah.R
import com.example.projek_aisyah.TambahObatActivity
import com.example.projek_aisyah.data.AppDatabase
import com.example.projek_aisyah.data.entity.ObatEntity
import com.example.projek_aisyah.databinding.FragmentTabObatBinding
import kotlinx.coroutines.launch

class TabObatFragment : Fragment() {

    private var _binding: FragmentTabObatBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var obatAdapter: ObatAdapter
    private val obatList = mutableListOf<ObatEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabObatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())

        obatAdapter = ObatAdapter(
            obatList,
            onItemClick = { selected ->
                Toast.makeText(
                    requireContext(),
                    "${selected.nama} · ${selected.aturan} · ${selected.frekuensi}x sehari · ${selected.jam}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onDelete = { selected -> deleteObat(selected) }
        )

        binding.rvObat.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = obatAdapter
        }

        binding.fabTambahObat.setOnClickListener {
            startActivity(Intent(requireContext(), TambahObatActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Muat ulang agar obat baru langsung tampil setelah kembali dari form
        if (_binding != null) fetchObat()
    }

    private fun fetchObat() {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = db.obatDao().getAll()
            obatList.clear()
            obatList.addAll(result)
            obatAdapter.notifyDataSetChanged()
            binding.tvKosong.visibility = if (obatList.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun deleteObat(obat: ObatEntity) {
        viewLifecycleOwner.lifecycleScope.launch {
            db.obatDao().delete(obat)
            fetchObat()
        }
    }

    override fun onDestroyView() {
        binding.rvObat.adapter = null
        _binding = null
        super.onDestroyView()
    }
}