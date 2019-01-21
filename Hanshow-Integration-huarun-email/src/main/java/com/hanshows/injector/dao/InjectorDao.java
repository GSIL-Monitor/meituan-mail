package com.hanshows.injector.dao;

import java.util.List;
import java.util.Map;

/**  
* @ClassName: InjectorDao  
* @Description: TODO(dao接口)  
* @author tingshuozhang  
* @date 2017年6月15日  
*    
*/  
public interface InjectorDao {

	
	/**  
	* @Title: insert
	* @Description: TODO(插入数据库)  
	* @param @param list
	* @param @return    参数  
	* @return int    返回类型  
	* @throws  
	*/  
	int insert(List<Map<String, Object>> list);
	
}
