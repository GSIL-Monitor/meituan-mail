package com.hanshows.model;

public class HttpResponse {
	private int status;
	
	private String content;

	public HttpResponse(){
		
	}
	
	public HttpResponse(int status, String content){
		this.status=status;
		this.content=content;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
