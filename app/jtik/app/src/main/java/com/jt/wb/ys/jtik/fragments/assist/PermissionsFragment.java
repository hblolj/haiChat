package com.jt.wb.ys.jtik.fragments.assist;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jt.wb.ys.common.app.Application;
import com.jt.wb.ys.jtik.R;
import com.jt.wb.ys.jtik.fragments.media.GalleryFragment;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 权限申请弹出框
 */
public class PermissionsFragment extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks{


    public PermissionsFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new GalleryFragment.TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_permissions, container, false);
        root.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击时，申请权限
                requestPerm();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshState(getView());
    }

    /**
     * 刷新根布局中的图片状态
     * @param root
     */
    private void refreshState(View root){

        if (root == null){
            return;
        }

        Context context = getContext();
        root.findViewById(R.id.im_state_permission_network).setVisibility(haveNetwordPerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_read).setVisibility(haveReadPerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_write).setVisibility(haveWritePerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_record_audio).setVisibility(haveRecordAudioPerm(context) ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取是否有网络权限
     * @param context 上下文
     * @return True 则有
     */
    private static boolean haveNetwordPerm(Context context){
        // 装备需要检查的网络权限
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否有外部存储读取权限
     * @param context 上下文
     * @return True 则有
     */
    private static boolean haveReadPerm(Context context){
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否有外部存储写入权限
     * @param context 上下文
     * @return True 则有
     */
    private static boolean haveWritePerm(Context context){
        String[] perms = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 获取是否有录音权限
     * @param context 上下文
     * @return True 则有
     */
    private static boolean haveRecordAudioPerm(Context context){
        String[] perms = new String[]{
                Manifest.permission.RECORD_AUDIO
        };

        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 私有的 show 方法
     * @param fragmentManager
     */
    private static void show(FragmentManager fragmentManager){
        new PermissionsFragment().show(fragmentManager, PermissionsFragment.class.getName());
    }

    /**
     * 检查是否具有所有的权限
     * @param context
     * @param fragmentManager
     * @return 是否有权限
     */
    public static boolean haveAll(Context context, FragmentManager fragmentManager){
        // 检查是否具有所有的权限
        Boolean haveAll = haveNetwordPerm(context)
                && haveReadPerm(context)
                && haveWritePerm(context)
                && haveRecordAudioPerm(context);

        // 如果没有则显示当前申请权限的界面
        if (!haveAll){
            show(fragmentManager);
        }

        return haveAll;
    }

    /**
     * 权限回调标识
     */
    private static final int RC = 0x0100;

    @AfterPermissionGranted(RC)
    private void requestPerm(){
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };

        if (EasyPermissions.hasPermissions(getContext(), perms)){
            Application.showToast(R.string.label_permission_ok);
            // Fragment 中调用 getView 得到根布局，前提是在 onCreateView 之后
            refreshState(getView());
        }else {
            EasyPermissions.requestPermissions(this, getString(R.string.title_assist_permissions),
                    RC, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // 如果有权限没有申请成功的权限存在，则弹出框，用户点击后去到设置界面自己打开权限
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    /**
     * 权限申请的是否回调的方法，在这个方法中把对应的权限申请状态交给 EasyPermissions 框架
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 传递对应的参数，并且告知接收权限的处理者是当前 Fragment
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
