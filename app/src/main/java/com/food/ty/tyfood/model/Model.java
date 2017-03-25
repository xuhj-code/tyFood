package com.food.ty.tyfood.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model {


	public static <T> T create(JsonElement json, Class<T> classOfModel) {
//		LOGD("Json", json.toString());
		if (json == null) Log.d("Model", "null");
		T t = gson().fromJson(json, classOfModel);
//		LOGD("Model", t.toString());
		return t;
	}
	
	/**
	 * 
	 * @param json  JsonArray
	 * @param classOfModel
	 * @return 空数组将会返回空列表而不是null
	 * 
	 */
	public static <T> List<T> createList(JsonElement json, Class<T> classOfModel) {
		List<T> list = new ArrayList<T>();
		JsonArray array = json.getAsJsonArray();
		Iterator<JsonElement> iterator = array.iterator();
		while(iterator.hasNext()){
			list.add(create(iterator.next(), classOfModel));
		}
        return list;

//FIXME 无法获取 List<T> 的运行时类型
        
//		Type listType = new TypeToken<List<T>>() {
//			
//		}.getType();
//		
//		LOGD("Json", json.toString());
//		List<T> list = gson().fromJson(json, T[].class);
//		for (T t : list) {
//			LOGD("List:"+t.getClass().getName(), t.toString());
//		}
//		return list;
	}

	protected static Gson gson() {
		//如果key对应的值都为小写加下划线连接可以采用这个NamingPolicy进行验证
//		return new GsonBuilder().setFieldNamingPolicy(
//				FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		return new GsonBuilder().create();
	}

	public static String toJson(Object model){
		Gson gson = gson();
		return gson.toJson(model);
	}
	
	public static <T> T fromJson(String json, Class<T> classOfModel){
		Gson gson = gson();
		return gson.fromJson(json, classOfModel);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(this.getClass().getName());
		result.append(" Object {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of
		// superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		// print field names paired with their values
		for (Field field : fields) {
			result.append("  ");
			try {
				result.append(field.getName());
				result.append(": ");
				// requires access to private field:
				result.append(field.get(this));
			} catch (IllegalAccessException ex) {
				Log.d("Model", ex.getMessage());
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}
}
