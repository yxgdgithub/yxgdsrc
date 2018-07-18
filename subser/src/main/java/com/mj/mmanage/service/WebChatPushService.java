package com.mj.mmanage.service;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import com.mj.webchat.share.HttpsUtil;

public class WebChatPushService implements Runnable {
	
	private static Logger logger = Logger.getLogger(WebChatPushService.class);

	private JSONObject jsonObject;
	private String templateURL;
	
	@Override
	public void run() {
		
		String result = HttpsUtil.httpsRequestToString(templateURL, "POST", jsonObject.toString());
        try {
        	JSONObject resultJson = JSON.parseObject(result);
            String errmsg = (String) resultJson.get("errmsg");
    		logger.info("errmsg->"+ errmsg);	
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
//
//	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr){
//        try {
//            URL url = new URL(requestUrl);
//            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setUseCaches(false);
//            // 设置请求方式（GET/POST）
//            conn.setRequestMethod(requestMethod);
//            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
//            // 当outputStr不为null时向输出流写数据
//            if (null != outputStr) {
//                OutputStream outputStream = conn.getOutputStream();
//                // 注意编码格式
//                outputStream.write(outputStr.getBytes("UTF-8"));
//                outputStream.close();
//            }
//            // 从输入流读取返回内容
//            InputStream inputStream = conn.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String str = null;
//            StringBuffer buffer = new StringBuffer();
//            while ((str = bufferedReader.readLine()) != null) {
//                buffer.append(str);
//            }
//            // 释放资源
//            bufferedReader.close();
//            inputStreamReader.close();
//            inputStream.close();
//            inputStream = null;
//            conn.disconnect();
//            return buffer.toString();
//        } catch (ConnectException ce) {
//            System.out.println("连接超时：{}");
//        } catch (Exception e) {
//            System.out.println("https请求异常：{}");
//        }
//        return null;
//    }

	public String getTemplateURL() {
		return templateURL;
	}

	public void setTemplateURL(String templateURL) {
		this.templateURL = templateURL;
	}
}
