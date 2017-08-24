package citi.hibernate.daoImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import citi.hibernate.dao.OrdersDao;
import citi.hibernate.dao.StationeryDao;
import citi.hibernate.entity.Orders;
import citi.hibernate.entity.Staff;
import citi.hibernate.util.HibernateUtil;

@Repository
public class OrdersDaoImpl implements OrdersDao {
	@Resource
	StationeryDao stationeryDaoImpl;

	@Override
	public List<Orders> getOrders(HttpSession session) {
		// TODO Auto-generated method stub
		Session sessionHibernate = HibernateUtil.getSession();
		// HQL String queryString = "select orders.stationery.stationeryid as stationeryid,orders.stationery.name as name,orders.stationery.price as price,orders.quantity as quantity,orders.stationery.standard as standard,orders.stationery.kind as kind from Orders as orders where orders.staff.soeid = ?";
		String queryString = "select new Orders(orders.ordersid,staff,stationery,orders.quantity,orders.date) from Orders orders left join orders.staff staff left join orders.stationery stationery where staff.soeid = ?";
		String soeid = (String) session.getAttribute("soeid");
		List<Orders> orders = sessionHibernate.createQuery(queryString).setParameter(0, soeid).list();
		return orders;
	}

	@Override
	public List<Orders> searchOrdersBySoeid(String soeid) {
		// TODO Auto-generated method stub
		Session sessionHibernate = HibernateUtil.getSession();
		//String queryString = "select orders.stationery.stationeryid as stationeryid,orders.stationery.name as name,orders.stationery.price as price,orders.quantity as quantity,orders.stationery.standard as standard,orders.stationery.kind as kind from Orders as orders where orders.staff.soeid = ?";
		String queryString = "select new Orders(orders.ordersid,staff,stationery,orders.quantity,orders.date) from Orders orders left join orders.staff staff left join orders.stationery stationery where staff.soeid = ?";
		List<Orders> orders = sessionHibernate.createQuery(queryString).setParameter(0, soeid).list();
		return orders;
	}
	@Override
	public boolean saveOrders(int stationeryid, int quantity, HttpSession session) {
		// TODO Auto-generated method stub
		Session session2 = HibernateUtil.getSession();
		String ordersid = selectOrder(stationeryid, (String) session.getAttribute("soeid"));
		if (ordersid != null) {
			updateOrder(ordersid, quantity);
		} else {
			ordersid = UUID.randomUUID().toString();
			session2.beginTransaction();
			session2.save(new Orders(ordersid,(Staff) session.getAttribute("staff"), stationeryDaoImpl.findById(stationeryid),
					quantity, new Date()));
			session2.getTransaction().commit();
		}
		return true;
	}

	@Override
	public boolean submitOrders(int stationeryid, int quantity, HttpSession session) {
		// TODO Auto-generated method stub
		Session session2 = HibernateUtil.getSession();
		String ordersid = selectOrder(stationeryid, (String) session.getAttribute("soeid"));
		if (ordersid != null) {
			addOrder(ordersid, quantity);
		} else {
			ordersid = UUID.randomUUID().toString();
			session2.beginTransaction();
			session2.save(new Orders(ordersid,(Staff) session.getAttribute("staff"), stationeryDaoImpl.findById(stationeryid),
					quantity, new Date()));
			session2.getTransaction().commit();
		}

		return true;
	}

	private String selectOrder(int stationeryid, String soeid) {
		Session session = HibernateUtil.getSession();
		String queryString = "select orders.ordersid from Orders orders left join orders.stationery stationery left join orders.staff staff where stationery.stationeryid = ? and staff.soeid = ?";
		String ordersid = (String) session.createQuery(queryString).setParameter(0, stationeryid).setParameter(1, soeid)
				.uniqueResult();
		return ordersid;
	}

