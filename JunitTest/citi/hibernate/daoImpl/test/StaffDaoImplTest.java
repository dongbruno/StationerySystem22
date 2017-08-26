package citi.hibernate.daoImpl.test;

import static org.junit.Assert.*;

import org.junit.Test;

import citi.hibernate.daoImpl.StaffDaoImpl;
import citi.hibernate.entity.Staff;
import citi.hibernate.util.HibernateUtil;

public class StaffDaoImplTest {

	@Test
	public void testGetSysteminfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBySoeid() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		StaffDaoImpl sdi = new StaffDaoImpl();
		HibernateUtil.openSession();
		sdi.update(new Staff("YD83768", "董永辉1", true, "LJZ1", "VIII1", "muni1"));
		HibernateUtil.closeSession();
	}

}
