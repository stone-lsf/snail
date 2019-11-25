package org.snail.storage.api.subject;

import org.snail.storage.api.entry.SnailEntry;
import org.snail.storage.api.subject.segment.Segment;

/**
 * @author shifeng.luo
 * @version created on 2019-11-20 13:23
 */
public interface Subject {

	/**
	 * return file subject
	 *
	 * @return file subject
	 */
	String name();

	/**
	 * subject start
	 */
	void start();

	/**
	 * return one subject Reader which read from the sequence
	 *
	 * @param sequence sequence
	 * @return one subject Reader which read from the sequence
	 */
	SubjectReader openReader(long sequence);

	/**
	 * return subject Appender
	 *
	 * @return subject Appender
	 */
	SubjectAppender appender();

	/**
	 * return next sequence
	 *
	 * @return next sequence
	 */
	long nextSequence();

	/**
	 * return last segment
	 *
	 * @return last segment
	 */
	Segment lastSegment();

	/**
	 * return first segment
	 *
	 * @return first segment
	 */
	Segment firstSegment();

	/**
	 * 创建一个segment
	 *
	 * @return segment
	 */
	Segment next();
}
