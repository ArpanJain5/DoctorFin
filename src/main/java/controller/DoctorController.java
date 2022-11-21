package controller;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.DoctorDao;
import model.DoctorModel;
import service.Services;

/**
 * Servlet implementation class DoctorController
 */
@WebServlet("/DoctorController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 512, maxFileSize = 1024 * 1024 * 512, maxRequestSize = 1024 * 1024
		* 512)
public class DoctorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DoctorController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private String extractfilename(Part file) {
		String cd = file.getHeader("content-disposition");
		System.out.println(cd);
		String[] items = cd.split(";");
		for (String string : items) {
			if (string.trim().startsWith("filename")) {
				return string.substring(string.indexOf("=") + 2, string.length() - 1);
			}
		}
		return "";
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("register")) {
			DoctorModel d = new DoctorModel();

			String savePath = "C:\\Users\\tops\\eclipse-workspace\\DoctorFin\\src\\main\\webapp\\image";
			File fileSaveDir = new File(savePath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}
			Part file1 = request.getPart("image");
			String fileName = extractfilename(file1);
			file1.write(savePath + File.separator + fileName);
			String filePath = savePath + File.separator + fileName;

			String savePath2 = "C:\\Users\\tops\\eclipse-workspace\\DoctorFin\\src\\main\\webapp\\image";
			File imgSaveDir = new File(savePath2);
			if (!imgSaveDir.exists()) {
				imgSaveDir.mkdir();
			}
			d.setImage(fileName);
			d.setName(request.getParameter("name"));
			d.setContact(Integer.parseInt(request.getParameter("contact")));
			d.setAddress(request.getParameter("address"));
			d.setSpeciality(request.getParameter("speciality"));
			d.setExperince(request.getParameter("experience"));
			d.setEmail(request.getParameter("email"));
			d.setPassword(request.getParameter("password"));
			DoctorDao.insertDoctor(d);
			response.sendRedirect("doctor-login.jsp");
		}
		else if(action.equalsIgnoreCase("login")) {
			DoctorModel d = new DoctorModel();
			d.setEmail(request.getParameter("email"));
			d.setPassword(request.getParameter("password"));
			DoctorModel d1 = DoctorDao.doctorLogin(d);
			if(d1 == null) {
				request.setAttribute("msg", "email or password is incorrect");
				request.getRequestDispatcher("doctor-login.jsp").forward(request, response);
			}
			else {
				HttpSession session = request.getSession();
				session.setAttribute("data", d1);
				request.getRequestDispatcher("doctor-index.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("Change Password")) {
			String np = request.getParameter("np");
			String cnp = request.getParameter("cnp");
			String email = request.getParameter("email");
			if(np.equals(cnp)) {
				DoctorDao.doctorChangePasword(email, np);
				response.sendRedirect("doctor-index.jsp");
			}
			else {
				request.setAttribute("msg", "new password and confirm new password not matched");
				request.getRequestDispatcher("doctor-change-passsword.jsp").forward(request, response);
			}
			
		}
		else if(action.equalsIgnoreCase("Get OTP")) {
			String email = request.getParameter("email");
			boolean flag = DoctorDao.checkEmail(email);
			if(flag == true) {
				Services s=new Services();
				Random r=new Random();
				int num=r.nextInt(9999);
				System.out.println(num);
				s.sendMail(email,num);
				request.setAttribute("email", email);
				request.setAttribute("otp", num);
				request.getRequestDispatcher("doctor-verify-otp.jsp").forward(request, response);
			}
			else {
				request.setAttribute("msg", "email id not registered");
				request.getRequestDispatcher("doctor-forgot-password.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("verify")) {
			String email = request.getParameter("email");
			int otp1 = Integer.parseInt(request.getParameter("otp1"));
			int otp2 = Integer.parseInt(request.getParameter("otp2"));
			if(otp1 == otp2) {	
				request.setAttribute("email", email);
				request.getRequestDispatcher("doctor-new-password.jsp").forward(request, response);
			}
			else {
				request.setAttribute("email", email);
				request.setAttribute("otp", otp1);
				request.setAttribute("msg", "OTP not matched");
				request.getRequestDispatcher("doctor-verify-otp.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("np")) {
			String email = request.getParameter("email");
			String np = request.getParameter("np");
			String cnp = request.getParameter("cnp");
			if(np.equals(cnp)) {
				DoctorDao.doctorChangePasword(email, np);
				response.sendRedirect("doctor-login.jsp");
			}
			else {
				request.setAttribute("msg", "New Password and confirm new password not matched");
				request.getRequestDispatcher("doctor-new-password.jsp").forward(request, response);
			}
		}
	}

}
