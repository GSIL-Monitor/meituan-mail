package com.hanshows.cleaner;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hanshows.cdi.utils.FileOperateUtils;



@Component(value="cleanJob")
public class CleanTimer {
	
	private static final Logger logger = Logger.getLogger(CleanTimer.class);
	private List<?> scanerPath;
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyMMddHHmmss");

	public void cleaner() throws IOException {
		PropertiesConfiguration config = null;
		logger.info("---------------------------------------------------------------------------------------------------------");
		try {
			config = new PropertiesConfiguration("config.properties");
			scanerPath = config.getList("scaner_path");
			logger.info("folder is:" + scanerPath);
		} catch (ConfigurationException e) {
			logger.error("read cleaner.properties error!", e);
		}
		for (int i = 0; i < scanerPath.size(); i++) {
			String sc = "";
			sc = scanerPath.get(i).toString();
			logger.info("Delete folder:" + sc);
			ArrayList<String> scanFolder = FileOperateUtils.readFileByDateList(sc);
			if (scanFolder.size() > 0) {
				int sum = 0;
				for (String dataFile : scanFolder) {
					try {
						File file =new File(sc + File.separator  + dataFile);
						Long time =file.lastModified();
						Calendar beforeTime = Calendar.getInstance();
						beforeTime.add(Calendar.DATE, -config.getInt("cleanerKeepTime"));// 5分钟之前的时间
						Date beforeD = beforeTime.getTime();
						
						if(beforeD.getTime()>time){
							if(deleteFile(sc + File.separator + dataFile))
							sum++;
						}else{
							logger.info("This document is within the retention period:"+dataFile);
						}
						
					} catch (Exception e) {
						logger.error("delete file fail! file is :" + scanerPath + dataFile);
					}
				}
				if (sum > 0) {
					logger.info("delete file success!count is:" + sum);
				} else {
					logger.info("no file to delete!");
				}
			}
		}
		logger.info("---------------------------------------------------------------------------------------------------------");
	}
	
	public  boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				logger.info("###DELETE DATA FILE:" + fileName + " SUCCESS");
				return true;
			} else {
				logger.info("###DELETE DATA FILE:" + fileName + " FAIL");
				return false;
			}
		} else {
			logger.info("###DELETE DATA FILE：" + fileName + " Non-existent");
			return false;
		}
	}

	
	private long dateSubtraction(String t){
		String nowTime = dateFormater.format(new Date().getTime());
		long subNum = 0;
		try {
			subNum = (dateFormater.parse(nowTime).getTime() - dateFormater.parse(t).getTime()) / 3600000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subNum;
	}
}
