package org.snail.storage.api.subject.file;

import org.snail.storage.api.entry.Entry;

import java.util.Iterator;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 12:55
 */
public interface SnailFileReader<T extends Entry> extends Iterator<T> {

	T next();

	void reset(int offset);

	boolean hasNext();
}
