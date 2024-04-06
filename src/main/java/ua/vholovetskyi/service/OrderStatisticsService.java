package ua.vholovetskyi.service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ua.vholovetskyi.service.handler.dto.Statisticse;
import ua.vholovetskyi.input.UserInputArgs;
import ua.vholovetskyi.model.OrderStatus;
import ua.vholovetskyi.service.handler.DateStatisticsHandler;
import ua.vholovetskyi.service.handler.ItemStatisticsHandler;
import ua.vholovetskyi.service.handler.StatisticsHandler;
import ua.vholovetskyi.service.handler.StatusStatisticsHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OrderService {

    private final XmlMapper xmlMapper;

    public OrderService() {
        this.xmlMapper = new XmlMapper();
    }

    public void generateStatistics(UserInputArgs inputArgs) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Statisticse> submit = null;
        long start = System.nanoTime();
        int count = 0;
        try {
        for (String fileName : inputArgs.getFiles()) {
            StatisticsHandler currentHandler = getCurrentHandler(inputArgs, fileName);
            submit = executorService.submit(currentHandler);
            count++;
            Statisticse statistics = submit.get();
            xmlMapper.writeValue(new File("file" + Thread.currentThread().getName() + ".xml"), statistics);
        }
                long end = System.nanoTime();
                long elapsedTime = end - start;
                System.out.println((double) elapsedTime / 1_000_000_000);
                executorService.shutdown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (StreamWriteException e) {
                throw new RuntimeException(e);
            } catch (DatabindException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private StatisticsHandler getCurrentHandler(UserInputArgs inputArgs, String fileName) {
        List<StatisticsHandler> handlers = listHandlers(fileName, inputArgs.getOrderStatuses());

        Optional<StatisticsHandler> currentHandler = Optional.empty();
        for (StatisticsHandler handler : handlers) {
            if (handler.supports(inputArgs.getAttribute())) {
                currentHandler = Optional.of(handler);
                break;
            }
        }
        return currentHandler
                .orElseThrow(() -> new IllegalArgumentException("Unsupported handler for the attribute: %s".formatted(inputArgs.getAttribute().getName())));
    }

    private List<StatisticsHandler> listHandlers(String fileName, Set<OrderStatus> statuses) {
        return List.of(
                new ItemStatisticsHandler(fileName),
                new DateStatisticsHandler(fileName),
                new StatusStatisticsHandler(fileName, statuses)
        );
    }
}
