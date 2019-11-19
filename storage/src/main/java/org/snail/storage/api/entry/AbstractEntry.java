package org.snail.storage.api.entry;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:14
 */
public abstract class AbstractEntry implements Entry{

	/**
	 * 实际读取数据的crc32
	 */
	protected int actualCrc32;

	/**
	 * 32位循环校验码
	 */
	protected int crc32;

	@Override
	public boolean validCrc32() {
		return actualCrc32 == crc32;
	}
}
