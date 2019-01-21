package com.hanshows.shopweb.integration.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface ClientGoodsMapper {
	public List<HashMap<String, Object>> selectEsl();

	public int createTableSql(Map<String, Object> map);

	public List<HashMap<String, Object>> selectCreatTable(Map<String, Object> map);

	public int deleteByKey(Map<String, Object> map);

	public int insert(Map<String, Object> map);

	public List<HashMap<String, Object>> selectEslpr(Map<String, Object> map);
	
	public List<HashMap<String, Object>> selectAllEslPr();
	
	public List<HashMap<String, Object>> selectOutOfStockEslPr();
	
	public List<HashMap<String, Object>> selectTable(Map<String, Object> map);
}
