package org.snail.storage.api.entry;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:16
 */
@Getter
@Setter
public class SnailEntry extends AbstractEntry {

	private static final int FIX_HEADER_SIZE = LENGTH_SIZE + CRC_SIZE + 1 + 4 + 8 + 4 + 1;

	/**
	 * 版本号
	 */
	private byte version;

	/**
	 * 消息首部大小
	 */
	private int headSize;

	/**
	 * 消息序号
	 */
	private long sequence;

	/**
	 * 文件偏移
	 */
	private int offset;

	/**
	 * 压缩算法
	 */
	private byte compress;

	/**
	 * 负载
	 */
	private byte[] payload;

	@Override
	public int getLength() {
		int dataSize = (payload == null ? 0 : payload.length);
		return FIX_HEADER_SIZE + dataSize;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {
		int position = buffer.position();
		int length = this.getLength();

		buffer.putInt(length);
		buffer.putInt(0);
		buffer.put(version);
		buffer.putInt(FIX_HEADER_SIZE);
		buffer.putLong(sequence);
		buffer.putInt(offset);
		buffer.put(compress);

		if (payload != null) {
			buffer.put(payload);
		}

		int crc32Value = calculateCrc32(buffer, position, length);
		buffer.putInt(position + LENGTH_SIZE, crc32Value);
	}

	public boolean checkEnough(ByteBuffer buffer) {
		buffer.mark();
		int length = buffer.getInt();
		boolean enough = buffer.remaining() >= length-LENGTH_SIZE;
		buffer.reset();
		return enough;
	}

	@Override
	public void readFrom(ByteBuffer buffer) {
		int position = buffer.position();
		int length = buffer.getInt();
		super.actualCrc32 = calculateCrc32(buffer, position, length);
		this.crc32 = buffer.getInt();

		this.version = buffer.get();
		this.headSize = buffer.getInt();
		this.sequence = buffer.getLong();
		this.offset = buffer.getInt();
		this.compress = buffer.get();

		if (length > headSize) {
			this.payload = new byte[length - headSize];
			buffer.get(payload);
		}
	}

	private int calculateCrc32(ByteBuffer buffer, int position, int length) {
		CRC32 crc32 = new CRC32();
		byte[] array = buffer.array();
		int offset = LENGTH_SIZE + CRC_SIZE;
		crc32.update(array, position + offset, length - offset);
		return (int) crc32.getValue();
	}
}
