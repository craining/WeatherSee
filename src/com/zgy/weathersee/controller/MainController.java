package com.zgy.weathersee.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.zgy.weathersee.bean.Result;
import com.zgy.weathersee.observer.MainObserver;
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
					final String namespace = "http://WebXml.com.cn/";
					final String url = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
					final String methodName = "getRegionProvince";
					HttpTransportSE transport = new HttpTransportSE(url);
					transport.debug = true;
					SoapObject soapObject = new SoapObject(namespace, methodName);
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.bodyOut = soapObject;
					envelope.dotNet = true;
					envelope.setOutputSoapObject(soapObject);
					try {
						transport.call(namespace + methodName, envelope);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					}
					SoapObject result = null;
					result = (SoapObject) envelope.bodyIn;
					int i = result.getPropertyCount();
					String str = result.getProperty(i - 1).toString();
					ArrayList<Result> prov = PraseUtil.Parse(str);
					observer.getProvienceFinished(true, prov);
				} catch (Exception e) {
					e.printStackTrace();
					observer.getProvienceFinished(false, null);
				}

			}
		}).start();
	}

	public void getCity(final MainObserver observer, final String provName) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					final String namespace = "http://WebXml.com.cn/";
					final String url = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
					final String methodName = "getSupportCityString";
					HttpTransportSE transport = new HttpTransportSE(url);
					transport.debug = true;
					SoapObject soapObject = new SoapObject(namespace, methodName);
					soapObject.addProperty("theRegionCode", provName);
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.bodyOut = soapObject;
					envelope.dotNet = true;
					envelope.setOutputSoapObject(soapObject);
					try {
						transport.call(namespace + methodName, envelope);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					}
					SoapObject result = null;
					result = (SoapObject) envelope.bodyIn;
					int i = result.getPropertyCount();
					String str = result.getProperty(i - 1).toString();
					ArrayList<Result> prov = PraseUtil.Parse(str);
					observer.getCityFinished(true, prov);
				} catch (Exception e) {
					e.printStackTrace();
					observer.getCityFinished(false, null);
				}
			}
		}).start();
	}

	public void getWeather(final MainObserver observer, final String cityName) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					final String namespace = "http://WebXml.com.cn/";
					final String url = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
					final String methodName = "getWeather";
					HttpTransportSE transport = new HttpTransportSE(url);
					transport.debug = true;
					SoapObject soapObject = new SoapObject(namespace, methodName);
					soapObject.addProperty("theCityCode", cityName);
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.bodyOut = soapObject;
					envelope.dotNet = true;
					envelope.setOutputSoapObject(soapObject);
					try {
						transport.call(namespace + methodName, envelope);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					}
					SoapObject result = null;
					result = (SoapObject) envelope.bodyIn;
					int i = result.getPropertyCount();
					String str = result.getProperty(i - 1).toString();
					ArrayList<Result> prov = PraseUtil.Parse(str);
					observer.getWeatherFinished(true, prov);
				} catch (Exception e) {
					e.printStackTrace();
					observer.getWeatherFinished(false, null);
				}
			}
		}).start();
	}
}
