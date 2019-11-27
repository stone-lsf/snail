package org.snail.storage.api.entry;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:23
 */
@Getter
@Setter
public class JournalEntry extends AbstractEntry {

	private static final int HEADER_SIZE = 4 + 4 + 4 + 8 + 4;

	private int subject;

	/**
	 * 文件偏移
	 */
	private int offset;

	/**
	 * 主题sequence
	 */
	private long subjectSequence;

	/**
	 * 主题sequence
	 */
	private int subjectOffset;

	private byte[] payload;

	@Override
	public int getLength() {
		int payloadLength = payload == null ? 0 : payload.length;
		return HEADER_SIZE + payloadLength;
	}

	@Override
	public byte[] serialize() {
		int size = this.getLength();
		ByteBuffer buffer = ByteBuffer.allocate(size);
		buffer.putInt(0);
		buffer.putInt(subject);
		buffer.putInt(offset);
		buffer.putInt(subjectOffset);
		buffer.putLong(subjectSequence);
		buffer.put(payload);

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
		this.subject = buffer.getInt();
		this.offset = buffer.getInt();
		this.subjectOffset = buffer.getInt();
		this.subjectSequence = buffer.getLong();
		this.payload = new byte[data.length - HEADER_SIZE];
		buffer.get(payload);
	}
}
