package com.gaoan.forever.utils.gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonUtils {

	public static final Logger logger = LoggerFactory.getLogger(GsonUtils.class);

	public static <T> String toJson(T obj) {
		String str = new Gson().toJson(obj);
		return str;
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		T t = new Gson().fromJson(json, clazz);
		return t;
	}

	public static <T> T fromJson(String json, TypeToken<T> token) {
		T t = new Gson().fromJson(json, token.getType());
		return t;
	}

	public static <O, T> T fromJson(O obj, Class<T> clazz) {
		T t = new Gson().fromJson(new Gson().toJson(obj), clazz);
		return t;
	}

	public static <O, T> T fromJson(O obj, TypeToken<T> token) {
		T t = new Gson().fromJson(new Gson().toJson(obj), token.getType());
		return t;
	}

}
