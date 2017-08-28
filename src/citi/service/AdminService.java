package citi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import citi.hibernate.entity.Staff;
public interface AdminService {

	List<Staff> getAllAdminUsers();

	String setDeadline(String deadline, HttpSession session);

	String downloadFile(String dlType, String location);

	String setNote(String note, HttpSession session);
	
	List<Staff> addAdminBySoeid(String soeid);

	List<Staff> deleteAdminBySoeid(String soeid);

	String setStartDate(String startDate, HttpSession session);
}
