package com.peace.web.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class JXML {
	private DocumentBuilder builder;
	private Document doc = null;
	private DocumentBuilderFactory factory ;
	private XPathExpression expr = null;
	private XPathFactory xFactory;
	private XPath xpath;
	private String xmlFile;
	public static ArrayList<String> XMLVALUE ;  


	public JXML(String xmlFile){
		this.xmlFile = xmlFile;
		try {
			factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			xFactory = XPathFactory.newInstance();
			xpath = xFactory.newXPath();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(xmlFile)));

		}
		catch (Exception e){
			System.out.println(e);
		}    
	}

	public String[] getTags(){
		ArrayList<String> records = new ArrayList<String>();
		NodeList nodeList=doc.getElementsByTagName("*");
		for (int i=0; i<nodeList.getLength(); i++){
			Element element = (Element)nodeList.item(i);
			records.add(element.getNodeName());
		}
		return records.toArray(new String[records.size()]);
	}

	public String[] selectQuery(String query){
		ArrayList<String> values = new ArrayList<String>();

		try {
			expr = xpath.compile(query);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			for (int i=0; i<nodes.getLength();i++){             
				values.add(nodes.item(i).getNodeValue());			
			}
			return values.toArray(new String[values.size()]);
		} 
		catch (Exception e) {
			System.out.println("There is error in query string");
			//return values.toArray(new String[values.size()]);
			return values.toArray(new String[values.size()]);
		}       
	}

	public JSONObject selectQueryTags(String query){
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<String> tags = new ArrayList<String>();
		ArrayList<String> parent = new ArrayList<String>();
		JSONObject records = new JSONObject();
		try {
			expr = xpath.compile(query);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			for (int i=0; i<nodes.getLength();i++){             

				if (nodes.item(i).getFirstChild() != null){
					parent.add(nodes.item(i).getParentNode().getNodeName());
					tags.add(nodes.item(i).getNodeName());
					values.add(nodes.item(i).getFirstChild().getNodeValue());
				}
				else{
					parent.add(nodes.item(i).getParentNode().getNodeName());
					tags.add(nodes.item(i).getNodeName());
					values.add("");
				}

			}
			
			records.put("tags", tags);
			records.put("values", values);
			records.put("parent", parent);
			return records;
		} 
		catch (Exception e) {
			System.out.println("There is error in query string" + e.toString());
			return records;
		}       
	}


	public boolean updateQuery(String query,String value){
		try{
			NodeList nodes = (NodeList) xpath.evaluate(query, doc, XPathConstants.NODESET);
			for (int idx = 0; idx < nodes.getLength(); idx++) {
				nodes.item(idx).setTextContent(value);
			}
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(new DOMSource(doc), new StreamResult(new File(this.xmlFile)));
			return true;
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
}