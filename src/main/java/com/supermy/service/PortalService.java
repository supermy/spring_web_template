package com.supermy.service;


import com.supermy.utils.JsonUtil;
import com.supermy.utils.Pages;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/*
 * 功能业务控制类
 * 
 */
@Repository
public class PortalService {

	private final Logger logger = LoggerFactory
			.getLogger(PortalService.class);

	/**
	 * 库存管理管理
	 * 
	 * @param id
	 * @return
	 */
	public Map delStock(Integer id) {
		try {
            LinkedHashMap<String, Object> obj = gendata(id);
            //portalMapper.delStock(id);
			return JsonUtil.getJsonResult("1", "success", obj);
		} catch (Exception e) {
			e.printStackTrace();
            return JsonUtil.getJsonResult("-1", e.getMessage());
        }
	}

	public Map updateStock(Map stock) {
		try {
			//portalMapper.updateStock(stock);
			return JsonUtil.getJsonResult("1", "success", stock);
		} catch (Exception e) {
			e.printStackTrace();
            return JsonUtil.getJsonResult("-1", e.getMessage());

        }
	}

	public Map getStock(Integer id) {
		try {
			Map stock = gendata(id);
			//portalMapper.getStock(id);
			return JsonUtil.getJsonResult("1", "success", stock);
		} catch (Exception e) {
			e.printStackTrace();
            return JsonUtil.getJsonResult("-1", e.getMessage());
        }
	}

	public Map createStock(Map stock) {
		try {
			//portalMapper.createStock(stock);
			Map result = gendata(2);
			//portalMapper.getStock(new Integer(stock.get("id").toString()));
			return JsonUtil.getJsonResult("1", "success", result);
		} catch (Exception e) {
			e.printStackTrace();
            return JsonUtil.getJsonResult("-1", e.getMessage());
        }
	}

	public Map queryStocks(Map<String, Object> params) {

		if (params.get("export").equals("1")) {
			Pages pages = (Pages) params.get("page");
			pages.setPageSize(99999);
			pages.setCurrentPage(0);
			try {
				List<LinkedHashMap<String, Object>> result = genlist();
				//portalMapper.queryStocksByPage(params);
				// todo 导出方法
				Object result1 = "";
				return JsonUtil.getJsonResult("1", "success", result1, params);
			} catch (Exception e) {
				e.printStackTrace();
                return JsonUtil.getJsonResult("-1", e.getMessage());
            }

		} else {

			try {
				List<LinkedHashMap<String, Object>> result =genlist()  ;
				//portalMapper.queryStocksByPage(params);
				return JsonUtil.getJsonResult("1", "success", result, params);
			} catch (Exception e) {
				e.printStackTrace();
                return JsonUtil.getJsonResult("-1", e.getMessage());
            }
		}
	}


    static List<LinkedHashMap<String, Object>> genlist(){
        List<LinkedHashMap<String, Object>> result=new ArrayList<LinkedHashMap<String, Object>>();
        for (int i = 0; i < 10; i++) {
            LinkedHashMap<String, Object> obj =new LinkedHashMap<String, Object>();
            obj.put("id", i+"");
            obj.put("name", "hello"+i);
            result.add(obj);
        }
        return result;
    }

    static LinkedHashMap<String, Object> gendata(Integer id){
        LinkedHashMap<String, Object> result =new LinkedHashMap<String, Object>();
        result.put("id", id);
        result.put("name", "hello"+id);
        return result;
    }
}