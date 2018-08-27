package jp.co.terraresta.androidlesson.view.activity.common

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainTabFragmentAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    var mFragmentTitleList: MutableList<String>? = mutableListOf<String>()
    var mFragmentList: MutableList<Fragment>? = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment, title:String) {
        mFragmentList?.add(fragment)
        mFragmentTitleList?.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList!!.get(position)
    }

    override fun getCount(): Int {
        return mFragmentList!!.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitleList!!.get(position)
    }

}