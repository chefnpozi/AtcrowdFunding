package com.atguigu.scw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

public class OssTest {

	@Test
	public void test() throws FileNotFoundException {
		// yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
		String endpoint = "https://oss-cn-beijing.aliyuncs.com";
		// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
		String accessKeyId = "LTAI5tNWuQsuSjSCjpvtAV1K";
		String accessKeySecret = "Jjfyh9A4Cyoxm4daWMXqZCNALqlKbT";

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		// 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
		InputStream inputStream = new FileInputStream("E:\\Project\\lady\\c.jpg");
		// 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
		ossClient.putObject("scw20210626", "c.jpg", inputStream);

		// 关闭OSSClient。
		ossClient.shutdown();
	}

}
