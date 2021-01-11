package tpBot.state;

import rlbot.cppinterop.RLBotDll;
import rlbot.gamestate.*;
import tpBot.TpBot;
import tpBot.input.DataPacket;
import tpBot.output.ControlsOutput;
import tpBot.vector.Vec3;

public class TpState extends State{

    private double lastTime = 0.0;
    private double stateSetLastTime = 0.0;

    public TpState() {
    }

    @Override
    public ControlsOutput exec(DataPacket data, TpBot bot) {
        ControlsOutput controls = new ControlsOutput();

        GameState game = new GameState();
        CarState car = new CarState();
        PhysicsState physics = new PhysicsState();
        Vec3 ballPos = data.ball.position;
        DesiredVector3 dVec3 = new DesiredVector3((float) ballPos.x, (float) ballPos.y, (float) ballPos.z);
        DesiredVector3 dVelocity = new DesiredVector3(1f, 1f, 1f);
        car.withPhysics(physics.withLocation(dVec3).withVelocity(dVelocity));

        game.withCarState(bot.getIndex(), car);

        game.withGameInfoState(new GameInfoState().withWorldGravityZ(0.00001f));
        if (data.time - stateSetLastTime > 5){
            stateSetLastTime = data.time;
            RLBotDll.setGameState(game.buildPacket());
        }
        return controls;
    }
}
