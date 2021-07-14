package com.atguigu.scw.project.component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@ToString
@Slf4j
@Component
public class OssTemplate {
	
	// yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
	// 改为配置类注入失败
	@Value("${oss.endpoint}") 
	String endpoint;
	
	// 阿里云账号AccessKey拥有所有API的访问权限。
	@Value("${oss.accessKeyId}") 
	String accessKeyId;
	
	@Value("${oss.accessKeySecret}") 
	String accessKeySecret;
	
	// 水桶
	@Value("${oss.bucket}") 
	String bucket;

	public String upload(InputStream inputStream, String fileName) {
		
		log.debug("endpoint={}",endpoint);
		log.debug("accessKeyId={}",accessKeyId);
		log.debug("accessKeySecret={}",accessKeySecret);
		log.debug("bucket={}",bucket);
		
		try {
			// 创建OSSClient实例。
			OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
			
			// 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
			ossClient.putObject(bucket, "pic/"+fileName, inputStream);

			// 关闭OSSClient。
			ossClient.shutdown();
			
			// 返回该文件的统一资源定位符https://scw20210626.oss-cn-beijing.aliyuncs.com/c.jpg
			// oss.endpoint=oss-cn-beijing.aliyuncs.com
			String filePath = "https://" + bucket + "." + endpoint + "/pic/" + fileName;
			
			log.debug("文件上传成功-{}",filePath);
			return filePath;
		} catch (Exception e) {
			
			e.printStackTrace();
			log.debug("文件上传失败-{}",fileName);
			
			return null;
		}
	}

}
