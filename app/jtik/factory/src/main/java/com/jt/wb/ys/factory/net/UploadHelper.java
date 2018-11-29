package com.jt.wb.ys.factory.net;

import android.text.format.DateFormat;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jt.wb.ys.factory.Factory;
import com.jt.wb.ys.utils.HashUtil;

import java.io.File;
import java.util.Date;

/**
 * 上传工具类，上传任意文件到阿里 OSS 存储
 */
public class UploadHelper {

    private static final String TAG = UploadHelper.class.getSimpleName();

    private static final String END_POINT = "http://oss-cn-hangzhou.aliyuncs.com";

    /**
     * 上传的仓库名
     */
    private static final String BUCKET_NAME = "hai-liao";

    private static OSS getOSSClient(){

        //推荐使用OSSAuthCredentialsProvider。token过期可以及时更新
        OSSCredentialProvider credentialProvider = new OSSCustomSignerCredentialProvider(){

            @Override
            public String signContent(String content) {

                // 您需要在这里依照OSS规定的签名算法，实现加签一串字符内容，并把得到的签名传拼接上AccessKeyId后返回
                // 一般实现是，将字符内容post到您的业务服务器，然后返回签名
                // 如果因为某种原因加签失败，描述error信息后，返回nil

                // 以下是用本地算法进行的演示
                return OSSUtils.sign("LTAI5US3Er7yEFBK", "LyM7TFM0AgT9q0iAgHoryLpQomYMXj", content);
            }
        };

        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        return new OSSClient(Factory.app(), END_POINT, credentialProvider, conf);
    }

    /**
     * 上传文件方法，成功返回一个路径
     * @param objectKey 上传上去后，在服务器上独立的 Key
     * @param path 需要上传的文件的路径
     * @return 存储的地址
     */
    private static String upload(final String objectKey, String path){

        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(BUCKET_NAME, objectKey, path);

        // 文件元信息的设置是可选的
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setContentType("application/octet-stream"); // 设置content-type
        // metadata.setContentMD5(BinaryUtil.calculateBase64Md5(uploadFilePath)); // 校验MD5
        // put.setMetadata(metadata);

        final OSS oss = getOSSClient();

        try {

            PutObjectResult putResult = oss.putObject(put);

            Log.d("PutObject", "UploadSuccess");

            // 得到外网可访问地址
            String url = oss.presignPublicObjectURL(BUCKET_NAME, objectKey);

            Log.d(TAG, String.format("PublicObjectURL:%s", url));
            Log.d("ETag", putResult.getETag());
            Log.d("RequestId", putResult.getRequestId());

            return url;
        } catch (ClientException e) {
            // 本地异常如网络异常等
            e.printStackTrace();
        } catch (ServiceException e) {
            // 服务异常
            Log.e("RequestId", e.getRequestId());
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
        }

        return null;
    }

    /**
     * 上传普通图片
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadImage(String path){
        String key = getImageObjKey(path);
        return upload(key, path);
    }

    /**
     * 上传头像
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadPortrait(String path){
        String key = getPortraitObjKey(path);
        return upload(key, path);
    }

    /**
     * 上传音频
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadAudio(String path){
        String key = getAudioObjKey(path);
        return upload(key, path);
    }

    /**
     * 分月存储，避免一个文件夹存储太多
     * @return yyyyMM
     */
    private static String getDataString(){
        return DateFormat.format("yyyyMM", new Date()).toString();
    }

    // image/201811/sdsdsdsdsds.jpg
    private static String getImageObjKey(String path){
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dataString = getDataString();
        return String.format("image/%s/%s.jpg", dataString, fileMd5);
    }

    // portrait/201811/sdsdsdsdsds.jpg
    private static String getPortraitObjKey(String path){
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dataString = getDataString();
        return String.format("portrait/%s/%s.jpg", dataString, fileMd5);
    }

    // audio/201811/sdsdsdsdsds.jpg
    private static String getAudioObjKey(String path){
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dataString = getDataString();
        return String.format("audio/%s/%s.mp3", dataString, fileMd5);
    }
}
