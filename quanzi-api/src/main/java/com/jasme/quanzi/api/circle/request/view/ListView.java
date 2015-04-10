package com.jasme.quanzi.api.circle.request.view;

import com.jasme.quanzi.core.component.circle.enums.CircleType;

public class ListView {

	private CircleType type;
	
	private boolean special;

	public CircleType getType() {
		return type;
	}

	public void setType(CircleType type) {
		this.type = type;
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}
	
}
