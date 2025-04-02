package org.example.persistence;

import org.example.repository.RepositoryManager;

public class OtherPersistence extends PersistenceMode{
    public OtherPersistence(RepositoryManager imageRepository) {
        super(imageRepository);
    }

    @Override
    public void load(String repoName) {

    }

    @Override
    public void save(String repoName) {

    }
}
