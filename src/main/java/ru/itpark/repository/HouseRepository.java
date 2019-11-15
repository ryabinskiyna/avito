package ru.itpark.repository;

import ru.itpark.model.House;

import java.util.List;

public interface HouseRepository {
    House save(House house);

    List<House> getAll();
}
