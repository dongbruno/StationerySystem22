package citi.hibernate.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import citi.hibernate.dao.AdminDao;
import citi.hibernate.util.HibernateUtil;
@Repository
public class AdminDaoImpl implements AdminDao {

	@Override
	public String setNote(String note, String soeid) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String queryString = "update Systeminfo s set s.note = ?, s.update = ?, s.operator = ? where s.systemid = ?";
		Query query = session.createQuery(queryString).setParameter(0, note).setParameter(1, new Date()).setParameter(2, soeid).setParameter(3, 1);
		query.executeUpdate();
		session.getTransaction().commit();
		return "发布公告成功！";
	}

	@Override
	public String setDeadline(String deadline, String soeid) {
		// TODO Auto-generated method stub
		if(deadline.equalsIgnoreCase("")){
			return "格式错误，设置截止日期失败！";
		}else{
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String queryString = "update Systeminfo s set s.deadline = ?, s.update = ?, s.operator = ? where s.systemid = ?";
		Query query = session.createQuery(queryString).setParameter(0, toDateFromStr(deadline)).setParameter(1, new Date()).setParameter(2, soeid).setParameter(3, 1);
		query.executeUpdate();
		session.getTransaction().commit();
		return "设置截止日期成功！";
		}
	}
	private Date toDateFromStr(String sourceTime){
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		Date date = null;
	         try {
			 date = format.parse(sourceTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date;
	}

	@Override
	public String setStartDate(String startDate, String soeid) {
		// TODO Auto-generated method stub
		if(startDate.equalsIgnoreCase("")){
			return "格式错误，设置开始日期失败！";
		}else{
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String queryString = "update Systeminfo s set s.startdate = ?, s.update = ?, s.operator = ? where s.systemid = ?";
		Query query = session.createQuery(queryString).setParameter(0, toDateFromStr(startDate)).setParameter(1, new Date()).setParameter(2, soeid).setParameter(3, 1);
		query.executeUpdate();
		session.getTransaction().commit();
		return "设置开始日期成功！";
		}
	}

}
