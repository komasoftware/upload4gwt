package com.siderakis.upload4gwt.shared;

import java.io.Serializable;

public class UploadStatus implements Serializable {

	public enum Errors {
		SizeLimitExceededException,

	}

	private static final long serialVersionUID = 1L;
	private Long bytesRead;
	private Long contentLength;
	private String key;
	private Errors error = null;

	public String message = null;

	@SuppressWarnings("unused") private UploadStatus() {

	}

	public UploadStatus(final String uploadId, final Errors error, final String message) {
		super();
		this.key = uploadId;
		this.error = error;
		this.message = message;
		this.bytesRead = 0L;
		this.contentLength = 0L;
	}

	public UploadStatus(final String uploadId, final Long part, final Long total) {
		super();
		this.key = uploadId;
		this.bytesRead = part;
		this.contentLength = total;
	}

	public String getDisplaySpeed() {
		return "100Mb/sec";
	}

	public Errors getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public String getName() {
		return key;
	}

	public Integer getPercentage() {
		return contentLength.equals(0L) ? 0 : Math.round(bytesRead * 100 / contentLength);
	}

	public Boolean isError() {
		return error != null;
	}

	public Boolean isFinished() {
		return isError() || getPercentage().equals(100);
	}

	@Override public String toString() {
		return "UploadStatus [bytesRead=" + bytesRead + ", contentLength=" + contentLength + ", id=" + key
		+ ", getPercentage()=" + getPercentage() + "]";
	}
}
