package org.snail.common.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shifeng.luo
 * @version created on 2018/12/10 下午7:41
 */
@Slf4j
public class FileUtil {

	public static void ensureDirectory(String directory) {
		File file = new File(directory);

		if (file.exists()) {
			if (!file.isDirectory()) {
				log.error(":{} not a directory", directory);
				throw new IllegalArgumentException("not directory");
			}
			return;
		}

		file.mkdirs();
	}

	public static List<File> getFiles(String path) {
		return getFiles(path, (FileFilter) null);
	}

	public static List<File> getFiles(String path, FileFilter filter) {
		Preconditions.checkState(StringUtils.isNotBlank(path));

		File parent = new File(path);
		if (!parent.exists() || !parent.isDirectory()) {
			log.error("path:{} not exist or not a directory", path);
			throw new IllegalArgumentException("path not exist or not directory");
		}

		File[] files = (filter != null) ? parent.listFiles(filter) : parent.listFiles();
		return files == null ? new ArrayList<>() : Lists.newArrayList(files);
	}

	public static List<File> getFiles(String path, FilenameFilter filter) {
		Preconditions.checkState(StringUtils.isNotBlank(path));
		Preconditions.checkNotNull(filter);

		File parent = new File(path);
		if (!parent.exists() || !parent.isDirectory()) {
			log.error("path:{} not exist or not a directory", path);
			throw new IllegalArgumentException("path not exist or not directory");
		}

		File[] files = parent.listFiles(filter);
		return files == null ? new ArrayList<>() : Lists.newArrayList(files);
	}
}
