package com.peace.web.logic.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.peace.users.model.mram.LutInternalization;


import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.peace.users.dao.UserDao;

@Controller
public class InternalizationController {
	@Autowired
	UserDao dao;
	
	
	@RequestMapping(value="/api/lang/{key}",method=RequestMethod.GET)
	public @ResponseBody String saveForm( @PathVariable String key, HttpServletRequest req) throws IOException {
		
		List<LutInternalization> it=(List<LutInternalization>) dao.getHQLResult("from LutInternalization t", "list");

		JSONArray main = new JSONArray();
		JSONObject obj= new JSONObject();

		if(key.equalsIgnoreCase("en")){
			for(int i=0;i<it.size();i++){				
				obj.put(it.get(i).getNameMn(),it.get(i).getNameEn());	
				main.put(obj);
			}
		}else{
			for(int i=0;i<it.size();i++){				
				obj.put(it.get(i).getNameEn(),it.get(i).getNameMn());	
				main.put(obj);
			}
		}
		
		return obj.toString();

	}     
}
