/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.arms.ArmsSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

public class Robot extends TimedRobot {
    private ElevatorSubsystem elevator;
    private ArmsSubsystem arms;
    private Joystick joystick = new Joystick(0);

    @Override
    public void robotInit() {
        elevator = new ElevatorSubsystem();
        arms = new ArmsSubsystem();

        elevator.initialize();
        arms.initialize();
        
        arms.gimmeElevator(elevator);
        elevator.gimmeArms(arms);
    }

    @Override
    public void robotPeriodic() {
        // always run the CommandScheduler during periodic
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // square == 1
        JoystickButton levelFloor = new JoystickButton(joystick, 1);
        // cross == 2
        JoystickButton levelOne   = new JoystickButton(joystick, 2);
        // circle == 4
        JoystickButton levelTwo   = new JoystickButton(joystick, 3);

        // L1 == 5
        JoystickButton toggleArms = new JoystickButton(joystick, 5);
        toggleArms.whenPressed(new InstantCommand(
            () -> arms.toggle(), // lambda expression for command
            arms // requires arm subsystem
        ));

        JoystickButton[] buttonsList = {levelFloor, levelOne, levelTwo};

        LevelFloorCommand floorCommand = new LevelFloorCommand(elevator, arms, buttonsList);

    }

    public static Robot win() {
        return new Robot();
    }

}