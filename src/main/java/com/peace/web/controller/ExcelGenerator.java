package com.peace.web.controller;
import com.peace.users.dao.UserDao;
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


    public static ByteArrayInputStream customersToExcel(int reportType, int formId, int planYr, String formName, String appPath,UserDao dao) throws IOException {

        Workbook workbook = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();


        Date curDate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String DateToStr1 = format1.format(curDate);

        if(reportType == 3 && formId == 1){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_1_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "DATA2,DATA3,DATA4,DATA5,\n" +
                    "DATA6,\n" +
                    "DATA7,DATA8,DATA9,\n" +
                    "MRAM.DATA_MIN_PLAN_1.\"ID\"\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_1.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.REPORTYEAR  in ("+planYr+") \n" +
                    "\t and MRAM.LUT_DEPOSIT.MINERALTYPE=1\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_1.\"ID\"";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null) {
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null) {
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null) {
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null) {
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if(obj[21]!=null){
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if(obj[22]!=null) {
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                rowCount++;
            }
        }

        if(reportType == 3 && formId == 2){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_1_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "MRAM.DATA_MIN_PLAN_1.\"ID\"\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_1.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.REPORTYEAR  in ("+planYr+") \n" +
                    "\t and MRAM.LUT_DEPOSIT.MINERALTYPE=2\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_1.\"ID\"";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }

                rowCount++;
            }
        }
        if(reportType == 3 && formId == 3){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_2_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24\n" +
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
                    "   MRAM.ANNUAL_REGISTRATION.REPORTYEAR  = "+planYr+ " \n" +
                    "    and TYPE=1 \n" +
                    "\t\torder by  MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_2_1.\"ID\"";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[13]!=null){
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null){
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if(obj[21]!=null){
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if(obj[22]!=null){
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if(obj[23]!=null){
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if(obj[24]!=null){
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if(obj[25]!=null){
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if(obj[26]!=null){
                    cell27.setCellValue(obj[26].toString());
                }
                Cell cell28 = row.createCell(27);
                if(obj[27]!=null){
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if(obj[28]!=null){
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if(obj[29]!=null){
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if(obj[30]!=null){
                    cell31.setCellValue(obj[30].toString());
                }
                Cell cell32 = row.createCell(31);
                if(obj[31]!=null){
                    cell32.setCellValue(Double.parseDouble(obj[31].toString()));
                }
                Cell cell33 = row.createCell(32);
                if(obj[32]!=null){
                    cell33.setCellValue(obj[32].toString());
                }
                Cell cell34 = row.createCell(33);
                if(obj[33]!=null){
                    cell34.setCellValue(Double.parseDouble(obj[33].toString()));
                }
                Cell cell35 = row.createCell(34);
                if(obj[34]!=null){
                    cell35.setCellValue(obj[34].toString());
                }
                Cell cell36 = row.createCell(35);
                if(obj[35]!=null){
                    cell36.setCellValue(Double.parseDouble(obj[35].toString()));
                }
                Cell cell37 = row.createCell(36);
                if(obj[36]!=null){
                    cell37.setCellValue(obj[36].toString());
                }
                Cell cell38 = row.createCell(37);
                if(obj[37]!=null){
                    cell38.setCellValue(Double.parseDouble(obj[37].toString()));
                }

                rowCount++;
            }

            Sheet sheet1 = workbook.getSheetAt(1);
            Row rowS1= sheet1.getRow(1);
            Cell cel2l = rowS1.createCell(2);
            cel2l.setCellValue(planYr);
            Row rowS2 = sheet.getRow(2);
            Cell cels2 = rowS2.createCell(2);
            cels2.setCellValue(DateToStr1);
            String queryStr2="SELECT\n" +
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
                    "MRAM.DATA_MIN_PLAN_2_1.\"ID\"\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_2_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_2_1.PLANID\n" +
                    "\n" +
                    "WHERE\n" +
                    "   MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\t  and TYPE=5\n" +
                    "\t\torder by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_2_1.\"ID\",TYPE";
            List<Object[]> objectSheets=dao.getNativeSQLResult(queryStr2,"list");
            int rowCountSheets=7;
            for(Object[] obj:objectSheets){
                row = sheet.createRow(rowCountSheets);
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
                if(obj[13]!=null){
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null){
                    cell21.setCellValue(obj[20].toString());
                }
                Cell cell22 = row.createCell(21);
                if(obj[21]!=null){
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if(obj[22]!=null){
                    cell23.setCellValue(obj[22].toString());
                }
                Cell cell24 = row.createCell(23);
                if(obj[23]!=null){
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if(obj[24]!=null){
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if(obj[25]!=null){
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if(obj[26]!=null){
                    cell27.setCellValue(obj[26].toString());
                }
                Cell cell28 = row.createCell(27);
                if(obj[27]!=null){
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }

                rowCountSheets++;
            }
        }
        if(reportType == 3 && formId == 4){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_2_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "MRAM.DATA_MIN_PLAN_2_2.\"ID\"\n" +
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
                    "   MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr +" \n" +
                    "\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_2_2.ID,\n" +
                    "      \t\tMRAM.DATA_MIN_PLAN_2_2.DATAINDEX";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[13]!=null){
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null){
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if(obj[21]!=null){
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if(obj[22]!=null){
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if(obj[23]!=null){
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if(obj[24]!=null){
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if(obj[25]!=null){
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if(obj[26]!=null){
                    cell27.setCellValue(obj[26].toString());
                }
                Cell cell28 = row.createCell(27);
                if(obj[27]!=null){
                    cell28.setCellValue(obj[27].toString());
                }
                Cell cell29 = row.createCell(28);
                if(obj[28]!=null){
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if(obj[29]!=null){
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if(obj[30]!=null){
                    cell31.setCellValue(obj[30].toString());
                }
                Cell cell32 = row.createCell(31);
                if(obj[31]!=null){
                    cell32.setCellValue(Double.parseDouble(obj[31].toString()));
                }
                Cell cell33 = row.createCell(32);
                if(obj[32]!=null){
                    cell33.setCellValue(obj[32].toString());
                }
                Cell cell34 = row.createCell(33);
                if(obj[33]!=null){
                    cell34.setCellValue(Double.parseDouble(obj[33].toString()));
                }
                Cell cell35 = row.createCell(34);
                if(obj[34]!=null){
                    cell35.setCellValue(obj[34].toString());
                }
                Cell cell36 = row.createCell(35);
                if(obj[35]!=null){
                    cell36.setCellValue(Double.parseDouble(obj[35].toString()));
                }
                Cell cell37 = row.createCell(36);
                if(obj[36]!=null){
                    cell37.setCellValue(obj[36].toString());
                }
                Cell cell38 = row.createCell(37);
                if(obj[37]!=null){
                    cell38.setCellValue(Double.parseDouble(obj[37].toString()));
                }

                rowCount++;
            }
        }
        if(reportType == 3 && formId == 5){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_3.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "MRAM.LUT_DEPOSIT.MINERALTYPE\n" +
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
                    "   MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+ planYr +" \n" +
                    "\t \t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_3.\"ID\"";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null){
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if(obj[21]!=null){
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if(obj[22]!=null){
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if(obj[23]!=null){
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if(obj[24]!=null){
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if(obj[25]!=null){
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if(obj[26]!=null){
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if(obj[27]!=null){
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if(obj[28]!=null){
                    cell29.setCellValue(Double.parseDouble(obj[28].toString()));
                }
                Cell cell30 = row.createCell(29);
                if(obj[29]!=null){
                    cell30.setCellValue(Double.parseDouble(obj[29].toString()));
                }
                Cell cell31 = row.createCell(30);
                if(obj[30]!=null){
                    cell31.setCellValue(Double.parseDouble(obj[30].toString()));
                }
                Cell cell32 = row.createCell(31);
                if(obj[31]!=null){
                    cell32.setCellValue(Double.parseDouble(obj[31].toString()));
                }
                Cell cell33 = row.createCell(32);
                if(obj[32]!=null){
                    cell33.setCellValue(Double.parseDouble(obj[32].toString()));
                }

                rowCount++;
            }
        }
        if(reportType == 3 && formId == 6){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_4_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "MRAM.DATA_MIN_PLAN_4_1.\"ID\"\n" +
                    "FROM\n" +
                    "MRAM.ANNUAL_REGISTRATION\n" +
                    "INNER JOIN MRAM.SUB_LICENSES on MRAM.ANNUAL_REGISTRATION.LICENSEXB=MRAM.SUB_LICENSES.LICENSEXM\n" +
                    "INNER JOIN MRAM.LUT_MINERALS on MRAM.ANNUAL_REGISTRATION.MINID=MRAM.LUT_MINERALS.MINERALID \n" +
                    "INNER JOIN MRAM.LUT_DEPOSIT ON MRAM.ANNUAL_REGISTRATION.DEPOSITID = MRAM.LUT_DEPOSIT.DEPOSITID\n" +
                    "INNER JOIN MRAM.LUT_LICTYPE ON MRAM.ANNUAL_REGISTRATION.LICTYPE = MRAM.LUT_LICTYPE.LICTYPEID\n" +
                    "INNER JOIN MRAM.REG_REPORT_REQ on MRAM.REG_REPORT_REQ.\"ID\"=MRAM.ANNUAL_REGISTRATION.REQID \n" +
                    "INNER JOIN MRAM.DATA_MIN_PLAN_4_1 ON MRAM.ANNUAL_REGISTRATION.ID = MRAM.DATA_MIN_PLAN_4_1.PLANID\n" +
                    "where \n" +
                    "   MRAM.ANNUAL_REGISTRATION.REPORTYEAR  ="+planYr+"\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_4_1.\"ID\"";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null){
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if(obj[21]!=null){
                    cell22.setCellValue(obj[21].toString());
                }
                Cell cell23 = row.createCell(22);
                if(obj[22]!=null){
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if(obj[23]!=null){
                    cell24.setCellValue(obj[23].toString());
                }
                Cell cell25 = row.createCell(24);
                if(obj[24]!=null){
                    cell25.setCellValue(Double.parseDouble(obj[24].toString()));
                }
                Cell cell26 = row.createCell(25);
                if(obj[25]!=null){
                    cell26.setCellValue(obj[25].toString());
                }
                Cell cell27 = row.createCell(26);
                if(obj[26]!=null){
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if(obj[27]!=null){
                    cell28.setCellValue(obj[27].toString());
                }

                rowCount++;
            }
        }
        if(reportType == 3 && formId == 7){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_4_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "MRAM.DATA_MIN_PLAN_4_2.\"ID\"\n" +
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
                    "   MRAM.ANNUAL_REGISTRATION.REPORTYEAR= "+planYr+" \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_4_2.\"ID\"";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(obj[19].toString());
                }

                rowCount++;
            }
        }
        if(reportType == 3 && formId == 8){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_5.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "DATA8\n" +
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
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_5.ID";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[13]!=null){
                    cell114.setCellValue(Double.parseDouble(obj[13].toString()));
                }

                Cell cell15 = row.createCell(14);
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null){
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if(obj[21]!=null){
                    cell22.setCellValue(obj[21].toString());
                }


                rowCount++;
            }
        }
        if(reportType == 3 && formId == 9){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_6_1.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "DATA5\n" +
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
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_6_1.ID";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[13]!=null){
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if(obj[14]!=null){
                    cell15.setCellValue(Double.parseDouble(obj[14].toString()));
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(obj[18].toString());
                }

                rowCount++;
            }
        }
        if(reportType == 3 && formId == 10){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_6_2.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "DATA8\n" +
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
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = 2019 \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_6_2.ID";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[13]!=null){
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if(obj[14]!=null){
                    cell15.setCellValue(Double.parseDouble(obj[14].toString()));
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null){
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if(obj[21]!=null){
                    cell22.setCellValue(obj[21].toString());
                }

                rowCount++;
            }
        }

        if(reportType == 3 && formId == 11){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_7.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "MRAM.DATA_MIN_PLAN_7.\"ID\"\n" +
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
                    "   MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_7.\"ID\"";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null){
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if(obj[21]!=null){
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if(obj[22]!=null){
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if(obj[23]!=null){
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if(obj[24]!=null){
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if(obj[25]!=null){
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if(obj[26]!=null){
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if(obj[27]!=null){
                    cell28.setCellValue(obj[27].toString());
                }
                rowCount++;
            }
        }
        if(reportType == 3 && formId == 12){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_8.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "MRAM.DATA_MIN_PLAN_8.\"ID\"\n" +
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
                    "   MRAM.ANNUAL_REGISTRATION.REPORTYEAR="+planYr+"\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_8.\"ID\"";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null){
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if(obj[21]!=null){
                    cell22.setCellValue(Double.parseDouble(obj[21].toString()));
                }
                Cell cell23 = row.createCell(22);
                if(obj[22]!=null){
                    cell23.setCellValue(Double.parseDouble(obj[22].toString()));
                }
                Cell cell24 = row.createCell(23);
                if(obj[23]!=null){
                    cell24.setCellValue(Double.parseDouble(obj[23].toString()));
                }
                Cell cell25 = row.createCell(24);
                if(obj[24]!=null){
                    cell25.setCellValue(obj[24].toString());
                }
                Cell cell26 = row.createCell(25);
                if(obj[25]!=null){
                    cell26.setCellValue(Double.parseDouble(obj[25].toString()));
                }
                Cell cell27 = row.createCell(26);
                if(obj[26]!=null){
                    cell27.setCellValue(Double.parseDouble(obj[26].toString()));
                }
                Cell cell28 = row.createCell(27);
                if(obj[27]!=null){
                    cell28.setCellValue(Double.parseDouble(obj[27].toString()));
                }
                Cell cell29 = row.createCell(28);
                if(obj[28]!=null){
                    cell29.setCellValue(obj[28].toString());
                }
                Cell cell30 = row.createCell(29);
                if(obj[29]!=null){
                    cell30.setCellValue(obj[29].toString());
                }
                rowCount++;
            }
        }
        if(reportType == 3 && formId == 14){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_10.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7\n" +
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
                    "   MRAM.ANNUAL_REGISTRATION.REPORTYEAR  = "+planYr+"  and (DATAINDEX=1)\n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_10.\"ID\"";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(Double.parseDouble(obj[18].toString()));
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(obj[19].toString());
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null){
                    cell21.setCellValue(Double.parseDouble(obj[20].toString()));
                }
                Cell cell22 = row.createCell(21);
                if(obj[21]!=null){
                    cell22.setCellValue(obj[21].toString());
                }

                rowCount++;
            }
        }

        if(reportType == 3 && formId == 15){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_11.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "MRAM.DATA_MIN_PLAN_11.DATA7\n" +
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
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" and DATA2<>'Нийт'\n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_11.ID";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }

                rowCount++;
            }
        }
        if(reportType == 3 && formId == 16){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_12.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "DATA6\n" +
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
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_12.ID";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[13]!=null){
                    cell114.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(obj[18].toString());
                }

                rowCount++;
            }
        }
        if(reportType == 3 && formId == 17){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_14.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "DATA5\n" +
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
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_14.ID";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }

                rowCount++;
            }
        }
        if(reportType == 3 && formId == 18){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_15.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "DATA5\n" +
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
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_15.ID";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(Double.parseDouble(obj[16].toString()));
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(obj[17].toString());
                }

                rowCount++;
            }
        }
        if(reportType == 3 && formId == 20){
            FileInputStream fis = null;
            File files = new File(appPath+"/assets/excel/plan/Mining_Plan_17.xlsx");
            fis = new FileInputStream(files);
            workbook = new XSSFWorkbook(fis);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
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
            String queryStr="SELECT\n" +
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
                    "MRAM.DATA_MIN_PLAN_17.DATA8\n" +
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
                    "       MRAM.ANNUAL_REGISTRATION.REPORTYEAR = "+planYr+" \n" +
                    "\t\t\t \n" +
                    "order by MRAM.ANNUAL_REGISTRATION.LICENSEXB,MRAM.DATA_MIN_PLAN_17.ID";
            List<Object[]> objects=dao.getNativeSQLResult(queryStr,"list");
            int rowCount=7;
            for(Object[] obj:objects){
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
                if(obj[13]!=null){
                    cell14.setCellValue(obj[13].toString());
                }

                Cell cell15 = row.createCell(14);
                if(obj[14]!=null){
                    cell15.setCellValue(obj[14].toString());
                }
                Cell cell16 = row.createCell(15);
                if(obj[15]!=null){
                    cell16.setCellValue(obj[15].toString());
                }
                Cell cell17 = row.createCell(16);
                if(obj[16]!=null){
                    cell17.setCellValue(obj[16].toString());
                }
                Cell cell18 = row.createCell(17);
                if(obj[17]!=null){
                    cell18.setCellValue(Double.parseDouble(obj[17].toString()));
                }
                Cell cell19 = row.createCell(18);
                if(obj[18]!=null){
                    cell19.setCellValue(obj[18].toString());
                }
                Cell cell20 = row.createCell(19);
                if(obj[19]!=null){
                    cell20.setCellValue(Double.parseDouble(obj[19].toString()));
                }
                Cell cell21 = row.createCell(20);
                if(obj[20]!=null){
                    cell21.setCellValue(obj[20].toString());
                }

                rowCount++;
            }
        }
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
