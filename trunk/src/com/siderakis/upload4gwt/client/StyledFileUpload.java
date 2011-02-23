package com.siderakis.upload4gwt.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.FileUpload;

public class StyledFileUpload extends FileUpload {

	public StyledFileUpload() {
		super();

		addAttachHandler(new Handler() {
			@Override
			public void onAttachOrDetach(AttachEvent event) {
				styleInput(getFileUploadElement());
			}
		});

	}

	protected Element getFileUploadElement() {
		return getElement();
	}

	public StyledFileUpload(final String string) {

		addAttachHandler(new Handler() {
			@Override
			public void onAttachOrDetach(AttachEvent event) {
				styleInput(getFileUploadElement(), string);
			}
		});

	}

	private static native void styleInput(Element input) /*-{
		var fakeFileUpload = $doc.createElement('div');
		fakeFileUpload.className = 'fakefile';
		fakeFileUpload.appendChild($doc.createElement('input'));
		var image = $doc.createElement('img');
		image.src = '../button_select.gif';
		fakeFileUpload.appendChild(image);
		input.className = 'file hidden';
		var clone = fakeFileUpload.cloneNode(true);
		input.parentNode.appendChild(clone);
		input.relatedElement = clone.getElementsByTagName('input')[0];
		input.onchange = input.onmouseout = function() {
			this.relatedElement.value = this.value;
		}
	}-*/;

	private static native void styleInput(Element input, String defaultText) /*-{
		var fakeFileUpload = $doc.createElement('div');
		fakeFileUpload.appendChild($doc.createElement('input'));
		var clone = fakeFileUpload.cloneNode(true);
		input.parentNode.appendChild(clone);
		input.relatedElement = clone.getElementsByTagName('input')[0];
		input.relatedElement.value = defaultText;
		input.onchange = input.onmouseout = function() {
			this.relatedElement.value = (this.value == "") ? defaultText
					: this.value;
		}
	}-*/;

}
