package com.camelmultidb.service;

import com.camelmultidb.dto.User;
import com.camelmultidb.repository.mariaDB.ActorRepository;
import com.camelmultidb.repository.mssql.RentalNewRepository;
import com.camelmultidb.repository.mssql.entity.RentalNewEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiServiceImpl implements ApiService {
//    final ActorService actorService;
//    final CityService cityService;
    final RentalNewRepository rentalNewRepository;
    final ActorRepository actorRepository;
    final CityService cityService;
    final RentalService rentalService;

    @Override
    @Transactional
    public <T> T handleTransactional(User user) {
//        String postfix = " 13";
//        actorService.saveActor("THORA" + postfix);
//        cityService.saveCity("Ziguinchor" + postfix);

        int inventoryId = 11;
        cityService.saveCity("Ziguinchor " + inventoryId);
        rentalService.saveRental(inventoryId);
//        int a = 1/0;
        return (T) "success";
    }

    @Override
    @Transactional
    public void importExcel() throws Exception {
        File myFile = new File("D://query//report.xlsx");
        Workbook workbook = new XSSFWorkbook(new FileInputStream(myFile));
//        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
        List<RentalNewEntity> data = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
//        for (int i = 1; i < 3; i++) {
            row = sheet.getRow(i);
            RentalNewEntity entity = RentalNewEntity.builder()
                    .rentalDate(row.getCell(1).getDateCellValue())
                    .inventoryId((int) row.getCell(2).getNumericCellValue())
                    .customerId((int) row.getCell(3).getNumericCellValue())
                    .returnDate(new Date())
                    .staffId((int) row.getCell(5).getNumericCellValue())
                    .lastUpdate(row.getCell(6).getDateCellValue())
                    .build();
            data.add(entity);
        }
        long start = System.currentTimeMillis();
        rentalNewRepository.saveAll(data);
        long end = System.currentTimeMillis();
        log.info("aaa: " + (end - start));
    }

    @Override
    public <T> T findAllRental() {
        return (T) rentalNewRepository.findAll();
    }

    @Override
    public <T> T getActor() {
        var data = actorRepository.findTop2ByLastName();
        return (T) data;
    }

}
