package com.peace.web.controller;
import com.peace.users.dao.UserDao;
import com.peace.users.model.mram.AnnualRegistration;
import com.peace.users.model.mram.LnkCommentMain;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExcelGenerator {

    public static void setCellData(Sheet sheet, int rownum, int cellnum, Object data, CellStyle style, String type){
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

    public static ByteArrayInputStream customersToExcel(int reportType, int formId, int planYr, String formName, String appPath, UserDao dao) throws IOException {

        Workbook workbook = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Date curDate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String DateToStr1 = format1.format(curDate);

        if (reportType == 3 && formId == 1) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_1_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.DATA_MIN_PLAN_1.DATAINDEX,DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,DATA8,DATA9,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_1.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t and MRAM.LUT_DEPOSIT.MINERALTYPE=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_1.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                rowCount++;
            }
        }

        if (reportType == 3 && formId == 2) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_1_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then '7' \n" +
                    " when 0 then '0' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.DATA_MIN_PLAN_1.DATAINDEX,DATA1,\n" +
                    "DATA2,DATA3,DATA5,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_1.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR  in (" + planYr + ") \n" +
                    "\t and MRAM.LUT_DEPOSIT.MINERALTYPE=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_1.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(17);
                if (obj[17] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[17].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 3 && formId == 3) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_2_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.DATA_MIN_PLAN_2_1.DATAINDEX,\n" +
                    "DATA1,\n" +
                    " case DATA2 \n" +
                    " when 1 then 'А (Баттай)'\n" +
                    " when 2 then 'В (Бодитой)'\n" +
                    " when 3 then 'С (Боломжтой)' \n" +
                    " when 4 then 'С1 (Бодитой)'  \n" +
                    " when 5 then 'С2 (Боломжтой)'  \n" +
                    "end as DATA2,\n" +
                    "DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,\n" +
                    "DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_2_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_2_1.PLANID\n" +
                    "WHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID = 1 \n" +
                    "\tAND MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\tAND TYPE = 1 \n" +
                    "\tAND MRAM.ANNUAL_REGISTRATION.REPORTTYPE = 3\n" +
                    "\t\torder by  MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_2_1.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(obj[30].toString());
                }
                Cell cell32 = row.createCell(31);
                if (obj[31] != null) {
                    cell32.setCellValue(Double.parseDouble(obj[31].toString()));
                }
                Cell cell33 = row.createCell(32);
                if (obj[32] != null) {
                    cell33.setCellValue(obj[32].toString());
                }
                Cell cell34 = row.createCell(33);
                if (obj[33] != null) {
                    cell34.setCellValue(Double.parseDouble(obj[33].toString()));
                }
                Cell cell35 = row.createCell(34);
                if (obj[34] != null) {
                    cell35.setCellValue(obj[34].toString());
                }
                Cell cell36 = row.createCell(35);
                if (obj[35] != null) {
                    cell36.setCellValue(Double.parseDouble(obj[35].toString()));
                }
                Cell cell37 = row.createCell(36);
                if (obj[36] != null) {
                    cell37.setCellValue(obj[36].toString());
                }
                Cell cell38 = row.createCell(37);
                if (obj[37] != null) {
                    cell38.setCellValue(Double.parseDouble(obj[37].toString()));
                }
                Cell cell39 = row.createCell(38);
                if (obj[38] != null) {
                    cell39.setCellValue(Double.parseDouble(obj[38].toString()));
                }


                rowCount++;
            }

            Sheet sheet1 = workbook.getSheetAt(1);
            Row rowS1 = sheet1.getRow(1);
            Cell cel2l = rowS1.createCell(2);
            cel2l.setCellValue(planYr);
            Row rowS2 = sheet1.getRow(2);
            Cell cels2 = rowS2.createCell(2);
            cels2.setCellValue(DateToStr1);
            String queryStr2 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.DATA_MIN_PLAN_2_1.DATAINDEX,\n" +
                    "DATA1,\n" +
                    " case DATA2 \n" +
                    " when 1 then 'А (Баттай)'\n" +
                    " when 2 then 'В (Бодитой)'\n" +
                    " when 3 then 'С (Боломжтой)' \n" +
                    " when 4 then 'С1 (Бодитой)'  \n" +
                    " when 5 then 'С2 (Боломжтой)'  \n" +
                    "end as DATA2,\n" +
                    "DATA3,DATA4,DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_2_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_2_1.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    " MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t  and TYPE=5 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 \n" +
                    "\t\torder by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_2_1.ID,TYPE\n";
            List<Object[]> objectSheets = dao.getNativeSQLResult(queryStr2, "list");
            int rowCountSheets = 7;
            for (Object[] obj : objectSheets) {
                rowS1 = sheet1.createRow(rowCountSheets);
                Cell cell1 = rowS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = rowS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = rowS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = rowS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = rowS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = rowS1.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = rowS1.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = rowS1.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = rowS1.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = rowS1.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = rowS1.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = rowS1.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = rowS1.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(obj[26].toString());
                }
                Cell cell28 = rowS1.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                rowCountSheets++;
            }
        }
        if (reportType == 3 && formId == 4) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_2_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.DATA_MIN_PLAN_2_2.DATAINDEX,\n" +
                    "DATA1,\n" +
                    " case DATA2 \n" +
                    " when 1 then 'А (Баттай)'\n" +
                    " when 2 then 'В (Бодитой)'\n" +
                    " when 3 then 'С (Боломжтой)' \n" +
                    " when 4 then 'С1 (Бодитой)'  \n" +
                    " when 5 then 'С2 (Боломжтой)'  \n" +
                    "end as DATA2,\n" +
                    "DATA3,DATA4,DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,\n" +
                    "DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "\n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_2_2 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_2_2.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_2_2.ID,\n" +
                    "      \t\tMRAM.DATA_MIN_PLAN_2_2.DATAINDEX";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(obj[26].toString());
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(obj[30].toString());
                }
                Cell cell32 = row.createCell(31);
                if (obj[31] != null) {
                    cell32.setCellValue(Double.parseDouble(obj[31].toString()));
                }
                Cell cell33 = row.createCell(32);
                if (obj[32] != null) {
                    cell33.setCellValue(obj[32].toString());
                }
                Cell cell34 = row.createCell(33);
                if (obj[33] != null) {
                    cell34.setCellValue(Double.parseDouble(obj[33].toString()));
                }
                Cell cell35 = row.createCell(34);
                if (obj[34] != null) {
                    cell35.setCellValue(obj[34].toString());
                }
                Cell cell36 = row.createCell(35);
                if (obj[35] != null) {
                    cell36.setCellValue(Double.parseDouble(obj[35].toString()));
                }
                Cell cell37 = row.createCell(36);
                if (obj[36] != null) {
                    cell37.setCellValue(obj[36].toString());
                }
                Cell cell38 = row.createCell(37);
                if (obj[37] != null) {
                    cell38.setCellValue(Double.parseDouble(obj[37].toString()));
                }
                Cell cell39 = row.createCell(38);
                if (obj[38] != null) {
                    cell39.setCellValue(Double.parseDouble(obj[38].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 3 && formId == 5) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_3.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "TYPE,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,\n" +
                    "DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_3 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_3.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t \t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_3.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(Double.parseDouble(obj[30].toString()));
                }
                Cell cell32 = row.createCell(31);
                if (obj[31] != null) {
                    cell32.setCellValue(Double.parseDouble(obj[31].toString()));
                }
                Cell cell33 = row.createCell(32);
                if (obj[32] != null) {
                    cell33.setCellValue(Double.parseDouble(obj[32].toString()));
                }
                Cell cell34 = row.createCell(33);
                if (obj[33] != null) {
                    cell34.setCellValue(Double.parseDouble(obj[33].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 3 && formId == 6) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_4_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "TYPE,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,\n" +
                    "DATA11,DATA12,DATA13,DATA14,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_4_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_4_1.PLANID\n" +
                    "where \n" +
                    " MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR  =" + planYr + "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_4_1.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 3 && formId == 7) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_4_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    "when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATAINDEX,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_4_2 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_4_2.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and MRAM.ANNUAL_REGISTRATION.REPORTYEAR= " + planYr + " \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_4_2.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 3 && formId == 8) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_5.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATAINDEX,\n" +
                    "case DATA1\n" +
                    " when '1' then 'Идэвхжүүлсэн нүүрс'\n" +
                    " when '2' then 'Калцийн гипохлорид'\n" +
                    " when '3' then 'Идэвхжүүлсэн шохой' \n" +
                    " when '4' then 'Шохой' \n" +
                    " when '5' then 'Флокулянт'\n" +
                    "when '6' then 'Зэсийн сульфат (Цианидыг задлах урвалд католизатор)'\n" +
                    "when '7' then 'Төмрийн сульфат'\n" +
                    "when '8' then 'Натрийн метабисульфит (Цианидыг цианат болгон хувиргахад)'\n" +
                    "when '9' then 'Хүхрийн хүчил'\n" +
                    "when '10' then 'Давсны хүчил'\n" +
                    "when '11' then 'Мөнгөн ус'\n" +
                    "when '12' then 'Азотын хүчил'\n" +
                    "when '13' then 'Бура'\n" +
                    "when '14' then 'Натрийн карбонат'\n" +
                    "when '15' then 'Техникийн сода'\n" +
                    "when '16' then 'Метабисульфит натри'\n" +
                    "when '17' then 'Натрийн сульфит'\n" +
                    "when '18' then 'Натрийн цианид'\n" +
                    "when '19' then 'Натрийн шүлт'\n" +
                    "when '20' then 'Хартугалганы исэл'\n" +
                    "when '21' then 'Антисклант'\n" +
                    "when '22' then 'Дизель (kL)'\n" +
                    "when '23' then 'Флюс'\n" +
                    "when '24' then 'Хаг эсэргүүцэгч'\n" +
                    "when '25' then 'Цемент'\n" +
                    "when '26' then 'Ган бөмбөлөг'\n" +
                    "when '27' then 'Устөрөгчийн хэт исэл'\n" +
                    "when '28' then 'Аммонийн гидроксид' \n" +
                    "when '29' then 'Натрийн хлорид'\n" +
                    "when '30' then 'Пюролит PFA600'\n" +
                    "end as DATA1,\n" +
                    "\n" +
                    "case DATA2\n" +
                    " when '1' then 'C'\n" +
                    " when '2' then 'Ca(OCI)2'\n" +
                    " when '3' then 'Ca(OH)2' \n" +
                    " when '4' then 'CaO' \n" +
                    " when '5' then 'CH2CHCONH2'\n" +
                    "when '6' then 'Cu2CO4'\n" +
                    "when '7' then 'Fe2(SO4)3'\n" +
                    "when '8' then 'H2O5S 2Na'\n" +
                    "when '9' then 'H2SO4'\n" +
                    "when '10' then 'HCI'\n" +
                    "when '11' then 'Hg'\n" +
                    "when '12' then 'HNO3'\n" +
                    "when '13' then 'Na2B4O7'\n" +
                    "when '14' then 'Na2CO3'\n" +
                    "when '15' then 'Na2CO3'\n" +
                    "when '16' then 'Na2S2O5'\n" +
                    "when '17' then 'Na2CO3'\n" +
                    "when '18' then 'NaCN'\n" +
                    "when '19' then 'NaOH'\n" +
                    "when '20' then 'PbO'\n" +
                    "when '21' then '-'\n" +
                    "when '22' then '-'\n" +
                    "when '23' then '-'\n" +
                    "when '24' then '-'\n" +
                    "when '25' then '-'\n" +
                    "when '26' then 'Fe'\n" +
                    "when '27' then 'H2O2'\n" +
                    "when '28' then 'NH4O4'\n" +
                    "when '29' then 'NaCI'\n" +
                    "when '30' then '(C8H8)x(C10H10)'\n" +
                    "end as DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,\n" +
                    "DATA8,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL \n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_5 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_5.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_5.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 3 && formId == 9) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_6_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA0,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_6_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_6_1.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_6_1.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(Double.parseDouble(obj[14].toString()));
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 3 && formId == 10) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_6_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA9,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,\n" +
                    "DATA8,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_6_2 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_6_2.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_6_2.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(Double.parseDouble(obj[14].toString()));
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }

                rowCount++;
            }
        }

        if (reportType == 3 && formId == 11) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_7.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    "when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATAINDEX,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID\n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_7 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_7.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    " MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_7.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 3 && formId == 12) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_8.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATAINDEX,\n" +
                    "case DATA1\n" +
                    " when '1' then 'Эксковатор'\n" +
                    " when '2' then 'Автосамосвал'\n" +
                    " when '3' then 'Утгуурт ачигч' \n" +
                    " when '4' then 'Өрмийн машин' \n" +
                    " when '5' then 'Бульдозер'\n" +
                    "when '6' then 'Компрессор'\n" +
                    "when '7' then 'Өргөх төхөөрөмж'\n" +
                    "when '8' then 'Перфоратор'\n" +
                    "when '9' then 'Далд ачигч'\n" +
                    "when '10' then 'Тэргэнцэр'\n" +
                    "when '11' then 'Дизель станц'\n" +
                    "when '12' then 'Вентилятор'\n" +
                    "end as DATA1,\n" +
                    "DATA2,DATA3,DATA4,\n" +
                    "case DATA5\n" +
                    " when '1' then 'м3'\n" +
                    " when '2' then 'м3'\n" +
                    " when '3' then 'м3' \n" +
                    " when '4' then 'мм' \n" +
                    " when '5' then 'м3'\n" +
                    " when '6' then 'м3/мин'\n" +
                    " when '7' then 'тн'\n" +
                    " when '8' then 'мм'\n" +
                    " when '9' then 'м3'\n" +
                    " when '10' then 'м3'\n" +
                    " when '11' then 'кВт'\n" +
                    " when '12' then 'м3/мин'\n" +
                    "end as DATA5,\n" +
                    "DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_8 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_8.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    " MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR=" + planYr + "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_8.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(obj[28].toString());
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(obj[29].toString());
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(Double.parseDouble(obj[30].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 3 && formId == 13) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_9.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "\n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_9.PLANID\n" +
                    "WHERE\n" +
                    " \n" +
                    "\t\tTYPE=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR="+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=1\n" +
                    "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_9.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }

                rowCount++;
            }

            Sheet sheet1 = workbook.getSheetAt(1);
            Row rowS1 = sheet1.getRow(1);
            Cell cel21 = rowS1.createCell(2);
            cel21.setCellValue(planYr);
            Row rowS12 = sheet1.getRow(2);
            Cell cels2 = rowS12.createCell(2);
            cels2.setCellValue(DateToStr1);
            String queryStr2 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "\n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_9.PLANID\n" +
                    "WHERE\n" +
                    " \n" +
                    "\t\tTYPE=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR="+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=1\n" +
                    "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_9.ID\n" +
                    "\n" +
                    "\n";
            List<Object[]> objectSheets = dao.getNativeSQLResult(queryStr2, "list");
            int rowCountSheets = 7;
            for (Object[] obj : objectSheets) {
                rowS1 = sheet1.createRow(rowCountSheets);
                Cell cell1 = rowS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = rowS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = rowS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = rowS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = rowS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = rowS1.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = rowS1.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }

                rowCountSheets++;
            }

            Sheet sheet2 = workbook.getSheetAt(2);
            Row rowSS1 = sheet2.getRow(1);
            Cell cel3l = rowSS1.createCell(2);
            cel3l.setCellValue(planYr);
            Row rowSS12 = sheet2.getRow(2);
            Cell cel32 = rowSS12.createCell(2);
            cel32.setCellValue(DateToStr1);
            String queryStr3 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "\n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_9.PLANID\n" +
                    "WHERE\n" +
                    " \n" +
                    "\t\tTYPE=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR="+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=1\n" +
                    "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_9.ID\n" +
                    "\n" +
                    "\n" +
                    "\n";
            List<Object[]> objectSheets1 = dao.getNativeSQLResult(queryStr3, "list");
            int rowCountSheets1 = 7;
            for (Object[] obj : objectSheets1) {
                rowSS1 = sheet2.createRow(rowCountSheets1);
                Cell cell1 = rowSS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowSS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowSS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowSS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowSS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowSS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowSS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowSS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowSS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowSS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowSS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowSS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowSS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowSS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = rowSS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowSS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = rowSS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = rowSS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = rowSS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCountSheets1++;
            }
        }
        if (reportType == 3 && formId == 14) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_10.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "TYPE,\n" +
                    "case MRAM.DATA_MIN_PLAN_10.TYPE \n" +
                    " when 1 then 'Техникийн нөхөн сэргээлт – Гадаад овоолго'\n" +
                    " when 2 then 'Техникийн нөхөн сэргээлт – Дотоод овоолго'\n" +
                    " when 3 then 'Биологийн нөхөн сэргээлт –Шимт хөрс' \n" +
                    " when 4 then 'Биологийн нөхөн сэргээлт –Биологи' \n" +
                    " when 5 then 'Дүйцүүлэн хамгаалах нөхөн сэргээлт' \n" +
                    " when 6 then 'Уурхайг тохижуулах, тосгон орчимд хийгдэх нөхөн сэргээлт' \n" +
                    " \n" +
                    "end as Үзүүлэлт,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_10 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_10.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and MRAM.ANNUAL_REGISTRATION.REPORTYEAR  = " + planYr + "  and (DATAINDEX=1)\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_10.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }

                rowCount++;
            }
        }

        if (reportType == 3 && formId == 15) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_11.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA1,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA2,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA3,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA4,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA5,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA6,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA7,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_11 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_11.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " and DATA2<>'Нийт'\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_11.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 3 && formId == 16) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_12.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_12 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_12.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_12.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 3 && formId == 17) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_14.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_14 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_14.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_14.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 3 && formId == 18) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_15.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_15 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_15.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_15.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 3 && formId == 19) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_16.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.DATA_MIN_PLAN_16.DATA1,\n" +
                    "MRAM.DATA_MIN_PLAN_16.DATA2,\n" +
                    "MRAM.DATA_MIN_PLAN_16.DATA3,\n" +
                    "MRAM.DATA_MIN_PLAN_16.DATA4,\n" +
                    "MRAM.DATA_MIN_PLAN_16.DATA5,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_16 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_16.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and MRAM.ANNUAL_REGISTRATION.DIVISIONID=1\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_16.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 3 && formId == 20) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_17.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA1,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA2,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA3,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA4,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA5,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA6,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA7,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA8,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_17 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_17.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_17.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 4 && formId == 1) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP2 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP2.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + "\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP2.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 4 && formId == 2) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_3a.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA20,DATA22,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP3A ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP3A.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "\t     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=1\n" +
                    "\t\t\t and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 \t\t\t \n" +
                    "\t\t\t and (DATA1 in ('5.0','7.0','8.0','11.0','12.0','9.0') or \n" +
                    "\t\t\t      DATA3 in ('г. Дүгнэлтийн дугаар','в. Огноо','д. Огноо') or\n" +
                    "\t\t\t\t\t      \t(DATA3 like '%Тушаалын дугаар%')\n" +
                    "\t\t\t\t\t\t)\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP3A.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCount++;
            }

            Sheet sheet1 = workbook.getSheetAt(1);
            Row rowS1 = sheet1.getRow(1);
            Cell cel21 = rowS1.createCell(2);
            cel21.setCellValue(planYr);
            Row rowS12 = sheet1.getRow(2);
            Cell cels2 = rowS12.createCell(2);
            cels2.setCellValue(DateToStr1);
            String queryStr2 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP3A ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP3A.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "\t\n" +
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=1\n" +
                    "\t\t\t and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 \t\t\t \n" +
                    "\t\t\t and DATA7 is NULL\n" +
                    "\t\t\t and DATA1 is NULL\n" +
                    "\t\t\t and DATA3 is NULL\n" +
                    "\t\t\t and DATA2 is NULL\n" +
                    "\t\t\t and DATA16 not in ('Тоон утга','Сек')\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP3A.ID\n" +
                    "\n" +
                    "\n";
            List<Object[]> objectSheets = dao.getNativeSQLResult(queryStr2, "list");
            int rowCountSheets = 7;
            for (Object[] obj : objectSheets) {
                rowS1 = sheet1.createRow(rowCountSheets);
                Cell cell1 = rowS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = rowS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = rowS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = rowS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = rowS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = rowS1.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = rowS1.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = rowS1.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = rowS1.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = rowS1.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }

                rowCountSheets++;
            }

            Sheet sheet2 = workbook.getSheetAt(2);
            Row rowSS1 = sheet2.getRow(1);
            Cell cel3l = rowSS1.createCell(2);
            cel3l.setCellValue(planYr);
            Row rowSS12 = sheet2.getRow(2);
            Cell cel32 = rowSS12.createCell(2);
            cel32.setCellValue(DateToStr1);
            String queryStr3 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA4,DATA5,DATA7,DATA8,DATA10,DATA11,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA22,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP3A ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP3A.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "\t     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=1\n" +
                    "\t\t\t and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 \t\t\t \n" +
                    "\t\t\t and DATA7 is not NULL\n" +
                    "\t\t\t and DATA1 is NULL\n" +
                    "\t\t\t and DATA3 is NULL\n" +
                    "\t\t\t and DATA2 is NULL\n" +
                    "\t\t\t and DATA16 not in ('Тоон утга','Сек')\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP3A.ID\n" +
                    "\n";
            List<Object[]> objectSheets1 = dao.getNativeSQLResult(queryStr3, "list");
            int rowCountSheets1 = 7;
            for (Object[] obj : objectSheets1) {
                rowSS1 = sheet2.createRow(rowCountSheets1);
                Cell cell1 = rowSS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowSS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowSS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowSS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowSS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowSS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowSS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowSS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowSS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowSS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowSS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowSS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowSS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowSS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = rowSS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowSS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = rowSS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = rowSS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = rowSS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = rowSS1.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = rowSS1.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = rowSS1.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = rowSS1.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = rowSS1.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = rowSS1.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = rowSS1.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = rowSS1.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = rowSS1.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = rowSS1.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }

                rowCountSheets1++;
            }
        }

        if (reportType == 4 && formId == 3) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_3b.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA4,DATA5,DATA7,DATA8,DATA10,\n" +
                    "DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,\n" +
                    "DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,\n" +
                    "DATA31,DATA32,DATA33,DATA34,DATA35,DATA36,DATA37,DATA38,DATA39,DATA40,\n" +
                    "DATA41,DATA42,DATA43,DATA44,DATA45,DATA46,DATA47,DATA48,DATA49,DATA50,DATA51,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP3B ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP3B.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "    MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and   MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP3B.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());

                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());

                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());

                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(obj[26].toString());
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(obj[28].toString());
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(obj[29].toString());
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(obj[30].toString());
                }
                Cell cell32 = row.createCell(31);
                if (obj[31] != null) {
                    cell32.setCellValue(obj[31].toString());
                }
                Cell cell33 = row.createCell(32);
                if (obj[32] != null) {
                    cell33.setCellValue(obj[32].toString());
                }
                Cell cell34 = row.createCell(33);
                if (obj[33] != null) {
                    cell34.setCellValue(obj[33].toString());
                }
                Cell cell35 = row.createCell(34);
                if (obj[34] != null) {
                    cell35.setCellValue(obj[34].toString());
                }
                Cell cell36 = row.createCell(35);
                if (obj[35] != null) {
                    cell36.setCellValue(obj[35].toString());
                }
                Cell cell37 = row.createCell(36);
                if (obj[36] != null) {
                    cell37.setCellValue(obj[36].toString());
                }
                Cell cell38 = row.createCell(37);
                if (obj[37] != null) {
                    cell38.setCellValue(obj[37].toString());
                }
                Cell cell39 = row.createCell(38);
                if (obj[38] != null) {
                    cell39.setCellValue(obj[38].toString());
                }
                Cell cell40 = row.createCell(39);
                if (obj[39] != null) {
                    cell40.setCellValue(obj[39].toString());
                }
                Cell cell41 = row.createCell(40);
                if (obj[40] != null) {
                    cell41.setCellValue(obj[40].toString());
                }
                Cell cell42 = row.createCell(41);
                if (obj[41] != null) {
                    cell42.setCellValue(obj[41].toString());
                }
                Cell cell43 = row.createCell(42);
                if (obj[42] != null) {
                    cell43.setCellValue(obj[42].toString());
                }
                Cell cell44 = row.createCell(43);
                if (obj[43] != null) {
                    cell44.setCellValue(obj[43].toString());
                }
                Cell cell45 = row.createCell(44);
                if (obj[44] != null) {
                    cell45.setCellValue(obj[44].toString());
                }
                Cell cell46 = row.createCell(45);
                if (obj[45] != null) {
                    cell46.setCellValue(obj[45].toString());
                }
                Cell cell47 = row.createCell(46);
                if (obj[46] != null) {
                    cell47.setCellValue(obj[46].toString());
                }
                Cell cell48 = row.createCell(47);
                if (obj[47] != null) {
                    cell48.setCellValue(obj[47].toString());
                }
                Cell cell49 = row.createCell(48);
                if (obj[48] != null) {
                    cell49.setCellValue(obj[48].toString());
                }
                Cell cell50 = row.createCell(49);
                if (obj[49] != null) {
                    cell50.setCellValue(obj[49].toString());
                }
                Cell cell51 = row.createCell(50);
                if (obj[50] != null) {
                    cell51.setCellValue(obj[50].toString());
                }
                Cell cell52 = row.createCell(51);
                if (obj[51] != null) {
                    cell52.setCellValue(obj[51].toString());
                }
                Cell cell53 = row.createCell(52);
                if (obj[52] != null) {
                    cell53.setCellValue(obj[52].toString());
                }
                Cell cell54 = row.createCell(53);
                if (obj[53] != null) {
                    cell54.setCellValue(obj[53].toString());
                }
                Cell cell55 = row.createCell(54);
                if (obj[54] != null) {
                    cell55.setCellValue(obj[54].toString());
                }
                Cell cell56 = row.createCell(55);
                if (obj[55] != null) {
                    cell56.setCellValue(obj[55].toString());
                }
                Cell cell57 = row.createCell(56);
                if (obj[56] != null) {
                    cell57.setCellValue(obj[56].toString());
                }
                Cell cell58 = row.createCell(57);
                if (obj[57] != null) {
                    cell58.setCellValue(obj[57].toString());
                }
                Cell cell59 = row.createCell(58);
                if (obj[58] != null) {
                    cell59.setCellValue(obj[58].toString());
                }
                Cell cell60 = row.createCell(59);
                if (obj[59] != null) {
                    cell60.setCellValue(obj[59].toString());
                }
                Cell cell61 = row.createCell(60);
                if (obj[60] != null) {
                    cell61.setCellValue(obj[60].toString());
                }
                Cell cell62 = row.createCell(61);
                if (obj[61] != null) {
                    cell62.setCellValue(Double.parseDouble(obj[61].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 4 && formId == 4) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_4_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA9,DATA10,\n" +
                    "DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,\n" +
                    "DATA21,DATA22,DATA23,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP4_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP4_1.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP4_1.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(obj[28].toString());
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(Double.parseDouble(obj[30].toString()));
                }
                Cell cell32 = row.createCell(31);
                if (obj[31] != null) {
                    cell32.setCellValue(obj[31].toString());
                }
                Cell cell33 = row.createCell(32);
                if (obj[32] != null) {
                    cell33.setCellValue(Double.parseDouble(obj[32].toString()));
                }
                Cell cell34 = row.createCell(33);
                if (obj[33] != null) {
                    cell34.setCellValue(Double.parseDouble(obj[33].toString()));
                }
                Cell cell35 = row.createCell(34);
                if (obj[34] != null) {
                    cell35.setCellValue(obj[34].toString());
                }
                Cell cell36 = row.createCell(35);
                if (obj[35] != null) {
                    cell36.setCellValue(Double.parseDouble(obj[35].toString()));
                }

                rowCount++;
            }
        }

        if (reportType == 4 && formId == 5) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_4_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,\n" +
                    "DATA11,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP4_2 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP4_2.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP4_2.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 4 && formId == 6)
         {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_5.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP5 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP5.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4  and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP5.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }


                rowCount++;
            }
        }
        if (reportType == 4 && formId == 7) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_6_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL,\n" +
                    "MRAM.ANNUAL_REGISTRATION.SOURCE_E AS SOURCE_E,\n" +
                    "MRAM.ANNUAL_REGISTRATION.SOURCE_W AS SOURCE_W\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP6_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP6_1.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP6_1.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }

                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }

                rowCount++;
            }
        }
        if (reportType == 4 && formId == 8) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_6_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL,\n" +
                    "MRAM.ANNUAL_REGISTRATION.SOURCE_W AS SOURCE_W\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP6_2 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP6_2.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP6_2.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }

                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(obj[26].toString());
                }
                rowCount++;
            }
        }
        if (reportType == 4 && formId == 9) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_7.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP7 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP7.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP7.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 4 && formId == 10) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_8.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP8_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP8_1.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP8_1.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(obj[28].toString());
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(obj[29].toString());
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(Double.parseDouble(obj[30].toString()));
                }
                Cell cell32 = row.createCell(31);
                if (obj[31] != null) {
                    cell32.setCellValue(obj[31].toString());
                }
                Cell cell33 = row.createCell(32);
                if (obj[32] != null) {
                    cell33.setCellValue(obj[32].toString());
                }
                Cell cell34 = row.createCell(33);
                if (obj[33] != null) {
                    cell34.setCellValue(obj[33].toString());
                }
                Cell cell35 = row.createCell(34);
                if (obj[34] != null) {
                    cell35.setCellValue(obj[34].toString());
                }
                Cell cell36 = row.createCell(35);
                if (obj[35] != null) {
                    cell36.setCellValue(Double.parseDouble(obj[35].toString()));
                }


                rowCount++;
            }
        }
        if (reportType == 4 && formId == 11) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_9.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "\n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP9.PLANID\n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and  MRAM.ANNUAL_REGISTRATION.REPSTATUSID=1 \n" +
                    "    and TYPE=1 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR=" + planYr + "\n" +
                    "\t  and (DATA1 is not  NULL)\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP9.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }


                rowCount++;
            }

            Sheet sheet1 = workbook.getSheetAt(1);
            Row rowS1 = sheet1.getRow(1);
            Cell cel21 = rowS1.createCell(2);
            cel21.setCellValue(planYr);
            Row rowS12 = sheet1.getRow(2);
            Cell cels2 = rowS12.createCell(2);
            cels2.setCellValue(DateToStr1);
            String queryStr2 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "(select LUT_BLAST.\"Negj\" from LUT_BLAST where LUT_BLAST.\"Pro_name\"= MRAM.DATA_EXCEL_MINREP9.DATA2) as DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP9.PLANID\n" +
                    " \n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and MRAM.DATA_EXCEL_MINREP9.TYPE = 2 and MRAM.ANNUAL_REGISTRATION.REPORTYEAR=" + planYr + "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP9.ID\n" +
                    "\n" +
                    "\n";
            List<Object[]> objectSheets = dao.getNativeSQLResult(queryStr2, "list");
            int rowCountSheets = 7;
            for (Object[] obj : objectSheets) {
                rowS1 = sheet1.createRow(rowCountSheets);
                Cell cell1 = rowS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = rowS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = rowS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = rowS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = rowS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = rowS1.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = rowS1.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }

                rowCountSheets++;
            }

            Sheet sheet2 = workbook.getSheetAt(2);
            Row rowSS1 = sheet2.getRow(1);
            Cell cel3l = rowSS1.createCell(2);
            cel3l.setCellValue(planYr);
            Row rowSS12 = sheet2.getRow(2);
            Cell cel32 = rowSS12.createCell(2);
            cel32.setCellValue(DateToStr1);
            String queryStr3 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA13,\n" +
                    "DATA12,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP9.PLANID\n" +
                    " \n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and MRAM.DATA_EXCEL_MINREP9.TYPE = 3 and MRAM.ANNUAL_REGISTRATION.REPORTYEAR=" + planYr + "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP9.ID\n" +
                    "\n" +
                    "\n" +
                    "\n";
            List<Object[]> objectSheets1 = dao.getNativeSQLResult(queryStr3, "list");
            int rowCountSheets1 = 7;
            for (Object[] obj : objectSheets1) {
                rowSS1 = sheet2.createRow(rowCountSheets1);
                Cell cell1 = rowSS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowSS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowSS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowSS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowSS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowSS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowSS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowSS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowSS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowSS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowSS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowSS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowSS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowSS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = rowSS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowSS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = rowSS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = rowSS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = rowSS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCountSheets1++;
            }
        }

        if (reportType == 4 && formId == 12) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/miningStat10.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
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

            for (String v : vals) {
                sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA7 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA10 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA11 FROM DATA_EXCEL_MINREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1)";
                if (!v.equalsIgnoreCase(vals[vals.length - 1])) {
                    sqlQuery = sqlQuery + ",";
                }
            }
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setWrapText(true);


            sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + planYr + " AND A .XTYPE != 0 AND A .DIVISIONID = 1 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
            List<Object[]> objects = dao.getNativeSQLResult(sqlQuery, "list");
            int rowCount = 7;

            if (objects != null && objects.size() > 0) {
                for (int iterator = 0; iterator < objects.size(); iterator++) {
                    Object[] o = objects.get(iterator);
                    setCellData(sheet, iterator + 6, 0, iterator + 1, style, "number");
                    for (int cellIterator = 1; cellIterator < o.length; cellIterator++) {
                        setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, (cellIterator > 10) ? "float" : "string");
                    }
                }
            }

        }
        if (reportType == 4 && formId == 13) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/miningStat11.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
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

            for (String v : vals) {
                sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA6 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA9 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA10 FROM DATA_EXCEL_MINREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1)";
                if (!v.equalsIgnoreCase(vals[vals.length - 1])) {
                    sqlQuery = sqlQuery + ",";
                }
            }

            sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + planYr + " AND A .XTYPE != 0 AND A .DIVISIONID = 1 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setWrapText(true);
            if (resultObj != null && resultObj.size() > 0) {
                for (int iterator = 0; iterator < resultObj.size(); iterator++) {
                    Object[] o = resultObj.get(iterator);
                    setCellData(sheet, iterator + 6, 0, iterator + 1, style, "number");
                    for (int cellIterator = 1; cellIterator < o.length; cellIterator++) {
                        if (o[cellIterator] instanceof String) {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "string");
                        } else {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "float");
                        }
                    }
                }
            }

        }
        if (reportType == 4 && formId == 14) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/miningStat12.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String sqlQuery = "SELECT A.ID, A.LPNAME,A.LP_REG,A.LICENSEXB," +
                    "(SELECT LUT_APPSTATUS.STATUSNAMEMON FROM LUT_APPSTATUS WHERE LUT_APPSTATUS.STATUSID = A.REPSTATUSID AND ROWNUM=1), " +
                    "(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1)," +
                    "(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1)," +
                    "(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1)," +
                    "(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1)," +
                    "(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1)," +
                    "(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1)," +
                    "(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),";

            for (int iterator = 1; iterator <= 57; iterator++) {
                sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA6 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA7 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_MINREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1)";
                if (iterator != 57) {
                    sqlQuery = sqlQuery + ",";
                }
            }

            sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + planYr + " AND A .XTYPE != 0 AND A .DIVISIONID = 1 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setWrapText(true);
            if (resultObj != null && resultObj.size() > 0) {
                for (int iterator = 0; iterator < resultObj.size(); iterator++) {
                    Object[] o = resultObj.get(iterator);
                    setCellData(sheet, iterator + 6, 0, iterator + 1, style, "number");
                    for (int cellIterator = 1; cellIterator < o.length; cellIterator++) {
                        if (o[cellIterator] instanceof String) {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "string");
                        } else {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "float");
                        }
                    }
                }
            }

        }
        if (reportType == 4 && formId == 15) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_13.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP13 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP13.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4  and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP13.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 4 && formId == 16) {
            FileInputStream fis = null;
            File files = new File(appPath + "assets/reporttemplate/miningRep14.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            //Row row1 = sheet.getRow(2);
            Cell cel2 = row.createCell(7);
            cel2.setCellValue(DateToStr1);
            String sqlQuery = "SELECT A .LPNAME, A .LP_REG, A .LICENSEXB,(SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M .MINERALNAMEMON FROM LUT_MINERALS M WHERE M .MINERALID = A .MINID AND ROWNUM = 1),(SELECT D .DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D .DEPOSITID = A .DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),";

            for(int i=1;i<=24;i++){
                sqlQuery = sqlQuery + "(SELECT D.DATA4 FROM DATA_EXCEL_MINREP14 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT D.DATA5 FROM DATA_EXCEL_MINREP14 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
            }

            sqlQuery = sqlQuery.substring(0,sqlQuery.length()-1);

            sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID IN (1, 2, 7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + planYr + " AND A .DIVISIONID = 1 ORDER BY A .LPNAME ASC";

            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setWrapText(true);
            if (resultObj != null && resultObj.size() > 0) {
                for (int iterator = 0; iterator < resultObj.size(); iterator++) {
                    Object[] o = resultObj.get(iterator);
                    setCellData(sheet, iterator + 6, 0, iterator + 1, style, "number");
                    for (int cellIterator = 1; cellIterator < o.length; cellIterator++) {
                        if (o[cellIterator] instanceof String) {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "string");
                        } else {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "float");
                        }
                    }
                }
            }
        }
        if (reportType == 4 && formId == 17) {
            FileInputStream fis = null;
            File files = new File(appPath + "assets/reporttemplate/miningRep15.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            //Row row1 = sheet.getRow(2);
            Cell cel2 = row.createCell(7);
            cel2.setCellValue(DateToStr1);
            String sqlQuery = "SELECT A .LPNAME, A .LP_REG, A .LICENSEXB,(SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M .MINERALNAMEMON FROM LUT_MINERALS M WHERE M .MINERALID = A .MINID AND ROWNUM = 1),(SELECT D .DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D .DEPOSITID = A .DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),";

            for(int i=1;i<=26;i++){
                sqlQuery = sqlQuery + "(SELECT D.DATA4 FROM DATA_EXCEL_MINREP15 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT D.DATA5 FROM DATA_EXCEL_MINREP15 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
            }

            sqlQuery = sqlQuery.substring(0,sqlQuery.length()-1);

            sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID IN (1, 2, 7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + planYr + " AND A .DIVISIONID = 1 ORDER BY A .LPNAME ASC";

            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setWrapText(true);
            if (resultObj != null && resultObj.size() > 0) {
                for (int iterator = 0; iterator < resultObj.size(); iterator++) {
                    Object[] o = resultObj.get(iterator);
                    setCellData(sheet, iterator + 6, 0, iterator + 1, style, "number");
                    for (int cellIterator = 1; cellIterator < o.length; cellIterator++) {
                        if (o[cellIterator] instanceof String) {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "string");
                        } else {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "float");
                        }
                    }
                }
            }
        }

        if (reportType == 4 && formId == 18) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_16.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP16 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP16.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP16.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 4 && formId == 19) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_17.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "ORDERNUMBER,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA4,DATA6,DATA7,DATA8,DATA9,DATA10,\n" +
                    "DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP17 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP17.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP17.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(obj[26].toString());
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(obj[30].toString());
                }
                Cell cell32 = row.createCell(31);
                if (obj[31] != null) {
                    cell32.setCellValue(Double.parseDouble(obj[31].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 4 && formId == 20) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_18.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP18 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP18.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP18.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 4 && formId == 21) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_19.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3, \n" +
                    "DATA4, \n" +
                    "DATA5, \n" +
                    "DATA6, \n" +
                    "DATA7, \n" +
                    "DATA8, \n" +
                    "DATA9, \n" +
                    "DATA10, \n" +
                    "DATA11, \n" +
                    "DATA12, \n" +
                    "DATA13,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_MINREP19 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_MINREP19.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR=" + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_MINREP19.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }

                rowCount++;
            }
        }


        // COAL Division

        // end of Coal division


        if (reportType == 7 && formId == 1) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Geo_Plan_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOPLAN1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOPLAN1.PLANID\n" +
                    "\tWHERE\n" +
                    "    MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOPLAN1.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = row.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(obj[12].toString());
                }
                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 8 && formId == 1) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Geo_Report_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,\n" +
                    "DATA8,\n" +
                    "DATA9,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP1.PLANID\n" +
                    "\tWHERE\n" +
                    "    MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP1.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = row.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(obj[12].toString());
                }
                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 8 && formId == 2) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Geo_Report_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,\n" +
                    "DATA8,\n" +
                    "DATA9,\n" +
                    "DATA10,\n" +
                    "DATA11,\n" +
                    "DATA12,\n" +
                    "DATA13,\n" +
                    "DATA14,\n" +
                    "DATA15,\n" +
                    "DATA16,\n" +
                    "DATA17,\n" +
                    "DATA18,\n" +
                    "DATA19,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP2 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP2.PLANID\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP2.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = row.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(Double.parseDouble(obj[12].toString()));
                }
                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(Double.parseDouble(obj[30].toString()));
                }
                Cell cell32 = row.createCell(31);
                if (obj[31] != null) {
                    cell32.setCellValue(Double.parseDouble(obj[31].toString()));
                }


                rowCount++;
            }
        }
        if (reportType == 8 && formId == 3) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Geo_Report_3.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "case TYPE \n" +
                    " when 'TSOONOG' then 'Цооног'\n" +
                    " when 'SHURF' then 'Шурф'\n" +
                    " when 'SUVAG' then 'Суваг' \n" +
                    " \n" +
                    "end as TYPE,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,\n" +
                    "DATA8,\n" +
                    "DATA9,\n" +
                    "DATA10,\n" +
                    "DATA12,\n" +
                    "DATA13,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP3 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP3.PLANID\n" +
                    "\tWHERE\n" +
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and TYPE like 'TSOONOG'\n" +
                    " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP3.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = row.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(obj[12].toString());
                }
                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }

                rowCount++;
            }

            Sheet sheet1 = workbook.getSheetAt(1);
            Row rowS1 = sheet1.getRow(1);
            Cell cel21 = rowS1.createCell(2);
            cel21.setCellValue(planYr);
            Row rowS12 = sheet1.getRow(2);
            Cell cels2 = rowS12.createCell(2);
            cels2.setCellValue(DateToStr1);
            String queryStr2 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "case TYPE \n" +
                    " when 'TSOONOG' then 'Цооног'\n" +
                    " when 'SHURF' then 'Шурф'\n" +
                    " when 'SUVAG' then 'Суваг' \n" +
                    " \n" +
                    "end as TYPE,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,\n" +
                    "DATA8,\n" +
                    "DATA9,\n" +
                    "DATA10,DATA11,\n" +
                    "DATA12,\n" +
                    "DATA13,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP3 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP3.PLANID\n" +
                    "\tWHERE\n" +
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\t\t\t and MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 \n" +
                    "\t\t\t and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4\n" +
                    "\t\t\t and TYPE like 'SUVAG'\n" +
                    "\t\t\t \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP3.ID" +
                    "\n" +
                    "\n";
            List<Object[]> objectSheets = dao.getNativeSQLResult(queryStr2, "list");
            int rowCountSheets = 7;
            for (Object[] obj : objectSheets) {
                rowS1 = sheet1.createRow(rowCountSheets);
                Cell cell1 = rowS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowS1.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }

                Cell cell6 = rowS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowS1.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = rowS1.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = rowS1.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(obj[12].toString());
                }


                Cell cell114 = rowS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = rowS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = rowS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = rowS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = rowS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = rowS1.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = rowS1.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = rowS1.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = rowS1.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = rowS1.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = rowS1.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = rowS1.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = rowS1.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }

                rowCountSheets++;
            }

            Sheet sheet2 = workbook.getSheetAt(2);
            Row rowSS1 = sheet2.getRow(1);
            Cell cel3l = rowSS1.createCell(2);
            cel3l.setCellValue(planYr);
            Row rowSS12 = sheet2.getRow(2);
            Cell cel32 = rowSS12.createCell(2);
            cel32.setCellValue(DateToStr1);
            String queryStr3 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "case TYPE \n" +
                    " when 'TSOONOG' then 'Цооног'\n" +
                    " when 'SHURF' then 'Шурф'\n" +
                    " when 'SUVAG' then 'Суваг' \n" +
                    " \n" +
                    "end as TYPE,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,\n" +
                    "DATA8,\n" +
                    "DATA9,\n" +
                    "DATA10,\n" +
                    "DATA12,\n" +
                    "DATA13,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP3 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP3.PLANID\n" +
                    "\tWHERE\n" +
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\t\t\t and MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 \n" +
                    "\t\t\t and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 \n" +
                    "\t\t\t and TYPE like 'SHURF'\n" +
                    "\t\t\t \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP3.ID" +
                    "\n" +
                    "\n" +
                    "\n";
            List<Object[]> objectSheets1 = dao.getNativeSQLResult(queryStr3, "list");
            int rowCountSheets1 = 7;
            for (Object[] obj : objectSheets1) {
                rowSS1 = sheet2.createRow(rowCountSheets1);
                Cell cell1 = rowSS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowSS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowSS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowSS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowSS1.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = rowSS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowSS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowSS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowSS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowSS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowSS1.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = rowSS1.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = rowSS1.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(obj[12].toString());
                }

                Cell cell114 = rowSS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = rowSS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowSS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = rowSS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = rowSS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = rowSS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = rowSS1.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = rowSS1.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = rowSS1.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = rowSS1.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = rowSS1.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = rowSS1.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = rowSS1.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                rowCountSheets1++;
            }

        }



        if (reportType == 8 && formId == 4) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Geo_Report_4.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP4 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP4.PLANID\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP4.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = row.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(Double.parseDouble(obj[12].toString()));
                }
                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 8 && formId == 5) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Geo_Report_5.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP5 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP5.PLANID\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP5.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = row.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(Double.parseDouble(obj[12].toString()));
                }
                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 8 && formId == 6) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Geo_Report_6.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP6 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP6.PLANID\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP6.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = row.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(Double.parseDouble(obj[12].toString()));
                }
                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(Double.parseDouble(obj[14].toString()));
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }

                rowCount++;
            }
        }

        if (reportType == 8 && formId == 7) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Geo_Report_7.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,\n" +
                    "DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP7 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP7.PLANID\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP7.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = row.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(Double.parseDouble(obj[12].toString()));
                }
                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(Double.parseDouble(obj[14].toString()));
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 8 && formId == 8) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Geo_Report_8.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP8 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP8.PLANID\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP8.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = row.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(Double.parseDouble(obj[12].toString()));
                }
                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(Double.parseDouble(obj[14].toString()));
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 8 && formId == 9) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Geo_Report_9.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP9.PLANID\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP9.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = row.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(Double.parseDouble(obj[12].toString()));
                }
                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                rowCount++;
            }
        }

        //end reportType == 8 && formId == 9 bsn
        if (reportType == 8 && formId == 10) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Geo_Report_10.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP10 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP10.PLANID\n" +
                    "\tWHERE\n" +
                    "    MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and   MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP10.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                if (obj[4] != null) {
                    cell5.setCellValue(obj[4].toString());
                }
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(obj[11].toString());

                Cell cell13 = row.createCell(12);
                if (obj[12] != null) {
                    cell13.setCellValue(Double.parseDouble(obj[12].toString()));
                }
                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(Double.parseDouble(obj[14].toString()));
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCount++;
            }
        }

        if (reportType == 9) {
            System.out.println("ITs WORKING");
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Ognoo_Zagvar.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MIN_GROUP.GROUPNAME AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ID AS id,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MIN_GROUP on MRAM.ANNUAL_REGISTRATION.GROUPID=MRAM.LUT_MIN_GROUP.GROUPID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_GEOREP10 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_GEOREP10.PLANID\n" +
                    //"WHERE    MRAM.ANNUAL_REGISTRATION.DIVISIONID=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR = 2019\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_GEOREP10.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 6;
            for (Object[] obj : objects) {
                for (int i=0; i<2; i++) {
                    row = sheet.createRow(rowCount);
                    Cell cell1 = row.createCell(0);
                    cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                    Cell cell2 = row.createCell(1);
                    cell2.setCellValue(obj[1].toString());
                    Cell cell3 = row.createCell(2);
                    cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                    Cell cell4 = row.createCell(3);
                    cell4.setCellValue(obj[3].toString());
                    Cell cell5 = row.createCell(4);
                    if (obj[4] != null) {
                        cell5.setCellValue(obj[4].toString());
                    }
                    Cell cell6 = row.createCell(5);
                    cell6.setCellValue(obj[5].toString());
                    Cell cell7 = row.createCell(6);
                    cell7.setCellValue(obj[6].toString());
                    Cell cell8 = row.createCell(7);
                    cell8.setCellValue(obj[7].toString());
                    Cell cell9 = row.createCell(8);
                    cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                    Cell cell10 = row.createCell(9);
                    cell10.setCellValue(obj[9].toString());
                    Cell cell11 = row.createCell(10);
                    cell11.setCellValue(Double.parseDouble(obj[10].toString()));
                    Cell cell12 = row.createCell(11);
                    cell12.setCellValue(obj[11].toString());

                    String isGov = (i==0)?"1":"0";
                    String list2 = "SELECT * FROM MRAM.LNK_COMMENT_MAIN WHERE ISGOV = " + isGov + " AND PLANID = " + obj[0].toString();
                    List<Object[]> commentMains = dao.getNativeSQLResult(list2, "list");
                    int rowNo = 17;
                    for (Object[] com : commentMains) {
                        Cell cellNo = row.createCell(rowNo);
                        if (com[6] != null) {
                            String createdDate = com[6].toString();
                            cellNo.setCellValue(createdDate.substring(0, createdDate.length()-2));
                        }
                        rowNo++;
                    }

                    rowCount++;
                }
            }
        }

        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }


    // COAL division Begin

    public static ByteArrayInputStream formsToExcel(int reportType, int formId, int planYr, String formName, String appPath, UserDao dao) throws IOException
    {

        Workbook workbook = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Date curDate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String DateToStr1 = format1.format(curDate);

        if (reportType ==5  && formId == 1) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Coal_Plan_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATAINDEX,DATA1,DATA2,DATA3,DATA4,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_COAL_FORM_1_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_COAL_FORM_1_1.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and   MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+"  and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_COAL_FORM_1_1.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
              rowCount++;
            }
        }

        if (reportType == 5 && formId == 2) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Coal_Plan_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATAINDEX,DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,\n" +
                    "DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,\n" +
                    "DATA14,DATA15,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_COAL_FORM_2 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_COAL_FORM_2.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "    MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+"  and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_COAL_FORM_2.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }


                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }

                rowCount++;
            }
        }

        if (reportType == 5 && formId == 3) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Coal_Plan_3.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,\n" +
                    "DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,\n" +
                    "DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_COAL_FORM_3 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_COAL_FORM_3.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "    MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+"  and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_COAL_FORM_3.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }
                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(Double.parseDouble(obj[30].toString()));
                }
                Cell cell32 = row.createCell(31);
                if (obj[31] != null) {
                    cell32.setCellValue(Double.parseDouble(obj[31].toString()));
                }
                Cell cell33 = row.createCell(32);
                if (obj[32] != null) {
                    cell33.setCellValue(Double.parseDouble(obj[32].toString()));
                }
              rowCount++;
            }
        }
        if (reportType == 5 && formId == 4) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Coal_Plan_4.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATAINDEX,DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,\n" +
                    "DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_COAL_FORM_4 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_COAL_FORM_4.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "    MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+"  and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_COAL_FORM_4.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }

                rowCount++;
            }
        }

        if (reportType == 5 && formId == 5) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_5.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATAINDEX,\n" +
                    "case DATA1\n" +
                    " when '1' then 'Идэвхжүүлсэн нүүрс'\n" +
                    " when '2' then 'Калцийн гипохлорид'\n" +
                    " when '3' then 'Идэвхжүүлсэн шохой' \n" +
                    " when '4' then 'Шохой' \n" +
                    " when '5' then 'Флокулянт'\n" +
                    "when '6' then 'Зэсийн сульфат (Цианидыг задлах урвалд католизатор)'\n" +
                    "when '7' then 'Төмрийн сульфат'\n" +
                    "when '8' then 'Натрийн метабисульфит (Цианидыг цианат болгон хувиргахад)'\n" +
                    "when '9' then 'Хүхрийн хүчил'\n" +
                    "when '10' then 'Давсны хүчил'\n" +
                    "when '11' then 'Мөнгөн ус'\n" +
                    "when '12' then 'Азотын хүчил'\n" +
                    "when '13' then 'Бура'\n" +
                    "when '14' then 'Натрийн карбонат'\n" +
                    "when '15' then 'Техникийн сода'\n" +
                    "when '16' then 'Метабисульфит натри'\n" +
                    "when '17' then 'Натрийн сульфит'\n" +
                    "when '18' then 'Натрийн цианид'\n" +
                    "when '19' then 'Натрийн шүлт'\n" +
                    "when '20' then 'Хартугалганы исэл'\n" +
                    "when '21' then 'Антисклант'\n" +
                    "when '22' then 'Дизель (kL)'\n" +
                    "when '23' then 'Флюс'\n" +
                    "when '24' then 'Хаг эсэргүүцэгч'\n" +
                    "when '25' then 'Цемент'\n" +
                    "when '26' then 'Ган бөмбөлөг'\n" +
                    "when '27' then 'Устөрөгчийн хэт исэл'\n" +
                    "when '28' then 'Аммонийн гидроксид' \n" +
                    "when '29' then 'Натрийн хлорид'\n" +
                    "when '30' then 'Пюролит PFA600'\n" +
                    "end as DATA1,\n" +
                    "\n" +
                    "case DATA2\n" +
                    " when '1' then 'C'\n" +
                    " when '2' then 'Ca(OCI)2'\n" +
                    " when '3' then 'Ca(OH)2' \n" +
                    " when '4' then 'CaO' \n" +
                    " when '5' then 'CH2CHCONH2'\n" +
                    "when '6' then 'Cu2CO4'\n" +
                    "when '7' then 'Fe2(SO4)3'\n" +
                    "when '8' then 'H2O5S 2Na'\n" +
                    "when '9' then 'H2SO4'\n" +
                    "when '10' then 'HCI'\n" +
                    "when '11' then 'Hg'\n" +
                    "when '12' then 'HNO3'\n" +
                    "when '13' then 'Na2B4O7'\n" +
                    "when '14' then 'Na2CO3'\n" +
                    "when '15' then 'Na2CO3'\n" +
                    "when '16' then 'Na2S2O5'\n" +
                    "when '17' then 'Na2CO3'\n" +
                    "when '18' then 'NaCN'\n" +
                    "when '19' then 'NaOH'\n" +
                    "when '20' then 'PbO'\n" +
                    "when '21' then '-'\n" +
                    "when '22' then '-'\n" +
                    "when '23' then '-'\n" +
                    "when '24' then '-'\n" +
                    "when '25' then '-'\n" +
                    "when '26' then 'Fe'\n" +
                    "when '27' then 'H2O2'\n" +
                    "when '28' then 'NH4O4'\n" +
                    "when '29' then 'NaCI'\n" +
                    "when '30' then '(C8H8)x(C10H10)'\n" +
                    "end as DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,\n" +
                    "DATA8,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL \n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_5 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_5.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_5.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 5 && formId == 6) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_6_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA0,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_6_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_6_1.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_6_1.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(Double.parseDouble(obj[14].toString()));
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 5 && formId == 7) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_6_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA9,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,\n" +
                    "DATA8,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_6_2 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_6_2.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and  MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_6_2.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(Double.parseDouble(obj[14].toString()));
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }

                rowCount++;
            }
        }

        if (reportType == 5 && formId == 8) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_7.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    "when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATAINDEX,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID\n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_7 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_7.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    " MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_7.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                rowCount++;
            }
        }

        if (reportType == 5 && formId == 9) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_8.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATAINDEX,\n" +
                    "case DATA1\n" +
                    " when '1' then 'Эксковатор'\n" +
                    " when '2' then 'Автосамосвал'\n" +
                    " when '3' then 'Утгуурт ачигч' \n" +
                    " when '4' then 'Өрмийн машин' \n" +
                    " when '5' then 'Бульдозер'\n" +
                    "when '6' then 'Компрессор'\n" +
                    "when '7' then 'Өргөх төхөөрөмж'\n" +
                    "when '8' then 'Перфоратор'\n" +
                    "when '9' then 'Далд ачигч'\n" +
                    "when '10' then 'Тэргэнцэр'\n" +
                    "when '11' then 'Дизель станц'\n" +
                    "when '12' then 'Вентилятор'\n" +
                    "end as DATA1,\n" +
                    "DATA2,DATA3,DATA4,\n" +
                    "case DATA5\n" +
                    " when '1' then 'м3'\n" +
                    " when '2' then 'м3'\n" +
                    " when '3' then 'м3' \n" +
                    " when '4' then 'мм' \n" +
                    " when '5' then 'м3'\n" +
                    " when '6' then 'м3/мин'\n" +
                    " when '7' then 'тн'\n" +
                    " when '8' then 'мм'\n" +
                    " when '9' then 'м3'\n" +
                    " when '10' then 'м3'\n" +
                    " when '11' then 'кВт'\n" +
                    " when '12' then 'м3/мин'\n" +
                    "end as DATA5,\n" +
                    "DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_8 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_8.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    " MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR=" + planYr + "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_8.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(obj[28].toString());
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(obj[29].toString());
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(Double.parseDouble(obj[30].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 5 && formId == 10) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_9.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "\n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_9.PLANID\n" +
                    "WHERE\n" +
                    " \n" +
                    "\t\tTYPE=1 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR="+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_9.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }

                rowCount++;
            }

            Sheet sheet1 = workbook.getSheetAt(1);
            Row rowS1 = sheet1.getRow(1);
            Cell cel21 = rowS1.createCell(2);
            cel21.setCellValue(planYr);
            Row rowS12 = sheet1.getRow(2);
            Cell cels2 = rowS12.createCell(2);
            cels2.setCellValue(DateToStr1);
            String queryStr2 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "\n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_9.PLANID\n" +
                    "WHERE\n" +
                    " \n" +
                    "\t\tTYPE=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR="+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_9.ID\n" +
                    "\n" +
                    "\n";
            List<Object[]> objectSheets = dao.getNativeSQLResult(queryStr2, "list");
            int rowCountSheets = 7;
            for (Object[] obj : objectSheets) {
                rowS1 = sheet1.createRow(rowCountSheets);
                Cell cell1 = rowS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = rowS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = rowS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = rowS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = rowS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = rowS1.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = rowS1.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }

                rowCountSheets++;
            }

            Sheet sheet2 = workbook.getSheetAt(2);
            Row rowSS1 = sheet2.getRow(1);
            Cell cel3l = rowSS1.createCell(2);
            cel3l.setCellValue(planYr);
            Row rowSS12 = sheet2.getRow(2);
            Cell cel32 = rowSS12.createCell(2);
            cel32.setCellValue(DateToStr1);
            String queryStr3 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "\n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_9.PLANID\n" +
                    "WHERE\n" +
                    " \n" +
                    "\t\tTYPE=3 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR="+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_9.ID\n" +
                    "\n" +
                    "\n" +
                    "\n";
            List<Object[]> objectSheets1 = dao.getNativeSQLResult(queryStr3, "list");
            int rowCountSheets1 = 7;
            for (Object[] obj : objectSheets1) {
                rowSS1 = sheet2.createRow(rowCountSheets1);
                Cell cell1 = rowSS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowSS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowSS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowSS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowSS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowSS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowSS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowSS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowSS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowSS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowSS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowSS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowSS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowSS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = rowSS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowSS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = rowSS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = rowSS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = rowSS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCountSheets1++;
            }
        }
        if (reportType == 5 && formId == 11) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_10.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "TYPE,\n" +
                    "case MRAM.DATA_MIN_PLAN_10.TYPE \n" +
                    " when 1 then 'Техникийн нөхөн сэргээлт – Гадаад овоолго'\n" +
                    " when 2 then 'Техникийн нөхөн сэргээлт – Дотоод овоолго'\n" +
                    " when 3 then 'Биологийн нөхөн сэргээлт –Шимт хөрс' \n" +
                    " when 4 then 'Биологийн нөхөн сэргээлт –Биологи' \n" +
                    " when 5 then 'Дүйцүүлэн хамгаалах нөхөн сэргээлт' \n" +
                    " when 6 then 'Уурхайг тохижуулах, тосгон орчимд хийгдэх нөхөн сэргээлт' \n" +
                    " \n" +
                    "end as Үзүүлэлт,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_10 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_10.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and MRAM.ANNUAL_REGISTRATION.REPORTYEAR  = " + planYr + "  and (DATAINDEX=1)\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_10.\"ID\"";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }

                rowCount++;
            }
        }

        if (reportType == 5 && formId == 12) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_11.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA1,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA2,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA3,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA4,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA5,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA6,\n" +
                    "MRAM.DATA_MIN_PLAN_11.DATA7,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_11 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_11.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and  MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " and DATA2<>'Нийт'\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_11.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 5 && formId == 13) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_12.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_12 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_12.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_12.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 5 && formId == 14) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_14.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_14 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_14.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_14.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 5 && formId == 15) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_15.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_15 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_15.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_15.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());
                Cell cell114 = row.createCell(13);
                cell114.setCellValue(Double.parseDouble(obj[13].toString()));

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 5 && formId == 16) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_16.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.DATA_MIN_PLAN_16.DATA1,\n" +
                    "MRAM.DATA_MIN_PLAN_16.DATA2,\n" +
                    "MRAM.DATA_MIN_PLAN_16.DATA3,\n" +
                    "MRAM.DATA_MIN_PLAN_16.DATA4,\n" +
                    "MRAM.DATA_MIN_PLAN_16.DATA5,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_16 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_16.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "    MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_16.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
              Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 5 && formId == 17) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Plan_17.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA1,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA2,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA3,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA4,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA5,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA6,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA7,\n" +
                    "MRAM.DATA_MIN_PLAN_17.DATA8,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_17 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_17.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and  MRAM.ANNUAL_REGISTRATION.REPORTTYPE=3 and   MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_17.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 6 && formId == 1) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Coal_Report_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP2 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP2.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "    MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP2.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
               rowCount++;
            }

            Sheet sheet1 = workbook.getSheetAt(1);
            Row rowS1 = sheet1.getRow(1);
            Cell cel2l = rowS1.createCell(2);
            cel2l.setCellValue(planYr);
            Row rowS2 = sheet1.getRow(2);
            Cell cels2 = rowS2.createCell(2);
            cels2.setCellValue(DateToStr1);
            String queryStr2 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP2_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP2_1.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "    MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and   MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP2_1.ID";
            List<Object[]> objectSheets = dao.getNativeSQLResult(queryStr2, "list");
            int rowCountSheets = 7;
            for (Object[] obj : objectSheets) {
                rowS1 = sheet1.createRow(rowCountSheets);
                Cell cell1 = rowS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = rowS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = rowS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = rowS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = rowS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }

                rowCountSheets++;
            }

        }
        if (reportType == 6 && formId == 2) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Coal_Report_3.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "ORDERNUMBER,DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP3 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP3.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and DATA1 not like 'ЖИЧ:  Тухайн оны нөөцийг ашиглалтын тусгай зөвшөөрөл бүрээр гаргаж ирүүлнэ.'\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP3.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(Double.parseDouble(obj[15].toString()));
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));

                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));

                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));

                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(Double.parseDouble(obj[30].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 6 && formId == 3) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Coal_Report_4.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "ORDERNUMBER,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP4 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP4.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP4.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(Double.parseDouble(obj[30].toString()));
                }
                Cell cell32 = row.createCell(31);
                if (obj[31] != null) {
                    cell32.setCellValue(Double.parseDouble(obj[31].toString()));
                }

                rowCount++;
            }
        }


        if (reportType == 6 && formId == 4)
        {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_5.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP5 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP5.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP5.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }


                rowCount++;
            }
        }
        if (reportType == 6 && formId == 5) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_6_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP6_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP6_1.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP6_1.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 6 && formId == 6) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_6_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP6_2 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP6_2.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP6_2.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 6 && formId == 7) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_7.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP7 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP7.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP7.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 6 && formId == 8) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Coal_Report_8.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "ORDERNUMBER,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP8 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP8.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and MRAM.ANNUAL_REGISTRATION.DIVISIONID=2\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP8.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(obj[28].toString());
                }
                Cell cell30 = row.createCell(29);
                if (obj[29] != null) {
                    cell30.setCellValue(obj[29].toString());
                }
                Cell cell31 = row.createCell(30);
                if (obj[30] != null) {
                    cell31.setCellValue(Double.parseDouble(obj[30].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 6 && formId == 9) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_9.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "\n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP9.PLANID\n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and  MRAM.ANNUAL_REGISTRATION.REPSTATUSID=1 \n" +
                    "    and TYPE=1 and  MRAM.ANNUAL_REGISTRATION.REPORTYEAR=" + planYr + "\n" +
                    "\t  and (DATA1 is not  NULL)\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP9.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = row.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if (obj[27] != null) {
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if (obj[28] != null) {
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }


                rowCount++;
            }

            Sheet sheet1 = workbook.getSheetAt(1);
            Row rowS1 = sheet1.getRow(1);
            Cell cel21 = rowS1.createCell(2);
            cel21.setCellValue(planYr);
            Row rowS12 = sheet1.getRow(2);
            Cell cels2 = rowS12.createCell(2);
            cels2.setCellValue(DateToStr1);
            String queryStr2 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "(select LUT_BLAST.\"Negj\" from LUT_BLAST where LUT_BLAST.\"Pro_name\"= MRAM.DATA_EXCEL_COALREP9.DATA2) as DATA3,\n" +
                    "DATA4,\n" +
                    "DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP9.PLANID\n" +
                    " \n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and  MRAM.DATA_EXCEL_COALREP9.TYPE = 2 and MRAM.ANNUAL_REGISTRATION.REPORTYEAR=" + planYr + "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP9.ID\n" +
                    "\n" +
                    "\n";
            List<Object[]> objectSheets = dao.getNativeSQLResult(queryStr2, "list");
            int rowCountSheets = 7;
            for (Object[] obj : objectSheets) {
                rowS1 = sheet1.createRow(rowCountSheets);
                Cell cell1 = rowS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = rowS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = rowS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = rowS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = rowS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = rowS1.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = rowS1.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }

                rowCountSheets++;
            }

            Sheet sheet2 = workbook.getSheetAt(2);
            Row rowSS1 = sheet2.getRow(1);
            Cell cel3l = rowSS1.createCell(2);
            cel3l.setCellValue(planYr);
            Row rowSS12 = sheet2.getRow(2);
            Cell cel32 = rowSS12.createCell(2);
            cel32.setCellValue(DateToStr1);
            String queryStr3 = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3,\n" +
                    "DATA13,\n" +
                    "DATA12,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP9 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP9.PLANID\n" +
                    " \n" +
                    "WHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and  MRAM.DATA_EXCEL_COALREP9.TYPE = 3 and MRAM.ANNUAL_REGISTRATION.REPORTYEAR=" + planYr + "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP9.ID\n" +
                    "\n" +
                    "\n" +
                    "\n";
            List<Object[]> objectSheets1 = dao.getNativeSQLResult(queryStr3, "list");
            int rowCountSheets1 = 7;
            for (Object[] obj : objectSheets1) {
                rowSS1 = sheet2.createRow(rowCountSheets1);
                Cell cell1 = rowSS1.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = rowSS1.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = rowSS1.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = rowSS1.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = rowSS1.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = rowSS1.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = rowSS1.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = rowSS1.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = rowSS1.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = rowSS1.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = rowSS1.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = rowSS1.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = rowSS1.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell114 = rowSS1.createCell(13);
                if (obj[13] != null) {
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = rowSS1.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = rowSS1.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = rowSS1.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = rowSS1.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = rowSS1.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCountSheets1++;
            }
        }

        if (reportType == 6 && formId == 10) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/miningStat10.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
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

            for (String v : vals) {
                sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_COALREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 IS NOT NULL AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_COALREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 IS NOT NULL AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA7 FROM DATA_EXCEL_COALREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 IS NOT NULL AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_COALREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 IS NOT NULL AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA10 FROM DATA_EXCEL_COALREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 IS NOT NULL AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA11 FROM DATA_EXCEL_COALREP10 DD WHERE DD.PLANID = A.ID AND DD.DATA2 IS NOT NULL AND DD.DATA2 " + v + " AND ROWNUM = 1)";
                if (!v.equalsIgnoreCase(vals[vals.length - 1])) {
                    sqlQuery = sqlQuery + ",";
                }
            }
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setWrapText(true);


            sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + planYr + " AND A .XTYPE != 0 AND A .DIVISIONID = 2 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
            List<Object[]> objects = dao.getNativeSQLResult(sqlQuery, "list");
            int rowCount = 7;

            if (objects != null && objects.size() > 0) {
                for (int iterator = 0; iterator < objects.size(); iterator++) {
                    Object[] o = objects.get(iterator);
                    setCellData(sheet, iterator + 6, 0, iterator + 1, style, "number");
                    for (int cellIterator = 1; cellIterator < o.length; cellIterator++) {
                        setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, (cellIterator > 10) ? "float" : "string");
                    }
                }
            }

        }
        if (reportType == 6 && formId == 11) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/miningStat11.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
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

            for (String v : vals) {
                sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_COALREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_COALREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA6 FROM DATA_EXCEL_COALREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_COALREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA9 FROM DATA_EXCEL_COALREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA10 FROM DATA_EXCEL_COALREP11 DD WHERE DD.PLANID = A.ID AND DD.DATA2 " + v + " AND ROWNUM = 1)";
                if (!v.equalsIgnoreCase(vals[vals.length - 1])) {
                    sqlQuery = sqlQuery + ",";
                }
            }

            sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + planYr + " AND A .XTYPE != 0 AND A .DIVISIONID = 2 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setWrapText(true);
            if (resultObj != null && resultObj.size() > 0) {
                for (int iterator = 0; iterator < resultObj.size(); iterator++) {
                    Object[] o = resultObj.get(iterator);
                    setCellData(sheet, iterator + 6, 0, iterator + 1, style, "number");
                    for (int cellIterator = 1; cellIterator < o.length; cellIterator++) {
                        if (o[cellIterator] instanceof String) {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "string");
                        } else {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "float");
                        }
                    }
                }
            }

        }
        if (reportType == 6 && formId == 12) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/miningStat12.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String sqlQuery = "SELECT A.ID, A.LPNAME,A.LP_REG,A.LICENSEXB," +
                    "(SELECT LUT_APPSTATUS.STATUSNAMEMON FROM LUT_APPSTATUS WHERE LUT_APPSTATUS.STATUSID = A.REPSTATUSID AND ROWNUM=1), " +
                    "(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1)," +
                    "(SELECT M.MINERALNAMEMON FROM LUT_MINERALS M WHERE M.MINERALID = A.MINID AND ROWNUM = 1)," +
                    "(SELECT D.DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D.DEPOSITID = A.DEPOSITID AND ROWNUM = 1)," +
                    "(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1)," +
                    "(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1)," +
                    "(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1)," +
                    "(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A.LICENSEXB AND ROWNUM = 1),";

            for (int iterator = 1; iterator <= 57; iterator++) {
                sqlQuery = sqlQuery + "(SELECT DD.DATA4 FROM DATA_EXCEL_COALREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA5 FROM DATA_EXCEL_COALREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA6 FROM DATA_EXCEL_COALREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA7 FROM DATA_EXCEL_COALREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT DD.DATA8 FROM DATA_EXCEL_COALREP12 DD WHERE DD.PLANID = A.ID AND DD.ORDERNUMBER = " + iterator + " AND ROWNUM = 1)";
                if (iterator != 57) {
                    sqlQuery = sqlQuery + ",";
                }
            }

            sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID IN (1,2,7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + planYr + " AND A .XTYPE != 0 AND A .DIVISIONID = 2 ORDER BY A.LPNAME ASC, A.REPSTATUSID DESC";
            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setWrapText(true);
            if (resultObj != null && resultObj.size() > 0) {
                for (int iterator = 0; iterator < resultObj.size(); iterator++) {
                    Object[] o = resultObj.get(iterator);
                    setCellData(sheet, iterator + 6, 0, iterator + 1, style, "number");
                    for (int cellIterator = 1; cellIterator < o.length; cellIterator++) {
                        if (o[cellIterator] instanceof String) {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "string");
                        } else {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "float");
                        }
                    }
                }
            }

        }
        if (reportType == 6 && formId == 13) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_13.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP13 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP13.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP13.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 6 && formId == 14) {
            FileInputStream fis = null;
            File files = new File(appPath + "assets/reporttemplate/miningRep14.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            //Row row1 = sheet.getRow(2);
            Cell cel2 = row.createCell(7);
            cel2.setCellValue(DateToStr1);
            String sqlQuery = "SELECT A .LPNAME, A .LP_REG, A .LICENSEXB,(SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M .MINERALNAMEMON FROM LUT_MINERALS M WHERE M .MINERALID = A .MINID AND ROWNUM = 1),(SELECT D .DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D .DEPOSITID = A .DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),";

            for(int i=1;i<=24;i++){
                sqlQuery = sqlQuery + "(SELECT D.DATA4 FROM DATA_EXCEL_COALREP14 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT D.DATA5 FROM DATA_EXCEL_COALREP14 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
            }

            sqlQuery = sqlQuery.substring(0,sqlQuery.length()-1);

            sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID IN (1, 2, 7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + planYr + " AND A .DIVISIONID = 2 ORDER BY A .LPNAME ASC";

            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setWrapText(true);
            if (resultObj != null && resultObj.size() > 0) {
                for (int iterator = 0; iterator < resultObj.size(); iterator++) {
                    Object[] o = resultObj.get(iterator);
                    setCellData(sheet, iterator + 6, 0, iterator + 1, style, "number");
                    for (int cellIterator = 1; cellIterator < o.length; cellIterator++) {
                        if (o[cellIterator] instanceof String) {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "string");
                        } else {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "float");
                        }
                    }
                }
            }
        }
        if (reportType == 6 && formId == 15) {
            FileInputStream fis = null;
            File files = new File(appPath + "assets/reporttemplate/miningRep15.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            //Row row1 = sheet.getRow(2);
            Cell cel2 = row.createCell(7);
            cel2.setCellValue(DateToStr1);
            String sqlQuery = "SELECT A .LPNAME, A .LP_REG, A .LICENSEXB,(SELECT S.STATUSNAMEMON FROM LUT_APPSTATUS S WHERE S.STATUSID = A .REPSTATUSID AND ROWNUM = 1),(SELECT REQ.ADD_BUNLICENSENUM FROM REG_REPORT_REQ REQ WHERE REQ. ID = A .REQID AND ROWNUM = 1),(SELECT M .MINERALNAMEMON FROM LUT_MINERALS M WHERE M .MINERALID = A .MINID AND ROWNUM = 1),(SELECT D .DEPOSITNAMEMON FROM LUT_DEPOSIT D WHERE D .DEPOSITID = A .DEPOSITID AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREANAMEMON FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),(SELECT SUB_LICENSES.AREASIZE FROM SUB_LICENSES WHERE SUB_LICENSES.LICENSEXM = A .LICENSEXB AND ROWNUM = 1),";

            for(int i=1;i<=26;i++){
                sqlQuery = sqlQuery + "(SELECT D.DATA4 FROM DATA_EXCEL_COALREP15 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
                sqlQuery = sqlQuery + "(SELECT D.DATA5 FROM DATA_EXCEL_COALREP15 D WHERE D.PLANID = A .ID AND D.ORDERNUMBER = " + i + " AND ROWNUM = 1),";
            }

            sqlQuery = sqlQuery.substring(0,sqlQuery.length()-1);

            sqlQuery = sqlQuery + " FROM ANNUAL_REGISTRATION A WHERE A .ISTODOTGOL = 0 AND A .REPSTATUSID IN (1, 2, 7) AND A .REPORTTYPE = 4 AND A .REPORTYEAR = " + planYr + " AND A .DIVISIONID = 2 ORDER BY A .LPNAME ASC";

            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setWrapText(true);
            if (resultObj != null && resultObj.size() > 0) {
                for (int iterator = 0; iterator < resultObj.size(); iterator++) {
                    Object[] o = resultObj.get(iterator);
                    setCellData(sheet, iterator + 6, 0, iterator + 1, style, "number");
                    for (int cellIterator = 1; cellIterator < o.length; cellIterator++) {
                        if (o[cellIterator] instanceof String) {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "string");
                        } else {
                            setCellData(sheet, iterator + 6, cellIterator, o[cellIterator], style, "float");
                        }
                    }
                }
            }
        }

        if (reportType == 6 && formId == 16) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_16.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP16 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP16.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and     MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP16.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(Double.parseDouble(obj[14].toString()));
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 6 && formId == 17) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Coal_Report_17.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "ORDERNUMBER,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,\n" +
                    "DATA11,DATA12,DATA13,\n" +
                    "MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP17 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP17.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and  MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP17.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }

                rowCount++;
            }
        }
        if (reportType == 6 && formId == 18) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_18.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP18 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP18.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "  MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and  MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and    MRAM.ANNUAL_REGISTRATION.REPORTYEAR = " + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP18.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                rowCount++;
            }
        }
        if (reportType == 6 && formId == 19) {
            FileInputStream fis = null;
            File files = new File(appPath + "/assets/excel/plan/Mining_Report_19.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell cell = row.createCell(2);
            cell.setCellValue(planYr);
            Row row1 = sheet.getRow(2);
            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(DateToStr1);
            String queryStr = "SELECT\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSENUM AS Дугаар,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LPNAME AS Эзэмшигч,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LP_REG AS Регистер,\n" +
                    "MRAM.ANNUAL_REGISTRATION.LICENSEXB AS Лиценз_Дугаар,\n" +
                    "MRAM.REG_REPORT_REQ.ADD_BUNLICENSENUM as НэмэлтТЗ,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONAIMAG as Аймаг,\n" +
                    "MRAM.SUB_LICENSES.LOCATIONSOUM as Сум,\n" +
                    "MRAM.SUB_LICENSES.AREANAMEMON as Орд_нэр,\n" +
                    "MRAM.SUB_LICENSES.AREASIZE as Талбай,\n" +
                    "MRAM.LUT_MINERALS.MINERALNAMEMON as АМ_нэр,\n" +
                    "MRAM.LUT_DEPOSIT.DEPOSITNAMEMON AS АМ_төрөл,\n" +
                    "MRAM.ANNUAL_REGISTRATION.REPORTYEAR as Огноо,\n" +
                    "case MRAM.ANNUAL_REGISTRATION.REPSTATUSID \n" +
                    " when 1 then 'Баталгаажсан'\n" +
                    " when 2 then 'Буцаасан'\n" +
                    " when 3 then 'Татгалзсан' \n" +
                    " when 7 then 'Илгээсэн' \n" +
                    " when 0 then 'Хадгалсан' \n" +
                    "end as ТӨлөв,\n" +
                    "DATA1,\n" +
                    "DATA2,\n" +
                    "DATA3, \n" +
                    "DATA4, \n" +
                    "DATA5, \n" +
                    "DATA6, \n" +
                    "DATA7, \n" +
                    "DATA8, \n" +
                    "DATA9, \n" +
                    "DATA10, \n" +
                    "DATA11, \n" +
                    "DATA12, \n" +
                    "DATA13,MRAM.ANNUAL_REGISTRATION.ISTODOTGOL\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_EXCEL_COALREP19 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_EXCEL_COALREP19.PLANID\n" +
                    "\n" +
                    "\tWHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.DIVISIONID=2 and  MRAM.ANNUAL_REGISTRATION.REPORTTYPE=4 and   MRAM.ANNUAL_REGISTRATION.REPORTYEAR=" + planYr + " \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_EXCEL_COALREP19.ID";
            List<Object[]> objects = dao.getNativeSQLResult(queryStr, "list");
            int rowCount = 7;
            for (Object[] obj : objects) {
                row = sheet.createRow(rowCount);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(Double.parseDouble(obj[0].toString()));
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(obj[1].toString());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(Double.parseDouble(obj[2].toString()));
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(obj[3].toString());
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(obj[4].toString());
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(obj[5].toString());
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(obj[6].toString());
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(obj[7].toString());
                Cell cell9 = row.createCell(8);
                cell9.setCellValue(Double.parseDouble(obj[8].toString()));
                Cell cell10 = row.createCell(9);
                cell10.setCellValue(obj[9].toString());
                Cell cell11 = row.createCell(10);
                cell11.setCellValue(obj[10].toString());
                Cell cell12 = row.createCell(11);
                cell12.setCellValue(Double.parseDouble(obj[11].toString()));
                Cell cell13 = row.createCell(12);
                cell13.setCellValue(obj[12].toString());

                Cell cell14 = row.createCell(13);
                if (obj[13] != null) {
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if (obj[14] != null) {
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if (obj[15] != null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if (obj[16] != null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if (obj[17] != null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if (obj[18] != null) {
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if (obj[19] != null) {
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if (obj[20] != null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if (obj[21] != null) {
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if (obj[22] != null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if (obj[23] != null) {
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if (obj[24] != null) {
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if (obj[25] != null) {
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = row.createCell(26);
                if (obj[26] != null) {
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }

                rowCount++;
            }
        }

        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
