package ru.itpark;

import ru.itpark.model.House;
import ru.itpark.repository.HouseRepositoryInMemoryImpl;
import ru.itpark.repository.HouseRepositoryJdbcImpl;
import ru.itpark.service.HouseService;

public class Main {
    public static void main(String[] args) {
        final HouseService service = new HouseService(new HouseRepositoryJdbcImpl("jdbc:sqlite:db.sqlite"));
        service.register(new House(0, 10000000, "Вахитовский", "Площадь Тукая"));
        service.renew(new House(16, 1, "а", "б"));
        System.out.println(service.searchByPrice(2000000, 3000000, (o1, o2) -> o1.getPrice() - o2.getPrice()));
        System.out.println(service.searchByDistrict("Вахитовский", (o1, o2) -> o1.getPrice() - o2.getPrice()));
        System.out.println(service.sortBy((o1, o2) -> o2.getPrice() - o1.getPrice()));

        HouseService service1 = new HouseService(new HouseRepositoryInMemoryImpl());
        service1.register(new House(0, 1, "а", "б"));
        service1.register(new House(0, 2, "в", "г"));
        service1.register(new House(0, 3, "д", "е"));
        service1.renew(new House(3, 300, "д", "е"));

        System.out.println(service1.sortBy((o1, o2) -> o1.getPrice() - o2.getPrice()));


    }
}
