package com.siderakis.upload4gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.Widget;

public class FileInput extends Widget implements HasName, HasChangeHandlers {

	/** Single file input implementation */
	private static class FileInputImpl {
		// returns the single File from the inputElement
		public native JsArray<File> getFiles(InputElement inputElement) /*-{
			return inputElement.value && inputElement.value != "" ? [ {
				fileName : inputElement.value,
				fileSize : -1
			} ] : [];
		}-*/;

		public boolean isAllowMultipleFiles(final InputElement inputElement) {
			return false;
		}

		public void setAllowMultipleFiles(final InputElement inputElement, final boolean allow) {
		}

		public boolean supportsFileAPI() {
			return false;
		}
	}

	/** Multiple file input HTML5 implementation */
	@SuppressWarnings("unused")
	private static class FileInputImplHtml5 extends FileInputImpl {
		@Override
		public native JsArray<File> getFiles(InputElement inputElement) /*-{
			return inputElement.files;
		}-*/;

		@Override
		public boolean isAllowMultipleFiles(final InputElement inputElement) {
			return inputElement.hasAttribute("multiple");
		}

		@Override
		public void setAllowMultipleFiles(final InputElement inputElement, final boolean allow) {
			if (allow) {
				inputElement.setAttribute("multiple", "");
			} else {
				inputElement.removeAttribute("multiple");
			}
		}

		@Override
		public boolean supportsFileAPI() {
			return true;
		}
	}

	private final FileInputImpl impl;
	private final InputElement inputElement;

	public FileInput() {
		inputElement = Document.get().createFileInputElement();
		setElement(inputElement);
		setStyleName("gwt-FileUpload");
		impl = GWT.create(FileInputImpl.class);
	}

	@Override
	public HandlerRegistration addChangeHandler(final ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	public File[] getFiles() {
		final JsArray<File> files = impl.getFiles(inputElement);
		final File[] result = new File[files.length()];
		for (int i = 0; i < files.length(); ++i) {
			result[i] = files.get(i);
		}
		return result;
	}

	@Override
	public String getName() {
		return inputElement.getName();
	}

	public boolean isAllowedMultipleFiles() {
		return impl.isAllowMultipleFiles(inputElement);
	}

	public boolean isDisabled() {
		return inputElement.isDisabled();
	}

	public void setAllowMultipleFiles(final boolean allow) {
		impl.setAllowMultipleFiles(inputElement, allow);
	}

	public void setDisabled(final boolean disabled) {
		inputElement.setDisabled(disabled);
	}

	@Override
	public void setName(final String name) {
		inputElement.setName(name);
	}

	public boolean supportsFileAPI() {
		return impl.supportsFileAPI();
	}

}
