package com.example.projek_aisyah

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projek_aisyah.databinding.ActivityTambahObatBinding
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Locale

class TambahObatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahObatBinding

    private var frekuensi = 1
    private var jam = 13
    private var menit = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTambahObatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol kembali
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Stepper frekuensi per hari (1 sampai 6)
        updateFrekuensi()
        binding.btnKurang.setOnClickListener {
            if (frekuensi > 1) {
                frekuensi--
                updateFrekuensi()
            }
        }
        binding.btnTambahFrekuensi.setOnClickListener {
            if (frekuensi < 6) {
                frekuensi++
                updateFrekuensi()
            }
        }

        // Pilih jam minum
        binding.cardJam.setOnClickListener {
            tampilkanTimePicker()
        }

        // Simpan
        binding.btnSimpan.setOnClickListener {
            simpanObat()
        }
    }

    private fun updateFrekuensi() {
        binding.tvFrekuensi.text = "$frekuensi kali sehari"
        binding.btnKurang.alpha = if (frekuensi == 1) 0.4f else 1f
        binding.btnTambahFrekuensi.alpha = if (frekuensi == 6) 0.4f else 1f
    }

    private fun tampilkanTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(jam)
            .setMinute(menit)
            .setTitleText("Pilih jam minum")
            .build()

        picker.addOnPositiveButtonClickListener {
            jam = picker.hour
            menit = picker.minute
            binding.tvJam.text = String.format(Locale.getDefault(), "%02d:%02d", jam, menit)
        }
        picker.show(supportFragmentManager, "timePicker")
    }

    private fun simpanObat() {
        val nama = binding.etNama.text.toString().trim()
        val dosis = binding.etDosis.text.toString().trim()
        val stok = binding.etStok.text.toString().trim().toIntOrNull() ?: 0

        // Validasi input
        var valid = true
        binding.tilNama.error = if (nama.isEmpty()) {
            valid = false
            "Nama obat wajib diisi"
        } else null

        binding.tilDosis.error = if (dosis.isEmpty()) {
            valid = false
            "Dosis wajib diisi"
        } else null

        binding.tilStok.error = if (stok <= 0) {
            valid = false
            "Isi stok lebih dari 0"
        } else null

        if (!valid) {
            Snackbar.make(binding.root, "Lengkapi data obat dulu", Snackbar.LENGTH_SHORT).show()
            return
        }

        val bentuk = binding.root.findViewById<Chip>(binding.cgBentuk.checkedChipId).text.toString()
        val aturan = binding.root.findViewById<Chip>(binding.cgAturan.checkedChipId).text.toString()
        val jamText = String.format(Locale.getDefault(), "%02d:%02d", jam, menit)

        // ALERT DIALOG: konfirmasi simpan
        MaterialAlertDialogBuilder(this)
            .setTitle("Simpan obat?")
            .setMessage(
                "$nama $dosis ($bentuk)\n" +
                        "$aturan · $frekuensi kali sehari · $jamText\n" +
                        "Stok: $stok"
            )
            .setPositiveButton("Ya") { dialog, _ ->
                dialog.dismiss()
                // TODO: simpan ke database
                Log.e("Info Dialog", "Obat disimpan: $nama $dosis")
                Toast.makeText(this, "Obat berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}