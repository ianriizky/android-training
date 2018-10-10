package jp.co.terraresta.androidlesson.view.activity.talk

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import jp.co.terraresta.androidlesson.R
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_TO_USER_ID
import jp.co.terraresta.androidlesson.data.model.talk.TalkItem
import jp.co.terraresta.androidlesson.presenter.talk.TalkContract
import jp.co.terraresta.androidlesson.presenter.talk.TalkPresenter
import jp.co.terraresta.androidlesson.view.adapter.talk.TalkAdapter
import kotlinx.android.synthetic.main.activity_talk.*
import java.util.*
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.ActivityCompat
import android.widget.LinearLayout
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_CAMERA_ACTIVITY
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_CODE_GALLERY_ACTIVITY
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.content.DialogInterface
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.webkit.MimeTypeMap
import jp.co.terraresta.androidlesson.MainActivity
import jp.co.terraresta.androidlesson.common.Constants.REQUEST_NAME_NICKNAME
import java.io.File
import java.text.SimpleDateFormat


/**
 * Created by ooyama on 2017/05/29.
 */

class TalkActivity : AppCompatActivity(), TalkContract.View {
    override fun showError(msg: String) {
        loading?.dismiss()
        alertError(msg)
    }

    override fun setRess(data: List<TalkItem> ) {
            dataTalk.clear()
        for(i in data.indices){
            dataTalk.add(data[i])
        }
        recyclerTalkAdapter?.notifyDataSetChanged()
        loading?.dismiss()
    }

    private var dataTalk: MutableList<TalkItem> = ArrayList()
    private var recyclerTalkLayout: RecyclerView.LayoutManager? = null
    private var recyclerTalkAdapter: TalkAdapter? = null
    var talkPresenter: TalkContract.Presenter? = null
    var toUserId: Int? = null
    private var takePhoto: LinearLayout? = null
    private var takeGallery: LinearLayout? = null
    private var typeIntent: Int? = null
    private var photoFile: File? = null
    private var loading: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk)
        initialize()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /*
    * INIT
     */
    private fun initialize(){
        // get data from the intent (opponents user id)
        val intent: Intent = intent
        toUserId =  intent.getIntExtra(REQUEST_NAME_TO_USER_ID, 0)
        val nickname: String  = intent.getStringExtra(REQUEST_NAME_NICKNAME)

        toolbar_talk.setTitle(nickname)
        setSupportActionBar(toolbar_talk)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //init fetch data chat
        talkPresenter = TalkPresenter(this, this)
        talkPresenter?.fetchTalk(toUserId!!, 0)

        // Recylerview init
        recyclerTalkAdapter = TalkAdapter(dataTalk, this)
        recycler_view_talk.adapter = recyclerTalkAdapter
        val linearlayout = LinearLayoutManager(this)
        linearlayout.reverseLayout = true
        recyclerTalkLayout = linearlayout
        recycler_view_talk.layoutManager = recyclerTalkLayout

        // RECYLERVIEW SCROLL LISTENER
        recycler_view_talk.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy < 0){
                    val layout = recyclerView?.layoutManager as LinearLayoutManager
                    val lastItem = layout.findLastCompletelyVisibleItemPosition()
                    val current = layout.itemCount
                    // action when reach the top
                    if(current-1 == lastItem){
                        // fetch moar data
                        talkPresenter?.fetchTalk(toUserId!!, 1)
                    }
                }
            }
        })

       linear_layout_send.setOnClickListener {
           val message = edit_text_message.text.toString().trim()
           talkPresenter?.sendMessage(message)
           edit_text_message.setText("")
       }

        // ADD MEDIA MESSAGE
        image_button_add_media.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_dialog, null)
            takePhoto = view.findViewById(R.id.sheet_take_photo) as LinearLayout
            takeGallery = view.findViewById(R.id.sheet_select_photo) as LinearLayout

            val botSheet = BottomSheetDialog(this)
            botSheet.setContentView(view)
            botSheet.show()

            takePhoto?.setOnClickListener {
                openDialog()
                botSheet.dismiss()
            }
            takeGallery?.setOnClickListener{
                requestPermission(REQUEST_CODE_GALLERY_ACTIVITY)
                botSheet.dismiss()
            }

        }
    }

    fun loader() {
        loading = ProgressDialog(this)
        loading?.setMessage("uploading media...")
        loading?.setCancelable(false)
        loading?.show()
    }
    fun alertError(message: String) {
        var alertError = AlertDialog.Builder(this).create()
        alertError?.setMessage(message)
        alertError?.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            alertError.dismiss()
        })
        alertError.show()
    }

    // CHOOSE CAMERA ACTION PHOTO OR VIDEO
    private fun openDialog(){
        val value = arrayOf("Take Photo","Take Video")
        val alertdialogbuilder = AlertDialog.Builder(this)

        alertdialogbuilder.setTitle("Choose action")
        alertdialogbuilder.setItems(value, DialogInterface.OnClickListener { dialog, which ->
            when(which){
                0 -> {
                    // TYPE 0 is take photo
                    typeIntent = 0
                }
                1 -> {
                    // TYPE 1 is take video
                    typeIntent = 1
                }
            }
            requestPermission(REQUEST_CODE_CAMERA_ACTIVITY)
        })
        val dialog = alertdialogbuilder.create()
        dialog.show()
    }

    /*
    * SELECTING TYPE CAMERA ACTION
     */

    fun opencamera(){
        when(typeIntent){
            0 ->{
                // TAKE PHOTO
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                photoFile = getPhotoFileUri()
                if(photoFile != null) {
                    var fileprovider: Uri = FileProvider.getUriForFile(this, "jp.co.terraresta.androidlesson.fileprovider", photoFile)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileprovider)
                    startActivityForResult(intent, REQUEST_CODE_CAMERA_ACTIVITY)
                }
            }
            1 -> {
                // TAKE VIDEO
                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                startActivityForResult(intent, REQUEST_CODE_CAMERA_ACTIVITY)
            }
        }
    }
    fun getPhotoFileUri(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        // Return the file target for the photo based on filename
        return image
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "*/*"
        val strings = arrayOf("image/*", "video/*")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, strings)
        startActivityForResult(intent, REQUEST_CODE_GALLERY_ACTIVITY)
    }

