package com.peace.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import com.peace.users.model.mram.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.pdf.PdfReader;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.peace.users.model.DataSourceResult;
import com.peace.users.dao.FileBean;
import com.peace.users.dao.UserDao;
import com.peace.users.model.AccountList;
import com.peace.users.model.Excel;
import com.peace.users.model.FileMeta;
import com.peace.users.model.PUserRoleRel;
import com.peace.users.model.PUserRoles;
import com.peace.users.model.Pmenu;
import com.peace.users.model.Role;
import com.peace.users.model.Tbbranches;
import com.peace.users.model.Tbdepartment;
import com.peace.users.model.User;
import com.peace.users.model.Tbuniversities;
import com.peace.users.model.UserAuthority;
import com.peace.users.service.MyUserDetailsService;

import java.io.BufferedInputStream; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.InputStream; 
import java.util.Iterator; 

import org.apache.poi.poifs.filesystem.POIFSFileSystem; 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger.OutputField;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet; 
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;

@RestController
public class PdfController {

	@Autowired
    private UserDao dao;
         
	public static String PREFACE = "";
	
	@RequestMapping(value="/word/mid/{id}",method=RequestMethod.GET)
    public void exportOrgInfo(@PathVariable long id, HttpServletRequest req,HttpServletResponse response) throws JSONException, DocumentException, Exception {

		String appPath = req.getServletContext().getRealPath("");	
		FileInputStream wis = null;
		File wordFile = null;
		AnnualRegistration main = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id='"+id+"'", "current");
		SubLegalpersons sb = main.getSubLegalpersons();
		int year=Integer.parseInt(main.getReportyear())-1;
		List<LnkReqAnn> rss=(List<LnkReqAnn>) dao.getHQLResult("from LnkReqAnn t where t.reqid="+main.getReqid()+"  order by t.id desc", "list");
		LnkReqAnn rs = null;
		if (rss != null && rss.size() > 0){
			rs  = rss.get(0);
		}
		List<LnkCommentMain> cm= (List<LnkCommentMain>) dao.getHQLResult("from LnkCommentMain t where t.planid="+main.getId()+" and t.userid is not null and (t.isgov = 0 or t.isgov is null) order by t.id desc", "list");
        LutUsers loguser = null;

		if (cm.size() > 0){
            loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.id='"+cm.get(0).getUserid()+"'", "current");
        }

		if(main.getDivisionid()==1){
		    if (main.getReporttype() == 3){
                wordFile=new File(appPath+"/assets/reporttemplate/minCoverPlan.docx");
            }
            else if (main.getReporttype() == 4) {
                wordFile=new File(appPath+"/assets/reporttemplate/minCoverRep.docx");
            }
		}
		else if(main.getDivisionid()==2){
			wordFile=new File(appPath+"/assets/reporttemplate/NuurCoal.docx");  
		}
        else if(main.getDivisionid()==3){
            if (main.getReporttype() == 3){
                wordFile=new File(appPath+"/assets/reporttemplate/NuurGeoPlan.docx");
            }
            else if (main.getReporttype() == 4) {
                wordFile=new File(appPath+"/assets/reporttemplate/NuurGeoReport.docx");
            }
        }
		boolean mergedOutput = false;
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(wordFile);
		List<Map<DataFieldName, String>> data = new ArrayList<Map<DataFieldName, String>>();
	
		// Instance 1
		Map<DataFieldName, String> map = new HashMap<DataFieldName, String>();
	
		if(rs==null){
			SubLicenses lic = (SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='"+main.getLicensenum()+"'", "current");
			map.put(new DataFieldName("aname"), lic.getLocationAimag().toUpperCase());
			map.put(new DataFieldName("sname"), lic.getLocationSoum().toUpperCase());
			map.put(new DataFieldName("oname"), lic.getAreaNameMon().toUpperCase());
		}
		else{
			map.put(new DataFieldName("aname"), rs.getAimagid().toUpperCase());
			map.put(new DataFieldName("sname"), rs.getSumid().toUpperCase());
			map.put(new DataFieldName("oname"), rs.getHorde().toUpperCase());
            RegReportReq reqObj = rs.getRegReportReq();
            if (reqObj!=null){
                map.put(new DataFieldName("tz_nemelt"), String.valueOf(reqObj.getAddBunLicenseNum()));
            }
		}
		// Instance 2
		//map = new HashMap<DataFieldName, String>();
		map.put(new DataFieldName("Org_name"), sb.getLpName());
		
		map.put(new DataFieldName("t_year"), main.getReportyear());
		map.put(new DataFieldName("tz"), main.getLicenseXB());
		map.put(new DataFieldName("geng"), sb.getGENGINEER());
		map.put(new DataFieldName("accountant"), sb.getACCOUNTANT());
		map.put(new DataFieldName("economist"), sb.getECONOMIST());
		map.put(new DataFieldName("Year"), main.getReportyear());

		if(sb.getFamName()!=null){
			map.put(new DataFieldName("Dir_name"), sb.getFamName().substring(0, 1)+"."+sb.getGivName());
		}

		if (loguser != null){
            if(loguser.getFamnamemon()!=null){
                map.put(new DataFieldName("gvd"), loguser.getFamnamemon().substring(0, 1)+"."+loguser.getGivnamemon());
            }else{
                map.put(new DataFieldName("gvd"), loguser.getGivnamemon());
            }
        }

		
		if(main.getDivisionid()==2 || main.getDivisionid() == 1){
			LutMinerals dep=(LutMinerals) dao.getHQLResult("from LutMinerals t where t.id='"+main.getMinid()+"'", "current");
			map.put(new DataFieldName("deposit"), dep.getMineralnamemon());
		}
		else if (main.getDivisionid()==3){
            LutMinGroup dep=(LutMinGroup) dao.getHQLResult("from LutMinGroup t where t.id='"+main.getGroupid()+"'", "current");
            map.put(new DataFieldName("deposit"), dep.getGroupname());
        }
		
		map.put( new DataFieldName("selected_mat_suuri"), "Jason");
		map.put(new DataFieldName("mat_suuri_huvi"), "Collins Street");
		map.put(new DataFieldName("mat_suuri_dun"), "12/10/2013");
		map.put(new DataFieldName("audit_chiglel_count_4.2"), "1234800");
		map.put(new DataFieldName("audit_chiglel_count_4.2"), "Jason");
		map.put(new DataFieldName("audit_risk_count_4.2"), "Collins Street");
		map.put(new DataFieldName("member1"), "12/10/2013");
		map.put(new DataFieldName("mem1_alba"), "1234800");
		
			map.put(new DataFieldName("member2"), "Jason");
		map.put(new DataFieldName("mem2_alba"), "Collins Street");
		map.put(new DataFieldName("member3"), "12/10/2013");
		map.put(new DataFieldName("mem3_alba"), "1234800");
		data.add(map);		
		
		String xname=sb.getLpName().trim();
		xname = URLEncoder.encode(xname,"UTF-8"); 
		
		if (mergedOutput) {
			/*
			 * This is a "poor man's" merge, which generates the mail merge  
			 * results as a single docx, and just hopes for the best.
			 * Images and hyperlinks should be ok. But numbering 
			 * will continue, as will footnotes/endnotes.
			 *  
			 * If your resulting documents aren't opening in Word, then
			 * you probably need MergeDocx to perform the merge.
			 */
	
			// How to treat the MERGEFIELD, in the output?
			org.docx4j.model.fields.merge.MailMerger.setMERGEFIELDInOutput(OutputField.KEEP_MERGEFIELD);
			
	//		System.out.println(XmlUtils.marshaltoString(wordMLPackage.getMainDocumentPart().getJaxbElement(), true, true));
			
			WordprocessingMLPackage output = org.docx4j.model.fields.merge.MailMerger.getConsolidatedResultCrude(wordMLPackage, data, true);
			
	//		System.out.println(XmlUtils.marshaltoString(output.getMainDocumentPart().getJaxbElement(), true, true));
			
			try (ServletOutputStream outputStream = response.getOutputStream()) {
					response.setContentType("application/ms-word; charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
		            response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+xname+".docx");   
		            output.save(outputStream);  
		            outputStream.close();
		        }
		        catch (Exception e) {
		        	System.out.println("ishe orov");
				}
			
			
		}
		else {
			// Need to keep the MERGEFIELDs. If you don't, you'd have to clone the docx, and perform the
			// merge on the clone.  For how to clone, see the MailMerger code, method getConsolidatedResultCrude
			org.docx4j.model.fields.merge.MailMerger.setMERGEFIELDInOutput(OutputField.KEEP_MERGEFIELD);
			
			int i = 1;
			for (Map<DataFieldName, String> thismap : data) {
				org.docx4j.model.fields.merge.MailMerger.performMerge(wordMLPackage, thismap, true);
				try (ServletOutputStream outputStream = response.getOutputStream()) {
	  				response.setContentType("application/ms-word; charset=UTF-8");
	  				response.setCharacterEncoding("UTF-8");
	  				response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+xname+".docx");   
	  	         	wordMLPackage.save(outputStream);  
	  	            outputStream.close();
	  	        }
	  	        catch (Exception e) {
	  	        	System.out.println("ishe orov");
	  			}
				i++;
			}			
		}
		System.out.println("Mail merge performed successfully.");    
	} 
	
