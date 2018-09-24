package com.peace.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.peace.users.dao.UserDao;
import com.peace.users.model.mram.LnkMenuRole;
import com.peace.users.model.mram.LutMenu;
import com.peace.users.model.mram.LutUsers;



@Component
public class Services {
	

	@Autowired
    private UserDao dao;
	
	
/*	@CachePut(value="customers", key="#id")
	public Customer putCustomer(String firstName, long id){
		Customer cust = store.get(id);
		cust.setFirstName(firstName);
		return cust;
	}*/
	
	//@Cacheable(value="customers", key="#roles")
	public JSONObject getMjson(String roles, boolean superuser,LutUsers loguser,UserDetails userdet){
		System.out.println("Service processing...");
	
		JSONObject robj = new JSONObject();
		JSONArray result = new JSONArray();
		Collection<Object> roleset=null;
		if(superuser){
			List<LutMenu> rs=(List<LutMenu>) dao.getHQLResult("from LutMenu t where t.parentid=0 order by t.orderid asc", "list");
	    	if(rs.size()>0){
	    		for(int i=0;i<rs.size();i++){
	        		Map<String,Object> wmap=new HashMap<String, Object>();        	
	        		wmap.put("id", rs.get(i).getId());
	        		wmap.put("title", rs.get(i).getNamemn());
	        		
	        		wmap.put("icon", rs.get(i).getIcon());	        		
	        		
	        		List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>(); 
	        		
	        		if(rs.get(i).getChilds().size()>0){
	        			List<LutMenu> chi=rs.get(i).getChilds();
	        			
	        			for(int j=0;j<chi.size();j++){
	        				LutMenu rsa=chi.get(j);				        				
	        				
	        				List<LnkMenuRole> lk=rsa.getLnkMenuRoles();
	        				int a =lk.size();
	        				if(lk.size()>1){
	        					a=1;
	        				}
	        				for(int l=0; l<a;l++){
	        					LnkMenuRole rl=lk.get(l);	
	        					if(rl.getRread()>0){
	        						Map<String,Object> child=new HashMap<String, Object>();  	        			
					        		child.put("title", rsa.getNamemn());
					        		
					        		List<LutMenu> third=rsa.getChilds();
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
					        			child.put("link",  rsa.getStateurl());	
					        		}
					        		childs.add(child);
	        					}
	        					
	        				}
	        			/*	if(rsa.getChilds().size()==0){
	        					Map<String,Object> child=new HashMap<String, Object>();  	        			
				        		child.put("title", rsa.getNamemn());
				        		child.put("link",  rsa.getStateurl());		        		
				        		childs.add(child);
	        				}*/
	        			}	        			
		        		
	        		}	
	        		else{
	        			wmap.put("link", rs.get(i).getStateurl());
	        		}
	        		wmap.put("submenu", childs);
	        		result.put(wmap);      
	        	}
	    	}
	    	JSONArray ulist = new JSONArray();
			JSONObject wmap = new JSONObject();
    		wmap.put("role", "ROLE_SUPER");			        		
    		wmap.put("gname", "");
    		wmap.put("username", userdet.getUsername());
    		ulist.put(wmap);  
    		robj.put("ujson", ulist);
    		robj.put("mjson", result);
		}else{
			
			System.out.println("@@@"+roles.substring(1,roles.length()));
			
			roleset=(Collection<Object>) dao.getHQLResult("select c.id, c.namemn, c.parentid, c.stateurl, c.icon from LutMenu c, LnkMenuRole t where t.roleid ="+roles+" and c.id=t.menuid and t.rread=1 order by c.orderid asc", "list");
			Iterator<Object> ldata =roleset.iterator(); 
			while (ldata.hasNext()) {
				Object[] curr = (Object[]) ldata.next();	
				JSONObject wmap=new JSONObject();
				if(curr[2].toString().equalsIgnoreCase("0")){
					int inp=0;
	        		for(int i=0;i<result.length();i++){
	        			JSONObject it = (JSONObject) result.get(i);
	        		    if(it.getString("id").equals(curr[0].toString())){
	        		    	inp=inp+1;
	        		    }
	        		}
					wmap.put("id", curr[0].toString());
	        		wmap.put("title", curr[1].toString());		        		
	        		wmap.put("icon", curr[4].toString());	
	        		JSONArray childs = new JSONArray();  
	        		int count=0;
	        		Iterator<Object> fldata =roleset.iterator(); 
	        		while (fldata.hasNext()) {
						Object[] fcurr = (Object[]) fldata.next();
						if(fcurr[2] != null && curr[0].toString().equalsIgnoreCase(fcurr[2].toString())){
							JSONObject fchild=new JSONObject();
							count=count+1;
							fchild.put("title", fcurr[1].toString());
							
							int chicount=0;
							for(int i=0;i<childs.length();i++){
			        			JSONObject it = (JSONObject) childs.get(i);
			        			if(it.getString("title").equalsIgnoreCase(fcurr[1].toString())){
			        				chicount=chicount+1;
			        			}
							}
							
							JSONArray tchilds = new JSONArray(); 	
							int tcount=0;
							Iterator<Object> tldata =roleset.iterator(); 
			        		while (tldata.hasNext()) {
								Object[] tcurr = (Object[]) tldata.next();
								if(tcurr[2] != null && fcurr[0].toString().equalsIgnoreCase(tcurr[2].toString())){
									JSONObject tchild=new JSONObject();
									tcount=tcount+1;
									tchild.put("title", tcurr[1].toString());
									tchild.put("link",  tcurr[3].toString());	
									tchilds.put(tchild);
								}
			        		}
							
		        			fchild.put("submenu",  tchilds);	
							if(tcount==0){
								fchild.put("link",  fcurr[3].toString());	
							}
							if(chicount==0){
								childs.put(fchild);	
			        		}								
						}
	        		}
	        		
	        		wmap.put("submenu", childs);
	        		if(count==0){
	        			wmap.put("link", curr[3].toString());
	        		}
	        		if(inp==0){		        			
	        			result.put(wmap);  
	        		}	        		   
				}
				else{
					wmap.put("link", curr[3].toString());
				}
			}
			System.out.println("done"+result.toString());
    		robj.put("mjson", result);
		}
		
