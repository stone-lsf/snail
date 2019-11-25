package org.snail.storage.impl.subject;

import com.google.common.base.Preconditions;
import org.snail.storage.api.subject.Subject;
import org.snail.storage.api.subject.SubjectAppender;
import org.snail.storage.api.subject.SubjectReader;
import org.snail.storage.api.subject.segment.Segment;
import org.snail.storage.api.subject.segment.SegmentManager;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author shifeng.luo
 * @version created on 2019-11-20 13:32
 */
public class SnailSubject implements Subject {

	private final File path;

	private final String name;

	private AtomicBoolean started = new AtomicBoolean(false);

	private SegmentManager snailFileManager;

	public SnailSubject(File path) {
		this.path = path;
		this.name = path.getName();
	}


	@Override
	public String name() {
		return name;
	}

	@Override
	public void start() {

	}

	@Override
	public SubjectReader openReader(long sequence) {
		checkStarted();
		return null;
	}

	@Override
	public SubjectAppender appender() {
		checkStarted();
		return null;
	}

	@Override
	public long nextSequence() {
		checkStarted();
		return 0;
	}

	@Override
	public Segment lastSegment() {
		checkStarted();
		return null;
	}

	@Override
	public Segment firstSegment() {
		checkStarted();
		return null;
	}

	@Override
	public Segment next() {
		checkStarted();
		return null;
	}

	private void checkStarted() {
		Preconditions.checkState(started.get(), "Storage not start!");
	}
}
