package com.mingtai.base.util;

import com.alibaba.fastjson.JSONObject;
import com.mingtai.base.model.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HttpServletResponse帮助类
 */
public final class ResponseUtils {

	static Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

	/**
	 * 发送文本。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}

	/**
	 * 发送json。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}

	/**
	 * 发送json。使用UTF-8编码。
	 *
	 * @param response
	 *            HttpServletResponse
	 * @param success
	 * @param message
	 * @param apiResult
	 *            发送的字符串
	 */
	public static void renderJson(HttpServletResponse response, Boolean success, String message,  ApiResult apiResult) {
		apiResult.setSuccess(success);
		apiResult.setMessage(message);
		String text = JSONObject.toJSONString(apiResult);
		render(response, "application/json;charset=UTF-8", text);
	}
	/**
	 * 发送xml。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}

	/**
	 * 发送内容。使用UTF-8编码。
	 * 
	 * @param response
	 * @param contentType
	 * @param text
	 */
	public static void render(HttpServletResponse response, String contentType,
			String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static void renderApiJsonp(HttpServletResponse response,
									  HttpServletRequest request, String apiResult) {
		//js跨域请求
		String callback = request.getParameter("callback");
		if(!StringUtils.isBlank(callback)){
			ResponseUtils.renderJson(response,callback+"(" + apiResult + ")" );
		}else{
			ResponseUtils.renderJson(response, apiResult);
		}
	}
}
