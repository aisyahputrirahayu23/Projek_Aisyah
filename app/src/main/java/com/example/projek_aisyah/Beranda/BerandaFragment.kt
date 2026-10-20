package com.example.projek_aisyah.Beranda

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.projek_aisyah.DetailActivity
import com.example.projek_aisyah.LoginActivity
import com.example.projek_aisyah.R
import com.example.projek_aisyah.databinding.FragmentBerandaBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)

        val nama = requireActivity().intent.getStringExtra("nama")
        val umur = requireActivity().intent.getIntExtra("umur", 0)

        Log.w("Hasil", "Nama: $nama Umur: $umur")

        binding.btnKembali.setOnClickListener {
            requireActivity().finish()
//            val intent = Intent(this@MainActivity, LoginActivity::class.java)
//            startActivity(intent)
        }

        // tambah untuk nampilkan text view di halaman activity
        binding.nama.text = nama

        binding.btnSnackBar.setOnClickListener {
            Snackbar.make(binding.root, "Ini adalah Snackbar", Snackbar.LENGTH_SHORT)
                .setAction("Tutup") {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
//                    startActivity(intent)
                    Log.e("Info Snackbar", "Snackbar ditutup")
                }
                .show()
        }

        // tambah
        binding.btnAlertDialog.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
//                    startActivity(intent)
                    requireActivity().finish()
                    Log.e("Info Dialog", "Anda memilih Ya!")
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                    Log.e("Info Dialog", "Anda memilih Tidak!")
                }
                .show()
        }

        // Intent dari MainActivity ke DetailActivity
        binding.btnDetail.setOnClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()
                    dialog.dismiss()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }
}