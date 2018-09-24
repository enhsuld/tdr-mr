package com.peace.web.logic.controller;

import java.io.*;
import java.net.URLEncoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peace.users.model.mram.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.peace.MramApplication;
import com.peace.users.dao.UserDao;
import com.peace.users.model.FileMeta;

@RestController
@RequestMapping("/import")
public class FormImportController {

	@Autowired
	UserDao dao;

	private String clobToString(Clob data) {
		StringBuilder sb = new StringBuilder();
		try {
			Reader reader = data.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);

			String line;
			while(null != (line = br.readLine())) {
				sb.append(line);
			}
			br.close();
		} catch (SQLException e) {
			// handle this exception
		} catch (IOException e) {
			// handle this exception
		}
		return sb.toString();
	}

	@RequestMapping(value = "/comments/{type}", method = RequestMethod.POST)
	public String generateReport(HttpServletResponse response, HttpServletRequest req, @PathVariable String type) throws JsonProcessingException, SQLException {
		UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
		JSONArray result = new JSONArray();
		if (loguser != null){
			if (type.equalsIgnoreCase("LNK_COMMENT")){
				List<Object[]> resultObj = (List<Object[]>) dao.getNativeSQLResult("SELECT (SELECT LUT_FORM_NOTES.NOTE FROM LUT_FORM_NOTES WHERE LUT_FORM_NOTES. ID = D .NOTEID), D.NOTEID, DBMS_LOB.substr(D.COMNOTE, 4000), D.COMDATE, D.DESICIONID, D.OFFICERNAME, A.LICENSEXB, A.LP_REG, A.REPORTTYPE, A.LICTYPE, A.REPORTYEAR, A.REPSTATUSID, A.DIVISIONID, A.LPNAME, A.GROUPID, A.MINID, A.DEPOSITID, R.ADD_BUNLICENSENUM, R.AREANAME, S.AREASIZE, A.XTYPE, S.LOCATIONAIMAG, S.LOCATIONSOUM FROM LNK_COMMENT D LEFT JOIN ANNUAL_REGISTRATION A ON D.PLANID = A.ID LEFT JOIN REG_REPORT_REQ R ON A.REQID = R.ID LEFT JOIN SUB_LICENSES S ON S.LICENSENUM = A.LICENSENUM WHERE D .OFFICERID ="+loguser.getId()+" ORDER BY D .ID DESC", "list");
				String[] headers = {"NOTE", "NOTEID", "COMNOTE", "COMDATE", "DESICIONID", "OFFICERNAME", "LICENSEXB", "LP_REG", "REPORTTYPE", "LICTYPE", "REPORTYEAR", "REPSTATUSID", "DIVISIONID", "LPNAME", "GROUPID", "MINID", "DEPOSITID", "ADD_BUNLICENSENUM", "AREANAME", "AREASIZE", "XTYPE", "LOCATIONAIMAG", "LOCATIONSOUM"};
				if (resultObj != null){
					for(Object[] o : resultObj){
						JSONObject obj = new JSONObject();
						for(int i=0;i<headers.length;i++){
							/*if (o[i] instanceof Clob){
								Clob clobObj = (Clob) o[i];
								obj.put(headers[i].toLowerCase(), clobToString(clobObj));
							}
							else{*/
								obj.put(headers[i].toLowerCase(), (o[i] != null) ? o[i] : "");
							//}
						}
						result.put(obj);
					}
				}
			}
			else if (type.equalsIgnoreCase("LNK_COMMENT_MAIN")){
				List<Object[]> resultObj = (List<Object[]>) dao.getNativeSQLResult("SELECT D.MCOMMENT, D.CREATEDDATE, D.DESID, D.USERNAME, A.LICENSEXB, A.LP_REG, A.REPORTTYPE, A.LICTYPE, A.REPORTYEAR, A.REPSTATUSID, A.DIVISIONID, A.LPNAME, A.GROUPID, A.MINID, A.DEPOSITID, R.ADD_BUNLICENSENUM, R.AREANAME, S.AREASIZE, A.XTYPE, S.LOCATIONAIMAG, S.LOCATIONSOUM FROM LNK_COMMENT_MAIN D LEFT JOIN ANNUAL_REGISTRATION A ON D.PLANID = A.ID LEFT JOIN REG_REPORT_REQ R ON A.REQID = R.ID LEFT JOIN SUB_LICENSES S ON S.LICENSENUM = A.LICENSENUM WHERE D .USERID ="+loguser.getId()+" ORDER BY D .ID DESC", "list");
				String[] headers = {"MCOMMENT", "CREATEDDATE", "DESID", "USERNAME", "LICENSEXB", "LP_REG", "REPORTTYPE", "LICTYPE", "REPORTYEAR", "REPSTATUSID", "DIVISIONID", "LPNAME", "GROUPID", "MINID", "DEPOSITID", "ADD_BUNLICENSENUM", "AREANAME", "AREASIZE", "XTYPE", "LOCATIONAIMAG", "LOCATIONSOUM"};
				if (resultObj != null){
					for(Object[] o : resultObj){
						JSONObject obj = new JSONObject();
						for(int i=0;i<headers.length;i++){
							/*if (o[i] instanceof Clob){
								Clob clobObj = (Clob) o[i];
								obj.put(headers[i].toLowerCase(), clobToString(clobObj));
							}
							else{*/
							obj.put(headers[i].toLowerCase(), (o[i] != null) ? o[i] : "");
							//}
						}
						result.put(obj);
					}
				}
			}
			else if (type.equalsIgnoreCase("DATA_MIN_PLAN_5")){
				List<Object[]> resultObj = (List<Object[]>) dao.getNativeSQLResult("SELECT A.LICENSEXB, A.LICTYPE, A.LPNAME, A.LP_REG, A.DIVISIONID, A.REPORTYEAR, A.REPORTTYPE, A.MINID, A.DEPOSITID, A.GROUPID, D.DATA1, D.DATA2, D.DATA3, D.DATA4, D.DATA5, D.DATA6, D.DATA7, D.DATA8 FROM DATA_MIN_PLAN_5 D LEFT JOIN ANNUAL_REGISTRATION A ON A.ID = D.PLANID WHERE A.REPSTATUSID = 1 AND A.DIVISIONID = "+loguser.getDivisionid()+" ORDER BY D.PLANID, D.ID", "list");
				String[] headers = {"LICENSEXB", "LICTYPE", "LPNAME", "LP_REG", "DIVISIONID", "REPORTYEAR", "REPORTTYPE", "MINID", "DEPOSITID", "GROUPID", "DATA1", "DATA2", "DATA3", "DATA4", "DATA5", "DATA6", "DATA7", "DATA8"};
				if (resultObj != null){
					for(Object[] o : resultObj){
						JSONObject obj = new JSONObject();
						for(int i=0;i<headers.length;i++){
							obj.put(headers[i].toLowerCase(), (o[i] != null) ? o[i] : "");
						}
						result.put(obj);
					}
				}
			}
			else if (type.equalsIgnoreCase("DATA_MIN_PLAN_6_1")){
				List<Object[]> resultObj = (List<Object[]>) dao.getNativeSQLResult("SELECT A.LICENSEXB, A.LICTYPE, A.LPNAME, A.LP_REG, A.DIVISIONID, A.REPORTYEAR, A.REPORTTYPE, A.MINID, A.DEPOSITID, A.GROUPID, D.DATA1, D.DATA2, D.DATA3, D.DATA4, D.DATA5 FROM DATA_MIN_PLAN_6_1 D LEFT JOIN ANNUAL_REGISTRATION A ON A.ID = D.PLANID WHERE A.REPSTATUSID = 1 AND A.DIVISIONID = "+loguser.getDivisionid()+" ORDER BY D.PLANID, D.ID", "list");
				String[] headers = {"LICENSEXB", "LICTYPE", "LPNAME", "LP_REG", "DIVISIONID", "REPORTYEAR", "REPORTTYPE", "MINID", "DEPOSITID", "GROUPID", "DATA1", "DATA2", "DATA3", "DATA4", "DATA5"};
				if (resultObj != null){
					for(Object[] o : resultObj){
						JSONObject obj = new JSONObject();
						for(int i=0;i<headers.length;i++){
							obj.put(headers[i].toLowerCase(), (o[i] != null) ? o[i] : "");
						}
						result.put(obj);
					}
				}
			}
			else if (type.equalsIgnoreCase("DATA_MIN_PLAN_6_2")){
				List<Object[]> resultObj = (List<Object[]>) dao.getNativeSQLResult("SELECT A.LICENSEXB, A.LICTYPE, A.LPNAME, A.LP_REG, A.DIVISIONID, A.REPORTYEAR, A.REPORTTYPE, A.MINID, A.DEPOSITID, A.GROUPID, D.DATA1, D.DATA2, D.DATA3, D.DATA4, D.DATA5, D.DATA6, D.DATA7, D.DATA8, D.DATA9 FROM DATA_MIN_PLAN_6_2 D LEFT JOIN ANNUAL_REGISTRATION A ON A.ID = D.PLANID WHERE A.REPSTATUSID = 1 AND A.DIVISIONID = "+loguser.getDivisionid()+" ORDER BY D.PLANID, D.ID", "list");
				String[] headers = {"LICENSEXB", "LICTYPE", "LPNAME", "LP_REG", "DIVISIONID", "REPORTYEAR", "REPORTTYPE", "MINID", "DEPOSITID", "GROUPID", "DATA1", "DATA2", "DATA3", "DATA4", "DATA5", "DATA6", "DATA7", "DATA8", "DATA9"};
				if (resultObj != null){
					for(Object[] o : resultObj){
						JSONObject obj = new JSONObject();
						for(int i=0;i<headers.length;i++){
							/*if (o[i] instanceof Clob){
								Clob clobObj = (Clob) o[i];
								obj.put(headers[i].toLowerCase(), clobToString(clobObj));
							}
							else{*/
							obj.put(headers[i].toLowerCase(), (o[i] != null) ? o[i] : "");
							//}
						}
						result.put(obj);
					}
				}
			}
			else if (type.equalsIgnoreCase("DATA_MIN_PLAN_8")){
				List<Object[]> resultObj = (List<Object[]>) dao.getNativeSQLResult("SELECT A.LICENSEXB, A.LICTYPE, A.LPNAME, A.LP_REG, A.DIVISIONID, A.REPORTYEAR, A.REPORTTYPE, A.MINID, A.DEPOSITID, A.GROUPID, D.DATA1, D.DATA2, D.DATA3, D.DATA4, D.DATA5, D.DATA6, D.DATA7, D.DATA8, D.DATA9, D.DATA10, D.DATA11, D.DATA12, D.DATA13, D.DATA14, D.DATA15, D.DATA16, D.TOTAL, D.DATAINDEX FROM DATA_MIN_PLAN_8 D LEFT JOIN ANNUAL_REGISTRATION A ON A.ID = D.PLANID WHERE A.REPSTATUSID = 1 AND A.DIVISIONID = "+loguser.getDivisionid()+" ORDER BY D.PLANID, D.ID", "list");
				String[] headers = {"LICENSEXB", "LICTYPE", "LPNAME", "LP_REG", "DIVISIONID", "REPORTYEAR", "REPORTTYPE", "MINID", "DEPOSITID", "GROUPID", "DATA1", "DATA2", "DATA3", "DATA4", "DATA5", "DATA6", "DATA7", "DATA8", "DATA9", "DATA10", "DATA11", "DATA12", "DATA13", "DATA14", "DATA15", "DATA16", "TOTAL", "DATAINDEX"};
				if (resultObj != null){
					for(Object[] o : resultObj){
						JSONObject obj = new JSONObject();
						for(int i=0;i<headers.length;i++){
							/*if (o[i] instanceof Clob){
								Clob clobObj = (Clob) o[i];
								obj.put(headers[i].toLowerCase(), clobToString(clobObj));
							}
							else{*/
							obj.put(headers[i].toLowerCase(), (o[i] != null) ? o[i] : "");
							//}
						}
						result.put(obj);
					}
				}
			}
			else if (type.equalsIgnoreCase("DATA_MIN_PLAN_9")){
				List<Object[]> resultObj = (List<Object[]>) dao.getNativeSQLResult("SELECT A.LICENSEXB, A.LICTYPE, A.LPNAME, A.LP_REG, A.DIVISIONID, A.REPORTYEAR, A.REPORTTYPE, A.MINID, A.DEPOSITID, A.GROUPID, D.DATA1, D.DATA2, D.DATA3, D.DATA4, D.DATA5, D.DATA6, D.DATA7, D.DATA8, D.DATA9, D.DATA10, D.DATA11, D.DATA12, D.TYPE FROM DATA_MIN_PLAN_8 D LEFT JOIN ANNUAL_REGISTRATION A ON A.ID = D.PLANID WHERE A.REPSTATUSID = 1 AND A.DIVISIONID = "+loguser.getDivisionid()+" ORDER BY D.PLANID, D.ID", "list");
				String[] headers = {"LICENSEXB", "LICTYPE", "LPNAME", "LP_REG", "DIVISIONID", "REPORTYEAR", "REPORTTYPE", "MINID", "DEPOSITID", "GROUPID", "DATA1", "DATA2", "DATA3", "DATA4", "DATA5", "DATA6", "DATA7", "DATA8", "DATA9", "DATA10", "DATA11", "DATA12", "TYPE"};
				if (resultObj != null){
					for(Object[] o : resultObj){
						JSONObject obj = new JSONObject();
						for(int i=0;i<headers.length;i++){
							/*if (o[i] instanceof Clob){
								Clob clobObj = (Clob) o[i];
								obj.put(headers[i].toLowerCase(), clobToString(clobObj));
							}
							else{*/
							obj.put(headers[i].toLowerCase(), (o[i] != null) ? o[i] : "");
							//}
						}
						result.put(obj);
					}
				}
			}
		}
		return result.toString();
	}

	public Object getCellValue(FormulaEvaluator evaluator, Cell cell){
		if (cell.getCellType() == Cell.CELL_TYPE_STRING){
			return cell.getStringCellValue();
		}
		else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			return cell.getNumericCellValue();
		}
		else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA){
			try{
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC){
					return cellValue.getNumberValue();
				}
				else if (cellValue.getCellType() == Cell.CELL_TYPE_STRING){
					return cellValue.getStringValue();
				}
				else if (cellValue.getCellType() == Cell.CELL_TYPE_BOOLEAN){
					return cellValue.getBooleanValue();
				}
			}
			catch(Exception e){
				return null;
			}
			
		}
		return null;
	}

	public CellStyle getClonedCellStyle(Workbook wb, CellStyle currentStyle, Boolean is_locked){
        CellStyle newStyle = wb.createCellStyle();
        newStyle.cloneStyleFrom(currentStyle);
		newStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
		newStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        newStyle.setLocked(is_locked);
        return newStyle;
    }

	public void executeAllFormulasOfSheet(FormulaEvaluator evaluator, Sheet sheet){
		/*for(int rowIterator =sheet.getFirstRowNum(); rowIterator <= sheet.getLastRowNum(); rowIterator++){
			Row currentRow = sheet.getRow(rowIterator);
			if (currentRow != null){
				for(int columnIterator=currentRow.getFirstCellNum(); columnIterator <= currentRow.getLastCellNum(); columnIterator++){
					Cell currentCell = currentRow.getCell(columnIterator);
					if (currentCell != null){
						if (currentCell.getCellType() == Cell.CELL_TYPE_FORMULA){
							try{
								evaluator.evaluate(currentCell);
							}
							catch (Exception e){
								e.printStackTrace();
							}
						}
					}
				}
			}
		}*/
	}

	public void setPlanData(AnnualRegistration an, String sheetname, Workbook workbook){
	    try{
            AnnualRegistration oldAn = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.licensenum = " + an.getLicensenum() + " and t.reportyear = " + an.getReportyear() + " and t.repstatusid = 1 and t.divisionid = " + an.getDivisionid() + " and t.reporttype = 3 and (t.xtype is null or t.xtype != 0) and t.lictype = " + an.getLictype() + " and t.minid = " + an.getMinid() + " and t.depositid = " + an.getDepositid() + " and t.groupid = " + an.getGroupid(), "current");
            Sheet currentSheet = workbook.getSheet(sheetname);
            if (workbook != null && currentSheet != null && oldAn != null && sheetname != null && sheetname.length() > 0){
            	workbook.setForceFormulaRecalculation(true);
            	if (sheetname.equalsIgnoreCase("form-6-1") || sheetname.equalsIgnoreCase("cform-6-1")){
					List<DataMinPlan6_1> list = (List<DataMinPlan6_1>) dao.getHQLResult("from DataMinPlan6_1 t where t.planid = " + oldAn.getId(), "list");
					for (DataMinPlan6_1 d : list){
						for(int i=1;i<=6;i++){
							if (d.getData1().equalsIgnoreCase(String.valueOf(i))){
								Row currentRow = currentSheet.getRow(12 + i);
								if (currentRow != null){
									Cell currentCell = currentRow.getCell(3);
									if (currentCell == null){
										currentCell = currentRow.createCell(3);
									}
									if (d.getData4() != null && currentCell.getCellType() != Cell.CELL_TYPE_FORMULA){
										currentCell.setCellStyle(getClonedCellStyle(workbook, currentCell.getCellStyle(), true));
										currentCell.setCellValue(d.getData4());
									}
								}
							}
						}
					}
				}
				else if (sheetname.equalsIgnoreCase("form-6-2") || sheetname.equalsIgnoreCase("cform-6-2")){
					List<DataMinPlan6_2> list62 = (List<DataMinPlan6_2>) dao.getHQLResult("from DataMinPlan6_2 t where t.planid = " + oldAn.getId(), "list");
					Double[] indexes62 = {1.0, 2.0, 3.1, 3.2, 3.3, 3.4, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
					for (DataMinPlan6_2 d : list62){
						for(int i=0;i<indexes62.length;i++){
							if (Double.valueOf(d.getData1()).compareTo(indexes62[i]) == 0){
								Row currentRow = currentSheet.getRow(13 + i);
								if (currentRow != null){
									Cell currentCell = currentRow.getCell(5);
									if (currentCell == null){
										currentCell = currentRow.createCell(5);
									}

									if (currentCell.getCellStyle().getLocked() == false && d.getData5() != null && currentCell.getCellType() != Cell.CELL_TYPE_FORMULA){
										currentCell.setCellStyle(getClonedCellStyle(workbook, currentCell.getCellStyle(), true));
										currentCell.setCellValue(d.getData5());
									}

									currentCell = currentRow.getCell(7);
									if (currentCell == null){
										currentCell = currentRow.createCell(7);
									}
									if (currentCell.getCellStyle().getLocked() == false && d.getData6() != null && currentCell.getCellType() != Cell.CELL_TYPE_FORMULA){
										currentCell.setCellStyle(getClonedCellStyle(workbook, currentCell.getCellStyle(), true));
										currentCell.setCellValue(d.getData6());
									}

									currentCell = currentRow.getCell(9);
									if (currentCell == null){
										currentCell = currentRow.createCell(9);
									}
									if (currentCell.getCellStyle().getLocked() == false && d.getData7() != null && currentCell.getCellType() != Cell.CELL_TYPE_FORMULA){
										currentCell.setCellStyle(getClonedCellStyle(workbook, currentCell.getCellStyle(), true));
										currentCell.setCellValue(d.getData7());
									}

								}
							}
						}
					}
				}
				else if (sheetname.equalsIgnoreCase("form-10") || sheetname.equalsIgnoreCase("cform-10")){
					List<DataMinPlan10> list10 = (List<DataMinPlan10>) dao.getHQLResult("from DataMinPlan10 t where t.planid = " + oldAn.getId(), "list");
					Integer[] indexes10 = {17,18,20,21,22,23};
					for (DataMinPlan10 d : list10){
						for(int i=0;i<indexes10.length;i++){
							if (d.getType() == i+1){
								Row currentRow = currentSheet.getRow(indexes10[i]);
								if (currentRow != null){
									Cell currentCell = currentRow.getCell(4);
									if (currentCell == null){
										currentCell = currentRow.createCell(4);
									}

									if (currentCell.getCellStyle().getLocked() == false && d.getData2() != null && currentCell.getCellType() != Cell.CELL_TYPE_FORMULA){
										currentCell.setCellStyle(getClonedCellStyle(workbook, currentCell.getCellStyle(), true));
										currentCell.setCellValue(d.getData2());
									}

									currentCell = currentRow.getCell(7);
									if (currentCell == null){
										currentCell = currentRow.createCell(7);
									}
									if (currentCell.getCellStyle().getLocked() == false && d.getData4() != null && currentCell.getCellType() != Cell.CELL_TYPE_FORMULA){
										currentCell.setCellStyle(getClonedCellStyle(workbook, currentCell.getCellStyle(), true));
										currentCell.setCellValue(d.getData4());
									}

									currentCell = currentRow.getCell(10);
									if (currentCell == null){
										currentCell = currentRow.createCell(10);
									}
									if (currentCell.getCellStyle().getLocked() == false && d.getData6() != null && currentCell.getCellType() != Cell.CELL_TYPE_FORMULA){
										currentCell.setCellStyle(getClonedCellStyle(workbook, currentCell.getCellStyle(), true));
										currentCell.setCellValue(d.getData6());
									}

								}
							}
						}
					}
				}
				else if (sheetname.equalsIgnoreCase("form-11") || sheetname.equalsIgnoreCase("cform-11")){
					List<DataMinPlan11> list11 = (List<DataMinPlan11>) dao.getHQLResult("from DataMinPlan11 t where t.planid = " + oldAn.getId(), "list");
					for (DataMinPlan11 d : list11){
						for(int i=1;i<=8;i++){
							if (d.getData1() == i){
								Row currentRow = currentSheet.getRow(9 + i);
								if (currentRow != null){
									Cell currentCell = currentRow.getCell(3);
									if (currentCell == null){
										currentCell = currentRow.createCell(3);
									}
									if (d.getData4() != null && currentCell.getCellType() != Cell.CELL_TYPE_FORMULA){
										currentCell.setCellStyle(getClonedCellStyle(workbook, currentCell.getCellStyle(), true));
										currentCell.setCellValue(d.getData4());
									}

									currentCell = currentRow.getCell(7);
									if (currentCell == null){
										currentCell = currentRow.createCell(7);
									}
									if (d.getData7() != null && currentCell.getCellType() != Cell.CELL_TYPE_FORMULA){
										currentCell.setCellStyle(getClonedCellStyle(workbook, currentCell.getCellStyle(), true));
										currentCell.setCellValue(d.getData7());
									}
								}
							}
						}
					}
				}
				else if (sheetname.equalsIgnoreCase("form-14") || sheetname.equalsIgnoreCase("cform-14")){
					List<DataMinPlan14> list14 = (List<DataMinPlan14>) dao.getHQLResult("from DataMinPlan14 t where t.planid = " + oldAn.getId(), "list");
					for (DataMinPlan14 d : list14){
						for(int i=8;i<=31;i++){
							Row currentRow = currentSheet.getRow(i);
							if (currentRow != null) {
								Cell currentCell = currentRow.getCell(0);
								if (currentCell != null) {
									if (d.getData1() != null && d.getData1().length() > 0) {
										if ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC && d.getData1().replaceAll("\\s","").equalsIgnoreCase(String.valueOf(currentCell.getNumericCellValue()).replaceAll("\\s",""))) || (currentCell.getCellType() == Cell.CELL_TYPE_STRING && d.getData1().replaceAll("\\s","").equalsIgnoreCase(currentCell.getStringCellValue()))){
											Cell dataCell = currentRow.getCell(3);
											if (dataCell == null){
												dataCell = currentRow.createCell(3);
											}
											if (d.getData4() != null){
												dataCell.setCellStyle(getClonedCellStyle(workbook, dataCell.getCellStyle(), true));
												dataCell.setCellValue(d.getData4());
											}
										}
									}
								}
							}
						}
					}
				}
				else if (sheetname.equalsIgnoreCase("form-15") || sheetname.equalsIgnoreCase("cform-15")){
					List<DataMinPlan15> list15 = (List<DataMinPlan15>) dao.getHQLResult("from DataMinPlan15 t where t.planid = " + oldAn.getId(), "list");
					for (DataMinPlan15 d : list15){
						for(int i=9;i<=35;i++){
							Row currentRow = currentSheet.getRow(i);
							if (currentRow != null) {
								Cell currentCell = currentRow.getCell(0);
								if (currentCell != null) {
									if (d.getData1() != null && d.getData1().length() > 0) {
										if ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC && d.getData1().replaceAll("\\s","").equalsIgnoreCase(String.valueOf(currentCell.getNumericCellValue()).replaceAll("\\s",""))) || (currentCell.getCellType() == Cell.CELL_TYPE_STRING && d.getData1().replaceAll("\\s","").equalsIgnoreCase(currentCell.getStringCellValue()))){
											Cell dataCell = currentRow.getCell(3);
											if (dataCell == null){
												dataCell = currentRow.createCell(3);
											}
											if (d.getData4() != null){
												dataCell.setCellStyle(getClonedCellStyle(workbook, dataCell.getCellStyle(), true));
												dataCell.setCellValue(d.getData4());
											}
										}
									}
								}
							}
						}
					}
				}
				else if (sheetname.equalsIgnoreCase("form-19") || sheetname.equalsIgnoreCase("cform-19")){
					List<DataMinPlan17> list17 = (List<DataMinPlan17>) dao.getHQLResult("from DataMinPlan17 t where t.planid = " + oldAn.getId(), "list");
					for (DataMinPlan17 d : list17){
						for(int i=9;i<=29;i++){
							Row currentRow = currentSheet.getRow(i);
							if (currentRow != null) {
								Cell currentCell = currentRow.getCell(0);
								if (currentCell != null) {
									if (d.getData1() != null && d.getData1().length() > 0) {
										if ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC && d.getData1().replaceAll("\\s","").equalsIgnoreCase(String.valueOf(currentCell.getNumericCellValue()).replaceAll("\\s",""))) || (currentCell.getCellType() == Cell.CELL_TYPE_STRING && d.getData1().replaceAll("\\s","").equalsIgnoreCase(currentCell.getStringCellValue()))){
											Cell dataCell = currentRow.getCell(3);
											if (dataCell == null){
												dataCell = currentRow.createCell(3);
											}
											if (d.getData7() != null){
												dataCell.setCellStyle(getClonedCellStyle(workbook, dataCell.getCellStyle(), true));
												dataCell.setCellValue(d.getData7());
											}
										}
									}
								}
							}
						}
					}
				}
				executeAllFormulasOfSheet(workbook.getCreationHelper().createFormulaEvaluator(), currentSheet);
            }

        }catch(Exception e){
	        e.printStackTrace();
        }
	}

	public boolean checkFormDatas(List<LnkPlanTransition> transitions, long planid, Sheet sheet, FormulaEvaluator evaluator){
		Boolean result = true;
		AnnualRegistration planObj = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id = " + planid, "current");

		if (planObj != null && (planObj.getRepstatusid() == 1 || planObj.getRepstatusid() == 7)){
			result = false;
		}

		/*if (transitions != null && transitions.size() > 0) {
			if (transitions.get(0).getDecisionid() == 1) {
				result = false;
			}
		}*/

		Boolean yearCheck = false;
		for(int rowIterator=sheet.getFirstRowNum();rowIterator<=sheet.getLastRowNum();rowIterator++){
			Row currentRow = sheet.getRow(rowIterator);
			if (currentRow != null){
				for(int colIterator=0;colIterator<26;colIterator++){
					Cell currentCell = currentRow.getCell(colIterator);
					if (currentCell != null && currentCell.getCellType() == Cell.CELL_TYPE_STRING && (rowIterator-1 >= 0)){
						if (currentCell.getStringCellValue().equalsIgnoreCase("reportyear")){
							if (String.valueOf(getParametersCell(rowIterator-1, colIterator, evaluator, sheet)).equalsIgnoreCase(planObj.getReportyear())){
								yearCheck = true;
							}
						}
					}
				}
			}
		}

		if (!yearCheck){
			result = false;
		}

		return result;
	}

	public void createAttach(long plan, long noteid, String path1, String name, String ext, String special){
		try{
			List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = " + noteid + " and t.planid = " + plan, "list");
			if (noteid != 404){
				if (transitions != null && transitions.size() > 0){
					dao.PeaceCrud(null, "LnkPlanAttachedFiles", "multidelete", (long) plan, 0, 0, "where expid = " + plan + " and noteid = " + noteid + " and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
				}
				else{
					dao.PeaceCrud(null, "LnkPlanAttachedFiles", "multidelete", (long) plan, 0, 0, "where expid = " + plan + " and noteid = " + noteid + "and istodotgol = 0");
				}
				
			}

			for(LnkPlanTransition tr : transitions){
			    tr.setDecisionid(0);
                dao.PeaceCrud(tr, "LnkPlanTransition", "save", tr.getId(), 0, 0, null);
            }

			LnkPlanAttachedFiles lp = new LnkPlanAttachedFiles();
			lp.setNoteid(noteid);
			lp.setAttachfiletype(path1);
			lp.setExpid(plan);
			lp.setFilename(name);
			lp.setFileext(ext);
			lp.setAtdate(special);
			if (transitions != null && transitions.size() > 0){
				lp.setIstodotgol(transitions.get(0).getIstodotgol());
			}
			else{
				lp.setIstodotgol(false);
			}
			dao.PeaceCrud(lp, "LnkPlanAttachedFiles", "save", (long) 0, 0, 0, null);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void saveInfos(int plan, Sheet sheet, FormulaEvaluator evaluator, int noteid) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		try{
			String[] vals = {"givname","geologyst","accountant","licencenum","mintype","reportyear","manager","lpname","hrmanager","dansname","licencenumbers","companyname","orderyear"};
			String[] vals2 = {"givname","geologyst","accountant","licnum","mintype","year","manager","holdername","hrmanager","dansname","licnum","companyname","orderyear"};
			DataNotesInfo info = (DataNotesInfo) dao.getHQLResult("from DataNotesInfo t where t.planid="+plan+" and t.noteid = " + noteid, "current");
			if (info != null){
				dao.deleteById("DataNotesInfo", info.getId(), null);
			}
			info = new DataNotesInfo();
			info.setPlanid((long)plan);
			info.setNoteid((long)noteid);

			for(int rowIterator=sheet.getFirstRowNum();rowIterator<=sheet.getLastRowNum();rowIterator++){
				Row currentRow = sheet.getRow(rowIterator);
				if (currentRow != null){
					for(int colIterator=0;colIterator<26;colIterator++){
						Cell currentCell = currentRow.getCell(colIterator);
						if (currentCell != null && currentCell.getCellType() == Cell.CELL_TYPE_STRING && (rowIterator-1 >= 0)){
							for(int j=0;j<vals.length;j++){
								if (currentCell.getStringCellValue().equalsIgnoreCase(vals[j])){
									info.setField(vals2[j], String.valueOf(getParametersCell(rowIterator-1, colIterator, evaluator, sheet)));
								}
							}
						}
					}
				}
			}
			
			dao.PeaceCrud(info, "DataNotesInfo", "save", (long) 0, 0, 0, null);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public Object getParametersCell(int row, int column, FormulaEvaluator evaluator, Sheet sheet){
		Row rowData = sheet.getRow(row);
		if (rowData != null){
			Cell cellData = rowData.getCell(column);
			if (cellData != null){
				return getCellValue(evaluator, cellData);
			}
		}
		return null;
	}

	@RequestMapping(value="/upload/{plan}", method = RequestMethod.POST)
	public String uploadForm(@PathVariable int plan, HttpServletRequest req, @RequestParam("files") MultipartFile files, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JSONObject result = new JSONObject();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			try {
				MultipartFile mfile = (MultipartFile)files;
				if (mfile != null) {
					String appPath = req.getSession().getServletContext().getRealPath("");

					String SAVE_DIR = MramApplication.ROOT;

					Date d1 = new Date();
					SimpleDateFormat df = new SimpleDateFormat("MM-dd-YYYY");
					String special = df.format(d1);

                    SimpleDateFormat df1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                    String timestamp = df1.format(d1);

					/*LnkPlanAttachedFiles lr=(LnkPlanAttachedFiles) dao.getHQLResult("from LnkPlanAttachedFiles t where t.noteid="+obj.getLong("id")+" and t.expid="+obj.getLong("planid")+"", "current");

					if(lr!=null){
						if(lr.getLutFormNotes().getIsform()==1){
							String appPaths = req.getSession().getServletContext().getRealPath("");
							String delPath = appPaths + lr.getAttachfiletype();
							System.out.println(lr.getLutFormNotes().getIsform() + " is deleted!"); 
							File file = new File(delPath);
							if(file.exists()){
								System.out.println(file.getName() + " bn!");
							}else{
								System.out.println("bhgu.");
							}

							if(file.delete()){
								System.out.println(file.getName() + " is deleted!");
							}else{
								System.out.println("Delete operation is failed.");
							}
							dao.PeaceCrud(lr, "LnkPlanAttachedFiles", "delete", lr.getId(), 0, 0, null);
						}
					}*/


					UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id='"+plan+"'", "current");
					List<LnkPlanAttachedFiles> rs=(List<LnkPlanAttachedFiles>) dao.getHQLResult("from LnkPlanAttachedFiles t ", "list");;


					String path = appPath + File.separator + SAVE_DIR+ File.separator  +loguser.getLpreg()+ File.separator +special;

					File folder = new File(path);
					if(!folder.exists()){
						folder.mkdirs();
					}


					String fpath = path + File.separator +"("+rs.size()+")"+mfile.getOriginalFilename();
					File logoorgpath = new File(fpath);

					if(!logoorgpath.exists()){
						mfile.transferTo(logoorgpath);
					}
					else{
						result.put("status", false);
					}
					
					String path1 = File.separator + SAVE_DIR+ File.separator +loguser.getLpreg()+ File.separator +special+ File.separator +"("+rs.size()+")"+mfile.getOriginalFilename();
					FileInputStream fis = new FileInputStream(logoorgpath);
					String ext = FilenameUtils.getExtension(path1);
					if (ext.equalsIgnoreCase("xlsm") && an != null){
						Workbook workbook = new XSSFWorkbook(fis);
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

						for(int i=0;i<workbook.getNumberOfSheets();i++){
							Sheet sheet = workbook.getSheetAt(i);
							if (!workbook.isSheetHidden(i)){
								if (sheet.getSheetName().equalsIgnoreCase("geoplan1")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 126 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0){
											dao.PeaceCrud(null, "DataExcelGeoplan1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 126 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										}
										else{
											dao.PeaceCrud(null, "DataExcelGeoplan1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 126 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 126);
										createAttach(plan, 126, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for(int rowIterator=12; rowIterator<=sheet.getLastRowNum();rowIterator++){
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null){
												if ((currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(2) != null && currentRow.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(3) != null && currentRow.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK)){
													DataExcelGeoplan1 report = new DataExcelGeoplan1();
													report.setNoteid((long)126);
													report.setPlanid((long)plan);
													if (transitions != null && transitions.size() > 0){
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													}
													else{
														report.setIstodotgol(false);
													}
													report.setOrdernumber((long)rowIterator-11);
													for(int cellIterator = 1; cellIterator<=7; cellIterator++){
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null){
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null){
																if (cellIterator == 1){
																	data1 = String.valueOf(resultObj);
																}
																if (cellIterator >= 1 && cellIterator <= 4){
																	report.setField("data"+cellIterator, String.valueOf(resultObj));
																}
																else{
																	if (resultObj instanceof Double){
																		report.setField("data"+cellIterator, resultObj);
																	}
																	else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0){
																		report.setField("data"+cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
															else if (cellIterator == 1){
																report.setData1(data1);
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeoplan1", "save", (long) 0, 0, 0, null);
												}
												else{
													break;
												}
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("georep1")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 47 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelGeorep1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 47 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelGeorep1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 47 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 47);
										createAttach(plan, 47, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for (int rowIterator = 12; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if ((currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(2) != null && currentRow.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(3) != null && currentRow.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK)) {
													DataExcelGeorep1 report = new DataExcelGeorep1();
													report.setNoteid((long) 47);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 11);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 9; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {

																if (cellIterator == 1) {
																	data1 = String.valueOf(resultObj);

																}
																if (cellIterator >= 1 && cellIterator <= 4) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
															if (cellIterator == 1) {
																report.setData1(data1);
															}
														}
													}
													System.out.println();
													dao.PeaceCrud(report, "DataExcelGeorep1", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("georep2")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 48 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelGeorep2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 48 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelGeorep2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 48 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 48);
										createAttach(plan, 48, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if ((currentRow.getCell(0) != null && currentRow.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(2) != null && currentRow.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK)) {
													DataExcelGeorep2 report = new DataExcelGeorep2();
													report.setNoteid((long) 48);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 9);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator <= 18; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 0) {
																	data1 = String.valueOf(resultObj);
																}
																if (cellIterator >= 0 && cellIterator <= 2) {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															} else if (cellIterator == 0) {
																report.setData1(data1);
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep2", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("georep4")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 50 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelGeorep4", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 50 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelGeorep4", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 50 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 50);
										createAttach(plan, 50, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if ((currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(2) != null && currentRow.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK)) {
													DataExcelGeorep4 report = new DataExcelGeorep4();
													report.setNoteid((long) 50);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 10);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 7; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator != 3) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep4", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("georep6")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 52 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelGeorep6", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 52 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelGeorep6", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 52 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 52);
										createAttach(plan, 52, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) {
													DataExcelGeorep6 report = new DataExcelGeorep6();
													report.setNoteid((long) 52);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 10);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 7; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator != 3) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep6", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("georep8")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 54 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelGeorep8", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 54 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelGeorep8", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 54 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 54);
										createAttach(plan, 54, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) {
													DataExcelGeorep8 report = new DataExcelGeorep8();
													report.setNoteid((long) 54);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 9);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 8; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 1 || cellIterator == 2) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep8", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("georep9")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 55 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelGeorep9", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 55 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelGeorep9", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 55 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 55);
										createAttach(plan, 55, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 12; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) {
													DataExcelGeorep9 report = new DataExcelGeorep9();
													report.setNoteid((long) 55);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 11);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 7; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																//if (cellIterator == 1 || cellIterator == 2){
																report.setField("data" + cellIterator, String.valueOf(resultObj));
																/*}
															else if (resultObj instanceof Double){
																report.setField("data"+cellIterator, resultObj);
															}*/
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep9", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("georep10")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 56 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelGeorep10", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 56 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelGeorep10", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 56 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 56);
										createAttach(plan, 56, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 13; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if ((currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(2) != null && currentRow.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK)) {
													DataExcelGeorep10 report = new DataExcelGeorep10();
													report.setNoteid((long) 56);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 11);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 6; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 1 || cellIterator == 2) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep10", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("georep3")){
									int lastRow = 0;
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 49 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelGeorep3", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 49 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelGeorep3", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 49 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 49);
										createAttach(plan, 49, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 14; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if ((currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(2) != null && currentRow.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK)) {
													DataExcelGeorep3 report = new DataExcelGeorep3();
													report.setNoteid((long) 49);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 13);
													report.setType("TSOONOG");
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 13; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator < 3 || cellIterator > 9) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep3", "save", (long) 0, 0, 0, null);
												} else {
													lastRow = rowIterator;
													break;
												}
											}
										}
										for (int rowIterator = lastRow + 5, iterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, iterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if ((currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(2) != null && currentRow.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK)) {
													DataExcelGeorep3 report = new DataExcelGeorep3();
													report.setNoteid((long) 49);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) iterator);
													report.setType("SUVAG");
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 13; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator < 3 || cellIterator > 9) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep3", "save", (long) 0, 0, 0, null);
												} else {
													lastRow = rowIterator;
													break;
												}
											}
										}
										for (int rowIterator = lastRow + 5, iterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, iterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if ((currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(2) != null && currentRow.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK)) {
													DataExcelGeorep3 report = new DataExcelGeorep3();
													report.setNoteid((long) 49);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) iterator);
													report.setType("SHURF");
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 13; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator < 3 || cellIterator > 9) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep3", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
										}
									}


								}
								else if (sheet.getSheetName().equalsIgnoreCase("georep5")){
									int lastRow = 0;
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 51 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelGeorep5", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 51 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelGeorep5", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 51 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 51);
										createAttach(plan, 51, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 13; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_STRING) {
													DataExcelGeorep5 report = new DataExcelGeorep5();
													report.setNoteid((long) 51);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 12);
													report.setType("1");
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 16; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator <= 3) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep5", "save", (long) 0, 0, 0, null);
												} else {
													lastRow = rowIterator;
													break;
												}
											}
										}
										for (int rowIterator = lastRow + 2, iterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, iterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_STRING) {
													DataExcelGeorep5 report = new DataExcelGeorep5();
													report.setNoteid((long) 51);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) iterator);
													report.setType("2");
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 16; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator <= 3) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep5", "save", (long) 0, 0, 0, null);
												} else {
													lastRow = rowIterator;
													break;
												}
											}
										}
										for (int rowIterator = lastRow + 2, iterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, iterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_STRING) {
													DataExcelGeorep5 report = new DataExcelGeorep5();
													report.setNoteid((long) 51);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) iterator);
													report.setType("3");
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 16; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator <= 3) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep5", "save", (long) 0, 0, 0, null);
												} else {
													lastRow = rowIterator;
													break;
												}
											}
										}
										for (int rowIterator = lastRow + 2, iterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, iterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_STRING) {
													DataExcelGeorep5 report = new DataExcelGeorep5();
													report.setNoteid((long) 51);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) iterator);
													report.setType("4");
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 16; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator <= 3) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep5", "save", (long) 0, 0, 0, null);
												} else {
													lastRow = rowIterator;
													break;
												}
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("georep7")){
									int lastRow = 0;
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 347 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelGeorep7", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 347 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelGeorep7", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 347 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 347);
										createAttach(plan, 347, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 13; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_STRING) {
													DataExcelGeorep7 report = new DataExcelGeorep7();
													report.setNoteid((long) 347);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 12);
													report.setType("1");
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 16; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator < 3 || cellIterator == 12) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep7", "save", (long) 0, 0, 0, null);
												} else {
													lastRow = rowIterator;
													break;
												}
											}
										}
										for (int rowIterator = lastRow + 2, iterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, iterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_STRING) {
													DataExcelGeorep7 report = new DataExcelGeorep7();
													report.setNoteid((long) 347);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) iterator);
													report.setType("2");
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 16; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator < 3 || cellIterator == 12) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep7", "save", (long) 0, 0, 0, null);
												} else {
													lastRow = rowIterator;
													break;
												}
											}
										}
										for (int rowIterator = lastRow + 2, iterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, iterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_STRING) {
													DataExcelGeorep7 report = new DataExcelGeorep7();
													report.setNoteid((long) 347);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) iterator);
													report.setType("3");
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 16; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator < 3 || cellIterator == 12) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep7", "save", (long) 0, 0, 0, null);
												} else {
													lastRow = rowIterator;
													break;
												}
											}
										}
										for (int rowIterator = lastRow + 2, iterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, iterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if (currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_STRING) {
													DataExcelGeorep7 report = new DataExcelGeorep7();
													report.setNoteid((long) 347);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) iterator);
													report.setType("4");
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 16; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator < 3 || cellIterator == 12) {
																	report.setField("data" + cellIterator, String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelGeorep7", "save", (long) 0, 0, 0, null);
												} else {
													lastRow = rowIterator;
													break;
												}
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-2")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 86 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 86 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 86 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 86);
										createAttach(plan, 86, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												if ((currentRow.getCell(1) != null && currentRow.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(2) != null && currentRow.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK) || (currentRow.getCell(3) != null && currentRow.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK)) {
													DataExcelMinrep2 report = new DataExcelMinrep2();
													report.setNoteid((long) 86);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 9);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator <= 7; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 0) {
																	data1 = String.valueOf(resultObj);
																}
																if (cellIterator != 5 && cellIterator != 6) {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															} else if (cellIterator == 0) {
																report.setData1(data1);
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep2", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
											System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-3a")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 71 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep3a", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 71 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep3a", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 71 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 71);
										createAttach(plan, 71, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for (int rowIterator = 12; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 22; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep3a report = new DataExcelMinrep3a();
													report.setNoteid((long) 71);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 11);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 22; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep3a", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-3b")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 394 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep3b", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 394 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep3b", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 394 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 394);
										createAttach(plan, 394, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for (int rowIterator = 9; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 51; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep3b report = new DataExcelMinrep3b();
													report.setNoteid((long) 394);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 8);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 51; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																report.setField("data" + cellIterator, String.valueOf(resultObj));
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep3b", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-4-1")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 74;
									}
									else if (an.getLictype() == 3){
										noteid = 565;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {

										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep4_1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep4_1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 23; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep4_1 report = new DataExcelMinrep4_1();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 10);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 23; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator == 5 || cellIterator == 6 || cellIterator == 11 || cellIterator == 12 || cellIterator == 14 || cellIterator == 15 || cellIterator == 17 || cellIterator == 18 || cellIterator == 20 || cellIterator == 21)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep4_1", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-4-2")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 397;
									}
									else if (an.getLictype() == 3){
										noteid = 566;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep4_2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep4_2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 11; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep4_2 report = new DataExcelMinrep4_2();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 10);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 11; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator == 5 || cellIterator == 6 || cellIterator == 8 || cellIterator == 9)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep4_2", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-2-3")){
									int noteid = 0;
									if (an.getLictype() == 2){
										//noteid = 397;
									}
									else if (an.getLictype() == 3){
										noteid = 571;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											/*dao.PeaceCrud(null, "DataExcelMinrep4_2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep4_2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");*/
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-5")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 77;
									}
									else if (an.getLictype() == 3){
										noteid = 564;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep5", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep5", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 11; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep5 report = new DataExcelMinrep5();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 10);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 11; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator == 6 || cellIterator == 7 || cellIterator == 9 || cellIterator == 10)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep5", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-6-1")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 80;
									}
									else if (an.getLictype() == 3){
										noteid = 556;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep6_1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep6_1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for (int rowIterator = 13; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 6; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep6_1 report = new DataExcelMinrep6_1();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 12);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 6; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 3 || cellIterator == 4) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep6_1", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-6-2")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 396;
									}
									else if (an.getLictype() == 3){
										noteid = 555;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep6_2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep6_2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for (int rowIterator = 13; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 12; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep6_2 report = new DataExcelMinrep6_2();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 12);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 12; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator >= 5 && cellIterator <= 10) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep6_2", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-7")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 83;
									}
									else if (an.getLictype() == 3){
										noteid = 563;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep7", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep7", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										String data1 = null;
										for (int rowIterator = 12; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 15; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep7 report = new DataExcelMinrep7();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 11);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 15; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator >= 7 && cellIterator <= 10) || (cellIterator >= 13 && cellIterator <= 14)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep7", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-8")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 430;
									}
									else if (an.getLictype() == 3){
										noteid = 562;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep8_1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep8_1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);

										String data1 = null;
										for (int rowIterator = 15; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 22; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {

													DataExcelMinrep8_1 report = new DataExcelMinrep8_1();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 14);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 22; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator >= 7 && cellIterator <= 11) || (cellIterator >= 13 && cellIterator <= 15)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep8_1", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								/*else if (sheet.getSheetName().equalsIgnoreCase("form-8-2")){
									dao.PeaceCrud(null, "DataExcelMinrep8_2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 395");
									saveInfos(plan, sheet, evaluator, 395);
									createAttach(plan, 395, path1, mfile.getOriginalFilename(), ext, timestamp);
									
									dao.PeaceCrud(null, "DataExcelMinrep8_2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 429");
									saveInfos(plan, sheet, evaluator, 429);
									createAttach(plan, 429, path1, mfile.getOriginalFilename(), ext, timestamp);
									
									String data1 = null;
									for(int rowIterator=14; rowIterator<=sheet.getLastRowNum();rowIterator++){
										Row currentRow = sheet.getRow(rowIterator);
										if (currentRow != null){
											Boolean isEmpty = true;
											for(int checkI = 1;checkI<=17;checkI++){
												if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK){
													isEmpty = false;
												}
											}
											if (!isEmpty){
												DataExcelMinrep8_2 report = new DataExcelMinrep8_2();
												report.setNoteid((long)395);
												report.setPlanid((long)plan);
												report.setOrdernumber((long)rowIterator-13);
												for(int cellIterator = 1; cellIterator<=17; cellIterator++){
													Cell currentCell = currentRow.getCell(cellIterator);
													if (currentCell != null){
														Object resultObj = getCellValue(evaluator, currentCell);
														if (resultObj != null){
															if (((cellIterator >= 7 && cellIterator <= 11) || (cellIterator >= 13 && cellIterator <= 15)) && (resultObj instanceof Double)){
																report.setField("data"+(cellIterator), resultObj);
															}
															else{
																report.setField("data"+(cellIterator), String.valueOf(resultObj));
															}
														}
													}
												}
												dao.PeaceCrud(report, "DataExcelMinrep8_2", "save", (long) 0, 0, 0, null);
												
												report = new DataExcelMinrep8_2();
												report.setNoteid((long)429);
												report.setPlanid((long)plan);
												report.setOrdernumber((long)rowIterator-13);
												for(int cellIterator = 1; cellIterator<=17; cellIterator++){
													Cell currentCell = currentRow.getCell(cellIterator);
													if (currentCell != null){
														Object resultObj = getCellValue(evaluator, currentCell);
														if (resultObj != null){
															if (((cellIterator >= 7 && cellIterator <= 11) || (cellIterator >= 13 && cellIterator <= 15)) && (resultObj instanceof Double)){
																report.setField("data"+(cellIterator), resultObj);
															}
															else{
																report.setField("data"+(cellIterator), String.valueOf(resultObj));
															}
														}
													}
												}
												dao.PeaceCrud(report, "DataExcelMinrep8_2", "save", (long) 0, 0, 0, null);
												
											}
											else{
												break;
											}
										}
										else{
											break;
										}
										//System.out.println();
									}
								}*/
								else if (sheet.getSheetName().equalsIgnoreCase("form-9")){

									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 104 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep9", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 104 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep9", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 104 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 104);
										createAttach(plan, 104, path1, mfile.getOriginalFilename(), ext, timestamp);
										int lastRow = 0;
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 15; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep9 report = new DataExcelMinrep9();
													report.setNoteid((long) 104);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 10);
													report.setType((long) 1);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 15; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator >= 4 && cellIterator <= 11) || (cellIterator >= 14 && cellIterator <= 15)) {
																	System.out.println(cellIterator + "\t" + String.valueOf(resultObj));
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep9", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
											lastRow = rowIterator;
										}
										for (int rowIterator = lastRow + 5, loopIterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, loopIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 7; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep9 report = new DataExcelMinrep9();
													report.setNoteid((long) 104);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) loopIterator);
													report.setType((long) 2);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 7; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator >= 4 && cellIterator <= 7) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep9", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											lastRow = rowIterator;
										}
										for (int rowIterator = lastRow + 6, loopIterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, loopIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 5; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelMinrep9 report = new DataExcelMinrep9();
													report.setNoteid((long) 104);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) loopIterator);
													report.setType((long) 3);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 5; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 4) {
																	report.setField("data13", String.valueOf(resultObj));
																} else if (cellIterator == 5) {
																	report.setField("data12", String.valueOf(resultObj));
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep9", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-10")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 107;
									}
									else if (an.getLictype() == 3){
										noteid = 560;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep10", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep10", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);

										for (int rowIterator = 12; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 22; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep10 report = new DataExcelMinrep10();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 11);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 22; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator == 4 || cellIterator == 5 || cellIterator == 7 || cellIterator == 8 || cellIterator == 10 || cellIterator == 11) || (cellIterator >= 14 && cellIterator <= 21)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	if (resultObj != null) {
																		report.setField("data" + (cellIterator), String.valueOf(resultObj));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep10", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-11")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 110;
									}
									else if (an.getLictype() == 3){
										noteid = 559;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep11", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep11", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);

										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 10; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelMinrep11 report = new DataExcelMinrep11();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 9);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 10; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator >= 3 && cellIterator <= 4) || (cellIterator >= 7 && cellIterator <= 8)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0 && String.valueOf(resultObj).indexOf("-") < 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep11", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-12")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 113;
									}
									else if (an.getLictype() == 3){
										noteid = 558;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep12", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep12", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 8; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelMinrep12 report = new DataExcelMinrep12();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 9);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 8; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator >= 3 && cellIterator <= 6) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep12", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-13")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 116;
									}
									else if (an.getLictype() == 3){
										noteid = 557;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep13", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep13", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 5; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelMinrep13 report = new DataExcelMinrep13();
													report.setNoteid((long) 116);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 9);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 5; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 4) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep13", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-14")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 89;
									}
									else if (an.getLictype() == 3){
										noteid = 554;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep14", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep14", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 8; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 6; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelMinrep14 report = new DataExcelMinrep14();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 7);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 6; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 3 || cellIterator == 4) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep14", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-15")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 92;
									}
									else if (an.getLictype() == 3){
										noteid = 553;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep15", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep15", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 9; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 6; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelMinrep15 report = new DataExcelMinrep15();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 8);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 6; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 3 || cellIterator == 4) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep15", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-16")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 95;
									}
									else if (an.getLictype() == 3){
										noteid = 552;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep16", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep16", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 9; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 7; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelMinrep16 report = new DataExcelMinrep16();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 8);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 7; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 5 || cellIterator == 6) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep16", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-17")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 408 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep17", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 408 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep17", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 408 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 408);
										createAttach(plan, 408, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 19; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelMinrep17 report = new DataExcelMinrep17();
													report.setNoteid((long) 408);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 9);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 19; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																//System.out.print("data"+(cellIterator+1) + ":" + resultObj.getClass() + "\t");
																if (cellIterator == 2 || cellIterator == 4 || cellIterator == 10 || cellIterator == 12 || cellIterator == 13 || cellIterator == 16 || cellIterator == 17) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep17", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-18")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 119;
									}
									else if (an.getLictype() == 3){
										noteid = 551;
									}
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep18", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep18", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 10; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelMinrep18 report = new DataExcelMinrep18();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 10);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 10; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																//System.out.print("data"+(cellIterator+1) + ":" + resultObj.getClass() + "\t");
																if (cellIterator == 7) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep18", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("form-19")){
									int noteid = 0;
									if (an.getLictype() == 2){
										noteid = 398;
									}
									else if (an.getLictype() == 3){
										noteid = 550;
									}

									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelMinrep19", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelMinrep19", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = "+noteid+" and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, noteid);
										createAttach(plan, noteid, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 9; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 13; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelMinrep19 report = new DataExcelMinrep19();
													report.setNoteid((long) noteid);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 8);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 10; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator >= 3 && cellIterator <= 11) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelMinrep19", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-2")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 70 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 70 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 70 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 70);
										createAttach(plan, 70, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 4; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep2 report = new DataExcelCoalrep2();
													report.setNoteid((long) 70);
													report.setPlanid((long) plan);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													report.setOrdernumber((long) rowIterator - 9);
													for (int cellIterator = 0; cellIterator < 4; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep2", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-2.1")){
									//dao.PeaceCrud(null, "DataExcelMinrep19", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 398");
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 404 and t.planid = " + plan, "list");
									saveInfos(plan, sheet, evaluator, 404);
									createAttach(plan, 404, path1, mfile.getOriginalFilename(), ext, timestamp);
									for(int rowIterator=13; rowIterator<=sheet.getLastRowNum();rowIterator++){
										Row currentRow = sheet.getRow(rowIterator);
										if (currentRow != null){
											Boolean isEmpty = true;
											for(int checkI = 0;checkI<6;checkI++){
												if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK){
													isEmpty = false;
												}
											}
											if (!isEmpty){
												DataExcelCoalrep2_1 report = new DataExcelCoalrep2_1();
												report.setNoteid((long)404);
												report.setPlanid((long)plan);
												if (transitions != null && transitions.size() > 0){
													report.setIstodotgol(transitions.get(0).getIstodotgol());
												}
												else{
													report.setIstodotgol(false);
												}
												report.setOrdernumber((long)rowIterator-12);
												for(int cellIterator = 0; cellIterator<6; cellIterator++){
													Cell currentCell = currentRow.getCell(cellIterator);
													if (currentCell != null){
														Object resultObj = getCellValue(evaluator, currentCell);
														if (resultObj != null){
															report.setField("data"+(cellIterator+1), String.valueOf(resultObj));
														}
													}
												}
												dao.PeaceCrud(report, "DataExcelCoalrep2_1", "save", (long) 0, 0, 0, null);
											}
											else{
												break;
											}
										}
										else{
											break;
										}
										//System.out.println();
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-3")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 405 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep3", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 405 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep3", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 405 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 405);
										createAttach(plan, 405, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 17; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 16; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep3 report = new DataExcelCoalrep3();
													report.setNoteid((long) 405);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 16);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 16; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator <= 6) {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep3", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-4")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 73 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep4", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 73 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep4", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 73 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 73);
										createAttach(plan, 73, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 14; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 18; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep4 report = new DataExcelCoalrep4();
													report.setNoteid((long) 73);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 13);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 18; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator < 2) {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep4", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-5")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 76 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep5", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 76 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep5", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 76 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 76);
										createAttach(plan, 76, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 11; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep5 report = new DataExcelCoalrep5();
													report.setNoteid((long) 76);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 10);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 11; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator <= 5 || cellIterator == 8 || cellIterator == 11) {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																} else {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep5", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-6-1")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 79 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep6_1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 79 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep6_1", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 79 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 79);
										createAttach(plan, 79, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 13; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 6; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelCoalrep6_1 report = new DataExcelCoalrep6_1();
													report.setNoteid((long) 79);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 12);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 6; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 3 || cellIterator == 4) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep6_1", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-6-2")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 406 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep6_2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 406 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep6_2", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 406 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 406);
										createAttach(plan, 406, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 13; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 12; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelCoalrep6_2 report = new DataExcelCoalrep6_2();
													report.setNoteid((long) 406);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 12);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 12; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator >= 5 && cellIterator <= 10) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep6_2", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-7")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 82 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep7", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 82 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep7", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 82 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 82);
										createAttach(plan, 82, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 13; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 15; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelCoalrep7 report = new DataExcelCoalrep7();
													report.setNoteid((long) 82);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 12);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 15; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator >= 7 && cellIterator <= 11) || (cellIterator >= 13 && cellIterator <= 14)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep7", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-8")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 428 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep8", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 428 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep8", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 428 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 428);
										createAttach(plan, 428, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 16; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 17; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
													//System.out.println(checkI + " => " + isEmpty);
												}
												if (!isEmpty) {
													DataExcelCoalrep8 report = new DataExcelCoalrep8();
													report.setNoteid((long) 428);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 15);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 17; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															//System.out.print(cellIterator + ":\t" + currentCell.getCellType() + "\t");
															//System.out.print(cellIterator + ":\t" + ((currentCell.getCellType() == Cell.CELL_TYPE_STRING) ? currentCell.getStringCellValue() : ((currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? currentCell.getNumericCellValue() : "")) + "\t");
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator >= 7 && cellIterator <= 11) || (cellIterator >= 13 && cellIterator <= 15)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep8", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-9")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 102 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep9", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 102 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep9", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 102 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 102);
										createAttach(plan, 102, path1, mfile.getOriginalFilename(), ext, timestamp);
										int lastRow = 0;
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 15; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep9 report = new DataExcelCoalrep9();
													report.setNoteid((long) 102);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 10);
													report.setType((long) 1);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 15; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator >= 4 && cellIterator <= 11) || (cellIterator >= 14 && cellIterator <= 15)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep9", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											lastRow = rowIterator;
										}
										for (int rowIterator = lastRow + 5, loopIterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, loopIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 7; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep9 report = new DataExcelCoalrep9();
													report.setNoteid((long) 102);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) loopIterator);
													report.setType((long) 2);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 7; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator >= 4 && cellIterator <= 7) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep9", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											lastRow = rowIterator;
										}
										for (int rowIterator = lastRow + 6, loopIterator = 1; rowIterator <= sheet.getLastRowNum(); rowIterator++, loopIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 5; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep9 report = new DataExcelCoalrep9();
													report.setNoteid((long) 102);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) loopIterator);
													report.setType((long) 3);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 5; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 4) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else if (cellIterator == 5) {
																	report.setField("data12", String.valueOf(resultObj));
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep9", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-10")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 106 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep10", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 106 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep10", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 106 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 106);
										createAttach(plan, 106, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 13; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 22; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep10 report = new DataExcelCoalrep10();
													report.setNoteid((long) 106);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 12);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 22; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator == 4 || cellIterator == 5 || cellIterator == 7 || cellIterator == 10 || cellIterator == 11) || (cellIterator >= 14 && cellIterator <= 21)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	if (resultObj != null) {
																		report.setField("data" + (cellIterator), String.valueOf(resultObj));
																	}
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep10", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-11")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 109 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep11", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 109 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep11", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 109 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 109);
										createAttach(plan, 109, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 10; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep11 report = new DataExcelCoalrep11();
													report.setNoteid((long) 109);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 9);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 10; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if ((cellIterator >= 3 && cellIterator <= 4) || (cellIterator >= 7 && cellIterator <= 8)) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0 && String.valueOf(resultObj).indexOf("-") < 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep11", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-12")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 112 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep12", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 112 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep12", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 112 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 112);
										createAttach(plan, 112, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 8; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep12 report = new DataExcelCoalrep12();
													report.setNoteid((long) 112);
													report.setPlanid((long) plan);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													report.setOrdernumber((long) rowIterator - 9);
													for (int cellIterator = 0; cellIterator < 8; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator >= 3 && cellIterator <= 6) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep12", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-13")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 115 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep13", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 115 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep13", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 115 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 115);
										createAttach(plan, 115, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 5; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep13 report = new DataExcelCoalrep13();
													report.setNoteid((long) 115);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 9);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 5; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 4) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep13", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-14")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 88 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep14", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 88 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep14", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 88 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 88);
										createAttach(plan, 88, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 6; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep14 report = new DataExcelCoalrep14();
													report.setNoteid((long) 88);
													report.setPlanid((long) plan);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													report.setOrdernumber((long) rowIterator - 10);
													for (int cellIterator = 0; cellIterator < 6; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 3 || cellIterator == 4) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep14", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-15")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 91 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep15", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 91 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep15", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 91 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 91);
										createAttach(plan, 91, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 10; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 6; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep15 report = new DataExcelCoalrep15();
													report.setNoteid((long) 91);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 9);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 6; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 3 || cellIterator == 4) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep15", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-16")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 94 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep16", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 94 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep16", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 94 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 94);
										createAttach(plan, 94, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 1; checkI <= 7; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep16 report = new DataExcelCoalrep16();
													report.setNoteid((long) 94);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 10);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 1; cellIterator <= 7; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator == 5 || cellIterator == 6) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + cellIterator, resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + cellIterator, Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep16", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-17")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 118 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep17", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 118 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep17", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 118 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 118);
										createAttach(plan, 118, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 13; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 13; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep17 report = new DataExcelCoalrep17();
													report.setNoteid((long) 118);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 12);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 13; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																//System.out.print("data"+(cellIterator+1) + ":" + resultObj.getClass() + "\t");
																if (cellIterator == 3 || cellIterator == 7 || cellIterator == 8 || cellIterator == 10 || cellIterator == 11) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep17", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-18")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 120 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep18", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 120 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep18", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 120 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 120);
										createAttach(plan, 120, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 11; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 10; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep18 report = new DataExcelCoalrep18();
													report.setNoteid((long) 120);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 10);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 10; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																//System.out.print("data"+(cellIterator+1) + ":" + resultObj.getClass() + "\t");
																if (cellIterator == 7) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep18", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
								else if (sheet.getSheetName().equalsIgnoreCase("cform-19")){
									List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = 122 and t.planid = " + plan, "list");
									if (checkFormDatas(transitions, plan, sheet, evaluator)) {
										if (transitions != null && transitions.size() > 0) {
											dao.PeaceCrud(null, "DataExcelCoalrep19", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 122 and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
										} else {
											dao.PeaceCrud(null, "DataExcelCoalrep19", "multidelete", (long) plan, 0, 0, "where planid = " + plan + " and noteid = 122 and istodotgol = 0");
										}
										saveInfos(plan, sheet, evaluator, 122);
										createAttach(plan, 122, path1, mfile.getOriginalFilename(), ext, timestamp);
										for (int rowIterator = 14; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
											Row currentRow = sheet.getRow(rowIterator);
											if (currentRow != null) {
												Boolean isEmpty = true;
												for (int checkI = 0; checkI < 13; checkI++) {
													if (currentRow.getCell(checkI) != null && currentRow.getCell(checkI).getCellType() != Cell.CELL_TYPE_BLANK) {
														isEmpty = false;
													}
												}
												if (!isEmpty) {
													DataExcelCoalrep19 report = new DataExcelCoalrep19();
													report.setNoteid((long) 122);
													report.setPlanid((long) plan);
													report.setOrdernumber((long) rowIterator - 8);
													if (transitions != null && transitions.size() > 0) {
														report.setIstodotgol(transitions.get(0).getIstodotgol());
													} else {
														report.setIstodotgol(false);
													}
													for (int cellIterator = 0; cellIterator < 10; cellIterator++) {
														Cell currentCell = currentRow.getCell(cellIterator);
														if (currentCell != null) {
															Object resultObj = getCellValue(evaluator, currentCell);
															if (resultObj != null) {
																if (cellIterator >= 3 && cellIterator <= 11) {
																	if (resultObj instanceof Double) {
																		report.setField("data" + (cellIterator + 1), resultObj);
																	} else if (resultObj instanceof String && String.valueOf(resultObj).length() > 0) {
																		report.setField("data" + (cellIterator + 1), Double.parseDouble(String.valueOf(resultObj)));
																	}
																} else {
																	report.setField("data" + (cellIterator + 1), String.valueOf(resultObj));
																}
															}
														}
													}
													dao.PeaceCrud(report, "DataExcelCoalrep19", "save", (long) 0, 0, 0, null);
												} else {
													break;
												}
											} else {
												break;
											}
											//System.out.println();
										}
									}
								}
							}
							result.put("status", true);
						}
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			result.put("status", "session-error");
		}
		return result.toString();
	}

	@RequestMapping(value="/form/{plan}/{divisionid}/{noteid}", method = RequestMethod.GET)
	public String downloadForm(@PathVariable int plan, @PathVariable int divisionid,HttpServletResponse response, HttpServletRequest req, @PathVariable Long noteid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			try {
				ServletOutputStream out = response.getOutputStream();

				String appPath = req.getServletContext().getRealPath("");

				AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+plan+"", "current");

				SubLegalpersons sbl= (SubLegalpersons) dao.getHQLResult("from SubLegalpersons t where t.lpReg='"+an.getLpReg()+"'", "current");

				SubLicenses sblic= (SubLicenses) dao.getHQLResult("from SubLicenses t where t.lpReg='"+an.getLpReg()+"' and t.licenseXB like '%" + an.getLicenseXB() + "%'", "current");
				
				List<SubLicenses> licenselist= (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.lpReg='"+an.getLpReg() + "'", "list");
				
				LutFormNotes noteObj=(LutFormNotes) dao.getHQLResult("from LutFormNotes t where t.id="+noteid+"", "current");
				String sheetname = "";
				
				if (an != null && divisionid == 3 && an.getReporttype() == 3 && ((noteid > 0 && noteObj != null) || (noteid == 0))){
					
					if (noteid == 126){
						sheetname = "geoplan1";
					}
					
					RegReportReq rreq= (RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+an.getReqid()+"'", "current");
					LnkReqAnn ann=null;
					if(rreq.getLnkReqAnns().size()>0){
						ann=rreq.getLnkReqAnns().get(0);
					}

					String mintype = "";
					if(an.getMinid()!=0){
						if(ann!=null){   
							LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+ann.getDeposidid()+"'", "current");
							mintype = String.valueOf(qwe.getPnum());
						}
						else if (an.getDepositid() != null){
							LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+an.getDepositid()+"'", "current");
							mintype = String.valueOf(qwe.getDepositnamemon());
						}
						else{
							RegReportReq reqqq = an.getRegReportReq();
							if (reqqq != null){
								LutMinGroup qwe= (LutMinGroup) dao.getHQLResult("from LutMinGroup t where t.id='"+reqqq.getGroupid()+"'", "current");
								if (qwe != null){
									mintype = qwe.getGroupname();
								}
							}
						}
					}

					FileInputStream fis = null;

					File formfile = new File(appPath+"/assets/reporttemplate/GeoPlan.xlsm");

					fis = new FileInputStream(formfile);
					Workbook workbook = new XSSFWorkbook(fis);
					
					if (noteid > 0){
						for(int i=0;i<workbook.getNumberOfSheets();i++){
							Sheet sheet = workbook.getSheetAt(i);
							if (sheet.getSheetName().equalsIgnoreCase(sheetname)){
								workbook.setActiveSheet(i);
								workbook.setSelectedTab(i);
							}
						}
					}	
					for(int i=0;i<workbook.getNumberOfSheets();i++){
						Sheet sheet = workbook.getSheetAt(i);
						
						if ((noteid > 0) && !sheet.getSheetName().equalsIgnoreCase(sheetname)){
							workbook.setSheetHidden(i, true);
						}
						
						for(int rowIterator=sheet.getFirstRowNum();rowIterator<=sheet.getLastRowNum();rowIterator++){
							Row currentRow = sheet.getRow(rowIterator);

							if (currentRow != null){
								for(int colIterator=0;colIterator<=currentRow.getLastCellNum();colIterator++){
									Cell currentCell = currentRow.getCell(colIterator);
									if (currentCell != null && currentCell.getCellType() == Cell.CELL_TYPE_STRING && (rowIterator-1 >= 0)){
										Row dataRow = sheet.getRow(rowIterator-1);
										if (dataRow != null){
											Cell dataCell = dataRow.getCell(colIterator);
											if (dataCell == null){
												dataCell =dataRow.createCell(colIterator);
											}
											if (dataCell != null){
												if (currentCell.getStringCellValue().equalsIgnoreCase("givname")){
													dataCell.setCellValue((String) sbl.getGivName());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("licencenum")){
													dataCell.setCellValue((String) an.getLicenseXB());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("mintype")){
													dataCell.setCellValue(mintype);
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("mintype")){
													dataCell.setCellValue(mintype);
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("reportyear")){
													dataCell.setCellValue((String) an.getReportyear());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("geologyst")){
													dataCell.setCellValue((String) sbl.getGEOLOGIST());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("accountant")){
													dataCell.setCellValue((String) sbl.getACCOUNTANT());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("lpname")){
													dataCell.setCellValue((String) sbl.getLpName());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("lpreg")){
													dataCell.setCellValue((String) sbl.getLpReg());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("orderyear") && (sblic != null) && an.getReportyear() != null && an.getReportyear().length() > 0){
													if (sblic.getGrantDate() != null && sblic.getGrantDate().length() >= 4){
														int year = Integer.parseInt(sblic.getGrantDate().substring(sblic.getGrantDate().length()-4));
														dataCell.setCellValue(String.valueOf(Integer.parseInt(an.getReportyear()) - year));
													}
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("licgrantdate") && sblic != null){
													if (sblic.getGrantDate() != null && sblic.getGrantDate().length() >= 0){
														String[] grantvars = sblic.getGrantDate().split(" ");
														if (grantvars.length == 6){
															dataCell.setCellValue(grantvars[5] + " " + grantvars[1] + " " + grantvars[2]);
														}
													}
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("areasize") && sblic != null){
													dataCell.setCellValue(String.valueOf(sblic.getAreaSize()));
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("manager")){
													dataCell.setCellValue((String) sbl.getKEYMAN());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("companyname")){
													dataCell.setCellValue((String) an.getLpName());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("licencenumbers")){
													
													if (licenselist != null){
														String licences = "";
														for(int ii=0;ii<licenselist.size();ii++){
															if (ii == licenselist.size()-1){
																licences = licences + licenselist.get(ii).getLicenseXB();
															}
															else{
																licences = licences + licenselist.get(ii).getLicenseXB() + ", ";
															}
														}
														dataCell.setCellValue(licences);
													}
													else{
														dataCell.setCellValue(an.getLicenseXB());
													}
												}
											}
										}
									}
								}
							}
						}
					}

					String xname=an.getLpName().trim();
					if (noteObj != null){
						xname = xname + " " + noteObj.getNote().trim();
					}
					
					xname = URLEncoder.encode(xname,"UTF-8"); 
					try (ServletOutputStream outputStream = response.getOutputStream()) {
						response.setContentType("application/ms-excel; charset=UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+xname+".xlsm");
						workbook.write(outputStream);
						outputStream.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (an != null && divisionid == 3 && an.getReporttype() == 4 && ((noteid > 0 && noteObj != null) || (noteid == 0))){
					
					if (noteid == 47){
						sheetname = "georep1";
					}
					else if (noteid == 48){
						sheetname = "georep2";
					}
					else if (noteid == 49){
						sheetname = "georep3";
					}
					else if (noteid == 50){
						sheetname = "georep4";
					}
					else if (noteid == 51){
						sheetname = "georep5";
					}
					else if (noteid == 52){
						sheetname = "georep6";
					}
					else if (noteid == 347){
						sheetname = "georep7";
					}
					else if (noteid == 54){
						sheetname = "georep8";
					}
					else if (noteid == 55){
						sheetname = "georep9";
					}
					else if (noteid == 56){
						sheetname = "georep10";
					}

					RegReportReq rreq= (RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+an.getReqid()+"'", "current");
					LnkReqAnn ann=null;
					if(rreq.getLnkReqAnns().size()>0){
						ann=rreq.getLnkReqAnns().get(0);
					}

					String mintype = "";
					if(an.getMinid()!=0){
						if(ann!=null){   
							LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+ann.getDeposidid()+"'", "current");
							mintype = String.valueOf(qwe.getPnum());
						}
						else if (an.getDepositid() != null){
							LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+an.getDepositid()+"'", "current");
							mintype = String.valueOf(qwe.getDepositnamemon());
						}
						else{
							RegReportReq reqqq = an.getRegReportReq();
							if (reqqq != null){
								LutMinGroup qwe= (LutMinGroup) dao.getHQLResult("from LutMinGroup t where t.id='"+reqqq.getGroupid()+"'", "current");
								if (qwe != null){
									mintype = qwe.getGroupname();
								}
							}
						}
					}

					FileInputStream fis = null;

					File formfile = new File(appPath+"/assets/reporttemplate/GeoReps.xlsm");

					fis = new FileInputStream(formfile);
					Workbook workbook = new XSSFWorkbook(fis);
					
					if (noteid > 0){
						for(int i=0;i<workbook.getNumberOfSheets();i++){
							Sheet sheet = workbook.getSheetAt(i);
							if (sheet.getSheetName().equalsIgnoreCase(sheetname)){
								workbook.setActiveSheet(i);
								workbook.setSelectedTab(i);
							}
						}
					}	
					for(int i=0;i<workbook.getNumberOfSheets();i++){
						Sheet sheet = workbook.getSheetAt(i);
						
						if ((noteid > 0) && !sheet.getSheetName().equalsIgnoreCase(sheetname)){
							workbook.setSheetHidden(i, true);
						}
						
						for(int rowIterator=sheet.getFirstRowNum();rowIterator<=sheet.getLastRowNum();rowIterator++){
							Row currentRow = sheet.getRow(rowIterator);

							if (currentRow != null){
								for(int colIterator=0;colIterator<=currentRow.getLastCellNum();colIterator++){
									Cell currentCell = currentRow.getCell(colIterator);
									if (currentCell != null && currentCell.getCellType() == Cell.CELL_TYPE_STRING && (rowIterator-1 >= 0)){
										Row dataRow = sheet.getRow(rowIterator-1);
										if (dataRow != null){
											Cell dataCell = dataRow.getCell(colIterator);
											if (dataCell == null){
												dataCell =dataRow.createCell(colIterator);
											}
											if (dataCell != null){
												if (currentCell.getStringCellValue().equalsIgnoreCase("givname")){
													dataCell.setCellValue((String) sbl.getGivName());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("licencenum")){
													dataCell.setCellValue((String) an.getLicenseXB());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("mintype")){
													dataCell.setCellValue(mintype);
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("mintype")){
													dataCell.setCellValue(mintype);
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("reportyear")){
													dataCell.setCellValue((String) an.getReportyear());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("geologyst")){
													dataCell.setCellValue((String) sbl.getGEOLOGIST());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("accountant")){
													dataCell.setCellValue((String) sbl.getACCOUNTANT());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("lpname")){
													dataCell.setCellValue((String) sbl.getLpName());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("lpreg")){
													dataCell.setCellValue((String) sbl.getLpReg());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("orderyear") && (sblic != null) && an.getReportyear() != null && an.getReportyear().length() > 0){
													if (sblic.getGrantDate() != null && sblic.getGrantDate().length() >= 4){
														int year = Integer.parseInt(sblic.getGrantDate().substring(sblic.getGrantDate().length()-4));
														dataCell.setCellValue(String.valueOf(Integer.parseInt(an.getReportyear()) - year));
													}
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("licgrantdate") && sblic != null){
													if (sblic.getGrantDate() != null && sblic.getGrantDate().length() >= 0){
														String[] grantvars = sblic.getGrantDate().split(" ");
														if (grantvars.length == 6){
															dataCell.setCellValue(grantvars[5] + " " + grantvars[1] + " " + grantvars[2]);
														}
													}
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("areasize") && sblic != null){
													dataCell.setCellValue(String.valueOf(sblic.getAreaSize()));
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("manager")){
													dataCell.setCellValue((String) sbl.getKEYMAN());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("companyname")){
													dataCell.setCellValue((String) an.getLpName());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("licencenumbers")){
													
													if (licenselist != null){
														String licences = "";
														for(int ii=0;ii<licenselist.size();ii++){
															if (ii == licenselist.size()-1){
																licences = licences + licenselist.get(ii).getLicenseXB();
															}
															else{
																licences = licences + licenselist.get(ii).getLicenseXB() + ", ";
															}
														}
														dataCell.setCellValue(licences);
													}
													else{
														dataCell.setCellValue(an.getLicenseXB());
													}
												}
											}
										}
									}
								}
							}
						}
					}

					String xname=an.getLpName().trim();
					if (noteObj != null){
						xname = xname + " " + noteObj.getNote().trim();
					}
					xname = URLEncoder.encode(xname,"UTF-8"); 
					try (ServletOutputStream outputStream = response.getOutputStream()) {
						response.setContentType("application/ms-excel; charset=UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+xname+".xlsm");
						workbook.write(outputStream);
						outputStream.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (an != null && divisionid == 1 && an.getReporttype() == 4 && ((noteid > 0 && noteObj != null) || (noteid == 0))){
					
					if (noteid == 71){
						sheetname = "Form-3a";
					}
					else if (noteid == 571){
						sheetname = "Form-2-3";
					}
					else if (noteid == 394){
						sheetname = "Form-3b";
					}
					else if (noteid == 74 || noteid == 565){
						sheetname = "Form-4-1";
					}
					else if (noteid == 77 || noteid == 564){
						sheetname = "Form-5";
					}
					else if (noteid == 80 || noteid == 556){
						sheetname = "Form-6-1";
					}
					else if (noteid == 83 || noteid == 563){
						sheetname = "Form-7";
					}
					else if (noteid == 430 || noteid == 562){
						sheetname = "Form-8";
					}
					else if (noteid == 104){
						sheetname = "Form-9";
					}
					else if (noteid == 107 || noteid == 560){
						sheetname = "Form-10";
					}
					else if (noteid == 110 || noteid == 559){
						sheetname = "Form-11";
					}
					else if (noteid == 113 || noteid == 558){
						sheetname = "Form-12";
					}
					else if (noteid == 116 || noteid == 557){
						sheetname = "Form-13";
					}
					/*else if (noteid == 395 || noteid == 429){
						sheetname = "Form-8-2";
					}*/
					else if (noteid == 396 || noteid == 555){
						sheetname = "Form-6-2";
					}
					else if (noteid == 397 || noteid == 566){
						sheetname = "Form-4-2";
					}
					else if (noteid == 86){
						sheetname = "Form-2";
					}
					else if (noteid == 89 || noteid == 554){
						sheetname = "Form-14";
					}
					else if (noteid == 92 || noteid == 459 || noteid == 553 || noteid == 595){
						sheetname = "Form-15";
					}
					else if (noteid == 95 || noteid == 552){
						sheetname = "Form-16";
					}
					else if (noteid == 408){
						sheetname = "Form-17";
					}
					else if (noteid == 119 || noteid == 551){
						sheetname = "Form-18";
					}
					else if (noteid == 398 || noteid == 457 || noteid == 550 || noteid == 594){
						sheetname = "Form-19";
					}

					RegReportReq rreq= (RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+an.getReqid()+"'", "current");
					LnkReqAnn ann=null;
					if(rreq.getLnkReqAnns().size()>0){
						ann=rreq.getLnkReqAnns().get(0);
					}
					
					String mintype = "";
					if(an.getMinid()!=0){
						if(ann!=null){   
							LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+ann.getDeposidid()+"'", "current");
							mintype = String.valueOf(qwe.getDepositnamemon());
						}
						else if (an.getDepositid() != null){
							LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+an.getDepositid()+"'", "current");
							mintype = String.valueOf(qwe.getDepositnamemon());
						}
						else{
							RegReportReq reqqq = an.getRegReportReq();
							if (reqqq != null){
								LutMinGroup qwe= (LutMinGroup) dao.getHQLResult("from LutMinGroup t where t.id='"+reqqq.getGroupid()+"'", "current");
								if (qwe != null){
									mintype = qwe.getGroupname();
								}
							}
						}
					}

					FileInputStream fis = null;

					File formfile = new File(appPath+"/assets/reporttemplate/miningRep.xlsm");

					fis = new FileInputStream(formfile);
					Workbook workbook = new XSSFWorkbook(fis);

					setPlanData(an, sheetname, workbook);

					Sheet sheet3a = workbook.getSheet("Form-3a");
					if (sheet3a != null && ann != null){
						Row dataRow = sheet3a.getRow(12);
						if (dataRow != null){
							Cell dataCell = dataRow.getCell(19);
							if (dataCell != null){
								dataCell.setCellValue(ann.getAimagid());
							}
						}
						dataRow = sheet3a.getRow(13);
						if (dataRow != null){
							Cell dataCell = dataRow.getCell(19);
							if (dataCell != null){
								dataCell.setCellValue(ann.getSumid());
							}
						}
						dataRow = sheet3a.getRow(14);
						if (dataRow != null){
							Cell dataCell = dataRow.getCell(19);
							if (dataCell != null){
								dataCell.setCellValue(ann.getHorde());
							}
						}
						dataRow = sheet3a.getRow(15);
						if (dataRow != null){
							Cell dataCell = dataRow.getCell(19);
							if (dataCell != null){
								dataCell.setCellValue(ann.getHorde());
							}
						}
					}
					
					if (noteid > 0){
						for(int i=0;i<workbook.getNumberOfSheets();i++){
							Sheet sheet = workbook.getSheetAt(i);
							if (sheet.getSheetName().equalsIgnoreCase(sheetname)){
								workbook.setActiveSheet(i);
								workbook.setSelectedTab(i);
							}
						}
					}
					for(int i=0;i<workbook.getNumberOfSheets();i++){
						Sheet sheet = workbook.getSheetAt(i);
						
						if ((noteid > 0) && !sheet.getSheetName().equalsIgnoreCase(sheetname)){
							workbook.setSheetHidden(i, true);
						}
						
						for(int rowIterator=sheet.getFirstRowNum();rowIterator<=sheet.getLastRowNum();rowIterator++){
							Row currentRow = sheet.getRow(rowIterator);

							if (currentRow != null){
								for(int colIterator=0;colIterator<=currentRow.getLastCellNum();colIterator++){
									Cell currentCell = currentRow.getCell(colIterator);
									if (currentCell != null && currentCell.getCellType() == Cell.CELL_TYPE_STRING && (rowIterator-1 >= 0)){
										Row dataRow = sheet.getRow(rowIterator-1);
										if (dataRow != null){
											Cell dataCell = dataRow.getCell(colIterator);
											if (dataCell == null){
												dataCell =dataRow.createCell(colIterator);
											}
											if (currentCell.getStringCellValue().equalsIgnoreCase("givname")){
												dataCell.setCellValue((String) sbl.getGivName());
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("licencenum")){
												dataCell.setCellValue((String) an.getLicenseXB());
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("mintype")){
												dataCell.setCellValue(mintype);
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("mintype")){
												dataCell.setCellValue(mintype);
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("reportyear")){
												dataCell.setCellValue((String) an.getReportyear());
												System.out.println("Sheet: " + sheet.getSheetName() + "\t" + currentRow.getLastCellNum());
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("geologyst")){
												dataCell.setCellValue((String) sbl.getGEOLOGIST());
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("accountant")){
												dataCell.setCellValue((String) sbl.getACCOUNTANT());
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("lpname")){
												dataCell.setCellValue((String) sbl.getLpName());
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("lpreg")){
												dataCell.setCellValue((String) sbl.getLpReg());
											}

											else if (currentCell.getStringCellValue().equalsIgnoreCase("licgrantdate") && sblic != null){
												if (sblic.getGrantDate() != null && sblic.getGrantDate().length() >= 0){
													String[] grantvars = sblic.getGrantDate().split(" ");
													if (grantvars.length == 6){
														dataCell.setCellValue(grantvars[5] + " " + grantvars[1] + " " + grantvars[2]);
													}
												}
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("areasize") && sblic != null){
												dataCell.setCellValue(String.valueOf(sblic.getAreaSize()));
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("manager")){
												dataCell.setCellValue((String) sbl.getKEYMAN());
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("engineering1")){
												dataCell.setCellValue((String) sbl.getGENGINEER());
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("economyst")){
												dataCell.setCellValue((String) sbl.getECONOMIST());
											}
											else if (currentCell.getStringCellValue().equalsIgnoreCase("btechnology") && ann != null){
												LutConcentration conc= (LutConcentration) dao.getHQLResult("from LutConcentration t where t.id='"+ann.getConcetrate()+"'", "current");
												if (conc != null){
													dataCell.setCellValue((String) conc.getNamemon());
												}

											}
										}

									}
								}
							}
						}
					}
					
					String xname=an.getLpName().trim();
					if (noteObj != null){
						xname = xname + " " + noteObj.getNote().trim();
					}
					xname = URLEncoder.encode(xname,"UTF-8"); 
					try (ServletOutputStream outputStream = response.getOutputStream()) {
						response.setContentType("application/ms-excel; charset=UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+xname+".xlsm");
						workbook.write(outputStream);
						outputStream.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (an != null && divisionid == 2 && an.getReporttype() == 4 && ((noteid > 0 && noteObj != null) || (noteid == 0))){
					
					if (noteid == 70){
						sheetname = "CForm-2";
					}
					else if (noteid == 404){
						sheetname = "CForm-2.1";
					}
					else if (noteid == 405){
						sheetname = "CForm-3";
					}
					else if (noteid == 73){
						sheetname = "CForm-4";
					}
					else if (noteid == 76){
						sheetname = "CForm-5";
					}
					else if (noteid == 79){
						sheetname = "CForm-6-1";
					}
					else if (noteid == 406){
						sheetname = "CForm-6-2";
					}
					else if (noteid == 82){
						sheetname = "CForm-7";
					}
					else if (noteid == 428){
						sheetname = "CForm-8";
					}
					else if (noteid == 102){
						sheetname = "CForm-9";
					}
					else if (noteid == 106){
						sheetname = "CForm-10";
					}
					else if (noteid == 109){
						sheetname = "CForm-11";
					}
					else if (noteid == 112){
						sheetname = "CForm-12";
					}
					else if (noteid == 115){
						sheetname = "CForm-13";
					}
					else if (noteid == 88){
						sheetname = "CForm-14";
					}
					else if (noteid == 91 || noteid == 460){
						sheetname = "CForm-15";
					}
					else if (noteid == 94){
						sheetname = "CForm-16";
					}
					else if (noteid == 118){
						sheetname = "CForm-17";
					}
					else if (noteid == 120){
						sheetname = "CForm-18";
					}
					else if (noteid == 122 || noteid == 458){
						sheetname = "CForm-19";
					}

					RegReportReq rreq= (RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+an.getReqid()+"'", "current");
					LnkReqAnn ann=null;
					if(rreq.getLnkReqAnns().size()>0){
						ann=rreq.getLnkReqAnns().get(0);
					}
					
					String mintype = "";
					if(an.getMinid()!=0){
						if(ann!=null){   
							LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+ann.getDeposidid()+"'", "current");
							mintype = String.valueOf(qwe.getDepositnamemon());
						}
						else if (an.getDepositid() != null){
							LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+an.getDepositid()+"'", "current");
							mintype = String.valueOf(qwe.getDepositnamemon());
						}
						else{
							RegReportReq reqqq = an.getRegReportReq();
							if (reqqq != null){
								LutMinGroup qwe= (LutMinGroup) dao.getHQLResult("from LutMinGroup t where t.id='"+reqqq.getGroupid()+"'", "current");
								if (qwe != null){
									mintype = qwe.getGroupname();
								}
							}
						}
					}

					FileInputStream fis = null;

					File formfile = new File(appPath+"/assets/reporttemplate/coalReps.xlsm");

					fis = new FileInputStream(formfile);
					Workbook workbook = new XSSFWorkbook(fis);
					setPlanData(an, sheetname, workbook);
					if (noteid > 0){
						for(int i=0;i<workbook.getNumberOfSheets();i++){
							Sheet sheet = workbook.getSheetAt(i);
							if (sheet.getSheetName().equalsIgnoreCase(sheetname)){
								workbook.setActiveSheet(i);
								workbook.setSelectedTab(i);
							}
						}
					}
					for(int i=0;i<workbook.getNumberOfSheets();i++){
						Sheet sheet = workbook.getSheetAt(i);
						
						if ((noteid > 0) && !sheet.getSheetName().equalsIgnoreCase(sheetname)){
							workbook.setSheetHidden(i, true);
						}
						
						for(int rowIterator=sheet.getFirstRowNum();rowIterator<=sheet.getLastRowNum();rowIterator++){
							Row currentRow = sheet.getRow(rowIterator);

							if (currentRow != null){
								for(int colIterator=0;colIterator<=currentRow.getLastCellNum();colIterator++){
									Cell currentCell = currentRow.getCell(colIterator);
									if (currentCell != null && currentCell.getCellType() == Cell.CELL_TYPE_STRING && (rowIterator-1 >= 0)){
										Row dataRow = sheet.getRow(rowIterator-1);
										if (dataRow != null){
											Cell dataCell = dataRow.getCell(colIterator);
											if (dataCell == null){
												dataCell =dataRow.createCell(colIterator);
											}
											if (dataCell != null){
												if (currentCell.getStringCellValue().equalsIgnoreCase("givname")){
													dataCell.setCellValue((String) sbl.getGivName());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("licencenum")){
													dataCell.setCellValue((String) an.getLicenseXB());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("mintype")){
													dataCell.setCellValue(mintype);
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("mintype")){
													dataCell.setCellValue(mintype);
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("reportyear")){
													dataCell.setCellValue((String) an.getReportyear());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("geologyst")){
													dataCell.setCellValue((String) sbl.getGEOLOGIST());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("accountant")){
													dataCell.setCellValue((String) sbl.getACCOUNTANT());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("lpname")){
													dataCell.setCellValue((String) sbl.getLpName());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("lpreg")){
													dataCell.setCellValue((String) sbl.getLpReg());
												}
												
												else if (currentCell.getStringCellValue().equalsIgnoreCase("licgrantdate") && sblic != null){
													if (sblic.getGrantDate() != null && sblic.getGrantDate().length() >= 0){
														String[] grantvars = sblic.getGrantDate().split(" ");
														if (grantvars.length == 6){
															dataCell.setCellValue(grantvars[5] + " " + grantvars[1] + " " + grantvars[2]);
														}
													}
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("areasize") && sblic != null){
													dataCell.setCellValue(String.valueOf(sblic.getAreaSize()));
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("manager")){
													dataCell.setCellValue((String) sbl.getKEYMAN());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("engineering1")){
													dataCell.setCellValue((String) sbl.getGENGINEER());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("economyst")){
													dataCell.setCellValue((String) sbl.getECONOMIST());
												}
												else if (currentCell.getStringCellValue().equalsIgnoreCase("btechnology") && ann != null){
													LutConcentration conc= (LutConcentration) dao.getHQLResult("from LutConcentration t where t.id='"+ann.getConcetrate()+"'", "current");
													if (conc != null){
														dataCell.setCellValue((String) conc.getNamemon());
													}
													
												}
											}
										}

									}
								}
							}
						}
					}
					
					String xname=an.getLpName().trim();
					if (noteObj != null){
						xname = xname + " " + noteObj.getNote().trim();
					}
					xname = URLEncoder.encode(xname,"UTF-8"); 
					try (ServletOutputStream outputStream = response.getOutputStream()) {
						response.setContentType("application/ms-excel; charset=UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+xname+".xlsm");
						workbook.write(outputStream);
						outputStream.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				out.close();
			} catch (Exception io) {
				io.printStackTrace();
			}
			return null;		
		}	
		return "false";	
	}
	
	@RequestMapping(value="/downloadForm/{noteid}/{plan}", method = RequestMethod.GET)
	public String downloadAttachedForm(@PathVariable int plan, HttpServletResponse response, HttpServletRequest req, @PathVariable Long noteid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			try {
				ServletOutputStream out = response.getOutputStream();

				String appPath = req.getServletContext().getRealPath("");

				AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+plan+"", "current");
				LutFormNotes noteObj=(LutFormNotes) dao.getHQLResult("from LutFormNotes t where t.id="+noteid+"", "current");

				String sheetname = "";
				
				if (an != null && noteObj!=null){
					
					if (noteid == 126){
						sheetname = "geoplan1";
					}
					else if (noteid == 47){
						sheetname = "georep1";
					}
					else if (noteid == 48){
						sheetname = "georep2";
					}
					else if (noteid == 49){
						sheetname = "georep3";
					}
					else if (noteid == 50){
						sheetname = "georep4";
					}
					else if (noteid == 51){
						sheetname = "georep5";
					}
					else if (noteid == 52){
						sheetname = "georep6";
					}
					else if (noteid == 347){
						sheetname = "georep7";
					}
					else if (noteid == 54){
						sheetname = "georep8";
					}
					else if (noteid == 55){
						sheetname = "georep9";
					}
					else if (noteid == 56){
						sheetname = "georep10";
					}
					else if (noteid == 71){
						sheetname = "Form-3a";
					}
					else if (noteid == 394){
						sheetname = "Form-3b";
					}
					else if (noteid == 571){
						sheetname = "Form-2-3";
					}
					else if (noteid == 74){
						sheetname = "Form-4-1";
					}
					else if (noteid == 77){
						sheetname = "Form-5";
					}
					else if (noteid == 80){
						sheetname = "Form-6-1";

					}
					else if (noteid == 83){
						sheetname = "Form-7";
					}
					else if (noteid == 430){
						sheetname = "Form-8";
					}
					else if (noteid == 104){
						sheetname = "Form-9";
					}
					else if (noteid == 107){
						sheetname = "Form-10";
					}
					else if (noteid == 110){
						sheetname = "Form-11";
					}
					else if (noteid == 113){
						sheetname = "Form-12";
					}
					else if (noteid == 116){
						sheetname = "Form-13";
					}
					/*else if (noteid == 395){
						sheetname = "Form-8-2";
					}*/
					else if (noteid == 396){
						sheetname = "Form-6-2";
					}
					else if (noteid == 397){
						sheetname = "Form-4-2";
					}
					else if (noteid == 86){
						sheetname = "Form-2";
					}
					else if (noteid == 89){
						sheetname = "Form-14";
					}
					else if (noteid == 92){
						sheetname = "Form-15";
					}
					else if (noteid == 95){
						sheetname = "Form-16";
					}
					else if (noteid == 408){
						sheetname = "Form-17";
					}
					else if (noteid == 119){
						sheetname = "Form-18";
					}
					else if (noteid == 398){
						sheetname = "Form-19";
					}
					else if (noteid == 70){
						sheetname = "CForm-2";
					}
					else if (noteid == 404){
						sheetname = "CForm-2.1";
					}
					else if (noteid == 405){
						sheetname = "CForm-3";
					}
					else if (noteid == 73){
						sheetname = "CForm-4";
					}
					else if (noteid == 76){
						sheetname = "CForm-5";
					}
					else if (noteid == 79){
						sheetname = "CForm-6-1";
					}
					else if (noteid == 406){
						sheetname = "CForm-6-2";
					}
					else if (noteid == 82){
						sheetname = "CForm-7";
					}
					else if (noteid == 428){
						sheetname = "CForm-8";
					}
					else if (noteid == 102){
						sheetname = "CForm-9";
					}
					else if (noteid == 106){
						sheetname = "CForm-10";
					}
					else if (noteid == 109){
						sheetname = "CForm-11";
					}
					else if (noteid == 112){
						sheetname = "CForm-12";
					}
					else if (noteid == 115){
						sheetname = "CForm-13";
					}
					else if (noteid == 88){
						sheetname = "CForm-14";
					}
					else if (noteid == 91){
						sheetname = "CForm-15";
					}
					else if (noteid == 94){
						sheetname = "CForm-16";
					}
					else if (noteid == 118){
						sheetname = "CForm-17";
					}
					else if (noteid == 120){
						sheetname = "CForm-18";
					}
					else if (noteid == 122){
						sheetname = "CForm-19";
					}
					
					List<LnkPlanAttachedFiles> atts = (List<LnkPlanAttachedFiles>) dao.getHQLResult("from LnkPlanAttachedFiles t where t.expid="+plan+" and t.noteid = " + noteid + " order by t.id desc", "list");
					if (atts != null && atts.size() > 0){
						FileInputStream fis = null;

						File formfile = new File(appPath+atts.get(0).getAttachfiletype());
						if (formfile.exists()){
							fis = new FileInputStream(formfile);
							Workbook workbook = new XSSFWorkbook(fis);
							
							for(int i=0;i<workbook.getNumberOfSheets();i++){
								Sheet sheet = workbook.getSheetAt(i);
								if (sheet.getSheetName().equalsIgnoreCase(sheetname)){
									workbook.setActiveSheet(i);
									workbook.setSelectedTab(i);
								}
							}
							
							for(int i=0;i<workbook.getNumberOfSheets();i++){
								Sheet sheet = workbook.getSheetAt(i);
								if (!sheet.getSheetName().equalsIgnoreCase(sheetname)){
									workbook.setSheetHidden(i, true);
								}
							}

							String xname=an.getLpName().trim();
							xname = xname + " " + noteObj.getNote().trim();
							xname = URLEncoder.encode(xname,"UTF-8"); 
							try (ServletOutputStream outputStream = response.getOutputStream()) {
								response.setContentType("application/ms-excel; charset=UTF-8");
								response.setCharacterEncoding("UTF-8");
								response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+xname+".xlsm");
								workbook.write(outputStream);
								outputStream.close();
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				
				out.close();
			} catch (Exception io) {
				io.printStackTrace();
			}
			return null;		
		}	
		return "false";	
	}
	
	@RequestMapping(value="/generateFormReport/{noteid}", method = RequestMethod.GET)
	public String generateFormReport(HttpServletResponse response, HttpServletRequest req, @PathVariable Long noteid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			try {
				ServletOutputStream out = response.getOutputStream();

				String appPath = req.getServletContext().getRealPath("");

				LutFormNotes noteObj=(LutFormNotes) dao.getHQLResult("from LutFormNotes t where t.id="+noteid+"", "current");

				String sheetname = "";
				
				if (noteObj!=null){
					
					if (noteid == 183){
						List<AnnualRegistration> atts = (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.id in (select t.planid from LnkPlanTransition t where t.noteid = 183 and t.decisionid = 1) and t.reportyear = 2017 and t.reporttype = 3", "list");
						Workbook workbook = new XSSFWorkbook();
						Sheet sheet = workbook.createSheet("datas4-1");
						Row row = sheet.createRow(0);
						row.createCell(0).setCellValue("Тусгай зөвшөөрлийн дугаар");
						row.createCell(1).setCellValue("Байгууллагын РД");
						row.createCell(2).setCellValue("Байгууллагын нэр");
						row.createCell(3).setCellValue("Ашигт малтмалын төрөл");
						row.createCell(4).setCellValue("Ордны нэр");
						row.createCell(5).setCellValue("Аймаг");
						row.createCell(6).setCellValue("Сум");
						row.createCell(7).setCellValue("Борлуулалтын орлого");
						row.createCell(8).setCellValue("Бүтээгдэхүүн");
						row.createCell(9).setCellValue("Х/нэгж");
						row.createCell(10).setCellValue("Агуулга");
						row.createCell(11).setCellValue("Ашигт малтмалын нэр");
						row.createCell(12).setCellValue("Х/нэгж");
						row.createCell(13).setCellValue("Агуулга");
						row.createCell(14).setCellValue("Х/нэгж");
						row.createCell(15).setCellValue("Метал (эрдэс хэмжээ)");
						row.createCell(16).setCellValue("Х/нэгж");
						row.createCell(17).setCellValue("Эрдэс авалт");
						
						for(int i=0;i<atts.size();i++){
							row = sheet.createRow(i+1);
							
							Cell cell = row.createCell(0);
							cell.setCellValue(atts.get(i).getLicenseXB());
							
							cell = row.createCell(1);
							cell.setCellValue(atts.get(i).getLpReg());
							
							cell = row.createCell(2);
							cell.setCellValue(atts.get(i).getLpName());
							
							RegReportReq rreq= (RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+atts.get(i).getReqid()+"'", "current");
							LnkReqAnn ann=null;
							if(rreq.getLnkReqAnns().size()>0){
								ann=rreq.getLnkReqAnns().get(0);
							}
							
							String mintype = "";
							if(atts.get(i).getMinid()!=0){
								if(ann!=null){   
									LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+ann.getDeposidid()+"'", "current");
									mintype = String.valueOf(qwe.getDepositnamemon());
								}
								else if (atts.get(i).getDepositid() != null){
									LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+atts.get(i).getDepositid()+"'", "current");
									mintype = String.valueOf(qwe.getDepositnamemon());
								}
								else{
									RegReportReq reqqq = atts.get(i).getRegReportReq();
									if (reqqq != null){
										LutMinGroup qwe= (LutMinGroup) dao.getHQLResult("from LutMinGroup t where t.id='"+reqqq.getGroupid()+"'", "current");
										if (qwe != null){
											mintype = qwe.getGroupname();
										}
									}
								}
							}
							
							cell = row.createCell(3);
							cell.setCellValue(mintype);
							
							if (ann!=null){
								cell = row.createCell(4);
								cell.setCellValue(ann.getHorde());
								
								cell = row.createCell(5);
								cell.setCellValue(ann.getAimagid());
								
								cell = row.createCell(6);
								cell.setCellValue(ann.getSumid());
							}
							
							List<DataMinPlan16> borlogo = (List<DataMinPlan16>) dao.getHQLResult("from DataMinPlan16 t where t.planid = " + atts.get(i).getId() + " and t.data1 = '  1' order by t.id asc", "list");
							if (borlogo.size() > 0){
								cell = row.createCell(7);
								if (borlogo.get(0).getData4() != null){
									cell.setCellValue(borlogo.get(0).getData4());
								}
							}
							
							List<DataMinPlan4_1> datas = (List<DataMinPlan4_1>) dao.getHQLResult("from DataMinPlan4_1 t where t.planid = " + atts.get(i).getId() + " and t.type in (4,5) order by t.type asc", "list");
							for(int j=0;j<datas.size();j++){
								cell = row.createCell((j*10)+8);
								cell.setCellValue((datas.get(j).getData1() != null) ? datas.get(j).getData1() : "");
								
								cell = row.createCell((j*10)+9);
								cell.setCellValue((datas.get(j).getData2() != null) ? datas.get(j).getData2() : "");
								
								cell = row.createCell((j*10)+10);
								if (datas.get(j).getData3() != null){
									cell.setCellValue(datas.get(j).getData3());
								}
								else{
									cell.setCellValue("");
								}
								
								cell = row.createCell((j*10)+11);
								cell.setCellValue((datas.get(j).getData5() != null) ? datas.get(j).getData5() : "");
								
								cell = row.createCell((j*10)+12);
								cell.setCellValue((datas.get(j).getData6() != null) ? datas.get(j).getData6() : "");
								
								cell = row.createCell((j*10)+13);
								cell.setCellValue((datas.get(j).getData7() != null) ? datas.get(j).getData7() : "");
								
								cell = row.createCell((j*10)+14);
								cell.setCellValue((datas.get(j).getData8() != null) ? datas.get(j).getData8() : "");
								
								cell = row.createCell((j*10)+15);
								if (datas.get(j).getData9() != null){
									cell.setCellValue(datas.get(j).getData9());
								}
								else{
									cell.setCellValue("");
								}
								
								cell = row.createCell((j*10)+16);
								cell.setCellValue((datas.get(j).getData12() != null) ? datas.get(j).getData12() : "");

								cell = row.createCell((j*10)+17);
								if (datas.get(j).getData13() != null){
									cell.setCellValue(datas.get(j).getData13());
								}
								else{
									cell.setCellValue("");
								}
							}
							
						}
						
						atts = (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.id in (select t.planid from LnkPlanTransition t where t.noteid = 25 and t.decisionid = 1) and t.reportyear = 2017 and t.reporttype = 3", "list");
						Sheet sheet42 = workbook.createSheet("datas4-2");
						row = sheet42.createRow(0);
						row.createCell(0).setCellValue("Тусгай зөвшөөрлийн дугаар");
						row.createCell(1).setCellValue("Байгууллагын РД");
						row.createCell(2).setCellValue("Байгууллагын нэр");
						row.createCell(3).setCellValue("Ашигт малтмалын төрөл");
						row.createCell(4).setCellValue("Ордны нэр");
						row.createCell(5).setCellValue("Аймаг");
						row.createCell(6).setCellValue("Сум");
						row.createCell(7).setCellValue("Борлуулалтын орлого");
						row.createCell(8).setCellValue("Бүтээгдэхүүн");
						row.createCell(9).setCellValue("Х/нэгж");
						row.createCell(10).setCellValue("Тоон утга");
						row.createCell(11).setCellValue("Х/нэгж");
						row.createCell(12).setCellValue("Гарц - тоон утга");
						
						for(int i=0;i<atts.size();i++){
							row = sheet42.createRow(i+1);
							
							Cell cell = row.createCell(0);
							cell.setCellValue(atts.get(i).getLicenseXB());
							
							cell = row.createCell(1);
							cell.setCellValue(atts.get(i).getLpReg());
							
							cell = row.createCell(2);
							cell.setCellValue(atts.get(i).getLpName());
							
							RegReportReq rreq = (RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+atts.get(i).getReqid()+"'", "current");
							LnkReqAnn ann = null;
							if(rreq.getLnkReqAnns().size()>0){
								ann=rreq.getLnkReqAnns().get(0);
							}
							
							String mintype = "";
							if(atts.get(i).getMinid()!=0){
								if(ann!=null){   
									LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+ann.getDeposidid()+"'", "current");
									mintype = String.valueOf(qwe.getDepositnamemon());
								}
								else if (atts.get(i).getDepositid() != null){
									LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+atts.get(i).getDepositid()+"'", "current");
									mintype = String.valueOf(qwe.getDepositnamemon());
								}
								else{
									RegReportReq reqqq = atts.get(i).getRegReportReq();
									if (reqqq != null){
										LutMinGroup qwe= (LutMinGroup) dao.getHQLResult("from LutMinGroup t where t.id='"+reqqq.getGroupid()+"'", "current");
										if (qwe != null){
											mintype = qwe.getGroupname();
										}
									}
								}
							}
							
							cell = row.createCell(3);
							cell.setCellValue(mintype);
							
							if (ann!=null){
								cell = row.createCell(4);
								cell.setCellValue(ann.getHorde());
								
								cell = row.createCell(5);
								cell.setCellValue(ann.getAimagid());
								
								cell = row.createCell(6);
								cell.setCellValue(ann.getSumid());
							}
							
							List<DataMinPlan16> borlogo = (List<DataMinPlan16>) dao.getHQLResult("from DataMinPlan16 t where t.planid = " + atts.get(i).getId() + " and t.data1 = '  1' order by t.id asc", "list");
							if (borlogo.size() > 0){
								cell = row.createCell(7);
								if (borlogo.get(0).getData4() != null){
									cell.setCellValue(borlogo.get(0).getData4());
								}
							}
							
							List<DataMinPlan4_2> datas42 = (List<DataMinPlan4_2>) dao.getHQLResult("from DataMinPlan4_2 t where t.planid = " + atts.get(i).getId() + " and t.type in (2,3) order by t.type asc", "list");
							for(int j=0;j<datas42.size();j++){
								cell = row.createCell((j*5)+8);
								cell.setCellValue((datas42.get(j).getData1() != null) ? datas42.get(j).getData1() : "");
								
								cell = row.createCell((j*5)+9);
								cell.setCellValue((datas42.get(j).getData2() != null) ? datas42.get(j).getData2() : "");
								
								cell = row.createCell((j*5)+10);
								if (datas42.get(j).getData3() != null){
									cell.setCellValue(datas42.get(j).getData3());
								}
								else{
									cell.setCellValue("");
								}
								
								cell = row.createCell((j*5)+11);
								cell.setCellValue((datas42.get(j).getData4() != null) ? datas42.get(j).getData4() : "");
								
								cell = row.createCell((j*5)+12);
								if (datas42.get(j).getData5() != null){
									cell.setCellValue(datas42.get(j).getData5());
								}
								else{
									cell.setCellValue("");
								}
							}
						}
						
						String xname=noteObj.getNote().trim();
						xname = URLEncoder.encode(xname,"UTF-8"); 
						try (ServletOutputStream outputStream = response.getOutputStream()) {
							response.setContentType("application/ms-excel; charset=UTF-8");
							response.setCharacterEncoding("UTF-8");
							response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+xname+".xlsx");
							workbook.write(outputStream);
							outputStream.close();
						}
						catch (Exception e) {
							e.printStackTrace();
						}

					}
					if (noteid == 185){
						List<AnnualRegistration> atts = (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.id in (select t.planid from LnkPlanTransition t where t.noteid = 185 and t.decisionid = 1) and t.reportyear = 2017 and t.reporttype = 3", "list");
						Workbook workbook = new XSSFWorkbook();
						Sheet sheet = workbook.createSheet("datas1-1");

						
						for(int i=0;i<atts.size();i++){
							Row row = sheet.createRow(i);
							
							Cell cell = row.createCell(0);
							cell.setCellValue(atts.get(i).getLicenseXB());
							
							cell = row.createCell(1);
							cell.setCellValue(atts.get(i).getLpReg());
							
							cell = row.createCell(2);
							cell.setCellValue(atts.get(i).getLpName());
							
							RegReportReq rreq= (RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+atts.get(i).getReqid()+"'", "current");
							LnkReqAnn ann=null;
							if(rreq.getLnkReqAnns().size()>0){
								ann=rreq.getLnkReqAnns().get(0);
							}
							
							String mintype = "";
							if(atts.get(i).getMinid()!=0){
								if(ann!=null){   
									LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+ann.getDeposidid()+"'", "current");
									mintype = String.valueOf(qwe.getDepositnamemon());
								}
								else if (atts.get(i).getDepositid() != null){
									LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+atts.get(i).getDepositid()+"'", "current");
									mintype = String.valueOf(qwe.getDepositnamemon());
								}
								else{
									RegReportReq reqqq = atts.get(i).getRegReportReq();
									if (reqqq != null){
										LutMinGroup qwe= (LutMinGroup) dao.getHQLResult("from LutMinGroup t where t.id='"+reqqq.getGroupid()+"'", "current");
										if (qwe != null){
											mintype = qwe.getGroupname();
										}
									}
								}
							}
							
							cell = row.createCell(3);
							cell.setCellValue(mintype);
							
							if (ann!=null){
								cell = row.createCell(4);
								cell.setCellValue(ann.getHorde());
								
								cell = row.createCell(5);
								cell.setCellValue(ann.getAimagid());
								
								cell = row.createCell(6);
								cell.setCellValue(ann.getSumid());
							}
							
							List<DataMinPlan16> borlogo = (List<DataMinPlan16>) dao.getHQLResult("from DataMinPlan16 t where t.planid = " + atts.get(i).getId() + " and t.data1 = '  1' order by t.id asc", "list");
							if (borlogo.size() > 0){
								cell = row.createCell(7);
								if (borlogo.get(0).getData4() != null){
									cell.setCellValue(borlogo.get(0).getData4());
								}
							}
							
							List<DataMinPlan1> datas = (List<DataMinPlan1>) dao.getHQLResult("from DataMinPlan1 t where t.planid = " + atts.get(i).getId() + " and t.noteid = 185 order by t.id asc", "list");
							int beginIndex = datas.size()-1;
							for(int j=0;j<datas.size();j++){
								if (datas.get(j).getDataIndex() != null && datas.get(j).getDataIndex() == 3){
									beginIndex = j;
								}
							}
							for(int j=beginIndex, j1=0;(j<datas.size() && !(datas.get(j).getDataIndex() != null && datas.get(j).getDataIndex()==5));j++,j1++){
								//System.out.println(datas.get(j).toString());
								
								cell = row.createCell((j1*8)+8);
								cell.setCellValue((datas.get(j).getData1() != null) ? datas.get(j).getData1() : "");
								
								cell = row.createCell((j1*8)+9);
								cell.setCellValue((datas.get(j).getData2() != null) ? datas.get(j).getData2() : "");
								
								cell = row.createCell((j1*8)+10);
								if (datas.get(j).getData3() != null){
									cell.setCellValue(datas.get(j).getData3());
								}
								else{
									cell.setCellValue("");
								}
								
								cell = row.createCell((j1*8)+11);
								cell.setCellValue((datas.get(j).getData5() != null) ? datas.get(j).getData5() : "");
								
								cell = row.createCell((j1*8)+12);
								cell.setCellValue((datas.get(j).getData6() != null) ? datas.get(j).getData6() : "");
								
								cell = row.createCell((j1*8)+13);
								if (datas.get(j).getData7() != null){
									cell.setCellValue(datas.get(j).getData7());
								}
								else{
									cell.setCellValue("");
								}
								
								cell = row.createCell((j1*8)+14);
								cell.setCellValue((datas.get(j).getData8() != null) ? datas.get(j).getData8() : "");
								
								cell = row.createCell((j1*8)+15);
								if (datas.get(j).getData9() != null){
									cell.setCellValue(datas.get(j).getData9());
								}
								else{
									cell.setCellValue("");
								}
							}
							
						}
						
						atts = (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.id in (select t.planid from LnkPlanTransition t where t.noteid = 184 and t.decisionid = 1) and t.reportyear = 2017 and t.reporttype = 3", "list");
						sheet = workbook.createSheet("datas1-2");
						
						for(int i=0;i<atts.size();i++){
							Row row = sheet.createRow(i);
							
							Cell cell = row.createCell(0);
							cell.setCellValue(atts.get(i).getLicenseXB());
							
							cell = row.createCell(1);
							cell.setCellValue(atts.get(i).getLpReg());
							
							cell = row.createCell(2);
							cell.setCellValue(atts.get(i).getLpName());
							
							RegReportReq rreq= (RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+atts.get(i).getReqid()+"'", "current");
							LnkReqAnn ann=null;
							if(rreq.getLnkReqAnns().size()>0){
								ann=rreq.getLnkReqAnns().get(0);
							}
							
							String mintype = "";
							if(atts.get(i).getMinid()!=0){
								if(ann!=null){   
									LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+ann.getDeposidid()+"'", "current");
									mintype = String.valueOf(qwe.getDepositnamemon());
								}
								else if (atts.get(i).getDepositid() != null){
									LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+atts.get(i).getDepositid()+"'", "current");
									mintype = String.valueOf(qwe.getDepositnamemon());
								}
								else{
									RegReportReq reqqq = atts.get(i).getRegReportReq();
									if (reqqq != null){
										LutMinGroup qwe= (LutMinGroup) dao.getHQLResult("from LutMinGroup t where t.id='"+reqqq.getGroupid()+"'", "current");
										if (qwe != null){
											mintype = qwe.getGroupname();
										}
									}
								}
							}
							
							cell = row.createCell(3);
							cell.setCellValue(mintype);
							
							if (ann!=null){
								cell = row.createCell(4);
								cell.setCellValue(ann.getHorde());
								
								cell = row.createCell(5);
								cell.setCellValue(ann.getAimagid());
								
								cell = row.createCell(6);
								cell.setCellValue(ann.getSumid());
							}
							
							List<DataMinPlan16> borlogo = (List<DataMinPlan16>) dao.getHQLResult("from DataMinPlan16 t where t.planid = " + atts.get(i).getId() + " and t.data1 = '  1' order by t.id asc", "list");
							if (borlogo.size() > 0){
								cell = row.createCell(7);
								if (borlogo.get(0).getData4() != null){
									cell.setCellValue(borlogo.get(0).getData4());
								}
							}
							
							List<DataMinPlan1> datas = (List<DataMinPlan1>) dao.getHQLResult("from DataMinPlan1 t where t.planid = " + atts.get(i).getId() + " and t.noteid = 184 order by t.id asc", "list");
							int beginIndex = datas.size()-1;
							for(int j=0;j<datas.size();j++){
								if (datas.get(j).getDataIndex() != null && datas.get(j).getDataIndex() == 3){
									beginIndex = j;
								}
							}
							for(int j=beginIndex, j1=0;(j<datas.size() && !(datas.get(j).getDataIndex() != null && datas.get(j).getDataIndex()==5));j++,j1++){
								//System.out.println(datas.get(j).toString());
								
								cell = row.createCell((j1*3)+8);
								cell.setCellValue((datas.get(j).getData1() != null) ? datas.get(j).getData1() : "");
								
								cell = row.createCell((j1*3)+9);
								cell.setCellValue((datas.get(j).getData2() != null) ? datas.get(j).getData2() : "");
								
								cell = row.createCell((j1*3)+10);
								if (datas.get(j).getData3() != null){
									cell.setCellValue(datas.get(j).getData3());
								}
								else{
									cell.setCellValue("");
								}
								
							}
							
						}
						
						String xname=noteObj.getNote().trim();
						xname = URLEncoder.encode(xname,"UTF-8"); 
						try (ServletOutputStream outputStream = response.getOutputStream()) {
							response.setContentType("application/ms-excel; charset=UTF-8");
							response.setCharacterEncoding("UTF-8");
							response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+xname+".xlsx");
							workbook.write(outputStream);
							outputStream.close();
						}
						catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
				
				out.close();
			} catch (Exception io) {
				io.printStackTrace();
			}
			return null;		
		}	
		return "false";	
	}
	
	@RequestMapping(value="/generateFormCover/{planid}", method = RequestMethod.GET)
	public String generateFormCover(HttpServletResponse response, HttpServletRequest req, @PathVariable Long planid) {
		AnnualRegistration an = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id = " + planid, "current");
		String appPath = req.getSession().getServletContext().getRealPath("");
		if (an != null){
			List<LnkReqAnn> rss=(List<LnkReqAnn>) dao.getHQLResult("from LnkReqAnn t where t.reqid="+an.getReqid()+" order by t.id desc", "list");
			LnkReqAnn ann = null;
			if (rss != null  && rss.size() > 0){
				ann  = rss.get(0);
			}
			//LnkReqAnn ann = (LnkReqAnn) dao.getHQLResult("from LnkReqAnn t where t.reqid = " + an.getReqid(), "current");
			String mintype = "";
			if(an.getMinid()!=0){
				if(ann!=null){   
					LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+ann.getDeposidid()+"'", "current");
					mintype = String.valueOf(qwe.getDepositnamemon());
				}
				else if (an.getDepositid() != null){
					LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+an.getDepositid()+"'", "current");
					mintype = String.valueOf(qwe.getDepositnamemon());
				}
				else{
					RegReportReq reqqq = an.getRegReportReq();
					if (reqqq != null){
						LutMinGroup qwe= (LutMinGroup) dao.getHQLResult("from LutMinGroup t where t.id='"+reqqq.getGroupid()+"'", "current");
						if (qwe != null){
							mintype = qwe.getGroupname();
						}
					}
				}
			}
			if (an.getDivisionid() == 1){
				try {
					FileInputStream fis = null;
					File formfile = new File(appPath+"assets/reporttemplate/MIningTemp.xlsx");
					fis = new FileInputStream(formfile);
					Workbook workbook = new XSSFWorkbook(fis);
					Sheet sheet = workbook.getSheet("Sheet1");
					
					Row row = sheet.getRow(2);
					if (row != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(an.getLicenseXB());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(an.getLicenseXB());
						}
					}
					
					row = sheet.getRow(4);
					if (row != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(mintype);
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(mintype);
						}
					}
					
					row = sheet.getRow(8);
					if (row != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(an.getReportyear());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(an.getReportyear());
						}
					}
					
					row = sheet.getRow(12);
					if (row != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(an.getLpName());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(an.getLpName());
						}
					}
					
					row = sheet.getRow(13);
					if (row != null){
						SubLegalpersons sbl= (SubLegalpersons) dao.getHQLResult("from SubLegalpersons t where t.lpReg='"+an.getLpReg()+"'", "current");
						Cell cell = row.getCell(3);
						if (sbl != null){
							if (cell != null){
								cell.setCellValue(sbl.getSteteReg());
							}
							else{
								cell = row.createCell(3);
								cell.setCellValue(sbl.getSteteReg());
							}
						}
					}
					
					row = sheet.getRow(14);
					if (row != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(an.getLpReg());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(an.getLpReg());
						}
					}
					
					row = sheet.getRow(15);
					if (row != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(an.getLicenseXB());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(an.getLicenseXB());
						}
					}
					
					row = sheet.getRow(16);
					if (row != null && ann != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(ann.getHorde());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(ann.getHorde());
						}
					}
					
					row = sheet.getRow(17);
					if (row != null && ann != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(ann.getAimagid());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(ann.getAimagid());
						}
					}
					
					row = sheet.getRow(18);
					if (row != null && ann != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(ann.getSumid());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(ann.getSumid());
						}
					}
					
					row = sheet.getRow(19);
					if (row != null && ann != null){
						LutMinerals mineral= (LutMinerals) dao.getHQLResult("from LutMinerals t where t.id='"+ann.getMineralid()+"'", "current");
						Cell cell = row.getCell(3);
						if (mineral != null){
							if (cell != null){
								cell.setCellValue(mintype);
							}
							else{
								cell = row.createCell(3);
								cell.setCellValue(mintype);
							}
						}
					}
					
					row = sheet.getRow(20);
					if (row != null && ann != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(mintype);
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(mintype);
						}
					}
					
					row = sheet.getRow(21);
					if (row != null && ann != null){
						LutMineType minetype= (LutMineType) dao.getHQLResult("from LutMineType t where t.id='"+ann.getMinetypeid()+"'", "current");
						Cell cell = row.getCell(3);
						if (minetype != null){
							if (cell != null){
								cell.setCellValue(minetype.getMtypeNameMon());
							}
							else{
								cell = row.createCell(3);
								cell.setCellValue(minetype.getMtypeNameMon());
							}
						}
					}
					
					row = sheet.getRow(22);
					if (row != null && ann != null){
						LutConcentration conc= (LutConcentration) dao.getHQLResult("from LutConcentration t where t.id='"+ann.getConcetrate()+"'", "current");
						Cell cell = row.getCell(3);
						if (conc != null){
							if (cell != null){
								cell.setCellValue(conc.getNamemon());
							}
							else{
								cell = row.createCell(3);
								cell.setCellValue(conc.getNamemon());
							}
						}
						
					}
					
					row = sheet.getRow(23);
					if (row != null && ann != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(ann.getKomissdate());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(ann.getKomissdate());
						}
					}
					
					row = sheet.getRow(24);
					if (row != null && ann != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(ann.getKomissakt());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(ann.getKomissakt());
						}
					}
					
					row = sheet.getRow(31);
					if (row != null && ann != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(ann.getWorkyear());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(ann.getWorkyear());
						}
					}
					
					row = sheet.getRow(32);
					if (row != null && ann != null){
						Cell cell = row.getCell(3);
						if (cell != null){
							cell.setCellValue(ann.getYearcapacity());
						}
						else{
							cell = row.createCell(3);
							cell.setCellValue(ann.getYearcapacity());
						}
					}
					
					row = sheet.getRow(34);
					if (row != null && ann != null){
						SubLegalpersons sbl= (SubLegalpersons) dao.getHQLResult("from SubLegalpersons t where t.lpReg='"+an.getLpReg()+"'", "current");
						Cell cell = row.getCell(3);
						if (sbl != null){
							if (cell != null){
								cell.setCellValue(sbl.getGENGINEER());
							}
							else{
								cell = row.createCell(3);
								cell.setCellValue(sbl.getGENGINEER());
							}
						}
					}
					
					String xname=an.getLpName().trim();
					xname = URLEncoder.encode(xname,"UTF-8"); 
					try (ServletOutputStream outputStream = response.getOutputStream()) {
						response.setContentType("application/ms-excel; charset=UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+xname+".xlsx");
						workbook.write(outputStream);
						outputStream.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "false";	
	}

    @RequestMapping(value = "/fix/{id}", method = RequestMethod.GET)
    public String fixTable(HttpServletResponse response, HttpServletRequest req, @PathVariable Long id) {
	    if (id == 1){
            List<LutButNegj> sbl= (List<LutButNegj>) dao.getHQLResult("from LutButNegj t", "list");
            for(LutButNegj a : sbl){
                a.setName(a.getName().substring(1,a.getName().length()-2));
                dao.PeaceCrud(a, "LutButNegj", "save", (long) a.getId(), 0, 0, null);
            }
        }
        return null;
    }

    @RequestMapping(value = "/gov/{id}", method = RequestMethod.GET)
    public String generateGovReport(HttpServletResponse response, HttpServletRequest req, @PathVariable Long id) {
        if (id == 1) {
            String sqlQuery = "SELECT DEP.DEPOSITNAMEMON";
            sqlQuery = sqlQuery + ",(SELECT MAX(D .DATA2) FROM DATA_MIN_PLAN_3 D WHERE D . TYPE = 1 AND D .data1 LIKE '%Хөрс хуулалт%' AND D .PLANID IN (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .DEPOSITID = DEP.DEPOSITID AND A .REPSTATUSID = 1 AND A .DIVISIONID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = 2017))";
            for (int i = 3; i <= 19; i++) {
                sqlQuery = sqlQuery + (",(SELECT SUM (data" + i + ") FROM DATA_MIN_PLAN_3 D WHERE D . TYPE = 1 AND D .data1 LIKE '%Хөрс хуулалт%' AND D .PLANID IN (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .DEPOSITID = DEP.DEPOSITID AND A .REPSTATUSID = 1 AND A .DIVISIONID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = 2017))");
            }
            sqlQuery = sqlQuery + " FROM LUT_DEPOSIT dep ORDER BY DEP.DEPOSITNAMEMON";
            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            JSONArray result = new JSONArray();
            for (int i=0;i<resultObj.size();i++) {
                Object[] o = resultObj.get(i);
                JSONArray obj = new JSONArray();
                for (int j = 0; j < 19; j++) {
                    if (o[j] != null) {
                        obj.put(o[j]);
                    } else {
                        if (i<2){
                            obj.put(0);
                        }
                        else{
                            obj.put("");
                        }
                    }
                }
                result.put(obj);
            }
            //System.out.println(result.toString());
            return result.toString();
        }
        else if (id == 2) {
            String sqlQuery = "SELECT DEP.DEPOSITNAMEMON";
            sqlQuery = sqlQuery + ",(SELECT MAX(D .DATA2) FROM DATA_MIN_PLAN_3 D WHERE D . TYPE = 1 AND D .data1 LIKE '%Хүдэр (элс) олборлолт%' AND D .PLANID IN (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A .DEPOSITID = DEP.DEPOSITID AND A .REPSTATUSID = 1 AND A .DIVISIONID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = 2017))";
            for (int i = 3; i <= 19; i++) {
                sqlQuery = sqlQuery + (",(SELECT SUM (data" + i + ") FROM DATA_MIN_PLAN_3 D WHERE D . TYPE = 1 AND D .data1 LIKE '%Хүдэр (элс) олборлолт%' AND D .PLANID IN (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .DEPOSITID = DEP.DEPOSITID AND A .REPSTATUSID = 1 AND A .DIVISIONID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = 2017))");
            }
            sqlQuery = sqlQuery + " FROM LUT_DEPOSIT dep ORDER BY DEP.DEPOSITNAMEMON";
            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            JSONArray result = new JSONArray();
            for (int i=0;i<resultObj.size();i++) {
                Object[] o = resultObj.get(i);
                JSONArray obj = new JSONArray();
                for (int j = 0; j < 19; j++) {
                    if (o[j] != null) {
                        obj.put(o[j]);
                    } else {
                        if (i<2){
                            obj.put(0);
                        }
                        else{
                            obj.put("");
                        }
                    }
                }
                result.put(obj);
            }
            //System.out.println(result.toString());
            return result.toString();
        }
        else if (id == 3) {
            String sqlQuery = "SELECT DEP.ID, DEP.NAME, DEP.NEGJ";
            for (int i = 3; i <= 19; i++) {
                sqlQuery = sqlQuery + (",(SELECT SUM (data" + i + ") FROM DATA_MIN_PLAN_3 D WHERE D . TYPE = 2 AND D.data1 = DEP.NAME AND D .PLANID IN (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .DIVISIONID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = 2017))");
            }
            sqlQuery = sqlQuery + " FROM LUT_BUT_NEGJ dep ORDER BY DEP.NAME, DEP.ID, DEP.NEGJ";
            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            JSONArray result = new JSONArray();
            for (int i=0;i<resultObj.size();i++) {
                Object[] o = resultObj.get(i);
                JSONArray obj = new JSONArray();
                for (int j = 0; j < 20; j++) {
                    if (o[j] != null) {
                        obj.put(o[j]);
                    } else {
                        obj.put(0);
                    }
                }
                result.put(obj);
            }
            //System.out.println(result.toString());
            return result.toString();
        }
        else if (id == 4) {
            String sqlQuery = "SELECT DEP.ID, DEP.NAME, DEP.NEGJ";
            for (int i = 3; i <= 19; i++) {
                sqlQuery = sqlQuery + (",(SELECT SUM (data" + i + ") FROM DATA_MIN_PLAN_3 D WHERE D . TYPE = 3 AND D.data1 = DEP.NAME AND D .PLANID IN (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .REPSTATUSID = 1 AND A .DIVISIONID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = 2017))");
            }
            sqlQuery = sqlQuery + " FROM LUT_BUT_NEGJ dep ORDER BY DEP.NAME, DEP.ID, DEP.NEGJ";
            List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
            JSONArray result = new JSONArray();
            for (int i=0;i<resultObj.size();i++) {
                Object[] o = resultObj.get(i);
                JSONArray obj = new JSONArray();
                for (int j = 0; j < 20; j++) {
                    if (o[j] != null) {
                        obj.put(o[j]);
                    } else {
                        obj.put(0);
                    }
                }
                result.put(obj);
            }
            //System.out.println(result.toString());
            return result.toString();
        }
        else if (id == 5) {
            JSONArray result = new JSONArray();
            List<Object[]> depositArray = dao.getNativeSQLResult("select * from LUT_DEPOSIT d where d.DEPOSITID in (select a.depositid from ANNUAL_REGISTRATION a where A.ISTODOTGOL = 0 AND A .DIVISIONID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = 2017 AND A .REPSTATUSID = 1) order by d.depositnamemon", "list");
            for(Object[] d : depositArray){
                String sqlQuery = "SELECT D .DATA1, (SELECT M.NEGJ FROM LUT_BUT_NEGJ M WHERE lower(M.NAME) like lower(D.DATA1) and rownum = 1), SUM (D .DATA3), SUM (D .DATA4), SUM (D .DATA5), SUM (D .DATA6), SUM (D .DATA7), SUM (D .DATA8), SUM (D .DATA9), SUM (D .DATA10), SUM (D .DATA11), SUM (D .DATA12), SUM (D .DATA13), SUM (D .DATA14), SUM (D .DATA15), SUM (D .DATA16), SUM (D .DATA17), SUM (D .DATA18), SUM (D .DATA19) FROM DATA_MIN_PLAN_3 D WHERE D .planid IN (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .DIVISIONID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = 2017 AND A .REPSTATUSID = 1 AND A .DEPOSITID = "+d[0].toString()+") AND D . TYPE = 2 AND D .DATA1 NOT LIKE '%Хүдэр (элс) боловсруулалт%' GROUP BY D .DATA1";
                List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                JSONObject obj = new JSONObject();
                obj.put("depositname",d[1].toString());
                JSONArray depresult = new JSONArray();
                for (int i=0;i<resultObj.size();i++) {
                    Object[] o = resultObj.get(i);
                    JSONArray detailobj = new JSONArray();
                    for (int j = 0; j < 19; j++) {
                        if (o[j] != null) {
                            detailobj.put(o[j]);
                        } else {
                            detailobj.put(0);
                        }
                    }
                    depresult.put(detailobj);
                }
                obj.put("details",depresult);
                result.put(obj);
            }
            return result.toString();
        }
        else if (id == 6) {
            JSONArray result = new JSONArray();
            List<Object[]> depositArray = dao.getNativeSQLResult("select * from LUT_DEPOSIT d where d.DEPOSITID in (select a.depositid from ANNUAL_REGISTRATION a where A.ISTODOTGOL = 0 AND A .DIVISIONID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = 2017 AND A .REPSTATUSID = 1) order by d.depositnamemon", "list");
            for(Object[] d : depositArray){
                String sqlQuery = "SELECT D .DATA1, (SELECT M.NEGJ FROM LUT_BUT_NEGJ M WHERE lower(M.NAME) like lower(D.DATA1) and rownum = 1), SUM (D .DATA3), SUM (D .DATA4), SUM (D .DATA5), SUM (D .DATA6), SUM (D .DATA7), SUM (D .DATA8), SUM (D .DATA9), SUM (D .DATA10), SUM (D .DATA11), SUM (D .DATA12), SUM (D .DATA13), SUM (D .DATA14), SUM (D .DATA15), SUM (D .DATA16), SUM (D .DATA17), SUM (D .DATA18), SUM (D .DATA19) FROM DATA_MIN_PLAN_3 D WHERE D .planid IN (SELECT A . ID FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A .DIVISIONID = 1 AND A .REPORTTYPE = 3 AND A .REPORTYEAR = 2017 AND A .REPSTATUSID = 1 AND A .DEPOSITID = "+d[0].toString()+") AND D . TYPE = 3 GROUP BY D .DATA1";
                List<Object[]> resultObj = dao.getNativeSQLResult(sqlQuery, "list");
                JSONObject obj = new JSONObject();
                obj.put("depositname",d[1].toString());
                JSONArray depresult = new JSONArray();
                for (int i=0;i<resultObj.size();i++) {
                    Object[] o = resultObj.get(i);
                    JSONArray detailobj = new JSONArray();
                    for (int j = 0; j < 19; j++) {
                        if (o[j] != null) {
                            detailobj.put(o[j]);
                        } else {
                            detailobj.put(0);
                        }
                    }
                    depresult.put(detailobj);
                }
                obj.put("details",depresult);
                result.put(obj);
            }
            return result.toString();
        }else {
            return null;
        }
    }
}
