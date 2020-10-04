package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.arms.ArmsSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.elevator.ElevatorConstants.ElevatorLevels;

public class LevelTwoCommand extends CommandBase {

    private ElevatorSubsystem elevator;
    private ArmsSubsystem arms;
    private JoystickButton[] buttons;
    private JoystickButton floor;
    private JoystickButton levelOne;
    
    public LevelTwoCommand(ElevatorSubsystem elevatorSubsystem, ArmsSubsystem armsSubsystem, JoystickButton[] buttonsList) {
        elevator = elevatorSubsystem;
        arms = armsSubsystem;
        buttons = buttonsList;

        floor = buttons[0];
        levelOne = buttons[1];
    }

    @Override
    public void execute() {
        elevator.moveTo(ElevatorLevels.TWO);
    }

    @Override
    public boolean isFinished() {
        // level 2 -> level floor
        if (floor.get() && !arms.areArmsOpen()) 
        {
            LevelFloorCommand floorCommand = new LevelFloorCommand(elevator, arms, buttons);
            CommandScheduler.getInstance().schedule(floorCommand);
        }

        // level 2 -> level 1
        if (levelOne.get() && !arms.areArmsOpen())
        {
            LevelOneCommand oneCommand = new LevelOneCommand(elevator, arms, buttons);
            CommandScheduler.getInstance().schedule(oneCommand);
        }

        return false;
    }
    
}
