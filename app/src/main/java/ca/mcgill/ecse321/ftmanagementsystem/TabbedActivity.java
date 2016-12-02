

package ca.mcgill.ecse321.ftmanagementsystem;

import android.app.Activity;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;

import ca.mcgill.ecse321.FoodTruckManagementSystem.controller.EquipmentController;
import ca.mcgill.ecse321.FoodTruckManagementSystem.controller.InvalidInputException;
import ca.mcgill.ecse321.FoodTruckManagementSystem.controller.MenuController;
import ca.mcgill.ecse321.FoodTruckManagementSystem.controller.StaffController;
import ca.mcgill.ecse321.FoodTruckManagementSystem.controller.SupplyController;
import ca.mcgill.ecse321.FoodTruckManagementSystem.model.Equipment;
import ca.mcgill.ecse321.FoodTruckManagementSystem.model.FoodTruckManager;
import ca.mcgill.ecse321.FoodTruckManagementSystem.model.Item;
import ca.mcgill.ecse321.FoodTruckManagementSystem.model.Shift;
import ca.mcgill.ecse321.FoodTruckManagementSystem.model.Staff;
import ca.mcgill.ecse321.FoodTruckManagementSystem.model.Supply;
/* This class contains all the refactored code from the jar files
    It implements all the functionality of the app

    @Author: Cyril Abiaad
 */
public class TabbedActivity extends AppCompatActivity {
    private HashMap<Integer, Staff> staffMembers;
    private HashMap<Integer, Shift> shifts;
    private HashMap<Integer, Supply> supplies;
    private HashMap<Integer, Equipment> equipments;
    private HashMap<Integer, Item> items;
    private GoogleApiClient client;


