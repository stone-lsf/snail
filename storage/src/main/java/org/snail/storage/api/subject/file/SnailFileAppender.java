package org.snail.storage.api.subject.file;

import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.subject.index.Indexed;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 12:55
 */
public interface SnailFileAppender<T extends Entry> {

	void append(T entry);

//	Indexed append(long sequence, byte[] data);

	int getCurrentOffset();

	void flush();

	void close();
}
