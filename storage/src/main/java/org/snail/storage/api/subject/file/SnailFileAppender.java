package org.snail.storage.api.subject.file;

import org.snail.storage.api.subject.index.Indexed;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 12:55
 */
public interface SnailFileAppender {

	Indexed append(long sequence, byte[] data);

	void flush();
}
