package org.snail.storage.impl.subject.file;

import lombok.extern.slf4j.Slf4j;
import org.snail.common.util.ReflectionUtil;
import org.snail.storage.api.exceptions.StorageException;
import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileAppender;
import org.snail.storage.api.subject.file.SnailFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:57
 */
@Slf4j
public class SnailChannelFile<T extends Entry> implements SnailFile<T> {

	private final File file;
	private final SnailFileAppender appender;
	private final Class<T> entryClass;

	public SnailChannelFile(File file) {
		this.file = file;
		this.entryClass = ReflectionUtil.getSuperClassGenericType(this.getClass());
		this.appender = new SnailChannelFileAppender(this, openChannel(file));
	}

	private FileChannel openChannel(File file) {
		try {
			return FileChannel.open(file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
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
		return appender;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public T createEntry() {
		try {
			return entryClass.newInstance();
		} catch (Exception e) {
			log.error("new entry instance caught", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() {
		appender.close();
	}
}
