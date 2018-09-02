package com.peace.web.logic.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import org.w3c.dom.Element;
import com.peace.users.model.User;
import com.peace.users.model.mram.AnnualRegistration;
import com.peace.users.model.mram.LnkComment;
import com.peace.users.model.mram.LnkCommentMain;
import com.peace.users.model.mram.LnkPlanAttachedFiles;
import com.peace.users.model.mram.LnkPlanNotes;
import com.peace.users.model.mram.LnkPlanTab;
import com.peace.users.model.mram.LnkPlanTransition;
import com.peace.users.model.mram.LnkReportRegBunl;
import com.peace.users.model.mram.LnkReqAnn;
import com.peace.users.model.mram.LutDecisions;
import com.peace.users.model.mram.LutDeposit;
import com.peace.users.model.mram.LutFormNotes;
import com.peace.users.model.mram.LutFormindicators;
import com.peace.users.model.mram.LutForms;
import com.peace.users.model.mram.LutUsers;
import com.peace.users.model.mram.RegWeeklyMontly;
import com.peace.users.model.mram.SubLicenses;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.XfaForm;
import com.peace.users.dao.UserDao;
import com.peace.users.model.Tbstudentdetail;
import com.sun.mail.handlers.multipart_mixed;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Controller
@RequestMapping("/logic")
public class PdfReportController {
	@Autowired
	UserDao dao;

	public static String PREFACE = "";

	@RequestMapping(value="/exportAnnualRegistration/{type}/{year}",method=RequestMethod.GET)
	public void exportMateriallag(@PathVariable long type,@PathVariable long year, HttpServletRequest req,HttpServletResponse response) throws JSONException, DocumentException, Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
			FileInputStream fis = null;
			String appPath = req.getServletContext().getRealPath("");	
			File files = new File(appPath+"/assets/reporttemplate/Export-hiih-tolgoi.xlsx");

			fis = new FileInputStream(files);
			Workbook workbook = new XSSFWorkbook(fis);

			XSSFSheet sheet = null;

			for(int i=workbook.getNumberOfSheets()-1;i>=0;i--){
				XSSFSheet tmpSheet =(XSSFSheet) workbook.getSheetAt(i);
				if(!tmpSheet.getSheetName().equals("МХЕГ-т")){
					workbook.removeSheetAt(i);
				}
				else{
					sheet = tmpSheet;
				}
			}

