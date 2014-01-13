package com.zgy.weathersee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageView imgLoading = (ImageView) findViewById(R.id.img_loading);

		Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);

		imgLoading.startAnimation(anim);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				finish();
				startActivity(new Intent(MainActivity.this, ProvienceShow.class));
				overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
			}
		}, 1500);
	}

}
