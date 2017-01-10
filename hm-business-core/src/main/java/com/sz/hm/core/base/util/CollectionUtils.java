package com.sz.hm.core.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CollectionUtils {

	/**
	 * ����list
	 * @param objs
	 * @return
	 */
	public static List<Object> newList(Object... objs){
		List<Object> args = new ArrayList<Object>(objs.length);
		
		for(Object obj:objs){
			args.add(obj);
		}
		
		return args;
	}
	
	/**
	 * ����list
	 * @param objs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> newList(Class<T> t,T... objs){
		List<T> args = new ArrayList<T>(objs.length);
		
		for(T obj:objs){
			args.add(obj);
		}
		
		return args;
	}
	
	/**
	 * ����Map
	 * @param objs ���鴮
	 * @return
	 */
	public static Map<String,String> newStringMap(String... objs){
		
		Map<String,String> map = new HashMap<String,String>(objs.length);
		
		String key=null;
		String value = null;
		
		for(int i=0;i<objs.length;i++){
			
			if(i%2==0){
				key=objs[i];
			}else{
				value=objs[i];
				map.put(key, value);
			}
		}
		
		return map;
	}
	
	/**
	 * ����Map
	 * @param objs ���鴮
	 * @return
	 */
	public static Map<String,Object> newObjectMap(Object... objs){
		
		Map<String,Object> map = new HashMap<String,Object>(objs.length);
		
		String key=null;
		Object value = null;
		
		for(int i=0;i<objs.length;i++){
			
			if(i%2==0){
				key=objs[i].toString();
			}else{
				value=objs[i];
				map.put(key, value);
			}
		}
		
		return map;
	}
	
}
