package org.snail.storage.impl.subject;

import lombok.extern.slf4j.Slf4j;
import org.snail.storage.api.subject.RollingPolicy;
import org.snail.storage.api.subject.Subject;
import org.snail.storage.api.subject.SubjectAppender;
import org.snail.storage.api.subject.segment.Segment;
import org.snail.storage.api.subject.segment.SegmentAppender;

import java.io.IOException;

/**
 * rolling the segment
 *
 * @author shifeng.luo
 * @version created on 2019-11-20 13:33
 */
@Slf4j
public class SnailSubjectAppender implements SubjectAppender {

	private final Subject subject;
	private final RollingPolicy policy;
	private Segment currentSegment;
	private SegmentAppender currentAppender;

	public SnailSubjectAppender(Subject subject, RollingPolicy policy) {
		this.subject = subject;
		this.policy = policy;
		this.currentSegment = subject.lastSegment();
		this.currentAppender = currentSegment.appender();
	}

	@Override
	public void append(byte[] data) {
//		SnailEntry entry = new SnailEntry();
//		entry.setSequence(sequence);
//		entry.setPayload(data);

		checkRolling(data);
		long sequence = subject.nextSequence();
		currentAppender.append(sequence, data);
	}

	@Override
	public void commit(long index) {

	}

	@Override
	public void reset(long index) {

	}

	@Override
	public void truncate(long index) {

	}

	@Override
	public void flush() {
		currentAppender.flush();
	}

	private void checkRolling(byte[] data) {
		if (policy.triggerRolling(data, currentSegment)) {
			try {
				rolling();
			} catch (IOException e) {
				log.error("rolling file caught ", e);
				System.exit(-1);
			}
		}
	}

	private void rolling() throws IOException {
		if (currentSegment != null) {
			currentAppender.close();
		}
		currentSegment = subject.next();
		currentAppender = currentSegment.appender();
	}
}
