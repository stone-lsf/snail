package org.snail.storage.impl.subject.file;

import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.exceptions.StorageException;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileAppender;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author shifeng.luo
 * @version created on 2019-11-29 14:04
 */
public class SnailMappedFileAppender<T extends Entry> implements SnailFileAppender<T> {

	private final SnailFile<T> snailFile;
	private final FileChannel channel;
	private final ByteBuffer writeBuffer;
	private final MappedByteBuffer buffer;
	private final int fileSize;
	private int offset;

	public SnailMappedFileAppender(SnailFile<T> snailFile, FileChannel channel, int fileSize) {
		this.snailFile = snailFile;
		this.channel = channel;
		this.fileSize = fileSize;
		this.writeBuffer = ByteBuffer.allocate(512 * 1024);
		try {
			this.buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public void append(T entry) {
		int length = entry.getLength();
		if (writeBuffer.remaining() > length) {
			entry.writeTo(writeBuffer);
		} else {
			write();
		}

		offset += entry.getLength();
	}

	@Override
	public int getCurrentOffset() {
		return offset;
	}

	@Override
	public void flush() {
		write();
		buffer.force();
	}

	private void write() {
		writeBuffer.flip();
		buffer.put(writeBuffer);
		writeBuffer.clear();
		writeBuffer.flip().limit(writeBuffer.capacity());
	}

	@Override
	public void close() {
		flush();
		clean();
	}

	public void clean() {
		if (buffer == null || !buffer.isDirect() || buffer.capacity() == 0) {
			return;
		}
		invoke(invoke(viewed(buffer), "cleaner"), "clean");
	}

	private Object invoke(final Object target, final String methodName, final Class<?>... args) {
		return AccessController.doPrivileged(new PrivilegedAction<Object>() {
			@Override
			public Object run() {
				try {
					Method method = method(target, methodName, args);
					method.setAccessible(true);
					return method.invoke(target);
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		});
	}

	private Method method(Object target, String methodName, Class<?>[] args)
			throws NoSuchMethodException {
		try {
			return target.getClass().getMethod(methodName, args);
		} catch (NoSuchMethodException e) {
			return target.getClass().getDeclaredMethod(methodName, args);
		}
	}

	private ByteBuffer viewed(ByteBuffer buffer) {
		String methodName = "viewedBuffer";
		Method[] methods = buffer.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().equals("attachment")) {
				methodName = "attachment";
				break;
			}
		}

		ByteBuffer viewedBuffer = (ByteBuffer) invoke(buffer, methodName);
		if (viewedBuffer == null) {
			return buffer;
		} else {
			return viewed(viewedBuffer);
		}
	}

}
