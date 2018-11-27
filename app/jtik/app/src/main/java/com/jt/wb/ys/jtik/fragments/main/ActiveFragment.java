package com.jt.wb.ys.jtik.fragments.main;


import com.jt.wb.ys.common.app.Fragment;
import com.jt.wb.ys.common.widget.GalleyView;
import com.jt.wb.ys.jtik.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends Fragment {

    @BindView(R.id.galleyView)
    GalleyView mGalley;

    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initData() {
        super.initData();

        mGalley.setUp(getLoaderManager(), new GalleyView.SelectChangeListener() {
            @Override
            public void onSelectedCountChange(int count) {

            }
        });
    }
}
