package com.hanshows.shopweb.integration.service;

import java.util.HashMap;
import java.util.List;

public interface ClientGoodsService {
	
	//创建表
	public int createTable(List list,String tableName,int size,String key);
	//根据字段列表创建表
	public int createTableByField(List list,String tableName,int size,String key);
	
	//插入表
	public int insertTable(List<HashMap<String, Object>> list,String tableName,String keyName);
	
	//插入表判断unlink
	public int insertTable(List<HashMap<String, Object>> list,String tableName,String keyName,int isUnlink);
	
	public List<HashMap<String, Object>> selectAll(String table1,String table2);

}
