package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil { // ��ü�� ���� �ʿ���� Ŭ���� �޼ҵ� ��밡�� static

	public static void createItem(TodoList l) {

		String title, category, due_date, desc, cheering, important;
		Scanner sc = new Scanner(System.in);

		System.out.print("[항목 추가] " + "제목! ");
		title = sc.nextLine();
		// sc.nextLine();
		l.isDuplicate(title);
		if (l.isDuplicate(title)) {
			System.out.println("제목이 중복된당!");
			return;
		}

		System.out.print("카테고리 " + "입력! ");
		category = sc.next();
		sc.nextLine();// ���͸� �Է¹޾ƾ� ���� ���� �Է� ���� ������ ���Ե� ���� ��

		System.out.print("내용 입력! ");
		desc = sc.nextLine();// trim�� �¿� ���� �������� Ȥ�ö� ����
		// sc.nextLine();

		System.out.print("응원해줘! ");
		cheering = sc.nextLine();// trim�� �¿� ���� �������� Ȥ�ö� ����
		// sc.nextLine();

		System.out.print("마감일자 입력! ");
		due_date = sc.next().trim();// trim�� �¿� ���� �������� Ȥ�ö� ����
		sc.nextLine();

		System.out.print("중요도! ");
		important = sc.next().trim();// trim�� �¿� ���� �������� Ȥ�ö� ����
		sc.nextLine();

		TodoItem t = new TodoItem(category, title, desc, due_date, cheering, important);

		if (l.addItem(t) > 0)
			System.out.println("추가되었음.");

	}

	public static void updateItem(TodoList l) {

		String new_title, new_desc, new_category, new_due_date, new_cheering, new_important;
		Scanner sc = new Scanner(System.in);

		System.out.print("[항목 수정]\n" + "수정할 항목의 번호를 입력! ");
		int index = sc.nextInt();

		System.out.print("새 제목! ");
		new_title = sc.next();
		sc.nextLine();

		System.out.print("새 카테고리! ");
		new_category = sc.next();
		sc.nextLine();

		System.out.print("새 내용! ");
		new_desc = sc.nextLine();
		// sc.nextLine();

		System.out.print("새 응원내용! ");
		new_cheering = sc.nextLine();
		// sc.nextLine();

		System.out.print("새 마감일자! ");
		new_due_date = sc.next().trim();// trim�� �¿� ���� �������� Ȥ�ö� ����
		sc.nextLine();

		System.out.print("새 중요도! ");
		new_important = sc.nextLine().trim();

		TodoItem t = new TodoItem(new_category, new_title, new_desc, new_due_date, new_cheering, new_important);

		t.setId(index);
		if (l.updateItem(t) > 0)
			System.out.println("수정되었습니다.");
	}

	/*
	 * public static void deleteItem(TodoList l) {
	 * 
	 * Scanner sc = new Scanner(System.in);
	 * 
	 * System.out.println("[Index Delete]\n" + "Insert you want to delete! ");
	 * 
	 * int index = sc.nextInt();
	 * 
	 * int count =0; char y_n; for (TodoItem item : l.getList()) { count++;
	 * 
	 * if(index == count) { System.out.println(item.toString());
	 * System.out.println("Are you gonna remove it? (y/n) > "); y_n =
	 * sc.next().charAt(0); if(y_n=='y') { l.deleteItem(item);
	 * System.out.println("Delete Complete!"); } else if(y_n=='n') {
	 * System.out.println("Delete Cancel!"); }
	 * 
	 * break; }
	 * 
	 * } }
	 */
	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);

		// int[] index = {0};
		int number;

		ArrayList<Integer> index = new ArrayList<>();

		while (true) {
			System.out.print("[항목 삭제]\n" + "삭제할 항목의 번호를 입력해랑! ");
			number = sc.nextInt();
			index.add(number);

			System.out.print("더 삭제할 거면 1을, 아니라면 0을 누르세요! ");
			int choice = sc.nextInt();
			if (choice == 0)
				break;
			System.out.println();

		}

		if (l.deleteItem(index) > 0)
			System.out.println("삭제되었습니다.");
	}

	public static void completeItem(TodoList l) { //todoUtil에서는 바꿀 index를 parameter로 completeItem으로 보내준다.
		
		Scanner sc = new Scanner(System.in);
		int number;
		
		ArrayList<Integer> index = new ArrayList<>();
		
		while(true) {
			System.out.print("[항목 완료 체크]\n"
					+"완료 표시할 번호를 입력해랑! ");
			number = sc.nextInt();
			index.add(number);
			
			System.out.print("더 완료 체크할 거면 1을, 아니라면 0을 누르세요! ");
			int choice = sc.nextInt();
			if(choice == 0)
				break;
			System.out.println();
		}
		
		
		
		if(l.completeItem(index)>0)
			System.out.println("완료 체크하였습니다.");
	
		
	}

	public static void saveList(TodoList l, String filename) {
		// TODO Auto-generated method stub
		try {
			Writer w = new FileWriter(filename);

			for (TodoItem myitem : l.getList()) {

				// w.write(myitem.getTitle()+"##"+myitem.getDesc()+"##"+myitem.getCurrent_date()+"\n");
				w.write(myitem.toSaveString());
			}
			w.close();
			System.out.println("Data Saved.");
		} catch (FileNotFoundException e) {
			// TODO: handle exception

			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/*
	 * public static void loadList(TodoList l, String filename) { // TODO
	 * Auto-generated method stub try { BufferedReader br = new BufferedReader(new
	 * FileReader(filename)); String oneline;
	 * 
	 * while((oneline = br.readLine())!= null) {
	 * 
	 * StringTokenizer st = new StringTokenizer(oneline, "##"); String category
	 * =st.nextToken(); String title = st.nextToken(); String desc = st.nextToken();
	 * String due_date=st.nextToken(); String current_date = st.nextToken();
	 * TodoItem t = new TodoItem(category, title, desc, due_date, current_date);
	 * //t.setCurrent_date(current_date); //l.addItem(t); l.addItem(t);
	 * 
	 * 
	 * } br.close(); System.out.println(l.getList().size() + " Data loaded!!!"); }
	 * catch (FileNotFoundException e) { // TODO: handle exception
	 * System.out.println(filename+" is gone.");
	 * 
	 * 
	 * }catch (IOException e) { // TODO: handle exception e.printStackTrace(); }
	 * 
	 * }
	 */

	/*
	 * public static void findList(TodoList l, String find) { //Set<String> clist =
	 * new HashSet<String>(); Scanner sc = new Scanner(System.in);
	 * 
	 * int count =0; int find_count=0; //char y_n; for (TodoItem item : l.getList())
	 * { count++; if(item.getTitle().contains(find) ||
	 * item.getDesc().contains(find)) {
	 * System.out.println(count+". "+item.toString()); find_count++; }
	 * 
	 * 
	 * } System.out.println("총 "+find_count+ "개의 항목을 찾았습니다.");
	 * 
	 * }
	 */
	public static void findList(TodoList l, String keyword) {

		int count = 0;

		for (TodoItem item : l.getList(keyword)) {
			if (item.getIs_completed() == 1) {
				System.out.println(item.toString_completed());
				continue;
			}
			System.out.println(item.toString());

			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);

	}

	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			if (item.getIs_completed() == 1) {
				System.out.println(item.toString_completed());
				continue;
			}
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void listCateAll(TodoList l) {
		int count = 0;

		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);

	}

	public static void listAll(TodoList l) { // getList로 가지고 오다가 is_complete가 1이라면 toString_completed로 가지고 온다.
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			if (item.getIs_completed() == 1) {
				System.out.println(item.toString_completed());
				continue;
			}
			System.out.println(item.toString());
		}
	}

	public static boolean isExistCategory(List<String> clist, String cate) {
		for (String c : clist) {
			if (c.equals(cate))
				return true;

		}
		return false;
	}
	/*
	 * public static void listAll(TodoList l) { int num = l.getList().size();
	 * 
	 * System.out.println("[All List, Num of List : "+num+"]"); int i=0; for
	 * (TodoItem item : l.getList()) { i++;
	 * System.out.println(i+". "+item.toString()); } }
	 */

	public static void listCompAll(TodoList l) { // complete된 index를 찾아서 getlist에 보내준다. //아니면 1과 같은 index를 모두 뽑으라는 건가
		int count = 0;
		for (TodoItem item : l.getCompList()) { // completed된 list를 가져온다.
			System.out.println(item.toString_completed());
			count++;
		}
		System.out.printf("총 %d개의 항목이 완료되었습니다.\n", count);
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			if (item.getIs_completed() == 1) {
				System.out.println(item.toString_completed());
				continue;
			}
			System.out.println(item.toString());
		}
	}

}
