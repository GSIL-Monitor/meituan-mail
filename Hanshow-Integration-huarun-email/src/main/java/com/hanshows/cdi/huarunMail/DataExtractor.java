package com.hanshows.cdi.huarunMail;
import com.hanshows.cdi.pojo.JobContext;
import com.hanshows.shopweb.integration.dao.ClientGoodsDao;
import com.hanshows.shopweb.integration.dao.impl.ClientGoodsDaoImpl;
import com.hanshows.shopweb.integration.service.ClientGoodsService;
import com.hanshows.shopweb.integration.service.impl.ClientGoodsServiceImpl;

import java.io.File;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class DataExtractor {
	
	

	private static final Logger logger = LoggerFactory.getLogger(DataExtractor.class);
	private JobContext jobContext;
	private boolean cleanCache;
	@SuppressWarnings("unused")
	private static ClientGoodsDao clientGoodsDao;
	
	ClientGoodsService clientGoodsService = new ClientGoodsServiceImpl();
	public DataExtractor() {
	}

	/**
	 * 初始化,加载所有配置文件,并检查配置文件中是否有错。
	 *
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		jobContext = new JobContext();
		PropertiesConfiguration configuration = new PropertiesConfiguration("config.properties");
	
		jobContext.setConfiguration(configuration);
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		jobContext.setSpringContext(applicationContext);
		// 创建local
        File local = new File(configuration.getString("system.local.dir", "local"));
        if (local.exists()) {
            if (!local.isDirectory()) {
                throw new IllegalArgumentException("临时文件目录设定的不是一个目录: " + local.getAbsolutePath());
            } else if (!local.canWrite()) {
                throw new IllegalArgumentException("临时文件目录没有写权限: " + local.getAbsolutePath());
            }
        } else if (!local.mkdirs()) {
            throw new IllegalArgumentException("没有权限创建临时文件目录: " + local.getAbsolutePath());
        }
        jobContext.setLocalDataSource(local);
		
		clientGoodsDao = ClientGoodsDaoImpl.getInstance(jobContext);//数据层接口实现
	}

	public static void main(String[] args) {
		DataExtractor extractor = new DataExtractor();
		try {
			logger.info("---------------------------------------------------------------------------------------------------------");
			logger.info("Hanshow-Integration-Huarun-Email-20180510");
			extractor.initialize();
			logger.info("---------------------------------------------------------------------------------------------------------");
		} catch (Exception e) {
			logger.error("initialization failed", e);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	   
	  //服务运行模式所需start/stop方法
		public static void start(String[] args) {
			DataExtractor extractor = new DataExtractor();
			try {
				logger.info("---------------------------------------------------------------------------------------------------------");
				logger.info("Hanshow-Integration-Huarun-Email-20180423 start");
				extractor.initialize();
				logger.info("---------------------------------------------------------------------------------------------------------");
			} catch (Exception e) {
				logger.error("initialization failed", e);
				e.printStackTrace();
				System.exit(-1);
			}
		}
		
		public static void stop(String[] args) {
			logger.info("Hanshow-Integration-Huarun-Email-20180423 stop");
			System.exit(0);
		}
}
