package com.hanshows.cdi.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class TestMail {
	    public static void main(String[] args) {  
	        // 不要使用SimpleEmail,会出现乱码问题  
	        HtmlEmail email = new HtmlEmail();  
	        // SimpleEmail email = new SimpleEmail();  
	        try {  
	            // 这里是SMTP发送服务器的名字：qq的如下：  
	            email.setHostName("smtp.exmail.qq.com");
	            // 字符编码集的设置  
	            email.setCharset("gbk");
	            // 收件人的邮箱  
	            String[] array = {"hongjie.dou@hanshow.com","416600065@qq.com"};
	            email.addTo(array);
	            // 发送人的邮箱  
	            email.setFrom("cloud.services@hanshow.com");
	            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码  
	            email.setAuthentication("cloud.services@hanshow.com", "Hs1234");  
	            email.setSubject("下午3：00会议室讨论，请准时参加");  
	            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签  
	            email.setMsg("<h1 style='color:red'>下午3：00会议室讨论</h1>" + " 请准时参加！");  
	            // 发送  
	            email.send();  
	  
	            System.out.println("邮件发送成功!");  
	        } catch (EmailException e) {  
	            e.printStackTrace();  
	            System.out.println("邮件发送失败!");
	        }  
	    }  
}
