package org.snail.storage.api.subject;

import org.snail.storage.api.subject.segment.Segment;

/**
 * @author shifeng.luo
 * @version created on 2019-11-20 21:52
 */
public interface RollingPolicy {

	/**
	 * 是否触发文件滚动
	 *
	 * @param segment 当前append文件
	 * @return 如果触发则返回true，否则返回false
	 */
	boolean triggerRolling(byte[] data, Segment segment);
}
