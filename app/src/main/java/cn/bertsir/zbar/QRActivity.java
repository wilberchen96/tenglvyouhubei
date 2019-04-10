package cn.bertsir.zbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.hbtl.yhb.R;
import com.hbtl.yhb.activities.BaseActivity;

import cn.bertsir.zbar.Qr.Symbol;
import cn.bertsir.zbar.view.ScanView;

public class QRActivity extends BaseActivity implements View.OnClickListener {

    private CameraSurfaceView cp;
    private SoundPool soundPool;
    private ScanView sv;
    private QrConfig options;

    private ImageView online_light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        options = (QrConfig) getIntent().getExtras().get(QrConfig.EXTRA_THIS_CONFIG);
        Symbol.scanType = options.getScan_type();
        Symbol.scanFormat = options.getCustombarcodeformat();
        Symbol.is_only_scan_center = options.isOnly_center();
        initView();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_qr;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cp != null) {
            cp.setScanCallback(resultCallback);
            cp.start();
        }
        sv.onResume();
    }

    private void initView() {
        cp = findViewById(R.id.cp);
        //bi~
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, options.getDing_path(), 1);

        sv = (ScanView) findViewById(R.id.sv);
        sv.setType(options.getScan_view_type());
        sv.startScan();


        sv.setCornerColor(options.getCORNER_COLOR());
        sv.setLineSpeed(options.getLine_speed());
        sv.setLineColor(options.getLINE_COLOR());

        registCommon();
    }




    //给手电筒手动输入的textview 设置监听
    private void registCommon() {
        online_light = findViewById(R.id.online_light);

        online_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }



    private ScanCallback resultCallback = new ScanCallback() {
        @Override
        public void onScanResult(String result) {
            if (options.isPlay_sound()) {
                soundPool.play(1, 1, 1, 0, 0, 1);
            }
            if (cp != null) {
                cp.setFlash(false);
            }
            if (TextUtils.isEmpty(result)) {
                showToast("无效二维码");
                return;
            }
            QrManager.getInstance().getResultCallback().onScanSuccess(result, 2);
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cp != null) {
            cp.setFlash(false);
            cp.stop();
        }
        soundPool.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cp != null) {
            cp.stop();
        }
        sv.onPause();
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final Uri uri = data.getData();
            final ContentResolver cr = this.getContentResolver();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap Qrbitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        final String qrcontent = QRUtils.getInstance().decodeQRcode(Qrbitmap);
                        Qrbitmap.recycle();
                        Qrbitmap = null;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!TextUtils.isEmpty(qrcontent)) {
                                    QrManager.getInstance().getResultCallback().onScanSuccess(qrcontent, 2);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "识别失败！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        Log.e("Exception", e.getMessage(), e);
                    }
                }
            }).start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
