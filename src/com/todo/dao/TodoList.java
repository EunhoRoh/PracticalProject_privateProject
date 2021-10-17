package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list; //todolist ��ü�� list�� �ٷ�
	Connection conn;

	public TodoList() {
		list = new ArrayList<TodoItem>(); //todoItem ��ü���� �ϳ��� arraylist�� ����
		// 이거 하는거 아닌가...ㅜ
		this.conn = DbConnect.getConnection();
	}
	/*
	public void addItem(TodoItem t) {
		list.add(t);
		
		conn.addItem();
	}*/
	
	/*
	public void deleteItem(TodoItem t) {
		list.remove(t);
		
		conn.deleteItem();
	}*/
	public int deleteItem(ArrayList<Integer> index) {
		String sql = "DELETE from list where id=?;";
		String sql2 = "DELETE from category where category_id=?;";
		
		
		PreparedStatement pstmt;
		PreparedStatement pstmt2;
		int count =0;
		int count2 =0;
		int num_index=0;
		int countCheck=0;
		try {
			for(int i=0; i< index.size(); i++) {
				pstmt = conn.prepareStatement(sql);
				pstmt2 = conn.prepareStatement(sql2);
				pstmt.setInt(1, index.get(i));
				pstmt2.setInt(1, index.get(i));
				countCheck++;
				count = pstmt.executeUpdate();
				count2 = pstmt2.executeUpdate();
				pstmt.close();
				pstmt2.close();
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return count;
	}
	
	public int completeItem(ArrayList<Integer> completing_index) { // todolist에서는 받아온 complete할 list를 complete 체크한다.
		String sql = "update list set is_completed =1"
				+" where id = ?;";
		PreparedStatement pstmt;
		int count =0;
		try {
			for(int i=0; i< completing_index.size(); i++) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, completing_index.get(i));
				count = pstmt.executeUpdate();
				pstmt.close();
			}
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return count;
	}
	
	
	/*
	void editItem(TodoItem t, TodoItem updated) {
		conn.updateItem();
		
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}*/
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, current_date=?, due_date=?, cheering=?, important=?"
				+" where id = ?;";
		String sql2 = "update category set category=?"
				+" where category_id = ?;";
		
		PreparedStatement pstmt;
		PreparedStatement pstmt2;
		int count =0;
		int count2 =0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt2 = conn.prepareStatement(sql2);
			
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			
			pstmt.setString(3, t.getCurrent_date());
			pstmt.setString(4, t.getDue_date());
			pstmt.setString(5, t.getCheering());
			pstmt.setString(6, t.getImportant());
			pstmt.setInt(7, t.getId());
			
			pstmt2.setString(1, t.getCategory());
			pstmt2.setInt(2, t.getId());
			
			count = pstmt.executeUpdate();
			count2 = pstmt2.executeUpdate();
			
			pstmt.close();
			pstmt2.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	/*
	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}*/
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list INNER JOIN category ON list.id = category.category_id;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category =rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String cheering=rs.getString("cheering");
				String important = rs.getString("important");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, cheering, important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		try {
			String sql = "SELECT * FROM list INNER JOIN category ON list.id = category.category_id WHERE title like ? OR memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String category =rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String cheering=rs.getString("cheering");
				String important = rs.getString("important");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, cheering, important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			
			pstmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
		
	
	// complete된 index list를 가지고 온다 이건가
	
	public ArrayList<TodoItem> getCompList() { 
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		
		try {
			String sql = "SELECT * FROM list INNER JOIN category ON list.id = category.category_id WHERE is_completed=1";
			pstmt = conn.prepareStatement(sql);
			
		
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String category =rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String cheering=rs.getString("cheering");
				String important = rs.getString("important");
				// 여긴 왜 is_completed를 가져오는 함수가 없을까?
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, cheering, important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			
			pstmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list INNER JOIN category ON list.id = category.category_id ORDER BY " + orderby;
			if(ordering==0)
				sql +=" DESC";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category =rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String cheering=rs.getString("cheering");
				String important = rs.getString("important");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, cheering, important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			
			stmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	


	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list INNER JOIN category ON list.id = category.category_id WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category =rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String cheering=rs.getString("cheering");
				String important = rs.getString("important");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, cheering, important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
				
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM category";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {			
				String category =rs.getString("category");
				list.add(category);
			}		
			stmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
		
	}
	
	public int getCount() {
		Statement stmt;
		int count =0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return count;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("All list\n");
		for (TodoItem myitem : list) {
			System.out.println("[Category] "+myitem.getCategory()+" [Title] "+myitem.getTitle()+" [Description] " + myitem.getDesc()
			+" [Cheering] "+myitem.getCheering() +" [Due_Date] "+myitem.getDue_date()+" [Current_date] "+myitem.getCurrent_date()
			+" [Important] "+myitem.getImportant());
		}
	}
	
	
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {//Ư����ü�� ���°�� �� �ִ���
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
	
		try {
			String sql = "SELECT title FROM list WHERE title like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
	
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				pstmt.close();
				return true;
			}
			pstmt.close();
			return false;
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	

	
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, current_date, due_date)"
					+ " values (?, ?, ?, ?);";
			String sql2 =  "insert into category (category_id, category)"
					+ " values (last_insert_rowid(),?);";
			int records =0;
			while((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line, "##");
		
				String category =st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date=st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, current_date);
				pstmt.setString(4, due_date);
				
				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				
				
				pstmt2.setString(1, category);
				
				int count = pstmt.executeUpdate();
				int count2 = pstmt2.executeUpdate();
				if(count>0 && count2>0) records++;
				pstmt.close();
				pstmt2.close();
			}
			System.out.println(records + "records read!!");
			br.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo,current_date, due_date, cheering, important)"
				+ " values (?,?,?,?,?,?);";
			
		String sql2 =  "insert into category (category_id, category)"
				+ " values (last_insert_rowid(),?);";
		
		PreparedStatement pstmt;
		PreparedStatement pstmt2;
		
		int count =0;
		int count2 =0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt2 = conn.prepareStatement(sql2);
			
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCurrent_date());
			pstmt.setString(4, t.getDue_date());
			pstmt.setString(5, t.getCheering());
			pstmt.setString(6, t.getImportant());
			
			pstmt2.setString(1, t.getCategory());
			
			count = pstmt.executeUpdate();
			count2 = pstmt2.executeUpdate();
			
			pstmt.close();
			pstmt2.close();
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return count;
		
	}
	
	
	
	

	
}
