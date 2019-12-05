package org.snail.storage.impl.subject.file;

import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.exceptions.StorageException;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileAppender;
import org.snail.storage.api.subject.file.SnailFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:23
 */
public class SnailMappedFile<T extends Entry> implements SnailFile<T> {

	protected final File file;
	protected final SnailFileAppender<T> appender;
	private final Class<T> entryClass;

	protected SnailMappedFile(File file, Class<T> entryClass) {
		this.file = file;
		this.entryClass = entryClass;
		this.appender = new SnailMappedFileAppender<>(this,  openChannel(file, CREATE, READ, WRITE), 1024*1024*1024);
	}

	private FileChannel openChannel(File file, OpenOption... openOptions) {
		try {
			return FileChannel.open(file.toPath(), openOptions);
		} catch (IOException e) {
			throw new StorageException(e);
		}
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
		appender.close();
	}

	@Override
	public File getFile() {
		return null;
	}
}
