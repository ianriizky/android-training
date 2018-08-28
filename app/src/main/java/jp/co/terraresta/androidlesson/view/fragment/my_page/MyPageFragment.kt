package jp.co.terraresta.androidlesson.view.fragment.my_page

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import jp.co.terraresta.androidlesson.MainActivity

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.common.Preferences


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
        var logoutIntent: Intent = Intent(this.context, MainActivity::class.java)
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK )
        this.context.startActivity(logoutIntent)
    }




}




