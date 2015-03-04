package com.karton.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet(description = "File download Servlet", urlPatterns = { "/downloadServlet" })
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {    	
    	String path = request.getParameter("filePath");
		File file = new File(path);
       
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
        
        FileUtils.copyFile(file, response.getOutputStream());
      	
    }
    	
    
    	
    
}
