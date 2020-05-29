# SimBot
The SimBot is an empty robot project for testing out ideas. It comes with

* FRCSim support with a simple launch condition
* An empty robot class
* vendor libraries for navx and CTRE
* Support for New style WPI commands

# Configuring the Sim
The first time you launch the sim it will create an [`FRCSim/config.yaml`](FRCSim/config.yaml) file with settings for whatever motors you added. If you add new motors you will have to delete this file and run the sim again for the config.yaml to be updated.

I find it easier to create a set of defaults at the top of my config.yaml file and reference those when setting up motors.

## config.yaml defaults
```yaml
defaults:
  # motor ids for use in the sim
  ids:
    leftDriveTalonLeader: 1
    leftDriveTalonFollower: 2
    rightDriveTalonLeader: 3
    rightDriveTalonFollower: 4
    shooter: 5
  # falcon motor specs
  falcon:
    stallTorque: 4.69
    stallCurrent: 257
    voltage: 12
    maxRPM: 6380
    mass: .5
    diameter: .06
  # CTRE bag motor specs
  bag:
    stallTorque: 0.4
    stallCurrent: 53.0
    voltage: 12.0
    maxRPM: 13180
    mass: 0.32
    diameter: 0.0404

  # gear ratios for drive falcons to encoder is 1:1
  driveGearing: '{{ 1 }}'
  
  # configure a shooter to be 1 to 2
  shooterGearing: "{{ .48 / 1 }}"

```

## Configuring a motor
When you launch the sim with a motor, it puts a motors list with one motor in it into the
transmissions section, like this:

```yaml
transmissions:
- motors:
  - id: 1
    name: BAG (1)
    model: BAG
    kt: 0.0
    kv: 0.0
    resistance: 0.0
    inertia: 0.0
    leftDrive: false
    rightDrive: false
    stallTorque: 0.4
    stallCurrent: 53.0
    voltage: 12.0
    maxRPM: 13180
    mass: 0.32
    diameter: 0.0404
    encoderCountsPerRevolution: 1024
  gearRatio: 1.0
  efficiency: 1.0
```

This defaults ot a generic bag motor. You can replace this section with more detail. For
example, if you created a ShooterSubsystem with a falon shooter you could replace the motors
section like this:

```yaml
- motors:
    - id: '{{ defaults.ids.shooter }}' # this is our shooter motor, so configure it by id
      name: 'Shooter ({{ defaults.ids.shooter }})' # give the motor a helpful name for the UI
      model: Falcon # We are using a falcon for our shooter

      # all these specs are from our defaults entry above for the falcon motor
      stallTorque: '{{ defaults.falcon.stallTorque }}'
      stallCurrent: '{{ defaults.falcon.stallCurrent }}'
      voltage: '{{ defaults.falcon.voltage }}'
      maxRPM: '{{ defaults.falcon.maxRPM }}'
      mass: '{{ defaults.falcon.mass * 10 }}'
      diameter: '{{ defaults.falcon.diameter }}'
  # setup the gear ratio of the shooter for simulation
  gearRatio: '{{ defaults.shooterGearing }}'
  efficiency: 1.0
```

## Configuring a DriveBase
FRCSim supports configuring a drive base with left and right motor groups. Similar to above, but
with an additional leftDrive and rightDrive boolean value, as well as encoderCountsPerRevolution
so the sim knows how many "ticks" of the motor encoder count towards a full rotation.

The below yaml will configure a drive base with two left motors and two right motors, each
in a group. It uses the defaults we created above.

```yaml
- motors:
    - id: '{{ defaults.ids.leftDriveTalonLeader }}'
      name: 'Left Drive ({{ defaults.ids.leftDriveTalonLeader }})'
      model: falcon
      stallTorque: '{{ defaults.falcon.stallTorque }}'
      stallCurrent: '{{ defaults.falcon.stallCurrent }}'
      voltage: '{{ defaults.falcon.voltage }}'
      maxRPM: '{{ defaults.falcon.maxRPM }}'
      mass: '{{ defaults.falcon.mass }}'
      diameter: '{{ defaults.falcon.diameter }}'
      encoderCountsPerRevolution: 2048
      leftDrive: true
    - id: '{{ defaults.ids.leftDriveTalonFollower }}'
      name: 'Left Drive Follower ({{ defaults.ids.leftDriveTalonFollower }})'
      model: falcon
      stallTorque: '{{ defaults.falcon.stallTorque }}'
      stallCurrent: '{{ defaults.falcon.stallCurrent }}'
      voltage: '{{ defaults.falcon.voltage }}'
      maxRPM: '{{ defaults.falcon.maxRPM }}'
      mass: '{{ defaults.falcon.mass }}'
      diameter: '{{ defaults.falcon.diameter }}'
      encoderCountsPerRevolution: 2048
      leftDrive: true
  gearRatio: '{{ defaults.driveGearing }}'
  efficiency: 1
- motors:
    - id: '{{ defaults.ids.rightDriveTalonLeader }}'
      name: 'Right Drive ({{ defaults.ids.rightDriveTalonLeader }})'
      model: falcon
      stallTorque: '{{ defaults.falcon.stallTorque }}'
      stallCurrent: '{{ defaults.falcon.stallCurrent }}'
      voltage: '{{ defaults.falcon.voltage }}'
      maxRPM: '{{ defaults.falcon.maxRPM }}'
      mass: '{{ defaults.falcon.mass }}'
      diameter: '{{ defaults.falcon.diameter }}'
      encoderCountsPerRevolution: 2048
      rightDrive: true
    - id: '{{ defaults.ids.rightDriveTalonFollower }}'
      name: 'Right Drive Follower ({{ defaults.ids.rightDriveTalonFollower }})'
      model: falcon
      stallTorque: '{{ defaults.falcon.stallTorque }}'
      stallCurrent: '{{ defaults.falcon.stallCurrent }}'
      voltage: '{{ defaults.falcon.voltage }}'
      maxRPM: '{{ defaults.falcon.maxRPM }}'
      mass: '{{ defaults.falcon.mass }}'
      diameter: '{{ defaults.falcon.diameter }}'
      encoderCountsPerRevolution: 2048
      rightDrive: true
  gearRatio: '{{ defaults.driveGearing }}'
  efficiency: 1
```

