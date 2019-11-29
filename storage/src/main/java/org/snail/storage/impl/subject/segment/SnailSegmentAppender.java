package org.snail.storage.impl.subject.segment;

import org.snail.storage.api.entry.IndexEntry;
import org.snail.storage.api.entry.SnailEntry;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.index.IndexPolicy;
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
	private final SnailFile<IndexEntry> indexFile;
	private final IndexPolicy policy;

	public SnailSegmentAppender(Segment segment, SnailFile<SnailEntry> snailFile, SnailFile<IndexEntry> indexFile,
								IndexPolicy policy) {
		this.segment = segment;
		this.snailFile = snailFile;
		this.indexFile = indexFile;
		this.policy = policy;
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
		if (policy.triggerIndex(sequence, offset)) {
			IndexEntry indexEntry = new IndexEntry();
			indexEntry.setIndexSequence(sequence);
			indexEntry.setOffset(offset);

			int indexFileOffset = indexFile.getCurrentOffset();
			indexEntry.setOffset(indexFileOffset);

			indexFile.append(indexEntry);
		}

		return new Indexed(sequence, data, offset);
	}


	@Override
	public void flush() {

	}

	@Override
	public void close() {

	}
}
