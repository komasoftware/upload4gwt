package com.siderakis.upload4gwt.server.dao;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.inject.Singleton;
import com.siderakis.upload4gwt.shared.UploadStatus;

/** Uses the low level App Engine Datastore API, and Memcache */
@Singleton
public class UploadStatusDAOAppEngineImpl implements UploadStatusDAO {

	@Override
	public String getBaseUploadId() {
		return getRandomKey();
	}

	private String getRandomKey() {
		int n = 16;
		char[] key = new char[n];
		int newChar = 'A';
		int charType = 0;
		for (int i = 0; i < n; i++) {
			charType = (int) (Math.random() * 3);
			switch (charType) {
			case 0:
				newChar = '0' + (int) (Math.random() * 10);
				break;
			case 1:
				newChar = 'a' + (int) (Math.random() * 26);
				break;
			case 2:
				newChar = 'A' + (int) (Math.random() * 26);
				break;
			}
			key[i] = (char) newChar;
		}
		return new String(key);
	}

	private final static String CACHE_KEY_PREFIX = "upload-ckp:";
	private final MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();

	@Override
	public void setUploadStatus(UploadStatus uploadStatus) {
		memcacheService.put(CACHE_KEY_PREFIX + uploadStatus.getName(), uploadStatus);
	}

	@Override
	public UploadStatus getUploadStatus(String key) {
		return (UploadStatus) memcacheService.get(CACHE_KEY_PREFIX + key);
	}
}
