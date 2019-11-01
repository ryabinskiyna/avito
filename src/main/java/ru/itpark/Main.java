package ru.itpark;

import java.sql.SQLException;

import static ru.itpark.service.HouseService.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println(sortBy((o1, o2) -> o2.getPrice() - o1.getPrice()));
        System.out.println(sortBy((o1, o2) -> o1.getDistrict().compareToIgnoreCase(o2.getDistrict())));
        System.out.println(searchByUnderground("сев", (o1, o2) -> o1.getUnderground().compareToIgnoreCase(o2.getUnderground())));
        System.out.println(searchByPrice(2_000_000, 4_000_000, (o1, o2) -> o1.getPrice() - o2.getPrice()));
    }
}
