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

	private static final int FIX_HEADER_SIZE = 4 + 4 + 8 + 4 + 2;

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
	 * 版本号
	 */
	private byte version;

	/**
	 * 压缩算法
	 */
	private byte compress;

	/**
	 * 负载
	 */
	private byte[] payload;

	@Override
	public int getSize() {
		int dataSize = (payload == null ? 0 : payload.length);
		return FIX_HEADER_SIZE + dataSize;
	}

	@Override
	public byte[] serialize() {
		int size = getSize();

		ByteBuffer buffer = ByteBuffer.allocate(size);
		buffer.putInt(0);
		buffer.putInt(FIX_HEADER_SIZE);
		buffer.putLong(sequence);
		buffer.putInt(offset);
		buffer.put(version);
		buffer.put(compress);

		if (payload != null) {
			buffer.put(payload);
		}

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
		this.headSize = buffer.getInt();
		this.sequence = buffer.getLong();
		this.offset = buffer.getInt();
		this.version = buffer.get();
		this.compress = buffer.get();

		if (data.length > headSize) {
			this.payload = new byte[data.length - headSize];
			buffer.get(payload);
		}
	}
}
