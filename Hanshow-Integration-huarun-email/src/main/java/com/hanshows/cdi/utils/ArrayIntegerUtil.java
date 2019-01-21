package com.hanshows.cdi.utils;

public class ArrayIntegerUtil {
	public  int trspace(String[] array ,int serlen){
		if(array != null){
			for(int i =0;i<array.length;i++){
				int v = Integer.valueOf(array[i]);
				if(serlen<=v){
					return v;
				}
			}
		}
		return -1;
	}
	public  int strlen(String[] fileds,String field,String value){
		for(int i=0;i<fileds.length;i++){
			if(field.equals(fileds[i])){
				return value.length();
			}
		}
		return -1;
	}
	
	public  boolean flagField(String[] fileds,String field){
		if(fileds==null){
			return false;
		}
		for(int i=0;i<fileds.length;i++){
			if(field.equals(fileds[i])){
				return true;
			}
		}
		return false;
	}
}
