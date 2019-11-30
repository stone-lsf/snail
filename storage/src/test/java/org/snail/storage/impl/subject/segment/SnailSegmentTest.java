package org.snail.storage.impl.subject.segment;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.snail.storage.BaseTest;
import org.snail.storage.api.entry.IndexEntry;
import org.snail.storage.api.entry.SnailEntry;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.segment.Segment;
import org.snail.storage.api.subject.segment.SegmentAppender;
import org.snail.storage.impl.subject.file.SnailChannelFile;

import java.io.File;
import java.util.Date;

/**
 * @author shifeng.luo
 * @version created on 2019-11-29 15:07
 */
@Slf4j
public class SnailSegmentTest extends BaseTest {


	private File data = new File(storagePath + "/data.log");
	private File index = new File(storagePath + "/index.log");

	private Segment segment;
	private SegmentAppender appender;

	@Before
	public void setUp() throws Exception {
		SnailFile<SnailEntry> dataFile = new SnailChannelFile<>(data, SnailEntry.class);
		SnailFile<IndexEntry> indexFile = new SnailChannelFile<>(index, IndexEntry.class);

		segment = new SnailSegment(dataFile, indexFile, (sequence, offset) -> sequence % 4 == 0);
	}

	@After
	public void tearDown() throws Exception {
		appender.flush();
	}

	@Test
	public void appender() {
		appender = segment.appender();
		log.info("==============" + new Date());
		String value = "test";
		for (long i = 0; i < 100L; i++) {
			String data = value + i;

			appender.append(i, data.getBytes());
		}

		log.info("==============" + new Date());
	}

	@Test
	public void openReader() {
	}
}