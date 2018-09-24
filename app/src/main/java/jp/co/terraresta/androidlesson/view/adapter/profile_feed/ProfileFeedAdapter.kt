package jp.co.terraresta.androidlesson.view.adapter.profile_feed

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import jp.co.terraresta.androidlesson.view.view_model.profile_feed.ProfileFeedViewModel
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.MainActivity
import android.view.LayoutInflater
import android.databinding.adapters.TextViewBindingAdapter.setText
import com.squareup.picasso.Picasso
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_USER_ID
import jp.co.terraresta.androidlesson.data.model.profile_feed.ProfileFeedItem
import jp.co.terraresta.androidlesson.view.activity.profile.ProfileDisplayActivity


/**
 * Created by ooyama on 2017/05/29.
 */

class ProfileFeedAdapter(data: MutableList<ProfileFeedItem>, ctx: Context): RecyclerView.Adapter<ProfileFeedAdapter.MyViewHolder>() {

    fun getData(data: List<ProfileFeedItem>){
       dataset = data
    }

    var dataset:List<ProfileFeedItem> = data
    var context: Context = ctx

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.adapter_card_profile_feed, parent, false)

//        view.setOnClickListener(MainActivity.myOnClickListener)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val textViewName = holder?.textViewName
        val imageView = holder?.imageViewIcon

        textViewName?.text = dataset[position].nickname
        if(dataset[position].imageId != 0){
            Picasso.with(this.context).load(dataset[position].imageUrl).into(imageView)
        } else {
           imageView?.setImageResource(R.drawable.ic_android_black_24dp)
        }

        imageView?.setOnClickListener {
            var intent: Intent = Intent(this.context, ProfileDisplayActivity::class.java)
            intent.putExtra(REQUEST_NAME_USER_ID, dataset[position].userId)
            this.context.startActivity(intent)
        }
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewName: TextView
        var imageViewIcon: CircleImageView

        init {
            this.textViewName = itemView.findViewById(R.id.profile_feed_name) as TextView
            this.imageViewIcon = itemView.findViewById(R.id.profile_feed_image) as CircleImageView
        }
    }

}
