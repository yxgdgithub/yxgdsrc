package com.mj.mmanage.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mj.webchat.share.SignatureBean;
import com.mj.webchat.share.Ticket;
import com.mj.webchat.util.AccessTokenUtil;

/**
 * 微信分享
 * 
 * @author lhl_fighting
 *
 */
@RestController
@RequestMapping("/WebChatShareController")
public class WebChatShareController {
	
	private static Logger logger = Logger.getLogger(WebChatShareController.class);

	@RequestMapping(value = "/getSignature")
	public String getSignature(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

			// 获取签名页面链接
			String url = request.getParameter("url");
			
			logger.info("url-->"+url);
			
			Ticket oldticket = AccessTokenUtil.JSAPI_TICKET;
			
			if (oldticket != null) {
				// 标签未超时
				/**
				 * 注意事项
				 * 1.签名用的noncestr和timestamp必须与wx.config中的nonceStr和timestamp相同。
				 * 2.签名用的url必须是调用JS接口页面的完整URL。 
				 * 3.出于安全考虑，开发者必须在服务器端实现签名的逻辑。
				 * 
				 * 根据第1点要求 signature 配置的时候很容易出错，需要把生成 Ticket的 noncestr和timestamp传给客户端
				 */
				logger.info("生成签名Json字符串");
				String signature = signature(oldticket.getTicket(), oldticket.getTimestamp(), oldticket.getNoncestr(), url);
				SignatureBean signatureBean = new SignatureBean();
				signatureBean.setNoncestr(oldticket.getNoncestr());
				signatureBean.setSignature(signature);
				signatureBean.setTimestamp(oldticket.getTimestamp());
				signatureBean.setUrl(url);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(new Gson().toJson(signatureBean));
			}
			else {
				logger.error("生成签名Json字符串时获取JSAPI_tICKET异常!");
			}
			return null;
	}

	/**
	 * 
	 * <p>
	 * Project:mryl_phone_v2
	 * </p>
	 * 
	 * <p>
	 * :mryl_phone_v2
	 * </p>
	 * 
	 * <p>
	 * Description:根据标签,时间戳,密匙,URL进行签名
	 * </p>
	 *
	 * <p>
	 * Company:Wiimedia
	 * </p>
	 *
	 * @Athor:SongJia
	 *
	 * @Date:2016-7-15 上午09:37:13
	 *
	 */
	private String signature(String jsapi_ticket, String timestamp, String noncestr, String url) {
		
		jsapi_ticket = "jsapi_ticket=" + jsapi_ticket;
		timestamp = "timestamp=" + timestamp;
		noncestr = "noncestr=" + noncestr;
		url = "url=" + url;
		String[] arr = new String[] { jsapi_ticket, timestamp, noncestr, url };
		
		// 将token、timestamp、nonce,url参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
			if (i != arr.length - 1) {
				content.append("&");
			}
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		return tmpStr;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {

		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

}
