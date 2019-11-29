package org.snail.storage.api.subject.index;

/**
 * @author shifeng.luo
 * @version created on 2019-11-29 14:07
 */
public interface IndexPolicy {

	boolean triggerIndex(long sequence, int offset);
}
