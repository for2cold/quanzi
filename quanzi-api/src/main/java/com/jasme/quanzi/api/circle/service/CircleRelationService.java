package com.jasme.quanzi.api.circle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasme.quanzi.api.circle.repository.CircleRelationRepository;
import com.jasme.quanzi.core.component.circle.entity.CircleRelation;
import com.jasme.swiiket.common.service.BaseService;

@Service
public class CircleRelationService extends BaseService<CircleRelation, Long> {

	private CircleRelationRepository circleRelationRepository;

	@Autowired
	public void setCircleRelationRepository(CircleRelationRepository circleRelationRepository) {
		this.circleRelationRepository = circleRelationRepository;
	}

}
