package com.cornucopia.service;

/**
 *	aidl interface
 *  maven generate need src/main/java
 */

interface IRemoteService {
	
	/**
	*	process id
	*/
	int getPid();
	
	/**
	* basic type
	*/
	void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
				double aDouble, String aString);
				
}