package citi.serviceImpl;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import citi.hibernate.dao.OrdersDao;
import citi.hibernate.entity.Orders;
import citi.hibernate.util.HibernateUtil;
import citi.service.OrdersService;
@Service
public class OrdersServiceImpl implements OrdersService {
@Resource
OrdersDao ordersDaoImpl;
	@Override
	public List<Orders> getOrders(HttpSession session) {
		// TODO Auto-generated method stub
		 HibernateUtil.openSession();
		 List<Orders> orders = ordersDaoImpl.getOrders(session);
         HibernateUtil.closeSession();
         return orders;
	}

	@Override
	public List<Orders> searchOrdersBySoeid(String soeid) {
		// TODO Auto-generated method stub
		 HibernateUtil.openSession();
		 List<Orders> orders = ordersDaoImpl.searchOrdersBySoeid(soeid);
         HibernateUtil.closeSession();
         return orders;
	}
	
	@Override
	public boolean saveOrders(JsonArray orderJsonArray, HttpSession session) {
		// TODO Auto-generated method stub
		 for(int i=0;i<orderJsonArray.size();i++){
             JsonObject subObject=orderJsonArray.get(i).getAsJsonObject();
             int stationeryid=subObject.get("stationeryid").getAsInt();
             int quantity=subObject.get("quantity").getAsInt();
             HibernateUtil.openSession();
             ordersDaoImpl.saveOrders(stationeryid, quantity, session);
             HibernateUtil.closeSession();
         }
		return true;
	}

	@Override
	public boolean submitOrders(JsonArray orderJsonArray, HttpSession session) {
		// TODO Auto-generated method stub
		 for(int i=0;i<orderJsonArray.size();i++){
             JsonObject subObject=orderJsonArray.get(i).getAsJsonObject();
             int stationeryid=subObject.get("stationeryid").getAsInt();
             int quantity=subObject.get("quantity").getAsInt();
             HibernateUtil.openSession();
             ordersDaoImpl.submitOrders(stationeryid, quantity, session);
             HibernateUtil.closeSession();
         }
		return true;
	}
	@Override
	public List selectOrdersInLocation(String location) {
		// TODO Auto-generated method stub
		 HibernateUtil.openSession();
		 List orders = ordersDaoImpl.selectOrdersInLocation(location);
         HibernateUtil.closeSession();
         return orders;
	}

	@Override
	public List<String> selectUnitsInLocation(String location) {
		// TODO Auto-generated method stub
		HibernateUtil.openSession();
		List<String> units = ordersDaoImpl.selectUnitsInLocation(location);
        HibernateUtil.closeSession();
        return units;
	}

	@Override
	public List selectOrdersInUnitAndLocation(String location, String unit) {
		// TODO Auto-generated method stub
		 HibernateUtil.openSession();
		 List orders = ordersDaoImpl.selectOrdersInUnitAndLocation(location, unit);
         HibernateUtil.closeSession();
         return orders;
	}

	@Override
	public List<String> selectTeamsInUnitAndLocation(String location, String unit) {
		// TODO Auto-generated method stub
		HibernateUtil.openSession();
		List<String> teams = ordersDaoImpl.selectTeamsInUnitAndLocation(location, unit);
        HibernateUtil.closeSession();
        return teams;
	}

	@Override
	public List<String> selectStaffsInTeamAndUnitAndLocation(String location, String unit, String team) {
		// TODO Auto-generated method stub
		HibernateUtil.openSession();
		List<String> staffs = ordersDaoImpl.selectStaffsInTeamAndUnitAndLocation(location, unit, team);
        HibernateUtil.closeSession();
        return staffs;
	}

	@Override
	public List selectOrdersOfStaffInTeamAndUnitAndLocation(String staff) {
		// TODO Auto-generated method stub
		 HibernateUtil.openSession();
		 List orders = ordersDaoImpl.selectOrdersOfStaffInTeamAndUnitAndLocation(staff);
         HibernateUtil.closeSession();
         return orders;
	}

	
}
