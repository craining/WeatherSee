package com.zgy.weathersee.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgy.weathersee.Debug;
import com.zgy.weathersee.R;
import com.zgy.weathersee.util.ResourceUtil;

/**
 * 字体的操作按钮，包括：购买、下载、使用、暂停、继续下载、等待中，"使用中"状态显示； TODO 后期加”删除“，
 * 
 * @Author zhuanggy
 * @Date:2014-1-7
 * @Copyright (c) 2014, 方正电子 All Rights Reserved.
 * @version
 * @since
 */
public class MoreDaysWeatherView extends LinearLayout   {

	private static final String TAG = "FontOpearView";
	// 上下文
	public Context mContext;
	// button
	private ImageView mImgOne;
	private ImageView mImgTwo;
	
	private TextView mTextWeather;

	public MoreDaysWeatherView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MoreDaysWeatherView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		ViewGroup main = (ViewGroup) inflater.inflate(R.layout.cv_weather_moredays, null);
		mImgOne = (ImageView) main.findViewById(R.id.img_weather_moredays_one);
		mImgTwo = (ImageView) main.findViewById(R.id.img_weather_moredays_two);
		mTextWeather = (TextView) main.findViewById(R.id.text_weather_moredays);

//		// 不知为何，按钮宽度无法填满，只能代码设置lp
//		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		((LinearLayout) main.findViewById(R.id.layout_fontopera)).setLayoutParams(lp);
//
//		mStatusBut.setOnClickListener(this);
		this.addView(main);
		// Debug.e(TAG, "height=" + this.getHeight() + "  w=" +
		// this.getWidth());
	}

	
	public void showMoreDaysWeather(String weather, String imgNameOne, String imgNameTwo) {
		Debug.v("", "weather=" + weather + "   imgNameOne=" + imgNameOne + "   imgNameTwo=" + imgNameTwo);
		mTextWeather.setText(weather);
		mImgOne.setImageResource(ResourceUtil.getAWeatherImageResId(mContext, imgNameOne));
		if(imgNameOne.equals(imgNameTwo)) {
			mImgTwo.setVisibility(View.GONE);
		} else {
			mImgTwo.setImageResource(ResourceUtil.getAWeatherImageResId(mContext, imgNameTwo));
		}
	}
	 
}
