package citi.hibernate.daoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Repository;

import citi.hibernate.dao.StaffDao;
import citi.hibernate.entity.Orders;
import citi.hibernate.entity.Staff;
import citi.hibernate.entity.Systeminfo;
import citi.hibernate.util.HibernateUtil;
import citi.serviceImpl.StaffServiceImpl;
@Repository
public class StaffDaoImpl implements StaffDao {
	private static final Log log = LogFactory.getLog(StaffDaoImpl.class);


	@Override
	public Systeminfo getSysteminfo() {
		// TODO Auto-generated method stub
		Session sessionHibernate = HibernateUtil.getSession();
		String queryString = "select new Systeminfo(s.note, s.startdate, s.deadline, s.update, s.operator) from Systeminfo s where s.systemid = ?";
		Systeminfo systeminfo = (Systeminfo) sessionHibernate.createQuery(queryString).setParameter(0, 1).uniqueResult();
		return systeminfo;
	}

	/*private String toStringFromDate(Date date) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return myFormat.format(date);
	}*/

	@Override
	public boolean insertStaff(Staff staff) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(staff);
		session.getTransaction().commit();
		return true;
	}
	public Staff findBySoeid(String soeid) {
		Session sessionHibernate = HibernateUtil.getSession();
		String queryString = "select new Staff(s.soeid, s.name,s.isadmin, s.location, s.unit, s.team) from Staff s where s.soeid = ?";
		Staff staff = (Staff) sessionHibernate.createQuery(queryString).setParameter(0, soeid).uniqueResult();
		return staff;
	}


	@Override
	public boolean update(Staff staff) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String queryString = "update Staff s set s.name = ?, s.location = ?, s.unit = ?, s.team = ? where s.soeid = ?";
		Query query = session.createQuery(queryString).setParameter(0, staff.getName()).setParameter(1, staff.getLocation()).setParameter(2, staff.getUnit()).setParameter(3, staff.getTeam()).setParameter(4, staff.getSoeid());
		query.executeUpdate();
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean addAdminBySoeid(String soeid) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String queryString = "update Staff s set s.isadmin = ? where s.soeid = ?";
		Query query = session.createQuery(queryString).setParameter(0, true).setParameter(1, soeid);
		query.executeUpdate();
		session.getTransaction().commit();
		return true;
	}

	@Override
	public List<Staff> getAllAdminUsers() {
		// TODO Auto-generated method stub
		Session sessionHibernate = HibernateUtil.getSession();
		String queryString = "select new Staff(s.soeid, s.isadmin) from Staff s where s.isadmin = ?";
		List<Staff> result = (List<Staff>) sessionHibernate.createQuery(queryString).setParameter(0, true).list();
		return result;
	}

	@Override
	public boolean deleteAdminBySoeid(String soeid) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String queryString = "update Staff s set s.isadmin = ? where s.soeid = ?";
		Query query = session.createQuery(queryString).setParameter(0, false).setParameter(1, soeid);
		query.executeUpdate();
		session.getTransaction().commit();
		return true;
	}

}
