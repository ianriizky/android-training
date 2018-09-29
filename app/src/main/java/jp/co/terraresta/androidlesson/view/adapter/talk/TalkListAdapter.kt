package jp.co.terraresta.androidlesson.view.adapter.talk

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.data.model.talk.TalkListItem
import jp.co.terraresta.androidlesson.view.activity.talk.TalkActivity

/**
 * Created by ooyama on 2017/05/29.
 */

class TalkListAdapter(data:MutableList<TalkListItem>, ctx: Context ): RecyclerView.Adapter<TalkListAdapter.TalkListViewHolder>() {

    var dataset: List<TalkListItem> = data
    var context: Context = ctx
    var checkItem: Boolean = false
    var arrayList: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TalkListViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.adapter_talk_list, parent, false)

        return TalkListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: TalkListViewHolder?, position: Int) {
        val itemList = dataset.get(position)
        val msg = holder?.message
        val nickname = holder?.nickname
        val avatar = holder?.avatar
        val checkbox = holder?.checkItem
        val talkWrap = holder?.talkWrap

        if(checkItem){
            checkbox!!.visibility = View.VISIBLE
            // prevent replace the checkbox state after scrolling
            checkbox.setOnCheckedChangeListener(null)
            // change state checkbox
            checkbox.isChecked = itemList.isSelected!!
            checkbox.setOnCheckedChangeListener { compoundButton, b ->
                itemList.isSelected = b
                if(compoundButton.isChecked){
                    arrayList.add(itemList.talkId.toString())
                } else {
                   arrayList.remove(itemList.talkId.toString())
                }
            }
        } else {
            // reset item checked
            for(i in dataset.indices){
                dataset[i].isSelected = false
            }
            //reset id talk string
            arrayList.clear()
            checkbox!!.visibility = View.GONE
        }


        nickname!!.text = itemList.nickname
        msg!!.text = itemList.message
        if(dataset[position].imageId != 0){
            Picasso.with(this.context).load(itemList.imageUrl)
                    .resize(100, 100)
                    .centerInside()
                    .into(avatar)
        }else {
            avatar?.setImageResource(R.drawable.ic_android_black_24dp)
        }

        talkWrap?.setOnClickListener {
            val intent = Intent(this.context, TalkActivity::class.java)
            this.context.startActivity(intent)
        }
    }

    fun getUserId(): ArrayList<String>{
        return arrayList
    }

    fun getChecked(checked: Boolean){
        checkItem = checked
    }


    class TalkListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var avatar: CircleImageView
        var nickname: TextView
        var message: TextView
        var talkWrap: LinearLayout
        var checkItem: CheckBox

        init {
            this.avatar = itemView.findViewById(R.id.ci_avatar) as CircleImageView
            this.nickname = itemView.findViewById(R.id.tv_nickname) as TextView
            this.message = itemView.findViewById(R.id.tv_msg) as TextView
            this.talkWrap = itemView.findViewById(R.id.ll_item) as LinearLayout
            checkItem = itemView.findViewById(R.id.cb_itemtalk) as CheckBox

            this.avatar.setImageResource(R.drawable.ic_android_black_24dp)

        }
    }
}
