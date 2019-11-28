package org.snail.storage.impl.subject.file;

import lombok.extern.slf4j.Slf4j;
import org.snail.common.util.ReflectionUtil;
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
public abstract class ChannelFile<T extends Entry> implements SnailFile<T> {

	private final File file;
	private final SnailFileAppender appender;
	private final Class<T> entryClass;

	public ChannelFile(File file) {
		this.file = file;
		this.entryClass = ReflectionUtil.getSuperClassGenericType(this.getClass());
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
		return new SnailChannelFileReader<T>(this, channel, offset);
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
	public void close() {
		appender.close();
	}

	@Override
	public File getFile() {
		return file;
	}
}
