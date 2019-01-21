package com.hanshows.cdi.utils;
/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　   █████━█████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ +　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 **/
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPUtil {
	private static final Logger logger = LoggerFactory.getLogger(SFTPUtil.class);
	
	private ChannelSftp sftp;
	private Session session;
	/** FTP 登录用户名*/  
    private String username;
    /** FTP 登录密码*/  
    private String password;  
    /** 私钥文件的路径*/  
    private String keyFilePath;  
    /** FTP 服务器地址IP地址*/  
    private String host;  
    /** FTP 端口*/  
    private int port;
    
    /** 
     * 构造基于密码认证的sftp对象 
     * @param userName 
     * @param password 
     * @param host 
     * @param port 
     */  
    public SFTPUtil(String username, String password, String host, int port) {  
        this.username = username;  
        this.password = password;  
        this.host = host;  
        this.port = port;  
    } 
    
    /** 
     * 构造基于秘钥认证的sftp对象 
     * @param userName 
     * @param host 
     * @param port 
     * @param keyFilePath 
     */  
    public SFTPUtil(String username, String host, int port, String keyFilePath) {  
        this.username = username;  
        this.host = host;  
        this.port = port;  
        this.keyFilePath = keyFilePath;  
    }
    
    public SFTPUtil(){}
    
    /** 
     * 连接sftp服务器 
     *  
     * @throws Exception 
     */  
    public void login(){  
        try {  
            JSch jsch = new JSch();  
            if (keyFilePath != null) {  
                jsch.addIdentity(keyFilePath);// 设置私钥  
                logger.info("sftp connect,path of private key file：{}" , keyFilePath);  
            }  
            logger.info("sftp connect by host:{} username:{}",host,username);  
  
            session = jsch.getSession(username, host, port);  
            logger.info("Session is build");  
            if (password != null) {  
                session.setPassword(password);  
            }  
            Properties config = new Properties();  
            config.put("StrictHostKeyChecking", "no");  
              
            session.setConfig(config);  
            session.connect();  
            logger.info("Session is connected");  
              
            Channel channel = session.openChannel("sftp");  
            channel.connect();  
            logger.info("channel is connected");  
  
            sftp = (ChannelSftp) channel;  
            logger.info(String.format("sftp server host:[%s] port:[%s] is connect successfull", host, port));  
        } catch (JSchException e) {  
        	logger.error("Cannot connect to specified sftp server : {}:{} \n Exception message is: {}", new Object[]{host, port, e.getMessage()});  
        }  
    }
    /** 
     * 关闭连接 server 
     */  
    public void logout(){
        if (sftp != null) {  
            if (sftp.isConnected()) {  
                sftp.disconnect();  
                logger.info("sftp is closed already");  
            }  
        }  
        if (session != null) {  
            if (session.isConnected()) {  
                session.disconnect();  
                logger.info("sshSession is closed already");  
            }  
        }  
    }  
    /** 
     * 下载文件 
     *  
     * @param directory 
     *            下载目录 
     * @param downloadFile 
     *            下载的文件 
     * @param saveFile 
     *            存在本地的路径 
     * @throws SftpException  
     * @throws FileNotFoundException  
     * @throws Exception 
     */  
    public void downloadFile(String sftpPath, String localPath) throws Exception{
    	sftp.get(sftpPath, localPath);
    }
    
    public boolean download(String directory, String downloadFile, String saveFile){  
    	
        try {
			sftp.get(directory + saveFile, downloadFile);
		} catch (SftpException e) {
			logger.error("No such file",e.getMessage());;
			return false;
		}
        logger.info("file:{} is download successful" , downloadFile); 
        return true;
    }  
    
    public void delete(String delFile) throws SftpException{
    	sftp.rm(delFile);
    }
    /** 
     * 下载文件 
     * @param directory 下载目录 
     * @param downloadFile 下载的文件名 
     * @return 字节数组 
     * @throws SftpException  
     * @throws IOException  
     * @throws Exception 
     */  
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException{  
        if (directory != null && !"".equals(directory)) {  
            sftp.cd(directory);  
        }  
        InputStream is = sftp.get(downloadFile);  
          
        byte[] fileData = IOUtils.toByteArray(is);  
          
        logger.info("file:{} is download successful" , downloadFile);  
        return fileData;  
    }
    /** 
     * 删除文件 
     *  
     * @param directory 
     *            要删除文件所在目录 
     * @param deleteFile 
     *            要删除的文件 
     * @throws SftpException  
     * @throws Exception 
     */  
    public void delete(String directory, String deleteFile) throws SftpException{  
        sftp.cd(directory);  
        sftp.rm(deleteFile);  
    } 
    
    public static void main(String[] args) throws SftpException, IOException {  
        SFTPUtil sftp = new SFTPUtil("SHUAI", "LOVEQQZ", "127.0.0.1", 22);  
        sftp.login();  
        byte[] buff = sftp.download("/Users/ZQQ/Documents/lib", "hs_goods.csv");  
        System.out.println(Arrays.toString(buff));  
        sftp.logout();  
    }
}
