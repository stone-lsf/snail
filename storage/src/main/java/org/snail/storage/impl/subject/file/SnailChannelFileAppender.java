package org.snail.storage.impl.subject.file;

import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileAppender;
import org.snail.storage.api.subject.index.Indexed;

import java.nio.channels.FileChannel;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:54
 */
public class SnailChannelFileAppender implements SnailFileAppender {

	private final SnailFile file;
	private final FileChannel channel;

	public SnailChannelFileAppender(SnailFile file, FileChannel channel) {
		this.file = file;
		this.channel = channel;
	}

	@Override
	public Indexed append(long sequence, byte[] data) {
		return null;
	}
}
