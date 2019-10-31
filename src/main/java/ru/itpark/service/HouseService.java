package ru.itpark.service;

import ru.itpark.model.House;
import ru.itpark.util.JdbcTemplate;
import ru.itpark.util.StringService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HouseService {
    private final List<House> items = JdbcTemplate.executeQuery(
            "jdbc:sqlite:db.sqlite",
            "SELECT id, price, district, underground FROM houses",
            resultSet -> new House(
                    resultSet.getInt("id"),
                    resultSet.getInt("price"),
                    resultSet.getString("district"),
                    resultSet.getString("underground")
            )
    );

    public HouseService() throws SQLException {
    }

    public List<House> sortBy(Comparator<House> comparator) {
        List<House> result = new ArrayList<>(items);
        result.sort(comparator);
        return result;
    }

    public List<House> searchByUnderground(String underground, Comparator<House> comparator) {
        List<House> results = new ArrayList<>();
        for (House house : items) {
            if (StringService.containsIgnoreCase(house.getUnderground(), underground)) {
                results.add(house);
            }
        }
        results.sort(comparator);
        return results;
    }

    public List<House> searchByPrice(int min, int max, Comparator<House> comparator) {
        List<House> result = new ArrayList<>();
        for (House house : items) {
            if (house.getPrice() >= min && house.getPrice() <= max) {
                result.add(house);
            }
        }
        result.sort(comparator);
        return result;
    }
}

