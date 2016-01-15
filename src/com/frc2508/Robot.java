
package com.frc2508;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {
    RobotDrive drive;
    LogitechGamepad gamepad;
    Talon talon;

    final String auto1 = "Autonomous #1";
    final String auto2 = "Autonomous #2";
    SendableChooser chooser;

    public Robot() {
        drive = new RobotDrive(0, 1, 2, 3);
        drive.setExpiration(0.1);
        gamepad = new LogitechGamepad();
        talon = new Talon(4);
    }
    
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault(auto1, auto1);
        chooser.addObject(auto2, auto2);
        SmartDashboard.putData("Auto modes", chooser);
    }

    public void autonomous() {
    	String autoSelected = (String) chooser.getSelected();
		System.out.println("Auto selected: " + autoSelected);
    	
    	switch(autoSelected) {
    	case auto1:
    		// Runs when auto #1 is selected
            drive.setSafetyEnabled(false);
            drive.drive(-0.5, 1.0);	// spin at half speed
            Timer.delay(2.0);		//    for 2 seconds
            drive.drive(0.0, 0.0);	// stop robot
            break;
            
    	case auto2:
    		// Runs when auto #2 is selected
            drive.setSafetyEnabled(false);
            drive.drive(-0.5, 0.0);	// drive forwards half speed
            Timer.delay(2.0);		//    for 2 seconds
            drive.drive(0.0, 0.0);	// stop robot
            break;
            
        default:
        	// shouldn't ever happen
    	}
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        drive.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
            drive.tankDrive(gamepad.getLeftStickY(), gamepad.getRightStickY());
            
            if (gamepad.getButtonLB()) {
            	talon.set(-0.3);
            }
            else if (gamepad.getButtonRB()) {
            	talon.set(0.3);
            }
            else {
            	talon.set(0);
            }
            
            Timer.delay(0.005); // wait for a motor update time
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}
