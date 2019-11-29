package org.snail.storage.impl.subject.file;

import lombok.extern.slf4j.Slf4j;
import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.exceptions.StorageException;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileAppender;
import org.snail.storage.api.subject.file.SnailFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;

import static java.nio.file.StandardOpenOption.*;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:57
 */
@Slf4j
public  class SnailChannelFile<T extends Entry> implements SnailFile<T> {

	protected final File file;
	protected final SnailFileAppender<T> appender;

	public SnailChannelFile(File file) {
		this.file = file;
		this.appender = new SnailChannelFileAppender<>(this, openChannel(file, CREATE, READ, WRITE));
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
		FileChannel channel = openChannel(file, READ);
		return new SnailChannelFileReader<>(this, channel, offset);
	}

	@Override
	public SnailFileAppender<T> appender() {
		return appender;
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
		return file;
	}
}
