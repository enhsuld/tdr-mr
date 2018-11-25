package com.peace.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.peace.users.dao.UserDao;
import com.peace.users.model.FileMeta;
import com.peace.users.model.mram.AnnualRegistration;
import com.peace.users.model.mram.LutMinerals;
import com.peace.users.model.mram.SubLegalpersons;
import com.peace.users.model.mram.SubLicenses;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@RestController
@RequestMapping("/user/upload")
public class ImportDataController {

     
	@Autowired
    private UserDao accountService;

	@Autowired
    private UserDao dao;
         

	
 	
    private PasswordEncoder passwordEncoder() {
    	PasswordEncoder encoder = new BCryptPasswordEncoder();
 		return encoder;
	}

	@Autowired
    public ImportDataController(UserDao accountService) {
         this.accountService = accountService;
    }
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	 LinkedList<FileMeta> files = new LinkedList<FileMeta>();
     FileMeta fileMeta = null;
	
     @RequestMapping(value="/data", method = RequestMethod.POST)
     public @ResponseBody LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) {
  
         //1. build an iterator
          Iterator<String> itr =  request.getFileNames();
          MultipartFile mpf = null;
  
          //2. get each file
          while(itr.hasNext()){
  
              //2.1 get next MultipartFile
              mpf = request.getFile(itr.next()); 
              System.out.println(mpf.getOriginalFilename() +" uploaded! "+files.size());
  
              //2.2 if files > 10 remove the first from the list
              if(files.size() >= 10)
                  files.pop();
  
              //2.3 create new fileMeta
              fileMeta = new FileMeta();
              fileMeta.setFileName(mpf.getOriginalFilename());
              fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
              fileMeta.setFileType(mpf.getContentType());
  
              try {
                 fileMeta.setBytes(mpf.getBytes());
                 System.out.println("###########"+mpf.getOriginalFilename());
               	 String SAVE_DIR = "uploads/";
    			 String appPath = request.getServletContext().getRealPath("");
    			 String savePath = appPath + File.separator + SAVE_DIR + File.separator +"vaio";
    			 File ufile = new File(savePath);
    			 if(!ufile.exists()){	
    				ufile.mkdir();
    			 }
    			 String fileName =mpf.getOriginalFilename();
       			 int mid = fileName.lastIndexOf(".");
       			 String fex = fileName.substring(mid+1,fileName.length());
       	
    			 String renName="uploads/"+fileName;
       			 renName=renName.trim();
       			
       			 System.out.println("renameed====> "+renName);
       			
       			
    			 File filepath = new File(ufile+ File.separator +fileName);
                 
                  // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)            
                 FileCopyUtils.copy(mpf.getBytes(), filepath);
                  
                 InputStream is = new FileInputStream(filepath);
      			 XSSFWorkbook workbook = new XSSFWorkbook(is);
      			 FormulaEvaluator formulaEval = workbook.getCreationHelper().createFormulaEvaluator();
     			 FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
     			 int uexelsheetcount=workbook.getNumberOfSheets();
     			 System.out.println("uexelsheetcount====> "+uexelsheetcount);
     			 
     			 if(uexelsheetcount>0){
     				for (int m = 0;m<uexelsheetcount;m++){
     					XSSFSheet sheet = workbook.getSheetAt(m);
     					
     					 System.out.println("sheetname====> "+sheet.getSheetName());
     					
     					if("From_CMCS_CompanyData".equals(sheet.getSheetName())){
     						XSSFRow inforow=null;
     						
     						System.out.println("sssRow"+sheet.getLastRowNum());
 							for (int row=1;row<=sheet.getLastRowNum();row++){
 								inforow =sheet.getRow(row);
 								
 								System.out.println("sss"+inforow.getCell(0));
 								
 								if(inforow!=null){
 									XSSFCell cell1=null;
 									
 									Integer lpreg=(int) inforow.getCell(0).getNumericCellValue();
 									List<SubLegalpersons> at=(List<SubLegalpersons>) dao.getHQLResult("from SubLegalpersons t where t.lpReg='"+lpreg+"'", "list");
 									
 									if(at.size()>0){
 										
 										SubLegalpersons rs= at.get(0);
 									
 										
 										rs.setLpReg(String.valueOf(lpreg));
 										if(inforow.getCell(1)!=null){
 											XSSFCell cell7=null;
	 										cell7=inforow.getCell(1);
	 										if(cell7!=null){	
	 											switch (cell7.getCellType()) {
												case Cell.CELL_TYPE_NUMERIC:
													rs.setSteteReg(String.valueOf(inforow.getCell(1).getNumericCellValue()));												
													break;
													
												case Cell.CELL_TYPE_STRING:
													rs.setSteteReg(inforow.getCell(1).getStringCellValue());			
													break;
	 											}
	 										}
 										}
 										if(inforow.getCell(2)!=null){
 											rs.setLpType((int) inforow.getCell(2).getNumericCellValue());
 										}
										if(inforow.getCell(3)!=null){
											rs.setLpName(inforow.getCell(3).getStringCellValue());									
 										}
										if(inforow.getCell(4)!=null){
											rs.setLpNameL1(inforow.getCell(4).getStringCellValue());
										}
										if(inforow.getCell(5)!=null){
											rs.setCountryName(inforow.getCell(5).getStringCellValue());
										}
										if(inforow.getCell(7)!=null){
											XSSFCell cell7=null;
	 										cell7=inforow.getCell(7);
	 										if(cell7!=null){	
	 											switch (cell7.getCellType()) {
												case Cell.CELL_TYPE_NUMERIC:
													rs.setPhone(String.valueOf(inforow.getCell(7).getNumericCellValue()));												
													break;
													
												case Cell.CELL_TYPE_STRING:
													rs.setPhone(inforow.getCell(7).getStringCellValue());			
													break;
	 											}
	 										}							
										}
										if(inforow.getCell(8)!=null){
											rs.setFamName(inforow.getCell(8).getStringCellValue());
										}
										if(inforow.getCell(9)!=null){
											rs.setFamNameL1(inforow.getCell(9).getStringCellValue());
										}
										if(inforow.getCell(10)!=null){
											rs.setGivName(inforow.getCell(10).getStringCellValue());
										}
										if(inforow.getCell(11)!=null){
											rs.setGivNameL1(inforow.getCell(11).getStringCellValue());
										}
										if(inforow.getCell(12)!=null){
											XSSFCell cell7=null;
	 										cell7=inforow.getCell(12);
	 										if(cell7!=null){	
	 											switch (cell7.getCellType()) {
												case Cell.CELL_TYPE_NUMERIC:
													rs.setMobile(String.valueOf(inforow.getCell(12).getNumericCellValue()));												
													break;
													
												case Cell.CELL_TYPE_STRING:
													rs.setMobile(inforow.getCell(12).getStringCellValue());			
													break;
	 											}
	 										}
										}
										if(inforow.getCell(13)!=null){
											rs.setEmail(inforow.getCell(13).getStringCellValue());
										}
 										XSSFCell cell14=null;
 										cell14=inforow.getCell(14);
 										if(cell14!=null){	
 											switch (cell14.getCellType()) {
											case Cell.CELL_TYPE_NUMERIC:
												rs.setAimagid((int) inforow.getCell(14).getNumericCellValue());												
												break;
												
											case Cell.CELL_TYPE_STRING:
												rs.setAimagid(0);			
												break;
 											}
 										}
 										XSSFCell cell15=null;
 										cell15=inforow.getCell(15);
 										if(cell15!=null){									
 											switch (cell15.getCellType()) {
											case Cell.CELL_TYPE_NUMERIC:												
												rs.setSumid((int) inforow.getCell(15).getNumericCellValue());
												break;
												
											case Cell.CELL_TYPE_STRING:
												rs.setSumid(0);			
												break;
 											}
 										}
 										if(inforow.getCell(16)!=null){
 											switch (inforow.getCell(16).getCellType()) {
											case Cell.CELL_TYPE_NUMERIC:
												rs.setKhoroo(String.valueOf(inforow.getCell(16).getNumericCellValue()));												
												break;
												
											case Cell.CELL_TYPE_STRING:
												rs.setKhoroo(inforow.getCell(16).getStringCellValue());			
												break;
 											}
 										}
 										if(inforow.getCell(17)!=null){
											switch (inforow.getCell(17).getCellType()) {
											case Cell.CELL_TYPE_NUMERIC:
												rs.setKhorooL1(String.valueOf(inforow.getCell(17).getNumericCellValue()));												
												break;
												
											case Cell.CELL_TYPE_STRING:
												rs.setKhorooL1(inforow.getCell(17).getStringCellValue());			
												break;
 											}					
										}
										if(inforow.getCell(18)!=null){
											rs.setCity(inforow.getCell(18).getStringCellValue());
										}
										if(inforow.getCell(19)!=null){
											rs.setCityL1(inforow.getCell(19).getStringCellValue());
										}
										if(inforow.getCell(20)!=null){
											rs.setStreet(inforow.getCell(20).getStringCellValue());
										}
										if(inforow.getCell(21)!=null){
											rs.setStreetL1(inforow.getCell(21).getStringCellValue());	
										}
										if(inforow.getCell(22)!=null){
											rs.setPostbox(inforow.getCell(22).getStringCellValue());
										}
										rs.setIspublic(0);
 										dao.PeaceCrud(rs, "SubLegalpersons", "update",  rs.getId(), 0, 0, null);
 										 
 									}
 									else{
 										SubLegalpersons rs= new SubLegalpersons();
 										
 										rs.setLpReg(String.valueOf(lpreg));
 										if(inforow.getCell(1)!=null){
 											XSSFCell cell7=null;
	 										cell7=inforow.getCell(1);
	 										if(cell7!=null){	
	 											switch (cell7.getCellType()) {
												case Cell.CELL_TYPE_NUMERIC:
													rs.setSteteReg(String.valueOf(inforow.getCell(1).getNumericCellValue()));												
													break;
													
												case Cell.CELL_TYPE_STRING:
													rs.setSteteReg(inforow.getCell(1).getStringCellValue());			
													break;
	 											}
	 										}
 										}
 										if(inforow.getCell(2)!=null){
 											rs.setLpType((int) inforow.getCell(2).getNumericCellValue());
 										}
										if(inforow.getCell(3)!=null){
											rs.setLpName(inforow.getCell(3).getStringCellValue());									
 										}
										if(inforow.getCell(4)!=null){
											rs.setLpNameL1(inforow.getCell(4).getStringCellValue());
										}
										if(inforow.getCell(5)!=null){
											rs.setCountryName(inforow.getCell(5).getStringCellValue());
										}
										if(inforow.getCell(7)!=null){
											XSSFCell cell7=null;
	 										cell7=inforow.getCell(7);
	 										if(cell7!=null){	
	 											switch (cell7.getCellType()) {
												case Cell.CELL_TYPE_NUMERIC:
													rs.setPhone(String.valueOf(inforow.getCell(7).getNumericCellValue()));												
													break;
													
												case Cell.CELL_TYPE_STRING:
													rs.setPhone(inforow.getCell(7).getStringCellValue());			
													break;
	 											}
	 										}							
										}
										if(inforow.getCell(8)!=null){
											rs.setFamName(inforow.getCell(8).getStringCellValue());
										}
										if(inforow.getCell(9)!=null){
											rs.setFamNameL1(inforow.getCell(9).getStringCellValue());
										}
										if(inforow.getCell(10)!=null){
											rs.setGivName(inforow.getCell(10).getStringCellValue());
										}
										if(inforow.getCell(11)!=null){
											rs.setGivNameL1(inforow.getCell(11).getStringCellValue());
										}
										if(inforow.getCell(12)!=null){
											XSSFCell cell7=null;
	 										cell7=inforow.getCell(12);
	 										if(cell7!=null){	
	 											switch (cell7.getCellType()) {
												case Cell.CELL_TYPE_NUMERIC:
													rs.setMobile(String.valueOf(inforow.getCell(12).getNumericCellValue()));												
													break;
													
												case Cell.CELL_TYPE_STRING:
													rs.setMobile(inforow.getCell(12).getStringCellValue());			
													break;
	 											}
	 										}
										}
										if(inforow.getCell(13)!=null){
											rs.setEmail(inforow.getCell(13).getStringCellValue());
										}
 										
 										XSSFCell cell14=null;
 										cell14=inforow.getCell(14);
 										if(cell14!=null){	
 											switch (cell14.getCellType()) {
											case Cell.CELL_TYPE_NUMERIC:
												rs.setAimagid((int) inforow.getCell(14).getNumericCellValue());												
												break;
												
											case Cell.CELL_TYPE_STRING:
												rs.setAimagid(0);			
												break;
 											}
 										}
 										XSSFCell cell15=null;
 										cell15=inforow.getCell(15);
 										if(cell15!=null){									
 											switch (cell15.getCellType()) {
											case Cell.CELL_TYPE_NUMERIC:
												rs.setSumid((int) inforow.getCell(15).getNumericCellValue());												
												break;
												
											case Cell.CELL_TYPE_STRING:
												rs.setSumid(0);			
												break;
 											}
 										}	
 										if(inforow.getCell(16)!=null){
 											switch (inforow.getCell(16).getCellType()) {
											case Cell.CELL_TYPE_NUMERIC:
												rs.setKhoroo(String.valueOf(inforow.getCell(16).getNumericCellValue()));												
												break;
												
											case Cell.CELL_TYPE_STRING:
												rs.setKhoroo(inforow.getCell(16).getStringCellValue());			
												break;
 											}
 										}
										if(inforow.getCell(17)!=null){
											switch (inforow.getCell(17).getCellType()) {
											case Cell.CELL_TYPE_NUMERIC:
												rs.setKhorooL1(String.valueOf(inforow.getCell(17).getNumericCellValue()));												
												break;
												
											case Cell.CELL_TYPE_STRING:
												rs.setKhorooL1(inforow.getCell(17).getStringCellValue());			
												break;
 											}					
										}
										if(inforow.getCell(18)!=null){
											rs.setCity(inforow.getCell(18).getStringCellValue());
										}
										if(inforow.getCell(19)!=null){
											rs.setCityL1(inforow.getCell(19).getStringCellValue());
										}
										if(inforow.getCell(20)!=null){
											rs.setStreet(inforow.getCell(20).getStringCellValue());
										}
										if(inforow.getCell(21)!=null){
											rs.setStreetL1(inforow.getCell(21).getStringCellValue());	
										}
										if(inforow.getCell(22)!=null){
											rs.setPostbox(inforow.getCell(22).getStringCellValue());
										}
 										 										
 										rs.setIspublic(0);
 										rs.setMSURVEYOR("");
 										rs.setConfirmed(false);
 										dao.PeaceCrud(rs, "SubLegalpersons", "save", (long) 0, 0, 0, null);
 									}
 									
 						
 								}							
 							
 							}
     					}
     					else if("From_CMCS_LicenseData".equals(sheet.getSheetName())){
     						XSSFRow inforow=null;
     						
     						System.out.println("sss"+sheet.getLastRowNum());
 							for (int row=1;row<=sheet.getLastRowNum();row++){
 								inforow =sheet.getRow(row);
 								
 								System.out.println("sss"+inforow.getCell(0));
 								
 								if(inforow!=null){
 									XSSFCell cell1=null;
 									
 									Integer licenseNum=(int) inforow.getCell(0).getNumericCellValue();
 									String mineral= inforow.getCell(16).getStringCellValue().trim();
 									Integer lpreg= (int) inforow.getCell(4).getNumericCellValue();
 									System.out.println("sss"+mineral);
 									
 									System.out.println("licnum"+licenseNum);
 									
 									String[] parts=null;
 									String minNew=null;
 									
 									if(mineral.contains(",")){
 										parts = mineral.split(",");
 										minNew=parts[0];
 									}
 									else{
 										minNew=mineral;
 									}
 									
 									System.out.println(minNew);
 									
 									List<SubLicenses> at=null;
 									
 									if(licenseNum>0){
 										at=(List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.licenseNum='"+licenseNum+"'", "list");
 									}
 									
 									List<LutMinerals> mn = null;
 									if(mineral!=null){
 										mn=(List<LutMinerals>) dao.getHQLResult("from LutMinerals t where t.mineralnameeng='"+minNew+"'", "list");
 									}
 									
 									List<SubLegalpersons> sub=(List<SubLegalpersons>) dao.getHQLResult("from SubLegalpersons t where t.lpReg='"+lpreg+"'", "list");
 									if(at.size()>0 && sub.get(0)!=null){
 										
 											SubLicenses rs= at.get(0);
 							 				
 	 										rs.setLicenseNum(licenseNum);
 	 										rs.setLicenseXB(inforow.getCell(1).getStringCellValue());
 	 										rs.setLicTypeId((int) inforow.getCell(2).getNumericCellValue());
 	 										rs.setLicStatusId((int) inforow.getCell(3).getNumericCellValue());
 	 										rs.setLpReg(String.valueOf(lpreg));
 	 										
 	 										
 	 										XSSFCell cell14=null;
 	 										cell14=inforow.getCell(6);
 	 										if(cell14!=null){	
 	 											switch (cell14.getCellType()) {
 												case Cell.CELL_TYPE_NUMERIC:
 													rs.setGrantDate(String.valueOf(inforow.getCell(6).getDateCellValue()));									
 													break;
 													
 												case Cell.CELL_TYPE_STRING:
 												//	rs.setGrantDate(String.valueOf(inforow.getCell(6).getDateCellValue()));	
 													break;
 	 											}
 	 										}
 	 										XSSFCell cell15=null;
 	 										cell15=inforow.getCell(7);
 	 										if(cell15!=null){									
 	 											switch (cell15.getCellType()) {
 												case Cell.CELL_TYPE_NUMERIC:
 													rs.setExpDate(String.valueOf(inforow.getCell(7).getDateCellValue()));											
 													break;
 													
 												case Cell.CELL_TYPE_STRING:
 												//	rs.setExpDate(String.valueOf(inforow.getCell(7).getDateCellValue()));
 													break;
 	 											}
 	 										}
 	 										rs.setLpName(sub.get(0).getLpName()); 		
 	 										if(inforow.getCell(8)!=null){
 	 											rs.setAreaSize((float) inforow.getCell(8).getNumericCellValue());
 	 										}
 	 										
 	 										rs.setLocationAimag(inforow.getCell(9).getStringCellValue());
 	 										rs.setLocationSoum(inforow.getCell(11).getStringCellValue());
 	 										rs.setAreaNameMon(inforow.getCell(13).getStringCellValue());
 	 										rs.setAreaNameEng(inforow.getCell(12).getStringCellValue());
 	 										if(mn!=null){
 	 											if(mn.size()>0){
 	 												rs.setMintype((long )mn.get(0).getId());
 	 	 											if(inforow.getCell(3).getNumericCellValue()==2 && mn.get(0).getId()==5){
 	 	 	 											rs.setDivisionId((long) 2);
 	 	 	 											rs.setPlan(false);
	 	 												rs.setReport(false);
 	 	 	 										}
 	 	 											else if(inforow.getCell(3).getNumericCellValue()==2 && mn.get(0).getId()!=5){
 	 	 												rs.setDivisionId((long) 1);
 	 	 												/*rs.setPlan(true);
 	 	 												rs.setReport(true);*/
 	 	 												rs.setPlan(false);
 	 	 												rs.setReport(false);
 	 	 											}
 	 	 											else if(inforow.getCell(3).getNumericCellValue()==1){
 	 	 												rs.setDivisionId((long) 3);
 	 	 											}
 	 											}
 	 											
 	 										}
 	 										else{
 	 											rs.setDivisionId((long) 3);
 	 										}
 	 										rs.setHaiguulreport(false);
 	 										
 	 										//rs.setRadioactiveFlagId((int) inforow.getCell(15).getNumericCellValue());
 	 										
 	 										dao.PeaceCrud(rs, "SubLegalpersons", "update",  rs.getId(), 0, 0, null);
 																				
 										 
 									}
 									else{
 										SubLicenses rs= new SubLicenses();
 						
 										rs.setLicenseNum(licenseNum);
 										rs.setLicenseXB(inforow.getCell(1).getStringCellValue());
 										if(inforow.getCell(2)!=null){
 											rs.setLicTypeId((int) inforow.getCell(2).getNumericCellValue());
 										}
 										if(inforow.getCell(3)!=null){
 											rs.setLicStatusId((int) inforow.getCell(3).getNumericCellValue());
 										}
 										
 										rs.setLpReg(String.valueOf(lpreg));
 										
 										XSSFCell cell14=null;
 										cell14=inforow.getCell(6);
 										if(cell14!=null){	
 											switch (cell14.getCellType()) {
											case Cell.CELL_TYPE_NUMERIC:
												rs.setGrantDate(String.valueOf(inforow.getCell(6).getDateCellValue()));									
												break;
												
											case Cell.CELL_TYPE_STRING:
											//	rs.setGrantDate(String.valueOf(inforow.getCell(6).getDateCellValue()));	
												break;
 											}
 										}
 										XSSFCell cell15=null;
 										cell15=inforow.getCell(7);
 										if(cell15!=null){									
 											switch (cell15.getCellType()) {
											case Cell.CELL_TYPE_NUMERIC:
												rs.setExpDate(String.valueOf(inforow.getCell(7).getDateCellValue()));											
												break;
												
											case Cell.CELL_TYPE_STRING:
											//	rs.setExpDate(String.valueOf(inforow.getCell(7).getDateCellValue()));
												break;
 											}
 										}
 										rs.setLpName(sub.get(0).getLpName()); 		
 										if(inforow.getCell(8)!=null){
 											rs.setAreaSize((float) inforow.getCell(8).getNumericCellValue());
 										}
 										
 										rs.setLocationAimag(inforow.getCell(9).getStringCellValue());
 										rs.setLocationSoum(inforow.getCell(11).getStringCellValue());
 										rs.setAreaNameMon(inforow.getCell(13).getStringCellValue());
 										rs.setAreaNameEng(inforow.getCell(12).getStringCellValue());
 										if(mn!=null){
 											if(mn.size()>0){
 												rs.setMintype((long )mn.get(0).getId());
 	 											if(inforow.getCell(3).getNumericCellValue()==2 && mn.get(0).getId()==5){
 	 	 											rs.setDivisionId((long) 2);
 	 	 											rs.setPlan(true);
 	 												rs.setReport(true);
 	 	 										}
 	 											else if(inforow.getCell(3).getNumericCellValue()==2 && mn.get(0).getId()!=5){
 	 												rs.setDivisionId((long) 1);
 	 												rs.setPlan(true);
 	 												rs.setReport(true);
 	 											}
 	 											else if(inforow.getCell(3).getNumericCellValue()==1){
 	 												rs.setDivisionId((long) 3);
 	 											}
 											}
 											
 										}
 										else{
 											rs.setDivisionId((long) 3);
 										}
 										rs.setHaiguulreport(false);
 										rs.setWeekly(false);
 										rs.setMonthly(false);
 										rs.setPlan(false);
 										rs.setReport(false);
 										rs.setRedemptionplan(false);
 										rs.setRedemptionreport(false);
 										rs.setConfigured(0);
 										dao.PeaceCrud(rs, "SubLegalpersons", "save", (long) 0, 0, 0, null);
 									}
 									
 						
 								}							
 							
 							}
     					}
     					else if("ANNUAL_REGISTRATION".equals(sheet.getSheetName())){
     						XSSFRow inforow=null;
     						
 							for (int row=1;row<=sheet.getLastRowNum()-1;row++){
 								inforow =sheet.getRow(row);
 								
 								System.out.println("sss"+inforow.getCell(0));
 								
 								if(inforow!=null){
 									XSSFCell cell1=null;
 									XSSFCell cell14=inforow.getCell(0);
 									int licenseNum=0;
 									if(cell14!=null){	
										switch (cell14.getCellType()) {
									case Cell.CELL_TYPE_NUMERIC:
										licenseNum=(int)	inforow.getCell(0).getNumericCellValue();				
										break;
										
									case Cell.CELL_TYPE_STRING:
										licenseNum=	Integer.parseInt(inforow.getCell(0).getStringCellValue());		
										break;
										}
									}
 									
 									String subdate= "";
 									XSSFCell cell7=inforow.getCell(7);
 									if(cell7!=null){	
										switch (cell7.getCellType()) {
									case Cell.CELL_TYPE_NUMERIC:
										subdate=	String.valueOf(inforow.getCell(7).getNumericCellValue());				
										break;
										
									case Cell.CELL_TYPE_STRING:
										subdate=	inforow.getCell(7).getStringCellValue();		
										break;
										}
									}
 									
 									
 									System.out.println("licenseNum====> "+licenseNum);
 									System.out.println("subdate====> "+subdate);					 									
 									AnnualRegistration lr=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id='"+licenseNum+"'", "current");
 									
 									if(lr!=null){
 										lr.setSubmissiondate(subdate); 	 									
 	 									dao.PeaceCrud(lr, "AnnualRegistration", "update",  lr.getId(), 0, 0, null);
 									} 									
 								}	
 							}
     					}
     				}
     			}
  
             } catch (IOException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
              //2.4 add to files
              files.add(fileMeta);
          }
         // result will be like this
         // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
         return files;
     }
     
     @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
     public void get(HttpServletResponse response,@PathVariable String value){
         FileMeta getFile = files.get(Integer.parseInt(value));
         try {      
                response.setContentType(getFile.getFileType());
                response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");
                FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
         }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
         }
     }
	
/*	@RequestMapping(value = "/user/upload/data", method = RequestMethod.POST)
    public @ResponseBody String content(@RequestParam MultipartFile files[], HttpServletRequest req) {
	 
		System.out.println("assa"+files[0].getOriginalFilename());
		try{
			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User loguser=(User) dao.getHQLResult("from User t where t.username='"+userDetail.getUsername()+"'", "current");
			
			String SAVE_DIR = "uploads/";
			String appPath = req.getServletContext().getRealPath("");
			String savePath = appPath + File.separator + SAVE_DIR;
			 File ufile = new File(savePath);
			if(!ufile.exists()){	
				ufile.mkdir();
			}
			String fileName =files[0].getOriginalFilename();
   			int mid = fileName.lastIndexOf(".");
   			String fex = fileName.substring(mid+1,fileName.length());
   	
			String renName="uploads/"+fileName;
   			renName=renName.trim();
   			
   			System.out.println("renameed====> "+renName);
   			
   			
			File filepath = new File(ufile+"/"+fileName);
			
			files[0].transferTo(filepath);
			
			String savePath1 = appPath + File.separator + renName;
			
			File logorenamed = new File(savePath1);
			
			String lastFilePath = null;
			
			if(filepath.renameTo(logorenamed)){
				lastFilePath=renName;
	
			}
			else {
				System.out.println("came here");
				//return null;
			}
				
			InputStream is = new FileInputStream(logorenamed);
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			FormulaEvaluator formulaEval = workbook.getCreationHelper().createFormulaEvaluator();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int uexelsheetcount=workbook.getNumberOfSheets();
			if(uexelsheetcount>0){
				for (int m = 0;m<uexelsheetcount;m++){
					XSSFSheet sheet = workbook.getSheetAt(m);
					
					System.out.println("sss"+sheet.getLastRowNum());
					
					if("SHEET1".equals(sheet.getSheetName().toUpperCase())){
						if(sheet.getLastRowNum()>=5){
							XSSFRow inforow=null;
							for (int row=4;row<=sheet.getLastRowNum()-4;row++){
								inforow =sheet.getRow(row);
								if(inforow!=null){
									XSSFCell cell1=null;
									User rs= new User();

									XSSFCell cell3=null;
									cell3=inforow.getCell(3);
									if(cell3!=null){									
										rs.setStudentCoursenameId(inforow.getCell(3).getStringCellValue());
									}
									XSSFCell cell4=null;
									cell4=inforow.getCell(4);
									if(cell4!=null){									
										rs.setStudentCourse((int) inforow.getCell(4).getNumericCellValue());
									}
									XSSFCell cell5=null;
									cell5=inforow.getCell(5);
									if(cell5!=null){	
										String fl=inforow.getCell(5).getStringCellValue();
										String[] parts = fl.split(" ");
										String part1 = parts[0]; // 004
										String part2 = parts[1]; // 034556
										
										rs.setStudentFirstname(part1);
										rs.setStudentLastname(part2);
									}
									XSSFCell cell6=null;
									cell6=inforow.getCell(6);
									if(cell6!=null){									
										rs.setStudentRd(inforow.getCell(6).getStringCellValue());
									}
									XSSFCell cell7=null;
									cell7=inforow.getCell(7);
									if(cell7!=null){									
										rs.setStudentMobilephone(String.valueOf(inforow.getCell(7).getNumericCellValue()));
									}
									XSSFCell cell8=null;
									cell8=inforow.getCell(8);
									if(cell8!=null){									
										rs.setStudentEmail(inforow.getCell(8).getStringCellValue());
									}
									XSSFCell cell9=null;
									cell9=inforow.getCell(9);
									if(cell9!=null){	
										
									}
									XSSFCell cell10=null;
									cell10=inforow.getCell(10);
									if(cell10!=null){									
										rs.setStudentCurrentaddress(inforow.getCell(10).getStringCellValue());
									}
									
									
									dao.PeaceCrud(rs, "User", "save", (long) 0, 0, 0, null);
									
									PUserRoleRel rl=new PUserRoleRel();
									rl.setRoleid(98);
									rl.setUserid(rs.getId());
									dao.PeaceCrud(rl, "PUserRoleRel", "save", (long) 0, 0, 0, null);
									   
								}							
							
								else {
									System.out.println("aldaa");
									return null;
								}
							}
						}
						else {
							System.out.println("oyutnii medeelel alga");
							return null;
						}
					}
				}
			}
	    } catch (FileNotFoundException ec) {
	        ec.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
			
	    return null;
   
    }*/

}