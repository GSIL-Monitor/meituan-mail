package com.hanshows.email;

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.internet.MimeBodyPart;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hanshows.cdi.generic.ftp.FtpClientWrap;
import com.hanshows.cdi.pojo.JobContext;
import com.hanshows.cdi.utils.MailSender;
import com.hanshows.excel.Xlsx;
import com.hanshows.shopweb.integration.dao.ClientGoodsDao;
import com.hanshows.shopweb.integration.dao.impl.ClientGoodsDaoImpl;
import com.mysql.fabric.xmlrpc.Client;

/**
 * @ClassName: PrinterTimer
 * @Description: TODO(输出价签状态)
 * @author tingshuozhang
 * @date 2017年6月22日
 * 
 */
@Component(value = "GetFileEmailJob")
public class GetFileEmailTimer {

	private static SimpleDateFormat recordDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat xlsxDate = new SimpleDateFormat("yyyy-MM-dd_HH,mm,ss");
	private static final Logger logger = Logger.getLogger(GetFileEmailTimer.class);
	private JobContext jobContext = new JobContext();
	private static ClientGoodsDao clientGoodsDao;
	private File bodyFile;
	private File titleFile;
	private String body;
	private String title;
	private String table;
	private Map<Integer, String> failMap = new HashMap<Integer, String>();
	private Map<String, String> ststusMap = new HashMap<String, String>();
	Xlsx xlsx = new Xlsx();
	String xlsxName ="";
	

