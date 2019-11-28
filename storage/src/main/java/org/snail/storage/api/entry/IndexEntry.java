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

	private static final int SIZE = 4 + +4 + 4 + 8;

	/**
	 * 消息在文件中的位置
	 */
	private int location;

	/**
	 * 文件偏移
	 */
	private int offset;

	/**
	 * 消息序号
	 */
	private long sequence;


	public static IndexEntry from(long sequence, int location) {
		IndexEntry entry = new IndexEntry();

		entry.setSequence(sequence);
		entry.setLocation(location);

		return entry;
	}

	@Override
	public int getLength() {
		return SIZE;
	}

	@Override
	public byte[] serialize() {
		int size = this.getLength();
		ByteBuffer buffer = ByteBuffer.allocate(size);

		buffer.putInt(crc32);
		buffer.putInt(location);
		buffer.putInt(offset);
		buffer.putLong(sequence);

		byte[] array = buffer.array();
		CRC32 crc32 = new CRC32();
		crc32.update(array, CRC_SIZE, size - CRC_SIZE);
		int crc32Value = (int) crc32.getValue();
		buffer.putInt(0, crc32Value);

		return array;
	}

	@Override
	public void deserialize(byte[] data) {
		CRC32 crc32 = new CRC32();
		crc32.update(data, CRC_SIZE, data.length - CRC_SIZE);
		super.actualCrc32 = (int) crc32.getValue();

		ByteBuffer buffer = ByteBuffer.wrap(data);
		this.crc32 = buffer.getInt();
		this.location = buffer.getInt();
		this.offset = buffer.getInt();
		this.sequence = buffer.getLong();
	}

	@Override
	public boolean checkEnough(ByteBuffer buffer) {
		return false;
	}
}
