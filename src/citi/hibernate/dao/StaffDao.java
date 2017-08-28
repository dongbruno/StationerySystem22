package citi.hibernate.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import citi.hibernate.entity.Staff;
import citi.hibernate.entity.Systeminfo;

public interface StaffDao {

	boolean insertStaff(Staff staff);
	
	Staff findBySoeid(String soeid);
	
	Systeminfo getSysteminfo();

	boolean update(Staff staff);
	
	boolean addAdminBySoeid(String soeid);

	List<Staff> getAllAdminUsers();

	boolean deleteAdminBySoeid(String soeid);
}
