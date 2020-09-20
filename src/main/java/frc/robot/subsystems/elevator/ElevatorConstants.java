package frc.robot.subsystems.elevator;

public class ElevatorConstants {
    public static int TICK_TOLERANCE = 10;

    public static enum ElevatorLevels {
        FLOOR (0),
        ONE (5000),
        TWO (6000);

        private int ticks;
        ElevatorLevels(int ticks) {
            this.ticks = ticks;
        }

        public int getTicks() { return ticks; }
    }
}