package com.thehuxley.data;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	private static ObjectMapper mapper = new ObjectMapper();

	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error(
					"Error while trying to serialize an object from class: "
							+ obj.getClass() + " as Json", e);
			return null;
		}

	}

	public static <T> T fromJson(String json, Class<T> classType) {
		try {
			return mapper.readValue(json, classType);
		} catch (IOException e) {
			logger.error(
					"Error while trying create an object from a json. Class: "
							+ classType + " as Json", e);
			return null;
		}
	}

}
