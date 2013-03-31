package pl.edu.mimuw.graphs.services.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.exceptions.InvalidDataExcetion;
import pl.edu.mimuw.graphs.services.api.PathResolvingService;

import com.google.common.base.Strings;

public class PathResolvingServiceImpl implements PathResolvingService {

	public final static Logger log = LoggerFactory.getLogger(PathResolvingServiceImpl.class);

	private String projectDirPrefix;
	private String gitRepoSuffix;
	private String dbDirSuffix;
	private final static String PROPERTIES_FILE = ".properties";

	private void checkAndCreateDir(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	@Override
	public String getGitDir(String projectName) throws InvalidDataExcetion {
		String path = getProjectDir(projectName) + gitRepoSuffix;
		checkAndCreateDir(path);
		return path;
	}

	@Override
	public String getDbDir(String projectName) throws InvalidDataExcetion {
		String path = getProjectDir(projectName) + dbDirSuffix;
		checkAndCreateDir(path);
		return path;
	}

	@Override
	public String getProjectDir(String projectName) throws InvalidDataExcetion {
		if (Strings.isNullOrEmpty(projectName))
			throw new InvalidDataExcetion("Project name is null or empty");
		String path = projectDirPrefix + projectName + "/";
		checkAndCreateDir(path);
		return path;
	}

	@Override
	public String getPropertiesFile(String projectName) throws GraphsException {
		String path = getProjectDir(projectName) + projectName + PROPERTIES_FILE;
		File file = new File(path);
		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					throw new GraphsException("failed to create file " + path);
				}
			} catch (IOException e) {
				throw new GraphsException(e);
			}
		}
		return path;
	}

	public String getProjectDirPrefix() {
		return projectDirPrefix;
	}

	public void setProjectDirPrefix(String projectDirPrefix) {
		this.projectDirPrefix = projectDirPrefix;
	}

	public String getGitRepoSuffix() {
		return gitRepoSuffix;
	}

	public void setGitRepoSuffix(String gitRepoSuffix) {
		this.gitRepoSuffix = gitRepoSuffix;
	}

	public String getDbDirSuffix() {
		return dbDirSuffix;
	}

	public void setDbDirSuffix(String dbDirSuffix) {
		this.dbDirSuffix = dbDirSuffix;
	}

}
