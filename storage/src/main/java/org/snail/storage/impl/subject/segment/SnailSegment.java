package org.snail.storage.impl.subject.segment;

import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.index.IndexFile;
import org.snail.storage.api.subject.segment.Segment;
import org.snail.storage.api.subject.segment.SegmentAppender;
import org.snail.storage.api.subject.segment.SegmentReader;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:15
 */
public class SnailSegment implements Segment {

	private final SnailFile data;
	private final IndexFile index;
	private final SegmentAppender appender;

	public SnailSegment(SnailFile data, IndexFile index) {
		this.data = data;
		this.index = index;
		this.appender = new SnailSegmentAppender(this, data.appender(), index);
	}


	@Override
	public SnailFile dataFile() {
		return data;
	}

	@Override
	public SnailFile indexFile() {
		return index;
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
