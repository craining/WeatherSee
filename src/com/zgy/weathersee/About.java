package com.zgy.weathersee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		ImageView imgback = (ImageView) findViewById(R.id.img_about_back);
		imgback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				exit();
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
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

}
