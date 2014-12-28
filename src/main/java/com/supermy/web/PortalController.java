package com.supermy.web;


import com.supermy.service.PortalService;
import com.supermy.utils.MyFilter;
import com.supermy.utils.MySort;
import com.supermy.utils.Pages;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 标准的rest方法列表
 * 
 * <pre>
 * /menu        GET     => index()
 * /menu        POST    => create()
 * /menu/{id}   GET     => show()
 * /menu/{id}   PUT     => update()
 * /menu/{id}   DELETE  => delete()
 * 
 * /menu/list       POST    => batchCreate()
 * /menu/list       PUT     => batchUpdate()
 * /menu/list/del   PUT     => batchDelete()
 * </pre>
 * 
 * @author james mo rest 客户端
 *
 */
@Controller
//@RequestMapping("test")
public class PortalController {

	private final Logger logger = LoggerFactory
			.getLogger(PortalController.class);

	@Autowired
	public PortalService portalService;
    
	@Autowired
	public HttpServletRequest request;

	/**
	 * 信息管理 GET http://127.0.0.1:8080/stocks?page=2&limit=2
	 */
	@RequestMapping(value = "/stocks", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public @ResponseBody
    Map queryStocks(
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "limit", defaultValue = "20") int limit,
			@RequestParam(value = "page", defaultValue = "1") int curPage,
			@RequestParam(value = "export", defaultValue = "0") int export,
			@RequestParam(value = "sort", defaultValue = "") MySort sort,
			@RequestParam(value = "filter", defaultValue = "") MyFilter filter
	// @RequestParam(value = "order", defaultValue =
	// "[{\"property\":\"name\",\"direction\":\"ASC\"},{\"property\":\"code\",\"direction\":\"DESC\"}]")
	// MySort sort,
	// @RequestParam(value = "filter", defaultValue =
	// "[{\"type\":\"date\",\"comparison\":\"gt\",\"value\":\"2014-06-07\",\"field\":\"cdate\"},{\"type\":\"date\",\"comparison\":\"lt\",\"value\":\"2014-06-09\",\"field\":\"udate\"},{\"type\":\"string\",\"value\":\"快卡\",\"field\":\"name\"}]")
	// MyFilter filter
	) {

		Map<String, Object> params = new HashMap<String, Object>();
		Pages pages = new Pages(curPage, limit);

		params.put("page", pages);
		params.put("sort", sort.getSort());
		params.put("filter", filter.getFilter());

		params.put("export", export);

		logger.info("stocka codes:{$1}", params);

		return portalService.queryStocks(params);
	}

	/**
	 * create POST http://127.0.0.1:8080/stocks
	 * 
	 * @param stock
	 * @return
	 * 
	 */
	@RequestMapping(value = "/stocks", method = RequestMethod.POST, consumes = "application/json")
	@Transactional(readOnly = false)
	public @ResponseBody
    Map createStocks(@RequestBody Map stock) {
		System.out.println("create Contact called" + stock);
		logger.info("create Product called", stock);

		return portalService.createStock(stock);
	}

	/**
	 * show GET http://127.0.0.1:8080/stocks/13
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/stocks/{id}", method = RequestMethod.GET)
	public @ResponseBody
    Map getStock(@PathVariable Integer id) {
		logger.info("get stock called");
		return portalService.getStock(id);
	}

	/**
	 * update PUT http://127.0.0.1:8080/stocks/13
	 * 
	 * @param stock
	 * @return
	 * 
	 */
	@RequestMapping(value = "/stocks/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody
    Map updateStock(@RequestBody Map stock, @PathVariable Integer id) {
		logger.info("update stock called");
		stock.put("id", id);
		return portalService.updateStock(stock);
	}

	/**
	 * 删除 DELETE http://127.0.0.1:8080/stocks/13
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/stocks/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
    Map deleteStock(@PathVariable Integer id) {
		logger.info("delete stocka code called" + id);
		return portalService.delStock(id);

	}

}
