package ca.mcgill.ecse321.ftmanagementsystem;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.FoodTruckManagementSystem.controller.InvalidInputException;
import ca.mcgill.ecse321.FoodTruckManagementSystem.controller.StaffController;
import ca.mcgill.ecse321.FoodTruckManagementSystem.model.FoodTruckManager;
import ca.mcgill.ecse321.FoodTruckManagementSystem.model.Staff;
import ca.mcgill.ecse321.FoodTruckManagementSystem.persistence.PersistenceXStream;

import static org.junit.Assert.*;

/**
 * Created by Max on 2016-12-02.
 */
public class TabbedActivityTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        PersistenceXStream.setFilename("test" + File.separator + "ca" + File.separator + "mcgill"+ File.separator + "ecse321" + File.separator + "foodtruckmanagementsystem" + File.separator + "persistence"+ File.separator +"data.xml");
        PersistenceXStream.setAlias("staff", Staff.class);
        PersistenceXStream.setAlias("manager", FoodTruckManager.class);
    }


    @After
    public void tearDown() throws Exception {
        //Clear all registrations
        FoodTruckManager fm = FoodTruckManager.getInstance();
        fm.delete();
    }

    @Test
    public void addStaff() throws Exception {
        /*FoodTruckManager fm = FoodTruckManager.getInstance();
        assertEquals(0, fm.getStaffs().size());

        String name = "Huck";
        String role = "Cook";

        StaffController sc = new StaffController();
        try {
            sc.createStaff(name, role);
        } catch (InvalidInputException e) {
            //Check no error occurred
            fail();
        }

        checkResultStaff(name, role, fm);

        FoodTruckManager fm2 = (FoodTruckManager) PersistenceXStream.loadFromXMLwithXStream();

        //check file contents
        checkResultStaff(name, role, fm2);*/
    }

    @Test
    public void addShift() throws Exception {

    }

    @Test
    public void createSupply() throws Exception {

    }

    @Test
    public void addToSupplyInventory() throws Exception {

    }

    @Test
    public void createEquipment() throws Exception {

    }

    @Test
    public void addToEquipmentInventory() throws Exception {

    }

    @Test
    public void addSupply() throws Exception {

    }

    @Test
    public void createItem() throws Exception {

    }

    @Test
    public void addItemtoOrder() throws Exception {

    }

    private void checkResultStaff(String name, String role, FoodTruckManager fm2)
    {
        assertEquals(1, fm2.getStaffs().size());
        assertEquals(name, fm2.getStaff(0).getName());
        assertEquals(role, fm2.getStaff(0).getRole());
    }
    private void checkResultShift(Time startTime, Time endTime, Date date, FoodTruckManager fm2)
    {
        assertEquals(0, fm2.getStaffs().size());
        assertEquals(1, fm2.getShifts().size());
        assertEquals(date.toString(), fm2.getShift(0).getShiftDate().toString());
        assertEquals(startTime.toString(), fm2.getShift(0).getStartTime().toString());
        assertEquals(endTime.toString(), fm2.getShift(0).getEndTime().toString());
        assertEquals(0, fm2.getShift(0).getStaff().size());
    }
    private void checkResultAddShiftToStaff(String name, String role, Date date, Time startTime, Time endTime, FoodTruckManager fm2)
    {
        assertEquals(1, fm2.getStaffs().size());
        assertEquals(name, fm2.getStaff(0).getName());
        assertEquals(role, fm2.getStaff(0).getRole());
        assertEquals(1, fm2.getShifts().size());
        assertEquals(date.toString(), fm2.getShift(0).getShiftDate().toString());
        assertEquals(startTime.toString(), fm2.getShift(0).getStartTime().toString());
        assertEquals(endTime.toString(), fm2.getShift(0).getEndTime().toString());
        assertEquals(1, fm2.getShift(0).getStaff().size());
        assertEquals(fm2.getStaff(0), fm2.getShift(0).getStaff(0));
    }



}