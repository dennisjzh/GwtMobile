/* Copyright (c) 2010 Zhihua (Dennis) Jiang
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtmobile.phonegap.kitchensink.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmobile.phonegap.client.FileMgr;
import com.gwtmobile.phonegap.client.FileMgr.DirectoryEntry;
import com.gwtmobile.phonegap.client.FileMgr.Entry;
import com.gwtmobile.phonegap.client.FileMgr.EntryCallback;
import com.gwtmobile.phonegap.client.FileMgr.Event;
import com.gwtmobile.phonegap.client.FileMgr.EventCallback;
import com.gwtmobile.phonegap.client.FileMgr.File;
import com.gwtmobile.phonegap.client.FileMgr.FileCallback;
import com.gwtmobile.phonegap.client.FileMgr.FileEntry;
import com.gwtmobile.phonegap.client.FileMgr.FileError;
import com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback;
import com.gwtmobile.phonegap.client.FileMgr.FileOptions;
import com.gwtmobile.phonegap.client.FileMgr.FileReader;
import com.gwtmobile.phonegap.client.FileMgr.FileSystem;
import com.gwtmobile.phonegap.client.FileMgr.FileSystemCallback;
import com.gwtmobile.phonegap.client.FileMgr.FileWriter;
import com.gwtmobile.phonegap.client.FileMgr.FileWriterCallback;
import com.gwtmobile.phonegap.client.FileMgr.LocalFileSystem;
import com.gwtmobile.phonegap.client.FileMgr.Metadata;
import com.gwtmobile.phonegap.client.FileMgr.MetadataCallback;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.widgets.ScrollPanel;

public class FileUi extends Page {

	private static FileUiUiBinder uiBinder = GWT.create(FileUiUiBinder.class);
	
	@UiField HTML text;
	@UiField ScrollPanel scroller;
	
	interface FileUiUiBinder extends UiBinder<Widget, FileUi> {
	}

	public FileUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
    @UiHandler("list0")
	void onList0SelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		getFile();
    		break;
    	case 1:
    		getMetadata();
    		break;
    	case 2:
    		isFile();
    		break;
    	case 3:
    		isDirectory();
    		break;
    	case 4:
    		getFile();
    		break;
    	case 5:
    		getFileInfo();
    		break;
    	case 6:
    		moveTo();
    		break;
    	case 7:
    		copyTo();
    		break;
    	case 8:
    		toURI();
    		break;
    	}
    }

    @UiHandler("list1")
	void onList1SelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		getFileParent();
    		break;
    	case 1:
    		createWriter();
    		break;
    	case 2:
    		write();
    		break;
    	case 3:
    		truncate();
    		break;
    	case 4:
    		seek();
    		break;
    	case 5:
    		abort();
    		break;
    	case 6:
    		readAsDataURL();
    		break;
    	case 7:
    		readAsText();
    		break;
    	case 8:
    		remove();
    		break;
    	}
    }

    private void getFile() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
					console("success: get file --" + file.getFullPath());
			}
		});
	}
    
    private void getMetadata() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(FileEntry file) {
				file.getMetadata(new MetadataCallback() {
					@Override
					public void onSuccess(Metadata metadata) {
						console("succeed: last modification time -- " + metadata.getModificationTime().toString());
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
	}

    private void isFile() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(FileEntry file) {
				console("is file -- " + file.isFile());
			}
		});
	}

    private void isDirectory() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(FileEntry file) {
				console("is directory -- " + file.isDirectory());
			}
		});
	}

    private void getFileInfo() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(FileEntry file) {
				file.file(new FileCallback() {
					@Override
					public void onSuccess(File file) {
						console( 
								"Name -- " + file.getName() + "<br/>" + 
								"Full Path -- " + file.getFullPath() + "<br/>" + 
//TODO: bug in phonegap. uncomment code below on 0.9.6.
//								"Last Modified -- " + file.getLastModifiedDate().toString() + "<br/>" + 
								"Size -- " + file.getSize() + "<br/>"
								);
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
	}

    private void moveTo() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				file.getParent(new EntryCallback() {
					@Override
					public void onSuccess(Entry entry) {
						file.moveTo(entry, "backup-kitchensink.txt", new EntryCallback() {
							@Override
							public void onSuccess(Entry entry) {
								console("success: move to -- " + entry.getFullPath());
							}
							@Override
							public void onError(FileError error) {
								console("error:" + error.getCode());
							}
						});
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
	}

    private void copyTo() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				file.getParent(new EntryCallback() {
					@Override
					public void onSuccess(Entry entry) {
						file.copyTo(entry, "backup-kitchensink.txt", new EntryCallback() {
							@Override
							public void onSuccess(Entry entry) {
								console("success: copy to -- " + entry.getFullPath());
							}
							@Override
							public void onError(FileError error) {
								console("error:" + error.getCode());
							}
						});
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
	}

    private void toURI() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				console("URI --" + file.toURI());
			}
		});
	}

    private void getFileParent() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				file.getParent(new EntryCallback() {
					@Override
					public void onSuccess(Entry entry) {
						console("success: get parent -- " + entry.getFullPath());
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
	}
    
    private void createWriter() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				file.createWriter(new FileWriterCallback() {
					@Override
					public void onSuccess(FileWriter writer) {
						console("success: writer created -- " + writer.getFileName());
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
    }
    
    private void write() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				file.createWriter(new FileWriterCallback() {
					@Override
					public void onSuccess(FileWriter writer) {
						writer.write("gwtmobile phonegap kitchen sink");
						console("success: content writen to file-- " + writer.getReadyState());
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
    }

    private void truncate() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				file.createWriter(new FileWriterCallback() {
					@Override
					public void onSuccess(FileWriter writer) {
						writer.truncate(10);
						console("success: file truncated to -- " + writer.getLength());
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
	}

    private void seek() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				file.createWriter(new FileWriterCallback() {
					@Override
					public void onSuccess(FileWriter writer) {
						writer.seek(10);
						console("success: file positioned to -- " + writer.getPosition());
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
	}

    private void abort() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				file.createWriter(new FileWriterCallback() {
					@Override
					public void onSuccess(FileWriter writer) {
						writer.write("some sample text");
						writer.abort();
						console("success:  -- " + writer.getReadyState());
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
	}

    private void readAsDataURL() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				file.file(new FileCallback() {
					@Override
					public void onSuccess(File file) {
						EventCallback callback = new EventCallback() {			
							@Override
							public void onEvent(Event evt) {
								console("Event Type: " + evt.getType() + "<br/>" + 
										"FileName: " + evt.getTarget().getFileName() + "<br/>" +  
										"Result: " + evt.getTarget().getResult() + "<br/>" +  
										(evt.getType().equals("error") ? ("Error: " + evt.getTarget().getError().getCode() + "<br/>") : "") +  
										text.getHTML());
							}
						};
						text.setText("");
						FileReader reader = FileMgr.newReaderInstance();
						reader.onLoad(callback);
						reader.onLoadStart(callback);
						reader.onLoadEnd(callback);
						reader.onProgress(callback);
						reader.onAbort(callback);
						reader.onError(callback);
						reader.readAsDataURL(file);
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
	}

    private void readAsText() {
    	getDemoFile(new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				file.file(new FileCallback() {
					@Override
					public void onSuccess(File file) {
						EventCallback callback = new EventCallback() {			
							@Override
							public void onEvent(Event evt) {
								console("Event Type: " + evt.getType() + "<br/>" + 
										"FileName: " + evt.getTarget().getFileName() + "<br/>" +  
										"Result: " + evt.getTarget().getResult() + "<br/>" +  
										(evt.getType().equals("error") ? ("Error: " + evt.getTarget().getError().getCode() + "<br/>") : "") +  
										text.getHTML());
							}
						};
						text.setText("");
						FileReader reader = FileMgr.newReaderInstance();
						reader.onLoad(callback);
						reader.onLoadStart(callback);
						reader.onLoadEnd(callback);
						reader.onProgress(callback);
						reader.onAbort(callback);
						reader.onError(callback);
						reader.readAsText(file);
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
	}


    private void remove() {
    	console("");
    	DemoCallback callback = new DemoCallback() {
			@Override
			public void onSuccess(final FileEntry file) {
				file.remove(new FileMgrCallback() {
					@Override
					public void onSuccess(boolean success) {
						console(text.getHTML() + "remove " + file.getFullPath() + " -- " + success + "<br/>");
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		};
    	getDemoFile("kitchensink.txt", callback);
    	getDemoFile("backup-kitchensink.txt", callback);
    }


    private void getDemoFile(final DemoCallback callback) {
    	getDemoFile("kitchensink.txt", callback);
    }
  
    private void getDemoFile(final String fileName, final DemoCallback callback) {
    	FileMgr.requestFileSystem(LocalFileSystem.PERSISTENT, new FileSystemCallback() {
			@Override
			public void onSuccess(FileSystem fs) {
				fs.getRoot().getDirectory("gwtmobile-phonegap", new FileOptions().create(true), new EntryCallback() {
					@Override
					public void onSuccess(Entry entry) {
						DirectoryEntry dir = (DirectoryEntry) entry;
						dir.getFile(fileName, new FileOptions().create(true), new EntryCallback() {
							@Override
							public void onSuccess(Entry entry) {
								FileEntry file = (FileEntry) entry;
								callback.onSuccess(file);
							}
							@Override
							public void onError(FileError error) {
								console("error:" + error.getCode());
							}
						});
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
			@Override
			public void onError(FileError error) {
				console("error:" + error.getCode());
			}
		});
	}
    
    private void console(String html) {
		scroller.setPostionToTop();
		text.setHTML(html);
    }
    
    interface DemoCallback {
    	void onSuccess(FileEntry file);
    }

}
