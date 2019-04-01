package com.peace.web.controller;

import com.peace.users.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/excel")
public class ExcelDownloadRestAPI {

    @Autowired
    private UserDao dao;

    @GetMapping(value = "/download/{reportType}/{formId}/{planYr}/{formName}")
    public ResponseEntity<InputStreamResource> excelReport(@PathVariable int reportType, HttpServletRequest req, @PathVariable int formId, @PathVariable int planYr,@PathVariable String formName) throws IOException {
        String appPath = req.getServletContext().getRealPath("");
        ByteArrayInputStream in = ExcelGenerator.customersToExcel(reportType,formId,planYr,formName,appPath,dao);
        HttpHeaders headers = new HttpHeaders();
        String name=URLEncoder.encode(formName+".xlsx","UTF-8");
        headers.add("Content-Disposition", "attachment; filename="+name+"");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }

    @GetMapping(value = "/new/download/{reportType}/{formId}/{planYr}/{formName}")
    public ResponseEntity<InputStreamResource> excelNewReport(@PathVariable int reportType, HttpServletRequest req, @PathVariable int formId, @PathVariable int planYr,@PathVariable String formName) throws IOException {
        String appPath = req.getServletContext().getRealPath("");
        ByteArrayInputStream in = ExcelGenerator.formsToExcel(reportType,formId,planYr,formName,appPath,dao);
        HttpHeaders headers = new HttpHeaders();
        String name=URLEncoder.encode(formName+".xlsx","UTF-8");
        headers.add("Content-Disposition", "attachment; filename="+name+"");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }
}
