package com.peace.web.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.peace.users.model.mram.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.servlet.ModelAndView;
import com.peace.users.model.DataSourceResult;
import com.peace.users.dao.FileBean;
import com.peace.users.dao.UserDao;

@RestController
public class MainController {

     
	@Autowired
    private UserDao accountService;

	@Autowired
    private UserDao dao;
         
    private PasswordEncoder passwordEncoder() {
    	PasswordEncoder encoder = new BCryptPasswordEncoder();
 		return encoder;
	}

	@Autowired
    public MainController(UserDao accountService) {
         this.accountService = accountService;
    }
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@RequestMapping(value="/user", method = RequestMethod.GET)
	public Principal user(Principal user) {
		return user;
	}
	
	//@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/user/step/{step}/{type}/{domain}", method = RequestMethod.POST, produces={"application/json; charset=UTF-8"})
    public @ResponseBody DataSourceResult cstep(@RequestBody String request,@PathVariable Integer step,@PathVariable Integer type,@PathVariable String domain, HttpServletRequest request1) {
		try{		
			int count=0;
			List<?> rs = null;		
		
			DataSourceResult result = new DataSourceResult();	
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				
				UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
				
				System.out.println("group" + loguser.getGroupid()+" division"+loguser.getDivisionid()+" stepid"+loguser.getStepid());
				if(domain.equalsIgnoreCase("AnnualRegistrationXplan")){
					domain="AnnualRegistration";
					JSONObject jsonObj = new JSONObject(request);
					if(loguser.getDivisionid()==3){
						jsonObj.put("custom", "where repstatusid =7  and divisionid=3 and  lictype=1 and xtype=0 and reporttype="+type+" ");
					}
					else if(loguser.getDivisionid()==2){
						jsonObj.put("custom", "where repstatusid in (1,2,3,7)  and divisionid=2 and  lictype=2 and xtype=0 and reporttype="+type+" and minid=5");
					}
					else if(loguser.getDivisionid()==1){
						jsonObj.put("custom", "where  repstatusid =7 and divisionid=1  and lictype!=1 and xtype=0 and reporttype="+type+" and minid!=5");
					}	
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				}
				if(domain.equalsIgnoreCase("AnnualRegistrationPlan1")){
					if(loguser.getGroupid()==8 && loguser.getDivisionid()==2){
						System.out.println("oyutnii medeelel alga"); 
						domain="AnnualRegistration";
						JSONObject jsonObj = new JSONObject(request);
						if(loguser.getDivisionid()==3){
							jsonObj.put("custom", "where repstatusid =7  and divisionid=3 and  lictype=1 and reporttype="+type+" and repstepid="+step+" and xtype != 0");	
						}
						else if(loguser.getDivisionid()==2){
							jsonObj.put("custom", "where repstatusid =7  and divisionid=2 and  lictype=2 and reporttype="+type+" and repstepid="+step+" and minid=5 and xtype != 0");	
						}
						else if(loguser.getDivisionid()==1){
							jsonObj.put("custom", "where  repstatusid =7 and divisionid=1  and lictype=2 and reporttype="+type+" and repstepid="+step+"  and minid!=5 and xtype != 0");	
						}
						//jsonObj.put("custom", "where repstatusid!=0 ");	
						rs= dao.kendojson(jsonObj.toString(), domain);
						count=dao.resulsetcount(jsonObj.toString(), domain);
						result.setData(rs);
						result.setTotal(count);
					}
					else {
						if(loguser.getStepid()==6){
					
							domain="AnnualRegistration";
							JSONObject jsonObj = new JSONObject(request);
							if(loguser.getDivisionid()==3){
								jsonObj.put("custom", "where (repstatusid =7 and divisionid=3 and  lictype=1 and reporttype="+type+" and repstepid="+step+")  and xtype != 0");	
							}
							else if(loguser.getDivisionid()==2){
								jsonObj.put("custom", "where (repstatusid =7 and divisionid=2 and  lictype=2 and reporttype="+type+" and minid=5 and repstepid="+step+")  and xtype != 0");	
							}
							else if(loguser.getDivisionid()==1){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and repstepid="+step+"  and xtype != 0");	
							}
							//jsonObj.put("custom", "where repstatusid!=0 ");	
							rs= dao.kendojson(jsonObj.toString(), domain);
							count=dao.resulsetcount(jsonObj.toString(), domain);
							result.setData(rs);
							result.setTotal(count);
						}
						else if(loguser.getStepid()==1){
							domain="AnnualRegistration";
							JSONObject jsonObj = new JSONObject(request);
							if(loguser.getDivisionid()==3){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=3 and  lictype=1 and reporttype="+type+" and (repstepid="+step+" or rejectstep="+step+") ");	
							}
							else if(loguser.getDivisionid()==2){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=2 and  lictype=2 and reporttype="+type+" and minid=5 and (repstepid="+step+" or rejectstep="+step+") ");	
							}
							else if(loguser.getDivisionid()==1){
								jsonObj.put("custom", "where  repstatusid =7 and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and (repstepid="+step+" or rejectstep="+step+") and xtype != 0");	
							}
							//jsonObj.put("custom", "where repstatusid!=0 ");	
							rs= dao.kendojson(jsonObj.toString(), domain);
							count=dao.resulsetcount(jsonObj.toString(), domain);
							result.setData(rs);
							result.setTotal(count);
						}
						else if(loguser.getStepid()==8){
							domain="AnnualRegistration";
							JSONObject jsonObj = new JSONObject(request);
							if(loguser.getDivisionid()==3){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=3 and  lictype=1 and reporttype="+type+" and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");	
							}
							else if(loguser.getDivisionid()==2){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=2 and  lictype=2 and reporttype="+type+" and minid=5 and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");	
							}
							else if(loguser.getDivisionid()==1){
								jsonObj.put("custom", "where  repstatusid =7 and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");	
							}
							//jsonObj.put("custom", "where repstatusid!=0 ");	
							rs= dao.kendojson(jsonObj.toString(), domain);
							count=dao.resulsetcount(jsonObj.toString(), domain);
							result.setData(rs);
							result.setTotal(count);
						}
						else if(loguser.getStepid()==3){
							System.out.println("oyutnii medeelel alga 3333"); 
							domain="AnnualRegistration";
							JSONObject jsonObj = new JSONObject(request);
							if(loguser.getDivisionid()==3){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=3 and  lictype=1 and reporttype="+type+" and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");	
							}
							else if(loguser.getDivisionid()==2){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=2 and  lictype=2 and reporttype="+type+" and minid=5 and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");	
							}
							else if(loguser.getDivisionid()==1){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and  (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");	
							}
							//jsonObj.put("custom", "where repstatusid!=0 ");	
							rs= dao.kendojson(jsonObj.toString(), domain);
							count=dao.resulsetcount(jsonObj.toString(), domain);
							result.setData(rs);
							result.setTotal(count);
						}
						/*else if(loguser.getStepid()==5){
							System.out.println("oyutnii medeelel alga 3333"); 
							domain="AnnualRegistration";
							JSONObject jsonObj = new JSONObject(request);
							if(loguser.getDivisionid()==3){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=3 and  lictype=1 and reporttype="+type+" and (repstepid="+step+" or rejectstep="+step+") ");	
							}
							else if(loguser.getDivisionid()==2){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=2 and  lictype=2 and reporttype="+type+" and minid=5 and (repstepid="+step+" or rejectstep="+step+") ");	
							}
							else if(loguser.getDivisionid()==1){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and  (repstepid="+step+" or rejectstep="+step+") ");	
							}
							//jsonObj.put("custom", "where repstatusid!=0 ");	
							rs= dao.kendojson(jsonObj.toString(), domain);
							count=dao.resulsetcount(jsonObj.toString(), domain);
							result.setData(rs);
							result.setTotal(count);
						}*/
						else{
							if(loguser.getGroupid()==3){
								System.out.println("oyutnii medeelel alga"); 
								domain="AnnualRegistration";
								JSONObject jsonObj = new JSONObject(request);
								if(loguser.getDivisionid()==3){
									jsonObj.put("custom", "where repstatusid =7 and groupid=2 and divisionid=3 and  lictype=1 and reporttype="+type+" and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");	
								}
								else if(loguser.getDivisionid()==2){
									jsonObj.put("custom", "where repstatusid =7 and groupid=2 and divisionid=2 and  lictype=2 and reporttype="+type+"  and minid=5 and (repstepid="+step+" or rejectstep="+step+") and xtype != 0");	
								}
								else if(loguser.getDivisionid()==1){
									jsonObj.put("custom", "where repstatusid =7 and groupid=2 and divisionid=1 and lictype!=1 and reporttype="+type+"  and minid!=5 and (repstepid="+step+" or rejectstep="+step+") and xtype != 0");	
								}
								//jsonObj.put("custom", "where repstatusid!=0 ");	
								rs= dao.kendojson(jsonObj.toString(), domain);
								count=dao.resulsetcount(jsonObj.toString(), domain);
								result.setData(rs);
								result.setTotal(count);
							}
							else if(loguser.getGroupid()==7){
								System.out.println("oyutnii medeelel alga"); 
								domain="AnnualRegistration";
								JSONObject jsonObj = new JSONObject(request);
								if(loguser.getDivisionid()==3){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=3 and  lictype=1 and reporttype="+type+" and (repstepid="+step+" or rejectstep="+step+") and xtype != 0");	
								}
								else if(loguser.getDivisionid()==2){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=2 and  lictype=2 and reporttype="+type+" and minid=5 and (repstepid="+step+" or rejectstep="+step+") and xtype != 0");	
								}
								else if(loguser.getDivisionid()==1){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=1 and lictype!=1 and reporttype="+type+" and minid!=5 and (repstepid="+step+" or rejectstep="+step+") and xtype != 0");	
								}
								//jsonObj.put("custom", "where repstatusid!=0 ");	
								rs= dao.kendojson(jsonObj.toString(), domain);
								count=dao.resulsetcount(jsonObj.toString(), domain);
								result.setData(rs);
								result.setTotal(count);
							}
							else if(loguser.getGroupid()==1){
								System.out.println("oyutnii medeelel alga 1 "+loguser.getGroupid()); 
								domain="AnnualRegistration";
								JSONObject jsonObj = new JSONObject(request);
								if(loguser.getDivisionid()==3){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=3 and  lictype=1 and reporttype="+type+" and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");	
								}
								else if(loguser.getDivisionid()==2){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=2 and  lictype=2 and reporttype="+type+" and minid=5 and (repstepid="+step+" or rejectstep="+step+") and xtype != 0");	
								}
								else if(loguser.getDivisionid()==1){
									if(loguser.getStepid()==4 || loguser.getStepid()==5){
										jsonObj.put("custom", "where repstatusid =7  and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");
									}
									else{
										jsonObj.put("custom", "where repstatusid =7 and divisionid=1  and groupid=1 and lictype!=1 and reporttype="+type+" and minid!=5 and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");
									}										
								}
								//jsonObj.put("custom", "where repstatusid!=0 ");	
								rs= dao.kendojson(jsonObj.toString(), domain);
								count=dao.resulsetcount(jsonObj.toString(), domain);
								result.setData(rs);
								result.setTotal(count);
							}
							else if(loguser.getGroupid()==2){
								System.out.println("oyutnii medeelel alga 2 "+loguser.getGroupid()); 
								domain="AnnualRegistration";
								JSONObject jsonObj = new JSONObject(request);
								if(loguser.getDivisionid()==3){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=3 and  lictype=1 and reporttype="+type+" and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");	
								}
								else if(loguser.getDivisionid()==2){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=2 and  lictype=2 and reporttype="+type+" and minid=5 and (repstepid="+step+" or rejectstep="+step+") and xtype != 0");	
								}
								else if(loguser.getDivisionid()==1){
									if(loguser.getStepid()==4 || loguser.getStepid()==5){
										jsonObj.put("custom", "where repstatusid =7  and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");
									}
									else{
										jsonObj.put("custom", "where repstatusid =7  and divisionid=1 and lictype!=1 and reporttype="+type+" and minid!=5 and (repstepid="+step+" or rejectstep="+step+")  and xtype != 0");
									}	
								}
								//jsonObj.put("custom", "where repstatusid!=0 ");	
								rs= dao.kendojson(jsonObj.toString(), domain);
								count=dao.resulsetcount(jsonObj.toString(), domain);
								result.setData(rs);
								result.setTotal(count);
							}
						}
					}
				
				}
				if(domain.equalsIgnoreCase("AnnualRegistrationReport") || domain.equalsIgnoreCase("AnnualRegistrationPlan")){
					domain="AnnualRegistration";
					//JSONObject jsonObj = new JSONObject(request);
					/*if(step==7){
						if(loguser.getDivisionid()==3){
							jsonObj.put("custom", "where repstatusid=1 and divisionid=3 and  lictype=1 and xtype!=0 and reporttype="+type+"");	
						}
						else if(loguser.getDivisionid()==2){
							jsonObj.put("custom", "where repstatusid=1 and divisionid=2 and  lictype=2 and xtype!=0 and reporttype="+type+" and minid=5");	
						}
						else if(loguser.getDivisionid()==1){
							jsonObj.put("custom", "where  repstatusid=1 and divisionid=1 and lictype=2 and xtype!=0 and reporttype="+type+" and minid!=5");	
						}
					}
					else{
						if(loguser.getDivisionid()==3){
							jsonObj.put("custom", "where repstatusid=7 and divisionid=3 and lictype=1 and xtype!=0 and reporttype="+type+" and repstepid="+step+"");	
						}
						else if(loguser.getDivisionid()==2){
							jsonObj.put("custom", "where repstatusid=7 and divisionid=2 and lictype=2 and xtype!=0 and reporttype="+type+" and repstepid="+step+" and minid=5");	
						}
						else if(loguser.getDivisionid()==1){
							jsonObj.put("custom", "where  (repstatusid=7 and divisionid=1 and lictype=2 and  xtype!=0 and reporttype="+type+" and repstepid="+step+"  and minid!=5)");	
						}
					}*/
				
					if(loguser.getGroupid()==8 && loguser.getDivisionid()==2){
						domain="AnnualRegistration";
						JSONObject jsonObj = new JSONObject(request);
						if(loguser.getDivisionid()==3){
							jsonObj.put("custom", "where repstatusid =7  and divisionid=3 and  lictype=1 and reporttype="+type+" and repstepid="+step+" and xtype != 0");	
						}
						else if(loguser.getDivisionid()==2){
							jsonObj.put("custom", "where repstatusid =7  and divisionid=2 and  lictype in ('2','3') and reporttype="+type+" and repstepid="+step+" and minid in ('5','8') and xtype != 0");
						}
						else if(loguser.getDivisionid()==1){
							jsonObj.put("custom", "where  repstatusid =7 and divisionid=1  and lictype=2 and reporttype="+type+" and repstepid="+step+"  and minid!=5 and xtype != 0");	
						}
						//jsonObj.put("custom", "where repstatusid!=0 ");	
						rs= dao.kendojson(jsonObj.toString(), domain);
						count=dao.resulsetcount(jsonObj.toString(), domain);
						result.setData(rs);
						result.setTotal(count);
					}
					else {
						if(loguser.getStepid()==6){
					
							domain="AnnualRegistration";
							JSONObject jsonObj = new JSONObject(request);
							if(loguser.getDivisionid()==3){
								jsonObj.put("custom", "where (repstatusid =7 and divisionid=3 and  lictype=1 and reporttype="+type+" and repstepid="+step+")  and xtype != 0");	
							}
							else if(loguser.getDivisionid()==2){
								jsonObj.put("custom", "where (repstatusid =7 and divisionid=2 and  lictype in ('2','3') and reporttype="+type+" and minid in ('5','8')  and repstepid="+step+")  and xtype != 0");
							}
							else if(loguser.getDivisionid()==1){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and repstepid="+step+"  and xtype != 0");	
							}
							//jsonObj.put("custom", "where repstatusid!=0 ");	
							rs= dao.kendojson(jsonObj.toString(), domain);
							count=dao.resulsetcount(jsonObj.toString(), domain);
							result.setData(rs);
							result.setTotal(count);
						}
						else if(loguser.getStepid()==1){
							domain="AnnualRegistration";
							JSONObject jsonObj = new JSONObject(request);
							if(loguser.getDivisionid()==3){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=3 and  lictype=1 and reporttype="+type+" and (repstepid="+step+" or rejectstep="+step+") ");	
							}
							else if(loguser.getDivisionid()==2){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=2 and  lictype in ('2','3') and reporttype="+type+" and minid in ('5','8')  and repstepid="+step+" ");
							}
							else if(loguser.getDivisionid()==1){
								jsonObj.put("custom", "where  repstatusid =7 and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and repstepid="+step+" and xtype != 0");	
							}
							//jsonObj.put("custom", "where repstatusid!=0 ");	
							rs= dao.kendojson(jsonObj.toString(), domain);
							count=dao.resulsetcount(jsonObj.toString(), domain);
							result.setData(rs);
							result.setTotal(count);
						}
						else if(loguser.getStepid()==8){
							domain="AnnualRegistration";
							JSONObject jsonObj = new JSONObject(request);
							if(loguser.getDivisionid()==3){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=3 and  lictype=1 and reporttype="+type+" and repstepid="+step+" and xtype != 0");	
							}
							else if(loguser.getDivisionid()==2){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=2 and  lictype in ('2','3') and reporttype="+type+" and minid in ('5','8')  and repstepid="+step+"  and xtype != 0");
							}
							else if(loguser.getDivisionid()==1){
								jsonObj.put("custom", "where  repstatusid =7 and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and repstepid="+step+"  and xtype != 0");	
							}
							//jsonObj.put("custom", "where repstatusid!=0 ");	
							rs= dao.kendojson(jsonObj.toString(), domain);
							count=dao.resulsetcount(jsonObj.toString(), domain);
							result.setData(rs);
							result.setTotal(count);
						}
						else if(loguser.getStepid()==3){
							System.out.println("oyutnii medeelel alga 3333"); 
							domain="AnnualRegistration";
							JSONObject jsonObj = new JSONObject(request);
							if(loguser.getDivisionid()==3){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=3 and  lictype=1 and reporttype="+type+" and repstepid="+step+"  and xtype != 0");	
							}
							else if(loguser.getDivisionid()==2){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=2 and  lictype in ('2','3')and reporttype="+type+" and minid in ('5','8')  and repstepid="+step+"  and xtype != 0");
							}
							else if(loguser.getDivisionid()==1){
								jsonObj.put("custom", "where repstatusid =7 and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and  repstepid="+step+"   and xtype != 0");	
							}
							//jsonObj.put("custom", "where repstatusid!=0 ");	
							rs= dao.kendojson(jsonObj.toString(), domain);
							count=dao.resulsetcount(jsonObj.toString(), domain);
							result.setData(rs);
							result.setTotal(count);
						}
                        else if(loguser.getStepid()==4){
                            System.out.println("oyutnii medeelel alga 4444");
                            domain="AnnualRegistration";
                            JSONObject jsonObj = new JSONObject(request);
                            if(loguser.getDivisionid()==3){
                                jsonObj.put("custom", "where repstatusid =7 and divisionid=3 and  lictype=1 and reporttype="+type+" and repstepid="+step+"  and xtype != 0");
                            }
                            else if(loguser.getDivisionid()==2){
                                jsonObj.put("custom", "where repstatusid =7 and divisionid=2 and  lictype in ('2','3') and reporttype="+type+" and minid in ('5','8')  and repstepid="+step+"  and xtype != 0");
                            }
                            else if(loguser.getDivisionid()==1){
                                jsonObj.put("custom", "where repstatusid =7 and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and  repstepid="+step+"   and xtype != 0");
                            }
                            //jsonObj.put("custom", "where repstatusid!=0 ");
                            rs= dao.kendojson(jsonObj.toString(), domain);
                            count=dao.resulsetcount(jsonObj.toString(), domain);
                            result.setData(rs);
                            result.setTotal(count);
                        }
						else{
							if(loguser.getGroupid()==3){
								System.out.println("oyutnii medeelel alga"); 
								domain="AnnualRegistration";
								JSONObject jsonObj = new JSONObject(request);
								if(loguser.getDivisionid()==3){
									jsonObj.put("custom", "where repstatusid =7 and groupid=2 and divisionid=3 and  lictype=1 and reporttype="+type+" and repstepid="+step+"  and xtype != 0");	
								}
								else if(loguser.getDivisionid()==2){
									jsonObj.put("custom", "where repstatusid =7 and groupid=2 and divisionid=2 and  lictype in ('2','3') and reporttype="+type+"  and minid in ('5','8') and repstepid="+step+"  and xtype != 0");
								}
								else if(loguser.getDivisionid()==1){
									jsonObj.put("custom", "where repstatusid =7 and groupid=2 and divisionid=1 and lictype!=1 and reporttype="+type+"  and minid!=5 and repstepid="+step+"  and xtype != 0");	
								}
								//jsonObj.put("custom", "where repstatusid!=0 ");	
								rs= dao.kendojson(jsonObj.toString(), domain);
								count=dao.resulsetcount(jsonObj.toString(), domain);
								result.setData(rs);
								result.setTotal(count);
							}
							else if(loguser.getGroupid()==7){
								System.out.println("oyutnii medeelel alga"); 
								domain="AnnualRegistration";
								JSONObject jsonObj = new JSONObject(request);
								if(loguser.getDivisionid()==3){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=3 and  lictype=1 and reporttype="+type+" and repstepid="+step+" and xtype != 0");	
								}
								else if(loguser.getDivisionid()==2){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=2 and  lictype in ('2','3') and reporttype="+type+" and minid in ('5','8')  and repstepid="+step+"  and xtype != 0");
								}
								else if(loguser.getDivisionid()==1){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=1 and lictype!=1 and reporttype="+type+" and minid!=5 and repstepid="+step+"  and xtype != 0");	
								}
								//jsonObj.put("custom", "where repstatusid!=0 ");	
								rs= dao.kendojson(jsonObj.toString(), domain);
								count=dao.resulsetcount(jsonObj.toString(), domain);
								result.setData(rs);
								result.setTotal(count);
							}
							else if(loguser.getGroupid()==1){
								System.out.println("oyutnii medeelel alga 1 "+loguser.getGroupid()); 
								domain="AnnualRegistration";
								JSONObject jsonObj = new JSONObject(request);
								if(loguser.getDivisionid()==3){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=3 and  lictype=1 and reporttype="+type+" and repstepid="+step+" and xtype != 0");	
								}
								else if(loguser.getDivisionid()==2){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=2 and  lictype in ('2','3') and reporttype="+type+" and minid in ('5','8')  and repstepid="+step+" and xtype != 0");
								}
								else if(loguser.getDivisionid()==1){
									if(loguser.getStepid()==4 || loguser.getStepid()==5){
										jsonObj.put("custom", "where repstatusid =7  and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and repstepid="+step+"  and xtype != 0");
									}
									else{
										jsonObj.put("custom", "where repstatusid =7 and divisionid=1  and groupid=1 and lictype!=1 and reporttype="+type+" and minid!=5 and repstepid="+step+"  and xtype != 0");
									}										
								}
								//jsonObj.put("custom", "where repstatusid!=0 ");	
								rs= dao.kendojson(jsonObj.toString(), domain);
								count=dao.resulsetcount(jsonObj.toString(), domain);
								result.setData(rs);
								result.setTotal(count);
							}
							else if(loguser.getGroupid()==2){
								System.out.println("oyutnii medeelel alga 2 "+loguser.getGroupid()); 
								domain="AnnualRegistration";
								JSONObject jsonObj = new JSONObject(request);
								if(loguser.getDivisionid()==3){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=3 and  lictype=1 and reporttype="+type+" and repstepid="+step+"  and xtype != 0");	
								}
								else if(loguser.getDivisionid()==2){
									jsonObj.put("custom", "where repstatusid =7 and groupid=1 and divisionid=2 and  lictype in ('2','3') and reporttype="+type+" and minid in ('5','8') and repstepid="+step+" and xtype != 0");
								}
								else if(loguser.getDivisionid()==1){
									if(loguser.getStepid()==4 || loguser.getStepid()==5){
										jsonObj.put("custom", "where repstatusid =7  and divisionid=1  and lictype!=1 and reporttype="+type+" and minid!=5 and repstepid="+step+"  and xtype != 0");
									}
									else{
										jsonObj.put("custom", "where repstatusid =7  and divisionid=1 and lictype!=1 and reporttype="+type+" and minid!=5 and repstepid="+step+"  and xtype != 0");
									}	
								}
								//jsonObj.put("custom", "where repstatusid!=0 ");	
								rs= dao.kendojson(jsonObj.toString(), domain);
								count=dao.resulsetcount(jsonObj.toString(), domain);
								result.setData(rs);
								result.setTotal(count);
							}
						}
					}
					//jsonObj.put("custom", "where repstatusid!=0 ");	
					/*rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);*/
				}
				
				if(domain.equalsIgnoreCase("AnnualRegistrationPlanConfirmed")){
					domain="AnnualRegistration";
					JSONObject jsonObj = new JSONObject(request);
					boolean xtype=false;
					String str="";
					if(jsonObj.has("filter")){
						JSONArray jar=jsonObj.getJSONObject("filter").getJSONArray("filters");
						for(int i=0;i<jar.length();i++){
							JSONObject bo = (JSONObject) jar.get(i);
							if(bo.getString("field").equalsIgnoreCase("xtype")){
								if(bo.getInt("value")==0){
									xtype=true;
									str="and xtype=0";
								}
								else{
									str="and xtype!=0";
								}
								jar.remove(i);
							}
						}
					}
					domain="AnnualRegistration";
					if(loguser.getDivisionid()==3){
						jsonObj.put("custom", "where istodotgol = 0 and repstatusid =1 and groupid=1 and divisionid=3 and  lictype=1 and reporttype="+type+"");
					}
					else if(loguser.getDivisionid()==2){
						jsonObj.put("custom", "where istodotgol = 0 "+str+" and repstatusid =1 and divisionid=2 and reporttype="+type+" and minid=5");
					}
					else if(loguser.getDivisionid()==1){
						jsonObj.put("custom", "where istodotgol = 0 and repstatusid =1 and divisionid=1 and reporttype="+type+"  and minid!=5");
					}
					//jsonObj.put("custom", "where repstatusid!=0 ");	
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				}
				if(domain.equalsIgnoreCase("AnnualRegistrationPlanXConfirmed")){
					domain="AnnualRegistration";
					JSONObject jsonObj = new JSONObject(request);
					System.out.println("oyutnii medeelel alga");
					domain="AnnualRegistration";
					if(loguser.getDivisionid()==3){
						jsonObj.put("custom", "where istodotgol = 0 and repstatusid =1 and groupid=1 and divisionid=3 and  lictype=1 and reporttype="+type+"");
					}
					else if(loguser.getDivisionid()==2){
						jsonObj.put("custom", "where istodotgol = 0 and xtype=0 and repstatusid =1 and divisionid=2 and reporttype="+type+" and minid=5");
					}
					else if(loguser.getDivisionid()==1){
						jsonObj.put("custom", "where istodotgol = 0 and repstatusid =1 and divisionid=1 and reporttype="+type+"  and minid!=5");
					}
					//jsonObj.put("custom", "where repstatusid!=0 ");
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				}
				if(domain.equalsIgnoreCase("worklist")){
					domain="LnkComment";
					JSONObject jsonObj = new JSONObject(request);
					//domain="select t from AnnualRegistration t, LnkComment c";
					System.out.println(request);
					//jsonObj.put("customJoins", "select t, (select) from AnnualRegistration t, LnkComment c  where c.planid=t.id and c.officerid = "+loguser.getId()+" and t.repstatusid!=0 group by t.id");
					jsonObj.put("custom", "where officerid = "+loguser.getId()+"");
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				}
				if(domain.equalsIgnoreCase("history")){
					domain="AnnualRegistration";
					JSONObject jsonObj = new JSONObject(request);
					domain="AnnualRegistration";
									 
					jsonObj.put("custom", "where  reporttype="+type+" and repstatusid=5 and divisionid="+loguser.getDivisionid()+"");
					//jsonObj.put("custom", "where repstatusid!=0 ");	
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				}
			}
			
			return  result;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}	
	
	
	//@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/user/angular/{domain}", method = RequestMethod.POST, produces={"application/json; charset=UTF-8"})
    public @ResponseBody DataSourceResult customers(@RequestBody String request,@PathVariable String domain, HttpServletRequest request1) {
		try{		
			int count=0;
			List<?> rs = null;		
		
			DataSourceResult result = new DataSourceResult();	
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				
				UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				
				if(domain.equalsIgnoreCase("LutMenu")){
					List<LutMenu> wrap = new ArrayList<LutMenu>();

					rs= dao.kendojson(request, domain);
					count=dao.resulsetcount(request, domain);
					
					for(int i=0;i<rs.size();i++){
						LutMenu or=(LutMenu) rs.get(i);
						LutMenu cor=new LutMenu();
						cor.setId(or.getId());
						cor.setIcon(or.getIcon());
						cor.setIsactive(or.getIsactive());
						cor.setNamemn(or.getNamemn());
						cor.setNameen(or.getNameen());
						cor.setStateurl(or.getStateurl());
						cor.setOrderid(or.getOrderid());
						cor.setParentid(or.getParentid());
						wrap.add(cor);
					}
					result.setData(wrap);	
					result.setTotal(count);
				}
				else if(domain.equalsIgnoreCase("LutRole")){
					List<LutRole> wrap = new ArrayList<LutRole>();

					rs= dao.kendojson(request, domain);
					count=dao.resulsetcount(request, domain);
					
					/*for(int i=0;i<rs.size();i++){
						LutRole or=(LutRole) rs.get(i);
						LutRole cor=new LutRole();
						cor.setId(or.getId());
						cor.setRoleNameMon(or.getRoleNameMon());
						cor.setRoleNameEng(or.getRoleNameEng());
						cor.setAccess(or.getAccess());
						wrap.add(cor);
					}*/
					result.setData(rs);	
					result.setTotal(count);
				}
							
				else if(domain.equalsIgnoreCase("LutUsers")){
					List<LutUsers> wrap = new ArrayList<LutUsers>();

					rs= dao.kendojson(request, domain);
					count=dao.resulsetcount(request, domain);
				 	                 	
					for(int i=0;i<rs.size();i++){
						LutUsers or=(LutUsers) rs.get(i);
						LutUsers cor=new LutUsers();
						cor.setId(or.getId());
						cor.setFamnamemon(or.getFamnamemon());
						cor.setFamnameeng(or.getFamnameeng());
						cor.setGivnameeng(or.getGivnameeng());
						cor.setGivnamemon(or.getGivnamemon());
						cor.setIsactive(or.getIsactive());
						cor.setUsername(or.getUsername());
						cor.setUserpass(or.getUserpass());
						cor.setEmail(or.getEmail());
						cor.setMobile(or.getMobile());
						cor.setLpreg(or.getLpreg());
						cor.setIsactive(or.getIsactive());
						cor.setIspublic(or.getIspublic());
						cor.setRoleid(or.getRoleid());
						cor.setPositionid(or.getPositionid());
						cor.setDivisionid(or.getDivisionid());
						cor.setGroupid(or.getGroupid());
						cor.setStepid(or.getStepid());
						wrap.add(cor);
					}
					result.setData(wrap);	
					result.setTotal(count);
				}
				else if(domain.equalsIgnoreCase("SubLicensesConfig")){
					domain="SubLicenses";					
					JSONObject jsonObj = new JSONObject(request);
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					jsonObj.put("custom", "where lpReg="+loguser.getLpreg()+" and weekly=1 and configured=0");
					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				
								
				else if(domain.equalsIgnoreCase("RegReportReqWeek")){
					domain="RegReportReq";
					JSONObject jsonObj = new JSONObject(request);
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					jsonObj.put("custom", "where lpReg="+loguser.getLpreg()+"  and isactive=1");
					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("SubLicensesDivision")){
					domain="SubLicenses";
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					JSONObject jsonObj = new JSONObject(request);
					if(loguser.getDivisionid()==3){
						jsonObj.put("custom", "where licTypeId=1");
					}
					else if(loguser.getDivisionid()==1){
						jsonObj.put("custom", "where licTypeId=2 and mintype!=5");
					}
					else if(loguser.getDivisionid()==2){
						jsonObj.put("custom", "where licTypeId=2 and mintype=5");
					}
					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("SubLicensesHeadMount")){
					domain="SubLicenses";
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					JSONObject jsonObj = new JSONObject(request);
					if(loguser.getDivisionid()==3){
						jsonObj.put("custom", "where licTypeId=1");	
					}
					else if(loguser.getDivisionid()==2){
						jsonObj.put("custom", "where (licTypeId!=1 and mintype=5)");	
					}
					else if(loguser.getDivisionid()==1){
						jsonObj.put("custom", "where (licTypeId!=1 and mintype!=5)");	
					}
					
			/*		JSONObject jsonObj = new JSONObject(request);					
					jsonObj.put("custom", "where licTypeId=2 and mintype!=6");	*/				
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("SubLicensesHeadCoal")){
					domain="SubLicenses";
					JSONObject jsonObj = new JSONObject(request);				
					jsonObj.put("custom", "where (licTypeId=2 and mintype=5) or (licTypeId=3 and mintype=5)");					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("SubLicensesHeadGeo")){
					domain="SubLicenses";
					JSONObject jsonObj = new JSONObject(request);					
					jsonObj.put("custom", "where licTypeId=1");					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("SubLicensesPlan")){
					domain="SubLicenses";
					JSONObject jsonObj = new JSONObject(request);
					jsonObj.put("custom", "where licTypeId=1 and redemptionplan=0 and plan=1");					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				else if(domain.equalsIgnoreCase("SubLicensesPlanCom")){
					domain="SubLicenses";
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					JSONObject jsonObj = new JSONObject(request);
					//jsonObj.put("custom", "where lpReg="+loguser.getLpreg()+" and configured=0 and licTypeId=2 and redemptionplan=0 and plan=1");
					jsonObj.put("custom", "where (lpReg="+loguser.getLpreg()+" and licTypeId=2 and report=1) or (lpReg="+loguser.getLpreg()+" and licTypeId=2 and plan=1) or (lpReg="+loguser.getLpreg()+" and licTypeId=3 and report=1)  or (lpReg="+loguser.getLpreg()+" and licTypeId=3 and plan=1)");
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("SubLicensesMainConfiguration")){
					domain="SubLicenses";
					JSONObject jsonObj = new JSONObject(request);
					JSONArray arr= new JSONArray();
					String str="{'field':'licTypeId','dir':'desc'}";
					JSONObject arrObj = new JSONObject(str);
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					arr.put(arrObj);
					jsonObj.put("custom", "where lpReg="+loguser.getLpreg()+"");
					jsonObj.put("sort", arr);		
					//jsonObj.put("sort", [{'field':'licTypeId','dir':'asc'}]);				
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("AnnualRegistrationCom")){
					domain="AnnualRegistration";
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					JSONObject jsonObj = new JSONObject(request);
					//jsonObj.put("custom", "where lpReg='"+loguser.getLpreg()+"' and repstatusid!=1");					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("AnnualRegistrationComHistory")){
					domain="AnnualRegistration";
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					JSONObject jsonObj = new JSONObject(request);
					jsonObj.put("custom", "where lpReg='"+loguser.getLpreg()+"' and repstatusid=1");					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("AnnualRegistrationGov")){
					domain="AnnualRegistration";
					JSONObject jsonObj = new JSONObject(request);
				//	jsonObj.put("custom", "where repstatusid!=0 and lictype="+loguser.getDivisionid()+"");	
					jsonObj.put("custom", "where repstatusid!=0 ");	
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("AnnualRegistrationClient")){
					domain="AnnualRegistration";
					JSONObject jsonObj = new JSONObject(request);
					jsonObj.put("custom", "where repstatusid=1");	
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				else if(domain.equalsIgnoreCase("AnnualRegistrationPlan")){
					domain="AnnualRegistration";
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					JSONObject jsonObj = new JSONObject(request);
					if(loguser.getDivisionid()==3){
						jsonObj.put("custom", "where lictype=1 and divisionid=3 and reporttype=3 and (repstatusid=7 or repstatusid=8 or repstatusid=2) ");	
					}
					else if(loguser.getDivisionid()==2){
						jsonObj.put("custom", "where lictype=2 and divisionid=2 and reporttype=3 and minid=5 and (repstatusid=7 or repstatusid=8)");	
					}
					else if(loguser.getDivisionid()==1){
						jsonObj.put("custom", "where lictype=2 and divisionid=1 and reporttype=3 and minid!=5 and (repstatusid=7 or repstatusid=8) ");	
					}
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);				
				}
				
				
				else if(domain.equalsIgnoreCase("AnnualRegistrationColor")){
					domain="AnnualRegistration";
					JSONObject jsonObj = new JSONObject(request);
					/*if(loguser.getDivisionid()==3){
						jsonObj.put("custom", "where lictype=1 and reporttype=3 and repstatusid=7 or repstatusid=8 ");	
					}
					else if(loguser.getDivisionid()==2){
						jsonObj.put("custom", "where lictype=2 and reporttype=3 and minid=5 and repstatusid=7 or repstatusid=8");	
					}
					else if(loguser.getDivisionid()==1){
						jsonObj.put("custom", "where lictype=2 and reporttype=3 and minid!=5 and repstatusid=7 or repstatusid=8 ");	
					}*/
					//jsonObj.put("custom", "where repstatusid!=0 ");	
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);				
				}
				
				else if(domain.equalsIgnoreCase("AnnualRegistration")){
					domain="AnnualRegistration";
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					JSONObject jsonObj = new JSONObject(request);
					if(loguser.getDivisionid()==3){
						jsonObj.put("custom", "where repstatusid!=0 and lictype=1");	
					}
					else if(loguser.getDivisionid()==2){
						jsonObj.put("custom", "where repstatusid!=0 and lictype=2 and minid=5");	
					}
					else if(loguser.getDivisionid()==1){
						jsonObj.put("custom", "where repstatusid!=0 and lictype=2 and minid!=5");	
					}
					//jsonObj.put("custom", "where repstatusid!=0 ");	
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				else if(domain.equalsIgnoreCase("AnnualRegistration")){
					domain="AnnualRegistration";
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					JSONObject jsonObj = new JSONObject(request);
					if(loguser.getDivisionid()==3){
						jsonObj.put("custom", "where repstatusid!=0 and lictype=1");	
					}
					else if(loguser.getDivisionid()==2){
						jsonObj.put("custom", "where repstatusid!=0 and lictype=2 and minid=5");	
					}
					else if(loguser.getDivisionid()==1){
						jsonObj.put("custom", "where repstatusid!=0 and lictype=2 and minid!=5");	
					}
					//jsonObj.put("custom", "where repstatusid!=0 ");	
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				
				}
				else if(domain.equalsIgnoreCase("AnnualRegistrationXreportMin")){
					domain="AnnualRegistration";		
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					JSONObject jsonObj = new JSONObject(request);
					jsonObj.put("custom", "where reporttype=4 and divisionid="+loguser.getDivisionid()+" and xtype=0 and repstatusid in (1,2,3,7)");
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				}
				
				else if(domain.equalsIgnoreCase("AnnualRegistrationXreportHistory")){
					domain="AnnualRegistration";		
					//LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					JSONObject jsonObj = new JSONObject(request);
					//jsonObj.put("custom", "where reporttype=4 and divisionid="+loguser.getDivisionid()+" and xtype=0 and repstatusid=7");
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					result.setData(rs);
					result.setTotal(count);
				}
	
				else if(domain.equalsIgnoreCase("RegReportReq")){
					JSONObject jsonObj = new JSONObject(request);
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					jsonObj.put("custom", "where lpReg="+loguser.getLpreg()+" and wk=0  and isactive=1");
					
					List<RegReportReq> wrap = new ArrayList<RegReportReq>();

					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
																				
					for(int i=0;i<rs.size();i++){
						RegReportReq or=(RegReportReq) rs.get(i);
												
						RegReportReq cor=new RegReportReq();
						cor.setId(or.getId());
						cor.setAddBunLicenseNum(or.getAddBunLicenseNum());
						cor.setAreaName(or.getAreaName());
						cor.setBundledLicenseNum(or.getBundledLicenseNum());
						cor.setDivisionId(or.getDivisionId());
						cor.setLicenseXB(or.getLicenseXB());
						cor.setLatestChangeDateTime(or.getLatestChangeDateTime());
					//	cor.setAnnualRegistrations(or.getAnnualRegistrations());
						cor.setGroupid(or.getGroupid());
						cor.setIsactive(or.getIsactive());
						cor.setMineralid(or.getMineralid());
						cor.setIsconfiged(or.getIsconfiged());
						cor.setLictype(or.getLictype());
						cor.setLnkReqAnns(or.getLnkReqAnns());
						cor.setLnkReqPvs(or.getLnkReqPvs());
						cor.setLpReg(or.getLpReg());
						cor.setMv(or.getMv());
						cor.setReportTypeId(or.getReportTypeId());
						cor.setWk(or.getWk());
		
					/*	if(or.getLnkReqAnns().size()>0){
							cor.setLnkAnns(1);
						}
						else{
							cor.setLnkAnns(0);
						}
						if(or.getLnkReqPvs().size()>0){
							cor.setLnkPvs(1);
						}
						else{
							cor.setLnkPvs(0);
						}*/
						cor.setXreport(or.getXreport());
						cor.setXplan(or.getXplan());
						cor.setCreport(or.getCreport());
						cor.setCplan(or.getCplan());
						cor.setCyear(or.getCyear());
						
						/*for(int k=0;k<or.getAnnualRegistrations().size();k++){
							AnnualRegistration ann = or.getAnnualRegistrations().get(k);
							System.out.println("reptype: "+ann.getReporttype());
							if(ann.getReporttype()==4){
								cor.setReport(1);
							}
							if(ann.getReporttype()==3){
								cor.setPlan(1);
							}
						}*/
						
						wrap.add(cor);
					}
					result.setData(wrap);	
					result.setTotal(count);
				
				}
				else if(domain.equalsIgnoreCase("RegWeeklyMontly")){				
					JSONObject jsonObj = new JSONObject(request);
					
					jsonObj.put("custom", "where wrid='"+jsonObj.getString("id")+"'");						
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					
					result.setData(rs);
					result.setTotal(count);
				
				}
				else if(domain.equalsIgnoreCase("RegWeeklyMontlyPrevious")){
					domain="RegWeeklyMontly";
					JSONObject jsonObj = new JSONObject(request);					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("WeeklyRegistrationGovHistory")){
					domain="WeeklyRegistration";
					JSONObject jsonObj = new JSONObject(request);	
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					jsonObj.put("custom", "where divisionid='"+loguser.getDivisionid()+"' and repstatusid=1 ");					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("WeeklyRegistrationGov")){
					domain="WeeklyRegistration";
					JSONObject jsonObj = new JSONObject(request);	
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					jsonObj.put("custom", "where divisionid='"+loguser.getDivisionid()+"' and repstatusid=7 ");					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("WeeklyRegistrationCom")){
					domain="WeeklyRegistration";
					JSONObject jsonObj = new JSONObject(request);	
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					jsonObj.put("custom", "where lpReg='"+loguser.getLpreg()+"'");					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("WeeklyRegistrationStatus")){
					domain="WeeklyRegistration";
					JSONObject jsonObj = new JSONObject(request);	
					LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
					jsonObj.put("custom", "where lpReg='"+loguser.getLpreg()+"' and repstatusid!=1");					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				else if(domain.equalsIgnoreCase("RegWeeklyMontlyGroup")){	
					domain="RegWeeklyMontly";
					JSONObject jsonObj = new JSONObject(request);
					
					//jsonObj.put("group", "[{'field':'SUBMISSIONDATE'}]");						
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);	
					
					result.setData(rs);
					result.setTotal(count);
				
				}
				
				
				else if(domain.equalsIgnoreCase("SubLegalpersons")){				
					JSONObject jsonObj = new JSONObject(request);
					
					System.out.println("end bn####"+request);
					
					jsonObj.put("custom", "where ispublic=0");	
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					
					result.setData(rs);
					result.setTotal(count);
				}
				else if(domain.equalsIgnoreCase("SubLegalpersonsPublic")){	
					domain="SubLegalpersons";
					JSONObject jsonObj = new JSONObject(request);
					
					jsonObj.put("custom", "where ispublic=1");	
					
					rs= dao.kendojson(jsonObj.toString(), domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					
					result.setData(rs);
					result.setTotal(count);
				}
				else if(domain.equalsIgnoreCase("LutReporttype")){
					List<LutReporttype> wrap = new ArrayList<LutReporttype>();

					rs= dao.kendojson(request, domain);
					count=dao.resulsetcount(request, domain);
					
					for(int i=0;i<rs.size();i++){
						LutReporttype or=(LutReporttype) rs.get(i);
						LutReporttype cor=new LutReporttype();
						cor.setId(or.getId());
						cor.setReportTypeNameEng(or.getReportTypeNameEng());
						cor.setReportTypeNameMon(or.getReportTypeNameMon());
						wrap.add(cor);
					}
					result.setData(wrap);	
					result.setTotal(count);
				
				}
				else if(domain.equalsIgnoreCase("DataMinPlan1")){
					List<DataMinPlan1> wrap = new ArrayList<DataMinPlan1>();
					
					domain="DataMinPlan1";
					
					JSONObject jsonObj = new JSONObject(request);
					jsonObj.put("custom", " d, AnnualRegistration t where t.id=d.planid and d.noteid=185 and t.reporttype=3");	
					
				/*	JSONObject jsonObj = new JSONObject(request);
					jsonObj.put("custom", " d, AnnualRegistration t where t.id=d.planid and t.repstatusid=1");	
					
					List<Object[]> cc=  (List<Object[]>) dao.getHQLResult("select t.id ,a.horde,a.aimagid, a.sumid, a.mineralid,a.deposidid,a.countries,a.appdate,a.yearcapacity,a.workyear, a.minetypeid,a.komissid, a.komissdate,a.komissakt,a.bombid from  AnnualRegistration t, DataMinPlan1 d, LnkReqAnn a  where a.reqid=t.reqid and t.id=d.planid and t.repstatusid=1 group by t.id ,a.horde,a.aimagid, a.sumid, a.mineralid,a.deposidid,a.countries,a.appdate,a.yearcapacity,a.workyear, a.minetypeid,a.komissid, a.komissdate,a.komissakt,a.bombid", "list");
					
					ArrayList<Object[]> arr = (ArrayList<Object[]>) cc;
					String ids="";
					for(Object[] item:cc){
						if(ids.length()>0){
							ids=ids+","+item;
							
							System.out.println("1 "+item[0]+ " 2 " + item[1]+" 3"+ item[2]+" 4"+item[3]+" 5"+item[4]+ " 6 " + item[5]+"7"+ item[6]+" 8"+item[7]+"");
						}
						else{
							ids=String.valueOf(item);
						}
					}
					System.out.println("@@ size "+cc.size());
					*/
					
					rs=dao.kendojson(request, domain);
					//List<Object[]> cc=(List<Object[]>) dao.kendojson(request, domain);
					System.out.println("@@ size "+rs.size());
					
					//rs= dao.kendojson(request, domain);
					count=dao.resulsetcount(jsonObj.toString(), domain);
					for(int i=0;i<rs.size();i++){
						DataMinPlan1 or=(DataMinPlan1) rs.get(i);
						DataMinPlan1 cor=new DataMinPlan1();
						cor.setDataIndex(or.getDataIndex());
						cor.setPlanid(or.getPlanid());
						cor.setNoteid(or.getNoteid());
						cor.setData9(or.getData9());
						cor.setData8(or.getData8());
						cor.setData7(or.getData7());
						cor.setData6(or.getData6());
						cor.setData5(or.getData5());
						cor.setData4(or.getData4());
						cor.setData3(or.getData3());
						cor.setData2(or.getData2());
						cor.setData1(or.getData1());
						cor.setAnnualRegistration(or.getAnnualRegistration());
						cor.setLpName(or.getAnnualRegistration().getLpName());
						wrap.add(cor);
					}
		/*			for(Object item:rs){
						DataMinPlan1 cor=new DataMinPlan1();
						cor.setId(Long.parseLong(item[0].toString()));
						if(item[1]!=null){
							cor.setLpName(item[1].toString());
						}
						if(item[2]!=null){
							cor.setLicenseXB(item[2].toString());					
						}
						if(item[3]!=null){
							cor.setHorde(item[3].toString());
						}
						if(item[4]!=null){
							cor.setAimagid(item[4].toString());
						}
						if(item[5]!=null){
							cor.setSumid(item[5].toString());
						}
						if(item[6]!=null){
							cor.setMineralid(item[6].toString());
						}
						if(item[7]!=null){
							cor.setDeposidid(item[7].toString());
						}
						if(item[8]!=null){
							cor.setCountries(item[8].toString());
						}
						if(item[9]!=null){
							cor.setAppdate(item[9].toString());
						}
						if(item[10]!=null){
							cor.setYearcapacity(item[10].toString());
						}
						if(item[11]!=null){
							cor.setWorkyear(item[11].toString());
						}
						if(item[12]!=null){
							cor.setMinetypeid(item[12].toString());
						}
						if(item[13]!=null){
							cor.setStatebudgetid(item[13].toString());
						}
						if(item[14]!=null){
							cor.setConcetrate(item[14].toString());
						}
						if(item[15]!=null){
							cor.setKomissid(item[15].toString());
						}
						if(item[16]!=null){
							cor.setKomissdate(item[16].toString());
						}
						if(item[17]!=null){
							cor.setKomissakt(item[17].toString());
						}
						if(item[18]!=null){
							cor.setStartdate(item[18].toString());
						}
						if(item[19]!=null){
							cor.setBombid(item[19].toString());
						}
						if(item[20]!=null){
							cor.setBombtype(item[20].toString());
						}
						
						
						if(item[21]!=null){
							cor.setData1(item[21].toString());		
						}
						if(item[22]!=null){							
							cor.setData2(item[22].toString());
						}
						if(item[23]!=null){							
							cor.setData3(Double.parseDouble(item[23].toString()));
						}
						if(item[24]!=null){
							cor.setData4(Long.parseLong(item[24].toString()));
						}
						if(item[25]!=null){
							cor.setData5(item[25].toString());
						}
						if(item[26]!=null){
							cor.setData6(item[26].toString());
						}
						if(item[27]!=null){
							cor.setData7(Double.parseDouble(item[27].toString()));
						}
						if(item[29]!=null){
							cor.setData9(Double.parseDouble(item[29].toString()));
						}
						if(item[30]!=null){
							cor.setNoteid(Long.parseLong(item[30].toString()));
						}
						if(item[31]!=null){
							cor.setPlanid(Long.parseLong(item[31].toString()));
						}
						if(item[32]!=null){
							cor.setDataIndex(Long.parseLong(item[32].toString()));
						}
						
						wrap.add(cor);
					}*/
					result.setData(wrap);	
					result.setTotal(wrap.size());
				
				}
				
				
				else{
					System.out.println(request);;
					rs= dao.kendojson(request, domain);
					count=dao.resulsetcount(request, domain);
					result.setData(rs);
					result.setTotal(count);
				}
				return  result;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
/*	@RequestMapping(value = "/user/upload/data", method = RequestMethod.POST)
    public @ResponseBody String content(@RequestParam MultipartFile file, @RequestParam String branch,@RequestParam String department,HttpServletRequest req) {
	 
		System.out.println("assa"+branch);
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
			String fileName =file.getOriginalFilename();
   			int mid = fileName.lastIndexOf(".");
   			String fex = fileName.substring(mid+1,fileName.length());
   	
			String renName="uploads/"+fileName;
   			renName=renName.trim();
   			
   			System.out.println("renameed====> "+renName);
   			
   			
			File filepath = new File(ufile+"/"+fileName);
			
			file.transferTo(filepath);
			
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
										rs.setUniversityid(loguser.getUniversityid());
										if(department!=null){
											rs.setDepartmentid(Integer.parseInt(department));
										}
										if(branch!=null){
											rs.setBranchid(Integer.parseInt(branch));
										}
										
										String username=inforow.getCell(6).getStringCellValue();
										rs.setUsername(username);
										rs.setEnabled(true);
										rs.setPassword(passwordEncoder.encode(username));
										rs.setStudentBusDir(inforow.getCell(9).getStringCellValue());
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
   
    }
	*/
	
	@RequestMapping(value = "/getDivisionList", method = RequestMethod.GET)
	public String getDivisionList() {
		JSONArray result = new JSONArray();
		List<LutDivision> rs= (List<LutDivision>) dao.getHQLResult("from LutDivision t", "list");
		for(LutDivision an : rs){
			JSONObject anobj = new JSONObject();
			anobj.put("id", an.getId());
			anobj.put("name", an.getDivisionnamemon());
			result.put(anobj);
		}
		return result.toString();
	}
	
	@RequestMapping(value = "/getReportTypes", method = RequestMethod.GET)
	public String getReportTypes() {
		JSONArray result = new JSONArray();
		List<LutReporttype> rs= (List<LutReporttype>) dao.getHQLResult("from LutReporttype t", "list");
		for(LutReporttype an : rs){
			JSONObject anobj = new JSONObject();
			anobj.put("id", an.getId());
			anobj.put("name", an.getReportTypeNameMon());
			result.put(anobj);
		}
		return result.toString();
	}
	
	@RequestMapping(value = "/getFormNotes", method = RequestMethod.POST)
	public String getFormNotes(@RequestBody String jsonStr) {
		JSONArray result = new JSONArray();
		JSONObject obj = new JSONObject(jsonStr);
		if (obj.has("divisionid") && obj.has("reporttype")){
			List<LutFormNotes> rs= (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.divisionid = " + obj.getLong("divisionid") + "and t.reporttype = " + obj.getLong("reporttype") + "and t.isform = 1", "list");
			for(LutFormNotes an : rs){
				JSONObject anobj = new JSONObject();
				anobj.put("id", an.getId());
				anobj.put("name", an.getNote());
				result.put(anobj);
			}
		}
		
		return result.toString();
	}
	
	@RequestMapping(value = "/getPlanListByForm/{noteid}", method = RequestMethod.POST)
	public List<Long> getPlanListByForm(@PathVariable Long noteid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LutUsers currentUser = null;
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			currentUser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username = '" + userDetail.getUsername() + "'", "current");
		}
		if (currentUser != null){
			return (List<Long>) dao.getHQLResult("select t.id from AnnualRegistration t where t.repstatusid = 1 and t.divisionid = " + currentUser.getDivisionid() , "list");
		}
		else{
			return null;
		}
	}
	
	
	@RequestMapping(value = "/getMessages", method = RequestMethod.GET)
	public String getMessages() {
		JSONObject result = new JSONObject();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LutUsers currentUser = null;
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			currentUser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username = '" + userDetail.getUsername() + "'", "current");
		}
		JSONArray messageArray = new JSONArray();
		JSONArray alertArray = new JSONArray();
		List<LutNews> rs= (List<LutNews>) dao.getHQLResult("from LutNews t order by t.createdat desc", "list");
		List<Long> logs= (List<Long>) dao.getHQLResult("select t.newsid from LnkNewsLog t where t.userid = " + ((currentUser != null) ? currentUser.getId() : 0), "list");
		if (rs.size() > 0){
			for(LutNews news : rs){
				
				JSONObject temp = new JSONObject();
				temp.put("id", news.getId());
				temp.put("title", news.getTitle());
				temp.put("description", news.getDescription());
				temp.put("createdat", news.getCreatedat());
				temp.put("status", news.getStatus());
				
				boolean isread = false;
				for(Long t : logs){
					if (t == news.getId()){
						isread = true;
					}
				}
				
				temp.put("isread", isread);
				
				if (news.getStatus() == 1){
					alertArray.put(temp);
				}
				else{
					messageArray.put(temp);
				}
			}
		}
		result.put("messages", messageArray);
		result.put("alerts", alertArray);
		return result.toString();
	}

    @RequestMapping(value = "/allMessages", method = RequestMethod.GET)
    public String allMessages() {
        JSONObject result = new JSONObject();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LutUsers currentUser = null;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            currentUser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username = '" + userDetail.getUsername() + "'", "current");
        }
        JSONArray messageArray = new JSONArray();
        List<LutNews> rs= (List<LutNews>) dao.getHQLResult("from LutNews t order by t.createdat desc", "list");
        List<Long> logs= (List<Long>) dao.getHQLResult("select t.newsid from LnkNewsLog t where t.userid = " + ((currentUser != null) ? currentUser.getId() : 0), "list");
        if (rs.size() > 0){
            for(LutNews news : rs){

                JSONObject temp = new JSONObject();
                temp.put("id", news.getId());
                temp.put("title", news.getTitle());
                temp.put("description", news.getDescription());
                temp.put("createdat", news.getCreatedat());
                temp.put("status", news.getStatus());

                boolean isread = false;
                for(Long t : logs){
                    if (t == news.getId()){
                        isread = true;
                    }
                }

                temp.put("isread", isread);
                messageArray.put(temp);
            }
        }
        result.put("messages", messageArray);
        return result.toString();
    }
	
	@RequestMapping(value = "/updateNewsData", method = RequestMethod.POST)
	public boolean updateNewsData(@RequestBody String jsonStr) {
		JSONObject jsonObj = new JSONObject(jsonStr);
		if (jsonObj.has("id")){
			LutNews news = (LutNews) dao.getHQLResult("from LutNews t where t.id = " + jsonObj.getLong("id"), "current");
			if (news != null){
				news.setTitle(jsonObj.getString("title"));
				news.setDescription(jsonObj.getString("description"));
				news.setStatus(jsonObj.getInt("status"));
				dao.PeaceCrud(news, "LutNews", "save", (long) 0, 0, 0, null);
				return true;
			}
		}
		else{
			LutNews news = new LutNews();
			news.setTitle(jsonObj.getString("title"));
			news.setDescription(jsonObj.getString("description"));
			news.setStatus(jsonObj.getInt("status"));
			dao.PeaceCrud(news, "LutNews", "save", (long) 0, 0, 0, null);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST)
	public boolean changeUserPassword(@RequestBody String jsonStr) {
		JSONObject jsonObj = new JSONObject(jsonStr);
		UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
		if (loguser != null && jsonObj.has("old_password") && jsonObj.has("new_password") && jsonObj.has("new_password_confirm") && jsonObj.getString("new_password").equals(jsonObj.getString("new_password_confirm"))){
			if (passwordEncoder.matches(jsonObj.getString("old_password"), loguser.getUserpass())){		
				loguser.setUserpass(passwordEncoder.encode(jsonObj.getString("new_password")));
				dao.PeaceCrud(loguser, "LutUser", "save", (long) 0, 0, 0, null);
				return true;
			}
		}
		return false;
	}
	
	/*@RequestMapping(value = "/selectFormYear/{id}", method = RequestMethod.GET)
	public boolean selectFormYear(@PathVariable Long id) {
		List<LutYear> years = (List<LutYear>) dao.getHQLResult("from LutYear t", "list");
		for(LutYear y : years){
			if (y.getId().equals(id)){
				y.setIsselected(true);
			}
			else{
				y.setIsselected(false);
			}
			dao.PeaceCrud(y, "LutYear", "save", (long) 0, 0, 0, null);
		}
		return true;
	}*/
	
/*	@RequestMapping(value = "/getSelectedFormYearNEw/{ac}/{type}/{div}/{year}/{licensenum}", method = RequestMethod.GET)
	public Long getSelectedFormYearNew(@PathVariable Long ac,@PathVariable Long type,@PathVariable Long div,@PathVariable Long licensenum) {
		List<LutYear> years = (List<LutYear>) dao.getHQLResult("from LutYear t where t.divisionid="+div+" and t.type = " + type+" order by id desc", "list");
		if ((years == null || years.size() != 1) || (years != null && years.size() > 0 && years.get(0).getIsactive() == false)){
			List<SubLicenses> lics = (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.licenseNum ='" + licensenum + "'", "list");
			if (lics != null && !lics.isEmpty() && lics.size() > 0){
				if (type == 3 && lics.get(0).getLplan() == true && years != null && !years.isEmpty()){
					return (long) years.get(0).getValue();
				}
				if (type == 4 && lics.get(0).getLreport() == true && years != null && !years.isEmpty()){
					return (long) years.get(0).getValue();
				}
			}
		}
		else{
			return (long) years.get(0).getValue();
		}
		return (long) 0;
	}*/
	
	@RequestMapping(value = "/getSelectedFormYear/{ac}/{type}/{div}/{licensenum}", method = RequestMethod.GET)
	public Long getSelectedFormYear(@PathVariable Long ac,@PathVariable Long type,@PathVariable Long div,@PathVariable Long licensenum) {
		List<LutYear> years = (List<LutYear>) dao.getHQLResult("from LutYear t where t.divisionid="+div+" and t.type = " + type+" order by id desc", "list");
		if ((years == null || years.size() != 1) || (years != null && years.size() > 0 && years.get(0).getIsactive() == false)){
			List<SubLicenses> lics = (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.licenseNum ='" + licensenum + "'", "list");
			if (lics != null && !lics.isEmpty() && lics.size() > 0){
				if (type == 3 && lics.get(0).getLplan() == true && years != null && !years.isEmpty()){
					return (long) years.get(0).getValue();
				}
				if (type == 4 && lics.get(0).getLreport() == true && years != null && !years.isEmpty()){
					return (long) years.get(0).getValue();
				}
			}
		}
		else{
			return (long) years.get(0).getValue();
		}
		return (long) 0;
	}
	
	
	@RequestMapping(value = "/deleteNews/{id}", method = RequestMethod.GET)
	public boolean deleteNews(@PathVariable Long id) {
		if (id > 0){
			
			LutNews news = (LutNews) dao.getHQLResult("from LutNews t where t.id = " + id, "current");
			List<LnkNewsLog> logs= (List<LnkNewsLog>) dao.getHQLResult("from LnkNewsLog t where t.newsid = " + news.getId(), "list");
			if (logs.size() > 0){
				for(LnkNewsLog l : logs){
					dao.PeaceCrud(null, "LnkNewsLog", "delete", (long) l.getId(), 0, 0, "id");
				}
			}
			
			dao.PeaceCrud(null, "LutNews", "delete", (long) news.getId(), 0, 0, "id");
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "/markAsReadMessage/{msgid}", method = RequestMethod.GET)
	public boolean markAsReadMessage(@PathVariable Long msgid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			LutUsers currentUser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username = '" + userDetail.getUsername() + "'", "current");
			if (currentUser != null){
				LutNews msg = (LutNews) dao.getHQLResult("from LutNews t where t.id = " + msgid, "current");
				if (msg != null){
					LnkNewsLog log = (LnkNewsLog) dao.getHQLResult("from LnkNewsLog t where t.newsid = " + msgid + " and t.userid = " + currentUser.getId(), "current");
					if (log == null){
						log = new LnkNewsLog();
						log.setNewsid(msg.getId());
						log.setUserid(currentUser.getId());
						dao.PeaceCrud(log, "LnkNewsLog", "save", (long) 0, 0, 0, null);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	@RequestMapping(value = "/stats/dashboard/{id}", method = RequestMethod.GET)
	public String getstats(@PathVariable Long id){
		JSONObject response = new JSONObject();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			LutUsers currentUser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username = '" + userDetail.getUsername() + "'", "current");
			Calendar cal = Calendar.getInstance();
			int planYr = cal.get(Calendar.YEAR);
			if (currentUser != null){
				if (currentUser.getDivisionid() == 1 || currentUser.getDivisionid() == 2){
					//id=1 - yavtsaar buleglej statistic avah
					if (id == 1){
						JSONArray xreportsArray = new JSONArray();
						for(int reporttype=3;reporttype<=4;reporttype++){
							JSONArray repstepArray = new JSONArray();
							for(int repstep=1;repstep<=6;repstep++){
								List<Long> ars= (List<Long>) dao.getHQLResult("select count(*) from AnnualRegistration t where t.reportyear="+planYr+" and t.istodotgol = 0 and t.repstatusid = 7 and t.xtype != 0 and t.divisionid = " + currentUser.getDivisionid() + " and t.repstepid =" + repstep + " and t.reporttype = " + reporttype + " and t.lictype!=1 " + ((currentUser.getDivisionid() == 1) ? " and t.minid != 5 " : (currentUser.getDivisionid() == 5) ? " and t.minid = 5" : ""), "list");
								if (ars.isEmpty()){
									repstepArray.put(0);
								}
								else{
									repstepArray.put(ars.get(0));
								}
							}
							response.put("stat"+reporttype, repstepArray);
							
							JSONArray repstepArrayBack = new JSONArray();
							for(int repstep=1;repstep<=6;repstep++){
								List<Long> ars= (List<Long>) dao.getHQLResult("select count(*) from AnnualRegistration t where t.reportyear="+planYr+" and  t.istodotgol = 0 and t.istodotgol = 0 and t.repstatusid = 2 and t.xtype != 0 and t.divisionid = " + currentUser.getDivisionid() + " and t.rejectstep = "+ repstep +" and t.reporttype = " + reporttype + " and t.lictype!=1 " + ((currentUser.getDivisionid() == 1) ? " and t.minid != 5 " : (currentUser.getDivisionid() == 5) ? " and t.minid = 5" : ""), "list");
								if (ars.isEmpty()){
									repstepArrayBack.put(0);
								}
								else{
									repstepArrayBack.put((-1)*ars.get(0));
								}
							}
							response.put("stat2"+reporttype, repstepArrayBack);
							
							List<Long> ars= (List<Long>) dao.getHQLResult("select count(*) from AnnualRegistration t where t.reportyear="+planYr+" and t.istodotgol = 0 and t.repstatusid = 7 and t.xtype = 0 and t.divisionid = " + currentUser.getDivisionid() + " and t.reporttype = " + reporttype + " and t.lictype!=1 " + ((currentUser.getDivisionid() == 1) ? " and t.minid != 5 " : (currentUser.getDivisionid() == 2) ? " and t.minid = 5" : ""), "list");
							if (ars.isEmpty()){
								xreportsArray.put(0);
							}
							else{
								xreportsArray.put(ars.get(0));
							}
							long[] repstatuses = {1,2,7,3};
							JSONArray repstatusarray = new JSONArray();
							for(long repstatus : repstatuses){
								ars= (List<Long>) dao.getHQLResult("select count(*) from AnnualRegistration t where t.reportyear="+planYr+" and  t.istodotgol = 0 and t.repstatusid = " + repstatus + " and t.xtype != 0 and t.divisionid = " + currentUser.getDivisionid() + " and t.reporttype = " + reporttype + " and t.lictype!=1 " + ((currentUser.getDivisionid() == 1) ? " and t.minid != 5 " : (currentUser.getDivisionid() == 5) ? " and t.minid = 5" : ""), "list");
								if (ars.isEmpty()){
									repstatusarray.put(new JSONObject().put("value", 0).put("name", (repstatus == 1) ? "Зөвшөөрсөн" : ((repstatus == 2) ? "Засварт буцаасан" : ((repstatus == 7) ? "Илгээсэн" : "Татгалзсан"))));
								}
								else{
									repstatusarray.put(new JSONObject().put("value", ars.get(0)).put("name", (repstatus == 1) ? "Зөвшөөрсөн" : ((repstatus == 2) ? "Засварт буцаасан" : ((repstatus == 7) ? "Илгээсэн" : "Татгалзсан"))));
								}
							}
							
							response.put("statrepstatus"+reporttype, repstatusarray);
						}
						response.put("statx", xreportsArray);
						
					}
					else if (id == 2){
						List<Object[]> deposits = (List<Object[]>) dao.getHQLResult("select d.depositnamemon, d.depositid from LutDeposit d where " + ((currentUser.getDivisionid() == 1) ? "  d.mineralsid != 5 " : (currentUser.getDivisionid() == 2) ? " d.mineralsid = 5" : ""), "list");
						for(int reporttype=3;reporttype<=4;reporttype++){
							List<Object[]> ars = (List<Object[]>) dao.getHQLResult("select d.depositnamemon, d.depositid, count(t.depositid) from AnnualRegistration t, LutDeposit d where t.reportyear="+planYr+" and  t.istodotgol = 0  and t.reporttype = "+reporttype+" and d.depositid=t.depositid and t.repstatusid in (1,2,7) and t.xtype != 0 and t.divisionid = " + currentUser.getDivisionid() + " and t.lictype!=1 " + ((currentUser.getDivisionid() == 1) ? " and t.minid != 5 " : (currentUser.getDivisionid() == 2) ? " and t.minid = 5" : "") + ((currentUser.getDivisionid() == 1) ? " and d.mineralsid != 5 " : (currentUser.getDivisionid() == 2) ? " and d.mineralsid = 5" : "") + " group by d.depositnamemon, d.depositid", "list");
							JSONArray repsarrayall = new JSONArray();
							
							for(Object[] d : deposits){
								Boolean isfilled = false;
								for(Object[] obj : ars){
									if (d[1] == obj[1]){
										isfilled = true;
										repsarrayall.put(obj[2]);
									}
								}
								if (!isfilled){
									repsarrayall.put(0);
								}
							}
							response.put("deps"+reporttype+"all", repsarrayall);
							
							ars = (List<Object[]>) dao.getHQLResult("select d.depositnamemon, d.depositid, count(t.depositid) from AnnualRegistration t, LutDeposit d where t.reportyear="+planYr+" and t.istodotgol = 0 and t.reporttype = "+reporttype+" and d.depositid=t.depositid and t.repstatusid in (1) and t.xtype != 0 and t.divisionid = " + currentUser.getDivisionid() + " and t.lictype!=1 " + ((currentUser.getDivisionid() == 1) ? " and t.minid != 5 " : (currentUser.getDivisionid() == 2) ? " and t.minid = 5" : "") + ((currentUser.getDivisionid() == 1) ? " and d.mineralsid != 5 " : (currentUser.getDivisionid() == 2) ? " and d.mineralsid = 5" : "") + " group by d.depositnamemon, d.depositid", "list");
							JSONArray repsarray = new JSONArray();
							for(Object[] d : deposits){
								Boolean isfilled = false;
								for(Object[] obj : ars){
									if (d[1] == obj[1]){
										isfilled = true;
										repsarray.put(obj[2]);
									}
								}
								if (!isfilled){
									repsarray.put(0);
								}
							}
							response.put("deps"+reporttype+"1", repsarray);
							JSONArray depositslabel = new JSONArray();
							for(Object[] d : deposits){
								depositslabel.put(d[0]);
							}
							response.put("depslabels", depositslabel);
						}
					}
					else if (id == 3){
                        int reportgeo1 = 0, plangeo1 = 0, plangeo = 0,reportgeo = 0;
                        List<AnnualRegistration> ars = null;
                        if (currentUser.getDivisionid() == 1){
                            ars= (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration where reportyear="+planYr+" and istodotgol = 0 and repstatusid != 0 and DIVISIONID = 3 and LICENSEXB like '%MV-%' and xtype != 0 and minid != 5","list");
                        }
					    else if (currentUser.getDivisionid() == 2){
                            ars= (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration where reportyear="+planYr+" and istodotgol = 0 and repstatusid != 0 and DIVISIONID = 3 and LICENSEXB like '%MV-%' and xtype != 0 and minid = 5","list");
                        }
                        for(AnnualRegistration a : ars){
                            if (a.getReporttype() == 3){
                                if (a.getRepstatusid() == 1){
                                    plangeo1++;
                                }
                                plangeo++;
                            }
                            else if (a.getReporttype() == 4){
                                if (a.getRepstatusid() == 1){
                                    reportgeo1++;
                                }
                                reportgeo++;
                            }
                        }

                        response.put("geomining3", new JSONArray().put(new JSONObject().put("value",reportgeo).put("name","Нийт ирүүлсэн тайлан")).put(new JSONObject().put("value",plangeo).put("name","Нийт ирүүлсэн төлөвлөгөө")));
                        response.put("geomining4", new JSONArray().put(new JSONObject().put("value",reportgeo1).put("name","Баталгаажсан тайлан")).put(new JSONObject().put("value",plangeo1).put("name","Баталгаажсан төлөвлөгөө")));
                    }
				}
			}
		}
		return response.toString();
	}

    @RequestMapping(value = "/setas/todotgol/{id}", method = RequestMethod.POST)
    public Boolean setasTodotgol(@PathVariable Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            LutUsers currentUser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username = '" + userDetail.getUsername() + "'", "current");
            if (currentUser != null){
                AnnualRegistration an = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id = " + id, "current");
                an.setIstodotgol(true);
                dao.PeaceCrud(an, "AnnualRegistration", "save", an.getId(), 0, 0, null);
                return true;
            }
        }
        return false;
    }
    @RequestMapping(value = "/submit/transfer", method = RequestMethod.POST)
    public String submitTransfer(@RequestBody String jsonstr){
        JSONObject result = new JSONObject();
        result.put("status", false);
        DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            LutUsers currentUser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username = '" + userDetail.getUsername() + "'", "current");
            if (currentUser != null && (currentUser.getUsername().equalsIgnoreCase("Lkhagvaa") || currentUser.getUsername().equalsIgnoreCase("Lkhagvabaatar") || currentUser.getUsername().equalsIgnoreCase("Turmunkh"))){
                JSONObject jsonObj = new JSONObject(jsonstr);
                if (jsonObj.has("id") && jsonObj.has("repstepid") && (!jsonObj.isNull("repstatusid") && !jsonObj.isNull("rejectstep"))){
                    AnnualRegistration an = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id = " + jsonObj.getLong("id"), "current");
                    if (an != null){
                        an.setRepstatusid(jsonObj.getLong("repstatusid"));
                        an.setRejectstep(jsonObj.getLong("rejectstep"));
                        an.setRepstepid(jsonObj.getLong("repstepid"));
						an.setIstodotgol((jsonObj.getLong("istodotgol") == 1) ? true : false);
                        if (an.getRepstatusid() == 2){
                            an.setReject(2);
                        }
                        else{
                            an.setReject(0);
                        }

                        dao.PeaceCrud(an, "AnnualRegistration", "save", an.getId(), 0, 0, null);

                        if (jsonObj.has("description") && !jsonObj.isNull("description")){
                            LnkCommentMain newComment = new LnkCommentMain();
                            newComment.setPlanid(an.getId());
                            newComment.setDesid(an.getRepstatusid());
                            newComment.setMcomment(jsonObj.getString("description"));
                            newComment.setUserid(currentUser.getId());
                            newComment.setUsername(currentUser.getUsername());
                            newComment.setCreatedDate(dateFormat1.format(new Date()));
                            newComment.setIsgov((long)2);
                            dao.PeaceCrud(newComment, "LnkCommentMain", "save", (long)0, 0, 0, null);
                        }

                        if (an.getDivisionid() == 1 || an.getDivisionid() == 2){
                            for(int i=2;i<=an.getRepstepid() && i<6;i++){
                                LnkPlanTab tab = (LnkPlanTab) dao.getHQLResult("from LnkPlanTab t where t.planid = " + an.getId() + " and t.tabid = " + i, "current");
                                if (tab == null){
                                    tab = new LnkPlanTab();
                                    tab.setPlanid(an.getId());
                                    tab.setTabid(i);
                                    dao.PeaceCrud(tab, "LnkPlanTab", "save", (long)0, 0, 0, null);
                                }
                            }
                        }
                        result.put("status", true);
                    }
                }
            }
        }
        return result.toString();
    }


	@RequestMapping(value = "/submit/todotgol", method = RequestMethod.POST)
	public String submitTodotgol(@RequestBody String jsonstr){
		JSONObject result = new JSONObject();
		result.put("status", false);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			LutUsers currentUser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username = '" + userDetail.getUsername() + "'", "current");
			if (currentUser != null){
				JSONObject jsonObj = new JSONObject(jsonstr);
				if (jsonObj.has("planid") && jsonObj.has("noteids") && (!jsonObj.isNull("planid") && !jsonObj.isNull("noteids"))){
					AnnualRegistration an = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id = " + jsonObj.getLong("planid"), "current");
					JSONArray noteids = jsonObj.getJSONArray("noteids");
					if (an != null && noteids != null && noteids.length() > 0){
						for(int i=0;i<noteids.length();i++){
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = " + noteids.getLong(i) + " and t.planid = " + an.getId(), "list");
							if (transitions != null && transitions.size() > 0){
								transitions.get(0).setDecisionid(2);
								transitions.get(0).setIstodotgol(true);
								dao.PeaceCrud(transitions.get(0), "LnkPlanTransition", "save", transitions.get(0).getId(), 0, 0, null);
							}
						}
						an.setRepstatusid((long)2);
						an.setIstodotgol(true);
						dao.PeaceCrud(an, "AnnualRegistration", "save", an.getId(), 0, 0, null);
						result.put("status", true);
					}
				}
			}
		}
		return result.toString();
	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

    @RequestMapping(value = "/public/search", method = RequestMethod.POST)
    public String searchAnnualRegistration(@RequestBody String jsonstr){
	    JSONArray result = new JSONArray();
	    JSONObject jsonObject = new JSONObject(jsonstr);
	    if (jsonObject.has("reporttype") && !jsonObject.isNull("reporttype")&&jsonObject.has("q") && !jsonObject.isNull("q")){
	        String q = jsonObject.getString("q");
	        String[] vals = {"licnum", "lpname", "lpreg", "reptype", "year", "x", "lictype", "division", "aimag", "soum", "status"};
	        Long reporttype = jsonObject.getLong("reporttype");
	        if (q != null && q.length() >= 3){
                String querySql = "SELECT A .LICENSEXB, A .LPNAME, A .LP_REG, A .REPORTTYPE, A .REPORTYEAR, A.XTYPE, (SELECT LUT_LICTYPE.LICTYPENAMEMON FROM LUT_LICTYPE WHERE A.LICTYPE = LUT_LICTYPE.LICTYPEID AND ROWNUM = 1), (SELECT LUT_DIVISION.DIVISIONNAMEMON FROM LUT_DIVISION WHERE A.DIVISIONID = LUT_DIVISION.DIVISIONID AND ROWNUM = 1), (SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE A.LICENSENUM = SUB_LICENSES.LICENSENUM AND ROWNUM = 1), (SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE A.LICENSENUM = SUB_LICENSES.LICENSENUM AND ROWNUM = 1), A .REPSTATUSID FROM ANNUAL_REGISTRATION A WHERE (LOWER(A .LICENSENUM) LIKE LOWER('%"+q+"%') OR LOWER(A .LICENSEXB) LIKE LOWER('%"+q+"%') OR LOWER(A .LPNAME) LIKE LOWER('%"+q+"%') OR LOWER(A .LP_REG) LIKE LOWER('%"+q+"%') ) AND A .REPSTATUSID IN (1, 2, 7) AND A.REPORTTYPE = " + reporttype + " ORDER BY A.LPNAME ASC, A.DIVISIONID ASC";
                List<Object[]> resultObj = dao.getNativeSQLResult(querySql, "list");
                if (resultObj != null && resultObj.size() > 0){
                    for(Object[] o : resultObj){
                        JSONObject temp = new JSONObject();
                        for(int i=0;i<11;i++){
                            if (o[i] != null){
                                temp.put(vals[i],o[i]);
                            }
                        }
                        result.put(temp);
                    }
                }
            }

        }
        return result.toString();
    }

    public String getValue(List<Object[]> objects, String division, String type, String repstatus){
        for(Object[] o : objects){
            if (o[0].toString().equalsIgnoreCase(division)){
                if (o[1].toString().equalsIgnoreCase(type)){
                    if (o[2].toString().equalsIgnoreCase(repstatus)){
                        return o[3].toString();
                    }
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/public/stats/{id}", method = RequestMethod.GET)
    public String getHomeStats(@PathVariable Long id){
        JSONArray result = new JSONArray();
        if (id == 1){
            String querySql = "SELECT DIVISIONID, REPORTTYPE, REPSTATUSID, COUNT(*) FROM ANNUAL_REGISTRATION A WHERE A.ISTODOTGOL = 0 AND A.REPSTATUSID IN (1,2,7,3) AND ((A.REPORTTYPE=4 AND A.REPORTYEAR=2016) OR (A.REPORTTYPE = 3 AND A.REPORTYEAR = 2017)) AND A.XTYPE != 0 GROUP BY A.DIVISIONID, A.REPORTTYPE, A.REPSTATUSID ORDER BY A.DIVISIONID";
            List<Object[]> resultObj = dao.getNativeSQLResult(querySql, "list");
            String[] types = {"төлөвлөгөө", "тайлан"};
            String[] stack = {"plan","report"};
            String[] titles = {"Баталгаажсан", "Засварт буцаасан", "Илгээгдсэн","Татгалзсан"};
            String[] repstatuses = {"1","2","7","3"};

            if (resultObj!= null){
                for(int type=3;type<=4;type++) {
                    for (int repstatus = 0; repstatus < repstatuses.length; repstatus++) {
                        JSONArray numberArr = new JSONArray();
                        for (int division = 1; division <= 3; division++) {
                            String val = getValue(resultObj, String.valueOf(division), String.valueOf(type), repstatuses[repstatus]);
                            if (val != null) {
                                numberArr.put(Integer.parseInt(val));
                            } else {
                                numberArr.put(0);
                            }
                        }
                        JSONObject dataObj = new JSONObject();
                        dataObj.put("name", titles[repstatus] + " " + types[type - 3]);
                        dataObj.put("stack", stack[type - 3]);
                        dataObj.put("data", numberArr);
                        result.put(dataObj);
                    }

                }
            }
        }

        return result.toString();
    }

    @RequestMapping(value = "/public/stats/{divisionid}/{id}", method = RequestMethod.GET)
    public String getstatsPublic(@PathVariable Long id,@PathVariable Long divisionid, @RequestBody(required=false) String jsonstr){
        JSONObject response = new JSONObject();
        if (divisionid == 1 || divisionid == 2){
            //id=1 - yavtsaar buleglej statistic avah
            if (id == 1){
                JSONArray xreportsArray = new JSONArray();
                for(int reporttype=3;reporttype<=4;reporttype++){
                    JSONArray repstepArray = new JSONArray();
                    for(int repstep=1;repstep<=6;repstep++){
                        List<Long> ars= (List<Long>) dao.getHQLResult("select count(*) from AnnualRegistration t where t.istodotgol = 0 and t.repstatusid = 7 and t.xtype != 0 and t.divisionid = " + divisionid + " and t.repstepid =" + repstep + " and t.reporttype = " + reporttype + " and t.lictype!=1 " + ((divisionid == 1) ? " and t.minid != 5 " : (divisionid == 5) ? " and t.minid = 5" : ""), "list");
                        if (ars.isEmpty()){
                            repstepArray.put(0);
                        }
                        else{
                            repstepArray.put(ars.get(0));
                        }
                    }
                    response.put("stat"+reporttype, repstepArray);

                    JSONArray repstepArrayBack = new JSONArray();
                    for(int repstep=1;repstep<=6;repstep++){
                        List<Long> ars= (List<Long>) dao.getHQLResult("select count(*) from AnnualRegistration t where t.istodotgol = 0 and t.repstatusid = 2 and t.xtype != 0 and t.divisionid = " + divisionid + " and t.rejectstep = "+ repstep +" and t.reporttype = " + reporttype + " and t.lictype!=1 " + ((divisionid == 1) ? " and t.minid != 5 " : (divisionid == 5) ? " and t.minid = 5" : ""), "list");
                        if (ars.isEmpty()){
                            repstepArrayBack.put(0);
                        }
                        else{
                            repstepArrayBack.put((-1)*ars.get(0));
                        }
                    }
                    response.put("stat2"+reporttype, repstepArrayBack);

                    List<Long> ars= (List<Long>) dao.getHQLResult("select count(*) from AnnualRegistration t where t.istodotgol = 0 and t.repstatusid = 7 and t.xtype = 0 and t.divisionid = " + divisionid + " and t.reporttype = " + reporttype + " and t.lictype!=1 " + ((divisionid == 1) ? " and t.minid != 5 " : (divisionid == 5) ? " and t.minid = 5" : ""), "list");
                    if (ars.isEmpty()){
                        xreportsArray.put(0);
                    }
                    else{
                        xreportsArray.put(ars.get(0));
                    }
                    long[] repstatuses = {1,2,7,3};
                    JSONArray repstatusarray = new JSONArray();
                    for(long repstatus : repstatuses){
                        ars= (List<Long>) dao.getHQLResult("select count(*) from AnnualRegistration t where t.istodotgol = 0 and t.repstatusid = " + repstatus + " and t.xtype != 0 and t.divisionid = " + divisionid + " and t.reporttype = " + reporttype + " and t.lictype!=1 " + ((divisionid == 1) ? " and t.minid != 5 " : (divisionid == 5) ? " and t.minid = 5" : ""), "list");
                        if (ars.isEmpty()){
                            repstatusarray.put(0);
                        }
                        else{
                            repstatusarray.put(ars.get(0));
                        }
                    }

                    response.put("statrepstatus"+reporttype, repstatusarray);
                }
                response.put("statx", xreportsArray);

            }
            else if (id == 2){
                List<Object[]> deposits = (List<Object[]>) dao.getHQLResult("select d.depositnamemon, d.depositid from LutDeposit d where " + ((divisionid == 1) ? "  d.mineralsid != 5 " : (divisionid == 2) ? " d.mineralsid = 5" : ""), "list");
                for(int reporttype=3;reporttype<=4;reporttype++){
                    List<Object[]> ars = (List<Object[]>) dao.getHQLResult("select d.depositnamemon, d.depositid, count(t.depositid) from AnnualRegistration t, LutDeposit d where t.istodotgol = 0 and t.reporttype = "+reporttype+" and d.depositid=t.depositid and t.repstatusid in (1,2,7) and t.xtype != 0 and t.divisionid = " + divisionid + " and t.lictype!=1 " + ((divisionid == 1) ? " and t.minid != 5 " : (divisionid == 2) ? " and t.minid = 5" : "") + ((divisionid == 1) ? " and d.mineralsid != 5 " : (divisionid == 2) ? " and d.mineralsid = 5" : "") + " group by d.depositnamemon, d.depositid", "list");
                    JSONArray repsarrayall = new JSONArray();

                    for(Object[] d : deposits){
                        Boolean isfilled = false;
                        for(Object[] obj : ars){
                            if (d[1] == obj[1]){
                                isfilled = true;
                                repsarrayall.put(obj[2]);
                            }
                        }
                        if (!isfilled){
                            repsarrayall.put(0);
                        }
                    }
                    response.put("deps"+reporttype+"all", repsarrayall);

                    ars = (List<Object[]>) dao.getHQLResult("select d.depositnamemon, d.depositid, count(t.depositid) from AnnualRegistration t, LutDeposit d where t.istodotgol = 0 and t.reporttype = "+reporttype+" and d.depositid=t.depositid and t.repstatusid in (1) and t.xtype != 0 and t.divisionid = " + divisionid + " and t.lictype!=1 " + ((divisionid == 1) ? " and t.minid != 5 " : (divisionid == 2) ? " and t.minid = 5" : "") + ((divisionid == 1) ? " and d.mineralsid != 5 " : (divisionid == 2) ? " and d.mineralsid = 5" : "") + " group by d.depositnamemon, d.depositid", "list");
                    JSONArray repsarray = new JSONArray();
                    for(Object[] d : deposits){
                        Boolean isfilled = false;
                        for(Object[] obj : ars){
                            if (d[1] == obj[1]){
                                isfilled = true;
                                repsarray.put(obj[2]);
                            }
                        }
                        if (!isfilled){
                            repsarray.put(0);
                        }
                    }
                    response.put("deps"+reporttype+"1", repsarray);
                    JSONArray depositslabel = new JSONArray();
                    for(Object[] d : deposits){
                        depositslabel.put(d[0]);
                    }
                    response.put("depslabels", depositslabel);
                }
            }
            else if (id == 3){
                int reportgeo1 = 0, plangeo1 = 0, plangeo = 0,reportgeo = 0;
                List<AnnualRegistration> ars = null;
                if (divisionid == 1){
                    ars= (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration where istodotgol = 0 and repstatusid != 0 and DIVISIONID = 3 and LICENSEXB like '%MV-%' and xtype != 0 and minid != 5","list");
                }
                else if (divisionid == 2){
                    ars= (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration where istodotgol = 0 and repstatusid != 0 and DIVISIONID = 3 and LICENSEXB like '%MV-%' and xtype != 0 and minid = 5","list");
                }
                for(AnnualRegistration a : ars){
                    if (a.getReporttype() == 3){
                        if (a.getRepstatusid() == 1){
                            plangeo1++;
                        }
                        plangeo++;
                    }
                    else if (a.getReporttype() == 4){
                        if (a.getRepstatusid() == 1){
                            reportgeo1++;
                        }
                        reportgeo++;
                    }
                }

                response.put("geomining3", new JSONArray().put(new JSONObject().put("value",reportgeo).put("name","Нийт ирүүлсэн тайлан")).put(new JSONObject().put("value",plangeo).put("name","Нийт ирүүлсэн төлөвлөгөө")));
                response.put("geomining4", new JSONArray().put(new JSONObject().put("value",reportgeo1).put("name","Баталгаажсан тайлан")).put(new JSONObject().put("value",plangeo1).put("name","Баталгаажсан төлөвлөгөө")));
            }
        }
        /*else if (divisionid == 3){
            if (id == 1){
                for(int reporttype=3;reporttype<=4;reporttype++){
                    JSONArray repstepArray = new JSONArray();
                    for(int repstep=8;repstep<=10;repstep++){
                        List<Long> ars= (List<Long>) dao.getHQLResult("select count(*) from AnnualRegistration t where t.repstatusid = 7 and t.xtype != 0 and t.divisionid = " + divisionid + " and t.repstepid =" + repstep + " and t.reporttype = " + reporttype + " and t.lictype!=1 " + ((divisionid == 1) ? " and t.minid != 5 " : (divisionid == 5) ? " and t.minid = 5" : ""), "list");
                        if (ars.isEmpty()){
                            repstepArray.put(0);
                        }
                        else{
                            repstepArray.put(ars.get(0));
                        }
                    }
                    response.put("stat"+reporttype, repstepArray);

                    JSONArray repstepArrayBack = new JSONArray();
                    for(int repstep=8;repstep<=8;repstep++){
                        List<Long> ars= (List<Long>) dao.getHQLResult("select count(*) from AnnualRegistration t where t.repstatusid = 2 and t.xtype != 0 and t.divisionid = " + divisionid + " and t.rejectstep = "+ repstep +" and t.reporttype = " + reporttype + " and t.lictype!=1 " + ((divisionid == 1) ? " and t.minid != 5 " : (divisionid == 5) ? " and t.minid = 5" : ""), "list");
                        if (ars.isEmpty()){
                            repstepArrayBack.put(0);
                        }
                        else{
                            repstepArrayBack.put((-1)*ars.get(0));
                        }
                    }
                    response.put("stat2"+reporttype, repstepArrayBack);

                    long[] repstatuses = {1,2,7};
                    JSONArray repstatusarray = new JSONArray();
                    for(long repstatus : repstatuses){
                        List<Long> ars= (List<Long>) dao.getHQLResult("select count(*) from AnnualRegistration t where t.repstatusid = " + repstatus + " and t.xtype != 0 and t.divisionid = " + divisionid + " and t.reporttype = " + reporttype + " and t.lictype!=1 " + ((divisionid == 1) ? " and t.minid != 5 " : (divisionid == 5) ? " and t.minid = 5" : ""), "list");
                        if (ars.isEmpty()){
                            repstatusarray.put(0);
                        }
                        else{
                            repstatusarray.put(ars.get(0));
                        }
                    }

                    response.put("statrepstatus"+reporttype, repstatusarray);
                }
            }
        }*/
        return response.toString();
    }

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;

	}
	@ExceptionHandler({org.springframework.http.converter.HttpMessageNotReadableException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String resolveException() {
	    return "error";
	}

}