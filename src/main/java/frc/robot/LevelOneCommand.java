package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.arms.ArmsSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.elevator.ElevatorConstants.ElevatorLevels;

public class LevelOneCommand extends CommandBase {

    private ElevatorSubsystem elevator;
    private ArmsSubsystem arms;
    private JoystickButton[] buttons;
    private JoystickButton floor;
    private JoystickButton twoCommand;
    
    public LevelOneCommand(ElevatorSubsystem elevatorSubsystem, ArmsSubsystem armsSubsystem, JoystickButton[] buttonsList) {
        elevator = elevatorSubsystem;
        arms = armsSubsystem;
        buttons = buttonsList;

        floor = buttons[0];
        
        twoCommand = buttons[2]; 
    }

    @Override
    public void execute() {
        elevator.moveTo(ElevatorLevels.FLOOR);
    }

    @Override
    public boolean isFinished() {
        // level 1 -> level floor
        if (floor.get() && !arms.areArmsOpen()) {
            LevelFloorCommand floorCommand = new LevelFloorCommand(elevator, arms, buttons);
            CommandScheduler.getInstance().schedule(floorCommand);
        }


        // level 1 -> level two
        if (twoCommand.get() && arms.areArmsClosed()) {
            LevelTwoCommand twoCommand = new LevelTwoCommand(elevator, arms, buttons);
            CommandScheduler.getInstance().schedule(twoCommand);
            
        }
        
        
        
        return false;
    }
