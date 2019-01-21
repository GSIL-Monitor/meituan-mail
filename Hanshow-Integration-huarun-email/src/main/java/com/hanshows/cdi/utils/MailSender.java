package com.hanshows.cdi.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.hanshows.cdi.pojo.JobContext;

public class MailSender {
	public static void sendMail(JobContext jobContext, Map<Integer, String> map, String body, String title,String attachPath,String attachName) throws EmailException {
		// 不要使用SimpleEmail,会出现乱码问题
		HtmlEmail email = new HtmlEmail();
		// SimpleEmail email = new SimpleEmail();
		// 这里是SMTP发送服务器的名字：qq的如下：
		email.setHostName(jobContext.getMailhost());
		// 字符编码集的设置
		email.setCharset(jobContext.getMailcharset());
		// 收件人的邮箱
		email.addTo(jobContext.getMailaddressee());
		// 发送人的邮箱
		email.setFrom(jobContext.getSender());
		// 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
		if (jobContext.getAnonymous().equals("false")) {
			email.setAuthentication(jobContext.getUsr(), jobContext.getPwd());
		}
		email.setSubject(title);
		// 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
		email.setMsg(body);
		
		 EmailAttachment  attachment = null;  
         if(attachPath!=null){  
             attachment = new EmailAttachment();  
             try {  
                 attachment.setPath(attachPath);  
                 attachment.setName(MimeUtility.encodeText(attachName));  
                 attachment.setDisposition(EmailAttachment.ATTACHMENT);  
                 attachment.setDescription("Picture of John");  
             }catch (UnsupportedEncodingException e) {  
                 e.printStackTrace();  
             }  
         }  

         if(attachPath!=null){  
             email.attach(attachment);  
         }  
		
		// 发送
		email.send();
	}
}