			if(sheet!=null && loguser!=null){
				List<AnnualRegistration> list = null;
				//int year = Calendar.getInstance().get(Calendar.YEAR);
				if (type == 1){
					list =  (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.repstatusid=7 and t.reporttype=3 and t.divisionid = " + loguser.getDivisionid() + " and t.xtype!=0 and t.reportyear = '" + year + "'", "list");
				}
				else if (type == 2){
					list =  (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.repstatusid=2 and t.reporttype=3 and t.divisionid = " + loguser.getDivisionid() + " and t.xtype!=0 and t.rejectstep is not null and t.reportyear = '" + year + "'", "list");
				}
				else if (type == 3){
					list =  (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.repstatusid=7 and t.reporttype=3 and t.divisionid = " + loguser.getDivisionid() + " and t.xtype=0 and t.reportyear = '" + year + "'", "list");
				}
				else if (type == 4){
					list =  (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.repstatusid=2 and t.reporttype=3 and t.divisionid = " + loguser.getDivisionid() + " and t.xtype = 0 and t.rejectstep is not null and t.reportyear = '" + year + "'", "list");
				}
				else if (type == 5){

					String queryStr = "from AnnualRegistration t where t.repstatusid=1 and t.reporttype=3 and t.divisionid = " + loguser.getDivisionid() + " and t.reportyear = '" + year + "'";
					if (loguser.getDivisionid() == 3){
						queryStr = queryStr + " and lictype=1 and groupid=1";
					} 
					else if (loguser.getDivisionid() == 2){
						queryStr = queryStr + " and minid=5 and lictype=2";
					}
					else if (loguser.getDivisionid() == 1){
						queryStr = queryStr + " and minid!=5 and lictype=2 and groupid=1";
					}
					list =  (List<AnnualRegistration>) dao.getHQLResult(queryStr, "list");
				}
				else if (type == 6){
					String queryStr = "from AnnualRegistration t where t.repstatusid = 2 and t.rejectstep!=0 and t.reject!=0 and t.divisionid = " + loguser.getDivisionid() + " and reporttype = 3";
					list =  (List<AnnualRegistration>) dao.getHQLResult(queryStr, "list");
				}
				else if (type == 7){
					String queryStr = "from AnnualRegistration t where t.repstatusid = 2 and t.rejectstep!=0 and t.reject!=0 and t.divisionid = " + loguser.getDivisionid() + " and reporttype = 4";
					list =  (List<AnnualRegistration>) dao.getHQLResult(queryStr, "list");
				}
				
				else if (type == 8){
					String queryStr = "from AnnualRegistration t where t.repstatusid = 2 and t.rejectstep!=0 and t.reject!=0 and t.divisionid = " + loguser.getDivisionid();
					list =  (List<AnnualRegistration>) dao.getHQLResult(queryStr, "list");
				}
				if (list != null && !list.isEmpty()){
					int rowId = 3;
					XSSFRow titleRow = sheet.getRow(1);
					titleRow.getCell(0).setCellValue(year + " онд үйл ажиллагаа явуулахаар УАТөлөвлөгөө ирүүлсэн аж ахуйн нэгжүүдийн жагсаалт");
					for(AnnualRegistration item : list){
						Row row = sheet.getRow(rowId);
						row.getCell(0).setCellValue(rowId - 2);
						row.getCell(1).setCellValue(item.getLpName());
						row.getCell(2).setCellValue(item.getLicenseXB());
						if (item.getDepositid() != null){
							LutDeposit deposit=(LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+item.getDepositid()+"'", "current");
							row.getCell(3).setCellValue((deposit != null) ? deposit.getDepositnamemon() : "");
						}
						
						LnkReqAnn reqann=(LnkReqAnn) dao.getHQLResult("from LnkReqAnn t where t.reqid='"+item.getReqid()+"'", "current");
						row.getCell(4).setCellValue((reqann != null) ? reqann.getAimagid() : "");
						row.getCell(5).setCellValue((reqann != null) ? reqann.getSumid() : "");
						row.getCell(6).setCellValue((reqann != null) ? reqann.getHorde() : "");
						rowId++;
					}
				}
				String xname=year + " онд үйл ажиллагаа явуулахаар УАТөлөвлөгөө ирүүлсэн аж ахуйн нэгжүүдийн жагсаалт";
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
	}

	public static void limitedPdf(ServletOutputStream out,HttpServletRequest req, int plan, int id) throws Exception {

		/*        try { 
        	String SAVE_DIR = "resources//assets";
    		String appPath = req.getServletContext().getRealPath("");
    		String savePath = appPath + File.separator + SAVE_DIR+"//wordTemplates//";

    		PREFACE = "D://temp//";
    		//File file = new File("D://temp//itext-test.pdf");
			//FileOutputStream fileout = out;
    		String pdfname="";
    		if(tablename.equalsIgnoreCase("STS_A1_DATA")){
    			pdfname="T_A-1.pdf";
    		}
    		else if(tablename.equalsIgnoreCase("STS_A2_DATA")){
    			pdfname="T_A-2.pdf";
    		}
    		else if(tablename.equalsIgnoreCase("STS_A3_DATA")){
    			pdfname="T_A-3.pdf";
    		}
    		PdfReader reader = new PdfReader(savePath+pdfname);
	        PdfStamper stamper = new PdfStamper(reader,out);
	      //  AcroFields form = stamper.getAcroFields();
	    	//form.setField("AuditCode", "test");
	        form.setField("text_1", "Bruno Lowagie");
	        form.setFieldProperty("text_2", "fflags", 0, null);
	        form.setFieldProperty("text_2", "bordercolor", BaseColor.RED, null);
	        form.setField("text_2", "bruno");
	        form.setFieldProperty("text_3", "clrfflags", TextField.PASSWORD, null);
	        form.setFieldProperty("text_3", "setflags", PdfAnnotation.FLAGS_PRINT, null);
	        form.setField("text_3", "12345678", "xxxxxxxx");
	        form.setFieldProperty("text_4", "textsize", new Float(12), null);
	        form.regenerateField("text_4");
	        stamper.close();
	        reader.close();


    		// Open a document 
    		PdfReader reader = new PdfReader(savePath+"T_A-1.pdf");

    		AcroFields form = reader.getAcroFields();
    		form.setField("AuditCode", "lallala");


    		   PdfReader reader = new PdfReader(savePath+"T_A-1.pdf");
    	        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(PREFACE));
    	        AcroFields form = stamper.getAcroFields();
    	        form.setField("AuditCode", "lallala");
    	        form.setField("text_1", "Bruno Lowagie");
    	        form.setFieldProperty("text_2", "fflags", 0, null);
    	        form.setFieldProperty("text_2", "bordercolor", BaseColor.RED, null);
    	        form.setField("text_2", "bruno");
    	        form.setFieldProperty("text_3", "clrfflags", TextField.PASSWORD, null);
    	        form.setFieldProperty("text_3", "setflags", PdfAnnotation.FLAGS_PRINT, null);
    	        form.setField("text_3", "12345678", "xxxxxxxx");
    	        form.setFieldProperty("text_4", "textsize", new Float(12), null);
    	        form.regenerateField("text_4");
    	        stamper.close();
    	        reader.close();
    		com.aspose.pdf.Document pdfDocument = new com.aspose.pdf.Document(savePath+"T_A-1.pdf");

    		// Get a field
    		com.aspose.pdf.TextBoxField textBoxField = (com.aspose.pdf.TextBoxField)pdfDocument.getForm().get("AuditCode");

    		//Set the field value
    		textBoxField.setValue("Value of TextField 13213");

    		// Save the updated document



        	 if(repid==1){
        		 Document doc =new Document(savePath+"T_A-1.pdf");
        		 int organizationid=(int) refresh.get("orgid");
                 System.out.println("org id"+organizationid);       	            

                 DataSet pizzaDs = new DataSet();           
                 DataTable itemDetails = new DataTable(sql, "Items");
                 pizzaDs.getTables().add(itemDetails);
                 doc.getMailMerge().executeWithRegions(pizzaDs);
                 if(organizationid==2){
             	 	doc.getMailMerge().execute(new String[]{"OrgName","AuditYear","AuditOrgName","LeadingAuditor","AuditorName","SysDate","ManagerName"}, new Object[]{refresh.get("orgname"),refresh.get("year"),refresh.get("rootOrg"),refresh.get("LeaderAuditor"),refresh.get("auditor"),refresh.get("sysdate"),refresh.get("manager")});
             	 }
             	 else{
             		doc.getMailMerge().execute(new String[]{"OrgName","AuditYear","AuditOrgName","LeadingAuditor","AuditorName","SysDate","ManagerName"}, new Object[]{refresh.get("orgname"),refresh.get("year"),refresh.get("rootOrg"),refresh.get("LeadingAuditor"),refresh.get("auditor"),refresh.get("sysdate"),refresh.get("manager")});
             	 }             	
             	 doc.getWriteProtection();             	
                 doc.save(out, com.aspose.words.SaveFormat.DOC);
            }

        	System.out.println("end irchihlee");
        } catch (Exception e) {
            throw new Exception("Aspose: Unable to export to ms word format.. some error occured",e);

        }
    }*/
	}
}
