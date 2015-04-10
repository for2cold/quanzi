package com.jasme.quanzi.api.circle.web.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jasme.quanzi.api.circle.request.view.CreateView;
import com.jasme.quanzi.api.circle.request.view.ListView;
import com.jasme.quanzi.api.circle.response.view.CircleItem;
import com.jasme.quanzi.api.circle.service.CircleService;
import com.jasme.quanzi.api.userinfo.web.bind.annotation.CurrentUser;
import com.jasme.quanzi.core.component.circle.entity.Circle;
import com.jasme.quanzi.core.component.user.entity.User;
import com.jasme.swiiket.common.web.controller.BaseApiController;

/**
 * 圈子管理
 * 
 * <p>User: Jasme
 * <p>Date: 2015年3月30日 上午10:13:22
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/api/circle")
public class CircleController extends BaseApiController<Circle, Long> {

	private CircleService circleService;

	@Autowired
	public void setCircleService(CircleService circleService) {
		this.circleService = circleService;
	}
	
	/**
	 * 获取所有圈子
	 * 
	 * @param view
	 * @param result
	 * @param user
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(@Valid ListView view, BindingResult result, @CurrentUser User user) {
		
		if (result.hasErrors()) {
			return responseFiledError(result);
		}
		
		List<CircleItem> list = circleService.findList(user, view);
		
		return responseSuccessData(list);
	}
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	}
	
	/**
	 * 创建圈子
	 * 
	 * @param view
	 * @param result
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public Object create(@Valid CreateView view, BindingResult result, @CurrentUser User user) {
		
		if (result.hasErrors()) {
			return responseFiledError(result);
		}
		
		circleService.create(user, view);
		
		return null;
	}
}
