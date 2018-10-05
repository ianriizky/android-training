package jp.co.terraresta.androidlesson.view.adapter.talk

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.Image
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_KEY_IMAGE_URL
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_KEY_VIDEO_URL
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_USER_ID
import jp.co.terraresta.androidlesson.common.Preferences
import jp.co.terraresta.androidlesson.data.model.talk.TalkItem
import jp.co.terraresta.androidlesson.view.activity.media.PhotoViewerActivity
import jp.co.terraresta.androidlesson.view.activity.media.VideoViewerActivity
import jp.co.terraresta.androidlesson.view.activity.profile.ProfileDisplayActivity
import org.w3c.dom.Text
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ooyama on 2017/05/29.
 */


class TalkAdapter(data: List<TalkItem>, ctx: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var dataset: List<TalkItem> = data
    var context: Context = ctx
    var pref: Preferences = Preferences()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder?
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_talk_in, parent, false)
        viewHolder = Talk(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
       return dataset.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val listData = dataset[position]
        val viewhold: Talk = holder as Talk

        // CHAT LOGICAL
        if(listData.messageKind == 2)
        {
            // OURS CHAT
            viewhold.message_right_container.visibility = View.VISIBLE
            viewhold.message_left_container.visibility = View.GONE
            viewhold.photoProfile.visibility = View.GONE

            // display time chat
            viewhold.time_right.visibility = View.VISIBLE
            viewhold.time_left.visibility = View.GONE
            viewhold.time_right.text = generateTimeText(listData.time!!)

            // display type of message (text or image or video)
            if(listData.mediaType == 1){
                // IMAGE MESSAGE TYPE
                viewhold.message_right_img.visibility = View.VISIBLE
                viewhold.message_right.text = ""

                // load image
                Picasso.with(context)
                        .load(listData.mediaUrl)
                        .into(viewhold.message_right_img)
                // image detail
                viewhold.message_right_img.setOnClickListener {
                    val intent = Intent(context, PhotoViewerActivity::class.java)
                    intent.putExtra(REQUEST_KEY_IMAGE_URL, listData.mediaUrl)
                    context.startActivity(intent)
                }
            } else if(listData.mediaType == 2){
                // VIDEO MESSAGE TYPE
                viewhold.message_right_img.visibility = View.VISIBLE
                viewhold.message_right.text = ""

                // play video
                viewhold.message_right_img.setImageResource(R.drawable.ic_play_circle_filled_black_24dp)
                // video detail
                viewhold.message_right_img.setOnClickListener {
                    val intent = Intent(context, VideoViewerActivity::class.java)
                    intent.putExtra(REQUEST_KEY_VIDEO_URL, listData.mediaUrl)
                    context.startActivity(intent)
                }
            } else {
                // TEXT MESSAGE TYPE
                viewhold.message_right_img.visibility = View.GONE
                viewhold.message_right.text = listData.message
            }
        } else { // -------------------------------------------------------------------------------------- //
            // OPPONENTS CHAT
            viewhold.photoProfile.visibility = View.VISIBLE
            viewhold.message_left_container.visibility = View.VISIBLE
            viewhold.message_right_container.visibility = View.GONE

            // display time chat
            viewhold.time_right.visibility = View.GONE
            viewhold.time_left.visibility = View.VISIBLE
            viewhold.time_left.text = generateTimeText(listData.time!!)

            viewhold.photoProfile.setOnClickListener {
                val intent = Intent(context, ProfileDisplayActivity::class.java)
                intent.putExtra(REQUEST_NAME_USER_ID, listData.userId)
                context.startActivity(intent)
            }
            // display type of message (text or image or video)
            if(listData.mediaType == 1){
                // IMAGE MESSAGE TYPE
                viewhold.message_left_img.visibility = View.VISIBLE
                viewhold.message_left.text = ""

                // load image
                Picasso.with(context)
                        .load(listData.mediaUrl)
                        .into(viewhold.message_left_img)
                // image detail
                viewhold.message_left_img.setOnClickListener {
                    val intent = Intent(context, PhotoViewerActivity::class.java)
                    intent.putExtra(REQUEST_KEY_IMAGE_URL, listData.mediaUrl)
                    context.startActivity(intent)
                }
            } else if(listData.mediaType == 2){
                // VIDEO MESSAGE TYPE
                viewhold.message_left_img.visibility = View.VISIBLE
                viewhold.message_left.text = ""

                // play video
                viewhold.message_left_img.setImageResource(R.drawable.ic_play_circle_filled_black_24dp)
                // video detail
                viewhold.message_left_img.setOnClickListener {
                    val intent = Intent(context, VideoViewerActivity::class.java)
                    intent.putExtra(REQUEST_KEY_VIDEO_URL, listData.mediaUrl)
                    context.startActivity(intent)
                }
            } else {
                // TEXT MESSAGE TYPE
                // set message
                viewhold.message_left_img.visibility = View.GONE
                viewhold.message_left.text = listData.message
            }
            // set photo profile
            if(listData.imageId != 0){
                Picasso.with(context)
                        .load(listData.imageUrl)
                        .resize(50,50)
                        .into(viewhold.photoProfile)
            } else {
                viewhold.photoProfile.setImageResource(R.drawable.ic_android_black_24dp)
            }
        }

        // set date message divider
        var prevpos = ""
        if(position > 0){
            var previndex = position - 1
            prevpos = dataset[previndex].time!!
        }
        // call date divider logical
        checkDate(dataset[position].time!!, prevpos, viewhold)
    }

    // date message divider logical
    private fun checkDate(curDate: String, prevDate: String, viewhold: Talk){
        val format = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")

        if(prevDate == ""){
            // if prev date index is 0
            val date = format.parse(curDate)
            viewhold.message_date_cont.visibility = View.VISIBLE
            viewhold.message_date.text = generateDateText(date)
        } else {
            // start to compare prev date and current date
            val date = format.parse(curDate)
            val prevdate = format.parse(prevDate)

            if (date.date == prevdate.date) {
                viewhold.message_date_cont.visibility = View.GONE
                viewhold.message_date.text = ""
            } else {
                // set view visible when the date is different
                viewhold.message_date_cont.visibility = View.VISIBLE
                viewhold.message_date.text = generateDateText(date)

            }
        }

    }

    // set time message logical
    private fun generateTimeText(date: String): String {
        val format = SimpleDateFormat("yyyy/MM/dd hh:mm:ss") // format date
        val decFormat = DecimalFormat("00") // decimal format
        val mDate = format.parse(date) // parse date from format date has been made
        val cal = Calendar.getInstance()
        cal.time = mDate // parse date data to calendar

        val h = cal.get(Calendar.HOUR) // get hour from calender
        val m = cal.get(Calendar.MINUTE)
        val finalminute = decFormat.format(java.lang.Double.valueOf(m.toString())) // parse to decimal format
        val finalhour = decFormat.format(java.lang.Double.valueOf(h.toString()))

        val finaltime :String= finalhour.toString() + ":" + finalminute
        return finaltime
    }

    // set date message logical
    // same logical from above
    private fun generateDateText(date: Date):String{
        val mFormat = DecimalFormat("00")
        val cal = Calendar.getInstance()
        cal.time = date

        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        val finalmonth = mFormat.format(java.lang.Double.valueOf(m.toString()))
        val finalday = mFormat.format(java.lang.Double.valueOf(d.toString()))

        val finaldate = finalday.toString() + "/" +finalmonth +"/" +y
        println("day : $finaldate")
       return  finaldate
    }



    open class Talk(itemView: View): RecyclerView.ViewHolder(itemView){
        var photoProfile: CircleImageView
        var message_left_container: LinearLayout
        var message_right_container: LinearLayout
        var message_left: TextView
        var message_right: TextView
        var message_right_img: ImageView
        var message_left_img: ImageView
        var message_date: TextView
        var message_date_cont: RelativeLayout
        var time_left: TextView
        var time_right: TextView
        var message_left_parent: RelativeLayout
        var message_right_parent: RelativeLayout
        init {
            this.photoProfile = itemView.findViewById(R.id.photo_profile_talk) as CircleImageView
            this.message_left = itemView.findViewById(R.id.msg_talk_in) as TextView
            this.message_left_container = itemView.findViewById(R.id.msg_talk_in_wrapper) as LinearLayout
            this.time_left = itemView.findViewById(R.id.time_talk_in) as TextView
            this.message_right = itemView.findViewById(R.id.msg_talk_out) as TextView
            this.message_right_container = itemView.findViewById(R.id.msg_talk_out_wrapper) as LinearLayout
            this.time_right = itemView.findViewById(R.id.time_talk_out) as TextView
            this.message_date = itemView.findViewById(R.id.talk_date) as TextView
            this.message_date_cont = itemView.findViewById(R.id.talk_date_cont) as RelativeLayout
            this.message_right_img = itemView.findViewById(R.id.msg_talk_out_img) as ImageView
            this.message_left_img = itemView.findViewById(R.id.msg_talk_in_img) as ImageView
            this.message_left_parent = itemView.findViewById(R.id.msg_talk_in_parent) as RelativeLayout
            this.message_right_parent = itemView.findViewById(R.id.msg_talk_out_parent) as RelativeLayout

            this.photoProfile.visibility = View.GONE
            this.message_left_container.visibility = View.GONE
            this.message_right_container.visibility = View.GONE
            this.time_left.visibility = View.GONE
//            this.time_right.visibility = View.GONE
            this.message_right_img.visibility = View.GONE
            this.message_left_img.visibility = View.GONE
            this.message_date_cont.visibility = View.GONE
        }
    }

}

