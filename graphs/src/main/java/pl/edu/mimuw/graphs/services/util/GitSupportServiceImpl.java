package pl.edu.mimuw.graphs.services.util;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import pl.edu.mimuw.graphs.exceptions.GitException;
import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.services.api.GitSupportService;
import pl.edu.mimuw.graphs.services.api.PathResolvingService;

public class GitSupportServiceImpl implements GitSupportService {

	private PathResolvingService dirResolvingService;

	@Override
	public void cloneRepository(String name, String repoUlr) throws GraphsException {

		File file = new File(dirResolvingService.getProjectDir(name));
		if (file.list().length > 0) {
			throw new GitException("Project with name " + name + "aleready exists");
		}

		File clonedRepoDir = new File(dirResolvingService.getGitDir(name));
		clonedRepoDir.mkdirs();

		try {
			Git.cloneRepository().setURI(repoUlr).setDirectory(clonedRepoDir).call();
		} catch (InvalidRemoteException e) {
			throw new GitException(e);
		} catch (TransportException e) {
			throw new GitException(e);
		} catch (GitAPIException e) {
			throw new GitException(e);

		}
	}

	public PathResolvingService getDirResolvingService() {
		return dirResolvingService;
	}

	public void setDirResolvingService(PathResolvingService dirResolvingService) {
		this.dirResolvingService = dirResolvingService;
	}

}
