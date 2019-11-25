package org.snail.storage.impl.subject.segment;

import org.snail.storage.api.subject.file.SnailFileAppender;
import org.snail.storage.api.subject.index.IndexFile;
import org.snail.storage.api.subject.index.Indexed;
import org.snail.storage.api.subject.segment.Segment;
import org.snail.storage.api.subject.segment.SegmentAppender;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:15
 */
public class SnailSegmentAppender implements SegmentAppender {

	private final Segment segment;
	private final SnailFileAppender fileAppender;
	private final IndexFile indexFile;

	public SnailSegmentAppender(Segment segment, SnailFileAppender fileAppender, IndexFile indexFile) {
		this.segment = segment;
		this.fileAppender = fileAppender;
		this.indexFile = indexFile;
	}


	@Override
	public Indexed append(long sequence, byte[] data) {
		Indexed indexed = fileAppender.append(sequence, data);
		indexFile.index(sequence,indexed.getOffset());
		return indexed;
	}

	@Override
	public void flush() {

	}

	@Override
	public void close() {

	}
}
