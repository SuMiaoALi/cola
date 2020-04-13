package com.baoyun.ins.utils.ali;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baoyun.ins.utils.json.GlobalReturnCode;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.redis.RedisConstant;
import com.baoyun.ins.utils.redis.RedisUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 阿里短信服务工具类
 * @Author cola
 * @Date 2020年2月7日
 */

@Slf4j
@Component
public class SmsUtil {
	
	// 短信模板id
	@Value("${ali.templateCode}")
	private String templateCode;
	
	// 短信签名
	@Value("${ali.signName}")
	private String signName;
	
	// 阿里keyid
	@Value("${ali.accessKeyId}")
	private String accessKeyId;

	// 阿里keysecret
	@Value("${ali.accessKeySecret}")
	private String accessKeySecret;
	
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * @Description: sendSms
	 * @Author cola
	 * @Data: 2020年2月7日
	 * @param phone
	 * @param type
	 * @return
	 */
	public Msg<?> sendSms(String phone) {
		Msg<Object> msg = new Msg<>();
		// 验证码已存在
		if (redisUtil.exists(RedisConstant.SMS + phone)) {
			msg.setMessage(redisUtil.get(RedisConstant.SMS + phone, String.class));
			msg.setCode("10000");
			return msg;
		}
		// 验证码
		String code = generateVerifyCode();
		
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		
		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("RegionId", "cn-hangzhou");
		request.putQueryParameter("PhoneNumbers", phone);
		request.putQueryParameter("SignName", signName);
		request.putQueryParameter("TemplateCode", templateCode);
		request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
		
		try {
			msg.setMessage(code);
			CommonResponse response = client.getCommonResponse(request);
			log.info("短信发送结果:" + response.getData());
			// 解析jsonstr
			JSONObject json = JSONObject.parseObject(response.getData());
			// 发送成功
			if ("OK".equals(json.getString("Code"))) {
				redisUtil.set(RedisConstant.SMS + phone, code); //code有效期30分钟
			} else {
				msg.setCode(GlobalReturnCode.OPERA_FAILURE);
    			msg.setMessage(json.getString("失败"));
			}
		}
		catch(Exception e) {
			System.out.println("异常");
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * @Description: 生成验证码
	 * @Author cola
	 * @Data: 2020年2月7日
	 * @return
	 */
	public String generateVerifyCode() {
		String sources = "0123456789";
		int verifySize = 6;
		int codesLen = sources.length();
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder verifyCode = new StringBuilder(verifySize);
		for (int i = 0; i < verifySize; i++) {
			verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
		}
		return verifyCode.toString();
	}
}
