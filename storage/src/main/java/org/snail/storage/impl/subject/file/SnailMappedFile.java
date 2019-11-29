package org.snail.storage.impl.subject.file;

import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileAppender;
import org.snail.storage.api.subject.file.SnailFileReader;

import java.io.File;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:23
 */
public class SnailMappedFile<T extends Entry> implements SnailFile<T> {

	protected final File file;
	protected final SnailFileAppender<T> appender;

	protected SnailMappedFile(File file) {
		this.file = file;
		this.appender = new SnailMappedFileAppender<>();
	}

	@Override
	public SnailFileReader<T> openReader(int offset) {
		return null;
	}

	@Override
	public SnailFileAppender<T> appender() {
		return null;
	}


	@Override
	public void append(T entry) {
		appender.append(entry);
	}

	@Override
	public int getCurrentOffset() {
		return appender.getCurrentOffset();
	}

	@Override
	public void flush() {
		appender.flush();
	}

	@Override
	public boolean isOpen() {
		return false;
	}


	@Override
	public void close() {

	}

	@Override
	public File getFile() {
		return null;
	}
}
