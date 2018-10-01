package com.peace.web.logic.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peace.users.model.mram.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.peace.users.dao.UserDao;

@Controller
@RequestMapping("/export/stat")
public class StatisticController {

    @Autowired
    UserDao dao;

    @RequestMapping(value = "/excel/{year}/{id}", method = RequestMethod.GET)
    public String division3(HttpServletResponse response, HttpServletRequest req, @PathVariable Long year, @PathVariable Long id) {
        String appPath = req.getSession().getServletContext().getRealPath("");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String currentdate = dateFormat.format(date);
        try {
            //Маягт 2-н мэдээ - Уулын хэлтэс
            if (id == 1) {
                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat1.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                Sheet sheet = workbook.getSheet("SGT");
                List<Object[]> ans = dao.getNativeSQLResult("SELECT T.ID, T.REQID, T.REPORTYEAR, T.LICENSEXB, DEP.DEPOSITNAMEMON, LIC.LOCATIONAIMAG, LIC.LOCATIONSOUM, LIC.AREANAMEMON, LIC.AREASIZE, T.LPNAME, T.APPROVEDDATE FROM ANNUAL_REGISTRATION T LEFT JOIN LUT_DEPOSIT DEP ON DEP.DEPOSITID = T.DEPOSITID LEFT JOIN SUB_LICENSES LIC ON LIC.LICENSENUM = T.LICENSENUM WHERE T.ISTODOTGOL = 0 AND T.REPSTATUSID = 1 AND T.REPORTYEAR = "+year+1+" AND T.REPORTTYPE = 3 AND T.DIVISIONID = 1 AND T.XTYPE != 0 ORDER BY T.LICENSEXB", "list");
                if (ans != null && ans.size() > 0) {
                    for (int i = 0, rowIterator = 2; i < ans.size(); i++, rowIterator++) {
                        Object[] an = ans.get(i);
                        List<LnkReqAnn> reqanns = (List<LnkReqAnn>) dao.getHQLResult("from LnkReqAnn t where t.reqid='" + an[1] + "' order by t.id desc", "list");
                        List<DataMinPlan1> data11 = (List<DataMinPlan1>) dao.getHQLResult("from DataMinPlan1 t where t.planid='" + an[0].toString() + "' and t.dataIndex in (1,2,3,4,5) order by t.id asc", "list");
                        LnkReqAnn reqann = null;
                        if (reqanns != null && reqanns.size() > 0) {
                            reqann = reqanns.get(0);
                        }

                        List<String> data21Blocks = null;
                        List<Double> data27 = null;
                        List<Object> data4 = null;
                        if (reqann != null) {
                            if (reqann.getIsmatter() == 1) {
                                data21Blocks = (List<String>) dao.getHQLResult("select distinct(t.data4) from DataMinPlan2_1 t where t.planid='" + an[0].toString() + "' and t.type = 1 and t.data4 is not null", "list");
                                data27 = (List<Double>) dao.getHQLResult("select data22 from DataMinPlan2_1 t where t.planid='" + an[0].toString() + "' and t.type = 2 and t.data1 = 'Нийт'", "list");
                                data4 = (List<Object>) dao.getHQLResult("select data3 from DataMinPlan4_1 t where t.planid='" + an[0].toString() + "' and t.type = 1 and t.data1 like '%Өмнөх онуудын үлдэгдэл үйлдвэрлэлийн нөөц (хүдэр, элс)%'", "list");
                            } else if (reqann.getIsmatter() == 2) {
                                data21Blocks = (List<String>) dao.getHQLResult("select distinct(t.data4) from DataMinPlan2_2 t where t.planid='" + an[0].toString() + "' and t.type = 1 and t.data4 is not null", "list");
                                data27 = (List<Double>) dao.getHQLResult("select data22 from DataMinPlan2_2 t where t.planid='" + an[0].toString() + "' and t.type = 2 and t.data1 = 'Нийт'", "list");
                                data4 = (List<Object>) dao.getHQLResult("select data3 from DataMinPlan4_2 t where t.planid='" + an[0].toString() + "' and t.type = 1 and t.dataindex=1 and t.data1 like '%Өмнөх онуудын үлдэгдэл үйлдвэрлэлийн нөөц (хүдэр, элс)%'", "list");
                            }
                        }

                        List<DataMinPlan3> data3 = (List<DataMinPlan3>) dao.getHQLResult("from DataMinPlan3 t where t.planid='" + an[0].toString() + "' and (t.data1 like '%Бэлтгэл малталт%' or t.data1 like '%Үндсэн малталт%' or t.data1 like '%Хүдэр (элс) олборлолт%' or t.data1 like '%Хүдэр (элс) боловсруулалт%') order by t.id", "list");

                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null) {
                            currentRow = sheet.createRow(rowIterator);
                        }

                        // 1
                        Cell currentCell = currentRow.getCell(0);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(0);
                        }
                        currentCell.setCellStyle(style);
                        currentCell.setCellValue(i + 1);

                        // 2
                        currentCell = currentRow.getCell(1);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(1);
                        }
                        currentCell.setCellStyle(style);
                        if (an[3] != null) {
                            currentCell.setCellValue(an[3].toString());
                        }

                        // 3
                        currentCell = currentRow.getCell(2);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(2);
                        }
                        currentCell.setCellStyle(style);
                        if (an[4] != null) {
                            currentCell.setCellValue(an[4].toString());
                        }

                        // 4
                        currentCell = currentRow.getCell(3);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(3);
                        }
                        currentCell.setCellStyle(style);
                        if (an[5] != null) {
                            currentCell.setCellValue(an[5].toString());
                        }

