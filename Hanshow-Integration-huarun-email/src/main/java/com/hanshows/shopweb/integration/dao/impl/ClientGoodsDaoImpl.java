package com.hanshows.shopweb.integration.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.hanshows.cdi.pojo.JobContext;
import com.hanshows.shopweb.integration.dao.ClientGoodsDao;
import com.hanshows.shopweb.integration.dao.ClientGoodsMapper;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

@Component
public class ClientGoodsDaoImpl implements ClientGoodsDao {

	private static Logger logger = Logger.getLogger(new ClientGoodsDaoImpl().getClass());
	private static SqlSessionFactory sqlSessionFactory;
	private static ClientGoodsDaoImpl baseDao;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public static ClientGoodsDao getInstance() {
		return baseDao;
	}
	
	public static ClientGoodsDao getInstance(JobContext context) {
		if (baseDao == null) {
			baseDao = new ClientGoodsDaoImpl();
		}
		if (null == sqlSessionFactory) {
			try {
				sqlSessionFactory = (SqlSessionFactory) context.getSpringContext().getBean("sqlSessionFactory");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
		return baseDao;
	}


	@Override
	public List<HashMap<String, Object>> selectEsl() {
		SqlSession session = null;
		List<HashMap<String, Object>> list;
		try {
			lock.readLock().lock();
			session = sqlSessionFactory.openSession();
			ClientGoodsMapper mapper = session.getMapper(ClientGoodsMapper.class);
			logger.debug("selectEsl begin");
			list = mapper.selectEsl();
			logger.debug("selectEsl end");
		} finally {
			lock.readLock().unlock();
			if (session != null)
				session.close();
		}
		return list;
	}
	@Override
	public int createTableSql(String sql){ 
		Map<String , Object> map=new HashMap<String , Object>(); 
		map.put("sql",sql);
		SqlSession session = null;
		int  re;
		try {
			lock.readLock().lock();
			session = sqlSessionFactory.openSession();
			ClientGoodsMapper mapper = session.getMapper(ClientGoodsMapper.class);
			logger.debug("createTable begin");
			re = mapper.createTableSql(map);
			logger.debug("createTable end");
		} finally {
			lock.readLock().unlock();
			if (session != null)
				session.close();
		}
		return re;
		} 
	
	@Override
	public List<HashMap<String, Object>> selectCreatTable(String tableName) throws Exception{
		List<HashMap<String, Object>> list = null;
		Map<String , Object> map=new HashMap<String , Object>(); 
		map.put("tableName",tableName); 
		SqlSession session = null;
		try {
			lock.readLock().lock();
			session = sqlSessionFactory.openSession();
			ClientGoodsMapper mapper = session.getMapper(ClientGoodsMapper.class);
			logger.debug("selectCreateTable begin");
			list = mapper.selectCreatTable(map);
			logger.debug("selectCreateTable end");
		}finally {
			lock.readLock().unlock();
			if (session != null)
				session.close();
		}
		return list;
	}
	
	@Override
	public int deleteByKey(String tableName, String keyName,String value){
		Map<String , Object> map=new HashMap<String , Object>(); 
		map.put("tableName",tableName); 
		map.put("keyName",keyName); 
		map.put("value",value); 
		int re = -1;
		SqlSession session = null;
		try {
			lock.readLock().lock();
			session = sqlSessionFactory.openSession();
			ClientGoodsMapper mapper = session.getMapper(ClientGoodsMapper.class);
			logger.debug("deleteByKey begin");
			re =mapper.deleteByKey(map);
			logger.debug("deleteByKey end");
		}finally {
			lock.readLock().unlock();
			if (session != null)
				session.close();
		}
		return re;
	}
	
	@Override
	public int insert(String tableName, String keys,String value){
		Map<String , Object> map=new HashMap<String , Object>(); 
		map.put("tableName",tableName);
		map.put("keys",keys); 
		map.put("value",value); 
		logger.info(keys);
		logger.info(value);
		int re = -1;
		SqlSession session = null;
		try {
			lock.readLock().lock();
			session = sqlSessionFactory.openSession();
			ClientGoodsMapper mapper = session.getMapper(ClientGoodsMapper.class);
			logger.debug("deleteByKey begin");
			re =mapper.insert(map);
			logger.debug("deleteByKey end");
		}finally {
			lock.readLock().unlock();
			if (session != null)
				session.close();
		}
		return re;
	}
	
	public List<HashMap<String, Object>> selectEslpr(String ESLId){
		List<HashMap<String, Object>> list = null;
		Map<String , Object> map=new HashMap<String , Object>(); 
		map.put("ESLId",ESLId); 
		SqlSession session = null;
		try {
			lock.readLock().lock();
			session = sqlSessionFactory.openSession();
			ClientGoodsMapper mapper = session.getMapper(ClientGoodsMapper.class);
			logger.debug("selectEslpr begin");
			list = mapper.selectEslpr(map);
			logger.debug("selectEslpr end");
		}finally {
			lock.readLock().unlock();
			if (session != null)
				session.close();
		}
		return list;
	}
	
	public List<HashMap<String, Object>> selectAllEslPr(){
		List<HashMap<String, Object>> list = null;
		SqlSession session = null;
		try {
			lock.readLock().lock();
			session = sqlSessionFactory.openSession();
			ClientGoodsMapper mapper = session.getMapper(ClientGoodsMapper.class);
			logger.debug("selectAllEslPr begin");
			list = mapper.selectAllEslPr();
			logger.debug("selectAllEslPr end");
		}finally {
			lock.readLock().unlock();
			if (session != null)
				session.close();
		}
		return list;
	}
	
	public List<HashMap<String, Object>> selectOutOfStockEslPr(){
		List<HashMap<String, Object>> list = null;
		SqlSession session = null;
		try {
			lock.readLock().lock();
			session = sqlSessionFactory.openSession();
			ClientGoodsMapper mapper = session.getMapper(ClientGoodsMapper.class);
			logger.debug("selectOutOfStockEslPr begin");
			list = mapper.selectOutOfStockEslPr();
			logger.debug("selectOutOfStockEslPr end");
		}finally {
			lock.readLock().unlock();
			if (session != null)
				session.close();
		}
		return list;
	}
	
	public List<HashMap<String, Object>> selectTable(String tableName){
		List<HashMap<String, Object>> list = null;
		Map<String , Object> map=new HashMap<String , Object>(); 
		map.put("tableName",tableName); 
		SqlSession session = null;
		try {
			lock.readLock().lock();
			session = sqlSessionFactory.openSession();
			ClientGoodsMapper mapper = session.getMapper(ClientGoodsMapper.class);
			logger.debug("selectTable begin");
			list = mapper.selectTable(map);
			logger.debug("selectTable end");
		}finally {
			lock.readLock().unlock();
			if (session != null)
				session.close();
		}
		return list;
	}

}
