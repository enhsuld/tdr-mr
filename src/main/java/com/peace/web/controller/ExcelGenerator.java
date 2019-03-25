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
                rowCount++;
            }
        }

        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
