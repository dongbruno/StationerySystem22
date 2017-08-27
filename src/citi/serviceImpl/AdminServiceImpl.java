package citi.serviceImpl;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import citi.export.excel.ExportExcel;
import citi.hibernate.dao.AdminDao;
import citi.hibernate.dao.StaffDao;
import citi.hibernate.entity.Staff;
import citi.hibernate.util.HibernateUtil;
import citi.service.AdminService;
@Service
public class AdminServiceImpl implements AdminService {
	@Resource
	ExportExcel ExportExcelImpl;
	@Resource
	AdminDao adminDaoImpl;
	@Resource
	StaffDao staffDaoImpl;
	
	@Override
	public String setNote(String note, HttpSession session) {
		// TODO Auto-generated method stub
		 HibernateUtil.openSession();
		 String result = adminDaoImpl.setNote(note, (String) session.getAttribute("soeid"));
         HibernateUtil.closeSession();
		 return result;
	}
	
	@Override
	public String setDeadline(String deadline, HttpSession session) {
		// TODO Auto-generated method stub
		
		 HibernateUtil.openSession();
		 String result = adminDaoImpl.setDeadline(deadline, (String) session.getAttribute("soeid"));
         HibernateUtil.closeSession();
		 return result;
	}

	@Override
	public String downloadFile(String dlType, String location) {
		String result = null;
		if(dlType.equalsIgnoreCase("purchase_sheet")){
			try {
				result = ExportExcelImpl.generatePurchaseExcel(location);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(dlType.equalsIgnoreCase("dispatch_sheet")){
			try {
				result = ExportExcelImpl.generateDispatchExcel(location);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public List<Staff> addAdminBySoeid(String soeid) {
		// TODO Auto-generated method stub
		HibernateUtil.openSession();
		Staff dbStaff = staffDaoImpl.findBySoeid(soeid);
		if(dbStaff == null) {
			staffDaoImpl.insertStaff(new Staff(soeid, true));
		}else {
			staffDaoImpl.addAdminBySoeid(soeid);
		}
		List<Staff> result = staffDaoImpl.getAllAdminUsers();
		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public List<Staff> getAllAdminUsers() {
		// TODO Auto-generated method stub
		HibernateUtil.openSession();
		List<Staff> result = staffDaoImpl.getAllAdminUsers();
		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public List<Staff> deleteAdminBySoeid(String soeid) {
		// TODO Auto-generated method stub
		HibernateUtil.openSession();
		staffDaoImpl.deleteAdminBySoeid(soeid);
		List<Staff> result = staffDaoImpl.getAllAdminUsers();
		HibernateUtil.closeSession();
		return result;
	}

	
	
}
