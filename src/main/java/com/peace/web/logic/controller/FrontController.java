package com.peace.web.logic.controller;

import com.peace.users.dao.UserDao;
import com.peace.users.model.mram.LutNews;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import org.springframework.util.StringUtils;
/**
 * Created by suld on 4/9/2017.
 */
@Controller
public class FrontController {
    @Autowired
    private UserDao dao;

    @RequestMapping(value={"/"}, method = RequestMethod.GET)
    public String getIndex(Model model){
        List<LutNews> news = (List<LutNews>) dao.getHQLResult("from LutNews t order by t.id desc", "list");
        model.addAttribute("path", "index");
        model.addAttribute("news", news);
       // model.addAttribute("currentUser", servicesUser.getCurrentUser());
        return "index";
    }
    
/*    @RequestMapping(value={"/admin/**"}, method = RequestMethod.GET)
    public String getAdmin(Model model){
        model.addAttribute("path", "index");
       // model.addAttribute("currentUser", servicesUser.getCurrentUser());
        return "admin";
    }
*/
    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public String updatePage(HttpServletRequest request) {

		ModelAndView model = new ModelAndView();

		if (isRememberMeAuthenticated()) {
			//send login for update
			//setRememberMeTargetUrlToSession(request);
			//model.addObject("loginUpdate", true);
			//model.setViewName("/login");

		} else {
			//model.setViewName("index");
		}

		return "admin";

	}
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
	  Model model, 
          HttpServletRequest request) {

		if (error != null) {
			model.addAttribute("error", "Invalid username and password!");

			//login form for update page
                        //if login error, get the targetUrl from session again.
			String targetUrl = getRememberMeTargetUrlFromSession(request);
			System.out.println(targetUrl);
			if(StringUtils.hasText(targetUrl)){
				model.addAttribute("targetUrl", targetUrl);
				model.addAttribute("loginUpdate", true);
			}

		}

		/*if (logout != null) {
			model.addAttribute("msg", "You've been logged out successfully.");
		}*/
		 model.addAttribute("path", "sign-in");

		return "index";

	}

    @RequestMapping(value = "/division/{id}", method = RequestMethod.GET)
    public String login(@PathVariable Long id, Model model,HttpServletRequest request) {
        model.addAttribute("path", "stat"+id);
        return "index";
    }
    
    
    /**
	 * Check if user is login by remember me cookie, refer
	 * org.springframework.security.authentication.AuthenticationTrustResolverImpl
	 */
	private boolean isRememberMeAuthenticated() {

		Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}

		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
	}

	/**
	 * save targetURL in session
	 */
	private void setRememberMeTargetUrlToSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.setAttribute("targetUrl", "/admin");
		}
	}

	/**
	 * get targetURL from session
	 */
	private String getRememberMeTargetUrlFromSession(HttpServletRequest request){
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if(session!=null){
			targetUrl = session.getAttribute("targetUrl")==null?""
                             :session.getAttribute("targetUrl").toString();
		}
		return targetUrl;
	}

    
    

    @RequestMapping(value={"/search"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String getSearchResult(HttpServletRequest request, Model model, @RequestParam(required = false, value = "reporttype") Long reporttype, @RequestParam(required = false, value = "q") String q){
        if (q != null && reporttype!=null && q.length() >= 3){
            String querySql = "SELECT A .LICENSEXB, A .LPNAME, A .LP_REG, A .REPORTTYPE, A .REPORTYEAR, A.XTYPE, (SELECT LUT_LICTYPE.LICTYPENAMEMON FROM LUT_LICTYPE WHERE A.LICTYPE = LUT_LICTYPE.LICTYPEID AND ROWNUM = 1), A.DIVISIONID, (SELECT SUB_LICENSES.LOCATIONAIMAG FROM SUB_LICENSES WHERE A.LICENSENUM = SUB_LICENSES.LICENSENUM AND ROWNUM = 1), (SELECT SUB_LICENSES.LOCATIONSOUM FROM SUB_LICENSES WHERE A.LICENSENUM = SUB_LICENSES.LICENSENUM AND ROWNUM = 1), A .REPSTATUSID,A .REPSTEPID, A .REJECTSTEP FROM ANNUAL_REGISTRATION A WHERE (LOWER(A .LICENSENUM) LIKE LOWER('%"+q+"%') OR LOWER(A .LICENSEXB) LIKE LOWER('%"+q+"%') OR LOWER(A .LPNAME) LIKE LOWER('%"+q+"%') OR LOWER(A .LP_REG) LIKE LOWER('%"+q+"%') ) AND A .REPSTATUSID IN (1, 2, 7, 3) ";

            if (reporttype == 3 || reporttype == 4){
                querySql = querySql + " AND A.REPORTTYPE = " + reporttype;
            }

            querySql = querySql + " ORDER BY A.LPNAME ASC, A.DIVISIONID ASC";
            List<Object[]> resultObj = dao.getNativeSQLResult(querySql, "list");
            model.addAttribute("results", resultObj);
        }

        model.addAttribute("path", "search");
        model.addAttribute("q", q);
        model.addAttribute("reporttype", reporttype);
        model.addAttribute("currentUser", "suld");
        return "index";
    }


}
