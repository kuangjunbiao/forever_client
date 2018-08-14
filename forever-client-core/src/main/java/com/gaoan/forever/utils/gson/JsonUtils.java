package com.gaoan.forever.utils.gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

    public static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static <T> String toJson(T obj) {
	String str = JSON.toJSONString(obj);
	return str;
    }

    public static JSONObject fromJson(String json) {
	JSONObject obj = JSON.parseObject(json);
	return obj;
    }

    public static <T> T fromJson(String json, Class<? extends Object> clazz) {
	T t = JSON.parseObject(json, clazz);
	return t;
    }

    public static <O, T> T fromJson(O obj, Class<? extends Object> clazz) {
	T t = JSON.parseObject(JSON.toJSONString(obj), clazz);
	return t;
    }

}
