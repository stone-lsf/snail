package org.snail.storage.impl.subject.segment;

import org.snail.storage.api.entry.IndexEntry;
import org.snail.storage.api.entry.SnailEntry;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.index.IndexFile;
import org.snail.storage.api.subject.index.IndexPolicy;
import org.snail.storage.api.subject.segment.Segment;
import org.snail.storage.api.subject.segment.SegmentAppender;
import org.snail.storage.api.subject.segment.SegmentReader;
import org.snail.storage.impl.subject.index.SnailIndexFile;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:15
 */
public class SnailSegment implements Segment {

	private final SnailFile<SnailEntry> dataFile;
	private final IndexFile indexFile;
	private final SegmentAppender appender;

	public SnailSegment(SnailFile<SnailEntry> dataFile, SnailFile<IndexEntry> index, IndexPolicy policy) {
		this.dataFile = dataFile;
		this.indexFile = new SnailIndexFile(policy, index);
		this.appender = new SnailSegmentAppender(this, dataFile, indexFile);
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
