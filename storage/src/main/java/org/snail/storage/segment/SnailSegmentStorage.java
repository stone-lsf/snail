package org.snail.storage.segment;

import org.snail.storage.api.SnailStorage;
import org.snail.storage.api.SnailStorageAppender;
import org.snail.storage.api.SnailStorageReader;

import java.io.IOException;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:51
 */
public class SnailSegmentStorage implements SnailStorage {

	@Override
	public SnailStorageReader openReader(String subject, long sequence) {
		return null;
	}

	@Override
	public SnailStorageAppender openAppender(String subject) {
		return null;
	}

	@Override
	public void add(String subject) {

	}

	@Override
	public void start() {

	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public String getJournalPath() {
		return null;
	}

	@Override
	public String getStorePath() {
		return null;
	}
}
