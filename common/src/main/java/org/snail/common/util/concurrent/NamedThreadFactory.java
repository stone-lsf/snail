package org.snail.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.SuppressPackageLocation;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shifeng.luo
 * @version created on 2019-11-26 13:27
 */
@Slf4j
public class NamedThreadFactory implements ThreadFactory {

	private static final AtomicInteger POOL_SEQUENCE = new AtomicInteger(1);
	private final AtomicInteger threadNum = new AtomicInteger(1);
	private final String prefix;
	private final boolean daemon;
	private final ThreadGroup group;
	private final Thread.UncaughtExceptionHandler exceptionHandler;

	public NamedThreadFactory(String poolPrefix) {
		this(poolPrefix, false);
	}

	public NamedThreadFactory(String poolPrefix, Thread.UncaughtExceptionHandler exceptionHandler) {
		this(poolPrefix, false, exceptionHandler);
	}

	public NamedThreadFactory(String poolPrefix, boolean daemon) {
		this(poolPrefix, daemon, new LogUncaughtExceptionHandler());
	}

	public NamedThreadFactory(String poolPrefix, boolean daemon, Thread.UncaughtExceptionHandler exceptionHandler) {
		this.prefix = poolPrefix + "-" + POOL_SEQUENCE.getAndIncrement();
		this.daemon = daemon;

		SecurityManager securityManager = System.getSecurityManager();
		this.group = securityManager == null ? Thread.currentThread().getThreadGroup() : securityManager.getThreadGroup();
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public Thread newThread(Runnable runnable) {
		Preconditions.checkNotNull(runnable);

		String name = prefix + "-" + this.threadNum.getAndIncrement();
		Thread thread = new Thread(group, runnable, name);
		thread.setDaemon(daemon);
		thread.setUncaughtExceptionHandler(exceptionHandler);
		return thread;
	}

	private static class LogUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			log.error("thread:{} caught exception", t, e);
		}
	}

}
