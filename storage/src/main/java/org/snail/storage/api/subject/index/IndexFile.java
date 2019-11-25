package org.snail.storage.api.subject.index;

import org.snail.storage.api.subject.file.SnailFile;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:27
 */
public interface IndexFile extends SnailFile {

	void index(long sequence, int offset);
}
