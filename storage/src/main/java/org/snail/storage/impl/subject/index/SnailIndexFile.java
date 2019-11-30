package org.snail.storage.impl.subject.index;

import org.snail.storage.api.entry.IndexEntry;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.index.IndexFile;
import org.snail.storage.api.subject.index.IndexPolicy;

/**
 * @author shifeng.luo
 * @version created on 2019-11-29 14:59
 */
public class SnailIndexFile implements IndexFile {

	private final IndexPolicy policy;
	private final SnailFile<IndexEntry> snailFile;

	public SnailIndexFile(IndexPolicy policy, SnailFile<IndexEntry> snailFile) {
		this.policy = policy;
		this.snailFile = snailFile;
	}

	@Override
	public void index(long sequence, int offset) {
		if (policy.triggerIndex(sequence, offset)) {
			IndexEntry indexEntry = new IndexEntry();
			indexEntry.setIndexSequence(sequence);
			indexEntry.setOffset(offset);

			int indexFileOffset = snailFile.getCurrentOffset();
			indexEntry.setOffset(indexFileOffset);

			snailFile.append(indexEntry);
		}
	}

	@Override
	public void flush() {
		snailFile.flush();
	}
}
