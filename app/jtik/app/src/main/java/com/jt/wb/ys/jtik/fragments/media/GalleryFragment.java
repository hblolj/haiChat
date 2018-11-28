package com.jt.wb.ys.jtik.fragments.media;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.jt.wb.ys.common.tools.UiTool;
import com.jt.wb.ys.common.widget.GalleryView;
import com.jt.wb.ys.jtik.R;

/**
 * 图片选择 Fragment
 */
public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectChangeListener {

    private GalleryView mGalleryView;

    private OnSelectedListener mListener;

    public GalleryFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStatusBottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGalleryView = root.findViewById(R.id.galleryView);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGalleryView.setUp(getLoaderManager(), this);
    }

    @Override
    public void onSelectedCountChange(int count) {
        if (count > 0){
            // 如果选中了一张图片，隐藏自己
            dismiss();
            if (mListener != null){
                // 得到所有选中的图片的路径
                String[] paths = mGalleryView.getSelectedPath();
                // 返回第一张
                mListener.onSelectedImage(paths[0]);
                mListener = null;
            }
        }
    }

    /**
     * 设置事件监听，并返回自己
     * @param mListener
     * @return
     */
    public GalleryFragment setmListener(OnSelectedListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public interface OnSelectedListener{
        void onSelectedImage(String path);
    }

    /**
     * 为了解决顶部状态栏变黑而写的 TransStatusBottomSheetDialog
     */
    public static class TransStatusBottomSheetDialog extends BottomSheetDialog{

        public TransStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusBottomSheetDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            final Window window = getWindow();
            if (window == null){
                return;
            }

            // 获取到屏幕的高度
            int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
            // 得到状态栏的高度
            int statusBarHeight = UiTool.getStatusBarHeight(getOwnerActivity());;

            int dialogHeight = screenHeight - statusBarHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    dialogHeight<=0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        }
    }
}
