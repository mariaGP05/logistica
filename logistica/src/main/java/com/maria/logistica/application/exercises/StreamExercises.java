package com.maria.logistica.application.exercises;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.model.ServiceType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamExercises {

    // 1
    public List<Client> getActiveClients(List<Client> clients) {
        return clients.stream()
                .filter(client -> Boolean.TRUE.equals(client.getActive()))
                .toList();
    }

    // 2
    public List<Order> getDeliveredLongOrders(List<Order> orders, double minKm) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .filter(order -> order.getDistanceKm() != null && order.getDistanceKm() > minKm)
                .toList();
    }

    // 3
    public List<LogisticService> getAvailableServicesByType(List<LogisticService> services, ServiceType type) {
        return services.stream()
                .filter(service -> Boolean.TRUE.equals(service.getAvailable()))
                .filter(service -> service.getType() == type)
                .toList();
    }

    // 4
    public List<Order> getCancelledInRange(List<Order> orders, LocalDate from, LocalDate to) {
        Predicate<Order> isCancelled = order -> order.getStatus() == OrderStatus.CANCELLED;
        Predicate<Order> inRange = order -> order.getCreatedAt() != null
                && !order.getCreatedAt().toLocalDate().isBefore(from)
                && !order.getCreatedAt().toLocalDate().isAfter(to);

        return orders.stream()
                .filter(isCancelled.and(inRange))
                .toList();
    }

    // 5
    public List<Order> getExpensiveOrdersByClient(List<Order> orders, Long clientId, BigDecimal minCost) {
        return orders.stream()
                .filter(order -> order.getClient() != null)
                .filter(order -> clientId.equals(order.getClient().getId()))
                .filter(order -> order.getTotalCost() != null)
                .filter(order -> order.getTotalCost().compareTo(minCost) > 0)
                .toList();
    }

    // 6
    public boolean allActiveOrdersHaveAvailableService(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                .allMatch(order -> order.getService() != null && Boolean.TRUE.equals(order.getService().getAvailable()));
    }

    // 7
    public List<String> getActiveClientEmails(List<Client> clients) {
        return clients.stream()
                .filter(client -> Boolean.TRUE.equals(client.getActive()))
                .map(Client::getEmail)
                .toList();
    }

    // 8
    public List<BigDecimal> getExpressDiscountedCosts(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getService() != null && order.getService().getType() == ServiceType.EXPRESS)
                .filter(order -> order.getTotalCost() != null)
                .map(order -> order.getTotalCost().multiply(new BigDecimal("0.90")))
                .toList();
    }

    // 9
    public List<String> getFullNamesSorted(List<Client> clients) {
        return clients.stream()
                .map(client -> client.getLastName() + ", " + client.getFirstName())
                .sorted()
                .toList();
    }

    // 10
    public List<Double> getDistancesInMiles(List<Order> orders) {
        return orders.stream()
                .mapToDouble(order -> order.getDistanceKm() == null ? 0.0 : order.getDistanceKm() * 0.621371)
                .map(distance -> Math.round(distance * 100.0) / 100.0)
                .boxed()
                .toList();
    }

    // 11
    public List<String> getOrderSummaries(List<Order> orders) {
        return orders.stream()
                .map(order -> String.format(
                        Locale.ROOT,
                        "[%d] %s → %s | %s | %.1fkm | €%s | %s",
                        order.getId(),
                        order.getOriginCity(),
                        order.getDestinationCity(),
                        order.getService() != null && order.getService().getType() != null
                                ? order.getService().getType()
                                : (order.getService() != null ? order.getService().getName() : "N/A"),
                        order.getDistanceKm() != null ? order.getDistanceKm() : 0.0,
                        order.getTotalCost() != null
                                ? order.getTotalCost().setScale(2, RoundingMode.HALF_UP)
                                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP),
                        order.getStatus()
                ))
                .toList();
    }

    // 12
    public List<String> getAllNoteWordsSorted(List<Order> orders) {
        return orders.stream()
                .map(Order::getNotes)
                .filter(note -> note != null && !note.isBlank())
                .flatMap(note -> Arrays.stream(note.trim().split("\\s+")))
                .filter(word -> !word.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    // 13
    public List<ServiceType> getDistinctServiceTypesForClient(List<Order> orders, Long clientId) {
        return orders.stream()
                .filter(order -> order.getClient() != null && clientId.equals(order.getClient().getId()))
                .filter(order -> order.getService() != null && order.getService().getType() != null)
                .map(order -> order.getService().getType())
                .distinct()
                .toList();
    }

    // 14
    public List<String> getAllCities(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> Arrays.stream(new String[]{order.getOriginCity(), order.getDestinationCity()}))
                .filter(city -> city != null && !city.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    // 15
    public List<Client> getClientsWithExpensiveOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getTotalCost() != null && order.getTotalCost().compareTo(new BigDecimal("500")) > 0)
                .filter(order -> order.getClient() != null && order.getClient().getId() != null)
                .map(Order::getClient)
                .collect(Collectors.toMap(
                        Client::getId,
                        client -> client,
                        (existing, duplicate) -> existing,
                        LinkedHashMap::new
                ))
                .values()
                .stream()
                .toList();
    }

    // 16
    public BigDecimal totalRevenueDelivered(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .map(order -> order.getTotalCost() == null ? BigDecimal.ZERO : order.getTotalCost())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 17
    public double totalKmNotCancelled(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                .mapToDouble(order -> order.getDistanceKm() == null ? 0.0 : order.getDistanceKm())
                .sum();
    }

    // 18
    public String getOriginCitiesJoined(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .map(Order::getOriginCity)
                .filter(city -> city != null && !city.isBlank())
                .distinct()
                .sorted()
                .reduce("", (a, b) -> a.isEmpty() ? b : a + " | " + b);
    }

    // 19
    public Optional<Order> getMostExpensiveOrder(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                .filter(order -> order.getTotalCost() != null)
                .max(Comparator.comparing(Order::getTotalCost));
    }

    // 20
    public double productOfMultipliers(List<LogisticService> services) {
        return services.stream()
                .filter(service -> service.getType() != null)
                .mapToDouble(service -> service.getType().getMultiplier())
                .reduce(1.0, (a, b) -> a * b);
    }

    // 21
    public Map<OrderStatus, List<Order>> groupOrdersByStatus(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus));
    }

    // 22
    public Map<ServiceType, Long> countOrdersByServiceType(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getService() != null && order.getService().getType() != null)
                .collect(Collectors.groupingBy(order -> order.getService().getType(), Collectors.counting()));
    }

    // 23
    public Map<ServiceType, BigDecimal> revenueByServiceType(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .filter(order -> order.getService() != null && order.getService().getType() != null)
                .collect(Collectors.groupingBy(
                        order -> order.getService().getType(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                order -> order.getTotalCost() == null ? BigDecimal.ZERO : order.getTotalCost(),
                                BigDecimal::add
                        )));
    }

    // 24
    public Map<Long, String> activeClientIdToEmail(List<Client> clients) {
        return clients.stream()
                .filter(client -> Boolean.TRUE.equals(client.getActive()))
                .filter(client -> client.getId() != null)
                .collect(Collectors.toMap(Client::getId, Client::getEmail));
    }

    // 25
    public DoubleSummaryStatistics deliveredDistanceStats(List<Order> orders) {
        DoubleSummaryStatistics stats = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getDistanceKm() == null ? 0.0 : order.getDistanceKm())
                .summaryStatistics();

        System.out.printf("Entregados: %d | km mín: %.2f | km máx: %.2f | km promedio: %.2f%n",
                stats.getCount(), stats.getMin(), stats.getMax(), stats.getAverage());

        return stats;
    }

    // 26
    public List<Order> getTop3ExpensiveOrders(List<Order> orders, Long clientId) {
        return orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                .filter(order -> order.getClient() != null && clientId.equals(order.getClient().getId()))
                .filter(order -> order.getTotalCost() != null)
                .sorted(Comparator.comparing(Order::getTotalCost).reversed())
                .limit(3)
                .toList();
    }

    // 27
    public boolean hasExpressOrderOver1000(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getService() != null && order.getService().getType() == ServiceType.EXPRESS)
                .anyMatch(order -> order.getTotalCost() != null && order.getTotalCost().compareTo(new BigDecimal("1000")) > 0);
    }

    // 28
    public Map<ServiceType, Double> avgCostByServiceType(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .filter(order -> order.getService() != null && order.getService().getType() != null)
                .collect(Collectors.groupingBy(
                        order -> order.getService().getType(),
                        Collectors.averagingDouble(order -> order.getTotalCost() == null ? 0.0 : order.getTotalCost().doubleValue())
                ));
    }

    // 29
    public List<String> getClientReport(List<Client> clients, List<Order> orders) {
        return clients.stream()
                .filter(client -> Boolean.TRUE.equals(client.getActive()))
                .map(client -> {
                    List<Order> clientOrders = orders.stream()
                            .filter(order -> order.getClient() != null && client.getId().equals(order.getClient().getId()))
                            .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                            .toList();

                    long count = clientOrders.size();
                    BigDecimal total = clientOrders.stream()
                            .map(order -> order.getTotalCost() == null ? BigDecimal.ZERO : order.getTotalCost())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return client.getFirstName() + " " + client.getLastName()
                            + " — " + count + " pedidos — " + total.setScale(2, RoundingMode.HALF_UP) + " €";
                })
                .toList();
    }

    // 30
    public Map<Boolean, List<Order>> partitionByExpensive(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.partitioningBy(order ->
                        order.getTotalCost() != null
                                && order.getTotalCost().compareTo(new BigDecimal("300")) >= 0));
    }
}