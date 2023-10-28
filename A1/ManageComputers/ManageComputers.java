import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Scanner;

import domain.BaseComputer;
import domain.DesktopComputer;
import domain.LaptopComputer;

public class ManageComputers {
    //Constant
    private static final String DATADIRLOCATION="./data/";
   
    
    //Declare required variables/objects
    private final Scanner in = new Scanner(System.in);
    private final ArrayList<BaseComputer> allList = new ArrayList<BaseComputer>();
    
    //Constructor: Show the program menu to the user on-screen when program run/instantiated
    public ManageComputers() {
        showMenu();
    }

    //Given the menu option number entered by the user (choice), runs appropriate method
    public void runMenu(int choice) {

        switch (choice) {

            case 0:
                //Stop program immediately
                System.exit(0);
                break;

            case 1:
                //Deserialize files in data directory into objects in ArrayList in RAM.
                deserialList();
                break;

            case 2:
                //Serialize objects in ArrayList in RAM into files in data directory.
                serialList();
                break;

            case 3:
                //Loop through all computers in system, print out details of each
                for (int i = 0; i < allList.size(); i++) {
                    System.out.println("------------" + "\n" + "COMPUTER #" + (i + 1));
                    System.out.println(allList.get(i).toString());
                }
                break;

            case 4:
                //Add appropriate type of new computer (preference) to system
                int preference = addSubMenu();

                //Loop allows user to add multiple computers quickly
                while (preference != 3) {

                    switch (preference) {

                        case 1:
                            LaptopComputer lp = addLaptopComputer();
                            if (lp != null) {
                                allList.add(lp);
                            }
                            break;

                        case 2:
                            DesktopComputer dp = addDesktopComputer();
                            if (dp != null) {
                                allList.add(dp);
                            }
                            break;

                            case 3:
                                showMenu();
                            break;

                        default:
                            System.out.println("*** Invalid Choice***");

                    }
                    preference = addSubMenu();
                }
                break;

            case 5:
                deleteComputer();
                break;

            case 6:
                editComputer();
                break;

            default:
                System.out.println("Not a valid choice");

        }

        if (choice != 0)
            showMenu();

    }

    //Deserialize all files in data directory back into objects in memory
    private void deserialList() {

		try {
			allList.clear();
			boolean exist = true;

			for (int i = 0; exist == true; i++) {

				File tmpDir = new File(DATADIRLOCATION + (i + 1) + ".txt");

				if (tmpDir.exists()) {

					FileInputStream fileIn;

					fileIn = new FileInputStream(tmpDir);
					ObjectInputStream in = new ObjectInputStream(fileIn);
					Object o = in.readObject();
					allList.add((BaseComputer)o);
					
					in.close();
					fileIn.close();

				} else {
					exist = false;
				}
			}

		} catch (AccessControlException e) {
			System.out.println("*** ERROR: access Denied ***");
		} catch (FileNotFoundException e) {
			System.out.println("*** ERROR: Requested file not found ***");
		} catch (IOException e) {
			System.out.println("*** ERROR: IOException occured ***");
		} catch (ClassNotFoundException e) {
			System.out.println("*** ERROR: Class not found ***");
		}

	}
    //Serialize objects in memory into files in data directory (one file per object in memory)
    private void serialList() {

        try {

            File tmpDir = new File(DATADIRLOCATION);
            String[] myFiles;

            //First delete all existing files in the directory
            if (tmpDir.isDirectory()) {

                myFiles = tmpDir.list();

                for (int i1 = 0; i1 < myFiles.length; i1++) {
                    File myFile = new File(tmpDir, myFiles[i1]);
                    myFile.delete();
                }

            }

            //Then write out new copies of files from in-memory data
            for (int i = 0; i < allList.size(); i++) {

                FileOutputStream fout = new FileOutputStream(DATADIRLOCATION + (i + 1) + ".txt");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(allList.get(i));

                oos.close();
            }

        } catch (AccessControlException e) {
            System.out.println("*** ERROR: access denied ***");
        } catch (IOException e) {
            System.out.println("*** ERROR: IOException occured ***");
        }

    }

