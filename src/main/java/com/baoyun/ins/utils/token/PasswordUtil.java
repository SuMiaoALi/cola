package com.baoyun.ins.utils.token;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 密码生产工具
 * @ClassName: PasswordUtil 
 * @Description: TODO
 * @author louis
 * @date 2018年3月29日 下午4:09:24
 */
public class PasswordUtil {
	
	/**
	 * 生成加密盐值
	 * @Description: TODO
	 * @author louis
	 * @date 2018年3月29日 下午4:09:38
	 * @return
	 * @throws
	 */
	public static String salt() {
		return new SecureRandomNumberGenerator().nextBytes().toHex();
	}
	
	/**
	 * 密码加密
	 * @Description: TODO
	 * @author louis
	 * @date 2018年3月29日 下午4:11:11
	 * @param password
	 * @param salt
	 * @return
	 * @throws
	 */
	public static String hex(String password,String salt) {
		return new Md5Hash(password, salt, 2).toHex();
	}

}
