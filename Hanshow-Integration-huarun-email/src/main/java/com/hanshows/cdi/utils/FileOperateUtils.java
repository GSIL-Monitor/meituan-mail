package com.hanshows.cdi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 过滤指路歌曲文件
 * 
 * @author dylan_xu
 * @date Mar 11, 2012
 * @modified by
 * @modified date
 * @since JDK1.6
 * @see com.hanshows.utils.woyo.utils.FileUtil
 */
public class FileOperateUtils {
	public static final Logger logger = Logger
			.getLogger(FileOperateUtils.class);
	public static Set<String> sets = new HashSet<String>();

	public static String readTxt(String filePathAndName, String encoding)
			throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data);
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
			if (st != null && st.length() > 1)
				st = st.substring(0, st.length() - 1);
		} catch (IOException es) {
			st = "";
			logger.error(es.getMessage());
		}
		return st;
	}

	public static List<String> readLines(String path, String encoding) {
		encoding = encoding.trim();

		List<String> result = new ArrayList<String>();
		try {
			FileInputStream fs = new FileInputStream(path);
			UnicodeReader isr;
			if (encoding.equals("")) {
				isr = new UnicodeReader(fs, null);
			} else {
				isr = new UnicodeReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);

			String data = "";
			while ((data = br.readLine()) != null) {
				result.add(data);
			}
			br.close();
			isr.close();
			fs.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public static String readOnlyOne(String path, String encoding)
			throws IOException {

		FileInputStream fs = new FileInputStream(path);
		UnicodeReader isr;
		if (encoding.equals("")) {
			isr = new UnicodeReader(fs, null);
		} else {
			isr = new UnicodeReader(fs, encoding);
		}
		BufferedReader br = new BufferedReader(isr);
		String content = "";
		String data = "";
		while ((data = br.readLine()) != null) {
			content += data;
		}
		return content;
	}

	public static String read(String path, String encoding) throws IOException {

		FileInputStream fs = new FileInputStream(path);
		UnicodeReader isr;
		if (encoding.equals("")) {
			isr = new UnicodeReader(fs, null);
		} else {
			isr = new UnicodeReader(fs, encoding);
		}
		BufferedReader br = new BufferedReader(isr);
		String content = "";
		String data = "";
		while ((data = br.readLine()) != null) {
			content += data + "\r\n";
		}
		return content;
	}

	

	/**
	 * 创建文件夹
	 * 
	 * @param strFilePath
	 *            文件夹路径
	 */
	public boolean mkdirFolder(String strFilePath) {
		boolean bFlag = false;
		try {
			File file = new File(strFilePath.toString());
			if (!file.exists()) {
				bFlag = file.mkdir();
			}
		} catch (Exception e) {
			logger.error("新建目录操作出错" + e.getLocalizedMessage());

		}
		return bFlag;
	}

	public boolean createFile(String strFilePath, String strFileContent) {
		boolean bFlag = false;
		try {
			File file = new File(strFilePath.toString());
			if (!file.exists()) {
				bFlag = file.createNewFile();
			}
			if (bFlag == Boolean.TRUE) {
				FileWriter fw = new FileWriter(file);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(strFileContent.toString());
				pw.close();
			}
		} catch (Exception e) {
			logger.error("新建文件操作出错" + e.getLocalizedMessage());

		}
		return bFlag;
	}

	/**
	 * 删除文件
	 * 
	 * @param strFilePath
	 * @return
	 */
	public static boolean removeFile(String strFilePath) {
		boolean result = false;
		if (strFilePath == null || "".equals(strFilePath)) {
			return result;
		}
		File file = new File(strFilePath);
		if (file.isFile() && file.exists()) {
			result = false;
			while(result == Boolean.FALSE){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}
				result = file.delete();
			}
			if (result == Boolean.TRUE) {
				logger.debug("[REMOE_FILE:" + strFilePath + "删除成功!]");
			} else {
				logger.debug("[REMOE_FILE:" + strFilePath + "删除失败]");
			}
		}
		return result;
	}

	/**
	 * 删除文件夹(包括文件夹中的文件内容，文件夹)
	 * 
	 * @param strFolderPath
	 * @return
	 */
	public static boolean removeFolder(String strFolderPath) {
		boolean bFlag = false;
		try {
			if (strFolderPath == null || "".equals(strFolderPath)) {
				return bFlag;
			}
			File file = new File(strFolderPath.toString());

			bFlag = file.delete();
			if (bFlag == Boolean.TRUE) {
				logger.info("[REMOE_FOLDER:" + file.getPath() + "删除成功!]");
			} else {
				logger.info("[REMOE_FOLDER:" + file.getPath() + "删除失败]");
			}
		} catch (Exception e) {
			logger.error("FLOADER_PATH:" + strFolderPath + "删除文件夹失败!");
			logger.error(e.getMessage());
		}
		return bFlag;
	}

	/**
	 * 移除所有文件
	 * 
	 * @param strPath
	 */
	public static void removeAllFile(String strPath) {
		File file = new File(strPath);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] fileList = file.list();
		File tempFile = null;
		for (int i = 0; i < fileList.length; i++) {
			if (strPath.endsWith(File.separator)) {
				tempFile = new File(strPath + fileList[i]);
			} else {
				tempFile = new File(strPath + File.separator + fileList[i]);
			}
			if (tempFile.isFile()) {
				tempFile.delete();
			}
			if (tempFile.isDirectory()) {
				removeAllFile(strPath + "/" + fileList[i]);// 下删除文件夹里面的文件
				removeFolder(strPath + "/" + fileList[i]);// 删除文件夹
			}
		}

	}

	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			File newfile = new File(new File(newPath).getParent());
			if (!newfile.exists()) {
				newfile.mkdir();
				newfile.createNewFile();
			}
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					logger.info(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				logger.debug("[COPY_FILE:" + oldfile.getPath() + "复制文件成功!]");
			}
		} catch (Exception e) {
			logger.error("复制单个文件操作出错 \r\n" + e.getMessage());
		}
	}

	public static void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
					logger.debug("[COPY_FILE:" + temp.getPath() + "复制文件成功!]");
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			logger.error("复制整个文件夹内容操作出错 \r\n" + e.getMessage());

		}
	}

	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		removeFile(oldPath);
	}

	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		removeAllFile(oldPath);
		removeFolder(oldPath);
	}
	
	public static ArrayList<String> readFileList(String folderPath)
	{
		ArrayList<String> result=new ArrayList<String>();
		File folder = new File(folderPath);
		if(folder.isDirectory())
		{
			String[] files = folder.list();
			if(files!=null&&files.length>0)
			{
				for(String file:files)
				{
					File tempFile = new File(file);
					if(!tempFile.isDirectory())
					{
						result.add(file);
					}
				}
			}
			
		}
		return result;
	}
	
	/*
	 * 根据时间排序
	 */
	public static ArrayList<String> readFileByDateList(String folderPath)
	{
		ArrayList<String> result=new ArrayList<String>();
		File folder = new File(folderPath);
		if(folder.isDirectory())
		{
			File[] files = folder.listFiles();
			if(files!=null&&files.length>0)
			{
				
				Arrays.sort(files,new Comparator< File>(){

					@Override
					public int compare(File f1, File f2) {
						long diff = f1.lastModified() - f2.lastModified();
						if (diff > 0)
							  return 1;
							else if (diff == 0)
							  return 0;
							else
							  return -1;
					}
					public boolean equals(Object obj) {
						return true;
					     }
				});
			     
			     for(File file : files){
			    	 if(!file.isDirectory()){
			    		 result.add(file.getName());
			    	 }
			     }
			}
			
		}
		return result;
	}
}
