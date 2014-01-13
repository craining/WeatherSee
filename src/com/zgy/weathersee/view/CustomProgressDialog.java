package com.zgy.weathersee.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgy.weathersee.R;

/**
 * 自定义等待窗
 */
public class CustomProgressDialog {

	private ProgressDialog progressDialog;

	private static CustomProgressDialog instance;
	private TextView mTextView;
	private ViewGroup mMainView;
	private static Context mContext;

	private CustomProgressDialog() {
	}

	/**
	 * 获得弹窗实例
	 * 
	 * @param
	 * @date 2014-1-7
	 */
	public static CustomProgressDialog getInstance(Context con) {
		mContext = con;
		if (instance == null) {
			instance = new CustomProgressDialog();
		}
		return instance;
	}

	/**
	 * 显示等待框
	 * 
	 * @param
	 * @author zhuanggy
	 * @date 2013-12-25
	 */
	public void showProgressDialog(String message, boolean cancelable, Typeface typeface, DialogInterface.OnKeyListener keyListener, DialogInterface.OnDismissListener dismissListener) {
		// 创建进度对话框对象
		// if (progressDialog == null) {
		progressDialog = new ProgressDialog(mContext);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		mMainView = (ViewGroup) inflater.inflate(R.layout.layout_progressdlg, null);
		mTextView = (TextView) mMainView.findViewById(R.id.text_progressdlg_msg);
		progressDialog.getWindow().setGravity(Gravity.CENTER);
		// }
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(cancelable);
		if (keyListener != null) {
			progressDialog.setOnKeyListener(keyListener);
		}

		if (dismissListener != null) {
			progressDialog.setOnDismissListener(dismissListener);
		}

		if (!progressDialog.isShowing()) {
			progressDialog.show();
		}
		if (message == null) {
			mTextView.setVisibility(View.GONE);
		} else {
			mTextView.setVisibility(View.VISIBLE);
			mTextView.setText(message);
			if (typeface != null) {
				mTextView.setTypeface(typeface);
			} else {
				mTextView.setTypeface(Typeface.DEFAULT);
			}
		}
		progressDialog.setContentView(mMainView);
	}

	public void dismiss() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	public boolean isShowing() {
		if (progressDialog == null || !progressDialog.isShowing()) {
			return false;
		}
		return true;
	}
}
