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
	private final MappedByteBuffer buffer;
	private final int fileSize;
	private int offset;

	public SnailMappedFileAppender(SnailFile<T> snailFile, FileChannel channel, int fileSize) {
		this.snailFile = snailFile;
		this.channel = channel;
		this.fileSize = fileSize;
		try {
			this.buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	@Override
	public void append(T entry) {
		int length = entry.getLength();
		if (buffer.remaining() > length) {
			entry.writeTo(buffer);
		}

		offset += entry.getLength();
	}

	@Override
	public int getCurrentOffset() {
		return offset;
	}

	@Override
	public void flush() {
		this.buffer.force();
	}

	@Override
	public void close() {

	}

	public static void clean(final ByteBuffer buffer) {
		if (buffer == null || !buffer.isDirect() || buffer.capacity() == 0) {
			return;
		}
		invoke(invoke(viewed(buffer), "cleaner"), "clean");
	}

	private static Object invoke(final Object target, final String methodName, final Class<?>... args) {
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

	private static Method method(Object target, String methodName, Class<?>[] args)
			throws NoSuchMethodException {
		try {
			return target.getClass().getMethod(methodName, args);
		} catch (NoSuchMethodException e) {
			return target.getClass().getDeclaredMethod(methodName, args);
		}
	}

	private static ByteBuffer viewed(ByteBuffer buffer) {
		String methodName = "viewedBuffer";
		Method[] methods = buffer.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals("attachment")) {
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
