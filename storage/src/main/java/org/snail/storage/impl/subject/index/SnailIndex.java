package org.snail.storage.impl.subject.index;

import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.subject.index.IndexFile;
import org.snail.storage.impl.subject.file.MappedFile;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:36
 */
public class SnailIndex extends MappedFile implements IndexFile {

	@Override
	public void index(long sequence, int offset) {

	}

	@Override
	public Entry createEntry(long sequence, byte[] data) {
		return null;
	}
}
