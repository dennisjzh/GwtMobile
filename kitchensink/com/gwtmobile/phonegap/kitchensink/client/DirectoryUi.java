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
import com.gwtmobile.phonegap.client.FileMgr.DirectoryReader;
import com.gwtmobile.phonegap.client.FileMgr.Entry;
import com.gwtmobile.phonegap.client.FileMgr.EntryCallback;
import com.gwtmobile.phonegap.client.FileMgr.FileError;
import com.gwtmobile.phonegap.client.FileMgr.FileMgrCallback;
import com.gwtmobile.phonegap.client.FileMgr.FileOptions;
import com.gwtmobile.phonegap.client.FileMgr.FileSystem;
import com.gwtmobile.phonegap.client.FileMgr.FileSystemCallback;
import com.gwtmobile.phonegap.client.FileMgr.FreeDiskSpaceCallback;
import com.gwtmobile.phonegap.client.FileMgr.LocalFileSystem;
import com.gwtmobile.phonegap.client.FileMgr.Metadata;
import com.gwtmobile.phonegap.client.FileMgr.MetadataCallback;
import com.gwtmobile.phonegap.client.FileMgr.ReaderCallback;
import com.gwtmobile.ui.client.event.SelectionChangedEvent;
import com.gwtmobile.ui.client.page.Page;
import com.gwtmobile.ui.client.widgets.ScrollPanel;

public class DirectoryUi extends Page {

	private static DirectoryUiUiBinder uiBinder = GWT.create(DirectoryUiUiBinder.class);
	
	@UiField HTML text;
	@UiField ScrollPanel scroller;
	
	interface DirectoryUiUiBinder extends UiBinder<Widget, DirectoryUi> {
	}