	@RequestMapping(value="/zero/get/{id}/{noteid}",method=RequestMethod.POST)
    public String getZeroFiles(@PathVariable long id, @PathVariable long noteid, HttpServletRequest req,HttpServletResponse response) throws JSONException, DocumentException, Exception {
		JSONArray result = new JSONArray();
		List<LnkPlanZeroAttachments> rs=(List<LnkPlanZeroAttachments>) dao.getHQLResult("from LnkPlanZeroAttachments y where y.planid = " + id + " and y.noteid = " + noteid, "list");
		if (rs != null && !rs.isEmpty()){
			for(LnkPlanZeroAttachments attach : rs){
				result.put(new JSONObject().put("id", attach.getId()).put("atdate", attach.getAtdate()).put("atturl", attach.getAtturl()).put("fileext", attach.getFileext()).put("filename", attach.getFilename()).put("noteid", attach.getNoteid()));
			}
		}
		return result.toString();
	}
	
	@RequestMapping(value="/zero/deleteAtt/{planid}/{noteid}/{id}",method=RequestMethod.DELETE)
    public Boolean deleteZeroAttach(@PathVariable long id, @PathVariable long noteid,@PathVariable long planid, HttpServletRequest req,HttpServletResponse response) throws JSONException, DocumentException, Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<LnkPlanZeroAttachments> rs=(List<LnkPlanZeroAttachments>) dao.getHQLResult("from LnkPlanZeroAttachments y where y.id = " + id + " and y.planid = " + planid + " and y.noteid = " + noteid, "list");
		if (rs != null && !rs.isEmpty()){
			for(LnkPlanZeroAttachments attach : rs){
				File oldfile = new File(attach.getAtturl());
				oldfile.delete();
				dao.PeaceCrud(null, "LnkPlanZeroAttachments ", "delete", attach.getId(), 0, 0, "id");
			}
			return true;
		}
		return false;
	}
	
	@RequestMapping(value="/zero/submit/final/{planid}",method=RequestMethod.POST)
    public String submitZeroReportFinal(@PathVariable long planid, HttpServletRequest req,HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JSONObject result = new JSONObject();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			
			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
			AnnualRegistration pe=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+planid+"", "current");
			
			if (pe != null){
				List<LnkPlanZeroAttachments> rs=(List<LnkPlanZeroAttachments>) dao.getHQLResult("from LnkPlanZeroAttachments y where y.planid = " + pe.getId(), "list");
				JSONArray missing = new JSONArray();
				if (rs.size() > 0){
					for(int i=1;i<8;i++){
						Boolean isfilled = false;
						for(LnkPlanZeroAttachments a : rs){
							if (a.getNoteid() == i){
								isfilled = true;
							}
						}
						if (!isfilled){
							missing.put(i);
						}
					}
				}
				
				if (missing.length() == 0){
					pe.setLpReg(loguser.getSubLegalpersons().getLpReg());				
					pe.setPersonid(loguser.getId());
					pe.setXtype(0);
					pe.setRepstepid((long) 0);
					pe.setRepstatusid((long) 7);
					dao.PeaceCrud(pe, "AnnualRegistration", "save",(long) planid, 0, 0, null);
					result.put("status", true);
				}
				else{
					result.put("status", false);
					result.put("missing", missing);
				}
			}
		}
		
		return result.toString();
	}
	
	@RequestMapping(value="/logic/zero/save",method=RequestMethod.POST)
	public @ResponseBody String save( @RequestParam("zerofile") MultipartFile zerofile, @RequestParam("planid") Integer planid,@RequestParam("zeroid") Integer zeroid, MultipartRequest mpr, HttpServletRequest req) throws JSONException, DocumentException, Exception {

	     /*firstFile = (MultipartFile)firstFile;
	     System.out.println("upload hiigdeh file "+firstFile.getInputStream());*/
	  	
	     Gson gson= new Gson();
		 JSONObject jo = new JSONObject();
	 	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				
				UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
				
				if (zerofile != null) {
				 	String appPath = req.getSession().getServletContext().getRealPath("");
				 	String SAVE_DIR = "uploads";
					Date d1 = new Date();
					SimpleDateFormat df = new SimpleDateFormat("MM-dd-YYYY");
					String special = df.format(d1);
					
					String outwrapper = appPath + File.separator + SAVE_DIR+ File.separator + loguser.getLpreg();
					File outwrapper1 = new File(outwrapper);
		        		if(!outwrapper1.exists()){
		        			outwrapper1.mkdir();
		    				  System.out.println("end"+outwrapper1);
		    			}
					
	        	    String savePath = appPath + File.separator + SAVE_DIR+ File.separator + loguser.getLpreg()+ File.separator +special;
	        	    System.out.println("de dir"+special);
	        	    File logodestination = new File(savePath);
	        		if(!logodestination.exists()){
	        			logodestination.mkdir();
	    				  System.out.println("end"+logodestination);
	    			}
	        		AnnualRegistration pe=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+planid+"", "current");
	        		List<LnkPlanZeroAttachments> rs=(List<LnkPlanZeroAttachments>) dao.getHQLResult("from LnkPlanZeroAttachments y where y.planid = " + planid + " and y.noteid = " + zeroid, "list");
	        		
	        		if (rs != null && !rs.isEmpty() && (zeroid!=8)){
	        			for(LnkPlanZeroAttachments za : rs){
	        				File oldfile = new File(za.getAtturl());
	        				oldfile.delete();
	        				dao.PeaceCrud(null, "LnkPlanZeroAttachments ", "delete", za.getId(), 0, 0, "id");
	        			}
	        		}
	        		
	        		if (!zerofile.isEmpty()){
        				String lFile = appPath + File.separator + SAVE_DIR+ File.separator + loguser.getLpreg()+ File.separator +special+ File.separator +"("+rs.size()+")"+zerofile.getOriginalFilename();
        				File lfFile = new File(lFile);
        				if(!lfFile.exists()){
        					zerofile.transferTo(lfFile);
    	        			jo.put("return", "false");
    	        		}
        				JSONObject filetemp = new JSONObject();
	        			filetemp.put("url", "uploads"+File.separator+pe.getLpReg()+File.separator+special+File.separator+lfFile.getName());
	        			filetemp.put("name", lfFile.getName());
	        			
	        			LnkPlanZeroAttachments attach = new LnkPlanZeroAttachments();
	        			attach.setAtdate(special);
	        			attach.setAtturl("uploads"+File.separator+pe.getLpReg()+File.separator+special+File.separator+lfFile.getName());
	        			attach.setFileext(FilenameUtils.getExtension("uploads"+File.separator+pe.getLpReg()+File.separator+special+File.separator+lfFile.getName()));
	        			attach.setFilename(lfFile.getName());
	        			attach.setNoteid((long)zeroid);
	        			attach.setPlanid(planid);
	        			dao.PeaceCrud(attach, "LnkPlanZeroAttachments", "save",(long) 0, 0, 0, null);
	        			jo.put("attachment", new JSONObject().put("id", attach.getId()).put("atdate", attach.getAtdate()).put("atturl", attach.getAtturl()).put("fileext", attach.getFileext()).put("filename", attach.getFilename()).put("noteid", attach.getNoteid()));
	        		}
    				
    				jo.put("return", "true");
		                
		        } 
			}
	        return jo.toString();
		
    }
	
	@RequestMapping(value = "/user/upload/data/pdf", method = RequestMethod.POST)
    public @ResponseBody String content(MultipartHttpServletRequest request,  HttpServletRequest req) {
	 
		 LinkedList<FileMeta> files = new LinkedList<FileMeta>();
	     FileMeta fileMeta = null;
			System.out.println("nanana");
		    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		 	Date date = new Date();
			String currentDate = dateFormat.format(date);
			String ufilepath=null;
		    FileBean mfile =  null;
		    String appPath = req.getSession().getServletContext().getRealPath("");
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
	 
	                 // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)            
	                 FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("D:/temp/"+mpf.getOriginalFilename()));
	                 PREFACE = "D:/temp/"+mpf.getOriginalFilename();
	                 
	                
	                /* com.itextpdf.text.pdf.PdfReader pdfTemplate = PdfReader(PREFACE);*/
	           /*      
	                 com.itextpdf.text.pdf.PdfReader reader = new PdfReader(PREFACE);
	                 int n = reader.getNumberOfPages();
	               
	                 String str=PdfTextExtractor.getTextFromPage(reader, 1,null);
	                     System.out.println(str);*/
	                     PdfReader reader = new PdfReader(PREFACE);
	                     int n = reader.getNumberOfPages();
	                     AcroFields pdfTemplate = reader.getAcroFields();
						 //pdfTemplate.get
	           /*    String str=PdfTextExtractor.getTextFromPage(reader, 2); //Extracting the content from a particular page.
	                         System.out.println(str);*/
	                         System.out.println(pdfTemplate.getField("fill_1"));
	    	                 System.out.println(pdfTemplate.getField("fill_2"));
	                 System.out.println();/*
	                 
	                 
	                 System.out.println(pdfTemplate.getAcroFields().getField("fill_1"));
	                 System.out.println(pdfTemplate.getAcroFields().getField("fill_2"));*/
	 
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	             //2.4 add to files
	             files.add(fileMeta);
	         }
			
		    return null;
   
    }
	
	private PdfReader PdfReader(String preface2) {
		// TODO Auto-generated method stub
		return null;
	}
	
}