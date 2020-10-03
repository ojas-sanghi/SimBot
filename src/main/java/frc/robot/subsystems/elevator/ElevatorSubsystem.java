package frc.robot.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.arms.ArmsSubsystem;
import frc.robot.subsystems.elevator.ElevatorConstants.ElevatorLevels;

public class ElevatorSubsystem extends SubsystemBase {
    // motor that moves elevator up and down
    private WPI_TalonFX elevatorMotor;

    public void initialize() {
        // create motor, add tuning constants
        elevatorMotor = new WPI_TalonFX(1);
        elevatorMotor.selectProfileSlot(0, 0);
        elevatorMotor.config_kP(0, 5);
        elevatorMotor.config_kD(0, 1000);

        // for testing, don't uncomment
        //elevatorMotor.set(ControlMode.Position, 4000);
    }

    private ArmsSubsystem arms;
    public void gimmeArms(ArmsSubsystem arms) {
        this.arms = arms;
    }

    public void periodic() {
        // telemetry
        SmartDashboard.putNumber("Elevator/Position (ticks)", elevatorMotor.getSelectedSensorPosition() + 0.00000001*Math.random());
        SmartDashboard.putBoolean("Elevator/Reached goal", isWithinTolerance());
    }



    // return if the elevator is within a tolerance of its target position
    public boolean isWithinTolerance() {
        return Math.abs(elevatorMotor.getClosedLoopError()) <= ElevatorConstants.TICK_TOLERANCE;
    }

    // move elevator to position
    // ideally, we would want to actually change this method and prevent unsafe state transitions
    // but instead we'll put that in the state machine ;)))
    // aka DO NOT EDIT THIS
    private ElevatorLevels currentLevel = ElevatorLevels.FLOOR;
    public void moveTo(ElevatorLevels level) {
        elevatorMotor.set(ControlMode.Position, level.getTicks());
        currentLevel = level;
    }

    public ElevatorLevels getCurrentLevel() { return currentLevel; }
}
