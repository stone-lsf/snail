package org.snail.storage.segment.subject;

import org.snail.storage.api.subject.Subject;
import org.snail.storage.api.subject.SubjectAppender;
import org.snail.storage.api.subject.segment.Segment;
import org.snail.storage.api.subject.segment.SegmentAppender;

/**
 * @author shifeng.luo
 * @version created on 2019-11-20 13:33
 */
public class SubjectSegmentAppender implements SubjectAppender {

	private final Subject subject;
	private Segment currentSegment;
	private SegmentAppender segmentAppender;

	public SubjectSegmentAppender(Subject subject) {
		this.subject = subject;
	}

	@Override
	public void append(byte[] entry) {

	}
}
