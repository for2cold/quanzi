package com.jasme.quanzi.api.circle.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasme.quanzi.api.circle.exception.CircleApiException;
import com.jasme.quanzi.api.circle.repository.CircleRepository;
import com.jasme.quanzi.api.circle.request.view.CreateView;
import com.jasme.quanzi.api.circle.request.view.ListView;
import com.jasme.quanzi.api.circle.response.view.CircleItem;
import com.jasme.quanzi.core.component.circle.entity.Circle;
import com.jasme.quanzi.core.component.circle.entity.CircleRelation;
import com.jasme.quanzi.core.component.circle.enums.CircleStatus;
import com.jasme.quanzi.core.component.circle.enums.CircleType;
import com.jasme.quanzi.core.component.user.entity.User;
import com.jasme.swiiket.common.search.Searchable;
import com.jasme.swiiket.common.service.BaseService;

/**
 * <p>User: Jasme
 * <p>Date: 2015年3月30日 下午2:37:10
 * <p>Version: 1.0
 */
@Service
public class CircleService extends BaseService<Circle, Long> {

	private CircleRepository circleRepository;
	private CircleRelationService circleRelationService;

	@Autowired
	public void setCircleRepository(CircleRepository circleRepository) {
		this.circleRepository = circleRepository;
	}
	
	@Autowired
	public void setCircleRelationService(CircleRelationService circleRelationService) {
		this.circleRelationService = circleRelationService;
	}

	public List<CircleItem> findList(User user, ListView view) {
		// TODO Auto-generated method stub
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("user.id_eq", user.getId())
						.addSearchParam("circle.type_eq", view.getType())
						.addSearchParam("circle.status_eq", CircleStatus.NORMAL);
		List<CircleRelation> datas = circleRelationService.findAll(searchable).getContent();
		
		List<CircleItem> list = new ArrayList<>();
		
		for (CircleRelation cr : datas) {
			
			CircleItem item = new CircleItem();
			item.setName(cr.getCircle().getName());
			item.setStatus(cr.getCircle().getStatus());
			item.setType(cr.getCircle().getType());
			item.setCreateDate(cr.getCircle().getCreateDate());
			item.setId(cr.getCircle().getId());
			
			list.add(item);
		}
		
		return list;
	}

	public void create(User user, CreateView view) {
		// TODO Auto-generated method stub
		// 同名检测
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("name_eq", view.getName())
						.addSearchParam("user.id_eq", user.getId());
		
		if (circleRepository.count(searchable) > 0) {
			
			throw new CircleApiException("circle.duplicate.name.error");
		}
		
		Circle circle = new Circle();
		Date createDate = new Date();
		circle.setName(view.getName());
		circle.setCreateDate(createDate);
		circle.setType(view.getType());
		circle.setUser(user);
		circle.setStatus(CircleStatus.NORMAL);
		circleRepository.save(circle);
		
	}
	
}
