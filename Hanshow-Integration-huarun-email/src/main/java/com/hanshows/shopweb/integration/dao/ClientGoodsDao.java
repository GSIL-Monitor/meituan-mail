package com.hanshows.shopweb.integration.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;



public interface ClientGoodsDao {
	public List<HashMap<String, Object>> selectEsl();
	
	/**  
	* @Title: createTableSql  
	* @Description: TODO(创建表)  
	* @param @param 建表sql
	* @param @return 成功返回0 
	* @return int 返回类型  
	* @throws  
	*/  
	public int createTableSql(String sql);
	
	/**  
	* @Title: selectCreatTable  
	* @Description: TODO(查询表结构)  
	* @param @param tableName
	* @param @return  返回表字段list   
	* @return List<HashMap<String,Object>>    返回类型  
	* @throws  
	*/  
	public List<HashMap<String, Object>> selectCreatTable(String tableName)throws Exception;
	
	/**  
	* @Title: deleteByKey  
	* @Description: TODO(根据主键删除数据)  
	* @param @param list    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public int deleteByKey(String tableName, String keyName,String value);
	
	/**  
	* @Title: insert  
	* @Description: TODO(批量添加数据)  
	* @param @param tableName
	* @param @param keyName
	* @param @param value
	* @param @return    参数  
	* @return int    返回类型  
	* @throws  
	*/  
	public int insert(String tableName, String keyName,String value);
	
	/**  
	* @Title: selectEslpr  
	* @Description: TODO(查询绑定关系)  
	* @param @param ESLId
	* @param @return    参数  
	* @return List<HashMap<String,Object>>    返回类型  
	* @throws  
	*/  
	public List<HashMap<String, Object>> selectEslpr(String ESLId);
	
	public List<HashMap<String, Object>> selectTable(String tableName);
	
	public List<HashMap<String, Object>> selectAllEslPr();
	
	public List<HashMap<String, Object>> selectOutOfStockEslPr();
	
	
	
	

}
