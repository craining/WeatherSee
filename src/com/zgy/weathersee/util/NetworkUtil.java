package com.zgy.weathersee.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * 网络工具类
 * 
 * @Date:2014-1-7
 * @version
 * @since
 */
public class NetworkUtil {

	/**
	 * Wifi 连接是否可用，但包括假网的情况（即使连接着路由，但路由没连接外网）
	 * @param 
	 * @date 2014-1-7
	 */
	public static boolean isWifiEnabled(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (wifi == State.CONNECTING || wifi == State.CONNECTED) {
			return true;
		}
		return false;
	}

	/**
	 * 移动网络是否可用
	 * @param 
	 * @date 2014-1-7
	 */
	public static boolean isMobileEnabled(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (wifi != State.CONNECTED) {
			State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
			if (mobile == State.CONNECTED) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 网络是否可以，包含wifi和移动网络
	 * @param 
	 * @date 2014-1-7
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connect == null) {
			return false;
		} else {
			NetworkInfo[] info = connect.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean ifNoConnectionExit(final Activity activity) {
		if(!isNetworkAvailable(activity)) {
			new AlertDialog.Builder(activity).setTitle("提示").setMessage("当前无网络，请联网后重试").setPositiveButton("好的", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					activity.finish();
				}
			}).create().show();
			
			return true;
		}
		return false;
	}

}
