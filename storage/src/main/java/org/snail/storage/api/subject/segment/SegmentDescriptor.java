package org.snail.storage.api.subject.segment;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * @author shifeng.luo
 * @version created on 2019-11-20 13:16
 */
@Getter
@Setter
public class SegmentDescriptor implements Comparable<SegmentDescriptor>{

	private String segment;

	public static boolean isSegmentData(File file) {
		return false;
	}

	public static boolean isSegmentIndex(File file) {
		return false;
	}

	@Override
	public int compareTo(SegmentDescriptor o) {
		return 0;
	}
}
