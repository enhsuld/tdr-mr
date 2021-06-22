package com.peace.web.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import java.util.UUID;

//import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;

import com.peace.users.model.mram.*;
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
import com.peace.users.model.DataSourceResult;
import com.peace.MramApplication;
import com.peace.users.dao.FileBean;
import com.peace.users.dao.UserDao;
import com.peace.users.model.Excel;
import com.peace.users.service.MyUserDetailsService;


@RestController
@RequestMapping("/user/service")
public class ServiceController {


    @Autowired
    private UserDao dao;

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    Services services;

    final static Logger logger = Logger.getLogger(ServiceController.class);


    @RequestMapping(value = "/rs/{domain}/{path}/{expid}", method = RequestMethod.GET)
    public @ResponseBody
    String domSgtw(@PathVariable String domain, @PathVariable String path, @PathVariable Long expid, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            Class<?> classtoConvert;
            classtoConvert = Class.forName(domain.equalsIgnoreCase("AnnualRegistrationDashboard") ? "com.peace.users.model.mram.AnnualRegistration" : domain);
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                ObjectMapper mapper = new ObjectMapper();
                if (domain.equalsIgnoreCase("com.peace.users.model.mram.LnkReqAnn")) {
                    List<LnkReqAnn> rs = (List<LnkReqAnn>) dao.getHQLResult("from " + domain + " t where t." + path + "=" + expid + "", "list");
                    if (rs.size() > 0) {
                        return mapper.writeValueAsString(rs.get(0));
                    } else {
                        return "false";
                    }
                } else if (domain.equalsIgnoreCase("com.peace.users.model.mram.LnkReqPv")) {
                    List<LnkReqAnn> rs = (List<LnkReqAnn>) dao.getHQLResult("from " + domain + " t where t." + path + "=" + expid + "", "list");
                    if (rs.size() > 0) {
                        return mapper.writeValueAsString(rs.get(rs.size() - 1));
                    } else {
                        return null;
                    }
                } else if (domain.equalsIgnoreCase("AnnualRegistrationDashboard")) {
                    List<AnnualRegistration> rs = (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t." + path + "=" + userDetail.getUsername() + "", "list");
                    if (rs.size() > 0) {
                        return mapper.writeValueAsString(rs);
                    } else {
                        return null;
                    }
                } else {
                    return mapper.writeValueAsString(dao.getHQLResult("from " + domain + " t where t." + path + "=" + expid + "", "current"));
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "error.500";
    }

    @RequestMapping(value = "/rs/{domain}/{path}/{expid}/{path2}/{id2}", method = RequestMethod.GET)
    public @ResponseBody
    String widthids(@PathVariable String domain, @PathVariable String path, @PathVariable Long expid, @PathVariable String path2, @PathVariable Long id2, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            Class<?> classtoConvert;
            classtoConvert = Class.forName(domain);


            if (!(auth instanceof AnonymousAuthenticationToken)) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(dao.getHQLResult("from " + domain + " t where t." + path + "=" + expid + " and t." + path2 + "=" + id2 + "", "current"));
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "error.500";
    }

    @RequestMapping(value = "/anstep/{domain}/{mid}/{tabid}", method = RequestMethod.GET)
    public @ResponseBody
    String aaa(@PathVariable String domain, @PathVariable Long mid, @PathVariable Long tabid, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Class<?> classtoConvert;
            classtoConvert = Class.forName(domain);
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                ObjectMapper mapper = new ObjectMapper();
                List<LnkPlanTransition> rs = (List<LnkPlanTransition>) dao.getHQLResult("from " + domain + " t where t.decisionid=0 and t.tabid=" + tabid + " and t.planid=" + mid + "", "list");
                if (rs.size() == 0) {
                    return "true";
                } else {
                    return "false";
                }
                // return mapper.writeValueAsString(dao.getHQLResult("from "+domain+" t where t."+path+"="+lr.getReqid()+"", "current"));
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "error.500";
    }

    @RequestMapping(value = "/ann/{domain}/{path}/{expid}", method = RequestMethod.GET)
    public @ResponseBody
    String domann(@PathVariable String domain, @PathVariable String path, @PathVariable Long expid, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Class<?> classtoConvert;
            classtoConvert = Class.forName(domain);
            AnnualRegistration lr = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id='" + expid + "'", "current");

            if (!(auth instanceof AnonymousAuthenticationToken)) {
                ObjectMapper mapper = new ObjectMapper();
                List<LnkReqAnn> rs = (List<LnkReqAnn>) dao.getHQLResult("from " + domain + " t where t." + path + "=" + lr.getReqid() + "", "list");
                if (rs.size() > 0) {
                    return mapper.writeValueAsString(rs.get(rs.size() - 1));
                } else {
                    return null;
                }
                // return mapper.writeValueAsString(dao.getHQLResult("from "+domain+" t where t."+path+"="+lr.getReqid()+"", "current"));
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "error.500";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/removeAttach/{id}", method = RequestMethod.DELETE, produces = {"application/json; charset=UTF-8"})
    public String removeAttach(@PathVariable int id, HttpServletRequest req) throws ClassNotFoundException, JSONException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            LnkPlanAttachedFiles lr = (LnkPlanAttachedFiles) dao.getHQLResult("from LnkPlanAttachedFiles t where t.id='" + id + "'", "current");
            String appPath = req.getSession().getServletContext().getRealPath("");
            String delPath = appPath + lr.getAttachfiletype();
            System.out.println(lr.getAttachfiletype() + " is deleted!");
            File file = new File(delPath);

            if (file.exists()) {
                System.out.println(file.getName() + " bn!");
            } else {
                System.out.println("bhgu.");
            }
            if (lr.getFileext().equalsIgnoreCase("xlsm") == false){
                if (file.delete()) {
                    System.out.println(file.getName() + " is deleted!");
                } else {
                    System.out.println("Delete operation is failed.");
                }
            }

            dao.PeaceCrud(lr, "LnkPlanAttachedFiles", "delete", lr.getId(), 0, 0, null);
            return "true";
        }

        return "false";

    }

    @RequestMapping(value = "/getWeekDetail/{expid}", method = RequestMethod.GET)
    public @ResponseBody
    String tzw(@PathVariable Long expid, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();


            if (!(auth instanceof AnonymousAuthenticationToken)) {

                JSONArray main = new JSONArray();
                JSONObject obj = new JSONObject();
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");

                WeeklyRegistration an = (WeeklyRegistration) dao.getHQLResult("from WeeklyRegistration t where t.id=" + expid + " ", "current");
                if (loguser.getLpreg().equalsIgnoreCase("9999999")) {
                    if (an.getRepstatusid() == 0 || an.getRepstatusid() == 3 || an.getRepstatusid() == 8 || an.getRepstatusid() == 7) {
                        obj.put("onoff", true);
                    } else {
                        obj.put("onoff", false);
                    }
                } else {
                    if (an.getRepstatusid() == 0 || an.getRepstatusid() == 3 || an.getRepstatusid() == 8) {
                        obj.put("onoff", true);
                    } else {
                        obj.put("onoff", false);
                    }
                }

                ObjectMapper mapper = new ObjectMapper();
                List<LnkCommentWeekly> cm = an.getLnkCommentWeeklies();

                JSONArray mcm = new JSONArray();
                for (int i = 0; i < cm.size(); i++) {
                    JSONObject cc = new JSONObject();
                    cc.put("desid", cm.get(i).getDesid());
                    cc.put("username", cm.get(i).getUsername());
                    cc.put("mcomment", cm.get(i).getMcomment());
                    cc.put("id", cm.get(i).getId());
                    cc.put("date", cm.get(i).getComdate());
                    mcm.put(cc);
                }
                obj.put("mcom", mcm);
                obj.put("comment", an.getCommenttext());
                main.put(obj);
                return main.toString();
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "error.500";
    }


    @RequestMapping(value = "/confirmWeekReport/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    String confirmWeekReport(@RequestBody String jsonString, @PathVariable int id, HttpServletRequest req) throws ClassNotFoundException, JSONException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {

            UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");

            WeeklyRegistration lic = (WeeklyRegistration) dao.getHQLResult("from WeeklyRegistration t where t.id='" + id + "'", "current");          
            JSONObject str = new JSONObject(jsonString);
            Calendar c = Calendar.getInstance(Locale.ENGLISH);
           // SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            String formatted = dateFormat.format(c.getTime());
            
            WeeklyRegistration rw = lic;
            if (rw.getRepstatusid() == 3) {
                rw.setRepstatusid((long) 8);
            } else {
                rw.setRepstatusid((long) 7);
            }

            rw.setSubmissiondate(formatted);
            rw.setCommenttext(str.getString("comment"));
            rw.setPersonid(loguser.getId());
            dao.PeaceCrud(rw, "RegWeeklyMontly", "update", rw.getId(), 0, 0, null);
            
            LnkCommentWeekly mn = new LnkCommentWeekly();
			mn.setMcomment(str.getString("comment"));
			mn.setWrid(lic.getId());
			mn.setUserid(loguser.getId());
			mn.setComdate(formatted);
			mn.setUsername(loguser.getUsername());
			mn.setDesid((long) 7);
			dao.PeaceCrud(mn, "LnkCommentWeekly", "save", (long) 0, 0, 0, null);		 

            return "true";
        }

        return "false";

    }

    @RequestMapping(value = "/sgt/{expid}", method = RequestMethod.GET)
    public @ResponseBody
    String detailSgt(@PathVariable Long expid, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();


            if (!(auth instanceof AnonymousAuthenticationToken)) {

                JSONArray main = new JSONArray();
                JSONObject obj = new JSONObject();

                AnnualRegistration an = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id=" + expid + " ", "current");

                obj.put("lcnum", an.getLicensenum());
                obj.put("lcxb",an.getLicenseXB());
                obj.put("lpreg", an.getLpReg());
                obj.put("lictype", an.getLictype());
                obj.put("year", an.getReportyear());
                if (an.getRepstatusid() == 0 || an.getRepstatusid() == 2) {
                    obj.put("send", true);
                } else {
                    obj.put("send", false);
                }
                obj.put("xxx", an.getRepstatusid());
                obj.put("reporttype", an.getReporttype());
                obj.put("step", an.getRepstatusid());
                obj.put("divisionid", an.getDivisionid());
                obj.put("reasonid", an.getReasonid());
                obj.put("xtype", an.getXtype());
                obj.put("xcomment", an.getXcomment());
                obj.put("lpname", an.getSubLegalpersons().getLpName());
                main.put(obj);
                return main.toString();
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "error.500";
    }

    @RequestMapping(value = "/wsgt/{expid}", method = RequestMethod.GET)
    public @ResponseBody
    String detailSgtw(@PathVariable Long expid, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();


            if (!(auth instanceof AnonymousAuthenticationToken)) {

                JSONArray main = new JSONArray();
                JSONObject obj = new JSONObject();

                WeeklyRegistration an = (WeeklyRegistration) dao.getHQLResult("from WeeklyRegistration t where t.id=" + expid + " ", "current");

                LutWeeks wk = (LutWeeks) dao.getHQLResult("from LutWeeks t where t.id=" + an.getWeekid() + " ", "current");

                obj.put("lcnum", an.getLicensenum());
                obj.put("lpreg", an.getLpReg());
                obj.put("lictype", an.getLictype());
                obj.put("year", an.getYear());
                obj.put("reporttype", an.getReporttype());
                obj.put("weekid", an.getWeekid());
                obj.put("lpname", an.getSubLegalpersons().getLpName());
                obj.put("range", wk.getStartdate() + " - " + wk.getEnddate());
                main.put(obj);
                return main.toString();
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "error.500";
    }

    @RequestMapping(value = "/tz/{expid}", method = RequestMethod.GET)
    public @ResponseBody
    String tz(@PathVariable Long expid, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();


            if (!(auth instanceof AnonymousAuthenticationToken)) {

                JSONArray main = new JSONArray();
                JSONObject obj = new JSONObject();

                AnnualRegistration an = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id=" + expid + " ", "current");

                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");

                if ((long) an.getGroupid() == loguser.getGroupid()) {
                    if (an.getRepstatusid() == 7) {
                        obj.put("xxx", "true");
                    } else {
                        obj.put("xxx", "false");
                    }
                } else {
                    obj.put("xxx", "false");
                }

                if (loguser.getPositionid() == 1) {
                    obj.put("des", "true");
                } else {
                    obj.put("des", "false");
                }

                obj.put("lcnum", an.getLicensenum());
                obj.put("lpreg", an.getLpReg());
                obj.put("lictype", an.getLictype());
                obj.put("year", an.getReportyear());
                obj.put("reporttype", an.getReporttype());

                obj.put("lpname", an.getSubLegalpersons().getLpName());
                main.put(obj);
                return main.toString();
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "error.500";
    }

    @RequestMapping(value = "/xonoff/{expid}", method = RequestMethod.GET)
    public @ResponseBody
    String xonoff(@PathVariable Long expid, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (!(auth instanceof AnonymousAuthenticationToken)) {

                JSONArray main = new JSONArray();
                JSONObject obj = new JSONObject();

                AnnualRegistration an = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id=" + expid + " ", "current");


                if (an.getRepstatusid() == 0 || an.getRepstatusid() == 2) {
                    obj.put("xxx", true);
                } else {
                    obj.put("xxx", false);
                }
                main.put(obj);
                return main.toString();
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "error.500";
    }

    @RequestMapping(value = "/note/imp/{expid}", method = RequestMethod.GET)
    public @ResponseBody
    String detailNote(@PathVariable Long expid, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();


            if (!(auth instanceof AnonymousAuthenticationToken)) {

                JSONArray main = new JSONArray();
                JSONObject obj = new JSONObject();
                JSONArray arr1 = new JSONArray();

                AnnualRegistration an = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id=" + expid + " ", "current");
                SubLicenses lic = (SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='" + an.getLicensenum() + "'", "current");
                List<LutFormNotes> lf = null;

                String str = "";

                for (int i = 0; i < an.getLnkPlanNoteses().size(); i++) {
                    str = str + "," + an.getLnkPlanNoteses().get(i).getNoteid();

                }

                for (int i = 0; i < an.getLnkPlanAttachedFileses().size(); i++) {
                    str = str + "," + an.getLnkPlanAttachedFileses().get(i).getNoteid();
                }

                List<LnkReqAnn> lanl = (List<LnkReqAnn>) dao.getHQLResult("from LnkReqAnn t where t.reqid='" + an.getReqid() + "'", "list");

                LnkReqAnn lan = null;
                if (lanl.size() > 0) {
                    lan = lanl.get(lanl.size() - 1);
                }

                //an.getRegReportReq().get

                System.out.println("###########################" + str);
                System.out.println("minid" + an.getMinid());
                System.out.println("xv" + an.getLictype());
                System.out.println("xtype" + an.getXtype());

                if (an.getXtype() == null || an.getXtype() != 0) {
                    if (an.getMinid() != null) {
                        if (an.getMinid() == 5) {
                            if (lanl.size() > 0) {
                                lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2 and t.iscommon=1 and t.isxreport=0 order by t.id", "list");
                            } else {
                                lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.iscommon=1 and t.isxreport=0 order by t.id", "list");
                            }
                        } else {
                            if (an.getLictype() == 1) {
                                System.out.println("end lic==1");
                                ;
                                lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.iscommon=1 and t.isxreport=0 order by t.id", "list");
                            } else {
                                System.out.println("end lic!=1");
                                ;
                                System.out.println("end division" + an.getDivisionid());
                                ;
                                if (an.getDivisionid() == 2) {
                                    lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2 and t.iscommon=1 and t.isxreport=0 order by t.id", "list");
                                } else {
                                    lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.iscommon=1 and t.isxreport=0 order by t.id", "list");
                                }
                            }
                        }
                    } else {
                        lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.iscommon=1 and t.isxreport=0  order by t.id", "list");
                    }

                    List<LutFormNotes> clf = null;

                    if (lan != null) {

                        if (lan.getMinetypeid() != null) {
                            clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.divisionid=" + an.getDivisionid() + "  and t.reporttype=" + an.getReporttype() + " and t.mintypeid=" + lan.getMinetypeid() + "   order by t.id", "list");
                            for (int i = 0; i < clf.size(); i++) {
                                lf.add(clf.get(i));
                            }
                        }

                        if (lan.getIsconcetrate() == 1) {
                            if (an.getMinid() != null) {
                                if (an.getMinid() == 5) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2 and t.isconcentrate=1   order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        lf.add(clf.get(i));
                                    }
                                } else {
                                    if (an.getLictype() == 1) {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.isconcentrate=1 order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    } else {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.isconcentrate=1  order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            } else {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.isconcentrate=1   order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                        if (lan.getIsexplosion() == 1) {
                            if (an.getMinid() != null) {
                                if (an.getMinid() == 5) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2   and t.isexplosion=1  order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        lf.add(clf.get(i));
                                    }
                                } else {
                                    if (an.getLictype() == 1) {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.isexplosion=1 order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    } else {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.isexplosion=1  order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            } else {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.isexplosion=1   order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                        if (lan.getKomissid() == 1) {
                            if (an.getMinid() != null) {
                                if (an.getMinid() == 5) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2  and t.iskomiss=1  order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        lf.add(clf.get(i));
                                    }
                                } else {
                                    if (an.getLictype() == 1) {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.iskomiss=1 order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    } else {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.iskomiss=1  order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            } else {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.iskomiss=1   order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                        if (lan.getStatebudgetid() == 1) {
                            if (an.getMinid() != null) {
                                if (an.getMinid() == 5) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + "  and t.isbudget=1 and t.divisionid=2 order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        lf.add(clf.get(i));
                                    }
                                } else {
                                    if (an.getLictype() == 1) {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.isbudget=1 order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    } else {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.isbudget=1  order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            } else {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.isbudget=1   order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                        if (lan.getIsmatter() == 1) {
                            if (an.getMinid() != null) {
                                if (an.getMinid() == 5) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2 and t.ismatter=1  order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        lf.add(clf.get(i));
                                    }
                                } else {
                                    if (an.getLictype() == 1) {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.ismatter=1 order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    } else {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.ismatter=1  order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            } else {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.ismatter=1   order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                        if (lan.getIsmatter() == 2) {

                            System.out.println("nenene");

                            if (an.getMinid() != null) {
                                if (an.getMinid() == 5) {
                                    System.out.println("5");
                                /*	clf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid=2 and t.ismatter=0 and t.iscommon=0 and t.isconcentrate=0 and t.isexplosion=0  order by t.id", "list");
									for(int i=0;i<clf.size();i++){
										lf.add(clf.get(i));
									}*/
                                } else {
                                    System.out.println("6");
                                    if (an.getLictype() == 1) {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.ismatter=0 and t.iscommon=0 and t.isconcentrate=0 and t.isexplosion=0 order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    } else {
                                        if (an.getDivisionid() != 2) {
                                            clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.ismatter=0 and t.iscommon=0  and t.isconcentrate=0 and t.isexplosion=0 order by t.id", "list");
                                            for (int i = 0; i < clf.size(); i++) {
                                                lf.add(clf.get(i));
                                            }
                                        }
                                    }
                                }
                            } else {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.ismatter=0 and t.iscommon=0 and t.isconcentrate=0 and t.isexplosion=0 order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                    }

                    if (lic != null) {
                        if (lic.getLplan() != null && lic.getLplan()) {
                            if (an.getMinid() != null) {
                                if (an.getMinid() == 5) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + "  and t.lplan=1 and t.divisionid=2 order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        lf.add(clf.get(i));
                                    }
                                } else {
                                    if (an.getLictype() == 1) {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.lplan=1 order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    } else {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.lplan=1  order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            } else {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.lplan=1   order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                        if (lic.getLreport() != null && lic.getLreport()) {
                            if (an.getMinid() != null) {
                                if (an.getMinid() == 5) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + "  and t.lreport=1 and t.divisionid=2 order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        lf.add(clf.get(i));
                                    }
                                } else {
                                    if (an.getLictype() == 1) {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.lreport=1 order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    } else {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.lreport=1  order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            } else {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.lreport=1   order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                    }
                } else {
                    if (an.getMinid() != null) {
                        if (an.getMinid() == 5) {
                            if (lanl.size() > 0) {
                                lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2 and t.iscommon=1 and t.isxreport=1 order by t.id", "list");
                            } else {
                                lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.iscommon=1 and t.isxreport=1 order by t.id", "list");
                            }
                        } else {
                            if (an.getLictype() == 1) {
                                System.out.println("end lic==1");
                                ;
                                lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.iscommon=1 and t.isxreport=1 order by t.id", "list");
                            } else {
                                System.out.println("end lic!=1");
                                ;
                                System.out.println("end division" + an.getDivisionid());
                                ;
                                if (an.getDivisionid() == 2) {
                                    lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2 and t.iscommon=1 and t.isxreport=1 order by t.id", "list");
                                } else {
                                    lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.iscommon=1 and t.isxreport=1 order by t.id", "list");
                                }
                            }
                        }
                    } else {
                        lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.iscommon=1 and t.isxreport=1   order by t.id", "list");
                    }
                }


                JSONArray forms = new JSONArray();

                for (int i = 0; i < lf.size(); i++) {
                    JSONObject jo = new JSONObject();
                    if (lf.get(i).getIsform() == 1) {

                        if (lf.get(i).getNote().contains("-")) {
                            String[] strarr = lf.get(i).getNote().split("-");
                            jo.put("form", strarr[1]);
                            jo.put("name", strarr[0]);
                        } else {
                            jo.put("name", lf.get(i).getNote());
                        }
                        jo.put("isform", lf.get(i).getIsform());

                        jo.put("id", lf.get(i).getId());
                        jo.put("inptype", lf.get(i).getInptype());
                        jo.put("url", lf.get(i).getUrl());
                        forms.put(jo);
                    }
                }


                if (an.getLnkPlanNoteses().size() > 0 || an.getLnkPlanAttachedFileses().size() > 0) {
                    JSONArray div = new JSONArray();

                    for (int i = 0; i < lf.size(); i++) {
                        System.out.println("id " + lf.get(i).getId());

                        JSONObject jo = new JSONObject();
                        jo.put("id", lf.get(i).getId());
                        jo.put("title", lf.get(i).getNote());
                        jo.put("inptype", lf.get(i).getInptype());
                        jo.put("divisionid", lf.get(i).getDivisionid());
                        jo.put("help", lf.get(i).getNotecontent());
                        jo.put("isform", lf.get(i).getIsform());
                        jo.put("size", lf.get(i).getFsize());
                        jo.put("issaved", "0");
                        jo.put("isrequired", lf.get(i).getIsrequired());

                        JSONArray arr = new JSONArray();

                        //	List<LnkPlanAttachedFiles> at=(List<LnkPlanAttachedFiles>) dao.getHQLResult("from LnkPlanAttachedFiles t where t.noteid="+lf.get(i).getId()+" and t.expid="+expid+" order by t.id", "list");

                        List<LnkPlanAttachedFiles> at = an.getLnkPlanAttachedFileses();

                        List<LnkPlanTransition> tz = an.getLnkPlanTransitions();

                        System.out.println("id 1 " + lf.get(i).getId());

                        for (int t = 0; t < tz.size(); t++) {
                            if ((long) lf.get(i).getId() == an.getLnkPlanTransitions().get(t).getNoteid()) {
                                if (tz.get(t).getDecisionid() != null) {
                                    jo.put("decision", tz.get(t).getDecisionid());
                                    jo.put("mdecision", tz.get(t).getMdecisionid());

                                } else if (tz.get(t).getMdecisionid() != null) {
                                    if (tz.get(t).getDecisionid() != null) {
                                        jo.put("decision", tz.get(t).getDecisionid());
                                    } else {
                                        jo.put("decision", 0);
                                    }
                                    jo.put("mdecision", tz.get(t).getMdecisionid());
                                } else {
                                    jo.put("decision", 0);
                                }
                            }
                        }

                        System.out.println("id 2 " + at.size());
                        if (at.size() > 0) {
                            for (int c = 0; c < at.size(); c++) {
                                if (at.get(c).getNoteid() != null) {
                                    if ((long) lf.get(i).getId() == at.get(c).getNoteid()) {
                                        jo.put("issaved", "10");
                                        JSONObject jo1 = new JSONObject();
                                        jo1.put("id", at.get(c).getId());
                                        jo1.put("url", at.get(c).getAttachfiletype());
                                        jo1.put("title", at.get(c).getFilename());
                                        jo1.put("date", at.get(c).getAtdate());
                                        jo1.put("ext", at.get(c).getFileext());
                                        jo1.put("size", lf.get(i).getFsize());
                                        arr.put(jo1);
                                    }
                                }
                            }
                        }

                        System.out.println("id 3 " + lf.get(i).getId());
                        //List<LnkComment> cmm=(List<LnkComment>) dao.getHQLResult("from LnkComment t where t.noteid="+lf.get(i).getId()+" and t.planid="+expid+" order by t.id", "list");
                        List<LnkComment> cmm = an.getLnkComments();
                        JSONArray com = new JSONArray();
                        if (cmm.size() > 0) {
                            for (int w = 0; w < cmm.size(); w++) {
                                LnkComment lnkcom = cmm.get(w);
                                if ((long) lf.get(i).getId() == cmm.get(w).getNoteid()) {
                                    JSONObject jo1 = new JSONObject();
                                    JSONArray content = new JSONArray();
                                    content.put(lnkcom.getComnote());
                                    jo1.put("user_id", lnkcom.getOfficerid());
                                    jo1.put("user_name", lnkcom.getOfficername());
                                    jo1.put("content", content);
                                    jo1.put("des", lnkcom.getLutDecisions().getDecisionNameMon());
                                    jo1.put("destext", lnkcom.getComnote());
                                    jo1.put("desicion", lnkcom.getDesicionid());
                                    jo1.put("date", lnkcom.getComdate());
                                    com.put(jo1);
                                }
                            }
                        }

                        jo.put("comment", com);

                        jo.put("images", arr);

                        List<LnkPlanNotes> lpc = an.getLnkPlanNoteses();
                        if (lpc.size() > 0) {
                            for (int j = 0; j < lpc.size(); j++) {
                                if (lf.get(i).getId() == lpc.get(j).getNoteid()) {
                                    jo.put("issaved", "10");
                                    jo.put("content", lpc.get(j).getNotes());
                                    jo.put("date", lpc.get(j).getNotedate());
                                }

                            }
                        }


                        div.put(jo);

                    }


                    List<LnkCommentMain> mcom = an.getLnkCommentMains();
                    JSONArray coma = new JSONArray();
                    if (mcom.size() > 0) {
                        for (int j = 0; j < mcom.size(); j++) {
                            JSONObject mn = new JSONObject();
                            mn.put("content", mcom.get(j).getMcomment());
                            mn.put("userid", mcom.get(j).getUserid());
                            mn.put("username", mcom.get(j).getUsername());
                            mn.put("cdate", mcom.get(j).getCreatedDate());
                            coma.put(mn);
                        }
                    }

                    obj.put("mcom", coma);
                    obj.put("form", forms);
                    obj.put("notes", div);
                } else {
                    JSONArray coma = new JSONArray();
                    JSONArray div = new JSONArray();
                    for (int i = 0; i < lf.size(); i++) {
                        JSONObject jo = new JSONObject();
                        jo.put("id", lf.get(i).getId());
                        jo.put("title", lf.get(i).getNote());
                        jo.put("title", lf.get(i).getNote());
                        jo.put("inptype", lf.get(i).getInptype());
                        jo.put("divisionid", lf.get(i).getDivisionid());
                        jo.put("content", "");
                        jo.put("comment", "");
                        jo.put("date", "");
                        jo.put("help", lf.get(i).getNotecontent());
                        jo.put("isform", lf.get(i).getIsform());
                        jo.put("size", lf.get(i).getFsize());
                        jo.put("images", arr1);
                        jo.put("isrequired", lf.get(i).getIsrequired());
                        div.put(jo);
                    }
                    obj.put("mcom", coma);
                    obj.put("form", forms);
                    obj.put("notes", div);
                }
                main.put(obj);
                return main.toString();
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return "error.500";
    }


    @RequestMapping(value = "/note/imp/gov/{expid}", method = RequestMethod.GET)
    public @ResponseBody
    String detailNoteGov(@PathVariable Long expid, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (!(auth instanceof AnonymousAuthenticationToken)) {

                JSONArray main = new JSONArray();
                JSONObject obj = new JSONObject();
                JSONArray arr1 = new JSONArray();


                AnnualRegistration an = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id=" + expid + " ", "current");
                SubLicenses lic = (SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='" + an.getLicensenum() + "'", "current");

                List<LnkPlanNotes> lp = an.getLnkPlanNoteses();
                List<LutFormNotes> lf = null;

                String str = "";

                for (int i = 0; i < an.getLnkPlanNoteses().size(); i++) {
                    if (an.getLnkPlanNoteses().get(i) != null) {
                        str = str + "," + an.getLnkPlanNoteses().get(i).getNoteid();
                    }
                }
                if (an.getLnkPlanAttachedFileses().size() > 0) {
                    for (int i = 0; i < an.getLnkPlanAttachedFileses().size(); i++) {
                        str = str + "," + an.getLnkPlanAttachedFileses().get(i).getNoteid();
                    }

                }
                lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.id in (" + ((str.length() > 1) ? str.substring(1) : "0") + ")  order by t.id", "list");

                if (an.getMinid() != null) {
                    if (an.getMinid() == 5) {
                        System.out.println("end minid==5");
                        ;
                        lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=" + an.getDivisionid() + " and t.iscommon=1  order by t.id", "list");
                    } else {
                        if (an.getLictype() == 1) {
                            System.out.println("end lic==1");
                            ;
                            lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.iscommon=1 order by t.id", "list");
                        } else {
                            System.out.println("end lic!=1");
                            ;
                            System.out.println("end division" + an.getDivisionid());
                            ;
                            if (an.getDivisionid() == 2) {
                                lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2 and t.iscommon=1  order by t.id", "list");
                            } else {
                                lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.iscommon=1  order by t.id", "list");
                            }
                        }
                    }
                } else {
                    lf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.iscommon=1   order by t.id", "list");
                }


                //LutYear ye=(LutYear) dao.getHQLResult("from LutYear t where t.type="+an.getReporttype()+" and t.isactive=1", "current");
                LnkReqAnn lan = null;
                if (an.getReporttype() == 3) {
                    long year = Long.parseLong(an.getReportyear());
                    //lan=(LnkReqAnn) dao.getHQLResult("from LnkReqAnn t where t.reqid='"+an.getReqid()+"' and t.cyear='"+(year-1)+"'", "current");
                    List<LnkReqAnn> ran = (List<LnkReqAnn>) dao.getHQLResult("from LnkReqAnn t where t.reqid='" + an.getReqid() + "'", "list");
                    if (ran.size() > 0) {
                        lan = ran.get(ran.size() - 1);
                    }

                } else {
                    //List<LnkReqAnn> ran = (List<LnkReqAnn>) dao.getHQLResult("from LnkReqAnn t where t.reqid='" + an.getReqid() + "' and t.cyear='" + an.getReportyear() + "'", "list");
                    List<LnkReqAnn> ran = (List<LnkReqAnn>) dao.getHQLResult("from LnkReqAnn t where t.reqid='" + an.getReqid() + "'", "list");
                    if (ran.size() > 0) {
                        lan = ran.get(ran.size() - 1);
                    }
                }


                List<LutFormNotes> clf = null;

                if (lan != null) {
                    if (lan.getMinetypeid() != null) {
                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.divisionid=" + an.getDivisionid() + "  and t.reporttype=" + an.getReporttype() + " and t.mintypeid=" + lan.getMinetypeid() + "   order by t.id", "list");
                        for (int i = 0; i < clf.size(); i++) {
                            lf.add(clf.get(i));
                        }
                    }
                    if (lan.getIsconcetrate() == 1) {
                        if (an.getMinid() != null) {
                            if (an.getMinid() == 5) {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2 and t.isconcentrate=1   order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    if (!lf.contains(clf.get(i))) {
                                        lf.add(clf.get(i));
                                    }
                                }
                            } else {
                                if (an.getLictype() == 1) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.isconcentrate=1 order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                } else {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.isconcentrate=1  order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            }
                        } else {
                            clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.isconcentrate=1   order by t.id", "list");
                            for (int i = 0; i < clf.size(); i++) {
                                if (!lf.contains(clf.get(i))) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                    }
                    if (lan.getIsexplosion() == 1) {
                        if (an.getMinid() != null) {
                            if (an.getMinid() == 5) {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2   and t.isexplosion=1  order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    if (!lf.contains(clf.get(i))) {
                                        lf.add(clf.get(i));
                                    }
                                }
                            } else {
                                if (an.getLictype() == 1) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.isexplosion=1 order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                } else {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.isexplosion=1  order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            }
                        } else {
                            clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.isexplosion=1   order by t.id", "list");
                            for (int i = 0; i < clf.size(); i++) {
                                if (!lf.contains(clf.get(i))) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                    }
                    if (lan.getKomissid() == 1) {
                        if (an.getMinid() != null) {
                            if (an.getMinid() == 5) {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2  and t.iskomiss=1  order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    if (!lf.contains(clf.get(i))) {
                                        lf.add(clf.get(i));
                                    }
                                }
                            } else {
                                if (an.getLictype() == 1) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.iskomiss=1 order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                } else {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.iskomiss=1  order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            }
                        } else {
                            clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.iskomiss=1   order by t.id", "list");
                            for (int i = 0; i < clf.size(); i++) {
                                if (!lf.contains(clf.get(i))) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                    }
                    if (lan.getStatebudgetid() == 1) {
                        if (an.getMinid() != null) {
                            if (an.getMinid() == 5) {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + "  and t.isbudget=1 and t.divisionid=2 order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    if (!lf.contains(clf.get(i))) {
                                        lf.add(clf.get(i));
                                    }
                                }
                            } else {
                                if (an.getLictype() == 1) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.isbudget=1 order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                } else {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.isbudget=1  order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            }
                        } else {
                            clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.isbudget=1   order by t.id", "list");
                            for (int i = 0; i < clf.size(); i++) {
                                if (!lf.contains(clf.get(i))) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                    }
                    if (lan.getIsmatter() == 1) {
                        if (an.getMinid() != null) {
                            if (an.getMinid() == 5) {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=2 and t.ismatter=1  order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    if (!lf.contains(clf.get(i))) {
                                        lf.add(clf.get(i));
                                    }
                                }
                            } else {
                                if (an.getLictype() == 1) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.ismatter=1 order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                } else {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.ismatter=1  order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            }
                        } else {
                            clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.ismatter=1   order by t.id", "list");
                            for (int i = 0; i < clf.size(); i++) {
                                if (!lf.contains(clf.get(i))) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                    }
                    if (lan.getIsmatter() == 2) {

                        System.out.println("nenene");

                        if (an.getMinid() != null) {
                            if (an.getMinid() == 5) {
                                System.out.println("5");
							/*	clf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid=2 and t.ismatter=0 and t.iscommon=0 and t.isconcentrate=0 and t.isexplosion=0  order by t.id", "list");
								for(int i=0;i<clf.size();i++){
									lf.add(clf.get(i));
								}*/
                            } else {
                                System.out.println("6");
                                if (an.getLictype() == 1) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.ismatter=0 and t.iscommon=0 and t.isconcentrate=0 and t.isexplosion=0 order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                } else {
                                    if (an.getDivisionid() != 2) {
                                        clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.ismatter=0 and t.iscommon=0  and t.isconcentrate=0 and t.isexplosion=0 order by t.id", "list");
                                        for (int i = 0; i < clf.size(); i++) {
                                            if (!lf.contains(clf.get(i))) {
                                                lf.add(clf.get(i));
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.ismatter=0 and t.iscommon=0 and t.isconcentrate=0 and t.isexplosion=0 order by t.id", "list");
                            for (int i = 0; i < clf.size(); i++) {
                                if (!lf.contains(clf.get(i))) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                    }
                }

                if (lic != null) {
                    if (lic.getLplan()) {
                        if (an.getMinid() != null) {
                            if (an.getMinid() == 5) {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + "  and t.lplan=1 and t.divisionid=2 order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    if (!lf.contains(clf.get(i))) {
                                        lf.add(clf.get(i));
                                    }
                                }
                            } else {
                                if (an.getLictype() == 1) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.lplan=1 order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                } else {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.lplan=1  order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            }
                        } else {
                            clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.lplan=1   order by t.id", "list");
                            for (int i = 0; i < clf.size(); i++) {
                                if (!lf.contains(clf.get(i))) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                    }
                    if (lic.getLreport()) {
                        if (an.getMinid() != null) {
                            if (an.getMinid() == 5) {
                                clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + "  and t.lreport=1 and t.divisionid=2 order by t.id", "list");
                                for (int i = 0; i < clf.size(); i++) {
                                    if (!lf.contains(clf.get(i))) {
                                        lf.add(clf.get(i));
                                    }
                                }
                            } else {
                                if (an.getLictype() == 1) {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3  and t.lreport=1 order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                } else {
                                    clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=1 and t.lreport=1  order by t.id", "list");
                                    for (int i = 0; i < clf.size(); i++) {
                                        if (!lf.contains(clf.get(i))) {
                                            lf.add(clf.get(i));
                                        }
                                    }
                                }
                            }
                        } else {
                            clf = (List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype=" + an.getLictype() + " and t.reporttype=" + an.getReporttype() + " and t.divisionid=3 and t.lreport=1   order by t.id", "list");
                            for (int i = 0; i < clf.size(); i++) {
                                if (!lf.contains(clf.get(i))) {
                                    lf.add(clf.get(i));
                                }
                            }
                        }
                    }
                }






			/*	if(an.getMinid()!=null){
					if(an.getMinid()==5){
						lf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid=2   order by t.id", "list");
					}
					else{
						if(an.getLictype()==1){
							lf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid=3   order by t.id", "list");
						}
						else{
							lf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid=1   order by t.id", "list");
						}
					}
				}
				else{
					lf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid=3   order by t.id", "list");
				}*/


                //	List<LnkPlanAttachedFiles> at=(List<LnkPlanAttachedFiles>) dao.getHQLResult("from LnkPlanAttachedFiles t where t.expid="+expid+" order by t.id", "list");
                //	List<LnkComment> cm=(List<LnkComment>) dao.getHQLResult("from LnkComment t where t.planid="+expid+" order by t.id", "list");

                List<LnkPlanTab> tb = an.getLnkPlanTabs();

                //System.out.println("!!!!!!!!!!"+cm.size());;

                //List<LnkPlanTransition> ptz=(List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+expid+" order by t.id", "list");

                if (an.getLnkComments().size() > 0 || an.getLnkPlanAttachedFileses().size() > 0) {
                    JSONArray div = new JSONArray();

                    JSONArray art = new JSONArray();

                    for (int c = 0; c < tb.size(); c++) {
                        LnkPlanTab qwe = tb.get(c);
                        JSONObject jo1 = new JSONObject();
                        jo1.put("ext", qwe.getTabid());
                        art.put(jo1);
                    }
                    obj.put("tabs", art);

                    JSONArray pt = new JSONArray();

                    for (int i = 0; i < lf.size(); i++) {
                        JSONObject jo = new JSONObject();
                        jo.put("id", lf.get(i).getId());
                        jo.put("title", lf.get(i).getNote());
                        jo.put("inptype", lf.get(i).getInptype());
                        jo.put("divisionid", lf.get(i).getDivisionid());
                        jo.put("isapproval", lf.get(i).getIsapproval());
                        jo.put("isform", lf.get(i).getIsform());
                        jo.put("issaved", "0");
                        List<LnkPlanTransition> tz = an.getLnkPlanTransitions();


                        if (tz.size() > 0) {
                            for (int t = 0; t < tz.size(); t++) {
                                if (lf.get(i).getIsapproval() == 1) {

                                }
                                if ((long) lf.get(i).getId() == an.getLnkPlanTransitions().get(t).getNoteid()) {
                                    if (tz.get(t).getDecisionid() != null) {
                                        jo.put("decision", tz.get(t).getDecisionid());
                                        jo.put("mdecision", tz.get(t).getMdecisionid());

                                    } else if (tz.get(t).getMdecisionid() != null) {
                                        if (tz.get(t).getDecisionid() != null) {
                                            jo.put("decision", tz.get(t).getDecisionid());
                                        } else {
                                            jo.put("decision", 0);
                                        }
                                        jo.put("mdecision", tz.get(t).getMdecisionid());
                                    } else {
                                        jo.put("decision", 0);
                                    }
                                }
                            }
                        }

                        JSONArray arr = new JSONArray();
                        for (int c = 0; c < an.getLnkPlanAttachedFileses().size(); c++) {
                            if (an.getLnkPlanAttachedFileses().get(c).getNoteid() != null && (long) lf.get(i).getId() == an.getLnkPlanAttachedFileses().get(c).getNoteid()) {
                                JSONObject jo1 = new JSONObject();
                                jo1.put("id", an.getLnkPlanAttachedFileses().get(c).getId());
                                jo1.put("url", an.getLnkPlanAttachedFileses().get(c).getAttachfiletype());
                                jo1.put("title", an.getLnkPlanAttachedFileses().get(c).getFilename());
                                jo1.put("date", an.getLnkPlanAttachedFileses().get(c).getAtdate());
                                jo1.put("ext", an.getLnkPlanAttachedFileses().get(c).getFileext());
                                jo.put("issaved", "10");

                                arr.put(jo1);
                            }

                        }

                        JSONArray com = new JSONArray();
                        for (int w = 0; w < an.getLnkComments().size(); w++) {
                            if ((long) lf.get(i).getId() == an.getLnkComments().get(w).getNoteid()) {
                                JSONObject jo1 = new JSONObject();
                                JSONArray content = new JSONArray();
                                content.put(an.getLnkComments().get(w).getComnote());
                                jo1.put("id", an.getLnkComments().get(w).getId());
                                jo1.put("user_id", an.getLnkComments().get(w).getOfficerid());
                                jo1.put("user_name", an.getLnkComments().get(w).getOfficername());
                                jo1.put("content", content);
                                //	jo1.put("des", lf.get(i).getLnkCommentes().get(w).getLutDecisions().getDecisionNameMon());
                                jo1.put("destext", an.getLnkComments().get(w).getComnote());
                                jo1.put("desicion", an.getLnkComments().get(w).getDesicionid());
                                jo1.put("date", an.getLnkComments().get(w).getComdate());
                                com.put(jo1);
                            }
                        }

		    	/*		JSONArray com = new JSONArray();
	    				for(int w=0;w<lf.get(i).getLnkCommentes().size();w++){
		    				if((long) lf.get(i).getLnkCommentes().get(w).getPlanid()==an.getId()){
		    					JSONObject jo1 = new JSONObject();
		    					JSONArray content = new JSONArray();
		    					content.put(lf.get(i).getLnkCommentes().get(w).getComnote());
				    			jo1.put("user_id", lf.get(i).getLnkCommentes().get(w).getOfficerid());
				    			jo1.put("user_name", lf.get(i).getLnkCommentes().get(w).getOfficername());
				    			jo1.put("content", content);
				    		//	jo1.put("des", lf.get(i).getLnkCommentes().get(w).getLutDecisions().getDecisionNameMon());
				    			jo1.put("destext", lf.get(i).getLnkCommentes().get(w).getComnote());
				    			jo1.put("desicion", lf.get(i).getLnkCommentes().get(w).getDesicionid());
				    			jo1.put("date", lf.get(i).getLnkCommentes().get(w).getComdate());
				    			com.put(jo1);
		    				}
		    			}*/

                        jo.put("comment", com);

                        jo.put("images", arr);
                        for (int j = 0; j < lp.size(); j++) {
                            if (lp.get(j).getNoteid() == lf.get(i).getId()) {

                                jo.put("content", lp.get(j).getNotes());
                                jo.put("date", lp.get(j).getNotedate());
                            }
                        }

		    		/*	for(int j=0;j<an.getLnkPlanAttachedFileses();j++){
		    				if(lp.get(j).getNoteid()==lf.get(i).getId()){

		    					jo.put("content", lp.get(j).getNotes());
		    					jo.put("date", lp.get(j).getNotedate());
		    				}
		    			}*/
                        boolean ocontain = false;

                        for (int q = 0; q < div.length(); ++q) {
                            JSONObject rec = div.getJSONObject(q);
                            int id = rec.getInt("id");
                            if (jo.get("id").toString().equalsIgnoreCase(String.valueOf(id))) {
                                ocontain = true;
                            }
                        }

                        if (!ocontain) {
                            div.put(jo);
                        }


                    }

                    //	List<LnkPlanTransition> tz= an.getLnkPlanTransitions();



	  		/*		for(int t=0; t<tz.size();t++){
	  					JSONObject jo2 = new JSONObject();
	  					if(tz.get(t).getDecisionid()!=null){
    	    				jo2.put("note", tz.get(t).getLutFormNotes().getNote());
    	    				jo2.put("decision", tz.get(t).getDecisionid());
    	    				jo2.put("decisionText", tz.get(t).getLutDecisions().getDecisionNameMon());
    	    				pt.put(jo2);
    	    			}
	  					else{
	  						jo2.put("note", tz.get(t).getLutFormNotes().getNote());
    	    				jo2.put("decision", tz.get(t).getDecisionid());
    	    				jo2.put("decisionText", false);
    	    				pt.put(jo2);
	  					}
	  				}*/
                    
                    List<LnkCommentMain> mcom = an.getLnkCommentMains();
                    JSONArray coma = new JSONArray();
                    if (mcom.size() > 0) {
                        for (int j = 0; j < mcom.size(); j++) {
                            JSONObject mn = new JSONObject();
                            mn.put("content", mcom.get(j).getMcomment());
                            mn.put("userid", mcom.get(j).getUserid());
                            mn.put("username", mcom.get(j).getUsername());
                            mn.put("cdate", mcom.get(j).getCreatedDate());
                            coma.put(mn);
                        }
                    }

                    obj.put("mcom", coma);


                    obj.put("mns", pt);
                    obj.put("notes", div);
                } else {
                    JSONArray div = new JSONArray();
                    for (int i = 0; i < lf.size(); i++) {
                        JSONObject jo = new JSONObject();
                        JSONArray com = new JSONArray();
                        jo.put("id", lf.get(i).getId());
                        jo.put("title", lf.get(i).getNote());
                        jo.put("title", lf.get(i).getNote());
                        jo.put("inptype", lf.get(i).getInptype());
                        jo.put("divisionid", lf.get(i).getDivisionid());
                        jo.put("isform", lf.get(i).getIsform());
                        jo.put("content", "");
                        jo.put("comment", com);
                        jo.put("date", "");

                        jo.put("images", arr1);
                        div.put(jo);

                    }

                    obj.put("notes", div);

                }
                main.put(obj);
                return main.toString();
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return "error.500";
    }

	/*
	@RequestMapping(value="/note/imp/gov/{expid}",method=RequestMethod.GET)
	public @ResponseBody String detailNoteGov(@PathVariable Long expid, HttpServletRequest req) throws ClassNotFoundException, JSONException{
		try {
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (!(auth instanceof AnonymousAuthenticationToken)) {

				JSONArray main = new JSONArray();
				JSONObject obj= new JSONObject();
				JSONArray arr1 = new JSONArray();



				AnnualRegistration an=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id="+expid+" ", "current");
				List<LnkPlanNotes> lp=an.getLnkPlanNoteses();
				//List<LutFormNotes> lf=(List<LutFormNotes>) an.getLnkPlanNoteses().get(0).getLutFormNotes();
				List<LutFormNotes> lf=null;
				if(an.getMinid()!=null){
					if(an.getMinid()==5){
						lf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid=2   order by t.id", "list");
					}
					else{
						if(an.getLictype()==1){
							lf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid=3   order by t.id", "list");
						}
						else{
							lf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid=1   order by t.id", "list");
						}
					}
				}
				else{
					lf=(List<LutFormNotes>) dao.getHQLResult("from LutFormNotes t where t.lictype="+an.getLictype()+" and t.reporttype="+an.getReporttype()+" and t.divisionid=3   order by t.id", "list");
				}



				//List<LnkPlanAttachedFiles> at=(List<LnkPlanAttachedFiles>) dao.getHQLResult("from LnkPlanAttachedFiles t where t.expid="+expid+" order by t.id", "list");
				List<LnkPlanAttachedFiles> at=an.getLnkPlanAttachedFileses();
				List<LnkComment> cm=an.getLnkComments();

				List<LnkPlanTab> tb=an.getLnkPlanTabs();

				//List<LnkPlanTransition> ptz=(List<LnkPlanTransition>) dao.getHQLResult("from LnkPlanTransition t where t.planid="+expid+" order by t.id", "list");

				if(cm.size()>0 || at.size()>0){
					JSONArray div = new JSONArray();

					JSONArray art = new JSONArray();
	    			for(int c=0;c<tb.size();c++){
	    				LnkPlanTab qwe =tb.get(c);
	    				JSONObject jo1 = new JSONObject();
		    			jo1.put("ext", qwe.getTabid());
		    			art.put(jo1);
    				}

	    			obj.put("tabs", art);

	    			JSONArray pt = new JSONArray();

					for(int i=0;i<lf.size();i++){
					//	LnkPlanTransition tz=(LnkPlanTransition) dao.getHQLResult("from LnkPlanTransition t where t.noteid="+lf.get(i).getId()+" and t.planid="+an.getId()+" ", "current");
						//LnkPlanTransition tz=an.getLnkPlanTransitions();
						LutFormNotes rp=lf.get(i);

	    				JSONObject jo2 = new JSONObject();
	    				JSONObject jo = new JSONObject();
		    			jo.put("id", lf.get(i).getId());
		    			jo.put("title", lf.get(i).getNote());
		    			jo.put("inptype", lf.get(i).getInptype());
		    			jo.put("divisionid", lf.get(i).getDivisionid());

		    			JSONArray arr = new JSONArray();


		    			if(lf.get(i).getLnkPlanAttachedFileses().size()>0){
		    				for(int c=0;c<lf.get(i).getLnkPlanAttachedFileses().size();c++){
		    					//LnkPlanAttachedFiles lpa=lf.get(i).getLnkPlanAttachedFileses().get(c);
		    					if(an.getId()==lf.get(i).getLnkPlanAttachedFileses().get(c).getExpid()){
		    						JSONObject jo1 = new JSONObject();
	    			    			jo1.put("id", lf.get(i).getLnkPlanAttachedFileses().get(c).getId());
	    			    			jo1.put("url",lf.get(i).getLnkPlanAttachedFileses().get(c).getAttachfiletype());
	    			    			jo1.put("title", lf.get(i).getLnkPlanAttachedFileses().get(c).getFilename());
	    			    			jo1.put("date", lf.get(i).getLnkPlanAttachedFileses().get(c).getAtdate());
	    			    			jo1.put("ext", lf.get(i).getLnkPlanAttachedFileses().get(c).getFileext());
	    			    			arr.put(jo1);
		    					}
			    			}
		    			}

		    			JSONArray com = new JSONArray();
		    			if(lf.get(i).getLnkCommentes().size()>0){
		    				for(int w=0;w<lf.get(i).getLnkCommentes().size();w++){
		    					//LnkComment cmm=lf.get(i).getLnkCommentes().get(w);
			    				if((long) lf.get(i).getLnkCommentes().get(w).getPlanid()==an.getId()){
			    					JSONObject jo1 = new JSONObject();
			    					JSONArray content = new JSONArray();
			    					content.put(lf.get(i).getLnkCommentes().get(w).getComnote());
					    			jo1.put("user_id", lf.get(i).getLnkCommentes().get(w).getOfficerid());
					    			jo1.put("user_name", lf.get(i).getLnkCommentes().get(w).getOfficername());
					    			jo1.put("content", content);
					    			jo1.put("des", lf.get(i).getLnkCommentes().get(w).getLutDecisions().getDecisionNameMon());
					    			jo1.put("destext", lf.get(i).getLnkCommentes().get(w).getComnote());
					    			jo1.put("desicion", lf.get(i).getLnkCommentes().get(w).getDesicionid());
					    			jo1.put("date", lf.get(i).getLnkCommentes().get(w).getComdate());
					    			com.put(jo1);
			    				}
			    			}
		    			}

		    			List<LnkComment> cmm=lf.get(i).getLnkCommentes();
		    			JSONArray com = new JSONArray();
		    			if(cmm.size()>0){
		    				for(int w=0;w<cmm.size();w++){
		    					LnkComment lnkcom =cmm.get(w);
		    					if((long) lnkcom.getPlanid()==an.getId()){
		    						JSONObject jo1 = new JSONObject();
			    					JSONArray content = new JSONArray();
			    					content.put(lnkcom.getComnote());
					    			jo1.put("user_id", lnkcom.getOfficerid());
					    			jo1.put("user_name", lnkcom.getOfficername());
					    			jo1.put("content", content);
					    			jo1.put("des", lnkcom.getLutDecisions().getDecisionNameMon());
					    			jo1.put("destext", lnkcom.getComnote());
					    			jo1.put("desicion", lnkcom.getDesicionid());
					    			jo1.put("date", lnkcom.getComdate());
					    			com.put(jo1);
		    					}
			    			}
		    			}

		    			jo.put("comment", com);

		    			jo.put("images", arr);
		    			List<LnkPlanNotes> lpc=an.getLnkPlanNoteses();
		    			if(lpc.size()>0){
		    				for(int j=0;j<lpc.size();j++){
		    					if(lf.get(i).getId()==lpc.get(j).getNoteid()){
		    						jo.put("content", lpc.get(j).getNotes());
			    					jo.put("date", lpc.get(j).getNotedate());
		    					}

			    			}
		    			}


	    			div.put(jo);

			    	}
					obj.put("mns", pt);
					obj.put("notes", div);
				}

				else{
					JSONArray div = new JSONArray();
					for(int i=0;i<lf.size();i++){
		    			JSONObject jo = new JSONObject();
		    			JSONArray com = new JSONArray();
		    			jo.put("id", lf.get(i).getId());
		    			jo.put("title", lf.get(i).getNote());
		    			jo.put("title", lf.get(i).getNote());
		    			jo.put("inptype", lf.get(i).getInptype());
		    			jo.put("divisionid", lf.get(i).getDivisionid());
		    			jo.put("content", "");
		    			jo.put("comment", com);
		    			jo.put("date", "");

		    			jo.put("images", arr1);
		    			div.put(jo);

			    	}

					obj.put("notes", div);

				}
				main.put(obj);
				return main.toString();
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
		return "error.500";
	}*/


    @RequestMapping(value = "/gen/{type}/{domain}/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String general(@PathVariable String type, @PathVariable int id, @PathVariable String domain, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            if (type.equalsIgnoreCase("read")) {
                Class<?> classtoConvert;
                String domainName = domain;
                classtoConvert = Class.forName(domain);
                System.out.println("lalallala");
                ;
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(dao.getHQLResult("from " + domain + " t where t.id=" + id + "", "current"));
            }

        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @RequestMapping(value = "/cascading/kendo/{domain}", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String cascadingTreeNew(@PathVariable String domain, @RequestBody String request, HttpServletRequest req) {
        try {
            JSONObject obj = new JSONObject(request);
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Gson gson = new Gson();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (domain.equalsIgnoreCase("LutRole")) {
                    List<LutRole> rs = (List<LutRole>) dao.getHQLResult("from LutRole t order by t.id", "list");
                    for (int i = 0; i < rs.size(); i++) {
                        Map<String, Object> wmap = new HashMap<String, Object>();
                        wmap.put("value", rs.get(i).getId());
                        wmap.put("text", rs.get(i).getRoleNameMon());

                        result.add(wmap);
                    }
                } else if (domain.equalsIgnoreCase("LutMineType")) {
                    final List<LutMineType> rs = (List<LutMineType>) dao.getHQLResult("from LutMineType t", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getId());
                            wmap.put("name", rs.get(i).getMtypeNameMon());
                            result.add(wmap);

                        }
                    }
                } else if (domain.equalsIgnoreCase("LutMinerals")) {
                    final List<LutMinerals> rs = (List<LutMinerals>) dao.getHQLResult("from LutMinerals t", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getId());
                            wmap.put("name", rs.get(i).getMineralnamemon());
                            wmap.put("parentid", rs.get(i).getMineralgroupid());
                            result.add(wmap);

                        }
                    }
                } else if (domain.equalsIgnoreCase("LutMineType")) {
                    final List<LutMineType> rs = (List<LutMineType>) dao.getHQLResult("from LutMineType t", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getId());
                            wmap.put("name", rs.get(i).getMtypeNameMon());
                            result.add(wmap);

                        }
                    }
                } else if (domain.equalsIgnoreCase("LutDeposit")) {
                    if (obj.has("filter")) {
                        JSONObject chobj = obj.getJSONObject("filter");
                        JSONArray arr = chobj.getJSONArray("filters");
                        JSONObject mobj = new JSONObject(arr.get(0).toString());
                        //List<LutAdminunit> rs=(List<LutAdminunit>) dao.getHQLResult("from LutAdminunit t where t.parentid="+mobj.getInt("value")+"", "list");
                        List<LutDeposit> rs = (List<LutDeposit>) dao.getHQLResult("from LutDeposit t where  t.mineralsid=" + mobj.getInt("value") + "", "list");
                        System.out.println("aaaaaa" + rs.size());

                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getDepositid());
                            wmap.put("name", rs.get(i).getDepositnamemon());
                            wmap.put("parent", rs.get(i).getMineraltype());
                            result.add(wmap);

                        }
                    } else {
                        final List<LutDeposit> rs = (List<LutDeposit>) dao.getHQLResult("from LutDeposit t", "list");
                        if (rs.size() > 0) {
                            for (int i = 0; i < rs.size(); i++) {
                                Map<String, Object> wmap = new HashMap<String, Object>();
                                wmap.put("id", rs.get(i).getDepositid());
                                wmap.put("name", rs.get(i).getDepositnamemon());
                                result.add(wmap);
                            }
                        }
                    }

                } else if (domain.equalsIgnoreCase("LutConcentration")) {
                    final List<LutConcentration> rs = (List<LutConcentration>) dao.getHQLResult("from LutConcentration t", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getId());
                            wmap.put("name", rs.get(i).getNamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutDeposit")) {
                    final List<LutMinerals> rs = (List<LutMinerals>) dao.getHQLResult("from LutMinerals t", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getId());
                            wmap.put("name", rs.get(i).getMineralnamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutAdminunit")) {
                    if (obj.has("filter")) {
                        JSONObject chobj = obj.getJSONObject("filter");
                        JSONArray arr = chobj.getJSONArray("filters");
                        JSONObject mobj = new JSONObject(arr.get(0).toString());
                        List<LutAdminunit> rs = (List<LutAdminunit>) dao.getHQLResult("from LutAdminunit t where t.parentid=" + mobj.getInt("value") + "", "list");
                        System.out.println("aaaaaa" + rs.size());

                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getSid());
                            wmap.put("name", rs.get(i).getNameL1());
                            wmap.put("parent", rs.get(i).getSid());
                            result.add(wmap);

                        }
                    }
                } else if (domain.equalsIgnoreCase("LutAdminunitParent")) {

                    if (obj.has("filter")) {
                        JSONObject chobj = obj.getJSONObject("filter");
                        JSONArray arr = chobj.getJSONArray("filters");
                        JSONObject mobj = new JSONObject(arr.get(0).toString());
                        List<LutAdminunit> rs = (List<LutAdminunit>) dao.getHQLResult("from LutAdminunit t where lower(t.nameL1) LIKE lower('" + mobj.getString("value") + "%') and t.parentid=1 and t.sid!=1", "list");
                        System.out.println("aaaaaa" + rs.size());

                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getSid());
                            wmap.put("name", rs.get(i).getNameL1());
                            wmap.put("parent", rs.get(i).getSid());
                            result.add(wmap);

                        }
                    } else {
                        final List<LutAdminunit> rs = (List<LutAdminunit>) dao.getHQLResult("from LutAdminunit t where t.parentid=1 and t.sid!=1", "list");
                        if (rs.size() > 0) {
                            for (int i = 0; i < rs.size(); i++) {
                                Map<String, Object> wmap = new HashMap<String, Object>();
                                wmap.put("id", rs.get(i).getSid());
                                wmap.put("name", rs.get(i).getNameL1());
                                wmap.put("parent", rs.get(i).getSid());
                                result.add(wmap);

                            }
                        }
                    }

                }

            }
            String orgstr = gson.toJson(result);

            String rjson = orgstr.replaceAll("=", ":");
            return rjson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value = "/cascading/{domain}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String cascadingTree(@PathVariable String domain, HttpServletRequest req) {
        try {

            // JSONObject obj= new JSONObject(jsonString);
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Gson gson = new Gson();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (domain.equalsIgnoreCase("LutRole")) {
                    List<LutRole> rs = (List<LutRole>) dao.getHQLResult("from LutRole t order by t.id", "list");
                    for (int i = 0; i < rs.size(); i++) {
                        Map<String, Object> wmap = new HashMap<String, Object>();
                        wmap.put("value", rs.get(i).getId());
                        wmap.put("text", rs.get(i).getRoleNameMon());

                        result.add(wmap);
                    }
                } else if (domain.equalsIgnoreCase("LutMramUser")) {
                    final List<LutUsers> rs = (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.ispublic=1", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getId());
                            wmap.put("name", rs.get(i).getGivnamemon());
                            result.add(wmap);

                        }
                    }
                } else if (domain.equalsIgnoreCase("Messages")) {
                    final List<DataTezuMail> rs = (List<DataTezuMail>) dao.getHQLResult("from DataTezuMail t", "list");
                    if (rs.size() > 0) {
                        Gson gs = new Gson();
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getId());
                            wmap.put("sender_avatar", rs.get(i).getSenderAvatar());
                            wmap.put("sender", rs.get(i).getSender());
                            wmap.put("email", rs.get(i).getEmail());
                            wmap.put("title", rs.get(i).getTitle());
                            wmap.put("date", rs.get(i).getSenddate());
                            wmap.put("recipent", rs.get(i).getRecipientName());
                            wmap.put("content", rs.get(i).getContent());
                            wmap.put("sender_color", rs.get(i).getSenderColor());
                            wmap.put("verified", rs.get(i).getVerified());

                            JSONArray arr = new JSONArray();
                            List<LnkTezuData> tdata = rs.get(i).getLnkTezuDatas();
                            for (int j = 0; j < tdata.size(); j++) {
                                JSONObject aro = new JSONObject();
                                LnkTezuData ld = tdata.get(j);
                                aro.put("fileName", ld.getFilename());
                                aro.put("fileSize", ld.getFilesize());
                                aro.put("fileType", ld.getFiletype());
                                arr.put(aro);
                            }
                            wmap.put("atteachments", arr);
                            result.add(wmap);

                        }
                    }
                } else if (domain.equalsIgnoreCase("LutMineType")) {
                    final List<LutMineType> rs = (List<LutMineType>) dao.getHQLResult("from LutMineType t", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getId());
                            wmap.put("name", rs.get(i).getMtypeNameMon());
                            result.add(wmap);

                        }
                    }
                } else if (domain.equalsIgnoreCase("LutMinGroup")) {
                    final List<LutMinGroup> rs = (List<LutMinGroup>) dao.getHQLResult("from LutMinGroup t", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getId());
                            wmap.put("name", rs.get(i).getGroupname());
                            result.add(wmap);

                        }
                    }
                } else if (domain.equalsIgnoreCase("LutMinerals")) {
                    String str = req.getParameter("filter[filters][0][value]");
                    final List<LutMinerals> rs = (List<LutMinerals>) dao.getHQLResult("from LutMinerals t where t.mineralgroupid=" + str + "", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("mineralgroupid", rs.get(i).getId());
                            wmap.put("name", rs.get(i).getMineralnamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutDeposit")) {
                    String str = req.getParameter("filter[filters][0][value]");
                    final List<LutDeposit> rs = (List<LutDeposit>) dao.getHQLResult("from LutDeposit t where t.mineralsid=" + str + "", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getDepositid());
                            wmap.put("name", rs.get(i).getDepositnamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutProducts")) {
                    String str = req.getParameter("filter[filters][0][value]");
                    final List<LutProducts> rs = (List<LutProducts>) dao.getHQLResult("from LutProducts t where t.depositid=" + str + "", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getDepositid());
                            wmap.put("name", rs.get(i).getProductnamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutSubProduct")) {
                    String str = req.getParameter("filter[filters][0][value]");
                    final List<LutSubProduct> rs = (List<LutSubProduct>) dao.getHQLResult("from LutSubProduct t where t.parentproductid=" + str + "", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getSubDepositid());
                            wmap.put("name", rs.get(i).getSubDepositnamemon());
                            result.add(wmap);
                        }
                    }
                }
            }
            String orgstr = gson.toJson(result);

            String rjson = orgstr.replaceAll("=", ":");
            return rjson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value = "/custom/data/{domain}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String treeCat(@PathVariable String domain) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            try {
                if (domain.equalsIgnoreCase("HelpCategory")) {
                    final List<HelpCategory> rs = (List<HelpCategory>) dao.getHQLResult("from HelpCategory t", "list");
                    if (rs.size() > 0) {
                        JSONArray arr = new JSONArray();
                        for (int i = 0; i < rs.size(); i++) {
                            JSONObject obj = new JSONObject();
                            obj.put("id", rs.get(i).getId());
                            obj.put("catname", rs.get(i).getCatname());
                            obj.put("cattype", rs.get(i).getCattype());
                            JSONArray arr1 = new JSONArray();
                            List<HelpData> childs = rs.get(i).getHelpDatas();
                            for (int j = 0; j < childs.size(); j++) {
                                HelpData hd = childs.get(j);
                                JSONObject chobj = new JSONObject();
                                chobj.put("helptitle", hd.getHelptitle());
                                chobj.put("helptext", hd.getHelptext());
                                arr1.put(chobj);
                            }
                            obj.put("childs", arr1);
                            arr.put(obj);
                        }
                        return arr.toString();
                    }
                } else if (domain.equalsIgnoreCase("stats")) {

                    LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");

                    Long userdivision = loguser.getDivisionid();

                    int count = 0;
                    int licplan = 0;
                    int licreport = 0;
                    int licweekly = 0;
                    int licrplan = 0;
                    int licrreport = 0;
                    int repstatus2 = 0;
                    int repstatus7 = 0;
                    domain = "AnnualRegistration";
                    JSONObject jsonObj = new JSONObject();
                    if (loguser.getDivisionid() == 3) {
                        jsonObj.put("custom", "t where t.repstatusid=1 and t.lictype=1");
                    } else if (loguser.getDivisionid() == 2) {
                        jsonObj.put("custom", "t where t.repstatusid=1 and t.lictype=2 and t.minid=5");
                    } else if (loguser.getDivisionid() == 1) {
                        jsonObj.put("custom", "t where t.repstatusid=1 and t.lictype=2 and t.minid!=5");
                    }
                    count = dao.resulsetcount(jsonObj.toString(), domain);


                    String sublic = "SubLicenses";
                    JSONObject plan = new JSONObject();
                    if (userdivision == 3) {
                        plan.put("custom", "t where t.divisionId=" + userdivision + "");
                    } else if (userdivision == 2) {
                        plan.put("custom", "t where t.divisionId=" + userdivision + "");
                    } else if (userdivision == 1) {
                        plan.put("custom", "t where t.divisionId=" + userdivision + "");
                    }

                    licplan = dao.resulsetcount(plan.toString(), sublic);

                    JSONObject report = new JSONObject();
                    if (userdivision == 3) {
                        report.put("custom", "t where t.report=1 and t.licTypeId=1");
                    } else if (userdivision == 2) {
                        report.put("custom", "t where t.report=1 and t.licTypeId=2 and t.mintype=5");
                    } else if (userdivision == 1) {
                        report.put("custom", "t where t.report=1 and t.licTypeId=2 and t.mintype!=5");
                    }
                    licreport = licplan;

                    JSONObject weekly = new JSONObject();
                    if (userdivision == 3) {
                        weekly.put("custom", "t where t.weekly=1 and t.licTypeId=1");
                    } else if (userdivision == 2) {
                        weekly.put("custom", "t where t.weekly=1 and t.licTypeId=2 and t.mintype=5");
                    } else if (userdivision == 1) {
                        weekly.put("custom", "t where t.weekly=1 and t.licTypeId=2 and t.mintype!=5");
                    }
                    licweekly = dao.resulsetcount(weekly.toString(), sublic);

                    JSONObject redemptionplan = new JSONObject();
                    if (userdivision == 3) {
                        redemptionplan.put("custom", "t where t.redemptionplan=1 and t.licTypeId=1");
                    } else if (userdivision == 2) {
                        redemptionplan.put("custom", "t where t.redemptionplan=1 and t.licTypeId=2 and t.mintype=5");
                    } else if (userdivision == 1) {
                        redemptionplan.put("custom", "t where t.redemptionplan=1 and t.licTypeId=2 and t.mintype!=5");
                    }

                    licrplan = dao.resulsetcount(redemptionplan.toString(), sublic);

                    JSONObject redemptionreport = new JSONObject();
                    if (userdivision == 3) {
                        redemptionreport.put("custom", "t where t.redemptionreport=1 and t.licTypeId=1");
                    } else if (userdivision == 2) {
                        redemptionreport.put("custom", "t where t.redemptionreport=1 and t.licTypeId=2 and t.mintype=5");
                    } else if (userdivision == 1) {
                        redemptionreport.put("custom", "t where t.redemptionreport=1 and t.licTypeId=2 and t.mintype!=5");
                    }
                    licrreport = dao.resulsetcount(redemptionreport.toString(), sublic);


                    int anplan = 0;
                    int anreport = 0;
                    int anpstep1 = 0;
                    int anpstep2 = 0;
                    int anpstep3 = 0;
                    int anpstep4 = 0;
                    int anpstep5 = 0;
                    int anpstep6 = 0;
                    int anpstep7 = 0;

                    int anpreject = 0;
                    int anpback = 0;

                    int anrstep1 = 0;
                    int anrstep2 = 0;
                    int anrstep3 = 0;
                    int anrstep4 = 0;
                    int anrstep5 = 0;
                    int anrstep6 = 0;
                    int anrstep7 = 0;

                    int anrreject = 0;
                    int anrback = 0;


                    JSONObject janplan = new JSONObject();
                    janplan.put("custom", "where reporttype=3 and repstatusid in (1,2,7) and xtype!=0 and lictype!=1 and divisionid=" + userdivision + "");
                    anplan = dao.resulsetcount(janplan.toString(), domain);


                    JSONObject janreport = new JSONObject();
                    janreport.put("custom", "where reporttype=4 and repstatusid=7 and divisionid=" + userdivision + "");
                    anreport = dao.resulsetcount(janreport.toString(), domain);

                    JSONObject janpstep1 = new JSONObject();
                    if (userdivision == 3) {
                        janpstep1.put("custom", "where reporttype=3 and repstatusid=7 and repstepid=1 and divisionid=" + userdivision + "  and lictype=1 and xtype!=0");
                    } else if (userdivision == 2) {
                        janpstep1.put("custom", "where reporttype=3 and repstatusid=7 and repstepid=1 and divisionid=" + userdivision + "  and lictype=2 and minid = 5 and xtype!=0");
                    } else if (userdivision == 1) {
                        janpstep1.put("custom", "where reporttype=3 and repstatusid=7 and repstepid=1 and divisionid=" + userdivision + "  and lictype=2 and minid != 5 and xtype!=0");
                    }
                    //janpstep1.put("custom", "where reporttype=3 and repstatusid=7 and repstepid=1 and divisionid="+userdivision+"");
                    anpstep1 = dao.resulsetcount(janpstep1.toString(), domain);

                    JSONObject janpstep2 = new JSONObject();
                    janpstep2.put("custom", "where reporttype=3 and repstatusid=7and repstepid=2 and divisionid=" + userdivision + "");
                    anpstep2 = dao.resulsetcount(janpstep2.toString(), domain);

                    JSONObject janpstep3 = new JSONObject();
                    janpstep3.put("custom", "where reporttype=3 and repstatusid=7 and repstepid=3 and divisionid=" + userdivision + "");
                    anpstep3 = dao.resulsetcount(janpstep3.toString(), domain);

                    JSONObject janpstep4 = new JSONObject();
                    janpstep4.put("custom", "where reporttype=3 and repstatusid=7 and repstepid=4 and divisionid=" + userdivision + "");
                    anpstep4 = dao.resulsetcount(janpstep4.toString(), domain);

                    JSONObject janpstep5 = new JSONObject();
                    janpstep5.put("custom", "where reporttype=3 and repstatusid=7 and repstepid=5 and divisionid=" + userdivision + "");
                    anpstep5 = dao.resulsetcount(janpstep5.toString(), domain);

                    JSONObject janpstep6 = new JSONObject();
                    janpstep6.put("custom", "where reporttype=3 and repstatusid=7 and repstepid=6 and divisionid=" + userdivision + "");
                    anpstep6 = dao.resulsetcount(janpstep6.toString(), domain);


                    JSONObject janpstep7 = new JSONObject();
                    janpstep7.put("custom", "where reporttype=3 and repstatusid=1 and divisionid=" + userdivision + "");
                    anpstep7 = dao.resulsetcount(janpstep7.toString(), domain);

                    JSONObject janpreject = new JSONObject();
                    janpreject.put("custom", "where reporttype=3 and repstatusid=7 and repstepid=7 and divisionid=" + userdivision + "");
                    anpreject = dao.resulsetcount(janpreject.toString(), domain);

                    JSONObject janpback = new JSONObject();
                    janpback.put("custom", "where reporttype=3 and repstatusid=7 and repstepid=7 and divisionid=" + userdivision + "");
                    anpback = dao.resulsetcount(janpback.toString(), domain);


                    JSONObject janrstep1 = new JSONObject();
                    //janrstep1.put("custom", "where reporttype=4 and repstatusid=7 and repstepid=1 and divisionid="+userdivision+"");
                    if (userdivision == 3) {
                        janrstep1.put("custom", "where reporttype=4 and repstatusid=7 and repstepid=1 and divisionid=" + userdivision + "  and lictype=1 and xtype!=0");
                    } else if (userdivision == 2) {
                        janrstep1.put("custom", "where reporttype=4 and repstatusid=7 and repstepid=1 and divisionid=" + userdivision + "  and lictype=2 and minid = 5 and xtype!=0");
                    } else if (userdivision == 1) {
                        janrstep1.put("custom", "where reporttype=4 and repstatusid=7 and repstepid=1 and divisionid=" + userdivision + "  and lictype=2 and minid != 5 and xtype!=0");
                    }
                    anrstep1 = dao.resulsetcount(janrstep1.toString(), domain);


                    JSONObject janrstep2 = new JSONObject();
                    janrstep2.put("custom", "where reporttype=4 and repstatusid=7 and repstepid=2 and divisionid=" + userdivision + "");
                    anrstep2 = dao.resulsetcount(janrstep2.toString(), domain);


                    JSONObject janrstep3 = new JSONObject();
                    janrstep3.put("custom", "where reporttype=4 and repstatusid=7 and repstepid=3 and divisionid=" + userdivision + "");
                    anrstep3 = dao.resulsetcount(janrstep3.toString(), domain);


                    JSONObject janrstep4 = new JSONObject();
                    janrstep4.put("custom", "where reporttype=4 and repstatusid=7 and repstepid=4 and divisionid=" + userdivision + "");
                    anrstep4 = dao.resulsetcount(janrstep4.toString(), domain);


                    JSONObject janrstep5 = new JSONObject();
                    janrstep5.put("custom", "where reporttype=4 and repstatusid=7 and repstepid=5 and divisionid=" + userdivision + "");
                    anrstep5 = dao.resulsetcount(janrstep5.toString(), domain);


                    JSONObject janrstep6 = new JSONObject();
                    janrstep6.put("custom", "where reporttype=4 and repstatusid=7 and repstepid=6 and divisionid=" + userdivision + "");
                    anrstep6 = dao.resulsetcount(janrstep6.toString(), domain);


                    JSONObject janrstep7 = new JSONObject();
                    janrstep7.put("custom", "where reporttype=4 and repstepid=7 and divisionid=" + userdivision + "");
                    anrstep7 = dao.resulsetcount(janrstep7.toString(), domain);

                    JSONObject janrreject = new JSONObject();
                    janrreject.put("custom", "where reporttype=4 and repstatusid=7 and repstepid=7 and divisionid=" + userdivision + "");
                    anrreject = dao.resulsetcount(janrreject.toString(), domain);

                    JSONObject janrback = new JSONObject();
                    janrback.put("custom", "where reporttype=4 and repstatusid=7 and repstepid=7 and divisionid=" + userdivision + "");
                    anrback = dao.resulsetcount(janrback.toString(), domain);
                    //List<LutUsers> rs= (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.isactive=1 and t.lpreg!='9999999'", "list");

                    int tshalgajbga = 0;
                    JSONObject shalgajbga = new JSONObject();
                    shalgajbga.put("custom", "where reporttype=3 and repstatusid=7 and xtype!=0 and divisionid=" + userdivision + "");
                    tshalgajbga = dao.resulsetcount(shalgajbga.toString(), domain);

                    int tashalgajbga = 0;
                    JSONObject tailanshalgajbga = new JSONObject();
                    tailanshalgajbga.put("custom", "where reporttype=4 and repstatusid=7 and xtype!=0 and divisionid=" + userdivision + "");
                    tashalgajbga = dao.resulsetcount(tailanshalgajbga.toString(), domain);

                    int huleenavsant = 0;
                    JSONObject huleenavssantu = new JSONObject();
                    huleenavssantu.put("custom", "where reporttype=3 and repstatusid=1 and xtype!=0 and divisionid=" + userdivision + "");
                    huleenavsant = dao.resulsetcount(huleenavssantu.toString(), domain);

                    int huleenavsantailan = 0;
                    JSONObject huleenavssanta = new JSONObject();
                    huleenavssanta.put("custom", "where reporttype=4 and repstatusid=1 and xtype!=0 and divisionid=" + userdivision + "");
                    huleenavsantailan = dao.resulsetcount(huleenavssanta.toString(), domain);

                    JSONObject jsonObjrepstatus2 = new JSONObject();
                    if (loguser.getDivisionid() == 3) {
                        jsonObjrepstatus2.put("custom", "t where t.repstatusid=2 and reporttype=3 and t.divisionid=" + userdivision + "");
                    } else if (loguser.getDivisionid() == 2) {
                        jsonObjrepstatus2.put("custom", "t where t.repstatusid=2 and reporttype=3 and t.divisionid=" + userdivision + " and t.minid=5");
                    } else if (loguser.getDivisionid() == 1) {
                        jsonObjrepstatus2.put("custom", "t where t.repstatusid=2 and reporttype=3 and t.divisionid=" + userdivision + "  and t.minid!=5");
                    }
                    repstatus2 = dao.resulsetcount(jsonObjrepstatus2.toString(), domain);


                    int repstatus2t = 0;
                    JSONObject jsonObjrepstatus2t = new JSONObject();
                    if (loguser.getDivisionid() == 3) {
                        jsonObjrepstatus2t.put("custom", "t where t.repstatusid=2 and reporttype=4 and t.divisionid=" + userdivision + "");
                    } else if (loguser.getDivisionid() == 2) {
                        jsonObjrepstatus2t.put("custom", "t where t.repstatusid=2 and reporttype=4 and t.divisionid=" + userdivision + " and t.minid=5");
                    } else if (loguser.getDivisionid() == 1) {
                        jsonObjrepstatus2t.put("custom", "t where t.repstatusid=2 and reporttype=4 and t.divisionid=" + userdivision + "  and t.minid!=5");
                    }
                    repstatus2t = dao.resulsetcount(jsonObjrepstatus2t.toString(), domain);


                    String SubLegalpersons = "SubLegalpersons";
                    int activeU = 0;
                    JSONObject actUser = new JSONObject();
                    actUser.put("custom", "t, LutUsers l where t.lpReg=l.username and t.confirmed=1");
                    activeU = dao.resulsetcount(actUser.toString(), SubLegalpersons);

                    Integer configPlan = 0;
                    JSONObject cplan = new JSONObject();
                    if (userdivision == 3) {
                        cplan.put("custom", "t where t.divisionId=" + userdivision + " and plan=1");
                    } else if (userdivision == 2) {
                        cplan.put("custom", "t where t.divisionId=" + userdivision + "  and plan=1");
                    } else if (userdivision == 1) {
                        cplan.put("custom", "t where t.divisionId=" + userdivision + "  and plan=1");
                    }

                    configPlan = dao.resulsetcount(cplan.toString(), sublic);

                    JSONObject returnObj = new JSONObject();

                    int xtul = 0;
                    JSONObject hultul = new JSONObject();
                    if (loguser.getDivisionid() == 3) {
                        hultul.put("custom", "where repstatusid =7  and divisionid=3 and  lictype=1 and xtype=0 and reporttype=3 ");
                    } else if (loguser.getDivisionid() == 2) {
                        hultul.put("custom", "where repstatusid =7  and divisionid=2 and  lictype=2 and xtype=0 and reporttype=3 and minid=5");
                    } else if (loguser.getDivisionid() == 1) {
                        hultul.put("custom", "where  repstatusid =7 and divisionid=1  and lictype=2 and xtype=0 and reporttype=3 and minid!=5");
                    }
                    xtul = dao.resulsetcount(hultul.toString(), domain);

                    int xtal = 0;
                    JSONObject hultal = new JSONObject();
                    hultal.put("custom", "where reporttype=4 and divisionid=" + loguser.getDivisionid() + " and xtype=0 and repstatusid=7");
                    xtal = dao.resulsetcount(hultal.toString(), domain);


					/*JSONObject hultul = new JSONObject();
					hultul.put("custom", "where reporttype=3 and repstatusid=7 and xtype=0 and divisionid="+userdivision+"");
					xtul=dao.resulsetcount(hultul.toString(), domain);

					int xtal=0;
					JSONObject hultal = new JSONObject();
					hultal.put("custom", "where reporttype=4 and repstatusid=7 and xtype=0 and divisionid="+userdivision+"");
					xtal=dao.resulsetcount(hultal.toString(), domain);
					*/


                    returnObj.put("activeUser", activeU);
                    returnObj.put("configPlan", configPlan);

                    returnObj.put("butssantuluwluguu", repstatus2);
                    returnObj.put("butssantailan", repstatus2t);
                    returnObj.put("tushalgajbga", tshalgajbga);
                    returnObj.put("tashalgajbga", tashalgajbga);
                    returnObj.put("huleenavsantuluwluguu", huleenavsant);
                    returnObj.put("huleenavsantailan", huleenavsantailan);

                    returnObj.put("plan", licplan);
                    returnObj.put("report", licreport);
                    returnObj.put("redemptionplan", licrplan);
                    returnObj.put("redemptionreport", licrreport);
                    returnObj.put("xtul", xtul);
                    returnObj.put("xtal", xtal);
                    returnObj.put("weekly", licweekly);


                    returnObj.put("anplan", anplan);
                    returnObj.put("anreport", anreport);

                    returnObj.put("anpstep1", anpstep1);
                    returnObj.put("anpstep2", anpstep2);
                    returnObj.put("anpstep3", anpstep3);
                    returnObj.put("anpstep4", anpstep4);
                    returnObj.put("anpstep5", anpstep5);
                    returnObj.put("anpstep6", anpstep6);
                    returnObj.put("anpstep7", anpstep7);
                    returnObj.put("anpback", anpback);
                    returnObj.put("anpreject", anpreject);

                    returnObj.put("anrstep1", anrstep1);
                    returnObj.put("anrstep2", anrstep2);
                    returnObj.put("anrstep3", anrstep3);
                    returnObj.put("anrstep4", anrstep4);
                    returnObj.put("anrstep5", anrstep5);
                    returnObj.put("anrstep6", anrstep6);
                    returnObj.put("anrstep7", anrstep7);
                    returnObj.put("anrback", anrback);
                    returnObj.put("anrreject", anrreject);

                    System.out.println(count);

                    return returnObj.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return domain;

    }

    @RequestMapping(value = "/resourse/data/{domain}/{id}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String treeView(@PathVariable String domain, @PathVariable Integer id) {
        try {

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Gson gson = new Gson();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!(auth instanceof AnonymousAuthenticationToken)) {

                if (domain.equalsIgnoreCase("deposididData")) {
                    List<LutDeposit> rs = (List<LutDeposit>) dao.getHQLResult("from LutDeposit t where t.mineralsid='" + id + "'", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getDepositid());
                            wmap.put("text", rs.get(i).getDepositnamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("ownlicenses")) {
                    List<LutUsers> loguser = (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "list");
                    if (loguser.size() > 0) {
                        List<SubLicenses> rs = (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where mintype!=6 and ((t.lpReg='" + loguser.get(0).getLpreg() + "' and t.licTypeId=2 and licenseNum!=" + id + " and t.plan=1) or (t.lpReg='" + loguser.get(0).getLpreg() + "' and t.licTypeId=2 and licenseNum!=" + id + " and t.report=1) or (t.lpReg='" + loguser.get(0).getLpreg() + "' and t.licTypeId=3 and t.plan=1) or (t.lpReg='" + loguser.get(0).getLpreg() + "' and t.licTypeId=3 and t.report=1)) order by t.id", "list");
                        //List<SubLicenses> rs=(List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.lpReg='"+loguser.get(0).getLpreg()+"' and t.licTypeId=2 and t.configured=0 and licenseNum!="+id+" order by t.id", "list");
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getLicenseNum());
                            wmap.put("text", rs.get(i).getLicenseNum());

                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("officers")) {
                    //final List<AnnualRegistration> rs=(List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.repstepid="+id+"", "list");
                    List<LutUsers> rs = (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.stepid='" + id + "'", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getGivnamemon());
                            result.add(wmap);

                        }
                    }
                } else if (domain.equalsIgnoreCase("officersgeo")) {
                    List<LutUsers> rs = (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.groupid='" + id + "'", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getGivnamemon());
                            result.add(wmap);


                        }
                    }
                }
            }
            String orgstr = gson.toJson(result);

            String rjson = orgstr.replaceAll("=", ":");
            return rjson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/resourse/{domain}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String tree(@PathVariable String domain) {
        try {

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Gson gson = new Gson();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                if (domain.equalsIgnoreCase("LutConcentration")) {
                    final List<LutConcentration> rs = (List<LutConcentration>) dao.getHQLResult("from LutConcentration t order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getNamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutMineType")) {
                    final List<LutMineType> rs = (List<LutMineType>) dao.getHQLResult("from LutMineType t order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getMtypeNameMon());
                            result.add(wmap);

                        }
                    }
                } else if (domain.equalsIgnoreCase("LutMinerals")) {
                    List<LutMinerals> rs = (List<LutMinerals>) dao.getHQLResult("from LutMinerals t order by t.id", "list");
                    for (int i = 0; i < rs.size(); i++) {
                        Map<String, Object> wmap = new HashMap<String, Object>();
                        wmap.put("value", rs.get(i).getId());
                        wmap.put("text", rs.get(i).getMineralnamemon());
                        wmap.put("parentid", rs.get(i).getMineralgroupid());
                        result.add(wmap);
                    }
                } else if (domain.equalsIgnoreCase("LutDeposit")) {
                    List<LutDeposit> rs = (List<LutDeposit>) dao.getHQLResult("from LutDeposit t order by t.id", "list");
                    for (int i = 0; i < rs.size(); i++) {
                        Map<String, Object> wmap = new HashMap<String, Object>();
                        wmap.put("value", rs.get(i).getDepositid());
                        wmap.put("text", rs.get(i).getDepositnamemon());
                        wmap.put("parentid", rs.get(i).getMineralsid());
                        result.add(wmap);
                    }
                } else if (domain.equalsIgnoreCase("LutButNegj")) {
                    List<LutButNegj> rs = (List<LutButNegj>) dao.getHQLResult("from LutButNegj t order by t.id", "list");
                    for (int i = 0; i < rs.size(); i++) {
                        Map<String, Object> wmap = new HashMap<String, Object>();
                        wmap.put("value", rs.get(i).getId());
                        wmap.put("text", rs.get(i).getName());
                        result.add(wmap);
                    }
                } else if (domain.equalsIgnoreCase("LnkButDeposit")) {
                    List<LnkButDeposit> rs = (List<LnkButDeposit>) dao.getHQLResult("from LnkButDeposit t order by t.id", "list");
                    for (int i = 0; i < rs.size(); i++) {
                        Map<String, Object> wmap = new HashMap<String, Object>();
                        wmap.put("butid", rs.get(i).getButid());
                        wmap.put("depid", rs.get(i).getDepositid());
                        result.add(wmap);
                    }
                } else if (domain.equalsIgnoreCase("LutRole")) {
                    List<LutRole> rs = (List<LutRole>) dao.getHQLResult("from LutRole t order by t.id", "list");
                    for (int i = 0; i < rs.size(); i++) {
                        Map<String, Object> wmap = new HashMap<String, Object>();
                        wmap.put("value", rs.get(i).getId());
                        wmap.put("text", rs.get(i).getRoleNameMon());
                        result.add(wmap);
                    }
                } else if (domain.equalsIgnoreCase("LutCountries")) {
                    final List<LutCountries> rs = (List<LutCountries>) dao.getHQLResult("from LutCountries t", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            if (rs.get(i).getCountryId() != null) {
                                wmap.put("id", rs.get(i).getCountryId());
                                wmap.put("name", rs.get(i).getCountryNameMon());
                                result.add(wmap);
                            }

                        }
                    }
                } else if (domain.equalsIgnoreCase("getDate")) {

                    Calendar c = Calendar.getInstance(Locale.ENGLISH);
                    c.setFirstDayOfWeek(Calendar.SUNDAY);
                    c.setTime(new Date());
                    int today = c.get(Calendar.DAY_OF_WEEK);
                    c.add(Calendar.DAY_OF_WEEK, -today + 4 + Calendar.SUNDAY);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    String formatted = format1.format(c.getTime());

                    Calendar d = Calendar.getInstance(Locale.ENGLISH);
                    d.setFirstDayOfWeek(Calendar.SUNDAY);
                    d.setTime(new Date());
                    int dtoday = d.get(Calendar.DAY_OF_WEEK);

                    System.out.println(Calendar.DAY_OF_WEEK);

                    d.add(Calendar.DAY_OF_WEEK, -dtoday - 2 + Calendar.SUNDAY);
                    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                    String formatted2 = format2.format(d.getTime());

                    System.out.println("@@@" + Calendar.DAY_OF_WEEK);
                    
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.now();
                    
                    
                    String input = dtf.format(localDate);
                    String format = "yyyy-MM-dd";
                    SimpleDateFormat df = new SimpleDateFormat(format);
                    Date date = df.parse(input);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int week = cal.get(Calendar.WEEK_OF_YEAR);

                    System.out.println("Input " + input + " is in week " + week);

                    LutWeeks wk = (LutWeeks) dao.getHQLResult("from LutWeeks t where t.week="+week+"", "current");

                    Map<String, Object> wmap = new HashMap<String, Object>();

                    wmap.put("week", wk.getWeek());
                    wmap.put("start", wk.getStartdate());
                    wmap.put("end", wk.getEnddate());
                    wmap.put("year", wk.getYear());
                    wmap.put("month", wk.getMonth());
                    wmap.put("id", wk.getId());

                    result.add(wmap);
                } else if (domain.equalsIgnoreCase("officers")) {
                    final List<AnnualRegistration> rs = (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            if (rs.get(i).getOfficerid() != null) {
                                wmap.put("value", rs.get(i).getOfficerid());
                                wmap.put("text", rs.get(i).getLutUsers().getGivnamemon());
                                result.add(wmap);
                            }

                        }
                    }
                } else if (domain.equalsIgnoreCase("LutDecisions")) {
                    final List<LutDecisions> rs = (List<LutDecisions>) dao.getHQLResult("from LutDecisions t", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getDecisionId());
                            wmap.put("text", rs.get(i).getDecisionNameMon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("publicOrgs")) {
                    final List<SubLegalpersons> rs = (List<SubLegalpersons>) dao.getHQLResult("from SubLegalpersons t where t.lpReg = 9999999 order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getLpReg());
                            wmap.put("text", rs.get(i).getLpName());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LicOrgs")) {

                    List<SubLicenses> lc = null;

                    LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");

                    if (loguser.getDivisionid() == 3) {
                        lc = (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.licTypeId=1 order by t.id", "list");
                    } else if (loguser.getDivisionid() == 2) {
                        lc = (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.licTypeId=2 and t.mintype=5 order by t.id", "list");
                    } else if (loguser.getDivisionid() == 1) {
                        lc = (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.licTypeId=2 and t.mintype!=5 order by t.id", "list");
                    }


                    String str = "";
                    if (lc.size() > 0) {
                        for (int i = 0; i < lc.size(); i++) {
                            str += "," + lc.get(i).getLpReg();

                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", lc.get(i).getLpReg());
                            if (lc.get(i).getSubLegalpersons() != null) {
                                wmap.put("text", lc.get(i).getSubLegalpersons().getLpName());
                            }

                            result.add(wmap);

						/*	Map<String,Object> wmap=new HashMap<String, Object>();
			        		wmap.put("value", lc.get(i).getLpReg());
			        		wmap.put("text", lc.get(i).getSubLegalpersons().getLpName());
			        		result.add(wmap);  */
                        }
						/*System.out.println(str);

						List<SubLegalpersons> rs=(List<SubLegalpersons>) dao.getHQLResult("from SubLegalpersons t where t.lpReg in ("+str.substring(1)+") order by t.id", "list");
						System.out.println("sss"+rs.size());
						if(rs.size()>0){
				    		for(int i=0;i<rs.size();i++){
				        		Map<String,Object> wmap=new HashMap<String, Object>();
				        		wmap.put("value", rs.get(i).getLpReg());
				        		wmap.put("text", rs.get(i).getLpName());
				        		result.add(wmap);
				        	}
				    	}*/
                    }


                } else if (domain.equalsIgnoreCase("LutDivision")) {
                    final List<LutDivision> rs = (List<LutDivision>) dao.getHQLResult("from LutDivision t order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getDivisionnamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutLptype")) {
                    final List<LutLptype> rs = (List<LutLptype>) dao.getHQLResult("from LutLptype t order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getLptypedecsmon());

                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutMenu")) {
                    final List<LutMenu> rs = (List<LutMenu>) dao.getHQLResult("from LutMenu t order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getNamemn());

                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("SubLegalpersons")) {
                    final List<SubLegalpersons> rs = (List<SubLegalpersons>) dao.getHQLResult("from SubLegalpersons t where t.lpType != 1 order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getLpReg());
                            wmap.put("text", rs.get(i).getLpName());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutLicstatus")) {
                    final List<LutLicstatus> rs = (List<LutLicstatus>) dao.getHQLResult("from LutLicstatus t  order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getLicStatusNameMon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutLictype")) {
                    final List<LutLictype> rs = (List<LutLictype>) dao.getHQLResult("from LutLictype t  order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getLicTypeNameMon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("lutMinerals")) {
                    final List<LutMinerals> rs = (List<LutMinerals>) dao.getHQLResult("from LutMinerals t  order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getMineralnamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutMinGroup")) {
                    final List<LutMinGroup> rs = (List<LutMinGroup>) dao.getHQLResult("from LutMinGroup t order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getGroupname());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("weeklicenses")) {
                    LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");
                    List<SubLicenses> rs = (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.lpReg='" + loguser.getLpreg() + "' and t.weekly=1 and  t.licTypeId=2 and t.configured=0 order by t.id", "list");
                    for (int i = 0; i < rs.size(); i++) {
                        Map<String, Object> wmap = new HashMap<String, Object>();
                        wmap.put("id", rs.get(i).getLicenseNum());
                        wmap.put("lcnum", rs.get(i).getLicenseNum());
                        wmap.put("title", rs.get(i).getAreaNameMon());
                        result.add(wmap);
                    }
                } else if (domain.equalsIgnoreCase("ownlicenses")) {
                    List<LutUsers> loguser = (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "list");
                    if (loguser.size() > 0) {
                        //List<SubLicenses> rs=(List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.lpReg='"+loguser.get(0).getLpreg()+"' and t.licTypeId=2 and t.configured=0 order by t.id", "list");
                        List<SubLicenses> rs = (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where (t.lpReg='" + loguser.get(0).getLpreg() + "' and t.licTypeId=2 and t.plan=1) or (t.lpReg='" + loguser.get(0).getLpreg() + "' and t.licTypeId=2 and t.report=1) or (t.lpReg='" + loguser.get(0).getLpreg() + "' and t.licTypeId=3 and t.plan=1) or (t.lpReg='" + loguser.get(0).getLpreg() + "' and t.licTypeId=3 and t.report=1)   order by t.id", "list");
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getLicenseNum());
                            wmap.put("lcnum", rs.get(i).getLicenseNum());
                            wmap.put("title", rs.get(i).getAreaNameMon());
                            wmap.put("haiguulreport", rs.get(i).getHaiguulreport());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("weekLicenses")) {
                    List<LutUsers> loguser = (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "list");
                    if (loguser.size() > 0) {
                        List<SubLicenses> rs = (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.lpReg='" + loguser.get(0).getLpreg() + "' and t.licTypeId=2 and t.configured=0 order by t.id", "list");
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getLicenseNum());
                            wmap.put("lcnum", rs.get(i).getLicenseNum());
                            wmap.put("title", rs.get(i).getAreaNameMon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("halic")) {
                    List<LutUsers> loguser = (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "list");
                    System.out.println("username" + loguser.size());
                    if (loguser.size() > 0) {
                        List<SubLicenses> rs = (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.lpReg='" + loguser.get(0).getLpreg() + "' and t.configured=0 and t.plan=1 t.licTypeId=1 and t.redemptionplan=0 order by t.id", "list");
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("id", rs.get(i).getLicenseNum());
                            wmap.put("lcnum", rs.get(i).getLicenseNum());
                            wmap.put("title", rs.get(i).getAreaNameMon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutFormindicators")) {
                    final List<LutFormindicators> rs = (List<LutFormindicators>) dao.getHQLResult("from LutFormindicators t  order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getIndicatornamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutMeasurements")) {
                    final List<LutMeasurements> rs = (List<LutMeasurements>) dao.getHQLResult("from LutMeasurements t  order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getMeasnamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("formdata")) {
                    final List<LutFiletype> rs = (List<LutFiletype>) dao.getHQLResult("from LutFiletype t  order by t.id", "list");
                    final List<LutReporttype> rt = (List<LutReporttype>) dao.getHQLResult("from LutReporttype t  order by t.id", "list");
                    final List<LutDivision> dv = (List<LutDivision>) dao.getHQLResult("from LutDivision t  order by t.id", "list");

                    JSONArray arr = new JSONArray();

                    JSONArray ja = new JSONArray();

                    JSONArray div = new JSONArray();

                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            JSONObject jo = new JSONObject();
                            jo.put("value", rs.get(i).getId());
                            jo.put("text", rs.get(i).getFileTypeName());
                            ja.put(jo);
                        }
                    }

                    if (rt.size() > 0) {
                        for (int i = 0; i < rt.size(); i++) {
                            JSONObject jo = new JSONObject();
                            jo.put("value", rt.get(i).getId());
                            jo.put("text", rt.get(i).getReportTypeNameMon());
                            arr.put(jo);
                        }
                    }

                    if (dv.size() > 0) {
                        for (int i = 0; i < dv.size(); i++) {
                            JSONObject jo = new JSONObject();
                            jo.put("value", dv.get(i).getId());
                            jo.put("text", dv.get(i).getDivisionnamemon());
                            div.put(jo);
                        }
                    }

                    JSONObject mainObj = new JSONObject();
                    mainObj.put("LutFiletype", ja);
                    mainObj.put("LutReporttype", arr);
                    mainObj.put("LutDivision", div);
                    return mainObj.toString();
                } else if (domain.equalsIgnoreCase("LutLictype")) {
                    final List<LutLictype> rs = (List<LutLictype>) dao.getHQLResult("from LutLictype t  order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getLicTypeNameMon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutAppstatus")) {
                    final List<LutAppstatus> rs = (List<LutAppstatus>) dao.getHQLResult("from LutAppstatus t  order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getStatusNameMon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutAppsteps")) {
                    final List<LutAppsteps> rs = (List<LutAppsteps>) dao.getHQLResult("from LutAppsteps t  order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getStepsNameMon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutReporttype")) {
                    final List<LutReporttype> rs = (List<LutReporttype>) dao.getHQLResult("from LutReporttype t  order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getReportTypeNameMon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutUsers")) {
                    final List<LutUsers> rs = (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.divisionid!=null order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getGivnamemon());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("HelpCategory")) {
                    final List<HelpCategory> rs = (List<HelpCategory>) dao.getHQLResult("from HelpCategory t order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getId());
                            wmap.put("text", rs.get(i).getCatname());
                            result.add(wmap);
                        }
                    }
                } else if (domain.equalsIgnoreCase("LutWeeks")) {
                    final List<LutWeeks> rs = (List<LutWeeks>) dao.getHQLResult("from LutWeeks t order by t.id", "list");
                    if (rs.size() > 0) {
                        for (int i = 0; i < rs.size(); i++) {
                            Map<String, Object> wmap = new HashMap<String, Object>();
                            wmap.put("value", rs.get(i).getWeek());
                            wmap.put("text", rs.get(i).getWeek()+" - "+rs.get(i).getStartdate()+" / "+rs.get(i).getStartdate());
                            result.add(wmap);
                        }
                    }
                }


            }
            String orgstr = gson.toJson(result);

            String rjson = orgstr.replaceAll("=", ":");
            return rjson;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/confirmReport/{id}/{type}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public String confirmReport(@PathVariable String type, @PathVariable int id, HttpServletRequest req) throws ClassNotFoundException, JSONException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {

            UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");

            List<RegWeeklyMontly> lic = null;
            if (type.equalsIgnoreCase("0")) {
                lic = (List<RegWeeklyMontly>) dao.getHQLResult("from RegWeeklyMontly t where t.licenseNum='" + id + "'", "list");
            } else {
                lic = (List<RegWeeklyMontly>) dao.getHQLResult("from RegWeeklyMontly t where t.licenseNum='" + id + "' and t.submissionDate='" + type + "'", "list");
            }
            for (int i = 0; i < lic.size(); i++) {
                RegWeeklyMontly rw = lic.get(i);
                rw.setRepStatusID((long) 1);
                dao.PeaceCrud(rw, "RegWeeklyMontly", "update", rw.getId(), 0, 0, null);
            }


            //dao.PeaceCrud(null, "RegWeeklyMontly", "update",(long) 0 , 0, 0, "");

            LnkWeekly wk = new LnkWeekly();
            wk.setFormID(lic.get(0).getFormID());
            wk.setLicenseNum(lic.get(0).getRegReportReq().getBundledLicenseNum());
            wk.setLpReg(lic.get(0).getLpReg());
            wk.setReportTypeID(lic.get(0).getReportTypeID());
            wk.setRepStatusID(lic.get(0).getRepStatusID());
            wk.setSubmissiondate(lic.get(0).getSubmissionDate());
            dao.insert(wk, loguser);
            //dao.PeaceCrud(wk, "LnkWeekly", "save",(long) 0 , 0, 0, null);
            return "true";
        }

        return "false";

    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/createReport/{type}/{id}", method = RequestMethod.PUT, produces = {"application/json; charset=UTF-8"})
    public String createReport(@PathVariable int type, @PathVariable int id, @RequestBody String jsonString, HttpServletRequest req) throws ClassNotFoundException, JSONException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {

            JSONObject str = new JSONObject(jsonString);

            List<WeeklyRegistration> wl = (List<WeeklyRegistration>) dao.getHQLResult("from WeeklyRegistration t where t.reqid=" + id + " and t.weekid="+str.getInt("weekid")+"", "list");

            JSONObject obj=new JSONObject();
            if (wl.size() == 0) {
            	/*List<WeeklyMainData> wmd = (List<WeeklyMainData>) dao.getHQLResult("from WeeklyMainData t where t.planid=" + wl.get(0).getId() + " order by t.id", "list");
            	if(wmd.size()==0){
            		
            	}*/
                
            	
                
                AnnualRegistration lr=(AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id='"+id+"'", "current");
                UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
				//SubLicenses lic=(SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='"+lr.getLicensenum()+"'", "current");
				
				LutWeeks lw=(LutWeeks) dao.getHQLResult("from LutWeeks t where t.year="+lr.getReportyear()+" and t.week="+str.getInt("weekid")+"", "current");
				
            	WeeklyRegistration wr= new WeeklyRegistration();
				wr.setReqid(lr.getId());
				wr.setPersonid(loguser.getId());
				wr.setRepstepid((long) 0);
				wr.setLpReg(loguser.getLpreg());
				wr.setLictype(lr.getLictype());
				wr.setLicensenum(Integer.parseInt(lr.getLicensenum()));
				wr.setLpName(loguser.getSubLegalpersons().getLpName());
				wr.setReporttype(type);
				wr.setDivisionid(lr.getDivisionid());
				wr.setRepstatusid((long) 0);
				wr.setYear(Integer.parseInt(lr.getReportyear()));
				wr.setWeekid(str.getInt("weekid"));
				wr.setLicenseXB(lr.getLicenseXB());
				if(lw!=null){
					wr.setWeekstr(lw.getStartdate()+" - "+lw.getEnddate());
				}
				
				dao.PeaceCrud(wr, "WeeklyRegistration", "save",(long) 0 , 0, 0, null);
				
				
				List<DataMinPlan3> datamin3 = (List<DataMinPlan3>) dao.getHQLResult("from DataMinPlan3 t where t.planid=" + id + " order by t.id", "list");
                for (DataMinPlan3 dt : datamin3) {
                    WeeklyMainData wd = new WeeklyMainData();
                    wd.setPlanid(wr.getId());
                    wd.setIndicator(dt.getData1());
                    wd.setMeasurement(dt.getData2());
                    if(dt.getData3()!=null){
                    	  wd.setData75(Double.parseDouble(dt.getData3().toString()));
                    }     
                    wd.setGroupid(dt.getType());
                    wd.setWpercentage(0);
                    wd.setExecution(0);
                    dao.PeaceCrud(wd, "WeeklyMainData", "save", (long) 0, 0, 0, null);

                }
				
				obj.put("minid", wr.getDivisionid());
				obj.put("wrid", wr.getId());
				obj.put("report", "true");
				return obj.toString();
            }

			/*com.peace.users.model.mram.RegReportReq lr=(com.peace.users.model.mram.RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='"+id+"'", "current");
			List<WeeklyRegistration> wl= (List<WeeklyRegistration>) dao.getHQLResult("from WeeklyRegistration t where t.licensenum="+lr.getBundledLicenseNum()+" and t.year='"+str.getInt("year")+"' and t.weekid="+str.getInt("week")+"", "list");

			JSONObject obj=new JSONObject();
			if(wl.size()>0){
				obj.put("minid", lr.getDivisionId());
				obj.put("wrid", wl.get(0).getId());
				obj.put("report", "false");
		        return obj.toString();

			}else{

				SubLicenses lic=(SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='"+lr.getBundledLicenseNum()+"'", "current");

			//	LutForms form= (LutForms) dao.getHQLResult("from LutForms t where t.lutReporttype='"+type+"'", "current");

				UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");

				List<LutFormindicators> rs=(List<LutFormindicators>) dao.getHQLResult("from LutFormindicators t where t.divisionid='"+lr.getDivisionId()+"'", "list");
				Calendar c = Calendar.getInstance(Locale.ENGLISH);
				c.setFirstDayOfWeek(Calendar.FRIDAY);
				c.setTime(new Date());
				int today = c.get(Calendar.DAY_OF_WEEK);
				c.add(Calendar.DAY_OF_WEEK, -today-7+Calendar.FRIDAY);
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				String formatted = format1.format(c.getTime());

				WeeklyRegistration wr= new WeeklyRegistration();
				wr.setReqid(lr.getId());
				wr.setPersonid(loguser.getId());
				wr.setRepstepid((long) 0);
				wr.setLpReg(loguser.getLpreg());
				wr.setLictype(lic.getLicTypeId());
				wr.setLicensenum(lic.getLicenseNum());
				wr.setLpName(loguser.getSubLegalpersons().getLpName());
				wr.setReporttype(type);
				wr.setDivisionid(lr.getDivisionId());
				wr.setRepstatusid((long) 0);
				wr.setYear(str.getInt("year"));
				wr.setWeekid(str.getInt("week"));
				wr.setLicenseXB(lr.getLicenseXB());
				wr.setWeekstr(str.getString("start")+" - "+str.getString("end"));
				dao.PeaceCrud(wr, "WeeklyRegistration", "save",(long) 0 , 0, 0, null);

				System.out.println("ind setLicenseNum"+lr.getId());
				for(int i=0;i<rs.size();i++){
					LutFormindicators ind=rs.get(i);
					RegWeeklyMontly wk= new RegWeeklyMontly();
					wk.setPersonId(loguser.getId());
					wk.setLicenseNum(lr.getBundledLicenseNum());
					wk.setFormID(ind.getFormid());
					wk.setFormIndex(ind.getIndicatorid());
					wk.setReportTypeID(lr.getId());
					wk.setLpReg(lic.getLpReg());
					wk.setMeasID(ind.getMeasID());
					wk.setReportTypeID(lr.getReportTypeId());
					wk.setSubmissionDate(formatted);
					wk.setWrid(wr.getId());
					wk.setEnddate(str.getString("end"));
					wk.setStartdate(str.getString("start"));
					wk.setYear(str.getInt("year"));
					wk.setMonth(str.getInt("month"));
					wk.setLwid(str.getInt("week"));
					dao.PeaceCrud(wk, "RegWeeklyMontly", "save",(long) 0 , 0, 0, null);
				}
				obj.put("minid", wr.getDivisionid());
				obj.put("wrid", wr.getId());
				obj.put("report", "true");
				return obj.toString();
			}*/

        }

        return "false";

    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/RegReportReq", method = RequestMethod.POST)
    public String RegReportReq(@RequestBody String jsonString, HttpServletRequest req) throws ClassNotFoundException, JSONException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            System.out.println("end" + jsonString);
            UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");

            JSONObject str = new JSONObject(jsonString);
            if (str.getInt("id") == 0) {
                com.peace.users.model.mram.RegReportReq qqq = new com.peace.users.model.mram.RegReportReq();


                JSONArray arr = str.getJSONArray("additional");
                SubLicenses lic = (SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='" + str.getInt("main") + "'", "current");
                List<String> a = new ArrayList<String>();
                if (arr.length() > 0) {
                    qqq.setAreaName(str.getString("landname"));
                    qqq.setAddBunLicenseNum(arr.toString());
                    qqq.setLictype(lic.getLicTypeId());
                } else {
                    qqq.setAreaName(lic.getAreaNameMon());
                    //a.add(String.valueOf(str.getInt("main")));
                    //qqq.setAddBunLicenseNum(a.toString());
                    qqq.setAddBunLicenseNum(" ");
                }

                Date d1 = new Date();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
                String formattedDate = df.format(d1);
                qqq.setLicenseXB(lic.getLicenseXB());
                //qqq.setReportTypeId(str.getLong("priority"));
                qqq.setBundledLicenseNum(str.getInt("main"));
                qqq.setLpReg(loguser.getLpreg());
                qqq.setIsactive(true);

                if (lic.getLicenseXB().substring(0, 2).equalsIgnoreCase("mv")) {
                    qqq.setMv(0);
                } else if (lic.getLicenseXB().substring(0, 2).equalsIgnoreCase("xv")) {
                    qqq.setMv(2);
                } else {
                    qqq.setMv(1);
                }
                SimpleDateFormat dfc = new SimpleDateFormat("YYYY");
                String special = dfc.format(d1);
                qqq.setCyear(Integer.parseInt(special));
                qqq.setLatestChangeDateTime(formattedDate);
                qqq.setWk(str.getInt("wk"));
                if (lic.getMintype() == 5) {
                    qqq.setDivisionId((long) 2);
                } else {
                    qqq.setDivisionId((long) 1);
                }

                if (str.has("divisionid")) {
                    if (str.getLong("divisionid") > 0) {
                        qqq.setDivisionId(str.getLong("divisionid"));
                        qqq.setMv(2);
                        qqq.setGroupid(0);
                    }
                }

                qqq.setIsconfiged(0);
                if (lic.getMintype() != null) {
                    qqq.setMineralid(String.valueOf(lic.getMintype()));
                }
                dao.PeaceCrud(qqq, "RegReportReq", "save", (long) 0, 0, 0, null);


                lic.setConfigured(1);

                dao.PeaceCrud(lic, "SubLicenses", "update", lic.getId(), 0, 0, null);

                LnkReportRegBunl bundle = new LnkReportRegBunl();
                bundle.setAddBunLicenseNum(String.valueOf(str.getInt("main")));
                bundle.setBundledLicenseNum(String.valueOf(qqq.getId()));
                dao.PeaceCrud(bundle, "LnkReportRegBunl", "save", (long) 0, 0, 0, null);

                if (arr.length() > 0) {
                    for (int i = 0; i < arr.length(); i++) {
                        String additional = arr.get(i).toString();
                        LnkReportRegBunl bund = new LnkReportRegBunl();
                        if (additional.equalsIgnoreCase(String.valueOf(str.getInt("main")))) {

                        } else {
                            bund.setAddBunLicenseNum(arr.get(i).toString());
                            bund.setBundledLicenseNum(String.valueOf(qqq.getId()));
                            dao.PeaceCrud(bund, "LnkReportRegBunl", "save", (long) 0, 0, 0, null);
                        }
                        SubLicenses alic = (SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='" + arr.get(i).toString() + "'", "current");
                        alic.setConfigured(1);

                        dao.PeaceCrud(alic, "SubLicenses", "update", alic.getId(), 0, 0, null);
                    }
                    com.peace.users.model.mram.RegReportReq up = (com.peace.users.model.mram.RegReportReq) dao.getHQLResult("from RegReportReq t where t.id='" + qqq.getId() + "'", "current");
                    List rs = up.getLnkReportRegBunl();

                    for (int i = 0; i < rs.size(); i++) {
                        LnkReportRegBunl bund = (LnkReportRegBunl) rs.get(i);

                        if (!bund.getAddBunLicenseNum().equalsIgnoreCase(String.valueOf(str.getInt("main")))) {
                            a.add(bund.getAddBunLicenseNum());
                        }
                    }
                    up.setAddBunLicenseNum(a.toString());
                    dao.PeaceCrud(up, "RegReportReq", "update", up.getId(), 0, 0, null);

                }
            }
            return "true";
        }

        return "false";

    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/cru", method = RequestMethod.GET)
    public String createuser(HttpServletRequest req) throws ClassNotFoundException, JSONException {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("end end");
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List lps = (List<SubLegalpersons>) dao.getHQLResult("from SubLegalpersons t", "list");
            LutRole lr = (LutRole) dao.getHQLResult("from LutRole t where t.roleNameEng='ROLE_COMPANY'", "current");

            if (lps.size() > 0) {
                for (int i = 0; i < lps.size(); i++) {
                    SubLegalpersons lp = (SubLegalpersons) lps.get(i);
                    lp.setConfirmed(true);
                    dao.PeaceCrud(lp, "SubLegalpersons", "save", lp.getId(), 0, 0, null);

                    LutUsers lu = new LutUsers();
                    lu.setLpreg(lp.getLpReg());
                    lu.setUsername(lp.getLpReg());
                    lu.setUserpass(passwordEncoder.encode(lp.getLpReg()));
                    lu.setIsactive(false);

                    dao.PeaceCrud(lu, "lu", "save", (long) 0, 0, 0, null);


                    LnkOffRole rl = new LnkOffRole();
                    rl.setRoleid(lr.getId());
                    rl.setUserid(lu.getId());
                    dao.PeaceCrud(rl, "LnkOffRole", "save", (long) 0, 0, 0, null);
                }
                return "true";
            }
            Gson gson = new Gson();
            return "false";
        }

        return "error.500";

    }

    @RequestMapping(value = "/ujson", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String ujson(HttpServletRequest req) {
        try {

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");
                if (loguser != null) {
                    JSONObject fistList = new JSONObject();
                    fistList.put("id", loguser.getId());
                    fistList.put("role", loguser.getLnkOffRoles().get(0).getLutRole().getRoleNameEng());
                    fistList.put("username", loguser.getGivnamemon());
                    fistList.put("lpreg", loguser.getLpreg());

                    if (loguser.getDivisionid() != null) {
                        LutDivision div = (LutDivision) dao.getHQLResult("from LutDivision t where t.id='" + loguser.getDivisionid() + "'", "current");
                        fistList.put("lpname", loguser.getSubLegalpersons().getLpName() + " - " + div.getDivisionnamemon());
                    } else {
                        fistList.put("lpname", loguser.getSubLegalpersons().getLpName());
                    }
                    fistList.put("avatar", loguser.getAvatar());
                    fistList.put("step", loguser.getStepid());
                    fistList.put("mobile", loguser.getMobile());
                    fistList.put("famnamemon", loguser.getFamnamemon());
                    fistList.put("famnameeng", loguser.getFamnameeng());
                    fistList.put("givnamemon", loguser.getGivnamemon());
                    fistList.put("givnameeng", loguser.getGivnameeng());
                    fistList.put("divisionid", loguser.getDivisionid());
                    fistList.put("group", loguser.getGroupid());
                    fistList.put("positionid", loguser.getPositionid());
                    if (loguser.getPositionid() == 1 || loguser.getPositionid() == 2) {
                        fistList.put("config", "true");
                    } else {
                        fistList.put("config", "false");
                    }

                    if (loguser.getPositionid() == 1) {
                        fistList.put("tab", "true");
                    } else {
                        fistList.put("tab", "false");
                    }
                    return fistList.toString();
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/mrole/{str}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String mrole(@PathVariable String str, HttpServletRequest req) {
        try {
            System.out.println("page" + str);
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            LutUsers loguser = null;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Collection<?> coll = userDetail.getAuthorities();
                Iterator<?> itr = coll.iterator();
                while (itr.hasNext()) {
                    String rolename = itr.next().toString();
                    System.out.println("sss " + rolename);
                    if ("ROLE_SUPER".equals(rolename)) {
                        Map<String, Object> wmap = new HashMap<String, Object>();
                        wmap.put("read", 1);
                        wmap.put("create", 1);
                        wmap.put("update", 1);
                        wmap.put("delete", 1);
                        wmap.put("export", 1);
                        result.add(wmap);

                    } else {
                        loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");
                        LutMenu rs = (LutMenu) dao.getHQLResult("from LutMenu t where t.stateurl='" + str + "' order by t.id", "current");
                        List<LnkMenuRole> roleset = loguser.getLnkOffRoles().get(0).getLutRole().getLnkMenuRoles();
                        System.out.println("role size" + roleset.size());
                        System.out.println("menuid" + rs.getId());
                        System.out.println("role name" + roleset.get(0).getLutRole().getRoleNameMon());
                        if (roleset.size() > 0) {
                            for (int i = 0; i < roleset.size(); i++) {
                                Map<String, Object> wmap = new HashMap<String, Object>();
                                if (roleset.get(i).getMenuid().equals(rs.getId())) {
                                    System.out.println("roleset" + roleset.get(i).getMenuid());
                                    if (roleset.get(i).getRread() > 0) {
                                        wmap.put("read", 1);
                                    } else {
                                        wmap.put("read", 0);
                                    }
                                    if (roleset.get(i).getRcreate() > 0) {
                                        wmap.put("create", 1);
                                    } else {
                                        wmap.put("create", 0);
                                    }
                                    if (roleset.get(i).getRupdate() > 0) {
                                        wmap.put("update", 1);
                                    } else {
                                        wmap.put("update", 0);
                                    }
                                    if (roleset.get(i).getRdelete() > 0) {
                                        wmap.put("delete", 1);
                                    } else {
                                        wmap.put("delete", 0);
                                    }
                                    if (roleset.get(i).getRexport() > 0) {
                                        wmap.put("export", 1);
                                    } else {
                                        wmap.put("export", 0);
                                    }
                                    result.add(wmap);
                                }
                            }
                        }
                    }

                }

            }


            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value = "/rolemenu", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String rolemenu(HttpServletRequest req) {
        try {

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            LutUsers loguser = null;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Collection<?> coll = userDetail.getAuthorities();
                Iterator<?> itr = coll.iterator();
                while (itr.hasNext()) {
                    String rolename = itr.next().toString();
                    //String returnS=dao.loginedUserViewAuthority(userDetail, id);
                    if ("ROLE_SUPER".equals(rolename)) {
                        List<LutMenu> rs = (List<LutMenu>) dao.getHQLResult("from LutMenu t  order by t.orderid", "list");
                        if (rs.size() > 0) {
                            for (int i = 0; i < rs.size(); i++) {
                                Map<String, Object> wmap = new HashMap<String, Object>();
                                wmap.put("id", rs.get(i).getId());
                                wmap.put("title", rs.get(i).getNamemn());

                                wmap.put("icon", rs.get(i).getIcon());

                                List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();

                                if (rs.get(i).getChilds().size() > 0) {
                                    List<LutMenu> chi = rs.get(i).getChilds();

                                    for (int j = 0; j < rs.get(i).getChilds().size(); j++) {
                                        Map<String, Object> child = new HashMap<String, Object>();
                                        child.put("title", chi.get(j).getNamemn());
                                        child.put("link", chi.get(j).getStateurl());
                                        childs.add(child);
                                    }

                                } else {
                                    wmap.put("link", rs.get(i).getStateurl());
                                }
                                wmap.put("submenu", childs);
                                result.add(wmap);
                            }
                        }

                    }

                }

            }


            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/mjson", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String mjson(HttpServletRequest req) {
        try {

            JSONObject result = new JSONObject();
            LutUsers loguser = null;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Collection<?> coll = userDetail.getAuthorities();
                Iterator<?> itr = coll.iterator();
                long userid = 0;
                String roles = "";
                boolean rolesuper = false;
                while (itr.hasNext()) {
                    String rolename = itr.next().toString();
                    if (!"ROLE_SUPER".equals(rolename) && rolename.length() > 0) {
                        loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");
                        userid = loguser.getId();
                    } else {
                        rolesuper = true;
                    }
                }

                if (rolesuper) {
                    result = services.getMjson(roles, true, loguser, userDetail);
                } else {
					/*for(int i=0;i<loguser.get.size();i++){
						roles=roles+","+loguser.getRoleid();
					}*/
                    roles = String.valueOf(loguser.getLnkOffRoles().get(0).getLutRole().getId());
                    result = services.getMjson(roles, false, loguser, userDetail);
                }

                System.out.println("done");
                System.out.println("done" + result.toString());

            }

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*@RequestMapping(value="/mjson",method=RequestMethod.GET, produces={"application/json; charset=UTF-8"})
	public @ResponseBody String mjson(HttpServletRequest req){
		try{

			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			LutUsers loguser= null;
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Collection<?> coll=userDetail.getAuthorities();
				Iterator<?> itr=coll.iterator();
				while(itr.hasNext()){
					String rolename = itr.next().toString();
					//String returnS=dao.loginedUserViewAuthority(userDetail, id);
					if("ROLE_SUPER".equals(rolename)){
						List<LutMenu> rs=(List<LutMenu>) dao.getHQLResult("from LutMenu t where t.parentid=0 order by t.orderid", "list");
				    	if(rs.size()>0){
				    		for(int i=0;i<rs.size();i++){
				        		Map<String,Object> wmap=new HashMap<String, Object>();
				        		wmap.put("id", rs.get(i).getId());
				        		wmap.put("title", rs.get(i).getNamemn());

				        		wmap.put("icon", rs.get(i).getIcon());

				        		List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();

				        		if(rs.get(i).getChilds().size()>0){
				        			List<LutMenu> chi=rs.get(i).getChilds();

				        			for(int j=0;j<rs.get(i).getChilds().size();j++){
				        				Map<String,Object> child=new HashMap<String, Object>();
						        		child.put("title", chi.get(j).getNamemn());
						        		child.put("link", chi.get(j).getStateurl());
						        		childs.add(child);
				        			}

				        		}
				        		else{
				        			wmap.put("link", rs.get(i).getStateurl());
				        		}
				        		wmap.put("submenu", childs);
				        		result.add(wmap);
				        	}
				    	}

					}
					else{
						loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
						List<LnkMenuRole> roleset=loguser.getRole().getLnkMenuRoles();
						if(roleset.size()>0){
				    		for(int i=0;i<roleset.size();i++){
				        		Map<String,Object> wmap=new HashMap<String, Object>();
				        		if(roleset.get(i).getRread()>0){
				        			LutMenu lm=roleset.get(i).getLutMenu();;
				        			if(lm.getParentid()==0){
				        				wmap.put("id", lm.getId());
						        		wmap.put("title", lm.getNamemn());

						        		wmap.put("icon", lm.getIcon());

						        		List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();
						        		if(roleset.get(i).getLutMenu().getChilds().size()>0){
						        			List<LutMenu> chi=roleset.get(i).getLutMenu().getChilds();
						        			for(int j=0;j<chi.size();j++){
						        				LutMenu rs=chi.get(j);

						        				List<LnkMenuRole> lk=rs.getLnkMenuRoles();
						        				int a =lk.size();
						        				if(lk.size()>1){
						        					a=1;
						        				}
						        				for(int l=0; l<a;l++){
						        					LnkMenuRole rl=lk.get(l);
						        					if(rl.getRread()>0){
						        						Map<String,Object> child=new HashMap<String, Object>();
										        		child.put("title", rs.getNamemn());

										        		List<LutMenu> third=rs.getChilds();
										        		if(third.size()>0){
										        			List<Map<String, Object>> tchilds = new ArrayList<Map<String, Object>>();
										        			for(int t=0; t<third.size(); t++){
										        				LutMenu tlm=third.get(t);
										        				Map<String,Object> tchild=new HashMap<String, Object>();
										        				tchild.put("title", tlm.getNamemn());
										        				tchild.put("link",  tlm.getStateurl());
										        				tchilds.add(tchild);
										        			}
										        			child.put("submenu",  tchilds);
										        		}
										        		else{
										        			child.put("link",  rs.getStateurl());
										        		}
										        		childs.add(child);
						        					}

						        				}
						        				if(rs.getLnkMenuRoles().get(0).getRread()>0){
						        					Map<String,Object> child=new HashMap<String, Object>();
									        		child.put("title", rs.getNamemn());
									        		child.put("link",  rs.getStateurl());
									        		childs.add(child);
						        				}
						        			}

						        		}
						        		else{
						        			wmap.put("link", roleset.get(i).getLutMenu().getStateurl());
						        		}
						        		wmap.put("submenu", childs);
						        		result.add(wmap);
				        			}

				        		}
				        	}
				    	}
					}

				}

			}


	    	ObjectMapper mapper = new ObjectMapper();
	        return mapper.writeValueAsString(result);

		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}*/

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/parentmenus", method = RequestMethod.GET)
    public String parentmenus(HttpServletRequest req) throws ClassNotFoundException, JSONException {
        //List<LutMenu> rel=(List<LutMenu>) dao.getHQLResult("from LutMenu t where t.stateurl!='#'", "list");
        List<LutMenu> rel = (List<LutMenu>) dao.getHQLResult("from LutMenu t where t.parentid=0 order by t.orderid ", "list");
        JSONArray arr = new JSONArray();
        for (int i = 0; i < rel.size(); i++) {
            JSONObject fistList = new JSONObject();
            fistList.put("id", rel.get(i).getId());
            fistList.put("title", rel.get(i).getNamemn());
            fistList.put("value", rel.get(i).getId());
            fistList.put("parent_id", rel.get(i).getParentid());
            fistList.put("level", 1);
            arr.put(fistList);

            if (rel.get(i).getChilds().size() > 0) {

                List<LutMenu> chi = rel.get(i).getChilds();

                for (int j = 0; j < chi.size(); j++) {
                    LutMenu rs = chi.get(j);
                    JSONObject fistList1 = new JSONObject();
                    fistList1.put("id", rs.getId());
                    fistList1.put("title", rs.getNamemn());
                    fistList1.put("value", rs.getId());
                    fistList1.put("parent_id", rs.getParentid());
                    fistList1.put("level", 2);
                    arr.put(fistList1);

                    List<LutMenu> chil = rs.getChilds();

                    for (int c = 0; c < chil.size(); c++) {
                        LutMenu rsc = chil.get(c);
                        JSONObject fistList2 = new JSONObject();
                        fistList2.put("id", rsc.getId());
                        fistList2.put("title", rsc.getNamemn());
                        fistList2.put("value", rsc.getId());
                        fistList2.put("parent_id", rsc.getParentid());
                        fistList2.put("level", 3);
                        arr.put(fistList2);
                    }

                }

            }
        }
        JSONObject wmap = new JSONObject();
        wmap.put("options", arr);

        return wmap.toString();
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/read/{domain}/{id}", method = RequestMethod.GET)
    public Object read(@PathVariable int id, @PathVariable String domain, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<LnkMenuRole> rel = (List<LnkMenuRole>) dao.getHQLResult("from LnkMenuRole t where t.roleid=" + id + "", "list");
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < rel.size(); i++) {
                Map<String, Object> wmap = new HashMap<String, Object>();
                wmap.put("menuid", rel.get(i).getMenuid());
                wmap.put("roleid", rel.get(i).getRoleid());
                wmap.put("create", rel.get(i).getRcreate());
                wmap.put("read", rel.get(i).getRread());
                wmap.put("update", rel.get(i).getRupdate());
                wmap.put("delete", rel.get(i).getRdelete());
                wmap.put("export", rel.get(i).getRexport());
                result.add(wmap);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/create/user", method = RequestMethod.GET)
    public Object createusser(HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<SubLegalpersons> rel = (List<SubLegalpersons>) dao.getHQLResult("from SubLegalpersons t where t.confirmed=0", "list");
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < rel.size(); i++) {

                SubLegalpersons lp = rel.get(i);
                LutRole lr = (LutRole) dao.getHQLResult("from LutRole t where t.roleNameEng='ROLE_COMPANY'", "current");
                List<LutUsers> lu = (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.lpreg='" + lp.getLpReg() + "'", "list");
                //Boolean bs=batchobj.getBoolean("confirmed");
                lp.setConfirmed(true);
                dao.PeaceCrud(lp, "SubLegalpersons", "save", lp.getId(), 0, 0, null);

                if (lu.size() > 0) {
                    LutUsers lus = lu.get(0);
                    lus.setUserpass(passwordEncoder.encode(lp.getLpReg()));
                    //Boolean bs=batchobj.getBoolean("confirmed");
                    lus.setIsactive(true);
                    dao.PeaceCrud(lus, "lu", "update", lus.getId(), 0, 0, null);
                } else {
                    LutUsers lus = new LutUsers();
                    lus.setLpreg(lp.getLpReg());
                    lus.setUsername(lp.getLpReg());
                    lus.setIspublic(0);
                    lus.setUserpass(passwordEncoder.encode(lp.getLpReg()));
                    //  Boolean bs=batchobj.getBoolean("confirmed");
                    lus.setIsactive(true);
                    dao.PeaceCrud(lus, "lu", "save", (long) 0, 0, 0, null);

                    LnkOffRole rl = new LnkOffRole();
                    rl.setRoleid(lr.getId());
                    rl.setUserid(lus.getId());
                    dao.PeaceCrud(rl, "LnkOffRole", "save", (long) 0, 0, 0, null);
                }


            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/create/xv", method = RequestMethod.GET)
    public Object createlic(HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            List<SubLicenses> rel = (List<SubLicenses>) dao.getHQLResult("from SubLicenses t where t.licTypeId=1 and configured=0", "list");
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < rel.size(); i++) {

                SubLicenses lp = rel.get(i);
                lp.setPlan(true);
                lp.setReport(true);
                lp.setConfigured(1);
                lp.setDivisionId((long) 3);
                dao.PeaceCrud(lp, "SubLicenses", "save", lp.getId(), 0, 0, "licenseNum");

                List<com.peace.users.model.mram.RegReportReq> rq = (List<com.peace.users.model.mram.RegReportReq>) dao.getHQLResult("from RegReportReq t where t.cyear=2020 and t.bundledLicenseNum='" + lp.getLicenseNum() + "'", "list");

                if (rq.size() > 0) {
                   /* com.peace.users.model.mram.RegReportReq qqq = rq.get(0);
                    qqq.setLictype(lp.getLicTypeId());
                    qqq.setBundledLicenseNum(lp.getLicenseNum());
                    qqq.setAreaName(lp.getAreaNameMon());
                    qqq.setLpReg(lp.getLpReg());
                    qqq.setIsactive(true);
                    qqq.setAddBunLicenseNum(String.valueOf(lp.getLicenseNum()));
                    qqq.setDivisionId((long) 3);
                    Date d1 = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
                    String formattedDate = df.format(d1);
                    qqq.setLatestChangeDateTime(formattedDate);
                    qqq.setWk(0);
                    qqq.setLicenseXB(lp.getLicenseXB());
                    qqq.setGroupid(0);
                    String str = lp.getLicenseXB().substring(0, Math.min(lp.getLicenseXB().length(), 2));

                    if (str.equalsIgnoreCase("mv")) {
                        qqq.setMv(0);
                    } else if (str.equalsIgnoreCase("xv")) {
                        qqq.setMv(2);
                    } else {
                        qqq.setMv(1);
                    }


                    dao.PeaceCrud(qqq, "RegReportReq", "update", (long) qqq.getId(), 0, 0, null);*/
                } else {
                    com.peace.users.model.mram.RegReportReq qqq = new com.peace.users.model.mram.RegReportReq();
                    qqq.setLictype(lp.getLicTypeId());
                    qqq.setBundledLicenseNum(lp.getLicenseNum());
                    qqq.setAreaName(lp.getAreaNameMon());
                    qqq.setLpReg(lp.getLpReg());
                    qqq.setIsactive(true);
                    qqq.setAddBunLicenseNum(String.valueOf(lp.getLicenseNum()));
                    Date d1 = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
                    String formattedDate = df.format(d1);
                    qqq.setDivisionId((long) 3);
                    qqq.setCyear(2020);
                    qqq.setLatestChangeDateTime(formattedDate);
                    qqq.setWk(0);
                    qqq.setGroupid(0);
                    String str = lp.getLicenseXB().substring(0, Math.min(lp.getLicenseXB().length(), 2));

                    if (str.equalsIgnoreCase("mv")) {
                        qqq.setMv(0);
                    } else if (str.equalsIgnoreCase("xv")) {
                        qqq.setMv(2);
                    } else {
                        qqq.setMv(1);
                    }
                    qqq.setLicenseXB(lp.getLicenseXB());
                    dao.PeaceCrud(qqq, "RegReportReq", "save", (long) 0, 0, 0, null);
                }


            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/general/delete/{domain}/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id, @PathVariable String domain, HttpServletRequest req) throws ClassNotFoundException, JSONException {
        try {
            String appPath = req.getSession().getServletContext().getRealPath("");
            if (domain.equalsIgnoreCase("com.macronote.macrocms.domain.complex.ClientDynamicContent")) {
				/* ClientDynamicContent rel=(ClientDynamicContent) dao.getHQLResult("from ClientDynamicContent t where t.id="+id+"", "current");
				 String path=rel.getFile();
				 //clean-up
				 String delPath = appPath + File.separator + path;
		    	 File destination = new File(delPath);


	    		if(destination.delete()){
	    			System.out.println(destination.getName() + " is deleted!");
	    		}else{
	    			System.out.println("Delete operation is failed.");
	    		}

				dao.MacroCmsAction(null, domain, "delete", id, 0, 0, null);
				dao.getUniqueSqlResult("delete from client_catcon_rel where conID="+id+"");*/
            } else {
                dao.PeaceCrud(null, domain, "delete", (long) id, 0, 0, null);
            }

        } catch (Exception e) {
            logger.error(e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/delete/{domain}/{id}", method = RequestMethod.DELETE, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String delete(Model model, @PathVariable int id, @PathVariable String domain) {
        try {
            Class<?> classtoConvert;
            String domainName = domain;
            classtoConvert = Class.forName(domain);
            dao.PeaceCrud(null, domainName, "delete", (long) id, 0, 0, null);

            return "true";
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return null;
        }

    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{action}/{domain}/{id}", method = RequestMethod.DELETE, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String update(Model model, @RequestBody String jsonString, @PathVariable int id, @PathVariable String action, @PathVariable String domain) {
        System.out.println("json STR " + jsonString);
        try {
            Class<?> classtoConvert;
            JSONObject obj = new JSONObject(jsonString);


            String domainName = domain;
            System.out.println(domain);
            classtoConvert = Class.forName(domain);
            Gson gson = new Gson();
            Object object = gson.fromJson(obj.toString(), classtoConvert);


            if (action.equals("update")) {

                if (!obj.has("models")) {
                    id = (int) obj.getInt("id");
                    dao.PeaceCrud(object, domainName, "update", (long) id, 0, 0, null);
                } else {
                    JSONArray rs = (JSONArray) obj.get("models");
                    System.out.println("rs obj " + rs);
                    for (int i = 0; i < rs.length(); i++) {
                        String str = rs.get(i).toString();
                        JSONObject batchobj = new JSONObject(str);
                        System.out.println("ba obj " + batchobj);
                        Object bobj = gson.fromJson(batchobj.toString(), classtoConvert);
                        System.out.println("ba obj " + bobj);
                        int upid = batchobj.getInt("id");
                        System.out.println("id  " + upid);
                        dao.PeaceCrud(bobj, domainName, "update", (long) upid, 0, 0, null);
                    }

                }

            } else if (action.equals("delete")) {
                id = (int) obj.getInt("id");
                ;
                System.out.println("endd " + domainName);
                if (domainName.equalsIgnoreCase("com.macronote.macrocms.domain.admin.MacroCmsUserRole")) {
                    dao.PeaceCrud(object, domainName, "delete", (long) id, 0, 0, null);
                    //  dao.deleteByNatvive("delete from macrocmsuserrolemenurel where roleid="+id+"");
                } else if (domainName.equalsIgnoreCase("com.macronote.macrocms.domain.complex.ClientDynamicContent")) {
                    dao.PeaceCrud(object, domainName, "delete", (long) id, 0, 0, null);
                    //  dao.deleteByNatvive("delete from client_catcon_rel where conID="+id+"");
                } else {
                    dao.PeaceCrud(object, domainName, "delete", (long) id, 0, 0, null);
                }

            } else if (action.equals("create")) {
                dao.PeaceCrud(object, domainName, "save", (long) 0, 0, 0, null);
            }
            return "true";
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return null;
        }

    }


    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private SmtpMailSender smtpMailSender;

    @RequestMapping(value = "/editing/{action}/{domain}", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String update(Model model, @RequestBody String jsonString, @PathVariable String action, @PathVariable String domain) throws JSONException {
        System.out.println("json STR " + jsonString);
        try {
            Class<?> classtoConvert;
            JSONObject obj = new JSONObject(jsonString);


            String domainName = domain;
            System.out.println(domain);
            classtoConvert = Class.forName(domain);
            Gson gson = new Gson();
            Object object = gson.fromJson(obj.toString(), classtoConvert);

            if (action.equals("update")) {

                if (!obj.has("models")) {


                    if (domainName.equalsIgnoreCase("com.peace.users.model.mram.LutUsers")) {
                        JSONObject str = new JSONObject(jsonString);

                        dao.PeaceCrud(null, "LnkOffRole", "delete", (long) str.getLong("id"), 0, 0, "userid");

                        LutUsers cr = (LutUsers) dao.getHQLResult("from LutUsers t where t.id='" + str.getInt("id") + "'", "current");
                        if (!str.isNull("lpreg")) {
                            cr.setLpreg(str.getString("lpreg"));
                        }
                        if (!str.isNull("famnamemon")) {
                            cr.setFamnamemon(str.getString("famnamemon"));
                        }
                        if (!str.isNull("famnameeng")) {
                            cr.setFamnameeng(str.getString("famnameeng"));
                        }
                        if (!str.isNull("givnamemon")) {
                            cr.setGivnamemon(str.getString("givnamemon"));
                        }
                        if (!str.isNull("givnameeng")) {
                            cr.setGivnameeng(str.getString("givnameeng"));
                        }
                        if (!str.isNull("mobile")) {
                            cr.setMobile(str.getString("mobile"));
                        }
                        if (!str.isNull("positionid")) {
                            cr.setPositionid(str.getInt("positionid"));
                        }
                        if (!str.isNull("email")) {
                            cr.setEmail(str.getString("email"));
                        }
                        if (!str.isNull("stepid")) {
                            cr.setStepid(str.getInt("stepid"));
                        }


                        cr.setRoleid(str.getLong("roleid"));
                        cr.setGroupid(str.getLong("groupid"));
                        cr.setUsername(str.getString("username"));
                        cr.setDivisionid(str.getLong("divisionid"));

                        if (cr.getUserpass().equalsIgnoreCase(str.getString("userpass"))) {
                            cr.setUserpass(str.getString("userpass"));
                        } else {
                            cr.setUserpass(passwordEncoder.encode(str.getString("userpass")));
                        }

                        cr.setIspublic(1);
                        if (str.getBoolean("isactive")) {
                            cr.setIsactive(true);
                        } else {
                            cr.setIsactive(false);
                        }
                        dao.PeaceCrud(cr, domainName, "update", str.getLong("id"), 0, 0, null);


                        LnkOffRole rl = new LnkOffRole();
                        rl.setRoleid(str.getLong("roleid"));
                        rl.setUserid(cr.getId());
                        dao.PeaceCrud(rl, "LnkOffRole", "save", (long) 0, 0, 0, null);


                    } else if (domainName.equalsIgnoreCase("com.peace.users.model.mram.RegReportReq")) {
                        int id = (int) obj.getInt("id");
                        dao.PeaceCrud(object, domainName, "update", (long) id, 0, 0, null);
                        List<AnnualRegistration> cr = (List<AnnualRegistration>) dao.getHQLResult("from AnnualRegistration t where t.reqid='" + id + "'" + " and t.divisionid = 3", "list");
                        if (cr != null && !cr.isEmpty()) {
                            for (AnnualRegistration a : cr) {
                                a.setGroupid(obj.getInt("groupid"));
                                dao.PeaceCrud(a, "AnnualRegistration", "update", (long) a.getId(), 0, 0, null);
                            }
                        }
                    } else {
                        int id = (int) obj.getInt("id");
                        dao.PeaceCrud(object, domainName, "update", (long) id, 0, 0, null);
                    }

                } else {
                    JSONArray rs = (JSONArray) obj.get("models");
                    System.out.println("rs obj " + rs);
                    for (int i = 0; i < rs.length(); i++) {
                        String str = rs.get(i).toString();
                        JSONObject batchobj = new JSONObject(str);
                        System.out.println("ba obj " + batchobj);
                        Object bobj = gson.fromJson(batchobj.toString(), classtoConvert);
                        System.out.println("ba obj " + bobj);
                        int upid = batchobj.getInt("id");
                        System.out.println("id  " + upid);

                        if (domainName.equalsIgnoreCase("com.peace.users.model.mram.SubLegalpersons")) {

                            SubLegalpersons lp = (SubLegalpersons) dao.getHQLResult("from SubLegalpersons t where t.id=" + upid + "", "current");
                            LutRole lr = (LutRole) dao.getHQLResult("from LutRole t where t.roleNameEng='ROLE_COMPANY'", "current");
                            List<LutUsers> lu = (List<LutUsers>) dao.getHQLResult("from LutUsers t where t.lpreg='" + lp.getLpReg() + "'", "list");
                            Boolean bs = batchobj.getBoolean("confirmed");
                            lp.setConfirmed(bs);
                            if (bs == false) {
                                LutUsers luser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + lp.getLpReg() + "'", "current");
                                if (luser != null) {
                                    luser.setIsactive(false);
                                    dao.PeaceCrud(luser, "LutUsers", "save", luser.getId(), 0, 0, null);
                                }
                            }
                            if (batchobj.has("email") && !batchobj.isNull("email")) {
                                lp.setEmail(batchobj.getString("email"));
                                if (lp.getConfirmed()) {
                                    lp.setUpdateddate(new Date());
                                }
                            }

                            dao.PeaceCrud(lp, "SubLegalpersons", "save", lp.getId(), 0, 0, null);

                            if (lu.size() > 0) {
                                LutUsers lus = lu.get(0);
                                String uuid = UUID.randomUUID().toString();

                                if (bs) {
                                    lus.setIsactive(bs);
                                    lus.setEmail(lp.getEmail());
                                    if (lp.getEmail() != null && lp.getEmail().length() > 0) {
                                        smtpMailSender.send(lp.getEmail(), "username" + " : " + lus.getUsername(), "password" + " : " + uuid);
                                        lus.setUserpass(passwordEncoder.encode(uuid));
                                    }
                                }

                                dao.PeaceCrud(lus, "lu", "update", lus.getId(), 0, 0, null);
                            } else {
                                LutUsers lus = new LutUsers();
                                lus.setLpreg(lp.getLpReg());
                                lus.setUsername(lp.getLpReg());
                                lus.setIspublic(0);
                                lus.setUserpass(passwordEncoder.encode(lp.getLpReg()));
                                //  Boolean bs=batchobj.getBoolean("confirmed");
                                lus.setIsactive(bs);
                                dao.PeaceCrud(lus, "lu", "save", (long) 0, 0, 0, null);

                                LnkOffRole rl = new LnkOffRole();
                                rl.setRoleid(lr.getId());
                                rl.setUserid(lus.getId());
                                dao.PeaceCrud(rl, "LnkOffRole", "save", (long) 0, 0, 0, null);
                            }

                        } else if (domainName.equalsIgnoreCase("com.peace.users.model.mram.RegWeeklyMontly")) {
                            RegWeeklyMontly rw = (RegWeeklyMontly) dao.getHQLResult("from RegWeeklyMontly t where t.id=" + upid + "", "current");

                            JSONObject batchobj1 = new JSONObject(str.replace("null", "0"));

                            rw.setData1(batchobj1.getLong("data1"));
                            rw.setData2(batchobj1.getLong("data2"));
                            rw.setData3(batchobj1.getLong("data3"));
                            rw.setData4(batchobj1.getLong("data4"));
                            rw.setData5(batchobj1.getLong("data5"));
                            rw.setData6(batchobj1.getLong("data6"));
                            rw.setData7(batchobj1.getLong("data7"));

                            dao.PeaceCrud(rw, "RegWeeklyMontly", "update", (long) upid, 0, 0, null);


                        } else {
                            dao.PeaceCrud(bobj, domainName, "update", (long) upid, 0, 0, null);
                        }

                    }

                }

            } else if (action.equals("delete")) {
                int id = (int) obj.getInt("id");
                ;
                System.out.println("endd " + domainName);
                if (domainName.equalsIgnoreCase("com.macronote.macrocms.domain.admin.MacroCmsUserRole")) {
                    dao.PeaceCrud(object, domainName, "delete", (long) id, 0, 0, null);
                    // dao.deleteByNatvive("delete from macrocmsuserrolemenurel where roleid="+id+"");
                } else if (domainName.equalsIgnoreCase("com.macronote.macrocms.domain.complex.ClientDynamicContent")) {
                    dao.PeaceCrud(object, domainName, "delete", (long) id, 0, 0, null);
                    //  dao.deleteByNatvive("delete from client_catcon_rel where conID="+id+"");
                } else if (domainName.equalsIgnoreCase("com.peace.users.model.mram.LutUsers")) {
                    System.out.println("endd " + domainName);
                    System.out.println("endd " + domainName);
                    LutUsers cr = (LutUsers) dao.getHQLResult("from LutUsers t where t.id='" + obj.getInt("id") + "'", "current");
                    LnkOffRole aa = (LnkOffRole) dao.getHQLResult("from LnkOffRole t where t.userid='" + cr.getId() + "'", "current");
                    if (aa != null) {
                        dao.PeaceCrud(aa, domainName, "delete", (long) aa.getId(), 0, 0, null);
                    }
                    dao.PeaceCrud(cr, domainName, "delete", (long) id, 0, 0, null);
                    //  dao.deleteByNatvive("delete from client_catcon_rel where conID="+id+"");
                } else {
                    dao.PeaceCrud(object, domainName, "delete", (long) id, 0, 0, null);
                }

            } else if (action.equals("create")) {
                if (domainName.equalsIgnoreCase("com.peace.users.model.mram.LutUsers")) {
                    JSONObject str = new JSONObject(jsonString);
                    LutUsers cr = new LutUsers();

                    cr.setLpreg(str.getString("lpreg"));
                    cr.setFamnamemon(str.getString("famnamemon"));
                    cr.setFamnameeng(str.getString("famnameeng"));
                    cr.setGivnamemon(str.getString("givnamemon"));
                    cr.setGivnameeng(str.getString("givnameeng"));
                    cr.setMobile(str.getString("mobile"));
                    //cr.setPosition(str.getString("position"));
                    cr.setPositionid(str.getInt("positionid"));
                    cr.setEmail(str.getString("email"));
                    cr.setRoleid(str.getLong("roleid"));
                    cr.setGroupid(str.getLong("groupid"));
                    cr.setUsername(str.getString("username"));
                    cr.setDivisionid(str.getLong("divisionid"));
                    cr.setStepid(str.getInt("stepid"));
                    cr.setUserpass(passwordEncoder.encode(str.getString("userpass")));
                    cr.setIspublic(1);
                    if (str.getBoolean("isactive")) {
                        cr.setIsactive(true);
                    }
                    dao.PeaceCrud(cr, domainName, "save", (long) 0, 0, 0, null);
                    LnkOffRole rl = new LnkOffRole();
                    rl.setRoleid(str.getLong("roleid"));
                    rl.setUserid(cr.getId());
                    dao.PeaceCrud(rl, domainName, "save", (long) 0, 0, 0, null);
                } else {
                    dao.PeaceCrud(object, domainName, "save", (long) 0, 0, 0, null);
                }

            }
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String detail(HttpServletRequest req) throws JSONException {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");
                Gson gson = new Gson();
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(loguser.getSubLegalpersons());

            }
        } catch (Exception e) {
            logger.error(e);
        }
        return null;

    }

    @RequestMapping(value = "/lp/detail/{id}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String lpdetail(@PathVariable Integer id, HttpServletRequest req) throws JSONException {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                AnnualRegistration an = (AnnualRegistration) dao.getHQLResult("from AnnualRegistration t where t.id=" + id + "", "current");

                SubLegalpersons lp = (SubLegalpersons) dao.getHQLResult("from SubLegalpersons t where t.lpReg='" + an.getLpReg() + "'", "current");

                Gson gson = new Gson();

                ObjectMapper mapper = new ObjectMapper();
                //  return mapper.writeValueAsString(lp);

                LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.lpreg='" + lp.getLpReg() + "'", "current");
                return mapper.writeValueAsString(loguser.getSubLegalpersons());

                //return gson.toJson(loguser);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return null;

    }

    @RequestMapping(value = "/lpinfo", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String lpinfo(@RequestBody String data) throws JSONException {
        System.out.println(data);
        JSONObject obj = new JSONObject(data);
        SubAddLpInfo sa = new SubAddLpInfo();
        sa.setLpReg(obj.getString("lpreg"));
        sa.setReportingPersonName(obj.getString("name"));
        dao.PeaceCrud(sa, "SubAddLpInfo", "save", (long) 0, 0, 0, null);

        SubAddLpInfo salist = (SubAddLpInfo) dao.getHQLResult("from SubAddLpInfo t where t.holderId=" + sa.getHolderId() + "", "current");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(salist);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;


        // return "true";
    }

    @RequestMapping(value = "/save/note", method = RequestMethod.PUT, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String ajaxNote(@RequestBody String jsonString) throws JSONException {
        System.out.println(jsonString);
        JSONObject obj = new JSONObject(jsonString);
        LnkPlanNotes pn = new LnkPlanNotes();

        return "true";
    }

    @RequestMapping(value = "/form/config", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String ajaxconf(@RequestBody String jsonString) throws JSONException {
        System.out.println(jsonString);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");

            JSONObject obj = new JSONObject(jsonString);
            SubLicenses lp = (SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='" + obj.getInt("licenseNum") + "'", "current");
            lp.setWeekly(obj.getBoolean("weekly"));
            lp.setMonthly(obj.getBoolean("monthly"));
            lp.setPlan(obj.getBoolean("plan"));
            lp.setDivisionId(loguser.getDivisionid());
            lp.setReport(obj.getBoolean("report"));
            lp.setRedemptionplan(obj.getBoolean("redemptionplan"));
            lp.setRedemptionreport(obj.getBoolean("redemptionreport"));
            dao.PeaceCrud(lp, "SubLicenses", "save", obj.getLong("licenseNum"), 0, 0, "licenseNum");
        }
        return "true";
    }

    @RequestMapping(value = "/form/config/m", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String ajaxconfm(@RequestBody String jsonString) throws JSONException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");
            JSONObject obj = new JSONObject(jsonString);
            SubLicenses lp = (SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='" + obj.getInt("licenseNum") + "'", "current");
            lp.setWeekly(obj.getBoolean("weekly"));
            lp.setMonthly(obj.getBoolean("monthly"));
            lp.setPlan(obj.getBoolean("plan"));
            lp.setReport(obj.getBoolean("report"));
            lp.setRedemptionplan(obj.getBoolean("redemptionplan"));
            lp.setHaiguulreport(obj.getBoolean("haiguulreport"));
            lp.setLplan(obj.getBoolean("lplan"));
            lp.setLreport(obj.getBoolean("lreport"));
            lp.setFtime(obj.getBoolean("ftime"));
            lp.setConfigured(0);
            lp.setConfigureddate(new Date());
            lp.setDivisionId(loguser.getDivisionid());
            lp.setRedemptionreport(obj.getBoolean("redemptionreport"));
            dao.PeaceCrud(lp, "SubLicenses", "save", obj.getLong("licenseNum"), 0, 0, "licenseNum");
        }
        return "true";
    }

    @RequestMapping(value = "/form/config/h", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String ajaxconfh(@RequestBody String jsonString) throws JSONException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {

            UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");

            System.out.println(jsonString);
            JSONObject obj = new JSONObject(jsonString);
            SubLicenses lp = (SubLicenses) dao.getHQLResult("from SubLicenses t where t.licenseNum='" + obj.getInt("licenseNum") + "'", "current");
            lp.setWeekly(obj.getBoolean("weekly"));
            lp.setMonthly(obj.getBoolean("monthly"));
            lp.setPlan(obj.getBoolean("plan"));
            lp.setReport(obj.getBoolean("report"));
            lp.setLplan(obj.getBoolean("lplan"));
            lp.setLreport(obj.getBoolean("lreport"));
            lp.setFtime(obj.getBoolean("ftime"));
            lp.setConfigured(1);
            lp.setDivisionId(loguser.getDivisionid());
            lp.setRedemptionplan(obj.getBoolean("redemptionplan"));
            lp.setRedemptionreport(obj.getBoolean("redemptionreport"));
            lp.setConfigureddate(new Date());
            dao.PeaceCrud(lp, "SubLicenses", "save", obj.getLong("licenseNum"), 0, 0, "licenseNum");

            List<com.peace.users.model.mram.RegReportReq> rq = (List<com.peace.users.model.mram.RegReportReq>) dao.getHQLResult("from RegReportReq t where t.bundledLicenseNum='" + obj.getInt("licenseNum") + "'", "list");

            if (rq.size() > 0) {
                com.peace.users.model.mram.RegReportReq qqq = rq.get(0);
                qqq.setLictype(lp.getLicTypeId());
                qqq.setBundledLicenseNum(lp.getLicenseNum());
                qqq.setAreaName(lp.getAreaNameMon());
                qqq.setLpReg(lp.getLpReg());
                qqq.setIsactive(true);
                qqq.setAddBunLicenseNum(String.valueOf(lp.getLicenseNum()));
                qqq.setDivisionId(loguser.getDivisionid());
                Date d1 = new Date();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
                String formattedDate = df.format(d1);
                qqq.setLatestChangeDateTime(formattedDate);
                qqq.setWk(0);
                qqq.setLicenseXB(lp.getLicenseXB());
                //qqq.setGroupid(0);
                String str = lp.getLicenseXB().substring(0, Math.min(lp.getLicenseXB().length(), 2));

                if (str.equalsIgnoreCase("mv")) {
                    qqq.setMv(0);
                } else if (str.equalsIgnoreCase("xv")) {
                    qqq.setMv(2);
                } else {
                    qqq.setMv(1);
                }


                dao.PeaceCrud(qqq, "RegReportReq", "update", (long) qqq.getId(), 0, 0, null);
            } else {
                com.peace.users.model.mram.RegReportReq qqq = new com.peace.users.model.mram.RegReportReq();
                qqq.setLictype(lp.getLicTypeId());
                qqq.setBundledLicenseNum(lp.getLicenseNum());
                qqq.setAreaName(lp.getAreaNameMon());
                qqq.setLpReg(lp.getLpReg());
                qqq.setIsactive(true);
                //qqq.setAddBunLicenseNum(String.valueOf(lp.getLicenseNum()));
                Date d1 = new Date();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
                String formattedDate = df.format(d1);
                qqq.setDivisionId(loguser.getDivisionid());
                qqq.setLatestChangeDateTime(formattedDate);
                qqq.setWk(0);
                qqq.setGroupid(0);
                String str = lp.getLicenseXB().substring(0, Math.min(lp.getLicenseXB().length(), 2));

                if (str.equalsIgnoreCase("mv")) {
                    qqq.setMv(0);
                } else if (str.equalsIgnoreCase("xv")) {
                    qqq.setMv(2);
                } else {
                    qqq.setMv(1);
                }
                qqq.setLicenseXB(lp.getLicenseXB());
                dao.PeaceCrud(qqq, "RegReportReq", "save", (long) 0, 0, 0, null);
            }
        }
        return "true";
    }

    @RequestMapping(value = "/useredit", method = RequestMethod.PUT, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String ajaxuseredit(@RequestBody String jsonString) throws JSONException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            JSONObject obj = new JSONObject(jsonString);
            SubLegalpersons lp = (SubLegalpersons) dao.getHQLResult("from SubLegalpersons t where t.lpReg='" + obj.getString("lpReg") + "'", "current");
            lp.setFamName(obj.getString("famName"));
            lp.setGivName(obj.getString("givName"));
            lp.setFamNameL1(obj.getString("famNameL1"));
            lp.setGivNameL1(obj.getString("givNameL1"));

            if (!obj.isNull("phone")) {
                lp.setPhone(obj.getString("phone"));
            }
            // lp.setPersonName(obj.getString("personName"));
            // lp.setMobile(String.valueOf(obj.getInt("mobile")));

            if (!obj.isNull("city")) {
                lp.setCity(obj.getString("city"));
            }

            if (!obj.isNull("cityL1")) {
                lp.setCityL1(obj.getString("cityL1"));
            }

            if (!obj.isNull("email")) {
                lp.setEmail(obj.getString("email"));
            }
            if (!obj.isNull("disctrict")) {
                lp.setDisctrict(obj.getString("disctrict"));
            }
            if (!obj.isNull("disctrictL1")) {
                lp.setDisctrictL1(obj.getString("disctrictL1"));
            }
            if (!obj.isNull("khoroo")) {
                lp.setKhoroo(obj.getString("khoroo"));
            }
            if (!obj.isNull("khorooL1")) {
                lp.setKhorooL1(obj.getString("khorooL1"));
            }
            if (!obj.isNull("street")) {
                lp.setStreet(obj.getString("street"));
            }
            if (!obj.isNull("streetL1")) {
                lp.setStreetL1(obj.getString("streetL1"));
            }
            if (!obj.isNull("postbox")) {
                lp.setPostbox(obj.getString("postbox"));
            }
            if (!obj.isNull("gengineer")) {
                lp.setGENGINEER(obj.getString("gengineer"));
            }
            if (!obj.isNull("gengineermail")) {
                lp.setGENGINEERMAIL(obj.getString("gengineermail"));
            }
            if (!obj.isNull("gengineerphone")) {
                lp.setGENGINEERPHONE(String.valueOf(obj.getString("gengineerphone")));
            }
            if (!obj.isNull("geologist")) {
                lp.setGEOLOGIST(obj.getString("geologist"));
            }
            if (!obj.isNull("geologistmail")) {
                lp.setGEOLOGISTMAIL(obj.getString("geologistmail"));
            }
            if (!obj.isNull("geologistphone")) {
                lp.setGEOLOGISTPHONE(String.valueOf(obj.getString("geologistphone")));
            }
            if (!obj.isNull("msurveyor")) {
                lp.setMSURVEYOR(obj.getString("msurveyor"));
            }
            if (!obj.isNull("msurveyoremail")) {
                lp.setMSURVEYOREMAIL(obj.getString("msurveyoremail"));
            }
            if (!obj.isNull("msurveyoremail")) {
                lp.setMSURVEYOREMAIL(obj.getString("msurveyoremail"));
            }
            if (!obj.isNull("msurveyorphone")) {
                lp.setMSURVEYORPHONE(String.valueOf(obj.getString("msurveyorphone")));
            }
            if (!obj.isNull("accountant")) {
                lp.setACCOUNTANT(obj.getString("accountant"));
            }
            if (!obj.isNull("accountantemail")) {
                lp.setACCOUNTANTEMAIL(obj.getString("accountantemail"));
            }
            if (!obj.isNull("accountantphone")) {
                lp.setACCOUNTANTPHONE(String.valueOf(obj.getString("accountantphone")));
            }
            if (!obj.isNull("economist")) {
                lp.setECONOMIST(obj.getString("economist"));
            }
            if (!obj.isNull("economistemail")) {
                lp.setECONOMISTEMAIL(obj.getString("economistemail"));
            }
            if (!obj.isNull("economistphone")) {
                lp.setECONOMISTPHONE(String.valueOf(obj.getString("economistphone")));
            }
            if (!obj.isNull("keyman")) {
                lp.setKEYMAN(obj.getString("keyman"));
            }
            if (!obj.isNull("keymanemail")) {
                lp.setKEYMANEMAIL(obj.getString("keymanemail"));
            }
            if (!obj.isNull("keymanphone")) {
                lp.setKEYMANPHONE(String.valueOf(obj.getString("keymanphone")));
            }
            if (!obj.isNull("minehead")) {
                lp.setMinehead(obj.getString("minehead"));
            }
            if (!obj.isNull("mineemail")) {
                lp.setMineemail(obj.getString("mineemail"));
            }
            if (!obj.isNull("minephone")) {
                lp.setMinephone(String.valueOf(obj.getString("minephone")));
            }

            dao.PeaceCrud(lp, "SubLegalpersons", "save", obj.getLong("id"), 0, 0, null);

            if (!obj.isNull("userpass")) {
                LutUsers us = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + lp.getLpReg() + "'", "current");
                us.setUserpass(passwordEncoder.encode(obj.getString("userpass")));
                dao.PeaceCrud(us, "User", "update", (long) us.getId(), 0, 0, null);
            }


        }
        return "true";
    }

    @RequestMapping(value = "/rolesubmit", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String ajaxsubmit(@RequestBody String jsonString) throws JSONException {
        System.out.println(jsonString);
        JSONArray rs = new JSONArray(jsonString);

        for (int i = 0; i < rs.length(); i++) {
            String str = rs.get(i).toString();
            JSONObject batch = new JSONObject(str);

            String rolename = batch.getString("definition");
            String roledefinition = batch.getString("rolename");
            int access = batch.getInt("access");
            int roleid = batch.getInt("roleid");
            System.out.println("sss" + roleid);
            if (roleid == 0) {
                LutRole object = new LutRole();
                object.setRoleNameEng(roledefinition);
                object.setRoleNameMon(rolename);
                object.setAccess(access);
                dao.PeaceCrud(object, "Category", "save", (long) 0, 0, 0, null);

                LnkRoleAuth auth = new LnkRoleAuth();
                auth.setAuthName(rolename);
                auth.setRoleid(object.getId());

                dao.PeaceCrud(auth, "Category", "save", (long) 0, 0, 0, null);

                JSONArray mn = (JSONArray) batch.get("ilist");

                if (mn.length() > 0) {
                    for (int j = 0; j < mn.length(); j++) {
                        JSONObject itr = mn.getJSONObject(j);
                        int menuid = Integer.parseInt(itr.get("menuid").toString());
                        System.out.println("menuid" + menuid);
                        int create = 0;
                        int read = 0;
                        int update = 0;
                        int destroy = 0;
                        int export = 0;
                        JSONArray ids = (JSONArray) itr.get("ids");
                        if (ids.length() > 0) {
                            for (int c = 0; c < ids.length(); c++) {
                                int r = (int) ids.get(c);
                                switch (r) {
                                    case 1:
                                        create = 1;
                                        break;
                                    case 2:
                                        read = 1;
                                        break;
                                    case 3:
                                        update = 1;
                                        break;
                                    case 4:
                                        destroy = 1;
                                        break;
                                    case 5:
                                        export = 1;
                                        break;
                                }
                            }
                        }
                        LutMenu current = (LutMenu) dao.PeaceCrud(null, "LutMenu", "current", (long) menuid, 0, 0, "");

                        //	List<LutMenu> pc=(List<LutMenu>) dao.getHQLResult("from LutMenu t order by t.id", "list");

                        LnkMenuRole rmenu = new LnkMenuRole();
                        //rmenu.setLutMenu(current);
                        rmenu.setMenuid((long) menuid);
                        rmenu.setRoleid(object.getId());
                        rmenu.setRcreate((long) create);
                        rmenu.setRread((long) read);
                        rmenu.setRupdate((long) update);
                        rmenu.setRdelete((long) destroy);
                        rmenu.setRexport((long) export);
                        rmenu.setOrderid((long) current.getOrderid());
                        dao.PeaceCrud(rmenu, "Category", "save", (long) 0, 0, 0, null);

                    }

                }
                return "true";
            } else {
                System.out.println("enenen" + roleid);
                System.out.println("enenen" + roledefinition);
                System.out.println("enenen" + rolename);
                LutRole object1 = (LutRole) dao.getHQLResult("from LutRole t where t.id=" + roleid + "", "current");
                object1.setRoleNameEng(roledefinition);
                object1.setRoleNameMon(rolename);
                object1.setAccess(access);
                dao.PeaceCrud(object1, "Category", "update", (long) roleid, 0, 0, null);

                LnkRoleAuth auth = (LnkRoleAuth) dao.getHQLResult("from LnkRoleAuth t where t.roleid=" + roleid + "", "current");
                auth.setAuthName(rolename);
                auth.setRoleid(object1.getId());

                dao.PeaceCrud(auth, "Category", "update", (long) roleid, 0, 0, null);

                dao.PeaceCrud(null, "LnkMenuRole", "delete", (long) roleid, 0, 0, "roleid");

                JSONArray mn = (JSONArray) batch.get("ilist");

                if (mn.length() > 0) {
                    for (int j = 0; j < mn.length(); j++) {
                        JSONObject itr = mn.getJSONObject(j);
                        int menuid = Integer.parseInt(itr.get("menuid").toString());
                        System.out.println("menuid" + menuid);
                        int create = 0;
                        int read = 0;
                        int update = 0;
                        int destroy = 0;
                        int export = 0;
                        JSONArray ids = (JSONArray) itr.get("ids");
                        if (ids.length() > 0) {
                            for (int c = 0; c < ids.length(); c++) {
                                int r = (int) ids.get(c);
                                switch (r) {
                                    case 1:
                                        create = 1;
                                        break;
                                    case 2:
                                        read = 1;
                                        break;
                                    case 3:
                                        update = 1;
                                        break;
                                    case 4:
                                        destroy = 1;
                                        break;
                                    case 5:
                                        export = 1;
                                        break;
                                }
                            }
                        }
                        LutMenu mnu = (LutMenu) dao.getHQLResult("from LutMenu t where t.id=" + menuid + "", "current");
                        LnkMenuRole rmenu = new LnkMenuRole();
                        rmenu.setMenuid((long) menuid);
                        rmenu.setRoleid((long) roleid);
                        rmenu.setRcreate((long) create);
                        rmenu.setRread((long) read);
                        rmenu.setRupdate((long) update);
                        rmenu.setRdelete((long) destroy);
                        rmenu.setRexport((long) export);
                        rmenu.setOrderid((long) mnu.getOrderid());
                        dao.PeaceCrud(rmenu, "Category", "save", (long) 0, 0, 0, null);

                    }
                    return "true";
                }
            }


        }

        return "true";


    }

    @RequestMapping(value = "/userUpdate", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody
    String userUpdate(@RequestParam(value = "avatar_file", required = false) MultipartFile avatar_file, @RequestParam String givnamemon, @RequestParam String mobile, @RequestParam String givnameeng, @RequestParam String famnameeng, @RequestParam String famnamemon, @RequestParam Long id, HttpServletRequest req) throws JSONException {
        try {
            System.out.println("UserUpdate ::: " + id + "\t" + givnamemon + "\t" + mobile + "\t" + givnameeng + "\t" + famnameeng + "\t" + famnamemon);
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                LutUsers loguser = (LutUsers) dao.getHQLResult("from LutUsers t where t.username='" + userDetail.getUsername() + "'", "current");
                if ((loguser != null) && (loguser.getId().equals(id))) {
                    loguser.setFamnameeng(famnameeng);
                    loguser.setFamnamemon(famnamemon);
                    loguser.setGivnameeng(givnameeng);
                    loguser.setGivnamemon(givnamemon);
                    loguser.setMobile(mobile);
                    if (avatar_file != null) {
                        if (loguser.getAvatar() != null) {
                            String appPaths = req.getSession().getServletContext().getRealPath("");
                            String delPath = appPaths + loguser.getAvatar();
                            File file = new File(delPath);
                            file.delete();
                        }
                        String appPath = req.getSession().getServletContext().getRealPath("");

                        String SAVE_DIR = MramApplication.ROOT;

                        Date d1 = new Date();
                        SimpleDateFormat df = new SimpleDateFormat("MM-dd-YYYY");
                        String special = df.format(d1);

                        String path = appPath + "/" + SAVE_DIR + "/" + loguser.getLpreg() + "/" + special;

                        File folder = new File(path);
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }

                        String fpath = appPath + "/" + SAVE_DIR + "/" + loguser.getLpreg() + "/" + special + "/" + avatar_file.getOriginalFilename();
                        File logoorgpath = new File(fpath);

                        if (!logoorgpath.exists()) {
                            avatar_file.transferTo(logoorgpath);
                        }
                        String path1 = "/" + SAVE_DIR + "/" + loguser.getLpreg() + "/" + special + "/" + avatar_file.getOriginalFilename();
                        loguser.setAvatar(path1);
                    }
                    dao.PeaceCrud(loguser, "LutUsers", "save", (long) 0, 0, 0, null);
                    return "true";
                } else {
                    return "ID_ERROR";
                }

            } else {
                return "SESSION_ERROR";
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

}
