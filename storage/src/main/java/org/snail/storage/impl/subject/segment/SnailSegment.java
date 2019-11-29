package org.snail.storage.impl.subject.segment;

import org.snail.storage.api.entry.IndexEntry;
import org.snail.storage.api.entry.SnailEntry;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.index.IndexPolicy;
import org.snail.storage.api.subject.segment.Segment;
import org.snail.storage.api.subject.segment.SegmentAppender;
import org.snail.storage.api.subject.segment.SegmentReader;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:15
 */
public class SnailSegment implements Segment {

	private final SnailFile<SnailEntry> data;
	private final SnailFile<IndexEntry> index;
	private final SegmentAppender appender;

	public SnailSegment(SnailFile<SnailEntry> data, SnailFile<IndexEntry> index, IndexPolicy policy) {
		this.data = data;
		this.index = index;
		this.appender = new SnailSegmentAppender(this, data, index, policy);
	}


	@Override
	public SegmentAppender appender() {
		return appender;
	}

	@Override
	public SegmentReader openReader(long sequence) {
		return null;
	}
}
