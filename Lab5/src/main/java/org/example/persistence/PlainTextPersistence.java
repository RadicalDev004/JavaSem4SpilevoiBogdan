package org.example.persistence;

import org.example.repository.RepositoryManager;

public class PlainTextPersistence extends PersistenceMode {
    public PlainTextPersistence(RepositoryManager imageRepository) {
        super(imageRepository);
    }

    @Override
    public void load(String repoName) {

    }

    @Override
    public void save(String repoName) {

    }
}
