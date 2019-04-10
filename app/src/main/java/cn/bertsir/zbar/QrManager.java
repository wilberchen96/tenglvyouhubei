package cn.bertsir.zbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import com.hbtl.yhb.R;

/**
 * Created by Bert on 2017/9/22.
 */

public class QrManager {

    private static QrManager instance;
    private QrConfig options;
    private static int scan_type = QrConfig.TYPE_QRCODE;
    private static int scan_view_type = QrConfig.SCANVIEW_TYPE_QRCODE;
    private static QrConfig qrConfig = new QrConfig.Builder()
            .setDesText("")//扫描框下文字
            .setShowDes(false)//是否显示扫描框下面文字
            .setShowLight(true)//显示手电筒按钮
            .setShowTitle(false)//显示Title
            .setShowAlbum(false)//显示从相册选择按钮
            .setCornerColor(Color.parseColor("#5682FA"))//设置扫描框颜色
            .setLineColor(Color.parseColor("#5682FA"))//设置扫描线颜色
            .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
            .setScanType(scan_type)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
            .setScanViewType(scan_view_type)//设置扫描框类型（二维码还是条形码，默认为二维码）
            .setCustombarcodeformat(QrConfig.BARCODE_EAN13)//此项只有在扫码类型为TYPE_CUSTOM时才有效
            .setPlaySound(true)//是否扫描成功后bi~的声音
            .setDingPath(R.raw.qrcode)//设置提示音(不设置为默认的Ding~)
            .setIsOnlyCenter(false)//是否只识别框中内容(默认为全屏识别)
            .setTitleText("")//设置Tilte文字
            .setTitleBackgroudColor(Color.parseColor("#262020"))//设置状态栏颜色
            .setTitleTextColor(Color.WHITE)//设置Title文字颜色
            .create();

    public OnScanResultCallback resultCallback;

    public synchronized static QrManager getInstance() {
        if(instance == null)
            instance = new QrManager();
        return instance;
    }

    public OnScanResultCallback getResultCallback() {
        return resultCallback;
    }


    public QrManager init(QrConfig options) {
        this.options = options;
        return this;
    }

    public void startScan(Activity activity, OnScanResultCallback resultCall){

        if (options == null) {
            options = new QrConfig.Builder().create();
        }
        Intent intent = new Intent(activity, QRActivity.class);
        intent.putExtra(QrConfig.EXTRA_THIS_CONFIG, qrConfig);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        // 绑定图片接口回调函数事件
        resultCallback = resultCall;
    }



    public interface OnScanResultCallback {
        /**
         * 处理成功
         * 多选
         *
         * @param result
         * @param type 手输订单号=1  扫码=2
         */
        void onScanSuccess(String result,int type);

    }
}