	public void GetFileEmailTimer() {
		try {
			// 创建模板文件目录
			File contentDir = new File("content");
			jobContext.setContentDir(contentDir);
			
			PropertiesConfiguration configuration = new PropertiesConfiguration();
			configuration.load(new InputStreamReader(Client.class.getClassLoader().getResourceAsStream("config.properties"), "UTF-8"));
			
			jobContext.setCharset(configuration.getString("system.charset", "UTF-8"));
			jobContext.setAlarmLimit(configuration.getInt("mail.smtp.limit"));
			jobContext.setAnonymous(configuration.getString("mail.smtp.anonymous"));
			jobContext.setMailhost(configuration.getString("mail.smtp.host"));
			jobContext.setMailcharset(configuration.getString("mail.smtp.charset"));
			jobContext.setMailaddressee(configuration.getStringArray("mail.smtp.addressee"));
			jobContext.setSender(configuration.getString("mail.smtp.sender"));
			jobContext.setUsr(configuration.getString("mail.smtp.usr"));
			jobContext.setPwd(configuration.getString("mail.smtp.pwd"));
			jobContext.setAlarm(configuration.getString("mail.smtp.alarm"));
			jobContext.setStatus(configuration.getList("email.status"));
			
			jobContext.setFtpswitch(configuration.getInt("ftp.switch"));
			jobContext.setMailswitch(configuration.getInt("mail.switch"));
			jobContext.setLocalswitch(configuration.getInt("local.switch"));
			
			// 获得FTP server的地址和用户信息
			String ftpServerHost = configuration.getString("generic.ftpserver.host");
			if (ftpServerHost == null) {
				throw new IllegalArgumentException("config.properties中没有找到通用对接模块FTP服务器主机地址: generic.ftpserver.host");
			} else {
				jobContext.setFtpServerHost(ftpServerHost);
			}
			int ftpServerPort = configuration.getInt("generic.ftpserver.port", 0);
			if (ftpServerPort == 0) {
				throw new IllegalArgumentException("config.properties中没有找到通用对接模块FTP服务器端口号: generic.ftpserver.port");
			} else {
				jobContext.setFtpServerPort(ftpServerPort);
			}
			String ftpUsername = configuration.getString("generic.ftpserver.username");
			if (ftpUsername == null) {
				throw new IllegalArgumentException("config.properties中没有找到登录通用对接模块FTP服务器的用户名: generic.ftpserver.username");
			} else {
				jobContext.setFtpUsername(ftpUsername);
			}
			String ftpPassword = configuration.getString("generic.ftpserver.password");
			if (ftpPassword == null) {
				throw new IllegalArgumentException("config.properties中没有找到登录通用对接模块FTP服务器的密码: generic.ftpserver.password");
			} else {
				jobContext.setFtpPassword(ftpPassword);
			}
			String ftpPath = configuration.getString("generic.ftpserver.path");
			jobContext.setFtpPath(ftpPath);
			
			jobContext.setStoreCode(configuration.getString("storeCode"));
			
			jobContext.setLocalPath(configuration.getString("customer.local.path"));
			
			jobContext.setBackfilePath(new File(configuration.getString("GetFileEmailPath")));
			
			jobContext.setGetFileEmailPath(new File(configuration.getString("GetFileEmailPath")));
			
			

			jobContext.setEmailField(configuration.getList("getfile.email.field"));
			clientGoodsDao = ClientGoodsDaoImpl.getInstance();// 数据层接口实现

			bodyFile = new File(jobContext.getContentDir() + File.separator + "MailBody");
			titleFile = new File(jobContext.getContentDir() + File.separator + "MailTitle");
			body = reader(bodyFile);
			title = reader(titleFile);
			
			System.out.println(jobContext.getStatus());

			for (Object status : jobContext.getStatus()) {
				String statusString[] = status.toString().split(":");
				ststusMap.put(statusString[0], statusString[1]);
			}
			
			 File local = new File(configuration.getString("system.local.dir", "local"));
			 jobContext.setLocalDataSource(local);
			 

			
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 获取邮件数据
	public List<HashMap<String, Object>> getEmailData(){// 获取邮件数据
		File file = jobContext.getGetFileEmailPath();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		//遍历文件
		for(File emailFile : file.listFiles()){
			String fileName = emailFile.getName();
			Date date = new Date();
			String time = xlsxDate.format(date);
			logger.info("data processing ->>> Start processing files" + fileName);
			File file2 = new File(jobContext.getLocalDataSource(), fileName+time+".bak");
			backupFile(emailFile,file2);
			logger.info("data processing ->>> Complete backup" + fileName);
			
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(emailFile.getAbsolutePath()),"UTF-8"));
				String line = null;
				int sum = 0;
				while((line = br.readLine())!=null){
					HashMap<String, Object> map = new HashMap<String, Object>();
					sum++;
			
					logger.info(line);
					//json对象转Map
				  
				    
				    map = JSON.parseObject(line,new TypeReference<HashMap<String, Object>>(){});
				 
				    if(map!=null){
				    	
				    	list.add(map);
				    }
					
				}
				
			} catch (Exception e) {			
				logger.error(">> !!! Parsing error !!! "+e.getMessage(),e);
			}  finally {
				try {
					if (br != null) {
						br.close();
					}
				} catch (IOException e) {
					logger.warn("Close file stream fail: " + e);
				}
			}
			emailFile.delete();
		}
		return list;


		
	}

	//门店绑定关系
	public List<HashMap<String, Object>> makeEmail(List<HashMap<String, Object>> list){
		String timestamp =  recordDateFormat.format(new Date());
		
		logger.info("------------------------------------------------------------------------------------------------");
		
		// 如果查询结果不为0绘制表格
		if (list.size() > 0) {
			int sum = 1;
			table =  "";
			table += "<h1>美团 对接状态邮件提报</h1>";
			table += "<h3>报文生成时间:"+timestamp+"</h3>";
			table += "<table class=\"bordered\">";		
			table += "<thead><tr>";
			table += "  <th scope=\"col\"></th>";
			for (Object key : jobContext.getEmailField())
				table += "  <th scope=\"col\">"
						+ key.toString().split(":")[1] + "</th>";
			table += "</tr></thead>";
			
			for (HashMap<String, Object> map : list) {

				table += " <tr valign=\"top\">";
				table += "<td>" + sum + "</td>";
				for (Object key : jobContext.getEmailField()) {
					table += "<td>" + map.get(key.toString().split(":")[0]) + "</td>";
				}
				sum++;
			}
			
	
			
			table += "</table>";
		} else {
			// 查询结果为0没有失败变价记录
			table = "";
			table +="<h1>美团 无对接信息</h1>";
			table +="<h3>报文生成时间:"+timestamp+"</h3>";
		}

 
		failCounter(jobContext.getAlarm(), failMap);
		logger.info("---------------------------------------------------------------------------------------------------");
		return list;
	}
	
	public void ftpUplode(File dataFile) throws IOException{
		logger.info("ftp上传文件");
		logger.info("dataFile："+dataFile);
		FtpClientWrap ftpClient = new FtpClientWrap();
		ftpClient.connectServer(jobContext.getFtpServerHost(), jobContext.getFtpServerPort(),
				jobContext.getFtpUsername(), jobContext.getFtpPassword());
		ftpClient.setFileTypeToBinary();
		ftpClient.makeDirectory(jobContext.getFtpPath());
		ftpClient.changeWorkingDirectory(jobContext.getFtpPath());
		logger.info("path："+jobContext.getFtpPath());
		ftpClient.uploadFile(dataFile.getAbsolutePath(), dataFile.getName());
		ftpClient.closeServer();
		logger.info("ftp上传end");
	}
	
	
	public void email() {
		GetFileEmailTimer();
		String fileName = null;
		List<HashMap<String, Object>> list = getEmailData();
		try {
			fileName = writeExcel(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Email
		if(jobContext.getMailswitch()==1){
			makeEmail(list);
		}
		//ftp
		if(jobContext.getFtpswitch()==1){
			File file = new File(fileName);
			try {
				ftpUplode(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//本地
		if(jobContext.getLocalswitch()==1){
			try {
				downloadFileLocal(jobContext.getLocalDataSource().getAbsolutePath(),fileName,jobContext.getLocalPath());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	//写excel
	public String  writeExcel(List<HashMap<String, Object>> list) throws Exception{
		HashMap<String,List<HashMap<String, Object>>> eslprMap = new HashMap<String,List<HashMap<String, Object>>>();
		String timestamp =  xlsxDate.format(new Date());
	
		if(list.size()>0){
			xlsxName =jobContext.getLocalDataSource()+File.separator+"meituanIntegration"+jobContext.getStoreCode()+timestamp+".xlsx";
		}else{
			xlsxName =jobContext.getLocalDataSource()+File.separator+"meituanIntegration"+jobContext.getStoreCode()+"NoIntegration"+timestamp+".txt";
			File file = new File(xlsxName);
			file.createNewFile();
		}
		logger.info("xlsx文件地址:"+xlsxName);  
		String title[]=new String[jobContext.getEmailField().size()];
		for (int i=0;i<jobContext.getEmailField().size();i++){
			title[i]=jobContext.getEmailField().get(i).toString().split(":")[1];
		}  
		for(HashMap<String, Object> map :list){
			String storeCode = map.get("storeCode").toString();
			if(eslprMap.get(storeCode)!=null){
				List<HashMap<String, Object>> plist=eslprMap.get(storeCode);
				plist.add(map);
				eslprMap.put(storeCode,plist);
			}else{
				List<HashMap<String, Object>> plist= new ArrayList<HashMap<String, Object>>(); 
				plist.add(map);
				eslprMap.put(storeCode,plist);
			}
			
		}
		//创建文件  
		logger.info("创建文件："+xlsxName);
		
		  File file = new File(xlsxName);  
		for (Entry<String, List<HashMap<String, Object>>> entry : eslprMap.entrySet()) { 
				if(!file.exists()){
					 Xlsx.createExcel(xlsxName,entry.getKey(),title);  
				}
				if(!Xlsx.sheetExist(xlsxName, entry.getKey())){
					 Xlsx.createSheet(xlsxName,entry.getKey(),title);  
				}
				
				logger.info("list："+list.size());
		        logger.info("写入文件："+xlsxName);
		        Xlsx.writeToExcel(xlsxName,entry.getKey(),entry.getValue(),jobContext.getEmailField(),ststusMap);  
		        logger.info("写入文件结束"); 
				  
			
			}
 
      
        return xlsxName;
	}

	// 邮件告警
	private String reader(File fi) {
		StringBuilder result = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BOMInputStream(new FileInputStream(fi)), "UTF-8"))) {
			String s = null;
			while ((s = reader.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(s);
			}
		} catch (Exception e) {
			logger.info(format("read %s IO error", fi.getName()) + e);
		}
		return result.toString();
	}

	private void failCounter(String alerm, Map<Integer, String> map) {

		mailSender(map, "isSended");

	}

	private void mailSender(Map<Integer, String> map, String send) {
		
		String content = "";

		content = body.replace("$fail_limit$", table);
		
		title = title.replace("$fail_title$", "对接状态邮件提报");
		try {

			MailSender.sendMail(jobContext, map, content, title,xlsxName,"meituan 对接状态邮件提报"+xlsxDate.format(new Date())+".xlsx");

			logger.info("sending warning e-mail  ->>> sucess");

		} catch (EmailException e) {
			logger.warn("sending warning e-mail  ->>> fail: " , e);
		}
	}
	
	class DownLoadException extends Exception  
	{  
	    public DownLoadException(String msg)  
	    {  
	        super(msg);  
	    }  
	} 
	
	private void downloadFileLocal(String downLoadFrom,String fileName,String downLoadTo) throws Exception {
		logger.info("downLoadFrom:"+downLoadFrom);
		File file = new File(fileName);
		if(!file.exists()){
			 throw new DownLoadException(" downloadFile Error");  
		}
		File file2 = new File(downLoadFrom);
		File[] tempList = file2.listFiles();
		
		if(tempList!=null){
			logger.info(" >>> Read Local Files");
			logger.info("  >> Number Of Data Files:" + tempList.length);
			logger.info(""+file.getName());
			for (int i = 0; i < tempList.length; i++) {
				String ftpfileName = tempList[i].getName();
				if(ftpfileName.equals(file.getName())){
					boolean isSuccess = copyFile(fileName,downLoadTo + file.getName());
				}
			}
			
		}else{
			logger.info(" >>> Read Local Files");
			logger.info("  >> Number Of Data Files:0");
		}		
	}

	
	private boolean copyFile(String oldPath, String newPath) {
		boolean isok = false;
		InputStream inStream =null;
		FileOutputStream fs =null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				inStream = new FileInputStream(oldPath); // 读入原文件
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024*1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				
				isok = true;
			}
			
		} catch (Exception e) {
			logger.info("###BACKUP LOCAL DATA ->>> ERROR");
			e.printStackTrace();

		}  finally {
			try {
				inStream.close();
				fs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("关闭流异常"+e.getMessage());
			}
			
		}
		return isok;
	}
	
	/**
	 * 
	 * @param f1
	 *            需备份文件
	 * @param f2
	 *            备份至目标文件
	 * @return
	 */
	private static boolean backupFile(File f1, File f2) {
		boolean flag = false;
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(f1);
			fo = new FileOutputStream(f2);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			if (in.transferTo(0, in.size(), out) >= 0) {// 连接两个通道，并且从in通道读取，然后写入out通道
				flag = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

}
