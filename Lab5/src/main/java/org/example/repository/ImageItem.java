package org.example.repository;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public record ImageItem(String name, List<String> tags, LocalDate date, Path location) {}
