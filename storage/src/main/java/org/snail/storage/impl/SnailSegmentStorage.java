package org.snail.storage.impl;

import com.google.common.base.Preconditions;
import org.snail.common.util.FileUtil;
import org.snail.storage.api.SnailStorage;
import org.snail.storage.api.SnailStorageAppender;
import org.snail.storage.api.SnailStorageReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:51
 */
public class SnailSegmentStorage implements SnailStorage {

	private final byte version = 1;
	private final byte compress = 0;

	private final String path;

	private AtomicBoolean started = new AtomicBoolean(false);

	public SnailSegmentStorage(String path) {
		this.path = path;
	}

	@Override
	public SnailStorageReader openReader(String subject, long sequence) {
		checkStarted();
		return null;
	}

	@Override
	public SnailStorageAppender openAppender(String subject) {
		return null;
	}

	@Override
	public void add(String subject) {
		checkStarted();
	}

	@Override
	public void start() {
		if (!started.compareAndSet(false, true)) {
			return;
		}

		List<File> subjectDirectories = FileUtil.getFiles(path, File::isDirectory);

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

	private void checkStarted() {
		Preconditions.checkState(started.get(), "Storage not start!");
	}
}
