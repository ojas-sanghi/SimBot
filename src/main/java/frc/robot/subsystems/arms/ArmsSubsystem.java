package frc.robot.subsystems.arms;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.elevator.ElevatorConstants.ElevatorLevels;

public class ArmsSubsystem extends SubsystemBase {
    // left and right arm motors
    private WPI_TalonSRX leftArm;
    private WPI_TalonSRX rightArm;

    private ElevatorSubsystem elevator;
    public void gimmeElevator(ElevatorSubsystem elevator) {
        this.elevator = elevator;
    }

    public void initialize() {
        leftArm = new WPI_TalonSRX(2);
        rightArm = new WPI_TalonSRX(3);

        leftArm.set(ControlMode.Follower, 3);
    }

    public void periodic() {
        SmartDashboard.putNumber("Arms/Left %",  leftArm.getMotorOutputPercent());
        SmartDashboard.putNumber("Arms/Right %", rightArm.getMotorOutputPercent());
    }



    // flag indicating arms are open
    private boolean areTheyOpen = true;
    public void toggle() {
        // make sure it is safe to toggle. Don't toggle in mid air
        if (elevator.getCurrentLevel() == ElevatorLevels.ONE || !elevator.isWithinTolerance()) {
            return;
        }

        // switch from open to close and close to open
        if (areTheyOpen) {
            rightArm.set(ControlMode.PercentOutput, 1);
        } else {
            rightArm.set(ControlMode.PercentOutput, 0);
        }

        // toggle boolean flag
        areTheyOpen = !areTheyOpen;
    }
}