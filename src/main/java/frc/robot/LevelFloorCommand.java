===package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.arms.ArmsSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.elevator.ElevatorConstants.ElevatorLevels;

public class LevelFloorCommand extends CommandBase {

    private ElevatorSubsystem elevator;
    private ArmsSubsystem arms;
    private JoystickButton[] buttons;
    private JoystickButton one;
    
    public LevelFloorCommand(ElevatorSubsystem elevatorSubsystem, ArmsSubsystem armsSubsystem, JoystickButton[] buttonsList) {
        elevator = elevatorSubsystem;
        arms = armsSubsystem;
        buttons = buttonsList;

        one = buttons[1];
    }

    @Override
    public void execute() {
        elevator.moveTo(ElevatorLevels.FLOOR);
    }

    @Override
    public boolean isFinished() {

        if (one.get() && !arms.areArmsOpen()) {
            LevelOneCommand oneCommand = new LevelOneCommand(elevator, arms, buttons);
            CommandScheduler.getInstance().schedule(oneCommand);
        }

        return false;
    }
}
