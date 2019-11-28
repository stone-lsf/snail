package org.snail.storage.impl.subject.file;

import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileAppender;
import org.snail.storage.api.subject.file.SnailFileReader;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:23
 */
public class SnailMappedFile<T extends Entry> implements SnailFile<T> {

	@Override
	public SnailFileReader<T> openReader(int offset) {
		return null;
	}

	@Override
	public SnailFileAppender<T> appender() {
		return null;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public T createEntry() {
		return null;
	}

	@Override
	public void close() {

	}
}
