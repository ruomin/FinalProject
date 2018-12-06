package com.example.ruoruo.finalproject.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class GsonUtil {

    /**
     * change Map to Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }


    /**
     * change the object to json string
     *
     * @param object
     * @return
     */
    public static String objectToString(Object object) {
        String gsonString = null;
        Gson gson = new Gson();
        gsonString = gson.toJson(object);
        return gsonString;
    }

    /**
     * change to bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        Gson gson = new Gson();
        t = gson.fromJson(gsonString, cls);
        return t;
    }

    /**
     * get the object
     *
     * @param clazz
     * @param json
     * @return
     */
    public static Object getInstanceByJson(Class<?> clazz, String json) {
        Object obj = null;
        Gson gson = new Gson();
        obj = gson.fromJson(json, clazz);
        return obj;
    }

    public static Map<String, String> stringToMap(String data) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, String> map = g.fromJson(data, new TypeToken<Map<String, String>>() {
        }.getType());
        return map;
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> jsonArrToList(String json, Class<T[]> clazz) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(json, clazz);
        return Arrays.asList(array);
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }


    /**
     * json data type to List
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        List<JsonObject> jsonObjects = new Gson().fromJson(json, type);
        List<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }


}
