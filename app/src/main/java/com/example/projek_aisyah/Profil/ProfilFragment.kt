package com.example.projek_aisyah.Profil

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.projek_aisyah.LoginActivity
import com.example.projek_aisyah.databinding.FragmentProfilBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private val pilihanTunda = listOf(5, 10, 15, 30)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val pengaturan = requireContext().getSharedPreferences("pengaturan_pref", Context.MODE_PRIVATE)

        // ---- Profil ----
        var nama = requireActivity().intent.getStringExtra("nama") ?: "Pengguna"
        tampilkanNama(nama)

        binding.btnUbah.setOnClickListener {
            val input = EditText(requireContext()).apply {
                inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                hint = "Nama"
                setText(nama)
                setSelection(text.length)
                setPadding(dp(24), dp(16), dp(24), dp(8))
            }
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Ubah nama")
                .setView(input)
                .setPositiveButton("Simpan") { dialog, _ ->
                    val baru = input.text.toString().trim()
                    if (baru.isNotEmpty()) {
                        nama = baru
                        tampilkanNama(nama)
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        // ---- Pengaturan pengingat ----
        ikatSwitch(binding.switchNotifikasi, "notifikasi", pengaturan)
        ikatSwitch(binding.switchSuara, "suara", pengaturan)
        ikatSwitch(binding.switchStok, "stok", pengaturan)

        var lamaTunda = pengaturan.getInt("lama_tunda", 10)
        binding.tvLamaTunda.text = "$lamaTunda menit"
        binding.rowLamaTunda.setOnClickListener {
            val pilihan = pilihanTunda.map { "$it menit" }.toTypedArray()
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Lama tunda")
                .setSingleChoiceItems(pilihan, pilihanTunda.indexOf(lamaTunda)) { dialog, which ->
                    lamaTunda = pilihanTunda[which]
                    pengaturan.edit().putInt("lama_tunda", lamaTunda).apply()
                    binding.tvLamaTunda.text = "$lamaTunda menit"
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        // ---- Lainnya ----
        binding.rowTema.setOnClickListener {
            Snackbar.make(binding.root, "Mode gelap akan segera hadir", Snackbar.LENGTH_SHORT).show()
        }

        binding.rowTentang.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("MediRemind")
                .setMessage(
                    "Versi 1.0\n" +
                            "Aplikasi pengingat jadwal minum obat dan pencatat kepatuhan harian.\n\n" +
                            "Proyek mata kuliah Mobile Programming."
                )
                .setPositiveButton("Tutup") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        // LOGOUT
        binding.rowLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    userPref.edit().clear().apply()
                    dialog.dismiss()
                    val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // supaya tombol Back dari halaman login tidak kembali ke aplikasi
                    }
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    // Tampilkan nama dan huruf pertamanya di avatar
    private fun tampilkanNama(nama: String) {
        binding.tvNama.text = nama
        binding.tvInisial.text = nama.take(1).uppercase()
    }

    // Muat status switch dari SharedPreferences, simpan setiap kali berubah
    private fun ikatSwitch(sw: SwitchCompat, kunci: String, pref: SharedPreferences) {
        sw.isChecked = pref.getBoolean(kunci, true)
        sw.setOnCheckedChangeListener { _, isChecked ->
            pref.edit().putBoolean(kunci, isChecked).apply()
        }
    }

    private fun dp(nilai: Int): Int = (nilai * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}