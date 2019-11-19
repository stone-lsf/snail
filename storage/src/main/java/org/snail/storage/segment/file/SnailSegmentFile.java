package org.snail.storage.segment.file;

import org.snail.storage.api.file.*;

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
}
