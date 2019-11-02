package ru.itpark;

import ru.itpark.service.HouseService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        HouseService service = new HouseService();
        System.out.println(service.sortBy((o1, o2) -> o2.getPrice() - o1.getPrice()));
        System.out.println(service.sortBy((o1, o2) -> o1.getDistrict().compareToIgnoreCase(o2.getDistrict())));
        System.out.println(service.searchByUnderground("сев", (o1, o2) -> o1.getUnderground().compareToIgnoreCase(o2.getUnderground())));
        System.out.println(service.searchByPrice(2_000_000, 4_000_000, (o1, o2) -> o1.getPrice() - o2.getPrice()));
    }
}
