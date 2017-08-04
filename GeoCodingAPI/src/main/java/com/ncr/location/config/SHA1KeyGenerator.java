package com.ncr.location.config;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import com.ncr.location.model.GeoLocationResponse;

@Component
public class SHA1KeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object obj, Method method1, Object... params) {

		for (Object param : params) {
			
			if (param instanceof GeoLocationResponse) {
				GeoLocationResponse response = (GeoLocationResponse) param;
				String key = getSHA1 (response.getLatitude(), response.getLongitude());
				return key;
			}
		}
		return new java.util.Date().toString();
	}

	private static String getSHA1(String latitude, String longitude) {

		MessageDigest md = null;
		String resp = "";
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] arr = (md.digest((latitude + "," + longitude).getBytes("UTF-8")));
			resp = DatatypeConverter.printHexBinary(arr);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resp;

	}
}
