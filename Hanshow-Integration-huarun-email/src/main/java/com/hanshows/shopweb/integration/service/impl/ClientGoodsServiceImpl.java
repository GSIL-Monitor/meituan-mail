package com.hanshows.shopweb.integration.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hanshows.shopweb.integration.dao.ClientGoodsDao;
import com.hanshows.shopweb.integration.dao.impl.ClientGoodsDaoImpl;
import com.hanshows.shopweb.integration.service.ClientGoodsService;

/**  
* @ClassName: ClientGoodsServiceImpl  
* @Description: TODO(serviceImpl)  
* @author tingshuozhang  
* @date 2017年6月21日  
*    
*/  
@Service
public class ClientGoodsServiceImpl implements ClientGoodsService{
	
	private static final Logger logger = LoggerFactory.getLogger(ClientGoodsServiceImpl.class);
	
	ClientGoodsDao clientGoodsDao = new ClientGoodsDaoImpl();

	/* (非 Javadoc)  
	* <p>Title: createTable</p>  
	* <p>Description: 创建表</p>  
	* @param list
	* @param tableName
	* @param size
	* @return  
	* @see com.hanshows.shopweb.integration.service.ClientGoodsService#createTable(java.util.List, java.lang.String, int)  
	*/  
	@SuppressWarnings("rawtypes")
	public int createTable(List list,String tableName,int size,String key){
		int re = -1;
		try {
			String sql ="DROP TABLE IF EXISTS "+tableName+"; CREATE TABLE ";
			//构造表名
			sql += tableName + "(";
			for(int i=0;i<list.size();i++){
				sql += list.get(i)+" varchar("+size+")";
				if(key.equals(list.get(i)))
				sql+=" not null primary key";
				sql+=",";
			}
			sql = sql.substring(0, sql.length()-1);
			sql += ");";
			System.out.println(sql);
			re = clientGoodsDao.createTableSql(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;
	}
	
	/* (非 Javadoc)  
	* <p>Title: insertTable</p>  
	* <p>Description: 插入表</p>  
	* @param list
	* @param tableName
	* @return  
	* @see com.hanshows.shopweb.integration.service.ClientGoodsService#insertTable(java.util.List, java.lang.String)  
	*/  
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int insertTable(List<HashMap<String,Object>> list,String tableName,String keyName){
		int re = -1;
		String delvalues="";
		List keyList = new ArrayList();
		String keys="";
		String values="";
		if(list.size()>0){
			 HashMap<String,Object> keymap = list.get(0);
			 Iterator iterator = keymap.keySet().iterator();
		        while (iterator.hasNext()) {
		        	String key = (String)iterator.next();
 		        	keyList.add(key);
		        	keys+=key+",";
		        }
		        keys= keys.substring(0, keys.length()-1);
		for(int i=0;i<list.size();i++){
			HashMap<String,Object> map = list.get(i);
			values+="(";
			for(int j=0;j<keyList.size();j++){
				values+="\""+map.get(keyList.get(j))+"\",";
			}
			values=values.substring(0, values.length()-1);
			values+="),";
			delvalues+="\""+map.get(keyName)+"\",";
		}
		values=values.substring(0, values.length()-1);
		delvalues=delvalues.substring(0, delvalues.length()-1);
		
		logger.info(keys);
		logger.info(values);
		
		re=clientGoodsDao.insert(tableName, keys, values);
		
		
		}
		return re;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int createTableByField(List list,String tableName,int size,String key){
		logger.info("================================================================================");
		int re = -1;
		List listnf = new ArrayList();
		List listnDB = new ArrayList();
		List listf = list;
		for(int i=0;i<listf.size();i++){
			listnf.add(listf.get(i).toString().split(":")[0]);
		}
		
		logger.info(">>> Validation Database Table："+tableName);
		List<HashMap<String, Object>> listDB;
		try {
			listDB = clientGoodsDao.selectCreatTable(tableName);
			logger.info(">>> Validation Database End");
			for(int i=0;i<listDB.size();i++){
				listnDB.add(listDB.get(i).get("Field"));
			}
		} catch (Exception e) {
			logger.info(">>> Table "+tableName+" Is Not Creat");
			logger.debug(e.getMessage());
		}
		
		if(equalList(listnf,listnDB)){
			logger.info(">>> Table Is Creat");
		}else{		
			logger.info(">>> Creat Table");			
			createTable(listnf, tableName, 255,key);
			logger.info(">>> Creat Table End");
		}
		logger.info("--------------------------------------------------------------------------------");
		return re;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean equalList(List list1, List list2) {
        if (list1.size() != list2.size())
            return false;
        for (Object object : list1) {
            if (!list2.contains(object))
            return false;
        }
        return true;

	}
	
	public int insertTable(List<HashMap<String, Object>> list,String tableName,String keyName,int isUnlink){
		int re =-1;
		if(isUnlink==1){
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map = list.get(i);
				String eslId =  map.get("shelfLabelId").toString();
				String a;
				try {
					List<HashMap<String, Object>> Esl = new ArrayList<>();
					if(Esl.size()==0){
						logger.warn(">>> The EslId Does Not Exist:"+eslId);
						list.remove(i);
						continue;
					}else{
						a = Esl.get(0).get("goods_id").toString().split("/###/")[1];
						list.get(i).put("itemId", a);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				}
			
			re=insertTable(list,tableName,keyName);
		}else{
			re=insertTable(list,tableName,keyName);
		}
		return re;
	}
	
	public List<HashMap<String, Object>> selectAll(String table1,String table2){
		List<HashMap<String, Object>> list = new ArrayList<>();
		list = clientGoodsDao.selectTable(table1);
		list.addAll(clientGoodsDao.selectTable(table2));
		return list;	
	}

}
