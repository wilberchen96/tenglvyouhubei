package com.hbtl.yhb.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.hbtl.yhb.R;
import com.hbtl.yhb.http.ProgressObserver;
import com.hbtl.yhb.http.RxHttp;
import com.hbtl.yhb.http.RxSchedulers;
import com.hbtl.yhb.modles.AccountModel;
import com.hbtl.yhb.modles.UserModel;
import com.hbtl.yhb.utils.Json;
import com.hbtl.yhb.utils.SpManager;
import com.hbtl.yhb.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.ed_name)
    public EditText ed_name;
    @BindView(R.id.ed_password)
    public EditText ed_password;
    @BindView(R.id.ig_cleanname)
    public ImageView ig_cleanname;
    @BindView(R.id.ig_password)
    public ImageView ig_password;
    @BindView(R.id.tv_login)
    public TextView tv_login;
    @BindView(R.id.root_container)
    public LinearLayout root_container;
    @BindView(R.id.content_container)
    public LinearLayout content_container;


    private boolean isPasswordCanSee = false;
    private boolean hasPermission = false;
    private AccountModel accountModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        initData();
        initView();
        initCommon();
        requestpermission();
    }


    private void initStatusBar() {
       ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init();
    }

    @Override
    protected int getContentId() {
        return R.layout.login;
    }


    private void initData() {
        String ac = (String) SpManager.getData(SpManager.KEY_ACCOUNT, "");
        if (!TextUtils.isEmpty(ac)) {
            accountModel = Json.parseObject(ac, AccountModel.class);
        }

    }

    private void initView() {
        hideTitleBar();
        root_container = findViewById(R.id.root_container);
        content_container = findViewById(R.id.content_container);
        ed_name = findViewById(R.id.ed_name);
        ed_password = findViewById(R.id.ed_password);
        ig_cleanname = findViewById(R.id.ig_cleanname);
        ig_password = findViewById(R.id.ig_password);
        tv_login = findViewById(R.id.tv_login);


        if (accountModel != null) {
            if (!TextUtils.isEmpty(accountModel.getName())) {
                ed_name.setText(accountModel.getName());
            }
            if (!TextUtils.isEmpty(accountModel.getPassword())) {
                ed_password.setText(accountModel.getPassword());
            }
        }

        keepLoginBtnNotOver(root_container, content_container);

        //触摸外部，键盘消失
        root_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Utils.closeKeyboard(LoginActivity.this);
                return false;
            }
        });
    }

    private void initCommon() {
        //设置点击事件
        ig_cleanname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_name != null) {
                    ed_name.setText("");
                }
            }
        });

        ig_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordCanSee) {
                    ig_password.setImageResource(R.drawable.icon_3);
                    ed_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //   ed_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    ig_password.setImageResource(R.drawable.icon_2);
                    //   ed_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ed_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                isPasswordCanSee = !isPasswordCanSee;
                String pwd = ed_password.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    ed_password.setSelection(pwd.length());
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermission) {
                    Toast.makeText(getApplicationContext(), "请同意用户权限", Toast.LENGTH_SHORT).show();
                    requestpermission();
                    return;
                }
                if (TextUtils.isEmpty(ed_name.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ed_password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                RxHttp.getInstance().getApi().login(ed_name.getText().toString(), ed_password.getText().toString())
                        .compose(RxSchedulers.observableIO2Main())
                        .subscribe(new ProgressObserver<UserModel>(getSelfContext()) {
                            @Override
                            public void onSuccess(UserModel result) {
                                if (result != null) {
                                    //保存账号
                                    AccountModel accountModel = new AccountModel();
                                    accountModel.setName(ed_name.getText().toString());
                                    accountModel.setPassword(ed_password.getText().toString());
                                    SpManager.saveData(SpManager.KEY_ACCOUNT, Json.toJSONString(accountModel));

                                    Intent intent = new Intent(getSelfContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Throwable e, String errorMsg) {
                                showToast(errorMsg);
                            }
                        });


            }
        });

        //用户名监听
        ed_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    ig_cleanname.setVisibility(View.VISIBLE);
                } else {
                    ig_cleanname.setVisibility(View.GONE);
                }
            }
        });

    }

    private void requestpermission() {
        RxPermissions rxPermissions = new RxPermissions(LoginActivity.this);
        String[] arrs = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};

        rxPermissions.request(arrs).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                hasPermission = aBoolean;
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    /**
     * 保持登录按钮始终不会被覆盖
     *
     * @param root
     * @param subView
     */
    private void keepLoginBtnNotOver(final View root, final View subView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                // 若不可视区域高度大于200，则键盘显示,其实相当于键盘的高度
                if (rootInvisibleHeight > 200) {
                    // 显示键盘时
                    int srollHeight = rootInvisibleHeight - (root.getHeight() - subView.getHeight()) - Utils.getNavigationBarHeight(root.getContext());
                    if (srollHeight > 0) {
                        root.scrollTo(0, srollHeight);
                    }
                } else {
                    // 隐藏键盘时
                    root.scrollTo(0, 0);
                }
            }
        });
    }

    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
