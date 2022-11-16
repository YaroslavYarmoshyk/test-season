package org.example.service;

import org.example.model.Position;
import org.example.model.Season;

public interface SeasonService {
    boolean isActive(final Season season, final String startAnalyze, final String endAnalyze);
}
