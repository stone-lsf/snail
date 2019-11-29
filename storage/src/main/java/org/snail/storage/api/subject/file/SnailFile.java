package org.snail.storage.api.subject.file;

import org.snail.storage.api.entry.Entry;

import java.io.Closeable;
import java.io.File;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:56
 */
public interface SnailFile<T extends Entry> extends Closeable {

	/**
	 * 从指定偏移位置创建文件读取器
	 *
	 * @param offset 偏移地址
	 * @return {@link SnailFileReader}
	 */
	SnailFileReader<T> openReader(int offset);

	/**
	 * return the file appender
	 *
	 * @return {@link SnailFileAppender}
	 */
	SnailFileAppender<T> appender();

	/**
	 * return whether the file is open
	 *
	 * @return whether the file is open
	 */
	boolean isOpen();

	void append(T entry);

	int getCurrentOffset();

	void flush();

//	/**
//	 * create a entry
//	 *
//	 * @return entry
//	 */
//	T createEntry();
//
//	T createEntry(long sequence, int offset, byte[] data);

	@Override
	void close();

	File getFile();
}
