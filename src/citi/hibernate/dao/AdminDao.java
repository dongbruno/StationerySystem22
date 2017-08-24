package citi.hibernate.dao;

public interface AdminDao {
	String setDeadline(String deadline, String soeid);

	String setNote(String note, String soeid);
	
	
}
