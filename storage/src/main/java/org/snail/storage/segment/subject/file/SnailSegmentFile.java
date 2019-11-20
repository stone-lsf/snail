package org.snail.storage.segment.subject.file;

import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileAppender;
import org.snail.storage.api.subject.file.SnailFileReader;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:57
 */
public class SnailSegmentFile implements SnailFile {

	@Override
	public SnailFileReader openReader(int offset) {
		return null;
	}

	@Override
	public SnailFileAppender appender() {
		return null;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public void close() {

	}
}
