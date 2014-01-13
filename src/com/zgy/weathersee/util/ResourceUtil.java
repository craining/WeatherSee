package com.zgy.weathersee.util;

import com.zgy.weathersee.Debug;

import junit.framework.Assert;
import android.content.Context;

public class ResourceUtil {
	private static int getResourceId(Context context, String resName, String resType) {
		Assert.assertNotNull(context);
		Assert.assertNotNull(resName);
		return context.getResources().getIdentifier(resName, resType, context.getPackageName());
	}

	public static int getAWeatherImageResId(Context context, String imgName) {
		Debug.v("getAWeatherImageResId", "a_" + imgName);
		return getResourceId(context, "a_" + imgName, "drawable");
	}
}
