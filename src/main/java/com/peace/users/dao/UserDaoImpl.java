package com.peace.users.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import com.peace.users.model.mram.AnnualRegistration;
import com.peace.users.model.mram.LnkPlanLogs;
import org.apache.log4j.Logger;
/*import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;*/


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.peace.users.model.mram.LutUsers;
import com.peace.web.service.ServiceController;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;
    private Object sheet;
    final static Logger logger = Logger.getLogger(ServiceController.class);

    @SuppressWarnings("unchecked")
    public LutUsers findByUserName(String username) {

        List<LutUsers> users = new ArrayList<LutUsers>();
        users = sessionFactory.getCurrentSession().createQuery("from LutUsers where username=?").setParameter(0, username).list();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public List<Object[]> getNativeSQLResult(String queryStr, String type) {
        try {
            if ("list".equals(type)) {
                Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
                try {
                    return query.list();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Object saveOrUpdate(Object obj) {
        try {
            sessionFactory.getCurrentSession().update(obj);
            return true;
        } catch (ConstraintViolationException err) {
            err.printStackTrace();
            return false;
        }

    }

    @Override
    public Object findAll(String domain, String whereclause) {
        Query query = null;
        if (whereclause != null) {
            query = sessionFactory.getCurrentSession().createQuery(whereclause);
        } else {
            query = sessionFactory.getCurrentSession().createQuery("from " + domain + " objlist  order by objlist.id desc ");
        }

        List<Object> robj = query.list();
        query = null;
        return robj;

    }

    @Override
    public Object findById(String domain, long id, String whereclause) {
        Query query = null;
        query = sessionFactory.getCurrentSession().createQuery("from " + domain + " t where t.id=:id");
        query.setParameter("id", id);
        Object robj = query.list().get(0);
        return robj;

    }

    @Override
    public void deleteById(String domain, long obj_id, String whereclause) {
        Query query = null;
        if (whereclause != null) {
            query = sessionFactory.getCurrentSession().createQuery("delete from " + domain + "  t where t." + whereclause + "=:obj_id");
            query.setParameter("obj_id", obj_id);

        } else {
            query = sessionFactory.getCurrentSession().createQuery("delete from " + domain + "  t where t.id=:obj_id");
            query.setParameter("obj_id", obj_id);
        }
        //List list = query.list();
        int qresult = query.executeUpdate();
        //return qresult;

    }


    @Override
    public Object getHQLResult(String hqlString, String returnType) {
        Query query = sessionFactory.getCurrentSession().createQuery(hqlString);
        System.out.println(query);
        if ("list".equals(returnType)) {
            return query.list();
        } else if ("current".equals(returnType)) {
            if (query.list().size() > 0) {
                if (query.list().size() == 1) {
                    return query.list().get(0);
                } else {
                    return null;
                }
            }

        } else if ("count".equals(returnType)) {
            int resultInt = ((Long) query.uniqueResult()).intValue();
            return resultInt;
        } else {
            return null;
        }

        return null;
    }

    public List<Object> kendojson(String request, String tablename) {

        try {

            Gson gson = new Gson();
            int skip = 0;
            int take = 2;
            int page = 0;
            String field = "";
            String order = "";
            String dir = "";
            JSONArray sort = null;
            String group = "";
            JSONObject filter = null;
            String org = "";
            String custom = "";
            String customJoins = "";
            String nat = "";
            String flquery = "";
            String isspecial = "";
            System.out.println("sss" + request + tablename);
            JSONObject req = new JSONObject(request);
            skip = req.getInt("skip");

            page = req.getInt("page");
            if (req.has("sort")) {
                sort = req.getJSONArray("sort");
            }
            if (req.has("take")) {
                take = req.getInt("take");
            }
            if (req.has("group")) {
                group = req.getString("group");
            }
            if (req.has("filter")) {

                if (!req.isNull("filter")) {
                    filter = req.getJSONObject("filter");
                }

            }

            if (req.has("custom")) {
                custom = req.getString("custom");
            }

            if (req.has("customJoins")) {
                customJoins = req.getString("customJoins");
            }

            if (req.has("field")) {
                field = req.getString("field");
            }
            if (req.has("dir")) {
                dir = req.getString("dir");
            }
            if (custom.length() > 0) {
                flquery = custom;
            }
            if (customJoins.length() > 0) {
                flquery = customJoins;
            }
            if (req.has("native")) {
                nat = req.getString("native");
            }
            if (req.has("isspecial")) {
                isspecial = req.getString("isspecial");
            }

            String multiOrde = "";

            if (sort != null) {
                JSONArray arr = sort;
                for (int i = 0; i < arr.length(); i++) {
                    String str = arr.get(i).toString();
                    JSONObject srt = new JSONObject(str);
                    if (srt.isNull("field")) {
                        field = "";
                    } else {
                        field = srt.getString("field");
                        multiOrde = multiOrde + " " + field;

                    }
                    if (srt.isNull("dir")) {
                        dir = "";
                    } else {
                        dir = srt.getString("dir");
                        multiOrde = multiOrde + " " + dir + ",";
                    }
                }

            }
            if (multiOrde.length() > 0) {
                System.out.println("$$$$ " + multiOrde.substring(0, multiOrde.length() - 1));
            }
            String groupfield = "";
            String groupdir = "";
            if (group.length() > 0) {
                JSONArray arr = new JSONArray(group);
                for (int i = 0; i < arr.length(); i++) {
                    String str = arr.get(i).toString();
                    JSONObject srt = new JSONObject(str);
                    if (srt.isNull("field")) {
                        groupfield = "";
                    } else {
                        groupfield = srt.getString("field");
                    }
                    if (srt.isNull("dir")) {
                        groupdir = "";
                    } else {
                        groupdir = srt.getString("dir");
                    }
                }

            }
            String filterfield = "";
            String operator = "";
            String value = "";


            if (filter != null) {

                JSONObject fltr = filter;

                String logic = fltr.getString("logic");

                JSONArray arr = fltr.getJSONArray("filters");
                for (int i = 0; i < arr.length(); i++) {
                    String str = arr.get(i).toString();
                    JSONObject srt = new JSONObject(str);
                    if (srt.isNull("field")) {
                        filterfield = "";
                    } else {
                        filterfield = srt.getString("field");
                    }
                    if (srt.isNull("operator")) {
                        operator = "";
                    } else {
                        operator = srt.getString("operator");
                    }
                    if (srt.isNull("value")) {
                        value = "";
                    } else {
                        if (srt.get("value") instanceof Boolean) {
                            value = (srt.getBoolean("value") ? "1" : "0");
                        } else {
                            value = String.valueOf(srt.get("value"));
                        }
                    }
                    if (i > 0) {

                        switch (operator) {
                            case "startswith":
                                flquery = flquery + " " + logic + " lower(" + filterfield + ") LIKE lower('" + value + "%')";
                                break;
                            case "contains":
                                flquery = flquery + " " + logic + " lower(" + filterfield + ") LIKE lower('%" + value + "%')";
                                break;
                            case "doesnotcontain":
                                flquery = flquery + " " + logic + " lower(" + filterfield + ") NOT LIKE lower('%" + value + "%')";
                                break;
                            case "endswith":
                                flquery = flquery + " " + logic + " lower(" + filterfield + ") LIKE lower('%" + value + "')";
                                break;
                            case "neq":
                                flquery = flquery + " " + logic + " lower(" + filterfield + ") != lower('" + value + "')";
                                break;
                            case "eq":
                                flquery = flquery + " " + logic + " lower(" + filterfield + ") = lower('" + value + "')";
                                break;
                        }
                    } else {
                        if (custom.length() > 0) {
                            System.out.println("enduu");
                            if (custom.contains("orgid1")) {

                                System.out.println("enduu 111");
                                switch (operator) {
                                    case "startswith":
                                        flquery = " Where lower(" + filterfield + ") LIKE lower('" + value + "%')";
                                        break;
                                    case "contains":
                                        flquery = " Where lower(" + filterfield + ") LIKE lower('%" + value + "%')";
                                        break;
                                    case "doesnotcontain":
                                        flquery = " Where lower(" + filterfield + ") NOT LIKE lower('%" + value + "%')";
                                        break;
                                    case "endswith":
                                        flquery = " Where lower(" + filterfield + ") LIKE lower('%" + value + "')";
                                        break;
                                    case "neq":
                                        flquery = " Where lower(" + filterfield + ") != lower('" + value + "')";
                                        break;
                                    case "eq":
                                        flquery = " Where lower(" + filterfield + ") = lower('" + value + "')";
                                        break;
                                }
                            } else {

                                System.out.println("enduu 222 ");
                                switch (operator) {
                                    case "startswith":
                                        flquery = " " + custom + " and lower(" + filterfield + ") LIKE lower('" + value + "%')";
                                        break;
                                    case "contains":
                                        flquery = " " + custom + " and lower(" + filterfield + ") LIKE lower('%" + value + "%')";
                                        break;
                                    case "doesnotcontain":
                                        flquery = " " + custom + " and lower(" + filterfield + ") NOT LIKE lower('%" + value + "%')";
                                        break;
                                    case "endswith":
                                        flquery = " " + custom + " and lower(" + filterfield + ") LIKE lower('%" + value + "')";
                                        break;
                                    case "neq":
                                        flquery = " " + custom + " and lower(" + filterfield + ") != lower('" + value + "')";
                                        break;
                                    case "eq":
                                        flquery = " " + custom + " and lower(" + filterfield + ") = lower('" + value + "')";
                                        break;
                                }
                            }
                        } else {
                            switch (operator) {
                                case "startswith":
                                    flquery = " Where lower(" + filterfield + ") LIKE lower('" + value + "%')";
                                    break;
                                case "contains":
                                    flquery = " Where lower(" + filterfield + ") LIKE lower('%" + value + "%')";
                                    break;
                                case "doesnotcontain":
                                    flquery = " Where lower(" + filterfield + ") NOT LIKE lower('%" + value + "%')";
                                    break;
                                case "endswith":
                                    flquery = " Where lower(" + filterfield + ") LIKE lower('%" + value + "')";
                                    break;
                                case "neq":
                                    flquery = " Where lower(" + filterfield + ") != lower('" + value + "')";
                                    break;
                                case "eq":
                                    flquery = " Where lower(" + filterfield + ") = lower('" + value + "')";
                                    break;
                            }
                        }

                    }

                }

            }
			
		/*	if(filter!=null){
				
				JSONObject fltr= filter;		
				
				String logic=fltr.getString("logic");
				//String filters=fltr.getString("filters");
			
				JSONArray arr= fltr.getJSONArray("filters");
				for(int i=0; i<arr.length();i++){
					String str=arr.get(i).toString();
					JSONObject srt= new JSONObject(str);
					if(srt.isNull("field")){
						filterfield="";	
					}
					else{
						filterfield=srt.getString("field");	
					}
					if(srt.isNull("operator")){
						operator="";
					}
					else{
						operator=srt.getString("operator");
					}
					if(srt.isNull("value")){
						value="";
					}
					else{
						value=String.valueOf(srt.get("value"));
					}
					if(i>0){
						
						switch(operator){
							case "startswith":flquery=flquery+  " "+logic+" "+filterfield+ " LIKE '"+value+"%'"; break;
							case "contains":flquery=flquery+  " "+logic+" "+filterfield+ " LIKE '%"+value+"%'"; break;
							case "doesnotcontain":flquery=flquery+  " "+logic+" "+filterfield+ " NOT LIKE '%"+value+"%'"; break;
							case "endswith":flquery=flquery+  " "+logic+" "+filterfield+ " LIKE '%"+value+"'"; break;
							case "neq":flquery=flquery+  " "+logic+" "+filterfield+ " != '"+value+"'"; break;
							case "eq":flquery=flquery+  " "+logic+" "+filterfield+ " = '"+value+"'"; break;
						}						
					}
					else{
						if(custom.length()>0){
							System.out.println("enduu");
							if(custom.contains("orgid1")){
								
								System.out.println("enduu 111");
								switch(operator){
									case "startswith":flquery=" Where "+filterfield+ " LIKE '"+value+"%'"; break;
									case "contains":flquery=" Where "+filterfield+ " LIKE '%"+value+"%'"; break;
									case "doesnotcontain":flquery=" Where "+filterfield+ " NOT LIKE '%"+value+"%'"; break;
									case "endswith":flquery=" Where "+filterfield+ " LIKE '%"+value+"'"; break;
									case "neq":flquery=" Where "+filterfield+ " != '"+value+"'"; break;
									case "eq":flquery=" Where "+filterfield+ " = '"+value+"'"; break;
								}								
							}
							else{
								
								System.out.println("enduu 222 "); 
								switch(operator){
									case "startswith":flquery=" "+custom+" and "+filterfield+ " LIKE '"+value+"%'"; break;
									case "contains":flquery=" "+custom+" and "+filterfield+ " LIKE '%"+value+"%'"; break;
									case "doesnotcontain":flquery=" "+custom+" and "+filterfield+ " NOT LIKE '%"+value+"%'"; break;
									case "endswith":flquery=" "+custom+" and "+filterfield+ " LIKE '%"+value+"'"; break;
									case "neq":flquery=" "+custom+" and "+filterfield+ " != '"+value+"'"; break;
									case "eq":flquery=" "+custom+" and "+filterfield+ " = '"+value+"'"; break;
								}
							}							
						}
						else{
							switch(operator){
								case "startswith":flquery=" Where "+filterfield+ " LIKE '"+value+"%'"; break;
								case "contains":flquery=" Where "+filterfield+ " LIKE '%"+value+"%'"; break;
								case "doesnotcontain":flquery=" Where "+filterfield+ " NOT LIKE '%"+value+"%'"; break;
								case "endswith":flquery=" Where "+filterfield+ " LIKE '%"+value+"'"; break;
								case "neq":flquery=" Where "+filterfield+ " != '"+value+"'"; break;
								case "eq":flquery=" Where "+filterfield+ " = '"+value+"'"; break;
							}
						}
						
						
						
						System.out.println("end bn "+flquery);
					}
					
				}
			
			}*/


            System.out.println("filter " + flquery);

            if (groupfield.isEmpty()) {
                group = "";
            } else {
                group = "group by " + groupfield + " " + groupdir + "";
            }
            if (field.isEmpty()) {
                order = "order by id desc";
            } else {
                order = "order by " + multiOrde.substring(0, multiOrde.length() - 1) + "";
            }

            String query = "";

            if (customJoins.length() > 0) {
                query = customJoins;
            } else {
                query = "from " + tablename + "  " + flquery + "  " + group + " " + order + "";
            }


            System.out.println("query " + query);

            if (isspecial.isEmpty()) {
                Query hql = sessionFactory.getCurrentSession().createQuery(query);
                hql.setFirstResult(skip);
                hql.setMaxResults(take);
                List<Object> rlist = hql.list();
                //sessionFactory.getCurrentSession().flush();

                return rlist;
            } else {
                Query nquery = sessionFactory.getCurrentSession().createSQLQuery(isspecial);
                List<Object> nlist = nquery.list();

                return nlist;
            }
			
			
           /* Query query1= session.getCurrentSession().createSQLQuery();
			List rlist = query1.list();
			return rlist;*/
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public String insert(Object obj, LutUsers inserter) {
        try {
            //sessionFactory.getCurrentSession().beginTransaction();

            sessionFactory.getCurrentSession().saveOrUpdate(obj);
            //session.getCurrentSession().flush();
            //String id=( sessionFactory.getCurrentSession().createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).toString();

            //sessionFactory.getCurrentSession().getTransaction().commit();

            if (inserter != null)
                logger.fatal("\",\"a\":\"INSERT\" , \"User\":" + inserter.toString() + " , \"Obj\":" + obj.toString() + ",\"end\":\"");

            return "true";
        } catch (ConstraintViolationException aldaa) {
            logger.error(aldaa);

            return "0";
        }
    }

    @Override
    public Object PeaceCrud(Object obj, String domainname, String method, Long obj_id, int page_val, int maxresult,
                            String whereclause) {
        try {
            Query query = null;
            if ("save".equals(method)) {
                try {
                    sessionFactory.getCurrentSession().saveOrUpdate(obj);
                    if (domainname.equalsIgnoreCase("AnnualRegistration")) {
                        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        AnnualRegistration tempObj = (AnnualRegistration) obj;
                        LnkPlanLogs log = new LnkPlanLogs();
                        log.setPlanid(tempObj.getId());
                        log.setLogdate(new Date());
                        log.setReject(tempObj.getReject());
                        log.setRejectstep(tempObj.getRejectstep());
                        log.setUsername(userDetail.getUsername());
                        log.setRepstatusid(tempObj.getRepstatusid());
                        log.setRepstepid(tempObj.getRepstepid());
                        sessionFactory.getCurrentSession().saveOrUpdate(log);
                    }
                    return true;
                } catch (ConstraintViolationException aldaa) {
                    aldaa.printStackTrace();

                    return false;
                }

            } else if ("update".equals(method)) {
                try {

                    sessionFactory.getCurrentSession().update(obj);
                    if (domainname.equalsIgnoreCase("AnnualRegistration")) {
                        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        AnnualRegistration tempObj = (AnnualRegistration) obj;
                        LnkPlanLogs log = new LnkPlanLogs();
                        log.setPlanid(tempObj.getId());
                        log.setLogdate(new Date());
                        log.setReject(tempObj.getReject());
                        log.setRejectstep(tempObj.getRejectstep());
                        log.setUsername(userDetail.getUsername());
                        log.setRepstatusid(tempObj.getRepstatusid());
                        log.setRepstepid(tempObj.getRepstepid());
                        sessionFactory.getCurrentSession().saveOrUpdate(log);
                    }
                    return true;
                } catch (ConstraintViolationException aldaa) {
                    aldaa.printStackTrace();

                    return false;
                }
            } else if ("delete".equals(method)) {

                if (whereclause != null) {

                    query = sessionFactory.getCurrentSession().createQuery("delete from " + domainname + "  dname where dname." + whereclause + "=:obj_id");
                    query.setParameter("obj_id", obj_id);
                } else {
                    query = sessionFactory.getCurrentSession().createQuery("delete from " + domainname + "  dname where dname.id=:obj_id");
                    query.setParameter("obj_id", obj_id);
                }
                //List list = query.list();
                int qresult = query.executeUpdate();
            } else if ("multidelete".equals(method)) {

                if (whereclause != null) {
                    query = sessionFactory.getCurrentSession().createQuery("delete from " + domainname + " " + whereclause);
                } else {
                    query = sessionFactory.getCurrentSession().createQuery("delete from " + domainname + "  dname where dname.id=:obj_id");
                    query.setParameter("obj_id", obj_id);
                }
                int qresult = query.executeUpdate();
            } else if ("list".equals(method)) {
                if (whereclause != null) {

                    query = sessionFactory.getCurrentSession().createQuery(whereclause);
                } else {
                    query = sessionFactory.getCurrentSession().createQuery("from " + domainname + " objlist  order by objlist.id desc ");
                }
                int pval = page_val - 1;
                query.setFirstResult(maxresult * pval);
                query.setMaxResults(maxresult);

                List<Object> robj = query.list();
                query = null;
                return robj;
            } else if ("current".equals(method)) {
                query = sessionFactory.getCurrentSession().createQuery("from " + domainname + " t where t.id=:obj_id");
                query.setParameter("obj_id", obj_id);
                Object robj = query.list().get(0);
                return robj;

            } else if ("calculatepage".equals(method)) {
                if (whereclause == null) {
                    int resultInt = ((Long) sessionFactory.getCurrentSession().createQuery("select count(*) from " + domainname + "").uniqueResult()).intValue();
                    sessionFactory.getCurrentSession().flush();
                    if (resultInt % maxresult == 0) {
                        return resultInt / maxresult;
                    } else {
                        return resultInt / maxresult + 1;
                    }
                } else {
                    int resultInt = sessionFactory.getCurrentSession().createQuery(whereclause).list().size();
                    query = null;
                    sessionFactory.getCurrentSession().flush();
                    if (resultInt % maxresult == 0) {
                        return resultInt / maxresult;
                    } else {
                        return resultInt / maxresult + 1;
                    }
                }
            } else if ("countrecord".equalsIgnoreCase(method)) {
                if (whereclause == null) {
                    int resultInt = ((Long) sessionFactory.getCurrentSession().createQuery("select count(*) from " + domainname + "").uniqueResult()).intValue();
                    query = null;
                    sessionFactory.getCurrentSession().flush();
                    return resultInt;
                } else {
                    int resultInt = sessionFactory.getCurrentSession().createQuery(whereclause).list().size();
                    sessionFactory.getCurrentSession().flush();
                    return resultInt;
                }
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public int resulsetcount(String request, String tablename) {
        try {
            Gson gson = new Gson();

            String field = "";
            String order = "";
            String dir = "";
            String sort = "";
            String group = "";
            JSONObject filter = null;
            String org = "";
            String custom = "";
            String isspecial = "";
            System.out.println("req " + request);
            System.out.println("req " + tablename);
            JSONObject req = new JSONObject(request);

            if (req.has("filter")) {
                if (!req.isNull("filter")) {
                    filter = req.getJSONObject("filter");
                }
            }

            if (req.has("custom")) {
                custom = req.getString("custom");
            }
            System.out.println("req " + request);
            System.out.println("group " + group);
            System.out.println("sort " + sort);
            System.out.println("filter " + filter);

            String filterfield = "";
            String operator = "";
            String value = "";
            String flquery = "";
            if (custom.length() > 0) {
                flquery = custom;
            }
            if (req.has("isspecial")) {
                isspecial = req.getString("isspecial");
            }
            if (filter != null) {

                JSONObject fltr = filter;

                String logic = fltr.getString("logic");
                //String filters=fltr.getString("filters");

                JSONArray arr = fltr.getJSONArray("filters");
                for (int i = 0; i < arr.length(); i++) {
                    String str = arr.get(i).toString();
                    JSONObject srt = new JSONObject(str);
                    if (srt.isNull("field")) {
                        filterfield = "";
                    } else {
                        filterfield = srt.getString("field");
                    }
                    if (srt.isNull("operator")) {
                        operator = "";
                    } else {
                        operator = srt.getString("operator");
                    }
                    if (srt.isNull("value")) {
                        value = "";
                    } else {
                        if (srt.get("value") instanceof Boolean) {
                            value = (srt.getBoolean("value") ? "1" : "0");
                        } else {
                            value = String.valueOf(srt.get("value"));
                        }

                    }
                    if (i > 0) {

                        switch (operator) {
                            case "startswith":
                                flquery = flquery + " " + logic + " " + filterfield + " LIKE '" + value + "%'";
                                break;
                            case "contains":
                                flquery = flquery + " " + logic + " " + filterfield + " LIKE '%" + value + "%'";
                                break;
                            case "doesnotcontain":
                                flquery = flquery + " " + logic + " " + filterfield + " NOT LIKE '%" + value + "%'";
                                break;
                            case "endswith":
                                flquery = flquery + " " + logic + " " + filterfield + " LIKE '%" + value + "'";
                                break;
                            case "neq":
                                flquery = flquery + " " + logic + " " + filterfield + " != '" + value + "'";
                                break;
                            case "eq":
                                flquery = flquery + " " + logic + " " + filterfield + " = '" + value + "'";
                                break;
                        }
                    } else {
                        if (custom.length() > 0) {
                            if (custom.contains("orgid")) {
                                switch (operator) {
                                    case "startswith":
                                        flquery = " Where " + filterfield + " LIKE '" + value + "%'";
                                        break;
                                    case "contains":
                                        flquery = " Where " + filterfield + " LIKE '%" + value + "%'";
                                        break;
                                    case "doesnotcontain":
                                        flquery = " Where " + filterfield + " NOT LIKE '%" + value + "%'";
                                        break;
                                    case "endswith":
                                        flquery = " Where " + filterfield + " LIKE '%" + value + "'";
                                        break;
                                    case "neq":
                                        flquery = " Where " + filterfield + " != '" + value + "'";
                                        break;
                                    case "eq":
                                        flquery = " Where " + filterfield + " = '" + value + "'";
                                        break;
                                }
                            } else {
                                switch (operator) {
                                    case "startswith":
                                        flquery = " " + custom + " and " + filterfield + " LIKE '" + value + "%'";
                                        break;
                                    case "contains":
                                        flquery = " " + custom + " and " + filterfield + " LIKE '%" + value + "%'";
                                        break;
                                    case "doesnotcontain":
                                        flquery = " " + custom + " and " + filterfield + " NOT LIKE '%" + value + "%'";
                                        break;
                                    case "endswith":
                                        flquery = " " + custom + " and " + filterfield + " LIKE '%" + value + "'";
                                        break;
                                    case "neq":
                                        flquery = " " + custom + " and " + filterfield + " != '" + value + "'";
                                        break;
                                    case "eq":
                                        flquery = " " + custom + " and " + filterfield + " = '" + value + "'";
                                        break;
                                }
                            }
                        } else {
                            switch (operator) {
                                case "startswith":
                                    flquery = " Where " + filterfield + " LIKE '" + value + "%'";
                                    break;
                                case "contains":
                                    flquery = " Where " + filterfield + " LIKE '%" + value + "%'";
                                    break;
                                case "doesnotcontain":
                                    flquery = " Where " + filterfield + " NOT LIKE '%" + value + "%'";
                                    break;
                                case "endswith":
                                    flquery = " Where " + filterfield + " LIKE '%" + value + "'";
                                    break;
                                case "neq":
                                    flquery = " Where " + filterfield + " != '" + value + "'";
                                    break;
                                case "eq":
                                    flquery = " Where " + filterfield + " = '" + value + "'";
                                    break;
                            }
                        }


                        System.out.println("end bn " + flquery);
                    }

                }

            }


            String query = "select count(*) from " + tablename + " " + flquery + " ";

            System.out.println("query" + query);

            System.out.println("query " + query);
            if (isspecial.isEmpty()) {
                Query hql = sessionFactory.getCurrentSession().createQuery(query);
                int count = Integer.parseInt(hql.list().get(0).toString());
                // 	sessionFactory.getCurrentSession().flush();
                return count;
            } else {
                Query nquery = sessionFactory.getCurrentSession().createSQLQuery(isspecial);
                List<Object> nlist = nquery.list();

                return nlist.size();
            }
			
	    /*	Query hql = session.getCurrentSession().createQuery(query);
	    	int count = Integer.parseInt(hql.list().get(0).toString());			
			session.getCurrentSession().flush();
			return count;*/

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<?> jData(Integer pageSize, Integer skip, String sortColumn, String sortColumnDir, String searchStr,
                         String domain) {


        String order = "order by " + sortColumn + " " + sortColumnDir + "";


        String query = "from " + domain + "  " + searchStr + "  " + order + "";

        Query hql = sessionFactory.getCurrentSession().createQuery(query);
        hql.setFirstResult(skip);
        hql.setMaxResults(pageSize);
        List<Object> rlist = hql.list();

        return rlist;
    }

}