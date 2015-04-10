package com.jasme.quanzi.api.circle.request.view;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.jasme.quanzi.core.component.circle.enums.CircleType;

public class CreateView implements Serializable {

	@NotNull(message = "{not.null}")
	@Length(min = 2, max = 20)
	private String name;

	@NotNull(message = "{not.null}")
	private CircleType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CircleType getType() {
		return type;
	}

	public void setType(CircleType type) {
		this.type = type;
	}
}
