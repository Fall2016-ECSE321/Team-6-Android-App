package ca.mcgill.ecse321.ftmanagementsystem;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import ca.mcgill.ecse321.FoodTruckManagementSystem.controller.InvalidInputException;
import ca.mcgill.ecse321.FoodTruckManagementSystem.controller.StaffController;
import ca.mcgill.ecse321.FoodTruckManagementSystem.model.FoodTruckManager;
import ca.mcgill.ecse321.FoodTruckManagementSystem.model.Shift;
import ca.mcgill.ecse321.FoodTruckManagementSystem.model.Staff;
import ca.mcgill.ecse321.ftmanagementsystem.DatePickerFragment;
import ca.mcgill.ecse321.ftmanagementsystem.R;
import ca.mcgill.ecse321.ftmanagementsystem.TimePickerFragment;

/* Maxence: "We need to comment on every methods done so far."
    Also, this is a test for the PUSH optiong
 */


public class MainActivity extends AppCompatActivity {
    private HashMap<Integer, Staff> staffMembers;
    private HashMap<Integer, Shift> shifts;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        refreshData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

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
            shiftAdapter.add(s.getShiftDate() + " (" + s.getStartTime() + "--" + s.getEndTime() +")");
            this.shifts.put(j, s);
        }
        spinner2.setAdapter(shiftAdapter);
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

    public void addStaff(View v) {
        TextView name = (TextView) findViewById(R.id.newstaff_name);
        TextView role = (TextView) findViewById(R.id.newstaff_role);
        StaffController sc = new StaffController();
        String error = "";
        try {
            sc.createStaff(name.getText().toString(), role.getText().toString());

            if (name == null) {
                error = error + " Staff name cannot be empty!";
            }

            if (role == null) {
                error = error + " Staff member must have a role!";
            }
            if (error.length() > 0) {
                throw new InvalidInputException(error);
            }
        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

        }
        refreshData();
    }
    public void removeShift(View v){
        Spinner spinner1 = (Spinner) findViewById(R.id.shiftSpinner);

        int index = spinner1.getSelectedItemPosition();
        try {
            StaffController sc = new StaffController();
            sc.removeShift(shifts.get(index));
            String error = "Must select a shift to remove";
            if (shifts.get(index) == null)
                throw new InvalidInputException(error);
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
    public void removeStaff(View v) {
        //Initialize staff spinner and get staff from spinner
        Spinner spinner1 = (Spinner) findViewById(R.id.nameSpinner);

        int index = spinner1.getSelectedItemPosition();
        //Try removing staff
        try {
            StaffController sc = new StaffController();
            sc.removeStaff(staffMembers.get(index));
            String error = "Must select a staff to remove";
            if (staffMembers.get(index) == null)
                throw new InvalidInputException(error);
        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        refreshData();

    }

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
//            Date temp = Date.valueOf(tv1.getText().toString());

//            Log.d("DAte",temp.toString());

            sc.createShift(sqldate, time1, time2);

            if (tv1 == null) {
                error = error + " Shift date cannot be empty!";
            }

            if (tv2 == null) {
                error = error + " Shift start time cannot be empty!";
            }

            if (tv3 == null) {
                error = error + " Shift end time cannot be empty!";
            }

            error = error.trim();
            if (error.length() > 0)
                throw new InvalidInputException(error);
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

    public void AddStafftoShift(View view) {

        Spinner spinner1 = (Spinner) findViewById(R.id.shiftSpinner);
        int index = spinner1.getSelectedItemPosition();

        Spinner spinner2 = (Spinner) findViewById(R.id.nameSpinner);
        int index2 = spinner2.getSelectedItemPosition();
        try {
            StaffController sc = new StaffController();
            Shift s = shifts.get(index);
            Staff f = staffMembers.get(index2);

            String error = "";
            if(f == null) {
                error = error + " Staff member must be selected!";
            }

            if(s == null) {
                error = error + " Shift must be selected!";
            }

//            if(f != null && s != null && !s.addStaff(f)) {
//                error = error + " Shift is already assigned to selected staff!";
//            }

                if(f != null && s != null && !s.addStaff(f))
                    error = error + " Shift is already assigned to selected staff!";

            error.trim();
            if (error.length() >0)
                throw new InvalidInputException(error);
        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        refreshData();

    }
    public void removeShiftfromStaff(View v){
        Spinner spinner1 = (Spinner) findViewById(R.id.shiftSpinner);
        int index = spinner1.getSelectedItemPosition();

        Spinner spinner2 = (Spinner) findViewById(R.id.nameSpinner);
        int index2 = spinner2.getSelectedItemPosition();
        try {
            StaffController sc = new StaffController();
            Shift s = shifts.get(index);
            Staff f = staffMembers.get(index2);

            String error = "";
            if(f == null) {
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
                throw new InvalidInputException(error);
        } catch (InvalidInputException e) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        refreshData();

    }


    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
        public SpinnerActivity() {
        }

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
