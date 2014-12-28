package com.supermy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


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
 *         校园版自然人控制类
 *
 */
@Controller
public class SampleController {

	@RequestMapping("home")
	public String loadHomePage(Model m) {
		m.addAttribute("name", "JamesMo......");
		return "home";
	}

    @RequestMapping("/")
    public String loadHomePage1(Model m) {
        m.addAttribute("name", "JamesMo......");
        return "index";
    }

}
