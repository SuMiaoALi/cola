package com.baoyun.ins.config.constant;

public interface C {
	
	//用户
	interface USER {
		//用户状态
		interface STATUS {
			int NORMAL = 0;//正常
			int FORBIDDEN = 1;//禁止的
			int OBSOLETE = 2;//作废的
		}
		
		interface TYPE {
			String USER = "USER"; // 正式用户
			String TOURIST = "TOURIST"; // 游客
		}
		
		interface SSO {
			String WX = "wx";
			String DINGDING = "dd";
		}
		
		interface LOGIN {
			String WX = "wx";//微信小程序
			String TT = "tt";//头条小程序
			String WXTP = "wxtp";//微信公众号
			String WXQE = "wxqe";//微信问答小程序
			String TTQE = "ttqe";//头条问答小程序
		}
		
	}
	
	interface STATUS {
		String NORMAL = "0";//正常
		String FORBIDDEN = "1";//禁止的
		String OBSOLETE = "2";//作废的
	}
	

	
	

}
