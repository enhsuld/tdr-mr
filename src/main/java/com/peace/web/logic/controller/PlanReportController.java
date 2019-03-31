package com.peace.web.logic.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.peace.users.model.mram.AnnualRegistration;
import com.peace.users.model.mram.DataCoalForm1_1;
import com.peace.users.model.mram.DataCoalForm2;
import com.peace.users.model.mram.DataCoalForm3;
import com.peace.users.model.mram.DataCoalForm4;
import com.peace.users.model.mram.DataForm11;
import com.peace.users.model.mram.DataForm14;
import com.peace.users.model.mram.DataForm15;
import com.peace.users.model.mram.DataGeoReport1;
import com.peace.users.model.mram.DataGeoReport05;
import com.peace.users.model.mram.DataGeoReport07;
import com.peace.users.model.mram.DataGeoPlan1;
import com.peace.users.model.mram.DataGeoReport10;
import com.peace.users.model.mram.DataGeoReport2;
import com.peace.users.model.mram.DataGeoReport3;
import com.peace.users.model.mram.DataGeoReport4;
import com.peace.users.model.mram.DataGeoReport6;
import com.peace.users.model.mram.DataGeoReport8;
import com.peace.users.model.mram.DataGeoReport9;
import com.peace.users.model.mram.DataMinPlan1;
import com.peace.users.model.mram.DataMinPlan10;
import com.peace.users.model.mram.DataMinPlan11;
import com.peace.users.model.mram.DataMinPlan12;
import com.peace.users.model.mram.DataMinPlan13;
import com.peace.users.model.mram.DataMinPlan14;
import com.peace.users.model.mram.DataMinPlan15;
import com.peace.users.model.mram.DataMinPlan16;
import com.peace.users.model.mram.DataMinPlan17;
import com.peace.users.model.mram.DataMinPlan2_1;
import com.peace.users.model.mram.DataMinPlan2_2;
import com.peace.users.model.mram.DataMinPlan3;
import com.peace.users.model.mram.DataMinPlan4_1;
import com.peace.users.model.mram.DataMinPlan4_2;
import com.peace.users.model.mram.DataMinPlan5;
import com.peace.users.model.mram.DataMinPlan6_1;
import com.peace.users.model.mram.DataMinPlan6_2;
import com.peace.users.model.mram.DataMinPlan7;
import com.peace.users.model.mram.DataMinPlan8;
import com.peace.users.model.mram.DataMinPlan9;
import com.peace.users.model.mram.DataNotesInfo;
import com.peace.users.model.mram.DataTezuMail;
import com.peace.users.model.mram.HelpData;
import com.peace.users.model.mram.LnkComment;
import com.peace.users.model.mram.LnkCommentMain;
import com.peace.users.model.mram.LnkCommentWeekly;
import com.peace.users.model.mram.LnkNewsLog;
import com.peace.users.model.mram.LnkOffRole;
import com.peace.users.model.mram.LnkPlanAttachedFiles;
import com.peace.users.model.mram.LnkPlanNotes;
import com.peace.users.model.mram.LnkPlanTab;
import com.peace.users.model.mram.LnkPlanTransition;
import com.peace.users.model.mram.LnkReportRegBunl;
import com.peace.users.model.mram.LnkReqAnn;
import com.peace.users.model.mram.LnkReqPv;
import com.peace.users.model.mram.LnkTezuData;
import com.peace.users.model.mram.LutConcentration;
import com.peace.users.model.mram.LutDecisions;
import com.peace.users.model.mram.LutDeposit;
import com.peace.users.model.mram.LutFormNotes;
import com.peace.users.model.mram.LutInternalization;
import com.peace.users.model.mram.LutMinGroup;
import com.peace.users.model.mram.LutMinerals;
import com.peace.users.model.mram.LutRole;
import com.peace.users.model.mram.LutUsers;
import com.peace.users.model.mram.LutWeeks;
import com.peace.users.model.mram.LutYear;
import com.peace.users.model.mram.RegReportReq;
import com.peace.users.model.mram.SubLegalpersons;
import com.peace.users.model.mram.SubLicenses;
import com.peace.users.model.mram.WeeklyMainData;
import com.peace.users.model.mram.WeeklyRegistration;
import com.peace.web.controller.JXML;
import com.peace.web.service.SmtpMailSender;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.Seconds;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.XfaForm;
import com.peace.MramApplication;
import com.peace.users.dao.UserDao;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Controller
@RequestMapping("/logic")
public class PlanReportController {
	@Autowired
	UserDao dao;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private SmtpMailSender smtpMailSender;  
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@RequestMapping(value="/reset/users/{division}",method=RequestMethod.GET,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String userConfig(@PathVariable Long division) throws JSONException, MessagingException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			List<SubLegalpersons> sub=(List<SubLegalpersons>) dao.getHQLResult("from SubLegalpersons t where t.confirmed=0", "list");
			for(SubLegalpersons item:sub){
				    SubLegalpersons lp = item;
					LutRole lr=  (LutRole) dao.getHQLResult("from LutRole t where t.roleNameEng='ROLE_COMPANY'", "current");							 
					List<LutUsers> lu= (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.lpreg='"+lp.getLpReg()+"'", "list");	
					lp.setConfirmed(true);
					LutUsers luser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+lp.getLpReg()+"'", "current");
					if (luser != null){
						luser.setIsactive(false);
						dao.PeaceCrud(luser, "LutUsers", "save", luser.getId(), 0, 0, null);
					}
					lp.setUpdateddate(new Date());
					
					dao.PeaceCrud(lp, "SubLegalpersons", "save", lp.getId(), 0, 0, null);
					
					if(lu.size()>0){
						LutUsers lus=lu.get(0);
						String uuid = UUID.randomUUID().toString();
						lus.setIsactive(true);
				    	lus.setEmail(lp.getEmail());
				    	if(lp.getEmail() != null && lp.getEmail().length()>0){
						   smtpMailSender.send(lp.getEmail(), "username"+" : "+lus.getUsername(),"password"+" : "+ lus.getUsername());
						   lus.setUserpass(passwordEncoder.encode(lus.getUsername()));
					    }
						dao.PeaceCrud(lus, "lu", "update", lus.getId(), 0, 0, null);
					}
					else{
						LutUsers lus= new LutUsers();
						lus.setLpreg(lp.getLpReg());
						lus.setUsername(lp.getLpReg());
						lus.setIspublic(0);
					    lus.setUserpass(passwordEncoder.encode(lp.getLpReg()));
					    lus.setIsactive(true);
					    dao.PeaceCrud(lus, "lu", "save", (long) 0, 0, 0, null);
					    
					    LnkOffRole rl=new LnkOffRole();				
					    rl.setRoleid(lr.getId());
					    rl.setUserid(lus.getId());
					    dao.PeaceCrud(rl, "LnkOffRole", "save", (long) 0, 0, 0, null); 
					}
			}
			return "true";
		}
		return null;
	}
	
	@RequestMapping(value="/lic/{year}/{division}/{lictype}",method=RequestMethod.GET,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String licConfig(@PathVariable Long division, @PathVariable Integer lictype, @PathVariable Integer year) throws JSONException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			List<SubLicenses> sub=(List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.divisionId="+division+" and t.licTypeId="+lictype+"", "list");
			for(SubLicenses item:sub){
				SubLicenses lp=(SubLicenses) item;
				lp.setConfigureddate(new Date());
				lp.setPlan(true);
				lp.setReport(true);
				dao.PeaceCrud(lp, "SubLicenses", "update", item.getId(), 0, 0, null);
				
				com.peace.users.model.mram.RegReportReq qqq= new com.peace.users.model.mram.RegReportReq();
				qqq.setLictype(lp.getLicTypeId());
				qqq.setBundledLicenseNum(lp.getLicenseNum());
				qqq.setAreaName(lp.getAreaNameMon());
				qqq.setLpReg(lp.getLpReg());
				qqq.setIsactive(true);
				Date d1 = new Date();
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
				String formattedDate = df.format(d1);
				qqq.setDivisionId((long) division);
				qqq.setLatestChangeDateTime(formattedDate);
				qqq.setWk(0);
				qqq.setGroupid(0);
				String str = lp.getLicenseXB().substring(0, Math.min(lp.getLicenseXB().length(), 2));
				
				if(str.equalsIgnoreCase("mv")){
					qqq.setMv(0);
				}
				else if(str.equalsIgnoreCase("xv")){
					qqq.setMv(2);
				}
				else{
					qqq.setMv(1);
				}
				qqq.setCyear(year);
				qqq.setLicenseXB(lp.getLicenseXB());
				dao.PeaceCrud(qqq, "RegReportReq", "save", (long) 0, 0, 0, null); 
			}
			return "true";
		}
		return null;
	}

	@RequestMapping(value="/tezu",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String tezu(@RequestBody String jsonString) throws JSONException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			System.out.println(jsonString);
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date1 = new Date();
			String special = dateFormat.format(date1);
			JSONObject obj= new JSONObject(jsonString);

			//	 SubLicenses sub=(SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum="+obj.getInt("licNum")+"", "current");
			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
			LutUsers recipent=(LutUsers) dao.getHQLResult("from LutUsers t where t.id='"+obj.getLong("recipient")+"'", "current");



			DataTezuMail mn = new DataTezuMail();
			mn.setSenddate(special);
			mn.setContent(obj.getString("message"));
			mn.setEmail(loguser.getSubLegalpersons().getKEYMANEMAIL());
			mn.setSender(loguser.getSubLegalpersons().getKEYMAN());
			mn.setSenderAvatar(loguser.getAvatar());
			mn.setUserid(loguser.getId());
			mn.setVerified(false);
			mn.setSenderColor("cyan");
			if(obj.getInt("tezu")==1){
				mn.setLicnum(obj.getLong("licNum"));
				mn.setTezu(true);
			}
			else{
				mn.setTezu(false);
			}

			mn.setRecipientName(recipent.getGivnamemon());
			mn.setTitle(obj.getString("title"));
			mn.setRecipient(obj.getLong("recipient"));
			dao.PeaceCrud(mn, "DataTezuMail", "save", (long) 0, 0, 0, null);

			String str=obj.getString("attachments");
			Gson gs=new Gson();
			JSONArray arr= new JSONArray(str);
			if(arr.length()>0){
				for(int i=0; i<arr.length();i++){
					JSONObject aro= new JSONObject(arr.get(i).toString());
					LnkTezuData ld=new LnkTezuData();
					ld.setTezuid(mn.getId());
					ld.setFilename(aro.getString("fileName"));
					ld.setFilesize(aro.getString("fileSize"));
					ld.setFiletype(aro.getString("fileType"));
					ld.setFileUrl(aro.getString("fileUrl"));
					dao.PeaceCrud(ld, "LnkTezuData", "save", (long) 0, 0, 0, null);
				} 
			}		 		 
			return gs.toJson(mn).toString();
		}
		return null;
	}

	@RequestMapping(value="/submitZeroReport",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String zeroReport(@RequestBody String jsonString) throws JSONException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			System.out.println(jsonString);
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date1 = new Date();
			String special = dateFormat.format(date1);
			JSONObject obj= new JSONObject(jsonString);

			AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+obj.getInt("id")+"", "current");
			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

			an.setRepstatusid((long) 5);
			an.setRepstepid((long) 0);
			an.setOfficerid(loguser.getId());
			an.setApproveddate(special);
			dao.PeaceCrud(an, "AnnualRegistration", "save", (long) obj.getLong("id"), 0, 0, null);

			LnkCommentMain mn = new LnkCommentMain();
			mn.setMcomment(obj.getString("content"));
			mn.setPlanid(obj.getLong("id"));
			mn.setUserid(loguser.getId());
			mn.setUsername(loguser.getGivnamemon());
			mn.setDesid((long) 1);
			dao.PeaceCrud(mn, "LnkCommentMain", "save", (long) 0, 0, 0, null);

			return "true";
		}
		return null;
	}


	@RequestMapping(value="/submitPVConfig/{id}",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String submitPVConfig(@PathVariable Long id, @RequestBody String jsonString) throws JSONException{
		System.out.println(jsonString);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			JSONObject obj= new JSONObject(jsonString);

			List<LnkReqPv> rs=(List<LnkReqPv>) dao.getHQLResult("from LnkReqPv t where t.reqid="+id+"", "list");

			if(rs.size()>0){
				LnkReqPv an = rs.get(0);

				an.setReqid(id);  
				an.setFactoryname(obj.getString("factoryname"));

				if(obj.has("aimagid")){
					an.setAimagid(obj.getLong("aimagid"));
				}
				if(obj.has("sumid")){
					an.setSumid(obj.getLong("sumid"));
				}


				an.setInvcountry(obj.getString("invcountry"));
				an.setInvperccent(obj.getLong("invperccent"));
				an.setTezuappdate(obj.getString("tezuappdate"));
				an.setTezulp(obj.getString("tezulp"));
				an.setTezupower(obj.getLong("tezupower"));
				an.setWorkyear(obj.getLong("workyear"));
				an.setStartdate(obj.getString("startdate"));
				an.setRawmine(obj.getString("rawmine"));

				dao.PeaceCrud(an, "LnkReqPv", "update", (long) obj.getLong("id"), 0, 0, null);
			}
			else{
				LnkReqPv an= new LnkReqPv();

				an.setReqid(id);  
				an.setFactoryname(obj.getString("factoryname"));

				if(obj.has("aimagid")){
					an.setAimagid(obj.getLong("aimagid"));
				}
				if(obj.has("sumid")){
					an.setSumid(obj.getLong("sumid"));
				}


				an.setInvcountry(obj.getString("invcountry"));
				an.setInvperccent(obj.getLong("invperccent"));
				an.setTezuappdate(obj.getString("tezuappdate"));
				an.setTezulp(obj.getString("tezulp"));
				an.setTezupower(obj.getLong("tezupower"));
				an.setWorkyear(obj.getLong("workyear"));
				an.setStartdate(obj.getString("startdate"));
				an.setRawmine(obj.getString("rawmine"));

				dao.PeaceCrud(an, "LnkReqPv", "save", (long) 0, 0, 0, null);
			}				
		}		 
		return "true";
	}

	@RequestMapping(value="/submitMVConfig/{id}",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String submitMVConfig(@PathVariable Integer id,@RequestBody String jsonString) throws JSONException{
		System.out.println(jsonString);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			JSONObject obj= new JSONObject(jsonString);
			Date d1 = new Date();
			SimpleDateFormat dfc= new SimpleDateFormat("YYYY");
			String special = dfc.format(d1);
			//qqq.setCyear(Integer.parseInt(special));
			
			RegReportReq annual=(RegReportReq) dao.getHQLResult("from RegReportReq t where t.id="+id+"", "current");
			//LutYear ye=(LutYear) dao.getHQLResult("from LutYear t where t.type=3 and t.divisionid="+annual.getDivisionId()+" and t.isactive=1", "current");
			//List<LnkReqAnn> rs=(List<LnkReqAnn>) dao.getHQLResult("from LnkReqAnn t where t.reqid="+id, "list");
            List<LnkReqAnn> rs = annual.getLnkReqAnns();

            if (rs.size() > 0){
                if(rs.size()>1) {
                    for(int i=0;i<rs.size()-1;i++){
                        dao.PeaceCrud(rs.get(i), "LnkReqAnn", "delete", (long) rs.get(i).getId(), 0, 0, null);
                    }
                }

				LnkReqAnn an = rs.get(rs.size()-1);

				//an.setCyear(ye.getValue());
				an.setReqid(obj.getLong("reqid"));
				an.setHorde(obj.getString("horde"));

				if(obj.has("aimagid")){
					an.setAimagid(obj.getString("aimagid"));
				}
				if(obj.has("sumid")){
					an.setSumid(obj.getString("sumid"));
				}
				if(obj.has("mineralid")){
					LutMinerals minid=(LutMinerals) dao.getHQLResult("from LutMinerals t where t.id='"+obj.getLong("mineralid")+"'", "current");
                    annual.setAreaName(an.getHorde());
					annual.setGroupid(minid.getMineralgroupid());
					annual.setIsconfiged(1);
					if(minid.getId()==5){
						annual.setDivisionId((long) 2);
					}
					else{
						annual.setDivisionId((long) 1);
					}
					annual.setMineralid(obj.getString("deposidid"));
					dao.PeaceCrud(annual, "RegReportReq", "update", (long) id, 0, 0, null);
					List<AnnualRegistration> ann=(List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.reqid="+annual.getId(), "list");
					if(ann.size()>0){
						for(AnnualRegistration item:ann){
							item.setDivisionid(annual.getDivisionId());
							item.setMinid(minid.getId());
							item.setDepositid(obj.getLong("deposidid"));
							dao.PeaceCrud(item, "AnnualRegistration", "update", (long) item.getId(), 0, 0, null);
						}
					}
					an.setMineralid(obj.getLong("mineralid"));
				}



				if(obj.has("deposidid")){
					System.out.println("ene aksafasoijfoiasjf: "+ obj.getLong("deposidid"));
					an.setDeposidid(obj.getLong("deposidid"));
					LutDeposit dep=(LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+obj.getLong("deposidid")+"'", "current");
					an.setIsmatter(dep.getIsmatter());

				}
				if(obj.has("minetypeid")){
					an.setMinetypeid(obj.getLong("minetypeid"));
				}
				if(obj.has("bombid")){
					System.out.println("ene soligdohnuu: "+ obj.getLong("bombid"));
					an.setBombid(obj.getLong("bombid"));
					an.setIsexplosion(obj.getInt("bombid"));
				}
				an.setCountries(obj.getString("countries"));
				an.setAppdate(obj.getString("appdate"));
				an.setYearcapacity(obj.getLong("yearcapacity"));
				an.setWorkyear(obj.getLong("workyear"));

				an.setKomissid(obj.getLong("komissid"));


				an.setStartdate(obj.getString("startdate"));

				if(obj.has("bombtype")){
					an.setBombtype(obj.getLong("bombtype"));
				}
				if(!obj.isNull("komissakt")){
					an.setKomissakt(obj.getString("komissakt"));
				}
				if(!obj.isNull("komissdate")){
					an.setKomissdate(obj.getString("komissdate"));
				}

				if(obj.has("concetrate")){
					an.setConcetrate(obj.getString("concetrate"));
					LutConcentration dep=(LutConcentration) dao.getHQLResult("from LutConcentration t where t.id='"+obj.getString("concetrate")+"'", "current");
					an.setIsconcetrate(dep.getIsconcentrate());
				}
				an.setStatebudgetid(obj.getLong("statebudgetid"));

				dao.PeaceCrud(an, "LnkReqAnn", "update", (long) obj.getLong("reqid"), 0, 0, null);

			}
			else{
				LnkReqAnn an= new LnkReqAnn();
				//an.setCyear(ye.getValue());
				an.setReqid((long) id);  
				an.setHorde(obj.getString("horde"));
				an.setAimagid(obj.getString("aimagid"));
				an.setSumid(obj.getString("sumid"));
				//	 an.setMineralid(obj.getLong("mineralid"));
				if(obj.has("mineralid") && !obj.isNull("mineralid")){
					LutMinerals minid=(LutMinerals) dao.getHQLResult("from LutMinerals t where t.id='"+obj.getLong("mineralid")+"'", "current");
					annual.setGroupid(minid.getMineralgroupid());	
					annual.setIsconfiged(1);
					if(minid.getId()==5){
						annual.setDivisionId((long) 2);
					}
					else{
						annual.setDivisionId((long) 1);
					}
					annual.setMineralid(obj.getString("deposidid"));
					dao.PeaceCrud(annual, "RegReportReq", "update", (long) id, 0, 0, null);

					an.setMineralid(obj.getLong("mineralid"));
				}
				if(obj.has("deposidid") && !obj.isNull("deposidid")){
					an.setDeposidid(obj.getLong("deposidid"));
					LutDeposit dep=(LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+obj.getLong("deposidid")+"'", "current");
					an.setIsmatter(dep.getIsmatter());
				}
				if(obj.has("countries") && !obj.isNull("countries")){
					an.setCountries(obj.getString("countries"));		 
				}
				if(obj.has("appdate") && !obj.isNull("appdate")){
					an.setAppdate(obj.getString("appdate"));
				}
				if(obj.has("yearcapacity") && !obj.isNull("yearcapacity")){
					an.setYearcapacity(obj.getLong("yearcapacity"));
				}
				if(obj.has("workyear") && !obj.isNull("workyear")){
					an.setWorkyear(obj.getLong("workyear"));
				}
				if(obj.has("minetypeid") && !obj.isNull("minetypeid")){
					an.setMinetypeid(obj.getLong("minetypeid"));
				}
				if(obj.has("komissid") && !obj.isNull("komissid")){
					an.setKomissid(obj.getLong("komissid"));
				}
				if(obj.has("komissakt") && !obj.isNull("komissakt")){
					an.setKomissakt(obj.getString("komissakt"));
				}
				if(obj.has("komissdate") && !obj.isNull("komissdate")){
					an.setKomissdate(obj.getString("komissdate"));
				}
				if(obj.has("startdate") && !obj.isNull("startdate")){
					an.setStartdate(obj.getString("startdate"));
				}
				if(obj.has("bombtype") && !obj.isNull("bombtype")){
					an.setBombtype(obj.getLong("bombtype"));

				}
				if(obj.has("concetrate") && !obj.isNull("concetrate")){
					an.setConcetrate(obj.getString("concetrate"));
					LutConcentration dep=(LutConcentration) dao.getHQLResult("from LutConcentration t where t.id='"+obj.getString("concetrate")+"'", "current");
					an.setIsconcetrate(dep.getIsconcentrate());
				}
				if(obj.has("statebudgetid") && !obj.isNull("statebudgetid")){
					an.setStatebudgetid(obj.getLong("statebudgetid"));
				}

				if(obj.has("bombid") && !obj.isNull("bombid")){
					System.out.println("ene soligdohnuu: "+ obj.getLong("bombid"));
					an.setBombid(obj.getLong("bombid"));
					an.setIsexplosion(obj.getInt("bombid"));
				}
				//	 an.setBombid(obj.getLong("bombid"));




				dao.PeaceCrud(an, "LnkReqAnn", "save", (long) 0, 0, 0, null);

				/*		if(obj.getJSONArray("subContracts").length()>0){
					List<SubContract> rc= an.getSubContracts();
					SubContract sb=null;
					if(rc.size()>0){
						sb=rc.get(0);
					}
					else{
						sb=new SubContract();
					}
					JSONArray arr=obj.getJSONArray("subContracts");
					JSONObject ss=(JSONObject) arr.get(0);
					//sb.setContractstatus(ss.getString("contractstatus"));
					//sb.setContractamount(ss.getLong("contractamount"));
					//sb.setPaid(ss.getLong("paid"));
					//sb.setBalance(ss.getLong("balance"));
					//sb.setCurrentpayment(ss.getLong("currentpayment"));
					//sb.setConclusiondate(ss.getString("conclusiondate"));
					sb.setReqannid(an.getId());
					//sb.setContractdate(ss.getString("contractdate"));
					if(!ss.isNull("contractstatus")){
						sb.setContractstatus(ss.getString("contractstatus"));
					}
					if(!ss.isNull("contractamount")){
						sb.setContractamount(ss.getLong("contractamount"));	
									}
					if(!ss.isNull("paid")){
						sb.setPaid(ss.getLong("paid"));
					}
					if(!ss.isNull("balance")){
						sb.setBalance(ss.getLong("balance"));
					}
					if(!ss.isNull("currentpayment")){
						sb.setCurrentpayment(ss.getLong("currentpayment"));
					}
					if(!ss.isNull("conclusiondate")){
						sb.setConclusiondate(ss.getString("conclusiondate"));
					}
					if(!ss.isNull("contractdate")){
						sb.setContractdate(ss.getString("contractdate"));
					}
					if(rc.size()>0){
						dao.PeaceCrud(sb, "SubContract", "update", (long) sb.getId(), 0, 0, null);
					}
					else{
						dao.PeaceCrud(sb, "SubContract", "save", (long) 0, 0, 0, null);
					}
				}

				if(obj.getJSONArray("subNoContracts").length()>0){
					List<SubNoContract> rc= an.getSubNoContracts();
					SubNoContract sb=null;
					if(rc.size()>0){
						sb=rc.get(0);
					}
					else{
						sb=new SubNoContract();
					}
					JSONArray arr=obj.getJSONArray("subNoContracts");
					JSONObject ss=(JSONObject) arr.get(0);
					sb.setReqannid(an.getId());
					sb.setMramdate(ss.getString("mramdate"));
					sb.setMramletterid(ss.getString("mramletterid"));
					sb.setMramregid(ss.getString("mramregid"));
					sb.setReference(ss.getString("reference"));
					sb.setRequestdate(ss.getString("requestdate"));
					sb.setRequeststatus(ss.getString("requeststatus"));
					if(rc.size()>0){
						dao.PeaceCrud(sb, "SubNoContract", "update", (long) sb.getId(), 0, 0, null);
					}
					else{
						dao.PeaceCrud(sb, "SubNoContract", "save", (long) 0, 0, 0, null);
					}

				}*/

			}				
		}		 
		return "true";
	}

	@RequestMapping(value="/help/save/{id}",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String help(@PathVariable Integer id,@RequestBody String jsonString) throws JSONException{
		System.out.println(jsonString);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			JSONObject obj= new JSONObject(jsonString);

			List<HelpData> rs=(List<HelpData>) dao.getHQLResult("from HelpData t where t.id="+id+"", "list");

			if(rs.size()>0){
				HelpData an = rs.get(0);
				an.setHelptext(obj.getString("content"));
				JSONObject cat= obj.getJSONObject("category");
				an.setHelptext(obj.getString("content"));
				an.setCatid(cat.getLong("value"));
				an.setHelptitle(obj.getString("title"));

				dao.PeaceCrud(an, "HelpData", "update", (long) obj.getLong("id"), 0, 0, null);
			}
			else{
				HelpData an= new HelpData();

				an.setHelptext(obj.getString("content"));
				JSONObject cat= obj.getJSONObject("category");
				an.setHelptext(obj.getString("content"));
				an.setCatid(cat.getLong("value"));
				an.setHelptitle(obj.getString("title"));
				dao.PeaceCrud(an, "HelpData", "save", (long) 0, 0, 0, null);
			}				
		}		 
		return "true";
	}



	@RequestMapping(value="/form/save",method=RequestMethod.POST)
	public @ResponseBody String saveForm( @RequestParam("files") Object files,@RequestParam("obj") String jsonstring, MultipartRequest mpr, HttpServletRequest req) throws IOException {

		MultipartFile mfile =  null;

		Gson gson= new Gson();
		JSONObject jo = new JSONObject();
		System.out.println("end"+jsonstring);

		JSONObject obj= new JSONObject(jsonstring);

		if(obj.has("id")&&obj.getLong("id")==0){

			LutFormNotes fn= new LutFormNotes();

			if(obj.has("note")){
				fn.setNote(obj.getString("note"));
			}
			if(obj.has("isapproval")){
				fn.setIsapproval(obj.getInt("isapproval"));
			}
			if(obj.has("notecontent")){
				fn.setNotecontent(obj.getString("notecontent"));
			}
			if(obj.has("divisionid")){
				fn.setDivisionid(obj.getInt("divisionid"));
			}
			if(obj.has("inptype")){
				fn.setInptype(obj.getInt("inptype"));
			}
			if(obj.has("lictype")){
				fn.setLictype(obj.getLong("lictype"));
			}
			if(obj.has("reporttype")){
				fn.setReporttype(obj.getLong("reporttype"));
			}
			if(obj.has("transid")){
				fn.setTransid(obj.getInt("transid"));
			}
			if(obj.has("onoffid")){
				fn.setOnoffid(obj.getInt("onoffid"));
			}
			if(obj.has("isform")){
				fn.setIsform(obj.getInt("isform"));
			}

			if(obj.has("iscommon")){
				fn.setIscommon(obj.getInt("iscommon"));
			}
			if(obj.has("isconcentrate")){
				fn.setIsconcentrate(obj.getInt("isconcentrate"));
			}
			if(obj.has("isexplosion")){
				fn.setIsexplosion(obj.getInt("isexplosion"));
			}
			if(obj.has("ismatter")){
				fn.setIsmatter(obj.getInt("ismatter"));
			}
			if(obj.has("isbudget")){
				fn.setIsbudget(obj.getInt("isbudget"));
			}
			if(obj.has("iskomiss")){
				fn.setIskomiss(obj.getInt("iskomiss"));
			}

			if(obj.has("lplan")){
				fn.setLplan(obj.getInt("lplan"));
			}
			if(obj.has("lreport")){
				fn.setLreport(obj.getInt("lreport"));
			}
			
			if(obj.has("isxreport")){
				fn.setIsxreport(obj.getInt("isxreport"));
			}
			
			if(obj.has("minetypeid")){
				fn.setMintypeid(obj.getInt("minetypeid"));
			}


			if(obj.getInt("isform")==1){
				mfile = (MultipartFile)files;
				if (mfile != null) {
					String appPath = req.getSession().getServletContext().getRealPath("");
					String SAVE_DIR = "uploads";
					Date d1 = new Date();
					SimpleDateFormat df = new SimpleDateFormat("MM-dd-YYYY");
					String special = df.format(d1);

					String savePath = appPath + "/" + SAVE_DIR+ "/" +special;
					System.out.println("de dir"+special);
					File logodestination = new File(savePath);
					if(!logodestination.exists()){
						logodestination.mkdir();
						System.out.println("end"+logodestination);
					}
					//LutFormNotes fn=(LutFormNotes) dao.getHQLResult("from LutFormNotes y where y.id="+obj.getInt("id")+"", "current");

					String delPath = appPath + fn.getUrl();

					System.out.println("@@@"+delPath);
					File destination = new File(delPath); 
					if(destination.delete()){
						System.out.println(destination.getName() + " is deleted!");
					}else{
						System.out.println("Delete operation is failed.");
					}

					//List<LnkPlanAttachedFiles> rs=(List<LnkPlanAttachedFiles>) dao.getHQLResult("from LnkPlanAttachedFiles y", "list");
					String path = appPath + "/" + SAVE_DIR+ "/" +special+ "/" +"("+fn.getId()+")"+mfile.getOriginalFilename();
					File logoorgpath = new File(path);

					if(!logoorgpath.exists()){
						mfile.transferTo(logoorgpath);
					}
					else{
						jo.put("return", "false");
					}

					String ext = Files.getFileExtension(path);

					String root ="";
					String path1 = root + "/" + SAVE_DIR+ "/" +special+ "/" +"("+fn.getId()+")"+ mfile.getOriginalFilename();
					double kilobytes = (logoorgpath.length() / 1024);
					fn.setFsize((int) kilobytes);
					fn.setUrl(path1);					
				}  
			}else{
				if(obj.has("isfile")){
					fn.setIsfile(obj.getInt("isfile"));
				}
				if(obj.has("fsize")){
					fn.setFsize(obj.getInt("fsize"));
				}
			}
			jo.put("return", "true");
			if(obj.has("isrequired")){
				if(!obj.isNull("isrequired")){
					fn.setIsrequired(obj.getInt("isrequired"));
				}				
			}
			dao.PeaceCrud(fn, "LutFormNotes", "save", (long) 0, 0, 0, null);
		}
		else{

			LutFormNotes fn=(LutFormNotes) dao.getHQLResult("from LutFormNotes y where y.id="+obj.getLong("id")+"", "current");

			if(obj.has("note")){
				fn.setNote(obj.getString("note"));
			}
			if(obj.has("isapproval")){
				fn.setIsapproval(obj.getInt("isapproval"));
			}
			if(obj.has("notecontent")){
				if(!obj.isNull("notecontent")){
					fn.setNotecontent(obj.getString("notecontent"));
				}
			}
			if(obj.has("divisionid")){
				fn.setDivisionid(obj.getInt("divisionid"));
			}
			if(obj.has("inptype")){
				fn.setInptype(obj.getInt("inptype"));
			}
			if(obj.has("lictype")){
				fn.setLictype(obj.getLong("lictype"));
			}
			if(obj.has("reporttype")){
				fn.setReporttype(obj.getLong("reporttype"));
			}
			if(obj.has("transid")){
				if(!obj.isNull("transid")){
					fn.setTransid(obj.getInt("transid"));
				}
			}
			if(obj.has("onoffid")){
				if(!obj.isNull("onoffid")){
					fn.setOnoffid(obj.getInt("onoffid"));
				}
			}
			if(!obj.isNull("isform")){
				fn.setIsform(obj.getInt("isform"));
			}
			if(!obj.isNull("iscommon")){
				fn.setIscommon(obj.getInt("iscommon"));
			}
			if(!obj.isNull("isconcentrate")){
				fn.setIsconcentrate(obj.getInt("isconcentrate"));
			}
			if(!obj.isNull("isexplosion")){
				fn.setIsexplosion(obj.getInt("isexplosion"));
			}
			if(!obj.isNull("ismatter")){
				fn.setIsmatter(obj.getInt("ismatter"));
			}
			if(!obj.isNull("isbudget")){
				fn.setIsbudget(obj.getInt("isbudget"));
			}
			if(!obj.isNull("iskomiss")){
				fn.setIskomiss(obj.getInt("iskomiss"));
			}

			if(obj.has("lplan") && !obj.isNull("lplan")){
				fn.setLplan(obj.getInt("lplan"));
			}
			if(obj.has("lreport") && !obj.isNull("lreport")){
				fn.setLreport(obj.getInt("lreport"));
			}
			
			if(obj.has("isxreport")){
				fn.setIsxreport(obj.getInt("isxreport"));
			}
			
			if(obj.has("minetypeid")){
				fn.setMintypeid(obj.getInt("minetypeid"));
			}

			if(obj.getInt("isform")==1){
				mfile = (MultipartFile)files;
				if (mfile != null) {
					String appPath = req.getSession().getServletContext().getRealPath("");
					String SAVE_DIR = "uploads";
					Date d1 = new Date();
					SimpleDateFormat df = new SimpleDateFormat("MM-dd-YYYY");
					String special = df.format(d1);

					String savePath = appPath + "/" + SAVE_DIR+ "/" +special;
					File logodestination = new File(savePath);
					if(!logodestination.exists()){
						logodestination.mkdir();
						System.out.println("end"+logodestination);
					}

					String delPath = appPath + fn.getUrl();

					File destination = new File(delPath); 
					if(destination.delete()){
						System.out.println(destination.getName() + " is deleted!");
					}else{
						System.out.println("Delete operation is failed.");
					}
					String path = appPath + "/" + SAVE_DIR+ "/" +special+ "/" +"("+fn.getId()+")"+mfile.getOriginalFilename();
					File logoorgpath = new File(path);

					if(!logoorgpath.exists()){
						mfile.transferTo(logoorgpath);
					}
					else{
						jo.put("return", "false");
					}

					String ext = Files.getFileExtension(path);

					String root ="";
					String path1 = root + "/" + SAVE_DIR+ "/" +special+ "/" +"("+fn.getId()+")"+ mfile.getOriginalFilename();
					if(!obj.isNull("fsize")){
						fn.setFsize(obj.getInt("fsize"));
					}
					
					fn.setUrl(path1);
				}  
			}else{
				if(obj.has("isfile")){
					fn.setIsfile(obj.getInt("isfile"));
				}
				if(obj.has("fsize")){
					if(!obj.isNull("fsize")){
						fn.setFsize(obj.getInt("fsize"));
					}				
				}
			}
			if(obj.has("isrequired")){
				if(!obj.isNull("isrequired")){
					fn.setIsrequired(obj.getInt("isrequired"));
				}				
			}
			dao.PeaceCrud(fn, "LutFormNotes", "update", (long) obj.getLong("id"), 0, 0, null);	
			jo.put("return", "true");

		}      
		return jo.toString();

	}


	@RequestMapping(value="/ognoo", method = RequestMethod.GET,produces={"application/json; charset=UTF-8"})
	public String ognoo(HttpServletRequest req) throws ClassNotFoundException, JSONException, ParseException{

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			List<AnnualRegistration> ann= (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.divisionid=3 and t.repstatusid!=0 and t.submissiondate is null", "list");
			
			for(int i=0; i<ann.size(); i++){
				AnnualRegistration item=ann.get(i);
				
				List<LnkPlanAttachedFiles> la= (List<LnkPlanAttachedFiles>) dao.getHQLResult("from LnkPlanAttachedFiles t where t.expid="+item.getId()+"", "list");
				
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                if (la.size() > 0){
                    item.setSubmissiondate(la.get(la.size()-1).getAtdate());
                    dao.PeaceCrud(item, "AnnualRegistration", "update", (long) item.getId(), 0, 0, null);
                }

			}
			
			return null;		

		}	
		return "false";		
	}

	
	
	@RequestMapping(value="/duration", method = RequestMethod.GET,produces={"application/json; charset=UTF-8"})
	public String duration(HttpServletRequest req) throws ClassNotFoundException, JSONException, ParseException{

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			List<AnnualRegistration> ann= (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.repstatusid=1 and t.reporttype=4 and t.divisionid=1", "list");
			
			for(int i=0; i<ann.size(); i++){
				AnnualRegistration item=ann.get(i);
				
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

				Date d1 = null;
				Date d2 = null;
				
			    PeriodFormatter dhm = new PeriodFormatterBuilder()
		    		.appendWeeks()
			        .appendSuffix(" week", " долоо хоног")
			        .appendSeparator(" , ")
			        .appendDays()
			        .appendSuffix(" day", " хоног")
			        .appendSeparator(" , ")
			        .appendHours()
			        .appendSuffix(" hour", " цаг")
			        .appendSeparator(" , ")
			        .appendMinutes()
			        .appendSuffix(" minute", " минут")
			        .appendSeparator(" , ")
			        .appendSeconds()
			        .appendSuffix(" second", " секунд")
			        .toFormatter();
			    
			    String govDateStart = null;
				String govDateStop = null;
				String comDateStart = null;
				String comDateStop = null;
				
				Seconds govSeconds= Seconds.ZERO;
				Seconds comSeconds = Seconds.ZERO;
				
				long csec=0;
				int gsec=0;
				
				List<LnkCommentMain> stcoms = (List<LnkCommentMain>) dao.getHQLResult("from LnkCommentMain t where t.planid="+item.getId()+"  and t.createdDate is not null order by t.id asc", "list");
				for(int y=0; y<stcoms.size(); y++){
					if(stcoms.get(y).getIsgov()!=null){
						if(stcoms.get(y).getIsgov()==1){
							comDateStop=stcoms.get(y).getCreatedDate();
							
							if(y==0){
								govDateStart=stcoms.get(y).getCreatedDate();
							}
							else{
								govDateStart=comDateStop;
							}						
							
						}
						else if(stcoms.get(y).getIsgov()==0){
							comDateStart=stcoms.get(y).getCreatedDate();
							govDateStop=stcoms.get(y).getCreatedDate();
						}
						if(stcoms.get(y).getIsgov()==1){
							d2 = format.parse(govDateStart);
							if(y==0){							
								d1 = format.parse(comDateStop);
							}
							else{
								d1 = format.parse(comDateStart);
							}
							
							DateTime gdt1 = new DateTime(d1);
							DateTime gdt2 = new DateTime(d2);
													
							Seconds seconds = Seconds.secondsBetween(gdt1, gdt2);
							comSeconds = comSeconds.plus(seconds);						
							Period pc = new Period(seconds);		
							csec=csec+pc.getSeconds();
							System.out.println("com : "+dhm.print(pc.normalizedStandard()));
							
						}
						if(stcoms.get(y).getIsgov()==0){
							d1 = format.parse(comDateStart);
							if(y==0){
								d2 = format.parse(govDateStop);
							}
							else{
								d2 = format.parse(govDateStart);
							}			
							
							DateTime gdt1 = new DateTime(d1);
							DateTime gdt2 = new DateTime(d2);
							
							Seconds seconds = Seconds.secondsBetween(gdt2, gdt1);
							govSeconds = govSeconds.plus(seconds);
							
							Period pc = new Period(seconds);
							gsec=gsec+pc.getSeconds();
							System.out.println("gov : "+dhm.print(pc.normalizedStandard()));
						}
					}					
				}
				
				Seconds s1 = Seconds.seconds((int)csec);
				Period p1 = new Period(s1);
				System.out.println(dhm.print(p1.normalizedStandard()));
				
				Seconds s2 = Seconds.seconds(gsec);
				Period p2 = new Period(s2);
				System.out.println(dhm.print(p2.normalizedStandard()));
				 
				item.setCduration(dhm.print(p1.normalizedStandard()));
				item.setGduration(dhm.print(p2.normalizedStandard()));
				dao.PeaceCrud(item, "AnnualRegistration", "update", (long) item.getId(), 0, 0, null);
			}
			
			return null;		

		}	
		return "false";		
	}
	
	@RequestMapping(value="/pdf/{plan}/{id}", method = RequestMethod.GET,produces={"application/json; charset=UTF-8"})
	public String fillPdf(@PathVariable int plan, @PathVariable int id,HttpServletResponse response, HttpServletRequest req) throws ClassNotFoundException, JSONException{

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			try {
				ServletOutputStream out = response.getOutputStream();

				limitedPdf(out,req,plan,id);

				out.close();

			} catch (Exception io) {
				io.printStackTrace();
			}

			return null;		

		}	
		return "false";		
	}


	private void setDataMin4_1(JSONArray values_array, JSONObject data, Long planid, Long noteid, Long type, List<LnkPlanTransition> transitions){


		if (type >=1 && type < 4){
			for(int j=0;j<(int)(values_array.length()/8);j++){
				DataMinPlan4_1 temp = new DataMinPlan4_1();
				temp.setData1(data.getString("data1"));
				temp.setData2(data.getString("data2"));
				if (!(data.getString("data3").equalsIgnoreCase(null) || data.getString("data3").length() == 0)){
					temp.setData3(data.getDouble("data3"));
				}
				temp.setPlanid(planid);
				temp.setNoteid(noteid);
				temp.setType(type);
				
				if (transitions != null && transitions.size() > 0){
					temp.setIstodotgol(transitions.get(0).getIstodotgol());
				}
				else{
					temp.setIstodotgol(false);
				}

				if (!(values_array.getString(j*8).equalsIgnoreCase(null) || values_array.getString(j*8).length() == 0)){
					temp.setData4(values_array.getDouble(j*8));
				}
				temp.setData5(values_array.getString(j*8 + 1));

				if (!(values_array.getString(j*8 + 2).equalsIgnoreCase(null) || values_array.getString(j*8 + 2).length() == 0)){
					temp.setData6(values_array.getString(j*8 + 2));
				}

				temp.setData7(values_array.getString(j*8 + 3));


				temp.setData8(values_array.getString(j*8 + 4));

				if (!(values_array.getString(j*8 + 5).equalsIgnoreCase(null) || values_array.getString(j*8 + 5).length() == 0)){
					temp.setData9(values_array.getDouble(j*8 + 5));
				}
				if (!(values_array.getString(j*8 + 6).equalsIgnoreCase(null) || values_array.getString(j*8 + 6).length() == 0)){
					temp.setData10(values_array.getString(j*8 + 6));
				}
				if (!(values_array.getString(j*8 + 7).equalsIgnoreCase(null) || values_array.getString(j*8 + 7).length() == 0)){
					temp.setData14(values_array.getString(j*8 + 7));
				}
				System.out.println(temp.toString());
				dao.PeaceCrud(temp, "DataMinPlan4_1", "save", (long) 0, 0, 0, null);
			}
		}
		else if (type == 4){
			for(int j=0;j<(int)(values_array.length()/6);j++){
				DataMinPlan4_1 temp = new DataMinPlan4_1();
				temp.setData1(data.getString("data1"));
				temp.setData2(data.getString("data2"));
				if (!(data.getString("data3").equalsIgnoreCase(null) || data.getString("data3").length() == 0)){
					temp.setData3(data.getDouble("data3"));
				}
				temp.setData10(data.getString("data10"));
				temp.setData11(data.getDouble("data11"));
				temp.setData12(data.getString("data12"));
				temp.setData13(data.getDouble("data13"));
				temp.setData14(data.getString("data14"));
				temp.setPlanid(planid);
				temp.setNoteid(noteid);
				temp.setType(type);
				if (transitions != null && transitions.size() > 0){
					temp.setIstodotgol(transitions.get(0).getIstodotgol());
				}
				else{
					temp.setIstodotgol(false);
				}

				if (!(values_array.getString(j*6).equalsIgnoreCase(null) || values_array.getString(j*6).length() == 0)){
					temp.setData4(values_array.getDouble(j*6));
				}

				temp.setData5(values_array.getString(j*6 + 1));

				if (!(values_array.getString(j*6 + 2).equalsIgnoreCase(null) || values_array.getString(j*6 + 2).length() == 0)){
					temp.setData6(values_array.getString(j*6 + 2));
				}

				temp.setData7(values_array.getString(j*6 + 3));


				temp.setData8(values_array.getString(j*6 + 4));

				if (!(values_array.getString(j*6 + 5).equalsIgnoreCase(null) || values_array.getString(j*6 + 5).length() == 0)){
					temp.setData9(values_array.getDouble(j*6 + 5));
				}
				dao.PeaceCrud(temp, "DataMinPlan4_1", "save", (long) 0, 0, 0, null);
			}
		}
		else if (type == 5 || type==6){
			DataMinPlan4_1 temp = new DataMinPlan4_1();
			if (data.has("data1")){
				temp.setData1(data.getString("data1"));
			}
			if (data.has("data2")){
				temp.setData2(data.getString("data2"));
			}
			if (data.has("data3") && !(data.getString("data3").equalsIgnoreCase(null) || data.getString("data3").length() == 0)){
				temp.setData3(data.getDouble("data3"));
			}
			if (data.has("data4") && !(data.getString("data4").equalsIgnoreCase(null) || data.getString("data4").length() == 0)){
				temp.setData4(data.getDouble("data4"));
			}
			if (data.has("data5")){
				temp.setData5(data.getString("data5"));
			}
			if (data.has("data6")){
				temp.setData6(data.getString("data6"));
			}
			if (data.has("data7")){
				temp.setData7(data.getString("data7"));
			}
			if (data.has("data8")){
				temp.setData8(data.getString("data8"));
			}
			if (data.has("data9") && !(data.getString("data9").equalsIgnoreCase(null) || data.getString("data9").length() == 0)){
				temp.setData9(data.getDouble("data9"));
			}
			temp.setData10(data.getString("data10"));
			if (data.has("data11") && !(data.getString("data11").equalsIgnoreCase(null) || data.getString("data11").length() == 0)){
				temp.setData11(data.getDouble("data11"));
			}
			temp.setData12(data.getString("data12"));
			if (data.has("data13") && !(data.getString("data13").equalsIgnoreCase(null) || data.getString("data13").length() == 0)){
				temp.setData13(data.getDouble("data13"));
			}
			if (data.has("data14")){
				temp.setData14(data.getString("data14"));
			}

			temp.setPlanid(planid);
			if (transitions != null && transitions.size() > 0){
				temp.setIstodotgol(transitions.get(0).getIstodotgol());
			}
			else{
				temp.setIstodotgol(false);
			}
			temp.setNoteid(noteid);
			temp.setType(type);
			dao.PeaceCrud(temp, "DataMinPlan4_1", "save", (long) 0, 0, 0, null);
		}


	}



	public  String importPdf(MultipartFile mfile, HttpServletRequest req, Long planid, Long noteid, String pathUrl) throws DocumentException, IOException ,Exception {

		try { 





			String appPath = req.getServletContext().getRealPath("");

			//LnkPlanAttachedFiles at=(LnkPlanAttachedFiles) dao.getHQLResult("from LnkPlanAttachedFiles t where t.noteid="+noteid+" and t.expid="+planid+"", "current");

			if(mfile!=null){

                FileInputStream fisStream = new FileInputStream(pathUrl);

				PdfReader reader = new PdfReader(fisStream);
				// PdfStamper stamper = new PdfStamper(reader,  out);

				//PdfReader reader = new PdfReader(nt.getUrl());
				int n = reader.getNumberOfPages();
				AcroFields form = reader.getAcroFields();

				XfaForm xfa = form.getXfa();

				String[] staticFields = {"LicNum","Lic_num","economist","accountant","RegID","FormID","holderName","givName","geologist","geologist","Year1","BTech","MinType","MineType","engineer","mineHead","minehead","msurveyor","Engineering1","Engineering2","Officer", "Description"};
				String[] staticFieldsVars = {"licnum","licnum","economist","accountant","regid","formid","holdername","givname","geologyst","geologyst","year","btech","mintype","mintype","engineer","minehead","minehead","msurveyor","engineering1","engineering2","officer","description"};
				DataNotesInfo info = (DataNotesInfo) dao.getHQLResult("from DataNotesInfo t where t.planid="+planid+" and t.noteid =" + noteid + "", "current");
				if (info != null){
					dao.deleteById("DataNotesInfo", info.getId(), null);
				}
				info = new DataNotesInfo();
				info.setPlanid(planid);
				info.setNoteid(noteid);
				if(!xfa.isXfaPresent()){



					for(int sf = 0; sf<staticFields.length;sf++){
						if (form.getField(staticFields[sf]) != null && form.getField(staticFields[sf]).length() > 0){
							info.setField(staticFieldsVars[sf], form.getField(staticFields[sf]));
						}
					}

					if(info.getFormid() == null || !info.getFormid().equalsIgnoreCase(String.valueOf(noteid))){
						return "noeq";
					}

					dao.PeaceCrud(info, "DataNotesInfo", "save", (long) 0, 0, 0, null);
					if (noteid == 35){
						List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
						if (transitions != null && transitions.size() > 0){
							dao.PeaceCrud(null, "DataMinPlan13", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
						}
						else{
							dao.PeaceCrud(null, "DataMinPlan13", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
						}
						//dao.PeaceCrud(null, "DataMinPlan13", "delete", (long) planid, 0, 0, "planid");
						for(int i=1;i<=2;i++){
							DataMinPlan13 data = new DataMinPlan13();
							for(int j=1;j<=8;j++){
								if (form.getField("Data"+i+"_"+j) != null && form.getField("Data"+i+"_"+j).length() > 0){
									data.setField("data"+j, form.getField("Data"+i+"_"+j));
								}
							}
							if (transitions != null && transitions.size() > 0){
								data.setIstodotgol(transitions.get(0).getIstodotgol());
							}
							else{
								data.setIstodotgol(false);
							}
							data.setNoteid(noteid);
							data.setPlanid(planid);
							data.setType(Long.valueOf(i));
							dao.PeaceCrud(data, "DataMinPlan13", "save", (long) 0, 0, 0, null);
						}
					}
					

					else if(noteid==127){
						dao.PeaceCrud(null, "DataForm11", "delete", (long) planid, 0, 0, "planid");

						DataForm11 dp= new DataForm11();                		   
						dp.setData1(form.getField("Data1_1"));	
						dp.setData2(form.getField("Data1_2"));
						dp.setData3(form.getField("Data1_3"));	               		     
						if(form.getField("Data1_4").length()>0){
							dp.setData4(Integer.parseInt(form.getField("Data1_4")));
						}
						dp.setData5(form.getField("Data1_5"));
						dp.setData6(form.getField("Data1_6"));	               		     
						if(form.getField("Data1_7").length()>0){
							dp.setData7(Integer.parseInt(form.getField("Data1_7")));
						}
						dp.setPlanid(planid);
						dao.PeaceCrud(dp, "DataForm11", "save", (long) 0, 0, 0, null);

						DataForm11 dp2= new DataForm11();     
						dp2.setData1(form.getField("Data2_1"));	
						dp2.setData2(form.getField("Data2_2"));
						dp2.setData3(form.getField("Data2_3"));
						if(form.getField("Data2_4").length()>0){
							dp2.setData4(Integer.parseInt(form.getField("Data2_4")));
						}

						dp2.setData5(form.getField("Data2_5"));
						dp2.setPlanid(planid);
						dp2.setData6(form.getField("Data2_6"));
						if(form.getField("Data2_7").length()>0){
							dp2.setData7(Integer.parseInt(form.getField("Data2_7")));
						}

						dao.PeaceCrud(dp2, "DataForm11", "save", (long) 0, 0, 0, null);
						DataForm11 dp3= new DataForm11();     

						dp3.setData1(form.getField("Data3_1"));	
						dp3.setData2(form.getField("Data3_2"));
						dp3.setData3(form.getField("Data3_3"));
						if(form.getField("Data3_4").length()>0){
							dp3.setData4(Integer.parseInt(form.getField("Data3_4")));	
						}

						dp3.setData5(form.getField("Data3_5"));
						dp3.setPlanid(planid);
						dp3.setData6(form.getField("Data3_6"));
						if(form.getField("Data3_7").length()>0){
							dp3.setData7(Integer.parseInt(form.getField("Data3_7")));	
						}
						dao.PeaceCrud(dp3, "DataForm11", "save", (long) 0, 0, 0, null);

						DataForm11 dp4= new DataForm11();     
						dp4.setData1(form.getField("Data4_1"));	
						dp4.setData2(form.getField("Data4_2"));
						dp4.setData3(form.getField("Data4_3"));
						if(form.getField("Data4_4").length()>0){
							dp4.setData4(Integer.parseInt(form.getField("Data4_4")));
						}	               		     
						dp4.setData5(form.getField("Data4_5"));
						dp4.setPlanid(planid);
						dp4.setData6(form.getField("Data4_6"));
						if(form.getField("Data4_7").length()>0){
							dp4.setData7(Integer.parseInt(form.getField("Data4_7")));
						}
						dao.PeaceCrud(dp4, "DataForm11", "save", (long) 0, 0, 0, null);

						DataForm11 dp5= new DataForm11();     
						dp5.setData1(form.getField("Data5_1"));	
						dp5.setData2(form.getField("Data5_2"));
						dp5.setData3(form.getField("Data5_3"));
						if(form.getField("Data5_4").length()>0){
							dp5.setData4(Integer.parseInt(form.getField("Data5_4")));
						}	               		      
						dp5.setData5(form.getField("Data5_5"));
						dp5.setPlanid(planid);
						dp5.setData6(form.getField("Data5_6"));
						if(form.getField("Data5_7").length()>0){
							dp5.setData7(Integer.parseInt(form.getField("Data5_7")));
						}
						dao.PeaceCrud(dp5, "DataForm11", "save", (long) 0, 0, 0, null);

						DataForm11 dp6= new DataForm11();     
						dp6.setData1(form.getField("Data6_1"));	
						dp6.setData2(form.getField("Data6_2"));
						dp6.setData3(form.getField("Data6_3"));
						if(form.getField("Data6_4").length()>0){
							dp6.setData4(Integer.parseInt(form.getField("Data6_4")));
						}	               		     
						dp6.setData5(form.getField("Data6_5"));
						dp6.setPlanid(planid);
						dp6.setData6(form.getField("Data6_6"));
						if(form.getField("Data6_7").length()>0){
							dp6.setData7(Integer.parseInt(form.getField("Data6_7")));
						}	  
						dao.PeaceCrud(dp6, "DataForm11", "save", (long) 0, 0, 0, null);
						DataForm11 dp7= new DataForm11();     

						dp7.setData1(form.getField("Data7_1"));	
						dp7.setData2(form.getField("Data7_2"));
						dp7.setData3(form.getField("Data7_3"));
						if(form.getField("Data7_4").length()>0){
							dp7.setData4(Integer.parseInt(form.getField("Data7_4")));
						}	               		     
						dp7.setData5(form.getField("Data7_5"));
						dp7.setPlanid(planid);
						dp7.setData6(form.getField("Data7_6"));
						if(form.getField("Data7_7").length()>0){
							dp7.setData7(Integer.parseInt(form.getField("Data7_7")));
						}	
						dao.PeaceCrud(dp7, "DataForm11", "save", (long) 0, 0, 0, null);

						DataForm11 dp8= new DataForm11();     
						dp8.setData1(form.getField("Data8_1"));	
						dp8.setData2(form.getField("Data8_2"));
						dp8.setData3(form.getField("Data8_3"));
						if(form.getField("Data8_4").length()>0){
							dp8.setData4(Integer.parseInt(form.getField("Data8_4")));
						}	               		     
						dp8.setData5(form.getField("Data8_5"));
						dp8.setPlanid(planid);
						dp8.setData6(form.getField("Data8_6"));
						if(form.getField("Data8_7").length()>0){
							dp8.setData7(Integer.parseInt(form.getField("Data8_7")));
						}
						dao.PeaceCrud(dp8, "DataForm11", "save", (long) 0, 0, 0, null);


					}
					else if(noteid==124){
						dao.PeaceCrud(null, "DataForm14", "delete", (long) planid, 0, 0, "planid");

						DataForm14 dp= new DataForm14();                		   
						dp.setData1(form.getField("Data1_1"));	
						dp.setData2(form.getField("Data1_2"));
						dp.setData3(form.getField("Data1_3"));	               		     
						if(form.getField("Data1_4").length()>0){
							dp.setData4(Integer.parseInt(form.getField("Data1_4")));
						}
						dp.setData5(form.getField("Data1_5"));
						dp.setPlanid(planid);
						dao.PeaceCrud(dp, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp2= new DataForm14();     
						dp2.setData1(form.getField("Data2_1"));	
						dp2.setData2(form.getField("Data2_2"));
						dp2.setData3(form.getField("Data2_3"));
						if(form.getField("Data2_4").length()>0){
							dp2.setData4(Integer.parseInt(form.getField("Data2_4")));
						}

						dp2.setData5(form.getField("Data2_5"));
						dp2.setPlanid(planid);
						dao.PeaceCrud(dp2, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp3= new DataForm14();     

						dp3.setData1(form.getField("Data3_1"));	
						dp3.setData2(form.getField("Data3_2"));
						dp3.setData3(form.getField("Data3_3"));
						if(form.getField("Data3_4").length()>0){
							dp3.setData4(Integer.parseInt(form.getField("Data3_4")));	
						}

						dp3.setData5(form.getField("Data3_5"));
						dp3.setPlanid(planid);
						dao.PeaceCrud(dp3, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp4= new DataForm14();     
						dp4.setData1(form.getField("Data4_1"));	
						dp4.setData2(form.getField("Data4_2"));
						dp4.setData3(form.getField("Data4_3"));
						if(form.getField("Data4_4").length()>0){
							dp4.setData4(Integer.parseInt(form.getField("Data4_4")));
						}	               		     
						dp4.setData5(form.getField("Data4_5"));
						dp4.setPlanid(planid);
						dao.PeaceCrud(dp4, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp5= new DataForm14();     
						dp5.setData1(form.getField("Data5_1"));	
						dp5.setData2(form.getField("Data5_2"));
						dp5.setData3(form.getField("Data5_3"));
						if(form.getField("Data5_4").length()>0){
							dp5.setData4(Integer.parseInt(form.getField("Data5_4")));
						}

						dp5.setData5(form.getField("Data5_5"));
						dp5.setPlanid(planid);
						dao.PeaceCrud(dp5, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp6= new DataForm14();     
						dp6.setData1(form.getField("Data6_1"));	
						dp6.setData2(form.getField("Data6_2"));
						dp6.setData3(form.getField("Data6_3"));
						if(form.getField("Data6_4").length()>0){
							dp6.setData4(Integer.parseInt(form.getField("Data6_4")));
						}	               		     
						dp6.setData5(form.getField("Data6_5"));
						dp6.setPlanid(planid);
						dao.PeaceCrud(dp6, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp7= new DataForm14();     
						dp7.setData1(form.getField("Data7_1"));	
						dp7.setData2(form.getField("Data7_2"));
						dp7.setData3(form.getField("Data7_3"));
						if(form.getField("Data7_4").length()>0){
							dp7.setData4(Integer.parseInt(form.getField("Data7_4")));
						}	               		     
						dp7.setData5(form.getField("Data7_5"));
						dp7.setPlanid(planid);
						dao.PeaceCrud(dp7, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp8= new DataForm14();     
						dp8.setData1(form.getField("Data8_1"));	
						dp8.setData2(form.getField("Data8_2"));
						dp8.setData3(form.getField("Data8_3"));
						if(form.getField("Data8_4").length()>0){
							dp8.setData4(Integer.parseInt(form.getField("Data8_4")));
						}	               		     
						dp8.setData5(form.getField("Data8_5"));
						dp8.setPlanid(planid);
						dao.PeaceCrud(dp8, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp9= new DataForm14();     
						dp9.setData1(form.getField("Data9_1"));	
						dp9.setData2(form.getField("Data9_2"));
						dp9.setData3(form.getField("Data9_3"));
						if(form.getField("Data9_4").length()>0){
							dp9.setData4(Integer.parseInt(form.getField("Data9_4")));
						}

						dp9.setData5(form.getField("Data9_5"));
						dp9.setPlanid(planid);
						dao.PeaceCrud(dp9, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp10= new DataForm14();     
						dp10.setData1(form.getField("Data10_1"));	
						dp10.setData2(form.getField("Data10_2"));
						dp10.setData3(form.getField("Data10_3"));
						if(form.getField("Data10_4").length()>0){
							dp10.setData4(Integer.parseInt(form.getField("Data10_4")));
						}

						dp10.setData5(form.getField("Data10_5"));
						dp10.setPlanid(planid);
						dao.PeaceCrud(dp10, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp11= new DataForm14();     
						dp11.setData1(form.getField("Data11_1"));	
						dp11.setData2(form.getField("Data11_2"));
						dp11.setData3(form.getField("Data11_3"));
						if(form.getField("Data11_4").length()>0){
							dp11.setData4(Integer.parseInt(form.getField("Data11_4")));
						}

						dp11.setData5(form.getField("Data11_5"));
						dp11.setPlanid(planid);
						dao.PeaceCrud(dp11, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp12= new DataForm14();     
						dp12.setData1(form.getField("Data12_1"));	
						dp12.setData2(form.getField("Data12_2"));
						dp12.setData3(form.getField("Data12_3"));
						if(form.getField("Data12_4").length()>0){
							dp12.setData4(Integer.parseInt(form.getField("Data12_4")));
						}

						dp12.setData5(form.getField("Data12_5"));
						dp12.setPlanid(planid);
						dao.PeaceCrud(dp12, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp13= new DataForm14();     
						dp13.setData1(form.getField("Data13_1"));	
						dp13.setData2(form.getField("Data13_2"));
						dp13.setData3(form.getField("Data13_3"));
						if(form.getField("Data13_4").length()>0){
							dp13.setData4(Integer.parseInt(form.getField("Data13_4")));
						}

						dp13.setData5(form.getField("Data13_5"));
						dp13.setPlanid(planid);
						dao.PeaceCrud(dp13, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp14= new DataForm14();     
						dp14.setData1(form.getField("Data14_1"));	
						dp14.setData2(form.getField("Data14_2"));
						dp14.setData3(form.getField("Data14_3"));
						if(form.getField("Data14_4").length()>0){
							dp14.setData4(Integer.parseInt(form.getField("Data14_4")));
						}

						dp14.setData5(form.getField("Data14_5"));
						dp14.setPlanid(planid);
						dao.PeaceCrud(dp14, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp15= new DataForm14();     
						dp15.setData1(form.getField("Data15_1"));	
						dp15.setData2(form.getField("Data15_2"));
						dp15.setData3(form.getField("Data15_3"));
						if(form.getField("Data15_4").length()>0){
							dp15.setData4(Integer.parseInt(form.getField("Data15_4")));
						}

						dp15.setData5(form.getField("Data15_5"));
						dp15.setPlanid(planid);
						dao.PeaceCrud(dp15, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp16= new DataForm14();     
						dp16.setData1(form.getField("Data16_1"));	
						dp16.setData2(form.getField("Data16_2"));
						dp16.setData3(form.getField("Data16_3"));
						if(form.getField("Data15_4").length()>0){
							dp16.setData4(Integer.parseInt(form.getField("Data16_4")));
						}

						dp16.setData5(form.getField("Data16_5"));
						dp16.setPlanid(planid);
						dao.PeaceCrud(dp16, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp17= new DataForm14();     
						dp17.setData1(form.getField("Data17_1"));	
						dp17.setData2(form.getField("Data17_2"));
						dp17.setData3(form.getField("Data17_3"));
						if(form.getField("Data17_4").length()>0){
							dp17.setData4(Integer.parseInt(form.getField("Data17_4")));
						}

						dp17.setData5(form.getField("Data17_5"));
						dp17.setPlanid(planid);
						dao.PeaceCrud(dp17, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp18= new DataForm14();     
						dp18.setData1(form.getField("Data18_1"));	
						dp18.setData2(form.getField("Data18_2"));
						dp18.setData3(form.getField("Data18_3"));
						if(form.getField("Data18_4").length()>0){
							dp18.setData4(Integer.parseInt(form.getField("Data18_4")));
						}

						dp18.setData5(form.getField("Data18_5"));
						dp18.setPlanid(planid);
						dao.PeaceCrud(dp18, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp19= new DataForm14();     
						dp19.setData1(form.getField("Data19_1"));	
						dp19.setData2(form.getField("Data19_2"));
						dp19.setData3(form.getField("Data19_3"));
						if(form.getField("Data19_4").length()>0){
							dp19.setData4(Integer.parseInt(form.getField("Data19_4")));
						}	               		    
						dp19.setData5(form.getField("Data19_5"));
						dp19.setPlanid(planid);
						dao.PeaceCrud(dp19, "DataForm14", "save", (long) 0, 0, 0, null);

						DataForm14 dp20= new DataForm14();     
						dp20.setData1(form.getField("Data20_1"));	
						dp20.setData2(form.getField("Data20_2"));
						dp20.setData3(form.getField("Data20_3"));
						if(form.getField("Data20_4").length()>0){
							dp20.setData4(Integer.parseInt(form.getField("Data20_4")));
						}	 

						dp20.setData5(form.getField("Data20_5"));
						dp20.setPlanid(planid);
						dao.PeaceCrud(dp20, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp21= new DataForm14();     
						dp21.setData1(form.getField("Data21_1"));	
						dp21.setData2(form.getField("Data21_2"));
						dp21.setData3(form.getField("Data21_3"));
						if(form.getField("Data21_4").length()>0){
							dp21.setData4(Integer.parseInt(form.getField("Data21_4")));
						}	

						dp21.setData5(form.getField("Data21_5"));
						dp21.setPlanid(planid);
						dao.PeaceCrud(dp21, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp22= new DataForm14();     
						dp22.setData1(form.getField("Data22_1"));	
						dp22.setData2(form.getField("Data22_2"));
						dp22.setData3(form.getField("Data22_3"));

						if(form.getField("Data22_4").length()>0){
							dp22.setData4(Integer.parseInt(form.getField("Data22_4")));
						}	

						dp22.setData5(form.getField("Data22_5"));
						dp22.setPlanid(planid);
						dao.PeaceCrud(dp22, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp23= new DataForm14();     
						dp23.setData1(form.getField("Data23_1"));	
						dp23.setData2(form.getField("Data23_2"));
						dp23.setData3(form.getField("Data23_3"));
						if(form.getField("Data23_4").length()>0){
							dp23.setData4(Integer.parseInt(form.getField("Data23_4")));
						}	

						dp23.setData5(form.getField("Data23_5"));
						dp23.setPlanid(planid);
						dao.PeaceCrud(dp23, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp24= new DataForm14();     
						dp24.setData1(form.getField("Data24_1"));	
						dp24.setData2(form.getField("Data24_2"));
						dp24.setData3(form.getField("Data24_3"));
						if(form.getField("Data24_4").length()>0){
							dp24.setData4(Integer.parseInt(form.getField("Data24_4")));
						}	

						dp24.setData5(form.getField("Data24_5"));
						dp24.setPlanid(planid);
						dao.PeaceCrud(dp24, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp25= new DataForm14();     
						dp25.setData1(form.getField("Data25_1"));	
						dp25.setData2(form.getField("Data25_2"));
						dp25.setData3(form.getField("Data25_3"));
						if(form.getField("Data25_4").length()>0){
							dp25.setData4(Integer.parseInt(form.getField("Data25_4")));
						}	

						dp25.setData5(form.getField("Data25_5"));
						dp25.setPlanid(planid);

						dao.PeaceCrud(dp25, "DataForm14", "save", (long) 0, 0, 0, null);
						DataForm14 dp26= new DataForm14();     

						dp26.setData1(form.getField("Data26_1"));	
						dp26.setData2(form.getField("Data26_2"));
						dp26.setData3(form.getField("Data26_3"));
						if(form.getField("Data26_4").length()>0){
							dp26.setData4(Integer.parseInt(form.getField("Data26_4")));
						}	

						dp26.setData5(form.getField("Data26_5"));
						dp26.setPlanid(planid);

						dao.PeaceCrud(dp26, "DataForm14", "save", (long) 0, 0, 0, null);

					}

					else if(noteid==125){
						dao.PeaceCrud(null, "DataForm15", "delete", (long) planid, 0, 0, "planid");

						DataForm15 dp= new DataForm15();                		   
						dp.setData1(form.getField("Data1_1"));	
						dp.setData2(form.getField("Data1_2"));
						dp.setData3(form.getField("Data1_3"));
						dp.setData4(Integer.parseInt(form.getField("Data1_4")));
						dp.setData5(form.getField("Data1_5"));
						dp.setPlanid(planid);
						dao.PeaceCrud(dp, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp2= new DataForm15();     
						dp2.setData1(form.getField("Data2_1"));	
						dp2.setData2(form.getField("Data2_2"));
						dp2.setData3(form.getField("Data2_3"));
						dp2.setData4(Integer.parseInt(form.getField("Data2_4")));
						dp2.setData5(form.getField("Data2_5"));
						dp2.setPlanid(planid);
						dao.PeaceCrud(dp2, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp3= new DataForm15();     

						dp3.setData1(form.getField("Data3_1"));	
						dp3.setData2(form.getField("Data3_2"));
						dp3.setData3(form.getField("Data3_3"));
						dp3.setData4(Integer.parseInt(form.getField("Data3_4")));
						dp3.setData5(form.getField("Data3_5"));
						dp3.setPlanid(planid);
						dao.PeaceCrud(dp3, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp4= new DataForm15();     
						dp4.setData1(form.getField("Data4_1"));	
						dp4.setData2(form.getField("Data4_2"));
						dp4.setData3(form.getField("Data4_3"));
						dp4.setData4(Integer.parseInt(form.getField("Data4_4")));
						dp4.setData5(form.getField("Data4_5"));
						dp4.setPlanid(planid);
						dao.PeaceCrud(dp4, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp5= new DataForm15();     
						dp5.setData1(form.getField("Data5_1"));	
						dp5.setData2(form.getField("Data5_2"));
						dp5.setData3(form.getField("Data5_3"));
						dp5.setData4(Integer.parseInt(form.getField("Data5_4")));
						dp5.setData5(form.getField("Data5_5"));
						dp5.setPlanid(planid);
						dao.PeaceCrud(dp5, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp6= new DataForm15();     
						dp6.setData1(form.getField("Data6_1"));	
						dp6.setData2(form.getField("Data6_2"));
						dp6.setData3(form.getField("Data6_3"));
						dp6.setData4(Integer.parseInt(form.getField("Data6_4")));
						dp6.setData5(form.getField("Data6_5"));
						dp6.setPlanid(planid);
						dao.PeaceCrud(dp6, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp7= new DataForm15();     
						dp7.setData1(form.getField("Data7_1"));	
						dp7.setData2(form.getField("Data7_2"));
						dp7.setData3(form.getField("Data7_3"));
						dp7.setData4(Integer.parseInt(form.getField("Data7_4")));
						dp7.setData5(form.getField("Data7_5"));
						dp7.setPlanid(planid);
						dao.PeaceCrud(dp7, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp8= new DataForm15();     
						dp8.setData1(form.getField("Data8_1"));	
						dp8.setData2(form.getField("Data8_2"));
						dp8.setData3(form.getField("Data8_3"));
						dp8.setData4(Integer.parseInt(form.getField("Data8_4")));
						dp8.setData5(form.getField("Data8_5"));
						dp8.setPlanid(planid);
						dao.PeaceCrud(dp8, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp9= new DataForm15();     
						dp9.setData1(form.getField("Data9_1"));	
						dp9.setData2(form.getField("Data9_2"));
						dp9.setData3(form.getField("Data9_3"));
						dp9.setData4(Integer.parseInt(form.getField("Data9_4")));
						dp9.setData5(form.getField("Data9_5"));
						dp9.setPlanid(planid);
						dao.PeaceCrud(dp9, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp10= new DataForm15();     
						dp10.setData1(form.getField("Data10_1"));	
						dp10.setData2(form.getField("Data10_2"));
						dp10.setData3(form.getField("Data10_3"));
						dp10.setData4(Integer.parseInt(form.getField("Data10_4")));
						dp10.setData5(form.getField("Data10_5"));
						dp10.setPlanid(planid);
						dao.PeaceCrud(dp10, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp11= new DataForm15();     
						dp11.setData1(form.getField("Data11_1"));	
						dp11.setData2(form.getField("Data11_2"));
						dp11.setData3(form.getField("Data11_3"));
						dp11.setData4(Integer.parseInt(form.getField("Data11_4")));
						dp11.setData5(form.getField("Data11_5"));
						dp11.setPlanid(planid);
						dao.PeaceCrud(dp11, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp12= new DataForm15();     
						dp12.setData1(form.getField("Data12_1"));	
						dp12.setData2(form.getField("Data12_2"));
						dp12.setData3(form.getField("Data12_3"));
						dp12.setData4(Integer.parseInt(form.getField("Data12_4")));
						dp12.setData5(form.getField("Data12_5"));
						dp12.setPlanid(planid);
						dao.PeaceCrud(dp12, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp13= new DataForm15();     
						dp13.setData1(form.getField("Data13_1"));	
						dp13.setData2(form.getField("Data13_2"));
						dp13.setData3(form.getField("Data13_3"));
						dp13.setData4(Integer.parseInt(form.getField("Data13_4")));
						dp13.setData5(form.getField("Data13_5"));
						dp13.setPlanid(planid);
						dao.PeaceCrud(dp13, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp14= new DataForm15();     
						dp14.setData1(form.getField("Data14_1"));	
						dp14.setData2(form.getField("Data14_2"));
						dp14.setData3(form.getField("Data14_3"));
						dp14.setData4(Integer.parseInt(form.getField("Data14_4")));
						dp14.setData5(form.getField("Data14_5"));
						dp14.setPlanid(planid);
						dao.PeaceCrud(dp14, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp15= new DataForm15();     
						dp15.setData1(form.getField("Data15_1"));	
						dp15.setData2(form.getField("Data15_2"));
						dp15.setData3(form.getField("Data15_3"));
						dp15.setData4(Integer.parseInt(form.getField("Data15_4")));
						dp15.setData5(form.getField("Data15_5"));
						dp15.setPlanid(planid);
						dao.PeaceCrud(dp15, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp16= new DataForm15();     
						dp16.setData1(form.getField("Data16_1"));	
						dp16.setData2(form.getField("Data16_2"));
						dp16.setData3(form.getField("Data16_3"));
						dp16.setData4(Integer.parseInt(form.getField("Data16_4")));
						dp16.setData5(form.getField("Data16_5"));
						dp16.setPlanid(planid);
						dao.PeaceCrud(dp16, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp17= new DataForm15();     
						dp17.setData1(form.getField("Data17_1"));	
						dp17.setData2(form.getField("Data17_2"));
						dp17.setData3(form.getField("Data17_3"));
						dp17.setData4(Integer.parseInt(form.getField("Data17_4")));
						dp17.setData5(form.getField("Data17_5"));
						dp17.setPlanid(planid);
						dao.PeaceCrud(dp17, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp18= new DataForm15();     
						dp18.setData1(form.getField("Data18_1"));	
						dp18.setData2(form.getField("Data18_2"));
						dp18.setData3(form.getField("Data18_3"));
						dp18.setData4(Integer.parseInt(form.getField("Data18_4")));
						dp18.setData5(form.getField("Data18_5"));
						dp18.setPlanid(planid);
						dao.PeaceCrud(dp18, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp19= new DataForm15();     
						dp19.setData1(form.getField("Data19_1"));	
						dp19.setData2(form.getField("Data19_2"));
						dp19.setData3(form.getField("Data19_3"));
						dp19.setData4(Integer.parseInt(form.getField("Data19_4")));
						dp19.setData5(form.getField("Data19_5"));
						dp19.setPlanid(planid);
						dao.PeaceCrud(dp19, "DataForm15", "save", (long) 0, 0, 0, null);

						DataForm15 dp20= new DataForm15();     
						dp20.setData1(form.getField("Data20_1"));	
						dp20.setData2(form.getField("Data20_2"));
						dp20.setData3(form.getField("Data20_3"));
						dp20.setData4(Integer.parseInt(form.getField("Data20_4")));
						dp20.setData5(form.getField("Data20_5"));
						dp20.setPlanid(planid);
						dao.PeaceCrud(dp20, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp21= new DataForm15();     
						dp21.setData1(form.getField("Data21_1"));	
						dp21.setData2(form.getField("Data21_2"));
						dp21.setData3(form.getField("Data21_3"));
						dp21.setData4(Integer.parseInt(form.getField("Data21_4")));
						dp21.setData5(form.getField("Data21_5"));
						dp21.setPlanid(planid);
						dao.PeaceCrud(dp21, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp22= new DataForm15();     
						dp22.setData1(form.getField("Data22_1"));	
						dp22.setData2(form.getField("Data22_2"));
						dp22.setData3(form.getField("Data22_3"));
						dp22.setData4(Integer.parseInt(form.getField("Data22_4")));
						dp22.setData5(form.getField("Data22_5"));
						dp22.setPlanid(planid);
						dao.PeaceCrud(dp22, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp23= new DataForm15();     
						dp23.setData1(form.getField("Data23_1"));	
						dp23.setData2(form.getField("Data23_2"));
						dp23.setData3(form.getField("Data23_3"));
						dp23.setData4(Integer.parseInt(form.getField("Data23_4")));
						dp23.setData5(form.getField("Data23_5"));
						dp23.setPlanid(planid);
						dao.PeaceCrud(dp23, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp24= new DataForm15();     
						dp24.setData1(form.getField("Data24_1"));	
						dp24.setData2(form.getField("Data24_2"));
						dp24.setData3(form.getField("Data24_3"));
						dp24.setData4(Integer.parseInt(form.getField("Data24_4")));
						dp24.setData5(form.getField("Data24_5"));
						dp24.setPlanid(planid);
						dao.PeaceCrud(dp24, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp25= new DataForm15();     
						dp25.setData1(form.getField("Data25_1"));	
						dp25.setData2(form.getField("Data25_2"));
						dp25.setData3(form.getField("Data25_3"));
						dp25.setData4(Integer.parseInt(form.getField("Data25_4")));
						dp25.setData5(form.getField("Data25_5"));
						dp25.setPlanid(planid);

						dao.PeaceCrud(dp25, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp26= new DataForm15();     

						dp26.setData1(form.getField("Data26_1"));	
						dp26.setData2(form.getField("Data26_2"));
						dp26.setData3(form.getField("Data26_3"));
						dp26.setData4(Integer.parseInt(form.getField("Data26_4")));
						dp26.setData5(form.getField("Data26_5"));
						dp26.setPlanid(planid);

						dao.PeaceCrud(dp26, "DataForm15", "save", (long) 0, 0, 0, null);
						DataForm15 dp27= new DataForm15();     

						dp27.setData1(form.getField("Data27_1"));	
						dp27.setData2(form.getField("Data27_2"));
						dp27.setData3(form.getField("Data27_3"));
						dp27.setData4(Integer.parseInt(form.getField("Data27_4")));
						dp27.setData5(form.getField("Data27_5"));
						dp27.setPlanid(planid);

						dao.PeaceCrud(dp27, "DataForm15", "save", (long) 0, 0, 0, null);
					}
				}
				else{
					try{
						System.out.println("aaaaaaaaaaaaaaaaa "+xfa.getDatasetsNode());


						Node node = xfa.getDatasetsNode();
						/*NodeList list = node.getChildNodes();

					for (int i = 0; i < list.getLength(); i++) {
						System.out.println("1 "+list.item(i).getLocalName());;
						if("data".equalsIgnoreCase(list.item(i).getLocalName())) {
							node = list.item(i);
							break;
						}
					}
					list = node.getChildNodes();
					for (int i = 0; i < list.getLength(); i++) {
						System.out.println("2 "+list.item(i).getLocalName());;
						if("MainForm".equalsIgnoreCase(list.item(i).getLocalName())) {
							node = list.item(i);
							break;
						}
					}
					list = node.getChildNodes();
					for (int i = 0; i < list.getLength(); i++) {
						System.out.println("2 "+list.item(i).getLocalName());;
						if("MainForm".equalsIgnoreCase(list.item(i).getLocalName())) {
							node = list.item(i);	
							break;
						}
					}
					list = node.getChildNodes();*/

						Transformer tf = TransformerFactory.newInstance().newTransformer();
						tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
						tf.setOutputProperty(OutputKeys.INDENT, "yes");
						StreamResult result = new StreamResult(new StringWriter());
						tf.transform(new DOMSource(node), result);
						//System.out.println(result.getWriter().toString());
						JXML jxml = new JXML(result.getWriter().toString());
						AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+planid+"", "current");

						for(int sf = 0; sf<staticFields.length;sf++){
							String[] response = jxml.selectQuery("//"+staticFields[sf]+"/text()");
							if (response.length > 0){
								info.setField(staticFieldsVars[sf], response[0]);
							}
						}

						if(!info.getFormid().equalsIgnoreCase(String.valueOf(noteid))){
							return "noeq";
						}


						JSONObject main = jxml.selectQueryTags("//MainForm/*");
						JSONArray arr = (JSONArray) main.get("values");
						JSONArray tags = (JSONArray) main.get("tags");
						JSONObject robj = new JSONObject();


						/*for(int i=0; i<tags.length();i++){
						if(tags.get(i).toString().equalsIgnoreCase("Lic_num")){
							if(!arr.get(i).toString().equalsIgnoreCase(an.getLicensenum())){
								robj.put("Lic", false);
								return robj.toString();
							}
						}
						if(tags.get(i).toString().equalsIgnoreCase("Year1")){
							if(!arr.get(i).toString().equalsIgnoreCase(an.getReportyear())){
								robj.put("Year1", false);
								return robj.toString();
							}
						}
						if(tags.get(i).toString().equalsIgnoreCase("FormID")){
							if(!arr.get(i).toString().equalsIgnoreCase(String.valueOf(noteid))){
								robj.put("FormID", false);
								return robj.toString();
							}
						}
						if(tags.get(i).toString().equalsIgnoreCase("RegID")){
							if(!arr.get(i).toString().equalsIgnoreCase(an.getLpReg())){
								robj.put("RegID", false);
								return robj.toString();
							}
						}
						if(tags.get(i).toString().equalsIgnoreCase("PlanID")){
							if(!arr.get(i).toString().equalsIgnoreCase(String.valueOf(an.getId()))){
								robj.put("PlanID", false);
								return robj.toString();
							}
						}
					}*/

						System.out.println("$$$$$$"+main.toString());
						System.out.println("$$$$$$"+arr.toString());


						System.out.println("$$$$$$"+arr.get(0).toString());
						System.out.println("$$$$$$"+an.getLicensenum());



						dao.PeaceCrud(info, "DataNotesInfo", "save", (long) 0, 0, 0, null);

						if (noteid == 27 || noteid == 78){
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan6_1", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan6_1", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							//dao.PeaceCrud(null, "DataMinPlan6_1", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							for(int i=1;i<=6;i++){
								JSONObject response = jxml.selectQueryTags("//MainForm/Table1/Row"+i+"/*");
								JSONArray tags_array = (JSONArray) response.get("tags");
								JSONArray data_array = (JSONArray) response.get("values");
								JSONArray parent_array = (JSONArray) response.get("parent");
								if (data_array.length() > 0){
									DataMinPlan6_1 data = new DataMinPlan6_1();
									data.setData1(data_array.getString(0));
									data.setData2(data_array.getString(1));

									data.setData3(data_array.getString(2));
									if (!(data_array.getString(3).equalsIgnoreCase(null) || data_array.getString(3).length() == 0)){
										data.setData4(data_array.getDouble(3));
									}
									data.setData5(data_array.getString(4));
									data.setNoteid(noteid);
									data.setPlanid(planid);
									
									if (transitions != null && transitions.size() > 0){
										data.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										data.setIstodotgol(false);
									}
									data.setData0((jxml.selectQuery("//MainForm/Tsahilgaan/text()").length > 0) ? jxml.selectQuery("//MainForm/Tsahilgaan/text()")[0] : "");
									dao.PeaceCrud(data, "DataMinPlan6_1", "save", (long) 0, 0, 0, null);
								}

							}



						}

						if (noteid == 190){
							dao.PeaceCrud(null, "DataCoalForm3", "multidelete", (long) 0, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							String[] vars = {"B_olborlolt","B_gargalt","Sub_gar/B_bayajuulalt","Sub_bor/B_borluulalt"};
							for(int i=0; i<vars.length;i++){
								for(int j=1;j<=10;j++){
									JSONObject response = jxml.selectQueryTags("//MainForm/Body_form/"+vars[i]+"/Row"+j+"/*");
									JSONArray data_array = (JSONArray) response.get("values");
									if (data_array.length() > 0){
										System.out.println(data_array.toString());
										DataCoalForm3 data = new DataCoalForm3();
										for(int i1=0;i1<19;i1++){
											if (i1 == 0 || i1 == 1){
												data.setField("data"+(i1+1), data_array.getString(i1));
											}
											else{
												if (!(data_array.getString(i1).equalsIgnoreCase(null) || data_array.getString(i1).length() == 0)){
													data.setField("data"+(i1+1), data_array.getDouble(i1));
												}
											}

										}
										data.setNoteid(noteid);
										data.setPlanid(planid);
										dao.PeaceCrud(data, "DataCoalForm3", "save", (long) 0, 0, 0, null);
									}
								}

							}

						}
						
						if (noteid == 48){
							//dao.PeaceCrud(null, "DataMinPlan6_2", "multidelete", (long) 0, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan6_2", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan6_2", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}

							String[] dirs = {"Row1","Row1_1","Row1_2","Row2","Row3","Row4","Row5","Row6","Row7","Row8","Row9","Row10","Row11","Row12","Row12_1","Row12_2","Row12_3","Row12_4","Row13","Row13_1","Row13_2","Row14","Row15","Row16","Row17","Row18","Row19"};
							for(int i=0;i<dirs.length;i++){
								JSONObject response = jxml.selectQueryTags("//MainForm/Sub_table/Table1/"+dirs[i]+"/*");
								JSONArray tags_array = (JSONArray) response.get("tags");
								JSONArray data1_array = (JSONArray) response.get("values");
								JSONArray parent_array = (JSONArray) response.get("parent");
								if (data1_array.length() > 0){
									DataGeoReport2 data = new DataGeoReport2();
									for(int j=1;j<=18;j++){
										if ((data1_array.getString(j-1) != null) && (data1_array.getString(j-1).length() > 0)){
											if (j == 3){
												data.setField("data"+j, Double.parseDouble(data1_array.getString(j-1)));
											}
											else if (j < 3){
												data.setField("data"+j, data1_array.getString(j-1));
											}
											else{
												data.setField("data"+j, data1_array.getDouble(j-1));
											}
										}
									}
									data.setNoteid(noteid);
									data.setPlanid(planid);
									if (transitions != null && transitions.size() > 0){
										data.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										data.setIstodotgol(false);
									}
									dao.PeaceCrud(data, "DataGeoReport2", "save", (long) 0, 0, 0, null);
								}
							}
						}

						if (noteid == 229 || noteid == 247){
							//dao.PeaceCrud(null, "DataMinPlan6_2", "multidelete", (long) 0, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan6_2", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan6_2", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Table1/*/*");
							String[] water = jxml.selectQuery("//Water/text()");
							JSONArray tags_array = (JSONArray) response.get("tags");
							JSONArray data1_array = (JSONArray) response.get("values");
							JSONArray parent_array = (JSONArray) response.get("parent");
							if (data1_array.length() > 0){
								/*for(int i=0;i<data1_array.length();i++){
								System.out.println(i + "==> " + data1_array.get(i));
							}*/
								System.out.println(data1_array.toString());
								for(int i=0;i<(int)(data1_array.length()/8);i++){
									DataMinPlan6_2 data = new DataMinPlan6_2();
									data.setData1(data1_array.getString(i*8));
									data.setData2(data1_array.getString(i*8+1));
									data.setData3(data1_array.getString(i*8+2));
									data.setData4(data1_array.getString(i*8+3));
									if (!(data1_array.getString(i*8+4).equalsIgnoreCase(null) || data1_array.getString(i*8+4).length() == 0)){
										System.out.println(i + "\t" + data1_array.getString(i*8+4) + "\t" + (i*8+4));
										data.setData5(Double.parseDouble(data1_array.getString(i*8+4).replaceAll(",", "")));
									}

									if (!(data1_array.getString(i*8+5).equalsIgnoreCase(null) || data1_array.getString(i*8+5).length() == 0)){
										data.setData6(Double.parseDouble(data1_array.getString(i*8+5).replaceAll(",", "")));
									}
									if (!(data1_array.getString(i*8+6).equalsIgnoreCase(null) || data1_array.getString(i*8+6).length() == 0)){
										data.setData7(Double.parseDouble(data1_array.getString(i*8+6).replaceAll(",", "")));
									}
									data.setData8(data1_array.getString(i*8+7));
									if (water.length > 0){
										data.setData9(water[0]);
									}
									data.setNoteid(noteid);
									data.setPlanid(planid);
									if (transitions != null && transitions.size() > 0){
										data.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										data.setIstodotgol(false);
									}
									dao.PeaceCrud(data, "DataMinPlan6_2", "save", (long) 0, 0, 0, null);
								}
							}
							/*data.setData1(form.getField("Data"+i+"_1"));
				    	data.setData2(form.getField("Data"+i+"_2"));
				    	data.setData3(form.getField("Data"+i+"_3"));
				    	data.setData4(form.getField("Data"+i+"_4"));
				    	if (!(form.getField("Data"+i+"_5").equalsIgnoreCase(null) || form.getField("Data"+i+"_5").length() == 0)){
				    		data.setData5(Double.parseDouble(form.getField("Data"+i+"_5")));
				    	}
				    	if (!(form.getField("Data"+i+"_6").equalsIgnoreCase(null) || form.getField("Data"+i+"_6").length() == 0)){
				    		data.setData6(Double.parseDouble(form.getField("Data"+i+"_6")));
				    	}
				    	if (!(form.getField("Data"+i+"_7").equalsIgnoreCase(null) || form.getField("Data"+i+"_7").length() == 0)){
				    		data.setData7(Double.parseDouble(form.getField("Data"+i+"_7")));
				    	}
				    	data.setData8(form.getField("Data"+i+"_8"));
				    	data.setData9(form.getField("Data8"));*/



						}
						if (noteid == 69){
							dao.PeaceCrud(null, "DataCoalForm2 ", "delete", (long) planid, 0, 0, "planid");
							JSONObject response = jxml.selectQueryTags("//MainForm/Body_form/Sub1/Table1/Row1/Table11/Row1/*");
							JSONArray tags_array = (JSONArray) response.get("tags");
							JSONArray data1_array = (JSONArray) response.get("values");
							JSONArray parent_array = (JSONArray) response.get("parent");

							String[] delStrings = {"TextField18","Table2"};
							JSONArray temp_data_value = new JSONArray();
							for(int j=0;j<tags_array.length();j++){
								if (java.util.Arrays.asList(delStrings).indexOf(tags_array.get(j)) == -1){
									temp_data_value.put(data1_array.get(j));
								}
							}
							for(int i=0;i<(int)(temp_data_value.length()/16);i++){
								DataCoalForm2 data = new DataCoalForm2();
								for(int j=1;j<=15;j++){
									if (!(temp_data_value.getString(i*16+j).equalsIgnoreCase(null) || temp_data_value.getString(i*16+j).length() == 0)){
										if (j==1 || j==2){
											data.setField("data"+j, temp_data_value.getString(i*16+j));
										}
										else{
											data.setField("data"+j, temp_data_value.getDouble(i*16+j));
										}
									}
								}
								if (!(temp_data_value.getString(i*16).equalsIgnoreCase(null) || temp_data_value.getString(i*16).length() == 0)){
								    if (temp_data_value.getString(i*16).indexOf('.') > -1){
                                        data.setDataIndex((long)Double.parseDouble(temp_data_value.getString(i*16)));
                                    }
                                    else{
                                        data.setDataIndex(Long.parseLong(temp_data_value.getString(i*16)));
                                    }

								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("A");
								//System.out.println(data.toString());
								dao.PeaceCrud(data, "DataCoalForm2", "save", (long) 0, 0, 0, null);



							}

							response = jxml.selectQueryTags("//MainForm/Body_form/Sub2/Table2/Row1/Table22/Row1/*");
							tags_array = (JSONArray) response.get("tags");
							data1_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");
							/*for(int i=0;i<data1_array.length();i++){
							System.out.println(i + "." + data1_array.get(i));
						}*/
							temp_data_value = new JSONArray();
							for(int j=0;j<tags_array.length();j++){
								if (java.util.Arrays.asList(delStrings).indexOf(tags_array.get(j)) == -1){
									temp_data_value.put(data1_array.get(j));
								}
							}
							for(int i=0;i<(int)(temp_data_value.length()/16);i++){
								DataCoalForm2 data = new DataCoalForm2();
								for(int j=1;j<=15;j++){
									if (!(temp_data_value.getString(i*16+j).equalsIgnoreCase(null) || temp_data_value.getString(i*16+j).length() == 0)){
									    if (j == 1 || j == 2){
                                            data.setField("data"+j, temp_data_value.getString(i*16+j));
                                        }
                                        else{
                                            data.setField("data"+j, temp_data_value.getDouble(i*16+j));
                                        }
									}
								}
								if (!(temp_data_value.getString(i*16).equalsIgnoreCase(null) || temp_data_value.getString(i*16).length() == 0)){
								    if (temp_data_value.getString(i*16).indexOf(".") > -1){
                                        data.setDataIndex((long)temp_data_value.getDouble(i*16));
                                    }
                                    else{
                                        data.setDataIndex(Long.parseLong(temp_data_value.getString(i*16)));
                                    }

								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("B");
								dao.PeaceCrud(data, "DataCoalForm2", "save", (long) 0, 0, 0, null);
							}

							response = jxml.selectQueryTags("//MainForm/Body_form/Sub3/Table3/Row1/Table33/Row1/*");
							tags_array = (JSONArray) response.get("tags");
							data1_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");
							/*for(int i=0;i<data1_array.length();i++){
							System.out.println(i + "." + data1_array.get(i));
						}*/
							temp_data_value = new JSONArray();
							for(int j=0;j<tags_array.length();j++){
								if (java.util.Arrays.asList(delStrings).indexOf(tags_array.get(j)) == -1){
									temp_data_value.put(data1_array.get(j));
								}
							}
							for(int i=0;i<(int)(temp_data_value.length()/16);i++){
								DataCoalForm2 data = new DataCoalForm2();
								for(int j=1;j<=15;j++){
									if (!(temp_data_value.getString(i*16+j).equalsIgnoreCase(null) || temp_data_value.getString(i*16+j).length() == 0)){
										if (j == 1 || j == 2){
											data.setField("data"+j, temp_data_value.getString(i*16+j));
										}
										else{
											data.setField("data"+j, temp_data_value.getDouble(i*16+j));
										}
									}
								}
								if (!(temp_data_value.getString(i*16).equalsIgnoreCase(null) || temp_data_value.getString(i*16).length() == 0)){
                                    if (temp_data_value.getString(i*16).indexOf(".") > -1){
                                        data.setDataIndex((long)temp_data_value.getDouble(i*16));
                                    }
                                    else{
                                        data.setDataIndex(Long.parseLong(temp_data_value.getString(i*16)));
                                    }
								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("C");
								dao.PeaceCrud(data, "DataCoalForm2", "save", (long) 0, 0, 0, null);
							}
							response = jxml.selectQueryTags("//MainForm/Body_form/Total/FooterRow/*");
							tags_array = (JSONArray) response.get("tags");
							data1_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");

							temp_data_value = new JSONArray();
							for(int j=0;j<tags_array.length();j++){
								if (java.util.Arrays.asList(delStrings).indexOf(tags_array.get(j)) == -1){
									temp_data_value.put(data1_array.get(j));
								}
							}
							for(int i=0;i<temp_data_value.length();i++){
								System.out.println(i + "." + temp_data_value.get(i));
							}
							for(int i=0;i<(int)(temp_data_value.length()/4);i++){
								DataCoalForm2 data = new DataCoalForm2();
								for(int j=0;j<4;j++){
									if (!(temp_data_value.getString(i*4+j).equalsIgnoreCase(null) || temp_data_value.getString(i*4+j).length() == 0)){
										String[] number  = temp_data_value.getString(i*4+j).split(",");
										StringBuilder builder = new StringBuilder();
										for(String num : number){
											builder.append(num);
										}
										data.setField("data"+(j+1), Double.parseDouble(builder.toString()));
									}
								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("TOTAL");
								dao.PeaceCrud(data, "DataCoalForm2", "save", (long) 0, 0, 0, null);
							}
							response = jxml.selectQueryTags("//MainForm/Body_form/Table1/FooterRow/*");
							tags_array = (JSONArray) response.get("tags");
							data1_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");

							temp_data_value = new JSONArray();
							for(int j=0;j<tags_array.length();j++){
								if (java.util.Arrays.asList(delStrings).indexOf(tags_array.get(j)) == -1){
									temp_data_value.put(data1_array.get(j));
								}
							}
							for(int i=0;i<temp_data_value.length();i++){
								System.out.println(i + "." + temp_data_value.get(i));
							}
							for(int i=0;i<(int)(temp_data_value.length()/4);i++){
								DataCoalForm2 data = new DataCoalForm2();
								for(int j=0;j<4;j++){
									if (!(temp_data_value.getString(i*4+j).equalsIgnoreCase(null) || temp_data_value.getString(i*4+j).length() == 0)){
										data.setField("data"+(j+1), temp_data_value.getDouble(i*4+j));
									}
								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("TOTAL_A");
								dao.PeaceCrud(data, "DataCoalForm2", "save", (long) 0, 0, 0, null);
							}
							response = jxml.selectQueryTags("//MainForm/Body_form/Table2/FooterRow/*");
							tags_array = (JSONArray) response.get("tags");
							data1_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");

							temp_data_value = new JSONArray();
							for(int j=0;j<tags_array.length();j++){
								if (java.util.Arrays.asList(delStrings).indexOf(tags_array.get(j)) == -1){
									temp_data_value.put(data1_array.get(j));
								}
							}
							for(int i=0;i<temp_data_value.length();i++){
								System.out.println(i + "." + temp_data_value.get(i));
							}
							for(int i=0;i<(int)(temp_data_value.length()/4);i++){
								DataCoalForm2 data = new DataCoalForm2();
								for(int j=0;j<4;j++){
									if (!(temp_data_value.getString(i*4+j).equalsIgnoreCase(null) || temp_data_value.getString(i*4+j).length() == 0)){
										data.setField("data"+(j+1), temp_data_value.getDouble(i*4+j));
									}
								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("TOTAL_B");
								dao.PeaceCrud(data, "DataCoalForm2", "save", (long) 0, 0, 0, null);
							}
							response = jxml.selectQueryTags("//MainForm/Body_form/Sub3/Table3/FooterRow/*");
							tags_array = (JSONArray) response.get("tags");
							data1_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");

							temp_data_value = new JSONArray();
							for(int j=0;j<tags_array.length();j++){
								if (java.util.Arrays.asList(delStrings).indexOf(tags_array.get(j)) == -1){
									temp_data_value.put(data1_array.get(j));
								}
							}

							for(int i=0;i<(int)(temp_data_value.length()/4);i++){
								DataCoalForm2 data = new DataCoalForm2();
								for(int j=0;j<4;j++){
									if (!(temp_data_value.getString(i*4+j).equalsIgnoreCase(null) || temp_data_value.getString(i*4+j).length() == 0)){
									    if (j == 0 || j == 1){
                                            data.setField("data"+(j+1), temp_data_value.getString(i*4+j));
                                        }
                                        else{
                                            data.setField("data"+(j+1), temp_data_value.getDouble(i*4+j));
                                        }
									}
								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("TOTAL_C");
								dao.PeaceCrud(data, "DataCoalForm2", "save", (long) 0, 0, 0, null);
							}

							response = jxml.selectQueryTags("//MainForm/Body_form/Total/Total/FooterRow_Total/*");
							tags_array = (JSONArray) response.get("tags");
							data1_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");

							temp_data_value = new JSONArray();
							for(int j=0;j<tags_array.length();j++){
								if (java.util.Arrays.asList(delStrings).indexOf(tags_array.get(j)) == -1){
									temp_data_value.put(data1_array.get(j));
								}
							}

							for(int i=0;i<(int)(temp_data_value.length()/4);i++){
								DataCoalForm2 data = new DataCoalForm2();
								for(int j=0;j<4;j++){
									if (!(temp_data_value.getString(i*4+j).equalsIgnoreCase(null) || temp_data_value.getString(i*4+j).length() == 0)){
                                        if (j == 0 || j == 1){
                                            data.setField("data"+(j+1), temp_data_value.getString(i*4+j));
                                        }
                                        else{
                                            data.setField("data"+(j+1), Double.parseDouble(temp_data_value.getString(i*4+j).replaceAll(",", "")));
                                        }
									}
								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("TOTAL_ABC");
								dao.PeaceCrud(data, "DataCoalForm2", "save", (long) 0, 0, 0, null);
							}
						}

						if (noteid == 38 || noteid == 87){
							//dao.PeaceCrud(null, "DataMinPlan14", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan14", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan14", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Table_Body/*/*");
							JSONArray data_array = (JSONArray) response.get("values");
							if (data_array.length() > 0){
								for(int i=0;i<(int)(data_array.length()/5);i++){
									DataMinPlan14 data = new DataMinPlan14();
									for(int j=0;j<5;j++){
										if (!(data_array.getString(i*5+j).equalsIgnoreCase(null) || data_array.getString(i*5+j).length() == 0)){
											if (j == 3){
												data.setField("data"+(j+1), data_array.getDouble(i*5+j));
											}
											else{
												data.setField("data"+(j+1), data_array.getString(i*5+j));
											}
										}
									}
									data.setNoteid(noteid);
									data.setPlanid(planid);
									if (transitions != null && transitions.size() > 0){
										data.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										data.setIstodotgol(false);
									}
									dao.PeaceCrud(data, "DataMinPlan14", "save", (long) 0, 0, 0, null);
								}
							}
						}
						if (noteid == 39 || noteid == 90){
							dao.PeaceCrud(null, "DataMinPlan15", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan15", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan15", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Table_Body/*/*");
							JSONArray data_array = (JSONArray) response.get("values");
							if (data_array.length() > 0){
								for(int i=0;i<(int)(data_array.length()/5);i++){
									DataMinPlan15 data = new DataMinPlan15();
									for(int j=0;j<5;j++){
										if (!(data_array.getString(i*5+j).equalsIgnoreCase(null) || data_array.getString(i*5+j).length() == 0)){
											if (j == 3){
												data.setField("data"+(j+1), data_array.getDouble(i*5+j));
											}
											else{
												data.setField("data"+(j+1), data_array.getString(i*5+j));
											}
										}
									}
									data.setNoteid(noteid);
									data.setPlanid(planid);
									if (transitions != null && transitions.size() > 0){
										data.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										data.setIstodotgol(false);
									}
									dao.PeaceCrud(data, "DataMinPlan15", "save", (long) 0, 0, 0, null);
								}
							}
						}

						if (noteid == 40 || noteid == 93){
							//dao.PeaceCrud(null, "DataMinPlan16", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan16", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan16", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							String[] vars = {"1","2","3","11"};
							for(int i=0; i<vars.length;i++){
								if (i==0 || i==2){
									JSONObject response = jxml.selectQueryTags("//MainForm/Table_Body"+vars[i]+"/*/*");
									JSONArray data_array = (JSONArray) response.get("values");
									if (data_array.length() > 0){
										for(int j=0;j<(int)(data_array.length()/5);j++){
											DataMinPlan16 data = new DataMinPlan16();

											data.setData1(data_array.getString(j*5));
											data.setData2(data_array.getString(j*5+1));
											data.setData3(data_array.getString(j*5+2));
											if (!(data_array.getString(j*5+3).equalsIgnoreCase(null) || data_array.getString(j*5+3).length() == 0)){
												data.setData4(data_array.getDouble(j*5+3));
											}
											data.setData5(data_array.getString(j*5+4));
											data.setNoteid(noteid);
											data.setPlanid(planid);
											if (transitions != null && transitions.size() > 0){
												data.setIstodotgol(transitions.get(0).getIstodotgol());
											}
											else{
												data.setIstodotgol(false);
											}
											dao.PeaceCrud(data, "DataMinPlan16", "save", (long) 0, 0, 0, null);
										}
									}
								}
								else if (i==1){
									JSONObject response = jxml.selectQueryTags("//MainForm/Table_Body"+vars[i]+"/Row1/*");
									JSONArray data_array = (JSONArray) response.get("values");
									if (data_array.length() > 0){
										for(int j=0;j<(int)(data_array.length()/6);j++){
											DataMinPlan16 data = new DataMinPlan16();

											data.setData1(data_array.getString(j*6));
											data.setData2(data_array.getString(j*6+1));
											data.setData3(data_array.getString(j*6+3));
											if (!(data_array.getString(j*6+4).equalsIgnoreCase(null) || data_array.getString(j*6+4).length() == 0)){
												data.setData4(data_array.getDouble(j*6+4));
											}
											data.setData5(data_array.getString(j*6+5));
											data.setNoteid(noteid);
											data.setPlanid(planid);
											if (transitions != null && transitions.size() > 0){
												data.setIstodotgol(transitions.get(0).getIstodotgol());
											}
											else{
												data.setIstodotgol(false);
											}
											dao.PeaceCrud(data, "DataMinPlan16", "save", (long) 0, 0, 0, null);
										}
									}
									response = jxml.selectQueryTags("//MainForm/Table_Body"+vars[i]+"/Row2/*");
									data_array = (JSONArray) response.get("values");
									if (data_array.length() > 0){
										for(int j=0;j<(int)(data_array.length()/5);j++){
											DataMinPlan16 data = new DataMinPlan16();

											data.setData1(data_array.getString(j*5));
											data.setData2(data_array.getString(j*5+1));
											data.setData3(data_array.getString(j*5+2));
											if (!(data_array.getString(j*5+3).equalsIgnoreCase(null) || data_array.getString(j*5+3).length() == 0)){
												data.setData4(data_array.getDouble(j*5+3));
											}
											data.setData5(data_array.getString(j*5+4));
											data.setNoteid(noteid);
											data.setPlanid(planid);
											if (transitions != null && transitions.size() > 0){
												data.setIstodotgol(transitions.get(0).getIstodotgol());
											}
											else{
												data.setIstodotgol(false);
											}
											dao.PeaceCrud(data, "DataMinPlan16", "save", (long) 0, 0, 0, null);
										}
									}
								}
								else if (i==3){
									String[] types = {"11","111","112","113","114"};
									for(int i1 = 0; i1< types.length;i1++){
										JSONObject response = jxml.selectQueryTags("//MainForm/Table_Body"+vars[i]+"/Row"+types[i1]+"/*");
										JSONArray data_array = (JSONArray) response.get("values");
										if (types[i1].equalsIgnoreCase("11")){
											if (data_array.length() > 0){
												for(int j=0;j<(int)(data_array.length()/6);j++){
													DataMinPlan16 data = new DataMinPlan16();

													data.setData1(data_array.getString(j*6));
													data.setData2(data_array.getString(j*6+1));
													data.setData3(data_array.getString(j*6+3));
													if (!(data_array.getString(j*6+4).equalsIgnoreCase(null) || data_array.getString(j*6+4).length() == 0)){
														data.setData4(data_array.getDouble(j*6+4));
													}
													data.setData5(data_array.getString(j*6+5));
													data.setNoteid(noteid);
													data.setPlanid(planid);
													if (transitions != null && transitions.size() > 0){
														data.setIstodotgol(transitions.get(0).getIstodotgol());
													}
													else{
														data.setIstodotgol(false);
													}
													dao.PeaceCrud(data, "DataMinPlan16", "save", (long) 0, 0, 0, null);
												}
											}
										}
										else{
											if (data_array.length() > 0){
												for(int j=0;j<(int)(data_array.length()/5);j++){
													DataMinPlan16 data = new DataMinPlan16();

													data.setData1(data_array.getString(j*5));
													data.setData2(data_array.getString(j*5+1));
													data.setData3(data_array.getString(j*5+2));
													if (!(data_array.getString(j*5+3).equalsIgnoreCase(null) || data_array.getString(j*5+3).length() == 0)){
														data.setData4(data_array.getDouble(j*5+3));
													}
													data.setData5(data_array.getString(j*5+4));
													data.setNoteid(noteid);
													data.setPlanid(planid);
													if (transitions != null && transitions.size() > 0){
														data.setIstodotgol(transitions.get(0).getIstodotgol());
													}
													else{
														data.setIstodotgol(false);
													}
													dao.PeaceCrud(data, "DataMinPlan16", "save", (long) 0, 0, 0, null);
												}
											}
										}
									}
								}
							}
							/*for(int i=1;i<=23;i++){
							DataMinPlan16 data = new DataMinPlan16();
							for(int j=1;j<=5;j++){
					    		if (form.getField("Data"+i+"_"+j) != null && form.getField("Data"+i+"_"+j).length() > 0){
					    			if (j == 4){
					    				data.setField("data"+j, Double.parseDouble(form.getField("Data"+i+"_"+j)));
					    			}
					    			else{
					    				data.setField("data"+j, form.getField("Data"+i+"_"+j));
					    			}
					    		}
					    	}
					    	data.setNoteid(noteid);
					    	data.setPlanid(planid);
					    	dao.PeaceCrud(data, "DataMinPlan16", "save", (long) 0, 0, 0, null);
						}*/
						}
						if (noteid == 30){
							//dao.PeaceCrud(null, "DataMinPlan9 ", "delete", (long) planid, 0, 0, "planid");
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan9", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan9", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Sub_table1/Table3/Row1/*");
							JSONArray data_array = (JSONArray) response.get("values");
							if (data_array.length() > 0){
								for(int i=0;i<data_array.length()/12;i++){
									DataMinPlan9 data = new DataMinPlan9();
									for(int j=0;j<12;j++){
										if (!(data_array.getString(i*12 + j).equalsIgnoreCase(null) || data_array.getString(i*12 + j).length() == 0)){
											data.setField("data"+ (j+1), data_array.getString(i*12 + j));
										}
									}
									data.setType((long)1);
									data.setNoteid(noteid);
									data.setPlanid(planid);
									if (transitions != null && transitions.size() > 0){
										data.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										data.setIstodotgol(false);
									}
									dao.PeaceCrud(data, "DataMinPlan9", "save", (long) 0, 0, 0, null);
								}
							}

							response = jxml.selectQueryTags("//MainForm/Sub_table2/Table7/Row1/*");
							data_array = (JSONArray) response.get("values");
							if (data_array.length() > 0){
								for(int i=0;i<data_array.length()/7;i++){
									DataMinPlan9 data = new DataMinPlan9();
									for(int j=0;j<7;j++){
										if (!(data_array.getString(i*7 + j).equalsIgnoreCase(null) || data_array.getString(i*7 + j).length() == 0)){
											data.setField("data"+ (j+1), data_array.getString(i*7 + j));
										}
									}
									data.setType((long)2);
									data.setNoteid(noteid);
									data.setPlanid(planid);
									if (transitions != null && transitions.size() > 0){
										data.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										data.setIstodotgol(false);
									}
									dao.PeaceCrud(data, "DataMinPlan9", "save", (long) 0, 0, 0, null);
								}
							}
							response = jxml.selectQueryTags("//MainForm/Table8/Row1/*");
							data_array = (JSONArray) response.get("values");
							if (data_array.length() > 0){
								for(int i=0;i<data_array.length()/5;i++){
									DataMinPlan9 data = new DataMinPlan9();
									for(int j=0;j<5;j++){
										if (!(data_array.getString(i*5 + j).equalsIgnoreCase(null) || data_array.getString(i*5 + j).length() == 0)){
											data.setField("data"+ (j+1), data_array.getString(i*5 + j));
										}
									}
									data.setType((long)3);
									data.setNoteid(noteid);
									data.setPlanid(planid);
									if (transitions != null && transitions.size() > 0){
										data.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										data.setIstodotgol(false);
									}
									dao.PeaceCrud(data, "DataMinPlan9", "save", (long) 0, 0, 0, null);
								}
							}
						}
						if (noteid == 36 || noteid == 180){
							//dao.PeaceCrud(null, "DataMinPlan17", "delete", (long) planid, 0, 0, "planid");
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan17", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan17", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							String[] rowids = {"1","11","111","112","113","114","12","121","122","123","124","2","21","22","3","31","32","33","34","35","36","4","5"};
							for(int i=0; i<rowids.length;i++){
								if (i<rowids.length - 2){
									JSONObject response = jxml.selectQueryTags("//MainForm/Table_Body/Row"+rowids[i]+"/*");
									JSONArray data_array = (JSONArray) response.get("values");
									if (data_array.length() > 0){
										DataMinPlan17 data = new DataMinPlan17();
										data.setData1(data_array.getString(0));
										data.setData2(data_array.getString(1));
										data.setData3(data_array.getString(2));
										data.setData4(data_array.getString(3));
										data.setData5(data_array.getString(4));
										data.setData6(data_array.getString(5));
										data.setData7(data_array.getString(6));
										data.setData8(data_array.getString(7));
										data.setNoteid(noteid);
										data.setPlanid(planid);
										if (transitions != null && transitions.size() > 0){
											data.setIstodotgol(transitions.get(0).getIstodotgol());
										}
										else{
											data.setIstodotgol(false);
										}
										dao.PeaceCrud(data, "DataMinPlan17", "save", (long) 0, 0, 0, null);
									}
								}
								else if (i == rowids.length - 2){
									JSONObject response = jxml.selectQueryTags("//MainForm/Table_Body/Row"+rowids[i]+"/*");
									JSONArray data_array = (JSONArray) response.get("values");
									if (data_array.length() > 0){
										DataMinPlan17 data = new DataMinPlan17();
										for(int j = 0; j < data_array.length();j++){
											data.setField("data"+ (j+1), data_array.getString(j));
										}
										data.setNoteid(noteid);
										data.setPlanid(planid);
										if (transitions != null && transitions.size() > 0){
											data.setIstodotgol(transitions.get(0).getIstodotgol());
										}
										else{
											data.setIstodotgol(false);
										}
										dao.PeaceCrud(data, "DataMinPlan17", "save", (long) 0, 0, 0, null);
									}
								}
								else if (i == rowids.length - 1){
									JSONObject response = jxml.selectQueryTags("//MainForm/Table_Body/Row"+rowids[i]+"/*");
									JSONArray data_array = (JSONArray) response.get("values");
									if (data_array.length() > 0){
										for(int j = 0; j < (int)(data_array.length()/6);j++){
											DataMinPlan17 data = new DataMinPlan17();
											for(int j1 = 0; j1<6; j1++){
												data.setField("data"+ (j1+1), data_array.getString(j*6+j1));
											}
											data.setNoteid(noteid);
											data.setPlanid(planid);
											if (transitions != null && transitions.size() > 0){
												data.setIstodotgol(transitions.get(0).getIstodotgol());
											}
											else{
												data.setIstodotgol(false);
											}
											dao.PeaceCrud(data, "DataMinPlan17", "save", (long) 0, 0, 0, null);
										}

									}
								}
							}
							/*for(int i=1;i<=23;i++){
							DataMinPlan17 data = new DataMinPlan17();
							for(int j=1;j<=8;j++){
					    		if (form.getField("Data"+i+"_"+j) != null && form.getField("Data"+i+"_"+j).length() > 0){
					    			if (j>=4 && j<=7){
					    				data.setField("data"+j, Double.parseDouble(form.getField("Data"+i+"_"+j)));
					    			}
					    			else{
					    				data.setField("data"+j, form.getField("Data"+i+"_"+j));
					    			}
					    		}
							}
							data.setNoteid(noteid);
					    	data.setPlanid(planid);
					    	data.setLicnum(form.getField("LicNum"));
					    	data.setMintype(form.getField("MinType"));
					    	dao.PeaceCrud(data, "DataMinPlan17", "save", (long) 0, 0, 0, null);
						}*/
						}
						if (noteid == 49){
							dao.PeaceCrud(null, "DataGeoReport3 ", "delete", (long) planid, 0, 0, "planid");
							JSONObject response = jxml.selectQueryTags("//MainForm/Sub_tsoonog/Tsoonog/Row1/*");
							JSONArray data1_array = (JSONArray) response.get("values");
							for(int i=0;i<(int)(data1_array.length()/12);i++){
								DataGeoReport3 data = new DataGeoReport3();
								if (!(data1_array.getString(i*12).equalsIgnoreCase(null) || data1_array.getString(i*12).length() == 0)){
									data.setDataIndex(data1_array.getLong(i*12));
								}
								data.setData1(data1_array.getString(i*12+1));
								for(int j=2;j<=9;j++){
									if (!(data1_array.getString(i*12+j).equalsIgnoreCase(null) || data1_array.getString(i*12+j).length() == 0)){
										data.setField("data"+j, data1_array.getDouble(i*12+j));
									}
								}
								data.setData11(data1_array.getString(i*12+10));
								data.setData12(data1_array.getString(i*12+11));
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("Tsoonog");
								System.out.println(data.toString());
								dao.PeaceCrud(data, "DataGeoReport3", "save", (long) 0, 0, 0, null);
							}

							response = jxml.selectQueryTags("//MainForm/Sub_subag/Subag/Row1/*");
							data1_array = (JSONArray) response.get("values");
							for(int i=0;i<(int)(data1_array.length()/13);i++){
								DataGeoReport3 data = new DataGeoReport3();
								if (!(data1_array.getString(i*13).equalsIgnoreCase(null) || data1_array.getString(i*13).length() == 0)){
									data.setDataIndex(data1_array.getLong(i*13));
								}

								for(int j=1;j<=12;j++){
									if (!(data1_array.getString(i*13+j).equalsIgnoreCase(null) || data1_array.getString(i*13+j).length() == 0)){
										if (j == 1 || j == 11 || j == 12){
											data.setField("data"+j, data1_array.getString(i*13+j));
										}
										else if (j == 8 && data1_array.get(i*13+j) instanceof Double){
											data.setField("data"+j, data1_array.getDouble(i*13+j));
										}
										else{
											data.setField("data"+j, data1_array.getDouble(i*13+j));
										}

									}
								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("Subag");
								System.out.println(data.toString());
								dao.PeaceCrud(data, "DataGeoReport3", "save", (long) 0, 0, 0, null);
							}

							response = jxml.selectQueryTags("//MainForm/Sub_shurf/Shurf/Row1/*");
							data1_array = (JSONArray) response.get("values");
							for(int i=0;i<(int)(data1_array.length()/12);i++){
								DataGeoReport3 data = new DataGeoReport3();
								if (!(data1_array.getString(i*12).equalsIgnoreCase(null) || data1_array.getString(i*12).length() == 0)){
									data.setDataIndex(data1_array.getLong(i*12));
								}
								data.setData1(data1_array.getString(i*12+1));
								for(int j=2;j<=10;j++){
									if (!(data1_array.getString(i*12+j).equalsIgnoreCase(null) || data1_array.getString(i*12+j).length() == 0)){
										data.setField("data"+j, data1_array.getDouble(i*12+j));
									}
								}
								data.setData11(data1_array.getString(i*12+10));
								data.setData12(data1_array.getString(i*12+11));
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("Shurf");
								//System.out.println(data.toString());
								dao.PeaceCrud(data, "DataGeoReport3", "save", (long) 0, 0, 0, null);
							}

							String[] responseStr = jxml.selectQuery("//MainForm/Sub_tsoonog/Tsoonog_footer/HeaderRow3/Sum1/text()");
							if (responseStr.length > 0){
								DataGeoReport3 data = new DataGeoReport3();
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("Tsoonog_total");
								data.setData8(Double.parseDouble(responseStr[0]));
								dao.PeaceCrud(data, "DataGeoReport3", "save", (long) 0, 0, 0, null);
							}

							responseStr = jxml.selectQuery("//MainForm/Sub_subag/Subag/Total/*/text()");

							if (responseStr.length > 0){
								DataGeoReport3 data = new DataGeoReport3();
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("Subag_total");
								
								data.setData8(Double.parseDouble(responseStr[0]));
								if (responseStr.length > 1){
									data.setData9(Double.parseDouble(responseStr[1]));
								}
								if (responseStr.length > 2){
									data.setData10(Double.parseDouble(responseStr[2]));
								}
								dao.PeaceCrud(data, "DataGeoReport3", "save", (long) 0, 0, 0, null);
							}
							
							responseStr = jxml.selectQuery("//MainForm/Sub_shurf/Shurf/Total/*/text()");

							if (responseStr.length > 0 && responseStr.length == 2){
								DataGeoReport3 data = new DataGeoReport3();
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setType("Shufr_total");
								data.setData8(Double.parseDouble(responseStr[0]));
								data.setData9(Double.parseDouble(responseStr[1]));
								dao.PeaceCrud(data, "DataGeoReport3", "save", (long) 0, 0, 0, null);
							}
						}
						if (noteid == 50){
							dao.PeaceCrud(null, "DataGeoReport4 ", "delete", (long) planid, 0, 0, "planid");
							JSONObject response = jxml.selectQueryTags("//MainForm/Sub_table/Table1/Row1/Table2/Row1/*");
							JSONArray data1_array = (JSONArray) response.get("values");
							for(int i=0;i<(int)(data1_array.length()/6);i++){
								DataGeoReport4 data = new DataGeoReport4();
								for(int j=0;j<6;j++){
									data.setField("data"+(j+1), data1_array.getString(i*6+j));
								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								dao.PeaceCrud(data, "DataGeoReport4", "save", (long) 0, 0, 0, null);
							}
						}
						if (noteid == 51){
							dao.PeaceCrud(null, "DataGeoReport05 ", "delete", (long) planid, 0, 0, "planid");
							String[] types = {"Ord/Sub_ord/Table_ord", "Ilrel/Sub_ilrel/Table_ilrel","Erdes/Sub_erdes/Table_erdes","Element/Sub_element/Table_element"};
							for(int i1=0;i1<types.length;i1++){
								JSONObject response = jxml.selectQueryTags("//MainForm/"+types[i1]+"/Row1/*");
								JSONArray data1_array = (JSONArray) response.get("values");
								for(int i=0;i<(int)(data1_array.length()/16);i++){
									DataGeoReport05 data = new DataGeoReport05();
									if (!(data1_array.getString(i*16).equalsIgnoreCase(null) || data1_array.getString(i*16).length() == 0)){
										data.setDataIndex(data1_array.getLong(i*16));
									}
									for(int j=1;j<=15;j++){
										if (!(data1_array.getString(i*16+j).equalsIgnoreCase(null) || data1_array.getString(i*16+j).length() == 0)){
											if ((j>=3) && (j<=13)){
												data.setField("data"+j, data1_array.getDouble(i*16+j));
											}
											else{
												data.setField("data"+j, data1_array.getString(i*16+j));
											}
										}

									}
									data.setNoteid(noteid);
									data.setPlanid(planid);
									data.setType(String.valueOf(i1));
									dao.PeaceCrud(data, "DataGeoReport05", "save", (long) 0, 0, 0, null);
								}
							}
						}
						
						if (noteid == 54){
							dao.PeaceCrud(null, "DataGeoReport8", "delete", (long) planid, 0, 0, "planid");
							for(int i=1;i<=4;i++){
								JSONObject response = jxml.selectQueryTags("//MainForm/Table1/Row"+i+"/*");
								JSONArray data1_array = (JSONArray) response.get("values");
								if (data1_array.length() > 0){
									DataGeoReport8 data = new DataGeoReport8();
									
									for(int j=1;j<=8;j++){
										if (!(data1_array.getString(j-1).equalsIgnoreCase(null) || data1_array.getString(j-1).length() == 0)){
											if (j<3){
												data.setField("data"+j, data1_array.getString(j-1));
											}
											else{
												data.setField("data"+j, data1_array.getDouble(j-1));
											}
										}
									}
									data.setNoteid(noteid);
									data.setPlanid(planid);
									dao.PeaceCrud(data, "DataGeoReport8", "save", (long) 0, 0, 0, null);
								}
							}
						}
						
						if (noteid == 55){
							dao.PeaceCrud(null, "DataGeoReport9", "delete", (long) planid, 0, 0, "planid");
							for(int i=1;i<=3;i++){
								JSONObject response = jxml.selectQueryTags("//MainForm/Table3/Row"+i+"/*");
								JSONArray data1_array = (JSONArray) response.get("values");
								if (data1_array.length() > 0){
									System.out.println(data1_array.toString());
									DataGeoReport9 data = new DataGeoReport9();
									for(int j=1;j<=7;j++){
										if (!(data1_array.getString(j-1).equalsIgnoreCase(null) || data1_array.getString(j-1).length() == 0)){
											data.setField("data"+j, data1_array.getString(j-1));
										}
									}
									data.setNoteid(noteid);
									data.setPlanid(planid);
									System.out.println(data.toString());
									dao.PeaceCrud(data, "DataGeoReport9", "save", (long) 0, 0, 0, null);
								}
							}
						}
						
						if (noteid == 56){
							dao.PeaceCrud(null, "DataGeoReport10", "delete", (long) planid, 0, 0, "planid");
							for(int i=1;i<=8;i++){
								JSONObject response = jxml.selectQueryTags("//MainForm/Table2/Row"+i+"/*");
								JSONArray data1_array = (JSONArray) response.get("values");
								if (data1_array.length() > 0){
									DataGeoReport10 data = new DataGeoReport10();
									for(int j=1;j<=6;j++){
										if (!(data1_array.getString(j-1).equalsIgnoreCase(null) || data1_array.getString(j-1).length() == 0)){
											if (j<3){
												data.setField("data"+j, data1_array.getString(j-1));
											}
											else{
												data.setField("data"+j, data1_array.getDouble(j-1));
											}
										}
									}
									data.setNoteid(noteid);
									data.setPlanid(planid);
									dao.PeaceCrud(data, "DataGeoReport10", "save", (long) 0, 0, 0, null);
								}
							}
						}
						
						if (noteid == 52){
							dao.PeaceCrud(null, "DataGeoReport6 ", "delete", (long) planid, 0, 0, "planid");
							JSONObject response = jxml.selectQueryTags("//MainForm/Sub_table/Table1/Row1/Table2/Row1/*");
							JSONArray data1_array = (JSONArray) response.get("values");
							for(int i=0;i<(int)(data1_array.length()/6);i++){
								DataGeoReport6 data = new DataGeoReport6();
								for(int j=0;j<6;j++){
									if (!(data1_array.getString(i*6+j).equalsIgnoreCase(null) || data1_array.getString(i*6+j).length() == 0)){
										data.setField("data"+(j+1), data1_array.getString(i*6+j));
									}

								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								dao.PeaceCrud(data, "DataGeoReport6", "save", (long) 0, 0, 0, null);
							}
						}
						if (noteid == 347){
							dao.PeaceCrud(null, "DataGeoReport07 ", "delete", (long) planid, 0, 0, "planid");
							String[] types = {"Sub_subag", "Sub_shurf","Sub_tsoonog","Sub_busad"};
							for(int i1=0;i1<types.length;i1++){
								JSONObject response = jxml.selectQueryTags("//MainForm/Maintable/Group" + (i1+1) + "/"+types[i1]+"/Table5/Row1/*");
								JSONArray data1_array = (JSONArray) response.get("values");
								for(int i=0;i<(int)(data1_array.length()/16);i++){
									DataGeoReport07 data = new DataGeoReport07();
									if (!(data1_array.getString(i*16).equalsIgnoreCase(null) || data1_array.getString(i*16).length() == 0)){
										data.setDataIndex(data1_array.getLong(i*16));
									}
									for(int j=1;j<=15;j++){
										if (!(data1_array.getString(i*16+j).equalsIgnoreCase(null) || data1_array.getString(i*16+j).length() == 0)){
											if (((j>=2) && (j<=10)) || (j == 15)){
												data.setField("data"+j, data1_array.getDouble(i*16+j));
											}
											else{
												data.setField("data"+j, data1_array.getString(i*16+j));
											}
										}

									}
									data.setNoteid(noteid);
									data.setPlanid(planid);
									data.setType(String.valueOf(i1));
									dao.PeaceCrud(data, "DataGeoReport07", "save", (long) 0, 0, 0, null);
								}
							}
						}
						if (noteid == 184 || noteid == 185){
							//dao.PeaceCrud(null, "DataMinPlan1", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan1", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan1", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
                            Long dataIndex;
							for(int t=0;t<=3;t++){
								JSONObject response = jxml.selectQueryTags("//MainForm/Sub_table2"+((t==0) ? "" : t)+"/Table2/Row1/*");
								JSONArray data1_array = (JSONArray) response.get("values");
								System.out.println(data1_array.toString());;
								DataMinPlan1 data = new DataMinPlan1();
								data.setNoteid(noteid);
								data.setPlanid(planid);
								data.setDataIndex(data1_array.getLong(0));
								dataIndex = data.getDataIndex();
								data.setData1(data1_array.getString(1));
								if (!(data1_array.getString(2).equalsIgnoreCase(null) || data1_array.getString(2).length() == 0)){
									data.setData2(data1_array.getString(2));
								}
								if(!data1_array.isNull(3) && !(data1_array.getString(3).equalsIgnoreCase(null) || data1_array.getString(3).length() == 0)){
									data.setData3(data1_array.getDouble(3));
								}
								
								if (noteid == 184 && !(data1_array.getString(4).equalsIgnoreCase(null) || data1_array.getString(4).length() == 0)){
									data.setData5(data1_array.getString(4));
								}
								if (transitions != null && transitions.size() > 0){
									data.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									data.setIstodotgol(false);
								}

								dao.PeaceCrud(data, "DataMinPlan1", "save", (long) 0, 0, 0, null);

								if (t == 1){
									response = jxml.selectQueryTags("//MainForm/Sub_table31/Table3/Row1/Table3/Row1/*");
									data1_array = (JSONArray) response.get("values");
									for(int i=0;i<(data1_array.length()/6);i++){
										data = new DataMinPlan1();
										data.setNoteid(noteid);
										data.setPlanid(planid);
										data.setDataIndex(dataIndex);
										if (transitions != null && transitions.size() > 0){
											data.setIstodotgol(transitions.get(0).getIstodotgol());
										}
										else{
											data.setIstodotgol(false);
										}
										if (!(data1_array.getString(i*6).equalsIgnoreCase(null) || data1_array.getString(i*6).length() == 0)){
											//data.setData4(data1_array.getLong(i*6));
											data.setData4((long) Double.parseDouble(data1_array.getString(i*6).replaceAll(",", "")));
										}
										data.setData5(data1_array.getString(i*6+1));
										data.setData6(data1_array.getString(i*6+2));
										if (!(data1_array.getString(i*6+3).equalsIgnoreCase(null) || data1_array.getString(i*6+3).length() == 0)){
											data.setData7(data1_array.getDouble(i*6+3));
										}
										data.setData8(data1_array.getString(i*6+4));
										if (!(data1_array.getString(i*6+5).equalsIgnoreCase(null) || data1_array.getString(i*6+5).length() == 0)){
											data.setData9(data1_array.getDouble(i*6+5));
										}
										dao.PeaceCrud(data, "DataMinPlan1", "save", (long) 0, 0, 0, null);
									}
								}
								else{
									if (noteid == 185){
										response = jxml.selectQueryTags("//MainForm/Sub_table3"+((t==0) ? "" : t)+"/Table3/Row1/*");
										JSONArray Sub_table3 = (JSONArray) response.get("values");
										for(int j=0;j<Sub_table3. length()/5;j++){
											response = jxml.selectQueryTags("//MainForm/Sub_table3/Table3/Row1["+(j+1)+"]/Table3/Row1/*");
											data1_array = (JSONArray) response.get("values");
											for(int i=0;i<(data1_array.length()/6);i++){
												data = new DataMinPlan1();
												data.setNoteid(noteid);
												data.setPlanid(planid);
                                                data.setDataIndex(dataIndex);
												if (transitions != null && transitions.size() > 0){
													data.setIstodotgol(transitions.get(0).getIstodotgol());
												}
												else{
													data.setIstodotgol(false);
												}
												//data.setDataIndex(Sub_table3.getLong(j*5));
												data.setData1(Sub_table3.getString(j*5+1));
												if (!(Sub_table3.getString(j*5+2).equalsIgnoreCase(null) || Sub_table3.getString(j*5+2).length() == 0)){
													data.setData2(Sub_table3.getString(j*5+2));
												}
												if (!(Sub_table3.getString(j*5+3).equalsIgnoreCase(null) || Sub_table3.getString(j*5+3).length() == 0)){
													data.setData3(Sub_table3.getDouble(j*5+3));
												}
												if (!(data1_array.getString(i*6).equalsIgnoreCase(null) || data1_array.getString(i*6).length() == 0)){
													data.setData4((long) Double.parseDouble(data1_array.getString(i*6).replaceAll(",", "")));
												}
												data.setData5(data1_array.getString(i*6+1));
												data.setData6(data1_array.getString(i*6+2));
												if (!(data1_array.getString(i*6+3).equalsIgnoreCase(null) || data1_array.getString(i*6+3).length() == 0)){
													data.setData7(data1_array.getDouble(i*6+3));
												}
												data.setData8(data1_array.getString(i*6+4));
												if (!(data1_array.getString(i*6+5).equalsIgnoreCase(null) || data1_array.getString(i*6+5).length() == 0)){
													data.setData9(data1_array.getDouble(i*6+5));
												}
												dao.PeaceCrud(data, "DataMinPlan1", "save", (long) 0, 0, 0, null);
											}
										}

									}
									if (noteid == 184){
										response = jxml.selectQueryTags("//MainForm/Sub_table3"+((t==0) ? "" : t)+"/Table3/Row1/*");
										JSONArray Sub_table3 = (JSONArray) response.get("values");
										JSONObject count = jxml.selectQueryTags("//MainForm/Sub_table3"+((t==0) ? "" : t)+"/Table3/Row1[1]/*");
										JSONArray countdata = (JSONArray) count.get("values");
										int c = countdata.length();
										System.out.println(c + "\n" + Sub_table3.toString());
										for(int j=0;c>0 && j<Sub_table3. length()/c;j++){
											/*response = jxml.selectQueryTags("//MainForm/Sub_table3/Table3/Row1["+(j+1)+"]/Table3/Row1/*");
											data1_array = (JSONArray) response.get("values");*/
											data = new DataMinPlan1();
											data.setNoteid(noteid);
											data.setPlanid(planid);
                                            data.setDataIndex(dataIndex);
											if (transitions != null && transitions.size() > 0){
												data.setIstodotgol(transitions.get(0).getIstodotgol());
											}
											else{
												data.setIstodotgol(false);
											}
											//data.setDataIndex(Sub_table3.getLong(j*5));
											data.setData1(Sub_table3.getString(j*c+1));
											if (!(Sub_table3.getString(j*c+2).equalsIgnoreCase(null) || Sub_table3.getString(j*c+2).length() == 0)){
												data.setData2(Sub_table3.getString(j*c+2));
											}
											if (!(Sub_table3.getString(j*c+3).equalsIgnoreCase(null) || Sub_table3.getString(j*c+3).length() == 0)){
												data.setData3(Sub_table3.getDouble(j*c+3));
											}
											
											if ((c>4) && !(Sub_table3.getString(j*c+4).equalsIgnoreCase(null) || Sub_table3.getString(j*c+4).length() == 0)){
												data.setData5(Sub_table3.getString(j*c+4));
											}
											
											dao.PeaceCrud(data, "DataMinPlan1", "save", (long) 0, 0, 0, null);
										}
									}
								}

							}

							JSONObject response = jxml.selectQueryTags("//MainForm/Table_footer/Table_Footer/HeaderRow/*");
							JSONArray data1_array = (JSONArray) response.get("values");
							DataMinPlan1 data = new DataMinPlan1();
							data.setNoteid(noteid);
							data.setPlanid(planid);
							data.setDataIndex(data1_array.getLong(0));
							data.setData1(data1_array.getString(1));
							data.setData5(data1_array.getString(2));
							if (transitions != null && transitions.size() > 0){
								data.setIstodotgol(transitions.get(0).getIstodotgol());
							}
							else{
								data.setIstodotgol(false);
							}
							dao.PeaceCrud(data, "DataMinPlan1", "save", (long) 0, 0, 0, null);
						}

						if (noteid == 275){

							dao.PeaceCrud(null, "DataCoalForm1_1 ", "delete", (long) planid, 0, 0, "planid");

							String[] dirs = {"Sub_table3/Table3","Sub_table2/Table2","Sub_table21/Table2","Sub_table22/Table2","Sub_table32/Table3","Sub_table23/Table2","Sub_table33/Table3"};
							for(int j=0;j<dirs.length;j++){
								JSONObject response = jxml.selectQueryTags("//MainForm/"+dirs[j]+"/Row1/*");
								JSONArray data1_array = (JSONArray) response.get("values");
								if (j==0){

									if (data1_array.length() > 0){
										for(int i=0;i<data1_array.length()/4;i++){
											DataCoalForm1_1 data = new DataCoalForm1_1();
											if (!(data1_array.getString(i*4).equalsIgnoreCase(null) || data1_array.getString(i*4).length() == 0)){
											    if (data1_array.getString(i*4).indexOf(".")>-1){
                                                    data.setDataIndex((long)data1_array.getDouble(i*4));
                                                }
                                                else{
                                                    data.setDataIndex(data1_array.getLong(i*4));
                                                }
											}
											data.setData1(data1_array.getString(i*4+1));
											data.setData2(data1_array.getString(i*4+2));
											if (!(data1_array.getString(i*4+3).equalsIgnoreCase(null) || data1_array.getString(i*4+3).length() == 0)){
												data.setData3(data1_array.getDouble(i*4+3));
											}
											//data.setData4(data1_array.getString(4));
											data.setNoteid(noteid);
											data.setPlanid(planid);
											dao.PeaceCrud(data, "DataCoalForm1_1", "save", (long) 0, 0, 0, null);
										}
									}
								}
								else{
									if (data1_array.length() > 0){
										for(int i=0;i<data1_array.length()/5;i++){
											DataCoalForm1_1 data = new DataCoalForm1_1();
											if (!(data1_array.getString(i*5).equalsIgnoreCase(null) || data1_array.getString(i*5).length() == 0)){
                                                if (data1_array.getString(i*5).indexOf(".")>-1){
                                                    data.setDataIndex((long)data1_array.getDouble(i*5));
                                                }
                                                else{
                                                    data.setDataIndex(data1_array.getLong(i*5));
                                                }

											}
											data.setData1(data1_array.getString(i*5+1));
											data.setData2(data1_array.getString(i*5+2));
											if (!(data1_array.getString(i*5+3).equalsIgnoreCase(null) || data1_array.getString(i*5+3).length() == 0)){
												data.setData3(data1_array.getDouble(i*5+3));
											}
											data.setData4(data1_array.getString(i*5+4));
											data.setNoteid(noteid);
											data.setPlanid(planid);
											dao.PeaceCrud(data, "DataCoalForm1_1", "save", (long) 0, 0, 0, null);
										}
									}
								}
							}
						}
						if (noteid == 126){
							dao.PeaceCrud(null, "DataGeoPlan1 ", "delete", (long) planid, 0, 0, "planid");
							for(int i=1;i<=18;i++){
								if ((i == 2) || (i == 3) || (i >= 12 && i <= 17)){
									JSONObject response = jxml.selectQueryTags("//MainForm/G"+i+"/Row1/*");
									JSONArray data1_array = (JSONArray) response.get("values");
									DataGeoPlan1 data = new DataGeoPlan1();
									data.setNoteid(noteid);
									data.setPlanid(planid);
									for(int j=0;j<6;j++){
										if (j == 0){
											data.setDataIndex(data1_array.getInt(j));
										}
										else if (j == 1 || j == 2){
											data.setField("data" + j, data1_array.getString(j));
										}
										else{
											if (!(data1_array.getString(j).equalsIgnoreCase(null) || data1_array.getString(j).length() == 0)){
												data.setField("data" + j, data1_array.getDouble(j));
											}
										}
									}
									data.setType(String.valueOf(i));
									dao.PeaceCrud(data, "DataGeoPlan1", "save", (long) 0, 0, 0, null);
								}
								else{
									JSONObject response = jxml.selectQueryTags("//MainForm/G"+i+"/Group1/Table1/Row1/*");
									JSONArray data1_array = (JSONArray) response.get("values");
									DataGeoPlan1 data = new DataGeoPlan1();
									data.setNoteid(noteid);
									data.setPlanid(planid);
									for(int j=0;j<6;j++){
										if (j == 0){
											data.setDataIndex(data1_array.getInt(j));
										}
										else if (j == 1 || j == 2){
											data.setField("data" + j, data1_array.getString(j));
										}
										else{
											if (!(data1_array.getString(j).equalsIgnoreCase(null) || data1_array.getString(j).length() == 0)){
												data.setField("data" + j, data1_array.getDouble(j));
											}
										}
									}
									data.setType(String.valueOf(i));
									dao.PeaceCrud(data, "DataGeoPlan1", "save", (long) 0, 0, 0, null);

									response = jxml.selectQueryTags("//MainForm/G"+i+"/Group1/Table1/Row2/*");
									data1_array = (JSONArray) response.get("values");
									if (data1_array.length() > 0){
										data = new DataGeoPlan1();
										data.setNoteid(noteid);
										data.setPlanid(planid);
										for(int j=0;j<6;j++){
											if (j == 0){
												data.setDataIndex(data1_array.getInt(j));
											}
											else if (j == 1 || j == 2){
												data.setField("data" + j, data1_array.getString(j));
											}
											else{
												if (!(data1_array.getString(j).equalsIgnoreCase(null) || data1_array.getString(j).length() == 0)){
													data.setField("data" + j, data1_array.getDouble(j));
												}
											}
										}
										data.setType(String.valueOf(i));
										dao.PeaceCrud(data, "DataGeoPlan1", "save", (long) 0, 0, 0, null);
									}

									response = jxml.selectQueryTags("//MainForm/G"+i+"/Group1/Table1/Row3/*");
									data1_array = (JSONArray) response.get("values");
									for(int j1=0;j1<(int)(data1_array.length()/6);j1++){
										data = new DataGeoPlan1();
										data.setNoteid(noteid);
										data.setPlanid(planid);
										data.setDataIndex(data1_array.getInt(j1*6));
										data.setData1(data1_array.getString(j1*6+1));
										data.setData2(data1_array.getString(j1*6+2));
										if (!(data1_array.getString(j1*6+3).equalsIgnoreCase(null) || data1_array.getString(j1*6+3).length() == 0)){
											data.setData3(data1_array.getDouble(j1*6+3));
										}
										if (!(data1_array.getString(j1*6+4).equalsIgnoreCase(null) || data1_array.getString(j1*6+4).length() == 0)){
											data.setData4(data1_array.getDouble(j1*6+4));
										}
										if (!(data1_array.getString(j1*6+5).equalsIgnoreCase(null) || data1_array.getString(j1*6+5).length() == 0)){
											data.setData5(data1_array.getDouble(j1*6+5));
										}
										data.setType(String.valueOf(i));
										dao.PeaceCrud(data, "DataGeoPlan1", "save", (long) 0, 0, 0, null);
									}

								}
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Total/Row1/*");
							JSONArray data1_array = (JSONArray) response.get("values");
							DataGeoPlan1 data = new DataGeoPlan1();
							data.setData1(data1_array.getString(1));
							if (!(data1_array.getString(data1_array.length()-1).equalsIgnoreCase(null) || data1_array.getString(data1_array.length()-1).length() == 0)){
								data.setData5(data1_array.getDouble(data1_array.length()-1));
							}
							data.setType("TOTAL");
							data.setNoteid(noteid);
							data.setPlanid(planid);
							dao.PeaceCrud(data, "DataGeoPlan1", "save", (long) 0, 0, 0, null);

							response = jxml.selectQueryTags("//MainForm/Total/Row2/*");
							data1_array = (JSONArray) response.get("values");
							data = new DataGeoPlan1();
							data.setData1(data1_array.getString(1));
							if (!(data1_array.getString(data1_array.length()-1).equalsIgnoreCase(null) || data1_array.getString(data1_array.length()-1).length() == 0)){
								data.setData5(data1_array.getDouble(data1_array.length()-1));
							}
							data.setType("ZARDAL");
							data.setNoteid(noteid);
							data.setPlanid(planid);
							dao.PeaceCrud(data, "DataGeoPlan1", "save", (long) 0, 0, 0, null);
						}
						if (noteid == 47){
							dao.PeaceCrud(null, "DataGeoReport1 ", "delete", (long) planid, 0, 0, "planid");
							for(int i=1;i<=18;i++){
								if ((i == 2) || (i == 3) || (i >= 12 && i <= 17)){
									JSONObject response = jxml.selectQueryTags("//MainForm/G"+i+"/Row1/*");
									JSONArray data1_array = (JSONArray) response.get("values");
									DataGeoReport1 data = new DataGeoReport1();
									data.setNoteid(noteid);
									data.setPlanid(planid);
									for(int j=0;j<8;j++){
										if (j == 0){
											data.setDataIndex(data1_array.getInt(j));
										}
										else if (j == 1 || j == 2){
											data.setField("data" + j, data1_array.getString(j));
										}
										else{
											if (!(data1_array.getString(j).equalsIgnoreCase(null) || data1_array.getString(j).length() == 0)){
												data.setField("data" + j, data1_array.getDouble(j));
											}
										}
									}
									data.setType(String.valueOf(i));
									dao.PeaceCrud(data, "DataGeoReport1", "save", (long) 0, 0, 0, null);
								}
								else{
									JSONObject response = jxml.selectQueryTags("//MainForm/G"+i+"/Group1/Table1/Row1/*");
									JSONArray data1_array = (JSONArray) response.get("values");
									DataGeoReport1 data = new DataGeoReport1();
									data.setNoteid(noteid);
									data.setPlanid(planid);
									for(int j=0;j<8;j++){
										if (j == 0){
											data.setDataIndex(data1_array.getInt(j));
										}
										else if (j == 1 || j == 2){
											data.setField("data" + j, data1_array.getString(j));
										}
										else{
											if (!(data1_array.getString(j).equalsIgnoreCase(null) || data1_array.getString(j).length() == 0)){
												data.setField("data" + j, Double.parseDouble(data1_array.getString(j).replace(",", "")));
											}
										}
									}
									data.setType(String.valueOf(i));
									dao.PeaceCrud(data, "DataGeoReport1", "save", (long) 0, 0, 0, null);

									response = jxml.selectQueryTags("//MainForm/G"+i+"/Group1/Table1/Row2/*");
									data1_array = (JSONArray) response.get("values");
									if (data1_array.length() > 0){
										data = new DataGeoReport1();
										data.setNoteid(noteid);
										data.setPlanid(planid);
										for(int j=0;j<8;j++){
											if (j == 0){
												data.setDataIndex(data1_array.getInt(j));
											}
											else if (j == 1 || j == 2){
												data.setField("data" + j, data1_array.getString(j));
											}
											else{
												if (!(data1_array.getString(j).equalsIgnoreCase(null) || data1_array.getString(j).length() == 0)){
													data.setField("data" + j, data1_array.getDouble(j));
												}
											}
										}
										data.setType(String.valueOf(i));
										dao.PeaceCrud(data, "DataGeoReport1", "save", (long) 0, 0, 0, null);
									}

									response = jxml.selectQueryTags("//MainForm/G"+i+"/Group1/Table1/Row3/*");
									data1_array = (JSONArray) response.get("values");
									for(int j1=0;j1<(int)(data1_array.length()/8);j1++){
										data = new DataGeoReport1();
										data.setNoteid(noteid);
										data.setPlanid(planid);
										data.setDataIndex(data1_array.getInt(j1*8));
										data.setData1(data1_array.getString(j1*8+1));
										data.setData2(data1_array.getString(j1*8+2));
										if (!(data1_array.getString(j1*8+3).equalsIgnoreCase(null) || data1_array.getString(j1*8+3).length() == 0)){
											data.setData3(data1_array.getDouble(j1*8+3));
										}
										if (!(data1_array.getString(j1*8+4).equalsIgnoreCase(null) || data1_array.getString(j1*8+4).length() == 0)){
											data.setData4(data1_array.getDouble(j1*8+4));
										}
										if (!(data1_array.getString(j1*8+5).equalsIgnoreCase(null) || data1_array.getString(j1*8+5).length() == 0)){
											data.setData5(data1_array.getDouble(j1*8+5));
										}
										if (!(data1_array.getString(j1*8+6).equalsIgnoreCase(null) || data1_array.getString(j1*8+6).length() == 0)){
											data.setData6(data1_array.getDouble(j1*8+6));
										}
										if (!(data1_array.getString(j1*8+7).equalsIgnoreCase(null) || data1_array.getString(j1*8+7).length() == 0)){
											data.setData7(data1_array.getDouble(j1*8+7));
										}
										data.setType(String.valueOf(i));
										dao.PeaceCrud(data, "DataGeoReport1", "save", (long) 0, 0, 0, null);
									}

								}
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Total/Row1/*");
							JSONArray data1_array = (JSONArray) response.get("values");
							DataGeoReport1 data = new DataGeoReport1();
							data.setData1(data1_array.getString(0));
							if (!(data1_array.getString(1).equalsIgnoreCase(null) || data1_array.getString(1).length() == 0)){
								data.setData1(data1_array.getString(1));
							}
							if (!(data1_array.getString(3).equalsIgnoreCase(null) || data1_array.getString(3).length() == 0)){
								data.setData4(Double.parseDouble(data1_array.getString(3).replace(",", "")));
							}
							if (!(data1_array.getString(6).equalsIgnoreCase(null) || data1_array.getString(6).length() == 0)){
								data.setData7(Double.parseDouble(data1_array.getString(6).replace(",", "")));
							}
							data.setType("TOTAL");
							data.setNoteid(noteid);
							data.setPlanid(planid);
							dao.PeaceCrud(data, "DataGeoReport1", "save", (long) 0, 0, 0, null);

							response = jxml.selectQueryTags("//MainForm/Total/Row2/*");
							data1_array = (JSONArray) response.get("values");
							if (data1_array.length() == 8){
								data = new DataGeoReport1();
								if (!(data1_array.getString(1).equalsIgnoreCase(null) || data1_array.getString(1).length() == 0)){
									data.setData1(data1_array.getString(1));
								}
								if (!(data1_array.getString(2).equalsIgnoreCase(null) || data1_array.getString(2).length() == 0)){
									data.setData2(data1_array.getString(2));
								}
								if (!(data1_array.getString(4).equalsIgnoreCase(null) || data1_array.getString(4).length() == 0)){
									data.setData4(Double.parseDouble(data1_array.getString(4).replace(",", "")));
								}
								if (!(data1_array.getString(7).equalsIgnoreCase(null) || data1_array.getString(7).length() == 0)){
									data.setData7(Double.parseDouble(data1_array.getString(7).replace(",", "")));
								}
								data.setType("ZARDAL");
								data.setNoteid(noteid);
								data.setPlanid(planid);
								dao.PeaceCrud(data, "DataGeoReport1", "save", (long) 0, 0, 0, null);
							}
							
						}
						if (noteid == 183){
							//dao.PeaceCrud(null, "DataMinPlan4_1 ", "delete", (long) planid, 0, 0, "planid");
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan4_1", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan4_1", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							// === 1 ===
							JSONObject response = jxml.selectQueryTags("//MainForm/Sub_table1/Table1/Row1/*");
							JSONArray tags_array = (JSONArray) response.get("tags");
							JSONArray data1_array = (JSONArray) response.get("values");
							JSONArray parent_array = (JSONArray) response.get("parent");

							int size = (int) (tags_array.length() / 5);
							String[] delStrings = {"NumericField1","TextField1","TextField2","NumericField2","Table3","TextField3","NumericField3","TextField4","TextField5","Sub_product","DropDownList1","TextField6","NumericField5","Table4"};
							for(int i=0;i<size;i++){
								JSONObject data = new JSONObject();
								data.put("data1", data1_array.getString(i*5+1));
								data.put("data2", data1_array.getString(i*5+2));
								data.put("data3", data1_array.getString(i*5+3));
								response = jxml.selectQueryTags("//MainForm/Sub_table1/Table1/Row1[" + (i+1) + "]/Sub_1/Table2/Row1/*");
								tags_array = (JSONArray) response.get("tags");
								JSONArray values_array = (JSONArray) response.get("values");
								parent_array = (JSONArray) response.get("parent");
								JSONArray temp_data_value = new JSONArray();
								for(int j=0;j<tags_array.length();j++){
									if (java.util.Arrays.asList(delStrings).indexOf(tags_array.get(j)) == -1){
										temp_data_value.put(values_array.get(j));
									}
								}
								setDataMin4_1(temp_data_value, data, planid, noteid,Long.valueOf(1),transitions);

							}

							// === 2 ===
							response = jxml.selectQueryTags("//MainForm/Sub_table1/Table1/Row2/*");
							data1_array = (JSONArray) response.get("values");
							JSONObject data = new JSONObject();
							data.put("data1", data1_array.getString(1));
							data.put("data2", data1_array.getString(2));
							data.put("data3", data1_array.getString(3));
							response = jxml.selectQueryTags("//MainForm/Sub_table1/Table1/Row2/Sub_2/Table2/Row1/*");
							JSONArray values_array = (JSONArray) response.get("values");

							setDataMin4_1(values_array, data, planid, noteid,Long.valueOf(2),transitions);

							// === 3 ===
							response = jxml.selectQueryTags("//MainForm/Sub_table1/Table1/Row3/*");
							data1_array = (JSONArray) response.get("values");
							data = new JSONObject();
							data.put("data1", data1_array.getString(1));
							data.put("data2", data1_array.getString(2));
							data.put("data3", data1_array.getString(3));
							response = jxml.selectQueryTags("//MainForm/Sub_table1/Table1/Row3/Sub_3/Table2/Row1/*");
							values_array = (JSONArray) response.get("values");
							setDataMin4_1(values_array, data, planid, noteid,Long.valueOf(3),transitions);

							// === 4 ===
							response = jxml.selectQueryTags("//MainForm/Sub_table2/Table2/Row1/*");
							data1_array = (JSONArray) response.get("values");
							tags_array = (JSONArray) response.get("tags");
							/*for(int i=0;i<data1_array.length();i++){
							System.out.println(i + " =>\t" + tags_array.get(i) + "=" + data1_array.get(i));
						}*/
							if (data1_array.length() > 0){
								data = new JSONObject();
								data.put("data1", data1_array.getString(1));
								data.put("data2", data1_array.getString(2));
								data.put("data3", data1_array.getString(3));
								data.put("data10", data1_array.getString(5));
								data.put("data11", data1_array.getString(6));
								data.put("data12", data1_array.getString(7));
								data.put("data13", "100");
								data.put("data14", data1_array.getString(8));
							}


							response = jxml.selectQueryTags("//MainForm/Sub_table2/Table2/Row1/Table3/Row1/*");
							values_array = (JSONArray) response.get("values");
							tags_array = (JSONArray) response.get("tags");
							JSONArray temp_data_value = new JSONArray();
							for(int j=0;j<tags_array.length();j++){
								if (java.util.Arrays.asList(delStrings).indexOf(tags_array.get(j)) == -1){
									temp_data_value.put(values_array.get(j));
								}
							}
							/*for(int i=0;i<temp_data_value.length();i++){
							System.out.println(i + " =>\t" + temp_data_value.get(i));
						}*/
							setDataMin4_1(temp_data_value, data, planid, noteid,Long.valueOf(4),transitions);
							//=== 5 ===
							response = jxml.selectQueryTags("//MainForm/Sub_table3/Table3/Row1/*");
							tags_array = (JSONArray) response.get("tags");
							data1_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");

							size = (int) (tags_array.length() / 9);
							for(int i=0;i<size;i++){
								data = new JSONObject();
								data.put("data1", data1_array.getString(i*9+2));
								data.put("data2", data1_array.getString(i*9+3));
								data.put("data3", data1_array.getString(i*9+4));
								data.put("data10", data1_array.getString(i*9+6));
								data.put("data11", data1_array.getString(i*9+7));

								response = jxml.selectQueryTags("//MainForm/Sub_table3/Table3/Row1[" + (i+1) + "]/Table3/Row1/*");
								tags_array = (JSONArray) response.get("tags");
								values_array = (JSONArray) response.get("values");
								parent_array = (JSONArray) response.get("parent");
								for(int j=0;j<(int)(values_array.length()/6);j++){
									data.put("data4", values_array.getString(j*6));
									data.put("data5", values_array.getString(j*6+1));
									data.put("data6", values_array.getString(j*6+2));
									data.put("data7", values_array.getString(j*6+3));
									data.put("data8", values_array.getString(j*6+4));
									data.put("data9", values_array.getString(j*6+5));
									String[] response1 = jxml.selectQuery("//MainForm/Sub_table3/Table3/Row1[" + (i+1) + "]/Table4/Row1["+ (j+1) +"]/TextField7/text()");
									if (response1.length > 0){
										data.put("data12", response1[0]);
									}
									response1 = jxml.selectQuery("//MainForm/Sub_table3/Table3/Row1[" + (i+1) + "]/Table4/Row1["+ (j+1) +"]/NumericField7/text()");
									if (response1.length > 0){
										data.put("data13", response1[0]);
									}
									response1 = jxml.selectQuery("//MainForm/Sub_table3/Table3/Row1[" + (i+1) + "]/Table4/Row1["+ (j+1) +"]/TextField8/text()");
									if (response1.length > 0){
										data.put("data14", response1[0]);
									}
									setDataMin4_1(null, data, planid, noteid,Long.valueOf(5),transitions);
								}

							}
							// === 6 ===
							response = jxml.selectQueryTags("//MainForm/Sub_table4/Table4/Row1/*");
							tags_array = (JSONArray) response.get("tags");
							data1_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");

							if (data1_array.length() > 0){
								data = new JSONObject();
								data.put("data1", data1_array.getString(1));
								data.put("data2", data1_array.getString(2));
								data.put("data3", data1_array.getString(3));
								data.put("data10", data1_array.getString(6));
								data.put("data11", data1_array.getString(7));
							}

							response = jxml.selectQueryTags("//MainForm/Sub_table4/Table4/Row1/Table3/Row1/*");
							tags_array = (JSONArray) response.get("tags");
							values_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");
							for(int j=0;j<(int)(values_array.length()/6);j++){
								data.put("data4", values_array.getString(j*6));
								data.put("data5", values_array.getString(j*6+1));
								data.put("data6", values_array.getString(j*6+2));
								data.put("data7", values_array.getString(j*6+3));
								data.put("data8", values_array.getString(j*6+4));
								data.put("data9", values_array.getString(j*6+5));
								String[] response1 = jxml.selectQuery("//MainForm/Sub_table4/Table4/Row1/Table4/Row1["+ (j+1) +"]/TextField7/text()");
								if (response1.length > 0){
									data.put("data12", response1[0]);
								}
								response1 = jxml.selectQuery("//MainForm/Sub_table4/Table4/Row1/Table4/Row1["+ (j+1) +"]/NumericField7/text()");
								if (response1.length > 0){
									data.put("data13", response1[0]);
								}
								response1 = jxml.selectQuery("//MainForm/Sub_table4/Table4/Row1/Table4/Row1["+ (j+1) +"]/TextField8/text()");
								if (response1.length > 0){
									data.put("data14", response1[0]);
								}
								System.out.println(data.toString());
								setDataMin4_1(null, data, planid, noteid,Long.valueOf(6),transitions);
							}

						}

						if (noteid == 25){
							//dao.PeaceCrud(null, "DataMinPlan4_2 ", "delete", (long) planid, 0, 0, "planid");
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan4_2", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan4_2", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							for(int i=1;i<=4;i++){
								JSONObject response = jxml.selectQueryTags("//MainForm/Table"+i+"/*/*");
								JSONArray tags_array = (JSONArray) response.get("tags");
								JSONArray values_array = (JSONArray) response.get("values");
								JSONArray parent_array = (JSONArray) response.get("parent");

								if (i==2){
									if (values_array.length() > 0){
										for(int j=0;j<(int)(values_array.length()/6);j++){
											DataMinPlan4_2 data = new DataMinPlan4_2();
											data.setDataindex(values_array.getString(j*6));
											data.setData1(values_array.getString(j*6+1));
											data.setData2(values_array.getString(j*6+2));
											data.setData3(values_array.getString(j*6+3));
											data.setData4(values_array.getString(j*6+4));
											data.setData5("100");
											data.setData6(values_array.getString(j*6+5));
											data.setType((long)i);
											data.setPlanid(planid);
											data.setNoteid(noteid);
											if (transitions != null && transitions.size() > 0){
												data.setIstodotgol(transitions.get(0).getIstodotgol());
											}
											else{
												data.setIstodotgol(false);
											}
											dao.PeaceCrud(data, "DataMinPlan4_2", "save", (long) 0, 0, 0, null);
										}
									}
								}
								else if (i==3){
									if (values_array.length() > 0){
										for(int j=0;j<(int)(values_array.length()/8);j++){
											DataMinPlan4_2 data = new DataMinPlan4_2();
											data.setDataindex(values_array.getString(j*8+1));
											data.setData1(values_array.getString(j*8+2));
											data.setData2(values_array.getString(j*8+3));
											data.setData3(values_array.getString(j*8+4));
											data.setData4(values_array.getString(j*8+5));
											data.setData5(values_array.getString(j*8+6));
											data.setData6(values_array.getString(j*8+7));
											data.setType((long)i);
											data.setPlanid(planid);
											data.setNoteid(noteid);
											if (transitions != null && transitions.size() > 0){
												data.setIstodotgol(transitions.get(0).getIstodotgol());
											}
											else{
												data.setIstodotgol(false);
											}
											dao.PeaceCrud(data, "DataMinPlan4_2", "save", (long) 0, 0, 0, null);
										}
									}
								}
								else{								
									if (values_array.length() > 0){
										for(int j=0;j<(int)(values_array.length()/7);j++){
											DataMinPlan4_2 data = new DataMinPlan4_2();
											data.setDataindex(values_array.getString(j*7));
											data.setData1(values_array.getString(j*7+1));
											data.setData2(values_array.getString(j*7+2));
											data.setData3(values_array.getString(j*7+3));
											data.setData4(values_array.getString(j*7+4));
											data.setData5(values_array.getString(j*7+5));
											data.setData6(values_array.getString(j*7+6));
											data.setType((long)i);
											data.setPlanid(planid);
											data.setNoteid(noteid);
											if (transitions != null && transitions.size() > 0){
												data.setIstodotgol(transitions.get(0).getIstodotgol());
											}
											else{
												data.setIstodotgol(false);
											}
											dao.PeaceCrud(data, "DataMinPlan4_2", "save", (long) 0, 0, 0, null);
										}
									}
								}

							}
						}

						if (noteid == 26 || noteid == 75){
							//dao.PeaceCrud(null, "DataMinPlan5", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan5", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan5", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Body/Table1/Row1/Table2/Row1/*");
							JSONArray tags_array = (JSONArray) response.get("tags");
							JSONArray data1_array = (JSONArray) response.get("values");
							JSONArray parent_array = (JSONArray) response.get("parent");
							for(int i=0;i<(int)(data1_array.length()/9);i++){
								DataMinPlan5 temp = new DataMinPlan5();
								temp.setPlanid(planid);
								temp.setNoteid(noteid);
								for(int j=0;j<9;j++){
									if (j==0){
										temp.setField("dataindex", data1_array.getString(i*9 + j));
									}
									else{
										temp.setField("data"+j, data1_array.getString(i*9 + j));
									}
								}
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								dao.PeaceCrud(temp, "DataMinPlan5", "save", (long) 0, 0, 0, null);
							}
						}

						if (noteid == 28 || noteid == 81){
							//dao.PeaceCrud(null, "DataMinPlan7", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan7", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan7", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Body/Table1/Row1/*");
							JSONArray tags_array = (JSONArray) response.get("tags");
							JSONArray data1_array = (JSONArray) response.get("values");
							JSONArray parent_array = (JSONArray) response.get("parent");
							for(int i=0;i<(int)(data1_array.length()/15);i++){
								DataMinPlan7 temp = new DataMinPlan7();
								temp.setPlanid(planid);
								temp.setNoteid(noteid);
								if (!(data1_array.getString(i*15).equalsIgnoreCase(null) || data1_array.getString(i*15).length() == 0)){
									temp.setData_index((long)data1_array.getDouble(i*15));
								}
								temp.setData1(data1_array.getString(i*15 +1));
								temp.setData2(data1_array.getString(i*15 +2));
								temp.setData3(data1_array.getString(i*15 +3));
								temp.setData4(data1_array.getString(i*15 +4));
								temp.setData5(data1_array.getString(i*15 +5));
								if (!(data1_array.getString(i*15 + 6).equalsIgnoreCase(null) || data1_array.getString(i*15 + 6).length() == 0)){
									temp.setData6(data1_array.getDouble(i*15 +6));
								}
								temp.setData7(data1_array.getString(i*15 +7));
								if (!(data1_array.getString(i*15+8).equalsIgnoreCase(null) || data1_array.getString(i*15+8).length() == 0)){
									temp.setData8(Long.parseLong(data1_array.getString(i*15 +8)));
								}
								if (!(data1_array.getString(i*15+9).equalsIgnoreCase(null) || data1_array.getString(i*15+9).length() == 0)){
									temp.setData9(Long.parseLong(data1_array.getString(i*15 +9)));
								}
								if (!(data1_array.getString(i*15+10).equalsIgnoreCase(null) || data1_array.getString(i*15+10).length() == 0)){
									temp.setData10(data1_array.getDouble(i*15 +10));
								}
								temp.setData11(data1_array.getString(i*15 +11));
								if (!(data1_array.getString(i*15+12).equalsIgnoreCase(null) || data1_array.getString(i*15+12).length() == 0)){
									temp.setData12(data1_array.getDouble(i*15 +12));
								}
								if (!(data1_array.getString(i*15+13).equalsIgnoreCase(null) || data1_array.getString(i*15+13).length() == 0)){
									temp.setData13(data1_array.getDouble(i*15 +13));
								}
								temp.setData14(data1_array.getString(i*15 +14));
								String[] response1 = jxml.selectQuery("//MainForm/Table_footer/HeaderRow2/Data13/text()");
								if (response1.length > 0){
									temp.setTotal(Double.parseDouble(response1[0]));
								}
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								dao.PeaceCrud(temp, "DataMinPlan7", "save", (long) 0, 0, 0, null);
								//System.out.println(i + " =>\t" + data1_array.get(i));
							}
						}

						if (noteid == 32 || noteid == 108){
							//dao.PeaceCrud(null, "DataMinPlan11", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan11", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan11", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}

							for(int i=1;i<=9;i++){
								DataMinPlan11 data = new DataMinPlan11();
								JSONObject response = jxml.selectQueryTags("//MainForm/Table_Body/Row"+i+"/*");
								JSONArray tags_array = (JSONArray) response.get("tags");
								JSONArray data1_array = (JSONArray) response.get("values");
								JSONArray parent_array = (JSONArray) response.get("parent");
								for(int j=1;j<=7;j++){
									if (data1_array.getString(j-1) != null && data1_array.getString(j-1).length() > 0){
										if (j==1){
											data.setField("data"+j, data1_array.getLong(j-1));
										}
										else if (j==4 || j==7){
											data.setField("data"+j, data1_array.getDouble(j-1));
										}
										else{
											data.setField("data"+j, data1_array.getString(j-1));
										}
									}
								}


								data.setNoteid(noteid);
								data.setPlanid(planid);
								if (transitions != null && transitions.size() > 0){
									data.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									data.setIstodotgol(false);
								}
								dao.PeaceCrud(data, "DataMinPlan11", "save", (long) 0, 0, 0, null);
							}
						}

						if (noteid == 72){
							dao.PeaceCrud(null, "DataCoalForm4", "delete", (long) planid, 0, 0, "planid");
							for(int i=1;i<=4;i++){
								DataCoalForm4 data = new DataCoalForm4();
								JSONObject response = jxml.selectQueryTags("//MainForm/Body_form/Table1/Row"+i+"/*");
								JSONArray data1_array = (JSONArray) response.get("values");
								for(int j=0;j<=13;j++){
									if (data1_array.getString(j) != null && data1_array.getString(j).length() > 0){
										if (j==0){
											data.setField("dataIndex", data1_array.getLong(j));
										}
										else if (j==1){
											data.setField("data"+j, data1_array.getString(j));
										}
										else{
											data.setField("data"+j, data1_array.getDouble(j));
										}
									}
								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								dao.PeaceCrud(data, "DataCoalForm4", "save", (long) 0, 0, 0, null);
							}
						}

						if (noteid == 33 || noteid == 111){
							//dao.PeaceCrud(null, "DataMinPlan12", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan12", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan12", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Table_Body/*/*");
							JSONArray tags_array = (JSONArray) response.get("tags");
							JSONArray data1_array = (JSONArray) response.get("values");
							JSONArray parent_array = (JSONArray) response.get("parent");
							for(int i=0;i<(int)(data1_array.length()/6);i++){
								DataMinPlan12 data = new DataMinPlan12();
								for(int j=0;j<=5;j++){
									if (data1_array.getString(i*6+j) != null && data1_array.getString(i*6+j).length() > 0){
										if (j == 3 || j==4){
											data.setField("data"+(j+1), data1_array.getDouble(i*6+j));
										}
										else{
											data.setField("data"+(j+1), data1_array.getString(i*6+j));
										}
									}
								}
								data.setNoteid(noteid);
								data.setPlanid(planid);
								if (transitions != null && transitions.size() > 0){
									data.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									data.setIstodotgol(false);
								}
								dao.PeaceCrud(data, "DataMinPlan12", "save", (long) 0, 0, 0, null);
							}
						}
						if (noteid == 29 || noteid == 99){
							//dao.PeaceCrud(null, "DataMinPlan8", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan8", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan8", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Body/Table1/Row1/*");
							JSONArray tags_array = (JSONArray) response.get("tags");
							JSONArray data1_array = (JSONArray) response.get("values");
							JSONArray parent_array = (JSONArray) response.get("parent");
							for(int i=0;i<(int)(data1_array.length()/17);i++){
								DataMinPlan8 temp = new DataMinPlan8();
								temp.setPlanid(planid);
								temp.setNoteid(noteid);
								if (!(data1_array.getString(i*17).equalsIgnoreCase(null) || data1_array.getString(i*17).length() == 0)){
									temp.setData_index(data1_array.getDouble(i*17));
								}
								temp.setData1(data1_array.getString(i*17 +1));
								temp.setData2(data1_array.getString(i*17 +2));
								temp.setData3(data1_array.getString(i*17 +3));
								temp.setData4(data1_array.getString(i*17 +4));
								temp.setData5(data1_array.getString(i*17 +5));
								if (!(data1_array.getString(i*17+6).equalsIgnoreCase(null) || data1_array.getString(i*17+6).length() == 0)){
									temp.setData6(data1_array.getDouble(i*17 +6));
								}
								temp.setData7(data1_array.getString(i*17 +7));
								if (!(data1_array.getString(i*17 + 8).equalsIgnoreCase(null) || data1_array.getString(i*17 + 8).length() == 0)){
									temp.setData8(data1_array.getLong(i*17 +8));
								}
								if (!(data1_array.getString(i*17 + 9).equalsIgnoreCase(null) || data1_array.getString(i*17 + 9).length() == 0)){
									temp.setData9(data1_array.getDouble(i*17 +9));
								}
								if (!(data1_array.getString(i*17 + 10).equalsIgnoreCase(null) || data1_array.getString(i*17 + 10).length() == 0)){
									System.out.println("a@@@@@@@@@@@@@"+data1_array.get(i*17 +10));
									if(data1_array.get(i*17 +10).toString().equalsIgnoreCase("-")){
										temp.setData10((double) 0);
									}
									else{
										temp.setData10(data1_array.getDouble(i*17 +10));
									}

								}
								temp.setData11(data1_array.getString(i*17 +11));
								if (!(data1_array.getString(i*17+12).equalsIgnoreCase(null) || data1_array.getString(i*17+12).length() == 0)){
									temp.setData12(data1_array.getDouble(i*17 +12));
								}
								if (!(data1_array.getString(i*17+13).equalsIgnoreCase(null) || data1_array.getString(i*17+13).length() == 0)){
									temp.setData13(data1_array.getDouble(i*17 +13));
								}
								if (!(data1_array.getString(i*17+14).equalsIgnoreCase(null) || data1_array.getString(i*17+14).length() == 0)){
									temp.setData14(data1_array.getDouble(i*17 +14));
								}
								temp.setData15(data1_array.getString(i*17 +15));
								temp.setData16(data1_array.getString(i*17 +16));
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								String[] response1 = jxml.selectQuery("//MainForm/Table_total/Total/Sum2/text()");
								if (response1.length > 0){
									temp.setTotal(Double.parseDouble(response1[0]));
								}
								dao.PeaceCrud(temp, "DataMinPlan8", "save", (long) 0, 0, 0, null);
								//System.out.println(i + " =>\t" + data1_array.get(i));
							}
						}

						if (noteid == 31 || noteid == 105){
							//dao.PeaceCrud(null, "DataMinPlan10", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan10", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan10", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							String[] labels = {"Gadaad","Dotood","Biology_hors","Biology_bio","Duitsuuleh","Tohijuulah","Total"};
							for(int i=0;i<labels.length;i++){
								if(i!=labels.length-1){
									JSONObject response = jxml.selectQueryTags("//MainForm/Table1/Table4/"+labels[i]+"/Table3/Row1/*");
									JSONArray tags_array = (JSONArray) response.get("tags");
									JSONArray data1_array = (JSONArray) response.get("values");
									JSONArray parent_array = (JSONArray) response.get("parent");

									String data1 = data1_array.getString(1);
									Double data2 = null;
									if (!(data1_array.getString(2).equalsIgnoreCase(null) || data1_array.getString(2).length() == 0)){
										data2 = Double.parseDouble(data1_array.getString(2));
									}
									String data3 = data1_array.getString(3);
									Double data4 = null;
									if (!(data1_array.getString(4).equalsIgnoreCase(null) || data1_array.getString(4).length() == 0)){
										data4 = Double.parseDouble(data1_array.getString(4));
									}
									String data5 = data1_array.getString(5);
									Double data6 = null;
									if (!(data1_array.getString(6).equalsIgnoreCase(null) || data1_array.getString(6).length() == 0)){
										data6 = Double.parseDouble(data1_array.getString(6));
									}
									String data7 = data1_array.getString(7);
									response = jxml.selectQueryTags("//MainForm/Table1/Table4/"+labels[i]+"/Table3/Row1/Table2/Row1/*");
									data1_array = (JSONArray) response.get("values");
									for(int j=0;j<(int)(data1_array.length()/10);j++){
										DataMinPlan10 temp = new DataMinPlan10();
										temp.setData1(data1);
										temp.setData2(data2);
										temp.setData3(data3);
										temp.setData4(data4);
										temp.setData5(data5);
										temp.setData6(data6);
										temp.setData7(data7);
										
										if (!(data1_array.getString(j*10).equalsIgnoreCase(null) || data1_array.getString(j*10).length() == 0)){
											temp.setDataIndex((long)data1_array.getDouble(j*10));
										}
										if (!(data1_array.getString(j*10+1).equalsIgnoreCase(null) || data1_array.getString(j*10+1).length() == 0)){
											temp.setData8((long)data1_array.getDouble(j*10+1));
										}
										if (!(data1_array.getString(j*10+2).equalsIgnoreCase(null) || data1_array.getString(j*10+2).length() == 0)){
											temp.setData9(data1_array.getDouble(j*10+2));
										}
										if (!(data1_array.getString(j*10+3).equalsIgnoreCase(null) || data1_array.getString(j*10+3).length() == 0)){
											temp.setData10(data1_array.getDouble(j*10+3));
										}
										if (!(data1_array.getString(j*10+4).equalsIgnoreCase(null) || data1_array.getString(j*10+4).length() == 0)){
											temp.setData11(data1_array.getDouble(j*10+4));
										}
										if (!(data1_array.getString(j*10+5).equalsIgnoreCase(null) || data1_array.getString(j*10+5).length() == 0)){
											temp.setData12(data1_array.getDouble(j*10+5));
										}
										if (!(data1_array.getString(j*10+6).equalsIgnoreCase(null) || data1_array.getString(j*10+6).length() == 0)){
											temp.setData13(data1_array.getDouble(j*10+6));
										}
										if (!(data1_array.getString(j*10+7).equalsIgnoreCase(null) || data1_array.getString(j*10+7).length() == 0)){
											temp.setData14(data1_array.getDouble(j*10+7));
										}
										if (!(data1_array.getString(j*10+8).equalsIgnoreCase(null) || data1_array.getString(j*10+8).length() == 0)){
											temp.setData15(data1_array.getDouble(j*10+8));
										}
										temp.setType(Long.valueOf(i+1));
										temp.setPlanid(planid);
										temp.setNoteid(noteid);
										if (transitions != null && transitions.size() > 0){
											temp.setIstodotgol(transitions.get(0).getIstodotgol());
										}
										else{
											temp.setIstodotgol(false);
										}
										//System.out.print(temp.toString());
										dao.PeaceCrud(temp, "DataMinPlan10", "save", (long) 0, 0, 0, null);
									}

								}
								else{
									DataMinPlan10 total = new DataMinPlan10();
									String[] response = jxml.selectQuery("//MainForm/Table1/Table4/"+labels[i]+"/Table3/Row1/Data3/text()");
									if (response.length > 0){
										total.setData2(Double.parseDouble(response[0]));
									}
									response = jxml.selectQuery("//MainForm/Table1/Table4/"+labels[i]+"/Table3/Row1/Data5/text()");
									if (response.length > 0){
										total.setData4(Double.parseDouble(response[0]));
									}
									response = jxml.selectQuery("//MainForm/Table1/Table4/"+labels[i]+"/Table3/Row1/Data7/text()");
									if (response.length > 0){
										total.setData6(Double.parseDouble(response[0]));
									}
									total.setType(Long.valueOf(i+1));
									total.setPlanid(planid);
									total.setNoteid(noteid);
									if (transitions != null && transitions.size() > 0){
										total.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										total.setIstodotgol(false);
									}
									dao.PeaceCrud(total, "DataMinPlan10", "save", (long) 0, 0, 0, null);
								}
							}
						}
						if (noteid == 181){
							//dao.PeaceCrud(null, "DataMinPlan2_2 ", "delete", (long) planid, 0, 0, "planid");
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan2_2", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan2_2", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Body_form/Table1/Row1/*");
							JSONArray data1_array = (JSONArray) response.get("values");
							// === 1 ===
							for(int i=0;i<(int)(data1_array.length()/19);i++){
								DataMinPlan2_2 temp = new DataMinPlan2_2();
								for(int j=0;j<19;j++){
									if (j == 0){
										if (!(data1_array.getString(i*19).equalsIgnoreCase(null) || data1_array.getString(i*19).length() == 0)){
											temp.setDataIndex(Long.parseLong(data1_array.getString(i*19)));
										}
									}
									else if (j==1 || (j>=3 && j <= 14) || (j == 17)){
										temp.setField("data"+j, data1_array.getString(i*19 + j));
									}
									else{
										if (!(data1_array.getString(i*19+j).equalsIgnoreCase(null) || data1_array.getString(i*19+j).length() == 0)){
											temp.setField("data"+j, Double.parseDouble(data1_array.getString(i*19 + j)));
										}
									}
								}
								temp.setPlanid(planid);
								temp.setNoteid(noteid);
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								temp.setType(Long.valueOf(1));
								dao.PeaceCrud(temp, "DataMinPlan2_2", "save", (long) 0, 0, 0, null);
								//System.out.println(temp.toString());


								/*JSONObject metalresponse = jxml.selectQueryTags("//MainForm/Body_form/Metall["+(i+1)+"]/Sub_metal/Table5/Row1/*");
							JSONArray metal_array = (JSONArray) metalresponse.get("values");
							for(int j=0;j<(int)(data1_array.length()/13);j++){
								DataMinPlan2_2 metal = new DataMinPlan2_2();
								metal.setPlanid(planid);
								metal.setNoteid(noteid);
								metal.setType(Long.valueOf(5));
								metal.setData1(temp.getData1());
								metal.setData2(temp.getData2());
								metal.setDataIndex(metal_array.getLong(j*13));
								metal.setData3(metal_array.getString(j*13+1));
								metal.setData4(metal_array.getString(j*13+2));
								metal.setData5(metal_array.getString(j*13+3));
								metal.setData6(metal_array.getString(j*13+4));
								metal.setData7(metal_array.getString(j*13+5));
								metal.setData8(metal_array.getString(j*13+6));
								metal.setData9(metal_array.getString(j*13+7));
								metal.setData10(metal_array.getString(j*13+8));
								metal.setData11(metal_array.getString(j*13+9));
								metal.setData12(metal_array.getString(j*13+10));
								metal.setData13(metal_array.getString(j*13+11));
								metal.setData14(metal_array.getString(j*13+12));


								dao.PeaceCrud(metal, "DataMinPlan2_2", "save", (long) 0, 0, 0, null);
							}*/
							}
							// === 2 ===
							response = jxml.selectQueryTags("//MainForm/Body_form/Table1/Footer/*");
							data1_array = (JSONArray) response.get("values");
							DataMinPlan2_2 temp = new DataMinPlan2_2();
							temp.setData1(data1_array.getString(0));
							if (!(data1_array.getString(2).equalsIgnoreCase(null) || data1_array.getString(2).length() == 0)){
								temp.setData5(data1_array.getString(2));
							}
							if (!(data1_array.getString(3).equalsIgnoreCase(null) || data1_array.getString(3).length() == 0)){
								temp.setData6(data1_array.getString(3));
							}
							if (!(data1_array.getString(4).equalsIgnoreCase(null) || data1_array.getString(4).length() == 0)){
								temp.setData7(data1_array.getString(4));
							}
							if (!(data1_array.getString(5).equalsIgnoreCase(null) || data1_array.getString(5).length() == 0)){
								temp.setData8(data1_array.getString(5));
							}
							if (!(data1_array.getString(6).equalsIgnoreCase(null) || data1_array.getString(6).length() == 0)){
								temp.setData9(data1_array.getString(6));
							}
							if (!(data1_array.getString(7).equalsIgnoreCase(null) || data1_array.getString(7).length() == 0)){
								temp.setData10(data1_array.getString(7));
							}
							if (!(data1_array.getString(9).equalsIgnoreCase(null) || data1_array.getString(9).length() == 0)){
								temp.setData12(data1_array.getString(9));
							}
							if (!(data1_array.getString(12).equalsIgnoreCase(null) || data1_array.getString(12).length() == 0)){
								temp.setData15(data1_array.getDouble(12));
							}
							if (!(data1_array.getString(13).equalsIgnoreCase(null) || data1_array.getString(13).length() == 0)){
								temp.setData16(data1_array.getDouble(13));
							}
							if (!(data1_array.getString(15).equalsIgnoreCase(null) || data1_array.getString(15).length() == 0)){
								temp.setData18(data1_array.getDouble(15));
							}
							temp.setPlanid(planid);
							temp.setNoteid(noteid);
							if (transitions != null && transitions.size() > 0){
								temp.setIstodotgol(transitions.get(0).getIstodotgol());
							}
							else{
								temp.setIstodotgol(false);
							}
							temp.setType(Long.valueOf(2));
							dao.PeaceCrud(temp, "DataMinPlan2_2", "save", (long) 0, 0, 0, null);
							// === 3 ===

							List<DataMinPlan2_2> d1= (List<DataMinPlan2_2>) dao.getHQLResult("from DataMinPlan2_2 t where t.planid = " + planid + " and t.noteid = " + noteid + " and t.type = 1", "list");
							for(int i=0; i<d1.size();i++){
								DataMinPlan2_2 d2 = d1.get(i);
								response = jxml.selectQueryTags("//MainForm/Body_form/Table3/Row1["+(i+1)+"]/*");
								data1_array = (JSONArray) response.get("values");
								JSONArray tags_array = (JSONArray) response.get("tags");
								for(int j=0;j<data1_array.length();j++){
									for(int j1=19;j1<=24;j1++){
										if (tags_array.getString(j).equalsIgnoreCase("data"+j1)){
											if (!(data1_array.getString(j).equalsIgnoreCase(null) || data1_array.getString(j).length() == 0)){
												if (data1_array.get(j) instanceof Double){
													d2.setField("data"+j1, data1_array.getDouble(j));
												}
											}
										}
									}
								}
								dao.PeaceCrud(d2, "DataMinPlan2_2", "save", (long) 0, 0, 0, null);


							}

							// === 4 ===
							DataMinPlan2_2 d3= (DataMinPlan2_2) dao.getHQLResult("from DataMinPlan2_2 t where t.planid = " + planid + " and t.noteid = " + noteid + " and t.type = 2", "current");
							if (d3 != null){
								response = jxml.selectQueryTags("//MainForm/Body_form/Table3/Footer/*");
								data1_array = (JSONArray) response.get("values");
								if (data1_array.length() > 0){
									if (data1_array.getString(3).length() > 0){
										d3.setData20(data1_array.getDouble(3));
									}
									if (data1_array.getString(5).length() > 0){
										d3.setData22(data1_array.getDouble(5));
									}
									if (data1_array.getString(7).length() > 0){
										d3.setData24(data1_array.getDouble(7));
									}

								}

								dao.PeaceCrud(d3, "DataMinPlan2_2", "save", (long) 0, 0, 0, null);
							}


							// === 5 ===
							/*response = jxml.selectQueryTags("//MainForm/Body_form/Metall/*");
						data1_array = (JSONArray) response.get("values");

						int size = (int)(data1_array.length() / 4);
						for(int i=0;i<size;i++){
							response = jxml.selectQueryTags("//MainForm/Body_form/Metall["+(i+1)+"]/Sub_head/Table5/HeaderRow/*");
							data1_array = (JSONArray) response.get("values");
							for(int j=0;j<data1_array.length();j++){
								System.out.println(j + " =>\t" + data1_array.get(j));
							}
							System.out.println("====================");
							Long dataIndex = Long.parseLong(data1_array.getString(0));
							String data1 = data1_array.getString(1);
							Double data2 = data1_array.getDouble(2);
							String data4 = data1_array.getString(4);
							response = jxml.selectQueryTags("//MainForm/Body_form/Metall["+(i+1)+"]/Sub_metal/Table5/Row1/*");
							data1_array = (JSONArray) response.get("values");
							for(int j=0;j<(int)(data1_array.length()/13);j++){
								temp = new DataMinPlan2_2();
								temp.setPlanid(planid);
								temp.setNoteid(noteid);
								temp.setType(Long.valueOf(5));
								temp.setData1(data1);
								temp.setData2(data2);
								temp.setData4(data4);
								temp.setDataIndex(dataIndex);
								temp.setData3(data1_array.getString(j*13+1));
								temp.setData5(data1_array.getString(j*13+3));
								temp.setData6(data1_array.getString(j*13+4));
								temp.setData7(data1_array.getString(j*13+5));
								temp.setData8(data1_array.getString(j*13+6));
								temp.setData9(data1_array.getString(j*13+7));
								temp.setData10(data1_array.getString(j*13+8));
								temp.setData11(data1_array.getString(j*13+9));
								temp.setData12(data1_array.getString(j*13+10));
								temp.setData13(data1_array.getString(j*13+11));
								temp.setData14(data1_array.getString(j*13+12));


								dao.PeaceCrud(temp, "DataMinPlan2_2", "save", (long) 0, 0, 0, null);
							}
						}*/
						}
						if (noteid == 182){
							//dao.PeaceCrud(null, "DataMinPlan2_1 ", "delete", (long) planid, 0, 0, "planid");
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan2_1", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan2_1", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Body_form/Table1/Row1/*");
							JSONArray data1_array = (JSONArray) response.get("values");
							// === 1 ===
							for(int i=0;i<(int)(data1_array.length()/19);i++){
								DataMinPlan2_1 temp = new DataMinPlan2_1();
								for(int j=0;j<19;j++){
									if (j == 0){
										if (!(data1_array.getString(i*19).equalsIgnoreCase(null) || data1_array.getString(i*19).length() == 0)){
											temp.setDataIndex((long)data1_array.getDouble(i*19));
										}
									}
									else if (j==1 || (j>=3 && j <= 14) || j==17){
										temp.setField("data"+j, data1_array.getString(i*19 + j));
									}
									else{
										if (!(data1_array.getString(i*19+j).equalsIgnoreCase(null) || data1_array.getString(i*19+j).length() == 0)){
											temp.setField("data"+j, Double.parseDouble(data1_array.getString(i*19 + j)));
										}
									}
								}
								temp.setPlanid(planid);
								temp.setNoteid(noteid);
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								temp.setType(Long.valueOf(1));
								dao.PeaceCrud(temp, "DataMinPlan2_1", "save", (long) 0, 0, 0, null);
								//System.out.println(temp.toString());


								JSONObject metalresponse = jxml.selectQueryTags("//MainForm/Body_form/Metall["+(i+1)+"]/Sub_metal/Table5/Row1/*");
								JSONArray metal_array = (JSONArray) metalresponse.get("values");
								System.out.println(i + " ======= \n" + metal_array.toString());
								for(int j=0;j<(int)(metal_array.length()/13);j++){
									DataMinPlan2_1 metal = new DataMinPlan2_1();
									metal.setPlanid(planid);
									metal.setNoteid(noteid);
									if (transitions != null && transitions.size() > 0){
										metal.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										metal.setIstodotgol(false);
									}
									metal.setType(Long.valueOf(5));
									metal.setData1(temp.getData1());
									metal.setData2(temp.getData2());
									metal.setDataIndex((long)metal_array.getDouble(j*13));
									metal.setData3(metal_array.getString(j*13+1));
									metal.setData4(metal_array.getString(j*13+2));
									metal.setData5(metal_array.getString(j*13+3));
									metal.setData6(metal_array.getString(j*13+4));
									metal.setData7(metal_array.getString(j*13+5));
									metal.setData8(metal_array.getString(j*13+6));
									metal.setData9(metal_array.getString(j*13+7));
									metal.setData10(metal_array.getString(j*13+8));
									metal.setData11(metal_array.getString(j*13+9));
									metal.setData12(metal_array.getString(j*13+10));
									metal.setData13(metal_array.getString(j*13+11));
									metal.setData14(metal_array.getString(j*13+12));


									dao.PeaceCrud(metal, "DataMinPlan2_1", "save", (long) 0, 0, 0, null);
								}
							}
							// === 2 ===
							response = jxml.selectQueryTags("//MainForm/Body_form/Table1/Footer/*");
							data1_array = (JSONArray) response.get("values");
							DataMinPlan2_1 temp = new DataMinPlan2_1();
							temp.setData1(data1_array.getString(0));
							if (!(data1_array.getString(2).equalsIgnoreCase(null) || data1_array.getString(2).length() == 0)){
								temp.setData5(data1_array.getString(2));
							}
							if (!(data1_array.getString(3).equalsIgnoreCase(null) || data1_array.getString(3).length() == 0)){
								temp.setData6(data1_array.getString(3));
							}
							if (!(data1_array.getString(4).equalsIgnoreCase(null) || data1_array.getString(4).length() == 0)){
								temp.setData7(data1_array.getString(4));
							}
							if (!(data1_array.getString(5).equalsIgnoreCase(null) || data1_array.getString(5).length() == 0)){
								temp.setData8(data1_array.getString(5));
							}
							if (!(data1_array.getString(6).equalsIgnoreCase(null) || data1_array.getString(6).length() == 0)){
								temp.setData9(data1_array.getString(6));
							}
							if (!(data1_array.getString(7).equalsIgnoreCase(null) || data1_array.getString(7).length() == 0)){
								temp.setData10(data1_array.getString(7));
							}
							if (!(data1_array.getString(9).equalsIgnoreCase(null) || data1_array.getString(9).length() == 0)){
								temp.setData12(data1_array.getString(9));
							}
							if (!(data1_array.getString(12).equalsIgnoreCase(null) || data1_array.getString(12).length() == 0)){
								temp.setData15(data1_array.getDouble(12));
							}
							if (!(data1_array.getString(13).equalsIgnoreCase(null) || data1_array.getString(13).length() == 0)){
								temp.setData16(data1_array.getDouble(13));
							}
							if (!(data1_array.getString(15).equalsIgnoreCase(null) || data1_array.getString(15).length() == 0)){
								temp.setData18(data1_array.getDouble(15));
							}
							temp.setPlanid(planid);
							temp.setNoteid(noteid);
							if (transitions != null && transitions.size() > 0){
								temp.setIstodotgol(transitions.get(0).getIstodotgol());
							}
							else{
								temp.setIstodotgol(false);
							}
							temp.setType(Long.valueOf(2));
							dao.PeaceCrud(temp, "DataMinPlan2_1", "save", (long) 0, 0, 0, null);
							// === 3 ===

							List<DataMinPlan2_1> d1= (List<DataMinPlan2_1>) dao.getHQLResult("from DataMinPlan2_1 t where t.planid = " + planid + " and t.noteid = " + noteid + " and t.type = 1", "list");
							for(int i=0; i<d1.size();i++){
								DataMinPlan2_1 d2 = d1.get(i);
								response = jxml.selectQueryTags("//MainForm/Body_form/Table3/Row1["+(i+1)+"]/*");
								data1_array = (JSONArray) response.get("values");
								JSONArray tags_array = (JSONArray) response.get("tags");
								for(int j=0;j<data1_array.length();j++){
									for(int j1=19;j1<=24;j1++){
										if (tags_array.getString(j).equalsIgnoreCase("data"+j1)){
											if (!(data1_array.getString(j).equalsIgnoreCase(null) || data1_array.getString(j).length() == 0)){
											    if (j1 == 19 || j1 == 21 || j1 == 23){
													System.out.println(data1_array.getString(j));
													d2.setField("data"+j1, data1_array.getString(j));
                                                }
                                                else{
													System.out.println(data1_array.get(j));
													d2.setField("data"+j1, data1_array.getDouble(j));
                                                }
											}
										}
									}
								}
								dao.PeaceCrud(d2, "DataMinPlan2_1", "save", (long) 0, 0, 0, null);


							}

							// === 4 ===
							DataMinPlan2_1 d3= (DataMinPlan2_1) dao.getHQLResult("from DataMinPlan2_1 t where t.planid = " + planid + " and t.noteid = " + noteid + " and t.type = 2", "current");
							if (d3 != null){
								response = jxml.selectQueryTags("//MainForm/Body_form/Table3/Footer/*");
								data1_array = (JSONArray) response.get("values");
								if (!(data1_array.getString(3).equalsIgnoreCase(null) || data1_array.getString(3).length() == 0)){
									d3.setData20(data1_array.getDouble(3));
								}
								if (!(data1_array.getString(5).equalsIgnoreCase(null) || data1_array.getString(5).length() == 0)){
									d3.setData22(data1_array.getDouble(5));
								}
								if (!(data1_array.getString(7).equalsIgnoreCase(null) || data1_array.getString(7).length() == 0)){
									d3.setData24(data1_array.getDouble(7));
								}
								dao.PeaceCrud(d3, "DataMinPlan2_1", "save", (long) 0, 0, 0, null);
							}


							// === 5 ===
							/*response = jxml.selectQueryTags("//MainForm/Body_form/Metall/*");
						data1_array = (JSONArray) response.get("values");

						int size = (int)(data1_array.length() / 4);
						for(int i=0;i<size;i++){
							response = jxml.selectQueryTags("//MainForm/Body_form/Metall["+(i+1)+"]/Sub_head/Table5/HeaderRow/*");
							data1_array = (JSONArray) response.get("values");
							for(int j=0;j<data1_array.length();j++){
								System.out.println(j + " =>\t" + data1_array.get(j));
							}
							System.out.println("====================");
							Long dataIndex = Long.parseLong(data1_array.getString(0));
							String data1 = data1_array.getString(1);
							Double data2 = data1_array.getDouble(2);
							String data4 = data1_array.getString(4);
							response = jxml.selectQueryTags("//MainForm/Body_form/Metall["+(i+1)+"]/Sub_metal/Table5/Row1/*");
							data1_array = (JSONArray) response.get("values");
							for(int j=0;j<(int)(data1_array.length()/13);j++){
								temp = new DataMinPlan2_1();
								temp.setPlanid(planid);
								temp.setNoteid(noteid);
								temp.setType(Long.valueOf(5));
								temp.setData1(data1);
								temp.setData2(data2);
								temp.setData4(data4);
								temp.setDataIndex(dataIndex);
								temp.setData3(data1_array.getString(j*13+1));
								temp.setData5(data1_array.getString(j*13+3));
								temp.setData6(data1_array.getString(j*13+4));
								temp.setData7(data1_array.getString(j*13+5));
								temp.setData8(data1_array.getString(j*13+6));
								temp.setData9(data1_array.getString(j*13+7));
								temp.setData10(data1_array.getString(j*13+8));
								temp.setData11(data1_array.getString(j*13+9));
								temp.setData12(data1_array.getString(j*13+10));
								temp.setData13(data1_array.getString(j*13+11));
								temp.setData14(data1_array.getString(j*13+12));


								dao.PeaceCrud(temp, "DataMinPlan2_1", "save", (long) 0, 0, 0, null);
							}
						}*/
						}

						if (noteid == 24){
							//dao.PeaceCrud(null, "DataMinPlan2_2 ", "delete", (long) planid, 0, 0, "planid");
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan2_2", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan2_2", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							JSONObject response = jxml.selectQueryTags("//MainForm/Body_form/Table1/Row1/*");
							JSONArray data1_array = (JSONArray) response.get("values");
							// === 1 ===
							for(int i=0;i<(int)(data1_array.length()/19);i++){
								DataMinPlan2_2 temp = new DataMinPlan2_2();
								for(int j=0;j<19;j++){
									if (j == 0){
										if (!(data1_array.getString(i*19).equalsIgnoreCase(null) || data1_array.getString(i*19).length() == 0)){
											temp.setDataIndex(Long.parseLong(data1_array.getString(i*19)));
										}
									}
									else if (j==1 || j==3){
										temp.setField("data"+j, data1_array.getString(i*19 + j));
									}
									else{
										if (!(data1_array.getString(i*19+j).equalsIgnoreCase(null) || data1_array.getString(i*19+j).length() == 0)){
											temp.setField("data"+j, Double.parseDouble(data1_array.getString(i*19 + j)));
										}
									}
								}
								temp.setPlanid(planid);
								temp.setNoteid(noteid);
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								temp.setType(Long.valueOf(1));
								dao.PeaceCrud(temp, "DataMinPlan2_2", "save", (long) 0, 0, 0, null);
								//System.out.println(temp.toString());
							}
							// === 2 ===
							response = jxml.selectQueryTags("//MainForm/Body_form/Table1/Footer/*");
							data1_array = (JSONArray) response.get("values");
							DataMinPlan2_2 temp = new DataMinPlan2_2();
							temp.setData1(data1_array.getString(0));
							temp.setData5(data1_array.getString(2));
							temp.setData6(data1_array.getString(3));
							temp.setData7(data1_array.getString(4));
							temp.setData8(data1_array.getString(5));
							temp.setData9(data1_array.getString(6));
							temp.setData10(data1_array.getString(7));
							temp.setData12(data1_array.getString(9));
							temp.setData15(data1_array.getDouble(12));
							temp.setData16(data1_array.getDouble(13));
							temp.setData18(data1_array.getDouble(15));
							temp.setPlanid(planid);
							temp.setNoteid(noteid);
							temp.setType(Long.valueOf(2));
							if (transitions != null && transitions.size() > 0){
								temp.setIstodotgol(transitions.get(0).getIstodotgol());
							}
							else{
								temp.setIstodotgol(false);
							}
							dao.PeaceCrud(temp, "DataMinPlan2_2", "save", (long) 0, 0, 0, null);
							//System.out.println(temp.toString());
							// === 3 ===
							response = jxml.selectQueryTags("//MainForm/Body_form/Table3/Row1/*");
							data1_array = (JSONArray) response.get("values");
							/*for(int i=0;i<data1_array.length();i++){
							System.out.println(i + " =>\t" + data1_array.get(i));
						}*/
							for(int i=0;i<(int)(data1_array.length()/11);i++){
								temp = new DataMinPlan2_2();
								for(int j=0;j<11;j++){
									if (j == 0){
										if (!(data1_array.getString(i*11).equalsIgnoreCase(null) || data1_array.getString(i*11).length() == 0)){
											temp.setDataIndex(Long.parseLong(data1_array.getString(i*11)));
										}
									}
									else if (j==1 || j==3){
										temp.setField("data"+j, data1_array.getString(i*11 + j));
									}
									else{
										if (!(data1_array.getString(i*11+j).equalsIgnoreCase(null) || data1_array.getString(i*11+j).length() == 0)){
											temp.setField("data"+j, Double.parseDouble(data1_array.getString(i*11 + j)));
										}
									}
								}
								temp.setPlanid(planid);
								temp.setNoteid(noteid);
								temp.setType(Long.valueOf(3));
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								dao.PeaceCrud(temp, "DataMinPlan2_2", "save", (long) 0, 0, 0, null);
								//System.out.println(temp.toString());
							}
							// === 4 ===
							response = jxml.selectQueryTags("//MainForm/Body_form/Table3/Footer/*");
							data1_array = (JSONArray) response.get("values");
							temp = new DataMinPlan2_2();

							temp.setData1(data1_array.getString(0));
							temp.setData6(data1_array.getString(3));
							temp.setData8(data1_array.getString(5));
							temp.setData10(data1_array.getString(7));
							temp.setPlanid(planid);
							temp.setNoteid(noteid);
							temp.setType(Long.valueOf(4));
							if (transitions != null && transitions.size() > 0){
								temp.setIstodotgol(transitions.get(0).getIstodotgol());
							}
							else{
								temp.setIstodotgol(false);
							}
							dao.PeaceCrud(temp, "DataMinPlan2_2", "save", (long) 0, 0, 0, null);

							// === 5 ===
							response = jxml.selectQueryTags("//MainForm/Body_form/Metall/*");
							data1_array = (JSONArray) response.get("values");

							int size = (int)(data1_array.length() / 4);
							for(int i=0;i<size;i++){
								response = jxml.selectQueryTags("//MainForm/Body_form/Metall["+(i+1)+"]/Sub_head/Table5/HeaderRow/*");
								data1_array = (JSONArray) response.get("values");
								for(int j=0;j<data1_array.length();j++){
									System.out.println(j + " =>\t" + data1_array.get(j));
								}
								System.out.println("====================");
								Long dataIndex = Long.parseLong(data1_array.getString(0));
								String data1 = data1_array.getString(1);
								Double data2 = data1_array.getDouble(2);
								String data4 = data1_array.getString(4);
								response = jxml.selectQueryTags("//MainForm/Body_form/Metall["+(i+1)+"]/Sub_metal/Table5/Row1/*");
								data1_array = (JSONArray) response.get("values");
								for(int j=0;j<(int)(data1_array.length()/13);j++){
									temp = new DataMinPlan2_2();
									temp.setPlanid(planid);
									temp.setNoteid(noteid);
									temp.setType(Long.valueOf(5));
									temp.setData1(data1);
									temp.setData2(data2);
									temp.setData4(data4);
									temp.setDataIndex(dataIndex);
									temp.setData3(data1_array.getString(j*13+1));
									temp.setData5(data1_array.getString(j*13+4));
									temp.setData6(data1_array.getString(j*13+6));
									temp.setData7(data1_array.getString(j*13+8));
									temp.setData8(data1_array.getString(j*13+10));
									temp.setData8(data1_array.getString(j*13+12));
									if (transitions != null && transitions.size() > 0){
										temp.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										temp.setIstodotgol(false);
									}
									dao.PeaceCrud(temp, "DataMinPlan2_2", "save", (long) 0, 0, 0, null);
								}
							}
						}

						if (noteid == 37 || noteid == 84){
							dao.PeaceCrud(null, "DataMinPlan3", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = " + noteid);
							List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = "+noteid+" and t.planid = " + planid, "list");
							if (transitions != null && transitions.size() > 0){
								dao.PeaceCrud(null, "DataMinPlan3", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
							}
							else{
								dao.PeaceCrud(null, "DataMinPlan3", "multidelete", (long) planid, 0, 0, "where planid = " + planid + " and noteid = "+noteid+" and istodotgol = 0");
							}
							// === OLBORLOLT ===
							for(int jj=1;jj<=4;jj++){
								JSONObject response = jxml.selectQueryTags("//B_olborlolt/Row"+jj+"/*");
								JSONArray tags_array = (JSONArray) response.get("tags");
								JSONArray values_array = (JSONArray) response.get("values");
								JSONArray parent_array = (JSONArray) response.get("parent");
								for(int i=0;i<(int)(tags_array.length()/19);i++){
									DataMinPlan3 temp = new DataMinPlan3();
									for(int j=0;j<19;j++){
										if (!(values_array.getString((i*19) + j).equalsIgnoreCase(null) || values_array.getString((i*19) + j).length() == 0)){
											if (j<2){
												temp.setField("data"+(j+1), values_array.get((i*19) + j));
											}
											else{
												temp.setField("data"+(j+1), Double.parseDouble(values_array.getString((i*19) + j).replace(",", "")));
											}
										}
									}
									temp.setNoteid(Long.valueOf(noteid));
									temp.setPlanid(planid);
									temp.setType(Long.valueOf(1));
									if (transitions != null && transitions.size() > 0){
										temp.setIstodotgol(transitions.get(0).getIstodotgol());
									}
									else{
										temp.setIstodotgol(false);
									}
									dao.PeaceCrud(temp, "DataMinPlan3", "save", (long) 0, 0, 0, null);
									//System.out.println(temp.toString());
								}
							}
							


							// === GARGALT ===
							JSONObject response = jxml.selectQueryTags("//Gar_header/Row1/*");
							JSONArray tags_array = (JSONArray) response.get("tags");
							JSONArray values_array = (JSONArray) response.get("values");
							JSONArray parent_array = (JSONArray) response.get("parent");
							for(int i=0;i<(int)(tags_array.length()/19);i++){
								DataMinPlan3 temp = new DataMinPlan3();
								for(int j=0;j<19;j++){
									if (!(values_array.getString((i*19) + j).equalsIgnoreCase(null) || values_array.getString((i*19) + j).length() == 0)){
										if (j<2){
											temp.setField("data"+(j+1), values_array.get((i*19) + j));
										}
										else{
											temp.setField("data"+(j+1), Double.parseDouble(values_array.getString((i*19) + j)));
										}
									}

								}
								temp.setNoteid(Long.valueOf(noteid));
								temp.setPlanid(planid);
								temp.setType(Long.valueOf(2));
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								dao.PeaceCrud(temp, "DataMinPlan3", "save", (long) 0, 0, 0, null);


								//System.out.println(temp.toString());
							}

							response = jxml.selectQueryTags("//B_gargalt/Row2/*");
							tags_array = (JSONArray) response.get("tags");
							values_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");
							for(int i = 0;i<(int)(tags_array.length()/20);i++){
								DataMinPlan3 temp = new DataMinPlan3();
								for(int j=0;j<20;j++){
									if ((j > 0) && !(values_array.getString((i*20) + j).equalsIgnoreCase(null) || values_array.getString((i*20) + j).length() == 0)){
										if (j<3){
											temp.setField("data"+j, values_array.get((i*20) + j));
										}
										else{
											temp.setField("data"+j, Double.parseDouble(values_array.getString((i*20) + j).replace(",", "")));
										}
									}
								}
								temp.setNoteid(Long.valueOf(noteid));
								temp.setPlanid(planid);
								temp.setType(Long.valueOf(2));
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								dao.PeaceCrud(temp, "DataMinPlan3", "save", (long) 0, 0, 0, null);
							}

							// === BORLUULALT ===
							response = jxml.selectQueryTags("//B_borluulalt/Row1/*");
							tags_array = (JSONArray) response.get("tags");
							values_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");

							for(int i=0;i<(int)(tags_array.length()/20);i++){
								DataMinPlan3 temp = new DataMinPlan3();
								for(int j=0;j<20;j++){
									if ((j > 0) && !(values_array.getString((i*20) + j).equalsIgnoreCase(null) || values_array.getString((i*20) + j).length() == 0)){
										if (j<3){
											temp.setField("data"+j, values_array.get((i*20) + j));
										}
										else{
											temp.setField("data"+j, Double.parseDouble(values_array.getString((i*20) + j).replace(",", "")));
										}
									}
								}
								temp.setNoteid(Long.valueOf(noteid));
								temp.setPlanid(planid);
								temp.setType(Long.valueOf(3));
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								dao.PeaceCrud(temp, "DataMinPlan3", "save", (long) 0, 0, 0, null);
								//System.out.println(temp.toString());
							}

							// === BORLUULALT / DOTOOD ===
							response = jxml.selectQueryTags("//B_borluulalt/Row2/*");
							tags_array = (JSONArray) response.get("tags");
							values_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");

							for(int i=0;i<(int)(tags_array.length()/20);i++){
								DataMinPlan3 temp = new DataMinPlan3();
								for(int j=0;j<20;j++){
									if ((j > 0) && !(values_array.getString((i*20) + j).equalsIgnoreCase(null) || values_array.getString((i*20) + j).length() == 0)){
										if (j<3){
											temp.setField("data"+j, values_array.get((i*20) + j));
										}
										else{
											temp.setField("data"+j, Double.parseDouble(values_array.getString((i*20) + j).replace(",", "")));
										}
									}
								}
								temp.setNoteid(Long.valueOf(noteid));
								temp.setPlanid(planid);
								temp.setType(Long.valueOf(4));
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								dao.PeaceCrud(temp, "DataMinPlan3", "save", (long) 0, 0, 0, null);
								//System.out.println(temp.toString());
							}

							// === BORLUULALT / GADAAD ===
							response = jxml.selectQueryTags("//B_borluulalt/Row3/*");
							tags_array = (JSONArray) response.get("tags");
							values_array = (JSONArray) response.get("values");
							parent_array = (JSONArray) response.get("parent");

							for(int i=0;i<(int)(tags_array.length()/20);i++){
								DataMinPlan3 temp = new DataMinPlan3();
								for(int j=0;j<20;j++){
									if ((j > 0) && !(values_array.getString((i*20) + j).equalsIgnoreCase(null) || values_array.getString((i*20) + j).length() == 0)){
										if (j<3){
											temp.setField("data"+j, values_array.get((i*20) + j));
										}
										else{
											temp.setField("data"+j, Double.parseDouble(values_array.getString((i*20) + j).replace(",", "")));
										}
									}
								}
								temp.setNoteid(Long.valueOf(noteid));
								temp.setPlanid(planid);
								temp.setType(Long.valueOf(5));
								if (transitions != null && transitions.size() > 0){
									temp.setIstodotgol(transitions.get(0).getIstodotgol());
								}
								else{
									temp.setIstodotgol(false);
								}
								dao.PeaceCrud(temp, "DataMinPlan3", "save", (long) 0, 0, 0, null);
								//System.out.println(temp.toString());
							}
							/*
						int i = 0;
						while(!parent_array.getString(i*19).equalsIgnoreCase("Header_dotood")){
							DataMinPlan3 temp = new DataMinPlan3();
							for(int j=0;j<19;j++){
								if (!(values_array.getString((i*19) + j).equalsIgnoreCase(null) || values_array.getString((i*19) + j).length() == 0)){
									if (j<2){
										temp.setField("data"+(j+1), values_array.get((i*19) + j));
									}
									else{
										temp.setField("data"+(j+1), Double.parseDouble(values_array.getString((i*19) + j)));
									}
								}
							}
							temp.setNoteid(Long.valueOf(noteid));
							temp.setPlanid(planid);
							temp.setParentid(Long.valueOf(3));
							System.out.println(temp.toString());
							i++;
						}*/

						}
					} catch(Exception e){
						e.printStackTrace();
						return "nodata";
					}
					/*if(noteid==126){
		                	 NodeList G23 = node.getChildNodes();
		                	for (int i = 0; i < list.getLength(); i++) {
			                	 System.out.println("note "+list.item(i).getLocalName());	
			                	 node = list.item(i);
		                       //  list = node.getChildNodes();

		                       //  NodeList G1 = node.getChildNodes();
		                      //   NodeList G23 = node.getChildNodes();
		                         NodeList G4 = node.getChildNodes();
		                         NodeList G5 = node.getChildNodes();
		                         NodeList G6 = node.getChildNodes();
		                         NodeList G7 = node.getChildNodes();
		                         NodeList G8 = node.getChildNodes();
		                         NodeList G9 = node.getChildNodes();
		                         NodeList G10 = node.getChildNodes();
		                         NodeList G11 = node.getChildNodes();
		                         NodeList G12 = node.getChildNodes();
		                         NodeList G13 = node.getChildNodes();
		                         NodeList G14 = node.getChildNodes();

			                		 NodeList G1 = node.getChildNodes();

				                     for (int j = 0; j < G1.getLength(); j++) {
						                	System.out.println("3 "+G1.item(j).getLocalName());;						                	   

						                	   if("Group1".equalsIgnoreCase(G1.item(j).getLocalName())){
						                		   node = G1.item(j);			
						                		   list = node.getChildNodes();

							                	     for (int d = j; d < list.getLength(); d++) {
							                	    	 System.out.println("4 "+list.item(d).getLocalName());;
							                	    	 if("Table1".equalsIgnoreCase(list.item(d).getLocalName())){
							                	    		 node = list.item(d);
							                	    		 list = node.getChildNodes();

								  		                	   for (int t = d; t < list.getLength(); t++) {
								  		                		   System.out.println("5 "+list.item(t).getLocalName());;

								  		                		   int parentid=0;
								  		                	 	   if("Row".equalsIgnoreCase(list.item(t).getLocalName())){
											                		    node = list.item(t);	
											                		    Element eElement = (Element) node;
											                		    DataGeoPlan1 dp= new DataGeoPlan1();
											                		    parentid=Integer.parseInt(eElement.getElementsByTagName("Data_index").item(0).getTextContent());
									  		                		    dp.setData1(eElement.getElementsByTagName("Data1").item(0).getTextContent());							  		                		 
									  		                		    if(eElement.getElementsByTagName("Data_index").item(0).getTextContent().toString().length()>0){
									  		                		    	 dp.setDataIndex(Integer.parseInt(eElement.getElementsByTagName("Data_index").item(0).getTextContent()));
									  		                		    }
									  		                		    dp.setExplorationId(planid);



									  		                		    dao.PeaceCrud(dp, "DataGeoPlan1", "save", (long) 0, 0, 0, null);
											                	   }
								  		                		   if("Row1".equalsIgnoreCase(list.item(t).getLocalName())){
								  			                		    node = list.item(t);		
								  			                		    Element eElement = (Element) node;
								  			                		    DataGeoPlan1 dp= new DataGeoPlan1();

									  		                		    dp.setData1(eElement.getElementsByTagName("Data1").item(0).getTextContent());
									  		                		    dp.setData2(eElement.getElementsByTagName("Data2").item(0).getTextContent());
									  		                		    if(eElement.getElementsByTagName("Data3").item(0).getTextContent().toString().length()>0){
									  		                		    	  dp.setData3(Integer.parseInt(eElement.getElementsByTagName("Data3").item(0).getTextContent()));
									  		                		    }
									  		                		    if(eElement.getElementsByTagName("Data4").item(0).getTextContent().toString().length()>0){
									  		                		    	  dp.setData4(Integer.parseInt(eElement.getElementsByTagName("Data4").item(0).getTextContent()));
									  		                		    }
									  		                		    if(eElement.getElementsByTagName("Data5").item(0).getTextContent().toString().length()>0){
									  		                		    	 dp.setData5(Integer.parseInt(eElement.getElementsByTagName("Data5").item(0).getTextContent()));
									  		                		    }
									  		                		    if(eElement.getElementsByTagName("Data6").item(0).getTextContent().toString().length()>0){
									  		                		    	  dp.setData6(Integer.parseInt(eElement.getElementsByTagName("Data6").item(0).getTextContent()));
									  		                		    }
										  		                		if(eElement.getElementsByTagName("Data7").item(0).getTextContent().toString().length()>0){
									  		                		    	  dp.setData6(Integer.parseInt(eElement.getElementsByTagName("Data7").item(0).getTextContent()));
									  		                		    }
									  		                		    if(eElement.getElementsByTagName("Data_index").item(0).getTextContent().toString().length()>0){
									  		                		    	 dp.setDataIndex(Integer.parseInt(eElement.getElementsByTagName("Data_index").item(0).getTextContent()));
									  		                		    }
									  		                		    dp.setExplorationId(planid);
									  		                		    dp.setParentIndex(parentid);


									  		                		    dao.PeaceCrud(dp, "DataGeoPlan1", "save", (long) 0, 0, 0, null);
								  			                		  //  dao.PeaceCrud(null, "DataGeoReport31", "delete", planid, 0, 0, "explorationId");		
								  			                	   }
								  		                	   }
							                	    	 }
							                	     } 
						                	   }
					                 }





				                     for (int j = 0; j < G23.getLength(); j++) {

				                    	  if("G23".equalsIgnoreCase(list.item(i).getLocalName())) {
				                    		  node = list.item(i);
				                    		  NodeList sss=node.getChildNodes();
				                    		  for(int c = 0; c < sss.getLength(); c++){
				                    			  System.out.println("G23 "+sss.item(c).getLocalName());;
				                    			  if("Row1".equalsIgnoreCase(sss.item(c).getLocalName())){
							                		    node = list.item(c);	
							                		    Element eElement = (Element) node;
							                		    DataGeoPlan1 dp= new DataGeoPlan1();
					  		                		    dp.setData1(eElement.getElementsByTagName("Data1").item(0).getTextContent());							  		                		 
					  		                		    if(eElement.getElementsByTagName("Data_index").item(0).getTextContent().toString().length()>0){
					  		                		    	 dp.setDataIndex(Integer.parseInt(eElement.getElementsByTagName("Data_index").item(0).getTextContent()));
					  		                		    }
					  		                		    dp.setExplorationId(planid);
					  		                		    dp.setParentIndex(0);


					  		                		    dao.PeaceCrud(dp, "DataGeoPlan1", "save", (long) 0, 0, 0, null);
							                	   }  
								             	    if("Row2".equalsIgnoreCase(sss.item(c).getLocalName())){
							                		    node = list.item(c);	
							                		    Element eElement = (Element) node;
							                		    DataGeoPlan1 dp= new DataGeoPlan1();
					  		                		    dp.setData1(eElement.getElementsByTagName("Data1").item(0).getTextContent());							  		                		 
					  		                		    if(eElement.getElementsByTagName("Data_index").item(0).getTextContent().toString().length()>0){
					  		                		    	 dp.setDataIndex(Integer.parseInt(eElement.getElementsByTagName("Data_index").item(0).getTextContent()));
					  		                		    }
					  		                		    dp.setExplorationId(planid);
					  		                		    dp.setParentIndex(0);


					  		                		    dao.PeaceCrud(dp, "DataGeoPlan1", "save", (long) 0, 0, 0, null);
							                	   }
				                    		  }
				                    	  }
					                 }

		                	 }


		                }*/


					/*if(noteid==71){
	                			 for (int i = 0; i < list.getLength(); i++) {
				                	 System.out.println("2 "+list.item(i).getLocalName());				                	
				                	 if("Table1".equalsIgnoreCase(list.item(i).getLocalName())) {
				                        node = list.item(i);
				                        list = node.getChildNodes();
					                     for (int j = 0; j < list.getLength(); j++) {
							                	System.out.println("3 "+list.item(j).getLocalName());;

							                	   if("Row1".equalsIgnoreCase(list.item(j).getLocalName())){
							                		   node = list.item(j);			
							                		   list = node.getChildNodes();

								                	     for (int d = j; d < list.getLength(); d++) {
								                	    	 System.out.println("4 "+list.item(d).getLocalName());;
								                	    	 if("Table2".equalsIgnoreCase(list.item(d).getLocalName())){
								                	    		 node = list.item(d);
								                	    		 list = node.getChildNodes();

									  		                	   for (int t = d; t < list.getLength(); t++) {
									  		                		   System.out.println("5 "+list.item(t).getLocalName());;
									  		                		   if("Row1".equalsIgnoreCase(list.item(t).getLocalName())){
									  			                		    node = list.item(t);		
									  			                		    Element eElement = (Element) node;
										  			                		DataGeoReport31 dp= new DataGeoReport31();

										  		                		    dp.setData1(eElement.getElementsByTagName("Data1").item(0).getTextContent());
										  		                		    dp.setData2(eElement.getElementsByTagName("Data2").item(0).getTextContent());
										  		                		    if(eElement.getElementsByTagName("Data3").item(0).getTextContent().toString().length()>0){
										  		                		    	  dp.setData3(Integer.parseInt(eElement.getElementsByTagName("Data3").item(0).getTextContent()));
										  		                		    }
										  		                		    if(eElement.getElementsByTagName("Data4").item(0).getTextContent().toString().length()>0){
										  		                		    	  dp.setData4(Integer.parseInt(eElement.getElementsByTagName("Data4").item(0).getTextContent()));
										  		                		    }
										  		                		    if(eElement.getElementsByTagName("Data5").item(0).getTextContent().toString().length()>0){
										  		                		    	 dp.setData5(Integer.parseInt(eElement.getElementsByTagName("Data5").item(0).getTextContent()));
										  		                		    }
										  		                		    if(eElement.getElementsByTagName("Data6").item(0).getTextContent().toString().length()>0){
										  		                		    	  dp.setData6(eElement.getElementsByTagName("Data6").item(0).getTextContent());
										  		                		    }
										  		                		    if(eElement.getElementsByTagName("Data_index").item(0).getTextContent().toString().length()>0){
										  		                		    	 dp.setDataIndex(Integer.parseInt(eElement.getElementsByTagName("Data_index").item(0).getTextContent()));
										  		                		    }
										  		                		    dp.setExplorationId(planid);



										  		                		    dao.PeaceCrud(dp, "DataGeoReport31", "save", (long) 0, 0, 0, null);
									  			                		  //  dao.PeaceCrud(null, "DataGeoReport31", "delete", planid, 0, 0, "explorationId");		
									  			                	   }
									  		                	   }
								                	    	 }
								                	     } 
							                	   }
						                 }
				                     }
				                	 else if("Table2".equalsIgnoreCase(list.item(i).getLocalName())) {
					                        node = list.item(i);		
					                        list = node.getChildNodes();
							                for (int j = 0; j < list.getLength(); j++) {
							                	System.out.println("3 "+list.item(j).getLocalName());;

							                	   if("Row1".equalsIgnoreCase(list.item(j).getLocalName())){
							                		   node = list.item(j);
								                	   list = node.getChildNodes();

								                	   for (int d = 0; d < list.getLength(); d++) {
								                		   System.out.println("4 "+list.item(d).getLocalName());;
								                		   if("Table3".equalsIgnoreCase(list.item(d).getLocalName())){
									                		   node = list.item(d);
										                	   list = node.getChildNodes();

										                	   for (int t = 0; t < list.getLength(); t++) {
										                		   System.out.println("5 "+list.item(t).getLocalName());;
										                		   if("Row1".equalsIgnoreCase(list.item(t).getLocalName())){
											                		   node = list.item(t);
												                	   list = node.getChildNodes();

												                	   //node = list.item(l);
											                		    Element eElement = (Element) node;
											                		    dao.PeaceCrud(null, "DataGeoReport32", "delete", planid, 0, 0, "explorationId");


											                			DataGeoReport32 dp= new DataGeoReport32();

											                		    dp.setData1(eElement.getElementsByTagName("Data1").item(0).getTextContent());
											                		    dp.setData2(eElement.getElementsByTagName("Data2").item(0).getTextContent());
											                		    if(eElement.getElementsByTagName("Data3").item(0).getTextContent().toString().length()>0){
											                		    	  dp.setData3(Integer.parseInt(eElement.getElementsByTagName("Data3").item(0).getTextContent()));
											                		    }
											                		    if(eElement.getElementsByTagName("Data4").item(0).getTextContent().toString().length()>0){
											                		    	  dp.setData4(Integer.parseInt(eElement.getElementsByTagName("Data4").item(0).getTextContent()));
											                		    }
											                		    if(eElement.getElementsByTagName("Data5").item(0).getTextContent().toString().length()>0){
											                		    	 dp.setData5(Integer.parseInt(eElement.getElementsByTagName("Data5").item(0).getTextContent()));
											                		    }
											                		    if(eElement.getElementsByTagName("Data6").item(0).getTextContent().toString().length()>0){
											                		    	  dp.setData6(Integer.parseInt(eElement.getElementsByTagName("Data6").item(0).getTextContent()));
											                		    }
											                		    if(eElement.getElementsByTagName("Data7").item(0).getTextContent().toString().length()>0){
											                		    	  dp.setData7(eElement.getElementsByTagName("Data6").item(0).getTextContent());
											                		    }
											                		    if(eElement.getElementsByTagName("Data_index").item(0).getTextContent().toString().length()>0){
											                		    	 dp.setDataIndex(Integer.parseInt(eElement.getElementsByTagName("Data_index").item(0).getTextContent()));
											                		    }
											                		    dp.setExplorationId(planid);



											                		    dao.PeaceCrud(dp, "DataGeoReport32", "save", (long) 0, 0, 0, null);


											                	   }
										                	   }

									                	   }
								                	   }

							                	   }
							                }
					                     }
			                	 }

		                }*/

				}
				/*			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(appPath+"/"+at.getAttachfiletype()));
		        AcroFields form1 = stamper.getAcroFields();
		        form1.removeXfa();
		        Map<String, AcroFields.Item> fields = form1.getFields();
		        for (String name : fields.keySet()) {
		            if (name.indexOf("Total") > 0)
		            	form1.setFieldProperty(name, "textcolor", BaseColor.RED, null);
		            form1.setField(name, "X");
		        }
		        stamper.close();
		        reader.close();*/
			}
			//   PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
			//     AcroFields form = stamper.getAcroFields();
			//   form.setField("Name", "1.0", "100%");
			//    form.setField("Company", "1217000.000000", "$1,217,000");

			//    System.out.println("@@@"+form.getField("YEAR")+fl.isFile()+fl.getName());
			//     form.setField("YEAR", "2222");
			//   stamper.close();
			//   reader.close();




			System.out.println("end irchihlee");
		} catch (Exception e) {
			throw new Exception("Aspose: Unable to export to ms word format.. some error occured",e);

		}
		return "true";
	}






	public  void limitedPdf(ServletOutputStream out,HttpServletRequest req, int plan, int id) throws DocumentException, IOException ,Exception {

		try { 

			String appPath = req.getServletContext().getRealPath("");

			AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+plan+"", "current");

			SubLegalpersons sbl= (SubLegalpersons) dao.getHQLResult("from SubLegalpersons t where t.lpReg='"+an.getLpReg()+"'", "current");

			LutFormNotes fn=(LutFormNotes) dao.getHQLResult("from LutFormNotes t where t.id="+id+"", "current");

			File fl= new File(appPath + "/" +fn.getUrl());


			PdfReader reader = new PdfReader(appPath+fn.getUrl());
			PdfStamper stamper = new PdfStamper(reader, out);
			AcroFields form = stamper.getAcroFields();

			XfaForm xfa = form.getXfa();

			if(xfa.isXfaPresent()){                
				Node node = xfa.getDatasetsNode();
				Node nodefooter = xfa.getDatasetsNode();
				NodeList list = node.getChildNodes();

				NodeList footerlist = node.getChildNodes();

				NodeList flist = node.getChildNodes();
				NodeList glist = node.getChildNodes();
				xfa.getDomDocument();

				for (int i = 0; i < list.getLength(); i++) {
					System.out.println("1 "+list.item(i).getLocalName());;
					if("data".equalsIgnoreCase(list.item(i).getLocalName())) {
						System.out.println("1aaaa "+list.item(i).getLocalName());;
						node = list.item(i);
						//break;
					}
				}
				list = node.getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					System.out.println("2 "+list.item(i).getLocalName());;
					if("form1".equalsIgnoreCase(list.item(i).getLocalName())) {
						node = list.item(i);
						break;
					}
				}
				list = node.getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					System.out.println("2 "+list.item(i).getLocalName());;
					if("MainForm".equalsIgnoreCase(list.item(i).getLocalName())) {
						node = list.item(i);	
						// break;
					}
				}
				list = node.getChildNodes();
				nodefooter=node;

				for (int i = 0; i < nodefooter.getChildNodes().getLength(); i++) {
					if("General_footer".equalsIgnoreCase(list.item(i).getLocalName())) {
						nodefooter = list.item(i);	
						// break;
					}
				}

				flist = nodefooter.getChildNodes();

				for (int i = 0; i < flist.getLength(); i++) {
					System.out.println("General_footer "+flist.item(i).getLocalName());;                	  

					if("geologyst".equalsIgnoreCase(flist.item(i).getLocalName())) {
						flist.item(i).setTextContent(sbl.getGEOLOGIST());
						xfa.fillXfaForm(node);
					}                
					else if("accountant".equalsIgnoreCase(flist.item(i).getLocalName())) {
						flist.item(i).setTextContent(sbl.getACCOUNTANT());
						xfa.fillXfaForm(node);
					}
					else if("economist".equalsIgnoreCase(flist.item(i).getLocalName())) {
						flist.item(i).setTextContent(sbl.getECONOMIST());
						xfa.fillXfaForm(node);
					} 
					else if("givName".equalsIgnoreCase(flist.item(i).getLocalName())) {
						flist.item(i).setTextContent(sbl.getGivName());
						xfa.fillXfaForm(node);
					} 
					else if("minehead".equalsIgnoreCase(flist.item(i).getLocalName())) {
						flist.item(i).setTextContent(sbl.getMinehead());
						xfa.fillXfaForm(node);
					} 
					else if("holderName".equalsIgnoreCase(flist.item(i).getLocalName())) {
						flist.item(i).setTextContent(sbl.getLpName()+" " +sbl.getLutLptype().getLptype());
						xfa.fillXfaForm(node);
					}
					else if("Holder_name".equalsIgnoreCase(flist.item(i).getLocalName())) {
						flist.item(i).setTextContent(sbl.getLpName()+" " +sbl.getLutLptype().getLptype());
						xfa.fillXfaForm(node);
					}

					else if("FormID".equalsIgnoreCase(flist.item(i).getLocalName())) {
						flist.item(i).setTextContent(String.valueOf(fn.getId()));
						xfa.fillXfaForm(node);
					}

					/*else if("Officer".equalsIgnoreCase(flist.item(i).getLocalName())) {
						flist.item(i).setTextContent(sbl.getKEYMAN());
						xfa.fillXfaForm(node);
					}*/
					else if("Engineering1".equalsIgnoreCase(flist.item(i).getLocalName())) {						
						flist.item(i).setTextContent(sbl.getGENGINEER());
						xfa.fillXfaForm(node);
					}
					else if("Engineering2".equalsIgnoreCase(flist.item(i).getLocalName())) {
						flist.item(i).setTextContent(sbl.getACCOUNTANT());
						xfa.fillXfaForm(node);
					}


				}

				RegReportReq rreq= (RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+an.getReqid()+"'", "current");
				LnkReqAnn ann=null;
				LnkReqPv pnn=null;
				if(rreq.getLnkReqAnns().size()>0){
					List<LnkReqAnn> ran= rreq.getLnkReqAnns();
					ann=ran.get(0);
				}
				if(rreq.getLnkReqPvs().size()>0){
					pnn=rreq.getLnkReqPvs().get(0);
				}
				String formname="";
				for (int i = 0; i < list.getLength(); i++) {
					System.out.println("fffff "+list.item(i).getLocalName());;                	  
					if("FormID".equalsIgnoreCase(list.item(i).getLocalName())) {
						formname=list.item(i).getTextContent();		
					}
				}
				for (int i = 0; i < list.getLength(); i++) {
					System.out.println("fffff "+list.item(i).getLocalName());;                	  

					if("Lic_num".equalsIgnoreCase(list.item(i).getLocalName())) {
						list.item(i).setTextContent(an.getLicenseXB());
						xfa.fillXfaForm(node);
					}

					else if("B_technology".equalsIgnoreCase(list.item(i).getLocalName())) {
						list.item(i).setTextContent(ann.getConcetrate());
						xfa.fillXfaForm(node);
					}
					else if("FormID".equalsIgnoreCase(list.item(i).getLocalName())) {
						formname=list.item(i).getTextContent();					
						list.item(i).setTextContent(String.valueOf(id));
						xfa.fillXfaForm(node);
					}
					else if("MinType".equalsIgnoreCase(list.item(i).getLocalName())) {
						System.out.println("%%%%%%%%%"+list.item(i).getTextContent());
						if(an.getMinid()!=0){
							if((ann!=null) && (formname.equalsIgnoreCase("Mining_plan_1.1") || formname.equalsIgnoreCase("Mining_plan_1.2") 
									|| formname.equalsIgnoreCase("Mining_plan_2.1") || formname.equalsIgnoreCase("Mining_plan_2.2") 
									|| formname.equalsIgnoreCase("Mining_plan_2_3") || formname.equalsIgnoreCase("Mining_plan_3.1") 
									|| formname.equalsIgnoreCase("Mining_plan_3.2") || formname.equalsIgnoreCase("Mining_plan_4.1") 
									|| formname.equalsIgnoreCase("Mining_plan_4.2"))){
								System.out.println("ann ^^^^ "+ann.getDeposidid());
								LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+ann.getDeposidid()+"'", "current");
								list.item(i).setTextContent(String.valueOf(qwe.getPnum()));
							}
							else if (an.getDepositid() != null){
									LutDeposit qwe= (LutDeposit) dao.getHQLResult("from LutDeposit t where t.id='"+an.getDepositid()+"'", "current");
									list.item(i).setTextContent(String.valueOf(qwe.getDepositnamemon()));
							}
							else{
								RegReportReq reqqq = an.getRegReportReq();
								if (reqqq != null){
									LutMinGroup qwe= (LutMinGroup) dao.getHQLResult("from LutMinGroup t where t.id='"+reqqq.getGroupid()+"'", "current");
									if (qwe != null){
										list.item(i).setTextContent(String.valueOf(qwe.getGroupname()));
									}
								}
								
							}
						}
						xfa.fillXfaForm(node);
					}
					else if("holderName".equalsIgnoreCase(list.item(i).getLocalName())) {
						//Node year1 = list.item(j);
						list.item(i).setTextContent(sbl.getLpName()+" " +sbl.getLutLptype().getLptype());
						xfa.fillXfaForm(node);
					}
					else if("Holder_name".equalsIgnoreCase(list.item(i).getLocalName())) {
						list.item(i).setTextContent(sbl.getLpName()+" " +sbl.getLutLptype().getLptype());
						xfa.fillXfaForm(node);
					}
					else if("Year1".equalsIgnoreCase(list.item(i).getLocalName())) {
						list.item(i).setTextContent(an.getReportyear());
						xfa.fillXfaForm(node);
					}
					else if("RegID".equalsIgnoreCase(list.item(i).getLocalName())) {
						list.item(i).setTextContent(an.getLpReg());
						xfa.fillXfaForm(node);
					}

					else if("PlanID".equalsIgnoreCase(list.item(i).getLocalName())) {
						list.item(i).setTextContent(String.valueOf(an.getId()));
						xfa.fillXfaForm(node);
					}
					else if("givName".equalsIgnoreCase(list.item(i).getLocalName())) {
						String f=sbl.getFamName();
						list.item(i).setTextContent(f.substring(0,1).toUpperCase()+"."+sbl.getGivName());
					}
					else if("geologist".equalsIgnoreCase(list.item(i).getLocalName())) {
						list.item(i).setTextContent(sbl.getGEOLOGIST());
						xfa.fillXfaForm(node);
					}                
					else if("accountant".equalsIgnoreCase(list.item(i).getLocalName())) {
						list.item(i).setTextContent(sbl.getACCOUNTANT());
						xfa.fillXfaForm(node);
					}
					else if("economist".equalsIgnoreCase(list.item(i).getLocalName())) {
						list.item(i).setTextContent(sbl.getECONOMIST());
						xfa.fillXfaForm(node);
					} 
					else if("msurveyor".equalsIgnoreCase(list.item(i).getLocalName())) {
						list.item(i).setTextContent(sbl.getMSURVEYOR());
						xfa.fillXfaForm(node);
					} 
					else if("minehead".equalsIgnoreCase(list.item(i).getLocalName())) {
						list.item(i).setTextContent(sbl.getMinehead());
						xfa.fillXfaForm(node);
					} 

				}
				xfa.fillXfaForm(node);
			}
			else{

				int year = Calendar.getInstance().get(Calendar.YEAR);

				List<LnkReqAnn> ann= an.getRegReportReq().getLnkReqAnns();
				LutMinerals sl= (LutMinerals) dao.getHQLResult("from LutMinerals t where t.id='"+an.getMinid()+"'", "current");   

				form.setField("LicNum", an.getLicenseXB());
				form.setField("economist", sbl.getECONOMIST());
				form.setField("accountant", sbl.getACCOUNTANT());
				form.setField("PlanID", String.valueOf(an.getId()));;
				form.setField("RegID", an.getLpReg());;
				form.setField("FormID", String.valueOf(fn.getId()));;
				form.setField("holderName", sbl.getLpName()+" " +sbl.getLutLptype().getLptype());;
				form.setField("givName", sbl.getFamName().substring(0,1).toUpperCase()+"."+sbl.getGivName());;
				form.setField("geologist", sbl.getGEOLOGIST());;
				form.setField("Year1",  String.valueOf(year));;
				if(ann.size()>0){
					form.setField("BTech",  ann.get(0).getConcetrate());;
				}
				form.setField("MinType",  sl.getMineralnamemon());;
				form.setField("MineType",  sl.getMineralnamemon());;
				form.setField("engineer",  sbl.getGENGINEER());;
				form.setField("mineHead",  sbl.getMinehead());;
			}

			stamper.close();
			reader.close();



			System.out.println("end irchihlee");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}


	@ResponseBody 
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/createXplanXreport/{y}/{type}/{id}", method = RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public String createXplanXreport(@PathVariable Integer y,@PathVariable Long type, @PathVariable int id, HttpServletRequest req) throws ClassNotFoundException, JSONException{

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			com.peace.users.model.mram.RegReportReq lr=(com.peace.users.model.mram.RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+id+"'", "current");

			JSONObject obj=new JSONObject();
			SubLicenses lic=(SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='"+lr.getBundledLicenseNum()+"'", "current");

			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
			Calendar c = Calendar.getInstance(Locale.ENGLISH);
			c.setFirstDayOfWeek(Calendar.FRIDAY);
			c.setTime(new Date());
			int today = c.get(Calendar.DAY_OF_WEEK);
			c.add(Calendar.DAY_OF_WEEK, -today+Calendar.FRIDAY);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
			String formatted = format1.format(c.getTime());
			String formatted1 = format2.format(c.getTime());

			LnkReqAnn ann=(LnkReqAnn) dao.getHQLResult("from LnkReqAnn t where t.reqid='"+lr.getId()+"'", "current");
			List<LutYear> year = (List<LutYear>) dao.getHQLResult("from LutYear t where t.isactive = 1 and t.type = " + type+" and t.divisionid="+lr.getDivisionId()+"", "list");
			List<AnnualRegistration> an=null;
			if(ann!=null){
				if(lr.getDivisionId()==2 || lr.getDivisionId()==1){
					an=(List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.divisionid="+lr.getDivisionId()+" and xtype=0 and t.reporttype="+type+" and  t.depositid="+ann.getDeposidid()+" and t.licensenum='"+lr.getBundledLicenseNum()+"' and t.reportyear='"+y+"'", "list");
				}				
			}else{
				an=(List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.divisionid=3 and xtype=0 and t.reporttype="+type+" and t.licensenum='"+lr.getBundledLicenseNum()+"' and t.reportyear='"+y+"'", "list");
			}
			
			if(an.size()>0){
				obj.put("subdate", "false");	
			}
			else{
				AnnualRegistration pe=new AnnualRegistration();
				pe.setReportyear(String.valueOf(y));
				pe.setLpReg(loguser.getSubLegalpersons().getLpReg());				
				pe.setPersonid(loguser.getId());
				pe.setLicensenum(String.valueOf(lic.getLicenseNum()));

				if (ann==null){
					pe.setLictype((int) 1);
					obj.put("mv", "true");	
				}
				else{
					pe.setLictype((int) lic.getLicTypeId());
					obj.put("mv", "false");	
				}

				pe.setDivisionid(lr.getDivisionId());
				pe.setReporttype(type);
				pe.setLicenseXB(lic.getLicenseXB());
				pe.setRepstatusid((long) 0);
				if(lic.getLicTypeId()==2){
					if(ann!=null){
						pe.setMinid(ann.getMineralid());
					}
					else{
						pe.setMinid(lic.getMintype());
					}					
				}
				else{
					pe.setMinid((long) lr.getGroupid());
				}
				pe.setXtype(0);
				pe.setRepstepid((long) 1);
				pe.setLpName(lic.getLpName());
				pe.setReqid(lr.getId());
				pe.setGroupid(lr.getGroupid());
				if(ann!=null){
					pe.setDepositid(ann.getDeposidid());
				}
				
				dao.PeaceCrud(pe, "PlanExploration", "save",(long) 0 , 0, 0, null);

				if(type==3){
					lr.setXplan(1);
				}
				else{
					lr.setXreport(1);
				}
				lr.setCyear(y);
				dao.PeaceCrud(lr, "RegReportReq", "update",(long) lr.getId() , 0, 0, null);
				if(ann!=null){
					ann.setCyear(y);
					dao.PeaceCrud(ann, "LnkReqAnn", "update",(long) ann.getId() , 0, 0, null);
				}
				

				obj.put("id", pe.getId());	
				obj.put("lic", lic.getLicTypeId());	
				obj.put("divisionid", pe.getDivisionid());	
				obj.put("reporttype", pe.getReporttype());	
				obj.put("reqid", lr.getId());	
				obj.put("subdate", "true");	
				if(lic.getFtime()){
					obj.put("ftime", "true");	
				}
				
			}
			return obj.toString();
		}

		return "false";

	}

	@ResponseBody 
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/createPlan/{y}/{type}/{id}", method = RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public String createReport(@PathVariable Integer y,@PathVariable Long type, @PathVariable int id, HttpServletRequest req) throws ClassNotFoundException, JSONException{

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			com.peace.users.model.mram.RegReportReq lr=(com.peace.users.model.mram.RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+id+"'", "current");

			JSONObject obj=new JSONObject();
			SubLicenses lic=(SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='"+lr.getBundledLicenseNum()+"'", "current");

			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
			Calendar c = Calendar.getInstance(Locale.ENGLISH);
			c.setFirstDayOfWeek(Calendar.FRIDAY);
			c.setTime(new Date());
			int today = c.get(Calendar.DAY_OF_WEEK);
			c.add(Calendar.DAY_OF_WEEK, -today+Calendar.FRIDAY);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
			String formatted = format1.format(c.getTime());
			String formatted1 = format2.format(c.getTime());

			LnkReqAnn ann=(LnkReqAnn) dao.getHQLResult("from LnkReqAnn t where t.reqid='"+lr.getId()+"'", "current");
			List<LutYear> year = (List<LutYear>) dao.getHQLResult("from LutYear t where t.isactive = 1 and t.type = " + type+" and t.divisionid="+lr.getDivisionId()+"", "list");
			List<AnnualRegistration> an=null;
			if(ann!=null){
				if(lr.getDivisionId()==2 || lr.getDivisionId()==1){
					an=(List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.divisionid="+lr.getDivisionId()+" and t.reporttype="+type+" and  t.depositid="+ann.getDeposidid()+" and t.licensenum='"+lr.getBundledLicenseNum()+"' and t.reportyear='"+y+"'", "list");
				}				
			}else{
				an=(List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.divisionid=3 and t.reporttype="+type+" and t.licensenum='"+lr.getBundledLicenseNum()+"' and t.reportyear='"+y+"'", "list");
			}
			
			if(an.size()>0){
				if(lr.getDivisionId()==2 || lr.getDivisionId()==1 || lr.getDivisionId()==3){
					AnnualRegistration pe=new AnnualRegistration();
					pe.setReportyear(String.valueOf(y));
					pe.setLpReg(loguser.getSubLegalpersons().getLpReg());				
					pe.setPersonid(loguser.getId());
					pe.setLicensenum(String.valueOf(lic.getLicenseNum()));

					if (ann==null){
						pe.setLictype((int) 1);
						obj.put("mv", "true");	
					}
					else{
						pe.setLictype((int) lic.getLicTypeId());
						obj.put("mv", "false");	
					}

					pe.setDivisionid(lr.getDivisionId());
					//pe.setDivisionid(lic.getDivisionId());
					pe.setReporttype(type);
					pe.setLicenseXB(lic.getLicenseXB());
					pe.setRepstatusid((long) 0);
					if(lic.getLicTypeId()==2){
						if(ann!=null){
							pe.setMinid(ann.getMineralid());
						}
						else{
							pe.setMinid(lic.getMintype());
						}					
					}
					else{
						pe.setMinid((long) lr.getGroupid());
					}
					pe.setRepstepid((long) 1);
					pe.setLpName(lic.getLpName());
					pe.setReqid(lr.getId());
					pe.setGroupid(lr.getGroupid());
					if(ann!=null){
						pe.setDepositid(ann.getDeposidid());
					}
					
					dao.PeaceCrud(pe, "PlanExploration", "save",(long) 0 , 0, 0, null);


					if(type==3){
						lr.setCplan(1);
					}
					else{
						lr.setCreport(1);
					}
					lr.setCyear(y);
					dao.PeaceCrud(lr, "RegReportReq", "update",(long) lr.getId() , 0, 0, null);
					if(ann!=null){
						ann.setCyear(y);
						dao.PeaceCrud(ann, "LnkReqAnn", "update",(long) ann.getId() , 0, 0, null);
					}
					

					obj.put("id", pe.getId());	
					obj.put("lic", lic.getLicTypeId());	
					obj.put("divisionid", pe.getDivisionid());	
					obj.put("reporttype", pe.getReporttype());	
					obj.put("reqid", lr.getId());	
					obj.put("subdate", "true");	
					if(lic.getFtime()){
						obj.put("ftime", "true");	
					}
				}
				else{
					obj.put("subdate", "false");	
				}
			}
			else{
				AnnualRegistration pe=new AnnualRegistration();
				pe.setReportyear(String.valueOf(y));
				pe.setLpReg(loguser.getSubLegalpersons().getLpReg());				
				pe.setPersonid(loguser.getId());
				pe.setLicensenum(String.valueOf(lic.getLicenseNum()));

				if (ann==null){
					pe.setLictype((int) 1);
					obj.put("mv", "true");	
				}
				else{
					pe.setLictype((int) lic.getLicTypeId());
					obj.put("mv", "false");	
				}

				pe.setDivisionid(lr.getDivisionId());
				//pe.setDivisionid(lic.getDivisionId());
				pe.setReporttype(type);
				pe.setLicenseXB(lic.getLicenseXB());
				pe.setRepstatusid((long) 0);
				if(lic.getLicTypeId()==2){
					if(ann!=null){
						pe.setMinid(ann.getMineralid());
					}
					else{
						pe.setMinid(lic.getMintype());
					}					
				}
				else{
					pe.setMinid((long) lr.getGroupid());
				}
				pe.setRepstepid((long) 1);
				pe.setLpName(lic.getLpName());
				pe.setReqid(lr.getId());
				pe.setGroupid(lr.getGroupid());
				if(ann!=null){
					pe.setDepositid(ann.getDeposidid());
				}
				
				dao.PeaceCrud(pe, "PlanExploration", "save",(long) 0 , 0, 0, null);


				if(type==3){
					lr.setCplan(1);
				}
				else{
					lr.setCreport(1);
				}
				lr.setCyear(y);
				dao.PeaceCrud(lr, "RegReportReq", "update",(long) lr.getId() , 0, 0, null);
				if(ann!=null){
					ann.setCyear(y);
					dao.PeaceCrud(ann, "LnkReqAnn", "update",(long) ann.getId() , 0, 0, null);
				}
				

				obj.put("id", pe.getId());	
				obj.put("lic", lic.getLicTypeId());	
				obj.put("divisionid", pe.getDivisionid());	
				obj.put("reporttype", pe.getReporttype());	
				obj.put("reqid", lr.getId());	
				obj.put("subdate", "true");	
				if(lic.getFtime()){
					obj.put("ftime", "true");	
				}
				
			}
			 
			/*else if (year == null || year.size() != 1){
				obj.put("subdate", "year");	
			}*/

			return obj.toString();



		}

		return "false";

	}



	@RequestMapping(value="/removeBundle/{id}", method = RequestMethod.DELETE,produces={"application/json; charset=UTF-8"})
	public  @ResponseBody String removeBundle(@PathVariable long id, HttpServletRequest req) throws ClassNotFoundException, JSONException{

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			JSONObject obj1 = new JSONObject();
			com.peace.users.model.mram.RegReportReq lr=(com.peace.users.model.mram.RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+id+"'", "current");
			if(lr.getAnnualRegistrations().size()>0){
				obj1.put("success", "created");	
			}else{
				SubLicenses lic=(SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='"+lr.getBundledLicenseNum()+"'", "current");
				lic.setConfigured(0);
				if(lr.getLnkReportRegBunl().size()>0){
					List rs=lr.getLnkReportRegBunl();
					for(int i=0;i<rs.size();i++){
						LnkReportRegBunl bund=(LnkReportRegBunl) rs.get(i);
						List<SubLicenses> bundc=(List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.licenseNum='"+bund.getAddBunLicenseNum()+"'", "list");
						if(bundc.size()>0){
							bundc.get(0).setConfigured(0);
							dao.PeaceCrud(bundc, "SubLicenses", "update", bundc.get(0).getId(), 0, 0, null);
						}
						//ustgah bol
						dao.PeaceCrud(null, "LnkReportRegBunl", "delete",bund.getId() , 0, 0, null);
					}
				}
				dao.PeaceCrud(lic, "SubLicenses", "update", lic.getId(), 0, 0, null);
				lr.setIsactive(false);
				dao.PeaceCrud(lr, "RegReportReq", "update", lr.getId(), 0, 0, null);
				//dao.PeaceCrud(lr, "RegReportReq", "delete", lr.getId(), 0, 0, null);
				obj1.put("success", "success");
			}
			return obj1.toString();	

		}

		return "false";

	}

	@RequestMapping(value="/submitPlanDetail",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String ajaxconf(@RequestBody String jsonString) throws JSONException{
		System.out.println(jsonString);
		//DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date date1 = new Date();
		String special = dateFormat1.format(date1);
		JSONObject obj= new JSONObject(jsonString);
		LnkPlanNotes lp=null;
		List<LnkPlanNotes> rs= (List<LnkPlanNotes>) dao.getHQLResult("from LnkPlanNotes t where t.noteid='"+obj.getLong("id")+"' and t.expid='"+obj.getLong("planid")+"'", "list");
		if(rs.size()>0){
			lp=rs.get(0); 
			lp.setNoteid(obj.getLong("id"));
			lp.setNotes(obj.getString("content"));
			lp.setExpid(obj.getLong("planid"));
			lp.setNotedate(special);
			dao.PeaceCrud(lp, "LnkPlanNotes", "update", (long) lp.getId(), 0, 0, null);
		}
		else{
			lp = new LnkPlanNotes();
			lp.setNoteid(obj.getLong("id"));
			lp.setNotes(obj.getString("content"));
			lp.setExpid(obj.getLong("planid"));
			lp.setNotedate(special);
			dao.PeaceCrud(lp, "LnkPlanNotes", "save", (long) 0, 0, 0, null);
		}		


		return "true";
	}

	@RequestMapping(value="/submitWeekComment",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String ajaxMainStepWeek(@RequestBody String jsonString) throws JSONException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date1 = new Date();
			String special = dateFormat.format(date1);
			JSONObject obj= new JSONObject(jsonString);

			WeeklyRegistration an=(WeeklyRegistration) dao.getHQLResult("from WeeklyRegistration t where t.id="+obj.getInt("appid")+"", "current");
			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

			//an.setRepstatusid(obj.getLong("appstatus"));
			// an.setRepstepid(obj.getLong("appstep"));
			an.setOfficerid(loguser.getId());
			an.setApproveddate(special);
			an.setRepstatusid(obj.getLong("stepid"));
			dao.PeaceCrud(an, "WeeklyRegistration", "update", (long) obj.getLong("appid"), 0, 0, null);
			
			List<WeeklyMainData> wm=(List<WeeklyMainData>) dao.getHQLResult("from WeeklyMainData t where t.planid="+an.getReqid()+"", "list");
			
			LutWeeks lw=(LutWeeks) dao.getHQLResult("from LutWeeks t where t.year="+an.getYear()+" and t.week="+an.getWeekid()+"", "current");
			
			List<LutWeeks> lws=(List<LutWeeks>) dao.getHQLResult("from LutWeeks t where t.year="+an.getYear()+" and t.month="+lw.getMonth()+"", "list");
			
			if(obj.getInt("stepid")==1){
				for(WeeklyMainData item:wm){
					double total=0;
					for(LutWeeks witem:lws){
						String filename="data"+witem.getWeek();
						total=total + item.getField(filename,item);
					}
									
					int fnum=57+lw.getMonth();
					String filename="data"+fnum;	
					double season1=0;
					double season2=0;
					double season3=0;
					double season4=0;
					
					item.setField(filename, total);
					
					season1=item.getData58()+item.getData59()+item.getData60();
					season2=item.getData61()+item.getData62()+item.getData63();
					season3=item.getData64()+item.getData65()+item.getData66();
					season4=item.getData67()+item.getData68()+item.getData69();
					
					item.setField("data70", season1);
					item.setField("data71", season2);
					item.setField("data72", season3);
					item.setField("data73", season4);
					item.setExecution(season1+season2+season3+season4);
					if(item.getData75()!=0){
						item.setWpercentage((season1+season2+season3+season4)/item.getData75());
					}
					else{
						item.setWpercentage(0);
					}
					
					
				    dao.PeaceCrud(item, "WeeklyMainData", "update", (long) item.getId(), 0, 0, null);
				}
			}
			

			LnkCommentWeekly mn = new LnkCommentWeekly();
			mn.setMcomment(obj.getString("comtext"));
			mn.setWrid(obj.getLong("appid"));
			mn.setUserid(loguser.getId());
			mn.setComdate(special);
			mn.setUsername(loguser.getGivnamemon());
			mn.setDesid(obj.getLong("stepid"));
			dao.PeaceCrud(mn, "LnkCommentWeekly", "save", (long) 0, 0, 0, null);		 

		}
		return "true";
	}

	@RequestMapping(value="/submitTabDesicion/{id}",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String submitTabDesicion(@RequestBody String jsonString, @PathVariable int id) throws JSONException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {	
			System.out.println(jsonString);
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date1 = new Date();
			String special = dateFormat.format(date1);
			JSONObject obj= new JSONObject(jsonString);


			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

			AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+obj.getInt("appid")+"", "current");

			List<LutFormNotes> lf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.inptype="+id+" and t.reporttype="+an.getReporttype()+" and t.divisionid="+loguser.getDivisionid()+"", "list");
			for(int i=0;i<lf.size();i++){
				LnkComment com=new LnkComment();
				com.setPlanid(obj.getInt("appid"));
				com.setOfficerid(loguser.getId());
				com.setComdate(special);
				com.setComnote(obj.getString("comtext"));
				com.setNoteid(lf.get(i).getId());
				com.setDesicionid(obj.getInt("appstatus"));
				com.setOfficername(loguser.getGivnamemon());
				dao.PeaceCrud(com, "LnkComment", "save", (long) 0, 0, 0, null);

				LnkPlanTransition ts=(LnkPlanTransition) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("appid")+" and t.noteid="+lf.get(i).getId()+"", "current");
				ts.setDecisionid(obj.getInt("appstatus"));
				dao.PeaceCrud(ts, "LnkPlanTransition", "save", an.getId(), 0, 0, null);

			}

			return "true";
		}
		return null;
	}

	
	@RequestMapping(value="/submitXreport",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String xajaxMainStep(@RequestBody String jsonString) throws JSONException, MessagingException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			System.out.println(jsonString);
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
			Date date1 = new Date();
			String special = dateFormat.format(date1);
			JSONObject obj= new JSONObject(jsonString);

			AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+obj.getInt("appid")+"", "current");
			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

			an.setRepstatusid(obj.getLong("appstatus"));
			//an.setRepstepid(obj.getLong("appstep"));
			an.setOfficerid(loguser.getId());
			an.setApproveddate(special);
			dao.PeaceCrud(an, "AnnualRegistration", "update", (long) obj.getLong("appid"), 0, 0, null);

			if(obj.getInt("appstatus")==2 && an.getSubLegalpersons().getKEYMANEMAIL()!=null){

				LutUsers res=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+an.getSubLegalpersons().getLpReg()+"'", "current");

				DataTezuMail mn = new DataTezuMail();
				mn.setSenddate(special);
				mn.setContent("Tailan butsaagdlaa");
				mn.setEmail(loguser.getSubLegalpersons().getKEYMANEMAIL());
				mn.setSender(loguser.getSubLegalpersons().getKEYMAN());
				mn.setSenderAvatar(loguser.getAvatar());
				mn.setUserid(loguser.getId());
				mn.setVerified(false);
				mn.setSenderColor("cyan");
				mn.setTezu(false);
				mn.setRecipientName(an.getSubLegalpersons().getLpName());
				mn.setTitle("Tailan butsaagdlaa");
				mn.setRecipient(res.getId());
				mn.setSender(loguser.getUsername());
				dao.PeaceCrud(mn, "DataTezuMail", "save", (long) 0, 0, 0, null);

				smtpMailSender.send(an.getSubLegalpersons().getKEYMANEMAIL(), "Tailan zasvart butsav" , "Tanai kompanii ilgeesen tailang AMG-aas hyanan uzej zasvarluulhaar butsaasan tul tsahim tailangaa shalgana uu");
			}




			LnkCommentMain mn = new LnkCommentMain();
			mn.setMcomment(obj.getString("comtext"));
			mn.setPlanid(obj.getLong("appid"));
			mn.setUserid(loguser.getId());
			mn.setUsername(loguser.getGivnamemon());
			mn.setDesid(obj.getLong("appstatus"));
			mn.setIsgov((long) 1);
			mn.setCreatedDate(special);
			dao.PeaceCrud(mn, "LnkCommentMain", "save", (long) 0, 0, 0, null);

			return "true";
		}
		return null;
	}
	
	
	@RequestMapping(value="/submitMainComment",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String ajaxMainStep(@RequestBody String jsonString) throws JSONException, MessagingException, ParseException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			System.out.println(jsonString);
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
			Date date1 = new Date();
			String special = dateFormat.format(date1);
			JSONObject obj= new JSONObject(jsonString);

			AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+obj.getInt("appid")+"", "current");
			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

			an.setRepstatusid(obj.getLong("appstatus"));
			//an.setRepstepid(an.getRepstepid());
			//an.setRejectstep(an.getRepstepid());
			an.setRepstepid(obj.getLong("appstep"));
			if (an.getRejectstep()==null){
				an.setRejectstep((long)1);
			}
			// an.setRejectstep(obj.getInt("appstep"));
			an.setOfficerid(loguser.getId());
			an.setApproveddate(special);
			dao.PeaceCrud(an, "AnnualRegistration", "save", (long) obj.getLong("appid"), 0, 0, null);

			if(obj.getInt("appstatus")==2 && an.getSubLegalpersons().getKEYMANEMAIL()!=null){

				LutUsers res=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+an.getSubLegalpersons().getLpReg()+"'", "current");

				DataTezuMail mn = new DataTezuMail();
				mn.setSenddate(special);
				mn.setContent("Tailan butsaagdlaa");
				mn.setEmail(loguser.getSubLegalpersons().getKEYMANEMAIL());
				mn.setSender(loguser.getSubLegalpersons().getKEYMAN());
				mn.setSenderAvatar(loguser.getAvatar());
				mn.setUserid(loguser.getId());
				mn.setVerified(false);
				mn.setSenderColor("cyan");
				mn.setTezu(false);
				mn.setRecipientName(an.getSubLegalpersons().getLpName());
				mn.setTitle("Tailan butsaagdlaa");
				mn.setRecipient(res.getId());
				mn.setSender(loguser.getUsername());
				dao.PeaceCrud(mn, "DataTezuMail", "save", (long) 0, 0, 0, null);
				try{
					smtpMailSender.send(an.getSubLegalpersons().getKEYMANEMAIL(), "Tailan zasvart butsav" , "Tanai kompanii ilgeesen tailang AMG-aas hyanan uzej zasvarluulhaar butsaasan tul tsahim tailangaa shalgana uu");
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}




			LnkCommentMain mn = new LnkCommentMain();
			mn.setMcomment(obj.getString("comtext"));
			mn.setPlanid(obj.getLong("appid"));
			mn.setUserid(loguser.getId());
			mn.setUsername(loguser.getGivnamemon());
			mn.setDesid(obj.getLong("appstatus"));
			mn.setCreatedDate(special);
			mn.setIsgov((long) 0);
			dao.PeaceCrud(mn, "LnkCommentMain", "save", (long) 0, 0, 0, null);
			
			
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

			Date d1 = null;
			Date d2 = null;
			
		    PeriodFormatter dhm = new PeriodFormatterBuilder()
	    		.appendWeeks()
		        .appendSuffix(" week", " долоо хоног")
		        .appendSeparator(" , ")
		        .appendDays()
		        .appendSuffix(" day", " хоног")
		        .appendSeparator(" , ")
		        .appendHours()
		        .appendSuffix(" hour", " цаг")
		        .appendSeparator(" , ")
		        .appendMinutes()
		        .appendSuffix(" minute", " минут")
		        .appendSeparator(" , ")
		        .appendSeconds()
		        .appendSuffix(" second", " секунд")
		        .toFormatter();
		    
		    String govDateStart = null;
			String govDateStop = null;
			String comDateStart = null;
			String comDateStop = null;
			
			Seconds govSeconds= Seconds.ZERO;
			Seconds comSeconds = Seconds.ZERO;
			
			long csec=0;
			int gsec=0;
			
			List<LnkCommentMain> stcoms = (List<LnkCommentMain>) dao.getHQLResult("from LnkCommentMain t where t.planid="+an.getId()+"  and t.createdDate is not null order by t.id asc", "list");
			for(int y=0; y<stcoms.size(); y++){
				if(stcoms.get(y).getIsgov()==1){
					comDateStop=stcoms.get(y).getCreatedDate();
					
					if(y==0){
						govDateStart=stcoms.get(y).getCreatedDate();
					}
					else{
						govDateStart=comDateStop;
					}						
					
				}
				else if(stcoms.get(y).getIsgov()==0){
					comDateStart=stcoms.get(y).getCreatedDate();
					govDateStop=stcoms.get(y).getCreatedDate();
				}
				if(stcoms.get(y).getIsgov()==1){
					d2 = format.parse(govDateStart);
					if(y==0){							
						d1 = format.parse(comDateStop);
					}
					else{
						if(comDateStart!=null){
							d1 = format.parse(comDateStart);
						}						
					}
					
					DateTime gdt1 = new DateTime(d1);
					DateTime gdt2 = new DateTime(d2);
											
					Seconds seconds = Seconds.secondsBetween(gdt1, gdt2);
					comSeconds = comSeconds.plus(seconds);						
					Period pc = new Period(seconds);		
					csec=csec+pc.getSeconds();
					//System.out.println("com : "+dhm.print(pc.normalizedStandard()));
					
				}
				if(stcoms.get(y).getIsgov()==0){
					d1 = format.parse(comDateStart);
					if(y==0){
						d2 = format.parse(govDateStop);
					}
					else{
						d2 = format.parse(govDateStart);
					}			
					
					DateTime gdt1 = new DateTime(d1);
					DateTime gdt2 = new DateTime(d2);
					
					Seconds seconds = Seconds.secondsBetween(gdt2, gdt1);
					govSeconds = govSeconds.plus(seconds);
					
					Period pc = new Period(seconds);
					gsec=gsec+pc.getSeconds();
					//System.out.println("gov : "+dhm.print(pc.normalizedStandard()));
				}
								
			}
			
			Seconds s1 = Seconds.seconds((int)csec);
			Period p1 = new Period(s1);
			System.out.println(dhm.print(p1.normalizedStandard()));
			
			Seconds s2 = Seconds.seconds(gsec);
			Period p2 = new Period(s2);
			System.out.println(dhm.print(p2.normalizedStandard()));
			 
			an.setCduration(dhm.print(p1.normalizedStandard()));
			an.setGduration(dhm.print(p2.normalizedStandard()));
			dao.PeaceCrud(an, "AnnualRegistration", "update", (long) an.getId(), 0, 0, null);

			return "true";
		}
		return null;
	}
	
	@RequestMapping(value="/submitGeoPlanComment",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String ajaxGeoPlanComment(@RequestBody String jsonString, HttpServletRequest req) throws DocumentException, Exception {
		System.out.println(jsonString);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date1 = new Date();
			String special = dateFormat.format(date1);
			JSONObject obj= new JSONObject(jsonString);


			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

			LnkComment com=new LnkComment();
			com.setPlanid(obj.getInt("planid"));
			com.setOfficerid(loguser.getId());
			com.setComdate(special);
			if(obj.has("comment")){
				com.setComnote(obj.getString("comment"));
			}
			com.setNoteid(obj.getLong("id"));
			com.setDesicionid(obj.getInt("desicion"));
			com.setOfficername(loguser.getGivnamemon());
			dao.PeaceCrud(com, "LnkComment", "save", (long) 0, 0, 0, null);	

			LutFormNotes nt=(LutFormNotes) dao.getHQLResult("from LutFormNotes t where t.id="+obj.getInt("id")+"", "current");
			LutDecisions an=(LutDecisions) dao.getHQLResult("from LutDecisions t where t.id="+obj.getInt("desicion")+"", "current");
			AnnualRegistration ar=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+obj.getLong("planid")+" ", "current");

			JSONArray arr = new JSONArray();	
			JSONObject jo1 = new JSONObject();
			jo1.put("user_id", com.getOfficerid());
			jo1.put("user_name", com.getOfficername());
			JSONArray content = new JSONArray();
			content.put(com.getComnote());

			jo1.put("content",content);
			jo1.put("date", com.getComdate());		
			jo1.put("des", an.getDecisionNameMon());
			jo1.put("destext", com.getComnote());
			jo1.put("desicion", com.getDesicionid());
			JSONObject jo = new JSONObject();

			if(loguser.getPositionid()>0){

				LnkPlanTransition tz=(LnkPlanTransition) dao.getHQLResult("from LnkPlanTransition t where t.noteid="+obj.getInt("id")+" and t.planid="+obj.getInt("planid")+"", "current");		 

				if (tz != null){
					if(loguser.getStepid()==6){
						tz.setMdecisionid(obj.getInt("desicion"));
					}
					else{
						tz.setDecisionid(obj.getInt("desicion"));
					}

					tz.setOffposition(loguser.getPositionid());
					dao.PeaceCrud(tz, "LnkPlanTransition", "update", (long) tz.getId(), 0, 0, null);
				}
				else{
					tz = new LnkPlanTransition();
					tz.setNoteid(obj.getLong("id"));
					tz.setPlanid(obj.getLong("planid"));
					tz.setTabid(obj.getInt("tabid"));
					tz.setDecisionid(obj.getInt("desicion"));
					tz.setOffposition(loguser.getPositionid());
					dao.PeaceCrud(tz, "LnkPlanTransition", "save", (long) 0, 0, 0, null);
				}
		 
				List<LnkPlanTransition> des= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.decisionid=1", "list");
				List<LnkPlanTransition> mdes= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.mdecisionid=1", "list");

				
				System.out.println("notesize"+obj.getLong("notesize"));
				System.out.println("mdes"+mdes.size());
				System.out.println("des"+des.size());

				if(obj.getLong("notesize")==des.size()+mdes.size()){	
					ar.setOfficerid(loguser.getId());
					ar.setRejectstep((long) 0);
					ar.setReject(0);
					ar.setRepstepid((long) nt.getInptype());
					ar.setRepstatusid((long) 1);
					
					dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);

				
					List<LnkPlanTab> nz=(List<LnkPlanTab>) dao.getHQLResult("from LnkPlanTab t where t.planid="+obj.getLong("planid")+" and t.tabid="+nt.getInptype()+"", "list");
					if(nz.size()>0){
						dao.PeaceCrud(null, "LnkPlanTab", "delete", (long) nz.get(0).getId(), 0, 0, null);
					}				 
					jo1.put("tabid",nt.getInptype());
					LnkPlanTab tb= new LnkPlanTab();
					tb.setPlanid(obj.getLong("planid"));
					tb.setTabid(nt.getInptype());
					dao.PeaceCrud(tb, "LnkPlanTab", "save", (long) 0, 0, 0, null);	


					System.out.println("trans"+nt.getTransid());
					if(nt.getTransid()!=null){
						if(nt.getTransid()==2){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(4);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);	
						}
						if(nt.getTransid()==0){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(3);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);	
						}
						if(nt.getTransid()==3){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(4);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);	
						}
					}


					jo1.put("step",true);
				}
				else{

					if(obj.getInt("desicion")==2){
						ar.setOfficerid(loguser.getId());
						ar.setRejectstep(obj.getLong("tabid"));
						ar.setReject(2);
						ar.setRepstatusid((long) 2);
						dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);
						
						Date d1 = new Date();
						SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
						String formattedDate = df.format(d1);
						
						LnkCommentMain cm= new LnkCommentMain();
						cm.setUsername(loguser.getUsername());
						cm.setUserid(loguser.getId());
						cm.setPlanid(ar.getId());
						cm.setMcomment(obj.getString("comment"));
						cm.setDesid((long) ar.getRejectstep());
						cm.setCreatedDate(formattedDate);
						dao.PeaceCrud(cm, "LnkCommentMain", "save", (long) 0, 0, 0, null);	
					}
					if(obj.getInt("desicion")==3){
						ar.setOfficerid(loguser.getId());
						ar.setRejectstep(obj.getLong("tabid"));
						ar.setReject(3);
						ar.setRepstatusid((long) 2);
						dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);
						
						Date d1 = new Date();
						SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
						String formattedDate = df.format(d1);
						
						LnkCommentMain cm= new LnkCommentMain();
						cm.setUsername(loguser.getUsername());
						cm.setUserid(loguser.getId());
						cm.setPlanid(ar.getId());
						cm.setMcomment(obj.getString("comment"));
						cm.setDesid((long) ar.getRejectstep());
						cm.setCreatedDate(formattedDate);
						dao.PeaceCrud(cm, "LnkCommentMain", "save", (long) 0, 0, 0, null);	
					}
					
				}
			}

			arr.put(jo1);

			jo.put("comdata", arr);

			return jo.toString();
		}
		return null;
	}

	@RequestMapping(value="/submitReportConfirm",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String ajaxReportConfirm(@RequestBody String jsonString, HttpServletRequest req) throws Exception {
		JSONObject obj = new JSONObject(jsonString);
		UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
		AnnualRegistration ar=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+obj.getLong("planid")+" ", "current");
		LutFormNotes nt=(LutFormNotes) dao.getHQLResult("from LutFormNotes t where t.id="+obj.getInt("id")+"", "current");
		ar.setOfficerid(loguser.getId());
		ar.setRejectstep((long) 0);
		ar.setReject(0);
		ar.setRepstepid((long) nt.getInptype());
		if(nt.getInptype()==10){
			ar.setRepstatusid((long) 1);
		}

		dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);
		return "true";
	}

	@RequestMapping(value="/submitReportNewComment",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String ajaxReportCommentNew(@RequestBody String jsonString, HttpServletRequest req) throws DocumentException, Exception {
		System.out.println(jsonString);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date1 = new Date();
			String special = dateFormat.format(date1);
			JSONObject obj= new JSONObject(jsonString);


			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

			LnkComment com=new LnkComment();
			com.setPlanid(obj.getInt("planid"));
			com.setOfficerid(loguser.getId());
			com.setComdate(special);
			if(obj.has("comment")){
				com.setComnote(obj.getString("comment"));
			}
			com.setNoteid(obj.getLong("id"));
			com.setDesicionid(obj.getInt("desicion"));
			com.setOfficername(loguser.getGivnamemon());
			dao.PeaceCrud(com, "LnkComment", "save", (long) 0, 0, 0, null);

			LutFormNotes nt=(LutFormNotes) dao.getHQLResult("from LutFormNotes t where t.id="+obj.getInt("id")+"", "current");

			LutDecisions an=(LutDecisions) dao.getHQLResult("from LutDecisions t where t.id="+obj.getInt("desicion")+"", "current");
			AnnualRegistration ar=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+obj.getLong("planid")+" ", "current");

			JSONArray arr = new JSONArray();
			JSONObject jo1 = new JSONObject();
			jo1.put("user_id", com.getOfficerid());
			jo1.put("user_name", com.getOfficername());
			JSONArray content = new JSONArray();
			content.put(com.getComnote());

			jo1.put("content",content);
			jo1.put("date", com.getComdate());
			jo1.put("des", an.getDecisionNameMon());
			jo1.put("destext", com.getComnote());
			jo1.put("desicion", com.getDesicionid());
			JSONObject jo = new JSONObject();

			if(loguser.getPositionid()>0){

				LnkPlanTransition tz=(LnkPlanTransition) dao.getHQLResult("from LnkPlanTransition t where t.noteid="+obj.getInt("id")+" and t.planid="+obj.getInt("planid")+"", "current");

				if (tz != null){
					if(loguser.getStepid()==6){
						tz.setMdecisionid(obj.getInt("desicion"));
					}
					else{
						tz.setDecisionid(obj.getInt("desicion"));
					}

					tz.setOffposition(loguser.getPositionid());
					dao.PeaceCrud(tz, "LnkPlanTransition", "update", (long) tz.getId(), 0, 0, null);
				}
				else{
					tz = new LnkPlanTransition();
					tz.setNoteid(obj.getLong("id"));
					tz.setPlanid(obj.getLong("planid"));
					tz.setTabid(obj.getInt("tabid"));
					tz.setDecisionid(obj.getInt("desicion"));
					tz.setOffposition(loguser.getPositionid());
					dao.PeaceCrud(tz, "LnkPlanTransition", "save", (long) 0, 0, 0, null);
				}


				List<LnkPlanTransition> des= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.decisionid=1 and t.tabid="+nt.getInptype()+"", "list");
				List<LnkPlanTransition> mdes= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.mdecisionid=1 and t.tabid="+nt.getInptype()+"", "list");

				System.out.println("tabid"+nt.getInptype());
				System.out.println("tzc"+obj.getLong("notesize"));
				System.out.println("des"+des.size());

				if(obj.getLong("notesize")==des.size()+mdes.size() && des.size()+mdes.size()>0){	

					List<LnkPlanTab> nz=(List<LnkPlanTab>) dao.getHQLResult("from LnkPlanTab t where t.planid="+obj.getLong("planid")+" and t.tabid="+nt.getInptype()+"", "list");
					if(nz.size()>0){
						dao.PeaceCrud(null, "LnkPlanTab", "delete", (long) nz.get(0).getId(), 0, 0, null);
					}
					jo1.put("tabid",nt.getInptype());
					LnkPlanTab tb= new LnkPlanTab();
					tb.setPlanid(obj.getLong("planid"));
					tb.setTabid(nt.getInptype());
					dao.PeaceCrud(tb, "LnkPlanTab", "save", (long) 0, 0, 0, null);


					System.out.println("trans"+nt.getTransid());
					if(nt.getTransid()!=null){
						if(nt.getTransid()==2){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(4);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);
						}
						if(nt.getTransid()==0){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(3);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);
						}
						if(nt.getTransid()==3){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(4);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);
						}
					}


					jo1.put("step",true);
				}
				else{
					if(obj.getInt("desicion")==2){
						ar.setOfficerid(loguser.getId());
						ar.setRejectstep(obj.getLong("tabid"));
						ar.setReject(2);
						ar.setRepstatusid((long) 2);
						dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);

						Date d1 = new Date();
						SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
						String formattedDate = df.format(d1);

						LnkCommentMain cm= new LnkCommentMain();
						cm.setUsername(loguser.getUsername());
						cm.setUserid(loguser.getId());
						cm.setPlanid(ar.getId());
						cm.setMcomment(obj.getString("comment"));
						cm.setDesid((long) ar.getRejectstep());
						cm.setCreatedDate(formattedDate);
						dao.PeaceCrud(cm, "LnkCommentMain", "save", (long) 0, 0, 0, null);
					}
					if(obj.getInt("desicion")==3){
						ar.setOfficerid(loguser.getId());
						ar.setRejectstep(obj.getLong("tabid"));
						ar.setReject(3);
						ar.setRepstatusid((long) 2);
						dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);

						Date d1 = new Date();
						SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
						String formattedDate = df.format(d1);

						LnkCommentMain cm= new LnkCommentMain();
						cm.setUsername(loguser.getUsername());
						cm.setUserid(loguser.getId());
						cm.setPlanid(ar.getId());
						cm.setMcomment(obj.getString("comment"));
						cm.setDesid((long) ar.getRejectstep());
						cm.setCreatedDate(formattedDate);
						dao.PeaceCrud(cm, "LnkCommentMain", "save", (long) 0, 0, 0, null);
					}

				}
			}

			arr.put(jo1);

			jo.put("comdata", arr);

			return jo.toString();
		}
		return null;
	}

	@RequestMapping(value="/submitReportComment",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String ajaxReportComment(@RequestBody String jsonString, HttpServletRequest req) throws DocumentException, Exception {
		System.out.println(jsonString);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date1 = new Date();
			String special = dateFormat.format(date1);
			JSONObject obj= new JSONObject(jsonString);


			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

			LnkComment com=new LnkComment();
			com.setPlanid(obj.getInt("planid"));
			com.setOfficerid(loguser.getId());
			com.setComdate(special);
			if(obj.has("comment")){
				com.setComnote(obj.getString("comment"));
			}
			com.setNoteid(obj.getLong("id"));
			com.setDesicionid(obj.getInt("desicion"));
			com.setOfficername(loguser.getGivnamemon());
			dao.PeaceCrud(com, "LnkComment", "save", (long) 0, 0, 0, null);	

			LutFormNotes nt=(LutFormNotes) dao.getHQLResult("from LutFormNotes t where t.id="+obj.getInt("id")+"", "current");

			LutDecisions an=(LutDecisions) dao.getHQLResult("from LutDecisions t where t.id="+obj.getInt("desicion")+"", "current");
			AnnualRegistration ar=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+obj.getLong("planid")+" ", "current");

			JSONArray arr = new JSONArray();	
			JSONObject jo1 = new JSONObject();
			jo1.put("user_id", com.getOfficerid());
			jo1.put("user_name", com.getOfficername());
			JSONArray content = new JSONArray();
			content.put(com.getComnote());

			jo1.put("content",content);
			jo1.put("date", com.getComdate());		
			jo1.put("des", an.getDecisionNameMon());
			jo1.put("destext", com.getComnote());
			jo1.put("desicion", com.getDesicionid());
			JSONObject jo = new JSONObject();

			if(loguser.getPositionid()>0){

				LnkPlanTransition tz=(LnkPlanTransition) dao.getHQLResult("from LnkPlanTransition t where t.noteid="+obj.getInt("id")+" and t.planid="+obj.getInt("planid")+"", "current");		 

				if (tz != null){
					if(loguser.getStepid()==6){
						tz.setMdecisionid(obj.getInt("desicion"));
					}
					else{
						tz.setDecisionid(obj.getInt("desicion"));
					}

					tz.setOffposition(loguser.getPositionid());
					dao.PeaceCrud(tz, "LnkPlanTransition", "update", (long) tz.getId(), 0, 0, null);
				}
				else{
					tz = new LnkPlanTransition();
					tz.setNoteid(obj.getLong("id"));
					tz.setPlanid(obj.getLong("planid"));
					tz.setTabid(obj.getInt("tabid"));
					tz.setDecisionid(obj.getInt("desicion"));
					tz.setOffposition(loguser.getPositionid());
					dao.PeaceCrud(tz, "LnkPlanTransition", "save", (long) 0, 0, 0, null);
				}

		 
				List<LnkPlanTransition> des= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.decisionid=1 and t.tabid="+nt.getInptype()+"", "list");
				List<LnkPlanTransition> mdes= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.mdecisionid=1 and t.tabid="+nt.getInptype()+"", "list");

				System.out.println("tabid"+nt.getInptype());
				System.out.println("tzc"+obj.getLong("notesize"));
				System.out.println("des"+des.size());

				if(obj.getLong("notesize")==des.size()+mdes.size() && des.size()+mdes.size()>0){
						ar.setOfficerid(loguser.getId());
						ar.setRejectstep((long) 0);
						ar.setReject(0);
						ar.setRepstepid((long) nt.getInptype());
						if(nt.getInptype()==10){
							ar.setRepstatusid((long) 1);
						}

						dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);


						List<LnkPlanTab> nz=(List<LnkPlanTab>) dao.getHQLResult("from LnkPlanTab t where t.planid="+obj.getLong("planid")+" and t.tabid="+nt.getInptype()+"", "list");
						if(nz.size()>0){
							dao.PeaceCrud(null, "LnkPlanTab", "delete", (long) nz.get(0).getId(), 0, 0, null);
						}
						jo1.put("tabid",nt.getInptype());
						LnkPlanTab tb= new LnkPlanTab();
						tb.setPlanid(obj.getLong("planid"));
						tb.setTabid(nt.getInptype());
						dao.PeaceCrud(tb, "LnkPlanTab", "save", (long) 0, 0, 0, null);


						System.out.println("trans"+nt.getTransid());
						if(nt.getTransid()!=null){
							if(nt.getTransid()==2){
								LnkPlanTab tbc= new LnkPlanTab();
								tbc.setPlanid(obj.getLong("planid"));
								tbc.setTabid(4);
								dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);
							}
							if(nt.getTransid()==0){
								LnkPlanTab tbc= new LnkPlanTab();
								tbc.setPlanid(obj.getLong("planid"));
								tbc.setTabid(3);
								dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);
							}
							if(nt.getTransid()==3){
								LnkPlanTab tbc= new LnkPlanTab();
								tbc.setPlanid(obj.getLong("planid"));
								tbc.setTabid(4);
								dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);
							}
						}


						jo1.put("step",true);
				}
				else{
					if(obj.getInt("desicion")==2){
						ar.setOfficerid(loguser.getId());
						ar.setRejectstep(obj.getLong("tabid"));
						ar.setReject(2);
						ar.setRepstatusid((long) 2);
						dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);
						
						Date d1 = new Date();
						SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
						String formattedDate = df.format(d1);
						
						LnkCommentMain cm= new LnkCommentMain();
						cm.setUsername(loguser.getUsername());
						cm.setUserid(loguser.getId());
						cm.setPlanid(ar.getId());
						cm.setMcomment(obj.getString("comment"));
						cm.setDesid((long) ar.getRejectstep());
						cm.setCreatedDate(formattedDate);
						dao.PeaceCrud(cm, "LnkCommentMain", "save", (long) 0, 0, 0, null);	
					}
					if(obj.getInt("desicion")==3){
						ar.setOfficerid(loguser.getId());
						ar.setRejectstep(obj.getLong("tabid"));
						ar.setReject(3);
						ar.setRepstatusid((long) 2);
						dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);
						
						Date d1 = new Date();
						SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
						String formattedDate = df.format(d1);
						
						LnkCommentMain cm= new LnkCommentMain();
						cm.setUsername(loguser.getUsername());
						cm.setUserid(loguser.getId());
						cm.setPlanid(ar.getId());
						cm.setMcomment(obj.getString("comment"));
						cm.setDesid((long) ar.getRejectstep());
						cm.setCreatedDate(formattedDate);
						dao.PeaceCrud(cm, "LnkCommentMain", "save", (long) 0, 0, 0, null);	
					}
					
				}
			}

			arr.put(jo1);

			jo.put("comdata", arr);

			return jo.toString();
		}
		return null;
	}
	
	
	@RequestMapping(value="/reportComment",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String reportComment(@RequestBody String jsonString, HttpServletRequest req) throws DocumentException, Exception {
		System.out.println(jsonString);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date1 = new Date();
			String special = dateFormat.format(date1);
			JSONObject obj= new JSONObject(jsonString);


			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

			LnkComment com=new LnkComment();
			com.setPlanid(obj.getInt("planid"));
			com.setOfficerid(loguser.getId());
			com.setComdate(special);
			if(obj.has("comment")){
				com.setComnote(obj.getString("comment"));
			}
			com.setNoteid(obj.getLong("id"));
			com.setDesicionid(obj.getInt("desicion"));
			com.setOfficername(loguser.getGivnamemon());
			dao.PeaceCrud(com, "LnkComment", "save", (long) 0, 0, 0, null);	

			LutFormNotes nt=(LutFormNotes) dao.getHQLResult("from LutFormNotes t where t.id="+obj.getInt("id")+"", "current");

			LutDecisions an=(LutDecisions) dao.getHQLResult("from LutDecisions t where t.id="+obj.getInt("desicion")+"", "current");
			AnnualRegistration ar=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+obj.getLong("planid")+" ", "current");

			JSONArray arr = new JSONArray();	
			JSONObject jo1 = new JSONObject();
			jo1.put("user_id", com.getOfficerid());
			jo1.put("user_name", com.getOfficername());
			JSONArray content = new JSONArray();
			content.put(com.getComnote());

			jo1.put("content",content);
			jo1.put("date", com.getComdate());
			if(an!=null){
				jo1.put("des", an.getDecisionNameMon());
			}

			jo1.put("destext", com.getComnote());
			jo1.put("desicion", com.getDesicionid());
			JSONObject jo = new JSONObject();

			if(loguser.getPositionid()>0){

				LnkPlanTransition tz=(LnkPlanTransition) dao.getHQLResult("from LnkPlanTransition t where t.noteid="+obj.getInt("id")+" and t.planid="+obj.getInt("planid")+"", "current");		 

				if (tz != null){
					if(loguser.getStepid()==6){
						tz.setMdecisionid(obj.getInt("desicion"));
						tz.setDecisionid(obj.getInt("desicion"));
					}
					else{
						tz.setDecisionid(obj.getInt("desicion"));
					}

					tz.setOffposition(loguser.getPositionid());
					dao.PeaceCrud(tz, "LnkPlanTransition", "update", (long) tz.getId(), 0, 0, null);
				}
				else{
					tz = new LnkPlanTransition();
					tz.setNoteid(obj.getLong("id"));
					tz.setPlanid(obj.getLong("planid"));
					tz.setTabid(obj.getInt("tabid"));
					tz.setDecisionid(obj.getInt("desicion"));
					tz.setOffposition(loguser.getPositionid());
					dao.PeaceCrud(tz, "LnkPlanTransition", "save", (long) 0, 0, 0, null);
				}


				String dateStart = null;
				String dateStop = null;
				//DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

				Date d1 = null;
				Date d2 = null;
				
				System.out.println("@@@@@@@"+nt.getTransid());
				System.out.println("@@@@@@@"+obj.getInt("desicion"));

				//List<LnkPlanTransition> tzc= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+"  and t.tabid="+nt.getInptype()+"", "list");		 
				List<LnkPlanTransition> cdes= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.decisionid=0 and t.tabid="+nt.getInptype()+"", "list");
				List<LnkPlanTransition> des= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.decisionid=1 and t.tabid="+nt.getInptype()+"", "list");
				List<LnkPlanTransition> alldes= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.decisionid in (1,2,3) and t.planid="+obj.getInt("planid")+" and t.tabid="+nt.getInptype()+"", "list");
				List<LnkPlanTransition> mdes= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.mdecisionid=1 and t.tabid="+nt.getInptype()+"", "list");
				
								
				System.out.println("tabid"+nt.getInptype());
				System.out.println("tzc"+obj.getLong("notesize"));
				System.out.println("des"+des.size());
				System.out.println("commentsize"+obj.getInt("commentsize"));
				System.out.println("commenttrue"+obj.getInt("commenttrue"));
				System.out.println("notesize"+obj.getInt("notesize"));
				
				int comtrue=0;
				if(obj.getInt("desicion")==1){
					comtrue=obj.getInt("commenttrue")+1;
				}
				else{
					comtrue=obj.getInt("commenttrue");
				}

				System.out.println("comtrue"+comtrue);
				
				if(obj.getInt("commentsize") <=comtrue && obj.getInt("notesize")==obj.getInt("commentsize")){	
					
					/*List<AnnualRegistration> ann= (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.repstatusid!=0", "list");

					for(int i=0; i<ann.size(); i++){
						AnnualRegistration item=ann.get(i);
						List<LnkCommentMain> stcoms = (List<LnkCommentMain>) dao.getHQLResult("from LnkCommentMain t where t.planid="+item.getId()+" and t.isgov=1 and t.createdDate is not null", "list");
						List<LnkCommentMain> stgov = (List<LnkCommentMain>) dao.getHQLResult("from LnkCommentMain t where t.planid="+item.getId()+" and t.isgov=0 and t.createdDate is not null", "list");
						
						
						
						dateStart = stcoms.get(0).getCreatedDate();
						dateStop = stcoms.get(stcoms.size()-1).getCreatedDate();
						d1 = format.parse(dateStart);
						d2 = format.parse(dateStop);

						DateTime dt1 = new DateTime(d1);
						DateTime dt2 = new DateTime(d2);
						
						ar.setCduration(Days.daysBetween(dt1, dt2).getDays() + " days, "+Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, "+Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes.");

						dateStart = stgov.get(0).getCreatedDate();
						dateStop = stgov.get(stgov.size()-1).getCreatedDate();
						d1 = format.parse(dateStart);
						d2 = format.parse(dateStop);

						DateTime gdt1 = new DateTime(d1);
						DateTime gdt2 = new DateTime(d2);
						
						ar.setGduration(Days.daysBetween(gdt1, gdt2).getDays() + " days, "+Hours.hoursBetween(gdt1, gdt2).getHours() % 24 + " hours, "+Minutes.minutesBetween(gdt1, gdt2).getMinutes() % 60 + " minutes.");
					}*/
					
					
					if(ar.getRepstepid()==6){
						ar.setRepstepid(ar.getRejectstep()+1);
					}
					else{
						ar.setRepstepid((long) nt.getInptype());
					}
					ar.setOfficerid(loguser.getId());
					ar.setRejectstep((long) 0);
					ar.setReject(0);					
					
					try {					

						if(nt.getInptype()-1==1){
							dateStart = ar.getSubmissiondate();
							dateStop = ar.getLastmodified();
							d1 = format.parse(dateStart);
							d2 = format.parse(dateStop);

							DateTime dt1 = new DateTime(d1);
							DateTime dt2 = new DateTime(d2);

							System.out.print(Days.daysBetween(dt1, dt2).getDays() + " days, ");
							System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
							System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
							System.out.print(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");
							ar.setFiduration(Days.daysBetween(dt1, dt2).getDays() + " days, "+Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, "+Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes.");
							ar.setFitime(special);
						}
						if(nt.getInptype()-1==2){
							if(ar.getFitime()==null){
								dateStart = ar.getSubmissiondate();
							}
							else{
								dateStart = ar.getFitime();
							}
							
							dateStop = ar.getLastmodified();
							d1 = format.parse(dateStart);
							d2 = format.parse(dateStop);

							DateTime dt1 = new DateTime(d1);
							DateTime dt2 = new DateTime(d2);

							ar.setSeduration(Days.daysBetween(dt1, dt2).getDays() + " days, "+Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, "+Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes.");
							ar.setSetime(special);
						}
						if(nt.getInptype()-1==3){
							dateStart = ar.getSetime();
							dateStop = ar.getLastmodified();
							d1 = format.parse(dateStart);
							d2 = format.parse(dateStop);

							DateTime dt1 = new DateTime(d1);
							DateTime dt2 = new DateTime(d2);

							ar.setThduration(Days.daysBetween(dt1, dt2).getDays() + " days, "+Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, "+Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes.");
							ar.setThtime(special);
						}
						if(nt.getInptype()-1==4){
							dateStart = ar.getThtime();
							dateStop = ar.getLastmodified();
							d1 = format.parse(dateStart);
							d2 = format.parse(dateStop);

							DateTime dt1 = new DateTime(d1);
							DateTime dt2 = new DateTime(d2);

							ar.setFoduration(Days.daysBetween(dt1, dt2).getDays() + " days, "+Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, "+Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes.");
							ar.setFotime(special);
						}
						if(nt.getInptype()-1==5){
							dateStart = ar.getFotime();
							dateStop = ar.getLastmodified();
							d1 = format.parse(dateStart);
							d2 = format.parse(dateStop);

							DateTime dt1 = new DateTime(d1);
							DateTime dt2 = new DateTime(d2);

							ar.setFaduration(Days.daysBetween(dt1, dt2).getDays() + " days, "+Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, "+Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes.");
							ar.setFatime(special);
						}
					 } catch (Exception e) {
						e.printStackTrace();
					 }			  
					
					
					dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);

					List<LnkPlanTab> nz=(List<LnkPlanTab>) dao.getHQLResult("from LnkPlanTab t where t.planid="+obj.getLong("planid")+" and t.tabid="+nt.getInptype()+"", "list");
					if(nz.size()>0){
						dao.PeaceCrud(null, "LnkPlanTab", "delete", (long) nz.get(0).getId(), 0, 0, null);
					}				 
					jo1.put("tabid",nt.getInptype());
					LnkPlanTab tb= new LnkPlanTab();
					tb.setPlanid(obj.getLong("planid"));
					tb.setTabid(nt.getInptype());
					dao.PeaceCrud(tb, "LnkPlanTab", "save", (long) 0, 0, 0, null);	


					System.out.println("trans"+nt.getTransid());
					if(nt.getTransid()!=null){
						if(nt.getTransid()==2){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(4);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);	
						}
						if(nt.getTransid()==0){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(3);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);	
						}
						if(nt.getTransid()==3){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(4);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);	
						}
					}
					jo1.put("step",true);
				}
				else{	
					if(obj.getInt("commentsize")==obj.getInt("notesize")){
						if(obj.getInt("desicion")==2){
							ar.setOfficerid(loguser.getId());
							ar.setRepstepid((long) 6);
							ar.setRejectstep((long) (nt.getInptype()-1));
							ar.setReject(2);
							dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);
							jo1.put("rej",true);
						}else{							
							if(cdes.size()==0 && obj.getLong("notesize")> des.size()+mdes.size()){
								System.out.println("end");	
								ar.setOfficerid(loguser.getId());
								ar.setRepstepid((long) 6);
								ar.setRejectstep((long) (nt.getInptype()-1));
								ar.setReject(2);
								dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);
								jo1.put("rej",true);
							}
						}
					
					
						if(obj.getInt("desicion")==3){
							ar.setOfficerid(loguser.getId());
							ar.setRepstepid((long) 6);
							ar.setRejectstep((long) (nt.getInptype()-1));
							ar.setReject(3);
							dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);
						}
						jo1.put("alldes",true);
					}					
					// dao.PeaceCrud(null, "LnkPlanTab", "delete", (long) 0, 0, 0, "dname.tabid>"+nt.getInptype()+"");
					List<LnkPlanTab> nz=(List<LnkPlanTab>) dao.getHQLResult("from LnkPlanTab t where t.planid="+obj.getLong("planid")+" and t.tabid="+nt.getInptype()+"", "list");
					if(nz.size()>0){
						dao.PeaceCrud(null, "LnkPlanTab", "delete", (long) nz.get(0).getId(), 0, 0, null);
					}
				}
			}

			arr.put(jo1);

			jo.put("comdata", arr);

			return jo.toString();
		}
		return null;
	}

	@RequestMapping(value="/submitComment",method=RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String ajaxComment(@RequestBody String jsonString, HttpServletRequest req) throws DocumentException, Exception {
		System.out.println(jsonString);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date1 = new Date();
			String special = dateFormat.format(date1);
			JSONObject obj= new JSONObject(jsonString);


			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

			LnkComment com=new LnkComment();
			com.setPlanid(obj.getInt("planid"));
			com.setOfficerid(loguser.getId());
			com.setComdate(special);
			if(obj.has("comment")){
				com.setComnote(obj.getString("comment"));
			}
			com.setNoteid(obj.getLong("id"));
			com.setDesicionid(obj.getInt("desicion"));
			com.setOfficername(loguser.getGivnamemon());
			dao.PeaceCrud(com, "LnkComment", "save", (long) 0, 0, 0, null);	

			LutFormNotes nt=(LutFormNotes) dao.getHQLResult("from LutFormNotes t where t.id="+obj.getInt("id")+"", "current");

			LutDecisions an=(LutDecisions) dao.getHQLResult("from LutDecisions t where t.id="+obj.getInt("desicion")+"", "current");
			AnnualRegistration ar=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+obj.getLong("planid")+" ", "current");

			JSONArray arr = new JSONArray();	
			JSONObject jo1 = new JSONObject();
			jo1.put("user_id", com.getOfficerid());
			jo1.put("user_name", com.getOfficername());
			JSONArray content = new JSONArray();
			content.put(com.getComnote());

			jo1.put("content",content);
			jo1.put("date", com.getComdate());
			if(an!=null){
				jo1.put("des", an.getDecisionNameMon());
			}

			jo1.put("destext", com.getComnote());
			jo1.put("desicion", com.getDesicionid());
			JSONObject jo = new JSONObject();

			if(loguser.getPositionid()>0){

				List<LnkPlanTransition> tzs=(List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid="+obj.getInt("id")+" and t.planid="+obj.getInt("planid")+"", "list");

				if (tzs.size()>0){
					for(LnkPlanTransition tz:tzs){
						if(loguser.getStepid()==6){
							tz.setMdecisionid(obj.getInt("desicion"));
							tz.setDecisionid(obj.getInt("desicion"));
						}
						else{
							tz.setDecisionid(obj.getInt("desicion"));
						}

						tz.setOffposition(loguser.getPositionid());
						dao.PeaceCrud(tz, "LnkPlanTransition", "update", (long) tz.getId(), 0, 0, null);
					}

				}
				else{
					LnkPlanTransition tz = new LnkPlanTransition();
					tz.setNoteid(obj.getLong("id"));
					tz.setPlanid(obj.getLong("planid"));
					tz.setTabid(obj.getInt("tabid"));
					if(obj.getInt("desicion")!=5){
						tz.setDecisionid(obj.getInt("desicion"));
					}
					else{
						tz.setDecisionid(1);
					}
					tz.setOffposition(loguser.getPositionid());
					dao.PeaceCrud(tz, "LnkPlanTransition", "save", (long) 0, 0, 0, null);
				}





				System.out.println("@@@@@@@"+nt.getTransid());
				System.out.println("@@@@@@@"+obj.getInt("desicion"));

				//List<LnkPlanTransition> tzc= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+"  and t.tabid="+nt.getInptype()+"", "list");		 
				List<LnkPlanTransition> cdes= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.decisionid=0 and t.tabid="+nt.getInptype()+"", "list");
				List<LnkPlanTransition> des= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.decisionid=1 and t.tabid="+nt.getInptype()+"", "list");
				List<LnkPlanTransition> alldes= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.decisionid in (1,2,3) and t.planid="+obj.getInt("planid")+" and t.tabid="+nt.getInptype()+"", "list");
				List<LnkPlanTransition> mdes= (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+obj.getInt("planid")+" and t.mdecisionid=1 and t.tabid="+nt.getInptype()+"", "list");

				
				
				System.out.println("tabid"+nt.getInptype());
				System.out.println("tzc"+obj.getLong("notesize"));
				System.out.println("des"+des.size());

				if(obj.getLong("notesize")==des.size()+mdes.size() && des.size()+mdes.size()>0){	
					ar.setOfficerid(loguser.getId());
					ar.setRejectstep((long) 0);
					ar.setReject(0);
					ar.setRepstepid((long) nt.getInptype());
					dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);

					List<LnkPlanTab> nz=(List<LnkPlanTab>) dao.getHQLResult("from LnkPlanTab t where t.planid="+obj.getLong("planid")+" and t.tabid="+nt.getInptype()+"", "list");
					if(nz.size()>0){
						dao.PeaceCrud(null, "LnkPlanTab", "delete", (long) nz.get(0).getId(), 0, 0, null);
					}				 
					jo1.put("tabid",nt.getInptype());
					LnkPlanTab tb= new LnkPlanTab();
					tb.setPlanid(obj.getLong("planid"));
					tb.setTabid(nt.getInptype());
					dao.PeaceCrud(tb, "LnkPlanTab", "save", (long) 0, 0, 0, null);	


					System.out.println("trans"+nt.getTransid());
					if(nt.getTransid()!=null){
						if(nt.getTransid()==2){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(4);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);	
						}
						if(nt.getTransid()==0){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(3);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);	
						}
						if(nt.getTransid()==3){
							LnkPlanTab tbc= new LnkPlanTab();
							tbc.setPlanid(obj.getLong("planid"));
							tbc.setTabid(4);
							dao.PeaceCrud(tbc, "LnkPlanTab", "save", (long) 0, 0, 0, null);	
						}
					}


					jo1.put("step",true);
				}
				else{
					System.out.println("lalar"+alldes.size());
					System.out.println("lalar"+obj.getLong("notesize"));	
					if(alldes.size()==obj.getLong("notesize")){
						if(obj.getInt("desicion")==2){
							ar.setOfficerid(loguser.getId());
							ar.setRepstepid((long) 6);
							ar.setRejectstep((long) (nt.getInptype()-1));
							ar.setReject(2);
							dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);
							jo1.put("rej",true);
						}else{							
							if(cdes.size()==0 && obj.getLong("notesize")> des.size()+mdes.size()){
								System.out.println("end");	
								ar.setOfficerid(loguser.getId());
								ar.setRepstepid((long) 6);
								ar.setRejectstep((long) (nt.getInptype()-1));
								ar.setReject(2);
								dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);
								jo1.put("rej",true);
							}
						}
					
					
						if(obj.getInt("desicion")==3){
							ar.setOfficerid(loguser.getId());
							ar.setRepstepid((long) 6);
							ar.setRejectstep((long) (nt.getInptype()-1));
							ar.setReject(3);
							dao.PeaceCrud(ar, "AnnualRegistration", "update", (long) ar.getId(), 0, 0, null);
						}
						jo1.put("alldes",true);
					}					
					// dao.PeaceCrud(null, "LnkPlanTab", "delete", (long) 0, 0, 0, "dname.tabid>"+nt.getInptype()+"");
					List<LnkPlanTab> nz=(List<LnkPlanTab>) dao.getHQLResult("from LnkPlanTab t where t.planid="+obj.getLong("planid")+" and t.tabid="+nt.getInptype()+"", "list");
					if(nz.size()>0){
						dao.PeaceCrud(null, "LnkPlanTab", "delete", (long) nz.get(0).getId(), 0, 0, null);
					}
				}
			}

			arr.put(jo1);

			jo.put("comdata", arr);

			return jo.toString();
		}
		return null;
	}


	@RequestMapping(value="/valform/{id}", method = RequestMethod.GET,produces={"application/json; charset=UTF-8"})
	public @ResponseBody String valForm(@PathVariable int id, HttpServletRequest req) throws ClassNotFoundException, JSONException{

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+id+"", "current");

			//List<LutFormNotes> lf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.divisionid="+an.getDivisionid()+" and t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.isfile=0  order by t.id", "list");

			List<LutFormNotes> lff=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.divisionid="+an.getDivisionid()+" and t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.isfile=1  order by t.id", "list");

			//	List<LnkPlanNotes> pn=(List<LnkPlanNotes>) dao.getHQLResult("from LnkPlanNotes t where t.planid="+id+"  order by t.id", "list");

			//	List<LnkPlanAttachedFiles> at=(List<LnkPlanAttachedFiles>) dao.getHQLResult("from LnkPlanAttachedFiles t where t.noteid="+id+"  order by t.id", "list");

			JSONArray com = new JSONArray();

			/*		for(int i=0;i<lf.size();i++){
				LutFormNotes fn= lf.get(i);

				List<LnkPlanNotes> pnn=(List<LnkPlanNotes>) dao.getHQLResult("from LnkPlanNotes t where t.noteid="+fn.getId()+" and t.expid="+an.getId()+"  order by t.id", "list");
				if(pnn.size()==0){
					JSONObject jo = new JSONObject();
					jo.put("id", fn.getId());
					jo.put("title", fn.getNote());	    			
					com.put(jo);
				}

			}*/
			for(int i=0;i<lff.size();i++){
				LutFormNotes fn= lff.get(i);	

				List<LnkPlanAttachedFiles> at=(List<LnkPlanAttachedFiles>) dao.getHQLResult("from LnkPlanAttachedFiles t where t.noteid="+fn.getId()+" and t.expid="+an.getId()+"   order by t.id", "list");
				if(at.size()==0){
					JSONObject jo = new JSONObject();
					jo.put("id", fn.getId());
					jo.put("title", fn.getNote());	    			
					com.put(jo);
				}

			}


			return com.toString();
		}

		return "false";

	}

	@ResponseBody 
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/sendform/{xtype}/{id}", method = RequestMethod.PUT,produces={"application/json; charset=UTF-8"})
	public String sendForm(@PathVariable int xtype,@PathVariable int id, HttpServletRequest req, @RequestBody(required=false) String jsonStr) throws ClassNotFoundException, JSONException{

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			JSONObject jsobj = null;
			if (jsonStr != null && jsonStr.length() > 0){
				jsobj = new JSONObject(jsonStr);
			}
			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

			Calendar c = Calendar.getInstance(Locale.ENGLISH);
			c.setFirstDayOfWeek(Calendar.FRIDAY);
			c.setTime(new Date());
			int today = c.get(Calendar.DAY_OF_WEEK);
			c.add(Calendar.DAY_OF_WEEK, -today+Calendar.FRIDAY);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String formatted = format1.format(c.getTime());
			
			Date d1 = new Date();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
			String formattedDate = df.format(d1);
			AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+id+"", "current");
			
			//AnnualRegistration annual=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+id+"", "current");
			
			LutYear ye=(LutYear) dao.getHQLResult("from LutYear t where t.type="+an.getReporttype()+" and t.value="+an.getReportyear()+" and t.divisionid="+an.getDivisionid()+" and t.isactive=1", "current");
			
			if(an.getXtype()!=null){
				if(an.getXtype()!=0){
					an.setXtype(xtype);
				}
				else if (jsobj != null){
					if (jsobj.has("reasonid") && !jsobj.isNull("reasonid")){
						an.setReasonid(jsobj.getInt("reasonid"));
					}
					if (jsobj.has("xcomment") && !jsobj.isNull("xcomment")){
						an.setXcomment(jsobj.getString("xcomment"));
					}
				}
			}
			else{
				an.setXtype(xtype);
			}
			
			
			if(an.getDivisionid()!=3){
				if(an.getReject()!=null){
					an.setRepstepid((long) an.getRejectstep());
				}	
			}
			if(an.getRepstatusid()==0){
				an.setSubmissiondate(formattedDate);
			}
			else{
				an.setLastmodified(formattedDate);
			}

			an.setRepstatusid((long) 7);
			dao.PeaceCrud(an, "AnnualRegistration", "update", (long) id, 0, 0, null);	
			
			LnkCommentMain cm= new LnkCommentMain();
			cm.setUsername(loguser.getUsername());
			cm.setUserid(loguser.getId());
			cm.setPlanid(an.getId());
			if(an.getReject()!=null){
				LutInternalization inte=(LutInternalization) dao.getHQLResult("from LutInternalization t where t.id=181", "current");
				cm.setMcomment(inte.getNameMn());	
			}
			else{
				LutInternalization inte=(LutInternalization) dao.getHQLResult("from LutInternalization t where t.id=180", "current");
				cm.setMcomment(inte.getNameMn());	
			}
			cm.setDesid((long) 1);
			cm.setCreatedDate(formattedDate);
			cm.setIsgov((long) 1);
			dao.PeaceCrud(cm, "LnkCommentMain", "save", (long) 0, 0, 0, null);	
			
			List<LnkPlanTransition> lf=(List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+an.getId()+"  order by t.id", "list");
			if(lf.size()==0){
				//System.out.println("exp"+an.getl);
				RegReportReq rreq=(RegReportReq) dao.getHQLResult("from RegReportReq t where t.id="+an.getReqid()+"", "current");
				if(rreq.getLnkReqAnns().size()>0){

					LnkReqAnn ann=(LnkReqAnn) dao.getHQLResult("from LnkReqAnn t where t.reqid="+an.getReqid()+" and t.cyear="+an.getReportyear()+"", "current");
					if(ann!=null){
						List<LutFormNotes> nt=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.iscommon=1 and t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid="+an.getDivisionid()+"  order by t.id", "list");
						for(int i=0;i<nt.size();i++){
							LutFormNotes it=nt.get(i);
							LnkPlanTransition tz= new LnkPlanTransition();
							tz.setNoteid(it.getId());
							tz.setOnoffid(it.getOnoffid());
							tz.setPlanid(an.getId());
							tz.setTabid(it.getInptype());
							tz.setIsapproval(it.getIsapproval());
							dao.PeaceCrud(tz, "LnkPlanTransition", "save", (long) 0, 0, 0, null);	
						}
						if(ann.getIsexplosion()==1){
							List<LutFormNotes> ex=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.iscommon=0 and t.isexplosion=1 and t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+"  order by t.id", "list");
							for(int i=0;i<ex.size();i++){
								LutFormNotes it=ex.get(i);
								LnkPlanTransition tz= new LnkPlanTransition();
								tz.setNoteid(it.getId());
								tz.setOnoffid(it.getOnoffid());
								tz.setPlanid(an.getId());
								tz.setTabid(it.getInptype());
								tz.setIsapproval(it.getIsapproval());
								dao.PeaceCrud(tz, "LnkPlanTransition", "save", (long) 0, 0, 0, null);	
							}
						}
						if(ann.getIsconcetrate()==1){
							List<LutFormNotes> co=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.iscommon=0 and t.isconcentrate=1 and t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid="+an.getDivisionid()+"  order by t.id", "list");
							for(int i=0;i<co.size();i++){
								LutFormNotes it=co.get(i);
								LnkPlanTransition tz= new LnkPlanTransition();
								tz.setNoteid(it.getId());
								tz.setOnoffid(it.getOnoffid());
								tz.setPlanid(an.getId());
								tz.setTabid(it.getInptype());
								tz.setIsapproval(it.getIsapproval());
								dao.PeaceCrud(tz, "LnkPlanTransition", "save", (long) 0, 0, 0, null);	
							}
						}
						if(ann.getIsmatter()==1){
							//List<LutFormNotes> ma=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.iscommon=0 and t.ismatter=1 and t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid="+an.getDivisionid()+"  order by t.id", "list");
							List<LutFormNotes> ma=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.iscommon=0 and t.ismatter=1 and t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+"  order by t.id", "list");
							for(int i=0;i<ma.size();i++){
								LutFormNotes it=ma.get(i);
								LnkPlanTransition tz= new LnkPlanTransition();
								tz.setNoteid(it.getId());
								tz.setOnoffid(it.getOnoffid());
								tz.setPlanid(an.getId());
								tz.setTabid(it.getInptype());
								tz.setIsapproval(it.getIsapproval());
								dao.PeaceCrud(tz, "LnkPlanTransition", "save", (long) 0, 0, 0, null);	
							}
						}
						else if(ann.getIsmatter()==2){
							List<LutFormNotes> ma=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.iscommon=0 and t.ismatter=0 and t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid="+an.getDivisionid()+"  order by t.id", "list");
							for(int i=0;i<ma.size();i++){
								LutFormNotes it=ma.get(i);
								LnkPlanTransition tz= new LnkPlanTransition();
								tz.setNoteid(it.getId());
								tz.setOnoffid(it.getOnoffid());
								tz.setPlanid(an.getId());
								tz.setTabid(it.getInptype());
								tz.setIsapproval(it.getIsapproval());
								dao.PeaceCrud(tz, "LnkPlanTransition", "save", (long) 0, 0, 0, null);	
							}
						}

					}					
				}
			}
			return "true";
			
			/*if(ye!=null || an.getRepstatusid()==2){
	
			}
			else{
				return "false";
			}*/
		
		}

		return "false";

	}

	@RequestMapping(value="/thumbnail",method=RequestMethod.GET)
	public void getThumbnail(HttpServletRequest req,HttpServletResponse response){
		try{
			System.out.println("thumbnail req coming ");

			String folderRoot = req.getParameter("path");
			System.out.println(folderRoot);
			File imgPath = new File(req.getSession().getServletContext().getRealPath(""+folderRoot+""));

			BufferedImage bufferedImage = ImageIO.read(imgPath.getAbsoluteFile());


			int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
			BufferedImage resizedImage = new BufferedImage(80, 50, type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(bufferedImage, 0, 0, 80, 50, null);
			g.dispose();
			OutputStream outputStream = response.getOutputStream();
			ImageIO.write(resizedImage, "png", outputStream);
			outputStream.close();

		}
		catch(Exception e){
			e.printStackTrace();

		}
	}

	@RequestMapping(value="/destroy",method=RequestMethod.DELETE)
	public @ResponseBody String destroyContent(@ModelAttribute("fileNames") String fileNames,HttpServletRequest req){
		try{
			String appPath = req.getSession().getServletContext().getRealPath("");
			String SAVE_DIR = "uploads";
			DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
			Date date1 = new Date();
			String special = dateFormat1.format(date1);
			String savePath = appPath + "/" + SAVE_DIR+ "/" +special;
			System.out.println("de dir"+special+" name"+fileNames);
			File logodestination = new File(savePath);
			if(!logodestination.exists()){
				logodestination.mkdir();
				System.out.println("end"+logodestination);
			}

			String path = appPath + "/" + SAVE_DIR+ "/" +special+ "/" + req.getParameter("fileNames");

			File newD = new File(path);
			if(newD.exists()){
				System.out.println("ustah zam (file) " +newD.getAbsolutePath());
				System.out.println("ustsan eseh "+ newD.delete());
			}

			return "true";
		}
		catch(Exception e){
			e.printStackTrace();
			return null;

		}
	}

	@RequestMapping(value="/save",method=RequestMethod.POST)
	public @ResponseBody String save( @RequestParam("files") MultipartFile files,@RequestParam("obj") String jsonstring, MultipartRequest mpr, HttpServletRequest req) throws JSONException, DocumentException, Exception {

		MultipartFile mfile =  null;
		mfile = (MultipartFile)files;
		System.out.println("upload hiigdeh file "+mfile.getInputStream());

		Gson gson= new Gson();
		JSONObject jo = new JSONObject();

		System.out.println("end"+jsonstring);
		if (mfile != null) {
			String appPath = req.getSession().getServletContext().getRealPath("");

			String SAVE_DIR = MramApplication.ROOT;

			Date d1 = new Date();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            SimpleDateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
			String special = df.format(d1);
            String folderDate = df2.format(d1);
			JSONObject obj= new JSONObject(jsonstring);

			LnkPlanAttachedFiles lr=(LnkPlanAttachedFiles) dao.getHQLResult("from LnkPlanAttachedFiles t where t.noteid="+obj.getLong("id")+" and t.expid="+obj.getLong("planid")+"", "current");
			List<LnkPlanTransition> tr=(List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid="+obj.getLong("id")+" and t.planid="+obj.getLong("planid")+"", "list");
			if(tr.size()>0){
				for(LnkPlanTransition item:tr){
					item.setDecisionid(0);
					dao.PeaceCrud(item, "LnkPlanTransition", "update", item.getId(), 0, 0, null);
				}		
			}
			List<LnkPlanTransition> transitions = (List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.noteid = " + obj.getLong("id") + " and t.planid = " + obj.getLong("planid"), "list");
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
					//dao.PeaceCrud(lr, "LnkPlanAttachedFiles", "delete", lr.getId(), 0, 0, null);
					
					if (obj.getLong("id") != 404){
						if (transitions != null && transitions.size() > 0){
							dao.PeaceCrud(null, "LnkPlanAttachedFiles", "multidelete", (long) obj.getLong("planid"), 0, 0, "where expid = " + obj.getLong("planid") + " and noteid = " + obj.getLong("id") + " and istodotgol = " + ((transitions.get(0).getIstodotgol() == true) ? "1" : "0"));
						}
						else{
							dao.PeaceCrud(null, "LnkPlanAttachedFiles", "multidelete", (long) obj.getLong("planid"), 0, 0, "where expid = " + obj.getLong("planid") + " and noteid = " + obj.getLong("id") + "and istodotgol = 0");
						}
						
					}
				}
			}


			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
			//List<LnkPlanAttachedFiles> rs=(List<LnkPlanAttachedFiles>) dao.getHQLResult("from LnkPlanAttachedFiles t ", "list");;
			byte[] array = new byte[7]; // length is bounded by 7
			new Random().nextBytes(array);
			int random = (int )(Math.random() * 50 + 1);

			String path = appPath + "/" + SAVE_DIR+ "/"  +loguser.getLpreg()+ "/" +folderDate;

			File folder = new File(path);
			if(!folder.exists()){
				folder.mkdirs();
			}


			String fpath = appPath + "/"+SAVE_DIR+ "/" +loguser.getLpreg()+ "/" +folderDate+ "/" +"("+random+")"+mfile.getOriginalFilename();
			File logoorgpath = new File(fpath);

			if(!logoorgpath.exists()){
				mfile.transferTo(logoorgpath);
			}
			else{
				jo.put("return", "false");
			}

			String path1 = "/" + SAVE_DIR+ "/" +loguser.getLpreg()+ "/" +folderDate+ "/" +"("+random+")"+mfile.getOriginalFilename();

			String ext = FilenameUtils.getExtension(path1);
			System.out.println("EXT ================ " + ext);



			LutFormNotes nt=(LutFormNotes) dao.getHQLResult("from LutFormNotes t where t.id="+obj.getInt("id")+"", "current");

			if(nt.getIsform()==1 && ext.equalsIgnoreCase("pdf")){    					 
				String ret = importPdf(mfile,req,obj.getLong("planid"),obj.getLong("id"),fpath);

				jo.put("rdata", ret);
				System.out.println("end"+ret);

				if(ret.equalsIgnoreCase("true")){
					LnkPlanAttachedFiles lp = new LnkPlanAttachedFiles();
					lp.setNoteid(obj.getLong("id"));
					lp.setAttachfiletype(path1);
					lp.setExpid(obj.getLong("planid"));
					lp.setFilename(mfile.getOriginalFilename());
					lp.setFileext(ext);
					lp.setAtdate(special);
					if (transitions != null && transitions.size() > 0){
						lp.setIstodotgol(transitions.get(0).getIstodotgol());
					}
					else{
						lp.setIstodotgol(false);
					}
					dao.PeaceCrud(lp, "LnkPlanNotes", "save", (long) 0, 0, 0, null);	

					JSONArray arr = new JSONArray();	
					JSONObject jo1 = new JSONObject();
					jo1.put("id", lp.getId());
					jo1.put("url", lp.getAttachfiletype());
					jo1.put("title", lp.getFilename());
					jo1.put("date", lp.getAtdate());		    		
					jo1.put("ext",lp.getFileext());
					arr.put(jo1);

					jo.put("atdata", arr);
				}

			}
			else{
				//jo.put("rdata", ret);
				//System.out.println("end"+ret);

				//if(ret.equalsIgnoreCase("true")){
				LnkPlanAttachedFiles lp = new LnkPlanAttachedFiles();
				lp.setNoteid(obj.getLong("id"));
				lp.setAttachfiletype(path1);
				lp.setExpid(obj.getLong("planid"));
				lp.setFilename(mfile.getOriginalFilename());
				lp.setFileext(ext);
				lp.setAtdate(special);
				dao.PeaceCrud(lp, "LnkPlanNotes", "save", (long) 0, 0, 0, null);	

				JSONArray arr = new JSONArray();	
				JSONObject jo1 = new JSONObject();
				jo1.put("id", lp.getId());
				jo1.put("url", lp.getAttachfiletype());
				jo1.put("title", lp.getFilename());
				jo1.put("date", lp.getAtdate());		    		
				jo1.put("ext",lp.getFileext());
				arr.put(jo1);

				jo.put("atdata", arr);
				//}
			}


			jo.put("return", "true");

		}        
		return jo.toString();

	}
	public void manipulatePdf(String src, String dest) throws DocumentException, IOException {
		PdfReader reader = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		AcroFields form = stamper.getAcroFields();
		form.removeXfa();
		XfaForm xfa = form.getXfa();

		/*       xfa.setXfaPresent(false);
	        Map<String, AcroFields.Item> fields = form.getFields();
	        for (String name : fields.keySet()) {
	            if (name.indexOf("Total") > 0)
	                form.setFieldProperty(name, "textcolor", BaseColor.RED, null);
	            form.setField(name, "X");
	        }*/
		stamper.setFormFlattening(true);
		stamper.close();
		reader.close();
	}

	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public @ResponseBody String destroy( @RequestParam("fileNames") String fileNames, HttpServletRequest req) throws IOException {
		String appPath = req.getSession().getServletContext().getRealPath("");
		String DEL_DIR = "uploads";
		String folder = "album";
		DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
		Date date1 = new Date();
		String special = dateFormat1.format(date1);
		//clean-up    
		String delPath = appPath + "/" + DEL_DIR+ "/" + folder+ "/" +special+ "/"  +fileNames;
		System.out.println("del dir"+delPath);
		File destination = new File(delPath);		    	

		if(destination.delete()){
			System.out.println(destination.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
		}
		return "true";

	}

    @RequestMapping(value="/fix/mining/form1",method=RequestMethod.GET)
    public String fixMining1(HttpServletRequest req){
        List<Long> planids = (List<Long>) dao.getHQLResult("select distinct(planid) from DataMinPlan1", "list");
        for(Long id : planids){
            AnnualRegistration annualRegistration = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id = " + id, "current");
            if (annualRegistration == null){
                dao.PeaceCrud(null, "DataMinPlan1", "multidelete", (long) id, 0, 0, "where planid = " + id);
            }
        }
        List<DataMinPlan1> datas = (List<DataMinPlan1>) dao.getHQLResult("from DataMinPlan1 t order by t.id asc", "list");
        Long dataIndex = null;
        for(DataMinPlan1 d : datas){
            if (d.getDataIndex() != null){
                dataIndex = d.getDataIndex();
            }
            else{
                d.setDataIndex(dataIndex);
                dao.PeaceCrud(d, "DataMinPlan1", "save", (long) d.getId(), 0, 0, null);
            }
        }
        return "true";
    }

	public String fNameGen(String extension,String type){
		String fullpath="";
		Random r = new Random();
		String token = Long.toString(Math.abs(r.nextLong()), 36).substring(0,Long.toString(Math.abs(r.nextLong()), 36).length()-1);
		DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date1 = new Date();
		String special = dateFormat1.format(date1);

		token=token+special;

		fullpath = type+special+"."+extension;

		return fullpath;

	}
}
