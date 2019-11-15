package ru.itpark.service;

import ru.itpark.model.House;
import ru.itpark.repository.HouseRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HouseService {
    private final HouseRepository repository;

    public HouseService(HouseRepository repository) {
        this.repository = repository;
    }

    public House register(House house) {
        if (house.getId() != 0) {
            throw new IllegalArgumentException("House id must be 0 for registration");
        }
        return repository.save(house);
    }

    public House renew(House house) {
        if (house.getId() == 0) {
            throw new IllegalArgumentException("House id must not be 0 for renew");
        }
        return repository.save(house);
    }

    public List<House> sortBy(Comparator<House> comparator) {
        List<House> results = repository.getAll();
        results.sort(comparator);
        return results;
    }

    public List<House> searchByDistrict(String district, Comparator<House> comparator) {
        List<House> results = new ArrayList<>();
        for (House house : repository.getAll()) {
            if (house.getDistrict().equals(district)) {
                results.add(house);
            }
        }
        results.sort(comparator);
        return results;
    }

    public List<House> searchByPrice(int min, int max, Comparator<House> comparator) {
        List<House> results = new ArrayList<>();
        for (House house : repository.getAll()) {
            if (house.getPrice() >= min && house.getPrice() <= max) {
                results.add(house);
            }
        }
        results.sort(comparator);
        return results;
    }
}

