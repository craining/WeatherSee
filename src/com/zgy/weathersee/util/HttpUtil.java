package com.zgy.weathersee.util;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class HttpUtil {

	
	public static String request(String methodName, List<NameValuePair> params) {
		final String namespace = "http://WebXml.com.cn/";
		final String url = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
		HttpTransportSE transport = new HttpTransportSE(url);
		transport.debug = true;
		SoapObject soapObject = new SoapObject(namespace, methodName);
		
		if(params != null) {
			for(NameValuePair p : params) {
				soapObject.addProperty(p.getName(), p.getValue());
			}
		}
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
		
		return str;
	}
}