    TabHost host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);


        host= (TabHost)findViewById(R.id.tabHost);
        refreshData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Staff");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Staff");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Supply");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Supply");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Menu");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Menu");
        host.addTab(spec);


        host.getCurrentTab();

    }

    /*Method to update what is being displayed in the app after the user enters a new quantity
    or removes something
    it is called at the end of every method

            @Author: Cyril Abiaad

            */
    private void refreshData() {

        TextView name = (TextView) findViewById(R.id.newstaff_name);
        name.setText("");
        TextView role = (TextView) findViewById(R.id.newstaff_role);
        role.setText("");
        // Initialize the data in the participant spinner
        Spinner spinner1 = (Spinner) findViewById(R.id.nameSpinner);

        ArrayAdapter<CharSequence> staffAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        staffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.staffMembers = new HashMap<Integer, Staff>();
        int i = 0;
        FoodTruckManager fm = FoodTruckManager.getInstance();
        for (Iterator<Staff> participants = fm.getStaffs().iterator();
             participants.hasNext(); i++) {
            Staff s = participants.next();
            staffAdapter.add(s.getName() + " (" + s.getRole() + ")");
            this.staffMembers.put(i, s);
        }
        spinner1.setAdapter(staffAdapter);
        Spinner spinner2 = (Spinner) findViewById(R.id.shiftSpinner);

        ArrayAdapter<CharSequence> shiftAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        shiftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.shifts = new HashMap<Integer, Shift>();
        int j = 0;
        FoodTruckManager fm2 = FoodTruckManager.getInstance();
        for (Iterator<Shift> shifttemp = fm2.getShifts().iterator();
             shifttemp.hasNext(); j++) {
            Shift s = shifttemp.next();
            shiftAdapter.add(s.getShiftDate() + " (" + s.getStartTime() + "--" + s.getEndTime() + ")");
            this.shifts.put(j, s);
        }
        spinner2.setAdapter(shiftAdapter);
        Spinner spinner3 = (Spinner) findViewById(R.id.supplySpinner);

        ArrayAdapter<CharSequence> supplyAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        supplyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.supplies = new HashMap<Integer, Supply>();
        int k = 0;
        FoodTruckManager fm3 = FoodTruckManager.getInstance();
        for (Iterator<Supply> supplytemp = fm3.getSupplies().iterator();
             supplytemp.hasNext(); k++) {
            Supply s = supplytemp.next();
            supplyAdapter.add(s.getName()+ " (" + s.getQuantity() + ")");
            this.supplies.put(k, s);
        }
        spinner3.setAdapter(supplyAdapter);
        Spinner spinner4 = (Spinner) findViewById(R.id.equipmentSpinner);

        ArrayAdapter<CharSequence> equipmentAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.equipments = new HashMap<Integer, Equipment>();
        int l = 0;
        FoodTruckManager fm4 = FoodTruckManager.getInstance();
        for (Iterator<Equipment> equipmenttemp = fm4.getEquipment().iterator();
             equipmenttemp.hasNext(); l++) {
            Equipment s = equipmenttemp.next();
            equipmentAdapter.add(s.getName()+ " (" + s.getQuantity() + ")");
            this.equipments.put(l, s);
        }
        spinner4.setAdapter(equipmentAdapter);

        Spinner spinner5 = (Spinner) findViewById(R.id.itemSpinner);

        ArrayAdapter<CharSequence> itemAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.items = new HashMap<Integer, Item>();
        int m = 0;
        FoodTruckManager fm5 = FoodTruckManager.getInstance();
        for (Iterator<Item> itemtemp = fm5.getItems().iterator();
             itemtemp.hasNext(); m++) {
            Item t = itemtemp.next();
            itemAdapter.add(t.getName());
            this.items.put(m, t);
        }
        spinner5.setAdapter(itemAdapter);

        Spinner spinner6 = (Spinner) findViewById(R.id.supplySpinner2);
        spinner6.setAdapter(supplyAdapter);
        Spinner spinner7 = (Spinner) findViewById(R.id.itemSpinner2);
        spinner7.setAdapter(itemAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
/*Method to add staff
by reading 2 string values from edit view (the role and name)

@Author: Cyril Abiaad

 */
    public void addStaff(View v) {
        TextView name = (TextView) findViewById(R.id.newstaff_name);
        TextView role = (TextView) findViewById(R.id.newstaff_role);
        StaffController sc = new StaffController();
        try {
            sc.createStaff(name.getText().toString(), role.getText().toString());
//TODO: CRASH
      /*      if (name == null) {
                error = error + " Staff name cannot be empty!";
            }

            if (role == null) {
                error = error + " Staff member must have a role!";
            }
            if (error.length() > 0) {
                throw new InvalidInputException(error);
            } */
        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

        }
        refreshData();
    }
    /*Method to remove a shift
by reading the current shift value from the spinner

@Author: Cyril Abiaad

 */
    public void removeShift(View v){
        Spinner spinner1 = (Spinner) findViewById(R.id.shiftSpinner);

        int index = spinner1.getSelectedItemPosition();
        try {
            StaffController sc = new StaffController();
            sc.removeShift(shifts.get(index));
       /*     String error = "Must select a shift to remove";
            if (shifts.get(index) == null)
                throw new InvalidInputException(error); */
        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();
    }
    /*Method to remove a shift
by reading the current staff value from the spinner

@Author: Cyril Abiaad

*/
    public void removeStaff(View v) {
        //Initialize staff spinner and get staff from spinner
        Spinner spinner1 = (Spinner) findViewById(R.id.nameSpinner);

        int index = spinner1.getSelectedItemPosition();
        //Try removing staff
        try {
            StaffController sc = new StaffController();
            sc.removeStaff(staffMembers.get(index));
        /*    String error = "Must select a staff to remove";
            if (staffMembers.get(index) == null)
                throw new InvalidInputException(error); */
        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        refreshData();

    }
    /*Method to add shift
by reading 3 string values from edit view (the start time end time and date)
it then parses then to the correct format before calling the relevant function

@Author: Cyril Abiaad

 */
    public void addShift(View v) {
        TextView tv1 = (TextView) this.findViewById(R.id.new_date);
        TextView tv2 = (TextView) this.findViewById(R.id.new_StartTime);
        TextView tv3 = (TextView) this.findViewById(R.id.new_EndTime);
        StaffController sc = new StaffController();
        String error = " ";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date parsed = simpleDateFormat.parse(tv1.getText().toString());
            Date sqldate = new Date(parsed.getTime());


            Time time1= Time.valueOf(tv2.getText().toString()+":00");
            Time time2= Time.valueOf(tv3.getText().toString()+":00");

            sc.createShift(sqldate, time1, time2);

            //   if (tv1 == null) {
            //       error = error + " Shift date cannot be empty!";
            //   }

            // if (tv2 == null) {
            //    error = error + " Shift start time cannot be empty!";
            //}

            //  if (tv3 == null) {
            //     error = error + " Shift end time cannot be empty!";
            // }

            //    error = error.trim();
            //   if (error.length() > 0)
            //      throw new InvalidInputException(error);
        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.refreshData();
    }
    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = this.getDateFromLabel(tf.getText());
        args.putInt("id", v.getId());
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }
    public void showTimePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = this.getTimeFromLabel(tf.getText());
        args.putInt("id", v.getId());
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(this.getSupportFragmentManager(), "timePicker");
    }
    private Bundle getTimeFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String[] comps = text.toString().split(":");
        int hour = 12;
        int minute = 0;
        if (comps.length == 2) {
            hour = Integer.parseInt(comps[0]);
            minute = Integer.parseInt(comps[1]);
        }

        rtn.putInt("hour", hour);
        rtn.putInt("minute", minute);
        return rtn;
    }
    private Bundle getDateFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String[] comps = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;
        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month - 1);
        rtn.putInt("year", year);
        return rtn;
    }
    public void setTime(int id, int h, int m) {
        TextView tv = (TextView) this.findViewById(id);
        tv.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(h), Integer.valueOf(m)}));
    }
    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) this.findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", new Object[]{Integer.valueOf(d), Integer.valueOf(m + 1), Integer.valueOf(y)}));
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    /*Method to Add a staff member to a shift
    it takes the staff member and the shift from 2 spinners the user selects
    and assigns a shift to the staff

@Author: Cyril Abiaad

*/

    public void AddStafftoShift(View view) {
        //read shift from spinner
        Spinner spinner1 = (Spinner) findViewById(R.id.shiftSpinner);
        int index = spinner1.getSelectedItemPosition();
        //read staff from spinner
        Spinner spinner2 = (Spinner) findViewById(R.id.nameSpinner);
        int index2 = spinner2.getSelectedItemPosition();
        try {
            StaffController sc = new StaffController();
            Shift s = shifts.get(index);
            Staff f = staffMembers.get(index2);
            sc.addShiftToStaff(f,s);
            //error handling
        /*    String error = "";
            if(f == null) {
                error = error + " Staff member must be selected!";
            }

            if(s == null) {
                error = error + " Shift must be selected!";
            }


                if(f != null && s != null && !s.addStaff(f))
                    error = error + " Shift is already assigned to selected staff!";

            error.trim();
            if (error.length() >0)
                throw new InvalidInputException(error); */
        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        refreshData();

    }

    /*Method to remove a staff member from a shift
    it takes the staff member and the shift from 2 spinners the user selects
    and removes the staff from the shift

@Author: Cyril Abiaad

*/
    public void removeShiftfromStaff(View v){
        // read shift from spinner
        Spinner spinner1 = (Spinner) findViewById(R.id.shiftSpinner);
        int index = spinner1.getSelectedItemPosition();
        //read staff from spinner
        Spinner spinner2 = (Spinner) findViewById(R.id.nameSpinner);
        int index2 = spinner2.getSelectedItemPosition();
        try {
            // try to execute
            StaffController sc = new StaffController();
            Shift s = shifts.get(index);
            Staff f = staffMembers.get(index2);
            sc.removeShiftFromStaff(f,s);
            String error = "";
            //Error handling
     /*       if(f == null) {
                error = error + " Staff member must be selected!";
            }

            if(s == null) {
                error = error + " Shift must be selected!";
            }
            if(f != null && s != null && !s.removeStaff(f)) {
                error = error + " Shift must be assigned to selected staff in order to unassign it!";
            }
            error.trim();
            if (error.length() >0)
                throw new InvalidInputException(error); */
            //output error message
        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        refreshData();

    }
    /*Method to create a new supply item
    it takes 2 string values the supply name quantity
    and 1 best before date

@Author: Cyril Abiaad

*/
    public void createSupply(View v) {
        TextView supply = (TextView) findViewById(R.id.newSupply);
        TextView quantity = (TextView) findViewById(R.id.supplyQuantity);
        TextView date = (TextView) findViewById(R.id.bestBeforeDate);
        SupplyController sc = new SupplyController();
        String error = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date parsed = simpleDateFormat.parse(date.getText().toString());
            Date sqldate = new Date(parsed.getTime());
              sc.createSupply(supply.getText().toString(),quantity.getText().toString(),sqldate);

        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        refreshData();
    }


    /*Method to remove a supply item
    removes supply item selected in spinner

@Author: Cyril Abiaad

*/
    public void removeSupply(View v) {
        Spinner spinner1 = (Spinner) findViewById(R.id.supplySpinner);

        int index = spinner1.getSelectedItemPosition();
        try {
            SupplyController sc = new SupplyController();
            sc.removeSupply(supplies.get(index));

        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();

    }
    /*Method to add a supply item to list of inventory
          reads the supply item from the spinner and a string value quantity

        @Author: Cyril Abiaad

        */
    public void addToSupplyInventory(View v){
        TextView tv1 = (TextView) findViewById(R.id.quantityS);
        Spinner spinner1 = (Spinner) findViewById(R.id.supplySpinner);
        int index = spinner1.getSelectedItemPosition();

        try {
            SupplyController sc = new SupplyController();
            sc.addToSupplyInventory(supplies.get(index),tv1.getText().toString());

        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();

    }
    /*Method to remove a supply item from list of inventory
          reads the supply item from the spinner and a string value quantity

        @Author: Cyril Abiaad

        */
    public void removeFromSupplyInventory(View v){
        TextView tv1 = (TextView) findViewById(R.id.quantityS);
        Spinner spinner1 = (Spinner) findViewById(R.id.supplySpinner);
        int index = spinner1.getSelectedItemPosition();

        try {
            SupplyController sc = new SupplyController();
            sc.removeFromSupplyInventory(supplies.get(index),tv1.getText().toString());

        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();

    }
    /*Method to create a new Equipment item
        it takes 2 string values the supply name quantity

    @Author: Cyril Abiaad

    */
    public void createEquipment(View v){
        TextView equipment = (TextView) findViewById(R.id.newEquipment);
        TextView quantity = (TextView) findViewById(R.id.equipmentQuantity);
        EquipmentController ec = new EquipmentController();
        try {
            ec.createEquipment(equipment.getText().toString(), quantity.getText().toString());

        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

        }
        refreshData();
    }
    /*Method to remove a equipment item
    removes equipment item selected in spinner

@Author: Cyril Abiaad

*/
    public void removeEquipment(View v){
        Spinner spinner1 = (Spinner) findViewById(R.id.equipmentSpinner);

        int index = spinner1.getSelectedItemPosition();
        try {
            EquipmentController ec = new EquipmentController();
            ec.removeEquipment(equipments.get(index));

        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();

    }
    /*Method to add an equipment item to list of inventory
          reads the equipment item from the spinner and a string value quantity

        @Author: Cyril Abiaad

        */
    public void addToEquipmentInventory(View v){
        TextView tv1 = (TextView) findViewById(R.id.quantityE);
        Spinner spinner1 = (Spinner) findViewById(R.id.equipmentSpinner);
        int index = spinner1.getSelectedItemPosition();

        try {
            EquipmentController ec = new EquipmentController();
            ec.addToEquipmentInventory(equipments.get(index),tv1.getText().toString());

        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();

    }
    /*Method to remove an equipment item from list of inventory
          reads the equipment item from the spinner and a string value quantity

        @Author: Cyril Abiaad

        */
    public void removeFromEquipmentInventory(View v){
        TextView tv1 = (TextView) findViewById(R.id.quantityE);
        Spinner spinner1 = (Spinner) findViewById(R.id.equipmentSpinner);
        int index = spinner1.getSelectedItemPosition();

        try {
            EquipmentController ec = new EquipmentController();
            ec.removeFromEquipmentInventory(equipments.get(index),tv1.getText().toString());

        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();

    }
    /*Method to add a supply item to a menu item
              reads the supply item from the spinner and the menu item name from another spinner

            @Author: Cyril Abiaad

            */
    public void addSupply(View v)
    {   Spinner item = (Spinner) findViewById(R.id.itemSpinner);
        Spinner supply = (Spinner) findViewById(R.id.supplySpinner2);
        int index = item.getSelectedItemPosition();
        int index2 = supply.getSelectedItemPosition();

        try {
            MenuController mc = new MenuController();
            mc.addSupply(supplies.get(index2),items.get(index));

        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();

    }
    /*Method to remove a supply item to a menu item
              reads the supply item from the spinner and the menu item name from another spinner

            @Author: Cyril Abiaad

            */
    public void removeSupplyfromItem(View v){
        Spinner item = (Spinner) findViewById(R.id.itemSpinner);
        Spinner supply = (Spinner) findViewById(R.id.supplySpinner2);
        int index = item.getSelectedItemPosition();
        int index2 = supply.getSelectedItemPosition();

        try {
            MenuController mc = new MenuController();
            mc.removeSupply(supplies.get(index2),items.get(index));

        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();

    }
    /*Method to create a new menu item
            it takes 2 string values the item name and description

        @Author: Cyril Abiaad

        */
    public void createItem(View v){
        TextView item = (TextView) findViewById(R.id.newItem);
        TextView desc = (TextView) findViewById(R.id.description);
        MenuController mc = new MenuController();
        try {
            mc.createItem(item.getText().toString(), desc.getText().toString());

        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

        }
        refreshData();
    }
    /*Method to remove an item from the menu
   removes menu item selected in spinner

@Author: Cyril Abiaad

*/
    public void removeItem(View v){
        Spinner spinner1 = (Spinner) findViewById(R.id.itemSpinner);

        int index = spinner1.getSelectedItemPosition();
        try {
            MenuController mc = new MenuController();
            mc.removeItem(items.get(index));

        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();

    }
    /*Method to order an item from the menu
  reads the value from a spinner

@Author: Cyril Abiaad

*/
    public void addItemtoOrder(View v){
        Spinner item = (Spinner) findViewById(R.id.itemSpinner2);
        int index = item.getSelectedItemPosition();

        try {
            MenuController mc = new MenuController();
            mc.makeOrder(items.get(index));

        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();
    }

    /*Method to delete the last order commited

@Author: Cyril Abiaad

*/
    public void removeItemFromOrder(View v){

        try {
            MenuController mc = new MenuController();
            mc.removeOrder();

        }
        catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        refreshData();


    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}




