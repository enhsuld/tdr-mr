package com.peace.web.service;

import java.io.File;
import java.text.DateFormat;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
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
import com.peace.users.model.mram.LnkComment;
import com.peace.users.model.mram.LnkMenuRole;
import com.peace.users.model.mram.LnkOffRole;
import com.peace.users.model.mram.LnkPlanAttachedFiles;
import com.peace.users.model.mram.LnkPlanNotes;
import com.peace.users.model.mram.LnkPlanTab;
import com.peace.users.model.mram.LnkPlanTransition;
import com.peace.users.model.mram.LnkReportRegBunl;
import com.peace.users.model.mram.LnkRoleAuth;
import com.peace.users.model.mram.LnkWeekly;
import com.peace.users.model.mram.LutAppstatus;
import com.peace.users.model.mram.LutAppsteps;
import com.peace.users.model.mram.LutDecisions;
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
import com.peace.users.model.mram.LutMinerals;
import com.peace.users.model.mram.LutReporttype;
import com.peace.users.model.mram.LutRole;
import com.peace.users.model.mram.LutUsers;
import com.peace.users.model.mram.RegWeeklyMontly;
import com.peace.users.model.mram.SubAddLpInfo;
import com.peace.users.model.mram.SubLegalpersons;
import com.peace.users.model.mram.SubLicenses;
import com.peace.users.service.MyUserDetailsService;



@RestController
@RequestMapping("/user/data/service")
public class DataTableController {


	@Autowired
    private UserDao dao;
         
    @Autowired
 	@Qualifier("userDetailsService")
 	UserDetailsService userDetailsService;

    final static Logger logger = Logger.getLogger(DataTableController.class);
    

  	@RequestMapping(value = "/{domain}", method = RequestMethod.POST, produces={"application/json; charset=UTF-8"})
      public @ResponseBody String customers(@RequestBody String request, @PathVariable String domain, HttpServletRequest req) {
  		try{		
  			int count=0;
  			List<?> rs = null;		
  		
  			DataSourceResult result = new DataSourceResult();	
  			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  			if (!(auth instanceof AnonymousAuthenticationToken)) {
  		  				
  				int vlength=0;
  				int vdraw=0;
  				int vstart=0;
  				
  				vlength=Integer.parseInt(req.getParameter("length"));
  				vdraw=Integer.parseInt(req.getParameter("draw"));
  				vstart=Integer.parseInt(req.getParameter("start"));
  				String searchParameter = req.getParameter("search[value]");
  				
  				String ind=req.getParameter("order[0][column]");
  				System.out.println("#######"+ind);
  				String sortColumn = req.getParameter("columns["+Integer.parseInt(ind) +"][data]");
  				
  				System.out.println("#######"+sortColumn);
  				
  				String sortColumnDir = req.getParameter("order[0][dir]");
				 //find search columns info
				String contactName = req.getParameter("columns[0][search][value]"); 
				String country = req.getParameter("columns[3][search][value]");
				
				int pageSize = vlength;
				int skip =vstart;
				int recordsTotal = 0;
				String searchStr="";
				
				rs=dao.jData(pageSize,skip,sortColumn,sortColumnDir,searchStr,domain);
				count=rs.size();
  				
				List<AnnualRegistration> wrap = new ArrayList<AnnualRegistration>();

				JSONArray arr = new JSONArray();
				JSONObject obj= new JSONObject();
			 	                 	
				for(int i=0;i<rs.size();i++){
					AnnualRegistration or=(AnnualRegistration) rs.get(i);
					AnnualRegistration cor=new AnnualRegistration();
					cor.setId(or.getId());
					cor.setLpReg(or.getLpReg());
					cor.setLictype(or.getLictype());
					cor.setMinid(or.getMinid());
					if(or.getXtype()!=null){
						cor.setXtype(or.getXtype());
					}
					else{
						cor.setXtype(0);
					}
					if(or.getApproveddate()!=null){
						cor.setApproveddate(or.getApproveddate());
					}
					else{
						cor.setApproveddate("");
					}
					if(or.getOfficerid()!=null){
						cor.setOfficerid(or.getOfficerid());
					}
					else{
						cor.setOfficerid((long) 0);
					}
					if(or.getSubmissiondate()!=null){
						cor.setSubmissiondate(or.getSubmissiondate());
					}
					else{
						cor.setSubmissiondate("");
					}
					cor.setReporttype(or.getReporttype());
					cor.setReportyear(or.getReportyear());
					cor.setRepstatusid(or.getRepstatusid());
					cor.setRepstepid(or.getRepstepid());
					cor.setLicensenum(or.getLicensenum());
				//	cor.setOfficerid(or.getOfficerid());
				//	cor.setApproveddate(or.getApproveddate());
					wrap.add(cor);
				}
				
					
				
				arr.put(wrap);
				obj.put("draw", vdraw);
				obj.put("recordsFiltered", count);
				obj.put("recordsTotal", count);
				obj.put("data", wrap);
				
				return obj.toString();
  			}
  		}
  		catch(Exception e){
  			e.printStackTrace();
  			return null;
  		}
  		return null;
  	}

}