package jp.co.terraresta.androidlesson.view.fragment.my_page

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import jp.co.terraresta.androidlesson.R

/**
 * Created by ooyama on 2017/05/29.
 */

class MyPageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle): View? {
        val view = inflater.inflate(R.layout.fragment_my_page, container, false)

        return view
    }
}