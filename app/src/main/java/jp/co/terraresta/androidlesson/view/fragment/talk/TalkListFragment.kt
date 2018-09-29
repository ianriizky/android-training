package jp.co.terraresta.androidlesson.view.fragment.talk

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.data.model.talk.TalkListItem
import jp.co.terraresta.androidlesson.presenter.talk.TalkListContract
import jp.co.terraresta.androidlesson.presenter.talk.TalkListPresenter
import jp.co.terraresta.androidlesson.view.adapter.talk.TalkListAdapter
import kotlinx.android.synthetic.main.fragment_talk_list.*

/**
 * Created by ooyama on 2017/05/29.
 */

class TalkListFragment : android.support.v4.app.Fragment(),  TalkListContract.View{
    private var loading: ProgressDialog? = null
    override fun setRess(data: List<TalkListItem> ) {
        dataTalk.clear()
        for(i in data.indices){
            dataTalk.add(data[i])
        }
        adapter?.notifyDataSetChanged()
        textBg?.visibility = View.GONE
        talkLayout?.visibility = View.VISIBLE
        talk_refresh.isRefreshing = false
        loading?.dismiss()
    }

    override fun showError(msg: String) {
        loading?.dismiss()
        talk_refresh.isRefreshing = false
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
    private var viewTalkList: View? = null
    var talkListPresnter: TalkListContract.Presenter? = null
    private var dataTalk: MutableList<TalkListItem> = ArrayList()
    private var textBg: TextView? = null
    private var delTalk: Button? = null
    private var talkLayout: ConstraintLayout? = null
    private var refreshTalkList: SwipeRefreshLayout? = null
    private var menuItemEdit: MenuItem? = null

    private var viewRecycler: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: TalkListAdapter? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        viewTalkList= inflater?.inflate(R.layout.fragment_talk_list, container, false)

        // init
        textBg = viewTalkList?.findViewById(R.id.tv_talklist_default) as TextView
        talkLayout = viewTalkList?.findViewById(R.id.constr_talklist) as ConstraintLayout
        delTalk = viewTalkList?.findViewById(R.id.btn_talk_delete) as Button
        refreshTalkList = viewTalkList?.findViewById(R.id.talk_refresh) as SwipeRefreshLayout
        viewRecycler = viewTalkList?.findViewById(R.id.rv_profilefeed) as RecyclerView

        talkListPresnter = TalkListPresenter(this.context, this)
        talkListPresnter?.fetchTalkList()
        adapter = TalkListAdapter(dataTalk, this.context)

        delTalk!!.visibility = View.GONE
        talkLayout!!.visibility = View.GONE

        viewRecycler?.adapter = adapter
        layoutManager = LinearLayoutManager(this.context)
        viewRecycler!!.layoutManager = layoutManager

        delTalk!!.setOnClickListener {
            val userID: ArrayList<String> = adapter!!.getUserId()
            if(userID.size != 0){
                println("TALK ID: " +userID.joinToString())
                talkListPresnter?.delTalkList(userID.joinToString())
                loader()
            } else {
               alertError("Please check at least one your message")
            }
//             reset checkbox, reset title menu button
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

    private fun loader() {
        loading = ProgressDialog(activity)
        loading?.setCancelable(false)
        loading?.setMessage("Deleting message...")
        loading?.show()
    }

    /*
     * ALERT ERROR
      */
    private fun alertError(message: String) {
        val alertError = AlertDialog.Builder(this.context).create()
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
               delTalk?.visibility = View.VISIBLE
               val title = item.title
               if (title == "Edit") {
                   adapter?.getChecked(true)
                   item.title = "Cancel"
                   delTalk?.visibility = View.VISIBLE
               } else {
                   adapter?.getChecked(false)
                   delTalk?.visibility = View.GONE
                   item.title = "Edit"
               }
               adapter?.notifyDataSetChanged()
           }
        }
        return super.onOptionsItemSelected(item)
    }
}
