package org.snail.storage.api.subject;

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
}
