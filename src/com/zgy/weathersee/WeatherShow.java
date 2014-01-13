package com.zgy.weathersee;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgy.weathersee.bean.Result;
import com.zgy.weathersee.bean.WeatherMoreDays;
import com.zgy.weathersee.controller.MainController;
import com.zgy.weathersee.observer.MainObserver;
import com.zgy.weathersee.util.NetworkUtil;
import com.zgy.weathersee.view.CustomProgressDialog;
import com.zgy.weathersee.view.MoreDaysWeatherView;

public class WeatherShow extends Activity {

	private TextView mTextTitle;
	private TextView mText;
	private String cityName;
	private LinearLayout mLayoutMoredaysWeather;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);

		mText = (TextView) findViewById(R.id.text_show);
		mTextTitle = (TextView) findViewById(R.id.text_weather);

		mLayoutMoredaysWeather = (LinearLayout) findViewById(R.id.layout_weather_show_moredays);

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
				if (TextUtils.isEmpty(mText.getText())) {
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
						if (weather.size() == 1) {
							mText.setText(weather.get(0).getName());
							findViewById(R.id.text_label_recentlydays).setVisibility(View.GONE);
							return;
						}
						try {

							String title = weather.get(0).getName() + " - " + weather.get(1).getName() + " 天气";
							mTextTitle.setText(title);

							sb.append(weather.get(3).getName()).append("\r\n").append(weather.get(4).getName()).append("\r\n").append(weather.get(5).getName()).append("\r\n\r\n")
							//
							.append(weather.get(6).getName()).append("\r\n\r\n");
							
							mText.setText(sb.toString());
							//
							// .append(weather.get(7).getName()).append("\r\n").append(weather.get(8).getName()).append("\r\n").append(weather.get(9).getName()).append("\r\n\r\n")
							//

							// .append(weather.get(12).getName()).append("\r\n").append(weather.get(13).getName()).append("\r\n").append(weather.get(14).getName()).append("\r\n\r\n")
							//
							// .append(weather.get(17).getName()).append("\r\n").append(weather.get(18).getName()).append("\r\n").append(weather.get(19).getName()).append("\r\n\r\n")

							//
							// .append(weather.get(22).getName()).append("\r\n").append(weather.get(23).getName()).append("\r\n").append(weather.get(24).getName()).append("\r\n\r\n")
							//
							// .append(weather.get(27).getName()).append("\r\n").append(weather.get(28).getName()).append("\r\n").append(weather.get(29).getName()).append("\r\n\r\n");

							
							List<WeatherMoreDays> moredays = new ArrayList<WeatherMoreDays>();
							
							WeatherMoreDays day0 = new WeatherMoreDays();
							WeatherMoreDays day1 = new WeatherMoreDays();
							WeatherMoreDays day2 = new WeatherMoreDays();
							WeatherMoreDays day3 = new WeatherMoreDays();
							WeatherMoreDays day4 = new WeatherMoreDays();
							day0.weather = weather.get(7).getName() + "\r\n" + weather.get(8).getName() + "\r\n" + weather.get(9).getName();
							day0.imgOne = weather.get(10).getName().replace(".gif", "");
							day0.imgTwo = weather.get(11).getName().replace(".gif", "");
							
							day1.weather = weather.get(12).getName() + "\r\n" + weather.get(13).getName() + "\r\n" + weather.get(14).getName();
							day1.imgOne = weather.get(15).getName().replace(".gif", "");
							day1.imgTwo = weather.get(16).getName().replace(".gif", "");

							day2.weather = weather.get(17).getName() + "\r\n" + weather.get(18).getName() + "\r\n" + weather.get(19).getName();
							day2.imgOne = weather.get(20).getName().replace(".gif", "");
							day2.imgTwo = weather.get(21).getName().replace(".gif", "");

							day3.weather = weather.get(22).getName() + "\r\n" + weather.get(23).getName() + "\r\n" + weather.get(24).getName();
							day3.imgOne = weather.get(25).getName().replace(".gif", "");
							day3.imgTwo = weather.get(26).getName().replace(".gif", "");

							day4.weather = weather.get(27).getName() + "\r\n" + weather.get(28).getName() + "\r\n" + weather.get(29).getName();
							day4.imgOne = weather.get(30).getName().replace(".gif", "");
							day4.imgTwo = weather.get(31).getName().replace(".gif", "");
							
							moredays.add(day0);
							moredays.add(day1);
							moredays.add(day2);
							moredays.add(day3);
							moredays.add(day4);
							
//							WeatherMoreDays[] moreDays;
//							moreDays = new WeatherMoreDays[5];
//							
//							moreDays[0].weather = weather.get(7).getName() + "\r\n" + weather.get(8).getName() + "\r\n" + weather.get(9).getName();
//							moreDays[0].imgOne = weather.get(10).getName().replace(".gif", "");
//							moreDays[0].imgTwo = weather.get(11).getName().replace(".gif", "");
//
//							moreDays[1].weather = weather.get(12).getName() + "\r\n" + weather.get(13).getName() + "\r\n" + weather.get(14).getName();
//							moreDays[1].imgOne = weather.get(15).getName().replace(".gif", "");
//							moreDays[1].imgTwo = weather.get(16).getName().replace(".gif", "");
//
//							moreDays[2].weather = weather.get(17).getName() + "\r\n" + weather.get(18).getName() + "\r\n" + weather.get(19).getName();
//							moreDays[2].imgOne = weather.get(20).getName().replace(".gif", "");
//							moreDays[2].imgTwo = weather.get(21).getName().replace(".gif", "");
//
//							moreDays[3].weather = weather.get(22).getName() + "\r\n" + weather.get(23).getName() + "\r\n" + weather.get(24).getName();
//							moreDays[3].imgOne = weather.get(25).getName().replace(".gif", "");
//							moreDays[3].imgTwo = weather.get(26).getName().replace(".gif", "");
//
//							moreDays[4].weather = weather.get(27).getName() + "\r\n" + weather.get(28).getName() + "\r\n" + weather.get(29).getName();
//							moreDays[4].imgOne = weather.get(30).getName().replace(".gif", "");
//							moreDays[4].imgTwo = weather.get(31).getName().replace(".gif", "");

							mLayoutMoredaysWeather.removeAllViews();
							for (WeatherMoreDays m : moredays) {
								MoreDaysWeatherView view = new MoreDaysWeatherView(WeatherShow.this);
								view.showMoreDaysWeather(m.weather, m.imgOne, m.imgTwo);
								mLayoutMoredaysWeather.addView(view);
							}
							findViewById(R.id.text_label_recentlydays).setVisibility(View.VISIBLE);
						} catch (Exception e) {
							e.printStackTrace();
						}
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
