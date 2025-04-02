package org.example.persistence;

import org.example.repository.RepositoryManager;

public class BinaryPersistence extends PersistenceMode {

    public BinaryPersistence(RepositoryManager imageRepository) {
        super(imageRepository);
    }

    @Override
    public void load(String repoName) {

    }

    @Override
    public void save(String repoName) {

    }
}
