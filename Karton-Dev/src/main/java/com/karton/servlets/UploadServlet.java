package com.karton.servlets;

import heuristic.Optimizer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
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
		System.out.println("gogurr: System.getProperty(-user.dir) = " + System.getProperty("user.dir"));
		String filePath = "inputs/";
		String inputFilePath = filePath;
		String fileName = null;

		request.removeAttribute("resultHtml");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Karton Optimizer - Result</title>");
		out.println("</head>"); 
		out.println("<body>");
		fileName = uploadFile(request, response, inputFilePath);
		if (fileName != null) {
			String[] output = new String[2];
			ArrayList<String> argList = new ArrayList<String>();

			Optimizer optimizer = new Optimizer();
			argList.add(inputFilePath + fileName);
			long startTime = System.currentTimeMillis();
			output = optimizer.optimize(argList.toArray(new String[argList.size()]));
			long stopTime = System.currentTimeMillis();

			out.println("<div>Hesaplama tamamlandi. GeÃ§en sÃ¼re: " + (stopTime - startTime) + " ms. </div></br>");
			out.println("<div><a href=downloadServlet?filePath=" + output[0]
					+ " type='vnd.ms-excel'>" + getFileNameFromPath(output[0]) + "</a></div></br>");
			out.println("<div><a href=downloadServlet?filePath=" + output[1]
					+ " type='vnd.ms-excel'>" + getFileNameFromPath(output[1]) + "</a></div></br>");
			out.println("<div><a href=downloadServlet?filePath=" + output[2]
					+ " type='vnd.ms-excel'>" + getFileNameFromPath(output[2]) + "</a></div></br>");
			
			String htmlResult = "";
			htmlResult += "<div>Hesaplama tamamlandi. Geçen süre: " + (stopTime - startTime) + " ms. </div></br>";
			htmlResult += "<div><a href=downloadServlet?filePath=" + output[0]
					+ " type='vnd.ms-excel'>" + getFileNameFromPath(output[0]) + "</a></div></br>";
			htmlResult += "<div><a href=downloadServlet?filePath=" + output[1]
					+ " type='vnd.ms-excel'>" + getFileNameFromPath(output[1]) + "</a></div></br>";
			htmlResult += "<div><a href=downloadServlet?filePath=" + output[2]
					+ " type='vnd.ms-excel'>" + getFileNameFromPath(output[2]) + "</a></div></br>";
			
			request.setAttribute("resultHtml", htmlResult);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			//response.sendRedirect(request.getHeader("Referer"));
			
		} else {
			out.println("<p>No file uploaded</p>");
		}
		out.println("</body>");
		out.println("</html>");
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
						System.out.println("file.getAbsolutePath() : " + file.getAbsolutePath());
					}
				}
				return fileName;
			} catch (Exception ex) {
				System.out.println(ex);
				return null;
			}
		} else {
			return null;
		}
	}
}
