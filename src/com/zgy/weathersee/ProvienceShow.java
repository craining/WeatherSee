package com.zgy.weathersee;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.zgy.weathersee.bean.Result;
import com.zgy.weathersee.controller.MainController;
import com.zgy.weathersee.observer.MainObserver;
import com.zgy.weathersee.util.NetworkUtil;
import com.zgy.weathersee.view.CustomProgressDialog;

public class ProvienceShow extends Activity implements OnItemClickListener {

	private ArrayList<Result> cityList;
	private ListView lv;
	private ArrayAdapter arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prov);
		lv = (ListView) findViewById(R.id.lv);
		lv.setOnItemClickListener(this);

		ImageView imgback = (ImageView) findViewById(R.id.img_prov_back);
		imgback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				exit();
			}
		});
	}

	@Override
	public void onBackPressed() {
		exit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if (cityList == null || cityList.size() <= 0) {
					if (NetworkUtil.ifNoConnectionExit(ProvienceShow.this)) {
						return;
					}
					CustomProgressDialog.getInstance(ProvienceShow.this).showProgressDialog("正在获取省份...", true, null, null, null);
					MainController.getInstance().getProvience(mObserver);
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
		Intent i = new Intent();
		i.setClass(ProvienceShow.this, CityShow.class);
		i.putExtra("provname", cityList.get(position).getName());
		startActivity(i);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_about:
			Intent i = new Intent();
			i.setClass(ProvienceShow.this, About.class);
			startActivity(i);
			break;

		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void exit() {
		new AlertDialog.Builder(ProvienceShow.this).setTitle("提示").setMessage("您确定要退出吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		}).create().show();
	}

	private MainObserver mObserver = new MainObserver() {

		@Override
		public void getProvienceFinished(final boolean result, ArrayList<Result> provs) {
			super.getProvienceFinished(result, provs);
			String[] p = null;
			if (result) {
				cityList = provs;
				p = new String[provs.size()];
				for (int i = 0; i < provs.size(); i++) {
					p[i] = provs.get(i).getName();
				}
			}
			final String[] prov = p;

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					CustomProgressDialog.getInstance(ProvienceShow.this).dismiss();
					if (result) {
						arrayAdapter = new ArrayAdapter<String>(ProvienceShow.this, R.layout.list_item, prov);
						lv.setAdapter(arrayAdapter);
					} else {
						new AlertDialog.Builder(ProvienceShow.this).setTitle("提示").setMessage("获取省份失败，请稍后再试").setPositiveButton("好的", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						}).create().show();
					}

				}
			});
		}

	};
}
