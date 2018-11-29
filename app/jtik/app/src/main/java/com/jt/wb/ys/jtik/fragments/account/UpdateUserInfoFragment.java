package com.jt.wb.ys.jtik.fragments.account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.jt.wb.ys.common.app.Application;
import com.jt.wb.ys.common.widget.PortraitView;
import com.jt.wb.ys.factory.Factory;
import com.jt.wb.ys.factory.net.UploadHelper;
import com.jt.wb.ys.jtik.R;
import com.jt.wb.ys.jtik.fragments.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * 更新用户信息 Fragment
 */
public class UpdateUserInfoFragment extends com.jt.wb.ys.common.app.Fragment {

    @BindView(R.id.im_portrait)
    PortraitView mPortraitView;

    public UpdateUserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_user_info;
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick(){
        new GalleryFragment().setmListener(new GalleryFragment.OnSelectedListener() {
            @Override
            public void onSelectedImage(String path) {
                UCrop.Options options = new UCrop.Options();
                // 设置图片处理的格式 JPEG
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                // 设置压缩后的图片精度
                options.setCompressionQuality(96);

                File cachePath = Application.getPortraitTmpFile();

                UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(cachePath))
                        .withAspectRatio(1,1)
                        .withMaxResultSize(520,520)
                        .withOptions(options)
                        .start(getActivity());
            }
        }).show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

    public void loadPortrait(Uri uri){
        Glide.with(this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mPortraitView);

        // 拿到本地文件的地址
        String localPath = uri.getPath();
        Log.e("TAG", "localPath: " + localPath);

        Factory.runOnAsync(() -> {
            String url = UploadHelper.uploadPortrait(localPath);
            Log.e("TAG", "url: " + url);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null){
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            if (cropError != null){
                cropError.printStackTrace();
            }
        }
    }

}
