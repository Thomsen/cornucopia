package com.cornucopia.parser.jaskson;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JasksonUtil {
	
	private static ObjectMapper mapper;
	
	public static ObjectMapper getMapper() {
		if (null == mapper) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}

}
