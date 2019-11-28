package org.snail.storage.impl.subject.file;

import org.snail.storage.api.entry.SnailEntry;
import org.snail.storage.impl.StorageConfig;

import java.io.File;

/**
 * @author shifeng.luo
 * @version created on 2019-11-28 13:35
 */
public class SnailChannelFile extends ChannelFile<SnailEntry> {

	public SnailChannelFile(File file) {
		super(file);
	}

	@Override
	public SnailEntry createEntry() {
		return new SnailEntry();
	}

	@Override
	public SnailEntry createEntry(long sequence, byte[] data) {
		SnailEntry entry = new SnailEntry();
		entry.setSequence(sequence);
		entry.setPayload(data);
		entry.setVersion(StorageConfig.VERSION);
		entry.setCompress(StorageConfig.COMPRESS);
		return entry;
	}
}
