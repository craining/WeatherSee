package com.zgy.weathersee;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgy.weathersee.bean.Result;
import com.zgy.weathersee.controller.MainController;
import com.zgy.weathersee.observer.MainObserver;
import com.zgy.weathersee.util.NetworkUtil;
import com.zgy.weathersee.view.CustomProgressDialog;

public class WeatherShow extends Activity {

	private TextView mText;
	private String cityName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);

		mText = (TextView) findViewById(R.id.text_show);

		ImageView imgback = (ImageView) findViewById(R.id.img_weather_back);
		imgback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				exit();
			}
		});

		Bundle b = getIntent().getExtras();
		  cityName = b.getString("cityName");

	}

	@Override
	protected void onResume() {
		super.onResume();
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(TextUtils.isEmpty(mText.getText())) {
					if (NetworkUtil.ifNoConnectionExit(WeatherShow.this)) {
						return;
					}
					CustomProgressDialog.getInstance(WeatherShow.this).showProgressDialog("正在获取天气...", true, null, null, null);
					MainController.getInstance().getWeather(mObserver, cityName);
				}
			}
		}, 100);
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	
	@Override
	public void onBackPressed() {
		exit();
	}

	private void exit() {
		finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
	}
	private MainObserver mObserver = new MainObserver() {

		@Override
		public void getWeatherFinished(final boolean result, final ArrayList<Result> weather) {
			super.getWeatherFinished(result, weather);

			for (int i = 0; i < weather.size(); i++) {
				Log.v(i + "", weather.get(i).getName());
			}

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					CustomProgressDialog.getInstance(WeatherShow.this).dismiss();
					if (result) {
						StringBuffer sb = new StringBuffer();
						try {
							sb.append(weather.get(3).getName()).append("\r\n").append(weather.get(0).getName()).append(weather.get(1).getName()).append("\r\n\r\n").append(weather.get(4).getName())
									.append("\r\n").append(weather.get(5).getName()).append("\r\n\r\n")
									//
									.append(weather.get(6).getName()).append("\r\n\r\n")
									//
									.append(weather.get(7).getName()).append("\r\n").append(weather.get(8).getName()).append("\r\n").append(weather.get(9).getName()).append("\r\n\r\n")
									//

									.append(weather.get(12).getName()).append("\r\n").append(weather.get(13).getName()).append("\r\n").append(weather.get(14).getName()).append("\r\n\r\n")
									//
									.append(weather.get(17).getName()).append("\r\n").append(weather.get(18).getName()).append("\r\n").append(weather.get(19).getName()).append("\r\n\r\n")

									//
									.append(weather.get(22).getName()).append("\r\n").append(weather.get(23).getName()).append("\r\n").append(weather.get(24).getName()).append("\r\n\r\n")
									//
									.append(weather.get(27).getName()).append("\r\n").append(weather.get(28).getName()).append("\r\n").append(weather.get(29).getName()).append("\r\n\r\n");
						} catch (Exception e) {
							e.printStackTrace();
						}

						mText.setText(sb.toString());
					} else {
						new AlertDialog.Builder(WeatherShow.this).setTitle("提示").setMessage("获取天气失败，请稍后再试").setPositiveButton("好的", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();
								overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
							}
						}).create().show();
					}

				}
			});
		}

	};
}
