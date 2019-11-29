package org.snail.storage.impl.subject.file;

import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.subject.file.SnailFileAppender;

/**
 * @author shifeng.luo
 * @version created on 2019-11-29 14:04
 */
public class SnailMappedFileAppender<T extends Entry> implements SnailFileAppender<T> {

	@Override
	public void append(T entry) {

	}

	@Override
	public int getCurrentOffset() {
		return 0;
	}

	@Override
	public void flush() {

	}

	@Override
	public void close() {

	}
}
