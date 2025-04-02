package org.example.persistence;

import org.example.repository.RepositoryManager;

public abstract class PersistenceMode {

    private RepositoryManager repositoryManager;

    public RepositoryManager getImageRepository() {
        return repositoryManager;
    }

    public PersistenceMode(RepositoryManager imageRepository) {
        this.repositoryManager = imageRepository;
    }

    public abstract void load(String repoName);
    public abstract void save(String repoName);
}
