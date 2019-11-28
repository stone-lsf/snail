package org.snail.storage.impl.subject.file;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.snail.storage.BaseTest;
import org.snail.storage.api.entry.Entry;
import org.snail.storage.api.entry.SnailEntry;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileReader;

import java.io.File;

/**
 * @author shifeng.luo
 * @version created on 2019-11-28 13:14
 */
public class SnailChannelFileReaderTest extends BaseTest {

	private File file = new File(storagePath + "/test.log");
	private SnailFile<SnailEntry> snailFile;
	private SnailFileReader<SnailEntry> reader;

	@Before
	public void setUp() throws Exception {
		snailFile = new SnailChannelFile(file);
		reader = snailFile.openReader(0);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void next() {
		while (reader.hasNext()){
			SnailEntry next = reader.next();
			System.out.println(new String(next.getPayload()));
		}
	}

	@Test
	public void reset() {
	}

	@Test
	public void hasNext() {
	}
}