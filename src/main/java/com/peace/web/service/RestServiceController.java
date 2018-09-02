package com.peace.web.service;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

//import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.peace.users.model.DataSourceResult;
import com.peace.users.dao.FileBean;
import com.peace.users.dao.UserDao;
import com.peace.users.model.AccountList;
import com.peace.users.model.Excel;
import com.peace.users.model.PMenuRel;
import com.peace.users.model.PUserAuth;
import com.peace.users.model.PUserRoleRel;
//import com.peace.users.model.PMenuRel;
import com.peace.users.model.PUserRoles;
import com.peace.users.model.Role;
import com.peace.users.model.Tbbranches;
import com.peace.users.model.Tbdepartment;
import com.peace.users.model.Tbuniversities;
/*import com.peace.users.model.PeaceMenu;
import com.peace.users.model.PeaceOrganization;*/
import com.peace.users.model.User;
import com.peace.users.model.UserAuthority;
import com.peace.users.model.mram.AnnualRegistration;
import com.peace.users.model.mram.DataTezuMail;
import com.peace.users.model.mram.LnkComment;
import com.peace.users.model.mram.LnkCommentMain;
import com.peace.users.model.mram.LnkCommentWeekly;
import com.peace.users.model.mram.LnkMenuRole;
import com.peace.users.model.mram.LnkOffRole;
import com.peace.users.model.mram.LnkPlanAttachedFiles;
import com.peace.users.model.mram.LnkPlanNotes;
import com.peace.users.model.mram.LnkPlanTab;
import com.peace.users.model.mram.LnkPlanTransition;
import com.peace.users.model.mram.LnkReportRegBunl;
import com.peace.users.model.mram.LnkRoleAuth;
import com.peace.users.model.mram.LnkTezuData;
import com.peace.users.model.mram.LnkWeekly;
import com.peace.users.model.mram.LutAdminunit;
import com.peace.users.model.mram.LutAppstatus;
import com.peace.users.model.mram.LutAppsteps;
import com.peace.users.model.mram.LutCountries;
import com.peace.users.model.mram.LutDecisions;
import com.peace.users.model.mram.LutDeposit;
import com.peace.users.model.mram.LutDivision;
import com.peace.users.model.mram.LutFiletype;
import com.peace.users.model.mram.LutFormNotes;
import com.peace.users.model.mram.LutFormindicators;
import com.peace.users.model.mram.LutForms;
import com.peace.users.model.mram.LutLicstatus;
import com.peace.users.model.mram.LutLictype;
import com.peace.users.model.mram.LutLptype;
import com.peace.users.model.mram.LutMeasurements;
import com.peace.users.model.mram.LutMenu;
import com.peace.users.model.mram.LutMinGroup;
import com.peace.users.model.mram.LutMineType;
import com.peace.users.model.mram.LutMinerals;
import com.peace.users.model.mram.LutProducts;
import com.peace.users.model.mram.LutReporttype;
import com.peace.users.model.mram.LutRole;
import com.peace.users.model.mram.LutSubProduct;
import com.peace.users.model.mram.LutUsers;
import com.peace.users.model.mram.LutWeeks;
import com.peace.users.model.mram.RegWeeklyMontly;
import com.peace.users.model.mram.SubAddLpInfo;
import com.peace.users.model.mram.SubLegalpersons;
import com.peace.users.model.mram.SubLicenses;
import com.peace.users.model.mram.HelpCategory;
import com.peace.users.model.mram.HelpData;
import com.peace.users.model.mram.WeeklyRegistration;
import com.peace.users.service.MyUserDetailsService;



@RestController
@RequestMapping("/user/service")
public class RestServiceController {

	//private static final AtomicLong counter = new AtomicLong();
	//counter.incrementAndGet()
	@Autowired
    private UserDao dao;
         
	@Autowired
	UserDao mainService;
	
    final static Logger logger = Logger.getLogger(RestServiceController.class);
    
	@RequestMapping(value = "/entity/{domain}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listAllUsers(@PathVariable String domain) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			if(domain.equalsIgnoreCase("messages")){
				UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
			
				List<DataTezuMail> rs=(List<DataTezuMail>) dao.getHQLResult("from DataTezuMail t where (t.recipient="+loguser.getId()+") or  (t.userid="+loguser.getId()+") order by t.id", "list");
				
		        if(rs.isEmpty()){
		            return new ResponseEntity<List<?>>(HttpStatus.NO_CONTENT);// HttpStatus.NOT_FOUND
		        }
		        return new ResponseEntity<List<?>>(rs, HttpStatus.OK);
			}      
			else{
				List<?> rs=(List<?>) dao.getHQLResult("from "+domain+" t order by t.id", "list");
		        if(rs.isEmpty()){
		            return new ResponseEntity<List<?>>(HttpStatus.NO_CONTENT);//HttpStatus.NOT_FOUND
		        }
		        return new ResponseEntity<List<?>>(rs, HttpStatus.OK);
			}
		}else{
			return new ResponseEntity<List<?>>(HttpStatus.NO_CONTENT);// HttpStatus.NOT_FOUND
		}
    }
	
	
	@RequestMapping(value = "/entity/{domain}/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateContent(@RequestBody String jsonString, @PathVariable("domain") String domain, @PathVariable("id") long id) throws ClassNotFoundException,ParseException,JsonSyntaxException {
	         
        Object content = mainService.findById(domain,id,null);
        Class<?> classtoConvert;
	    JSONObject obj = new JSONObject(jsonString);    		
	   
	    String domainName="com.peace.users.model.mram."+domain;
	    System.out.println(domainName);
	    classtoConvert=Class.forName(domainName);
	    Gson gson = new Gson();
	    Object object = gson.fromJson(obj.toString(),classtoConvert); 
	    mainService.saveOrUpdate(object);
        
        return new ResponseEntity<String>("true",HttpStatus.OK);
    }
	
	@RequestMapping(value = "/entity/{domain}/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteContent(@PathVariable("domain") String domain, @PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);
 
        Object content = mainService.findById(domain,id,null);
        if (content == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<String>("false",HttpStatus.NO_CONTENT);
        } 
        mainService.deleteById(domain,id,null);
        return new ResponseEntity<String>("true",HttpStatus.OK);
    }
	

}