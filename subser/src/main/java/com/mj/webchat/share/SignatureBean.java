package com.mj.webchat.share;

import com.mj.mmanage.util.Constants;

public class SignatureBean {
	 private String noncestr;
	 private String url;
	 private String timestamp;
	 private String signature;
	 private String appId = Constants.APPID;
	 
	 public String getAppId() {
		return appId;
	}
	public String getNoncestr() {
	 return noncestr;
	 }
	 public void setNoncestr(String noncestr) {
	 this.noncestr = noncestr;
	 }
	 public String getUrl() {
	 return url;
	 }
	 public void setUrl(String url) {
	 this.url = url;
	 }
	 public String getTimestamp() {
	 return timestamp;
	 }
	 public void setTimestamp(String timestamp) {
	 this.timestamp = timestamp;
	 }
	 public String getSignature() {
	 return signature;
	 }
	 public void setSignature(String signature) {
	 this.signature = signature;
	 }
}