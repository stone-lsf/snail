package org.snail.storage.api.subject.segment;

import org.snail.storage.api.subject.index.Indexed;

/**
 * @author shifeng.luo
 * @version created on 2019-11-20 14:04
 */
public interface SegmentAppender {

	Indexed append(long sequence, byte[] data);

	void flush();

	void close();
}