                        // 5
                        currentCell = currentRow.getCell(4);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(4);
                        }
                        currentCell.setCellStyle(style);
                        if (an[6] != null) {
                            currentCell.setCellValue(an[6].toString());
                        }

                        // 6
                        currentCell = currentRow.getCell(5);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(5);
                        }
                        currentCell.setCellStyle(style);
                        if (an[7] != null) {
                            currentCell.setCellValue(an[7].toString());
                        }

                        // 7
                        currentCell = currentRow.getCell(6);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(6);
                        }
                        currentCell.setCellStyle(style);
                        if (an[8] != null) {
                            currentCell.setCellValue(an[8].toString());
                        }

                        // 8
                        currentCell = currentRow.getCell(7);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(7);
                        }
                        currentCell.setCellStyle(style);
                        if (an[9] != null) {
                            currentCell.setCellValue(an[9].toString());
                        }

                        // 9
                        currentCell = currentRow.getCell(8);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(8);
                        }
                        currentCell.setCellStyle(style);

                        // 10
                        currentCell = currentRow.getCell(9);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(9);
                        }
                        currentCell.setCellStyle(style);

                        // 11
                        currentCell = currentRow.getCell(10);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(10);
                        }
                        currentCell.setCellStyle(style);

                        // 12
                        currentCell = currentRow.getCell(11);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(11);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue(reqann.getAppdate());
                        }

                        // 13
                        currentCell = currentRow.getCell(12);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(12);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue((reqann.getStatebudgetid() == 1) ? "Тийм" : "Үгүй");
                        }

                        // 14
                        currentCell = currentRow.getCell(13);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(13);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue(reqann.getWorkyear());
                        }

                        // 15
                        currentCell = currentRow.getCell(14);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(14);
                        }

                        currentCell.setCellStyle(style);
                        for (DataMinPlan1 d : data11) {
                            if (d.getDataIndex() == 1 && d.getData3() != null) {
                                currentCell.setCellValue(d.getData3());
                            }
                        }

                        // 16
                        currentCell = currentRow.getCell(15);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(15);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue((reqann.getMinetypeid() == 1) ? "Ил" : ((reqann.getMinetypeid() == 2) ? "Далд" : "Хосолсон"));
                        }

                        // 17
                        currentCell = currentRow.getCell(16);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(16);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue(reqann.getStartdate());
                        }
                        /*for (DataMinPlan1 d : data11) {
                            if (d.getDataIndex() == 5 && d.getData5() != null) {
                                currentCell.setCellValue(d.getData5());
                            }
                        }*/

                        // 18
                        currentCell = currentRow.getCell(17);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(17);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue(((reqann.getKomissdate() != null) ? reqann.getKomissdate() : "") + " - " + ((reqann.getKomissakt() != null) ? reqann.getKomissakt() : ""));
                        }

                        // 19
                        currentCell = currentRow.getCell(18);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(18);
                        }
                        currentCell.setCellStyle(style);

                        // 20
                        currentCell = currentRow.getCell(19);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(19);
                        }
                        currentCell.setCellStyle(style);

                        // 21
                        currentCell = currentRow.getCell(20);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(20);
                        }

                        currentCell.setCellStyle(style);
                        for (DataMinPlan1 d : data11) {
                            if (d.getDataIndex() == 2 && d.getData3() != null) {
                                currentCell.setCellValue(d.getData3());
                            }
                        }

                        // 22
                        currentCell = currentRow.getCell(21);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(21);
                        }

                        currentCell.setCellStyle(style);
                        for (DataMinPlan1 d : data11) {
                            if (d.getDataIndex() == 3 && d.getData3() != null) {
                                currentCell.setCellValue(d.getData3());
                            }
                        }

                        // 23
                        currentCell = currentRow.getCell(22);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(22);
                        }
                        currentCell.setCellStyle(style);
                        for (DataMinPlan1 d : data11) {
                            if (d.getDataIndex() == 4 && d.getData3() != null) {
                                currentCell.setCellValue(d.getData3());
                            }
                        }

                        // 24
                        currentCell = currentRow.getCell(23);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(23);
                        }
                        currentCell.setCellStyle(style);

                        // 25
                        currentCell = currentRow.getCell(24);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(24);
                        }
                        currentCell.setCellStyle(style);
                        if (an[10] != null) {
                            currentCell.setCellValue(an[10].toString());
                        }

                        // 26
                        currentCell = currentRow.getCell(25);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(25);
                        }
                        currentCell.setCellStyle(style);
                        String blocks = "";
                        if (data21Blocks != null) {
                            for (String s : data21Blocks) {
                                blocks = blocks + (s + ", ");
                            }
                        }
                        currentCell.setCellValue(blocks);

                        // 27
                        currentCell = currentRow.getCell(26);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(26);
                        }
                        currentCell.setCellStyle(style);
                        if (data27 != null && data27.size() > 0 && data27.get(0) != null) {
                            currentCell.setCellValue(data27.get(0));
                        }

                        // 28
                        currentCell = currentRow.getCell(27);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(27);
                        }
                        currentCell.setCellStyle(style);
                        if (data3 != null && data3.size() > 0) {
                            for (DataMinPlan3 d : data3) {
                                if (d.getData1() != null && d.getData1().indexOf("Бэлтгэл малталт") > -1 && d.getData3() != null) {
                                    currentCell.setCellValue(d.getData3());
                                }
                            }

                        }

                        // 29
                        currentCell = currentRow.getCell(28);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(28);
                        }
                        currentCell.setCellStyle(style);
                        if (data3 != null && data3.size() > 0) {
                            for (DataMinPlan3 d : data3) {
                                if (d.getData1() != null && d.getData1().indexOf("Үндсэн малталт") > -1 && d.getData3() != null) {
                                    currentCell.setCellValue(d.getData3());
                                }
                            }

                        }

                        // 30
                        currentCell = currentRow.getCell(29);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(29);
                        }
                        currentCell.setCellStyle(style);
                        if (data3 != null && data3.size() > 0) {
                            for (DataMinPlan3 d : data3) {
                                if (d.getData1() != null && d.getData1().indexOf("Хүдэр (элс) олборлолт") > -1 && d.getData3() != null) {
                                    currentCell.setCellValue(d.getData3());
                                }
                            }

                        }

                        // 31
                        currentCell = currentRow.getCell(30);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(30);
                        }
                        currentCell.setCellStyle(style);
                        if (data4 != null && data4.size() > 0 && data4.get(0) != null) {
                            currentCell.setCellValue(data4.get(0).toString());
                        }

                        // 32
                        currentCell = currentRow.getCell(31);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(31);
                        }
                        currentCell.setCellStyle(style);
                        if (data3 != null && data3.size() > 0) {
                            for (DataMinPlan3 d : data3) {
                                if (d.getData1() != null && d.getData1().indexOf("Хүдэр (элс) боловсруулалт") > -1 && d.getData3() != null) {
                                    currentCell.setCellValue(d.getData3());
                                }
                            }

                        }

                    }
                }

                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Хувийн хөрөнгөөр хийсэн геологи хайгуулын ажил
            else if (id == 2){

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat2.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                Sheet sheet = workbook.getSheet("SGTREPORT");
                int reportyear = 2016;
                List<Long> reportids = (List<Long>)dao.getHQLResult("select t.id from AnnualRegistration t where t.istodotgol = 0 and t.divisionid = 3 and t.reporttype = 4 and t.repstatusid = 1 and t.reportyear = "+year + " and t.xtype != 0", "list");
                Row currentRow = sheet.getRow(1);
                if (currentRow!=null){
                    Cell currentCell = currentRow.getCell(5);
                    if (currentCell != null){
                        currentCell = currentRow.createCell(5);
                    }
                    currentCell.setCellValue(year);
                }
                if (reportids.size() > 0){
                    for(int i=1;i<=18;i++){

                        String sql = "select SUM(data5), SUM(data6),SUM(data8),SUM(data9) from DATA_EXCEL_GEOREP1 t where t.planid in (select a.id from ANNUAL_REGISTRATION a where a.istodotgol = 0 and a.divisionid = 3 and a.reporttype = 4 and a.repstatusid = 1 and a.reportyear = "+year + " and a.xtype != 0) and t.data1 = '"+i+".0'";

                        List<Object[]> results = (List<Object[]>)dao.getNativeSQLResult(sql, "list");

                        if (results.size() > 0){
                            Object[] data = (Object[]) results.get(0);
                            currentRow = sheet.getRow(i + 3);
                            if (currentRow != null){
                                for(int j=0;j<4;j++){
                                    Cell currentCell = currentRow.getCell(j + 2);
                                    if (currentCell == null){
                                        currentCell = currentRow.createCell(j+2);
                                    }
                                    if (data[j] != null){
                                        currentCell.setCellValue(Double.parseDouble(data[j].toString()));
                                    }
                                    currentCell.setCellStyle(style);
                                }
                            }
                        }

                    }
                }

                sheet = workbook.getSheet("SGTPLAN");
                int planyear = 2017;
                List<Long> planids = (List<Long>)dao.getHQLResult("select t.id from AnnualRegistration t where t.istodotgol = 0 and t.divisionid = 3 and t.reporttype = 3 and t.repstatusid = 1 and t.reportyear = "+year+1 + " and t.xtype != 0", "list");
                currentRow = sheet.getRow(1);
                if (currentRow!=null){
                    Cell currentCell = currentRow.getCell(3);
                    if (currentCell != null){
                        currentCell = currentRow.createCell(3);
                    }
                    currentCell.setCellValue(year+1);
                }
                if (planids.size() > 0){
                    for(int i=1;i<=18;i++){
                        String sql = "select SUM(data6),SUM(data7) from DATA_EXCEL_GEOPLAN1 t where t.planid in (select a.id from ANNUAL_REGISTRATION a where a.istodotgol = 0 and a.divisionid = 3 and a.reporttype = 3 and a.repstatusid = 1 and a.reportyear = "+year+1 + " and a.xtype != 0) and t.data1 = '"+i+".0'";
                        List<Object[]> results = (List<Object[]>)dao.getNativeSQLResult(sql, "list");
                        if (results.size() > 0){
                            Object[] data = (Object[]) results.get(0);
                            currentRow = sheet.getRow(i + 3);
                            if (currentRow != null){
                                for(int j=0;j<2;j++){
                                    Cell currentCell = currentRow.getCell(j + 2);
                                    if (currentCell == null){
                                        currentCell = currentRow.createCell(j+2);
                                    }
                                    if (data[j] != null){
                                        currentCell.setCellValue(Double.parseDouble(data[j].toString()));
                                    }
                                    currentCell.setCellStyle(style);
                                }
                            }
                        }

                    }
                }

                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Хайгуул - Маягт 3-ын мэдээ
            else if (id == 3){

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat3.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                String sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A.GROUPID), (SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), D.DATA2, D.DATA3, D.DATA4, D.DATA5, D.DATA6, D.DATA7, D.DATA8, D.DATA9, D.DATA10, D.DATA12, D.DATA13 FROM DATA_EXCEL_GEOREP3 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = 'TSOONOG' AND D .DATA2 != 'Нийт' AND A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+year+" AND A. XTYPE != 0 ORDER BY D.PLANID ASC, D.ORDERNUMBER ASC";
                List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                Sheet sheet = workbook.getSheet("SGT_TSOONOG");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<17;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A.GROUPID), (SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), D.DATA2, D.DATA3, D.DATA4, D.DATA5, D.DATA6, D.DATA7, D.DATA8, D.DATA9, D.DATA10, D.DATA11, D.DATA12, D.DATA13 FROM DATA_EXCEL_GEOREP3 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = 'SUVAG' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A .REPORTTYPE = 4 AND A.ISTODOTGOL = 0 AND A .REPORTYEAR = "+year+" AND A. XTYPE != 0 ORDER BY D.PLANID ASC, D.ORDERNUMBER ASC";
                resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                sheet = workbook.getSheet("SGT_SUVAG");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<18;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A.GROUPID), (SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), D.DATA2, D.DATA3, D.DATA4, D.DATA5, D.DATA6, D.DATA7, D.DATA8, D.DATA9, D.DATA10, D.DATA12, D.DATA13 FROM DATA_EXCEL_GEOREP3 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = 'SHURF' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+year+" AND A.ISTODOTGOL = 0 AND A. XTYPE != 0 ORDER BY D.PLANID ASC, D.ORDERNUMBER ASC";
                resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                sheet = workbook.getSheet("SGT_SHURF");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<17;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Хайгуул - Маягт 5-ын мэдээ
            else if (id == 4){

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat4.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                String sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A .GROUPID),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),D .DATA2,D .DATA3,D .DATA4,D .DATA5,D .DATA6,D .DATA7,D .DATA8,D .DATA9,D .DATA10,D .DATA11,D .DATA12,D .DATA13,D .DATA14,D .DATA15,D .DATA16 FROM DATA_EXCEL_GEOREP5 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = '1' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A.ISTODOTGOL = 0 AND A .DIVISIONID = 3 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+year+" AND A .XTYPE != 0 ORDER BY D .PLANID ASC, D .ORDERNUMBER ASC";
                List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                Sheet sheet = workbook.getSheet("SGT_ORD");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<21;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A .GROUPID),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),D .DATA2,D .DATA3,D .DATA4,D .DATA5,D .DATA6,D .DATA7,D .DATA8,D .DATA9,D .DATA10,D .DATA11,D .DATA12,D .DATA13,D .DATA14,D .DATA15,D .DATA16 FROM DATA_EXCEL_GEOREP5 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = '2' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A.ISTODOTGOL = 0 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+year+" AND A .XTYPE != 0 ORDER BY D .PLANID ASC, D .ORDERNUMBER ASC";
                resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                sheet = workbook.getSheet("SGT_ILREL");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<21;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A .GROUPID),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),D .DATA2,D .DATA3,D .DATA4,D .DATA5,D .DATA6,D .DATA7,D .DATA8,D .DATA9,D .DATA10,D .DATA11,D .DATA12,D .DATA13,D .DATA14,D .DATA15,D .DATA16 FROM DATA_EXCEL_GEOREP5 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = '3' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A.ISTODOTGOL = 0 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+year+" AND A .XTYPE != 0 ORDER BY D .PLANID ASC, D .ORDERNUMBER ASC";
                resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                sheet = workbook.getSheet("SGT_ERDES");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<21;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A .GROUPID),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),D .DATA2,D .DATA3,D .DATA4,D .DATA5,D .DATA6,D .DATA7,D .DATA8,D .DATA9,D .DATA10,D .DATA11,D .DATA12,D .DATA13,D .DATA14,D .DATA15,D .DATA16 FROM DATA_EXCEL_GEOREP5 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = '4' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A.ISTODOTGOL = 0 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+year+" AND A .XTYPE != 0 ORDER BY D .PLANID ASC, D .ORDERNUMBER ASC";
                resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                sheet = workbook.getSheet("SGT_ELEMENT");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<21;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (id == 5){

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat5.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null){
                    Long count3 = (long)0;
                    String sqlQuery = "select ANNUAL_REGISTRATION.REPSTATUSID, COUNT(*) from ANNUAL_REGISTRATION where ANNUAL_REGISTRATION.LICENSEXB like 'XV-%' and ANNUAL_REGISTRATION.DIVISIONID = 3 and ANNUAL_REGISTRATION.ISTODOTGOL = 0 AND ANNUAL_REGISTRATION.REPORTTYPE = 4 and ANNUAL_REGISTRATION.REPORTYEAR = " + year + " and ANNUAL_REGISTRATION.REPSTATUSID != 0 GROUP BY ANNUAL_REGISTRATION.REPSTATUSID";
                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                    if (resultObj != null && resultObj.size() > 0){
                        for(Object[] o : resultObj){
                            if (o[0].toString().equalsIgnoreCase("7")){
                                setCellData(sheet, 4,2,o[1],style,"number");
                                count3 = count3 + Long.parseLong(o[1].toString());
                            }
                            else if (o[0].toString().equalsIgnoreCase("2")){
                                setCellData(sheet, 5,2,o[1],style,"number");
                                count3 = count3 + Long.parseLong(o[1].toString());
                            }
                            else if (o[0].toString().equalsIgnoreCase("1")){
                                setCellData(sheet, 6,2,o[1],style,"number");
                                count3 = count3 + Long.parseLong(o[1].toString());
                            }
                        }
                    }
                    setCellData(sheet, 3,2,count3,style,"number");

                    Long count1 = (long)0;
                    sqlQuery = "select ANNUAL_REGISTRATION.REPSTATUSID, COUNT(*) from ANNUAL_REGISTRATION where ANNUAL_REGISTRATION.ISTODOTGOL = 0 AND ANNUAL_REGISTRATION.LICENSEXB like 'MV-%' and ANNUAL_REGISTRATION.DIVISIONID = 3 and ANNUAL_REGISTRATION.REPORTTYPE = 4 and ANNUAL_REGISTRATION.REPORTYEAR = "+year+" and ANNUAL_REGISTRATION.REPSTATUSID != 0 GROUP BY ANNUAL_REGISTRATION.REPSTATUSID";
                    resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                    if (resultObj != null && resultObj.size() > 0){
                        for(Object[] o : resultObj){
                            if (o[0].toString().equalsIgnoreCase("7")){
                                setCellData(sheet, 4,3,o[1],style,"number");
                                count1 = count1 + Long.parseLong(o[1].toString());
                            }
                            else if (o[0].toString().equalsIgnoreCase("2")){
                                setCellData(sheet, 5,3,o[1],style,"number");
                                count1 = count1 + Long.parseLong(o[1].toString());
                            }
                            else if (o[0].toString().equalsIgnoreCase("1")){
                                setCellData(sheet, 6,3,o[1],style,"number");
                                count1 = count1 + Long.parseLong(o[1].toString());
                            }
                        }
                    }
                    setCellData(sheet, 3,3,count1,style,"number");

                    sqlQuery = "select ANNUAL_REGISTRATION.GROUPID, COUNT(*) from ANNUAL_REGISTRATION where ANNUAL_REGISTRATION.ISTODOTGOL = 0 AND ANNUAL_REGISTRATION.LICENSEXB like 'XV-%' and ANNUAL_REGISTRATION.DIVISIONID = 3 and ANNUAL_REGISTRATION.REPORTTYPE = 4 and ANNUAL_REGISTRATION.REPORTYEAR = "+year+" and ANNUAL_REGISTRATION.REPSTATUSID != 0 GROUP BY ANNUAL_REGISTRATION.GROUPID";
                    resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                    if (resultObj != null && resultObj.size() > 0){
                        for(Object[] o : resultObj){
                            if (o[0].toString().equalsIgnoreCase("10")){
                                setCellData(sheet, 7,2,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("11")){
                                setCellData(sheet, 8,2,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("12")){
                                setCellData(sheet, 9,2,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("13")){
                                setCellData(sheet, 10,2,o[1],style,"number");
                            }
                        }
                    }

                    sqlQuery = "select ANNUAL_REGISTRATION.GROUPID, COUNT(*) from ANNUAL_REGISTRATION where ANNUAL_REGISTRATION.ISTODOTGOL = 0 AND ANNUAL_REGISTRATION.LICENSEXB like 'MV-%' and ANNUAL_REGISTRATION.DIVISIONID = 3 and ANNUAL_REGISTRATION.REPORTTYPE = 4 and ANNUAL_REGISTRATION.REPORTYEAR = "+year+" and ANNUAL_REGISTRATION.REPSTATUSID != 0 GROUP BY ANNUAL_REGISTRATION.GROUPID";
                    resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                    if (resultObj != null && resultObj.size() > 0){
                        for(Object[] o : resultObj){
                            if (o[0].toString().equalsIgnoreCase("10")){
                                setCellData(sheet, 7,3,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("11")){
                                setCellData(sheet, 8,3,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("12")){
                                setCellData(sheet, 9,3,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("13")){
                                setCellData(sheet, 10,3,o[1],style,"number");
                            }
                        }
                    }
                }

                DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                Date date1 = new Date();
                String special = dateFormat1.format(date1);
                setCellData(sheet, 12,2,special,null,"string");
                setCellData(sheet, 1,3,year,null,"number");
                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Уул - Маягт 6.2-н мэдээ
            else if (id == 6) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat6.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2017;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String[] vals = {" = '1'", " = '2'", " LIKE '%3.1%'", " LIKE '%3.2%'", " LIKE '%3.3%'"," LIKE '%3.4%'"," = '4'", " = '5'"," = '6'", " = '7'"," = '8'"," = '9'"};
                    String sqlQuery = "SELECT A.LPNAME,A.LP_REG,A.LICENSEXB,A.REPORTYEAR,(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1),(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT DATA_MIN_PLAN_6_2.DATA9 FROM DATA_MIN_PLAN_6_2 WHERE DATA_MIN_PLAN_6_2.PLANID = A . ID AND ROWNUM = 1),";
                    for(String tempVal : vals){
                        for(int i=3;i<=8;i++){
                            sqlQuery = sqlQuery + "(SELECT DATA_MIN_PLAN_6_2.DATA"+i+" FROM DATA_MIN_PLAN_6_2 WHERE DATA_MIN_PLAN_6_2.PLANID = A.ID AND ROWNUM = 1 AND DATA_MIN_PLAN_6_2.DATA1 "+tempVal+")";
                            if (!(i==8 && tempVal.equalsIgnoreCase(vals[vals.length - 1]))){
                                sqlQuery = sqlQuery + ",";
                            }
                        }
                    }

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = "+reportyear+" AND A .XTYPE != 0 AND A .DIVISIONID = 1 ORDER BY A.LPNAME ASC";
                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+5,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                setCellData(sheet, iterator+5,cellIterator+1,o[cellIterator],style,"string");
                            }
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    //setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Mayagt6.2" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (id == 7) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/statgeo1.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGTREPORT");
                if (sheet != null) {
                    String sqlQuery = "SELECT A.ID, A .LPNAME, A .LICENSEXB, A .LP_REG, (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1), (SELECT M .GROUPNAME FROM LUT_MIN_GROUP M WHERE M .GROUPID = A .GROUPID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1) FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + year + " AND A .XTYPE != 0 AND A .DIVISIONID = 3 ORDER BY A .LPNAME ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                    List<Object[]> list= dao.getNativeSQLResult("SELECT * FROM DATA_EXCEL_GEOREP1 t where t.PLANID in (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+year+" AND (A .XTYPE != 0 OR A.XTYPE IS NULL) AND A .DIVISIONID = 3)", "list");
                    List<DataExcelGeorep1> realList = new ArrayList<DataExcelGeorep1>();

                    for(Object[] o : list){
                        try{
                            DataExcelGeorep1 temp = new DataExcelGeorep1();
                            temp.setId(Long.parseLong(o[0].toString()));
                            temp.setPlanid(Long.parseLong(o[1].toString()));
                            temp.setNoteid(Long.parseLong(o[2].toString()));
                            temp.setData1(o[3].toString());
                            temp.setData2(o[4].toString());
                            temp.setData3(o[5].toString());
                            temp.setData4(o[6].toString());
                            temp.setData5(Double.parseDouble(o[7].toString()));
                            temp.setData6(Double.parseDouble(o[8].toString()));
                            temp.setData7(Double.parseDouble(o[9].toString()));
                            temp.setData8(Double.parseDouble(o[10].toString()));
                            temp.setData9(Double.parseDouble(o[11].toString()));
                            temp.setOrdernumber(Long.parseLong(o[12].toString()));
                            temp.setIstodotgol(o[13].toString().equalsIgnoreCase("0") ? false : true);
                            realList.add(temp);
                        }
                        catch(NullPointerException e){

                        }
                    }

                    int it = 0;
                    for(Object[] obj : resultObj){
                        it++;
                        setCellData(sheet, it+5,0,it,style,"number");
                        setCellData(sheet, it+5,1,obj[1],style,"string");
                        setCellData(sheet, it+5,2,obj[2],style,"string");
                        setCellData(sheet, it+5,3,obj[3],style,"string");
                        setCellData(sheet, it+5,4,obj[4],style,"string");
                        setCellData(sheet, it+5,5,obj[5],style,"string");
                        setCellData(sheet, it+5,6,obj[6],style,"string");
                        setCellData(sheet, it+5,7,obj[7],style,"string");
                        setCellData(sheet, it+5,8,obj[8],style,"string");
                        setCellData(sheet, it+5,9,obj[9],style,"string");

                        List<DataExcelGeorep1> tempList = new ArrayList<DataExcelGeorep1>();
                        for(DataExcelGeorep1 d : realList){
                            if (d.getPlanid().toString().equalsIgnoreCase(obj[0].toString())){
                                tempList.add(d);
                            }
                        }

                        for(int i=0;i<18;i++){
                            Double sum1 = 0.0, sum2 = 0.0, sum3 = 0.0, sum4 = 0.0;
                            for(DataExcelGeorep1 d : tempList){
                                if (d.getData1().equalsIgnoreCase((i+1)+".0")){
                                    if (d.getData5() != null){
                                        sum1 = sum1 + d.getData5();
                                    }
                                    if (d.getData6() != null){
                                        sum2 = sum2 + d.getData6();
                                    }
                                    if (d.getData8() != null){
                                        sum3 = sum3 + d.getData8();
                                    }
                                    if (d.getData9() != null){
                                        sum4 = sum4 + d.getData9();
                                    }
                                }
                            }
                            setCellData(sheet, it+5,i*4+10,sum1,style,"float");
                            setCellData(sheet, it+5,i*4+11,sum2,style,"float");
                            setCellData(sheet, it+5,i*4+12,sum3,style,"float");
                            setCellData(sheet, it+5,i*4+13,sum4,style,"float");
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                }

                int planyear = 2017;

                sheet = workbook.getSheet("SGTPLAN");
                if (sheet != null) {
                    String sqlQuery = "SELECT A.ID, A .LPNAME, A .LICENSEXB, A .LP_REG, (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1), (SELECT M .GROUPNAME FROM LUT_MIN_GROUP M WHERE M .GROUPID = A .GROUPID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1) FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = " + planyear + " AND A .XTYPE != 0 AND A .DIVISIONID = 3 ORDER BY A .LPNAME ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    List<Object[]> list= dao.getNativeSQLResult("SELECT * FROM DATA_EXCEL_GEOPLAN1 t where t.planid in (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = "+planyear+" AND (A .XTYPE != 0 OR A.XTYPE IS NULL) AND A .DIVISIONID = 3)", "list");
                    int it = 0;

                    List<DataExcelGeoplan1> realList = new ArrayList<DataExcelGeoplan1>();

                    for(Object[] o : list){
                        try{
                            DataExcelGeoplan1 temp = new DataExcelGeoplan1();
                            temp.setId(Long.parseLong(o[0].toString()));
                            temp.setPlanid(Long.parseLong(o[1].toString()));
                            temp.setNoteid(Long.parseLong(o[2].toString()));
                            temp.setData1(o[3].toString());
                            temp.setData2(o[4].toString());
                            temp.setData3(o[5].toString());
                            temp.setData4(o[6].toString());
                            temp.setData5(Double.parseDouble(o[7].toString()));
                            temp.setData6(Double.parseDouble(o[8].toString()));
                            temp.setData7(Double.parseDouble(o[9].toString()));
                            temp.setOrdernumber(Long.parseLong(o[10].toString()));
                            temp.setIstodotgol(o[11].toString().equalsIgnoreCase("0") ? false : true);
                            realList.add(temp);
                        }
                        catch(NullPointerException e){

                        }
                    }

                    for(Object[] obj : resultObj){
                        it++;
                        setCellData(sheet, it+4,0,it,style,"number");
                        setCellData(sheet, it+4,1,obj[1],style,"string");
                        setCellData(sheet, it+4,2,obj[2],style,"string");
                        setCellData(sheet, it+4,3,obj[3],style,"string");
                        setCellData(sheet, it+4,4,obj[4],style,"string");
                        setCellData(sheet, it+4,5,obj[5],style,"string");
                        setCellData(sheet, it+4,6,obj[6],style,"string");
                        setCellData(sheet, it+4,7,obj[7],style,"string");
                        setCellData(sheet, it+4,8,obj[8],style,"string");
                        setCellData(sheet, it+4,9,obj[9],style,"string");
                        //List<DataExcelGeoplan1> list=(List<DataExcelGeoplan1>) dao.getHQLResult("from DataExcelGeoplan1 t where t.planid = " + obj[0].toString(), "list");

                        List<DataExcelGeoplan1> tempList = new ArrayList<DataExcelGeoplan1>();
                        for(DataExcelGeoplan1 d : realList){
                            if (d.getPlanid().toString().equalsIgnoreCase(obj[0].toString())){
                                tempList.add(d);
                            }
                        }

                        for(int i=0;i<18;i++){
                            Double sum1 = 0.0, sum2 = 0.0;
                            for(DataExcelGeoplan1 d : tempList){
                                if (d.getData1().equalsIgnoreCase((i+1)+".0")){
                                    if (d.getData6() != null){
                                        sum1 = sum1 + d.getData6();
                                    }
                                    if (d.getData7() != null){
                                        sum2 = sum2 + d.getData7();
                                    }
                                }
                            }
                            setCellData(sheet, it+4,i*2+10,sum1,style,"float");
                            setCellData(sheet, it+4,i*2+11,sum2,style,"float");
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                }
                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Уул - Маягт 10-н мэдээ
            else if (id == 8) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningStat10.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String[] vals = {
                            "like '%Нийт уулын ажилд хамрагдсан талбай%'",
                            "like '%Урд онуудад хийгдсэн техникийн нөхөн сэргээлт%'",
                            "like '%Урд онуудад хийгдсэн биологоийн нөхөн сэргээлт%'",
                            "like '%Тус онд уулын ажилд хамрагдсан талбай%'",
                            "like '%Техникийн нөхөн сэргээлт хийсэн талбай%'",
                            "like '%Техникийн нөхөн сэргээлт- гадаад овоолго%'",
                            "like '%Техникийн нөхөн сэргээлт- дотоод овоолго%'",
                            "like '%Биологийн нөхөн сэргээлт%' and DD.DATA3 IS NULL",
                            "like '%Шимт хөрсний Биологийн нөхөн сэргээлт%'",
                            "like '%Биологийн нөхөн сэргээлт%' and DD.DATA3 IS NOT NULL",
                            "like '%Дүйцүүлэн хамгаалах нөхөн сэргээлт%'",
                            "like '%Уурхайг тохижуулах, тосгон орчим хийгдэх нөхөн сэргээлт%'",
                            "like '%Нийт нөхөн сэргээсэн талбай%'"
                    };
                    String sqlQuery = "SELECT A.ID, A.LPNAME,A.LP_REG,A.LICENSEXB,(SELECT LUT_APPSTATUS.STATUSNAMEMON FROM LUT_APPSTATUS WHERE LUT_APPSTATUS.STATUSID = A.REPSTATUSID AND ROWNUM=1), (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1),(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),";

                    for(String v : vals){
                        sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA7 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA10 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA11 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1)";
                        if (!v.equalsIgnoreCase(vals[vals.length-1])){
                            sqlQuery = sqlQuery + ",";
                        }
                    }

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+year+" AND A .XTYPE != 0 AND A .DIVISIONID = 1 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+6,0,iterator+1,style,"number");
                            for(int cellIterator = 1; cellIterator < o.length; cellIterator++){
                                setCellData(sheet, iterator+6,cellIterator,o[cellIterator],style,(cellIterator > 10) ? "float" : "string");
                            }
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,year,null,"number");
                    String xname = ("Mayagt10" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - Маягт 11-н мэдээ
            else if (id == 9) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningStat11.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String[] vals = {
                            "like '%Хөрс%' AND DD.DATA1 = '1.0'",
                            "like '%Ус%' AND DD.DATA1 = '2.0'",
                            "like '%Агаар%' AND DD.DATA1 = '3.0'",
                            "like '%Ургамал / Амьтан%' AND DD.DATA1 = '4.0'",
                            "like '%Түүх соёлын дурсгалт газар%' AND DD.DATA1 = '5.0'",
                            "like '%Оршин суугчид%' AND DD.DATA1 = '6.0'",
                            "like '%Уурхайн байгаль орчны удирдлага зохион байгуулалт%' AND DD.DATA1 = '7.0'",
                            "like '%Бусад%' AND DD.DATA1 = '8.0'",
                            "like '%НИЙТ%' AND DD.DATA1 IS NULL"
                    };
                    String sqlQuery = "SELECT A.ID, A.LPNAME,A.LP_REG,A.LICENSEXB,(SELECT LUT_APPSTATUS.STATUSNAMEMON FROM LUT_APPSTATUS WHERE LUT_APPSTATUS.STATUSID = A.REPSTATUSID AND ROWNUM=1), (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1),(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),";

                    for(String v : vals){
                        sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA6 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA9 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA10 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1)";
                        if (!v.equalsIgnoreCase(vals[vals.length-1])){
                            sqlQuery = sqlQuery + ",";
                        }
                    }

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+reportyear+" AND A .XTYPE != 0 AND A .DIVISIONID = 1 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+6,0,iterator+1,style,"number");
                            for(int cellIterator = 1; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+6,cellIterator,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+6,cellIterator,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Mayagt11" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - Маягт 12-н мэдээ
            else if (id == 10) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningStat12.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "SELECT A.ID, A.LPNAME,A.LP_REG,A.LICENSEXB,(SELECT LUT_APPSTATUS.STATUSNAMEMON FROM LUT_APPSTATUS WHERE LUT_APPSTATUS.STATUSID = A.REPSTATUSID AND ROWNUM=1), (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1),(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),";

                    for(int iterator = 1;iterator <=57;iterator++){
                        sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA6 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA7 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1)";
                        if (iterator!=57){
                            sqlQuery = sqlQuery + ",";
                        }
                    }

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+reportyear+" AND A .XTYPE != 0 AND A .DIVISIONID = 1 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+6,0,iterator+1,style,"number");
                            for(int cellIterator = 1; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+6,cellIterator,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+6,cellIterator,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Mayagt12" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // Уул - Маягт 8-н мэдээ
            else if (id == 11) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningStat8.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "SELECT A .LPNAME, A .LP_REG, A .LICENSEXB, A.REPORTYEAR, (SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1),(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),D .DATA2, D .DATA3, D .DATA4, D .DATA5, D .DATA6, D .DATA7, D .DATA8, D .DATA9, D .DATA10, D .DATA11, D .DATA12, D .DATA13, D .DATA14, D .DATA15, D .DATA16, D .DATA17, D .DATA18, D .DATA19, D .DATA20, D .DATA21, D .DATA22 FROM DATA_EXCEL_MINREP8_1 D LEFT JOIN ANNUAL_REGISTRATION A ON D .PLANID = A . ID WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 4 AND A .DIVISIONID = 1 ORDER BY A .LPNAME ASC, D .ORDERNUMBER ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+5,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+5,cellIterator+1,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+5,cellIterator+1,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    //setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Mayagt8" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - АЛТ ОЛБОРЛОГЧ АЖ АХУЙН НЭГЖҮҮДИЙН УУЛЫН АЖЛЫН ТӨЛӨВЛӨГӨӨНИЙ ЯВЦ
            else if (id == 12) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningGoldReport.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                Long planyear = year;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String[] depositids = {"2","1","10","5","45"};
                    for(int dIterator = 0; dIterator < depositids.length ; dIterator++){
                        String sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_16 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID = 1 AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = '1' AND DATA2 LIKE '%Уурхайн%'";

                        List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,1,resultObj.get(0),style,"float");
                        }

                        sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_16 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID IN (1,2,7) AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = '1' AND DATA2 LIKE '%Уурхайн%'";

                        resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,4,resultObj.get(0),style,"float");
                        }

                        sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_15 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID = 1 AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = 1 AND DATA2 LIKE '%Нийт улсын төсөвт оруулах орлого%'";

                        resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,2,resultObj.get(0),style,"float");
                        }

                        sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_15 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID IN (1,2,7) AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = 1 AND DATA2 LIKE '%Нийт улсын төсөвт оруулах орлого%'";

                        resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,5,resultObj.get(0),style,"float");
                        }

                        sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_15 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID = 1 AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = 2.6 AND DATA2 LIKE '%Ашигт малтмалын нөөц ашигласны төлбөр%'";

                        resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,3,resultObj.get(0),style,"float");
                        }

                        sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_15 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID IN (1,2,7) AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = 2.6 AND DATA2 LIKE '%Ашигт малтмалын нөөц ашигласны төлбөр%'";

                        resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,6,resultObj.get(0),style,"float");
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 2,5,special,null,"string");
                    String xname = ("Altnii_statistic" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - 14-р тайлан
            else if (id == 14) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningRep14.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "SELECT A .LPNAME, A .LP_REG, A .LICENSEXB,(SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M .MINERALNAMEMON FROM LUT_MINERALS M WHERE M .MINERALID = A .MINID AND ROWNUM = 1),(SELECT D .DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D .DEPOSITID = A .DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),";

                    for(int i=1;i<=24;i++){
                        sqlQuery = sqlQuery + "(SELECT D.DATA4 FROM DATA_EXCEL_MINREP14 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA5 FROM DATA_EXCEL_MINREP14 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                    }

                    sqlQuery = sqlQuery.substring(0,sqlQuery.length()-1);

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID IN (1, 2, 7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + reportyear + " AND A .DIVISIONID = 1 ORDER BY A .LPNAME ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+6,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+6,cellIterator+1,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+6,cellIterator+1,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }
                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Report14" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - 15-р тайлан
            else if (id == 15) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningRep15.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                Long reportyear = year;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "SELECT A .LPNAME, A .LP_REG, A .LICENSEXB,(SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M .MINERALNAMEMON FROM LUT_MINERALS M WHERE M .MINERALID = A .MINID AND ROWNUM = 1),(SELECT D .DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D .DEPOSITID = A .DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),";

                    for(int i=1;i<=26;i++){
                        sqlQuery = sqlQuery + "(SELECT D.DATA4 FROM DATA_EXCEL_MINREP15 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA5 FROM DATA_EXCEL_MINREP15 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                    }

                    sqlQuery = sqlQuery.substring(0,sqlQuery.length()-1);

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID IN (1, 2, 7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + reportyear + " AND A .DIVISIONID = 1 ORDER BY A .LPNAME ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+6,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+6,cellIterator+1,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+6,cellIterator+1,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }
                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Report15" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //Бүх тайлан төлөвлөгөөний жагсаалт
            else if (id == 16) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/allTemplate.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                /*style.setWrapText(true);*/

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                /*int reportyear = 2016;*/

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "select ANNUAL_REGISTRATION.LPNAME AAN_NER, (SELECT LUT_APPSTATUS.STATUSNAMEMON FROM LUT_APPSTATUS WHERE LUT_APPSTATUS.STATUSID = ANNUAL_REGISTRATION.REPSTATUSID) TULUV, (SELECT LUT_REPORTTYPE.REPORTTYPENAMEMON FROM LUT_REPORTTYPE WHERE LUT_REPORTTYPE.REPORTTYPEID = ANNUAL_REGISTRATION.REPORTTYPE) TURUL, ANNUAL_REGISTRATION.REPORTYEAR TAILANGIIN_ON,ANNUAL_REGISTRATION.LICENSEXB UNDSEN_TZ,(CASE WHEN ANNUAL_REGISTRATION.XTYPE = 0 THEN 'YES' ELSE 'NO' END) X_ESEH,(SELECT REG_REPORT_REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ WHERE REG_REPORT_REQ.ID = ANNUAL_REGISTRATION.REQID) NEMELT_TZ,(SELECT LUT_MINERALS.MINERALNAMEMON FROM LUT_MINERALS WHERE LUT_MINERALS.MINERALID = ANNUAL_REGISTRATION.MINID) AM_NER,(SELECT LUT_DEPOSIT.DEPOSITNAMEMON FROM LUT_DEPOSIT WHERE LUT_DEPOSIT.DEPOSITID = ANNUAL_REGISTRATION.DEPOSITID) AM_TURUL,(SELECT LNK_REQ_ANN.AIMAGID FROM LNK_REQ_ANN WHERE LNK_REQ_ANN.REQID = ANNUAL_REGISTRATION.REQID AND ROWNUM = 1) AIMAG,(SELECT LNK_REQ_ANN.SUMID FROM LNK_REQ_ANN WHERE LNK_REQ_ANN.REQID = ANNUAL_REGISTRATION.REQID AND ROWNUM = 1) SUM_NER,(SELECT LNK_REQ_ANN.HORDE FROM LNK_REQ_ANN WHERE LNK_REQ_ANN.REQID = ANNUAL_REGISTRATION.REQID AND ROWNUM = 1) TALBAI,ANNUAL_REGISTRATION.SUBMISSIONDATE ILGEESEN_OGNOO, ANNUAL_REGISTRATION.APPROVEDDATE BATALGAAJUULSAN_OGNOO, (SELECT LUT_MINE_TYPE.MTYPENAMEMON FROM LUT_MINE_TYPE WHERE LUT_MINE_TYPE.MTYPEID = (SELECT LNK_REQ_ANN.MINETYPEID FROM LNK_REQ_ANN WHERE LNK_REQ_ANN.REQID = ANNUAL_REGISTRATION.REQID AND ROWNUM = 1)) MINETYPE, ANNUAL_REGISTRATION.REASONID FROM ANNUAL_REGISTRATION where ANNUAL_REGISTRATION.REPSTATUSID IN (1,2,7,3) and ANNUAL_REGISTRATION.DIVISIONID="+loguser.getDivisionid()+" order by ANNUAL_REGISTRATION.ID ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+1,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+1,cellIterator+1,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+1,cellIterator+1,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }
                    /*DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");*/
                    String xname = ("Report16" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - 6.2-р тайлан
            else if (id == 17) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningRep6_2.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "SELECT (SELECT LUT_REPORTTYPE.REPORTTYPENAMEMON FROM LUT_REPORTTYPE WHERE LUT_REPORTTYPE.REPORTTYPEID = A.REPORTTYPE), A.REPORTYEAR, A .LICENSEXB, (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1), A .LPNAME, (SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT M .MINERALNAMEMON FROM LUT_MINERALS M WHERE M .MINERALID = A .MINID AND ROWNUM = 1),(SELECT D .DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D .DEPOSITID = A .DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT LUT_CONCENTRATION.NAMEMON FROM LUT_CONCENTRATION WHERE LUT_CONCENTRATION.ID = (SELECT LNK_REQ_ANN.CONCETRATE FROM LNK_REQ_ANN WHERE LNK_REQ_ANN. REQID = A .REQID AND ROWNUM = 1)),";

                    for(int i=1;i<=12;i++){
                        sqlQuery = sqlQuery + "(SELECT D.DATA4 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA5 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA6 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA7 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA8 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA9 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA10 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA11 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA12 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                    }

                    sqlQuery = sqlQuery.substring(0,sqlQuery.length()-1);

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 4 AND A .DIVISIONID = 1 ORDER BY A .LPNAME ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+3,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+3,cellIterator+1,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+3,cellIterator+1,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }
                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    /*setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");*/
                    String xname = ("Report6-2" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/excel/{id}", method = RequestMethod.GET)
    public String generateReport(HttpServletResponse response, HttpServletRequest req, @PathVariable Long id) {
        String appPath = req.getSession().getServletContext().getRealPath("");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String currentdate = dateFormat.format(date);
        try {
            //Маягт 2-н мэдээ - Уулын хэлтэс
            if (id == 1) {
                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat1.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                Sheet sheet = workbook.getSheet("SGT");
                List<Object[]> ans = dao.getNativeSQLResult("SELECT T.ID, T.REQID, T.REPORTYEAR, T.LICENSEXB, DEP.DEPOSITNAMEMON, LIC.LOCATIONAIMAG, LIC.LOCATIONSOUM, LIC.AREANAMEMON, LIC.AREASIZE, T.LPNAME, T.APPROVEDDATE FROM ANNUAL_REGISTRATION T LEFT JOIN LUT_DEPOSIT DEP ON DEP.DEPOSITID = T.DEPOSITID LEFT JOIN SUB_LICENSES LIC ON LIC.LICENSENUM = T.LICENSENUM WHERE T.ISTODOTGOL = 0 AND T.REPSTATUSID = 1 AND T.REPORTYEAR = 2017 AND T.REPORTTYPE = 3 AND T.DIVISIONID = 1 AND T.XTYPE != 0 ORDER BY T.LICENSEXB", "list");
                if (ans != null && ans.size() > 0) {
                    for (int i = 0, rowIterator = 2; i < ans.size(); i++, rowIterator++) {
                        Object[] an = ans.get(i);
                        List<LnkReqAnn> reqanns = (List<LnkReqAnn>) dao.getHQLResult("from LnkReqAnn t where t.reqid='" + an[1] + "' order by t.id desc", "list");
                        List<DataMinPlan1> data11 = (List<DataMinPlan1>) dao.getHQLResult("from DataMinPlan1 t where t.planid='" + an[0].toString() + "' and t.dataIndex in (1,2,3,4,5) order by t.id asc", "list");
                        LnkReqAnn reqann = null;
                        if (reqanns != null && reqanns.size() > 0) {
                            reqann = reqanns.get(0);
                        }

                        List<String> data21Blocks = null;
                        List<Double> data27 = null;
                        List<Object> data4 = null;
                        if (reqann != null) {
                            if (reqann.getIsmatter() == 1) {
                                data21Blocks = (List<String>) dao.getHQLResult("select distinct(t.data4) from DataMinPlan2_1 t where t.planid='" + an[0].toString() + "' and t.type = 1 and t.data4 is not null", "list");
                                data27 = (List<Double>) dao.getHQLResult("select data22 from DataMinPlan2_1 t where t.planid='" + an[0].toString() + "' and t.type = 2 and t.data1 = 'Нийт'", "list");
                                data4 = (List<Object>) dao.getHQLResult("select data3 from DataMinPlan4_1 t where t.planid='" + an[0].toString() + "' and t.type = 1 and t.data1 like '%Өмнөх онуудын үлдэгдэл үйлдвэрлэлийн нөөц (хүдэр, элс)%'", "list");
                            } else if (reqann.getIsmatter() == 2) {
                                data21Blocks = (List<String>) dao.getHQLResult("select distinct(t.data4) from DataMinPlan2_2 t where t.planid='" + an[0].toString() + "' and t.type = 1 and t.data4 is not null", "list");
                                data27 = (List<Double>) dao.getHQLResult("select data22 from DataMinPlan2_2 t where t.planid='" + an[0].toString() + "' and t.type = 2 and t.data1 = 'Нийт'", "list");
                                data4 = (List<Object>) dao.getHQLResult("select data3 from DataMinPlan4_2 t where t.planid='" + an[0].toString() + "' and t.type = 1 and t.dataindex=1 and t.data1 like '%Өмнөх онуудын үлдэгдэл үйлдвэрлэлийн нөөц (хүдэр, элс)%'", "list");
                            }
                        }

                        List<DataMinPlan3> data3 = (List<DataMinPlan3>) dao.getHQLResult("from DataMinPlan3 t where t.planid='" + an[0].toString() + "' and (t.data1 like '%Бэлтгэл малталт%' or t.data1 like '%Үндсэн малталт%' or t.data1 like '%Хүдэр (элс) олборлолт%' or t.data1 like '%Хүдэр (элс) боловсруулалт%') order by t.id", "list");

                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null) {
                            currentRow = sheet.createRow(rowIterator);
                        }

                        // 1
                        Cell currentCell = currentRow.getCell(0);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(0);
                        }
                        currentCell.setCellStyle(style);
                        currentCell.setCellValue(i + 1);

                        // 2
                        currentCell = currentRow.getCell(1);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(1);
                        }
                        currentCell.setCellStyle(style);
                        if (an[3] != null) {
                            currentCell.setCellValue(an[3].toString());
                        }

                        // 3
                        currentCell = currentRow.getCell(2);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(2);
                        }
                        currentCell.setCellStyle(style);
                        if (an[4] != null) {
                            currentCell.setCellValue(an[4].toString());
                        }

                        // 4
                        currentCell = currentRow.getCell(3);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(3);
                        }
                        currentCell.setCellStyle(style);
                        if (an[5] != null) {
                            currentCell.setCellValue(an[5].toString());
                        }

                        // 5
                        currentCell = currentRow.getCell(4);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(4);
                        }
                        currentCell.setCellStyle(style);
                        if (an[6] != null) {
                            currentCell.setCellValue(an[6].toString());
                        }

                        // 6
                        currentCell = currentRow.getCell(5);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(5);
                        }
                        currentCell.setCellStyle(style);
                        if (an[7] != null) {
                            currentCell.setCellValue(an[7].toString());
                        }

                        // 7
                        currentCell = currentRow.getCell(6);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(6);
                        }
                        currentCell.setCellStyle(style);
                        if (an[8] != null) {
                            currentCell.setCellValue(an[8].toString());
                        }

                        // 8
                        currentCell = currentRow.getCell(7);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(7);
                        }
                        currentCell.setCellStyle(style);
                        if (an[9] != null) {
                            currentCell.setCellValue(an[9].toString());
                        }

                        // 9
                        currentCell = currentRow.getCell(8);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(8);
                        }
                        currentCell.setCellStyle(style);

                        // 10
                        currentCell = currentRow.getCell(9);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(9);
                        }
                        currentCell.setCellStyle(style);

                        // 11
                        currentCell = currentRow.getCell(10);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(10);
                        }
                        currentCell.setCellStyle(style);

                        // 12
                        currentCell = currentRow.getCell(11);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(11);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue(reqann.getAppdate());
                        }

                        // 13
                        currentCell = currentRow.getCell(12);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(12);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue((reqann.getStatebudgetid() == 1) ? "Тийм" : "Үгүй");
                        }

                        // 14
                        currentCell = currentRow.getCell(13);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(13);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue(reqann.getWorkyear());
                        }

                        // 15
                        currentCell = currentRow.getCell(14);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(14);
                        }

                        currentCell.setCellStyle(style);
                        for (DataMinPlan1 d : data11) {
                            if (d.getDataIndex() == 1 && d.getData3() != null) {
                                currentCell.setCellValue(d.getData3());
                            }
                        }

                        // 16
                        currentCell = currentRow.getCell(15);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(15);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue((reqann.getMinetypeid() == 1) ? "Ил" : ((reqann.getMinetypeid() == 2) ? "Далд" : "Хосолсон"));
                        }

                        // 17
                        currentCell = currentRow.getCell(16);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(16);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue(reqann.getStartdate());
                        }
                        /*for (DataMinPlan1 d : data11) {
                            if (d.getDataIndex() == 5 && d.getData5() != null) {
                                currentCell.setCellValue(d.getData5());
                            }
                        }*/

                        // 18
                        currentCell = currentRow.getCell(17);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(17);
                        }
                        currentCell.setCellStyle(style);
                        if (reqann != null) {
                            currentCell.setCellValue(((reqann.getKomissdate() != null) ? reqann.getKomissdate() : "") + " - " + ((reqann.getKomissakt() != null) ? reqann.getKomissakt() : ""));
                        }

                        // 19
                        currentCell = currentRow.getCell(18);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(18);
                        }
                        currentCell.setCellStyle(style);

                        // 20
                        currentCell = currentRow.getCell(19);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(19);
                        }
                        currentCell.setCellStyle(style);

                        // 21
                        currentCell = currentRow.getCell(20);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(20);
                        }

                        currentCell.setCellStyle(style);
                        for (DataMinPlan1 d : data11) {
                            if (d.getDataIndex() == 2 && d.getData3() != null) {
                                currentCell.setCellValue(d.getData3());
                            }
                        }

                        // 22
                        currentCell = currentRow.getCell(21);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(21);
                        }

                        currentCell.setCellStyle(style);
                        for (DataMinPlan1 d : data11) {
                            if (d.getDataIndex() == 3 && d.getData3() != null) {
                                currentCell.setCellValue(d.getData3());
                            }
                        }

                        // 23
                        currentCell = currentRow.getCell(22);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(22);
                        }
                        currentCell.setCellStyle(style);
                        for (DataMinPlan1 d : data11) {
                            if (d.getDataIndex() == 4 && d.getData3() != null) {
                                currentCell.setCellValue(d.getData3());
                            }
                        }

                        // 24
                        currentCell = currentRow.getCell(23);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(23);
                        }
                        currentCell.setCellStyle(style);

                        // 25
                        currentCell = currentRow.getCell(24);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(24);
                        }
                        currentCell.setCellStyle(style);
                        if (an[10] != null) {
                            currentCell.setCellValue(an[10].toString());
                        }

                        // 26
                        currentCell = currentRow.getCell(25);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(25);
                        }
                        currentCell.setCellStyle(style);
                        String blocks = "";
                        if (data21Blocks != null) {
                            for (String s : data21Blocks) {
                                blocks = blocks + (s + ", ");
                            }
                        }
                        currentCell.setCellValue(blocks);

                        // 27
                        currentCell = currentRow.getCell(26);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(26);
                        }
                        currentCell.setCellStyle(style);
                        if (data27 != null && data27.size() > 0 && data27.get(0) != null) {
                            currentCell.setCellValue(data27.get(0));
                        }

                        // 28
                        currentCell = currentRow.getCell(27);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(27);
                        }
                        currentCell.setCellStyle(style);
                        if (data3 != null && data3.size() > 0) {
                            for (DataMinPlan3 d : data3) {
                                if (d.getData1() != null && d.getData1().indexOf("Бэлтгэл малталт") > -1 && d.getData3() != null) {
                                    currentCell.setCellValue(d.getData3());
                                }
                            }

                        }

                        // 29
                        currentCell = currentRow.getCell(28);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(28);
                        }
                        currentCell.setCellStyle(style);
                        if (data3 != null && data3.size() > 0) {
                            for (DataMinPlan3 d : data3) {
                                if (d.getData1() != null && d.getData1().indexOf("Үндсэн малталт") > -1 && d.getData3() != null) {
                                    currentCell.setCellValue(d.getData3());
                                }
                            }

                        }

                        // 30
                        currentCell = currentRow.getCell(29);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(29);
                        }
                        currentCell.setCellStyle(style);
                        if (data3 != null && data3.size() > 0) {
                            for (DataMinPlan3 d : data3) {
                                if (d.getData1() != null && d.getData1().indexOf("Хүдэр (элс) олборлолт") > -1 && d.getData3() != null) {
                                    currentCell.setCellValue(d.getData3());
                                }
                            }

                        }

                        // 31
                        currentCell = currentRow.getCell(30);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(30);
                        }
                        currentCell.setCellStyle(style);
                        if (data4 != null && data4.size() > 0 && data4.get(0) != null) {
                            currentCell.setCellValue(data4.get(0).toString());
                        }

                        // 32
                        currentCell = currentRow.getCell(31);
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(31);
                        }
                        currentCell.setCellStyle(style);
                        if (data3 != null && data3.size() > 0) {
                            for (DataMinPlan3 d : data3) {
                                if (d.getData1() != null && d.getData1().indexOf("Хүдэр (элс) боловсруулалт") > -1 && d.getData3() != null) {
                                    currentCell.setCellValue(d.getData3());
                                }
                            }

                        }

                    }
                }

                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Хувийн хөрөнгөөр хийсэн геологи хайгуулын ажил
            else if (id == 2){

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat2.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                Sheet sheet = workbook.getSheet("SGTREPORT");
                int reportyear = 2016;
                List<Long> reportids = (List<Long>)dao.getHQLResult("select t.id from AnnualRegistration t where t.istodotgol = 0 and t.divisionid = 3 and t.reporttype = 4 and t.repstatusid = 1 and t.reportyear = "+reportyear + " and t.xtype != 0", "list");
                Row currentRow = sheet.getRow(1);
                if (currentRow!=null){
                    Cell currentCell = currentRow.getCell(5);
                    if (currentCell != null){
                        currentCell = currentRow.createCell(5);
                    }
                    currentCell.setCellValue(reportyear);
                }
                if (reportids.size() > 0){
                    for(int i=1;i<=18;i++){

                        String sql = "select SUM(data5), SUM(data6),SUM(data8),SUM(data9) from DATA_EXCEL_GEOREP1 t where t.planid in (select a.id from ANNUAL_REGISTRATION a where a.istodotgol = 0 and a.divisionid = 3 and a.reporttype = 4 and a.repstatusid = 1 and a.reportyear = "+reportyear + " and a.xtype != 0) and t.data1 = '"+i+".0'";

                        List<Object[]> results = (List<Object[]>)dao.getNativeSQLResult(sql, "list");

                        if (results.size() > 0){
                            Object[] data = (Object[]) results.get(0);
                            currentRow = sheet.getRow(i + 3);
                            if (currentRow != null){
                                for(int j=0;j<4;j++){
                                    Cell currentCell = currentRow.getCell(j + 2);
                                    if (currentCell == null){
                                        currentCell = currentRow.createCell(j+2);
                                    }
                                    if (data[j] != null){
                                        currentCell.setCellValue(Double.parseDouble(data[j].toString()));
                                    }
                                    currentCell.setCellStyle(style);
                                }
                            }
                        }

                    }
                }

                sheet = workbook.getSheet("SGTPLAN");
                int planyear = 2017;
                List<Long> planids = (List<Long>)dao.getHQLResult("select t.id from AnnualRegistration t where t.istodotgol = 0 and t.divisionid = 3 and t.reporttype = 3 and t.repstatusid = 1 and t.reportyear = "+planyear + " and t.xtype != 0", "list");
                currentRow = sheet.getRow(1);
                if (currentRow!=null){
                    Cell currentCell = currentRow.getCell(3);
                    if (currentCell != null){
                        currentCell = currentRow.createCell(3);
                    }
                    currentCell.setCellValue(planyear);
                }
                if (planids.size() > 0){
                    for(int i=1;i<=18;i++){
                        String sql = "select SUM(data6),SUM(data7) from DATA_EXCEL_GEOPLAN1 t where t.planid in (select a.id from ANNUAL_REGISTRATION a where a.istodotgol = 0 and a.divisionid = 3 and a.reporttype = 3 and a.repstatusid = 1 and a.reportyear = "+planyear + " and a.xtype != 0) and t.data1 = '"+i+".0'";
                        List<Object[]> results = (List<Object[]>)dao.getNativeSQLResult(sql, "list");
                        if (results.size() > 0){
                            Object[] data = (Object[]) results.get(0);
                            currentRow = sheet.getRow(i + 3);
                            if (currentRow != null){
                                for(int j=0;j<2;j++){
                                    Cell currentCell = currentRow.getCell(j + 2);
                                    if (currentCell == null){
                                        currentCell = currentRow.createCell(j+2);
                                    }
                                    if (data[j] != null){
                                        currentCell.setCellValue(Double.parseDouble(data[j].toString()));
                                    }
                                    currentCell.setCellStyle(style);
                                }
                            }
                        }

                    }
                }

                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Хайгуул - Маягт 3-ын мэдээ
            else if (id == 3){

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat3.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                String sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A.GROUPID), (SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), D.DATA2, D.DATA3, D.DATA4, D.DATA5, D.DATA6, D.DATA7, D.DATA8, D.DATA9, D.DATA10, D.DATA12, D.DATA13 FROM DATA_EXCEL_GEOREP3 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = 'TSOONOG' AND D .DATA2 != 'Нийт' AND A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = 2016 AND A. XTYPE != 0 ORDER BY D.PLANID ASC, D.ORDERNUMBER ASC";
                List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                Sheet sheet = workbook.getSheet("SGT_TSOONOG");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<17;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A.GROUPID), (SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), D.DATA2, D.DATA3, D.DATA4, D.DATA5, D.DATA6, D.DATA7, D.DATA8, D.DATA9, D.DATA10, D.DATA11, D.DATA12, D.DATA13 FROM DATA_EXCEL_GEOREP3 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = 'SUVAG' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A .REPORTTYPE = 4 AND A.ISTODOTGOL = 0 AND A .REPORTYEAR = 2016 AND A. XTYPE != 0 ORDER BY D.PLANID ASC, D.ORDERNUMBER ASC";
                resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                sheet = workbook.getSheet("SGT_SUVAG");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<18;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A.GROUPID), (SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), (SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1), D.DATA2, D.DATA3, D.DATA4, D.DATA5, D.DATA6, D.DATA7, D.DATA8, D.DATA9, D.DATA10, D.DATA12, D.DATA13 FROM DATA_EXCEL_GEOREP3 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = 'SHURF' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = 2016 AND A.ISTODOTGOL = 0 AND A. XTYPE != 0 ORDER BY D.PLANID ASC, D.ORDERNUMBER ASC";
                resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                sheet = workbook.getSheet("SGT_SHURF");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<17;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Хайгуул - Маягт 5-ын мэдээ
            else if (id == 4){

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat4.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                String sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A .GROUPID),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),D .DATA2,D .DATA3,D .DATA4,D .DATA5,D .DATA6,D .DATA7,D .DATA8,D .DATA9,D .DATA10,D .DATA11,D .DATA12,D .DATA13,D .DATA14,D .DATA15,D .DATA16 FROM DATA_EXCEL_GEOREP5 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = '1' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A.ISTODOTGOL = 0 AND A .DIVISIONID = 3 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = 2016 AND A .XTYPE != 0 ORDER BY D .PLANID ASC, D .ORDERNUMBER ASC";
                List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                Sheet sheet = workbook.getSheet("SGT_ORD");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<21;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A .GROUPID),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),D .DATA2,D .DATA3,D .DATA4,D .DATA5,D .DATA6,D .DATA7,D .DATA8,D .DATA9,D .DATA10,D .DATA11,D .DATA12,D .DATA13,D .DATA14,D .DATA15,D .DATA16 FROM DATA_EXCEL_GEOREP5 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = '2' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A.ISTODOTGOL = 0 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = 2016 AND A .XTYPE != 0 ORDER BY D .PLANID ASC, D .ORDERNUMBER ASC";
                resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                sheet = workbook.getSheet("SGT_ILREL");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<21;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A .GROUPID),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),D .DATA2,D .DATA3,D .DATA4,D .DATA5,D .DATA6,D .DATA7,D .DATA8,D .DATA9,D .DATA10,D .DATA11,D .DATA12,D .DATA13,D .DATA14,D .DATA15,D .DATA16 FROM DATA_EXCEL_GEOREP5 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = '3' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A.ISTODOTGOL = 0 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = 2016 AND A .XTYPE != 0 ORDER BY D .PLANID ASC, D .ORDERNUMBER ASC";
                resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                sheet = workbook.getSheet("SGT_ERDES");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<21;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                sqlQuery = "SELECT A .LICENSEXB, (SELECT LUT_MIN_GROUP.GROUPNAME FROM LUT_MIN_GROUP WHERE LUT_MIN_GROUP.GROUPID = A .GROUPID),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),D .DATA2,D .DATA3,D .DATA4,D .DATA5,D .DATA6,D .DATA7,D .DATA8,D .DATA9,D .DATA10,D .DATA11,D .DATA12,D .DATA13,D .DATA14,D .DATA15,D .DATA16 FROM DATA_EXCEL_GEOREP5 D LEFT JOIN ANNUAL_REGISTRATION A ON D .planid = A . ID WHERE D . TYPE = '4' AND D .DATA2 != 'Нийт' AND A .REPSTATUSID = 1 AND A .DIVISIONID = 3 AND A.ISTODOTGOL = 0 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = 2016 AND A .XTYPE != 0 ORDER BY D .PLANID ASC, D .ORDERNUMBER ASC";
                resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                sheet = workbook.getSheet("SGT_ELEMENT");

                for(int i=0,rowIterator=3;i<resultObj.size();i++,rowIterator++){
                    Object[] data = resultObj.get(i);
                    if (data!=null){
                        Row currentRow = sheet.getRow(rowIterator);
                        if (currentRow == null){
                            currentRow = sheet.createRow(rowIterator);
                        }
                        for(int j=0;j<21;j++){
                            Cell currentCell = currentRow.getCell(j);
                            if (currentCell == null){
                                currentCell = currentRow.createCell(j);
                            }
                            if (data[j] != null){
                                currentCell.setCellValue(data[j].toString());
                            }
                            currentCell.setCellStyle(style);
                        }
                    }
                }

                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (id == 5){

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat5.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null){
                    Long count3 = (long)0;
                    String sqlQuery = "select ANNUAL_REGISTRATION.REPSTATUSID, COUNT(*) from ANNUAL_REGISTRATION where ANNUAL_REGISTRATION.LICENSEXB like 'XV-%' and ANNUAL_REGISTRATION.DIVISIONID = 3 and ANNUAL_REGISTRATION.ISTODOTGOL = 0 AND ANNUAL_REGISTRATION.REPORTTYPE = 4 and ANNUAL_REGISTRATION.REPORTYEAR = " + reportyear + " and ANNUAL_REGISTRATION.REPSTATUSID != 0 GROUP BY ANNUAL_REGISTRATION.REPSTATUSID";
                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                    if (resultObj != null && resultObj.size() > 0){
                        for(Object[] o : resultObj){
                            if (o[0].toString().equalsIgnoreCase("7")){
                                setCellData(sheet, 4,2,o[1],style,"number");
                                count3 = count3 + Long.parseLong(o[1].toString());
                            }
                            else if (o[0].toString().equalsIgnoreCase("2")){
                                setCellData(sheet, 5,2,o[1],style,"number");
                                count3 = count3 + Long.parseLong(o[1].toString());
                            }
                            else if (o[0].toString().equalsIgnoreCase("1")){
                                setCellData(sheet, 6,2,o[1],style,"number");
                                count3 = count3 + Long.parseLong(o[1].toString());
                            }
                        }
                    }
                    setCellData(sheet, 3,2,count3,style,"number");

                    Long count1 = (long)0;
                    sqlQuery = "select ANNUAL_REGISTRATION.REPSTATUSID, COUNT(*) from ANNUAL_REGISTRATION where ANNUAL_REGISTRATION.ISTODOTGOL = 0 AND ANNUAL_REGISTRATION.LICENSEXB like 'MV-%' and ANNUAL_REGISTRATION.DIVISIONID = 3 and ANNUAL_REGISTRATION.REPORTTYPE = 4 and ANNUAL_REGISTRATION.REPORTYEAR = "+reportyear+" and ANNUAL_REGISTRATION.REPSTATUSID != 0 GROUP BY ANNUAL_REGISTRATION.REPSTATUSID";
                    resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                    if (resultObj != null && resultObj.size() > 0){
                        for(Object[] o : resultObj){
                            if (o[0].toString().equalsIgnoreCase("7")){
                                setCellData(sheet, 4,3,o[1],style,"number");
                                count1 = count1 + Long.parseLong(o[1].toString());
                            }
                            else if (o[0].toString().equalsIgnoreCase("2")){
                                setCellData(sheet, 5,3,o[1],style,"number");
                                count1 = count1 + Long.parseLong(o[1].toString());
                            }
                            else if (o[0].toString().equalsIgnoreCase("1")){
                                setCellData(sheet, 6,3,o[1],style,"number");
                                count1 = count1 + Long.parseLong(o[1].toString());
                            }
                        }
                    }
                    setCellData(sheet, 3,3,count1,style,"number");

                    sqlQuery = "select ANNUAL_REGISTRATION.GROUPID, COUNT(*) from ANNUAL_REGISTRATION where ANNUAL_REGISTRATION.ISTODOTGOL = 0 AND ANNUAL_REGISTRATION.LICENSEXB like 'XV-%' and ANNUAL_REGISTRATION.DIVISIONID = 3 and ANNUAL_REGISTRATION.REPORTTYPE = 4 and ANNUAL_REGISTRATION.REPORTYEAR = "+reportyear+" and ANNUAL_REGISTRATION.REPSTATUSID != 0 GROUP BY ANNUAL_REGISTRATION.GROUPID";
                    resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                    if (resultObj != null && resultObj.size() > 0){
                        for(Object[] o : resultObj){
                            if (o[0].toString().equalsIgnoreCase("10")){
                                setCellData(sheet, 7,2,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("11")){
                                setCellData(sheet, 8,2,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("12")){
                                setCellData(sheet, 9,2,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("13")){
                                setCellData(sheet, 10,2,o[1],style,"number");
                            }
                        }
                    }

                    sqlQuery = "select ANNUAL_REGISTRATION.GROUPID, COUNT(*) from ANNUAL_REGISTRATION where ANNUAL_REGISTRATION.ISTODOTGOL = 0 AND ANNUAL_REGISTRATION.LICENSEXB like 'MV-%' and ANNUAL_REGISTRATION.DIVISIONID = 3 and ANNUAL_REGISTRATION.REPORTTYPE = 4 and ANNUAL_REGISTRATION.REPORTYEAR = "+reportyear+" and ANNUAL_REGISTRATION.REPSTATUSID != 0 GROUP BY ANNUAL_REGISTRATION.GROUPID";
                    resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                    if (resultObj != null && resultObj.size() > 0){
                        for(Object[] o : resultObj){
                            if (o[0].toString().equalsIgnoreCase("10")){
                                setCellData(sheet, 7,3,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("11")){
                                setCellData(sheet, 8,3,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("12")){
                                setCellData(sheet, 9,3,o[1],style,"number");
                            }
                            else if (o[0].toString().equalsIgnoreCase("13")){
                                setCellData(sheet, 10,3,o[1],style,"number");
                            }
                        }
                    }
                }

                DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                Date date1 = new Date();
                String special = dateFormat1.format(date1);
                setCellData(sheet, 12,2,special,null,"string");
                setCellData(sheet, 1,3,reportyear,null,"number");
                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Уул - Маягт 6.2-н мэдээ
            else if (id == 6) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/stat6.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2017;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String[] vals = {" = '1'", " = '2'", " LIKE '%3.1%'", " LIKE '%3.2%'", " LIKE '%3.3%'"," LIKE '%3.4%'"," = '4'", " = '5'"," = '6'", " = '7'"," = '8'"," = '9'"};
                    String sqlQuery = "SELECT A.LPNAME,A.LP_REG,A.LICENSEXB,A.REPORTYEAR,(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1),(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT DATA_MIN_PLAN_6_2.DATA9 FROM DATA_MIN_PLAN_6_2 WHERE DATA_MIN_PLAN_6_2.PLANID = A . ID AND ROWNUM = 1),";
                    for(String tempVal : vals){
                        for(int i=3;i<=8;i++){
                            sqlQuery = sqlQuery + "(SELECT DATA_MIN_PLAN_6_2.DATA"+i+" FROM DATA_MIN_PLAN_6_2 WHERE DATA_MIN_PLAN_6_2.PLANID = A.ID AND ROWNUM = 1 AND DATA_MIN_PLAN_6_2.DATA1 "+tempVal+")";
                            if (!(i==8 && tempVal.equalsIgnoreCase(vals[vals.length - 1]))){
                                sqlQuery = sqlQuery + ",";
                            }
                        }
                    }

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = "+reportyear+" AND A .XTYPE != 0 AND A .DIVISIONID = 1 ORDER BY A.LPNAME ASC";
                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+5,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                setCellData(sheet, iterator+5,cellIterator+1,o[cellIterator],style,"string");
                            }
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    //setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Mayagt6.2" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (id == 7) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/statgeo1.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGTREPORT");
                if (sheet != null) {
                    String sqlQuery = "SELECT A.ID, A .LPNAME, A .LICENSEXB, A .LP_REG, (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1), (SELECT M .GROUPNAME FROM LUT_MIN_GROUP M WHERE M .GROUPID = A .GROUPID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1) FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + reportyear + " AND A .XTYPE != 0 AND A .DIVISIONID = 3 ORDER BY A .LPNAME ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                    List<Object[]> list= dao.getNativeSQLResult("SELECT * FROM DATA_EXCEL_GEOREP1 t where t.PLANID in (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+reportyear+" AND (A .XTYPE != 0 OR A.XTYPE IS NULL) AND A .DIVISIONID = 3)", "list");
                    List<DataExcelGeorep1> realList = new ArrayList<DataExcelGeorep1>();

                    for(Object[] o : list){
                        try{
                            DataExcelGeorep1 temp = new DataExcelGeorep1();
                            temp.setId(Long.parseLong(o[0].toString()));
                            temp.setPlanid(Long.parseLong(o[1].toString()));
                            temp.setNoteid(Long.parseLong(o[2].toString()));
                            temp.setData1(o[3].toString());
                            temp.setData2(o[4].toString());
                            temp.setData3(o[5].toString());
                            temp.setData4(o[6].toString());
                            temp.setData5(Double.parseDouble(o[7].toString()));
                            temp.setData6(Double.parseDouble(o[8].toString()));
                            temp.setData7(Double.parseDouble(o[9].toString()));
                            temp.setData8(Double.parseDouble(o[10].toString()));
                            temp.setData9(Double.parseDouble(o[11].toString()));
                            temp.setOrdernumber(Long.parseLong(o[12].toString()));
                            temp.setIstodotgol(o[13].toString().equalsIgnoreCase("0") ? false : true);
                            realList.add(temp);
                        }
                        catch(NullPointerException e){

                        }
                    }

                    int it = 0;
                    for(Object[] obj : resultObj){
                        it++;
                        setCellData(sheet, it+5,0,it,style,"number");
                        setCellData(sheet, it+5,1,obj[1],style,"string");
                        setCellData(sheet, it+5,2,obj[2],style,"string");
                        setCellData(sheet, it+5,3,obj[3],style,"string");
                        setCellData(sheet, it+5,4,obj[4],style,"string");
                        setCellData(sheet, it+5,5,obj[5],style,"string");
                        setCellData(sheet, it+5,6,obj[6],style,"string");
                        setCellData(sheet, it+5,7,obj[7],style,"string");
                        setCellData(sheet, it+5,8,obj[8],style,"string");
                        setCellData(sheet, it+5,9,obj[9],style,"string");

                        List<DataExcelGeorep1> tempList = new ArrayList<DataExcelGeorep1>();
                        for(DataExcelGeorep1 d : realList){
                            if (d.getPlanid().toString().equalsIgnoreCase(obj[0].toString())){
                                tempList.add(d);
                            }
                        }

                        for(int i=0;i<18;i++){
                            Double sum1 = 0.0, sum2 = 0.0, sum3 = 0.0, sum4 = 0.0;
                            for(DataExcelGeorep1 d : tempList){
                                if (d.getData1().equalsIgnoreCase((i+1)+".0")){
                                    if (d.getData5() != null){
                                        sum1 = sum1 + d.getData5();
                                    }
                                    if (d.getData6() != null){
                                        sum2 = sum2 + d.getData6();
                                    }
                                    if (d.getData8() != null){
                                        sum3 = sum3 + d.getData8();
                                    }
                                    if (d.getData9() != null){
                                        sum4 = sum4 + d.getData9();
                                    }
                                }
                            }
                            setCellData(sheet, it+5,i*4+10,sum1,style,"float");
                            setCellData(sheet, it+5,i*4+11,sum2,style,"float");
                            setCellData(sheet, it+5,i*4+12,sum3,style,"float");
                            setCellData(sheet, it+5,i*4+13,sum4,style,"float");
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                }

                int planyear = 2017;

                sheet = workbook.getSheet("SGTPLAN");
                if (sheet != null) {
                    String sqlQuery = "SELECT A.ID, A .LPNAME, A .LICENSEXB, A .LP_REG, (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1), (SELECT M .GROUPNAME FROM LUT_MIN_GROUP M WHERE M .GROUPID = A .GROUPID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1) FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = " + planyear + " AND A .XTYPE != 0 AND A .DIVISIONID = 3 ORDER BY A .LPNAME ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    List<Object[]> list= dao.getNativeSQLResult("SELECT * FROM DATA_EXCEL_GEOPLAN1 t where t.planid in (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = "+planyear+" AND (A .XTYPE != 0 OR A.XTYPE IS NULL) AND A .DIVISIONID = 3)", "list");
                    int it = 0;

                    List<DataExcelGeoplan1> realList = new ArrayList<DataExcelGeoplan1>();

                    for(Object[] o : list){
                        try{
                            DataExcelGeoplan1 temp = new DataExcelGeoplan1();
                            temp.setId(Long.parseLong(o[0].toString()));
                            temp.setPlanid(Long.parseLong(o[1].toString()));
                            temp.setNoteid(Long.parseLong(o[2].toString()));
                            temp.setData1(o[3].toString());
                            temp.setData2(o[4].toString());
                            temp.setData3(o[5].toString());
                            temp.setData4(o[6].toString());
                            temp.setData5(Double.parseDouble(o[7].toString()));
                            temp.setData6(Double.parseDouble(o[8].toString()));
                            temp.setData7(Double.parseDouble(o[9].toString()));
                            temp.setOrdernumber(Long.parseLong(o[10].toString()));
                            temp.setIstodotgol(o[11].toString().equalsIgnoreCase("0") ? false : true);
                            realList.add(temp);
                        }
                        catch(NullPointerException e){

                        }
                    }

                    for(Object[] obj : resultObj){
                        it++;
                        setCellData(sheet, it+4,0,it,style,"number");
                        setCellData(sheet, it+4,1,obj[1],style,"string");
                        setCellData(sheet, it+4,2,obj[2],style,"string");
                        setCellData(sheet, it+4,3,obj[3],style,"string");
                        setCellData(sheet, it+4,4,obj[4],style,"string");
                        setCellData(sheet, it+4,5,obj[5],style,"string");
                        setCellData(sheet, it+4,6,obj[6],style,"string");
                        setCellData(sheet, it+4,7,obj[7],style,"string");
                        setCellData(sheet, it+4,8,obj[8],style,"string");
                        setCellData(sheet, it+4,9,obj[9],style,"string");
                        //List<DataExcelGeoplan1> list=(List<DataExcelGeoplan1>) dao.getHQLResult("from DataExcelGeoplan1 t where t.planid = " + obj[0].toString(), "list");

                        List<DataExcelGeoplan1> tempList = new ArrayList<DataExcelGeoplan1>();
                        for(DataExcelGeoplan1 d : realList){
                            if (d.getPlanid().toString().equalsIgnoreCase(obj[0].toString())){
                                tempList.add(d);
                            }
                        }

                        for(int i=0;i<18;i++){
                            Double sum1 = 0.0, sum2 = 0.0;
                            for(DataExcelGeoplan1 d : tempList){
                                if (d.getData1().equalsIgnoreCase((i+1)+".0")){
                                    if (d.getData6() != null){
                                        sum1 = sum1 + d.getData6();
                                    }
                                    if (d.getData7() != null){
                                        sum2 = sum2 + d.getData7();
                                    }
                                }
                            }
                            setCellData(sheet, it+4,i*2+10,sum1,style,"float");
                            setCellData(sheet, it+4,i*2+11,sum2,style,"float");
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                }
                String xname = ("Export-" + id + " - " + currentdate).trim();
                xname = URLEncoder.encode(xname, "UTF-8");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    response.setContentType("application/ms-excel; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Уул - Маягт 10-н мэдээ
            else if (id == 8) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningStat10.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String[] vals = {
                            "like '%Нийт уулын ажилд хамрагдсан талбай%'",
                            "like '%Урд онуудад хийгдсэн техникийн нөхөн сэргээлт%'",
                            "like '%Урд онуудад хийгдсэн биологоийн нөхөн сэргээлт%'",
                            "like '%Тус онд уулын ажилд хамрагдсан талбай%'",
                            "like '%Техникийн нөхөн сэргээлт хийсэн талбай%'",
                            "like '%Техникийн нөхөн сэргээлт- гадаад овоолго%'",
                            "like '%Техникийн нөхөн сэргээлт- дотоод овоолго%'",
                            "like '%Биологийн нөхөн сэргээлт%' and DD.DATA3 IS NULL",
                            "like '%Шимт хөрсний Биологийн нөхөн сэргээлт%'",
                            "like '%Биологийн нөхөн сэргээлт%' and DD.DATA3 IS NOT NULL",
                            "like '%Дүйцүүлэн хамгаалах нөхөн сэргээлт%'",
                            "like '%Уурхайг тохижуулах, тосгон орчим хийгдэх нөхөн сэргээлт%'",
                            "like '%Нийт нөхөн сэргээсэн талбай%'"
                    };
                    String sqlQuery = "SELECT A.ID, A.LPNAME,A.LP_REG,A.LICENSEXB,(SELECT LUT_APPSTATUS.STATUSNAMEMON FROM LUT_APPSTATUS WHERE LUT_APPSTATUS.STATUSID = A.REPSTATUSID AND ROWNUM=1), (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1),(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),";

                    for(String v : vals){
                        sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA7 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA10 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA11 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1)";
                        if (!v.equalsIgnoreCase(vals[vals.length-1])){
                            sqlQuery = sqlQuery + ",";
                        }
                    }

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+reportyear+" AND A .XTYPE != 0 AND A .DIVISIONID = 1 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+6,0,iterator+1,style,"number");
                            for(int cellIterator = 1; cellIterator < o.length; cellIterator++){
                                setCellData(sheet, iterator+6,cellIterator,o[cellIterator],style,(cellIterator > 10) ? "float" : "string");
                            }
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Mayagt10" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - Маягт 11-н мэдээ
            else if (id == 9) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningStat11.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String[] vals = {
                        "like '%Хөрс%' AND DD.DATA1 = '1.0'",
                        "like '%Ус%' AND DD.DATA1 = '2.0'",
                        "like '%Агаар%' AND DD.DATA1 = '3.0'",
                        "like '%Ургамал / Амьтан%' AND DD.DATA1 = '4.0'",
                        "like '%Түүх соёлын дурсгалт газар%' AND DD.DATA1 = '5.0'",
                        "like '%Оршин суугчид%' AND DD.DATA1 = '6.0'",
                        "like '%Уурхайн байгаль орчны удирдлага зохион байгуулалт%' AND DD.DATA1 = '7.0'",
                        "like '%Бусад%' AND DD.DATA1 = '8.0'",
                        "like '%НИЙТ%' AND DD.DATA1 IS NULL"
                    };
                    String sqlQuery = "SELECT A.ID, A.LPNAME,A.LP_REG,A.LICENSEXB,(SELECT LUT_APPSTATUS.STATUSNAMEMON FROM LUT_APPSTATUS WHERE LUT_APPSTATUS.STATUSID = A.REPSTATUSID AND ROWNUM=1), (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1),(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),";

                    for(String v : vals){
                        sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA6 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA9 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA10 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1)";
                        if (!v.equalsIgnoreCase(vals[vals.length-1])){
                            sqlQuery = sqlQuery + ",";
                        }
                    }

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+reportyear+" AND A .XTYPE != 0 AND A .DIVISIONID = 1 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+6,0,iterator+1,style,"number");
                            for(int cellIterator = 1; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+6,cellIterator,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+6,cellIterator,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Mayagt11" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - Маягт 12-н мэдээ
            else if (id == 10) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningStat12.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "SELECT A.ID, A.LPNAME,A.LP_REG,A.LICENSEXB,(SELECT LUT_APPSTATUS.STATUSNAMEMON FROM LUT_APPSTATUS WHERE LUT_APPSTATUS.STATUSID = A.REPSTATUSID AND ROWNUM=1), (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1),(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),";

                    for(int iterator = 1;iterator <=57;iterator++){
                        sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA6 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA7 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1)";
                        if (iterator!=57){
                            sqlQuery = sqlQuery + ",";
                        }
                    }

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = "+reportyear+" AND A .XTYPE != 0 AND A .DIVISIONID = 1 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+6,0,iterator+1,style,"number");
                            for(int cellIterator = 1; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+6,cellIterator,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+6,cellIterator,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Mayagt12" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // Уул - Маягт 8-н мэдээ
            else if (id == 11) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningStat8.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "SELECT A .LPNAME, A .LP_REG, A .LICENSEXB, A.REPORTYEAR, (SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1),(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),D .DATA2, D .DATA3, D .DATA4, D .DATA5, D .DATA6, D .DATA7, D .DATA8, D .DATA9, D .DATA10, D .DATA11, D .DATA12, D .DATA13, D .DATA14, D .DATA15, D .DATA16, D .DATA17, D .DATA18, D .DATA19, D .DATA20, D .DATA21, D .DATA22 FROM DATA_EXCEL_MINREP8_1 D LEFT JOIN ANNUAL_REGISTRATION A ON D .PLANID = A . ID WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 4 AND A .DIVISIONID = 1 ORDER BY A .LPNAME ASC, D .ORDERNUMBER ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+5,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+5,cellIterator+1,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+5,cellIterator+1,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    //setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Mayagt8" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - АЛТ ОЛБОРЛОГЧ АЖ АХУЙН НЭГЖҮҮДИЙН УУЛЫН АЖЛЫН ТӨЛӨВЛӨГӨӨНИЙ ЯВЦ
            else if (id == 12) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningGoldReport.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int planyear = 2018;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String[] depositids = {"2","1","10","5","45"};
                    for(int dIterator = 0; dIterator < depositids.length ; dIterator++){
                        String sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_16 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID = 1 AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = '1' AND DATA2 LIKE '%Уурхайн%'";

                        List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,1,resultObj.get(0),style,"float");
                        }

                        sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_16 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID IN (1,2,7) AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = '1' AND DATA2 LIKE '%Уурхайн%'";

                        resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,4,resultObj.get(0),style,"float");
                        }

                        sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_15 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID = 1 AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = 1 AND DATA2 LIKE '%Нийт улсын төсөвт оруулах орлого%'";

                        resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,2,resultObj.get(0),style,"float");
                        }

                        sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_15 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID IN (1,2,7) AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = 1 AND DATA2 LIKE '%Нийт улсын төсөвт оруулах орлого%'";

                        resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,5,resultObj.get(0),style,"float");
                        }

                        sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_15 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID = 1 AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = 2.6 AND DATA2 LIKE '%Ашигт малтмалын нөөц ашигласны төлбөр%'";

                        resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,3,resultObj.get(0),style,"float");
                        }

                        sqlQuery = "SELECT SUM (DATA4) FROM DATA_MIN_PLAN_15 WHERE PLANID IN (SELECT ID FROM ANNUAL_REGISTRATION WHERE ISTODOTGOL = 0 AND DIVISIONID = 1 AND REPORTTYPE = 3 AND REPORTYEAR = "+planyear+" AND XTYPE != 0 AND REPSTATUSID IN (1,2,7) AND DEPOSITID = "+depositids[dIterator]+") AND DATA1 = 2.6 AND DATA2 LIKE '%Ашигт малтмалын нөөц ашигласны төлбөр%'";

                        resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                        if (resultObj.size() > 0){
                            setCellData(sheet, 5+dIterator,6,resultObj.get(0),style,"float");
                        }
                    }

                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 2,5,special,null,"string");
                    String xname = ("Altnii_statistic" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - 14-р тайлан
            else if (id == 14) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningRep14.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "SELECT A .LPNAME, A .LP_REG, A .LICENSEXB,(SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M .MINERALNAMEMON FROM LUT_MINERALS M WHERE M .MINERALID = A .MINID AND ROWNUM = 1),(SELECT D .DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D .DEPOSITID = A .DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),";

                    for(int i=1;i<=24;i++){
                        sqlQuery = sqlQuery + "(SELECT D.DATA4 FROM DATA_EXCEL_MINREP14 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA5 FROM DATA_EXCEL_MINREP14 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                    }

                    sqlQuery = sqlQuery.substring(0,sqlQuery.length()-1);

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID IN (1, 2, 7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + reportyear + " AND A .DIVISIONID = 1 ORDER BY A .LPNAME ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+6,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+6,cellIterator+1,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+6,cellIterator+1,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }
                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Report14" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - 15-р тайлан
            else if (id == 15) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningRep15.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "SELECT A .LPNAME, A .LP_REG, A .LICENSEXB,(SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M .MINERALNAMEMON FROM LUT_MINERALS M WHERE M .MINERALID = A .MINID AND ROWNUM = 1),(SELECT D .DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D .DEPOSITID = A .DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),";

                    for(int i=1;i<=26;i++){
                        sqlQuery = sqlQuery + "(SELECT D.DATA4 FROM DATA_EXCEL_MINREP15 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA5 FROM DATA_EXCEL_MINREP15 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                    }

                    sqlQuery = sqlQuery.substring(0,sqlQuery.length()-1);

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID IN (1, 2, 7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + reportyear + " AND A .DIVISIONID = 1 ORDER BY A .LPNAME ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+6,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+6,cellIterator+1,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+6,cellIterator+1,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }
                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");
                    String xname = ("Report15" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //Бүх тайлан төлөвлөгөөний жагсаалт
            else if (id == 16) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/allTemplate.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                /*style.setWrapText(true);*/

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                /*int reportyear = 2016;*/

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "select ANNUAL_REGISTRATION.LPNAME AAN_NER, (SELECT LUT_APPSTATUS.STATUSNAMEMON FROM LUT_APPSTATUS WHERE LUT_APPSTATUS.STATUSID = ANNUAL_REGISTRATION.REPSTATUSID) TULUV, (SELECT LUT_REPORTTYPE.REPORTTYPENAMEMON FROM LUT_REPORTTYPE WHERE LUT_REPORTTYPE.REPORTTYPEID = ANNUAL_REGISTRATION.REPORTTYPE) TURUL, ANNUAL_REGISTRATION.REPORTYEAR TAILANGIIN_ON,ANNUAL_REGISTRATION.LICENSEXB UNDSEN_TZ,(CASE WHEN ANNUAL_REGISTRATION.XTYPE = 0 THEN 'YES' ELSE 'NO' END) X_ESEH,(SELECT REG_REPORT_REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ WHERE REG_REPORT_REQ.ID = ANNUAL_REGISTRATION.REQID) NEMELT_TZ,(SELECT LUT_MINERALS.MINERALNAMEMON FROM LUT_MINERALS WHERE LUT_MINERALS.MINERALID = ANNUAL_REGISTRATION.MINID) AM_NER,(SELECT LUT_DEPOSIT.DEPOSITNAMEMON FROM LUT_DEPOSIT WHERE LUT_DEPOSIT.DEPOSITID = ANNUAL_REGISTRATION.DEPOSITID) AM_TURUL,(SELECT LNK_REQ_ANN.AIMAGID FROM LNK_REQ_ANN WHERE LNK_REQ_ANN.REQID = ANNUAL_REGISTRATION.REQID AND ROWNUM = 1) AIMAG,(SELECT LNK_REQ_ANN.SUMID FROM LNK_REQ_ANN WHERE LNK_REQ_ANN.REQID = ANNUAL_REGISTRATION.REQID AND ROWNUM = 1) SUM_NER,(SELECT LNK_REQ_ANN.HORDE FROM LNK_REQ_ANN WHERE LNK_REQ_ANN.REQID = ANNUAL_REGISTRATION.REQID AND ROWNUM = 1) TALBAI,ANNUAL_REGISTRATION.SUBMISSIONDATE ILGEESEN_OGNOO, ANNUAL_REGISTRATION.APPROVEDDATE BATALGAAJUULSAN_OGNOO, (SELECT LUT_MINE_TYPE.MTYPENAMEMON FROM LUT_MINE_TYPE WHERE LUT_MINE_TYPE.MTYPEID = (SELECT LNK_REQ_ANN.MINETYPEID FROM LNK_REQ_ANN WHERE LNK_REQ_ANN.REQID = ANNUAL_REGISTRATION.REQID AND ROWNUM = 1)) MINETYPE, ANNUAL_REGISTRATION.REASONID FROM ANNUAL_REGISTRATION where ANNUAL_REGISTRATION.REPSTATUSID IN (1,2,7,3) and ANNUAL_REGISTRATION.DIVISIONID="+loguser.getDivisionid()+" order by ANNUAL_REGISTRATION.ID ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+1,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+1,cellIterator+1,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+1,cellIterator+1,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }
                    /*DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");*/
                    String xname = ("Report16" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Уул - 6.2-р тайлан
            else if (id == 17) {

                FileInputStream fis = null;
                File formfile = new File(appPath + "assets/reporttemplate/miningRep6_2.xlsx");
                fis = new FileInputStream(formfile);
                Workbook workbook = new XSSFWorkbook(fis);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setWrapText(true);

                XSSFFont font = (XSSFFont) workbook.createFont();
                font.setFontName("Arial");
                style.setFont(font);

                int reportyear = 2016;

                Sheet sheet = workbook.getSheet("SGT");
                if (sheet != null) {
                    String sqlQuery = "SELECT (SELECT LUT_REPORTTYPE.REPORTTYPENAMEMON FROM LUT_REPORTTYPE WHERE LUT_REPORTTYPE.REPORTTYPEID = A.REPORTTYPE), A.REPORTYEAR, A .LICENSEXB, (SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1), A .LPNAME, (SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT M .MINERALNAMEMON FROM LUT_MINERALS M WHERE M .MINERALID = A .MINID AND ROWNUM = 1),(SELECT D .DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D .DEPOSITID = A .DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT LUT_CONCENTRATION.NAMEMON FROM LUT_CONCENTRATION WHERE LUT_CONCENTRATION.ID = (SELECT LNK_REQ_ANN.CONCETRATE FROM LNK_REQ_ANN WHERE LNK_REQ_ANN. REQID = A .REQID AND ROWNUM = 1)),";

                    for(int i=1;i<=12;i++){
                        sqlQuery = sqlQuery + "(SELECT D.DATA4 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA5 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA6 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA7 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA8 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA9 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA10 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA11 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                        sqlQuery = sqlQuery + "(SELECT D.DATA12 FROM DATA_EXCEL_MINREP6_2 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                    }

                    sqlQuery = sqlQuery.substring(0,sqlQuery.length()-1);

                    sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .REPORTTYPE = 4 AND A .DIVISIONID = 1 ORDER BY A .LPNAME ASC";

                    List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");

                    if (resultObj != null && resultObj.size() > 0){
                        for(int iterator=0;iterator<resultObj.size();iterator++){
                            Object[] o = resultObj.get(iterator);
                            setCellData(sheet, iterator+3,0,iterator+1,style,"number");
                            for(int cellIterator = 0; cellIterator < o.length; cellIterator++){
                                if (o[cellIterator] instanceof String){
                                    setCellData(sheet, iterator+3,cellIterator+1,o[cellIterator],style,"string");
                                }
                                else{
                                    setCellData(sheet, iterator+3,cellIterator+1,o[cellIterator],style,"float");
                                }
                            }
                        }
                    }
                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy оны MM сарын dd HH:mm");
                    Date date1 = new Date();
                    String special = dateFormat1.format(date1);
                    /*setCellData(sheet, 1,2,special,null,"string");
                    setCellData(sheet, 1,7,reportyear,null,"number");*/
                    String xname = ("Report6-2" + " - " + currentdate).trim();
                    xname = URLEncoder.encode(xname, "UTF-8");
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.setContentType("application/ms-excel; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + xname + ".xlsx");
                        workbook.write(outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCellData(Sheet sheet, int rownum, int cellnum, Object data, CellStyle style, String type){
        if (sheet != null){
            Row currentRow = sheet.getRow(rownum);
            if (currentRow == null){
                currentRow = sheet.createRow(rownum);
            }
            Cell currentCell = currentRow.getCell(cellnum);
            if (currentCell == null){
                currentCell = currentRow.createCell(cellnum);
            }
            if (data != null){
                if (type.equalsIgnoreCase("number")){
                    currentCell.setCellValue(Long.parseLong(data.toString()));
                }
                else if (type.equalsIgnoreCase("string")){
                    currentCell.setCellValue(data.toString());
                }
                else if (type.equalsIgnoreCase("float")){
                    currentCell.setCellValue(Double.parseDouble(data.toString()));
                }
            }

            if (style != null) {
                currentCell.setCellStyle(style);
            }
        }
    }
}
