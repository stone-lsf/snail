package org.snail.storage.impl.subject.segment;

import org.snail.storage.api.entry.SnailEntry;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.index.IndexFile;
import org.snail.storage.api.subject.index.Indexed;
import org.snail.storage.api.subject.segment.Segment;
import org.snail.storage.api.subject.segment.SegmentAppender;
import org.snail.storage.impl.StorageConfig;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:15
 */
public class SnailSegmentAppender implements SegmentAppender {

	private final Segment segment;
	private final SnailFile<SnailEntry> snailFile;
	private final IndexFile indexFile;


	public SnailSegmentAppender(Segment segment, SnailFile<SnailEntry> snailFile, IndexFile indexFile) {
		this.segment = segment;
		this.snailFile = snailFile;
		this.indexFile = indexFile;
	}


	@Override
	public Indexed append(long sequence, byte[] data) {
		int offset = snailFile.getCurrentOffset();

		SnailEntry entry = new SnailEntry();
		entry.setSequence(sequence);
		entry.setPayload(data);
		entry.setVersion(StorageConfig.VERSION);
		entry.setCompress(StorageConfig.COMPRESS);
		entry.setOffset(offset);

		snailFile.append(entry);
		indexFile.index(sequence,offset);

		return new Indexed(sequence, data, offset);
	}


	@Override
	public void flush() {
		snailFile.flush();
		indexFile.flush();
	}

	@Override
	public void close() {

	}
}
