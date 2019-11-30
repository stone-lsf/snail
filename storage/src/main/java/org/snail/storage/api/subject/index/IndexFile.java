package org.snail.storage.api.subject.index;

/**
 * @author shifeng.luo
 * @version created on 2019-11-29 14:58
 */
public interface IndexFile {

	void index(long sequence, int offset);

	void flush();
}
