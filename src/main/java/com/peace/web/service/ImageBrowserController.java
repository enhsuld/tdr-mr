package com.peace.web.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import com.peace.users.model.User;
import com.peace.users.model.mram.LutUsers;
import com.peace.users.model.mram.PeaceCmsContentImage;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.peace.users.dao.UserDao;
import com.peace.users.model.Tbstudentdetail;
import com.sun.mail.handlers.multipart_mixed;

@Controller
@RequestMapping("/imagebrowser")
public class ImageBrowserController {
	@Autowired
	UserDao dao;
	private String rootFolder ="/minicms/uploads/";
	@RequestMapping(value="/upload/{lpreg}",method=RequestMethod.POST)
	public @ResponseBody String upload(@RequestParam MultipartFile file, @PathVariable String lpreg,@RequestParam String path,HttpServletRequest req) throws IllegalStateException, IOException {
        if (file != null) {
        	System.out.println("zam "+path);
        	System.out.println("upload hiigdeh file "+file.getOriginalFilename());
        
			String str=req.getSession().getServletContext().getRealPath("")+"/uploads/"+lpreg+File.separator+file.getOriginalFilename();
			String str1=req.getSession().getServletContext().getRealPath("")+"uploads"+File.separator+lpreg;
			
        	File newDFile = new File(str1);
        	if(!newDFile.exists()){
        		newDFile.mkdir();
        	}
        	newDFile=null;
        	
        	
        	
        	
        	
        	File newD = new File(str);
        	if(!newD.exists()){
        		file.transferTo(newD);
            	PeaceCmsContentImage d = new PeaceCmsContentImage();
    			d.setType("f");
    			d.setName("uploads"+File.separator+lpreg+File.separator+file.getOriginalFilename());
    			Gson gson =new Gson();
    			String rjson =gson.toJson(d);
    			d=null;
    			gson=null;
                return rjson;
        	}
        	else{
        		 return null;
        	}
        	//File newD = new File(req.getSession().getServletContext().getRealPath(""+path+file.getOriginalFilename()+""));
        	
        }        
        return null;
    }
	
	public static void deleteFile(File element) {
	    if (element.isDirectory()) {
	        for (File sub : element.listFiles()) {
	            deleteFile(sub);
	        }
	    }
	    element.delete();
	}
	
