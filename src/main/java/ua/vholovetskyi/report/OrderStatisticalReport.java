package ua.vholovetskyi.report;

import ua.vholovetskyi.service.dto.Statistics;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-10
 */
public interface OrderStatisticalReport {

    /**
     * Save statistics.
     *
     * @param statistics recording statistics in file.
     */
    void saveReport(Statistics statistics);

}
