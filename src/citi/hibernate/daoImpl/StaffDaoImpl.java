package citi.hibernate.daoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	public Staff getStaff(HttpSession session) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Systeminfo getSystemInfo() {
		// TODO Auto-generated method stub
		Session sessionHibernate = HibernateUtil.getSession();
		String queryString = "from Systeminfo s where s.systemid = ?";
		Systeminfo systemInfo = (Systeminfo) sessionHibernate.createQuery(queryString).setParameter(0, 1).uniqueResult();
		return systemInfo;
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
	public List findByExample(Staff instance) {
		log.debug("finding Staff instance by example");
		try {
			List results = HibernateUtil.getSession().createCriteria("citi.hibernate.entity.Staff")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

}