	@RequestMapping(value="/destroy/{lpreg}",method=RequestMethod.POST)
	public @ResponseBody String destroyContent(@RequestParam final String name,@PathVariable String lpreg ,@RequestParam final String type, @RequestParam String path,HttpServletRequest req){
		try{
			System.out.println("init param "+name);
			PeaceCmsContentImage d = new PeaceCmsContentImage();
			d.setType(type);
			d.setName(name);
			
			System.out.println(req.getSession().getServletContext().getRealPath("")+File.separator+name);;
			
			File newD = new File(req.getSession().getServletContext().getRealPath("")+File.separator+name);
			if(newD.exists()){
				System.out.println("ustah zam (file) " +newD.getAbsolutePath());
				System.out.println("ustsan eseh "+ newD.delete());
			}
			 if (newD.isDirectory()) {
			        for (File sub : newD.listFiles()) {
			        	deleteFile(sub);
			        }
		    }
			newD.delete();
			Gson gson =new Gson();
			String rjson = gson.toJson(d);
			gson = null;
			d=null;
			return rjson;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
			
		}
	}
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public @ResponseBody String create(@RequestParam final String name, @RequestParam final String type, @RequestParam String path,HttpServletRequest req) throws IOException {
		try{
			UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
			String mainFolder="uploads/"+loguser.getLpreg();
			
			PeaceCmsContentImage d = new PeaceCmsContentImage();
			d.setType(type);
			d.setName(name);
			File newD = new File(req.getSession().getServletContext().getRealPath("")+File.separator+mainFolder+File.separator+name);
			//File newD = new File(req.getSession().getServletContext().getRealPath("")+"/"+"uploads"+"/"+path.substring(7)+name);

			if(!newD.exists()){
				System.out.println("shine folder uussen eseh "+ newD.mkdir());
			}
			Gson gson = new Gson();
			String rjson = gson.toJson(d);
			gson =  null;
			d=null;
	        return rjson;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
    }
	@RequestMapping(value="/read",method=RequestMethod.GET)
	public @ResponseBody String getImagePath(String path, @RequestParam String lpreg, HttpServletRequest req){
		Gson gson=new Gson();
		System.out.println("***************"+lpreg);


		
     //	UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
		String mainFolder="uploads/"+lpreg;
		
		List <PeaceCmsContentImage> rjson1 = new ArrayList<>();
		
		System.out.println(gson.toJson(rjson1));
		System.out.println(req.getSession().getServletContext().getRealPath(""));
		
		String str = req.getSession().getServletContext().getRealPath("")+mainFolder;
		File folder = new File(str);
		if(!folder.exists()){
			folder.mkdir();
		}
		for (final File fileEntry : folder.listFiles()) {
			PeaceCmsContentImage rjson = new PeaceCmsContentImage();
			
			if (fileEntry.isDirectory()) {
	        	rjson.setType("d");
	        	rjson.setName(fileEntry.getName());
	        }
	        else {
	        	rjson.setType("f");
	        	rjson.setName(mainFolder+"/"+fileEntry.getName());
	        	File file =new File(fileEntry.getAbsolutePath());
	        	rjson.setSize(file.length());
	        	File f = new File(mainFolder+"/"+fileEntry.getName());
	            String mimetype= new MimetypesFileTypeMap().getContentType(f);
	            String type = mimetype.split("/")[0];
	           /* if(type.equals("image")){
	            	rjson.setType("f");
		        	rjson.setName(mainFolder+"/"+fileEntry.getName());
		        	File file =new File(fileEntry.getAbsolutePath());
		        	rjson.setSize(file.length());
		        	//rjson1.add(rjson);
	            }
	            else{ 
	             	rjson.setType("f");
		        	rjson.setName(mainFolder+"/"+fileEntry.getName());
		        	File file =new File(fileEntry.getAbsolutePath());
		        	rjson.setSize(file.length());
		        	//rjson1.add(rjson);
	            }*/
	        }
			rjson1.add(rjson);
	        rjson=null;
	    }
		
		String rjsonr = gson.toJson(rjson1);
		
		gson=null;
		rjson1=null;
		
		System.out.println("rrr"+rjsonr);
		
		return rjsonr;
	}
	
	@RequestMapping(value="/read/file",method=RequestMethod.POST)
	public @ResponseBody String getfilePath(String path,HttpServletRequest req){
		Gson gson=new Gson();
		

     	UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LutUsers loguser=(LutUsers) dao.getHQLResult("from LutUsers t where t.username='"+userDetail.getUsername()+"'", "current");
		String mainFolder="uploads/"+loguser.getLpreg();
		
		List <PeaceCmsContentImage> rjson1 = new ArrayList<>();
		
		System.out.println(gson.toJson(rjson1));
		System.out.println(req.getSession().getServletContext().getRealPath(""));
		
		String str = req.getSession().getServletContext().getRealPath("")+mainFolder+"/"+path;
		File folder = new File(str);
		if(!folder.exists()){
			folder.mkdir();
		}
		for (final File fileEntry : folder.listFiles()) {
			PeaceCmsContentImage rjson = new PeaceCmsContentImage();
			
			if (fileEntry.isDirectory()) {
	        	//rjson.setType("d");
	        	//rjson.setName(mainFolder+"/"+fileEntry.getName());
	        }
	        else {
	        	File f = new File(mainFolder+"/"+fileEntry.getName());
	            String mimetype= new MimetypesFileTypeMap().getContentType(f);
	            String type = mimetype.split("/")[0];
	            if(type.equals("image")){
	            	rjson.setType("f");
		        	rjson.setName(mainFolder+"/"+fileEntry.getName());
		        	File file =new File(fileEntry.getAbsolutePath());
		        	rjson.setSize(file.length());
		        	rjson1.add(rjson);
	            }
	            else{ 
	             	rjson.setType("f");
		        	rjson.setName(mainFolder+"/"+fileEntry.getName());
		        	File file =new File(fileEntry.getAbsolutePath());
		        	rjson.setSize(file.length());
		        	rjson1.add(rjson);
	            }
	        	
	        }
	       
	        rjson=null;
	    }
		
		String rjsonr = gson.toJson(rjson1);
		
		gson=null;
		rjson1=null;
		
		System.out.println("rrr"+rjsonr);
		
		return rjsonr;
	}
	
	@RequestMapping(value="/thumbnail",method=RequestMethod.GET)
	public void getThumbnail(HttpServletRequest req,  HttpServletResponse response){
		try{
			System.out.println("thumbnail req coming ");
			
			String folderRoot = req.getParameter("path");
			System.out.println(folderRoot);
			
			File imgPath = new File(req.getSession().getServletContext().getRealPath("")+folderRoot);
			
			BufferedImage bufferedImage = ImageIO.read(imgPath.getAbsoluteFile());

			 
			int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
			BufferedImage resizedImage = new BufferedImage(80, 50, type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(bufferedImage, 0, 0, 80, 50, null);
			g.dispose();
			OutputStream outputStream = response.getOutputStream();
			ImageIO.write(resizedImage, "png", outputStream);
			outputStream.close();
		
/*			if(folderRoot.contains(mainFolder)){
				File imgPath = new File(req.getSession().getServletContext().getRealPath("")+File.separator+folderRoot);
				
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
			else{
				
				System.out.println(req.getSession().getServletContext().getRealPath("")+File.separator+mainFolder+File.separator+folderRoot);;
				File imgPath = new File(req.getSession().getServletContext().getRealPath("")+File.separator+folderRoot);
				
				if(imgPath.exists()){
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
				else{
					File imgPath1 = new File(req.getSession().getServletContext().getRealPath("")+File.separator+mainFolder+File.separator+folderRoot);
					BufferedImage bufferedImage = ImageIO.read(imgPath1.getAbsoluteFile());

					 
					int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
					BufferedImage resizedImage = new BufferedImage(80, 50, type);
					Graphics2D g = resizedImage.createGraphics();
					g.drawImage(bufferedImage, 0, 0, 80, 50, null);
					g.dispose();
					OutputStream outputStream = response.getOutputStream();
					ImageIO.write(resizedImage, "png", outputStream);
					outputStream.close();
				}
				
				
			}*/
			
			
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public @ResponseBody String save( @RequestParam("files") MultipartFile files, HttpServletRequest req) throws IOException {

	     MultipartFile mfile =  null;
	  	 mfile = (MultipartFile)files;
		 if (mfile != null) {
			 	String appPath = req.getSession().getServletContext().getRealPath("");
			 	 String SAVE_DIR = "uploads";
				 	 DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
					 Date date1 = new Date();
					 String special = dateFormat1.format(date1);
	        	    String savePath = appPath + File.separator + SAVE_DIR+ File.separator +special;
	        	    System.out.println("de dir"+special);
	        	    File logodestination = new File(savePath);
	        		if(!logodestination.exists()){
	        			logodestination.mkdir();
	    				  System.out.println("end"+logodestination);
	    			}

	        	    String path = appPath + File.separator + SAVE_DIR+ File.separator +special+ File.separator + mfile.getOriginalFilename();
	        		File logoorgpath = new File(path);
	        		
	        		
	        		mfile.transferTo(logoorgpath);
	        		
	        		/*String fileName =mfile.getOriginalFilename();
		   			int mid = fileName.lastIndexOf(".");
		   			String fex = fileName.substring(mid+1,fileName.length());
		   			
	        		String renName =fNameGen(fex, mfile.getOriginalFilename());
					File logorenamed = new File(logodestination+"/"+renName);
					if(logoorgpath.renameTo(logorenamed)){
						
					}*/
	        }        
	        return "true";
		
    }
	
	@RequestMapping(value="/withid",method=RequestMethod.POST)
	public @ResponseBody String withid(@RequestParam final int userid, @RequestParam("files") MultipartFile files, HttpServletRequest req) throws IOException {
		try{
			Tbstudentdetail detail = new Tbstudentdetail();
			User loguser= null;
			if(userid!=0){
				loguser=(User) dao.getHQLResult("from User t where t.id='"+userid+"'", "current");
				MultipartFile mfile =  null;
			  	mfile = (MultipartFile)files;
			  	if (mfile != null) { 	
			  		detail.setUserid(userid);
			  		String appPath = req.getSession().getServletContext().getRealPath("");
				 	String SAVE_DIR = "uploads";
					 	DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
						Date date1 = new Date();
						String special = dateFormat1.format(date1);
		        	    String savePath = appPath + File.separator + SAVE_DIR+ File.separator +loguser.getUsername();
		        	    System.out.println("de dir"+special);
		        	    File logodestination = new File(savePath);
		        		if(!logodestination.exists()){
		        			logodestination.mkdir();
		    				  System.out.println("end"+logodestination);
		    			}

		        	    String path = appPath + File.separator + SAVE_DIR+ File.separator +loguser.getUsername()+ File.separator + mfile.getOriginalFilename();
		        		File logoorgpath = new File(path);
		        		
		        		detail.setFile(path);
		        		dao.PeaceCrud(detail, "Tbstudentdetail", "save", (long) 0, 0, 0, null);
		        		mfile.transferTo(logoorgpath);
		        		return "true";
			  	} 
			}				
					
		  	return "false";
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
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
			 String delPath = appPath + File.separator + DEL_DIR+ File.separator + folder+ File.separator +special+ File.separator  +fileNames;
			 System.out.println("del dir"+delPath);
	    	 File destination = new File(delPath);		    	
				 
	  		if(destination.delete()){
	  			System.out.println(destination.getName() + " is deleted!");
	  		}else{
	  			System.out.println("Delete operation is failed.");
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
