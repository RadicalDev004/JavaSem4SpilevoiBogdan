package org.example;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

record ImageItem(String name, LocalDate date, List<String> tags, Path location) {}
