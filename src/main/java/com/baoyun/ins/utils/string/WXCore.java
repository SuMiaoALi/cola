package com.baoyun.ins.utils.string;

import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONObject;
 
 
/**
 * 封装对外访问方法
 * @author liuyazhuang
 *
 */
public class WXCore {
	
	private static final String WATERMARK = "watermark";
	private static final String APPID = "appid";
	/**
	 * 解密数据
	 * @return
	 * @throws Exception
	 */
	public static JSONObject decrypt(String appId, String encryptedData, String sessionKey, String iv){
		JSONObject jsonObject = null;
		try {
			AES aes = new AES();  
		    byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));  
		    if(null != resultByte && resultByte.length > 0){  
		        String result = new String(WxPKCS7Encoder.decode(resultByte));  
		    	jsonObject = JSONObject.fromObject(result);
		    	String decryptAppid = jsonObject.getJSONObject(WATERMARK).getString(APPID);
		    	if(!appId.equals(decryptAppid)){
		    		result = null;
		    	}
	        }  
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return jsonObject;
	}
	
	
//	public static void main(String[] args) throws Exception{
//	   String appId = "wx01ec75d538698bd5";
//	   String encryptedData = "oqjdUIo1RTNZakhNIJTVHYi8bwyBDYrsldnM857EsQr8VvJDqyt314doJ7tnW9uB2MGp05/upobe9lmtaXsY/fR1/dsMXbu4zS/mjPWekGk0wroadsdU0+Fyo5J9dbK0EheIVZ9f1lVcKWwgO1aN88ouK0iE74PWyLW1Bd5O5LXsQFEq6T0jM/fFoWh5/ZvwXhmfOoJIbsVFZhEkwJIzTg==";
//	   String sessionKey = "GoSyo/c7ZIMZOC8oDCnrLw==";
//	   String iv = "+eKnQv8VTT5b+3P7ui9Qww==";
//       System.out.println(decrypt(appId, encryptedData, sessionKey, iv));
//    }
}