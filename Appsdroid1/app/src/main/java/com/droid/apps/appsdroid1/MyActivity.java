package com.droid.apps.appsdroid1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MyActivity extends Activity {
    TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        tvResult = (TextView)findViewById(R.id.txtResult);
    }

    public void btnOpClicked(View view){
        Button btn=(Button)view;
        String str=btn.getText().toString();
        tvResult.setText(tvResult.getText() + str);
    }
    public void btnClicked(View view){
        Button btn=(Button)view;
        String str=btn.getText().toString();
        tvResult.setText(tvResult.getText() + str);
    }
    public void btnbsClicked(View view){
        String str=tvResult.getText().toString();
        tvResult.setText(str.substring(0,str.length()-1));
    }
    public String calculate(String Exp){
        String temp_first="",temp_second="",op="";

        int flag=0;
        for(int i=0;i<Exp.length();i++){
            if(!Exp.substring(i,i+1).equalsIgnoreCase("+") && !Exp.substring(i,i+1).equalsIgnoreCase("-") && !Exp.substring(i,i+1).equalsIgnoreCase("/") && !Exp.substring(i,i+1).equalsIgnoreCase("*") && !Exp.substring(i,i+1).equalsIgnoreCase("%") && !Exp.substring(i,i+1).equalsIgnoreCase("M")){
                if(flag==0)
                    temp_first=temp_first.concat(Exp.substring(i,i+1));
                else
                    temp_second=temp_second.concat(Exp.substring(i,i+1));
            }
            else{
                if(flag==1)
                {
                    temp_first=operation(op,temp_first,temp_second);
                    temp_second="";
                    System.out.println("fno: "+temp_first);
                }
                op=Exp.substring(i,i+1);
                if(op.equalsIgnoreCase("M"))
                    i=i+2;
                flag=1;

            }
        }
        temp_first=operation(op,temp_first,temp_second);
        return temp_first;

    }

    public String operation(String op,String temp_first,String temp_second){
        double first,second,Result=0;
        first=Double.parseDouble(temp_first);
        second=Double.parseDouble(temp_second);
        switch(op){
            case "+":Result=first+second;break;
            case "-":Result=first-second;break;
            case "*":Result=first*second;break;
            case "/":Result=first/second;break;
            case "%":Result=(first/second)*100;break;
            case "M":Result=first%second;break;
        }
        return Double.toString(Result);

    }
    public void btnSqClicked(View view) {
        String str=tvResult.getText().toString();
        double num=Double.parseDouble(str);
        tvResult.setText(Double.toString(num*num));
    }

    public void btnRClicked(View view){

        tvResult.setText(tvResult.getText().toString()+"^");

    }
    public void btnsqrClicked(View view){
        String str=tvResult.getText().toString();
        double num=Double.parseDouble(str);
        num=Math.sqrt(num);
        tvResult.setText(Double.toString(num));
    }
    public void btnIClicked(View view){
        double num;
        String str=tvResult.getText().toString();
        num=Double.parseDouble(str);
        num=1/num;
        tvResult.setText(Double.toString(num));
    }
    public void btnClear(View view){
        tvResult.setText("");
    }

    public void OnClickEquals(View view){
        String Exp=tvResult.getText().toString();
        String Output="";
        if(Exp.contains("^")){
            double first=Double.parseDouble(Exp.substring(0,Exp.indexOf("^")));
            double second=Double.parseDouble(Exp.substring(Exp.indexOf("^")+1,Exp.length()));
            double result=Math.pow(first,second);
            Output=Double.toString(result);
        }
        else {
            Output = calculate(Exp);
        }
        tvResult.setText(Output);

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
