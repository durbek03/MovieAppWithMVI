package com.example.movieappwithmvi.presenter.mainPage.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieappwithmvi.presenter.mainPage.DummyFragment

class ParentViewPagerAdapter(val genreList: List<String>, fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun createFragment(position: Int): Fragment {
        val dummyFrag = DummyFragment()
        dummyFrag.arguments = Bundle().apply { putString("genre", genreList[position]) }
        return dummyFrag
    }
}