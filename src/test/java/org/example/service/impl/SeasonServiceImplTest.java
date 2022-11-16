package org.example.service.impl;

import org.example.model.Season;
import org.example.service.SeasonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SeasonServiceImplTest {
    private static SeasonService seasonService;

    @BeforeAll
    static void setUp() {
        seasonService = new SeasonServiceImpl();
    }

    @Test
    @Order(1)
    @DisplayName("Test end analyze date inside the period")
    void isActive_endDateInPeriod_SameMonth() {
        final String startAnalyze = "11.11";
        final String endAnalyze = "16.11";
        final Season saintNicholas = getSaintNicholas();
        final boolean actual = seasonService.isActive(saintNicholas, startAnalyze, endAnalyze);

        assertTrue(actual, "Season should be true, as end analyze date: " + endAnalyze + " falls in the period: " + saintNicholas.getStart() + " - " + saintNicholas.getEnd());
    }

    @Test
    @Order(2)
    @DisplayName("Test start analyze date inside the period")
    void isActive_startDateInPeriod_SameMonth() {
        final String startAnalyze = "30.12";
        final String endAnalyze = "01.01";
        final Season saintNicholas = getSaintNicholas();
        final boolean actual = seasonService.isActive(saintNicholas, startAnalyze, endAnalyze);

        assertTrue(actual, "Season should be true, as start analyze date: " + startAnalyze + " falls in the period: " + saintNicholas.getStart() + " - " + saintNicholas.getEnd());
    }

    @Test
    @Order(3)
    @DisplayName("Test start and end analyze dates inside period")
    void isActive_startAndEndDatesInPeriod_SameMonth() {
        final String startAnalyze = "16.11";
        final String endAnalyze = "30.12";
        final Season saintNicholas = getSaintNicholas();
        final boolean actual = seasonService.isActive(saintNicholas, startAnalyze, endAnalyze);

        assertTrue(actual, "Season should be true, as start analyze date: " + startAnalyze + " and end analyze date: " + endAnalyze + " are falls in the period: " + saintNicholas.getStart() + " - " + saintNicholas.getEnd());
    }

    @Test
    @Order(4)
    @DisplayName("Test start and end analyze dates inside period between month")
    void isActive_startAndEndDatesInPeriod_DiffMonth() {
        final String startAnalyze = "16.10";
        final String endAnalyze = "30.11";
        final Season saintNicholas = getSaintNicholas();
        saintNicholas.setStart("01.01");
        saintNicholas.setEnd("01.12");
        final boolean actual = seasonService.isActive(saintNicholas, startAnalyze, endAnalyze);

        assertTrue(actual, "Season should be true, as start analyze date: " + startAnalyze + " and end analyze date: " + endAnalyze + " are falls in the period: " + saintNicholas.getStart() + " - " + saintNicholas.getEnd());
    }

    @Test
    @Order(5)
    @DisplayName("Test start and end analyze dates are outside period")
    void isActive_startAndEndDatesOutsidePeriod_SameMonth() {
        final String startAnalyze = "01.01";
        final String endAnalyze = "10.11";
        final Season saintNicholas = getSaintNicholas();
        final boolean actual = seasonService.isActive(saintNicholas, startAnalyze, endAnalyze);

        assertFalse(actual, "Season should be false, as start analyze date: " + startAnalyze + " and end analyze date: " + endAnalyze + " outside the period: " + saintNicholas.getStart() + " - " + saintNicholas.getEnd());
    }
    @Test
    @Order(6)
    @DisplayName("Test start and end analyze dates are outside period between months")
    void isActive_startAndEndDatesOutsidePeriod_DiffMonth() {
        final String startAnalyze = "01.01";
        final String endAnalyze = "10.11";
        final Season saintNicholas = getSaintNicholas();
        saintNicholas.setStart("11.11");
        final boolean actual = seasonService.isActive(saintNicholas, startAnalyze, endAnalyze);

        assertFalse(actual, "Season should be false, as start analyze date: " + startAnalyze + " and end analyze date: " + endAnalyze + " outside the period: " + saintNicholas.getStart() + " - " + saintNicholas.getEnd());
    }


    @Test
    @Order(7)
    @DisplayName("Test analyze date inside the period")
    void isActive_certainDateInPeriod_DiffMonth() {
        final String analyzeDate = "11.12";
        final Season saintNicholas = getSaintNicholas();
        final boolean actual = seasonService.isActive(saintNicholas, analyzeDate, analyzeDate);

        assertTrue(actual, "Season should be true, as analyze date: " + analyzeDate + " falls in the period: " + saintNicholas.getStart() + " - " + saintNicholas.getEnd());
    }

    @Test
    @Order(8)
    @DisplayName("Test analyze date inside the period between months")
    void isActive_certainDateInPeriod_DiffMonth_Between() {
        final String analyzeDate = "02.10";
        final Season saintNicholas = getSaintNicholas();
        saintNicholas.setStart("09.08");
        final boolean actual = seasonService.isActive(saintNicholas, analyzeDate, analyzeDate);

        assertTrue(actual, "Season should be true, as analyze date: " + analyzeDate + " falls in the period: " + saintNicholas.getStart() + " - " + saintNicholas.getEnd());
    }

    @Test
    @Order(9)
    @DisplayName("Test analyze date before the period")
    void isActive_certainDateOutsidePeriod_Before() {
        final String analyzeDate = "02.10";
        final Season saintNicholas = getSaintNicholas();
        final boolean actual = seasonService.isActive(saintNicholas, analyzeDate, analyzeDate);

        assertFalse(actual, "Season should be false, as analyze date: " + analyzeDate + " is before the period: " + saintNicholas.getStart() + " - " + saintNicholas.getEnd());
    }

    @Test
    @Order(10)
    @DisplayName("Test analyze date after the period")
    void isActive_certainDateOutsidePeriod_After() {
        final String analyzeDate = "31.12";
        final Season saintNicholas = getSaintNicholas();
        final boolean actual = seasonService.isActive(saintNicholas, analyzeDate, analyzeDate);

        assertFalse(actual, "Season should be false, as analyze date: " + analyzeDate + " is after the period: " + saintNicholas.getStart() + " - " + saintNicholas.getEnd());
    }

    @Test
    @Order(11)
    @DisplayName("Test start analyze date inside the period with change year")
    void isActive_startDateInChangePeriod_SameMonth() {
        final String startAnalyze = "01.02";
        final String endAnalyze = "01.03";
        final Season newYear = getNewYear();
        final boolean actual = seasonService.isActive(newYear, startAnalyze, endAnalyze);

        assertTrue(actual, "Season should be true, as end analyze date: " + endAnalyze + " falls in the period: " + newYear.getStart() + " - " + newYear.getEnd());
    }

    @Test
    @Order(12)
    @DisplayName("Test end analyze date inside the period with change year")
    void isActive_endDateInChangePeriod_DiffMonth() {
        final String startAnalyze = "11.11";
        final String endAnalyze = "02.01";
        final Season newYear = getNewYear();
        final boolean actual = seasonService.isActive(newYear, startAnalyze, endAnalyze);

        assertTrue(actual, "Season should be true, as end analyze date: " + endAnalyze + " falls in the period: " + newYear.getStart() + " - " + newYear.getEnd());
    }

    @Test
    @Order(13)
    @DisplayName("Test start and end analyze dates inside the period with change year")
    void isActive_startAndEndDateInChangePeriod_DiffMonth() {
        final String startAnalyze = "31.12";
        final String endAnalyze = "02.01";
        final Season newYear = getNewYear();
        final boolean actual = seasonService.isActive(newYear, startAnalyze, endAnalyze);

        assertTrue(actual, "Season should be true, as end analyze date: " + endAnalyze + " falls in the period: " + newYear.getStart() + " - " + newYear.getEnd());
    }


    @Test
    @Order(14)
    @DisplayName("Test start and end analyze dates outside the period with change year")
    void isActive_startAndEndDateOutsideChangePeriod_DiffMonth() {
        final String startAnalyze = "03.02";
        final String endAnalyze = "30.11";
        final Season newYear = getNewYear();
        final boolean actual = seasonService.isActive(newYear, startAnalyze, endAnalyze);

        assertFalse(actual, "Season should be false, as start and end analyze dates outside the period: " + newYear.getStart() + " - " + newYear.getEnd());
    }

    @Test
    @Order(15)
    @DisplayName("Test analyze date outside the period with change year after")
    void isActive_analyzeDateOutsideChangePeriod_DiffMonth_After() {
        final String analyzeDate = "03.03";
        final Season newYear = getNewYear();
        final boolean actual = seasonService.isActive(newYear, analyzeDate, analyzeDate);

        assertFalse(actual, "Season should be false, as analyze dates outside the period: " + newYear.getStart() + " - " + newYear.getEnd());
    }

    @Test
    @Order(16)
    @DisplayName("Test analyze date outside the period with change year before")
    void isActive_analyzeDateOutsideChangePeriod_DiffMonth_Before() {
        final String analyzeDate = "01.11";
        final Season newYear = getNewYear();
        final boolean actual = seasonService.isActive(newYear, analyzeDate, analyzeDate);

        assertFalse(actual, "Season should be false, as analyze dates outside the period: " + newYear.getStart() + " - " + newYear.getEnd());
    }

    @Test
    @Order(17)
    @DisplayName("Test analyze date inside the period diff month")
    void isActive_analyzeDateInsideChangePeriod_DiffMonth() {
        final String analyzeDate = "01.05";
        final Season newYear = getEaster();
        final boolean actual = seasonService.isActive(newYear, analyzeDate, analyzeDate);

        assertTrue(actual, "Season should be true, as analyze dates inside the period: " + newYear.getStart() + " - " + newYear.getEnd());
    }

    private Season getSaintNicholas() {
        return new Season("Saint Nicholas", "15.11", "30.12");
    }

    private Season getNewYear() {
        return new Season("New Year", "01.12", "02.02");
    }

    private Season getEaster() {
        return new Season("Easter", "01.04", "30.06");
    }
}