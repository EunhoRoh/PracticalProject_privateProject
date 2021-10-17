 package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		l.importData("todolist.txt");
		//boolean isList = false;
		boolean quit = false;
		//TodoUtil.loadList(l, "todolist.txt"); //���� �߰�
		Menu.displaymenu();
		do {
			Menu.prompt();
			//isList = false;
			String choice = sc.next();
			int is_completed =1;
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "find":
				String keyword = sc.next().trim();
				sc.nextLine();
				TodoUtil.findList(l, keyword);
				break;
				/*
				String find = sc.next();
				TodoUtil.findList(l, find);
				break;*/
				
			case "find_cate":
				String cate = sc.next().trim();
				sc.nextLine();
				TodoUtil.findCateList(l, cate);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name":
				System.out.println("제목순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
			
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "comp":
				
				TodoUtil.completeItem(l);
				break;
			
			case "ls_comp":
				TodoUtil.listCompAll(l);
				break;	
			
			//help �߰�
			case "help":
				Menu.displaymenu();
				break;
			
				

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("no command. (insert - help)");
				break;
			}
			
			//if(isList) l.listAll();
		} while (!quit);
		sc.close(); 
		//TodoUtil.saveList(l, "todolist.txt");
	}
}
