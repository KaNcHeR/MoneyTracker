package com.agrotrading.kancher.moneytracker.rest.model.expense;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpenseData {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("category_id")
    @Expose
    private Long categoryId;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("sum")
    @Expose
    private Double sum;
    @SerializedName("tr_date")
    @Expose
    private String trDate;

    /**
     *
     * @return
     * The id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The category_id
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     * The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *
     * @return
     * The sum
     */
    public Double getSum() {
        return sum;
    }

    /**
     *
     * @param sum
     * The sum
     */
    public void setSum(Double sum) {
        this.sum = sum;
    }

    /**
     *
     * @return
     * The trDate
     */
    public String getTrDate() {
        return trDate;
    }

    /**
     *
     * @param trDate
     * The tr_date
     */
    public void setTrDate(String trDate) {
        this.trDate = trDate;
    }

}
