package jp.co.terraresta.androidlesson.view.fragment.talk;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.co.terraresta.androidlesson.R;

/**
 * Created by ooyama on 2017/05/29.
 */

public class TalkListFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talk_list, container, false);

        return view;
    }
}