//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    fun getPath(uri: Uri): String? {
//        // Will return "image:x*"
//        val wholeID = DocumentsContract.getDocumentId(uri)
//// Split at colon, use second item in the array
//        val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
//        val column = arrayOf(MediaStore.Video.Media.DATA)
//// where id is equal to
//        val sel = MediaStore.Video.Media._ID + "=?"
//        val cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                column, sel, arrayOf(id), null)
//
//        var filePath = ""
//        val columnIndex = cursor!!.getColumnIndex(column[0])
//
//        if (cursor.moveToFirst()) {
//            filePath = cursor.getString(columnIndex)
//        }
//
//        cursor.close()
//        return filePath
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK){
            loader()
            if(requestCode == REQUEST_CODE_CAMERA_ACTIVITY){
                var mediauri: Uri? = null
                var type: Int? = null
                when(typeIntent){
                    0 -> {
                        // TAKE PHOTO
                        mediauri = Uri.parse(photoFile?.absolutePath!!)
                        type = 0
                    }
                    1 -> {
                        // TAKE VIDEO
                        mediauri = data?.data
                        type = 1
                    }
                }
                talkPresenter?.upCamMedia(mediauri!!, type!!)
            } else if(requestCode == REQUEST_CODE_GALLERY_ACTIVITY){
                var type: Int? = null
                val uri = data?.data
                val cr: ContentResolver = this.contentResolver
                val mimetype = cr.getType(uri).slice(0..4)
                if(mimetype == "video"){
                    type = 1
                } else {
                    type = 0
                }
                talkPresenter?.upGaleMedia(uri!!, type)
//                val tempuri = getPath(uri!!)
//                println("uri: $uri")
            }
        } else {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_CODE_CAMERA_ACTIVITY -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    opencamera()
                } else {
                    return
                }
                return
            }

            REQUEST_CODE_GALLERY_ACTIVITY -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openGallery()
                } else {
                    return
                }
            }
        }
    }

    private fun requestPermission(codePermission: Int) {
        when(codePermission){
            REQUEST_CODE_CAMERA_ACTIVITY -> {
                if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA) , REQUEST_CODE_CAMERA_ACTIVITY)
                    return
                }else if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE) , REQUEST_CODE_CAMERA_ACTIVITY)
                    return
                }else{
                    opencamera()
                }
            }

            REQUEST_CODE_GALLERY_ACTIVITY -> {
                if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE) , REQUEST_CODE_GALLERY_ACTIVITY)
                    return
                } else {
                    openGallery()
                }
            }
        }
    }
}
