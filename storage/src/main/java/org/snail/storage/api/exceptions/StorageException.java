package org.snail.storage.api.exceptions;

/**
 * @author shifeng.luo
 * @version created on 2019-11-19 13:48
 */
public class StorageException extends RuntimeException {

	public StorageException() {
	}

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageException(Throwable cause) {
		super(cause);
	}

	/**
	 * Exception thrown when an entry being stored is too large.
	 */
	public static class TooLarge extends StorageException {

		public TooLarge(String message) {
			super(message);
		}
	}

	/**
	 * Exception thrown when storage runs out of disk space.
	 */
	public static class OutOfDiskSpace extends StorageException {

		public OutOfDiskSpace(String message) {
			super(message);
		}
	}

	/**
	 * Exception thrown when data was bad.
	 */
	public static class BadDataException extends StorageException {

		public BadDataException(String message) {
			super(message);
		}
	}

}
