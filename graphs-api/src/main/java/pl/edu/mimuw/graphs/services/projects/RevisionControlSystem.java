package pl.edu.mimuw.graphs.services.projects;

public enum RevisionControlSystem {

	GIT("git");

	private String revisionControlName;

	RevisionControlSystem() {

	}

	RevisionControlSystem(String revisionControlSystem) {
		this.revisionControlName = revisionControlSystem;
	}

	public String getRevisionControlName() {
		return revisionControlName;
	}

}
