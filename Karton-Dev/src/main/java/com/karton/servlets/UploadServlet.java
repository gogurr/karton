package com.karton.servlets;

import heuristic.Optimizer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/uploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub 
	}
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<FileItem> fileItems = null;
		int num_minCombAmount = data.Constraints.MIN_ADET_KOMBINASYON;
		int num_minAmount = data.Constraints.MIN_ADET;
		int num_minAmountPreferred = data.Constraints.MIN_ADET_TERCIH;
		int num_minMeter = data.Constraints.MIN_METRE;
		int num_minKilo = data.Constraints.MIN_KILO;
		int num_minKiloPreferred = data.Constraints.MIN_KILO_TERCIH;
		double dbl_kg = data.Constraints.m2kg;
		boolean kiloModu = false;
        String filePath = "inputs/";
		String inputFilePath = filePath;
		String fileName = null;
		
		logString("IP:" + request.getRemoteAddr() + "-Process Started");
		
        // Parsing field values
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(10000000);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(10000000);

        try
        {
            // Parse the request to get file items.
            fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            Iterator<FileItem> i = fileItems.iterator();
    		
            while(i.hasNext())
            {
                FileItem fi = (FileItem)i.next();
                if(fi.getFieldName().equals("num_minCombAmount"))
                {
                	num_minCombAmount = Integer.parseInt(convertEmptyStringToZero(fi.getString()));
                }
                else if(fi.getFieldName().equals("num_minAmount"))
                {
                	num_minAmount = Integer.parseInt(convertEmptyStringToZero(fi.getString()));
                }
                else if(fi.getFieldName().equals("num_minAmountPreferred"))
                {
                	num_minAmountPreferred = Integer.parseInt(convertEmptyStringToZero(fi.getString()));
                }
                else if(fi.getFieldName().equals("num_minMeter"))
                {
                	num_minMeter = Integer.parseInt(convertEmptyStringToZero(fi.getString()));
                }
                else if(fi.getFieldName().equals("num_minKilo"))
                {
                	num_minKilo = Integer.parseInt(convertEmptyStringToZero(fi.getString()));
                }
                else if(fi.getFieldName().equals("num_minKiloPreferred"))
                {
                	num_minKiloPreferred = Integer.parseInt(convertEmptyStringToZero(fi.getString()));
                }
                else if(fi.getFieldName().equals("num_kg"))
                {
                	dbl_kg = Double.parseDouble(convertEmptyStringToZero(fi.getString()));
                }
                else if(fi.getFieldName().equals("chk_kgMode"))
                {
                	if (fi.getString().equals("on"))
                		kiloModu = true;
                	else
                		kiloModu = false;
                }
                else if(fi.getFieldName().equals("file"))
                {
                	fileName = uploadFile(inputFilePath, fi);
                }
            }
            
            
            logString("Filename:" + fileName + ",{kiloModu:" + kiloModu + ",num_minCombAmount:"
            		+ num_minCombAmount + ",num_minAmount:" + num_minAmount 
            		+ ",num_minAmountPreferred:" + num_minAmountPreferred + ",num_minMeter:" + num_minMeter 
            		+ ",num_minKilo:" + num_minKilo + ",num_minKiloPreferred:" + num_minKiloPreferred 
            		+ ",dbl_kg:" + dbl_kg + "}");
            
			request.removeAttribute("resultHtml");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			if (fileName != null) {
				String[] output = new String[2];
				ArrayList<String> argList = new ArrayList<String>();
	
				Optimizer optimizer = new Optimizer();
				argList.add(inputFilePath + fileName);
				long startTime = System.currentTimeMillis();
				optimizer.setParameters(num_minCombAmount, num_minAmount, num_minAmountPreferred, num_minMeter, kiloModu, num_minKilo, num_minKiloPreferred, dbl_kg);
				output = optimizer.optimize(argList.toArray(new String[argList.size()]));
				long stopTime = System.currentTimeMillis();
				
				String htmlResult = "";
				htmlResult += "<div>Hesaplama tamamlandi. Geçen süre: " + (stopTime - startTime) + " ms. </div></br>";
				htmlResult += "<div><a href=downloadServlet?filePath=" + output[0]
						+ " type='vnd.ms-excel'>" + getFileNameFromPath(output[0]) + "</a></div></br>";
				htmlResult += "<div><a href=downloadServlet?filePath=" + output[1]
						+ " type='vnd.ms-excel'>" + getFileNameFromPath(output[1]) + "</a></div></br>";
				htmlResult += "<div><a href=downloadServlet?filePath=" + output[2]
						+ " type='vnd.ms-excel'>" + getFileNameFromPath(output[2]) + "</a></div></br>";
				
				logString("IP:" + request.getRemoteAddr() + "-Success! Duration:" + (stopTime - startTime) + "ms.");
				request.setAttribute("resultHtml", htmlResult);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				//response.sendRedirect(request.getHeader("Referer"));
				
			} else {
				out.println("<p>No file uploaded</p>");
			}
		
        } 
		catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        logString("IP:" + request.getRemoteAddr() + "-End of Process");
	}

	private String convertEmptyStringToZero(String str){
		if (str.trim().isEmpty())
			return "0";
		else
			return str;
	}
	
	private String getFileNameFromPath(String path){
		if (path.contains("\\")) //windows
		{
			return path.substring(path.lastIndexOf("\\")+1);
		}
		else if (path.contains("/")){ //linux
			return path.substring(path.lastIndexOf("/")+1);
		}
		return "";
	}

	private String uploadFile(HttpServletRequest request, HttpServletResponse response, String filePath)
			throws ServletException, IOException {
		String fileName = null;
		File file;
		int maxFileSize = 5000 * 1024;
		int maxMemSize = 5000 * 1024;
		String outputFolder = "myTemp";
	
		// Verify the content type
		String contentType = request.getContentType();
		if ((contentType.indexOf("multipart/form-data") >= 0)) {
	
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// maximum size that will be stored in memory
			factory.setSizeThreshold(maxMemSize);
			// Location to save data that is larger than maxMemSize.
			factory.setRepository(new File(outputFolder));
	
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
						
			// maximum file size to be uploaded.
			upload.setSizeMax(maxFileSize);
			try {
				// Parse the request to get file items.
				List fileItems = upload.parseRequest(request);
				
				// Process the uploaded file items
				Iterator i = fileItems.iterator();
	
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					fileName = fi.getName();
					if (!fi.isFormField()) {
						if (fileName.lastIndexOf("\\") >= 0) {
							file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
						} else {
							file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
						}
						file.getParentFile().mkdirs();
						fi.write(file);
						return fileName;
					}
				}
			} catch (Exception ex) {
				System.out.println(ex);
				return null;
			}
		} 
		
		return null;
	}

	private String uploadFile(String filePath, FileItem fi)
			throws Exception {
		String fileName = null;
		File file;
		fileName = fi.getName();
		if (!fi.isFormField()) {
			if (fileName.lastIndexOf("\\") >= 0) {
				file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
			} else {
				file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
			}
			file.getParentFile().mkdirs();
			fi.write(file);
			return fileName;
		}			
		return null;
	}

	private void logString (String message){
		Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("(DD/MM/YYYY HH:mm:ss");
    	System.out.print(sdf.format(cal.getTime()) + "---");
		System.out.println(message);
	}
}
