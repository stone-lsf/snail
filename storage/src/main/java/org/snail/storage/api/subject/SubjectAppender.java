package org.snail.storage.api.subject;

/**
 * @author shifeng.luo
 * @version created on 2019-11-20 13:23
 */
public interface SubjectAppender {

	void append(byte[] data);

	/**
	 * Commits entries up to the given index.
	 *
	 * @param index The index up to which to commit entries.
	 */
	void commit(long index);

	/**
	 * Resets the head of the journal to the given index.
	 *
	 * @param index the index to which to reset the head of the journal
	 */
	void reset(long index);

	/**
	 * Truncates the log to the given index.
	 *
	 * @param index The index to which to truncate the log.
	 */
	void truncate(long index);

	void flush();
}