	public DirectoryUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
    @UiHandler("list0")
	void onList0SelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		getDirectory();
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
    		getDirectory();
    		break;
    	case 5:
    		moveTo();
    		break;
    	case 6:
    		copyTo();
    		break;
    	}
    }

    @UiHandler("list1")
	void onList1SelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		toURI();
    		break;
    	case 1:
    		getDirParent();
    		break;
    	case 2:
    		createReader();
    		break;
    	case 3:
    		remove();
    		break;
    	case 4:
    		removeRecursively();
    		break;
    	case 5:
    		getFile();
    		break;
    	case 6:
    		getFreeDiskSpace();
    		break;
    	}
    }

    private void getDirectory() {
    	getDemoDirectory(new DemoCallback() {
			@Override
			public void onSuccess(DirectoryEntry dir) {
				console("succeed: directory full path -- " + dir.getFullPath());
			}
		});
	}

    private void getMetadata() {
    	getDemoDirectory(new DemoCallback() {
			@Override
			public void onSuccess(DirectoryEntry dir) {
				dir.getMetadata(new MetadataCallback() {
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
    	getDemoDirectory(new DemoCallback() {
			@Override
			public void onSuccess(DirectoryEntry dir) {
				console("is file -- " + dir.isFile());
			}
		});
	}

    private void isDirectory() {
    	getDemoDirectory(new DemoCallback() {
			@Override
			public void onSuccess(DirectoryEntry dir) {
				console("is directory -- " + dir.isDirectory());
			}
		});
	}

    private void moveTo() {
    	getDemoDirectory(new DemoCallback() {
			@Override
			public void onSuccess(final DirectoryEntry dir) {
				dir.getParent(new EntryCallback() {
					@Override
					public void onSuccess(Entry entry) {
						DirectoryEntry parent = (DirectoryEntry) entry;
						dir.moveTo(parent, "backup-gwtmobile-phonegap", new EntryCallback() {
							@Override
							public void onSuccess(Entry entry) {
								console("succeed: moved to --" + entry.getFullPath());
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
    	getDemoDirectory(new DemoCallback() {
			@Override
			public void onSuccess(final DirectoryEntry dir) {
				dir.getParent(new EntryCallback() {
					@Override
					public void onSuccess(Entry entry) {
						DirectoryEntry parent = (DirectoryEntry) entry;
						dir.copyTo(parent, "backup-gwtmobile-phonegap", new EntryCallback() {
							@Override
							public void onSuccess(Entry entry) {
								console("succeed: copied to --" + entry.getFullPath());
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
    	getDemoDirectory(new DemoCallback() {
			@Override
			public void onSuccess(final DirectoryEntry dir) {
				console("URI --" + dir.toURI());
			}
		});
	}

    private void getDirParent() {
    	getDemoDirectory(new DemoCallback() {
			@Override
			public void onSuccess(final DirectoryEntry dir) {
				dir.getParent(new EntryCallback() {
					@Override
					public void onSuccess(Entry entry) {
						console("succeed: parent --" + entry.getFullPath());
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
    }

    private void createReader() {
    	getDemoDirectory(".", new DemoCallback() {
			@Override
			public void onSuccess(final DirectoryEntry dir) {
				DirectoryReader reader = dir.createReader();
				reader.readEntries(new ReaderCallback() {
					@Override
					public void onSuccess(Entry[] entries) {
						console("Directories reader: <br/>");
						for (int i = 0; i < entries.length; i++) {
							Entry entry = entries[i];
							console(text.getHTML() + entry.getFullPath() + "<br/>");
						}
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
			public void onSuccess(final DirectoryEntry dir) {
				dir.remove(new FileMgrCallback() {
					@Override
					public void onSuccess(boolean success) {
						console(text.getHTML() + "remove " + dir.getFullPath() + " -- " + success + "<br/>");
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		};
    	getDemoDirectory("gwtmobile-phonegap", callback);
    	getDemoDirectory("backup-gwtmobile-phonegap", callback);
    }

    private void removeRecursively() {
    	console("");
    	DemoCallback callback = new DemoCallback() {
			@Override
			public void onSuccess(final DirectoryEntry dir) {
				dir.removeRecursively(new FileMgrCallback() {
					@Override
					public void onSuccess(boolean success) {
						console(text.getHTML() + "remove " + dir.getFullPath() + " -- " + success + "<br/>");
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		};
    	getDemoDirectory("gwtmobile-phonegap", callback);
    	getDemoDirectory("backup-gwtmobile-phonegap", callback);
    }

    private void getFile() {
    	getDemoDirectory(new DemoCallback() {
			@Override
			public void onSuccess(final DirectoryEntry dir) {
				dir.getFile("kitchensink.txt", new FileOptions().create(true), new EntryCallback() {
					@Override
					public void onSuccess(Entry entry) {
						console("success: get file --" + entry.getFullPath());
					}
					@Override
					public void onError(FileError error) {
						console("error:" + error.getCode());
					}
				});
			}
		});
	}
    
    public void getFreeDiskSpace() {
		FileMgr.getFreeDiskSpace(new FreeDiskSpaceCallback() {			
			@Override
			public void onSuccess(double freeDiskSpace) {
				console("Free Disk Space: " + freeDiskSpace);
			}			
			@Override
			public void onError(FileError error) {
				console(error + "");
			}
		});
	}

    private void getDemoDirectory(final DemoCallback callback) {
    	getDemoDirectory("gwtmobile-phonegap", callback);
    }
    private void getDemoDirectory(final String dirName, final DemoCallback callback) {
    	FileMgr.requestFileSystem(LocalFileSystem.PERSISTENT, new FileSystemCallback() {
			@Override
			public void onSuccess(FileSystem fs) {
				fs.getRoot().getDirectory(dirName, new FileOptions().create(true), new EntryCallback() {
					@Override
					public void onSuccess(Entry entry) {
						DirectoryEntry dir = (DirectoryEntry) entry;
						callback.onSuccess(dir);
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
    	void onSuccess(DirectoryEntry dir);
    }
}
