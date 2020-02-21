package com.baoyun.ins.utils.json;

public class GlobalReturnCode {
	
	/**
	 * 默认值
	 */
	public static final String SUCCESS = "10000";
	
	/**
     * 保存成功
     */
    public static final String SAVE_SUCCESS = "10001";
    /**
     * 删除成功
     */
    public static final String DELETE_SUCCESS = "10002";
    /**
     * 操作成功
     */
    public static final String OPERA_SUCCESS = "10003";
    /**
     * 审核成功
     */
    public static final String AUDIT_SUCCESS = "10004";

    /**
     * 操作失败
     */
    public static final String OPERA_FAILURE = "10005";
    
    /**
     * 验签失败
     */
    public static final String SIGNATURE_VALID_FAILE = "10006";
    
    /**
     * Redis失连
     */
    public static final String REDIS_ERROR = "10007";

    /**
     * 无权限
     */
    public static final String NO_AUTH = "30001";
    /**
     * 系统错误
     */
    public static final String SYSTEM_ERROR = "30002";
    /**
     * 参数错误
     */
    public static final String PARAM_ERROR = "30003";

    /**
     * 路径不存在
     */
    public static final String SYSTEM_PATH_NOEXIST = "30004";
    
    /**
     * 用户不存在
     */
    public static final String USER_NOT_EXIT = "5001";
    
    /**
     * 密码错误
     */
    public static final String SECRET_ERROR = "5002";
    
    /**
     * token错误
     */
    public static final String AUTH_ERROR = "5003";
    
    /**
     * token过期
     */
    public static final String AUTH_EXPIRED = "5004";
    
    //验证码不存在或过期
    public static final String AUTH_CODE_NOT_EXIT = "5005";
    //验证码错误
    public static final String AUTH_CODE_ERROR = "5006";
    //作用域错误
    public static final String AUTH_SCOPE_ERROR = "5007";
    //已绑定微信
    public static final String WX_HAS_BIND = "5008";
    //用户已经绑定
    public static final String AUTH_BINDED = "5009";
    //用户被禁用
    public static final String AUTH_DISABLE = "5010";
    // 已扫码
    public static final String QRCODE_SCAN = "5011";
    /**
     * 订单
     */
    public static final String ORDER_CAN_NOT_CANCEL = "6001";//订单不能取消
    public static final String ORDER_CAN_NOT_STOP = "6002";//订单不能中止
    public static final String ORDER_CAN_NOT_PAY = "6003";//订单不能支付

    /**
     * 财务订单
     */
    public static final String NO_PAY = "8000";
    public static final String FIN_WATE_PAY = "8002";//订单待支付
    public static final String FIN_PAIED = "8001";//订单已支付
    public static final String FIN_INSUFFICIENT_BALANCE = "8003";//余额不足
    public static final String FIN_ORDER_NOT_EXIT = "8004";//订单不存在
    public static final String FIN_NOT_NOVICE = "8005";//新用户奖励已领取
    public static final String FIN_ORDER_FAIL = "8006";//下单失败
    
    public static final String DEVICE_MEASURING = "9001";//测量中
    public static final String DEVICE_MEASURED = "9002";//测量完成
    public static final String DEVICE_NO_QUOTA = "9003";//次数不够
    public static final String DEVICE_MEASUREMENT = "9004";//开始测量

    public static final String SMS_NOT_EXIT = "10001";//验证码不存在
    public static final String SMS_CODE_ERROR = "10002";//验证码错误
    
    
    public static final String PROFILE_PHONE_EXIT = "11001";//手机号已存在
    public static final String PROFILE_PHONE_NOT_EXIT = "11002";//手机号不存在
    
    public static final String RESUME_SCORE_UNFULL ="12001";//简历评分不足

    }