package com.peace.web.logic.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
import com.peace.users.model.mram.LutDecisions;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.XfaForm;
import com.peace.users.dao.UserDao;
import com.peace.users.model.FileMeta;
import com.peace.users.model.Tbstudentdetail;
import com.sun.mail.handlers.multipart_mixed;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	UserDao dao;
	
	LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    /***************************************************
     * URL: /rest/controller/upload  
     * upload(): receives files
     * @param request : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     ****************************************************/
    @RequestMapping(value="/upload", method = RequestMethod.POST)
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
 
             
             String appPath = request.getSession().getServletContext().getRealPath("");
		 	 String SAVE_DIR = "uploads";
				Date d1 = new Date();
				SimpleDateFormat df = new SimpleDateFormat("MM-dd-YYYY");
				String special = df.format(d1);
			
				SimpleDateFormat df1 = new SimpleDateFormat("YYYY");
				String year = df1.format(d1);
				
				UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
			
				String ss="";
				ss=request.getParameter("data");
				String url="";
				String dt="";
				String fl="";
				System.out.println("##"+ss);
				if(ss.length()>0){
					 dt = appPath + File.separator + SAVE_DIR+ File.separator +year+File.separator +loguser.getLpreg()+File.separator +special+File.separator +"tezu"+File.separator +request.getParameter("data");
	        	     fl = appPath + File.separator + SAVE_DIR+ File.separator +year+File.separator +loguser.getLpreg()+File.separator +special+ File.separator +"tezu"+File.separator +request.getParameter("data")+ File.separator+mpf.getOriginalFilename();
	        	     url = SAVE_DIR+ File.separator +year+File.separator +loguser.getLpreg()+File.separator +special+ File.separator +"tezu"+ File.separator+File.separator +request.getParameter("data") +File.separator +mpf.getOriginalFilename();					
				}
				else{
					 dt = appPath + File.separator + SAVE_DIR+ File.separator +year+File.separator +loguser.getLpreg()+File.separator +special+File.separator +"mail";
	        	     fl = appPath + File.separator + SAVE_DIR+ File.separator +year+File.separator +loguser.getLpreg()+File.separator +special+ File.separator +"mail"+ File.separator +mpf.getOriginalFilename();
	        	     url = SAVE_DIR+ File.separator +year+File.separator +loguser.getLpreg()+File.separator +special+ File.separator +"mail"+ File.separator +mpf.getOriginalFilename();
				}
				
        	    fileMeta.setFileUrl(url);
        	    
        	    File dtx = new File(dt);
        	    if(!dtx.exists()){
        	    	dtx.mkdirs();
    				  System.out.println("end"+dtx);
    			}
        	
        		File logoorgpath = new File(fl);
     
             try {
               // fileMeta.setBytes(mpf.getBytes());        
                 FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(logoorgpath));
 
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

}
