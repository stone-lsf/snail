package org.snail.storage.impl.subject.file;

import lombok.extern.slf4j.Slf4j;
import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.exceptions.StorageException;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileReader;
import org.snail.storage.impl.StorageConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.NoSuchElementException;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:54
 */
@Slf4j
public class SnailChannelFileReader<T extends Entry> implements SnailFileReader<T> {

	private final SnailFile<T> file;
	private final ByteBuffer memory;
	private final FileChannel channel;
	private T currentEntry;
	private T nextEntry;

	public SnailChannelFileReader(SnailFile<T> file, FileChannel channel, int startOffset) {
		this.file = file;
		this.channel = channel;
		this.memory = ByteBuffer.allocate(StorageConfig.FILE_READ_BUFFER_SIZE);
		memory.flip();
		try {
			channel.position(startOffset);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		// Set the current entry to the next entry.
		currentEntry = nextEntry;

		// Reset the next entry to null.
		nextEntry = null;

		// Read the next entry in the segment.
		readNext();

		// Return the current entry.
		return currentEntry;
	}

	@Override
	public void reset(int offset) {
		try {
			channel.position(offset);
		} catch (IOException e) {
			throw new StorageException(e);
		}

		memory.clear().limit(0);
		currentEntry = null;
		nextEntry = null;
		readNext();
	}

	@Override
	public boolean hasNext() {
		if (nextEntry == null) {
			readNext();
		}
		return nextEntry != null;
	}

	private void readNext() {
		T entry = file.createEntry();

		try {
			if (!entry.checkEnough(memory)) {
				memory.compact();
				memory.flip();
				channel.read(memory);
				memory.flip();
			}

			entry.readFrom(memory);
			if (!entry.validCrc32()) {
				throw new StorageException.BadDataException(file.getFile().getAbsolutePath() + " has bad data");
			}
			nextEntry = entry;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}
}
