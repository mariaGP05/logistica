package com.maria.logistica.application.exercises;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.model.ServiceType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StreamExercisesTest {

    private final StreamExercises exercises = new StreamExercises();

    @Test
    void getActiveClients_returnsOnlyActive() {
        Client a = Client.builder().id(1L).active(true).build();
        Client b = Client.builder().id(2L).active(false).build();

        List<Client> result = exercises.getActiveClients(List.of(a, b));

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void getActiveClients_emptyList_returnsEmptyList() {
        assertEquals(List.of(), exercises.getActiveClients(List.of()));
    }

    @Test
    void getDeliveredLongOrders_filtersCorrectly() {
        Order ok = Order.builder().status(OrderStatus.DELIVERED).distanceKm(120.0).build();
        Order shortOrder = Order.builder().status(OrderStatus.DELIVERED).distanceKm(10.0).build();
        Order other = Order.builder().status(OrderStatus.CONFIRMED).distanceKm(200.0).build();

        List<Order> result = exercises.getDeliveredLongOrders(List.of(ok, shortOrder, other), 100.0);

        assertEquals(1, result.size());
        assertEquals(120.0, result.get(0).getDistanceKm());
    }

    @Test
    void getDeliveredLongOrders_noMatch_returnsEmptyList() {
        Order o1 = Order.builder().status(OrderStatus.CONFIRMED).distanceKm(500.0).build();

        assertEquals(List.of(), exercises.getDeliveredLongOrders(List.of(o1), 100.0));
    }

    @Test
    void getAvailableServicesByType_filtersCorrectly() {
        LogisticService s1 = LogisticService.builder().type(ServiceType.EXPRESS).available(true).build();
        LogisticService s2 = LogisticService.builder().type(ServiceType.EXPRESS).available(false).build();
        LogisticService s3 = LogisticService.builder().type(ServiceType.STANDARD).available(true).build();

        List<LogisticService> result = exercises.getAvailableServicesByType(List.of(s1, s2, s3), ServiceType.EXPRESS);

        assertEquals(1, result.size());
        assertEquals(ServiceType.EXPRESS, result.get(0).getType());
    }

    @Test
    void getCancelledInRange_filtersCorrectly() {
        Order o1 = Order.builder().status(OrderStatus.CANCELLED).createdAt(LocalDateTime.of(2025, 1, 10, 10, 0)).build();
        Order o2 = Order.builder().status(OrderStatus.CANCELLED).createdAt(LocalDateTime.of(2025, 2, 5, 10, 0)).build();
        Order o3 = Order.builder().status(OrderStatus.DELIVERED).createdAt(LocalDateTime.of(2025, 1, 15, 10, 0)).build();

        List<Order> result = exercises.getCancelledInRange(
                List.of(o1, o2, o3),
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 31)
        );

        assertEquals(1, result.size());
        assertEquals(o1, result.get(0));
    }

    @Test
    void allActiveOrdersHaveAvailableService_trueWhenAllOk() {
        Order o1 = Order.builder()
                .status(OrderStatus.CONFIRMED)
                .service(LogisticService.builder().available(true).type(ServiceType.STANDARD).build())
                .build();
        Order o2 = Order.builder()
                .status(OrderStatus.DELIVERED)
                .service(LogisticService.builder().available(true).type(ServiceType.EXPRESS).build())
                .build();

        assertTrue(exercises.allActiveOrdersHaveAvailableService(List.of(o1, o2)));
    }

    @Test
    void allActiveOrdersHaveAvailableService_falseWhenOneUnavailable() {
        Order o1 = Order.builder()
                .status(OrderStatus.CONFIRMED)
                .service(LogisticService.builder().available(true).type(ServiceType.STANDARD).build())
                .build();
        Order o2 = Order.builder()
                .status(OrderStatus.IN_TRANSIT)
                .service(LogisticService.builder().available(false).type(ServiceType.EXPRESS).build())
                .build();

        assertFalse(exercises.allActiveOrdersHaveAvailableService(List.of(o1, o2)));
    }

    @Test
    void getActiveClientEmails_returnsOnlyEmailsOfActiveClients() {
        Client a = Client.builder().email("a@mail.com").active(true).build();
        Client b = Client.builder().email("b@mail.com").active(false).build();

        assertEquals(List.of("a@mail.com"), exercises.getActiveClientEmails(List.of(a, b)));
    }

    @Test
    void getExpressDiscountedCosts_appliesTenPercentDiscount() {
        Order o1 = Order.builder()
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .totalCost(new BigDecimal("100.00"))
                .build();
        Order o2 = Order.builder()
                .service(LogisticService.builder().type(ServiceType.STANDARD).build())
                .totalCost(new BigDecimal("50.00"))
                .build();

        assertEquals(List.of(new BigDecimal("90.0000")), exercises.getExpressDiscountedCosts(List.of(o1, o2)));
    }

    @Test
    void getFullNamesSorted_sortsAlphabetically() {
        Client c1 = Client.builder().firstName("Ana").lastName("García").build();
        Client c2 = Client.builder().firstName("Luis").lastName("Pérez").build();

        assertEquals(List.of("García, Ana", "Pérez, Luis"), exercises.getFullNamesSorted(List.of(c2, c1)));
    }

    @Test
    void getDistancesInMiles_convertsAndRounds() {
        Order o1 = Order.builder().distanceKm(10.0).build();
        Order o2 = Order.builder().distanceKm(1.0).build();

        assertEquals(List.of(6.21, 0.62), exercises.getDistancesInMiles(List.of(o1, o2)));
    }

    @Test
    void getOrderSummaries_buildsExpectedFormat() {
        LogisticService service = LogisticService.builder().type(ServiceType.EXPRESS).name("Express").build();
        Order order = Order.builder()
                .id(42L)
                .originCity("Madrid")
                .destinationCity("Bilbao")
                .service(service)
                .distanceKm(395.0)
                .totalCost(new BigDecimal("390.15"))
                .status(OrderStatus.DELIVERED)
                .build();

        List<String> result = exercises.getOrderSummaries(List.of(order));

        assertEquals(1, result.size());
        assertEquals("[42] Madrid → Bilbao | EXPRESS | 395.0km | €390.15 | DELIVERED", result.get(0));
    }

    @Test
    void getAllNoteWordsSorted_splitsDistinctAndSorts() {
        Order o1 = Order.builder().notes("fragil urgente entrega").build();
        Order o2 = Order.builder().notes("urgente prioridad").build();

        assertEquals(List.of("entrega", "fragil", "prioridad", "urgente"),
                exercises.getAllNoteWordsSorted(List.of(o1, o2)));
    }

    @Test
    void getDistinctServiceTypesForClient_returnsDistinctTypes() {
        Client client = Client.builder().id(1L).build();
        Order o1 = Order.builder().client(client).service(LogisticService.builder().type(ServiceType.EXPRESS).build()).build();
        Order o2 = Order.builder().client(client).service(LogisticService.builder().type(ServiceType.STANDARD).build()).build();
        Order o3 = Order.builder().client(client).service(LogisticService.builder().type(ServiceType.EXPRESS).build()).build();

        assertEquals(List.of(ServiceType.EXPRESS, ServiceType.STANDARD),
                exercises.getDistinctServiceTypesForClient(List.of(o1, o2, o3), 1L));
    }

    @Test
    void getAllCities_returnsDistinctSortedCities() {
        Order o1 = Order.builder().originCity("Madrid").destinationCity("Sevilla").build();
        Order o2 = Order.builder().originCity("Barcelona").destinationCity("Madrid").build();

        assertEquals(List.of("Barcelona", "Madrid", "Sevilla"), exercises.getAllCities(List.of(o1, o2)));
    }

    @Test
    void getClientsWithExpensiveOrders_deduplicatesById() {
        Client c1 = Client.builder().id(1L).firstName("Ana").lastName("García").active(true).build();
        Client c1Duplicate = Client.builder().id(1L).firstName("Otra").lastName("Persona").active(true).build();
        Client c2 = Client.builder().id(2L).firstName("Luis").lastName("Pérez").active(true).build();

        Order o1 = Order.builder().client(c1).totalCost(new BigDecimal("600")).build();
        Order o2 = Order.builder().client(c1Duplicate).totalCost(new BigDecimal("700")).build();
        Order o3 = Order.builder().client(c2).totalCost(new BigDecimal("800")).build();

        List<Client> result = exercises.getClientsWithExpensiveOrders(List.of(o1, o2, o3));

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(1L)));
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(2L)));
    }

    @Test
    void totalRevenueDelivered_sumsCorrectly() {
        Order a = Order.builder().status(OrderStatus.DELIVERED).totalCost(new BigDecimal("10.50")).build();
        Order b = Order.builder().status(OrderStatus.CANCELLED).totalCost(new BigDecimal("999.99")).build();
        Order c = Order.builder().status(OrderStatus.DELIVERED).totalCost(new BigDecimal("20.00")).build();

        assertEquals(new BigDecimal("30.50"), exercises.totalRevenueDelivered(List.of(a, b, c)));
    }

    @Test
    void totalKmNotCancelled_sumsNonCancelledOrders() {
        Order a = Order.builder().status(OrderStatus.DELIVERED).distanceKm(10.0).build();
        Order b = Order.builder().status(OrderStatus.CANCELLED).distanceKm(999.0).build();
        Order c = Order.builder().status(OrderStatus.CONFIRMED).distanceKm(5.0).build();

        assertEquals(15.0, exercises.totalKmNotCancelled(List.of(a, b, c)));
    }

    @Test
    void getOriginCitiesJoined_joinsUniqueDeliveredCities() {
        Order a = Order.builder().status(OrderStatus.DELIVERED).originCity("Madrid").build();
        Order b = Order.builder().status(OrderStatus.DELIVERED).originCity("Barcelona").build();
        Order c = Order.builder().status(OrderStatus.DELIVERED).originCity("Madrid").build();

        assertEquals("Barcelona | Madrid", exercises.getOriginCitiesJoined(List.of(a, b, c)));
    }

    @Test
    void getOrderSummaries_handlesNullServiceAndNullNumericFields() {
        Order order = Order.builder()
                .id(7L)
                .originCity("Valencia")
                .destinationCity("Zaragoza")
                .service(null)
                .distanceKm(null)
                .totalCost(null)
                .status(OrderStatus.PENDING)
                .build();

        List<String> result = exercises.getOrderSummaries(List.of(order));

        assertEquals(1, result.size());
        assertEquals("[7] Valencia → Zaragoza | N/A | 0.0km | €0.00 | PENDING", result.get(0));
    }

    @Test
    void getOrderSummaries_usesServiceNameWhenTypeIsNull() {
        LogisticService service = LogisticService.builder()
                .name("Servicio personalizado")
                .type(null)
                .build();

        Order order = Order.builder()
                .id(8L)
                .originCity("Sevilla")
                .destinationCity("Cádiz")
                .service(service)
                .distanceKm(12.5)
                .totalCost(new BigDecimal("15.00"))
                .status(OrderStatus.CONFIRMED)
                .build();

        List<String> result = exercises.getOrderSummaries(List.of(order));

        assertEquals(1, result.size());
        assertEquals("[8] Sevilla → Cádiz | Servicio personalizado | 12.5km | €15.00 | CONFIRMED", result.get(0));
    }

    @Test
    void getMostExpensiveOrder_returnsMaxNonCancelled() {
        Order o1 = Order.builder().status(OrderStatus.CONFIRMED).totalCost(new BigDecimal("10")).build();
        Order o2 = Order.builder().status(OrderStatus.DELIVERED).totalCost(new BigDecimal("99")).build();

        Optional<Order> result = exercises.getMostExpensiveOrder(List.of(o1, o2));

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("99"), result.get().getTotalCost());
    }

    @Test
    void productOfMultipliers_multipliesAll() {
        LogisticService s1 = LogisticService.builder().type(ServiceType.STANDARD).build();
        LogisticService s2 = LogisticService.builder().type(ServiceType.EXPRESS).build();

        assertEquals(1.8, exercises.productOfMultipliers(List.of(s1, s2)));
    }

    @Test
    void groupOrdersByStatus_groupsByStatus() {
        Order a = Order.builder().status(OrderStatus.CONFIRMED).build();
        Order b = Order.builder().status(OrderStatus.DELIVERED).build();
        Order c = Order.builder().status(OrderStatus.DELIVERED).build();

        Map<OrderStatus, List<Order>> result = exercises.groupOrdersByStatus(List.of(a, b, c));

        assertEquals(2, result.size());
        assertEquals(1, result.get(OrderStatus.CONFIRMED).size());
        assertEquals(2, result.get(OrderStatus.DELIVERED).size());
    }

    @Test
    void countOrdersByServiceType_countsCorrectly() {
        Order a = Order.builder().service(LogisticService.builder().type(ServiceType.EXPRESS).build()).build();
        Order b = Order.builder().service(LogisticService.builder().type(ServiceType.EXPRESS).build()).build();
        Order c = Order.builder().service(LogisticService.builder().type(ServiceType.STANDARD).build()).build();

        Map<ServiceType, Long> result = exercises.countOrdersByServiceType(List.of(a, b, c));

        assertEquals(2L, result.get(ServiceType.EXPRESS));
        assertEquals(1L, result.get(ServiceType.STANDARD));
    }

    @Test
    void revenueByServiceType_sumsOnlyDelivered() {
        Order a = Order.builder().status(OrderStatus.DELIVERED).service(LogisticService.builder().type(ServiceType.EXPRESS).build()).totalCost(new BigDecimal("10")).build();
        Order b = Order.builder().status(OrderStatus.DELIVERED).service(LogisticService.builder().type(ServiceType.EXPRESS).build()).totalCost(new BigDecimal("20")).build();
        Order c = Order.builder().status(OrderStatus.CONFIRMED).service(LogisticService.builder().type(ServiceType.EXPRESS).build()).totalCost(new BigDecimal("999")).build();

        Map<ServiceType, BigDecimal> result = exercises.revenueByServiceType(List.of(a, b, c));

        assertEquals(new BigDecimal("30"), result.get(ServiceType.EXPRESS));
    }

    @Test
    void activeClientIdToEmail_buildsMap() {
        Client active = Client.builder().id(1L).email("a@mail.com").active(true).build();
        Client inactive = Client.builder().id(2L).email("b@mail.com").active(false).build();

        Map<Long, String> result = exercises.activeClientIdToEmail(List.of(active, inactive));

        assertEquals(1, result.size());
        assertEquals("a@mail.com", result.get(1L));
    }

    @Test
    void deliveredDistanceStats_returnsCorrectStatistics() {
        Order a = Order.builder().status(OrderStatus.DELIVERED).distanceKm(10.0).build();
        Order b = Order.builder().status(OrderStatus.DELIVERED).distanceKm(20.0).build();
        Order c = Order.builder().status(OrderStatus.CANCELLED).distanceKm(100.0).build();

        var stats = exercises.deliveredDistanceStats(List.of(a, b, c));

        assertEquals(2, stats.getCount());
        assertEquals(10.0, stats.getMin());
        assertEquals(20.0, stats.getMax());
        assertEquals(30.0, stats.getSum());
    }

    @Test
    void getTop3ExpensiveOrders_returnsThreeOrderedDesc() {
        Client client = Client.builder().id(1L).build();

        Order o1 = Order.builder().client(client).status(OrderStatus.CONFIRMED).totalCost(new BigDecimal("100")).build();
        Order o2 = Order.builder().client(client).status(OrderStatus.CONFIRMED).totalCost(new BigDecimal("300")).build();
        Order o3 = Order.builder().client(client).status(OrderStatus.CONFIRMED).totalCost(new BigDecimal("200")).build();
        Order o4 = Order.builder().client(client).status(OrderStatus.CANCELLED).totalCost(new BigDecimal("999")).build();

        List<Order> result = exercises.getTop3ExpensiveOrders(List.of(o1, o2, o3, o4), 1L);

        assertEquals(3, result.size());
        assertEquals(new BigDecimal("300"), result.get(0).getTotalCost());
        assertEquals(new BigDecimal("200"), result.get(1).getTotalCost());
        assertEquals(new BigDecimal("100"), result.get(2).getTotalCost());
    }

    @Test
    void hasExpressOrderOver1000_returnsTrueWhenExists() {
        Order o1 = Order.builder().service(LogisticService.builder().type(ServiceType.EXPRESS).build()).totalCost(new BigDecimal("1001")).build();
        assertTrue(exercises.hasExpressOrderOver1000(List.of(o1)));
    }

    @Test
    void avgCostByServiceType_averagesCorrectly() {
        Order a = Order.builder().status(OrderStatus.DELIVERED).service(LogisticService.builder().type(ServiceType.EXPRESS).build()).totalCost(new BigDecimal("10")).build();
        Order b = Order.builder().status(OrderStatus.DELIVERED).service(LogisticService.builder().type(ServiceType.EXPRESS).build()).totalCost(new BigDecimal("30")).build();

        Map<ServiceType, Double> result = exercises.avgCostByServiceType(List.of(a, b));

        assertEquals(20.0, result.get(ServiceType.EXPRESS));
    }

    @Test
    void getClientReport_formatsOutput() {
        Client client = Client.builder().id(1L).firstName("Ana").lastName("García").active(true).build();

        Order o1 = Order.builder().client(client).status(OrderStatus.DELIVERED).totalCost(new BigDecimal("100.00")).build();
        Order o2 = Order.builder().client(client).status(OrderStatus.CONFIRMED).totalCost(new BigDecimal("50.50")).build();

        List<String> result = exercises.getClientReport(List.of(client), List.of(o1, o2));

        assertEquals(1, result.size());
        assertEquals("Ana García — 2 pedidos — 150.50 €", result.get(0));
    }

    @Test
    void partitionByExpensive_splitsCorrectly() {
        Order expensive = Order.builder().totalCost(new BigDecimal("300")).build();
        Order cheap = Order.builder().totalCost(new BigDecimal("299.99")).build();

        Map<Boolean, List<Order>> result = exercises.partitionByExpensive(List.of(expensive, cheap));

        assertEquals(1, result.get(true).size());
        assertEquals(1, result.get(false).size());
    }

    @Test
    void emptyLists_doNotFail() {
        assertEquals(List.of(), exercises.getActiveClients(List.of()));
        assertEquals(List.of(), exercises.getAllCities(List.of()));
        assertEquals(BigDecimal.ZERO, exercises.totalRevenueDelivered(List.of()));
        assertEquals(Optional.empty(), exercises.getMostExpensiveOrder(List.of()));
        assertEquals(0.0, exercises.totalKmNotCancelled(List.of()));
        assertEquals(List.of(), exercises.getClientsWithExpensiveOrders(List.of()));
        assertEquals(List.of(), exercises.getActiveClientEmails(List.of()));
        assertEquals(Map.of(), exercises.groupOrdersByStatus(List.of()));
    }

    @Test
    void getExpensiveOrdersByClient_returnsOnlyMatchingOrdersOverMinCost() {
        Client client = Client.builder().id(1L).build();

        Order valid = Order.builder()
                .client(client)
                .totalCost(new BigDecimal("600"))
                .build();

        Order wrongClient = Order.builder()
                .client(Client.builder().id(2L).build())
                .totalCost(new BigDecimal("700"))
                .build();

        Order lowCost = Order.builder()
                .client(client)
                .totalCost(new BigDecimal("100"))
                .build();

        List<Order> result = exercises.getExpensiveOrdersByClient(
                List.of(valid, wrongClient, lowCost),
                1L,
                new BigDecimal("500")
        );

        assertEquals(1, result.size());
        assertEquals(valid, result.get(0));
    }

    @Test
    void getExpensiveOrdersByClient_returnsEmptyWhenNothingMatches() {
        assertEquals(List.of(), exercises.getExpensiveOrdersByClient(List.of(), 1L, new BigDecimal("500")));
    }

    @Test
    void getDeliveredLongOrders_handlesNullDistanceAndGreaterThanThreshold() {
        Order nullDistance = Order.builder()
                .status(OrderStatus.DELIVERED)
                .distanceKm(null)
                .build();

        Order overThreshold = Order.builder()
                .status(OrderStatus.DELIVERED)
                .distanceKm(150.0)
                .build();

        List<Order> result = exercises.getDeliveredLongOrders(List.of(nullDistance, overThreshold), 100.0);

        assertEquals(1, result.size());
        assertEquals(overThreshold, result.get(0));
    }

    @Test
    void getCancelledInRange_handlesCreatedAtNullAndInRangeInclusive() {
        Order createdAtNull = Order.builder()
                .status(OrderStatus.CANCELLED)
                .createdAt(null)
                .build();

        Order inRange = Order.builder()
                .status(OrderStatus.CANCELLED)
                .createdAt(LocalDateTime.of(2025, 1, 15, 10, 0))
                .build();

        List<Order> result = exercises.getCancelledInRange(
                List.of(createdAtNull, inRange),
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 31)
        );

        assertEquals(1, result.size());
        assertEquals(inRange, result.get(0));
    }

    @Test
    void getExpensiveOrdersByClient_handlesNullClientNullCostAndValidOrder() {
        Client client = Client.builder().id(1L).build();

        Order nullClient = Order.builder()
                .client(null)
                .totalCost(new BigDecimal("600"))
                .build();

        Order nullCost = Order.builder()
                .client(client)
                .totalCost(null)
                .build();

        Order valid = Order.builder()
                .client(client)
                .totalCost(new BigDecimal("600"))
                .build();

        List<Order> result = exercises.getExpensiveOrdersByClient(
                List.of(nullClient, nullCost, valid),
                1L,
                new BigDecimal("500")
        );

        assertEquals(1, result.size());
        assertEquals(valid, result.get(0));
    }

    @Test
    void allActiveOrdersHaveAvailableService_returnsTrueAndFalseBranches() {
        Order valid = Order.builder()
                .status(OrderStatus.CONFIRMED)
                .service(LogisticService.builder().available(true).type(ServiceType.STANDARD).build())
                .build();

        Order unavailable = Order.builder()
                .status(OrderStatus.IN_TRANSIT)
                .service(LogisticService.builder().available(false).type(ServiceType.EXPRESS).build())
                .build();

        assertTrue(exercises.allActiveOrdersHaveAvailableService(List.of(valid)));
        assertFalse(exercises.allActiveOrdersHaveAvailableService(List.of(valid, unavailable)));
    }

    @Test
    void getExpressDiscountedCosts_handlesExpressAndNonExpressAndNullCost() {
        Order expressValid = Order.builder()
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .totalCost(new BigDecimal("100.00"))
                .build();

        Order expressNullCost = Order.builder()
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .totalCost(null)
                .build();

        Order standard = Order.builder()
                .service(LogisticService.builder().type(ServiceType.STANDARD).build())
                .totalCost(new BigDecimal("50.00"))
                .build();

        List<BigDecimal> result = exercises.getExpressDiscountedCosts(List.of(expressValid, expressNullCost, standard));

        assertEquals(1, result.size());
        assertEquals(new BigDecimal("90.0000"), result.get(0));
    }

    @Test
    void getDistancesInMiles_handlesNullAndNonNullDistance() {
        Order nullDistance = Order.builder().distanceKm(null).build();
        Order normalDistance = Order.builder().distanceKm(10.0).build();

        List<Double> result = exercises.getDistancesInMiles(List.of(nullDistance, normalDistance));

        assertEquals(2, result.size());
        assertEquals(0.0, result.get(0));
        assertEquals(6.21, result.get(1));
    }

    @Test
    void getAllNoteWordsSorted_handlesNullBlankAndRealNotes() {
        Order nullNotes = Order.builder().notes(null).build();
        Order blankNotes = Order.builder().notes("   ").build();
        Order realNotes = Order.builder().notes("fragil urgente fragil").build();

        List<String> result = exercises.getAllNoteWordsSorted(List.of(nullNotes, blankNotes, realNotes));

        assertEquals(List.of("fragil", "urgente"), result);
    }

    @Test
    void getDistinctServiceTypesForClient_handlesMatchingAndNonMatchingClientAndNullType() {
        Client client = Client.builder().id(1L).build();

        Order wrongClient = Order.builder()
                .client(Client.builder().id(2L).build())
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .build();

        Order nullType = Order.builder()
                .client(client)
                .service(LogisticService.builder().type(null).build())
                .build();

        Order valid = Order.builder()
                .client(client)
                .service(LogisticService.builder().type(ServiceType.STANDARD).build())
                .build();

        List<ServiceType> result = exercises.getDistinctServiceTypesForClient(
                List.of(wrongClient, nullType, valid),
                1L
        );

        assertEquals(List.of(ServiceType.STANDARD), result);
    }

    @Test
    void getAllCities_handlesNullBlankAndValidCities() {
        Order nullCity = Order.builder()
                .originCity(null)
                .destinationCity(null)
                .build();

        Order blankCity = Order.builder()
                .originCity(" ")
                .destinationCity("   ")
                .build();

        Order valid = Order.builder()
                .originCity("Madrid")
                .destinationCity("Sevilla")
                .build();

        List<String> result = exercises.getAllCities(List.of(nullCity, blankCity, valid));

        assertEquals(List.of("Madrid", "Sevilla"), result);
    }

    @Test
    void getClientsWithExpensiveOrders_handlesNullClientIdNullCostAndValidClient() {
        Client clientWithId = Client.builder().id(1L).firstName("Ana").build();
        Client clientWithoutId = Client.builder().id(null).firstName("SinId").build();

        Order nullClient = Order.builder()
                .client(null)
                .totalCost(new BigDecimal("600"))
                .build();

        Order nullIdClient = Order.builder()
                .client(clientWithoutId)
                .totalCost(new BigDecimal("600"))
                .build();

        Order lowCost = Order.builder()
                .client(clientWithId)
                .totalCost(new BigDecimal("500"))
                .build();

        Order valid = Order.builder()
                .client(clientWithId)
                .totalCost(new BigDecimal("600"))
                .build();

        List<Client> result = exercises.getClientsWithExpensiveOrders(List.of(nullClient, nullIdClient, lowCost, valid));

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void totalRevenueDelivered_handlesDeliveredAndNonDeliveredAndNullCost() {
        Order deliveredNullCost = Order.builder()
                .status(OrderStatus.DELIVERED)
                .totalCost(null)
                .build();

        Order deliveredCost = Order.builder()
                .status(OrderStatus.DELIVERED)
                .totalCost(new BigDecimal("20.00"))
                .build();

        Order cancelled = Order.builder()
                .status(OrderStatus.CANCELLED)
                .totalCost(new BigDecimal("999.99"))
                .build();

        assertEquals(new BigDecimal("20.00"),
                exercises.totalRevenueDelivered(List.of(deliveredNullCost, deliveredCost, cancelled)));
    }

    @Test
    void totalKmNotCancelled_handlesNullDistanceAndCancelled() {
        Order nullDistance = Order.builder()
                .status(OrderStatus.CONFIRMED)
                .distanceKm(null)
                .build();

        Order cancelled = Order.builder()
                .status(OrderStatus.CANCELLED)
                .distanceKm(100.0)
                .build();

        Order valid = Order.builder()
                .status(OrderStatus.DELIVERED)
                .distanceKm(15.5)
                .build();

        assertEquals(15.5, exercises.totalKmNotCancelled(List.of(nullDistance, cancelled, valid)));
    }

    @Test
    void getOriginCitiesJoined_handlesNullBlankDeliveredAndDuplicateCities() {
        Order nullCity = Order.builder()
                .status(OrderStatus.DELIVERED)
                .originCity(null)
                .build();

        Order blankCity = Order.builder()
                .status(OrderStatus.DELIVERED)
                .originCity(" ")
                .build();

        Order madrid = Order.builder()
                .status(OrderStatus.DELIVERED)
                .originCity("Madrid")
                .build();

        Order barcelona = Order.builder()
                .status(OrderStatus.DELIVERED)
                .originCity("Barcelona")
                .build();

        assertEquals("Barcelona | Madrid", exercises.getOriginCitiesJoined(List.of(nullCity, blankCity, madrid, barcelona, madrid)));
    }

    @Test
    void getMostExpensiveOrder_handlesCancelledNullCostAndValid() {
        Order cancelled = Order.builder()
                .status(OrderStatus.CANCELLED)
                .totalCost(new BigDecimal("999"))
                .build();

        Order nullCost = Order.builder()
                .status(OrderStatus.CONFIRMED)
                .totalCost(null)
                .build();

        Order valid = Order.builder()
                .status(OrderStatus.CONFIRMED)
                .totalCost(new BigDecimal("50"))
                .build();

        assertTrue(exercises.getMostExpensiveOrder(List.of(cancelled, nullCost, valid)).isPresent());
        assertEquals(new BigDecimal("50"), exercises.getMostExpensiveOrder(List.of(cancelled, nullCost, valid)).get().getTotalCost());
    }

    @Test
    void productOfMultipliers_handlesEmptyAndMultipleServices() {
        LogisticService s1 = LogisticService.builder().type(ServiceType.STANDARD).build();
        LogisticService s2 = LogisticService.builder().type(ServiceType.EXPRESS).build();

        assertEquals(1.0, exercises.productOfMultipliers(List.of()));
        assertEquals(1.8, exercises.productOfMultipliers(List.of(s1, s2)));
    }

    @Test
    void countOrdersByServiceType_handlesNullAndValidTypes() {
        Order nullService = Order.builder().service(null).build();
        Order nullType = Order.builder().service(LogisticService.builder().type(null).build()).build();
        Order express = Order.builder().service(LogisticService.builder().type(ServiceType.EXPRESS).build()).build();

        Map<ServiceType, Long> result = exercises.countOrdersByServiceType(List.of(nullService, nullType, express));

        assertEquals(1L, result.get(ServiceType.EXPRESS));
        assertEquals(1, result.size());
    }

    @Test
    void revenueByServiceType_handlesDeliveredAndNullCostAndNullType() {
        Order nullType = Order.builder()
                .status(OrderStatus.DELIVERED)
                .service(LogisticService.builder().type(null).build())
                .totalCost(new BigDecimal("10"))
                .build();

        Order nullCost = Order.builder()
                .status(OrderStatus.DELIVERED)
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .totalCost(null)
                .build();

        Order valid = Order.builder()
                .status(OrderStatus.DELIVERED)
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .totalCost(new BigDecimal("20"))
                .build();

        Map<ServiceType, BigDecimal> result = exercises.revenueByServiceType(List.of(nullType, nullCost, valid));

        assertEquals(new BigDecimal("20"), result.get(ServiceType.EXPRESS));
    }

    @Test
    void activeClientIdToEmail_handlesNullIdAndActiveFiltering() {
        Client nullIdActive = Client.builder().id(null).email("x@mail.com").active(true).build();
        Client active = Client.builder().id(1L).email("a@mail.com").active(true).build();
        Client inactive = Client.builder().id(2L).email("b@mail.com").active(false).build();

        Map<Long, String> result = exercises.activeClientIdToEmail(List.of(nullIdActive, active, inactive));

        assertEquals(1, result.size());
        assertEquals("a@mail.com", result.get(1L));
    }

    @Test
    void deliveredDistanceStats_handlesDeliveredAndNonDeliveredAndNullDistance() {
        Order nullDistance = Order.builder()
                .status(OrderStatus.DELIVERED)
                .distanceKm(null)
                .build();

        Order valid = Order.builder()
                .status(OrderStatus.DELIVERED)
                .distanceKm(10.0)
                .build();

        Order cancelled = Order.builder()
                .status(OrderStatus.CANCELLED)
                .distanceKm(100.0)
                .build();

        var stats = exercises.deliveredDistanceStats(List.of(nullDistance, valid, cancelled));

        assertEquals(2, stats.getCount());
        assertEquals(0.0, stats.getMin());
        assertEquals(10.0, stats.getMax());
        assertEquals(10.0, stats.getSum());
    }

    @Test
    void getTop3ExpensiveOrders_handlesWrongClientAndCancelled() {
        Client client = Client.builder().id(1L).build();

        Order wrongClient = Order.builder()
                .client(Client.builder().id(2L).build())
                .status(OrderStatus.CONFIRMED)
                .totalCost(new BigDecimal("100"))
                .build();

        Order cancelled = Order.builder()
                .client(client)
                .status(OrderStatus.CANCELLED)
                .totalCost(new BigDecimal("999"))
                .build();

        Order valid = Order.builder()
                .client(client)
                .status(OrderStatus.CONFIRMED)
                .totalCost(new BigDecimal("200"))
                .build();

        assertEquals(List.of(valid), exercises.getTop3ExpensiveOrders(List.of(wrongClient, cancelled, valid), 1L));
    }

    @Test
    void hasExpressOrderOver1000_handlesFalseAndTrueBranches() {
        Order falseOrder = Order.builder()
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .totalCost(new BigDecimal("999.99"))
                .build();

        Order trueOrder = Order.builder()
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .totalCost(new BigDecimal("1000.01"))
                .build();

        assertFalse(exercises.hasExpressOrderOver1000(List.of(falseOrder)));
        assertTrue(exercises.hasExpressOrderOver1000(List.of(falseOrder, trueOrder)));
    }

    @Test
    void avgCostByServiceType_handlesDeliveredNullCostAndNullType() {
        Order nullType = Order.builder()
                .status(OrderStatus.DELIVERED)
                .service(LogisticService.builder().type(null).build())
                .totalCost(new BigDecimal("10"))
                .build();

        Order nullCost = Order.builder()
                .status(OrderStatus.DELIVERED)
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .totalCost(null)
                .build();

        Order valid = Order.builder()
                .status(OrderStatus.DELIVERED)
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .totalCost(new BigDecimal("30"))
                .build();

        Map<ServiceType, Double> result = exercises.avgCostByServiceType(List.of(nullType, nullCost, valid));

        assertEquals(15.0, result.get(ServiceType.EXPRESS));
    }

    @Test
    void getClientReport_handlesActiveInactiveAndOrders() {
        Client active = Client.builder().id(1L).firstName("Ana").lastName("García").active(true).build();
        Client inactive = Client.builder().id(2L).firstName("Luis").lastName("Pérez").active(false).build();

        Order activeOrder1 = Order.builder().client(active).status(OrderStatus.DELIVERED).totalCost(new BigDecimal("100")).build();
        Order activeOrder2 = Order.builder().client(active).status(OrderStatus.CONFIRMED).totalCost(null).build();
        Order cancelled = Order.builder().client(active).status(OrderStatus.CANCELLED).totalCost(new BigDecimal("999")).build();
        Order inactiveOrder = Order.builder().client(inactive).status(OrderStatus.DELIVERED).totalCost(new BigDecimal("50")).build();

        List<String> result = exercises.getClientReport(List.of(active, inactive), List.of(activeOrder1, activeOrder2, cancelled, inactiveOrder));

        assertEquals(1, result.size());
        assertEquals("Ana García — 2 pedidos — 100.00 €", result.get(0));
    }
}