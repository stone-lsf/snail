package org.snail.storage.impl.subject.file;

import lombok.extern.slf4j.Slf4j;
import org.snail.common.concurrent.NamedThreadFactory;
import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.exceptions.StorageException;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileAppender;
import org.snail.storage.api.subject.index.Indexed;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.snail.storage.impl.StorageConfig.FILE_BUFFER_SIZE;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:54
 */
@Slf4j
public class SnailChannelFileAppender<T extends Entry> implements SnailFileAppender<T> {

	private final SnailFile<T> file;
	private final FileChannel channel;

	private final Object lock = new Object();
	private final AtomicBoolean closed = new AtomicBoolean(false);

	private volatile ByteBuffer input = ByteBuffer.allocate(FILE_BUFFER_SIZE);
	private volatile ByteBuffer output = ByteBuffer.allocate(FILE_BUFFER_SIZE);
	private ExecutorService executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<>(),
															  new NamedThreadFactory("FileAppender"));
	private int offset;

	public SnailChannelFileAppender(SnailFile<T> file, FileChannel channel) {
		this.file = file;
		this.channel = channel;
		output.flip();
		executor.execute(() -> {
			while (!closed.get()) {
				output();
			}
		});
	}

	@Override
	public Indexed append(long sequence, byte[] data) {
		T entry = file.createEntry(sequence, data);
		int length = entry.getLength();
		ByteBuffer input = this.input;
		if (input.remaining() > length) {
			entry.writeTo(input);
		} else {
			switchBuffer();
		}

		Indexed indexed = new Indexed(sequence, data, offset);
		offset += entry.getLength();

		return indexed;
	}


	@Override
	public void flush() {
		switchBuffer();
		doFlush();
	}

	@Override
	public void close() {
		try {
			flush();
			closed.set(true);
			channel.force(true);
			channel.close();
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	private void output() {
		synchronized (lock) {
			try {
				if (output.remaining() <= 0) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						log.error("waite interrupted", e);
					}
				}

				channel.write(output);
			} catch (IOException e) {
				log.error("write data caught", e);
				throw new StorageException(e);
			}
		}
	}

	private void doFlush() {
		synchronized (lock) {
			try {
				if (output.remaining() <= 0) {
					return;
				}

				channel.write(output);
			} catch (IOException e) {
				log.error("write data caught", e);
				throw new StorageException(e);
			}
		}
	}

	private void switchBuffer() {
		synchronized (lock) {
			ByteBuffer tmp = input;
			input = output;
			input.clear();

			output = tmp;
			output.flip();
			lock.notify();
		}
	}

}
