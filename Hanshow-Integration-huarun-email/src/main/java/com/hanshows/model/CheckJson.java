package com.hanshows.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckJson {
	
	private String storeCode;
	private String customerStoreCode;
	private String batchSize;
	private String batchNo;
	private String callbackUrl;
	private List<HashMap<String, Object>> items;
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getCustomerStoreCode() {
		return customerStoreCode;
	}
	public void setCustomerStoreCode(String customerStoreCode) {
		this.customerStoreCode = customerStoreCode;
	}
	public String getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(String batchSize) {
		this.batchSize = batchSize;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public List<HashMap<String, Object>> getItems() {
		return items;
	}
	public void setItems(List<HashMap<String, Object>> items) {
		this.items = items;
	}
	
	

}
