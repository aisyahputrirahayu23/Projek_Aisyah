package com.example.projek_aisyah.obat

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ObatSayaTabsAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    // Jumlah total tab
    override fun getItemCount(): Int = 3

    // Fragment yang ditampilkan berdasarkan posisi tab
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabObatFragment()
            1 -> TabTipsFragment()
            2 -> TabTentangFragment()
            else -> throw IllegalStateException("Posisi tidak valid")
        }
    }
}