package com.elmirov.course.ui.channels

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val children: List<Fragment>,
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int =
        children.size

    override fun createFragment(position: Int): Fragment =
        children[position]
}