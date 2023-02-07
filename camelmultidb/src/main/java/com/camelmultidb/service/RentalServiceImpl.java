package com.camelmultidb.service;

import com.camelmultidb.repository.mssql.RentalNewRepository;
import com.camelmultidb.repository.mssql.entity.RentalNewEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    final RentalNewRepository rentalNewRepository;

    @Override
    @Transactional
    public void saveRental(int inventoryId) {
        RentalNewEntity entity = rentalNewRepository.findById(152).get();
        entity.setInventoryId(inventoryId);
        rentalNewRepository.save(entity);
    }
}
