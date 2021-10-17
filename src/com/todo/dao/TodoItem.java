package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private String title;
	private String desc;
	private String current_date;
	private String category;
	private String due_date;
	private int id;
	private int is_completed;
	private String important;
	private String cheering;

	public String getImportant() {
		return important;
	}



	public void setImportant(String important) {
		this.important = important;
	}

	public String getCheering() {
		return cheering;
	}

	public void setCheering(String cheering) {
		this.cheering = cheering;
	}

	public TodoItem(String category, String title, String desc, String due_date, String current_date) {
		super();
		this.category = category;
		this.title = title;
		this.desc = desc;
		this.due_date = due_date;
		this.current_date = current_date;
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}

	public TodoItem(String category, String title, String desc, String due_date, int is_completed) {
		super();
		this.title = title;
		this.desc = desc;
		this.category = category;
		this.due_date = due_date;
		this.is_completed = is_completed;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		this.current_date = f.format(new Date());
	}
	
	public TodoItem(String category, String title, String desc, String due_date, int is_completed, String cheering, String important
			) {
		super();
		this.title = title;
		this.desc = desc;
		this.category = category;
		this.due_date = due_date;
		this.is_completed = is_completed;
		this.important = important;
		this.cheering = cheering;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		this.current_date = f.format(new Date());
	}
	

	public TodoItem(String category, String title, String desc, String due_date) {
		super();
		this.title = title;
		this.desc = desc;
		this.category = category;
		this.due_date = due_date;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		this.current_date = f.format(new Date());
	}

	public TodoItem(String category) {
		// TODO Auto-generated constructor stub
		super();
		this.category = category;
	}

	public TodoItem(String category, String title, String desc, String due_date, String cheering,
			String important) {
		// TODO Auto-generated constructor stub
		super();
		this.title = title;
		this.desc = desc;
		this.category = category;
		this.due_date = due_date;
		this.cheering = cheering;
		this.important = important;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		this.current_date = f.format(new Date());
	}



	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCurrent_date() {
		return current_date;
	}

	public void setCurrent_date(String current_date) {
		this.current_date = current_date;
	}

	public String toString() {
		return id + " " + "[" + category + "] " + title + " - " + desc +" - " + cheering + " - " + due_date 
				+ " - " + current_date+ " - "  + important;
	}

	public String toString_completed() {
		return id + " " + "[" + category + "] " + title + "[V]" + " - " + desc +" - " + cheering + " - " + due_date 
				+ " - " + current_date+ " - "  + important;
	}

	public String toSaveString() {
		return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "\n";
	}

	
	
	
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;

	}

	public int getId() {
		return id;
	}
}
