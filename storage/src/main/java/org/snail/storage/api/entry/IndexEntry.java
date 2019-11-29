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

	private static final int SIZE = 4 + 4 + 4 + 8;

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

	@Override
	public int getLength() {
		return SIZE;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {

	}

	@Override
	public void readFrom(ByteBuffer buffer) {

	}

	public byte[] serialize() {
		int size = this.getLength();
		ByteBuffer buffer = ByteBuffer.allocate(size);

		buffer.putInt(crc32);
		buffer.putInt(indexOffset);
		buffer.putInt(offset);
		buffer.putLong(indexSequence);

		byte[] array = buffer.array();
		CRC32 crc32 = new CRC32();
		crc32.update(array, CRC_SIZE, size - CRC_SIZE);
		int crc32Value = (int) crc32.getValue();
		buffer.putInt(0, crc32Value);

		return array;
	}

	public void deserialize(byte[] data) {
		CRC32 crc32 = new CRC32();
		crc32.update(data, CRC_SIZE, data.length - CRC_SIZE);
		super.actualCrc32 = (int) crc32.getValue();

		ByteBuffer buffer = ByteBuffer.wrap(data);
		this.crc32 = buffer.getInt();
		this.indexOffset = buffer.getInt();
		this.offset = buffer.getInt();
		this.indexSequence = buffer.getLong();
	}

	@Override
	public boolean checkEnough(ByteBuffer buffer) {
		return false;
	}
}
