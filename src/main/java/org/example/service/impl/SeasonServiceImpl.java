package org.example.service.impl;

import org.example.model.Season;
import org.example.service.SeasonService;
import org.springframework.stereotype.Service;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Override
    public boolean isActive(final Season season, final String startAnalyze, final String endAnalyze) {
        final boolean sameYear = !isYearChange(season);
        return isActiveByYear(season, startAnalyze, endAnalyze, sameYear);
    }

    private boolean isActiveByYear(final Season season, final String analyzeStart, final String analyseEnd, final boolean sameYear) {
        return inSeasonPeriod(season, analyzeStart, sameYear) || inSeasonPeriod(season, analyseEnd, sameYear);
    }

    private boolean inSeasonPeriod(final Season season, final String analyzeDate, final boolean sameYear) {
        final String seasonStart = season.getStart();
        final String seasonEnd = season.getEnd();
        final int startDay = getDayNumber(seasonStart);
        final int endDay = getDayNumber(season.getEnd());
        final int startMonth = getMonthNumber(seasonStart);
        final int endMonth = getMonthNumber(seasonEnd);

        final int analyzeDay = getDayNumber(analyzeDate);
        final int analyzeMonth = getMonthNumber(analyzeDate);

        if (endMonth == analyzeMonth || startMonth == analyzeMonth) {
            return insideSameMonthRange(startMonth, analyzeMonth, startDay, endDay, analyzeDay);
        }

        return sameYear ? insideDiffMonthRange(startMonth, endMonth, analyzeMonth) : insideDiffMonthDiffYearRange(startMonth, endMonth, analyzeMonth);
    }

    private boolean isYearChange(final Season season) {
        final String seasonStart = season.getStart();
        final String seasonEnd = season.getEnd();
        final int startMonth = getMonthNumber(seasonStart);
        final int endMonth = getMonthNumber(seasonEnd);

        return endMonth < startMonth;
    }

    private boolean insideSameMonthRange(final int startMonth, final int analyzeMonth, final int startSeasonDay, final int endSeasonDay, final int analyzeDay) {
        if (startMonth == analyzeMonth) {
            return analyzeDay >= startSeasonDay;
        }

        return analyzeDay <= endSeasonDay;
    }

    private boolean insideDiffMonthRange(final int startSeasonMonth, final int endSeasonMonth, final int analyzeMonth) {
        return analyzeMonth > startSeasonMonth && analyzeMonth < endSeasonMonth;
    }

    private boolean insideDiffMonthDiffYearRange(final int startSeasonMonth, final int endSeasonMonth, final int analyzeMonth) {
        return analyzeMonth > startSeasonMonth || analyzeMonth < endSeasonMonth;
    }

    private int getDayNumber(final String date) {
        return Integer.parseInt(date.split("\\.")[0]);
    }

    private int getMonthNumber(final String date) {
        return Integer.parseInt(date.split("\\.")[1]);
    }
}
