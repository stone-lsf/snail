package org.snail.storage.api.entry;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:22
 */
@Getter
@Setter
public class IndexEntry extends AbstractEntry {

	private static final int LENGTH = CRC_SIZE + 4 + 8 + 4;

	/**
	 * 文件偏移
	 */
	private int offset;

	/**
	 * 索引的消息序号
	 */
	private long indexSequence;

	/**
	 * 索引的消息在文件中的偏移
	 */
	private int indexOffset;


	public static IndexEntry from(long sequence, int location) {
		IndexEntry entry = new IndexEntry();

		entry.setIndexSequence(sequence);
		entry.setIndexOffset(location);

		return entry;
	}

	public int getLength() {
		return LENGTH;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {
		int position = buffer.position();

		buffer.putInt(0);
		buffer.putInt(offset);
		buffer.putLong(indexSequence);
		buffer.putInt(indexOffset);

		int crc32Value = calculateCrc32(buffer, position);
		buffer.putInt(position, crc32Value);
	}

	@Override
	public void readFrom(ByteBuffer buffer) {
		int position = buffer.position();
		super.actualCrc32 = calculateCrc32(buffer, position);
		this.crc32 = buffer.getInt();

		this.offset = buffer.getInt();
		this.indexSequence = buffer.getLong();
		this.indexOffset = buffer.getInt();
	}

	@Override
	public boolean checkEnough(ByteBuffer buffer) {
		buffer.mark();
		boolean enough = buffer.remaining() >= LENGTH;
		buffer.reset();
		return enough;
	}

	private int calculateCrc32(ByteBuffer buffer, int position) {
		CRC32 crc32 = new CRC32();
		byte[] array = buffer.array();
		crc32.update(array, position + CRC_SIZE, LENGTH - CRC_SIZE);
		return (int) crc32.getValue();
	}
}
