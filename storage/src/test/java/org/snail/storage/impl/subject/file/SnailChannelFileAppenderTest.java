package org.snail.storage.impl.subject.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.snail.storage.BaseTest;
import org.snail.storage.api.subject.file.SnailFile;
import org.snail.storage.api.subject.file.SnailFileAppender;

import java.io.File;
import java.util.Date;

/**
 * @author shifeng.luo
 * @version created on 2019-11-27 14:39
 */
@Slf4j
public class SnailChannelFileAppenderTest extends BaseTest {

	private File file = new File(storagePath + "/test.log");
	private SnailFile snailFile;
	private SnailFileAppender appender;

	@Before
	public void setUp() throws Exception {
		snailFile = new SnailChannelFile(file);
		appender = snailFile.appender();
	}

	@After
	public void tearDown() throws Exception {
		snailFile.close();
	}

	@Test
	public void append() {
		log.info("=============="+new Date());
		String value = "test";
		for (long i=0;i<100L;i++){
			String data = value+i;
			appender.append(i,data.getBytes());
		}

		log.info("=============="+new Date());
	}

	@Test
	public void flush() {
		appender.flush();
	}
}