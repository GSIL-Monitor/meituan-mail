package com.hanshows.shopweb.integration.dao;
/**  
* @ClassName: BaseDao  
* @Description: TODO(BaseDao)  
* @author tingshuozhang  
* @date 2017年6月16日  
*    
*/  
public interface BaseDao<T> {

	public int creatTable();
	
	public int insert();
	
}
