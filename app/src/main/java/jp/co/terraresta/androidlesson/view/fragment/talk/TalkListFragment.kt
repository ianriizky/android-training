package jp.co.terraresta.androidlesson.view.fragment.talk

import android.app.AlertDialog
import android.app.Fragment
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.data.model.talk.TalkListData
import jp.co.terraresta.androidlesson.data.model.talk.TalkListItem
import jp.co.terraresta.androidlesson.data.model.talk.TalkListItemRealm
import jp.co.terraresta.androidlesson.presenter.talk.TalkListContract
import jp.co.terraresta.androidlesson.presenter.talk.TalkListPresenter
import jp.co.terraresta.androidlesson.view.adapter.talk.TalkListAdapter
import kotlinx.android.synthetic.main.fragment_talk_list.*

/**
 * Created by ooyama on 2017/05/29.
 */

class TalkListFragment : android.support.v4.app.Fragment(),  TalkListContract.View{
    override fun setRess(data: List<TalkListItem> ) {
//        for(i in data.indices){
//            println("DATA NICKNAME: " +data[i].nickname)
//            this.data!!.add(data[i])
//
//        }
        adapter = TalkListAdapter(data, this.context)
        viewRecycler?.adapter = adapter
        adapter?.getChecked(false)
        textBg?.visibility = View.GONE
        talk_refresh.isRefreshing = false
        adapter?.notifyDataSetChanged()
    }

    override fun showError(msg: String) {
        alertError(msg)
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
    var viewTalkList: View? = null
    var talkListPresnter: TalkListContract.Presenter? = null
    var loading: ProgressDialog? = null
    var data: List<TalkListItem>? = ArrayList()
    var checkItem:Boolean  = false
    var textBg: TextView? = null
    var delTalk: Button? = null
    var refreshTalkList: SwipeRefreshLayout? = null
    var menuItemEdit: MenuItem? = null

    var viewRecycler: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: TalkListAdapter? = null



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        viewTalkList= inflater?.inflate(R.layout.fragment_talk_list, container, false)

        talkListPresnter = TalkListPresenter(this.context, this)
        talkListPresnter?.fetchTalkList()

        textBg = viewTalkList?.findViewById(R.id.tv_default) as TextView
        delTalk = viewTalkList?.findViewById(R.id.btn_talk_delete) as Button
        refreshTalkList = viewTalkList?.findViewById(R.id.talk_refresh) as SwipeRefreshLayout

        delTalk!!.visibility = View.GONE

        viewRecycler = viewTalkList?.findViewById(R.id.rv_profilefeed) as RecyclerView
        layoutManager = LinearLayoutManager(this.context)
        viewRecycler!!.layoutManager = layoutManager

        delTalk!!.setOnClickListener {
            var userid: ArrayList<String> = adapter!!.getUserId()
            var strings: String = ""
            for(i in userid.indices){
                strings = strings.plus(userid[i] +", ")
            }
            talkListPresnter?.delTalkList(strings)
            adapter?.getChecked(false)
            delTalk!!.visibility = View.GONE
            menuItemEdit?.title = "Edit"
            adapter!!.notifyDataSetChanged()
        }

        refreshTalkList!!.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                talkListPresnter?.fetchTalkList()
            }

        })
        return viewTalkList
    }

    fun loader() {
        loading = ProgressDialog(activity)
        loading?.setMessage("Fetching Profile Feed...")
        loading?.setCancelable(false)
        loading?.show()
    }

    /*
     * ALERT ERROR
      */
    fun alertError(message: String) {
        var alertError = AlertDialog.Builder(this.context).create()
        alertError?.setMessage(message)
        alertError?.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            alertError.dismiss()
        })
        alertError.show()
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.talklist_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        menuItemEdit = item
        when(item?.itemId){
           R.id.edit_talklist -> {
               val title = item!!.title
               if (title.equals("Edit") ) {
                   adapter?.getChecked(true)
                   item!!.title = "Cancel"
                   delTalk?.visibility = View.VISIBLE
               } else {
                   adapter?.getChecked(false)
                   item!!.title = "Edit"
                   delTalk?.visibility = View.GONE
               }
               adapter?.notifyDataSetChanged()
           }
        }
        return super.onOptionsItemSelected(item)
    }
}
