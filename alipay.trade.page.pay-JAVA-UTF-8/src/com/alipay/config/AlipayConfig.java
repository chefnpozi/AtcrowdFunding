package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000117686693";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCfUb4WCdtLpgB3+PMGVyTwb9XlNCodc/v7/vkbEuHzIjNcNwfE3UKQLGdJRiyUxi+okq8XIMRND3wv0o0mqDfYHhQB4y7FGDPrVJIKL5r+Lpa/MrckRbrfKSxrFn1lxITJ/lm2JHMgurL9HV7eS/f5UU/Xn5ewO/8YulDCq5yZM2nGl5G8cz4hx2F+rKIeFiLETdx1aOgDxon7hO193w5CuB9WTGBI/vAmJLGBMLPGE/jeF17AM8MIp+ViTtsWFpb8axvsuOcJnkNt0DWh2dIcje8z+dJXXmlix5RQ/a3CD/muMTRX0Ush4S2nZN55Zt+rtzTHrWQj4pVF4eCQALX/AgMBAAECggEARE2mQgqNwvvzXXwUrNY1lwNLX42itKfiE2J359yLlFJIVLOfGA6IGAmBao+0U0A4dHwWpsK8hhEqe9I4A6fA8V3L1BdTGNrMf4rtZYKNX4YXyPb0P4/Jkx0y1uJ4dmXvTC5IxecQmKqSTCQIZSOKt4/dvCLGxJ6kOjS+pQcwBxmuZ9SVWs7sNZo2oVLrm84I3oePaRanVnZ9g7t7qhKSbib4LcsM5LXxG41qhGAFC12FC2r8PchaArv8zA4PmONwgmeeeLfmsdzEVNEmT+q2h1aWLZxGyosXHn/oLEHFz00awP+tJPIWuTIEDJfLHIDNuVoghLJPaxMniGXYavhbaQKBgQDiVJ+r7qpYBfHJanoRj0yIusADCtJqvPV1YlgoPelRvhTBV2zPVTPE81gASd8H6k7i2qMYwM+uAQGVnmqRuJVXnKgoZiyQJ/q6N+mbb/eMs1wC3TEFpUFOAT/77LAnEh22TlRdIf8Wz2Zgimo+BmftqUx8xVUpOT2gMxR0RH/TIwKBgQC0NE2fx617mh9EDwtsVXG8QRMMPgrKh7x644g8M6qnnowKC9rUyb8bZ3CakTvF53awTgClJiLIjIUIRLxfiJLY0Zsgfu/XDVdrIPxp4IdmcFr+AQZVZzqjxYtJ0ISmGjP5K9CmAOs3yCndBmu/A8/BgjRArh8VQ+S0GIVYY3DddQKBgFPcXSlcjlt9IGCsBjzZZtdGsvytlteb2is7XETU482464HT/eo4vKzeKZ45wNzy8XXW4xW543mqv8aLYxtJVHSZBCmr6hkj5x/2taU1cHj+wDoDcTWRTMg1aFrVEVP4v2+cSGAVx6nRATMGNmVzBDEoSMH2cB6NKgtgwdRuyRoXAoGBAKstW+dBWEwnNTgDSoiYCbgX4K4qaTnGAoaBiLIXKi6bbs9EP5UOJ5kyg0ZwW2U84APNtNMsDLKQuPYHxSDCrSuEwGT374BKzhWXee5hOH1YkTbjZk7OBmf3iL6PctIN5fz1X/ewu797RjkMlmEWj81zj4s5W5FpsuBYwqIZo+bZAoGBAJwZJROF3WjqA+2KBAmt5wfF5XThvawaSI30U/OeCZt4lwhdEl2IsQPQGunZm7XhyVcvgcT3K92UQEWqcY51RnGr9NGgvHf+EZkd84FJak6xYg7IEcUEzT57FsxO7nOvAgMJTdaG0QyDp+sDqKnXgeThoSctEiwuml8kMYi+A/G3";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsS6rA65iCcAfyNwAetAlfPJoobZHtZ/o0RqWhHgKalO0Yf5qVTi4CbH2MnOO4Nos9Jn769HqYQp8FKukWbIuNM5HltW7Rt7WvU7BlIpkskzmGSfGJK3KZx46+gkWNhZ/f4wD2Ufm2VcmqP/6bXDu+es6tmaKRbPNagcGMcSZ3cfQVP6J9ZaQJjwJkSTdPiBAzBjFibfT0VjbGddU2QTqSoSiPOo+sfPyUEX/Q0aGB5j3jSyp5itENuY6Obf7F3X9R2MYysqQCJ5RhxuO4wDNnk6SX9qsxmnqRplBGJuturdjWSjdw1Ir+WM1tEFwnjVZG7FM6xTV9bvZJO+aohOx4QIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://scw20210710.free.idcfengye.com/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 穿透的域名 http://scw20210710.free.idcfengye.com
	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://scw20210710.free.idcfengye.com/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关 https://openapi.alipaydev.com/gateway.do 多了dev，沙箱环境使用
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

