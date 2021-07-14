package com.atguigu.scw.project.component;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.atguigu.scw.vo.resp.AppResponse;


@Component
public class SmsTemplate {
	
	// 将appcode等信息存在属性文件中
	@Value("${sms.host}")
	String host;
	
	@Value("${sms.path}")
	String path;
	
	@Value("${sms.method}")
	String method;
	
	@Value("${sms.appcode}")
	String appcode;

	public AppResponse<String> sendSms(Map<String, String> querys) {
		
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		
		Map<String, String> bodys = new HashMap<String, String>();

		try {
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			System.out.println(response.toString());
			
			return AppResponse.ok(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return AppResponse.fail(null);
		}
	}
	
}
