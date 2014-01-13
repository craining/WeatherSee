package com.zgy.weathersee.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.zgy.weathersee.bean.Result;
import com.zgy.weathersee.observer.MainObserver;
import com.zgy.weathersee.util.HttpUtil;
import com.zgy.weathersee.util.PraseUtil;

public class MainController {

	private static MainController instance;

	private MainController() {

	}

	public static MainController getInstance() {
		if (instance == null) {
			instance = new MainController();

		}
		return instance;
	}

	public void getProvience(final MainObserver observer) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					String str = HttpUtil.request("getRegionProvince", params);
					if (str != null && str.length() > 0) {
						ArrayList<Result> prov = PraseUtil.Parse(str);
						if (prov != null && prov.size() > 0) {
							observer.getProvienceFinished(true, prov);
							return;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				observer.getProvienceFinished(false, null);
			}
		}).start();
	}

	public void getCity(final MainObserver observer, final String provName) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("theRegionCode", provName));
					String str = HttpUtil.request("getSupportCityString", params);

					if (str != null && str.length() > 0) {
						ArrayList<Result> prov = PraseUtil.Parse(str);
						if (prov != null && prov.size() > 0) {
							observer.getCityFinished(true, prov);
							return;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				observer.getCityFinished(false, null);
			}
		}).start();
	}

	public void getWeather(final MainObserver observer, final String cityName) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("theCityCode", cityName));
					String str = HttpUtil.request("getWeather", params);

					if (str != null && str.length() > 0) {
						ArrayList<Result> prov = PraseUtil.Parse(str);
						if (prov != null && prov.size() > 0) {
							observer.getWeatherFinished(true, prov);
							return;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				observer.getWeatherFinished(false, null);

			}
		}).start();
	}
}
