package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<ToDoList Managing Commands>");
        System.out.println("1. add");
        System.out.println("2. del");
        System.out.println("3. edit");
        System.out.println("4. find - 통합 검색");
        System.out.println("5. find_cate - 카테고리 검색기능");
        
        System.out.println("6. ls - list all");
        System.out.println("7. ls_cate - 카테고리 목록");
        System.out.println("8. ls_name - sort by name");
        System.out.println("9. ls_name_desc - 제목역순 정렬");
        System.out.println("10. ls_date - sort by date");
        System.out.println("11. ls_date_desc - 최신순 정렬");
        System.out.println("12. comp - 레코드 완료 체크");
        System.out.println("13. ls_comp - 완료체크한 레코드 출력");
        
        
        System.out.println("14. exit");
        
    }
    public static void prompt() {
    	System.out.print("\nInsert Command > ");
    }
}
