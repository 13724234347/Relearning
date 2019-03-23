 package com.sojson.order.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sojson.common.controller.BaseController;
import com.sojson.common.model.Page;
import com.sojson.common.model.UOrder;
import com.sojson.order.service.UOrderService;

@Controller
@Scope(value="prototype")
@RequestMapping("order")
public class OrderController extends BaseController {
	@Autowired
	UOrderService uOrderService;
	/**
	 * 
	 * @return
	 */
	//orderList
	@RequestMapping(value="orderCRUD/list", method=RequestMethod.GET)
	public String index(){
	return "order/orderList";
	}
	/**
	 * 分页的形式查询user表的数据
	 * @return List<User>
	 */
	@RequestMapping(value="/orderCRUD", method=RequestMethod.GET)
	@ResponseBody
	public Page<UOrder> query(UOrder uorder, Page<UOrder> page){
		page.setT(uorder);
		System.out.println(page.toString());
		uOrderService.query(page);
		return page;
	}
	/**
	 * 
	 * @return
	 */
	//跳转添加页面
	@RequestMapping(value="/orderCRUD/add", method=RequestMethod.GET)
	public String addjsp(Map<String, Object> map){
		map.put("order", new UOrder());
	return "order/orderAdd";
	}
	//orderAdd
	@RequestMapping(value="/orderCRUD",method=RequestMethod.POST)
	public String  insert(UOrder uorder){
			uorder.setOrderTime(new Date());
			uOrderService.insert(uorder);
		return "order/orderList";
		
	}
	/**
	 * 删除用户
	 * @return String
	 */
	//orderDel
	@RequestMapping(value="/orderCRUD",method=RequestMethod.DELETE)
	@ResponseBody
	public void delete(Integer id){
		uOrderService.delete(id);
	}
	/**
	 * 跳转修改
	 * @return String
	 */
	//orderupjsp
	@RequestMapping(value="/orderCRUD/{id}",method=RequestMethod.GET)
	public String UpdateJsp(@PathVariable Integer id, Map<String, Object> map, UOrder uorder, Page<UOrder> page){
		page.setT(uorder);
		map.put("page", page);
		map.put("order", uOrderService.getById(id));//根据id获取对象放入request中
		return "order/orderUpdate";
	}
	/**
	 * 保存修改
	 * @return String
	 */
	//orderUpdate
	@RequestMapping(value="/orderCRUD",method=RequestMethod.PUT)
	public String saveUpdate(@ModelAttribute("order") UOrder uorder,Page<UOrder> page, Map<String, Object> map){
		page.setT(uorder);
		map.put("page", page);
		uorder.setOrderTime(new Date());
		uOrderService.update(uorder);
		return "order/orderList";
	}
}