		return robj;
	}
	
	/*public JSONObject getUjson(String roles, boolean superuser,LutUser loguser,UserDetails userdet){
		System.out.println("Service user processing...");
	
		JSONObject robj = new JSONObject();
		JSONArray result = new JSONArray();
		Collection<Object> roleset=null;
		if(superuser){
	    	JSONArray ulist = new JSONArray();
			JSONObject wmap = new JSONObject();
    		//wmap.put("id",loguser.getId());
    		wmap.put("role", "ROLE_SUPER");			        		
    		wmap.put("gname", "");
    		//wmap.put("position",loguser.getPositionid());
    		wmap.put("username", userdet.getUsername());
    	//	wmap.put("lpname", loguser.getLutDepartment().getDepartmentname());
    	//	wmap.put("depid", loguser.getDepartmentid());
    		ulist.put(wmap);  
    		robj.put("ujson", ulist);
    		robj.put("mjson", result);
		}else{
			JSONArray ulist = new JSONArray();
			JSONObject wmap = new JSONObject();
    		wmap.put("id",loguser.getId());
    		wmap.put("role", loguser.getLnkUserroles().get(0).getLutRole().getRoleauth());			        		
    		wmap.put("gname", loguser.getGivenname());
    		wmap.put("position",loguser.getPositionid());
    		wmap.put("username", loguser.getFamilyname().substring(0, 1)+"."+loguser.getGivenname());
    		wmap.put("lpname", loguser.getLutDepartment().getDepartmentname());
    		wmap.put("depid", loguser.getDepartmentid());
    		ulist.put(wmap);  
    		robj.put("ujson", ulist);
    		robj.put("mjson", result);
		}
		
		return robj;
	}*/
	@CacheEvict(value = "customers", key = "#id")
	public void evict(long id){
	}
}