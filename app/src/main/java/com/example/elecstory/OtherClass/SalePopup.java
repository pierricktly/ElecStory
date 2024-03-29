package com.example.elecstory.OtherClass;

import android.app.Activity;
import android.app.Dialog;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.elecstory.R;

public class SalePopup extends Dialog {

    private Activity activity;
    private Button button1, button2, Close;
    private TextView MessageSaleView, NameObjectSaleView;
    private String MessageSale, NameObjectSale;
    private LinearLayout ButtonSalePopups;

    public SalePopup(Activity activity){
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popup_sale_object);

        this.activity = activity;
        this.MessageSaleView = findViewById(R.id.messageSale);
        this.NameObjectSaleView = findViewById(R.id.nameObjectSale);
        this.button1 = findViewById(R.id.confirmSale);
        this.button2 = findViewById(R.id.upgradeFactorysButton);
        this.Close = findViewById(R.id.closeWindows);
        this.ButtonSalePopups = findViewById(R.id.ButtonSalePopup);
        this.MessageSale = "Hello\nHello\nHello\nHello\nHello\nHello\nHello\nHello";
        this.NameObjectSale = "NameObject";
    }

    public void build(){
        show();
        MessageSaleView.setText(MessageSale);
        NameObjectSaleView.setText(NameObjectSale);
    }

    public LinearLayout getButtonSalePopups() {
        return ButtonSalePopups;
    }

    public void setButtonSalePopups(LinearLayout buttonSalePopups) {
        ButtonSalePopups = buttonSalePopups;
    }

    public Button getClose() {
        return Close;
    }

    public void setClose(Button close) {
        Close = close;
    }

    public TextView getNameObjectSaleView() {
        return NameObjectSaleView;
    }

    public void setNameObjectSaleView(TextView nameObjectSaleView) {
        NameObjectSaleView = nameObjectSaleView;
    }

    public String getNameObjectSale() {
        return NameObjectSale;
    }

    public void setNameObjectSale(String nameObjectSale) {
        NameObjectSale = nameObjectSale;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Button getButton1() {
        return button1;
    }

    public void setButton1(Button button1) {
        this.button1 = button1;
    }

    public Button getButton2() {
        return button2;
    }

    public void setButton2(Button button2) {
        this.button2 = button2;
    }

    public TextView getMessageSaleView() {
        return MessageSaleView;
    }

    public void setMessageSaleView(TextView messageSaleView) {
        MessageSaleView = messageSaleView;
    }

    public String getMessageSale() {
        return MessageSale;
    }

    public void setMessageSale(String messageSale) {
        MessageSale = messageSale;
    }
}
