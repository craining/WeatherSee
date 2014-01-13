package com.zgy.weathersee;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zgy.weathersee.bean.Result;
import com.zgy.weathersee.controller.MainController;
import com.zgy.weathersee.observer.MainObserver;
import com.zgy.weathersee.util.NetworkUtil;
import com.zgy.weathersee.view.CustomProgressDialog;

public class CityShow extends Activity implements OnItemClickListener {

	private ArrayList<Result> cityList;
	private ListView lv;
	private ArrayAdapter arrayAdapter;
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city);
		lv = (ListView) findViewById(R.id.lv);
		lv.setOnItemClickListener(this);
		ImageView imgback = (ImageView) findViewById(R.id.img_city_back);
		imgback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				exit();
			}
		});

		Bundle b = getIntent().getExtras();
		name = b.getString("provname");

		((TextView) findViewById(R.id.text_city)).setText(name);

	}

	@Override
	protected void onResume() {
		super.onResume();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (cityList == null || cityList.size() <= 0) {
					if (NetworkUtil.ifNoConnectionExit(CityShow.this)) {
						return;
					}
					CustomProgressDialog.getInstance(CityShow.this).showProgressDialog("正在获取城市...", true, null, null, null);
					MainController.getInstance().getCity(mObserver, name);
				}

			}
		}, 100);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent i = new Intent(CityShow.this, WeatherShow.class);
		i.putExtra("cityName", cityList.get(position).getName());
		startActivity(i);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
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
		public void getCityFinished(final boolean result, ArrayList<Result> cities) {
			super.getCityFinished(result, cities);
			String[] p = null;
			if (result) {
				cityList = cities;
				p = new String[cities.size()];
				for (int i = 0; i < cities.size(); i++) {
					p[i] = cities.get(i).getName();
				}
			}
			final String[] prov = p;

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					CustomProgressDialog.getInstance(CityShow.this).dismiss();
					if (result) {
						arrayAdapter = new ArrayAdapter<String>(CityShow.this, R.layout.list_item, prov);
						lv.setAdapter(arrayAdapter);
					} else {
						new AlertDialog.Builder(CityShow.this).setTitle("提示").setMessage("获取城市失败，请稍后再试").setPositiveButton("好的", new DialogInterface.OnClickListener() {

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
