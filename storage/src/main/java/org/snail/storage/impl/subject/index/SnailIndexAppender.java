package org.snail.storage.impl.subject.index;

import org.snail.storage.api.subject.file.SnailFileAppender;
import org.snail.storage.api.subject.index.Indexed;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:37
 */
public class SnailIndexAppender implements SnailFileAppender {

	private final SnailFileAppender delegate;

	public SnailIndexAppender(SnailFileAppender delegate) {
		this.delegate = delegate;
	}

	@Override
	public Indexed append(long sequence, byte[] data) {
		return null;
	}

	@Override
	public void flush() {

	}

	@Override
	public void close() {

	}
}
