package org.snail.storage.api.subject.file;

import java.io.Closeable;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:56
 */
public interface SnailFile extends Closeable {

	/**
	 * 从指定偏移位置创建文件读取器
	 *
	 * @param offset 偏移地址
	 * @return {@link SnailFileReader}
	 */
	SnailFileReader openReader(int offset);

	/**
	 * return the file appender
	 *
	 * @return {@link SnailFileAppender}
	 */
	SnailFileAppender appender();

	/**
	 * return whether the file is open
	 *
	 * @return whether the file is open
	 */
	boolean isOpen();

	@Override
	void close();
}
