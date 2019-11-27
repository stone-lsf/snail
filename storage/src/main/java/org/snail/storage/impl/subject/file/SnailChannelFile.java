package org.snail.storage.impl.subject.file;

import org.snail.storage.api.StorageException;
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
public class SnailChannelFile implements SnailFile {

	private final File file;
	private final SnailFileAppender appender;

	public SnailChannelFile(File file) {
		this.file = file;
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
	public SnailFileReader openReader(int offset) {
		return null;
	}

	@Override
	public SnailFileAppender appender() {
		return appender;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public void close() {
		appender.close();
	}
}
