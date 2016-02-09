package com.agrotrading.kancher.moneytracker.ui.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.SeekBar;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.database.notable.CategoriesSumModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.statistics_fragment)
public class StatisticsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private List<CategoriesSumModel> categoriesSum;
    private ArrayList<Integer> colors = new ArrayList<>();
    private int defaultHoleColor;
    private float inTotal;

    @ViewById(R.id.chart)
    PieChart mChart;

    @AfterViews
    void ready() {
        getActivity().setTitle(getString(R.string.nav_drawer_statistics));
        initPieChart();
    }

    private void initPieChart() {
        defaultHoleColor = ContextCompat.getColor(getActivity(), R.color.bg_pie_hole);
        inTotal = (float) Categories.inTotal();
        categoriesSum = Categories.getCategoryWithSum();

        mChart.setUsePercentValues(true);
        mChart.setDescription(null);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText(generateCenterSpannableDefaultText());
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleRadius(58f);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleColor(defaultHoleColor);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setOnChartValueSelectedListener(this);

        setData(categoriesSum.size(), inTotal);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        mChart.getLegend().setEnabled(false);
    }

    private void setData(int count, float range) {

        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();

        for (CategoriesSumModel categorySum : categoriesSum) {
            xVals.add(categorySum.getName());
            yVals.add(new Entry((float)categorySum.getSum(), xVals.size() - 1));
            colors.add(RemoveAlpha(categorySum.getColor(), Color.WHITE ));
        }

        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.setDrawSliceText(false);
        mChart.invalidate();
    }

    private CharSequence generateCenterSpannableDefaultText() {

        SpannableString title = new SpannableString(getString(R.string.default_text_hole));
        SpannableString inTotal = new SpannableString(String.valueOf(this.inTotal));

        title.setSpan(new RelativeSizeSpan(1.5f), 0, title.length(), 0);
        title.setSpan(new StyleSpan(Typeface.NORMAL), 0, title.length(), 0);

        inTotal.setSpan(new RelativeSizeSpan(1.7f), 0, inTotal.length(), 0);
        inTotal.setSpan(new StyleSpan(Typeface.ITALIC), 0, inTotal.length(), 0);

        return TextUtils.concat(title,inTotal);
    }

    private CharSequence generateInfoCategoryText(String category, double sum) {

        SpannableString categorySpan = new SpannableString(category);
        SpannableString sumSpan = new SpannableString(String.valueOf((float) sum));

        categorySpan.setSpan(new RelativeSizeSpan(1.3f), 0, categorySpan.length(), 0);
        categorySpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, categorySpan.length(), 0);

        sumSpan.setSpan(new RelativeSizeSpan(1.5f), 0, sumSpan.length(), 0);
        sumSpan.setSpan(new StyleSpan(Typeface.ITALIC), 0, sumSpan.length(), 0);

        return TextUtils.concat(categorySpan,"\n",sumSpan);
    }

    public static int RemoveAlpha(int foreground, int background)
    {
        float alpha;
        float diff;

        if (Color.alpha(foreground) == 255) return foreground;

        alpha = (float) (Color.alpha(foreground) / 255.0);
        diff = (float) (1.0 - alpha);

        return Color.rgb(
                Math.round((float) Color.red(foreground) * alpha + Color.red(background) * diff),
                Math.round((float) Color.green(foreground) * alpha + Color.green(background) * diff),
                Math.round((float) Color.blue(foreground) * alpha + Color.blue(background) * diff)
        );
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e == null) return;
        mChart.setCenterText(generateInfoCategoryText(categoriesSum.get(e.getXIndex()).getName(), categoriesSum.get(e.getXIndex()).getSum()));
        mChart.setHoleColor(colors.get(e.getXIndex()));
    }

    @Override
    public void onNothingSelected() {
        mChart.setCenterText(generateCenterSpannableDefaultText());
        mChart.setHoleColor(defaultHoleColor);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
