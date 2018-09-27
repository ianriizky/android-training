package jp.co.terraresta.androidlesson.view.fragment.profile_feed

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.view.view_model.profile_feed.ProfileFeedViewModel
import android.R.attr.data
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.widget.GridLayout
import android.widget.ProgressBar
import jp.co.terraresta.androidlesson.data.model.profile_feed.ProfileFeedData
import jp.co.terraresta.androidlesson.data.model.profile_feed.ProfileFeedItem
import jp.co.terraresta.androidlesson.presenter.profile_feed.ProfileFeedContract
import jp.co.terraresta.androidlesson.presenter.profile_feed.ProfileFeedPresenter
import jp.co.terraresta.androidlesson.view.adapter.profile_feed.ProfileFeedAdapter


/**
* Created by ooyama on 2017/05/29.
*/

class ProfileFeedFragment : android.support.v4.app.Fragment(), ProfileFeedContract.View {
    override fun setNextRess(data: ProfileFeedData) {
        for(i in data.items!!.indices){
            this.data.add(data.items!![i])
        }
        adapter?.notifyDataSetChanged()
    }

    override fun showError(msg: String) {
    showError(msg)
}

override fun setRess(data: ProfileFeedData) {
    for(i in data.items!!.indices){
        this.data.add(data.items!![i])
    }

    recycleView!!.adapter = adapter
    adapter?.notifyDataSetChanged()

    pullRefresher?.setRefreshing(false)
//    loadProgress!!.visibility = View.VISIBLE
    loading?.dismiss()
}


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
}

    var dataprofile: MutableList<ProfileFeedItem>? = null
var profileFeedPresenter: ProfileFeedContract.Presenter? = null
var layoutManager: RecyclerView.LayoutManager? = null
var recycleView: RecyclerView? = null
var pullRefresher: SwipeRefreshLayout? = null
var loadProgress: ProgressBar? = null
var adapter: RecyclerView.Adapter<*>? = null
private var data: MutableList<ProfileFeedItem> = ArrayList()
var loading: ProgressDialog? = null
var feedView: View? = null


override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    feedView = inflater?.inflate(R.layout.fragment_profile_feed, container, false)
    intialize()
    return feedView
    }

    fun intialize(){

        profileFeedPresenter = ProfileFeedPresenter(this.context, this)
        profileFeedPresenter?.fetchProfileFeed()
        adapter  = ProfileFeedAdapter(data, this.context)

        pullRefresher = feedView?.findViewById(R.id.pull_refresh_container) as SwipeRefreshLayout
        loadProgress = feedView?.findViewById(R.id.item_progress) as ProgressBar
        recycleView = feedView?.findViewById(R.id.rv_profilefeed) as RecyclerView
        layoutManager = GridLayoutManager(this.context, 2)
        recycleView?.layoutManager = layoutManager
        recycleView?.itemAnimator = DefaultItemAnimator()

        loadProgress!!.visibility = View.GONE
        pullRefresher!!.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                reloadData()
            }

        })

        recycleView?.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(dy > 0) //check for scroll down
                {
                    var layout = recyclerView?.layoutManager as GridLayoutManager
                    var lastItem = layout.findLastCompletelyVisibleItemPosition()
                    var current = layout.itemCount
                    if(current <= lastItem+2){

                        profileFeedPresenter?.fetchProfileNext()
                    }

                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    fun reloadData(){
        profileFeedPresenter?.fetchProfileFeed()
        loadProgress!!.visibility = View.VISIBLE
    }

    fun alertError(message: String) {
        var alertError = AlertDialog.Builder(this.context).create()
        alertError?.setMessage(message)
        alertError?.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            alertError.dismiss()
        })
        alertError.show()
    }

    fun loader() {
        loading = ProgressDialog(activity)
        loading?.setMessage("Fetching Profile Feed...")
        loading?.setCancelable(false)
        loading?.show()
    }
}
