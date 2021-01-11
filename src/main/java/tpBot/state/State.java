package tpBot.state;

import tpBot.TpBot;
import tpBot.input.DataPacket;
import tpBot.output.ControlsOutput;

public abstract class State {

    public abstract ControlsOutput exec(DataPacket data, TpBot bot);

}
