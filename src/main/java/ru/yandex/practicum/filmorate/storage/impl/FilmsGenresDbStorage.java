package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FilmsGenresDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public List<FilmGenre> getByFilmId(Long id) {
        String query = "select * from filmorate.films_genre fg " +
                " join filmorate.film_film_genre ffg " +
                "       on fg.id = ffg.film_genre_id " +
                " where ffg.film_id = ?";
        return jdbcTemplate.query(query, new FilmGenreDbStorage.FilmGenreMapper(), id);
    }

    public Map<Long, List<FilmGenre>> getByFilmsIds(List<Long> filmIds) {
        StringBuilder sb = new StringBuilder("( ");
        for (int i = 0; i < filmIds.size(); i++) {
            sb.append(filmIds.get(i));
            if (i != filmIds.size() - 1) {
                sb.append(",");
            }
            sb.append(" ");
        }
        sb.append(" )");
        String query = "select " +
                " f.id as film_id, " +
                " fg.id as film_film_genre_id, " +
                " fg.name as film_film_genre_name " +
                " from filmorate.films f " +
                "   left join filmorate.film_film_genre ffg " +
                "       on ffg.film_id = f.id " +
                "   left join filmorate.films_genre fg " +
                "       on ffg.film_genre_id = fg.id " +
                " where f.id in " + sb;

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        Map<Long, List<FilmGenre>> resultMap = new HashMap<>();
        for (Map<String, Object> row : rows) {
            Long filmId = ((Number) row.get("film_id")).longValue();
            Long filmGenreId = row.get("film_film_genre_id") == null ? null : ((Number) row.get("film_film_genre_id")).longValue();
            String filmGenreName = (String) row.get("film_film_genre_name");
            List<FilmGenre> genreList = resultMap.computeIfAbsent(filmId, k -> new ArrayList<>());
            if (filmGenreId != null && filmGenreName != null) {
                FilmGenre genre = new FilmGenre();
                genre.setId(filmGenreId);
                genre.setName(filmGenreName);
                genreList.add(genre);
            }
        }

        return resultMap;
    }

    public void add(Long filmId, Long genreId) {
        jdbcTemplate.update("INSERT INTO filmorate.film_film_genre" +
                "   (film_id, film_genre_id) " +
                "   VALUES (?, ?)", filmId, genreId);
    }

    public void remove(Long filmId) {
        String deleteSql = "DELETE FROM filmorate.film_film_genre WHERE film_id = ?";
        jdbcTemplate.update(deleteSql, filmId);
    }
}
