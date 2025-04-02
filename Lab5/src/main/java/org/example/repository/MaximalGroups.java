package org.example.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MaximalGroups {
    private final ImageRepository imageRepository;
    public MaximalGroups(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<List<ImageItem>> run() {
        if(imageRepository == null)
        {
            throw new NullPointerException("imageRepository is null");
        }
        var allImages = imageRepository.getImages();
        List<List<ImageItem>> groups = new ArrayList<>();
        Set<String> currentGroup = new HashSet<>();

        while (!allImages.isEmpty())
        {
            List<ImageItem> currList = new ArrayList<>();
            var firstImage = allImages.get(0);
            currList.add(firstImage);
            currentGroup.addAll(firstImage.tags());

            for(int i = 1; i < allImages.size(); i++)
            {
                var currImg = allImages.get(i);
                if(currImg.tags().stream().anyMatch(currentGroup::contains))
                {
                    currList.add(currImg);
                    allImages.remove(i);
                    i--;
                }
            }
            allImages.remove(firstImage);
            groups.add(currList);
            currentGroup.clear();
        }
        return groups;
    }
}
