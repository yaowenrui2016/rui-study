package indi.rui.study.something.ENUM;

/**
 * @author: yaowr
 * @create: 2022-08-26
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("admin: id=" + BuiltInAccount.ADMIN.getId() + ", name=" + BuiltInAccount.ADMIN.getName());
        BuiltInAccount.ADMIN.setName("admin");
        System.out.println("admin: id=" + BuiltInAccount.ADMIN.getId() + ", name=" + BuiltInAccount.ADMIN.getName());
        BuiltInAccount.ADMIN.setName("admin_");
        System.out.println("admin: id=" + BuiltInAccount.ADMIN.getId() + ", name=" + BuiltInAccount.ADMIN.getName());
    }
}
