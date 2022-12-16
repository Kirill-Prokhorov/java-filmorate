package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import java.util.List;

@Slf4j
@Service
public class MpaService {
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {

        this.mpaStorage = mpaStorage;
    }

    public Mpa getMpaById(int id) {

        if (mpaStorage.getById(id) != null) {
            return mpaStorage.getById(id);
        } else {
            throw new NotFoundException("Нет рейтинга с таким айди " + id);
        }
    }

    public List<Mpa> getAllMpas() {

        List<Mpa> mpaList = mpaStorage.getAllRatings();
        log.debug("Load {} MPA", mpaList.size());
        return mpaList;
    }
}