	private boolean updateOrder(String ordersid, int quantity) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String queryString = "update Orders orders set orders.quantity = ? where orders.ordersid = ?";
		Query query = session.createQuery(queryString).setParameter(0, quantity).setParameter(1, ordersid);
		query.executeUpdate();
		session.getTransaction().commit();
		return true;
	}

	private boolean addOrder(String ordersid, int quantity) {
		Session session = HibernateUtil.getSession();
		String oldQueryString = "select orders.quantity as ordersid from Orders orders where orders.ordersid = ?";
		int oldQuantity = (int) session.createQuery(oldQueryString).setParameter(0, ordersid).uniqueResult();
		quantity += oldQuantity;

		session.beginTransaction();
		String queryString = "update Orders orders set orders.quantity = ? where orders.ordersid = ?";
		Query query = session.createQuery(queryString).setParameter(0, quantity).setParameter(1, ordersid);
		query.executeUpdate();
		session.getTransaction().commit();
		return true;
	}

	@Override
	public List selectOrdersInLocation(String location) {
		// TODO Auto-generated method stub
		Session sessionHibernate = HibernateUtil.getSession();
		//String queryString = "select orders.stationery.stationeryid as stationeryid,orders.stationery.name as name,orders.stationery.price as price,orders.quantity as quantity,orders.stationery.standard as standard,orders.stationery.kind as kind from Orders as orders where orders.staff.soeid = ?";
		String queryString = "select stationery.kind,stationery.name,stationery.standard,stationery.price,sum(orders.quantity) from Orders orders left join orders.staff staff left join orders.stationery stationery where staff.location = ? group by stationery.stationeryid";
		List orders = sessionHibernate.createQuery(queryString).setParameter(0, location).list();
		return orders;
	}

	@Override
	public List selectOrdersInUnitAndLocation(String location, String unit) {
		// TODO Auto-generated method stub
		Session sessionHibernate = HibernateUtil.getSession();
		//String queryString = "select orders.stationery.stationeryid as stationeryid,orders.stationery.name as name,orders.stationery.price as price,orders.quantity as quantity,orders.stationery.standard as standard,orders.stationery.kind as kind from Orders as orders where orders.staff.soeid = ?";
		String queryString = "select stationery.kind,stationery.name,stationery.standard,stationery.price,sum(orders.quantity) from Orders orders left join orders.staff staff left join orders.stationery stationery where staff.location = ? and staff.unit = ? group by stationery.stationeryid";
		List orders = sessionHibernate.createQuery(queryString).setParameter(0, location).setParameter(1, unit).list();
		return orders;
	}

	@Override
	public List<String> selectUnitsInLocation(String location) {
		// TODO Auto-generated method stub
		Session sessionHibernate = HibernateUtil.getSession();
		String queryString = "select distinct staff.unit from Orders orders left join orders.staff staff left join orders.stationery stationery where staff.location = ?";
		List<String> units = sessionHibernate.createQuery(queryString).setParameter(0, location).list();
		return units;
	}

	@Override
	public List<String> selectTeamsInUnitAndLocation(String location, String unit) {
		// TODO Auto-generated method stub
		Session sessionHibernate = HibernateUtil.getSession();
		String queryString = "select distinct staff.team from Orders orders left join orders.staff staff left join orders.stationery stationery where staff.location = ? and staff.unit = ?";
		List<String> teams = sessionHibernate.createQuery(queryString).setParameter(0, location).setParameter(1, unit).list();
		return teams;
	}

	@Override
	public List<String> selectStaffsInTeamAndUnitAndLocation(String location, String unit, String team) {
		// TODO Auto-generated method stub
		Session sessionHibernate = HibernateUtil.getSession();
		String queryString = "select distinct staff.soeid from Orders orders left join orders.staff staff left join orders.stationery stationery where staff.location = ? and staff.unit = ? and staff.team = ?";
		List<String> staffs = sessionHibernate.createQuery(queryString).setParameter(0, location).setParameter(1, unit).setParameter(2, team).list();
		return staffs;
	}

	@Override
	public List selectOrdersOfStaffInTeamAndUnitAndLocation(String staff) {
		// TODO Auto-generated method stub
		Session sessionHibernate = HibernateUtil.getSession();
		String queryString = "select staff.name,staff.soeid,orders.quantity,stationery.standard,stationery.name from Orders orders left join orders.staff staff left join orders.stationery stationery where staff.soeid = ? ";
		List orders = sessionHibernate.createQuery(queryString).setParameter(0, staff).list();
		return orders;
	}

}
