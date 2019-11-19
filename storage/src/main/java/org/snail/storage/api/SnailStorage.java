package org.snail.storage.api;

import java.io.IOException;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 12:40
 */
public interface SnailStorage {

	/**
	 * 开启一个{@link SnailStorageReader}
	 *
	 * @param subject  主题
	 * @param sequence 起始序号
	 * @return {@link SnailStorageReader}
	 */
	SnailStorageReader openReader(String subject, long sequence);

	/**
	 * 启动一个appender
	 *
	 * @param subject 主题
	 * @return {@link SnailStorageAppender}
	 */
	SnailStorageAppender openAppender(String subject);

	void add(String subject);

	void start();

	void close() throws IOException;

	String getJournalPath();

	String getStorePath();
}
