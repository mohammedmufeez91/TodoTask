package com.mufeez.taskzero.base;

import android.content.Context;
import androidx.fragment.app.DialogFragment;

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

public class BaseDialog extends DialogFragment implements BaseDialogView{

    private BaseActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity){
            mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }


    @Override
    public void showMessage(String message) {
        mActivity.showMessage(message);
    }

    @Override
    public void showMessage(int resId) {
        mActivity.showMessage(resId);
    }

}
