package org.snail.storage.impl;

/**
 * @author shifeng.luo
 * @version created on 2019-11-26 12:56
 */
public class StorageConfig {

	public static final byte VERSION = 1;

	public static final byte COMPRESS = 0;

	public static final int FILE_BUFFER_SIZE = 32 * 1024 * 1024;

	public static final int FILE_READ_BUFFER_SIZE = 4 * 1024 * 1024;
}
