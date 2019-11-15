package ru.itpark;

import ru.itpark.model.House;
import ru.itpark.repository.HouseRepositoryInMemoryImpl;
import ru.itpark.repository.HouseRepositoryJdbcImpl;
import ru.itpark.service.HouseService;

public class Main {
    public static void main(String[] args) {
        final HouseService serviceJdbc = new HouseService(new HouseRepositoryJdbcImpl("jdbc:sqlite:db.sqlite"));
        serviceJdbc.register(new House(0, 10000000, "Вахитовский", "Площадь Тукая"));
        serviceJdbc.renew(new House(16, 1, "а", "б"));
        System.out.println(serviceJdbc.searchByPrice(2000000, 3000000, (o1, o2) -> o1.getPrice() - o2.getPrice()));
        System.out.println(serviceJdbc.searchByDistrict("Вахитовский", (o1, o2) -> o1.getPrice() - o2.getPrice()));
        System.out.println(serviceJdbc.sortBy((o1, o2) -> o2.getPrice() - o1.getPrice()));

        final HouseService serviceInMemory = new HouseService(new HouseRepositoryInMemoryImpl());
        serviceInMemory.register(new House(0, 1, "а", "б"));
        serviceInMemory.register(new House(0, 2, "в", "г"));
        serviceInMemory.register(new House(0, 3, "д", "е"));
        serviceInMemory.renew(new House(3, 300, "д", "е"));

        System.out.println(serviceInMemory.sortBy((o1, o2) -> o1.getPrice() - o2.getPrice()));


    }
}
