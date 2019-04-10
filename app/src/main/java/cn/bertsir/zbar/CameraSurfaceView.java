package cn.bertsir.zbar;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tiano on 2018/12/11.
 */
public class CameraSurfaceView extends SurfaceView {

    private CameraManager mCameraManager;
    private CameraScanAnalysis mPreviewCallback;
    private Runnable mAutoFocusTask = new Runnable() {
        public void run() {
            mCameraManager.autoFocus(mFocusCallback);
        }
    };
    private Camera.AutoFocusCallback mFocusCallback = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            postDelayed(mAutoFocusTask, 1000);
        }
    };
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (holder.getSurface() == null) {
                return;
            }
            mCameraManager.stopPreview();
            startCameraPreview(holder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    public CameraSurfaceView(Context context) {
        this(context, null);

    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCameraManager = new CameraManager(context);
        mPreviewCallback = new CameraScanAnalysis();
    }

    /**
     * Set Scan results callback.
     *
     * @param callback {@link ScanCallback}.
     */
    public void setScanCallback(ScanCallback callback) {
        mPreviewCallback.setScanCallback(callback);
    }

    public void start() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                mCameraManager.openDriver(); //250
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object o) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("test", "onError: " + e.toString());
                        Toast.makeText(getContext(), "摄像头权限被拒绝！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        mPreviewCallback.onStart();
                        SurfaceHolder holder = getHolder();
                        holder.addCallback(callback);
                        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                        startCameraPreview(getHolder());
                    }
                });
    }

    /**
     * Camera stop preview.
     */
    public void stop() {
        removeCallbacks(mAutoFocusTask);
        mPreviewCallback.onStop();

        mCameraManager.stopPreview();
        mCameraManager.closeDriver();
    }

    private void startCameraPreview(SurfaceHolder holder) {
        try {
            mCameraManager.startPreview(holder, mPreviewCallback);
            mCameraManager.autoFocus(mFocusCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    public void setFlash() {
        mCameraManager.setFlash();
    }

    public void setFlash(boolean open) {
        mCameraManager.setFlash(open);
    }

}
