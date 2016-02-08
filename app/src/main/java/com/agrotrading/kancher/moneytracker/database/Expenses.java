package com.agrotrading.kancher.moneytracker.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Expenses")
public class Expenses extends Model {

    @Column(name = "_id")
    private long sId = 0;

    @Column(name = "price")
    private Double price;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private String date;

    @Column(name = "category", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public Categories category;

    public Expenses() {
        super();
    }

    public Expenses(Double price, String name, String date, Categories category) {
        super();
        this.price = price;
        this.name = name;
        this.date = date;
        this.category = category;
    }

    public static List<Expenses> getAllExpenses(String filter) {
        return new Select()
                .from(Expenses.class)
                .where("Name LIKE ?", new String[] {'%' + filter + '%'})
                .execute();
    }

    public static List<Expenses> getAllExpensesOrderById() {
        return new Select()
                .from(Expenses.class)
                .orderBy("id ASC")
                .execute();
    }

    public long getCategoryId(){
        return category.getsId();
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public long getsId() {
        return sId;
    }

    public void setsId(long sId) {
        this.sId = sId;
    }
}
