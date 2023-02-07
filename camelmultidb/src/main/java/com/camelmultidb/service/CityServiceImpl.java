package com.camelmultidb.service;

import com.camelmultidb.repository.mariaDB.CityRepository;
import com.camelmultidb.repository.mariaDB.entity.CityEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    final CityRepository cityRepository;

    @Override
    @Transactional
    public void saveCity(String cityName) {
        CityEntity entity = cityRepository.findById(600).get();
        entity.setCity(cityName);
        cityRepository.save(entity);
    }
}
