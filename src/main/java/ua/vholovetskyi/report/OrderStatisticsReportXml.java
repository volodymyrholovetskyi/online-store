package ua.vholovetskyi.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ua.vholovetskyi.service.dto.Statistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-10
 */
public class OrderStatisticsReportXml implements OrderStatisticalReport {

    private static final Logger LOG = Logger.getLogger(OrderStatisticsReportXml.class.getName());
    private final ObjectMapper xmlMapper;
    private final String fileName;

    public OrderStatisticsReportXml(String fileName) {
        this.fileName = fileName;
        this.xmlMapper = new XmlMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void saveReport(Statistics statistics) {
        try (var bufferedWriter = Files.newBufferedWriter(Paths.get(fileName))) {
            xmlMapper.writeValue(bufferedWriter, statistics);
            LOG.log(Level.INFO, "Done! Statistics written to %s.".formatted(fileName));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error in writeReport() method! Error message: %s".formatted(e.getMessage()));
        }
    }
}
