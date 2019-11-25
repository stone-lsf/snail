package org.snail.storage.api.subject.segment;

import org.snail.storage.api.subject.file.SnailFile;

/**
 * @author shifeng.luo
 * @version created on 2019-11-20 13:54
 */
public interface Segment {

	/**
	 * return data file
	 *
	 * @return data file
	 */
	SnailFile dataFile();

	/**
	 * return index file
	 *
	 * @return index file
	 */
	SnailFile indexFile();

	/**
	 * return segment appender
	 *
	 * @return segment appender
	 */
	SegmentAppender appender();

	/**
	 * build a new reader from sequence
	 *
	 * @param sequence sequence
	 * @return a new reader from sequence
	 */
	SegmentReader openReader(long sequence);
}
