package tpBot.input.car;


import rlbot.flat.PlayerInfo;
import tpBot.vector.Vec3;

public class CarOrientation {

    /** The direction that the front of the car is facing */
    public final Vec3 forward;

    /** The direction the roof of the car is facing. (0, 0, 1) means the car is upright. */
    public final Vec3 up;

    /** The direction that the left side of the car is facing. */
    public final Vec3 left;

    public CarOrientation(Vec3 forward, Vec3 up) {

        this.forward = forward;
        this.up = up;
        this.left = forward.cross(up);
    }

    public static CarOrientation fromFlatbuffer(PlayerInfo playerInfo) {
        return convert(
                playerInfo.physics().rotation().pitch(),
                playerInfo.physics().rotation().yaw(),
                playerInfo.physics().rotation().roll());
    }

    /**
     * All params are in radians.
     */
    private static CarOrientation convert(double pitch, double yaw, double roll) {

        double cp = Math.cos(pitch);
        double sp = Math.sin(pitch);
        double cy = Math.cos(yaw);
        double sy = Math.sin(yaw);
        double cr = Math.cos(roll);
        double sr = Math.sin(roll);

        Vec3 forward = new Vec3(cp * cy, cp * sy, sp);
        Vec3 up = new Vec3(-cr * cy * sp - sr * sy, -cr * sy * sp + sr * cy, cp * cr);

        return new CarOrientation(forward, up);
    }

    /**public double getPitch(DataPacket data){
     Vec3 foward = this.foward.normalized();
     Vec3 desiredFowardAngularVel = foward.cross(data.car.orientation.foward);
     desiredFowardAngularVel.scaledToMag(-1);
     Vec3 desiredUpAngularVel = up.cross(data.car.orientation.up);
     desiredUpAngularVel.scaledToMag(-1);

     double pitch = desiredFowardAngularVel.dot(data.car.orientation.left);
     return pitch;
     }*/

}

