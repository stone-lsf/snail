package org.snail.storage.api.subject;

/**
 * @author shifeng.luo
 * @version created on 2019-11-20 13:23
 */
public interface SubjectAppender {

	void append(byte[] entry);
}
