package ru.itpark.repository;

import ru.itpark.model.House;
import ru.itpark.util.JdbcTemplate;

import java.util.List;

public class HouseRepositoryJdbcImpl implements HouseRepository {
    private final String url;

    public HouseRepositoryJdbcImpl(String url) {
        this.url = url;
    }

    @Override
    public House save(House house) {
        return house.getId() == 0 ? insert(house) : update(house);
    }

    @Override
    public List<House> getAll() {
        return JdbcTemplate.executeQuery(url,
                "SELECT id, price, district, underground FROM houses",
                rs -> new House(
                        rs.getInt("id"),
                        rs.getInt("price"),
                        rs.getString("district"),
                        rs.getString("underground")
                ));
    }

    private House insert(House house) {
        String sql = "INSERT INTO houses (price, district, underground) VALUES(?, ?, ?);";

        int id = JdbcTemplate.executeUpdateReturningId(url, sql, stmt -> {
            stmt.setInt(1, house.getPrice());
            stmt.setString(2, house.getDistrict());
            stmt.setString(3, house.getUnderground());
        });
        house.setId(id);
        return house;
    }

    private House update(House house) {
        String sql = "UPDATE houses SET price = ?, district = ?, underground = ? WHERE id = ?;";

        JdbcTemplate.executeUpdate(url, sql, stmt -> {
            stmt.setInt(1, house.getPrice());
            stmt.setString(2, house.getDistrict());
            stmt.setString(3, house.getUnderground());
            stmt.setInt(4, house.getId());
        });
        return house;
    }
}
