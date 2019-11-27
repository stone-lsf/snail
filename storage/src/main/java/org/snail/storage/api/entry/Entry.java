package org.snail.storage.api.entry;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:13
 */
public interface Entry {

	int CRC_SIZE = 4;

	int getOffset();

	void setOffset(int offset);

	int getLength();

	byte[] serialize();

	void deserialize(byte[] data);

	boolean validCrc32();
}
