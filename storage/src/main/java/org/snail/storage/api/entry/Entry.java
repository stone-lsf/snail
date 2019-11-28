package org.snail.storage.api.entry;

import java.nio.ByteBuffer;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:13
 */
public interface Entry {

	int CRC_SIZE = 4;
	int LENGTH_SIZE = 4;

	int getOffset();

	void setOffset(int offset);

	int getLength();

	void writeTo(ByteBuffer buffer);

	void readFrom(ByteBuffer buffer);

	boolean validCrc32();

	boolean checkEnough(ByteBuffer buffer);
}
