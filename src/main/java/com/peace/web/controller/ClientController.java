package com.peace.web.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import com.peace.web.service.SmtpMailSender;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.pdf.PdfReader;
import com.google.gson.Gson;
import com.itextpdf.text.pdf.AcroFields;
import com.peace.users.model.DataSourceResult;
import com.peace.users.dao.FileBean;
import com.peace.users.dao.UserDao;
import com.peace.users.model.mram.LutMenu;
import com.peace.users.model.mram.LutUsers;
import com.peace.users.model.mram.SubLegalpersons;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet; 
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import java.util.UUID;
@RestController
public class ClientController {

	@Autowired
    private UserDao dao;

	@Autowired
	private SmtpMailSender smtpMailSender;  
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	
	 
	@RequestMapping(value="/service/send-mail", method=RequestMethod.PUT)
	public @ResponseBody String ajaxconf(@RequestBody String jsonString) throws JSONException, MessagingException{
		 
		System.out.println(jsonString);
	
		 JSONObject obj= new JSONObject(jsonString);
		// List<LutUsers> rs= (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.email='"+obj.getString("username")+"'", "list");
		 List<SubLegalpersons> rs= (List<SubLegalpersons>) dao.getHQLResult("from SubLegalpersons t where t.email='"+obj.getString("username")+"'", "list");
		 		 
		 if(rs.size()>0){
			 if(!rs.get(0).getLutUsers().get(0).getLpreg().equals("9999999")){
				 
				 String uuid = UUID.randomUUID().toString();
				 
				// uuid="2112868";
				 
				 LutUsers us=rs.get(0).getLutUsers().get(0);
				 us.setUserpass(passwordEncoder.encode(uuid));
				 dao.PeaceCrud(us, "User", "update", (long) us.getId(), 0, 0, null);
				 
				 smtpMailSender.send(rs.get(0).getEmail(), "username"+" : "+rs.get(0).getLutUsers().get(0).getUsername(),"password"+" : "+ uuid);
				 return "true";
			 }	
			 
		 }
		 else{
			 return "false";
		 }
		 return "false";
		 
	 }
	
	@RequestMapping(value="/defaultSuccess",method=RequestMethod.GET)
	public String defaultSuccess (HttpServletRequest req,HttpServletResponse res){
		try{
			req.setCharacterEncoding("utf-8");
			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<LutUsers> loguser=(List<LutUsers>) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "list");
		
			
			JSONObject js = new JSONObject();
			
			if(loguser.size()>0){		
				LutMenu mnu=(LutMenu) dao.getHQLResult("from LutMenu t where t.id='"+loguser.get(0).getLnkOffRoles().get(0).getLutRole().getAccess()+"'", "current");
				js.put("url", mnu.getStateurl());
				return  js.toString();
			}
			else{
				js.put("url", "restricted.pages.organizations");
				return js.toString();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}	
	}
}