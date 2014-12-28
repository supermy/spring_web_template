package com.supermy.utils;


import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class JsonUtil {

	public static Map getJsonResult(String STATE, String STATE_INFO,
			Object... object) {
		Map<String, Object> jsonMap = new Hashtable<String, Object>();
		Map<String, String> stateMap = new HashMap<String, String>();
		if ("1".equalsIgnoreCase(STATE)) {
			jsonMap.put("success", true);
		} else
			jsonMap.put("success", false);

		stateMap.put("STATE", STATE);
		stateMap.put("STATE_INFO", STATE_INFO);
		jsonMap.put("STATE", stateMap);

		if (object.length == 1) {
			if (null == object[0]) {// 单条数据不存在
				jsonMap.put("DATA", new HashMap());
			} else
				jsonMap.put("DATA", object[0]);
		}

		if (object.length > 1) {
			jsonMap.put("DATA", object[0]);

			Map obj = (Map) object[1];
			Pages pages = (Pages) obj.get("page");
			jsonMap.put("totalCount", pages.getTotalCount());
			jsonMap.put("page", pages);
			if (obj.get("export") != null) {
				jsonMap.put("export", obj.get("export"));
			}
		}

		return jsonMap;

	}

	public static Map getJsonResult(String STATE, String STATE_INFO) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> stateMap = new HashMap<String, String>();
		stateMap.put("STATE", STATE);
		stateMap.put("STATE_INFO", STATE_INFO);
		jsonMap.put("STATE", stateMap);
		return jsonMap;

	}

}
