package ru.itpark.service;

import ru.itpark.model.House;
import ru.itpark.util.JdbcTemplate;
import ru.itpark.util.StringService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HouseService {

    private List<House> getDataFromDB() throws SQLException {
        return JdbcTemplate.executeQuery(
                "jdbc:sqlite:db.sqlite",
                "SELECT id, price, district, underground FROM houses",
                resultSet -> new House(
                        resultSet.getInt("id"),
                        resultSet.getInt("price"),
                        resultSet.getString("district"),
                        resultSet.getString("underground")
                )
        );
    }

    public List<House> sortBy(Comparator<House> comparator) throws SQLException {
        List<House> results = getDataFromDB();
        results.sort(comparator);
        return results;
    }

    public List<House> searchByUnderground(String underground, Comparator<House> comparator) throws SQLException {
        List<House> results = new ArrayList<>();
        for (House house : getDataFromDB()) {
            if (StringService.containsIgnoreCase(house.getUnderground(), underground)) {
                results.add(house);
            }
        }
        results.sort(comparator);
        return results;
    }

    public List<House> searchByPrice(int min, int max, Comparator<House> comparator) throws SQLException {
        List<House> results = new ArrayList<>();
        for (House house : getDataFromDB()) {
            if (house.getPrice() >= min && house.getPrice() <= max) {
                results.add(house);
            }
        }
        results.sort(comparator);
        return results;
    }
}

