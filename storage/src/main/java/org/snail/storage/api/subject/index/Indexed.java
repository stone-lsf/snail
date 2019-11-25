package org.snail.storage.api.subject.index;

import lombok.Getter;

/**
 * @author shifeng.luo
 * @version created on 2019-11-25 13:45
 */
@Getter
public class Indexed {

	private final long sequence;

	private final byte[] data;

	private final int offset;

	public Indexed(long sequence, byte[] data, int offset) {
		this.sequence = sequence;
		this.data = data;
		this.offset = offset;
	}
}
