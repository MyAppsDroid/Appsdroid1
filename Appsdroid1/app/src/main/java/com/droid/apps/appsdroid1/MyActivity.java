package com.droid.apps.appsdroid1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MyActivity extends Activity {
    TextView tvResult;
    TextView errorMessage;
    PopupWindow popupMessage;
    Button insidePopupButton;
    LinearLayout layoutOfPopup;
    TextView popupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        tvResult = (TextView)findViewById(R.id.txtResult);
        errorMessage = (TextView)findViewById(R.id.errorMessage);
        tvResult.setSelected(true);
    }

    public void btnOpClicked(View view){

        Button btn=(Button)view;
        String str=btn.getText().toString();
        tvResult.setText(tvResult.getText() + str);
    }

    public void btnHelpClicked(View view){
        Button btn=(Button)view;
        String str=btn.getText().toString();
        tvResult.setText(tvResult.getText() + str);

        popupText = new TextView(this);
        insidePopupButton = new Button(this);
        layoutOfPopup = new LinearLayout(this);
        insidePopupButton.setText("OK");
        popupText.setText("This is Popup Window.press OK to dismiss it.");
        popupText.setPadding(0, 0, 0, 20);
        layoutOfPopup.setOrientation(LinearLayout.VERTICAL);
        layoutOfPopup.addView(popupText);
        layoutOfPopup.addView(insidePopupButton);
        popupMessage = new PopupWindow(layoutOfPopup, ViewGroup.LayoutParams.FILL_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
        popupMessage.setContentView(layoutOfPopup);
        popupMessage.showAsDropDown(insidePopupButton);

    }

    public void btnProClicked(View view){
        Button btn=(Button)view;
        String str=btn.getText().toString();
        tvResult.setText(tvResult.getText() + str);

        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=en"));
        startActivity(viewIntent);
    }
    public void btnClicked(View view){

        errorMessage.setText("");
        Button btn=(Button)view;
        String str=btn.getText().toString();
        tvResult.setText(tvResult.getText() + str);
    }
    public void btnbsClicked(View view){
        errorMessage.setText("");
        String str=tvResult.getText().toString();
        try{
            tvResult.setText(str.substring(0,str.length()-1));

        }catch (Exception e){
            tvResult.setText("");
    }
    }
    public String calculate(String Exp) {
        String temp_first = "", temp_second = "", op = "";
        try {

        int flag = 0;
        for (int i = 0; i < Exp.length(); i++) {
            if (!Exp.substring(i, i + 1).equalsIgnoreCase("+") && !Exp.substring(i, i + 1).equalsIgnoreCase("-") && !Exp.substring(i, i + 1).equalsIgnoreCase("/") && !Exp.substring(i, i + 1).equalsIgnoreCase("*") && !Exp.substring(i, i + 1).equalsIgnoreCase("%") && !Exp.substring(i, i + 1).equalsIgnoreCase("M")) {
                if (flag == 0)
                    temp_first = temp_first.concat(Exp.substring(i, i + 1));
                else
                    temp_second = temp_second.concat(Exp.substring(i, i + 1));
            } else {
                if (flag == 1) {
                    temp_first = operation(op, temp_first, temp_second);
                    temp_second = "";
                    System.out.println("fno: " + temp_first);
                }
                op = Exp.substring(i, i + 1);
                if (op.equalsIgnoreCase("M"))
                    i = i + 2;
                flag = 1;
            }
        }
    }catch (Exception e){
            errorMessage.setText("Syntax Error, try again1");
            tvResult.setText("");
        }
        temp_first=operation(op,temp_first,temp_second);
        return temp_first;

    }

    public String operation(String op,String temp_first,String temp_second){
        BigDecimal first,second;
        BigDecimal result=null;
        if(op.equals("-") && temp_first.equals("")){
            temp_first = "0";
        }

            try{
            first=new BigDecimal(temp_first);
            second=new BigDecimal(temp_second);
            switch(op){
                case "+":result=first.add(second);break;
                case "-":result=first.subtract(second);break;
                case "*":result=first.multiply(second);break;
                case "/":result=first.divide(second, MathContext.DECIMAL128);break;
                case "%":result=(first.divide(second, MathContext.DECIMAL128)).multiply(new BigDecimal(100));break;
                case "M":result=first.remainder(second);break;
            }
        }
        catch (NumberFormatException e){

            if(op.equals("") && temp_first.equals("") && temp_second.equals(""))
                return "";
            else
                errorMessage.setText("Syntax Error, try again2");
                tvResult.setText("");
        }
        catch (Exception e){
            if(e.getMessage().contains("Division by zero"))
            {
                return "Infinity";
            }
            errorMessage.setText("Syntax Error, try again2");
            tvResult.setText("");
        }

        return trimResult(result);
    }


    public void btnSqClicked(View view) {
        errorMessage.setText("");
        String str = tvResult.getText().toString();
        if (str.equals("Infinity")){
            tvResult.setText("Infinity");
        }
        else{
            BigDecimal num = null;
            try{
                num = new BigDecimal(str);
               num = num.multiply(num);

                tvResult.setText(normalizeOutput(trimResult(num)));
            }catch (Exception e){
                errorMessage.setText("Syntax Error, try again3");
                tvResult.setText("");
            }
        }

    }

    public void btnRClicked(View view){

        errorMessage.setText("");
        tvResult.setText(tvResult.getText().toString()+"^");
    }

    public void btnsqrClicked(View view){

        errorMessage.setText("");
        String str=tvResult.getText().toString();
        if (str.equals("Infinity")){
            tvResult.setText("Infinity");
        }
        else{
            BigDecimal num = null;
            try{
                num = new BigDecimal(str);
                num = new BigDecimal(Math.sqrt(num.doubleValue()));
                tvResult.setText(normalizeOutput(trimResult(num)));
            }catch (Exception e){
                errorMessage.setText("Syntax Error, try again4");
                tvResult.setText("");
            }
        }
    }

    public void btnIClicked(View view){
        BigDecimal num;
        try{
            String str=tvResult.getText().toString();
            num=new BigDecimal(str);
            BigDecimal one = new BigDecimal("1");
            num=one.divide(num, 20, RoundingMode.HALF_UP);
            tvResult.setText(normalizeOutput(trimResult(num)));

        }catch(Exception e){
            errorMessage.setText("Syntax Error, try again4-A");
            tvResult.setText("");
        }

    }

    public void btnClear(View view){
        errorMessage.setText("");
        tvResult.setText("");
    }

    public void OnClickEquals(View view){

        errorMessage.setText("");
        String exp = tvResult.getText().toString();
        String output = "";
        try {
            if (exp.contains("^")) {
                Double first = Double.parseDouble(exp.substring(0, exp.indexOf("^")));
                Double second = Double.parseDouble(exp.substring(exp.indexOf("^") + 1, exp.length()));
                Double result = Math.pow(first, second);
                if(result.toString().equals("Infinity")){
                    output = "Infinity";
                }
                else{
                    output = trimResult(new BigDecimal(Double.toString(result)));
                }
            } else {
                output = calculate(exp);
            }
            tvResult.setText(normalizeOutput(output));
        } catch (Exception e) {
            errorMessage.setText("Syntax Error, try again6");
            tvResult.setText("");
        }
    }

    public String normalizeOutput(String output){
        String normalizedOutput = "";
        if (output != null && output.length() != 0) {
            String[] splitOutput = output.split("\\.");

            if (splitOutput.length > 1 && splitOutput[1].equals("0")) {
                normalizedOutput = splitOutput[0];
            } else {
                normalizedOutput = output;
            }
        }
        return normalizedOutput;
    }

    public String trimResult(BigDecimal result){
        String calculatedString = result.toString();

        if(calculatedString.length() > 10){
            MathContext m = new MathContext(5);
            BigDecimal newValue = new BigDecimal(result.toString(), m);
            calculatedString = newValue.toString();
            if(calculatedString.length() >10){
                calculatedString = "Infinity";
            }
            if(calculatedString.length() < 10 && !calculatedString.contains("E") && !calculatedString.equals("Infinity")){
                MathContext m1 = new MathContext(7);
                BigDecimal newValue1 = new BigDecimal(result.toString(), m1);
                calculatedString = newValue1.toString();
            }
        }

        return calculatedString;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
