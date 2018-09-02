package com.peace.users.dao;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.peace.users.model.mram.LutUsers;

public interface UserDao {

	LutUsers findByUserName(String username);
	public Object PeaceCrud(Object obj, String domainname, String method,Long obj_id,int page_val,int maxresult,String whereclause);
	public Object getHQLResult(String hqlString,String type);
//	com.mkyong.users.model.User getUser(String username);
	public List<?> kendojson(String request, String domain);
	public int resulsetcount(String request, String domain);
	String insert(Object obj, LutUsers inserter);
	Object findAll(String domain, String whereclause);
	Object saveOrUpdate(Object obj);
	public Object findById(String domain,long id, String whereclause);
	void deleteById(String domain,long id, String whereclause);
	public List<?> jData(Integer pageSize,Integer skip,String sortColumn,String sortColumnDir,String searchStr,String domain);
	public List<Object[]> getNativeSQLResult(String query,String type);
}