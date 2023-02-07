package com.camelmultidb.service;

import com.camelmultidb.dto.User;

public interface ApiService {
    <T> T handleTransactional(User user);
//    void importExcel(MultipartFile file) throws Exception;
    void importExcel() throws Exception;
    <T> T findAllRental();
    <T> T getActor();
}
