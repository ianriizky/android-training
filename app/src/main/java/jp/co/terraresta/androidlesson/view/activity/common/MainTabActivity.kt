package jp.co.terraresta.androidlesson.view.activity.common

import android.content.Context
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.presenter.common.MainTabContract
import jp.co.terraresta.androidlesson.presenter.common.MainTabPresenter
import jp.co.terraresta.androidlesson.view.fragment.my_page.MyPageFragment
import jp.co.terraresta.androidlesson.view.fragment.profile_feed.ProfileFeedFragment
import jp.co.terraresta.androidlesson.view.fragment.talk.TalkListFragment
import kotlinx.android.synthetic.main.activity_main_tab.*

/**
 * Created by ooyama on 2017/05/29.
 */

class MainTabActivity : AppCompatActivity(),  MainTabContract.View{
    override fun getContext(): Context {
        return this
    }

    var viewPager: ViewPager? = null
    var tabPresenter: MainTabPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main_tab)

        setTabsView()
        tabPresenter = MainTabPresenter(this)

    }

    fun setTabsView(){
        viewPager = findViewById(R.id.container) as ViewPager
        tabs.setupWithViewPager(viewPager)
        var tabFragmentAdapter: MainTabFragmentAdapter = MainTabFragmentAdapter(supportFragmentManager)

        tabFragmentAdapter.addFragment(ProfileFeedFragment(), "Feed")
        tabFragmentAdapter.addFragment(TalkListFragment(), "Message")
        tabFragmentAdapter.addFragment(MyPageFragment(), "Mypage")

        viewPager?.adapter = tabFragmentAdapter
// Set icon tabs
        var icon:Array<Int> = arrayOf<Int> (
                R.drawable.ic_collections_black_24dp,
                R.drawable.ic_message_black_24dp,
                R.drawable.ic_person_black_24dp
        )
        for (i:Int in icon.indices) {
            tabs.getTabAt(i)?.setIcon(icon[i])
        }
    }

}
