package by.nalivajr.imagelistdemo.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import by.nalivajr.imagelistdemo.domain.model.ImagesPage
import by.nalivajr.imagelistdemo.ui.fragment.ImagesGridFragment

class ImagesPageAdapter(fragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var pages: List<ImagesPage> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int = pages.size

    override fun getItem(position: Int): Fragment {
        return ImagesGridFragment.newInstance(pages[position].images)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}