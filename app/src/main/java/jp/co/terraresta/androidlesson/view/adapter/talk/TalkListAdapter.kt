package jp.co.terraresta.androidlesson.view.adapter.talk

import android.content.Context
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

/**
 * Created by ooyama on 2017/05/29.
 */

class TalkListAdapter(data:List<TalkListItem>, ctx: Context ): RecyclerView.Adapter<TalkListAdapter.TalkListViewHolder>() {

    var dataset: List<TalkListItem> = data
    var context: Context = ctx
    var checkItem: Boolean = false
    var checkbox: CheckBox? = null
    var arrayList: ArrayList<String> = ArrayList()
    var arrIndex: Array<Int> = arrayOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TalkListViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.adapter_talk_list, parent, false)

        return TalkListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: TalkListViewHolder?, position: Int) {
        val msg = holder?.message
        val nickname = holder?.nickname
        val avatar = holder?.avatar
         checkbox = holder?.itemCheck
        val item = holder?.item

        if(checkedbox()){
            checkbox!!.visibility = View.VISIBLE
            var strings: String =""
            checkbox!!.setOnCheckedChangeListener { compoundButton, b ->
                if(compoundButton.isChecked){
                    arrayList.add(dataset[position].talkId.toString())
                } else {
                   arrayList.remove(dataset[position].talkId.toString())
                }
            }
        } else {
            checkbox!!.isChecked = false
            checkbox!!.visibility = View.GONE
        }

        nickname!!.text = dataset[position].nickname
        msg!!.text = dataset[position].message
        if(dataset[position].imageId != 0){
            Picasso.with(this.context).load(dataset[position].imageUrl).into(avatar)
        } else {
            avatar?.setImageResource(R.drawable.ic_android_black_24dp)
        }
    }

    public fun getUserId(): ArrayList<String>{
        return arrayList
    }


    public fun checkedbox(): Boolean{
        return checkItem!!
    }

   public fun getChecked(checked: Boolean){
       checkItem = checked
   }


    class TalkListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var avatar: CircleImageView
        var nickname: TextView
        var message: TextView
        var itemCheck: CheckBox
        var item: LinearLayout

        init {
            this.avatar = itemView.findViewById(R.id.ci_avatar) as CircleImageView
            this.nickname = itemView.findViewById(R.id.tv_nickname) as TextView
            this.message = itemView.findViewById(R.id.tv_msg) as TextView
            this.itemCheck = itemView.findViewById(R.id.cb_itemtalk) as CheckBox
            this.item = itemView.findViewById(R.id.ll_item) as LinearLayout
        }
    }
}
