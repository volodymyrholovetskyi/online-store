package ua.vholovetskyi.report;

import ua.vholovetskyi.service.dto.Statistics;

public interface OrderStatisticalReport {

    /**
     * Save statistics.
     *
     * @param statistics recording statistics in file.
     */
    void saveReport(Statistics statistics);

}
