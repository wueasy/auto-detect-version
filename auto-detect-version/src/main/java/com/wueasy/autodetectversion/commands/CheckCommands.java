/*
 * auto-detect-version - Tools for automatically detecting the latest version of dependent packages in POM files.
 * Copyright (C) 2017-2020 wueasy.com , All Rights Reserved.

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wueasy.autodetectversion.commands;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.xml.sax.SAXException;

@ShellComponent
public class CheckCommands {
	
	/**
	 * Address of detection, default Maven central warehouse
	 */
	private static final String CHECK_URL = "https://repo1.maven.org/maven2/";
	

	/**
	 * Detect the latest dependent version of jar package in POM file
	 * 
	 * demo:  
	 *      check E:\\git\\wueasy\\auto-detect-version\\auto-detect-version\\pom.xml
	 * @author: fallsea
	 * @param path
	 * @return
	 */
	@ShellMethod(value = "Detect the latest dependent version of jar package in POM file", key = "check")
    public String check(String path){
		
		SAXReader reader = new SAXReader();
		reader.setValidation(false);
		try {
			reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		} catch (SAXException e) {
			return "Failed to read POM file!";
		}
		Document document = null;
		try {
			if(path.startsWith("http://") || path.startsWith("https://")) {
				document = reader.read(new URL(path));
			}else {
				document = reader.read(new File(path));
			}
		} catch (DocumentException e) {
			return "Failed to read POM file!";
		} catch (MalformedURLException e) {
			return "Failed to read POM file!";
		} catch (Exception e) {
			return "Failed to read POM file!";
		}
		
		Map<String,String> variableMap = new HashMap<>();

		//Get POM variable information
		List<Element> propertieList = document.getRootElement().element("properties").elements();
		for (Element element : propertieList) {
			variableMap.put("${"+element.getName()+"}", element.getTextTrim());
		}
		
		if(null!=document.getRootElement().element("parent")) {
			Element element = document.getRootElement().element("parent");
			String groupId = element.elementText("groupId");
			String artifactId = element.elementText("artifactId");
			String version = element.elementText("version");
			
			if(StringUtils.isNotEmpty(version) && version.startsWith("$")) {
				version = variableMap.get(version);
			}
			if(StringUtils.isNotEmpty(version)) {
				check(groupId, artifactId, version);
			}
		}
		
		//Get jar package information
		if(null!=document.getRootElement().element("dependencyManagement") && null!=document.getRootElement().element("dependencyManagement").element("dependencies")) {
			check(document.getRootElement().element("dependencyManagement").element("dependencies").elements(), variableMap);
		}
		if(null!=document.getRootElement().element("dependencies")) {
			check(document.getRootElement().element("dependencies").elements(), variableMap);
		}
		return "Operation succeeded!";
    }
	
	/**
	 * 
	 * @author: fallsea
	 * @param dependencieList
	 * @param variableMap
	 */
	private void check(List<Element> dependencieList,Map<String,String> variableMap) {
		if(null!=dependencieList && !dependencieList.isEmpty()) {
			for (Element element : dependencieList) {
				String groupId = element.elementText("groupId");
				String artifactId = element.elementText("artifactId");
				String version = element.elementText("version");
				
				if(StringUtils.isNotEmpty(version) && version.startsWith("$")) {
					version = variableMap.get(version);
				}
				if(StringUtils.isNotEmpty(version)) {
					check(groupId, artifactId, version);
				}
			}
		}
	}
	
	/**
	 * 
	 * @author: fallsea
	 * @param groupId
	 * @param artifactId
	 * @param version
	 */
	private void check(String groupId,String artifactId,String version) {
		String url = CHECK_URL + groupId.replace(".", "/")+"/"+artifactId.replace(".", "/")+"/maven-metadata.xml";
		try {
			SAXReader reader = new SAXReader();
			reader.setValidation(false);
			reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			Document document = reader.read(new URL(url));
			String latest = document.getRootElement().element("versioning").elementText("release");
			if(!version.equals(latest)) {
				System.out.println("<dependency>");
				System.out.println("    <groupId>"+groupId+"</groupId>");
				System.out.println("    <artifactId>"+artifactId+"</artifactId>");
				System.out.println("    <version>"+version+"</version>");
				//Latest version
				System.out.println("    <latest>"+latest+"</latest>");
				//Latest version of the same version
				
				if(version.indexOf(".")!=-1) {
					String start = version.substring(0,version.indexOf(".")+1);
					List<Element> versionList = document.getRootElement().element("versioning").element("versions").elements();
					if(null!=versionList && !versionList.isEmpty()) {
						String latest2 = "";
						for (Element element : versionList) {
							if(element.getText().startsWith(start)) {
								latest2 = element.getText();
							}
						}
						if(StringUtils.isNotEmpty(latest2)) {
							System.out.println("    <largeLatest>"+latest2+"</largeLatest>");
						}
					}
				}
				System.out.println("</dependency>");
				System.out.println();
			}
		} catch (DocumentException e) {
		} catch (SAXException e) {
		} catch (MalformedURLException e) {
		} catch(Exception e) {
		}
	}
}
