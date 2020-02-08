package com.baoyun.ins.utils.ali;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//@Component
//public class AliUtils {
//	// 阿里访问权限
//	@Value("${ali.accessKeyId}")
//	private String aliAccessKeyId;
//	
//	@Value("${ali.accessKeySecret}")
//	private String aliAccessKeySecret;
//	
//	@Value("${ali.oss.bucket}")
//	private String bucket;
//	
//	@Value("${ali.endpoint}")
//	private String endPoint;
//	
//	// 头像上传阿里云oss  
//		public String uploadAliCloud(String path, String url) {
//			
//			// 创建OSSClient实例。
//			OSS ossClient = new OSSClientBuilder().build(endPoint, aliAccessKeyId, aliAccessKeySecret);
//			
//			InputStream is = null;
//			// 上传网络流。
//			try {
//				is = new URL(url).openStream();
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			ossClient.putObject(bucket, path, is);
//			
//			// 关闭OSSClient。
//			ossClient.shutdown();
//			 
//			return "http://img.51rry.com/" + path;
//			
//		}
//}
