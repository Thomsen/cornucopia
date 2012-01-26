package com.cornucopia.service.server;

/**
*	aidl接口
*/

interface IRemoteService {
	
	/**
	*	得到该服务进程ID
	*/
	int getPid();
	
	/**
	* 使用的基本的类型作为方法的参数
	*/
	void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
				double aDouble, String aString);
				
}