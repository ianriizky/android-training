package jp.co.terraresta.androidlesson.view.fragment.my_page

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import jp.co.terraresta.androidlesson.MainActivity

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.presenter.common.MainTabContract
import jp.co.terraresta.androidlesson.presenter.common.MainTabPresenter
import jp.co.terraresta.androidlesson.view.activity.common.MainTabActivity
import jp.co.terraresta.androidlesson.view.activity.talk.TalkActivity


/**
 * Created by ooyama on 2017/05/29.
 */

class MyPageFragment :  Fragment(){

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    var pref: Preferences = Preferences()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val view = inflater?.inflate(R.layout.fragment_my_page, container, false)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.logout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.logout) {
            logoutAction()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }

    }

    fun logoutAction(){
        pref.clearSharedPref(this.context)
        var newtintent: Intent = Intent(this.context, MainActivity::class.java)
        this.context.startActivity(newtintent)
    }




}




