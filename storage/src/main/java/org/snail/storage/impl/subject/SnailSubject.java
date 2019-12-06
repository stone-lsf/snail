package org.snail.storage.impl.subject;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.snail.storage.api.entry.IndexEntry;
import org.snail.storage.api.entry.SnailEntry;
import org.snail.storage.api.exceptions.StorageException;
import org.snail.storage.api.subject.Subject;
import org.snail.storage.api.subject.SubjectAppender;
import org.snail.storage.api.subject.SubjectReader;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.index.IndexPolicy;
import org.snail.storage.api.subject.segment.Segment;
import org.snail.storage.api.subject.segment.SegmentDescriptor;
import org.snail.storage.impl.subject.file.SnailChannelFile;
import org.snail.storage.impl.subject.segment.SnailSegment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author shifeng.luo
 * @version created on 2019-11-20 13:32
 */
@Getter
@Setter
public class SnailSubject implements Subject {

	private static final int SEGMENT_BUFFER_FACTOR = 3;

	private final File path;

	private final String name;

	private final int maxSegmentSize;

	private final IndexPolicy indexPolicy;

	private AtomicBoolean started = new AtomicBoolean(false);

	private NavigableMap<SegmentDescriptor, Segment> segmentMap = new ConcurrentSkipListMap<>();

	public SnailSubject(File path, int maxSegmentSize, IndexPolicy indexPolicy) {
		this.path = path;
		this.name = path.getName();
		this.maxSegmentSize = maxSegmentSize;
		this.indexPolicy = indexPolicy;
	}


	@Override
	public String name() {
		return name;
	}

	@Override
	public void start() {
		path.mkdirs();
		File[] files = path.listFiles(File::isFile);
		if (files == null) {
			return;
		}

		Map<String, SnailFile<SnailEntry>> dataFileMap = new HashMap<>();
		Map<String, SnailFile<IndexEntry>> indexFileMap = new HashMap<>();
		for (File file : files) {
			if (SegmentDescriptor.isSegmentData(file)) {
				SegmentDescriptor descriptor = new SegmentDescriptor();
				dataFileMap.put(descriptor.getSegment(), new SnailChannelFile<>(file, SnailEntry.class));
			}

			if (SegmentDescriptor.isSegmentIndex(file)) {
				SegmentDescriptor descriptor = new SegmentDescriptor();
				indexFileMap.put(descriptor.getSegment(), new SnailChannelFile<>(file, IndexEntry.class));
			}
		}

		for (String segment : dataFileMap.keySet()) {
			SnailFile<SnailEntry> snailFile = dataFileMap.get(segment);
			SnailFile<IndexEntry> indexFile = indexFileMap.get(segment);

			Segment sg = new SnailSegment(snailFile, indexFile, indexPolicy);
//			segmentMap.put(,sg);
		}

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
		assertDiskSpace();
		return null;
	}

	private void checkStarted() {
		Preconditions.checkState(started.get(), "Storage not start!");
	}

	private void assertDiskSpace() {
		if (path.getUsableSpace() < maxSegmentSize * SEGMENT_BUFFER_FACTOR) {
			throw new StorageException.OutOfDiskSpace("Not enough space to allocate a new segment");
		}
	}
}
