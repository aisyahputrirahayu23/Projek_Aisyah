package com.example.projek_aisyah.obat

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projek_aisyah.R
import com.example.projek_aisyah.databinding.ActivityObatSayaBinding
import com.google.android.material.tabs.TabLayoutMediator

class ObatSayaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityObatSayaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObatSayaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Insets: hanya atas, kiri, kanan (supaya Toolbar tidak tertutup status bar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Obat Saya"
            setDisplayHomeAsUpEnabled(true) // menampilkan panah
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // 1. Inisialisasi adapter
        val tabsAdapter = ObatSayaTabsAdapter(this)

        // 2. Pasang adapter ke ViewPager2
        binding.viewPager.adapter = tabsAdapter

        // 3. Hubungkan TabLayout dan ViewPager2 (setelah adapter dipasang)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Obat Saya"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_box)
                    // Badge angka: jumlah obat yang stoknya menipis
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 4
                }

                1 -> {
                    tab.text = "Tips"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_bell)
                }

                2 -> {
                    tab.text = "Tentang"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_info)
                }
            }
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}