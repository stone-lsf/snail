package org.snail.storage.api.subject.segment;

import org.snail.storage.api.subject.file.SnailFile;

/**
 * @author shifeng.luo
 * @version created on 2019-11-20 13:54
 */
public interface Segment {

	SnailFile dataFile();

	SnailFile indexFile();
}
