package com.siderakis.upload4gwt.shared;

import java.io.Serializable;

public class Status implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer percentage;

	@SuppressWarnings("unused")
	private Status() {
	}

	public Status(final Integer percentage) {
		super();
		this.percentage = percentage;
	}

	public Integer getPercentage() {
		return percentage;
	}
}
