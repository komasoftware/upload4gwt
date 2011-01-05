package com.siderakis.upload4gwt.shared;

import java.io.Serializable;

public class UploadStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long bytesRead;
	private Long contentLength;
	private Long id;

	@SuppressWarnings("unused")
	private UploadStatus() {

	}

	@Override
	public String toString() {
		return "UploadStatus [bytesRead=" + bytesRead + ", contentLength=" + contentLength + ", id=" + id
				+ ", getPercentage()=" + getPercentage() + "]";
	}

	public UploadStatus(Long id, Long part, Long total) {
		super();
		this.id = id;
		this.bytesRead = part;
		this.contentLength = total;
	}

	public Long getId() {
		return id;
	}

	public String getDisplaySpeed() {
		return "100Mb/sec";
	}

	public Integer getPercentage() {
		return contentLength.equals(0L) ? 0 : Math.round(bytesRead * 100 / contentLength);
	}

	public String getName() {
		return id + "";
	}
}
