package com.hanshows.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Xlsx {
	// private static XSSFWorkbook workbook = null;  
	    
	    /** 
	     * 判断文件是否存在. 
	     * @param fileDir  文件路径 
	     * @return 
	     */  
	    public static boolean fileExist(String fileDir){  
	         boolean flag = false;  
	         File file = new File(fileDir);  
	         flag = file.exists();  
	         return flag;  
	    }  
	    /** 
	     * 判断文件的sheet是否存在. 
	     * @param fileDir   文件路径 
	     * @param sheetName  表格索引名 
	     * @return 
	     */  
	    public static boolean sheetExist(String fileDir,String sheetName) throws Exception{  
	         boolean flag = false;  
	         File file = new File(fileDir);  
	         if(file.exists()){    //文件存在  
	            //创建workbook  
	             try {  
	            	 XSSFWorkbook  workbooksheet = new XSSFWorkbook(new FileInputStream(file));  
	                //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
	                XSSFSheet sheet = workbooksheet.getSheet(sheetName);    
	                if(sheet!=null)  
	                    flag = true;  
	            } catch (Exception e) {  
	                throw e;
	            }   
	              
	         }else{    //文件不存在  
	             flag = false;  
	         }  
	         return flag;  
	    }  
	    
	    public static void createSheet(String fileDir,String sheetName,String titleRow[]) throws Exception{  
	    	 //创建workbook  
	        File file = new File(fileDir);  
	        XSSFWorkbook workbook=null;
	        try {  
	            workbook = new XSSFWorkbook(new FileInputStream(file));  
	            workbook.createSheet(sheetName);    
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        //流  
	        FileOutputStream out = null; 
	        try {  
	            //添加表头  
	            XSSFRow row = workbook.getSheet(sheetName).createRow(0);    //创建第一行    
	            for(int i = 0;i < titleRow.length;i++){  
	                XSSFCell cell = row.createCell(i);  
	                cell.setCellValue(titleRow[i]);  
	            }  
	            out = new FileOutputStream(fileDir);  
	            workbook.write(out);  
	        } catch (Exception e) {  
	             e.printStackTrace();
	        } finally {    
	            try {    
	                out.close();    
	            } catch (IOException e) {    
	                e.printStackTrace();  
	            }    
	        }    
	    }
	    
	    
	    
	    /** 
	     * 创建新excel. 
	     * @param fileDir  excel的路径 
	     * @param sheetName 要创建的表格索引 
	     * @param titleRow excel的第一行即表格头 
	     */  
	    public static void createExcel(String fileDir,String sheetName,String titleRow[]) throws Exception{  
	        //创建workbook  
	    	XSSFWorkbook workbook = new XSSFWorkbook();  
	        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
	        XSSFSheet sheet1 = workbook.createSheet(sheetName);    
	        //新建文件  
	        FileOutputStream out = null;  
	        try {  
	            //添加表头  
	            XSSFRow row = workbook.getSheet(sheetName).createRow(0);    //创建第一行    
	            for(int i = 0;i < titleRow.length;i++){  
	                XSSFCell cell = row.createCell(i);  
	                cell.setCellValue(titleRow[i]);  
	            }  
	            out = new FileOutputStream(fileDir);  
	            workbook.write(out);  
	        } catch (Exception e) {  
	             e.printStackTrace();
	        } finally {    
	            try {    
	                out.close();    
	            } catch (IOException e) {    
	                e.printStackTrace();  
	            }    
	        }    
	    }  
	    /** 
	     * 删除文件. 
	     * @param fileDir  文件路径 
	     */  
	    public static boolean deleteExcel(String fileDir) {  
	        boolean flag = false;  
	        File file = new File(fileDir);  
	        // 判断目录或文件是否存在    
	        if (!file.exists()) {  // 不存在返回 false    
	            return flag;    
	        } else {    
	            // 判断是否为文件    
	            if (file.isFile()) {  // 为文件时调用删除文件方法    
	                file.delete();  
	                flag = true;  
	            }   
	        }  
	        return flag;  
	    }  
	    /** 
	     * 往excel中写入(已存在的数据无法写入). 
	     * @param fileDir    文件路径 
	     * @param sheetName  表格索引 
	     * @param object 
	     * @throws Exception 
	     */  
	    public static void writeToExcel(String fileDir,String sheetName,List<HashMap<String, Object>> mapList,List EmailField,Map<String, String> ststusMap) throws Exception{  
	    	System.out.println("写入文件");
	        //创建workbook  
	        File file = new File(fileDir);  
	        XSSFWorkbook workbook = null;
	        try {  
	            workbook = new XSSFWorkbook(new FileInputStream(file));  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        //流  
	        FileOutputStream out = null;  
	        XSSFSheet sheet = workbook.getSheet(sheetName);  
	        // 获取表格的总行数  
	         int rowCount = sheet.getLastRowNum() + 1; // 需要加一  
	         System.out.println(rowCount);
	        // 获取表头的列数  
	        int columnCount = sheet.getRow(0).getLastCellNum();  
	        try {  
	        	HashMap<String,String> mapkey = new HashMap<>();
	        	for (int i=0;i<EmailField.size();i++){
	        		mapkey.put(EmailField.get(i).toString().split(":")[1], EmailField.get(i).toString().split(":")[0]);
	    		}  
	            // 获得表头行对象  
	            XSSFRow titleRow = sheet.getRow(0);  
	            if(titleRow!=null){ 
	                for(int rowId=0;rowId<mapList.size();rowId++){
	                    Map map = mapList.get(rowId);
	                    XSSFRow newRow=sheet.createRow(rowId+1);
	                    for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {  //遍历表头  
	                        String title = titleRow.getCell(columnIndex).toString().trim().toString().trim();  
	                        XSSFCell cell = newRow.createCell(columnIndex);
	                        if(mapkey.get(title).equals("status")){
	                        	cell.setCellValue(map.get(mapkey.get(title))==null ? null : ststusMap.get(map.get(mapkey.get(title)).toString()));  
	                        }else{
	                        	cell.setCellValue(map.get(mapkey.get(title))==null ? null : map.get(mapkey.get(title)).toString());  
	                        }               
	                    } 
	                }
	            }  

	            out = new FileOutputStream(file);  
	            workbook.write(out);  
	            System.out.println("写入文件end");
	        } catch (Exception e) {  
	            e.printStackTrace();
	        } finally {    
	            try {    
	            	out.close();    
	            } catch (IOException e) {    
	                e.printStackTrace();  
	            }    
	        }    
	    }  
	      
	    public static void main(String[] args) throws Exception {  
	      
	        System.out.println(Xlsx.fileExist("F:/cdi/shopweb-plugin-apm-huarun/local/test2.xlsx"));  
	        //创建文件  
	        String title[] = {"id","name","password"};  
	        Xlsx.createExcel("F:/cdi/shopweb-plugin-apm-huarun/local/test2.xlsx","sheet1",title);  
	        List<Map> list=new ArrayList<Map>();
	        Map<String,String> map=new HashMap<String,String>();
	        map.put("id", "111");
	        map.put("name", "张三");
	        map.put("password", "111！@#");
	        
	        Map<String,String> map2=new HashMap<String,String>();
	        map2.put("id", "222");
	        map2.put("name", "李四");
	        map2.put("password", "222！@#");
	        list.add(map);
	        list.add(map2);
	       // Xlsx.writeToExcel("F:/cdi/shopweb-plugin-apm-huarun/local/test2.xlsx","sheet1",list);  
	        
	        String sql="select aaa,bbb,ccc from dddd";
	        String sqlForSplit = sql.substring(sql.toLowerCase().indexOf("select")+6,sql.toLowerCase().indexOf("from")).trim();
	        String sqlRemoveFrom=sql.substring(sql.toLowerCase().indexOf("from")+5).trim();
	        System.out.println(sqlRemoveFrom);  
	        String tableName=sqlRemoveFrom.indexOf(" ")==-1 ?  sqlRemoveFrom : sqlRemoveFrom.substring(0,sqlRemoveFrom.indexOf(" "));
	        System.out.println(tableName);  
	       
	     
	    }  

}
