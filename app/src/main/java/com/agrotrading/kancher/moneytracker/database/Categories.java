package com.agrotrading.kancher.moneytracker.database;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.TableInfo;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.agrotrading.kancher.moneytracker.database.notable.CategoriesSumModel;
import com.agrotrading.kancher.moneytracker.utils.ColorHelper;

import java.util.List;

@Table(name = "Categories")
public class Categories extends Model {

    @Column(name = "_id")
    private long sId = 0;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private int color = ColorHelper.getRandomColor();

    public Categories() {
        super();
    }

    public Categories(String name) {
        this.name = name;
    }

    public Categories(String name, long sId) {
        this.name = name;
        this.sId = sId;
    }

    public List<Expenses> expenses() {
        return getMany(Expenses.class, "category");
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<Categories> getAllCategories(String filter) {
        return new Select()
                .from(Categories.class)
                .where("Name LIKE ?", "%" + filter + "%")
                .orderBy("Name ASC")
                .execute();
    }

    public static List<Categories> getAllCategoriesOrderById() {
        return new Select()
                .from(Categories.class)
                .orderBy("id ASC")
                .execute();
    }

    public static List<CategoriesSumModel> getCategoryWithSum() {

        //List<CategoriesSumModel> categoriesSum = new ArrayList<>();
        From query = new Select(new String[]{"Categories.Name as name", "SUM(Expenses.Price) AS sum", "Categories.Color as color"})
                .from(Expenses.class)
                .groupBy("Category")
                .leftJoin(Categories.class)
                .on("Categories.Id = Expenses.Category");

        return CategoriesSumModel.formModelList(query);
    }

    public static void truncate(){
        TableInfo tableInfo = Cache.getTableInfo(Categories.class);
        ActiveAndroid.execSQL(
                String.format("DELETE FROM %s;",
                        tableInfo.getTableName()));
        ActiveAndroid.execSQL(
                String.format("DELETE FROM sqlite_sequence WHERE name='%s';",
                        tableInfo.getTableName()));
    }

    public long getsId() {
        return sId;
    }

    public void setsId(long sId) {
        this.sId = sId;
    }
}
