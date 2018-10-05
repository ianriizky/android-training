package jp.co.terraresta.androidlesson.view.fragment.talk

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.terraresta.androidlesson.R

class TalkBottomSheetFragment: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_bottom_sheet_dialog, container, false)

        return view
    }
}