    //User can edit data for in-memory object
    private void editComputer() {

        System.out.println("EDIT COMPUTER");
        System.out.println("Enter number of computer to edit: ");
        int option = in.nextInt();

        //Ensure that index of object entered by user is value 
        if (option > 0 && option <= allList.size()) {

            //Need to know type of object we're editing so we can add a revised version of
            //the appropriate type
            if (allList.get(option - 1) instanceof LaptopComputer) { //Laptop
                LaptopComputer lp = addLaptopComputer();
                if (lp != null) {
                    allList.set(option - 1, lp);
                }
            } else if (allList.get(option - 1) instanceof DesktopComputer) { //Desktop
                DesktopComputer dp = addDesktopComputer();
                if (dp != null) {
                    allList.set(option - 1, dp);
                }
            } else {
                System.out.println("*** ERROR: Invalid computer type ***");
            }
        } else {
            System.out.println("*** ERROR: Invalid computer number entered ***");
        }

    }

    //Delete in-memory object for computer being deleted (does not change files on drive until changes saved)
    private void deleteComputer() {

        System.out.println("DELETE COMPUTER");
        System.out.println("Enter number of computer to delete: ");
        int option = in.nextInt();

        //Ensure index of object is valid
        if (option <= allList.size() && option > 0)
            allList.remove(option - 1);
        else
            System.out.println("*** ERROR: Invalid computer number entered ***");
    }

    //Prompt for and read in data for new desktop computer
    private DesktopComputer addDesktopComputer() {

        System.out.println("Enter CPU type (i5/i7) : ");
        String cpu = in.next();
        while (!cpu.matches("(i5|i7)")) {
            System.out.println("Invalid input. Please enter a valid CPU type (i5/i7).");
            System.out.println("Enter CPU type (i5/i7) : ");
            cpu = in.next();
        }
        System.out.println("Enter RAM size (8/16) : ");
        String ram = in.next();
        while (!ram.matches("(8|16)")) {
            System.out.println("Invalid input. Please enter a valid RAM size (8/16).");
            System.out.println("Enter RAM size (8/16) : ");
            ram = in.next();
        }
        System.out.println("Enter disk size (250/500) : ");
        String disk = in.next();
        while (!disk.matches("(250|500)")) {
            System.out.println("Invalid input. Please enter a valid disk size (250/500).");
            System.out.println("Enter disk size (250/500) : ");
            disk = in.next();
        }
        System.out.println("Enter GPU (intel/amd/nvidia) : ");
        String gpu = in.next();
        while (!gpu.matches("(intel|amd|nvidia)")) {
            System.out.println("Invalid input. Please enter a valid GPU type (intel/amd/nvidia).");
            System.out.println("Enter GPU (intel/amd/nvidia) : ");
            gpu = in.next();
        }

        return DesktopComputer.getInstance(cpu, ram, disk, gpu);
    }

    //Prompt for and read in data for new laptop computer
    private LaptopComputer addLaptopComputer() {

        System.out.println("Enter CPU type (i5/i7) : ");
        String cpu = in.next();
        System.out.println("Enter RAM size (8/16) : ");
        String ram = in.next();
        System.out.println("Enter disk size (250/500) : ");
        String disk = in.next();
        System.out.println("Enter screen size (13/15) : ");
        String screen = in.next();

        return LaptopComputer.getInstance(cpu, ram, disk, screen);
    }

    //Prompt user to enter which type of computer they want to add to the system
    private int addSubMenu() {

        System.out.println("ADD NEW COMPUTER");
        System.out.println("1. Add new LaptopComputer");
        System.out.println("2. Add new DesktopComputer");
        System.out.println("3. Back to main menu");
        int preference = in.nextInt();

        return preference;

    }

    //Program menu. Shows program options and prompts user to enter their selection. Calls
    //runMenu method while passing in user selection to get program to perform selected action
    public void showMenu() {

        System.out.println("====MENU====");

        System.out.println("1. Load");
        System.out.println("2. Save");
        System.out.println("3. List");
        System.out.println("4. Add");
        System.out.println("5. Delete");
        System.out.println("6. Edit");
        System.out.println("0. Exit");
        System.out.println("Enter selection: ");

        int choice = in.nextInt();

        runMenu(choice);
    }
    public static void main(String[] args) {
    
    new ManageComputers();
}

}
