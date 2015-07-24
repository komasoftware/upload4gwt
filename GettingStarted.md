# Getting Started with Upload4GWT #

## Introduction ##

On the front end the main components are the `UploadFormPanel` and the `SimpleProgressBar`.  The `UploadFormPanel` extends the regular GWT form panel, and adds functionality that support a progress bar.  Any implementation of `HasProgress` can be used as a progress bar.  In this example the provided implementation `SimpleProgressBar` is being used.


## Using upload4gwt in your project ##

### Project set-up ###
# Copy the jar file into the war/lib directory, and add it to the build path.
(There is no jar because you will most likely have to edit the code to get it working in your project)
For a step-by-step setup guide to running the sample check out http://code.google.com/p/upload4gwt/wiki/RunningSample

# Add this line into the projects gwt.xml
```
	<inherits name='com.siderakis.upload4gwt.Upload4gwt' />
```

### Create the upload form ###

```
// Create a FlowPanel which form items can be added.
final FlowPanel panel = new FlowPanel();

// Create a UploadFormPanel and point it at a service.
final UploadFormPanel form = new UploadFormPanel();
form.setAction(GWT.getModuleBaseURL() + "upload");
form.setWidget(panel);

// Create a standard FileUpload.
final FileUpload upload = new FileUpload();
upload.setName("singleUploadFormElement");
panel.add(upload);

// Add a 'submit' button.
panel.add(new Button("Submit", new ClickHandler() {
	@Override public void onClick(final ClickEvent event) {
		form.submit();
	}
}));

// Create a SimpleProgressBar.
final SimpleProgressBar simpleProgressBar = new SimpleProgressBar();
// Add the progress bar to the panel (can be added anywhere).
panel.add(simpleProgressBar);
// Set it as the status display for the form.
form.setStatusDisplay(simpleProgressBar);
```

### Handle upload submission on the server ###